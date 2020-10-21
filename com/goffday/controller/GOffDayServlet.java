package com.goffday.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goffday.model.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grm.model.*;
import com.salon.model.*;

@WebServlet("/GOffDayServlet")
public class GOffDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GOffDayServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String salno = (session.getAttribute("salno")).toString();
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		GrmService grmSvc = new GrmService();
		SalonService saSvc = new SalonService();
		GodService godSvc = new GodService();

//�s�W�@���q���Ĳ�o
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String grmno = req.getParameter("grmno");

				String goffday = req.getParameter("goffday");
				if (goffday.trim().length() == 0 || goffday == null) {
					errorMsgs.add("���i�A�Ȥ�����o����");
				}

				String gofftime = req.getParameter("gofftime");
				if (gofftime.trim().length() == 0 || gofftime == null) {
					errorMsgs.add("���i�A�Ȯɶ����o����");
				}

				Integer goffdaytype = new Integer(req.getParameter("goffdaytype").trim());

				GODayVO god = new GODayVO();
				god.setGroomerNo(grmno);
				god.setOffDay(goffday);
				god.setOffTime(gofftime);
				god.setOffDayType(goffdaytype);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("god", god);
					RequestDispatcher failure = req.getRequestDispatcher("/back-end/grmOff/addGoffDay.jsp");
					failure.forward(req, res);
					return;
				}

				godSvc = new GodService();
				godSvc.addGOD(grmno, goffday, gofftime, goffdaytype);

				String url = "/back-end/grmOff/listGOffDay.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
		}

//���e�~�̧R�����i�A�Ȥ�� or �q�������Ĳ�o
		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String offno = req.getParameter("offno").trim();
				godSvc = new GodService();
				godSvc.deleteGOD(offno);

				String url = "/back-end/grmOff/listGOffDay.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
		}

//��o���e�v�w��or�w�Q�q�ʤ��  --ajax--
		if ("oneGrmOff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// ���o�w��ܪ����e�v�s��
				String groomerNo = req.getParameter("grmno");

				// ���o���e�v�Ҧ�off����l
				godSvc = new GodService();
				List<GODayVO> oneGrm = godSvc.getOneGod(groomerNo);
				TreeSet<String> off = new TreeSet<String>();
				// ���o���w���e�v���Ҧ�off day, �æs�JList
				for (GODayVO r : oneGrm) {
					off.add(r.getOffDay());
				}
				Gson gson = new Gson();
				String jsonOff = gson.toJson(off);
//				System.out.println(jsonOff);
				out.write(jsonOff);
				out.flush();
				out.close();
			} catch (Exception e) {
				errorMsgs.add("���o��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
		}
//�B�z���e�v�e�� -- ajax--
		if ("grmSetOff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// ���o�w��ܪ����e�v�s��
				String groomerNo = req.getParameter("grmno");

				String grmSetOff = req.getParameter("offdate");
				System.out.println(grmSetOff);
				// ��o����salon��~�ɶ�
				String st = saSvc.getonesalon(salno).getSalSTime();
				Integer stInt = new Integer(st);;
				String et = saSvc.getonesalon(salno).getSalETime();
				Integer etInt = new Integer(et);

				System.out.println("��~�ɶ���" + st + " " + et);
				System.out.println("�n�e�𪺮ɶ�" + stInt + "~" + etInt);

				GODayVO god = new GODayVO();

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("god", god);
					RequestDispatcher failure = req.getRequestDispatcher("/back-end/grmOff/addGoffDay.jsp");
					failure.forward(req, res);
					return;
				}

				for (Integer i = stInt; i <= etInt; i += 100) {
					String str1 = i.toString().substring(0, 2);
					String str2 = i.toString().substring(2);
					String offTime = str1 + ":" + str2;
					godSvc.addGOD(groomerNo, grmSetOff, offTime, 0);
				}

			} catch (Exception e) {
				errorMsgs.add("�s�W��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
			if (errorMsgs.isEmpty()) {
				System.out.print("ok");
				out.write('1');

				out.flush();
				out.close();
			}

		}
		
//�o���٨S�g��		
//�B�zback-end���e�v���i�A�Ȥ��list all
		if ("listGoff".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			try {
//System.out.println("���ѬO"+today);
				List<GrmVO> allGrm = grmSvc.getGrm(salno);
				TreeMap<String, Set> myOff = new TreeMap<String, Set>(); // ���e�v�s��:�𰲤�
				for (int i = 0; i < allGrm.size(); i++) {
					TreeSet<String> days = new TreeSet<String>(); // ����X����l�����X

					String oneGno = allGrm.get(i).getGroomerNo();
					List<GODayVO> off = godSvc.getOneGod(oneGno); // ������w���e�������w���e�v����VO
					for (GODayVO godayVO : off) {
						Date jday = sdf.parse(godayVO.getOffDay());
						if (jday.after(today)) {
							days.add(godayVO.getOffDay());
							String offday = godayVO.getOffDay().trim();
							days.add(offday);
						}
					}
					myOff.put(oneGno, days);
				}
				Gson gson = new Gson();
				String jsonOff = gson.toJson(myOff);
				// System.out.println(jsonOff);
				if (errorMsgs.isEmpty()) {
					out.write(jsonOff);
				}
			} catch (Exception e) {
				errorMsgs.add("���o��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			} finally {
				out.flush();
				out.close();
			}

		}
//�W�����٨S�g��
		
//�B�z�ϥΪ̿�ܪA�Ȥ�, �����g�X�Ӥ�l���i��ܮɬq --ajax--
System.out.println(action);
		if ("felistGoff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// �T�{�O�_���T���o���e�v
				String myGrm = ((GrmVO) session.getAttribute("myGrm")).getGroomerNo();
				// �ѪR�ҿ���
				String myDate = req.getParameter("myDate");
				System.out.println("��ܪ����e���O:" + salno + "   ���e�v�O:" + myGrm + "��ܪ���l�O" + myDate);
				// ��o�Ӭ��e����~�ɶ�
				List<String> openTime = new ArrayList<String>();// ��~�ɶ����Ҧ����ɶ�
				List<String> okTime = new ArrayList<String>();// ��~�ɶ����i�H�諸

				String st = saSvc.getonesalon(salno).getSalSTime();
				Integer stInt = new Integer(st);
				String et = saSvc.getonesalon(salno).getSalETime();
				Integer etInt = new Integer(et);
				for (Integer i = stInt; i < etInt; i += 100) {
					String str1 = i.toString().substring(0, 2);
					String str2 = i.toString().substring(2);
					String time = str1 + ":" + str2;
					openTime.add(time);
				}
				//okTime = openTime;
				System.out.println(st + "~" + et);
				// ���o�ӤѪ����i�A�Ȯɶ�
				List<GODayVO> offVO = godSvc.getOneGod(myGrm);
				List<String> offList = new ArrayList<String>();

				for (GODayVO i : offVO) {
					if (myDate.equals(i.getOffDay())) { // �諸��l������
						okTime = openTime;
						offList.add(i.getOffTime()); // �Ф@��list�Ӹ˸ӤѪ����i�A�Ȯɶ�
						for (int ng = 0; ng < okTime.size(); ng++) { // �qokTime�̭��c�����i�A�Ȯɶ�
							for (int j = 0; j < offList.size(); j++) {
								if ((okTime.get(ng)).equals(offList.get(j))) {
									okTime.remove(ng);
								}
							}
						}
					} else {
						okTime = openTime;
					}
				}
				if(errorMsgs.isEmpty()) {
					if(okTime.isEmpty()) {
						out.write('1');
					}else {
						Gson gson = new Gson();
						String jsonTimeList = gson.toJson(okTime);
						out.write(jsonTimeList);
					}
					
				}
			} catch (Exception e) {
				errorMsgs.add("���o��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grmOff/selectDayjsp");
				failureView.forward(req, res);
			} finally {
				out.flush();
				out.close();
			}
		}
	//�B�z��ܤ��
		if("toOrder".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String offDay = req.getParameter("offDay");
				String offTime = req.getParameter("offTime");
				String goffdaytype = req.getParameter("goffdaytype");
				
				session.setAttribute("offDay", offDay);
				session.setAttribute("offTime", offTime);
				session.setAttribute("goffdaytype", goffdaytype);
	System.out.print(offDay+offTime+"="+goffdaytype);
				//�]�w����, ����q�歶��
				String url = "";
				RequestDispatcher toOrder = req.getRequestDispatcher(url);
				toOrder.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("���o��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grmOff/selectDayjsp");
				failureView.forward(req, res);
			}
		}

	}

}
