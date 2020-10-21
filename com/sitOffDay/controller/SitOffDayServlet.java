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

/* --------------------------------------來自 showOneSitAll【Srv】.jsp - 取得一位保姆其中一種服務的時間-------------------------------------- */
		if ("transfer".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				System.out.println("SitOffDayServlet_37.sitSrvNo = " + sitSrvNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				// 跳過
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("跳轉失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------來自 showAllSrvDay.jsp - 取得一位保姆其中一種服務的時間-------------------------------------- */
		if ("getAll".equals(action)) {
//			List<String> errorMsgs = req.getParameter("errorMsgs");
//			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* sitSrvNo */
				String sitSrvNo = (String) req.getParameter("sitSrvNo");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
				
				list = sodSvc.getByFK(sitSrvNo);
				System.out.println("getAll取出"+list.size()+"筆資料");
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 直接寫出json格式資料
				JSONArray sodList = new JSONArray();
				
				Date startDay = null,endDay = null;					// null
				String startTime = null, endTime = null;			// null
				
				for (int i = 0; i < list.size(); i++) {
					SitOffDayVO sodVO = list.get(i);
					SitOffDayVO sodVOtemp = null;
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
						// 每筆都跟下一筆做比較
						if (i+1 < list.size()) {
							sodVOtemp = list.get(i+1); // 下一筆
						// 當最後一筆，且最後一組為多筆資料時不繼續比較
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
						// 當最後一筆，且最後一組為1筆資料時
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
						
						// 每組第一筆
						if (startDay == null) {
							startDay = thisDay;									// 10/7 10/8  --   --    10/12  ---------- 10/15 10/17 ------------
							endDay = thisDay;									// 10/7 10/8  --   --    10/12	---------- 10/15 10/17 ------------
							startTime = thisTime;								// null null  --   --    0800	---------- null  0800  ------------
							endTime = thisTime;									// null null  --   --    0800	---------- null  0800  ------------
						}
						// 同組才更改 endDay & endTime，且跳出此迴圈不寫入陣列
						if (sodVOtemp.getGroupID().equals(sodVO.getGroupID())) {
							endDay = nextDay;									// --  10/9  10/10  --	 10/12  10/12  --    --  10/17 10/18 10/18
							endTime = nextTime;									// --  null  null   --   0900   1000   --    --  0900  0100  1200
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
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("listSitFollow.jsp");
//				failureView.forward(req, res);
				out.write("error: " + e.getMessage());
			}
		}
		
/* ------------------------------來自 showAllSrvDay.jsp休假-------------------------------- */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs = ", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
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
						errorMsgs.add("請輸入休假日(起)");
					}
					if (offDayE.isEmpty()) {
						offDayE = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("請輸入休假日(訖)");
					}
				}

				/* 3-offTime */
				Set<String> offTimeSet = new HashSet<String>();
				String offTimeS = req.getParameter("offTimeS");
				String offTimeE = req.getParameter("offTimeE");
				
				if (offTimeS.length() > 0 && offTimeE.length() > 0) {
					// 如果只有一個時間段
					if (offTimeS.equals(offTimeE)) {
						offTimeSet.add(offTimeS);
					}
					// 如果有多個時間段，先轉成數字判斷
					Integer offTimeSint = Integer.valueOf(offTimeS);
					Integer offTimeEint = Integer.valueOf(offTimeE);
					// 只要時間段數字不一樣就繼續執行
					while(!offTimeSint.equals(offTimeEint)) {
						// 先加入最後一個時段(重複執行，但不會重複加入)
						offTimeSet.add(offTimeE);
						// 從第一個時段陸續加入
						String newOffTime = "0" + offTimeSint.toString();
						// 如果遇到零時
						if ("00".equals(newOffTime)) {
							newOffTime = "0000";
						}
						newOffTime.substring(-4);
						offTimeSet.add(newOffTime);
						// 加完第一個時段往後加一小時
						offTimeSint += 100;
						// 如果有跨夜時段，時段
						if (offTimeSint == 2500) {
							offTimeSint = 0;
						}
					}
				}
				
				/* 4-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// 新增休假-狀態0 ; 新增訂單-狀態1

				
				System.out.println("準備新增以下資料：sitSrvNo共" + sitSrvArr.length + "筆; offDay包含" + offDayList.toString() + "; offTime包含" + offTimeSet.toString() + "; offDayTyp是 "+offDayTyp +"的資料");
				// 先檢查新增的資料是否重複
				SitOffDayService sodSvc = new SitOffDayService();
				for (String sitSrvNo : sitSrvArr) {
					List<SitOffDayVO> dblist =  sodSvc.getByFK(sitSrvNo);
					for (SitOffDayVO dbSodVO : dblist) {
						// 當日期重複
						if (offDayList.contains(dbSodVO.getOffDay().toString())) {
							// 【且】存進去的是整天(沒有offTime)
							if (offTimeSet.size() < 1) {
								errorMsgs.add("以下日期重疊，請檢查休假資料後重新設定："+ dbSodVO.getOffDay().toString());
							}
							// 【且】存進去的是時段(有offTime)
							if (offTimeSet.contains(dbSodVO.getOffTime())) {
								errorMsgs.add(dbSodVO.getOffDay().toString()+" "+ dbSodVO.getOffTime()+ "時間重疊，請檢查休假資料後重新設定");
							}
						}
					}
				}
				// 回傳錯誤資訊(之後要改成sweetalert)
				if (! errorMsgs.isEmpty()) {
					System.out.println("準備新增資料與資料庫資料重複，故不執行增加動作，發送錯誤通知");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("檢查資料完畢，確定沒有重複!");
				
				
				/*************************** 2.開始新增資料 ***************************************/
				
				String groupID = "G" + (gpID++);
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						// 判斷offTime
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
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 如果是新增休假，回到showAllSrvDay.jsp
				if (offDayTyp==0) {
					// 回到原本的服務月曆
					req.setAttribute("sitSrvNoRN", sitSrvArr[0]);
					String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
					RequestDispatcher sucessView = req.getRequestDispatcher(url);
					sucessView.forward(req, res);
				}
				// 如果是新增訂單，do nothing
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("新增失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------來自 showAllSrvDay.jsp 的請求 - 修改休假(先刪除再新增) -------------------------------------- */
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
			Boolean updateOK = false;
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
				Set<String> offTimeSet = new HashSet<String>();
				String offTimeS = req.getParameter("offTimeS");
				String offTimeE = req.getParameter("offTimeE");
				
				if (offTimeS.length() > 0 && offTimeE.length() > 0) {
					// 如果只有一個時間段
					if (offTimeS.equals(offTimeE)) {
						offTimeSet.add(offTimeS);
					}
					// 如果有多個時間段，先轉成數字判斷
					Integer offTimeSint = Integer.valueOf(offTimeS);
					Integer offTimeEint = Integer.valueOf(offTimeE);
					// 只要時間段數字不一樣就繼續執行
					while(!offTimeSint.equals(offTimeEint)) {
						// 先加入最後一個時段(重複執行，但不會重複加入)
						offTimeSet.add(offTimeE);
						// 從第一個時段陸續加入
						String newOffTime = "0" + offTimeSint.toString();
						// 如果遇到零時
						if ("00".equals(newOffTime)) {
							newOffTime = "0000";
						}
						newOffTime.substring(-4);
						offTimeSet.add(newOffTime);
						// 加完第一個時段往後加一小時
						offTimeSint += 100;
						// 如果有跨夜時段，時段
						if (offTimeSint == 2500) {
							offTimeSint = 0;
						}
					}
				}
				
				/* 5-offDayTyp */
				Integer offDayTyp = Integer.valueOf(req.getParameter("offDayTyp"));
				// 新增休假-狀態0 ; 新增訂單-狀態1
				
				System.out.println("準備修改(新增)以下資料：sitSrvNo共" + sitSrvArr.length + "筆; offDay包含" + offDayList.toString() + "; offTime包含" + offTimeSet.toString() + "; offDayTyp是 "+offDayTyp + "; groupId= "+groupId+"的資料");
				// 先檢查新增的資料是否重複
				SitOffDayService sodSvc = new SitOffDayService();
				for (String sitSrvNo : sitSrvArr) {
					List<SitOffDayVO> dblist =  sodSvc.getByFK(sitSrvNo);
					for (SitOffDayVO dbSodVO : dblist) {
						// 不檢查自己
						if (!dbSodVO.getGroupID().equals(groupId)) {
							// 當日期重複
							if (offDayList.contains(dbSodVO.getOffDay().toString())) {
								// 【且】存進去的是整天(沒有offTime)
								if (offTimeSet.size() < 1) {
									errorMsgs.add("以下日期重疊，請檢查休假資料後重新設定："+ dbSodVO.getOffDay().toString());
								}
								// 【且】存進去的是時段(有offTime)
								if (offTimeSet.contains(dbSodVO.getOffTime())) {
									errorMsgs.add(dbSodVO.getOffDay().toString()+" "+ dbSodVO.getOffTime()+ "時間重疊，請檢查休假資料後重新設定");
								}
							}
						}
					}
				}
				
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					System.out.println("準備修改資料與資料庫資料重複，故不執行修改動作，發送錯誤通知");
					out.write("repeat");
//					RequestDispatcher failureView = req.getRequestDispatcher("showAllSrvDay.jsp");
//					failureView.forward(req, res);
					return;
				}
				System.out.println("檢查資料完畢，確定沒有重複!");
				
				/*************************** 2.開始新增資料 ***************************************/
				sodSvc.del(groupId);
				
				for (String sitSrvNo : sitSrvArr) {
					for (String offDay : offDayList) {
						// 判斷offTime
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
				
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 回到原本的服務月曆
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
				
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDayn.jsp");
				failureView.forward(req, res);
			}
		}

/* -------------------------------來自 showAllSrvDay.jsp / 【order】 的請求 - 刪除(直接刪除、修改刪除)休假------------------------------- */
		if ("del".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-offDayNo */
				String groupId = req.getParameter("groupId");
				
				/* 2-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("刪除 gpID="+groupId+"的資料，再回到 sitSrvNo = "+sitSrvNo+"的月曆");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
//				Boolean delOk = 
				sodSvc.del(groupId);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				// 回到原本的服務月曆
				req.setAttribute("sitSrvNoRN", sitSrvNo);
				
				String url = "/front-end/sitOffDay/showAllSrvDay.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
			
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("刪除失敗： " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOffDay/showAllSrvDay.jsp");
				failureView.forward(req, res);
			}
		}
		
/* --------------------------------------來自 showOneSrvDay.jsp 的請求 - 取得不可預約時間的資料--------------------------------------- */
		if("getOneSitSrvOffDay".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			PrintWriter out = res.getWriter();
	
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("準備查詢sitSrvNo = " + sitSrvNo + "的不可服務資料");
				
				/*************************** 2.開始新增資料 ***************************************/
				SitOffDayService sodSvc = new SitOffDayService();
				List<SitOffDayVO> sitOffDaylist = new ArrayList<SitOffDayVO>();
				sitOffDaylist = sodSvc.getByFK(sitSrvNo);
				System.out.println("共取出" + sitOffDaylist.size() + "筆sitOffDay資料");
		
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				JSONArray sodList = new JSONArray();
				
				for (SitOffDayVO sodVO : sitOffDaylist) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("offDay", sodVO.getOffDay());
					jsonObj.put("offTime", sodVO.getOffTime());
					sodList.put(jsonObj);
				}
				System.out.println("寫出sodList=" + sodList);
				out.print(sodList);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("showOneSrvDay.jsp");
//				failureView.forward(req, res);
				out.write("error：" + e.getMessage());
			}
		}
	}
}
