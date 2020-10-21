package com.goffday.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GODayJDBCDAO implements GODayDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO GROOMEROFFDAY (offno,groomerno,offday,offtime,offdaytype) VALUES ('GD' || lpad(GMOFF_SEQ.NEXTVAL, 3, 0),?, ?, ?, ?)";
	// 查詢單一美容師的所有offday
	private static final String GET_ALL_STMT = "SELECT offno, groomerno, offday, offtime, offdaytype FROM GROOMEROFFDAY where groomerno=? ";
	private static final String GET_ONE_STMT = "SELECT offno, groomerno, offday, offtime, offdaytype FROM GROOMEROFFDAY where offno=? ";
	private static final String DELETE = "DELETE from GROOMEROFFDAY where offno=?";
	private static final String UPDATE = "UPDATE GROOMEROFFDAY set groomerno=?, offday=?, offtime=?, offdaytype=? where offno=?";
	// UPDATE GROOMEROFFDAY set offday=to_date('2020-12-25 18:30' ,'yyyy-mm-dd
	// HH24:MI'), offdaytype=1 where offno='GD007';

//	public static void main(String[] args) {
//		GODayVO vo = new GODayVO();
//		GODayJDBCDAO test = new GODayJDBCDAO();
//		vo.setGroomerNo("G006");
//		vo.setOffDay("2020-09-08");
//		vo.setOffTime("18:00");
//		vo.setOffDayType(1);
//		vo.setOffNo("GD001");
//		test.insert(vo);
//		test.update(vo);
//		test.findByPrimaryKey(vo.getGroomerNo());
//	}

	public void insert(GODayVO godayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, godayVO.getGroomerNo());
			pstmt.setString(2, godayVO.getOffDay());
			pstmt.setString(3, godayVO.getOffTime());
			pstmt.setInt(4, godayVO.getOffDayType());

			pstmt.executeUpdate();
//			System.out.println("OK");
			con.commit();
			con.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			// Clean up JDBC resources
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
	public void update(GODayVO godayVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, godayVO.getGroomerNo());
			pstmt.setString(2, godayVO.getOffDay());
			pstmt.setString(3, godayVO.getOffTime());
			pstmt.setInt(4, godayVO.getOffDayType());
			pstmt.setString(5, godayVO.getOffNo());

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("更新成功");
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Clean up JDBC resources
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
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
	public void delete(String offno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, offno);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
//			System.out.println("OK");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
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
	public List<GODayVO> getAll(String groomerno) {
		List<GODayVO> list = new ArrayList<GODayVO>();
		GODayVO godayVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, groomerno);

			rs = pstmt.executeQuery();

			con.setAutoCommit(true);

			while (rs.next()) {
				godayVO = new GODayVO();
				godayVO.setOffNo(rs.getString("offno"));
				godayVO.setGroomerNo(rs.getString("groomerno"));
				godayVO.setOffDay(rs.getString("offday"));
				godayVO.setOffTime(rs.getString("offtime"));
				godayVO.setOffDayType(rs.getInt("offdaytype"));

				list.add(godayVO);

//				System.out.println("休假編號:"+rs.getString("offno"));
//				System.out.println("美容師編號:"+rs.getString("groomerno"));
//				System.out.println("NG日期"+rs.getString("offday"));
//				System.out.println("NG時間"+rs.getString("offtime"));
//				System.out.println("休假類型"+ rs.getInt("offdaytype"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public GODayVO findByPrimaryKey(String offno) {
		GODayVO godayVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, offno);

			rs = pstmt.executeQuery();

			con.setAutoCommit(true);

			while (rs.next()) {
				godayVO = new GODayVO();
				godayVO.setOffNo(rs.getString("offno"));
				godayVO.setGroomerNo(rs.getString("groomerno"));
				godayVO.setOffDay(rs.getString("offday"));
				godayVO.setOffTime(rs.getString("offtime"));
				godayVO.setOffDayType(rs.getInt("offdaytype"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		return godayVO;
	}

}
