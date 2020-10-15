package com.adoPetFollow.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adoPetFollow.model.AdoPetFollowService;

public class AdoPetFollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		/* showOneAdopet.jsp 點擊加入追蹤 ajax */
		if ("addFLW".equals(action)) {
			System.out.println("呼叫加入追蹤");

			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.接收請求參數 ****************************************/

				String memNo = req.getParameter("memNo");
				String adoPetNo = req.getParameter("adoPetNo");

				System.out.println("memNo" + memNo);
				System.out.println("adoPetNo" + adoPetNo);
				/*************************** 2.開始新增資料 ***************************************/

				AdoPetFollowService adoPetflwSvc = new AdoPetFollowService();

				Boolean insertStatus = adoPetflwSvc.adoPetFollowInsert(memNo, adoPetNo);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/

				if (insertStatus) {
					out.print("1");
				} else {
					out.print("0");
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {

				out.write("error: " + e.getMessage());
			}

		}

		/* showOneAdopet.jsp 點擊取消追蹤 ajax */
		if ("delFLW".equals(action)) {
			System.out.println("呼叫取消追蹤");

			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.接收請求參數 ****************************************/

				String memNo = req.getParameter("memNo");
				String adoPetNo = req.getParameter("adoPetNo");

				System.out.println("memNo" + memNo);
				System.out.println("adoPetNo" + adoPetNo);
				/*************************** 2.開始新增資料 ***************************************/

				AdoPetFollowService adoPetflwSvc = new AdoPetFollowService();

				Boolean deltStatus = adoPetflwSvc.adoPetFollowDelete(memNo, adoPetNo);

				/*************************** 3.取消完成,準備轉交(Send the Success view) ***********/

				if (deltStatus) {
					out.print("0");
				} else {
					out.print("1");
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {

				out.write("error: " + e.getMessage());
			}

		}

	}

}
