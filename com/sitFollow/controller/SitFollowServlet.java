package com.sitFollow.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.sitFollow.model.*;

@WebServlet("/sitFollow/sitFollow.do")
public class SitFollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
/* 來自會員查看所有追蹤的保姆 */
		if ("showAll".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* memNo */
				String memNo = (String) session.getAttribute("memNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				List<String> list = sfSvc.getAllByMemNo(memNo);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				session.setAttribute("list", list);

				String url = "/front-end/sitFollow/listSitFollowBySession.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitFollow/listSitFollow.jsp");
				failureView.forward(req, res);
			}
		}
		
/* 來自會員【新增】追蹤保姆的請求 - by ajax */
		if ("add".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean addOK = sfSvc.add(memNo, sitNo);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (addOK) {
					out.write('1');
				} else {
					out.write('0');
				}
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error");
			}
		}
		
		
/* 來自會員查看保姆頁面時【刪除】追蹤保姆的請求 - by ajax */
		if ("del".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-memNo */
				String memNo = req.getParameter("memNo");
				
				/* 2-sitNo */
				String sitNo = req.getParameter("sitNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitFollowService sfSvc = new SitFollowService();
				Boolean delOK = sfSvc.del(memNo, sitNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (delOK) {
					out.write('0');
				} else {
					out.write('1');
				}
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				out.write("error: " + e.getMessage());
			}
		}
	}
}
