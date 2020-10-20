package com.adoPetAlbum.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdoPetAlbumJDBCDAO implements AdoPetAlbumInterface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA103G3";
	private static final String PASSWORD = "123456";

	private static final String INSERT_ADOPETALBUM = "INSERT INTO ADOPETALBUM(ADOPICNO,ADOPETNO,ADOPETPIC) VALUES('AP'||lpad(ADOPETALBUM_SEQ.NEXTVAL,3,'0'),?,?)";
	private static final String DELETE_ADOPETALBUM = "DELETE FROM ADOPETALBUM WHERE ADOPICNO =?";
	private static final String GET_PICNO = "SELECT ADOPICNO,ADOPETPIC FROM ADOPETALBUM WHERE ADOPETNO=?";
	private static final String GET_PICS = "SELECT ADOPICNO,ADOPETPIC FROM ADOPETALBUM WHERE ADOPETNO=? AND ADOPICNO=?";
//	private static final String UPDATE_ADOPETALBUM ="UPDATE ADOPETALBUM SET ADOPETPIC=? WHERE ADOPICNO=?";

	@Override
	public void insert(AdoPetAlbumVO adoPetAlbumVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_ADOPETALBUM);
			pstmt.setString(1, adoPetAlbumVO.getAdoPetNo());

			pstmt.setBytes(2, adoPetAlbumVO.getAdoPetPic());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}
	}

	@Override
	public void delete(String adoPicNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_ADOPETALBUM);
			pstmt.setString(1, adoPicNo);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}

	}

	public List<String> getPicNoByAdoPetNo(String adoPetNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_PICNO);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String picNo = rs.getString("ADOPICNO");

				list.add(picNo);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}

		return list;
	}

	@Override
	public AdoPetAlbumVO getPic(AdoPetAlbumVO adoPetAlbumVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoPetAlbumVO pic = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_PICS);
			pstmt.setString(1, adoPetAlbumVO.getAdoPetNo());
			pstmt.setString(2, adoPetAlbumVO.getAdoPicNo());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pic = new AdoPetAlbumVO();
				pic.setAdoPetPic(rs.getBytes("ADOPETPIC"));

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}

		return pic;

	}

	@Override
	public List<AdoPetAlbumVO> getPicList(String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoPetAlbumVO adoPetAlbumVO = null;
		List<AdoPetAlbumVO> list = new ArrayList<AdoPetAlbumVO>();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_PICNO);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetAlbumVO = new AdoPetAlbumVO();
				adoPetAlbumVO.setAdoPetPic(rs.getBytes("ADOPETPIC"));
				list.add(adoPetAlbumVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}

		return list;
	}

//	public void update(AdoPetAlbumVO adoPetAlbumVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		
//		
//		try {
//			Class.forName(DRIVER);
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstmt = con.prepareStatement(INSERT_ADOPETALBUM);
//			pstmt.setBytes(1, adoPetAlbumVO.getAdoPetPic());
//			pstmt.setString(2, adoPetAlbumVO.getAdoPicNo());
//
//			pstmt.executeUpdate();
//			System.out.println("UPDATE OK");
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
//		} catch (SQLException sq) {
//			try {
//				con.rollback();
//				sq.printStackTrace();
//			} catch (SQLException sqRollback) {
//				throw new RuntimeException("A database error occured. " + sqRollback.getMessage());
//
//			} finally {
//
//				if (pstmt != null) {
//					try {
//						pstmt.close();
//					} catch (SQLException sqq) {
//
//						sqq.printStackTrace(System.err);
//					}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (SQLException sqt) {
//
//						sqt.printStackTrace(System.err);
//					}
//
//				}
//			}
//		}
//	}

	public static void main(String[] args) {
		AdoPetAlbumJDBCDAO dao = new AdoPetAlbumJDBCDAO();
//		dao.delete("AP008");

		List<String> list = dao.getPicNoByAdoPetNo("A001");
		for (String adoVO : list) {
			System.out.println(adoVO);

		}

	}

}
