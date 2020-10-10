package com.adoPet.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.adoPetAlbum.model.AdoPetAlbumService;
import com.adoPetAlbum.model.AdoPetAlbumVO;

public class AdoPetDAO implements AdoPetDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_ADOPET = "INSERT INTO ADOPET (ADOPETNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,PETWEIGHT,PETCAT,PETCHAR,LOCATION) VALUES('A'||lpad(ADOPET_SEQ.NEXTVAL,3,'0'),'0',?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_APPFORM = "UPDATE ADOPET SET MEMNO=?, APPFORM=? , ADOSTATUS='1' WHERE ADOPETNO=?";

	private static final String UPDATE_ADOPET = "UPDATE ADOPET SET ADOSTATUS=?, PETTYPE=?,PETNAME=?,PPETBREED=?,PETSEX=?,PETBIRTH=?,PETWEIGHT=?,PETCAT=?,PETCHAR=?,LOCATION=? WHERE ADOPETNO=?";

	private static final String UPDATE_ADOPET_STATUS = "UPDATE ADOPET SET ADOSTATUS=?,EMPNO=? WHERE ADOPETNO=?";
	private static final String SEARCH_ADOPET = "SELECT ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM (SELECT ADOPETNO,PETTYPE,PETNAME,ADOSTATUS,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET) WHERE ADOSTATUS='0'";

	private static final String MANAGE_BY_STATUS = "SELECT ADOPETNO,MEMNO,EMPNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION,APPFORM FROM ADOPET WHERE ADOSTATUS=?";
	//查詢單一寵物
	private static final String GET_ONE_ADOPET = "SELECT ADOPETNO,MEMNO,EMPNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION,APPFORM FROM ADOPET WHERE ADOPETNO=?";

	// 查詢全部未領養寵物
	private static final String GET_ALL_ADOPET = "SELECT ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET WHERE ADOSTATUS='0' ORDER BY ADOPETNO";
	private static final String INSERT_ADOPETALBUM = "INSERT INTO ADOPETALBUM(ADOPICNO,ADOPETNO,ADOPETPIC) VALUES('AP'||lpad(ADOPETALBUM_SEQ.NEXTVAL,3,'0'),?,?)";

	@Override
	public void insert(List<AdoPetAlbumVO> picArr, AdoPetVO adoPetVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String adoPetNo = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			String cols[] = { "ADOPETNO" };
			pstmt = con.prepareStatement(INSERT_ADOPET, cols);

			pstmt.setInt(1, adoPetVO.getPetType());
			pstmt.setString(2, adoPetVO.getPetName());
			pstmt.setString(3, adoPetVO.getPetBreed());
			pstmt.setInt(4, adoPetVO.getPetSex());
			pstmt.setDate(5, adoPetVO.getPetBirth());
			pstmt.setDouble(6, adoPetVO.getPetWeight());
			pstmt.setInt(7, adoPetVO.getPetCat());
			pstmt.setString(8, adoPetVO.getPetChar());
			pstmt.setString(9, adoPetVO.getLocation());

			pstmt.executeUpdate();

			/* 取得自增主鍵 ADOPETNO */
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				adoPetNo = rs.getString(1);

				System.out.println("寵物自增主鍵，adoPetNo=" + adoPetNo);
			} else {
				System.out.println("未取得寵物自增主鍵");
			}

			for (AdoPetAlbumVO adoPic : picArr) {
				pstmt.clearParameters();
				pstmt = con.prepareStatement(INSERT_ADOPETALBUM);
				pstmt.setString(1, adoPetNo);

				pstmt.setBytes(2, adoPic.getAdoPetPic());
				pstmt.executeUpdate();

			}

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException sq) {
			try {
				
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + sq.getMessage());
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

	}

	@Override
	public void update(AdoPetVO adoPetVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ADOPET);

			pstmt.setInt(1, adoPetVO.getAdoStatus());
			pstmt.setInt(2, adoPetVO.getPetType());
			pstmt.setString(3, adoPetVO.getPetName());
			pstmt.setString(4, adoPetVO.getPetBreed());
			pstmt.setInt(5, adoPetVO.getPetSex());
			pstmt.setDate(6, adoPetVO.getPetBirth());
			pstmt.setDouble(7, adoPetVO.getPetWeight());
			pstmt.setInt(8, adoPetVO.getPetCat());
			pstmt.setString(9, adoPetVO.getPetChar());
			pstmt.setString(10, adoPetVO.getLocation());
			pstmt.setString(11, adoPetVO.getAdoPetNo());
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
	public void insertAppForm(String memNo, String adoPetNo, byte[] appForm) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_APPFORM);

			pstmt.setString(1, memNo);
			pstmt.setBytes(2, appForm);
			pstmt.setString(3, adoPetNo);

			pstmt.executeUpdate();

		} catch (SQLException sq) {

			throw new RuntimeException("A database error occured. " + sq.getMessage());

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
	public AdoPetVO findByPrimaryKey(String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoPetVO adoPetVO = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ADOPET);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetVO = new AdoPetVO();
				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setMemNo(rs.getString("MEMNO"));
				adoPetVO.setEmpNo(rs.getString("EMPNO"));
				adoPetVO.setAdoStatus(rs.getInt("ADOSTATUS"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));

				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				adoPetVO.setAppForm(rs.getBytes("APPFORM"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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

		return adoPetVO;
	}

	@Override
	public List<AdoPetVO> SearchAdpPet(String sentence) {

		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH_ADOPET + sentence);
			
			System.out.println("DAO+String"+SEARCH_ADOPET + sentence);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				System.out.println(adoPetVO.getAdoPetNo());
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));
				adoPetVO.setAge(rs.getDouble("age"));
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

	@Override
	public List<AdoPetVO> getAll() {
		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ADOPET);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));

				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));

				adoPetVO.setAge(rs.getDouble("age"));
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

	@Override
	public List<AdoPetVO> findByAdoStatus(Integer adoStatus) {
		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MANAGE_BY_STATUS);
			pstmt.setInt(1, adoStatus);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setMemNo(rs.getString("MEMNO"));
				adoPetVO.setEmpNo(rs.getString("EMPNO"));

				adoPetVO.setAdoStatus(rs.getInt("ADOSTATUS"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));
				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				adoPetVO.setAppForm(rs.getBytes("APPFORM"));
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

	@Override
	public void update_status(Integer adoStatus,String empNo,String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ADOPET_STATUS);
			pstmt.setInt(1, adoStatus);
			pstmt.setString(2, empNo);
			pstmt.setString(3, adoPetNo);
			pstmt.executeQuery();
			
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

}
