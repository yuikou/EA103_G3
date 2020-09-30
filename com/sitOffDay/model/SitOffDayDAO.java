package com.sitOffDay.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

public class SitOffDayDAO implements SitOffDayDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String ADD_PSTMT 			= "INSERT INTO sitOffDay VALUES ('SD' || lpad(offDayNo_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?)";
	private static final String UPDATE_PSTMT 		= "UPDATE sitOffDay SET offDay=?, offTime=? WHERE offDayNo=? ";
	private static final String DEL_PSTMT 			= "DELETE sitOffDay WHERE offDayNo=? ";
	private static final String GET_BY_OFFDAYNO 	= "SELECT * FROM sitOffDay WHERE offDayNo=? ";
	private static final String GET_SIT_BY_OFFDATE 	= "SELECT sitSrvNo FROM sitOffDay WHERE offDay  BETWEEN to_date(?, 'yyyy-mm-dd') "
													+ "AND TO_DATE(?, 'yyyy-mm-dd') "
													+ "AND offTime=? "
													+ "AND sitSrvNo IN (SELECT sitSrvNo FROM sitSrv WHERE sitSrvCode=?)";
	
	@Override
	public Boolean add(SitOffDayVO sod) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, sod.getSitSrvNo());
			pstmt.setDate(2, sod.getOffDay());
			pstmt.setObject(3, sod.getOffTime());
			pstmt.setInt(4, sod.getOffDayTyp());
			
			if ( pstmt.executeUpdate() == 1) {
				addOK = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGG SQLEXC" + e.getMessage());
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
	public Boolean update(SitOffDayVO sod) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);
			pstmt.setDate(1, sod.getOffDay());
			pstmt.setObject(2, sod.getOffTime());
			pstmt.setString(3, sod.getOffDayNo());
			
			if ( pstmt.executeUpdate() == 1) {
				updateOK = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGG SQLEXC" + e.getMessage());
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
	public Boolean del(String offDayNo) {
		Boolean delOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DEL_PSTMT);
			pstmt.setString(1, offDayNo);
			if ( pstmt.executeUpdate() == 1) {
				delOK = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGG SQLEXC" + e.getMessage());
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
		return delOK;
	}

	@Override
	public SitOffDayVO getByPK(String offDayNo) {
		SitOffDayVO sod = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_OFFDAYNO);
			pstmt.setString(1, offDayNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sod = new SitOffDayVO();
				sod.setOffDayNo(rs.getString(1));
				sod.setSitSrvNo(rs.getString(2));
				sod.setOffDay(rs.getDate(3));
				sod.setOffTime(rs.getString(4));
				sod.setOffDayTyp(rs.getInt(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGGGG不知道說什麼了" + e.getMessage());
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
		return sod;
	}

	@Override
	public Set<String> getSitByDate(String sitSrvCode, String start_date, String end_date, String time) {
		Set<String> set = new HashSet<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SIT_BY_OFFDATE);
			pstmt.setString(1, start_date);
			pstmt.setString(2, end_date);
			pstmt.setString(3, time);
			pstmt.setString(4, sitSrvCode);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				set.add(rs.getString("sitSrvNo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGGGG不知道說什麼了" + e.getMessage());
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
		return set;
	}
}
