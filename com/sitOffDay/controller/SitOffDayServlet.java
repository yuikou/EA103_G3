package com.sitOffDay.controller;

import java.io.*;
import java.sql.Date;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sitOffDay.model.*;
import com.sitSrv.model.SitSrvVO;

@WebServlet("/sitOffDay/sitOffDay.do")
public class SitOffDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int gpID = 100;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

/* --------------------------------------�Ӧ� showOneSitAll�iSrv�j.jsp - ���o�@��O�i�䤤�@�تA�Ȫ��ɶ�-------------------------------------- */
		if ("transfer".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				System.out.println("SitOffDayServlet_37.sitSrvNo = " + sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				// ���L
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("���ॢ�ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------�Ӧ� showAllSrvDay.jsp - ���o�@��O�i�䤤�@�تA�Ȫ��ɶ�-------------------------------------- */
		if ("getAll".equals(action)) {
//			List<String> errorMsgs = req.getParameter("errorMsgs");
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				
				list = sodSvc.getByFK(sitSrvNo);
				System.out.println("getAll���X"+list.size()+"�����");
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �����g�Xjson�榡���
				JSONArray sodList = new JSONArray();
				
				Date startDay = null,endDay = null;					// null
				String startTime = null, endTime = null;			// null
				
				for (int i = 0; i < list.size(); i++) {
					SitOffDayVO sodVO = list.get(i);
					SitOffDayVO sodVOtemp = null;
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
						// �C������U�@�������
						if (i+1 < list.size()) {
							sodVOtemp = list.get(i+1); // �U�@��
						// ��̫�@���A�B�̫�@�լ��h����Ʈɤ��~����
						} else if (sodVO.getGroupID().equals(list.get(i-1).getGroupID())) {
							JSONObject sodjson = new JSONObject();
							sodjson.put("offDayNo", sodVO.getOffDayNo());
							sodjson.put("sitSrvNo", sodVO.getSitSrvNo());
							sodjson.put("offDayTyp", sodVO.getOffDayTyp());
							sodjson.put("groupID", sodVO.getGroupID());
							sodjson.put("offDayS", startDay);					//															10/17
							sodjson.put("offDayE", endDay);						//															10/18
							sodjson.put("offTimeS", startTime);					//															0800
							sodjson.put("offTimeE", endTime);					//															1200
							sodList.put(sodjson);
							continue;
						// ��̫�@���A�B�̫�@�լ�1����Ʈ�
						} else {
							JSONObject sodjson = new JSONObject();
							sodjson.put("offDayNo", sodVO.getOffDayNo());
							sodjson.put("sitSrvNo", sodVO.getSitSrvNo());
							sodjson.put("offDayTyp", sodVO.getOffDayTyp());
							sodjson.put("groupID", sodVO.getGroupID());
							sodjson.put("offDayS", sodVO.getOffDay());			// 11/5
							sodjson.put("offDayE", sodVO.getOffDay());			// 11/5
							sodjson.put("offTimeS", sodVO.getOffTime());		// null
							sodjson.put("offTimeE", sodVO.getOffTime());		// null
							sodList.put(sodjson);
							continue;
						}
						
						Date thisDay = sodVO.getOffDay(); 						/* 10/7 10/8 10/9  10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 */
						Date nextDay = sodVOtemp.getOffDay(); 					/* 10/8 10/9 10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 10/18 */
						String thisTime = sodVO.getOffTime(); 					/* null null null  null  0800  0900  1000  null  0800  2400  1100  */
						String nextTime = sodVOtemp.getOffTime(); 				/* null null null  0800	 0900  1000  null  0800  0900  0100  1200  */
						
						// �C�ղĤ@��
						if (startDay == null) {
							startDay = thisDay;									// 10/7 10/8  --   --    10/12  ---------- 10/15 10/17 ------------
							endDay = thisDay;									// 10/7 10/8  --   --    10/12	---------- 10/15 10/17 ------------
							startTime = thisTime;								// null null  --   --    0800	---------- null  0800  ------------
							endTime = thisTime;									// null null  --   --    0800	---------- null  0800  ------------
						}
						// �P�դ~��� endDay & endTime�A�B���X���j�餣�g�J�}�C
						if (sodVOtemp.getGroupID().equals(sodVO.getGroupID())) {
							endDay = nextDay;									// --  10/9  10/10  --	 10/12  10/12  --    --  10/17 10/18 10/18
							endTime = nextTime;									// --  null  null   --   0900   1000   --    --  0900  0100  1200
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
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
//				failureView.forward(req, res);
				out.write("error: " + e.getMessage());
			}
		}
		
/* ------------------------------�Ӧ� showAllSrvDay.jsp��-------------------------------- */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs = ", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String[] sitSrvArr = req.getParameterValues("sitSrvNo");

				for (String sitSrvNo : sitSrvArr) {
					if ("all".equals(sitSrvNo)) {
						@SuppressWarnings("unchecked")
						List<String> newSitSrvArr = (List<String>) session.getAttribute("sitSrvArrList");
						sitSrvArr= (String[]) newSitSrvArr.toArray(sitSrvArr);
					} 
				}
				
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
				Set<String> offTimeSet = new HashSet<String>();
				String offTimeS = req.getParameter("offTimeS");
				String offTimeE = req.getParameter("offTimeE");
				
				if (offTimeS.length() > 0 && offTimeE.length() > 0) {
					// �p�G�u���@�Ӯɶ��q
					if (offTimeS.equals(offTimeE)) {
						offTimeSet.add(offTimeS);
					}
					// �p�G���h�Ӯɶ��q�A���ন�Ʀr�P�_
					Integer offTimeSint = Integer.valueOf(offTimeS);
					Integer offTimeEint = Integer.valueOf(offTimeE);
					// �u�n�ɶ��q�Ʀr���@�˴N�~�����
					while(!offTimeSint.equals(offTimeEint)) {
						// ���[�J�̫�@�Ӯɬq(���ư���A�����|���ƥ[�J)
						offTimeSet.add(offTimeE);
						// �q�Ĥ@�Ӯɬq����[�J
						String newOffTime = "0" + offTimeSint.toString();
						// �p�G�J��s��
						if ("00".equals(newOffTime)) {
							newOffTime = "0000";
						}
						newOffTime.substring(-4);
						offTimeSet.add(newOffTime);
						// �[���Ĥ@�Ӯɬq����[�@�p��
						offTimeSint += 100;
						// �p�G����]�ɬq�A�ɬq
						if (offTimeSint == 2500) {
							offTimeSint = 0;
						}
					}
				}
				
				/* 4-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// �s�W��-���A0 ; �s�W�q��-���A1

				
				System.out.println("�ǳƷs�W�H�U��ơGsitSrvNo�@" + sitSrvArr.length + "��; offDay�]�t" + offDayList.toString() + "; offTime�]�t" + offTimeSet.toString() + "; offDayTyp�O "+offDayTyp +"�����");
				// ���ˬd�s�W����ƬO�_����
				SitOffDayService sodSvc = new SitOffDayService();
				for (String sitSrvNo : sitSrvArr) {
					List<SitOffDayVO> dblist =  sodSvc.getByFK(sitSrvNo);
					for (SitOffDayVO dbSodVO : dblist) {
						// ��������
						if (offDayList.contains(dbSodVO.getOffDay().toString())) {
							// �i�B�j�s�i�h���O���(�S��offTime)
							if (offTimeSet.size() < 1) {
								errorMsgs.add("�H�U������|�A���ˬd�𰲸�ƫ᭫�s�]�w�G"+ dbSodVO.getOffDay().toString());
							}
							// �i�B�j�s�i�h���O�ɬq(��offTime)
							if (offTimeSet.contains(dbSodVO.getOffTime())) {
								errorMsgs.add(dbSodVO.getOffDay().toString()+" "+ dbSodVO.getOffTime()+ "�ɶ����|�A���ˬd�𰲸�ƫ᭫�s�]�w");
							}
						}
					}
				}
				// �^�ǿ��~��T(����n�令sweetalert)
				if (! errorMsgs.isEmpty()) {
					System.out.println("�ǳƷs�W��ƻP��Ʈw��ƭ��ơA�G������W�[�ʧ@�A�o�e���~�q��");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("�ˬd��Ƨ����A�T�w�S������!");
				
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				
				String groupID = "G" + (gpID++);
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						// �P�_offTime
						if (offTimeSet.size() < 1) {
							String offTime = null;
							sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
						} else {
							for (String offTime : offTimeSet) {
								sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
							}
						}
					}
				}
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �p�G�O�s�W�𰲡A�^��showAllSrvDay.jsp
				if (offDayTyp==0) {
					// �^��쥻���A�Ȥ��
					req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
					String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
					RequestDispatcher sucessView = req.getRequestDispatcher(url);
					sucessView.forward(req, res);
				}
				// �p�G�O�s�W�q��Ado nothing
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�s�W���ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------�Ӧ� showAllSrvDay.jsp ���ШD - �ק��(���R���A�s�W) -------------------------------------- */
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			Boolean updateOK = false;
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
				Set<String> offTimeSet = new HashSet<String>();
				String offTimeS = req.getParameter("offTimeS");
				String offTimeE = req.getParameter("offTimeE");
				
				if (offTimeS.length() > 0 && offTimeE.length() > 0) {
					// �p�G�u���@�Ӯɶ��q
					if (offTimeS.equals(offTimeE)) {
						offTimeSet.add(offTimeS);
					}
					// �p�G���h�Ӯɶ��q�A���ন�Ʀr�P�_
					Integer offTimeSint = Integer.valueOf(offTimeS);
					Integer offTimeEint = Integer.valueOf(offTimeE);
					// �u�n�ɶ��q�Ʀr���@�˴N�~�����
					while(!offTimeSint.equals(offTimeEint)) {
						// ���[�J�̫�@�Ӯɬq(���ư���A�����|���ƥ[�J)
						offTimeSet.add(offTimeE);
						// �q�Ĥ@�Ӯɬq����[�J
						String newOffTime = "0" + offTimeSint.toString();
						// �p�G�J��s��
						if ("00".equals(newOffTime)) {
							newOffTime = "0000";
						}
						newOffTime.substring(-4);
						offTimeSet.add(newOffTime);
						// �[���Ĥ@�Ӯɬq����[�@�p��
						offTimeSint += 100;
						// �p�G����]�ɬq�A�ɬq
						if (offTimeSint == 2500) {
							offTimeSint = 0;
						}
					}
				}
				
				/* 5-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// �s�W��-���A0 ; �s�W�q��-���A1
				
				System.out.println("�ǳƭק�(�s�W)�H�U��ơGsitSrvNo�@" + sitSrvArr.length + "��; offDay�]�t" + offDayList.toString() + "; offTime�]�t" + offTimeSet.toString() + "; offDayTyp�O "+offDayTyp + "; groupId= "+groupId+"�����");
				// ���ˬd�s�W����ƬO�_����
				SitOffDayService sodSvc = new SitOffDayService();
				for (String sitSrvNo : sitSrvArr) {
					List<SitOffDayVO> dblist =  sodSvc.getByFK(sitSrvNo);
					for (SitOffDayVO dbSodVO : dblist) {
						// ���ˬd�ۤv
						if (!dbSodVO.getGroupID().equals(groupId)) {
							// ��������
							if (offDayList.contains(dbSodVO.getOffDay().toString())) {
								// �i�B�j�s�i�h���O���(�S��offTime)
								if (offTimeSet.size() < 1) {
									errorMsgs.add("�H�U������|�A���ˬd�𰲸�ƫ᭫�s�]�w�G"+ dbSodVO.getOffDay().toString());
								}
								// �i�B�j�s�i�h���O�ɬq(��offTime)
								if (offTimeSet.contains(dbSodVO.getOffTime())) {
									errorMsgs.add(dbSodVO.getOffDay().toString()+" "+ dbSodVO.getOffTime()+ "�ɶ����|�A���ˬd�𰲸�ƫ᭫�s�]�w");
								}
							}
						}
					}
				}
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					System.out.println("�ǳƭק��ƻP��Ʈw��ƭ��ơA�G������ק�ʧ@�A�o�e���~�q��");
					out.write("repeat");
//					RequestDispatcher failureView = req.getRequestDispatcher("showAllSrvDay.jsp");
//					failureView.forward(req, res);
					return;
				}
				System.out.println("�ˬd��Ƨ����A�T�w�S������!");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				sodSvc.del(groupId);
				
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						// �P�_offTime
						if (offTimeSet.size() < 1) {
							String offTime = null;
							updateOK = sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupId);
						} else {
							for (String offTime : offTimeSet) {
								updateOK = sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupId);
							}
						}
					}
				}
				
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �^��쥻���A�Ȥ��
//				req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
//				
//				String url = "showAllSrvDay.jsp";
//				RequestDispatcher sucessView = req.getRequestDispatcher(url);
//				sucessView.forward(req, res);
				if (updateOK) {
					out.write(groupId);
				} else {
					out.write("error");
				}
				
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDayn.jsp");
				failureView.forward(req, res);
			}
		}

/* -------------------------------�Ӧ� showAllSrvDay.jsp / �iorder�j ���ШD - �R��(�����R���B�ק�R��)��------------------------------- */
		if ("del".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-offDayNo */
				String groupId = req.getParameter("groupId");
				
				/* 2-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("�R�� gpID="+groupId+"����ơA�A�^�� sitSrvNo = "+sitSrvNo+"�����");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
//				Boolean delOk = 
				sodSvc.del(groupId);
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				// �^��쥻���A�Ȥ��
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				
				String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�R�����ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------�Ӧ� showOneSrvDay.jsp ���ШD - ���o���i�w���ɶ������--------------------------------------- */
		if("getOneSitSrvOffDay".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
	
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("�ǳƬd��sitSrvNo = " + sitSrvNo + "�����i�A�ȸ��");
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> sitOffDaylist = new ArrayList<SitOffDayVO>();
				sitOffDaylist = sodSvc.getByFK(sitSrvNo);
				System.out.println("�@���X" + sitOffDaylist.size() + "��sitOffDay���");
		
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				JSONArray sodList = new JSONArray();
				
				for (SitOffDayVO sodVO : sitOffDaylist) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("offDay", sodVO.getOffDay());
					jsonObj.put("offTime", sodVO.getOffTime());
					sodList.put(jsonObj);
				}
				System.out.println("�g�XsodList=" + sodList);
				out.print(sodList);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("showOneSrvDay.jsp");
//				failureView.forward(req, res);
				out.write("error�G" + e.getMessage());
			}
		}
	}
}
