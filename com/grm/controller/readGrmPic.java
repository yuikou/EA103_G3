package com.grm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grm.model.GrmService;


@WebServlet("/grm/picReader")
public class readGrmPic extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
		
		String grmNo = (String)req.getParameter("grmPic");
		GrmService gsv = new GrmService();
		byte[] gPic = gsv.getOneGrm(grmNo).getGroomerPic();
		out.write(gPic);
	}

}
