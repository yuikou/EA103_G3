package com.sitLic.controller;

import java.io.*;
import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.sitLic.model.*;

@WebServlet("/sitLic/sitLic.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SitLicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

/* 來自會員頁面的請求  - 查詢證照 */		
		if ("getAll".equals(action)) {
			/***************************開始查詢資料 ****************************************/
			String sitNo = (String) session.getAttribute("sessionSitNo");
			SitLicService slSvc = new SitLicService();
			List<SitLicVO> list = slSvc.getSitAllLic(sitNo);
			
			/***************************查詢完成,準備轉交(Send the Success view)*************/
			session.setAttribute("list", list);
			String url = "/front-end/sitLic/listOneSitAllLicBySession.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;//程式中斷
		}
		
/* 來自 addSitLic.jsp 的請求  - 新增證照 */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitNo */
				String sitNo = (String) session.getAttribute("sitNo");
				
				/* 2-licName */
				String licName = req.getParameter("licName").trim();
				if (licName == null || licName.trim().length() == 0) {
					errorMsgs.add("請輸入證書名稱");
				}
				
				/* 3-licEXP */
				Date licEXP = null;
				try {
					String licEXPStr = req.getParameter("licEXP");
					licEXP = Date.valueOf(licEXPStr.trim());
					
				} catch (IllegalArgumentException e) {
					licEXP = null;
				}
				
				/* 4-licStatus */
				Integer licStatus = 0;// 新增時預設狀態0-待審核
				
				/* 5-licPic */
				Collection<Part> parts = req.getParts();
				
				byte[] licPic = null;
				for (Part p : parts) {
					if (p.getContentType() != null) {
						InputStream is = p.getInputStream();
						licPic = new byte[is.available()];
						is.read(licPic);
						is.close();
					}
				}
				if ( licPic == null || licPic.length == 0) {
					errorMsgs.add("請上傳證照圖檔");
				}
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/addSitLic.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				SitLicService slSvc = new SitLicService();
				slSvc.add(sitNo, licName, licPic, licEXP, licStatus);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/sitLic/listOneSitAllLicBySession.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("新增失敗： "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/addSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* 來自listOneSitAllLicBySession.jsp的請求 - 取得要修改的證照資料 */
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* licNo */
				String licNo = req.getParameter("licNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitLicService slSvc = new SitLicService();
				SitLicVO sitLicVO = slSvc.getOneLicByPK(licNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("sitLicVO", sitLicVO);
				
				String url = "/front-end/sitLic/updateSitLic.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/updateSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* 來自updateSitLic.jsp的請求 - 修改證照 */
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-licNo */
				String licNo = req.getParameter("licNo");
				
				/* 2-licName */
				String licName = req.getParameter("licName").trim();
				if (licName == null || licName.length() == 0) {
					errorMsgs.add("請輸入證書名稱");
				}
				// 如果沒輸入就先回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/updateSitLic.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/* 3-licEXP */
				Date licEXP = null;
				try {
					String licEXPStr = req.getParameter("licEXP");
					licEXP = Date.valueOf(licEXPStr.trim());
					
				} catch (IllegalArgumentException e) {
					licEXP = null;
				}
				
				/* 4-licStatus */
				Integer licStatus = Integer.parseInt(req.getParameter("licStatus"));
				
				/* 5-licPic */
				Collection<Part> parts = req.getParts();
				
				byte[] licPic = null;
				for (Part p : parts) {
					if (p.getContentType() != null) {
						InputStream is = p.getInputStream();
						licPic = new byte[is.available()];
						is.read(licPic);
						is.close();
					}
				}
				
				/* 6-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
//				System.out.println("SitLicServlet_192.sitSrvNo"+sitSrvNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				SitLicService slSvc = new SitLicService();
				// 如果修改時沒有上傳圖片，就取資料庫的圖片重新update
				if (licPic.length < 1) {
					licPic = slSvc.getOneLicByPK(licNo).getLicPic();
				}
				slSvc.update(licNo, licName, licPic, licEXP, licStatus, sitSrvNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/sitLic/listOneSitAllLicBySession.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("修改失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/updateSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* 來自 listUnverifiedLic.jsp 的請求 - 修改證照狀態 */
		if ("verify".equals(action)) {
			PrintWriter out = res.getWriter();
//			MailService mailService = new MailService();
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-licNo */
				String licNo = req.getParameter("licNo");
				
				/* 2-licStatus */
				Integer licStatus = Integer.parseInt(req.getParameter("licStatus"));
				
				/* 3-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				
				/* 4-memEmail */
				String memEmail = req.getParameter("memEmail");
				
				/* 5-memName */
				String memName = req.getParameter("memName");
				
				/* 6-failReason */
				String failReason = req.getParameter("failReason");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitLicService slSvc = new SitLicService();
				Boolean updateOK  = slSvc.updateStatus(licNo, licStatus, sitSrvNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String subject_success = "PetFect-證照審核成功";
				String subject_fail = "PetFect-證照審核失敗";
				String messageText_success = "Hello! " + memName + ", 您的證照審核成功，已將證照名稱顯示於您的個人頁面。";
				String messageText_fail = "Hello! " + memName + ", 您的證照審核失敗~ \r\n 原因：  " + failReason 
										+ "\r\n 您可至「管理證照」專區，修改完成後再次送出審核，謝謝。";
				
				if (updateOK) {
					out.write(licNo);
					
					if (licStatus==1) {
						new Thread(new MailService(memEmail, subject_success, messageText_success)).start();
//						mailService.sendMail(memEmail, subject_success, messageText_success);
					} else if (licStatus==2) {
						new Thread(new MailService(memEmail, subject_fail, messageText_fail)).start();
//						mailService.sendMail(memEmail, subject_fail, messageText_fail);
					}
				} else {
					out.write("error");
				}
				
			} catch (Exception e) {
				out.write("error: " + e.getMessage());
			}
		}
	}
}
