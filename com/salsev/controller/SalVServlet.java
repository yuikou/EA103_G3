package com.salsev.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.salsev.model.SalVService;
import com.salsev.model.SalsevVO;

public class SalVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SalVServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String salno = session.getAttribute("salno").toString();
		String action = req.getParameter("action");
		
//System.out.println(action);
//來自addSalSev.jsp的請求
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String salsevname = req.getParameter("salsevname");
				
				String salsevinfo = req.getParameter("salsevinfo");
				if(salsevinfo == null || salsevinfo.trim().length() == 0) {
					errorMsgs.add("服務項目名稱請勿空白");
				}
				
				Integer petcat = new Integer (req.getParameter("petcat").trim());
				
				Integer salsevtime = new Integer(req.getParameter("salsevtime").trim()); 
				
				Integer salsevpr = new Integer(req.getParameter("salsevpr").trim()); 
				
				Integer status = new Integer(req.getParameter("status"));
				
				SalsevVO ssvo = new SalsevVO();
				ssvo.setSalsevname(salsevname);
				ssvo.setSalSevInfo(salsevinfo);
				ssvo.setPetcat(petcat);
				ssvo.setSalsevtime(salsevtime);
				ssvo.setSalsevpr(salsevpr);
				ssvo.setStatus(status);
				
				//錯誤處理
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salvVO", ssvo);
					RequestDispatcher failure = req.getRequestDispatcher("/back-end/salsev/addSalSev.jsp");
					failure.forward(req, res);
					return;
				}
				/***********************2.新增資料*************************/
				SalVService ssvSvc = new SalVService();
				ssvSvc.addSalV(salno, petcat, salsevname, salsevinfo, salsevtime, salsevpr, status);
				
				/***********************3.新增完成, 轉交畫面*************************/
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
		
//來自listAllSalSev.jsp的請求, 取得單一頁面以便修改服務項目
		if("getOne_For_Update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數
				String salsevno = req.getParameter("salsevno");
				
				//2.開始查詢資料
				SalVService ssv = new SalVService();
				SalsevVO salvVO = ssv.getOneSalv(salsevno);
				
				//3.查詢完成, 準備轉交
				req.setAttribute("salvVO", salvVO);
				String url = "/back-end/salsev/update_salv_input.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}

//來自update_salsev_input.jsp的請求, 更新服務項目		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數
				String salsevno = (req.getParameter("salsevno")).trim();
				
				String salsevname = (req.getParameter("salsevname")).trim();
				
				String salsevinfo = (req.getParameter("salsevinfo")).trim();
				
				Integer petcat = new Integer(req.getParameter("petcat"));
				
				Integer salsevtime = new Integer(req.getParameter("salsevtime"));
				
				Integer salsevpr = new Integer(req.getParameter("salsevpr"));
				
				Integer status = new Integer(req.getParameter("status"));
				
				SalsevVO salvVO = new SalsevVO();
				
				salvVO.setSalsevno(salsevno);
				salvVO.setSalsevname(salsevname);
				salvVO.setSalSevInfo(salsevinfo);
				salvVO.setPetcat(petcat);
				salvVO.setSalsevtime(salsevtime);
				salvVO.setSalsevpr(salsevpr);
				salvVO.setStatus(status);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salvVO", salvVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/salsev/listAllsalSev.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				//2.開始修改資料
				SalVService svsvc = new SalVService();
				salvVO = svsvc.updateSalV(salsevno, petcat, salsevname, salsevinfo, salsevtime, salsevpr, status);
				
				//3.修改完成, 準備轉交
				req.setAttribute("salvVO", salvVO);
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e){
				errorMsgs.add("修改資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數
				String salsevno = (req.getParameter("salsevno")).toString().trim();
				//2.開始刪除資料				
				SalVService ssvc = new SalVService();
				ssvc.deleteSalv(salsevno);
				
				//3.刪除成功,轉交畫面
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
//處理front-end選擇服務項目
		if("selectSalV".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.接收請求參數
				String salsevno = (req.getParameter("salsevNo")).toString().trim();
				//儲存設定到session
				session.setAttribute("salsevno", salsevno);
//System.out.println(salsevno);
				//設定完成, 轉交頁面到選擇美容師
				String url = "/front-end/grm/choose_groomer.jsp";
				RequestDispatcher selectGrm = req.getRequestDispatcher(url);
				selectGrm.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("轉交資料失敗:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/salsev/select_SalSev.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
