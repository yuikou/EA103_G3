package com.sitODetail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sitODetail.model.SitODetailService;
import com.sitODetail.model.SitODetailVO;
import com.sitOrder.model.SitOrderService;
import com.sitOrder.model.SitOrderVO;

@WebServlet("/sitODetail/sitODetail.do")
public class SitODetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SitODetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
//		res.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		
		if ("select_orderDetail".equals(action) || "select_orderDetail_forMem".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				String sitOrderNo = req.getParameter("sitOrderNo");
				SitODetailService sitODetailSrv = new SitODetailService();
				SitODetailVO sitODetailVO = sitODetailSrv.getByPKFK(sitOrderNo);
				
				req.setAttribute("sitODetailVO", sitODetailVO);
				String url = null;
				if ("select_orderDetail".equals(action)) {
					url = "/front-end/sitOrder/sitOrderPage.jsp";
				} else {
					url = "/front-end/sitOrder/sitOrderForMem.jsp";
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);


			} catch (Exception e) {
				
				errorMsgs.add("無法取得資料:" + e.getMessage());
				String errorUrl = null;
				if ("select_orderDetail".equals(action)) {
					errorUrl = "/front-end/sitOrder/sitOrderPage.jsp";
				} else {
					errorUrl = "/front-end/sitOrder/sitOrderForMem.jsp";
				}
				RequestDispatcher failureView = req.getRequestDispatcher(errorUrl);
				failureView.forward(req, res);
				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
