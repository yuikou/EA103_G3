package com.adoReservation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdoReservationJDBCDAO implements AdoReservationDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA103G3";
	private static final String PASSWORD = "123456";
	private static final String INSERT_ADORESERVATION = "INSERT INTO ADORESERVATION(RESERVATIONNO,ADOPETNO,MEMNO,VISITDATE,RESERVATIONSTATUS)"
			+ "VALUES ('R'||lpad(ADORESERVATION_SEQ.NEXTVAL,3,'0'),?,?,?,'0')";

	private static final String UPDATE_ADORESERVATION = "UPDATE ADORESERVATION SET VISITDATE=? ,RESERVATIONSTATUS=? WHERE RESERVATIONNO=?";
	private static final String GET_ADORESERVATION_BYSTATUS = "SELECT RESERVATIONNO,ADOPETNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE RESERVATIONSTATUS=? AND MEMNO=? ORDER BY VISITDATE";
	private static final String GET_ALL_ADORESERVATION = "SELECT RESERVATIONNO,ADOPETNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE MEMNO=? ORDER BY VISITDATE";
	private static final String ORDER_HISTORY = "UPDATE ADORESERVATION SET RESERVATIONSTATUS='3' WHERE VISITDATE < sysdate";
	private static final String DELETE_ADORESERVATION = "UPDATE ADORESERVATION SET RESERVATIONSTATUS=? WHERE RESERVATIONNO=?";
	private static final String GET_ADORESERVATION_BYPETNO = "SELECT RESERVATIONNO,MEMNO,VISITDATE,RESERVATIONSTATUS FROM ADORESERVATION WHERE ADOPETNO=? ORDER BY VISITDATE";

	private static final String GET_ONE_ADORESERVATION = "SELECT * FROM ADORESERVATION WHERE RESERVATIONNO=?";

	@Override
	public String insert(AdoReservationVO adoReservationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String reservationNO = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_ADORESERVATION);

			pstmt.setDate(1, adoReservationVO.getVisitDate());
			pstmt.setInt(2, adoReservationVO.getReservationStatus());
			pstmt.setString(3, adoReservationVO.getReservationNO());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_ADORESERVATION);

			pstmt.setInt(1, reservationStatus);
			pstmt.setString(2, reservationNO);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ADORESERVATION_BYSTATUS);

			pstmt.setInt(1, reservationStatus);
			pstmt.setString(2, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
				list.add(adoReservationVO);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ADORESERVATION_BYPETNO);

			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoReservationVO = new AdoReservationVO();
				adoReservationVO.setReservationNO(rs.getString("RESERVATIONNO"));
				adoReservationVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoReservationVO.setAdoPetNo(rs.getString("MEMNO"));
				adoReservationVO.setVisitDate(rs.getDate("VISITDATE"));
				adoReservationVO.setReservationStatus(rs.getInt("RESERVATIONSTATUS"));
				list.add(adoReservationVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(ORDER_HISTORY);
			pstmt.executeUpdate();
			System.out.println("ResDAO完成訂單修改狀態為3(歷史訂單)");
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

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

	public static void main(String[] args) {
		AdoReservationJDBCDAO dao = new AdoReservationJDBCDAO();

		// 新增
//		AdoReservationVO adoReservationVO = new AdoReservationVO();
//		adoReservationVO.setAdoPetNo("A001");
//		adoReservationVO.setMemNo("M009");
//		adoReservationVO.setVisitDate(java.sql.Date.valueOf("2020-10-02"));
//		dao.insert(adoReservationVO);

		// 修改成取消狀態:1
//		AdoReservationVO adoReservationVO2 = new AdoReservationVO();
//		adoReservationVO2.setVisitDate(java.sql.Date.valueOf("2020-11-12"));
//		adoReservationVO2.setReservationStatus(1);
//		adoReservationVO2.setReservationNO("R004");
//		dao.update(adoReservationVO2);

		// 用狀態查詢

//		List<AdoReservationVO> list = dao.findByStatus(0,"M015");
//		for(AdoReservationVO adoReservationVO3:list) {
//		System.out.print(adoReservationVO3.getReservationNO()+", ");
//		System.out.print(adoReservationVO3.getAdoPetNo()+", ");
//		System.out.print(adoReservationVO3.getVisitDate()+", ");
//		System.out.print(adoReservationVO3.getReservationStatus()+", ");
//		System.out.println();
//		}

		// 查詢全部
//		List<AdoReservationVO> list2 = dao.getAll("M009");
//		for(AdoReservationVO adoReservationVO3:list2) {
//		System.out.print(adoReservationVO3.getReservationNO()+", ");
//		System.out.print(adoReservationVO3.getAdoPetNo()+", ");
//		System.out.print(adoReservationVO3.getVisitDate()+", ");
//		System.out.print(adoReservationVO3.getReservationStatus()+", ");
//		System.out.println();
//		}

	}

}
