package com.sitSrv.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.sitLic.model.SitLicService;
import com.sitLic.model.SitLicVO;
import com.sitOffDay.model.SitOffDayService;
import com.sitSrv.model.SitSrvService;
import com.sitSrv.model.SitSrvVO;

@MultipartConfig
public class SitSrvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
/* 來自 addNewSitSrv.jsp 的請求  - 新增托養服務 */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvName */
				String[] sitSrvNameArr = req.getParameterValues("sitSrvName");
				String sitSrvName = null;
				for (String str : sitSrvNameArr) {
					if (str.trim().length()>0) {
						sitSrvName = str.trim();
					}
				}
				if (sitSrvName == null || sitSrvName.length() == 0) {
					errorMsgs.add("請輸入服務名稱");
				}
				System.out.println("47.sitSrvName = "+sitSrvName);
				
				/* 2-sitSrvCode */
				String sitSrvCode = req.getParameter("sitSrvCode");
				System.out.println("51.sitSrvCode = "+sitSrvCode);
				
				/* 3-sitNo */
				String sitNo = (String) session.getAttribute("sitNo");
				System.out.println("55.sitNo = "+sitNo);
				
				/* 4-srvFee */
				String srvFeeStr = req.getParameter("srvFee");
				if (srvFeeStr == null || srvFeeStr.trim().length() == 0) {
					errorMsgs.add("請輸入服務費率");
				}
				Integer srvFee = null;
				try {
					srvFee = Integer.valueOf(srvFeeStr);
				} catch (NumberFormatException ne) {
					errorMsgs.add("服務費率請輸入數字");
				}
				System.out.println("73.srvFee = "+srvFee);
				
				/* 5-srvInfo */
				String srvInfo = req.getParameter("srvInfo");
				System.out.println("72.srvInfo = "+srvInfo);
				
				/* 6-srvArea */
				String srvAreaStr = req.getParameter("srvArea");
				Integer srvArea = null;
				if (srvAreaStr != null) {
					srvArea = Integer.valueOf(srvAreaStr);
				}
				System.out.println("80.srvArea = "+srvArea);
				
				/* 7-acpPetNum */
				String acpPetNumStr = req.getParameter("acpPetNum");
				Integer acpPetNum = Integer.valueOf(acpPetNumStr);
				System.out.println("85.acpPetNum = "+acpPetNum);
				
				/* 8-acpPetTyp */
				String acpPetTypPart0 = req.getParameter("acpPetTypPart0");
				String acpPetTypPart1 = req.getParameter("acpPetTypPart1");
				String acpPetTypPart2 = req.getParameter("acpPetTypPart2");
				
				
				if (acpPetTypPart0 == null && acpPetTypPart1 == null) {
					errorMsgs.add("請點選接受的寵物類型");
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 == null) {
					errorMsgs.add("請點選可接受狗狗的最大體型");
				}
				Integer acpPetTyp = null;
				if (acpPetTypPart0 != null) {
					if (acpPetTypPart1 == null) {
						acpPetTyp = 0;
					} else {
						switch(acpPetTypPart2) {
						case "small":
							acpPetTyp = 1;
							break;
						case "medium":
							acpPetTyp = 2;
							break;
						case "large":
							acpPetTyp = 3;
							break;
						case "xlarge":
							acpPetTyp = 4;
						}
					}
				} else if (acpPetTypPart2 != null){
					switch(acpPetTypPart2) {
					case "small":
						acpPetTyp = 5;
						break;
					case "medium":
						acpPetTyp = 6;
						break;
					case "large":
						acpPetTyp = 7;
						break;
					case "xlarge":
						acpPetTyp = 8;
					}
				}
				System.out.println("133.acpPetTyp = "+acpPetTyp);
				
				/* 9-careLevel */
				String careLevelStr = req.getParameter("careLevel");
				Integer careLevel = null;
				if (careLevelStr != null) {
					careLevel = Integer.valueOf(careLevelStr);
				}
				System.out.println("141.careLevel = "+careLevel);
				
				/* 10-stayLoc */
				String stayLocStr = req.getParameter("stayLoc");
				Integer stayLoc = null;
				if (stayLocStr != null) {
					stayLoc = Integer.valueOf(stayLocStr);
				}
				System.out.println("149.stayLoc = "+stayLoc);
				
				/* 11-overnightLoc */
				String overnightLocStr = req.getParameter("overnightLoc");
				Integer overnightLoc = null;
				if (overnightLocStr != null) {
					overnightLoc = Integer.valueOf(overnightLocStr);
				}
				System.out.println("157.overnightLoc = "+overnightLoc);
				
				/* 12-smkFree */
				String smkFreeStr = req.getParameter("smkFree");
				Integer smkFree = null;
				if (smkFreeStr != null) {
					smkFree = Integer.valueOf(smkFreeStr);
				}
				System.out.println("165.smkFree = "+smkFree);
				
				/* 13-hasChild */
				String hasChildStr = req.getParameter("hasChild");
				Integer hasChild = null;
				if (hasChildStr != null) {
					hasChild = Integer.valueOf(hasChildStr);
				}
				System.out.println("173.hasChild = "+hasChild);
				
				/* 14-srvTime */
				String srvTimeHStr = req.getParameter("srvTimeH");
				String srvTimeMStr = req.getParameter("srvTimeM");
				
				if (Integer.valueOf(srvTimeHStr)<0 || Integer.valueOf(srvTimeHStr) >8) {
					errorMsgs.add("服務時間(小時)請輸入0~8的數字");
				}
				if (! "0".equals(srvTimeMStr) && ! "30".equals(srvTimeMStr)) {
					errorMsgs.add("服務時間(分鐘)只有0分或是30分喔");
				}
				String srvTime = srvTimeHStr + ("30".equals(srvTimeMStr)? "50": "00");
				System.out.println("186.srvTime = "+srvTime);
				
				/* 15-Eqpt */
				String eqptStr = req.getParameter("eqpt");
				Integer eqpt = null;
				if (eqptStr != null) {
					eqpt = Integer.valueOf(eqptStr);
				}
				System.out.println("199.eqpt = "+eqpt);
				
				/* 16-addBathing */
				String addBathingStr = req.getParameter("addBathing");
				Integer addBathing = null;
				if (addBathingStr == null) {
					addBathing = 0;
				} else {
					addBathing = Integer.valueOf(addBathingStr);
				}
				System.out.println("209.addBathing = "+addBathing);
				
				String addBathingFeeStr = req.getParameter("addBathingFee");
				Integer addBathingFee = null;
				if (addBathing == 1) {
					try {
						addBathingFee = Integer.valueOf(addBathingFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("加價服務價格請輸入數字");
					}
					System.out.println("219.addBathingFee = "+addBathingFee);
				}
				
				/* 17-addPickup */
				String addPickupStr = req.getParameter("addPickup");
				Integer addPickup = null;
				if (addPickupStr == null) {
					addPickup = 0;
				} else {
					addPickup = Integer.valueOf(addPickupStr);
				}
				System.out.println("230.addPickup = "+addPickup);
				
				String addPickupFeeStr = req.getParameter("addPickupFee");
				Integer addPickupFee = null;
				if (addPickup == 1) {
					try {
						addPickupFee = Integer.valueOf(addPickupFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("加價服務價格請輸入數字");
					}
					System.out.println("240.addPickupFee = "+addPickupFee);
				}
				
				SitSrvVO ssVO = new SitSrvVO();
				ssVO.setSitSrvName(sitSrvName);
				ssVO.setSitSrvCode(sitSrvCode);
				ssVO.setSrvFee(srvFee);
				ssVO.setSrvInfo(srvInfo);
				ssVO.setSrvArea(srvArea);
				ssVO.setAcpPetNum(acpPetNum);
				ssVO.setAcpPetTyp(acpPetTyp);
				ssVO.setCareLevel(careLevel);
				ssVO.setStayLoc(stayLoc);
				ssVO.setOvernightLoc(overnightLoc);
				ssVO.setSmkFree(smkFree);
				ssVO.setHasChild(hasChild);
				ssVO.setSrvTime(srvTime);
				ssVO.setEqpt(eqpt);
				ssVO.setAddBathing(addBathing);
				ssVO.setAddPickup(addPickup);
				
				/*************************** 證照專區 ***************************/
				
				/* 1-sitNo */
					
				/* 2-licName */
				String licName = req.getParameter("licName");
					
					
				/* 3-licEXP */
				Date licEXP = null;
				try {
					String licEXPStr = req.getParameter("licEXP");
					licEXP = Date.valueOf(licEXPStr.trim());
						
				} catch (IllegalArgumentException e) {
					licEXP = null;
				}
					
				/* 4-licStatus */
				Integer licStatus = 0;// 新增時預設狀態0-待審核
					
				/* 5-licPic */
				Part p = req.getPart("licPic");
					
				InputStream is = p.getInputStream();
				byte[] licPic = new byte[is.available()];
				is.read(licPic);
				is.close();
				System.out.println("293.licPic.length = "+ licPic.length);
				if ("Boarding".equals(sitSrvCode) && licPic.length < 1) {
					errorMsgs.add("請上傳證照圖檔");
				}
				
				/*************************** 證照專區 ***************************/
				
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					req.setAttribute("ssVO", ssVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("addNewSitSrv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				
				if (licPic.length > 0) {
					SitLicService slSvc = new SitLicService();
					SitLicVO sitLic = slSvc.addFromSitSrv(sitNo, licName, licPic, licEXP, licStatus);
					ssSvc.addWithSitLic(sitSrvName, sitSrvCode, sitNo, srvFee, srvInfo, srvArea, acpPetNum, acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, addBathing, addPickup, addBathingFee, addPickupFee, sitLic);
				} else {
					ssSvc.add(sitSrvName, sitSrvCode, sitNo, srvFee, srvInfo, srvArea, acpPetNum, acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, addBathing, addPickup, addBathing, addPickup);
				}
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher sucessView = req.getRequestDispatcher("addNewSitSrv.jsp");
				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("新增失敗： "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("addNewSitSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* 來自 addNewSitSrv.jsp 的請求  - 新增托養服務 */
		if ("search".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvCode */
				String sitSrvCode = req.getParameter("sitSrvCode");
				System.out.println("345.sitSrvCode = "+sitSrvCode);
				
				/* 2-acpPetTyp */
				String acpPetTypPart0 = req.getParameter("acpPetTypPart0");
				String acpPetTypPart1 = req.getParameter("acpPetTypPart1");
				String acpPetTypPart2 = req.getParameter("acpPetTypPart2");
				
				
				if (acpPetTypPart0 == null && acpPetTypPart1 == null) {
					errorMsgs.add("請點選我的寵物類型");
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 == null) {
					errorMsgs.add("請點選我的狗狗體型");
				}
				Set<Integer> acpPetTypSet = new HashSet<Integer>();
				if (acpPetTypPart0 != null) {
					Integer[] catArr = {0,1,2,3,4};
					for (Integer cat : catArr) {
						acpPetTypSet.add(cat);
					}
				}
				
				if (acpPetTypPart1 != null) {
					switch(acpPetTypPart2) {
					case "small":
						acpPetTypSet.add(1);
						acpPetTypSet.add(5);
						break;
					case "medium":
						acpPetTypSet.add(2);
						acpPetTypSet.add(6);
						break;
					case "large":
						acpPetTypSet.add(3);
						acpPetTypSet.add(7);
						break;
					case "xlarge":
						acpPetTypSet.add(4);
						acpPetTypSet.add(8);
					}
				}
				
				System.out.println("387.acpPetTypSet = "+ acpPetTypSet);
				Object[] acpPetTypArr = acpPetTypSet.toArray();
				
				/* 3-nearAddr */
				String nearAddr = req.getParameter("nearAddr");
				System.out.println("392.nearAddr = "+nearAddr);
				
				/* 4-dateFrom & 5-dateTo */
				String dateFrom = req.getParameter("dateFrom");
				String dateTo = req.getParameter("dateTo");
				
				try {
					Date.valueOf(dateFrom.trim());
					Date.valueOf(dateTo.trim());
					
				} catch (IllegalArgumentException e) {
					if (dateFrom.isEmpty()) {
						dateFrom = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("請選擇服務日期(起)");
					} else 	if (dateTo.isEmpty()) {
						dateTo = (new Date(System.currentTimeMillis())).toString();
						errorMsgs.add("請選擇服務日期(訖)");
					} else {
						errorMsgs.add("服務日期格式錯誤");
					}
				}
				System.out.println("413.dateFrom = "+dateFrom);
				System.out.println("414.dateTo = "+dateTo);
				
				/* 6-appendSQL */
				String appendSQL = "";
				
				// 回傳錯誤資訊
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("searchSitSrv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				SitOffDayService sodSvc = new SitOffDayService();
				
				List<SitSrvVO> sitSrvVOlist = new ArrayList<SitSrvVO>();
				Set<String> sitSrvNoSet = new HashSet<String>();
				
				// 先查符合的托養類型與寵物型態的服務VO
				sitSrvVOlist = ssSvc.choose_SitSrv(sitSrvCode, acpPetTypArr, appendSQL);
				
				
				// 再查此服務類型有休假的保母服務編號
				sitSrvNoSet = sodSvc.getSitByDate(sitSrvCode, dateFrom, dateTo);
				
				// 如果服務VO的服務編號有在休假的Set上，就移除這一個VO
				for (int i = 0; i < sitSrvVOlist.size(); i++) {
					SitSrvVO ssVO = sitSrvVOlist.get(i);
					if (sitSrvNoSet.contains(ssVO.getSitSrvNo())) {
						sitSrvVOlist.remove(i);
					}
				}
				System.out.println("447.sitSrvVOlist = " + sitSrvVOlist);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//				req.setAttribute("sitSrvVOlist", sitSrvVOlist);
//				RequestDispatcher sucessView = req.getRequestDispatcher("showSitSrv.jsp");
//				sucessView.forward(req, res);
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("查詢失敗： "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("searchSitSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* 來自 showOneSitAllSrv.jsp 的請求  - 暫停托養服務 */
		if ("pauseSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("SitSrvServlet_468.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 1, 0);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error：" + e.getMessage());
			}
		}
/* 來自 showOneSitAllSrv.jsp 的請求  - 重啟托養服務 */
		if ("rebootSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("SitSrvServlet_495.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 0, 0);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error：" + e.getMessage());
			}
		}
		
/* 來自 showOneSitAllSrv.jsp 的請求  - 刪除托養服務 */
		if ("delSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("SitSrvServlet_523.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 1, 1);
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error：" + e.getMessage());
			}
		}
		
/* 來自 showOneSitAllSrv.jsp 的請求  - 修改托養服務 */
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				/* licNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
			
				/*************************** 2.開始新增資料 ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				SitSrvVO ssVO = ssSvc.get_OneSit_OneSrv(sitSrvNo);
	
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("ssVO", ssVO);
				RequestDispatcher sucessView = req.getRequestDispatcher("updateSitSrv.jsp");
				sucessView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
