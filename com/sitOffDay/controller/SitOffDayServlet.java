package com.sitOffDay.controller;

import java.io.*;
import java.sql.Date;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sitOffDay.model.*;

public class SitOffDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int gpID = 7;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

/* --------------------------------------�Ӧ� listSitFollow.jsp - ���o�@��O�i�䤤�@�تA�Ȫ��ɶ�-------------------------------------- */
		if ("getAll".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				
				list = sodSvc.getByFK(sitSrvNo);
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �����g�Xjson�榡���
				JSONArray sodList = new JSONArray();
				
				Date startDay = null,endDay = null;					// null
				String startTime = null, endTime = null;			// null
				
				for (int i = 0; i < list.size(); i++) {
					SitOffDayVO sodVO = list.get(i);
					SitOffDayVO sodVOtemp;
					// �p�G�u���@�������g�J�}�C
					if (list.size() == 1) {
						JSONObject sodjson = new JSONObject();
						sodjson.put("offDayNo", sodVO.getOffDayNo());
						sodjson.put("sitSrvNo", sodVO.getSitSrvNo());
						sodjson.put("offDayS", sodVO.getOffDay());
						sodjson.put("offDayE", sodVO.getOffDay());
						sodjson.put("offTimeS", sodVO.getOffTime());
						sodjson.put("offTimeE", sodVO.getOffTime());
						sodjson.put("offDayTyp", sodVO.getOffDayTyp());
						sodjson.put("groupID", sodVO.getGroupID());
						
						sodList.put(sodjson);
					
					// �p�G���h���n�~��P�_
					} else {
						// �קK�̫�@���L�k���
						if (i+1 < list.size()) {
							sodVOtemp = list.get(i+1); // �U�@��
						} else {
							sodVOtemp = list.get(0);
						}
						
						Date thisDay = sodVO.getOffDay(); 						/* 10/7 10/8 10/9  10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 10/18 */
						Date nextDay = sodVOtemp.getOffDay(); 					/* 10/8 10/9 10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 10/18 10/7  */
						String thisTime = sodVO.getOffTime(); 					/* null null null  null  0800  0900  1000  null  0800  2400  1100  1200  */
						String nextTime = sodVOtemp.getOffTime(); 				/* null null null  0800	 0900  1000  null  0800  0900  0100  1200  null  */
						
						// �C�ղĤ@��
						if (startDay == null) {
							startDay = thisDay;									// 10/7 10/8  --   --    10/12  ---------- 10/15 10/17 -----------------
							endDay = thisDay;									// 10/7 10/8  --   --    10/12	---------- 10/15 10/17 -----------------
							startTime = thisTime;								// null null  --   --    0800	---------- null  0800  -----------------
							endTime = thisTime;									// null null  --   --    0800	---------- null  0800  -----------------
						}
						// �P�դ~��� endDay & endTime�A�B���X���j�餣�g�J�}�C
						if (sodVOtemp.getGroupID().equals(sodVO.getGroupID())) {
							endDay = nextDay;									// --  10/9  10/10  --	 10/12  10/12  --    --  10/17 10/18 10/18  --
							endTime = nextTime;									// --  null  null   --   0900   1000   --    --  0900  0100  1200   --
							continue;
							
						// ���P�դ~�N�w�g���n����Ƽg�J�}�C
						} else {
							
							JSONObject sodjson = new JSONObject();
							sodjson.put("offDayNo", sodVO.getOffDayNo());
							sodjson.put("sitSrvNo", sodVO.getSitSrvNo());
							sodjson.put("offDayTyp", sodVO.getOffDayTyp());
							sodjson.put("groupID", sodVO.getGroupID());
							sodjson.put("offDayS", startDay);					// 10/7 --   --    10/8  --    --    10/12  10/15  --   --   --     10/17
							sodjson.put("offDayE", endDay);						// 10/7 --   --    10/10 --    --    10/12  10/15  --   --   --     10/18
							sodjson.put("offTimeS", startTime);					// null --   --	   null  --    --    0800   null   --   --   --     0800
							sodjson.put("offTimeE", endTime);					// null --   --	   null  --    --    1000   null   --   --   --     1200
							
							
							sodList.put(sodjson);
							startDay = null; // �g�J�}�C��A�M��startDay�����U�@�ն}�l���P�_
						}
					}
					
				}
				out.print(sodList);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
				failureView.forward(req, res);
			}
		}
		
/* ------------------------------�Ӧ� showAllSitDay.jsp / �iorder�j ���ШD  - �s�W(�����s�W�B�ק�s�W)��-------------------------------- */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs = ", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String[] sitSrvArr = req.getParameterValues("sitSrvNo");

				
				/* 2-offDay */
				List<String> offDayList = new ArrayList<String>();
				String offDayS = req.getParameter("offDayS");
				String offDayE = req.getParameter("offDayE");
				try {
					Date offDateS = Date.valueOf(offDayS.trim());
					Date offDateE = Date.valueOf(offDayE.trim());
					
					while(!offDateS.after(offDateE)) {
						offDayList.add(offDateS.toString());
						offDateS.setTime(offDateS.getTime() + 1000*60*60*24);
					}
					
				} catch (IllegalArgumentException e) {
					if (offDayS.isEmpty()) {
						offDayS = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("�п�J�𰲤�(�_)");
					}
					if (offDayE.isEmpty()) {
						offDayE = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("�п�J�𰲤�(�W)");
					}
				}
				
				/* 3-offTime */
				String offTime = null;
				
				/* 4-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// �s�W��-���A0 ; �s�W�q��-���A1
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				String groupID = "G" + gpID;
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
					}
				}
				gpID++;
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �p�G�O�s�W�𰲡A�^��showAllSitDay.jsp
				if (offDayTyp==0) {
					// �^��쥻���A�Ȥ��
					req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
					String url = "showAllSitDay.jsp";
					RequestDispatcher sucessView = req.getRequestDispatcher(url);
					sucessView.forward(req, res);
				}
				// �p�G�O�s�W�q��Ado nothing
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�s�W���ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------�Ӧ� showAllSitDay.jsp ���ШD - �ק��(���R���A�s�W) -------------------------------------- */
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-groupId */
				String groupId = req.getParameter("groupId");
				
				/* 2-sitSrvNo */
				String[] sitSrvArr = req.getParameterValues("sitSrvNo");

				
				/* 3-offDay */
				List<String> offDayList = new ArrayList<String>();
				String offDayS = req.getParameter("offDayS");
				String offDayE = req.getParameter("offDayE");
				try {
					Date offDateS = Date.valueOf(offDayS.trim());
					Date offDateE = Date.valueOf(offDayE.trim());
					
					while(!offDateS.after(offDateE)) {
						offDayList.add(offDateS.toString());
						offDateS.setTime(offDateS.getTime() + 1000*60*60*24);
					}
					
				} catch (IllegalArgumentException e) {
					if (offDayS.isEmpty()) {
						offDayS = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("�п�J�𰲤�(�_)");
					}
					if (offDayE.isEmpty()) {
						offDayE = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("�п�J�𰲤�(�W)");
					}
				}
				
				/* 4-offTime */
				String offTime = null;
				
				/* 5-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// �s�W��-���A0 ; �s�W�q��-���A1
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				sodSvc.del(groupId);
				
				String groupID = "G" + (gpID++);
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
					}
				}
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �^��쥻���A�Ȥ��
				req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
				
				String url = "showAllSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDayn.jsp");
				failureView.forward(req, res);
			}
		}

/* -------------------------------�Ӧ� showAllSitDay.jsp / �iorder�j ���ШD - �R��(�����R���B�ק�R��)��------------------------------- */
		if ("del".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-offDayNo */
				String groupId = req.getParameter("groupId");
				
				/* 2-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				sodSvc.del(groupId);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �^��쥻���A�Ȥ��
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				
				String url = "showAllSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�R�����ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------�Ӧ� �O�i�ӤH�����ӧO�A�ȶ��� ���ШD - ���o���i�w���ɶ������--------------------------------------- */
		if("getOneSitSrv_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = (String) session.getAttribute("sitSrvNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				list = sodSvc.getByFK(sitSrvNo);
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("list", list);
				
				String url = "showOneSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showOneSitDay.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
