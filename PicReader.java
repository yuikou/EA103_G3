
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adoPetAlbum.model.AdoPetAlbumService;
import com.adoPetAlbum.model.AdoPetAlbumVO;
import com.member.model.MemService;
import com.member.model.MemVO;
import com.salon.model.SalonService;
import com.salon.model.SalonVO;
import com.sitLic.model.SitLicService;
import com.sitLic.model.SitLicVO;
import com.sitPhoto.model.SitPhotoService;
import com.sitPhoto.model.SitPhotoVO;
//import com.employee.model.*;
//import com.forum.model.ForumService;
//import com.forum.model.ForumVO;
import com.grm.model.GrmService;
import com.salon.model.SalonService;
import com.salonAlbum.model.SalonAlbService;


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
		
		
			/*---------芳郁---------*/
		/* 來自update_adopt.jsp的請求 - 顯示該待領養寵物所有圖片 */
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

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (IOException ie) {

				erroMsgas.add("圖片無法讀取" + ie.getMessage());
				String url = "/adoPet/adopt/back-end/listAllAdopt.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}

		}
		
		
		/* 來自listAllAdopt.jsp的請求 - 顯示一張待領養寵物圖片 */
		
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
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (IOException ie) {
				ie.printStackTrace();
				
				req.setAttribute("list", list); 
				String url = "/adoPet/adopt/back-end/listAllAdopt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
			}
		}
		
		/*---- 書凱 ----*/
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
		
		/* ----------于婷---------- */
		if ("sitLic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String licNo = req.getParameter("licNo");
						
				/***************************2.開始查詢資料*****************************************/
				SitLicService slSvc = new SitLicService();
				SitLicVO sitLic = slSvc.getOneLicByPK(licNo);
						
				// 直接寫出
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitLic.getLicPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
					out.flush();
				}
				
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		if ("getMemPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String memNo = req.getParameter("memNo");
						
				/***************************2.開始查詢資料*****************************************/
				MemService slSvc = new MemService();
				MemVO memVo = slSvc.getOneMem(memNo);
				
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
				
				ByteArrayInputStream bin = new ByteArrayInputStream(memVo.getMemPhoto());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
					out.flush();
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitFollow/listSitFollow.jsp");
				failureView.forward(req, res);
			}
					
		}
		
		/*---------怡晴---------*/
		if ("sitPhoto".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 ****************************************/
				String sitPNo = req.getParameter("sitPNo");
				/*************************** 2.開始查詢資料 *****************************************/
				SitPhotoService sitPhotoSrv = new SitPhotoService();
				SitPhotoVO sitPhotoVO = sitPhotoSrv.getByPK(sitPNo);
				
				
				// 直接寫出
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();

				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(sitPhotoVO.getSitPhoto());
				byte[] buffer = new byte[4 * 1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				bin.close();
				

			} catch (Exception e) {

				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/sitPhoto/sitPhotoUpload.jsp");
				failureView.forward(req, res);
			}
		}
		
		/* ----------繼元---------- */
		if ("employeePhoto".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String empno = req.getParameter("empNo");
						
				/***************************2.開始查詢資料*****************************************/
				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getOneEmployee(empno);
		
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(employeeVO.getEmpPhoto());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}					
		}
		

		if ("adPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String adno = req.getParameter("adNo");
						
				/***************************2.開始查詢資料*****************************************/
				AdvertisementService adSvc = new AdvertisementService();
				AdvertisementVO advertisementVO = adSvc.getOneAdvertisement(adno);
		
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(advertisementVO.getAdPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
											
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitFollow/listSitFollow.jsp");
				failureView.forward(req, res);
			}					
		}

		/* ----------政旭---------- */
		if ("salPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String salNo = req.getParameter("salNo");
						
				/***************************2.開始查詢資料*****************************************/
				SalonService salSvc = new SalonService();
				SalonVO salonVO = salSvc.getonesalon(salNo);
						
				// 直接寫出
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream

				ByteArrayInputStream bin = new ByteArrayInputStream(salonVO.getSalPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("salCertif".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String salNo = req.getParameter("salNo");
						
				/***************************2.開始查詢資料*****************************************/
				SalonService salSvc = new SalonService();
				SalonVO salonVO = salSvc.getonesalon(salNo);
						
				// 直接寫出
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream

				ByteArrayInputStream bin = new ByteArrayInputStream(salonVO.getSalCertif());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/sitLic/showOneSitLic.jsp");
				failureView.forward(req, res);
			}
		}
		

		/* ----------念群---------- */
		if ("getPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String forumNo = req.getParameter("forumNo");
						
				/***************************2.開始查詢資料*****************************************/
				ForumService forumSvc = new ForumService();
				ForumVO forumVO = forumSvc.getOneForum(forumNo);
		
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(forumVO.getForumPic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forum/forumDisplay.jsp");
				failureView.forward(req, res);
			}
					
		}


		/* ----------菱琦---------- */
		//從DB讀取美容師照片
		if ("grmPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				res.setContentType("image/*");
				ServletOutputStream out = res.getOutputStream();
				String grmNo = req.getParameter("grmPic").toString();
				GrmService gsv = new GrmService();
				byte[] gPic = gsv.getOneGrm(grmNo).getGroomerPic();
				out.write(gPic);
			
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		//從DB讀取美容店相簿的相片		
		if("salAlbNo".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				res.setContentType("image/*");
				ServletOutputStream out = res.getOutputStream();
			
				String salAlbNo = req.getParameter("salAlbNo");
				SalonAlbService sbsv = new SalonAlbService();
				byte[] salAlbPic = sbsv.getOnePic(salAlbNo).getSalAlbPic();
				out.write(salAlbPic);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		//從DB讀取美容業者(Salon)的相片
		if("salPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				res.setContentType("image/*");
				ServletOutputStream out = res.getOutputStream();
				
				String salno = req.getParameter("salPic").toString();
				SalonService salSvc = new SalonService();
				byte[] salPic = salSvc.getonesalon(salno).getSalPic();
				out.write(salPic);
			
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println(e.getMessage());
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
