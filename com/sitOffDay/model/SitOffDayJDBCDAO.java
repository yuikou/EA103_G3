package com.sitOffDay.model;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SitOffDayJDBCDAO implements SitOffDayDAO_interface{
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@localhost:1521:xe";
	private static String user="EA103G3";
	private static String passwd="123456";
			
	
	private static final String ADD_PSTMT 			= "INSERT INTO sitOffDay VALUES ('SD' || lpad(offDayNo_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?)";
	private static final String DEL_PSTMT 			= "DELETE sitOffDay WHERE groupId=? ";
	private static final String GET_BY_OFFDAYNO 	= "SELECT * FROM sitOffDay WHERE offDayNo=? ";
	private static final String GET_BY_SITSRVNO 	= "SELECT * FROM sitOffDay WHERE sitSrvNo=? ";
	private static final String GET_SIT_BY_OFFDATE 	= "SELECT sitSrvNo FROM sitOffDay WHERE offDay  BETWEEN to_date(?, 'yyyy-mm-dd') "
													+ "AND TO_DATE(?, 'yyyy-mm-dd') "
													+ "AND offTime is Null OR offTime = ? "
													+ "AND sitSrvNo IN (SELECT sitSrvNo FROM sitSrv WHERE sitSrvCode=?)";
//	private static final String GET_SIT_BY_OFFDATE2	= "SELECT sitSrvNo FROM sitOffDay WHERE offday  BETWEEN to_date('2020-11-13', 'yyyy-mm-dd') AND TO_DATE('2020-11-14', 'yyyy-mm-dd')" 
//														+ "AND sitSrvNo IN (SELECT sitSrvNo FROM sitSrv WHERE sitSrvCode='Boarding')";
	
	@Override
	public Boolean commit(Boolean addOK) {
		Boolean isCommit = false;
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			if (addOK) {
				con.commit();
				isCommit = true;
			} else {
				con.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("新增失敗： " + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isCommit;
	}
	
	@Override
	public Boolean add(SitOffDayVO sod) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, sod.getSitSrvNo());
			pstmt.setDate(2, sod.getOffDay());
			pstmt.setObject(3, sod.getOffTime());
			pstmt.setInt(4, sod.getOffDayTyp());
			
			if ( pstmt.executeUpdate() == 1) {
				addOK = true;
			}
//			con.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
//			try {
//				con.rollback();
				throw new RuntimeException("新增失敗： " + e.getMessage());
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
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
	public Boolean del(String groupId) {
		Boolean delOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DEL_PSTMT);
			pstmt.setString(1, groupId);
			if ( pstmt.executeUpdate() > 1) {
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
				throw new RuntimeException("刪除失敗： " + e.getMessage());
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
	public SitOffDayVO getByPK(String offDayNo) {
		SitOffDayVO sod = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_BY_OFFDAYNO);
			pstmt.setString(1, offDayNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sod = new SitOffDayVO();
				sod.setOffDayNo(rs.getString(1));
				sod.setSitSrvNo(rs.getString(2));
				sod.setOffDay(rs.getDate(3));
				sod.setOffTime(rs.getString(4));
				sod.setOffDayTyp(rs.getInt(5));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGGGG不知道說什麼了" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return sod;
	}
	
	@Override
	public List<SitOffDayVO> getByFK(String sitSrvNo) {
		List<SitOffDayVO> list = new ArrayList<SitOffDayVO>();
		SitOffDayVO sod = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_BY_SITSRVNO);
			pstmt.setString(1, sitSrvNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sod = new SitOffDayVO();
				sod.setOffDayNo(rs.getString(1));
				sod.setSitSrvNo(rs.getString(2));
				sod.setOffDay(rs.getDate(3));
				sod.setOffTime(rs.getString(4));
				sod.setOffDayTyp(rs.getInt(5));
				list.add(sod);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGGGG不知道說什麼了" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
	
	@Override
	public Set<String> getSitByDate(String sitSrvCode, String start_date, String end_date, String time) {
		Set<String> set = new HashSet<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_SIT_BY_OFFDATE);
			pstmt.setString(1, start_date);
			pstmt.setString(2, end_date);
			pstmt.setString(3, time);
			pstmt.setString(4, sitSrvCode);
//			pstmt = con.prepareStatement(GET_SIT_BY_OFFDATE2);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				set.add(rs.getString("sitSrvNo"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGGGG不知道說什麼了" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return set;
	}

	public static void main(String[] args) {
		SitOffDayJDBCDAO jdbcdao = new SitOffDayJDBCDAO();
		
		// 新增
		SitOffDayVO vo0 = new SitOffDayVO();
		vo0.setSitSrvNo("SS004");
		vo0.setOffDay(Date.valueOf("2020-10-5"));
		vo0.setOffTime("1400");
		vo0.setOffDayTyp(0);
		SitOffDayVO vo1 = new SitOffDayVO();
		vo1.setSitSrvNo("SS005");
		vo1.setOffDay(Date.valueOf("2020-10-5"));
		vo1.setOffTime("1400");
		vo1.setOffDayTyp(0);
		
		Boolean addOK = false;
		addOK = jdbcdao.add(vo0);
		System.out.println(addOK);
		addOK = jdbcdao.add(vo1);
		System.out.println(addOK);
		
//		Boolean commitOK = false;
//		commitOK = jdbcdao.commit(addOK);
//		System.out.println(commitOK);
		
		
//		if (commitOK) {
//			System.out.println("新增成功");
//		} else {
//			System.out.println("新增失敗");
//		}
//		
		
		// 刪除
//		if ( jdbcdao.del("SD003") ) {
//			System.out.println("刪除成功");
//		}
		// 查詢OnebyPK
		SitOffDayVO vo = jdbcdao.getByPK("SD004");
		System.out.println(vo.getOffDayNo());
		System.out.println(vo.getSitSrvNo());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(vo.getOffDay()));
		System.out.println(vo.getOffTime());
		System.out.println(vo.getOffDayTyp());
		
		// 查詢OnebyFK
		List<SitOffDayVO> list = jdbcdao.getByFK("SS004");
		for (SitOffDayVO vo2 : list) {
			System.out.println("-----------------------------");
			System.out.println(vo2.getOffDayNo());
			System.out.println(vo2.getSitSrvNo());
			System.out.println(df.format(vo.getOffDay()));
			System.out.println(vo2.getOffTime());
			System.out.println(vo2.getOffDayTyp());
		}
		
		// 查詢All
		Set<String> set = new HashSet<String>();
		set = jdbcdao.getSitByDate("DogWalking", "2020-10-4", "2020-10-6", "1000");
		
		if (set.size() < 1) {
			System.out.println("查無資料");
		} else {
			System.out.println("這個時段不可服務的sitNo有： ");
			for (String sitNo : set) {
				System.out.print(sitNo + " ");
			}
		}
	}

}
