package com.adoAlbum.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.adoPetAlbum.model.AdoPetAlbumService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class adoAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("uploadPic".equals(action)) {

			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgas);

			try {
				/* 收到update_adopt.jsp新增圖片，直接呼叫service上傳 */
				Collection<Part> parts = req.getParts();
//				List<AdoPetAlbumVO> adoptPiclist = new ArrayList<AdoPetAlbumVO>();

				AdoPetAlbumService adoAlbum = new AdoPetAlbumService();

				for (Part part : parts) {
					if (part.getContentType().contains("image")) {
						InputStream in = part.getInputStream();
						byte[] adoPetPic = new byte[in.available()];

						in.read(adoPetPic);
						in.close();

						adoAlbum.AdoPetAlbumInsert(req.getParameter("adoPetNo"), adoPetPic);
//					adoptPiclist.add(adoPetAlbumVO);}
					
					}
				}

				RequestDispatcher successView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				successView.forward(req, res);
				

			
			} catch (IOException ie) {
				erroMsgas.add("請重新上傳");
				ie.printStackTrace();
			}catch(NullPointerException ne) {
				erroMsgas.add("未讀取到圖片");
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				failureView.forward(req, res);
			}

			if (!erroMsgas.isEmpty()) {

				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				failureView.forward(req, res);
				return;
			}

		}

		/* 收到update_adopt.jsp圖片刪除*/  
		if("deletePic".equals(action)) {
			
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("errorMsgs", erroMsgas);
			
			try {
//				String[] adoPetNo = req.getParameterValues("adoPetNo");
				String[] adoPicNo = req.getParameterValues("adoPicNo");
				
				AdoPetAlbumService albumSvc = new AdoPetAlbumService();
				for(int i =0 ; i<adoPicNo.length; i++) {
					albumSvc.delete(adoPicNo[i]);
				}
				
				RequestDispatcher successView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				successView.forward(req, res);
				
			}catch(Exception e){
				erroMsgas.add("圖片尚未刪除，請確認頁面顯示圖片最新狀態");
				e.printStackTrace();
			}
			
			
			if(!erroMsgas.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				failureView.forward(req, res);
				return;
			}
		}
	}

}
