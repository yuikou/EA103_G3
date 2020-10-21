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

/* �Ӧ۷|���������ШD  - �d���ҷ� */		
		if ("getAll".equals(action)) {
			/***************************�}�l�d�߸�� ****************************************/
			String sitNo = (String) session.getAttribute("sitNo");
			SitLicDAO dao = new SitLicDAO();
			List<SitLicVO> list = dao.getSitAllLic(sitNo);
			
			/***************************�d�ߧ���,�ǳ����(Send the Success view)*************/
			session.setAttribute("list", list);
			String url = "/front-end/sitLic/listOneSitAllLicBySession.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;//�{�����_
		}
		
/* �Ӧ� addSitLic.jsp ���ШD  - �s�W�ҷ� */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitNo */
				String sitNo = (String) session.getAttribute("sitNo");
				
				/* 2-licName */
				String licName = req.getParameter("licName").trim();
				if (licName == null || licName.trim().length() == 0) {
					errorMsgs.add("�п�J�ҮѦW��");
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
				Integer licStatus = 0;// �s�W�ɹw�]���A0-�ݼf��
				
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
					errorMsgs.add("�ФW���ҷӹ���");
				}
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/addSitLic.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitLicService slSvc = new SitLicService();
				slSvc.add(sitNo, licName, licPic, licEXP, licStatus);
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/front-end/sitLic/listOneSitAllLic.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�s�W���ѡG "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/addSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* �Ӧ� listOneSitAllLic.jsp ���ШD - ���o�n�ק諸�ҷӸ�� */
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* licNo */
				String licNo = req.getParameter("licNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitLicService slSvc = new SitLicService();
				SitLicVO sitLicVO = slSvc.getOneLicByPK(licNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("sitLicVO", sitLicVO);
				
				String url = "/front-end/sitLic/updateSitLic.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/listOneSitAllLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* �Ӧ�updateSitLic.jsp���ШD - �ק��ҷ� */
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-licNo */
				String licNo = req.getParameter("licNo");
				
				/* 2-licName */
				String licName = req.getParameter("licName").trim();
				if (licName == null || licName.trim().length() == 0) {
					errorMsgs.add("�п�J�ҮѦW��");
				}
				// �p�G�S��J�N���^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/listOneSitAllLicBySession.jsp");
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
				System.out.println("SitLicServlet_191.sitSrvNo"+sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitLicService slSvc = new SitLicService();
				// �p�G�ק�ɨS���W�ǹϤ��A�N����Ʈw���Ϥ����supdate
				if (licPic.length < 1) {
					licPic = slSvc.getOneLicByPK(licNo).getLicPic();
				}
				slSvc.update(licNo, licName, licPic, licEXP, licStatus, sitSrvNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/front-end/sitLic/listOneSitAllLic.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�ק異�ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitLic/updateSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/* �Ӧ� listUnverifiedLic.jsp ���ШD - �ק��ҷӪ��A */
		if ("verify".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-licNo */
				String licNo = req.getParameter("licNo");
				
				/* 2-licStatus */
				Integer licStatus = Integer.parseInt(req.getParameter("licStatus"));
				
				/* 3-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("SitLicServlet_230.sitSrvNo"+sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitLicService slSvc = new SitLicService();
				Boolean updateOK  = slSvc.updateStatus(licNo, licStatus, sitSrvNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
//				String url = "listUnverifiedLic.jsp";
//				RequestDispatcher sucessView = req.getRequestDispatcher(url);
//				sucessView.forward(req, res);
				if (updateOK) {
					out.write(licNo);
				} else {
					out.write("error");
				}
				
			} catch (Exception e) {
//				errorMsgs.add("�f�֥��ѡG " + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("listUnverifiedLic.jsp");
//				failureView.forward(req, res);
				out.write("error: " + e.getMessage());
			}
		}
	}
}
