package com.sitPhoto.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitPhotoJDBCDAO implements SitPhotoDAO_interface {
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String userid = "EA103G3";
	private static final String pwd = "123456";

	private static final String ADD_PSTMT = "INSERT INTO sitPhoto (sitPNo, sitNo, sitPhoto) VALUES ('SP' || lpad(sitPhoto_SEQ.NEXTVAL, 3, '0'), ?, ?)";
	private static final String DELETE_PSTMT = "DELETE FROM sitPhoto WHERE sitPNo = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM sitPhoto WHERE sitNo = ?";
	private static final String GET_BY_PK = "SELECT * FROM sitPhoto WHERE sitPNo = ?";
			
	@Override
	public void add(SitPhotoVO sitPhotoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(ADD_PSTMT);

			pstmt.setString(1, sitPhotoVO.getSitNo());
			pstmt.setBytes(2, sitPhotoVO.getSitPhoto());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String sitPNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_PSTMT);
			pstmt.setString(1, sitPNo);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}
	}
	
	@Override
	public SitPhotoVO getByPK(String sitPNo) {
		SitPhotoVO sitPhotoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GET_BY_PK);

			pstmt.setString(1, sitPNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitPhotoVO = new SitPhotoVO();
				sitPhotoVO.setSitPNo(rs.getString("sitPNo"));
				sitPhotoVO.setSitNo(rs.getString("sitNo"));
				sitPhotoVO.setSitPhoto(rs.getBytes("sitPhoto"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return sitPhotoVO;
	}

	@Override
	public List<SitPhotoVO> getAll(String sitNo) {
		List<SitPhotoVO> list = new ArrayList<SitPhotoVO>();
		SitPhotoVO sitPhotoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GET_ALL_PSTMT);
			
			pstmt.setString(1, sitNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				sitPhotoVO = new SitPhotoVO();
				sitPhotoVO.setSitPNo(rs.getString("sitPNo"));
				sitPhotoVO.setSitNo(rs.getString("sitNo"));
				sitPhotoVO.setSitPhoto(rs.getBytes("sitPhoto"));
				list.add(sitPhotoVO);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}	
	
//	public static void main(String[] args) {
//
//		SitPhotoJDBCDAO dao = new SitPhotoJDBCDAO();
//
//		// 新增
//		SitPhotoVO sitPhotoVO = new SitPhotoVO();
//		sitPhotoVO.setSitNo("S001");
//		byte[] pic;
//		try {
//			pic = getPictureByteArray("WebContent/images/pet.jpg");
//			sitPhotoVO.setSitPhoto(pic);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		dao.add(sitPhotoVO);
//		

//		// 刪除
//		dao.delete("SP005");
//
//		// 查詢
//		List<SitPhotoVO> list = dao.getAll();
//		for (SitPhotoVO sitPhoto : list) {
//			System.out.print(sitPhoto.getSitPhoto());
//			System.out.println();
//		}
//	}
//	
//
//	public static byte[] getPictureByteArray(String path) throws IOException {
//		File file = new File(path);
//		FileInputStream fis = new FileInputStream(file);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[8192];
//		int i;
//		while ((i = fis.read(buffer)) != -1) {
//			baos.write(buffer, 0, i);
//		}
//		baos.close();
//		fis.close();
//
//		return baos.toByteArray();
//	}
}
