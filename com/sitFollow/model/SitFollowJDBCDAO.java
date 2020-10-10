package com.sitFollow.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SitFollowJDBCDAO implements SitFollowDAO_interface{
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@localhost:1521:xe";
	private static String user="EA103G3";
	private static String passwd="123456";
	
	private static final String ADD_PSTMT = "INSERT INTO sitFollow VALUES (?, ?)";
	private static final String DEL_PSTMT = "DELETE FROM sitFollow WHERE MEMNO=? AND SITNO=?";
	private static final String GET_ALL_BY_MEMNO = "SELECT memNo From petSitter "
												 + "WHERE sitNO IN (Select sitNO FROM sitFollow WHERE MEMNO=? ) "
												 + "ORDER BY sitNO DESC";

	@Override
	public Boolean add(String memNo, String sitNo) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, memNo);
			pstmt.setString(2, sitNo);
			
			// 如果新增一筆成功，回傳true給Controller，方便執行下一段程式碼
			if (pstmt.executeUpdate() == 1) {
				addOK = true;
			}
			con.commit();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("新增失敗： " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return addOK;
	}

	@Override
	public Boolean del(String memNo, String sitNo) {
		Boolean delOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(DEL_PSTMT);
			pstmt.setString(1, memNo);
			pstmt.setString(2, sitNo);
			// 如果刪除一筆成功，回傳true給Controller，方便執行下一段程式碼
			if (pstmt.executeUpdate() == 1) {
				delOK = true;
			}
			con.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("新增失敗： " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return delOK;
	}

	@Override
	public List<String> getAllByMemNo(String memNo) {

		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_MEMNO);
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString("memNo"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("出現DB問題了 OMG" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	
	public static void main(String[] args) {
		SitFollowJDBCDAO jdbcdao = new SitFollowJDBCDAO();
		List<String> list = new ArrayList<String>();
		
		// 新增
//		if ( jdbcdao.add("M003", "S004") ) {
//			System.out.println("新增成功");
//		}
		
		// 刪除
//		if ( jdbcdao.delete("M001", "S001") ) {
//			System.out.println("刪除成功");
//		}
		
		// 查詢
		list = jdbcdao.getAllByMemNo("M001");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("第" + (i+1) + "個memNo = " + list.get(i));
		}
	}
}
