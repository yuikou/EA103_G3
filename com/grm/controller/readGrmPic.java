package com.grm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grm.model.GrmService;
import com.salon.model.SalonService;
import com.salonAlbum.model.SalonAlbService;

@WebServlet("/grm/PicReader")
public class ReadGrmPic extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
		String action = req.getParameter("action").toString();
//System.out.println(action);
//從DB讀取美容師照片
		if ("grmPic".equals(action)) {
			String grmNo = req.getParameter("grmPic").toString();
			GrmService gsv = new GrmService();
			byte[] gPic = gsv.getOneGrm(grmNo).getGroomerPic();
			out.write(gPic);
		}
	
//從DB讀取美容店相簿的相片		
		if("salAlbNo".equals(action)) {
			String salAlbNo = req.getParameter("salAlbNo");
			SalonAlbService sbsv = new SalonAlbService();
			byte[] salAlbPic = sbsv.getOnePic(salAlbNo).getSalAlbPic();
			out.write(salAlbPic);
		}
//從DB讀取美容業者(Salon)的相片
		if("salPic".equals(action)) {
			String salno = req.getParameter("salPic").toString();
//System.out.println(salno);
			SalonService salSvc = new SalonService();
			byte[] salPic = salSvc.getonesalon(salno).getSalPic();
//System.out.println(salPic);
			out.write(salPic);
		}
		
	}	
	
}
