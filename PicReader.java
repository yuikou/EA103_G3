
import java.io.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adoPetAlbum.model.AdoPetAlbumService;
import com.adoPetAlbum.model.AdoPetAlbumVO;
import com.member.model.MemService;
import com.member.model.MemVO;
import com.sitLic.model.SitLicService;
import com.sitLic.model.SitLicVO;
import com.sitPhoto.model.SitPhotoService;
import com.sitPhoto.model.SitPhotoVO;

@WebServlet("/PicReader.do")
@MultipartConfig
public class PicReader extends HttpServlet {

	static DataSource ds;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	Connection con;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String action = req.getParameter("action");
		
		
			/*---------�ڭ�---------*/
		/* �Ӧ�update_adopt.jsp���ШD - ��ܸӫݻ�i�d���Ҧ��Ϥ� */
		if ("listAllPic".equals(action)) {
			List<String> erroMsgas = new LinkedList<String>();
			req.setAttribute("erroMsgas", erroMsgas);
			try {

				res.setContentType("image/gif");
				ServletOutputStream out = res.getOutputStream();

				AdoPetAlbumService dao = new AdoPetAlbumService();

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
				String url = "/adoPet/adopt/back-end/listAllAdopt.jsp";
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
				
				
				AdoPetAlbumService dao = new AdoPetAlbumService();

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
				
				req.setAttribute("list", list); 
				String url = "/adoPet/adopt/back-end/listAllAdopt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);
			}
		}
		
		/*---- �ѳ� ----*/
		if ("getMemPhoto".equals(action)) {
			try {
				res.setContentType("image/*");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT MEMPHOTO FROM MEMBER_TABLE WHERE MEMNO = '" + req.getParameter("memNo") + "'");
				ServletOutputStream out = res.getOutputStream();
				if (rs.next()) {
					BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("memPhoto"));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int len;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
				} else {
					res.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		if("getPetPhoto".equals(action)) {
			try {
				res.setContentType("image/*");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT PETPHOTO FROM PET WHERE PETNO = '" + req.getParameter("petNo") + "'");
				ServletOutputStream out = res.getOutputStream();

				if (rs.next()) {
					BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("petPhoto"));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int len;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
				} else {
					res.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		if("getPetPic".equals(action)) {
			try {
				res.setContentType("image/*");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT petPic FROM PETPIC WHERE PETPICNO = '" + req.getParameter("petPicNo") + "'");
				ServletOutputStream out = res.getOutputStream();

				if (rs.next()) {
					BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("petPic"));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int len;
					while ((len = in.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					in.close();
				} else {
					res.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
				rs.close();
				stmt.close();
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
		/* ----------�_�@---------- */
		if ("sitLic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String licNo = req.getParameter("licNo");
						
				/***************************2.�}�l�d�߸��*****************************************/
				SitLicService slSvc = new SitLicService();
				SitLicVO sitLic = slSvc.getOneLicByPK(licNo);
						
				// �����g�X
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]��InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitLic.getLicPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
					out.flush();
				}
				bin.close();
						
					
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		if ("sitFollow".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String memNo = req.getParameter("memNo");
						
				/***************************2.�}�l�d�߸��*****************************************/
				MemService slSvc = new MemService();
				MemVO memVo = slSvc.getOneMem(memNo);
				
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				ByteArrayInputStream bin = new ByteArrayInputStream(memVo.getMemPhoto());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitFollow/listSitFollow.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		/*---------�ɴ�---------*/
		if ("sitPhoto".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String sitPNo = req.getParameter("sitPNo");
				/*************************** 2.�}�l�d�߸�� *****************************************/
				SitPhotoService sitPhotoSrv = new SitPhotoService();
				SitPhotoVO sitPhotoVO = sitPhotoSrv.getByPK(sitPNo);

				// �����g�X
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();

				// byte[]��InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitPhotoVO.getSitPhoto());
				byte[] buffer = new byte[4 * 1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				bin.close();

			} catch (Exception e) {

				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
				failureView.forward(req, res);
			}
		}
	}

	public void init() throws ServletException {
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}