package com.salonOrder.model;

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

public class SalonOrderDAO implements SalonOrderDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT=
			"INSERT INTO SALONORDER(salorderno,memno,petno,salno,salorderdate,saltp,orderstatus) VALUES ('BO' || LPAD(SALORDER_SEQ.NEXTVAL,3,'0'),?,?,?,?,?,?)";
	private static final String DELETE =
			"DELETE FROM SALONORDER WHERE salorderno = ?";
	private static final String UPDATE =
			"UPDATE SALONORDER SET memno=?, petno=?, salno=? , salorderdate=?, saltp=?, orderstatus=? WHERE salorderno=?" ;
	private static final String GET_ALL_STMT=
			"SELECT salorderno,memno,petno,salno,salorderdate,saltp,orderstatus FROM SALONORDER ORDER BY salorderno";
	@Override
	public void insert(SalonOrderVO salonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con =  ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonOrderVO.getMemNo());
			pstmt.setString(2,salonOrderVO.getPetNo());
			pstmt.setString(3,salonOrderVO.getSalNo());
			pstmt.setTimestamp(4,salonOrderVO.getSalOrderDate());
			pstmt.setInt(5,salonOrderVO.getSalTp());
			pstmt.setInt(6,salonOrderVO.getOrderStatus());
			
			pstmt.executeUpdate();
		} catch (SQLException se) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void update(SalonOrderVO salonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con =  ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,salonOrderVO.getMemNo());
			pstmt.setString(2,salonOrderVO.getPetNo());
			pstmt.setString(3,salonOrderVO.getSalNo());
			pstmt.setTimestamp(4,salonOrderVO.getSalOrderDate());
			pstmt.setInt(5,salonOrderVO.getSalTp());
			pstmt.setInt(6,salonOrderVO.getOrderStatus());
			pstmt.setString(7,salonOrderVO.getSalOrderNo());
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con =  ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, salOrderNo);
			
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
	public List<SalonOrderVO> getall() {
		List<SalonOrderVO> list = new ArrayList<SalonOrderVO>();
		SalonOrderVO salonOrderVO = null;
				
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con =  ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			salonOrderVO = new SalonOrderVO();
			
			salonOrderVO.setSalOrderNo(rs.getString("salOrderNo"));
			salonOrderVO.setMemNo(rs.getString("memNo"));
			salonOrderVO.setPetNo(rs.getString("petNo"));
			salonOrderVO.setSalNo(rs.getString("salNo"));
			salonOrderVO.setSalOrderDate(rs.getTimestamp("salOrderDate"));
			salonOrderVO.setSalTp(rs.getInt("salTp"));
			salonOrderVO.setOrderStatus(rs.getInt("orderStatus"));
			list.add(salonOrderVO);
			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
}
