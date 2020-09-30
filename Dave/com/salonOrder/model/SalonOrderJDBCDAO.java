package com.salonOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalonOrderJDBCDAO implements SalonOrderDAO_interface{
	String driver ="oracle.jdbc.driver.OracleDriver" ;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";	
	String userid = "EA103G3";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonOrderVO.getMemNo());
			pstmt.setString(2,salonOrderVO.getPetNo());
			pstmt.setString(3,salonOrderVO.getSalNo());
			pstmt.setTimestamp(4,salonOrderVO.getSalOrderDate());
			pstmt.setInt(5,salonOrderVO.getSalTp());
			pstmt.setInt(6,salonOrderVO.getOrderStatus());
			
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,salonOrderVO.getMemNo());
			pstmt.setString(2,salonOrderVO.getPetNo());
			pstmt.setString(3,salonOrderVO.getSalNo());
			pstmt.setTimestamp(4,salonOrderVO.getSalOrderDate());
			pstmt.setInt(5,salonOrderVO.getSalTp());
			pstmt.setInt(6,salonOrderVO.getOrderStatus());
			pstmt.setString(7,salonOrderVO.getSalOrderNo());
			
			pstmt.executeUpdate();
			
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, salOrderNo);
			
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors			 
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
	
	
	public static void main(String [] args) {
		SalonOrderJDBCDAO dao = new SalonOrderJDBCDAO();
		
		//新增
//		SalonOrderVO salonOrderVO1 = new SalonOrderVO();
//		salonOrderVO1.setMemNo("M001");
//		salonOrderVO1.setPetNo("P001");
//		salonOrderVO1.setSalNo("B002");
//		salonOrderVO1.setSalOrderDate(java.sql.Timestamp.valueOf("2020-05-23 011:35:00"));
//		salonOrderVO1.setSalTp(1380);
//		salonOrderVO1.setOrderStatus(0);
//				
//		dao.insert(salonOrderVO1);
		
		
		//修改
//		SalonOrderVO salonOrderVO2 = new SalonOrderVO();
//		salonOrderVO2.setSalOrderNo("BO005");
//		salonOrderVO2.setMemNo("M001");
//		salonOrderVO2.setPetNo("P001");
//		salonOrderVO2.setSalNo("B002");
//		salonOrderVO2.setSalOrderDate(java.sql.Timestamp.valueOf("2020-08-08 016:35:00"));
//		salonOrderVO2.setSalTp(1750);
//		salonOrderVO2.setOrderStatus(1);
//				
//		dao.update(salonOrderVO2);
		
		//刪除
		
//		dao.delete("BO006");
		
		//查詢
		
//		List<SalonOrderVO> list =dao.getall();
//		for(SalonOrderVO asalonOrder :list) {
//			System.out.println(asalonOrder.getSalOrderNo() +",");
//			System.out.println(asalonOrder.getMemNo() +",");
//			System.out.println(asalonOrder.getPetNo() +",");
//			System.out.println(asalonOrder.getSalNo() +",");
//			System.out.println(asalonOrder.getSalOrderDate() +",");
//			System.out.println(asalonOrder.getSalTp() +",");
//			System.out.println(asalonOrder.getOrderStatus() +",");
//			System.out.println("=========================================");
//		}
		
	} 
	
	
	
}
