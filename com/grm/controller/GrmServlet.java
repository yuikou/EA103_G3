package com.grm.controller;

import java.io.*;
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

import com.grm.model.GrmService;
import com.grm.model.GrmVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class GrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GrmServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
//����o�o�X�ШD�����e���s��
		HttpSession session = req.getSession();
		String salno = (session.getAttribute("salno")).toString();
		String action = req.getParameter("action");
		
//System.out.println(salno);	
//System.out.println(action);	
		
// �Ӧ�addGrm.jsp���ШD, �s�W���e�v���		
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
			/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
			String gname = req.getParameter("gname");
			String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (gname == null || gname.trim().length() == 0) {
				errorMsgs.add("���e�v�ʺ�: �ФŪť�");
			} else if(!gname.trim().matches(nameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
				errorMsgs.add("���e�v�ʺ�: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
            }
			
			String ginfo = req.getParameter("ginfo").trim();
		
			InputStream in = req.getPart("grmPic").getInputStream();
			byte[] gpic = new byte[in.available()];
			in.read(gpic);
			in.close();
			
			Integer isdel = new Integer(req.getParameter("isDelete").trim());
			
			GrmVO grmvo = new GrmVO();
			grmvo.setGroomerName(gname);
			grmvo.setGroomerInfo(ginfo);
			grmvo.setGroomerPic(gpic);
			grmvo.setIsDelete(isdel);
			grmvo.setSalNo(salno);
			
			// ���~�B�z
			
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("grmVO", grmvo);
				RequestDispatcher failure = req.getRequestDispatcher("/back-end/grm/addGrm.jsp");
				failure.forward(req, res);
				return;
			}
			/***************************2.�}�l�s�W���***************************************/
			GrmService grmSvc = new GrmService();
			grmSvc.addGrmVO(salno, gname, ginfo, gpic, isdel);
			
			/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
			String url = "/back-end/grm/listAllGrm.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
// �Ӧ�listAllGrm.jsp���ШD, ���o��@���e�v�קﭶ��
		if ("getOne_For_Update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String groomerNo = req.getParameter("groomerNo");
				
				/***************************2.�}�l�d�߸��****************************************/
				GrmService grmSvc = new GrmService();
				GrmVO grmVO = grmSvc.getOneGrm(groomerNo);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("grmVO", grmVO);         // ��Ʈw���X��grmVO����,�s�Jreq
				String url = "/back-end/grm/update_grm_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_grm_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
// �Ӧ�update_grm_input.jsp���ШD, �ק�@����e�v�����
		if ("update".equals(action)) { // �Ӧ�listAllGrm.jsp
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
//System.out.println("�i�Jupdate�P�_");	
				String groomerNo = (req.getParameter("grmno").trim());
//System.out.println(groomerNo);
				String gname = req.getParameter("gname");
				String gnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (gname == null || gname.trim().length() == 0) {
					errorMsgs.add("!!���e�v�ʺ�!!�ФŪť�");
				} else if(!gname.trim().matches(gnameReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("���e�v�ʺ�: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
	            }
//System.out.println(gname);				
				String ginfo = req.getParameter("ginfo").trim();
//				if (ginfo == null || ginfo.trim().length() == 0) {
//					errorMsgs.add("!!���e�v²��!!�ФŪť�");
//				}	
//System.out.println(ginfo);				
				Integer isdel = new Integer(req.getParameter("isDelete"));
//System.out.println(isdel);				
				//�P�_�Ϥ��O�_�Q���, �p�G�S���Q��h�ϥ�DB�����, �p�G�����ϥηs���Ϥ�
				//�ϥ�getPart��k�����i�Ϥ����
				GrmVO grmvo = new GrmVO();
				GrmService grmSvc = new GrmService();
//System.out.println(req.getPart("grmPic"));
				byte[] gPic = null;				
				//�p�Gtype multipart/form-data����(part == null)�h�N��S���s���W�ǹϤ�
				if(req.getPart("grmPic").getContentType() == null) {
					//�ϥ�DB�̪����
					gPic = grmSvc.getOneGrm(groomerNo).getGroomerPic();
				}else {
				//�p�GgetPart�������
					InputStream in = req.getPart("grmPic").getInputStream();
					gPic = new byte[in.available()]; //�ϥ�in.available()��o"part.getInputStream()"���iŪ��bytes
					in.read(gPic);
					in.close();
				}
				
				grmvo.setGroomerNo(groomerNo);
				grmvo.setGroomerPic(gPic);
				grmvo.setGroomerName(gname);
				grmvo.setGroomerInfo(ginfo);
				grmvo.setIsDelete(isdel);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("grmvo", grmvo); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/grm/update_grm_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				grmvo = grmSvc.updateGrmVO(groomerNo, gname, ginfo, gPic, isdel);
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("grmvo", grmvo); // ��Ʈwupdate���\��,���T����grmVO����,�s�Jreq
				String url = "/back-end/grm/listAllGrm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // �Ӧ�listAllGrm.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String groomerNo = (req.getParameter("groomerNo")).toString().trim();
				
				/***************************2.�}�l�R�����***************************************/
				GrmService grmSvc = new GrmService();
				grmSvc.deleteGrmVO(groomerNo);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/back-end/grm/listAllGrm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllGrm.jsp");
				failureView.forward(req, res);
			}
		}
//�B�zfront-end��ܬ��e�v
		if("selectGrm".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***************************1.�����ШD�Ѽ�***************************************/
			//���o�w��ܪ����e�v�s��
			String groomerNo = req.getParameter("groomerNo").trim();
			//�]�w��session
			session.setAttribute("groomerNo", groomerNo);	
			/***************************2.�]�w����,�ǳ������ܤ��***********/	
			String url = "/front-end/grmOff/selectDay.jsp";
			RequestDispatcher selectDay = req.getRequestDispatcher(url);
			selectDay.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grm/choose_grm.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
