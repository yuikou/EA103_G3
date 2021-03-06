package com.salonOrderDetail.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.salonOrder.model.SalonOrderVO;

public class SalonOrderDetailJDBCDAO implements SalonOrderDetailDAO_interface{
	String driver ="oracle.jdbc.driver.OracleDriver" ;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";	
	String userid = "EA103G3";
	String passwd = "123456";

	private static final String INSERT_STMT =
			"INSERT INTO SALONORDERDETAIL(SALORDERNO,SALSEVNO,GROOMERNO,SALSEVPR,OFFNO) VALUES(?,?,?,?,?)";
	private static final String DELETE =
			"DELETE FROM SALONORDERDETAIL WHERE SALORDERNO =?";
	private static final String GET_ALL_STMT=
			"SELECT SALORDERNO,SALSEVNO,GROOMERNO,SALSEVPR FROM SALONORDERDETAIL ORDER BY SALORDERNO";
	private static final String GET_All_BY_ORDERNO =
			"SELECT * FROM SALONORDERDETAIL WHERE SALORDERNO = ? ORDER BY SALORDERNO";
	
	
	@Override
	public void insert(SalonOrderDetailVO salonOrderDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonOrderDetailVO.getSalOrderNo());
			pstmt.setString(2,salonOrderDetailVO.getSalSevNo());
			pstmt.setString(3,salonOrderDetailVO.getGroomerNo());
			pstmt.setInt(4,salonOrderDetailVO.getSalSevPr());
			
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
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1,salOrderNo);
			
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
	public List<SalonOrderDetailVO> getall() {
		List<SalonOrderDetailVO> list =new ArrayList<SalonOrderDetailVO>();
		SalonOrderDetailVO salonOrderDetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
	@Override
	public void insertSalonOrderDetail(SalonOrderDetailVO salonOrderDetailVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonOrderDetailVO.getSalOrderNo());
			pstmt.setString(2,salonOrderDetailVO.getSalSevNo());
			pstmt.setString(3,salonOrderDetailVO.getGroomerNo());
			pstmt.setInt(4,salonOrderDetailVO.getSalSevPr());
			pstmt.setString(5,salonOrderDetailVO.getOffNo());
			
			pstmt.executeUpdate();
			
			
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderDetail");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_All_BY_ORDERNO);
			
			pstmt.setString(1, salOrderNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				salonOrderDetailVO = new SalonOrderDetailVO();
				salonOrderDetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonOrderDetailVO.setGroomerNo(rs.getString("groomerNo"));
				salonOrderDetailVO.setSalSevNo(rs.getString("salSevNo"));
				salonOrderDetailVO.setSalSevPr(rs.getInt("salSevPr"));
				salonOrderDetailVO.setOffNo(rs.getString("offNo"));
				
				list.add(salonOrderDetailVO);
								
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
	
	@Override
	public List<SalonOrderDetailVO> getAllByMemNo(String memNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String [] args) {
		SalonOrderDetailJDBCDAO dao= new SalonOrderDetailJDBCDAO();
		
		//新增
		
//		SalonOrderDetailVO salonOrderDetailVO1 = new SalonOrderDetailVO();
//		
//		salonOrderDetailVO1.setSalOrderNo("BO003");
//		salonOrderDetailVO1.setSalSevNo("BS003");
//		salonOrderDetailVO1.setGroomerNo("G004");
//		salonOrderDetailVO1.setSalSevPr(800);
//		
//		dao.insert(salonOrderDetailVO1);
		
		//刪除	
//		dao.delete("BO004");
		
		//查詢
		
//		List<SalonOrderDetailVO> list = dao.getall();
//		
//		for(SalonOrderDetailVO asalonOrderDetail :list) {
//			System.out.println(asalonOrderDetail.getSalOrderNo() +",");
//			System.out.println(asalonOrderDetail.getSalSevNo() +",");
//			System.out.println(asalonOrderDetail.getGroomerNo() +",");
//			System.out.println(asalonOrderDetail.getSalSevPr() +",");
//			System.out.println("===============================");
//		}
		
		//查詢
//		List<SalonOrderDetailVO> list = dao.getAllBySalOrderNo("BO009");
//		for(SalonOrderDetailVO asalonOrderDetail :list) {
//			System.out.println(asalonOrderDetail.getSalSevNo());
//			System.out.println(asalonOrderDetail.getSalOrderNo());
//			System.out.println(asalonOrderDetail.getGroomerNo());
//			System.out.println("====================================");
//			
//		}
	
	
	}
}
