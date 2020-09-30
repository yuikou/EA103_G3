package com.employee.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmployeeJNDIDAO implements EmployeeDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE (EMPNO, ENAME, EMPID, EMPPSW, ENICKNAME, EMPEMAIL) VALUES ('EMP' || LPAD(EMPLOYEE_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE EMPLOYEE SET ENAME=?, EMPID=?, EMPPSW=?, ENICKNAME=?, EACCSTATUS=?, EMPEMAIL=? WHERE EMPNO = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM EMPLOYEE WHERE EMPNO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM EMPLOYEE ORDER BY EMPNO";
			
	@Override
	public void insert(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, employeeVO.geteName());
			pstmt.setString(2, employeeVO.getEmpId());
			pstmt.setString(3, employeeVO.getEmpPsw());
			pstmt.setString(4, employeeVO.geteNickname());
			pstmt.setString(5, employeeVO.getEmpEmail());
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
	public void update(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, employeeVO.geteName());
			pstmt.setString(2, employeeVO.getEmpId());
			pstmt.setString(3, employeeVO.getEmpPsw());
			pstmt.setString(4, employeeVO.geteNickname());
			pstmt.setInt(5, employeeVO.geteAccStatus());
			pstmt.setString(6, employeeVO.getEmpEmail());
			pstmt.setString(7, employeeVO.getEmpNo());
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
	public EmployeeVO findByPrimaryKey(String empNo) {
		
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
