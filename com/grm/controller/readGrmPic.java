package com.grm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grm.model.GrmService;
import com.salonAlbum.model.SalonAlbService;


@WebServlet("/grm/picReader")
public class readGrmPic extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
//從DB讀取美容師照片		
		String grmNo = req.getParameter("grmPic").toString();
		GrmService gsv = new GrmService();
		byte[] gPic = gsv.getOneGrm(grmNo).getGroomerPic();
		out.write(gPic);
	}

}
