package com.salonOrderDetail.model;

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

public class SalonOrderDetailDAO implements SalonOrderDetailDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT =
			"INSERT INTO SALONORDERDETAIL(SALORDERNO,SALSEVNO,GROOMERNO,SALSEVPR) VALUES(?,?,?,?)";
	private static final String DELETE =
			"DELETE FROM SALONORDERDETAIL WHERE SALORDERNO =?";
	private static final String GET_ALL_STMT=
			"SELECT SALORDERNO,SALSEVNO,GROOMERNO,SALSEVPR FROM SALONORDERDETAIL ORDER BY SALORDERNO";
	
	
	@Override
	public void insert(SalonOrderDetailVO salonOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonOrderDetailVO.getSalOrderNo());
			pstmt.setString(2,salonOrderDetailVO.getSalSevNo());
			pstmt.setString(3,salonOrderDetailVO.getGroomerNo());
			pstmt.setInt(4,salonOrderDetailVO.getSalSevPr());
			
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
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1,salOrderNo);
			
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
	public List<SalonOrderDetailVO> getall() {
		List<SalonOrderDetailVO> list =new ArrayList<SalonOrderDetailVO>();
		SalonOrderDetailVO salonOrderDetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				salonOrderDetailVO =new SalonOrderDetailVO();
				salonOrderDetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonOrderDetailVO.setSalSevNo(rs.getString("salSevNo"));
				salonOrderDetailVO.setGroomerNo(rs.getString("groomerNo"));
				salonOrderDetailVO.setSalSevPr(rs.getInt("salSevPr"));
				
				list.add(salonOrderDetailVO);
			}
		} catch (SQLException se) {
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
