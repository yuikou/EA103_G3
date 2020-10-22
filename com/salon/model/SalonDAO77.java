package com.salon.model;

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

public class SalonDAO77 implements SalonDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO SALON(SALNO,SALNAME,SALOWNER,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALINFO,SALPETTYPE,SALCERTIF) VALUES('B' || lpad(SALNO_SEQ.NEXTVAL,3,'0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM SALON WHERE SALNO = ?";
	private static final String UPDATE = "UPDATE SALON SET salname=?, salowner=?, salph=? , salmail=?, salCity=?,salDist=?,saladr=?, salstime=?, saletime=?, salremit=?, bankcode=?,  salinfo=?,salpettype=?,salCertif=? WHERE salno=?";
	private static final String GET_ALL_STMT = "SELECT SALNO,SALNAME,SALOWNER,SALPIC,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALTOTALSCORE,SALINFO,SALPETTYPE FROM SALON ORDER BY SALNO";
	private static final String GET_ONE_STMT = "SELECT SALNO,SALNAME,SALOWNER,SALPIC,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALINFO,SALPETTYPE,SALCERTIF FROM SALON  WHERE SALNO = ?";
	private static final String LOGIN = "SELECT * FROM salon WHERE SALAC=? AND SALPW=?";
	private static final String UPDATE_STATUS_STMT = "UPDATE SALON SET SALSTATUS = ? WHERE SALNO = ?";
	private static final String CHECKAC = "SELECT SALAC FROM SALON WHERE SALAC=?";

	@Override
	public void insert(SalonVO salonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, salonVO.getSalName());
			pstmt.setString(2, salonVO.getSalOwner());
			pstmt.setString(3, salonVO.getSalPh());
			pstmt.setString(4, salonVO.getSalMail());
			pstmt.setString(5, salonVO.getSalCity());
			pstmt.setString(6, salonVO.getSalDist());
			pstmt.setString(7, salonVO.getSalAdr());
			pstmt.setString(8, salonVO.getSalAc());
			pstmt.setString(9, salonVO.getSalPw());
			pstmt.setString(10, salonVO.getSalSTime());
			pstmt.setString(11, salonVO.getSalETime());
			pstmt.setString(12, salonVO.getSalRemit());
			pstmt.setString(13, salonVO.getBankCode());
			pstmt.setInt(14, salonVO.getSalStatus());
			pstmt.setString(15, salonVO.getSalInfo());
			pstmt.setInt(16, salonVO.getSalPetType());
			pstmt.setBytes(17, salonVO.getSalCertif());

			pstmt.execute();

		} catch (SQLException se) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(SalonVO salonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, salonVO.getSalName());
			pstmt.setString(2, salonVO.getSalOwner());
			pstmt.setString(3, salonVO.getSalPh());
			pstmt.setString(4, salonVO.getSalMail());
			pstmt.setString(5, salonVO.getSalCity());
			pstmt.setString(6, salonVO.getSalDist());
			pstmt.setString(7, salonVO.getSalAdr());
			pstmt.setString(8, salonVO.getSalSTime());
			pstmt.setString(9, salonVO.getSalETime());
			pstmt.setString(10, salonVO.getSalRemit());
			pstmt.setString(11, salonVO.getBankCode());
			pstmt.setString(12, salonVO.getSalInfo());
			pstmt.setInt(13, salonVO.getSalPetType());
			pstmt.setBytes(14, salonVO.getSalCertif());
			pstmt.setString(15, salonVO.getSalNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String salNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salNo);

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<SalonVO> getall() {
		List<SalonVO> list = new ArrayList<SalonVO>();
		SalonVO salonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				salonVO = new SalonVO();

				salonVO.setSalNo(rs.getString("salNo"));
				salonVO.setSalName(rs.getString("salName"));
				salonVO.setSalOwner(rs.getString("salOwner"));
				salonVO.setSalPic(rs.getBytes("salPic"));
				salonVO.setSalPh(rs.getString("salPh"));
				salonVO.setSalMail(rs.getString("salMail"));
				salonVO.setSalCity(rs.getString("salCity"));
				salonVO.setSalDist(rs.getString("salDist"));
				salonVO.setSalAdr(rs.getString("salAdr"));
				salonVO.setSalAc(rs.getString("salAc"));
				salonVO.setSalPw(rs.getString("salPw"));
				salonVO.setSalSTime(rs.getString("salSTime"));
				salonVO.setSalETime(rs.getString("salETime"));
				salonVO.setSalRemit(rs.getString("salRemit"));
				salonVO.setBankCode(rs.getString("bankCode"));
				salonVO.setSalStatus(rs.getInt("salStatus"));
				salonVO.setSalInfo(rs.getString("salInfo"));
				salonVO.setSalPetType(rs.getInt("salPetType"));
				salonVO.setSalTotalScore(rs.getInt("salTotalScore"));

				list.add(salonVO);

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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

	@Override
	public SalonVO findByPrimaryKey(String salNo) {
		SalonVO salonVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, salNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				salonVO = new SalonVO();
				salonVO.setSalNo(rs.getString("salNo"));
				salonVO.setSalName(rs.getString("salName"));
				salonVO.setSalOwner(rs.getString("salOwner"));
				salonVO.setSalPh(rs.getString("salPh"));
				salonVO.setSalPic(rs.getBytes("salPic"));
				salonVO.setSalMail(rs.getString("salMail"));
				salonVO.setSalCity(rs.getString("salCity"));
				salonVO.setSalDist(rs.getString("salDist"));
				salonVO.setSalAdr(rs.getString("salAdr"));
				salonVO.setSalAc(rs.getString("salAc"));
				salonVO.setSalPw(rs.getString("salPw"));
				salonVO.setSalSTime(rs.getString("salSTime"));
				salonVO.setSalETime(rs.getString("salETime"));
				salonVO.setSalRemit(rs.getString("salRemit"));
				salonVO.setBankCode(rs.getString("bankCode"));
				salonVO.setSalStatus(rs.getInt("salStatus"));
				salonVO.setSalInfo(rs.getString("salInfo"));
				salonVO.setSalPetType(rs.getInt("salPetType"));
				salonVO.setSalCertif(rs.getBytes("salCertif"));
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
		return salonVO;
	}

	@Override
	public void updateStatus(String salNo, Integer salStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalonVO login(String salAc, String salPw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkAc(String salAc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<SalonVO> findByCondition(String salCity, String salDist, Integer salPetType) {
		// TODO Auto-generated method stub
		return null;
	}

}