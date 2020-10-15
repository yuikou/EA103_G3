package com.adoAlbum.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adoPetAlbum.model.AdoPetAlbumDAO;
import com.adoPetAlbum.model.AdoPetAlbumVO;

public class ShowAdoPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		/* �Ӧ�update_adopt.jsp���ШD - ��ܸӫݻ�i�d���Ҧ��Ϥ� */
		if ("listAllPic".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				res.setContentType("image/gif");
				ServletOutputStream out = res.getOutputStream();

				AdoPetAlbumDAO dao = new AdoPetAlbumDAO();

				AdoPetAlbumVO pic = new AdoPetAlbumVO();
				pic.setAdoPetNo(req.getParameter("adoPetNo"));
				pic.setAdoPicNo(req.getParameter("adoPicNo"));
				AdoPetAlbumVO picDB = dao.getPic(pic);

				byte[] buf = new byte[4 * 1024];

				ByteArrayInputStream bin = new ByteArrayInputStream(picDB.getAdoPetPic());

				int len;
				while ((len = bin.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				bin.close();
				out.close();

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (IOException ie) {

				erroMsgas.add("�Ϥ��L�kŪ��" + ie.getMessage());

				String url = "/adoPet/adopt/back_end/listAllAdopt.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}

		}
		
		
		/* �Ӧ�listAllAdopt.jsp���ШD - ��ܤ@�i�ݻ�i�d���Ϥ� */
		if ("listAlladoPet".equals(action)) {
			
			List <String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			List<AdoPetAlbumVO> list =null;
			try {
				res.setContentType("image/gif");
				ServletOutputStream out = res.getOutputStream();
				
				
				AdoPetAlbumDAO dao = new AdoPetAlbumDAO();

				list = dao.getPicList(req.getParameter("adoPetNo"));

				for (AdoPetAlbumVO adoPetPic : list) {

					byte[] adoPetPicArr = adoPetPic.getAdoPetPic();

					out.write(adoPetPicArr);
					out.flush();
					
					out.close();
				}
				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (IOException ie) {

				ie.printStackTrace();
//				
//				req.setAttribute("list", list); 
//				String url = "/adoPet/adopt/back_end/listAllAdopt.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
//				successView.forward(req, res);
			}
		}
	}

}
