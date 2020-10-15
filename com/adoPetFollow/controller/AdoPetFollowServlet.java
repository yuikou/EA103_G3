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

		/* showOneAdopet.jsp �I���[�J�l�� ajax */
		if ("addFLW".equals(action)) {
			System.out.println("�I�s�[�J�l��");

			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/

				String memNo = req.getParameter("memNo");
				String adoPetNo = req.getParameter("adoPetNo");

				System.out.println("memNo" + memNo);
				System.out.println("adoPetNo" + adoPetNo);
				/*************************** 2.�}�l�s�W��� ***************************************/

				AdoPetFollowService adoPetflwSvc = new AdoPetFollowService();

				Boolean insertStatus = adoPetflwSvc.adoPetFollowInsert(memNo, adoPetNo);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/

				if (insertStatus) {
					out.print("1");
				} else {
					out.print("0");
				}

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {

				out.write("error: " + e.getMessage());
			}

		}

		/* showOneAdopet.jsp �I�������l�� ajax */
		if ("delFLW".equals(action)) {
			System.out.println("�I�s�����l��");

			PrintWriter out = res.getWriter();

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/

				String memNo = req.getParameter("memNo");
				String adoPetNo = req.getParameter("adoPetNo");

				System.out.println("memNo" + memNo);
				System.out.println("adoPetNo" + adoPetNo);
				/*************************** 2.�}�l�s�W��� ***************************************/

				AdoPetFollowService adoPetflwSvc = new AdoPetFollowService();

				Boolean deltStatus = adoPetflwSvc.adoPetFollowDelete(memNo, adoPetNo);

				/*************************** 3.��������,�ǳ����(Send the Success view) ***********/

				if (deltStatus) {
					out.print("0");
				} else {
					out.print("1");
				}

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {

				out.write("error: " + e.getMessage());
			}

		}

	}

}
