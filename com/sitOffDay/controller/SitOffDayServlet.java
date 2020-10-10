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

/* --------------------------------------來自 listSitFollow.jsp - 取得一位保姆其中一種服務的時間-------------------------------------- */
		if ("getAll".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				
				list = sodSvc.getByFK(sitSrvNo);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 直接寫出json格式資料
				JSONArray sodList = new JSONArray();
				
				Date startDay = null,endDay = null;					// null
				String startTime = null, endTime = null;			// null
				
				for (int i = 0; i < list.size(); i++) {
					SitOffDayVO sodVO = list.get(i);
					SitOffDayVO sodVOtemp;
					// 如果只有一筆直接寫入陣列
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
					
					// 如果有多筆要繼續判斷
					} else {
						// 避免最後一筆無法比較
						if (i+1 < list.size()) {
							sodVOtemp = list.get(i+1); // 下一筆
						} else {
							sodVOtemp = list.get(0);
						}
						
						Date thisDay = sodVO.getOffDay(); 						/* 10/7 10/8 10/9  10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 10/18 */
						Date nextDay = sodVOtemp.getOffDay(); 					/* 10/8 10/9 10/10 10/12 10/12 10/12 10/15 10/17 10/17 10/18 10/18 10/7  */
						String thisTime = sodVO.getOffTime(); 					/* null null null  null  0800  0900  1000  null  0800  2400  1100  1200  */
						String nextTime = sodVOtemp.getOffTime(); 				/* null null null  0800	 0900  1000  null  0800  0900  0100  1200  null  */
						
						// 每組第一筆
						if (startDay == null) {
							startDay = thisDay;									// 10/7 10/8  --   --    10/12  ---------- 10/15 10/17 -----------------
							endDay = thisDay;									// 10/7 10/8  --   --    10/12	---------- 10/15 10/17 -----------------
							startTime = thisTime;								// null null  --   --    0800	---------- null  0800  -----------------
							endTime = thisTime;									// null null  --   --    0800	---------- null  0800  -----------------
						}
						// 同組才更改 endDay & endTime，且跳出此迴圈不寫入陣列
						if (sodVOtemp.getGroupID().equals(sodVO.getGroupID())) {
							endDay = nextDay;									// --  10/9  10/10  --	 10/12  10/12  --    --  10/17 10/18 10/18  --
							endTime = nextTime;									// --  null  null   --   0900   1000   --    --  0900  0100  1200   --
							continue;
							
						// 不同組才將已經更改好的資料寫入陣列
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
							startDay = null; // 寫入陣列後，清空startDay做為下一組開始的判斷
						}
					}
					
				}
				out.print(sodList);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
				failureView.forward(req, res);
			}
		}
		
/* ------------------------------來自 showAllSitDay.jsp / 【order】 的請求  - 新增(直接新增、修改新增)休假-------------------------------- */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs = ", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
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
						errorMsgs.add("請輸入休假日(起)");
					}
					if (offDayE.isEmpty()) {
						offDayE = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("請輸入休假日(訖)");
					}
				}
				
				/* 3-offTime */
				String offTime = null;
				
				/* 4-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// 新增休假-狀態0 ; 新增訂單-狀態1
				
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				String groupID = "G" + gpID;
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
					}
				}
				gpID++;
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 如果是新增休假，回到showAllSitDay.jsp
				if (offDayTyp==0) {
					// 回到原本的服務月曆
					req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
					String url = "showAllSitDay.jsp";
					RequestDispatcher sucessView = req.getRequestDispatcher(url);
					sucessView.forward(req, res);
				}
				// 如果是新增訂單，do nothing
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("新增失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------來自 showAllSitDay.jsp 的請求 - 修改休假(先刪除再新增) -------------------------------------- */
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
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
						errorMsgs.add("請輸入休假日(起)");
					}
					if (offDayE.isEmpty()) {
						offDayE = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("請輸入休假日(訖)");
					}
				}
				
				/* 4-offTime */
				String offTime = null;
				
				/* 5-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// 新增休假-狀態0 ; 新增訂單-狀態1
				
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				sodSvc.del(groupId);
				
				String groupID = "G" + (gpID++);
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						sodSvc.add(sitSrvNo, Date.valueOf(offDay), offTime, offDayTyp, groupID);
					}
				}
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 回到原本的服務月曆
				req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
				
				String url = "showAllSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDayn.jsp");
				failureView.forward(req, res);
			}
		}

/* -------------------------------來自 showAllSitDay.jsp / 【order】 的請求 - 刪除(直接刪除、修改刪除)休假------------------------------- */
		if ("del".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-offDayNo */
				String groupId = req.getParameter("groupId");
				
				/* 2-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				sodSvc.del(groupId);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 回到原本的服務月曆
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				
				String url = "showAllSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("刪除失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showAllSitDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------來自 保姆個人頁面個別服務項目 的請求 - 取得不可預約時間的資料--------------------------------------- */
		if("getOneSitSrv_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = (String) session.getAttribute("sitSrvNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				list = sodSvc.getByFK(sitSrvNo);
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("list", list);
				
				String url = "showOneSitDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showOneSitDay.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
