package com.sitPhoto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
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

import com.sitPhoto.model.*;

@WebServlet("/SitPhotoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SitPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SitPhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		if ("display_sitPhoto".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String sitNo = req.getParameter("sitNo");
				req.setAttribute("sitNo", sitNo);
				
				String url = "/front-end/sitPhoto/sitPhotoUpload.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
						
				
			} catch(Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/petSitter/listOneSitter.jsp");
				failureView.forward(req, res);				
			}
			
		}

		if ("upload_sitPhoto".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String sitNo = req.getParameter("sitNo");
				req.setAttribute("sitNo", sitNo);
				
				byte[] sitPhoto = null;
				// ���request���Ҧ����ШD�޼�(�N�ШD�޼��ഫ��Part)
				Collection<Part> parts = req.getParts();
				
				
				for (Part part : parts) {
					if (part.getContentType() != null) {

						InputStream in = part.getInputStream();
						sitPhoto = new byte[in.available()];
						in.read(sitPhoto);
						in.close();	
						SitPhotoService sitPSrv = new SitPhotoService();
						SitPhotoVO sitPVO = new SitPhotoVO();
						sitPVO.setSitNo(sitNo);
						sitPVO.setSitPhoto(sitPhoto);
						sitPVO = sitPSrv.add(sitNo, sitPhoto);
			
					}
				}
				
				if (sitPhoto == null || sitPhoto.length == 0) {
					errorMsgs.add("�ФW�Ǧܤ֤@�i�Ӥ�");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
					failureView.forward(req, res);
					return;
				}
								
				String url = "/front-end/sitPhoto/sitPhotoUpload.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�W�ǷӤ�����: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete_sitPhoto".equals(action)) {
			 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String[] sitPNoArray = req.getParameterValues("ckbox");
				String sitNo = req.getParameter("sitNo");
				
				if (sitPNoArray == null) {
					errorMsgs.add("�п�ܤ֤@�i�Ӥ�");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
					failureView.forward(req, res);
					return;
				}
				
				for(String sitPNo : sitPNoArray) {
					SitPhotoService sitPhotoSrv = new SitPhotoService();
				    sitPhotoSrv.delete(sitPNo);
				}
				
				req.setAttribute("sitNo", sitNo);
				String url = "/front-end/sitPhoto/sitPhotoUpload.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
				failureView.forward(req, res);
			}		
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
