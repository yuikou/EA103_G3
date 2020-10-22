package com.adoReservation.model;

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

public class AdoReservationDAO implements AdoReservationDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_ADORESERVATION = "INSERT INTO ADORESERVATION(RESERVATIONNO,ADOPETNO,MEMNO,VISITDATE,RESERVATIONSTATUS)"
			+ "VALUES ('R'||lpad(ADORESERVATION_SEQ.NEXTVAL,3,'0'),?,?,?,'0')";

	private static final String UPDATE_ADORESERVATION = "UPDATE ADORESERVATION SET VISITDATE=? ,RESERVATIONSTATUS=? WHERE RESERVATIONNO=?";
	private static final String DELETE_ADORESERVATION = "UPDATE ADORESERVATION SET RESERVATIONSTATUS=? WHERE RESERVATIONNO=?";
	private static final String GET_ADORESERVATION_BYSTATUS = "SELECT RESERVATIONNO,ADOPETNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE RESERVATIONSTATUS=? AND MEMNO=? ORDER BY VISITDATE";
	private static final String GET_ALL_ADORESERVATION = "SELECT RESERVATIONNO,ADOPETNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE MEMNO=? ORDER BY VISITDATE";
	private static final String GET_ONE_ADORESERVATION = "SELECT * FROM ADORESERVATION WHERE RESERVATIONNO=?";
	private static final String GET_ADORESERVATION_BYPETNO = "SELECT * FROM ADORESERVATION WHERE ADOPETNO=? AND  to_date(VISITDATE) >= (SELECT to_date(sysdate) from dual) ORDER BY VISITDATE";
//	private static final String GET_ADORESERVATION_BYPETNO = "SELECT RESERVATIONNO,MEMNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE ADOPETNO=? AND WHERE VISITDATE < sysdate ORDER BY VISITDATE";
	

	/* 排成器用 */
	private static final String ORDER_HISTORY = "UPDATE ADORESERVATION SET RESERVATIONSTATUS='3' WHERE VISITDATE < sysdate AND RESERVATIONSTATUS='0'";

	@Override
	public String insert(AdoReservationVO adoReservationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String reservationNO = null;
		try {
			con = ds.getConnection();
			String clos[] = { "RESERVATIONNO" };
			pstmt = con.prepareStatement(INSERT_ADORESERVATION, clos);

			pstmt.setString(1, adoReservationVO.getAdoPetNo());
			pstmt.setString(2, adoReservationVO.getMemNo());
			pstmt.setDate(3, adoReservationVO.getVisitDate());

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				reservationNO = rs.getString(1);
				System.out.println("reservationNO" + reservationNO);
			} else {
				System.out.println("未取得訂單自增主鍵");
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		}
		return reservationNO;
	}

	@Override
	public void update(AdoReservationVO adoReservationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ADORESERVATION);

			pstmt.setDate(1, adoReservationVO.getVisitDate());
			pstmt.setInt(2, adoReservationVO.getReservationStatus());
			pstmt.setString(3, adoReservationVO.getReservationNO());
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
	public void delete( Integer reservationStatus,String reservationNO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ADORESERVATION);

			pstmt.setInt(1, reservationStatus);
			pstmt.setString(2, reservationNO);
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
	public List<AdoReservationVO> findByStatus(Integer reservationStatus, String memNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoReservationVO> list = new ArrayList<AdoReservationVO>();
		AdoReservationVO adoReservationVO = null;
		System.out.println(memNo+"近來DAO");
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ADORESERVATION_BYSTATUS);

			pstmt.setInt(1, reservationStatus);
			pstmt.setString(2, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				System.out.println("包裝VO");
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setMemNo(memNo);
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
				list.add(adoReservationVO);
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

		return list;
	}

	@Override
	public List<AdoReservationVO> getAll(String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoReservationVO> list = new ArrayList<AdoReservationVO>();
		AdoReservationVO adoReservationVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ADORESERVATION);

			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
				list.add(adoReservationVO);
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

		return list;
	}

	@Override
	public AdoReservationVO getOneRes(String reservationNO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoReservationVO adoReservationVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ADORESERVATION);

			pstmt.setString(1, reservationNO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setMemNo(rs.getString("MEMNO"));
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
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

		return adoReservationVO;
	}

	
	@Override
	public List<AdoReservationVO> getAllResByPetno(String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoReservationVO> list = new ArrayList<AdoReservationVO>();
		AdoReservationVO adoReservationVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ADORESERVATION_BYPETNO);

			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setMemNo(rs.getString("MEMNO"));
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
				list.add(adoReservationVO);
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

		return list;
	}
	
	@Override
	public void resSchedule() {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ORDER_HISTORY);
			pstmt.executeUpdate();
			System.out.println("ResDAO完成訂單修改狀態為3(歷史訂單)");
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

}
