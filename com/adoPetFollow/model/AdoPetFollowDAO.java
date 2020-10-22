package com.adoPetFollow.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.adoPet.model.AdoPetVO;

public class AdoPetFollowDAO implements AdoPetFollowDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_AdoPetFollow = "INSERT INTO ADOPETFOLLOW(MEMNO,ADOPETNO) VALUES (?,?)";
	private static final String DELETE_AdoPetFollow = "DELETE FROM ADOPETFOLLOW WHERE MEMNO=? AND ADOPETNO=?";
	private static final String GET_ALL_AdoPetFollow = "SELECT * FROM ADOPETFOLLOW WHERE MEMNO=?";
			
//			"SELECT ROWNUM,A.ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET A "
//			+ "JOIN ADOPETFOLLOW AW ON A.ADOPETNO = AW.ADOPETNO WHERE AW.MEMNO IN(SELECT MEMNO FROM MEMBER_TABLE WHERE MEMNO=?) AND ADOSTATUS='0' ORDER BY ROWNUM DESC";

	@Override
	public Boolean insert(AdoPetFollowVO adoPetFollowVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Boolean insertFinished = false;
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_AdoPetFollow);

			pstmt.setString(1, adoPetFollowVO.getMemNo());
			pstmt.setString(2, adoPetFollowVO.getAdoPetNo());

			if (pstmt.executeUpdate() == 1) {
				insertFinished = true;
				con.commit();
			}
		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		}
		return insertFinished;
	}

	@Override
	public Boolean delete(String memNo,String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Boolean deleteFinished = false;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_AdoPetFollow);
			pstmt.setString(1, memNo);
			pstmt.setString(2, adoPetNo);

			if (pstmt.executeUpdate() == 1) {
				deleteFinished = true;
				con.commit();
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return deleteFinished;
	}

	@Override
	public List<AdoPetFollowVO> getAll(String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoPetFollowVO> list = new ArrayList<AdoPetFollowVO>();
		AdoPetFollowVO adoPetFollowVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AdoPetFollow);

			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetFollowVO = new AdoPetFollowVO();

				adoPetFollowVO.setMemNo(rs.getString("MEMNO"));
				adoPetFollowVO.setAdoPetNo(rs.getString("ADOPETNO"));
				list.add(adoPetFollowVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

}
