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
	private static final String GET_All_BY_ORDERNO = 
			"SELECT * FROM SALONORDERDETAIL WHERE SALORDERNO = ? ORDER BY SALORDERNO";

//新增訂單明細(取得新增訂單同一條連線)	
	@Override
	public void insertSalonOD(SalonOrderDetailVO salOD, Connection con) {
		PreparedStatement pstmt = null;
		System.out.println(123);

		try {
			System.out.println(123);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, 	salOD.getSalOrderNo());
			pstmt.setString(2, salOD.getSalSevNo());
			pstmt.setString(3, salOD.getGroomerNo());
			pstmt.setInt(4, salOD.getSalSevPr());
		
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderDetail");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}
	}

	@Override
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salOrderNo);

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
	public List<SalonOrderDetailVO> getAllBySalOrderNo(String salOrderNo) {
		List<SalonOrderDetailVO> list = new ArrayList<SalonOrderDetailVO>();
		SalonOrderDetailVO salonOrderDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_All_BY_ORDERNO);

			pstmt.setString(1, salOrderNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				salonOrderDetailVO = new SalonOrderDetailVO();
				salonOrderDetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonOrderDetailVO.setGroomerNo(rs.getString("groomerNo"));
				salonOrderDetailVO.setSalSevNo(rs.getString("salSevNo"));
				salonOrderDetailVO.setSalSevPr(rs.getInt("salSevPr"));
				

				list.add(salonOrderDetailVO);
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
}
