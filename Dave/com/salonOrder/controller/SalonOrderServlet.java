package com.salonOrder.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.salonOrder.model.SalonOrderService;
import com.salonOrder.model.SalonOrderVO;


public class SalonOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
          	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insert".equals(action)) {
			try {
				String memNo = new String(req.getParameter("memNo"));
				String petNo = new String(req.getParameter("petNo"));
				String salNo = new String(req.getParameter("salNo"));				
				
				java.sql.Timestamp salOrderDate = null;
				try {
					salOrderDate =java.sql.Timestamp.valueOf(req.getParameter("salOrderDate").trim());
				}catch(IllegalArgumentException e) {
					salOrderDate = new java.sql.Timestamp(System.currentTimeMillis());
				}
				
				Integer salTp = new Integer(req.getParameter("salTp"));
				Integer orderStatus = new Integer(req.getParameter("orderStatus"));
				
				SalonOrderVO salonOrderVO = new SalonOrderVO();
				salonOrderVO.setMemNo(memNo);
				salonOrderVO.setPetNo(petNo);
				salonOrderVO.setSalNo(salNo);
				salonOrderVO.setSalOrderDate(salOrderDate);
				salonOrderVO.setSalTp(salTp);
				salonOrderVO.setOrderStatus(orderStatus);
				
				SalonOrderService salonOrderSvc =new SalonOrderService();
				salonOrderVO = salonOrderSvc.addSalonOrder(memNo, petNo, salNo, salOrderDate, salTp, orderStatus);
				
				
				String url ="";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);	
			}catch(Exception e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("");
				failureView.forward(req, res);
			}
			
		}
		
		
		
	}

}
