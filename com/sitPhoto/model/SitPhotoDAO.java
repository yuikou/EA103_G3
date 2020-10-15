package com.sitPhoto.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.petSitter.model.PetSitterVO;

public class SitPhotoDAO implements SitPhotoDAO_interface {

	private static DataSource ds = null;
	static {
		Context ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String ADD_PSTMT = "INSERT INTO sitPhoto (sitPNo, sitNo, sitPhoto) VALUES ('SP' || lpad(sitPhoto_SEQ.NEXTVAL, 3, '0'), ?, ?)";
	private static final String DELETE_PSTMT = "DELETE FROM sitPhoto WHERE sitPNo = ?";
	private static final String GET_ALL_PSTMT = "SELECT * FROM sitPhoto WHERE sitNo = ?";
	private static final String GET_BY_PK = "SELECT * FROM sitPhoto WHERE sitPNo = ?";
			
	@Override
	public void add(SitPhotoVO sitPhotoVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_PSTMT);

			pstmt.setString(1, sitPhotoVO.getSitNo());
			pstmt.setBytes(2, sitPhotoVO.getSitPhoto());
			pstmt.executeUpdate();

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
			con = ds.getConnection();

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_PSTMT);
			pstmt.setString(1, sitPNo);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

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

			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
}
