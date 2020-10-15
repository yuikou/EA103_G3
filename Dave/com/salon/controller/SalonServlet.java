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
					errorMsgs.add("�b���ФŪť�");	
				}else if(!salAc.trim().matches(salAcReg)) {
					errorMsgs.add("�b���п�J�^��B�Ʀr,�B���ץ����b6~15�Ӧr����");
				}

				String salPw = req.getParameter("salPw");
				String salPwReg = "^[(a-zA-Z0-9)]{6,15}$";
				if(salPw == null || salPw.trim().length() == 0) {
					errorMsgs.add("�b���ФŪť�");
				}else if (!salPw.trim().matches(salPwReg)) {
					errorMsgs.add("�K�X�п�J�^��B�Ʀr,�B���ץ����b6~15�Ӧr����");
				}

				String salName = req.getParameter("salName");
				String salNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$";
				if (salName == null || salName.trim().length() == 0) {
					errorMsgs.add("���e�����W�ФŪť�");
				} else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("���e�����W�u��O���B�^��r���B�Ʀr, �B���ץ��ݦb2��10����");
				}

				String salOwner = req.getParameter("salOwner");
				String salOwnerReg = "^[(\u4e00-\u9fa5)]{2,10}$";				
				if(salOwner == null || salOwner.trim().length() ==0) {
					errorMsgs.add("�t�d�H�m�W�ФŪť�");
				}else if(!salOwner.trim().matches(salOwnerReg)){
					errorMsgs.add("�t�d�H�m�W: �u��O����r��,�B���ץ��ݦb2��10����");
				}

				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)]{6,10}$";

				if (salPh == null || salPh.trim().length() == 0) {
					errorMsgs.add("�q�ܽФŪť�");
				} else if (!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("��J���T�q��");
				}

				String salMail = req.getParameter("salMail");

				if (salMail == null || salMail.trim().length() == 0) {
					errorMsgs.add("�q�l�l��ФŪť�");
				}

				String salCity = req.getParameter("salCity");
				if (salCity == null) {
					errorMsgs.add("�п�ܿ���");
				}

				String salDist = req.getParameter("salDist");
				if (salDist == null) {
					errorMsgs.add("�п�ܰϰ�");
				}

				String salAdr = req.getParameter("salAdr");
				if (salAdr == null) {
					errorMsgs.add("�п�J�a�}");
				}

				String salSTime = req.getParameter("salSTime");
				if (salSTime == null) {
					errorMsgs.add("�п�ܶ}���ɶ�");
				}

				String salETime = req.getParameter("salETime");
				if (salETime == null) {
					errorMsgs.add("�п�ܳ����ɶ�");
				}

				String salRemit = req.getParameter("salRemit");
				if (salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("�״ڱb���ФŪť�");
				}

				String bankCode = req.getParameter("bankCode");
				if (bankCode == null) {
					errorMsgs.add("�п�J�Ȧ�N�X");
				}

				String salInfo = req.getParameter("salInfo");

				Integer salStatus = 0;

				Integer salPetType = null;
				try {
					salPetType = new Integer(req.getParameter("salPetType"));
				} catch (NumberFormatException e) {
					errorMsgs.add("�п�ܱ����d������");
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

				// �}�l�s�W���
				SalonService salonSvc = new SalonService();
				salonVO = salonSvc.addsalon(salName, salOwner, salPh, salMail, salCity, salDist, salAdr, salAc, salPw,
						salSTime, salETime, salRemit, bankCode, salStatus, salInfo, salPetType, salCertif);
				req.setAttribute("salonVO", salonVO);
				// �s�W���\
				String url = "/front-end/salon/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����
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
					errorMsgs.add("���e�����W�ФŪť�");
				} else if (!salName.trim().matches(salNameReg)) {
					errorMsgs.add("���e�����W�u��O���B�^��r���B�Ʀr, �B���ץ��ݦb2��10����");
				}

				String salOwner = req.getParameter("salOwner");
			String salOwnerReg = "^[(\u4e00-\u9fa5)]{2,10}$";				
			if(salOwner == null || salOwner.trim().length() ==0) {
				errorMsgs.add("�t�d�H�m�W�ФŪť�");
			}else if(!salOwner.trim().matches(salOwnerReg)){
				errorMsgs.add("�t�d�H�m�W: �u��O����r��,�B���ץ��ݦb2��10����");
			}

				String salPh = req.getParameter("salPh");
				String salPhReg = "^[(0-9)]{6,10}$";

				if (salPh == null || salPh.trim().length() == 0) {
					errorMsgs.add("�q�ܽФŪť�");
				} else if (!salPh.trim().matches(salPhReg)) {
					errorMsgs.add("��J���T�q��");
				}

				String salMail = req.getParameter("salMail");

				if (salMail == null || salMail.trim().length() == 0) {
					errorMsgs.add("�q�l�l��ФŪť�");
				}

				String salCity = req.getParameter("salCity");
				if (salCity == null) {
					errorMsgs.add("�п�ܿ���");
				}

				String salDist = req.getParameter("salDist");
				if (salDist == null) {
					errorMsgs.add("�п�ܰϰ�");
				}

				String salAdr = req.getParameter("salAdr");
				if (salAdr == null) {
					errorMsgs.add("�п�J�a�}");
				}

				String salSTime = req.getParameter("salSTime");
				if (salSTime == null) {
					errorMsgs.add("�п�ܶ}���ɶ�");
				}

				String salETime = req.getParameter("salETime");
				if (salETime == null) {
					errorMsgs.add("�п�ܳ����ɶ�");
				}

				String salRemit = req.getParameter("salRemit");
				if (salRemit == null || salRemit.trim().length() == 0) {
					errorMsgs.add("�״ڱb���ФŪť�");
				}

				String bankCode = req.getParameter("bankCode");
				if (bankCode == null) {
					errorMsgs.add("�п�J�Ȧ�N�X");
				}

				String salInfo = req.getParameter("salInfo");

				Integer salPetType = null;
				try {
					salPetType = new Integer(req.getParameter("salPetType"));
				} catch (NumberFormatException e) {
					errorMsgs.add("�п�ܱ����d������");
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
					req.setAttribute("salonVO", salonVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/fail.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				SalonService salSvc = new SalonService();
				salonVO = salSvc.updatesalon(salNo, salName, salOwner, salPh, salMail, salCity, salDist, salAdr,
						salSTime, salETime, salRemit, bankCode, salInfo, salPetType, salCertif);

				req.setAttribute("salonVO", salonVO);
				String url = "/front-end/salon/salonindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
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
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o���:" + e.getMessage());
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
					errorMsgs.add("�п�J���e���s��");
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
					errorMsgs.add("���e���s�������T");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
					failureView.forward(req, res);
					return;
				}

				// �}�l�d���
				SalonService salSvc = new SalonService();
				SalonVO salonVO = salSvc.getonesalon(salNo);
				if (salonVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/serchgroomer.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("salonVO", salonVO);
				String url = "/front-end/salon/salonindex.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
						errorMsgs.add("�b���K�X���~");	
						String url = "/front-end/salon/Glogin.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
					} else if(salonVO.getSalStatus() == 0 || salonVO.getSalStatus() == 2){
						errorMsgs.add("�f�֩|���q�L");	
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
					errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/salon/salonManager.jsp");
				failureView.forward(req, res);
			}
			
		}
	
		
		if("approve".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				
				String salNo = req.getParameter("salNo").trim();								
				Integer salStatus = Integer.parseInt(req.getParameter("salStatus"));
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SalonService salSvc = new SalonService();
				salSvc.approve(salNo, salStatus);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/salon/unacceptList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�f�֥��ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listUnverifiedLic.jsp");
				failureView.forward(req, res);
			}
		
		}
		
		if("notApprove".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				
				String salNo = req.getParameter("salNo").trim();								
				Integer salStatus = Integer.parseInt(req.getParameter("salStatus"));
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SalonService salSvc = new SalonService();
				salSvc.notApprove(salNo, salStatus);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/back-end/salon/unacceptList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�f�֥��ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listUnverifiedLic.jsp");
				failureView.forward(req, res);
			}
		
		}
		
		

		
	}

}
