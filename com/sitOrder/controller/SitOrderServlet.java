package com.sitOrder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.petSitter.model.*;
import com.sitODetail.model.*;
import com.sitOrder.model.*;

@WebServlet("/sitOrder/sitOrder.do")
public class SitOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SitOrderServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		HttpSession session = req.getSession();
		String action = req.getParameter("action");
		
		if ("select_order".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {

				String sessionSitNo = (String) session.getAttribute("sessionSitNo");
				SitOrderService sitOrderSrv = new SitOrderService();
				Set<SitOrderVO> sitOrderSet = sitOrderSrv.getByFK_sitNo(sessionSitNo);
				
//				if (sitOrderSet.isEmpty()) {
//					errorMsgs.add("尚無訂單");
//				}
//				
//				if (!errorMsgs.isEmpty()) {		
//					req.setAttribute("sitOrderSet", sitOrderSet);
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/petSitter/listOneSitter.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				req.setAttribute("sitOrderSet", sitOrderSet);
				String url = "/front-end/sitOrder/sitOrderPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/petSitter/listOneSitter.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("select_order_forMem".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {

				String memNo = (String) session.getAttribute("memNo");
				SitOrderService sitOrderSrv = new SitOrderService();
				Set<SitOrderVO> sitOrderSet = sitOrderSrv.getByFK_memNo(memNo);
				
				req.setAttribute("sitOrderSet", sitOrderSet);
				String url = "/front-end/sitOrder/sitOrderForMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitterFront.jsp");
				failureView.forward(req, res);
			}
			
			
		}
		
		if ("display_for_reserve".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				String sitNo = req.getParameter("sitNo");
				req.setAttribute("sitNo", sitNo);
        		String url = "/front-end/sitOrder/reservePetSitter.jsp";
        		RequestDispatcher successView = req
        				.getRequestDispatcher(url);
        		successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/petSitter/listOneSitterForMem.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("reserve".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				System.out.println("able to come here");
				String sitNo = req.getParameter("sitNo").trim();
				req.setAttribute("sitNo", sitNo);
				String memNo = (String) session.getAttribute("memNo");
	    		
				Date sitSDate = null;
				Date sitEDate = null;
				try {
					sitSDate = java.sql.Date.valueOf(req.getParameter("sitSDate").trim());
					sitEDate = java.sql.Date.valueOf(req.getParameter("sitEDate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入日期!");
				}
				String sitOTime = req.getParameter("sitOTime");
				Integer totalPrice = new Integer(req.getParameter("totalPrice"));
				Integer refund = 0;		
				Integer coupon = 0;
				Integer commStar = 0;
				String sitComm = "";
				String pickupFrom = req.getParameter("pickupFrom");
				Integer orderStatus = 0;
			    
				String city = req.getParameter("city");
				String district = req.getParameter("district");
				String address = req.getParameter("address");
				String pickupTo = city + district + address;
				req.setAttribute("city", city);
				req.setAttribute("district", district);
				req.setAttribute("address", address);
				
				
				if (city.isEmpty() || district.isEmpty() || address.isEmpty()) {
					errorMsgs.add("請輸入正確地址");
				}				
				
				SitOrderVO sitOrderVO = new SitOrderVO();
	    		sitOrderVO.setMemNo(memNo);
	    		sitOrderVO.setSitNo(sitNo);
	    		sitOrderVO.setSitSDate(sitSDate);
	    		sitOrderVO.setSitEDate(sitEDate);
	    		sitOrderVO.setSitOTime(sitOTime);
	    		sitOrderVO.setTotalPrice(totalPrice);
	    		sitOrderVO.setOrderStatus(orderStatus);
	    		sitOrderVO.setRefund(refund);
	    		sitOrderVO.setCoupon(coupon);
	    		sitOrderVO.setCommStar(commStar);
	    		sitOrderVO.setSitComm(sitComm);
	    		sitOrderVO.setPickupFrom(pickupFrom);
	    		sitOrderVO.setPickupTo(pickupTo);
	    		
	    		String sitSrvNo = req.getParameter("sitSrvNo");
	    		String petNo = req.getParameter("petNo");
	    		Integer sitOpPrice = new Integer(req.getParameter("sitOpPrice"));
	    		Integer sitSrvTimes = new Integer(req.getParameter("sitSrvTimes"));
	    		String sitSrvUnit = req.getParameter("sitSrvUnit");
	    		
	    		List<SitODetailVO> list = new ArrayList<SitODetailVO>();
	    		SitODetailVO sitODetailVO = new SitODetailVO();
	    		sitODetailVO.setSitSrvNo(sitSrvNo);
	    		sitODetailVO.setPetNo(petNo);
	    		sitODetailVO.setSitOpPrice(sitOpPrice);
	    		sitODetailVO.setSitSrvTimes(sitSrvTimes);
	    		sitODetailVO.setSitSrvUnit(sitSrvUnit);
	    		list.add(sitODetailVO);
	    		
	    		if (!errorMsgs.isEmpty()) {
					req.setAttribute("sitOrderVO", sitOrderVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/sitOrder/reservePetSitter.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
	    		
	    		SitOrderDAO sitODAO = new SitOrderDAO();
	    		sitODAO.insertWithODetail(sitOrderVO, list);
	    		out.write("reserve");
	    		
	    		
//				String url = "/front-end/sitterFront.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

	    		
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOrder/reservePetSitter.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		if ("confirm_order".equals(action)) {
			
			
			try {
				String sitOrderNo = (String) req.getParameter("sitOrderNo");
				SitOrderService sitOrderSrv = new SitOrderService();
				SitOrderVO sitOrderVO = sitOrderSrv.getByPK(sitOrderNo);
				
				Integer orderStatus = sitOrderVO.getOrderStatus();
				if (sitOrderVO.getOrderStatus() == 0) {
					orderStatus = 1;
				}
				sitOrderVO = sitOrderSrv.update_orderStatus(orderStatus, sitOrderNo);
				if (orderStatus == 1) {
					out.write("confirm");
				}
			} catch (Exception e) {
				out.write("error: " + e.getMessage());			
			}
			
		}
		
		if("updateStarStatus".equals(action)) {
			
			try {
				System.out.println("we are able to reach servlet");
				String sitOrderNo = (String) req.getParameter("sitOrderNo");
				System.out.println(sitOrderNo);
				SitOrderService sitOrderSrv = new SitOrderService();
				SitOrderVO sitOrderVO = sitOrderSrv.getByPK(sitOrderNo);
				String sitNo = sitOrderVO.getSitNo();
				System.out.println(sitNo);
				
				Integer orderStatus = 3;
				
				Integer commStar = new Integer(req.getParameter("commStar"));
				String sitComm = req.getParameter("sitComm");
				
				sitOrderSrv.update_sitCommStar(orderStatus, commStar, sitComm, sitOrderNo);
				System.out.println(commStar);
				System.out.println(sitComm);
				
				PetSitterService petSitSrv = new PetSitterService();
				PetSitterVO petSitterVO = petSitSrv.getByPK(sitNo);
				Double totalComm = petSitterVO.getTotalComm() + commStar;
				petSitSrv.update_totalComm(sitNo, totalComm);
				System.out.println(totalComm);
	    		
	    		Double totalCus = new Double(petSitSrv.getByPK(sitNo).getTotalCus());
	    		totalCus += 1;
	    		System.out.println("totalCus=" + totalCus);
	    		
	    		SitOrderService sitOSrv = new SitOrderService();
	    		Set<SitOrderVO> sitOList = (Set<SitOrderVO>) sitOSrv.getByFK_memNo(sitOrderVO.getMemNo());
	    		
	    		Integer repeatCus = petSitSrv.getByPK(sitNo).getRepeatCus();
	    		System.out.println("repeatCusBefore="+repeatCus);
	    		if (sitOList.size()==2) {
	    			repeatCus += 1;
	    		}
	    		System.out.println("repeatCusAfter="+repeatCus);
	    		petSitSrv.update_repeat_totalCus(sitNo, totalCus, repeatCus);
	    		System.out.println("able to update");
				
				out.write("success");
								
			} catch (Exception e) {
				out.write("error: " + e.getMessage());			
			}
		}
		
		if("comment".equals(action)) {
			System.out.println("able to come here");
			
			String sitOrderNo = req.getParameter("sitOrderNo");
			SitOrderService sitOSrv = new SitOrderService();
			SitOrderVO sitOVO = sitOSrv.getByPK(sitOrderNo);
			String sitNo = sitOVO.getSitNo();
			JSONObject sitOrder = new JSONObject();
			sitOrder.put("sitOrderNo", sitOrderNo);
			sitOrder.put("sitNo", sitNo);
			out.print(sitOrder);
			
			System.out.println("success to write out");
		}
	}
		
//		if("display_comment".equals(action)) {
//			
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//				
//				String sitOrderNo = req.getParameter("sitOrderNo");
//				req.setAttribute("sitOrderNo", sitOrderNo);
//        		String url = "/front-end/sitOrder/sitterComment.jsp";
//        		RequestDispatcher successView = req.getRequestDispatcher(url);
//        		successView.forward(req, res);
//				
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitOrder/sitOrderPageForMem.jsp");
//				failureView.forward(req, res);
//			}
//		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
