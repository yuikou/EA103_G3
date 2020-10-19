package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Set_MemSession")
public class Set_MemSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Set_MemSession() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		HttpSession session = req.getSession();
		if (session.getAttribute("sitNo") != null) {
			session.removeAttribute("sitNo");
		}
		if (session.getAttribute("memNo") != null) {
			session.removeAttribute("memNo");
		}
		session.setAttribute("memNo", "M003");
		
		String ID = session.getId();
		out.println("ID=" + ID);
		res.sendRedirect("front-end/sitterFront.jsp");
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
