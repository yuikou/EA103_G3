package com.salon.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.salon.model.SalonService;
import com.salon.model.SalonVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class SalonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		 
		if("checkAc".equals(action)) {
			System.out.println(123);
			String salAc = req.getParameter("salAc");
			  SalonService salSvc = new SalonService();
			  System.out.println(salAc);
			  if(salSvc.checkAc(salAc)) {
			   res.getWriter().print(true);
			  } else {
			   res.getWriter().print(false);
			  }
			 
		}
		
		
		
		
		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs); 

			try {
				String salAc = req.getParameter("salAc");
				String salAcReg = "^[(a-zA-Z0-9)]{6,15}$";
				if(salAc == null || salAc.trim().length() ==0) {
					errorMsgs.add("帳號請勿空白");	
				}else if(!salAc.trim().matches(salAcReg)) {
					errorMsgs.add("帳號請輸入英文、數字,且長度必須在6~15個字之間");
				}

				String salPw = req.getParameter("salPw");
				String salPwReg = "^[(a-zA-Z0-9)]{6,15}$";
				if(salPw == null || salPw.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}else if (!salPw.trim().matches(salPwReg)) {
					errorMsgs.add("密碼請輸入英文、數字,且長度必須在6~15個字之間");
				}

				String salName = req.getParameter("salName");
				String salNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$";
				if (salName == null || salName.trim().length() == 0) {
					errorMsgs.add("美容店店名請勿空白");
				} else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("美容店店名只能是中、英文字母、數字, 且長度必需在2到10之間");
				}

				String salOwner = req.getParameter("salOwner");
				String salOwnerReg = "^[(\u4e00-\u9fa5)]{2,10}$";				
				if(salOwner == null || salOwner.trim().length() ==0) {
					errorMsgs.add("負責人姓名請勿空白");
				}else if(!salOwner.trim().matches(salOwnerReg)){
					errorMsgs.add("負責人姓名: 只能是中文字母,且長度必需在2到10之間");
				}

				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)]{6,10}$";

				if (salPh == null || salPh.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				} else if (!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("輸入正確電話");
				}

				String salMail = req.getParameter("salMail");

				if (salMail == null || salMail.trim().length() == 0) {
					errorMsgs.add("電子郵件請勿空白");
				}

				String salCity = req.getParameter("salCity");
				if (salCity == null) {
					errorMsgs.add("請選擇縣市");
				}

				String salDist = req.getParameter("salDist");
				if (salDist == null) {
					errorMsgs.add("請選擇區域");
				}

				String salAdr = req.getParameter("salAdr");
				if (salAdr == null) {
					errorMsgs.add("請輸入地址");
				}

				String salSTime = req.getParameter("salSTime");
				if (salSTime == null) {
					errorMsgs.add("請選擇開店時間");
				}

				String salETime = req.getParameter("salETime");
				if (salETime == null) {
					errorMsgs.add("請選擇閉店時間");
				}

				String salRemit = req.getParameter("salRemit");
				if (salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("匯款帳號請勿空白");
				}

				String bankCode = req.getParameter("bankCode");
				if (bankCode == null) {
					errorMsgs.add("請輸入銀行代碼");
				}

				String salInfo = req.getParameter("salInfo");

				Integer salStatus = 0;

				Integer salPetType = null;
				try {
					salPetType = new Integer(req.getParameter("salPetType"));
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇接受寵物類型");
				}

				byte[] salCertif = null;
				byte[] buf = null;

				Part part = req.getPart("salCertif");
				InputStream in = part.getInputStream();
				buf = new byte[in.available()];
				in.read(buf);
				salCertif = buf;

				SalonVO salonVO = new SalonVO();
				salonVO.setSalName(salName);
				salonVO.setSalOwner(salOwner);
				salonVO.setSalCity(salCity);
				salonVO.setSalDist(salDist);
				salonVO.setSalAdr(salAdr);
				salonVO.setSalAc(salAc);
				salonVO.setSalPw(salPw);
				salonVO.setSalSTime(salSTime);
				salonVO.setSalETime(salETime);
				salonVO.setSalRemit(salRemit);
				salonVO.setBankCode(bankCode);
				salonVO.setSalInfo(salInfo);
				salonVO.setSalStatus(salStatus);
				salonVO.setSalPetType(salPetType);
				salonVO.setSalCertif(salCertif);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("salonVO", salonVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonregi.jsp");
					failureView.forward(req, res);
					return;
				}

				// 開始新增資料
				SalonService salonSvc = new SalonService();
				salonVO = salonSvc.addsalon(salName, salOwner, salPh, salMail, salCity, salDist, salAdr, salAc, salPw,
						salSTime, salETime, salRemit, bankCode, salStatus, salInfo, salPetType, salCertif);
				req.setAttribute("salonVO", salonVO);
				// 新增成功
				String url = "/front-end/salon/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonregi.jsp");
				failureView.forward(req, res);
			}
		}

		if ("updatesalon".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				

				String salNo = new String(req.getParameter("salNo").trim());

				String salName = req.getParameter("salName");
				String salNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$";
				if (salName == null || salName.trim().length() == 0) {
					errorMsgs.add("美容店店名請勿空白");
				} else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("美容店店名只能是中、英文字母、數字, 且長度必需在2到10之間");
				}

				String salOwner = req.getParameter("salOwner");
			String salOwnerReg = "^[(\u4e00-\u9fa5)]{2,10}$";				
			if(salOwner == null || salOwner.trim().length() ==0) {
				errorMsgs.add("負責人姓名請勿空白");
			}else if(!salOwner.trim().matches(salOwnerReg)){
				errorMsgs.add("負責人姓名: 只能是中文字母,且長度必需在2到10之間");
			}

				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)]{6,10}$";

				if (salPh == null || salPh.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				} else if (!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("輸入正確電話");
				}

				String salMail = req.getParameter("salMail");

				if (salMail == null || salMail.trim().length() == 0) {
					errorMsgs.add("電子郵件請勿空白");
				}

				String salCity = req.getParameter("salCity");
				if (salCity == null) {
					errorMsgs.add("請選擇縣市");
				}

				String salDist = req.getParameter("salDist");
				if (salDist == null) {
					errorMsgs.add("請選擇區域");
				}

				String salAdr = req.getParameter("salAdr");
				if (salAdr == null) {
					errorMsgs.add("請輸入地址");
				}

				String salSTime = req.getParameter("salSTime");
				if (salSTime == null) {
					errorMsgs.add("請選擇開店時間");
				}

				String salETime = req.getParameter("salETime");
				if (salETime == null) {
					errorMsgs.add("請選擇閉店時間");
				}

				String salRemit = req.getParameter("salRemit");
				if (salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("匯款帳號請勿空白");
				}

				String bankCode = req.getParameter("bankCode");
				if (bankCode == null) {
					errorMsgs.add("請輸入銀行代碼");
				}

				String salInfo = req.getParameter("salInfo");

				Integer salPetType = null;
				try {
					salPetType = new Integer(req.getParameter("salPetType"));
				} catch (NumberFormatException e) {
					errorMsgs.add("請選擇接受寵物類型");
				}

				byte[] salCertif = null;
				byte[] buf = null;
				Part part = req.getPart("salCertif");
				InputStream in = part.getInputStream();
				buf = new byte[in.available()];
				in.read(buf);
				salCertif = buf;

				SalonVO salonVO = new SalonVO();
				salonVO.setSalNo(salNo);
				salonVO.setSalName(salName);
				salonVO.setSalOwner(salOwner);
				salonVO.setSalPh(salPh);
				salonVO.setSalMail(salMail);
				salonVO.setSalCity(salCity);
				salonVO.setSalDist(salDist);
				salonVO.setSalAdr(salAdr);
				salonVO.setSalSTime(salSTime);
				salonVO.setSalETime(salETime);
				salonVO.setSalRemit(salRemit);
				salonVO.setBankCode(bankCode);
				salonVO.setSalInfo(salInfo);
				salonVO.setSalPetType(salPetType);
				salonVO.setSalCertif(salCertif);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("salonVO", salonVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/fail.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				SalonService salSvc = new SalonService();
				salonVO = salSvc.updatesalon(salNo, salName, salOwner, salPh, salMail, salCity, salDist, salAdr,
						salSTime, salETime, salRemit, bankCode, salInfo, salPetType, salCertif);

				req.setAttribute("salonVO", salonVO);
				String url = "/front-end/salon/salonindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/fail.jsp");
				failureView.forward(req, res);
			}

		}
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String salNo = new String(req.getParameter("salNo"));

				SalonService salonSvc = new SalonService();
				salonSvc.deleteSalon(salNo);

				String url = "/front/salon/deletesuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonregi.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getALLSalon".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				SalonService salSvc = new SalonService();
				List<SalonVO> salonList = salSvc.getAll();

				req.setAttribute("salonList", salonList);
				String url = "/front-end/salon/listAllSalon.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getone_for_display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String str = req.getParameter("salNo");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入美容店編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("");
					failureView.forward(req, res);
					return;
				}

				String salNo = null;
				try {
					salNo = new String(str);
				} catch (Exception e) {
					errorMsgs.add("美容店編號不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
					failureView.forward(req, res);
					return;
				}

				// 開始查資料
				SalonService salSvc = new SalonService();
				SalonVO salonVO = salSvc.getonesalon(salNo);
				if (salonVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("salonVO", salonVO);
				String url = "/front-end/salon/salonindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
				failureView.forward(req, res);
			}

		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String salNo = new String(req.getParameter("salNo"));

				SalonService salSvc = new SalonService();
				SalonVO salonVO = salSvc.getonesalon(salNo);

				req.setAttribute("salonVO", salonVO);
				String url = "/front-end/salon/updateSalon.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonManager.jsp");
				failureView.forward(req, res);
			}
		}

		if ("unAcceptList".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				SalonService salSvc = new SalonService();
				List<SalonVO> salonList = salSvc.getAllUnaccept();

				req.setAttribute("salonList", salonList);
				String url = "/back-end/salon/unacceptList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonManager.jsp");
				failureView.forward(req, res);
			}

		}
		

		if ("login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			
				try {
					String salAc = new String(req.getParameter("salAc"));
					String salPw = new String(req.getParameter("salPw"));

					SalonService salSvc = new SalonService();
					SalonVO salonVO = salSvc.login(salAc, salPw);

					if (salonVO == null) {
						errorMsgs.add("帳號密碼錯誤");	
						String url = "/front-end/salon/Glogin.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
					} else if(salonVO.getSalStatus() == 0 || salonVO.getSalStatus() == 2){
						errorMsgs.add("審核尚未通過");	
						String url = "/front-end/salon/Glogin.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
					}else{
						 session = req.getSession();
						 session.setAttribute("salonVO", salonVO);	
						 
						try {
							String location = (String) session.getAttribute("location");
							if (location != null) {
								session.removeAttribute("location");
								res.sendRedirect(location);
								return;
							}
						} catch (Exception ignored) {
						}
						res.sendRedirect(req.getContextPath() + "/front-end/salon/index.jsp");						
					}

				} catch (Exception e) {
					errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonManager.jsp");
					failureView.forward(req, res);
				}
			}
		
		if("logout".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				session.invalidate();
				
				res.sendRedirect(req.getContextPath() + "/front-end/salon/index.jsp");	
				
			}catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonManager.jsp");
				failureView.forward(req, res);
			}
			
		}
	
		
		if("approve".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				
				String salNo = req.getParameter("salNo").trim();								
				Integer salStatus = Integer.parseInt(req.getParameter("salStatus"));
				
				/*************************** 2.開始新增資料 ***************************************/
				SalonService salSvc = new SalonService();
				salSvc.approve(salNo, salStatus);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/salon/unacceptList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("審核失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listUnverifiedLic.jsp");
				failureView.forward(req, res);
			}
		
		}
		
		if("notApprove".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				
				String salNo = req.getParameter("salNo").trim();								
				Integer salStatus = Integer.parseInt(req.getParameter("salStatus"));
				
				/*************************** 2.開始新增資料 ***************************************/
				SalonService salSvc = new SalonService();
				salSvc.notApprove(salNo, salStatus);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/salon/unacceptList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("審核失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listUnverifiedLic.jsp");
				failureView.forward(req, res);
			}
		
		}
		
		

		
	}

}
