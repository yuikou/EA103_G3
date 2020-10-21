package com.transDetail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.transDetail.model.*;
import com.member.model.*;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.domain.AioCheckOutOneTime;

public class TransDetailServlet extends HttpServlet {
	public static AllInOne all;
	static {
		all = new AllInOne("");
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		MemVO memVO = (MemVO) req.getSession().getAttribute("memVO");

		String checkValue = req.getParameter("MerchantID");
		System.out.println(checkValue);
		
		if ("insert".equals(action)) {
			
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("Member doesn't exist.");
				}

				Integer transAmount = null;
				try {
					transAmount = new Integer(req.getParameter("transAmount"));
				} catch (IllegalArgumentException e) {
					errorMsgs.add("please key in money!!");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/ecpay_input_form.jsp");
					failureView.forward(req, res);
					return;
				}
				
				TransDetailService tsSvc = new TransDetailService();
				String transNo = tsSvc.addTransDetail(memNo, null, null, 0, 0, new java.sql.Timestamp(System.currentTimeMillis()), transAmount);
				
				// start a new form for ecpay
				AioCheckOutOneTime obj = new AioCheckOutOneTime();
//				obj.setMerchantID("2000132"); for test
				obj.setMerchantTradeNo(transNo);
				// change time format
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				obj.setMerchantTradeDate(df.format(new java.sql.Timestamp(System.currentTimeMillis())));
				obj.setTotalAmount(String.valueOf(transAmount));
				obj.setTradeDesc("Deposit shopping points");
				obj.setItemName("Deposit");
//				obj.setReturnURL("http://locaolhost:8081/EA103G3/TransDetailServlet.do?action=update");
				obj.setReturnURL("http://locaolhost:8081/EA103G3/TransDetailServlet.do");

				obj.setClientBackURL("http://localhost:8081/EA103G3/ecpay_success.jsp");
				obj.setNeedExtraPaidInfo("N");
				obj.setRedeem("N");
				// make the form by api
				String form = all.aioCheckOut(obj, null);
				
				// send the form data
				out.print("<!DOCTYPE html>\r\n" + 
						"<html lang=\"en\">"+ 
						"<head>\r\n" + 
						"    <meta charset=\"UTF-8\">\r\n" + 
						"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + 
						"    <title>Document</title>\r\n" + 
						"</head>"+ 
						"<body>");
				out.print(form);
				out.print("</body>\r\n"+ 
						"</html>");
				
				// return new TransDetailVO
				TransDetailVO tsVO = tsSvc.getOneDetail(transNo);
				MemService memSvc = new MemService();
				Integer memPoint = memSvc.getOneMem(memVO.getMemNo()).getMemPoint();
//				tsSvc.deposit(tsVO, 0); //for test
				tsSvc.deposit(tsVO, memPoint);
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/ecpay_input_form.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
