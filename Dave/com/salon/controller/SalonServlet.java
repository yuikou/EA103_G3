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
					errorMsgs.add("�b���ФŪť�");	
				}else if(!salAc.trim().matches(salAcReg)) {
					errorMsgs.add("�b���п�J�^��B�Ʀr,�B���ץ����b6~15�Ӧr����");
				}
				
				String salPw =req.getParameter("salPw");
				String salPwReg = "^[(a-zA-Z0-9) {6-15}]$";
				if(salPw == null ||salPw.trim().length() == 0) {
					errorMsgs.add("�b���ФŪť�");
				}else if (salPw.trim().matches(salPwReg)) {
					errorMsgs.add("�K�X�п�J�^��B�Ʀr,�B���ץ����b6~15�Ӧr����");
				}
												
				String salName = req.getParameter("salName");
				String salNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";				
				if(salName == null ||salName.trim().length() == 0) {
					errorMsgs.add("���e�����W�ФŪť�");
				}else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("���e�����W�u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
				}
				
				String salOwner = req.getParameter("salOwner");
				String salOwnerReg = "^[(\u4e00-\u9fa5) {2,10}]$";				
				if(salOwner == null || salOwner.trim().length() ==0) {
					errorMsgs.add("�t�d�H�m�W�ФŪť�");
				}else if(!salOwner.trim().matches(salOwnerReg)){
					errorMsgs.add("�t�d�H�m�W: �u��O����r��,�B���ץ��ݦb2��10����");
				}
				
				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)] {6,10}$";
				
				if(salPh ==null ||salPh.trim().length() == 0) {
					errorMsgs.add("�q�ܽФŪť�");
				}else if(!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("��J���T�q��");
				}
				
				String salMail =req.getParameter("salMail");
				
				if(salMail == null || salMail.trim().length() ==0) {
					errorMsgs.add("�q�l�l��ФŪť�");
				}
				
				String salCity = req.getParameter("salCity");				
				if (salCity == null) {
					errorMsgs.add("�п�ܿ���");
				}
				
				String salDist = req.getParameter("salDist");
				if(salDist == null) {
					errorMsgs.add("�п�ܰϰ�");
				}
				
				String salAdr = req.getParameter("salAdr");
				if(salAdr == null) {
					errorMsgs.add("�п�J�a�}");
				}
				
				String salSTime = req.getParameter("salSTime");
				if(salSTime == null) {
					errorMsgs.add("�п�ܶ}���ɶ�");
				}
				
				String salETime = req.getParameter("salETime");
				if(salETime == null) {
					errorMsgs.add("�п�ܳ����ɶ�");
				}
				
				String salRemit = req.getParameter("salRemit");
				if(salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("�״ڱb���ФŪť�");					
				}
				
				String bankCode =req.getParameter("bankCode");
				if(bankCode == null) {
					errorMsgs.add("�п�J�Ȧ�N�X");
				}
				
				String salInfo = req.getParameter("salInfo");
				
				Integer salStatus =new Integer(req.getParameter("salStatus"));
				
				Integer salPetType = null;
				try {
					salPetType =new Integer(req.getParameter("salPetType"));
				}catch(NumberFormatException e) {
					errorMsgs.add("�п�ܱ����d������");
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
				
				//�}�l�s�W���
				SalonService salonSvc =new SalonService();
				salonVO =salonSvc.addsalon(salName, salOwner, salPh, salMail, salCity, salDist, salAdr, salAc, salPw, salSTime, salETime, salRemit, bankCode,salStatus, salInfo, salPetType);
				
				//�s�W���\
				String url = "front-end/salon/salonregicheck.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����
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

	
		
	

