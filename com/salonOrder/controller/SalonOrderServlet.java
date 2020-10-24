package com.salonOrder.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;
import com.salonOrder.model.SalonOrderService;
import com.salonOrder.model.SalonOrderVO;
import com.salonOrderDetail.model.SalonOrderDetailVO;


public class SalonOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
          	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("insert".equals(action)) {
			try {
				
				String memNo = new String(req.getParameter("memNo"));
				Integer salTp = new Integer(req.getParameter("salTp"));
				String petNo = new String(req.getParameter("petNo"));
				String salNo = new String(req.getParameter("salNo"));												
				Integer orderStatus = new Integer(req.getParameter("orderStatus"));
//判斷會員錢夠不夠				
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memNo);				
				Integer memPoint = memVO.getMemPoint();//會員點數
				
				if(memPoint < salTp) {
					res.sendRedirect(req.getContextPath() + "/front-end/charge.jsp");	
					return;
				}else {
					memPoint = memPoint - salTp;
				}
																
				SalonOrderVO salonOrderVO = new SalonOrderVO();
				salonOrderVO.setMemNo(memNo);
				salonOrderVO.setPetNo(petNo);
				salonOrderVO.setSalNo(salNo);				
				salonOrderVO.setSalTp(salTp);
				salonOrderVO.setOrderStatus(orderStatus);
				
//				SalonOrderService salonOrderSvc =new SalonOrderService();
//				salonOrderVO = salonOrderSvc.addSalonOrder(memNo, petNo, salNo, salTp, orderStatus);
				
				String salSevNo = new String(req.getParameter("salSevNo"));
				String groomerNo = new String(req.getParameter("groomerNo"));
				Integer salSevPr = new Integer(req.getParameter("salSevPr"));
				
//				List<SalonOrderDetailVO> list = new ArrayList<SalonOrderDetailVO>();
				SalonOrderDetailVO salODVO = new SalonOrderDetailVO();
				salODVO.setSalSevNo(salSevNo);
				salODVO.setGroomerNo(groomerNo);
				salODVO.setSalSevPr(salSevPr);				
//				list.add(salODVO);
																				
				// 插入訂單
				SalonOrderService salonOrderService =new SalonOrderService();
				salonOrderService.addSalonOrder(memNo, petNo, salNo, salTp, orderStatus,salSevNo,groomerNo,salSevPr,memPoint);
								
				
				String url ="/front-end/OK.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);	
			}catch(Exception e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/fail.jsp");
				failureView.forward(req, res);
			}			
		}						
	}

}
