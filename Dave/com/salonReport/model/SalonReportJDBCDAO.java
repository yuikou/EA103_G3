package com.salonReport.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalonReportJDBCDAO implements SalonReportDAO_interface{
	String driver ="oracle.jdbc.driver.OracleDriver" ;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";	
	String userid = "EA103G3";
	String passwd = "123456";
	
	private static final String INSERT_STMT=
			"INSERT INTO SALONREPORT(SALREPNO,SALORDERNO,MEMNO,EMPNO,SALREP)VALUES('BR' || LPAD(SALREPNO_SEQ.NEXTVAL,3,'0'),?,?,?,?)";
	private static final String DELETE=
			"DELETE FROM SALONREPORT FROM SALREPNO";
	private static final String UPDATE =
			"UPDATE SALONREPORT SET  SALORDERNO=?, MEMNO=? , EMPNO=?,SALREP=? WHERE SALREPNO=?" ;
	private static final String GET_ALL_STMT=
			"SELECT SALREPNO,SALORDERNO,MEMNO,EMPNO,SALREP FROM SALONREPORT ORDER BY SALREPNO";
	
	@Override
	public void insert(SalonReportVO salonReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,salonReportVO.getSalOrderNo());
			pstmt.setString(2,salonReportVO.getMemNo());
			pstmt.setString(3,salonReportVO.getEmpNo());
			pstmt.setString(4,salonReportVO.getSalRep());
			
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
	public void update(SalonReportVO salonReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,salonReportVO.getSalOrderNo());
			pstmt.setString(2,salonReportVO.getMemNo());
			pstmt.setString(3,salonReportVO.getEmpNo());
			pstmt.setString(4,salonReportVO.getSalRep());
			pstmt.setString(5,salonReportVO.getSalRepNo());
			
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
	public void delete(String salRepNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, salRepNo);
			
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
	public List<SalonReportVO> getall() {
		List<SalonReportVO> list = new ArrayList<SalonReportVO>();
		SalonReportVO salonReportVO =null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				salonReportVO = new SalonReportVO();
				
				salonReportVO.setSalRepNo(rs.getString("salRepNo"));
				salonReportVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonReportVO.setMemNo(rs.getString("salMemNo"));
				salonReportVO.setEmpNo(rs.getString("empNo"));
				salonReportVO.setSalRep(rs.getString("salRep"));
				
				list.add(salonReportVO);
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
	
	public static void main(String[] args) {
		SalonReportJDBCDAO dao =new SalonReportJDBCDAO();
		
		//新增
//		SalonReportVO salonReportVO1 = new SalonReportVO();
//		
//		salonReportVO1.setSalOrderNo("BO003");
//		salonReportVO1.setMemNo("M002");
//		salonReportVO1.setEmpNo("EMP001");
//		salonReportVO1.setSalRep("店家服務非常糟糕,對待寵物很不用心");
//		
//		dao.insert(salonReportVO1);
		
		//修改
		SalonReportVO salonReportVO2 = new SalonReportVO();
		
		salonReportVO2.setSalRepNo("BR001");
		salonReportVO2.setSalOrderNo("BO001");
		salonReportVO2.setMemNo("M001");
		salonReportVO2.setEmpNo("EMP002");
		salonReportVO2.setSalRep("價格太貴,下次不會再來了");
		
		dao.update(salonReportVO2);
		System.out.println("新增成功");
	}

	
	
	
	
}
