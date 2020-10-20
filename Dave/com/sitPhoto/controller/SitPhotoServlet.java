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
				errorMsgs.add("無法取得資料:" + e.getMessage());
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
				// 獲取request的所有的請求引數(將請求引數轉換為Part)
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
					errorMsgs.add("請上傳至少一張照片");
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
				errorMsgs.add("上傳照片失敗: " + e.getMessage());
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
					errorMsgs.add("請選至少一張照片");
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
				
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
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
