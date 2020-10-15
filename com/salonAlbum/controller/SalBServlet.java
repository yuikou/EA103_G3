package com.salonAlbum.controller;

import java.io.IOException;
import java.io.InputStream;
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

import com.salonAlbum.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SalBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SalBServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String salno = (session.getAttribute("salno")).toString();
		String action = req.getParameter("action");
		
//來自addAlbPic.jsp的請求, 新增相片		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try{
			//1. 接收請求參數
				Collection<Part> parts = req.getParts();
				List<SalonAlbVO> picArr = new ArrayList<SalonAlbVO>(); 
				SalonAlbVO vo = new SalonAlbVO();
					for(Part part : parts) {
					//parts裡的資料為圖片檔才跑以下處理
						if(part.getContentType() != null) {
							
						InputStream in = part.getInputStream();
						byte[] salpic = new byte[in.available()];
						in.read(salpic);
						in.close();
						
						vo.setSalAlbPic(salpic);
						picArr.add(vo);
					}
//						String salPortInfo = req.getParameter("salPortInfo").toString().trim();
//						vo.setSalPortInfo(salPortInfo);
					//判斷陣列是否有正確存入圖片
					if(picArr.size() < 1){
						errorMsgs.add("請上傳圖片(servlet)");
					}
				}
				
				// 錯誤處理
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salonAlbVO", picArr);
					RequestDispatcher failure = req.getRequestDispatcher("/back-end/salalb/addAlbPic.jsp");
					failure.forward(req, res);
					return;
				}
			//2.新增資料到DB
				SalonAlbService sbSvc = new SalonAlbService();
				for(SalonAlbVO row : picArr) {
					byte[] pic = row.getSalAlbPic();
//					String info = row.getSalPortInfo();
					sbSvc.addSalAlb(salno, pic);
				}
			//3.新增完成
				String url = "/back-end/salalb/listAllbyAlb.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/salalb/listAllbyAlb.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
		
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				String salAlbNo = (req.getParameter("salAlbNo")).toString().trim();
				
				SalonAlbService sbSvc = new SalonAlbService();
				sbSvc.deleteSalAlb(salAlbNo);
				
				String url = "/back-end/salalb/listAllbyAlb.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	

}
