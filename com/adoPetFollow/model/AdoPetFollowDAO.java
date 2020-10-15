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
	private static final String GET_ALL_AdoPetFollow = "SELECT ROWNUM,A.ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET A "
			+ "JOIN ADOPETFOLLOW AW ON A.ADOPETNO = AW.ADOPETNO WHERE AW.MEMNO IN(SELECT MEMNO FROM MEMBER_TABLE WHERE MEMNO=?) AND ADOSTATUS='0' ORDER BY ROWNUM DESC";

	@Override
	public void insert(AdoPetFollowVO adoPetFollowVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_AdoPetFollow);

			pstmt.setString(1, adoPetFollowVO.getMemNo());
			pstmt.setString(2, adoPetFollowVO.getAdoPetNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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

	}

	@Override
	public void delete(String memNo,String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_AdoPetFollow);
			pstmt.setString(1, memNo);
			pstmt.setString(2, adoPetNo);
			pstmt.executeUpdate();

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
	}

	@Override
	public List<AdoPetVO> getAll(String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AdoPetFollow);

			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));

				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));

				adoPetVO.setAge(rs.getString("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				list.add(adoPetVO);
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
