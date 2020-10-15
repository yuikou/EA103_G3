package com.adoPet.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.adoPet.model.AdoPetService;
import com.adoPet.model.AdoPetVO;
import com.adoPetAlbum.model.AdoPetAlbumVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class AdoPetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) { // 來自AdoptPet Upload.JSP

			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String petName = req.getParameter("petName");
				String petNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{1,6}$";
				if (petName == null || petName.trim().length() == 0) {
					erroMsgas.add("寵物名稱請勿空白");
				} else if (!petName.trim().matches(petNameReg)) {
					erroMsgas.add("寵物名稱只能是中、英文、數字，且長度必須在1-6之間");
				}

				String petBreed = req.getParameter("petBreed").trim();
				if (petBreed == null || petBreed.trim().length() == 0) {
					erroMsgas.add("請填寫寵物品種");
				}

				Integer petType = new Integer(req.getParameter("petType").trim());
				Integer petSex = new Integer(req.getParameter("petSex").trim());

				java.sql.Date petBirth = null;
				try {
					petBirth = java.sql.Date.valueOf(req.getParameter("petBirth").trim());
				} catch (IllegalArgumentException ie) {
					petBirth = new java.sql.Date(System.currentTimeMillis());
					erroMsgas.add("請輸入日期!");
				}

				Double petWeight = null;

				try {
					petWeight = new Double(req.getParameter("petWeight").trim());
				} catch (NumberFormatException ne) {
					petWeight = 0.0;
					erroMsgas.add("體重請填數字.");

				}

				/* 下拉式沒有寫驗證 */
				Integer petCat = new Integer(req.getParameter("petCat").trim());

				String location = req.getParameter("location").trim();

				String petChar = req.getParameter("petChar").trim();

				if (petChar == null || petChar.trim().length() == 0) {
					petChar = "";

				}

				AdoPetVO adoPetVO = new AdoPetVO();
				adoPetVO.setPetType(petType);
				adoPetVO.setPetName(petName);
				adoPetVO.setPetBreed(petBreed);
				adoPetVO.setPetSex(petSex);
				adoPetVO.setPetBirth(petBirth);
				adoPetVO.setPetWeight(petWeight);
				adoPetVO.setPetCat(petCat);
				adoPetVO.setPetChar(petChar);
				adoPetVO.setLocation(location);

				Collection<Part> parts = req.getParts();
				List<AdoPetAlbumVO> picArr = new ArrayList<AdoPetAlbumVO>();
				if (parts.size() != 0) {
					try {

						for (Part part : parts) {
							if (part.getContentType() != null) {
								InputStream in = part.getInputStream();
								byte[] adoPetPic = new byte[in.available()];
								in.read(adoPetPic);
								in.close();
								AdoPetAlbumVO adoPetAlbumVO = new AdoPetAlbumVO();
								adoPetAlbumVO.setAdoPetPic(adoPetPic);
								picArr.add(adoPetAlbumVO);
							}
						}
					} catch (IOException e) {

						erroMsgas.add("請重新上傳");
					}
				} else {
					erroMsgas.add("請上傳圖片");
				}

				if (!erroMsgas.isEmpty()) {
					req.setAttribute("adoPetVO", adoPetVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/AdoPet_Upload.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				AdoPetService AdoPetSvc = new AdoPetService();
				adoPetVO = AdoPetSvc.addAdoPet(picArr, petType, petName, petBreed, petSex, petBirth, petWeight, petCat,
						petChar, location);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				String url = "/adopt/back_end/listAllAdopt.jsp?adoStatus=0";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/AdoPet_Upload.jsp");
				failureView.forward(req, res);
			}

		}

		
	}
}
