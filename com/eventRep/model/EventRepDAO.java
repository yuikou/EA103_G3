package com.eventRep.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

public class EventRepDAO implements EventRepDAO_interface{
	private static DataSource ds = null;
	static {
		Context ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PETFECT");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, eventRep.getEventNo());
			pstmt.setString(2, eventRep.getMemNo());
			pstmt.setString(3, eventRep.getEventRep());
			pstmt.setInt(4, eventRep.getEventRepStatus());
			pstmt.setObject(5, eventRep.getEventRepResult());
			if ( pstmt.executeUpdate() == 1) {
				addOK = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. "+e.getMessage());
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

	// 如果修改EventRepResult=1，就要將event.eventStatus改為1
	@Override
	public Boolean update(EventRepVO eventRep) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);
			pstmt.setInt(1, eventRep.getEventRepStatus());
			pstmt.setObject(2, eventRep.getEventRepResult());
			pstmt.setString(3, eventRep.getEventRepNo());
			
			if ( pstmt.executeUpdate() == 1) {
				updateOK = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. "+e.getMessage());
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

}
