package com.goffday.model;

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

public class GODayDAO implements GODayDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO GROOMEROFFDAY (offno,groomerno,offday,offtime,offdaytype) VALUES ('GD' || lpad(GMOFF_SEQ.NEXTVAL, 3, 0),?, ?, ?, ?)";
	// 查詢單一美容師的所有offday
	private static final String GET_ALL_STMT = "SELECT offno, groomerno, offday, offtime, offdaytype FROM GROOMEROFFDAY where groomerno=? ";
	private static final String GET_ONE_STMT = "SELECT offno, groomerno, offday, offtime, offdaytype FROM GROOMEROFFDAY where offno=? ";
	private static final String DELETE = "DELETE from GROOMEROFFDAY where offno=?";
	private static final String UPDATE = "UPDATE GROOMEROFFDAY set groomerno=?, offday=?, offtime=?, offdaytype=? where offno=?";
	// UPDATE GROOMEROFFDAY set offday=to_date('2020-12-25 18:30' ,'yyyy-mm-dd
	// HH24:MI'), offdaytype=1 where offno='GD007';

	@Override
	public void insert(GODayVO godayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, godayVO.getGroomerNo());
			pstmt.setString(2, godayVO.getOffDay());
			pstmt.setString(3, godayVO.getOffTime());
			pstmt.setInt(4, godayVO.getOffDayType());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(GODayVO godayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, godayVO.getGroomerNo());
			pstmt.setString(2, godayVO.getOffDay());
			pstmt.setString(3, godayVO.getOffTime());
			pstmt.setInt(4, godayVO.getOffDayType());
			pstmt.setString(5, godayVO.getOffNo());	
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String offno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, offno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<GODayVO> getAll(String groomerno) {
		List<GODayVO> list = new ArrayList<GODayVO>();
		GODayVO godayVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, groomerno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				godayVO = new GODayVO();
				godayVO.setOffNo(rs.getString("offno"));
				godayVO.setGroomerNo(rs.getString("groomerno"));
				godayVO.setOffDay(rs.getString("offday"));
				godayVO.setOffTime(rs.getString("offtime"));
				godayVO.setOffDayType(rs.getInt("offdaytype"));
				
				list.add(godayVO);
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
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
	
	public GODayVO findByPrimaryKey(String offno) {
		GODayVO godayVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, offno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				godayVO = new GODayVO();
				godayVO.setOffNo(rs.getString("offno"));
				godayVO.setGroomerNo(rs.getString("groomerno"));
				godayVO.setOffDay(rs.getString("offday"));
				godayVO.setOffTime(rs.getString("offtime"));
				godayVO.setOffDayType(rs.getInt("offdaytype"));
				
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
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
		return godayVO;
	}

}
