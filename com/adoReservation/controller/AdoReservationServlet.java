package com.adoReservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adoPet.model.AdoPetService;
import com.adoPet.model.AdoPetVO;
import com.adoReservation.model.AdoReservationService;
import com.adoReservation.model.AdoReservationVO;

public class AdoReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	res.setContentType("text/html;charset=Big5");
		String action = req.getParameter("action");

		if ("agreeResRule".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				/*************************** 1.接收請求參數 ****************************************/
				String adoPetNo = req.getParameter("adoPetNo");

				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);

				/*************************** 準備轉交(Send the Success view) ************/
				req.setAttribute("adoPetVO", adoPetVO);
				req.setAttribute("agreeResRule", "agreeResRule");
				String url = "/front-end/adoPet/showOneAdopet.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoPet/showOneAdopet.jsp");
				failureView.forward(req, res);
			}

		}

		// 來自adoPetReservation.jsp請求關閉預約申請
		if ("closeReservation".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String adoPetNo = req.getParameter("adoPetNo");

				/*************************** 2.開始查詢資料 ****************************************/
				AdoPetService adoPetSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);

				/*************************** 準備轉交(Send the Success view) ************/
				req.setAttribute("adoPetVO", adoPetVO);
				req.removeAttribute("agreeResRule");
				String url = "/front-end/adoPet/showOneAdopet.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoPet/showOneAdopet.jsp");
				failureView.forward(req, res);
			}

		}

		if ("applyReservation".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			String adoPetNo =null;
			try {
				/*************************** 1.接收請求參數 ****************************************/
				 adoPetNo = req.getParameter("adoPetNo");
				
				String memNo = req.getParameter("memNo");

				java.sql.Date visitDate = null;
				try {
					visitDate = java.sql.Date.valueOf(req.getParameter("visitDate").trim());
				} catch (IllegalArgumentException ie) {
					visitDate = new java.sql.Date(System.currentTimeMillis());
					erroMsgas.add("請輸入日期!");
				}
				
				if (!erroMsgas.isEmpty()) {
					AdoPetService adoPetSvc = new AdoPetService();
					AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);
					req.setAttribute("adoPetVO", adoPetVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoPet/showOneAdopet.jsp");
					failureView.forward(req, res);
					return;
				}
				

				/*************************** 2.開始查詢資料 ****************************************/

				AdoReservationService adoResSvc = new AdoReservationService();
				String reservationNO = adoResSvc.adoReservationInsert(adoPetNo, memNo, visitDate);
				AdoReservationVO adoReservationVO = adoResSvc.getOneRes(reservationNO);
						

				/*************************** 準備轉交(Send the Success view) ************/

				// 轉交到訂單明細
				req.setAttribute("adoReservationVO", adoReservationVO);
				String url = "/front-end/adoReservation/adoPetResDetail.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 ? **********************************/
			} catch (Exception e) {
				/*要轉交才不會失敗*/
				AdoPetService adoPetSvc = new AdoPetService();
				AdoPetVO adoPetVO = adoPetSvc.findByPrimaryKey(adoPetNo);
				req.setAttribute("adoPetVO", adoPetVO);
				erroMsgas.add(e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoPet/showOneAdopet.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		//來自adoPetResList.jsp請求取消訂單
		if("cancelReservation".equals(action)) {
			System.out.println("區域");
			String memNo =null;
			List <AdoReservationVO> adoReservationVO =null;
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer reservationStatus = new Integer(req.getParameter("reservationStatus"));
				String reservationNO = req.getParameter("reservationNO");
				memNo = req.getParameter("memNo");
				/*************************** 2.開始查詢資料 ****************************************/
				
				AdoReservationService adoResSvc = new AdoReservationService();
				adoResSvc.delete(reservationStatus, reservationNO);
				adoReservationVO = adoResSvc.findByStatus(0, memNo);
				
				/*************************** 準備轉交(Send the Success view) ************/
				req.setAttribute("adoReservationVO", adoReservationVO);
				String url = "/front-end/adoReservation/adoPetResList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);				
				
			}catch(Exception e) {
				/*轉交到我的預約訂單*/
				AdoReservationService adoResSvc = new AdoReservationService();
				adoReservationVO = adoResSvc.findByStatus(0, memNo);
				req.setAttribute("adoReservationVO", adoReservationVO);
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoReservation/adoPetResList.jsp");
				failureView.forward(req, res);
			}
		}
		
	
		

		if ("getResByStatus".equals(action)) {
			System.out.println("getResByStatus近來");
			List<AdoReservationVO> adoReservationVO = null;
			String memNo =null;
			try {
				memNo = req.getParameter("memNo");
				System.out.println(memNo);
				Integer reservationStatus = new Integer(req.getParameter("reservationStatus"));
				System.out.println(reservationStatus);
				/*************************** 2.開始查詢資料 ****************************************/
				
				AdoReservationService adoResSvc = new AdoReservationService();
				adoReservationVO = adoResSvc.findByStatus(reservationStatus, memNo);
				
		
				if(adoReservationVO.size()==0) {
					AdoReservationVO adoResZero = new AdoReservationVO();
					adoResZero.setMemNo(memNo);
					adoResZero.setReservationStatus(reservationStatus);
					
					adoReservationVO.add(adoResZero);
					req.setAttribute("adoReservationVO", adoReservationVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoReservation/adoPetResList.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 準備轉交(Send the Success view) ************/
				
				req.setAttribute("adoReservationVO", adoReservationVO);
				String url = "/front-end/adoReservation/adoPetResList.jsp";
				RequestDispatcher sucessView = req.getRequestDispatcher(url);
				sucessView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				/*轉交到我的預約訂單*/
				AdoReservationService adoResSvc = new AdoReservationService();
				adoReservationVO = adoResSvc.findByStatus(0, memNo);
				req.setAttribute("adoReservationVO", adoReservationVO);
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/adoReservation/adoPetResList.jsp");
				failureView.forward(req, res);

			}
		}
	}

}
