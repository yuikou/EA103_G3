package com.member.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MemDAO implements MemDAO_interface{
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"insert into member_table (memNo, memName, memBirth, memSex, memPhone, memEmail, memAddress, memId, memPsw, memNickname, memPhoto) "
			+ "values ('M' || lpad(mem_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_MEMPSW = 
			"UPDATE MEMBER_TABLE set memPsw=? where memNo = ?";
	private static final String UPDATE = 
			"UPDATE MEMBER_TABLE set memName=?, memBirth=?, memSex=?, memPhone=?, memEmail=?, "
			+ "memAddress=? , memNickname=?, memPhoto=? where memNo = ?";
	private static final String UPDATE_MEMAUTH = 
			"UPDATE MEMBER_TABLE set memAuthority=1 where memNo = ?";
	private static final String DELETE =
			"UPDATE MEMBER_TABLE set memStatus=1 WHERE memNo = ?";
	private static final String GET_ONE_STMT =
			"SELECT * FROM MEMBER_TABLE WHERE memNo = ?";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM MEMBER_TABLE ORDER BY memNo";
	private static final String LOGIN = 
			"SELECT * FROM MEMBER_TABLE WHERE memId=? AND memPsw=?"; 
//	private static final String DUPID = 
//			"";
	@Override
	public void insert(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memVO.getMemName());
			pstmt.setDate(2, memVO.getMemBirth());
			pstmt.setInt(3, memVO.getMemSex());
			pstmt.setString(4, memVO.getMemPhone());
			pstmt.setString(5, memVO.getMemEmail());
			pstmt.setString(6, memVO.getMemAddress());
			pstmt.setString(7, memVO.getMemId());
			pstmt.setString(8, memVO.getMemPsw());
			pstmt.setString(9, memVO.getMemNickname());
			pstmt.setBytes(10, memVO.getMemPhoto());
			
			pstmt.executeUpdate();
			// Handle any SQL errors
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
	public void update(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memVO.getMemName());
			pstmt.setDate(2, memVO.getMemBirth());
			pstmt.setInt(3, memVO.getMemSex());
			pstmt.setString(4, memVO.getMemPhone());
			pstmt.setString(5, memVO.getMemEmail());
			pstmt.setString(6, memVO.getMemAddress());
			pstmt.setString(7, memVO.getMemNickname());
			pstmt.setBytes(8, memVO.getMemPhoto());
			pstmt.setString(9, memVO.getMemNo());
			
			pstmt.executeUpdate();
			System.out.println("update success");
			// Handle any SQL errors
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
	public void delete(String memNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, memNo);
			
			pstmt.executeUpdate();
			// Handle any SQL errors
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
	public void upgradeAuth(String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MEMAUTH);
//			"UPDATE MEMBER_TABLE set memAuthority=1 where memNo = ?";
			pstmt.setString(1, memNo);
			
			pstmt.executeUpdate();
			// Handle any SQL errors
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
	
	public void updatePsw(String memPsw, String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
//			"UPDATE MEMBER_TABLE set memPsw=? where memNo = ?";
			pstmt = con.prepareStatement(UPDATE_MEMPSW);
			
			pstmt.setString(1, memPsw);
			pstmt.setString(2, memNo);
			
			pstmt.executeUpdate();
			// Handle any SQL errors
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
	public MemVO findByPrimaryKey(String memNo) {
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, memNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMemNo(rs.getString("memNo"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemAuthority(rs.getInt("memAuthority"));
				memVO.setMemId(rs.getString("memId"));
				memVO.setMemStatus(rs.getInt("memStatus"));
				memVO.setMemAddress(rs.getString("memAddress"));
				memVO.setMemPhone(rs.getString("memPhone"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemEmail(rs.getNString("memEmail"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemNickname(rs.getString("memNickname"));
				memVO.setMemPhoto(rs.getBytes("memPhoto"));
				memVO.setMemPoint(rs.getInt("memPoint"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemSex(rs.getInt("memSex"));
			}
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
		return memVO;
	}
	
	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMemNo(rs.getString("memNo"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemAuthority(rs.getInt("memAuthority"));
				memVO.setMemId(rs.getString("memId"));
				memVO.setMemStatus(rs.getInt("memStatus"));
				memVO.setMemAddress(rs.getString("memAddress"));
				memVO.setMemPhone(rs.getString("memPhone"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemEmail(rs.getNString("memEmail"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemNickname(rs.getString("memNickname"));
				memVO.setMemPhoto(rs.getBytes("memPhoto"));
				memVO.setMemPoint(rs.getInt("memPoint"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemSex(rs.getInt("memSex"));
				list.add(memVO);
			}
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
	public MemVO login(String memId, String memPsw) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);
//		"SELECT 1 FROM MEMBER_TABLE WHERE memId=?";
			pstmt.setString(1, memId);
			pstmt.setString(2, memPsw);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMemNo(rs.getString("memNo"));
				memVO.setMemName(rs.getString("memName"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemAuthority(rs.getInt("memAuthority"));
				memVO.setMemId(rs.getString("memId"));
				memVO.setMemStatus(rs.getInt("memStatus"));
				memVO.setMemAddress(rs.getString("memAddress"));
				memVO.setMemPhone(rs.getString("memPhone"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemEmail(rs.getNString("memEmail"));
				memVO.setMemBirth(rs.getDate("memBirth"));
				memVO.setMemNickname(rs.getString("memNickname"));
				memVO.setMemPhoto(rs.getBytes("memPhoto"));
				memVO.setMemPoint(rs.getInt("memPoint"));
				memVO.setMemPsw(rs.getString("memPsw"));
				memVO.setMemSex(rs.getInt("memSex"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured, " + e.getMessage());
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
				}catch(SQLException se) {
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
		return memVO;
	}

	public static void main(String[] args) {
		MemJDBCDAO dao = new MemJDBCDAO();
		//insert
//		MemVO memVO1 = new MemVO();
//		memVO1.setMemName("吳湧至");
//		memVO1.setMemNickname("zzz");
//		memVO1.setMemSex(new Integer(1));
//		memVO1.setMemBirth(new Date(System.currentTimeMillis()-100000));
//		memVO1.setMemId("aaaaaa");
//		memVO1.setMemPsw("123456");
//		memVO1.setMemAddress("台北市");
//		memVO1.setMemEmail("aaa@bbbmail.com");
//		memVO1.setMemPhone("08-45612378");
//		memVO1.setMemPhoto(null);
//		dao.insert(memVO1);
		
		
		//update
//		MemVO memVO2 = new MemVO();
//		memVO2.setMemName("吳勇志2");
//		memVO2.setMemNickname("yyy");
//		memVO2.setMemSex(new Integer(1));
//		memVO2.setMemBirth(java.sql.Date.valueOf("2002-01-01"));
//		memVO2.setMemAddress("台北市");
//		memVO2.setMemEmail("aaa@bbbmail.com");
//		memVO2.setMemPhone("08-45612378");
//		memVO2.setMemPhoto(null);
//		memVO2.setMemNo("M016");
//		dao.update(memVO2);		
		
		
		//delete
//		dao.delete("M016");
		
		//updateAuth
//		dao.upgradeAuth("M016");
		
		//updatePsw
//		dao.updatePsw("jnbvqkefv","M016");
		
		//findByPrimaryKey
//		MemVO memVO3 = dao.findByPrimaryKey("M016");
//		System.out.print(memVO3.getMemNo()+",");
//		System.out.print(memVO3.getMemId()+",");
//		System.out.print(memVO3.getMemPsw()+",");
//		System.out.print(memVO3.getMemName()+",");
//		System.out.print(memVO3.getMemNickname()+",");
//		System.out.print(memVO3.getMemPhone()+",");
//		System.out.print(memVO3.getMemAddress()+",");
//		System.out.print(memVO3.getMemEmail()+",");
//		System.out.print(memVO3.getMemAuthority()+",");
//		System.out.print(memVO3.getMemStatus()+",");
//		System.out.print(memVO3.getMemBirth()+",");
//		System.out.println(memVO3.getMemPoint());
//		System.out.println("---------------------------------");
		
		//getAll
//		List<MemVO> list1 = dao.getAll();
//		for(MemVO aMem : list1) {
//			System.out.print(aMem.getMemNo()+",");
//			System.out.print(aMem.getMemId()+",");
//			System.out.print(aMem.getMemPsw()+",");
//			System.out.print(aMem.getMemName()+",");
//			System.out.print(aMem.getMemNickname()+",");
//			System.out.print(aMem.getMemPhone()+",");
//			System.out.print(aMem.getMemAddress()+",");
//			System.out.print(aMem.getMemEmail()+",");
//			System.out.print(aMem.getMemAuthority()+",");
//			System.out.print(aMem.getMemStatus()+",");
//			System.out.print(aMem.getMemBirth()+",");
//			System.out.println(aMem.getMemPoint());
//			System.out.println("---------------------------------");
//		}
		
		//isDup
//		System.out.println(dao.isDuplicate("king"));
	}
}
