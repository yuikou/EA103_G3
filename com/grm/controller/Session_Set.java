package com.grm.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


public class Session_Set extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
        session.setAttribute("salno","B002");
        
        String ID = session.getId();
        out.println("ID="+ID);
        res.sendRedirect("back-end/backEnd_index.jsp");
	}
}
