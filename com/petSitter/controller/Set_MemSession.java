package com.petSitter.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.member.model.MemService;
import com.member.model.MemVO;
import com.petSitter.model.PetSitterService;
import com.petSitter.model.PetSitterVO;

@WebServlet("/Set_MemSession")
public class Set_MemSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Set_MemSession() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		
		String memNo = req.getParameter("memNo");
		
		// ����W�@��sitNo�Qsession�O�d
		if (session.getAttribute("sessionSitNo") != null) {
			session.removeAttribute("sessionSitNo");
		}
		
		// �|���n�J�|���@��memVO, �i��memVO.getMemNo
		session.setAttribute("memNo", memNo);
		MemService memSrv = new MemService();
		MemVO memVO = memSrv.getOneMem(memNo);
		Integer auth = memVO.getMemAuthority();
		
		// �P�_�O�_���O������, �O�h�s�O��sessionSitNo
		if (auth == 1) {
			PetSitterService petSitSrv = new PetSitterService();
			PetSitterVO petSitterVO = petSitSrv.getByFK(memNo);
			String sessionSitNo = petSitterVO.getSitNo();
			session.setAttribute("sessionSitNo", sessionSitNo);
		}
		
		res.sendRedirect("front-end/sitterFront.jsp");
		
		// ��bnavbar
//		<c:if test="${memVO.memAuthority==0}"></c:if>
//		<c:if test="${memVO.memAuthority==1}"></c:if>
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
