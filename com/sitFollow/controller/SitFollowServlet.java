package com.sitFollow.controller;

import java.io.*;
import java.sql.Date;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.sitFollow.model.*;

public class SitFollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// �B�z����ШD
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
/* �Ӧ� listSitFollow.jsp ���ШD  - �|���d�ݩҦ��l�ܪ��O�i */
//		if ("showAll".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/*************************** 1.�����ШD�Ѽ� ****************************************/
//				/* memNo */
//				String memNo = (String) session.getAttribute("memNo");
//				
//				/*************************** 2.�}�l�s�W��� ***************************************/
//				SitFollowService sfSvc = new SitFollowService();
//				List<String> list = sfSvc.getAllByMemNo(memNo);
//	
//				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
//				req.setAttribute("list", list);
//				// ��e
//				String url = "listSitFollow.jsp";
//				RequestDispatcher sucessView = req.getRequestDispatcher(url);
//				sucessView.forward(req, res);
//				
//			/***************************��L�i�઺���~�B�z*************************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
/* �Ӧ۷|���i�s�W�j�l�ܫO�i���ШD - �����I�scontroller */
		if ("add".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isAdd = sfSvc.add(memNo, sitNo);
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (isAdd) {
					out.write('1');
				} else {
					out.write('0');
				}
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher(location);
//				failureView.forward(req, res);
				out.write("error");
			}
		}
		
		
/* �Ӧ۷|���d�ݫO�i�����ɡi�R���j�l�ܫO�i���ШD - �����I�scontroller */
		if ("del".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isDel = sfSvc.del(memNo, sitNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (isDel) {
					out.write('0');
				} else {
					out.write('1');
				}
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher(location);
//				failureView.forward(req, res);
				out.write("error");
			}
		}
		
		
/* �Ӧ۷|���d�ݩҦ��l�ܲM��ɡi�R���j�l�ܫO�i���ШD - �����I�scontroller */
		if ("delfromMemList".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isDel = sfSvc.del(memNo, sitNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// ����
				if (isDel) {
					res.sendRedirect("listSitFollow.jsp");
				} else {
					errorMsgs.add("�R������");
//					throw new RuntimeException("�R������");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
					failureView.forward(req, res);
				}
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
