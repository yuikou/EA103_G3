package com.sitSrv.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;

import com.member.model.MemDAO;
import com.member.model.MemVO;
import com.petSitter.model.PetSitterVO;
import com.sitLic.model.SitLicService;
import com.sitLic.model.SitLicVO;
import com.sitOffDay.model.SitOffDayService;
import com.sitSrv.model.SitSrvService;
import com.sitSrv.model.SitSrvVO;
import com.sitSrv.model.SitSrvVO2;

@WebServlet("/sitSrv/sitSrv.do")
@MultipartConfig
public class SitSrvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		/* --------------------------------------�Ӧ� showOneSitAllSrv.jsp - ���o�@��O�i�i�H�s�W���A��-------------------------------------- */
		if ("transfer".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* sitNo */
				PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO");
				String sitNo = petSitterVO.getSitNo();
				System.out.println("SitSrvServlet_51.sitNo = "+sitNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				List<SitSrvVO> sitSrvlist = ssSvc.get_OneSit_AllSrv(sitNo);
				List<String> sitSrvCodeList = new ArrayList<String>();
				for (SitSrvVO ssVO : sitSrvlist) {
					sitSrvCodeList.add(ssVO.getSitSrvCode());
				}
				System.out.println("SitSrvServlet_60.sitSrvCodeList = "+sitSrvCodeList);
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("sitSrvCodeList", sitSrvCodeList);
				RequestDispatcher sucessView = req.getRequestDispatcher("/front-end/sitSrv/addNewSitSrv.jsp");
				sucessView.forward(req, res);
			
			
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("���ॢ�ѡG " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
		/* --------------------------------------�Ӧ� addNewSitSrv.jsp ���ШD  - �s�W���i�A��-------------------------------------- */
		if ("add".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvName */
				String[] sitSrvNameArr = req.getParameterValues("sitSrvName");
				String sitSrvName = null;
				for (String str : sitSrvNameArr) {
					if (str.trim().length()>0) {
						sitSrvName = str.trim();
					}
				}
				if (sitSrvName == null || sitSrvName.length() == 0) {
					errorMsgs.add("�п�J�A�ȦW��");
				}
				System.out.println("SitSrvServlet_94.sitSrvName = "+sitSrvName);
				
				/* 2-sitSrvCode */
				String sitSrvCode = req.getParameter("sitSrvCode");
				System.out.println("SitSrvServlet_98.sitSrvCode = "+sitSrvCode);
				
				/* 3-sitNo */
				PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO");
				String sitNo = petSitterVO.getSitNo();
				System.out.println("SitSrvServlet_103.sitNo = "+sitNo);
				
				/* 4-srvFee */
				String srvFeeStr = req.getParameter("srvFee");
				if (srvFeeStr == null || srvFeeStr.trim().length() == 0) {
					errorMsgs.add("�п�J�A�ȶO�v");
				}
				Integer srvFee = null;
				try {
					srvFee = Integer.valueOf(srvFeeStr);
				} catch (NumberFormatException ne) {
					errorMsgs.add("�A�ȶO�v�п�J�Ʀr");
				}
				System.out.println("SitSrvServlet_116.srvFee = "+srvFee);
				
				/* 5-srvInfo */
				String srvInfo = req.getParameter("srvInfo");
				System.out.println("SitSrvServlet_120.srvInfo = "+srvInfo);
				
				/* 6-srvArea */
				String srvAreaStr = req.getParameter("srvArea");
				Integer srvArea = null;
				if (srvAreaStr != null) {
					srvArea = Integer.valueOf(srvAreaStr);
				}
				System.out.println("SitSrvServlet_128.srvArea = "+srvArea);
				
				/* 7-acpPetNum */
				String acpPetNumStr = req.getParameter("acpPetNum");
				Integer acpPetNum = Integer.valueOf(acpPetNumStr);
				System.out.println("SitSrvServlet_133.acpPetNum = "+acpPetNum);
				
				/* 8-acpPetTyp */
				String acpPetTypPart0 = req.getParameter("acpPetTypPart0");
				String acpPetTypPart1 = req.getParameter("acpPetTypPart1");
				String acpPetTypPart2 = req.getParameter("acpPetTypPart2");
				
				
				if (acpPetTypPart0 == null && acpPetTypPart1 == null) {
					errorMsgs.add("���I�ﱵ�����d������");
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 == null) {
					errorMsgs.add("���I��i�����������̤j�髬");
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
				System.out.println("SitSrvServlet_181.acpPetTyp = "+acpPetTyp);
				
				/* 9-careLevel */
				String careLevelStr = req.getParameter("careLevel");
				Integer careLevel = null;
				if (careLevelStr != null) {
					careLevel = Integer.valueOf(careLevelStr);
				}
				System.out.println("SitSrvServlet_189.careLevel = "+careLevel);
				
				/* 10-stayLoc */
				String stayLocStr = req.getParameter("stayLoc");
				Integer stayLoc = null;
				if (stayLocStr != null) {
					stayLoc = Integer.valueOf(stayLocStr);
				}
				System.out.println("SitSrvServlet_197.stayLoc = "+stayLoc);
				
				/* 11-overnightLoc */
				String overnightLocStr = req.getParameter("overnightLoc");
				Integer overnightLoc = null;
				if (overnightLocStr != null) {
					overnightLoc = Integer.valueOf(overnightLocStr);
				}
				System.out.println("SitSrvServlet_205.overnightLoc = "+overnightLoc);
				
				/* 12-smkFree */
				String smkFreeStr = req.getParameter("smkFree");
				Integer smkFree = null;
				if (smkFreeStr != null) {
					smkFree = Integer.valueOf(smkFreeStr);
				}
				System.out.println("SitSrvServlet_213.smkFree = "+smkFree);
				
				/* 13-hasChild */
				String hasChildStr = req.getParameter("hasChild");
				Integer hasChild = null;
				if (hasChildStr != null) {
					hasChild = Integer.valueOf(hasChildStr);
				}
				System.out.println("SitSrvServlet_221.hasChild = "+hasChild);
				
				/* 14-srvTime */
				String srvTimeHStr = req.getParameter("srvTimeH");
				String srvTimeMStr = req.getParameter("srvTimeM");
				
				if (Integer.valueOf(srvTimeHStr)<0 || Integer.valueOf(srvTimeHStr) >8) {
					errorMsgs.add("�A�Ȯɶ�(�p��)�п�J0~8���Ʀr");
				}
				if (! "0".equals(srvTimeMStr) && ! "30".equals(srvTimeMStr)) {
					errorMsgs.add("�A�Ȯɶ�(����)�u��0���άO30����");
				}
				if ("0".equals(srvTimeHStr) && "0".equals(srvTimeMStr)) {
					errorMsgs.add("�A�Ȯɶ��̧C30����");
				}
				String srvTime = srvTimeHStr + ("30".equals(srvTimeMStr)? "50": "00");
				if ("Boarding".equals(sitSrvCode) || "DayCare".equals(sitSrvCode)) {
					srvTime = null;
				}
				System.out.println("SitSrvServlet_240.srvTime = "+srvTime);
				
				/* 15-Eqpt */
				String eqptStr = req.getParameter("eqpt");
				Integer eqpt = null;
				if (eqptStr != null) {
					eqpt = Integer.valueOf(eqptStr);
				}
				System.out.println("SitSrvServlet_248.eqpt = "+eqpt);
				
				/* 16-addBathing */
				String addBathingStr = req.getParameter("addBathing");
				Integer addBathing = null;
				if (addBathingStr == null) {
					addBathing = 0;
				} else {
					addBathing = Integer.valueOf(addBathingStr);
				}
				System.out.println("SitSrvServlet_258.addBathing = "+addBathing);
				
				String addBathingFeeStr = req.getParameter("addBathingFee");
				Integer addBathingFee = null;
				if (addBathing == 1) {
					try {
						addBathingFee = Integer.valueOf(addBathingFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("�[���A�Ȼ���п�J�Ʀr");
					}
					System.out.println("SitSrvServlet_268.addBathingFee = "+addBathingFee);
				}
				
				/* 17-addPickup */
				String addPickupStr = req.getParameter("addPickup");
				Integer addPickup = null;
				if (addPickupStr == null) {
					addPickup = 0;
				} else {
					addPickup = Integer.valueOf(addPickupStr);
				}
				System.out.println("SitSrvServlet_279.addPickup = "+addPickup);
				
				String addPickupFeeStr = req.getParameter("addPickupFee");
				Integer addPickupFee = null;
				if (addPickup == 1) {
					try {
						addPickupFee = Integer.valueOf(addPickupFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("�[���A�Ȼ���п�J�Ʀr");
					}
					System.out.println("SitSrvServlet_289.addPickupFee = "+addPickupFee);
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
				
				/*************************** �ҷӱM�� ***************************/
				
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
				Integer licStatus = 0;// �s�W�ɹw�]���A0-�ݼf��
					
				/* 5-licPic */
				Part p = req.getPart("licPic");
					
				InputStream is = p.getInputStream();
				byte[] licPic = new byte[is.available()];
				is.read(licPic);
				is.close();
				System.out.println("SitSrvServlet_338.licPic.length = "+ licPic.length);
				if ("Boarding".equals(sitSrvCode) && licPic.length < 1) {
					errorMsgs.add("�ФW���ҷӹ���");
				}
				
				/*************************** �ҷӱM�� ***************************/
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					req.setAttribute("ssVO", ssVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/addNewSitSrv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				
				if (licPic.length > 0) {
					SitLicService slSvc = new SitLicService();
					SitLicVO sitLic = slSvc.addFromSitSrv(sitNo, licName, licPic, licEXP, licStatus);
					ssSvc.addWithSitLic(sitSrvName, sitSrvCode, sitNo, srvFee, srvInfo, srvArea, acpPetNum, acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, addBathing, addPickup, addBathingFee, addPickupFee, sitLic);
				} else {
					ssSvc.add(sitSrvName, sitSrvCode, sitNo, srvFee, srvInfo, srvArea, acpPetNum, acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, addBathing, addPickup, addBathing, addPickup);
				}
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				RequestDispatcher sucessView = req.getRequestDispatcher("/front-end/sitSrv/addNewSitSrv.jsp");
				sucessView.forward(req, res);
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("�s�W���ѡG "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/addNewSitSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* �Ӧ� addNewSitSrv.jsp ���ШD  - �s�W���i�A�� */
		if ("search".equals(action) || "browse".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvCode */
				String sitSrvCode = req.getParameter("sitSrvCode");
				if (sitSrvCode == null || sitSrvCode.isEmpty()) {
					sitSrvCode = "Boarding";
				}
//				System.out.println("SitSrvServlet_392.sitSrvCode = "+sitSrvCode);
				
				/* 2-acpPetTyp */
				String acpPetTypPart0 = req.getParameter("acpPetTypPart0");
				String acpPetTypPart1 = req.getParameter("acpPetTypPart1");
				String acpPetTypPart2 = req.getParameter("acpPetTypPart2");
				
				Set<Integer> acpPetTypSet = new HashSet<Integer>();
				if (acpPetTypPart0 != null) {
					Integer[] catArr = {0,1,2,3,4};
					for (Integer cat : catArr) {
						acpPetTypSet.add(cat);
					}
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 == null) {
					Integer[] dogArr = {1,2,3,4,5,6,7,8};
					for (Integer dog : dogArr) {
						acpPetTypSet.add(dog);
					}
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 != null) {
					switch(acpPetTypPart2) {
					case "small":
						acpPetTypSet.add(1);
						acpPetTypSet.add(5);
					case "medium":
						acpPetTypSet.add(2);
						acpPetTypSet.add(6);
					case "large":
						acpPetTypSet.add(3);
						acpPetTypSet.add(7);
					case "xlarge":
						acpPetTypSet.add(4);
						acpPetTypSet.add(8);
					}
				}
//				System.out.println("SitSrvServlet_428.acpPetTypSet = "+ acpPetTypSet);
				Object[] acpPetTypArr = acpPetTypSet.toArray();
				
				/* 3-nearAddr */
				String nearAddr = req.getParameter("nearAddr");
				String memAddress = null;
				if (nearAddr!=null) {

					int city_index = nearAddr.indexOf("��");
					int county_index = nearAddr.indexOf("��");
					if (city_index != -1) {
						memAddress = nearAddr.substring(city_index-2,city_index+1);
					} else if (county_index != -1) {
						memAddress = nearAddr.substring(county_index-2,city_index+1);
					}
				}
//				System.out.println("SitSrvServlet_431.memAddress = "+ memAddress);
				
				/* 4-dateFrom & 5-dateTo */
				String dateFrom = req.getParameter("dateFrom");
				String dateTo = req.getParameter("dateTo");
				
				try {
					Date.valueOf(dateFrom.trim());
					Date.valueOf(dateTo.trim());
					
				} catch (IllegalArgumentException e) {
					if (dateFrom.isEmpty()) {
						dateFrom = (new Date(System.currentTimeMillis())).toString();
					}
					if (dateTo.isEmpty()) {
						dateTo = (new Date(System.currentTimeMillis())).toString();
					} else {
						errorMsgs.add("�A�Ȥ���榡���~");
					}
				}
//				System.out.println("SitSrvServlet_450.dateFrom = "+dateFrom);
//				System.out.println("SitSrvServlet_451.dateTo = "+dateTo);
				
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/browseSitSrv.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/* 6.1-acpPetNum */
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null || "0".equals(req.getParameter("whichPage"))){
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
					session.setAttribute("map",map1);
					map = map1;
				} 
				
				
				/*************************** 2.�}�l�ƦX�d�� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				SitOffDayService sodSvc = new SitOffDayService();
				
				List<SitSrvVO2> sitSrvVOlist = new ArrayList<SitSrvVO2>();
				Set<String> sitSrvNoSet = new HashSet<String>();
				
				// ���d�ŦX�����i�����P�d�����A���A��VO
				sitSrvVOlist = ssSvc.choose_SitSrv(sitSrvCode, acpPetTypArr, map);
				System.out.println("SitSrvServlet_500.sitSrvVOlist.size(before = " + sitSrvVOlist.size());
				
				// �A�d���A���������𰲪��O���A�Ƚs��
				sitSrvNoSet = sodSvc.getSitByDate(sitSrvCode, dateFrom, dateTo);
				System.out.println("SitSrvServlet_504.sitSrvNoSet.size = " + sitSrvNoSet.size());
				
				// �p�G�A��VO���A�Ƚs�����b�𰲪�Set�W�A�N�����o�@��VO
				for (int i = 0; i < sitSrvVOlist.size(); i++) {
					SitSrvVO2 ssVO = sitSrvVOlist.get(i);
					if (sitSrvNoSet.contains(ssVO.getSitSrvNo())) {
						sitSrvVOlist.remove(i);
					}
				}
				System.out.println("SitSrvServlet_513.sitSrvVOlist.size(after = " + sitSrvVOlist.size());
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if("search".equals(action)) {
					req.setAttribute("sitSrvVOlist", sitSrvVOlist);
					req.setAttribute("dateFrom", dateFrom);
					req.setAttribute("dateTo", dateTo);
					RequestDispatcher sucessView = req.getRequestDispatcher("/front-end/sitSrv/browseSitSrv.jsp");
					sucessView.forward(req, res);
				} else if ("browse".equals(action)){
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					JSONArray sitSrvVOArray = new JSONArray(sitSrvVOlist);
					out.print(sitSrvVOArray);
				}
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("�d�ߥ��ѡG "+ e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/browseSitSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* �Ӧ� showOneSitAllSrv.jsp ���ШD  - �Ȱ����i�A�� */
		if ("pauseSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
//				System.out.println("SitSrvServlet_546.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 1, 0);
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error�G" + e.getMessage());
			}
		}
/* �Ӧ� showOneSitAllSrv.jsp ���ШD  - ���Ҧ��i�A�� */
		if ("rebootSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
//				System.out.println("SitSrvServlet_573.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 0, 0);
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error�G" + e.getMessage());
			}
		}
		
/* �Ӧ� showOneSitAllSrv.jsp ���ШD  - �R�����i�A�� */
		if ("delSrv".equals(action)) {
			PrintWriter out = res.getWriter();
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 1-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
//				System.out.println("SitSrvServlet_601.sitSrvNo = "+sitSrvNo);
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				Boolean updateOK = ssSvc.updateStatus(sitSrvNo, 1, 1);
				
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (updateOK) {
					out.write('1');
				} else {
					out.write("error");
				}
				
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				out.write("error�G" + e.getMessage());
			}
		}
		
/* �Ӧ� showOneSitAllSrv.jsp ���ШD  - �ק嶺�i�A�� */
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
			
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvService ssSvc = new SitSrvService();
				SitSrvVO ssVO = ssSvc.get_OneSit_OneSrv(sitSrvNo);
				// ���o�[���A�Ƚs��
				List<SitSrvVO> list = ssSvc.get_OneSit_AllSrv(ssVO.getSitNo());
				String addBathingNo=null, addPickupNo=null;
				for (SitSrvVO ss : list) {
		    		if (ss.getSitSrvName().indexOf("�[���~��") != -1 && ss.getSitSrvName().indexOf(sitSrvNo) != -1){
		    			addBathingNo = ss.getSitSrvNo();
		    		}
		    		if (ss.getSitSrvName().indexOf("�[�����e") != -1 && ss.getSitSrvName().indexOf(sitSrvNo) != -1){
		    			addPickupNo = ss.getSitSrvNo();
		    		}
		    	}
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("ssVO", ssVO);
				req.setAttribute("addBathingNo", addBathingNo);
				req.setAttribute("addPickupNo", addPickupNo);
				RequestDispatcher sucessView = req.getRequestDispatcher("/front-end/sitSrv/updateSitSrv.jsp");
				sucessView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
		
/* �Ӧ� updateSitSrv.jsp ���ШD  - �ק嶺�i�A�� */
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				/* 0-sitSrvNo */
				String sitSrvNo = req.getParameter("sitSrvNo");
				System.out.println("SitSrvServlet_648.sitSrvNo = "+sitSrvNo);
				
				/* 1-sitSrvName */
				String sitSrvName = req.getParameter("sitSrvName");
				String reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_ )]{1,20}$";
				if (sitSrvName == null || sitSrvName.trim().length() == 0) {
					errorMsgs.add("�п�J�A�ȦW��");
				} else if(!sitSrvName.matches(reg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�A�ȦW��: �u��O���B�^��r���B�Ʀr�B�Ů�M_ , �B���ץ��ݦb1��20����");
	            }
				System.out.println("SitSrvServlet_658.sitSrvName = "+sitSrvName);
				
				/* 2-sitSrvCode */
				String sitSrvCode = req.getParameter("sitSrvCode");
				System.out.println("SitSrvServlet_662.sitSrvCode = "+sitSrvCode);
				
				/* 3-sitNo */
				PetSitterVO petSitterVO = (PetSitterVO) session.getAttribute("petSitterVO");
				String sitNo = petSitterVO.getSitNo();
				System.out.println("SitSrvServlet_667.sitNo = "+sitNo);
				
				/* 4-srvFee */
				String srvFeeStr = req.getParameter("srvFee");
				if (srvFeeStr == null || srvFeeStr.trim().length() == 0) {
					errorMsgs.add("�п�J�A�ȶO�v");
				}
				Integer srvFee = null;
				try {
					srvFee = Integer.valueOf(srvFeeStr.trim());
				} catch (NumberFormatException ne) {
					errorMsgs.add("�A�ȶO�v�п�J�Ʀr");
				}
				System.out.println("SitSrvServlet_680.srvFee = "+srvFee);
				
				/* 5-srvInfo */
				String srvInfo = req.getParameter("srvInfo");
				System.out.println("SitSrvServlet_684.srvInfo = "+srvInfo);
				
				/* 6-srvArea */
				String srvAreaStr = req.getParameter("srvArea");
				Integer srvArea = null;
				if (srvAreaStr != null) {
					srvArea = Integer.valueOf(srvAreaStr);
				}
				System.out.println("SitSrvServlet_692.srvArea = "+srvArea);
				
				/* 7-acpPetNum */
				String acpPetNumStr = req.getParameter("acpPetNum");
				Integer acpPetNum = Integer.valueOf(acpPetNumStr);
				System.out.println("SitSrvServlet_697.acpPetNum = "+acpPetNum);
				
				/* 8-acpPetTyp */
				String acpPetTypPart0 = req.getParameter("acpPetTypPart0");
				String acpPetTypPart1 = req.getParameter("acpPetTypPart1");
				String acpPetTypPart2 = req.getParameter("acpPetTypPart2");
				
				
				if (acpPetTypPart0 == null && acpPetTypPart1 == null) {
					errorMsgs.add("���I�ﱵ�����d������");
				}
				if (acpPetTypPart1 != null && acpPetTypPart2 == null) {
					errorMsgs.add("���I��i�����������̤j�髬");
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
				System.out.println("SitSrvServlet_745.acpPetTyp = "+acpPetTyp);
				
				/* 9-careLevel */
				String careLevelStr = req.getParameter("careLevel");
				Integer careLevel = null;
				if (careLevelStr != null) {
					careLevel = Integer.valueOf(careLevelStr);
				}
				System.out.println("SitSrvServlet_753.careLevel = "+careLevel);
				
				/* 10-stayLoc */
				String stayLocStr = req.getParameter("stayLoc");
				Integer stayLoc = null;
				if (stayLocStr != null) {
					stayLoc = Integer.valueOf(stayLocStr);
				}
				System.out.println("SitSrvServlet_761.stayLoc = "+stayLoc);
				
				/* 11-overnightLoc */
				String overnightLocStr = req.getParameter("overnightLoc");
				Integer overnightLoc = null;
				if (overnightLocStr != null) {
					overnightLoc = Integer.valueOf(overnightLocStr);
				}
				System.out.println("SitSrvServlet_769.overnightLoc = "+overnightLoc);
				
				/* 12-smkFree */
				String smkFreeStr = req.getParameter("smkFree");
				Integer smkFree = null;
				if (smkFreeStr != null) {
					smkFree = Integer.valueOf(smkFreeStr);
				}
				System.out.println("SitSrvServlet_777.smkFree = "+smkFree);
				
				/* 13-hasChild */
				String hasChildStr = req.getParameter("hasChild");
				Integer hasChild = null;
				if (hasChildStr != null) {
					hasChild = Integer.valueOf(hasChildStr);
				}
				System.out.println("SitSrvServlet_785.hasChild = "+hasChild);
				
				/* 14-srvTime */
				String srvTimeHStr = req.getParameter("srvTimeH");
				String srvTimeMStr = req.getParameter("srvTimeM");
				
				if (Integer.valueOf(srvTimeHStr)<0 || Integer.valueOf(srvTimeHStr) >8) {
					errorMsgs.add("�A�Ȯɶ�(�p��)�п�J0~8���Ʀr");
				}
				if (! "0".equals(srvTimeMStr) && ! "30".equals(srvTimeMStr)) {
					errorMsgs.add("�A�Ȯɶ�(����)�u��0���άO30����");
				}
				if ("0".equals(srvTimeHStr) && "0".equals(srvTimeMStr)) {
					errorMsgs.add("�A�Ȯɶ��̧C30����");
				}
				String srvTime = srvTimeHStr + ("30".equals(srvTimeMStr)? "50": "00");
				if ("Boarding".equals(sitSrvCode) || "DayCare".equals(sitSrvCode)) {
					srvTime = null;
				}
				System.out.println("SitSrvServlet_804.srvTime = "+srvTime);
				
				/* 15-Eqpt */
				String eqptStr = req.getParameter("eqpt");
				Integer eqpt = null;
				if (eqptStr != null) {
					eqpt = Integer.valueOf(eqptStr);
				}
				System.out.println("SitSrvServlet_812.eqpt = "+eqpt);
				
				/* 16-addBathing */
				String addBathingStr = req.getParameter("addBathing");
				Integer addBathing = null;
				if (addBathingStr == null) {
					addBathing = 0;
				} else {
					addBathing = Integer.valueOf(addBathingStr);
				}
				System.out.println("SitSrvServlet_822.addBathing = "+addBathing);
				
				String addBathingFeeStr = req.getParameter("addBathingFee");
				Integer addBathingFee = null;
				if (addBathing == 1) {
					try {
						addBathingFee = Integer.valueOf(addBathingFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("�[���A�Ȼ���п�J�Ʀr");
					}
					System.out.println("SitSrvServlet_832.addBathingFee = "+addBathingFee);
				}
				
				/* 17-addPickup */
				String addPickupStr = req.getParameter("addPickup");
				Integer addPickup = null;
				if (addPickupStr == null) {
					addPickup = 0;
				} else {
					addPickup = Integer.valueOf(addPickupStr);
				}
				System.out.println("SitSrvServlet_843.addPickup = "+addPickup);
				
				String addPickupFeeStr = req.getParameter("addPickupFee");
				Integer addPickupFee = null;
				if (addPickup == 1) {
					try {
						addPickupFee = Integer.valueOf(addPickupFeeStr);
					} catch (NumberFormatException ne) {
						errorMsgs.add("�[���A�Ȼ���п�J�Ʀr");
					}
					System.out.println("SitSrvServlet_853.addPickupFee = "+addPickupFee);
				}
				
				SitSrvVO ssVO = new SitSrvVO();
				ssVO.setSitSrvNo(sitSrvNo);
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
				
				// �^�ǿ��~��T
				if (! errorMsgs.isEmpty()) {
					req.setAttribute("ssVO", ssVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/updateSitSrv.jsp");
					failureView.forward(req, res);
					return;
				}

				//---------------------------------------
				SitSrvService ssSvc = new SitSrvService();
				
				// �����o�[���A�ȡA�P�_���e���L�[��
				List<SitSrvVO> list = ssSvc.get_OneSit_AllSrv(sitNo);
				String addBathingNo=null, addPickupNo=null;
				for (SitSrvVO ss : list) {
		    		if (ss.getSitSrvName().indexOf("�[���~��") != -1 && ss.getSitSrvName().indexOf(sitSrvNo) != -1){
		    			addBathingNo = ss.getSitSrvNo();
		    		}
		    		if (ss.getSitSrvName().indexOf("�[�����e") != -1 && ss.getSitSrvName().indexOf(sitSrvNo) != -1){
		    			addPickupNo = ss.getSitSrvNo();
		    		}
		    	}
				System.out.println("SitSrvServlet_897.addBathingNo = " + addBathingNo);
				System.out.println("SitSrvServlet_898.addPickupNo = " + addPickupNo);
				
				SitSrvVO ssVO2 = null;
				// ���e�i���j�[���~���A�{�b�٬O�i���j�[���~��
				if (addBathingNo!= null && addBathing == 1) {
					System.out.println("����903");
					ssVO2 = new SitSrvVO();
					ssVO2.setSrvFee(addBathingFee);
					ssVO2.setSitSrvNo(addBathingNo);
				}
				// ���e�i���j�[���~���A�{�b�i�S���j�[���~��
				if (addBathingNo!= null && addBathing == 0) {
					// ssVO2=null-->�R���o�@���[���~��
					System.out.println("����911");
					ssSvc.updateStatus(addBathingNo, 1, 1);
				}
				// ���e�i�S���j�[���~���A�{�b�i�S���j�[���~��
				if (addBathingNo== null && addBathing == 0) {
					System.out.println("����916");
					// ssVO2=null-->do nothing
				}
				// ���e�i�S���j�[���~���A�{�b�i���j�[���~��
				if (addBathingNo== null && addBathing == 1) {
					System.out.println("����921");
					// ssVO2=null-->�s�W�o�@���[���~��
					ssSvc.add("�[���~��"+sitSrvNo, "Bathing", sitNo, addBathingFee, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				}
				
				SitSrvVO ssVO3 = null;
				// ���e�i���j�[�����e�A�{�b�٬O�i���j�[�����e
				if (addPickupNo!= null && addPickup == 1) {
					System.out.println("����929");
					ssVO3 = new SitSrvVO();
					ssVO3.setSrvFee(addPickupFee);
					ssVO3.setSitSrvNo(addPickupNo);
				}
				// ���e�i���j�[�����e�A�{�b�i�S���j�[�����e
				if (addPickupNo!= null && addPickup == 0) {
					System.out.println("����936");
					// ssVO3=null-->�R���o�@���[�����e
					ssSvc.updateStatus(addPickupNo, 1, 1);
				}
				// ���e�i�S���j�[�����e�A�{�b�i�S���j�[�����e
				if (addPickupNo== null && addPickup == 0) {
					System.out.println("����942");
					// ssVO3=null-->do nothing
				}
				// ���e�i�S���j�[�����e�A�{�b�i���j�[�����e
				if (addPickupNo== null && addPickup == 1) {
					System.out.println("����947");
					// ssVO3=null-->�s�W�o�@���[�����e
					ssSvc.add("�[�����e"+sitSrvNo, "Pickup", sitNo, addPickupFee, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				}
				
				/*************************** 2.�}�l�s�W��� ***************************************/
				SitSrvVO sitSrvVO = ssSvc.update(sitSrvNo, sitSrvName, sitSrvCode, sitNo, srvFee, srvInfo, srvArea, acpPetNum, acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, addBathing, addPickup, ssVO2, ssVO3);
	
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				req.setAttribute("sitSrvVO", sitSrvVO);
				RequestDispatcher sucessView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				sucessView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitSrv/showOneSitAllSrv.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
