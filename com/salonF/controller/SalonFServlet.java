package com.salonF.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.salonF.model.*;

public class SalonFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SalonFServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String salno = (session.getAttribute("salno")).toString();
		String memNo = (session.getAttribute("memNo")).toString();
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();

		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				SalonFVO saFVo = new SalonFVO();
				saFVo.setMemNo(memNo);
				saFVo.setSalNo(salno);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("saFVo", saFVo);
					RequestDispatcher failure = req.getRequestDispatcher("/front-end/salonFollow/FollowTest.jsp");//需要更換為業者頁面路徑
					failure.forward(req, res);
					return;
				}
				
				SalFService saFSvc = new SalFService();
				saFSvc.addSalF(memNo, salno);
				//新增完成, 轉交ajax
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			} 
			if (errorMsgs.isEmpty()) {
				out.write('1');
				out.flush();
				out.close();
			}
		}
		
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				SalFService saFSvc = new SalFService();
				saFSvc.deleteSalF(memNo, salno);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
			if (errorMsgs.isEmpty()) {
				out.write('0');
				out.flush();
				out.close();
			}
		}
		
	}

}
