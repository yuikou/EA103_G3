package com.salonOrderDetail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.salonOrderDetail.model.SalonOrderDetailService;
import com.salonOrderDetail.model.SalonOrderDetailVO;



public class SalonOrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		
		if("getSalOrderDetail".equals(action)) {			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
									
			try {
				String salOrderNo = new String(req.getParameter("salOrderNo"));
				SalonOrderDetailService salOrderDetail = new SalonOrderDetailService();
				List<SalonOrderDetailVO> salOrderDetailList =  salOrderDetail.getAllBySalOrderNo(salOrderNo);
								
				req.setAttribute("salOrderDetailList", salOrderDetailList);
				req.setAttribute("salOrderNo",salOrderNo);
				
				String url = "/front-end/salonOrderDetail/salonOrderDetailList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			}catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("getMemOrderDetail".equals(action)) {			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
									
			try {
				String salOrderNo = new String(req.getParameter("salOrderNo"));
				SalonOrderDetailService salOrderDetail = new SalonOrderDetailService();
				List<SalonOrderDetailVO> salOrderDetailList =  salOrderDetail.getAllBySalOrderNo(salOrderNo);
								
				req.setAttribute("salOrderDetailList", salOrderDetailList);
				req.setAttribute("salOrderNo",salOrderNo);
				
				String url = "/front-end/salonOrderDetail/memOrderDetailList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			}catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
				failureView.forward(req, res);
			}
		}	
	}
}
