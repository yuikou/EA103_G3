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
		// 處理中文請求
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
/* 來自 listSitFollow.jsp 的請求  - 會員查看所有追蹤的保姆 */
//		if ("showAll".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				/*************************** 1.接收請求參數 ****************************************/
//				/* memNo */
//				String memNo = (String) session.getAttribute("memNo");
//				
//				/*************************** 2.開始新增資料 ***************************************/
//				SitFollowService sfSvc = new SitFollowService();
//				List<String> list = sfSvc.getAllByMemNo(memNo);
//	
//				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//				req.setAttribute("list", list);
//				// 轉送
//				String url = "listSitFollow.jsp";
//				RequestDispatcher sucessView = req.getRequestDispatcher(url);
//				sucessView.forward(req, res);
//				
//			/***************************其他可能的錯誤處理*************************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
//				failureView.forward(req, res);
//			}
//		}
		
/* 來自會員【新增】追蹤保姆的請求 - 直接呼叫controller */
		if ("add".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isAdd = sfSvc.add(memNo, sitNo);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (isAdd) {
					out.write('1');
				} else {
					out.write('0');
				}
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher(location);
//				failureView.forward(req, res);
				out.write("error");
			}
		}
		
		
/* 來自會員查看保姆頁面時【刪除】追蹤保姆的請求 - 直接呼叫controller */
		if ("del".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isDel = sfSvc.del(memNo, sitNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (isDel) {
					out.write('0');
				} else {
					out.write('1');
				}
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher(location);
//				failureView.forward(req, res);
				out.write("error");
			}
		}
		
		
/* 來自會員查看所有追蹤清單時【刪除】追蹤保姆的請求 - 直接呼叫controller */
		if ("delfromMemList".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean isDel = sfSvc.del(memNo, sitNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 重導
				if (isDel) {
					res.sendRedirect("listSitFollow.jsp");
				} else {
					errorMsgs.add("刪除失敗");
//					throw new RuntimeException("刪除失敗");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
					failureView.forward(req, res);
				}
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
