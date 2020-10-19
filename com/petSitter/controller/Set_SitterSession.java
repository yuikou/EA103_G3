package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.petSitter.model.PetSitterService;
import com.petSitter.model.PetSitterVO;

@WebServlet("/Set_SitterSession")
public class Set_SitterSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Set_SitterSession() {
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
		
		session.setAttribute("sitNo", "S001");
		PetSitterService petSitSrv = new PetSitterService();
		PetSitterVO petSitterVO = petSitSrv.getByPK("S001");
		String memNo = petSitterVO.getMemNo();
		session.setAttribute("memNo", memNo);
		
		
		String ID = session.getId();
		out.println("ID=" + ID);
		res.sendRedirect("front-end/sitterFront.jsp");
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
