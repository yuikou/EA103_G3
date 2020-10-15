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

		if ("update".equals(action)) { // 來自update_adopt.jsp 修改單一寵物資料

			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String adoPetNo = req.getParameter("adoPetNo");
				System.out.println(adoPetNo);

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
				Integer adoStatus = new Integer(req.getParameter("adoStatus").trim());
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
				adoPetVO.setAdoStatus(adoStatus);
				adoPetVO.setPetType(petType);
				adoPetVO.setPetName(petName);
				adoPetVO.setPetBreed(petBreed);
				adoPetVO.setPetSex(petSex);
				adoPetVO.setPetBirth(petBirth);
				adoPetVO.setPetWeight(petWeight);
				adoPetVO.setPetCat(petCat);
				adoPetVO.setPetChar(petChar);
				adoPetVO.setLocation(location);

				if (!erroMsgas.isEmpty()) {
					req.setAttribute("adoPetVO", adoPetVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher(req.getContextPath() + "/adopt/back_end/update_adopt.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				adoPetVO = adoPetSvc.updateAdoPet(adoStatus, petType, petName, petBreed, petSex, petBirth, petWeight,
						petCat, petChar, location, adoPetNo);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				String url = "/adopt/back_end/listAllAdopt.jsp?adoStatus=0";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/update_adopt.jsp");
				failureView.forward(req, res);
			}

		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllAdopt.jsp請求，進入修改頁面
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				/*************************** 1.接收請求參數 ****************************************/
				String adoPetNo = req.getParameter("adoPetNo");

				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adoPetVO", adoPetVO);
				String url = "/adopt/back_end/update_adopt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/listAllAdopt.jsp?adoStatus=0");
				failureView.forward(req, res);
			}
		}

		if ("update_status".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);

			try {
				/*************************** 1.接收請求參數 ****************************************/

				String adoPetNo = req.getParameter("adoPetNo");
				Integer adoStatus = new Integer(req.getParameter("adoStatus"));
				String empNo = req.getParameter("empNo");
				System.out.println("empNo?????" + empNo);
				/*************************** 2.開始移除資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				adoPetSvc.update_status(adoStatus, empNo, adoPetNo);

				/*************************** 3.移除完成,準備轉交(Send the Success view) ************/
				String url = "/adopt/back_end/listAllAdopt.jsp?adoStatus=0";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/listAllAdopt.jsp?adoStatus=0");
				failureView.forward(req, res);
			}
		}

		if ("read_appForm".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String adoPetNo = req.getParameter("adoPetNo");
				System.out.println(adoPetNo);
				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoptSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoptSvc.findByPrimaryKey(adoPetNo);
				ServletOutputStream out = res.getOutputStream();

				byte[] buf = new byte[4 * 1024];

				ByteArrayInputStream bin = new ByteArrayInputStream(adoPetVO.getAppForm());

				int len;
				while ((len = bin.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				bin.close();
				out.close();

				/*************************** 3.無法轉交，會出現 IllegalStateException ************/
//				req.setAttribute("PDF", adoPetVO);
//				String url = "/adopt/back_end/readApplication.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/back_end/listAllAdopt.jsp?adoStatus=0");
				failureView.forward(req, res);
			}
		}



		/*********************** Front-End **************************/
		if ("showPetDetail".equals(action)) { // 來自searchAdopet.jsp請求，進入單一寵物頁面
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				/*************************** 1.接收請求參數 ****************************************/
				String adoPetNo = req.getParameter("adoPetNo");

				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adoPetVO", adoPetVO);
				String url = "/adopt/front_end/showOneAdopet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/front_end/searchAdopet.jsp");
				failureView.forward(req, res);
			}

		}
		if ("searchPet".equals(action)) {// 來自searchAdopet.jsp請求，搜尋寵物

			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				/*************************** 1.接收請求參數 ****************************************/
				
				StringBuffer sentence = new StringBuffer();
				if(req.getParameter("petType")!=null) {
					sentence.append(req.getParameter("petType"));
					
				}
				
				if(req.getParameter("petSex")!=null) {
					sentence.append(req.getParameter("petSex"));
					
				}
				
				if(req.getParameter("age")!=null) {
					sentence.append(req.getParameter("age"));
					
				}
				
				if(req.getParameter("petCat")!=null) {
					sentence.append(req.getParameter("petCat"));
					
				}
				if(req.getParameter("location")!=null) {
					sentence.append(req.getParameter("location"));
					
				}
				
				System.out.println(sentence.toString());

				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				List <AdoPetVO> adoList = adoPetSvc.SearchAdpPet(sentence.toString());
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adoList", adoList);
				HttpSession session= req.getSession();
				session.setAttribute("preferedList",adoList);
				String url = "/adopt/front_end/searchAdopet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/adopt/front_end/searchAdopet.jsp");
				failureView.forward(req, res);
			}

		}
	}
}
