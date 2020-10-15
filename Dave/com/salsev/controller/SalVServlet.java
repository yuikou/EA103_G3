package com.salsev.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.salsev.model.SalVService;
import com.salsev.model.SalsevVO;

public class SalVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SalVServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String salno = session.getAttribute("salno").toString();
		String action = req.getParameter("action");
		
//System.out.println(action);
//�Ӧ�addSalSev.jsp���ШD
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				String salsevname = req.getParameter("salsevname");
				
				String salsevinfo = req.getParameter("salsevinfo");
				if(salsevinfo == null || salsevinfo.trim().length() == 0) {
					errorMsgs.add("�A�ȶ��ئW�ٽФŪť�");
				}
				
				Integer petcat = new Integer (req.getParameter("petcat").trim());
				
				Integer salsevtime = new Integer(req.getParameter("salsevtime").trim()); 
				
				Integer salsevpr = new Integer(req.getParameter("salsevpr").trim()); 
				
				Integer status = new Integer(req.getParameter("status"));
				
				SalsevVO ssvo = new SalsevVO();
				ssvo.setSalsevname(salsevname);
				ssvo.setSalSevInfo(salsevinfo);
				ssvo.setPetcat(petcat);
				ssvo.setSalsevtime(salsevtime);
				ssvo.setSalsevpr(salsevpr);
				ssvo.setStatus(status);
				
				//���~�B�z
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salvVO", ssvo);
					RequestDispatcher failure = req.getRequestDispatcher("/back-end/salsev/addSalSev.jsp");
					failure.forward(req, res);
					return;
				}
				/***********************2.�s�W���*************************/
				SalVService ssvSvc = new SalVService();
				ssvSvc.addSalV(salno, petcat, salsevname, salsevinfo, salsevtime, salsevpr, status);
				
				/***********************3.�s�W����, ���e��*************************/
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
		
//�Ӧ�listAllSalSev.jsp���ШD, ���o��@�����H�K�ק�A�ȶ���
		if("getOne_For_Update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.�����ШD�Ѽ�
				String salsevno = req.getParameter("salsevno");
				
				//2.�}�l�d�߸��
				SalVService ssv = new SalVService();
				SalsevVO salvVO = ssv.getOneSalv(salsevno);
				
				//3.�d�ߧ���, �ǳ����
				req.setAttribute("salvVO", salvVO);
				String url = "/back-end/salsev/update_salv_input.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}

//�Ӧ�update_salsev_input.jsp���ШD, ��s�A�ȶ���		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.�����ШD�Ѽ�
				String salsevno = (req.getParameter("salsevno")).trim();
				
				String salsevname = (req.getParameter("salsevname")).trim();
				
				String salsevinfo = (req.getParameter("salsevinfo")).trim();
				
				Integer petcat = new Integer(req.getParameter("petcat"));
				
				Integer salsevtime = new Integer(req.getParameter("salsevtime"));
				
				Integer salsevpr = new Integer(req.getParameter("salsevpr"));
				
				Integer status = new Integer(req.getParameter("status"));
				
				SalsevVO salvVO = new SalsevVO();
				
				salvVO.setSalsevno(salsevno);
				salvVO.setSalsevname(salsevname);
				salvVO.setSalSevInfo(salsevinfo);
				salvVO.setPetcat(petcat);
				salvVO.setSalsevtime(salsevtime);
				salvVO.setSalsevpr(salsevpr);
				salvVO.setStatus(status);
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("salvVO", salvVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/salsev/listAllsalSev.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				//2.�}�l�ק���
				SalVService svsvc = new SalVService();
				salvVO = svsvc.updateSalV(salsevno, petcat, salsevname, salsevinfo, salsevtime, salsevpr, status);
				
				//3.�ק粒��, �ǳ����
				req.setAttribute("salvVO", salvVO);
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			}catch (Exception e){
				errorMsgs.add("�ק��ƥ���" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/salsev/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.�����ШD�Ѽ�
				String salsevno = (req.getParameter("salsevno")).toString().trim();
				//2.�}�l�R�����				
				SalVService ssvc = new SalVService();
				ssvc.deleteSalv(salsevno);
				
				//3.�R�����\,���e��
				String url = "/back-end/salsev/listAllSalSev.jsp";
				RequestDispatcher success = req.getRequestDispatcher(url);
				success.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/grm/listAllSalSev.jsp");
				failureView.forward(req, res);
			}
		}
//�B�zfront-end��ܪA�ȶ���
		if("selectSalV".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//1.�����ШD�Ѽ�
				String salsevno = (req.getParameter("salsevNo")).toString().trim();
				//�x�s�]�w��session
				session.setAttribute("salsevno", salsevno);
//System.out.println(salsevno);
				//�]�w����, ��歶�����ܬ��e�v
				String url = "/front-end/grm/choose_groomer.jsp";
				RequestDispatcher selectGrm = req.getRequestDispatcher(url);
				selectGrm.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("����ƥ���:"+ e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/salsev/select_SalSev.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
