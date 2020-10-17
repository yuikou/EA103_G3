package com.pet.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.pet.model.*;

@MultipartConfig
public class PetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		
		if("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String petNo = req.getParameter("petNo");
				if(petNo==null || petNo.trim().length()==0) {
					errorMsgs.add("�d���s�����o����");
				}
				PetService petSvc = new PetService();
				PetVO petVO = petSvc.getOnePet(petNo);
				if(petVO==null) {
					errorMsgs.add("�d�L���!");
				}
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/listAllPet.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("petVO", petVO);
				String url = "/front-end/pet/listOnePet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/listAllPet.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String petNo = req.getParameter("petNo");
				if(petNo==null || petNo.trim().length()==0) {
					errorMsgs.add("�d���s�����o����");
				}
				
				PetService petSvc = new PetService();
				petSvc.deletePet(petNo);
				
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/listAllPet.jsp");
					failureView.forward(req, res);
				}
				
				String url = "/front-end/pet/listAllPet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/listAllPet.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("insert".equals(action)) {
			Map<String, String> errorMsgs = new Hashtable<String, String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try{
				String petName = req.getParameter("petName");
				String petNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,5}$";
				if(petName==null || petName.trim().length()==0) {
					errorMsgs.put("petName", "�d���W�r���i����");
				}else if(!petName.trim().matches(petNameReg)) {
					errorMsgs.put("petName", "�d���m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���׳̦h5�Ӧr");
				}
				Integer petSex = null;
				if (req.getParameter("petSex") == null) {
					errorMsgs.put("petSex", "�п�ܩʧO");
				} else {
					petSex = new Integer(req.getParameter("petSex"));
				}
				
				java.sql.Date petBirth = null;
				try {
					petBirth = java.sql.Date.valueOf(req.getParameter("petBirth"));
				} catch(IllegalArgumentException e) {
					petBirth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("petBirth", "�п�J���!");
				}
				String memNo = req.getParameter("memNo");
				if(memNo == null || memNo.trim().length()==0) {
					errorMsgs.put("memNo", "�|���s�����~");
				}
				Integer petType = null;
				if(req.getParameter("petType")==null) {
					errorMsgs.put("petType", "�п���d������!");
				}else {
					petType = new Integer(req.getParameter("petType"));
				}
				
				Integer petCat = null;
				if(req.getParameter("petCat")==null) {
					errorMsgs.put("petCat", "�п���d������!");
				}else {
					petCat = new Integer(req.getParameter("petCat"));
				}
				String petChar = req.getParameter("petChar");
				
				Part part = req.getPart("petPhoto");
				byte[] petPhoto = null;
				InputStream in = part.getInputStream();
				if(in.available() != 0) {
					petPhoto = new byte[in.available()];
					in.read(petPhoto);
					in.close();
				} else {
					petPhoto = getPictureByteArray(getServletContext().getRealPath("/front-end/pet/image/petPhoto.jpeg"));
				}
				
				PetVO petVO = new PetVO();
				petVO.setMemNo(memNo);
				petVO.setPetBirth(petBirth);
				petVO.setPetCat(petCat);
				petVO.setPetChar(petChar);
				petVO.setPetName(petName);
				petVO.setPetPhoto(petPhoto);
				petVO.setPetSex(petSex);
				petVO.setPetType(petType);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("petVO", petVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/addPet.jsp");
					failureView.forward(req, res);
					return;
				}
				
				PetService petSvc = new PetService();
				petVO = petSvc.addPet(memNo, petName, petType, petCat, petSex, petBirth, petChar, petPhoto);
				req.setAttribute("petVO", petVO);
				String url = "/front-end/pet/listAllPet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				
				e.printStackTrace(System.err);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/addPet.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			if("getOne_For_Update".equals(action)) {
				try {
				String petNo = req.getParameter("petNo");
				if(petNo == null || petNo.trim().length()==0) {
					errorMsgs.add("�d���s�����~");
				}
				
				PetService petSvc = new PetService();
				PetVO petVO = petSvc.getOnePet(petNo);
				if(petVO == null) {
					errorMsgs.add("�d�L���");
				}
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("petVO", petVO);
					RequestDispatcher failView = req.getRequestDispatcher("/front-end/pet/listOnePet.jsp");
					failView.forward(req, res);
					return;
				}
				
				req.setAttribute("petVO", petVO);
				String url = "/front-end/pet/pet_update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				}catch(Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failView = req.getRequestDispatcher("/front-end/pet/listOnePet.jsp");
					failView.forward(req, res);
				}
			}
		}
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String memNo = req.getParameter("memNo");
				if(memNo==null || memNo.trim().length()==0) {
					errorMsgs.add("�|���s�����~");
				}
				
				String petNo = req.getParameter("petNo");
				if(petNo ==null || petNo.trim().length() == 0) {
					errorMsgs.add("�d���s�����~");
				}
				String petName = req.getParameter("petName");
				if(petName==null||petName.trim().length()==0) {
					errorMsgs.add("�ж�J�d���W�r");
				}
				
				Integer petSex = null;
				try {
					petSex = new Integer(req.getParameter("petSex").trim());
				}catch(NumberFormatException e) {
					errorMsgs.add("�п���d���ʧO");
				}
				
				Integer petType = null;
				try {
					petType = new Integer(req.getParameter("petType").trim());
				}catch(NumberFormatException e) {
					errorMsgs.add("�п���d������");
				}
				
				Integer petCat = null;
				try {
					petCat = new Integer(req.getParameter("petCat").trim());
				}catch(NumberFormatException e) {
					errorMsgs.add("�п���d�����O");
				}
				
				java.sql.Date petBirth = null;
				try {
					petBirth = java.sql.Date.valueOf(req.getParameter("petBirth"));
				}catch(IllegalArgumentException e) {
					petBirth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п���d���ͤ�");
				}
				
				String petChar = req.getParameter("petChar");
				
				Part part = req.getPart("petPhoto");
				byte[] petPhoto = null;
				if(part.getSize()!=0) {
					InputStream in = part.getInputStream();
					petPhoto = new byte[in.available()];
					in.read(petPhoto);
					in.close();
				}else {
					PetService petSvc = new PetService();
					PetVO petVO = petSvc.getOnePet(petNo);
					petPhoto = petVO.getPetPhoto();
				}
				
				PetVO petVO = new PetVO();
				petVO.setMemNo(memNo);
				petVO.setPetBirth(petBirth);
				petVO.setPetCat(petCat);
				petVO.setPetChar(petChar);
				petVO.setPetName(petName);
				petVO.setPetNo(petNo);
				petVO.setPetPhoto(petPhoto);
				petVO.setPetSex(petSex);
				petVO.setPetType(petType);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("petVO", petVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/pet_update.jsp");
					failureView.forward(req, res);
				}
				
				req.setAttribute("petVO", petVO);
				String url = "/front-end/pet/listOnePet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/pet/pet_update.jsp");
				failureView.forward(req, res);
			}
		}
	}
	// �ϥ�byte[]�覡
		public static byte[] getPictureByteArray(String path) throws IOException {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, i);
			}
			baos.close();
			fis.close();

			return baos.toByteArray();
		}
}
