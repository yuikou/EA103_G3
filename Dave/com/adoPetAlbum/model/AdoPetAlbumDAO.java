package com.adoPetAlbum.model;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;





public class AdoPetAlbumDAO implements AdoPetAlbumInterface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_ADOPETALBUM = "INSERT INTO ADOPETALBUM(ADOPICNO,ADOPETNO,ADOPETPIC) VALUES('AP'||lpad(ADOPETALBUM_SEQ.NEXTVAL,3,'0'),?,?)";
	private static final String DELETE_ADOPETALBUM = "DELETE FROM ADOPETALBUM WHERE ADOPICNO =?";
	private static final String GET_PICNO = "SELECT ADOPICNO,ADOPETPIC FROM ADOPETALBUM WHERE ADOPETNO=?";
	private static final String GET_PICS = "SELECT ADOPICNO,ADOPETPIC FROM ADOPETALBUM WHERE ADOPETNO=? AND ADOPICNO=?";
	
	@Override
	public void insert(AdoPetAlbumVO adoPetAlbumVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ADOPETALBUM);
			pstmt.setString(1, adoPetAlbumVO.getAdoPetNo());

			pstmt.setBytes(2, adoPetAlbumVO.getAdoPetPic());
			pstmt.executeUpdate();
			System.out.println("新增圖片成功");
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ADOPETALBUM);
			pstmt.setString(1, adoPicNo);

			pstmt.executeUpdate();
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
	public List<String> getPicNoByAdoPetNo(String adoPetNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PICNO);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
			String picNo = rs.getString("ADOPICNO");
				
			
				list.add(picNo);
			}

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
		AdoPetAlbumVO pic=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PICS);
			pstmt.setString(1, adoPetAlbumVO.getAdoPetNo());
			pstmt.setString(2, adoPetAlbumVO.getAdoPicNo());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pic = new AdoPetAlbumVO();
				pic.setAdoPetPic(rs.getBytes("ADOPETPIC"));
			
			}

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_PICNO);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetAlbumVO = new AdoPetAlbumVO();
				adoPetAlbumVO.setAdoPetPic(rs.getBytes("ADOPETPIC"));
				list.add(adoPetAlbumVO);
			}

		}catch (SQLException se) {
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

}
