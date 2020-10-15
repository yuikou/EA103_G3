
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

		res.setContentType("image/*");
		ServletOutputStream out = res.getOutputStream();
		String action = req.getParameter("action");

		if ("getMemPhoto".equals(action)) {
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT MEMPHOTO FROM MEMBER_TABLE WHERE MEMNO = '" + req.getParameter("memNo") + "'");

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
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT PETPHOTO FROM PET WHERE PETNO = '" + req.getParameter("petNo") + "'");

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
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT petPic FROM PETPIC WHERE PETPICNO = '" + req.getParameter("petPicNo") + "'");
//				SELECT * FROM PETPIC WHERE PETPICNO=?
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