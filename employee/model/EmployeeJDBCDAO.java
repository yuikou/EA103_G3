package com.employee.model;

import java.sql.*;
import java.util.*;

public class EmployeeJDBCDAO implements EmployeeDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "EA103G3";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE (EMPNO, ENAME, EMPID, EMPPSW, ENICKNAME, EMPEMAIL) VALUES ('EMP' || LPAD(EMPLOYEE_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE EMPLOYEE SET ENAME=?, EMPID=?, EMPPSW=?, ENICKNAME=?, EACCSTATUS=?, EMPEMAIL=? WHERE EMPNO = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM EMPLOYEE WHERE EMPNO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM EMPLOYEE ORDER BY EMPNO";
			
	@Override
	public void insert(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, employeeVO.geteName());
			pstmt.setString(2, employeeVO.getEmpId());
			pstmt.setString(3, employeeVO.getEmpPsw());
			pstmt.setString(4, employeeVO.geteNickname());
			pstmt.setString(5, employeeVO.getEmpEmail());
			pstmt.executeUpdate();

		} catch(ClassNotFoundException e) {
			throw new RuntimeException("載入資料庫失敗" + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("資料庫錯誤" + se.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public void update(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, employeeVO.geteName());
			pstmt.setString(2, employeeVO.getEmpId());
			pstmt.setString(3, employeeVO.getEmpPsw());
			pstmt.setString(4, employeeVO.geteNickname());
			pstmt.setInt(5, employeeVO.geteAccStatus());
			pstmt.setString(6, employeeVO.getEmpEmail());
			pstmt.setString(7, employeeVO.getEmpNo());
			pstmt.executeUpdate();

		} catch(ClassNotFoundException e) {
			throw new RuntimeException("載入資料庫失敗" + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("資料庫錯誤" + se.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public EmployeeVO findByPrimaryKey(String empNo) {
		
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, empNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpNo(rs.getString("empNo"));
				employeeVO.seteName(rs.getString("eName"));
				employeeVO.setEmpId(rs.getString("empId"));
				employeeVO.setEmpPsw(rs.getString("empPsw"));
				employeeVO.seteNickname(rs.getString("eNickname"));
				employeeVO.seteAccStatus(rs.getInt("eAccStatus"));
				employeeVO.setEmpEmail(rs.getString("empEmail"));
			}

		} catch(ClassNotFoundException e) {
			throw new RuntimeException("載入資料庫失敗" + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("資料庫錯誤" + se.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return employeeVO;
	}


	@Override
	public List<EmployeeVO> getAll() {
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		EmployeeVO employeeVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmpNo(rs.getString("empno"));
				employeeVO.seteName(rs.getString("ename"));
				employeeVO.setEmpId(rs.getString("empid"));
				employeeVO.setEmpPsw(rs.getString("emppsw"));
				employeeVO.seteNickname(rs.getString("enickname"));
				employeeVO.seteAccStatus(rs.getInt("eaccstatus"));
				employeeVO.setEmpEmail(rs.getString("empemail"));
				employeeVO.setEmpPhoto(rs.getBytes("empphoto"));				
				list.add(employeeVO);
			} 
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("載入資料庫失敗" + e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("資料庫錯誤" + se.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		EmployeeJDBCDAO dao = new EmployeeJDBCDAO();

		// 新增
		EmployeeVO employeeVO1 = new EmployeeVO();
//		employeeVO1.seteName("吳永志");
//		employeeVO1.setEmpId("EA10300");
//		employeeVO1.setEmpPsw("123456");
//		employeeVO1.seteNickname("你在嗎");
//		employeeVO1.setEmpEmail("tomcat@gmail.com");
//		dao.insert(employeeVO1);
		
		// 修改
		EmployeeVO employeeVO2 = new EmployeeVO();
//		employeeVO2.setEmpNo("EMP009");
//		employeeVO2.seteName("吳小志");
//		employeeVO2.setEmpId("EA10300");
//		employeeVO2.setEmpPsw("gggggg");
//		employeeVO2.seteNickname("有拉");
//		employeeVO2.seteAccStatus(1);
//		employeeVO2.setEmpEmail("cindycat@gmail.com");
//		dao.update(employeeVO2);
		
		// 查詢
		EmployeeVO employeeVO3 = dao.findByPrimaryKey("EMP009");
		System.out.print(employeeVO3.getEmpNo() + ",");
		System.out.print(employeeVO3.geteName() + ",");
		System.out.print(employeeVO3.getEmpId() + ",");
		System.out.print(employeeVO3.getEmpPsw() + ",");
		System.out.print(employeeVO3.geteNickname() + ",");
		System.out.print(employeeVO3.geteAccStatus() + ",");
		System.out.print(employeeVO3.getEmpEmail() + ",");
		System.out.println(employeeVO3.getEmpPhoto());
		System.out.println("---------------------");
		
		// 查詢
		List<EmployeeVO> list = dao.getAll();
		for (EmployeeVO aemployee : list) {
			System.out.print(aemployee.getEmpNo() + ",");
			System.out.print(aemployee.geteName() + ",");
			System.out.print(aemployee.getEmpId() + ",");
			System.out.print(aemployee.getEmpPsw() + ",");
			System.out.print(aemployee.geteNickname() + ",");
			System.out.print(aemployee.geteAccStatus() + ",");
			System.out.print(aemployee.getEmpEmail() + ",");
			System.out.print(aemployee.getEmpPhoto());
			System.out.println();
		}
	}
}
