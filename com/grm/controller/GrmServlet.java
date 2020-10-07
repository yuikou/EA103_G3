package com.grm.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.grm.model.GrmService;
import com.grm.model.GrmVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class GrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GrmServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
//先獲得發出請求的美容店編號
		HttpSession session = req.getSession();
		String salno = (session.getAttribute("salno")).toString();
		String action = req.getParameter("action");
		
//System.out.println(salno);	
//System.out.println(action);	
		
// 來自addGrm.jsp的請求, 新增美容師資料		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			String gname = req.getParameter("gname");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (gname == null || gname.trim().length() == 0) {
				errorMsgs.add("美容師暱稱: 請勿空白");
			} else if(!gname.trim().matches(nameReg)) { //以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("美容師暱稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
            }
			
			String ginfo = req.getParameter("ginfo").trim();
		
			InputStream in = req.getPart("grmPic").getInputStream();
			byte[] gpic = new byte[in.available()];
			in.read(gpic);
			in.close();
			
			Integer isdel = new Integer(req.getParameter("isDelete").trim());
			
			GrmVO grmvo = new GrmVO();
			grmvo.setGroomerName(gname);
			grmvo.setGroomerInfo(ginfo);
			grmvo.setGroomerPic(gpic);
			grmvo.setIsDelete(isdel);
			grmvo.setSalNo(salno);
			
			// 錯誤處理
			
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("grmVO", grmvo);
				RequestDispatcher failure = req.getRequestDispatcher("/back-end/grm/addGrm.jsp");
				failure.forward(req, res);
				return;
			}
			/***************************2.開始新增資料***************************************/
			GrmService grmSvc = new GrmService();
			grmSvc.addGrmVO(salno, gname, ginfo, gpic, isdel);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/back-end/grm/listAllGrm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
// 來自listAllGrm.jsp的請求, 取得單一美容師修改頁面
		if ("getOne_For_Update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String groomerNo = req.getParameter("groomerNo");
				
				/***************************2.開始查詢資料****************************************/
				GrmService grmSvc = new GrmService();
				GrmVO grmVO = grmSvc.getOneGrm(groomerNo);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("grmVO", grmVO);         // 資料庫取出的grmVO物件,存入req
				String url = "/back-end/grm/update_grm_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_grm_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
// 來自update_grm_input.jsp的請求, 修改一位美容師的資料
		if ("update".equals(action)) { // 來自listAllGrm.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//System.out.println("進入update判斷");	
				String groomerNo = (req.getParameter("grmno").trim());
//System.out.println(groomerNo);
				String gname = req.getParameter("gname");
				String gnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (gname == null || gname.trim().length() == 0) {
					errorMsgs.add("!!美容師暱稱!!請勿空白");
				} else if(!gname.trim().matches(gnameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("美容師暱稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
//System.out.println(gname);				
				String ginfo = req.getParameter("ginfo").trim();
//				if (ginfo == null || ginfo.trim().length() == 0) {
//					errorMsgs.add("!!美容師簡介!!請勿空白");
//				}	
//System.out.println(ginfo);				
				Integer isdel = new Integer(req.getParameter("isDelete"));
//System.out.println(isdel);				
				//判斷圖片是否被更改, 如果沒有被改則使用DB的原圖, 如果有更改使用新的圖片
				//使用getPart方法獲取單張圖片資料
				GrmVO grmvo = new GrmVO();
				GrmService grmSvc = new GrmService();
//System.out.println(req.getPart("grmPic"));
				byte[] gPic = null;				
				//如果type multipart/form-data為空(part == null)則代表沒有新的上傳圖片
				if(req.getPart("grmPic").getContentType() == null) {
					//使用DB裡的原圖
					gPic = grmSvc.getOneGrm(groomerNo).getGroomerPic();
				}else {
				//如果getPart有抓到資料
					InputStream in = req.getPart("grmPic").getInputStream();
					gPic = new byte[in.available()]; //使用in.available()獲得"part.getInputStream()"的可讀取bytes
					in.read(gPic);
					in.close();
				}
				
				grmvo.setGroomerNo(groomerNo);
				grmvo.setGroomerPic(gPic);
				grmvo.setGroomerName(gname);
				grmvo.setGroomerInfo(ginfo);
				grmvo.setIsDelete(isdel);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("grmvo", grmvo); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/grm/update_grm_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				grmvo = grmSvc.updateGrmVO(groomerNo, gname, ginfo, gPic, isdel);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("grmvo", grmvo); // 資料庫update成功後,正確的的grmVO物件,存入req
				String url = "/back-end/grm/listAllGrm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllGrm.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String groomerNo = (req.getParameter("groomerNo")).toString().trim();
				
				/***************************2.開始刪除資料***************************************/
				GrmService grmSvc = new GrmService();
				grmSvc.deleteGrmVO(groomerNo);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/grm/listAllGrm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
//處理front-end選擇美容師
		if("selectGrm".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***************************1.接收請求參數***************************************/
			//取得已選擇的美容師編號
			String groomerNo = req.getParameter("groomerNo").trim();
			//設定到session
			session.setAttribute("groomerNo", groomerNo);	
			/***************************2.設定完成,準備轉交到選擇日期***********/	
			String url = "/front-end/grmOff/selectDay.jsp";
			RequestDispatcher selectDay = req.getRequestDispatcher(url);
			selectDay.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("轉交資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grm/choose_grm.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
