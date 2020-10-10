package com.eventRep.model;

import java.sql.*;
import java.util.*;

public class EventRepJDBCDAO implements EventRepDAO_interface{
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "EA103G3";
	private static String passwd = "123456";

	private static final String ADD_PSTMT = "INSERT INTO eventRep VALUES ('ER' || lpad(eventRepNo_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?) ";
	private static final String UPDATE_PSTMT = "UPDATE eventRep SET eventRepStatus=?, eventRepResult=? WHERE eventRepNo=? ";
	private static final String GET_ONE_PSTMT = "SELECT * FROM  eventRep WHERE eventRepNo=? ";
	private static final String GET_ALL_PSTMT = "SELECT * FROM  eventRep WHERE eventRepStatus=? OR eventRepResult=?";
	
	@Override
	public Boolean add(EventRepVO eventRep) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, eventRep.getEventNo());
			pstmt.setString(2, eventRep.getMemNo());
			pstmt.setString(3, eventRep.getEventRep());
			pstmt.setInt(4, eventRep.getEventRepStatus());
			pstmt.setObject(5, eventRep.getEventRepResult());
			if ( pstmt.executeUpdate() == 1) {
				addOK = true;
			}
			con.commit();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. "+e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return addOK;
	}

	@Override
	public Boolean update(EventRepVO eventRep) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_PSTMT);
			pstmt.setInt(1, eventRep.getEventRepStatus());
			pstmt.setObject(2, eventRep.getEventRepResult());
			pstmt.setString(3, eventRep.getEventRepNo());
			
			if ( pstmt.executeUpdate() == 1) {
				updateOK = true;
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. "+e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateOK;
	}

	@Override
	public EventRepVO getOne(String eventRepNo) {
		EventRepVO eventRep = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_ONE_PSTMT);
			pstmt.setString(1, eventRepNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				eventRep = new EventRepVO();
				eventRep.setEventRepNo(rs.getString(1));
				eventRep.setEventNo(rs.getString(2));
				eventRep.setMemNo(rs.getString(3));
				eventRep.setEventRep(rs.getString(4));
				eventRep.setEventRepStatus(rs.getInt(5));
				eventRep.setEventRepResult(rs.getInt(6));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. "+e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return eventRep;
	}

	@Override
	public List<EventRepVO> getAll(Integer eventRepStatus, Integer eventRepResult) {
		List<EventRepVO> list = new ArrayList<EventRepVO>();
		EventRepVO eventRep = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_ALL_PSTMT);
			pstmt.setObject(1, eventRepStatus);
			pstmt.setObject(2, eventRepResult);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eventRep = new EventRepVO();
				eventRep.setEventRepNo(rs.getString(1));
				eventRep.setEventNo(rs.getString(2));
				eventRep.setMemNo(rs.getString(3));
				eventRep.setEventRep(rs.getString(4));
				eventRep.setEventRepStatus(rs.getInt(5));
				eventRep.setEventRepResult(rs.getObject(6));
				list.add(eventRep);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. "+e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		EventRepJDBCDAO jdbcDAO = new EventRepJDBCDAO();
		
		// 新增
//		EventRepVO eventRep = new EventRepVO();
//		eventRep.setEventNo("E001");
//		eventRep.setEventRep("GGYY");
//		eventRep.setMemNo("M008");
//		eventRep.setEventRepStatus(0);
//		
//		if ( jdbcDAO.add(eventRep) ) {
//			System.out.println("新增成功");
//		}
		// 修改
		EventRepVO eventRep2 = new EventRepVO();
		eventRep2.setEventRepStatus(1);
		eventRep2.setEventRepResult(0);
		eventRep2.setEventRepNo("ER007");
		
		if ( jdbcDAO.update(eventRep2) ) {
			System.out.println("修改成功");
		}
		
		// 查詢1筆
		EventRepVO evRep = jdbcDAO.getOne("ER007");
		System.out.println("EventRepNo = " + evRep.getEventRepNo());
		System.out.println("MemNo = " + evRep.getMemNo());
		System.out.println("EventNo = " + evRep.getEventNo());
		System.out.println("EventRep = " + evRep.getEventRep());
		System.out.println("EventRepStatus = " + evRep.getEventRepStatus());
		System.out.println("EventRepResult = " + evRep.getEventRepResult());
		// 查詢多筆
		
		List<EventRepVO> list = new ArrayList<EventRepVO>();
		list = jdbcDAO.getAll(0,null);
		
		System.out.println("----------------------------------------------");
		for (EventRepVO evRep2 : list) {
			System.out.println("EventRepNo = " + evRep2.getEventRepNo());
			System.out.println("MemNo = " + evRep2.getMemNo());
			System.out.println("EventNo = " + evRep2.getEventNo());
			System.out.println("EventRep = " + evRep2.getEventRep());
			System.out.println("EventRepStatus = " + evRep2.getEventRepStatus());
			System.out.println("EventRepResult = " + evRep2.getEventRepResult());
			System.out.println("----------------------------------------------");
		}
	}
}
