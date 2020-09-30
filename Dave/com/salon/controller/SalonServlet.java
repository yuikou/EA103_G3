package com.salon.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.salon.model.SalonService;
import com.salon.model.SalonVO;



public class SalonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insert".equals(action)) {
			System.out.println("success");
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String salAc =req.getParameter("salAc");
				String salAcReg = "^[(a-zA-Z0-9) {6-15}]$";
				if(salAc == null || salAc.trim().length() ==0) {
					errorMsgs.add("帳號請勿空白");	
				}else if(!salAc.trim().matches(salAcReg)) {
					errorMsgs.add("帳號請輸入英文、數字,且長度必須在6~15個字之間");
				}
				
				String salPw =req.getParameter("salPw");
				String salPwReg = "^[(a-zA-Z0-9) {6-15}]$";
				if(salPw == null ||salPw.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}else if (salPw.trim().matches(salPwReg)) {
					errorMsgs.add("密碼請輸入英文、數字,且長度必須在6~15個字之間");
				}
												
				String salName = req.getParameter("salName");
				String salNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";				
				if(salName == null ||salName.trim().length() == 0) {
					errorMsgs.add("美容店店名請勿空白");
				}else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("美容店店名只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}
				
				String salOwner = req.getParameter("salOwner");
				String salOwnerReg = "^[(\u4e00-\u9fa5) {2,10}]$";				
				if(salOwner == null || salOwner.trim().length() ==0) {
					errorMsgs.add("負責人姓名請勿空白");
				}else if(!salOwner.trim().matches(salOwnerReg)){
					errorMsgs.add("負責人姓名: 只能是中文字母,且長度必需在2到10之間");
				}
				
				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)] {6,10}$";
				
				if(salPh ==null ||salPh.trim().length() == 0) {
					errorMsgs.add("電話請勿空白");
				}else if(!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("輸入正確電話");
				}
				
				String salMail =req.getParameter("salMail");
				
				if(salMail == null || salMail.trim().length() ==0) {
					errorMsgs.add("電子郵件請勿空白");
				}
				
				String salCity = req.getParameter("salCity");				
				if (salCity == null) {
					errorMsgs.add("請選擇縣市");
				}
				
				String salDist = req.getParameter("salDist");
				if(salDist == null) {
					errorMsgs.add("請選擇區域");
				}
				
				String salAdr = req.getParameter("salAdr");
				if(salAdr == null) {
					errorMsgs.add("請輸入地址");
				}
				
				String salSTime = req.getParameter("salSTime");
				if(salSTime == null) {
					errorMsgs.add("請選擇開店時間");
				}
				
				String salETime = req.getParameter("salETime");
				if(salETime == null) {
					errorMsgs.add("請選擇閉店時間");
				}
				
				String salRemit = req.getParameter("salRemit");
				if(salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("匯款帳號請勿空白");					
				}
				
				String bankCode =req.getParameter("bankCode");
				if(bankCode == null) {
					errorMsgs.add("請輸入銀行代碼");
				}
				
				String salInfo = req.getParameter("salInfo");
				
				Integer salStatus =new Integer(req.getParameter("salStatus"));
				
				Integer salPetType = null;
				try {
					salPetType =new Integer(req.getParameter("salPetType"));
				}catch(NumberFormatException e) {
					errorMsgs.add("請選擇接受寵物類型");
				}
				
				SalonVO salonVO =new SalonVO();
				salonVO.setSalName(salName);
				salonVO.setSalOwner(salOwner);
				salonVO.setSalPh(salPh);
				salonVO.setSalMail(salMail);
				salonVO.setSalCity(salCity);
				salonVO.setSalDist(salDist);
				salonVO.setSalAdr(salAdr);
				salonVO.setSalAc(salAc);
				salonVO.setSalPw(salPw);
				salonVO.setSalSTime(salSTime);
				salonVO.setSalETime(salETime);
				salonVO.setBankCode(bankCode);
				salonVO.setSalInfo(salInfo);
				salonVO.setSalPetType(salPetType);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salonVO", salonVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/salon/salonregi.jsp");
					failureView.forward(req, res);
					return;
				}
				
				//開始新增資料
				SalonService salonSvc =new SalonService();
				salonVO =salonSvc.addsalon(salName, salOwner, salPh, salMail, salCity, salDist, salAdr, salAc, salPw, salSTime, salETime, salRemit, bankCode,salStatus, salInfo, salPetType);
				
				//新增成功
				String url = "front-end/salon/salonregicheck.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交
				successView.forward(req, res);
				
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/salon/salonregi.jsp");
				failureView.forward(req, res);		
				}
		
		}
		}
}

	
		
	

