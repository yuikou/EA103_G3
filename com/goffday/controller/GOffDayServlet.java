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

//新增一筆訂單時觸發
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String grmno = req.getParameter("grmno");

				String goffday = req.getParameter("goffday");
				if (goffday.trim().length() == 0 || goffday == null) {
					errorMsgs.add("不可服務日期不得為空");
				}

				String gofftime = req.getParameter("gofftime");
				if (gofftime.trim().length() == 0 || gofftime == null) {
					errorMsgs.add("不可服務時間不得為空");
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

//美容業者刪除不可服務日期 or 訂單取消時觸發
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
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
		}

//獲得美容師已休假or已被訂購日期  --ajax--
		if ("oneGrmOff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 取得已選擇的美容師編號
				String groomerNo = req.getParameter("grmno");

				// 取得美容師所有off的日子
				godSvc = new GodService();
				List<GODayVO> oneGrm = godSvc.getOneGod(groomerNo);
				TreeSet<String> off = new TreeSet<String>();
				// 取得指定美容師的所有off day, 並存入List
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
				errorMsgs.add("取得資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			}
		}
//處理美容師畫休 -- ajax--
		if ("grmSetOff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 取得已選擇的美容師編號
				String groomerNo = req.getParameter("grmno");

				String grmSetOff = req.getParameter("offdate");
				System.out.println(grmSetOff);
				// 獲得所屬salon營業時間
				String st = saSvc.getonesalon(salno).getSalSTime();
				Integer stInt = new Integer(st);;
				String et = saSvc.getonesalon(salno).getSalETime();
				Integer etInt = new Integer(et);

				System.out.println("營業時間為" + st + " " + et);
				System.out.println("要畫休的時間" + stInt + "~" + etInt);

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
				errorMsgs.add("新增資料失敗:" + e.getMessage());
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
		
//這裡還沒寫完		
//處理back-end美容師不可服務日期list all
		if ("listGoff".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			try {
//System.out.println("今天是"+today);
				List<GrmVO> allGrm = grmSvc.getGrm(salno);
				TreeMap<String, Set> myOff = new TreeMap<String, Set>(); // 美容師編號:休假日
				for (int i = 0; i < allGrm.size(); i++) {
					TreeSet<String> days = new TreeSet<String>(); // 放取出的日子的集合

					String oneGno = allGrm.get(i).getGroomerNo();
					List<GODayVO> off = godSvc.getOneGod(oneGno); // 拿到指定美容店的指定美容師的休假VO
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
				errorMsgs.add("取得資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/grmOff/listGOffDay.jsp");
				failureView.forward(req, res);
			} finally {
				out.flush();
				out.close();
			}

		}
//上面的還沒寫完
		
//處理使用者選擇服務日, 必須寫出該日子的可選擇時段 --ajax--
System.out.println(action);
		if ("felistGoff".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				// 確認是否正確取得美容師
				String myGrm = ((GrmVO) session.getAttribute("myGrm")).getGroomerNo();
				// 解析所選日期
				String myDate = req.getParameter("myDate");
				System.out.println("選擇的美容店是:" + salno + "   美容師是:" + myGrm + "選擇的日子是" + myDate);
				// 獲得該美容店營業時間
				List<String> openTime = new ArrayList<String>();// 營業時間內所有的時間
				List<String> okTime = new ArrayList<String>();// 營業時間內可以選的

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
				// 取得該天的不可服務時間
				List<GODayVO> offVO = godSvc.getOneGod(myGrm);
				List<String> offList = new ArrayList<String>();

				for (GODayVO i : offVO) {
					if (myDate.equals(i.getOffDay())) { // 選的日子有比對到
						okTime = openTime;
						offList.add(i.getOffTime()); // 創一個list來裝該天的不可服務時間
						for (int ng = 0; ng < okTime.size(); ng++) { // 從okTime裡面剃除不可服務時間
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
				errorMsgs.add("取得資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grmOff/selectDayjsp");
				failureView.forward(req, res);
			} finally {
				out.flush();
				out.close();
			}
		}
	//處理選擇日期
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
				//設定完成, 轉交到訂單頁面
				String url = "";
				RequestDispatcher toOrder = req.getRequestDispatcher(url);
				toOrder.forward(req, res);
			
			} catch (Exception e) {
				errorMsgs.add("取得資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/grmOff/selectDayjsp");
				failureView.forward(req, res);
			}
		}

	}

}
