package com.salsev.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalSevJDBCDAO implements SalsevDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO SALONSERVICE(SALSEVNO, SALNO, PETCAT, SALSEVNAME, SALSEVINFO, SALSEVTIME, SALSEVPR, STATUS) VALUES ('BS' || lpad(SALSER_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE SALONSERVICE set PETCAT=?,SALSEVNAME=?, SALSEVINFO=?, SALSEVTIME=?, SALSEVPR=?, STATUS=? WHERE SALSEVNO=?";
//	//���اR��1. �qDB�R��
//	private static final String DELETE = "DELETE FROM SALONSERVICE WHERE SALSEVNO=?";
	// ���اR��2. �q�e�ݭ����R��(status=1)
	private static final String DELETE = "UPDATE SALONSERVICE set STATUS=1 WHERE SALSEVNO=?";
	// �@�����e��show�X�Ҧ��A�ȶ���
	private static final String GET_ONE_STMT = "SELECT SALSEVNO, SALNO, PETCAT, SALSEVNAME, SALSEVINFO, SALSEVTIME, SALSEVPR, STATUS FROM SALONSERVICE WHERE salno=?";
	private static final String GET_ONE_SEV = "SELECT SALSEVNO, SALNO, PETCAT, SALSEVNAME, SALSEVINFO, SALSEVTIME, SALSEVPR, STATUS FROM SALONSERVICE WHERE SALSEVNO=?";

	// �H�U���ե�
//	public static void main(String[] args) {
//		SalsevVO vo = new SalsevVO();
//		
//		vo.setSalno("B002");
//		vo.setPetcat(4);
//		vo.setSalsevname("��¦���e123");
//		vo.setSalSevInfo("�]�t�G�ū��ҡB�M�զ�");
//		vo.setSalsevtime(180);
//		vo.setSalsevpr(21000);
//		vo.setStatus(0);
//		vo.setSalsevno("BS013");
		
//		SalSevJDBCDAO test = new SalSevJDBCDAO();
//		test.insert(vo);
//		test.update(vo);
//		test.delete(vo.getSalsevno());
//		test.findByPrimaryKey(vo.getSalno());
//		test.getAll(vo.getSalno());

//	}

	@Override
	public void insert(SalsevVO salvVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, salvVO.getSalno());
			pstmt.setInt(2, salvVO.getPetcat());
			pstmt.setString(3, salvVO.getSalsevname());
			pstmt.setString(4, salvVO.getSalSevInfo());
			pstmt.setInt(5, salvVO.getSalsevtime());
			pstmt.setInt(6, salvVO.getSalsevpr());
			pstmt.setInt(7, salvVO.getStatus());

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
//			System.out.println("insert OK");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void update(SalsevVO salvVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

//"UPDATE SALONSERVICE set PETCAT=?,SALSEVNAME=?, SALSEVINFO=?, SALSEVTIME=?, SALSEVPR=?, STATUS=? WHERE salSevNo=?";

			pstmt.setInt(1, salvVO.getPetcat());
			pstmt.setString(2, salvVO.getSalsevname());
			pstmt.setString(3, salvVO.getSalSevInfo());
			pstmt.setInt(4, salvVO.getSalsevtime());
			pstmt.setInt(5, salvVO.getSalsevpr());
			pstmt.setInt(6, salvVO.getStatus());
			pstmt.setString(7, salvVO.getSalsevno());

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("update OK");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void delete(String salsevno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salsevno);

			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			System.out.println("delete(not show) OK");
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public List<SalsevVO> getAll(String salno) {
		List<SalsevVO> list = new ArrayList<SalsevVO>();
		SalsevVO savVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, salno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				savVO = new SalsevVO();
				savVO.setSalsevno(rs.getString("salsevno"));
				savVO.setPetcat(rs.getInt("petcat"));
				savVO.setSalsevname(rs.getString("salsevname"));
				savVO.setSalSevInfo(rs.getString("salsevinfo"));
				savVO.setSalsevtime(rs.getInt("salsevtime"));
				savVO.setSalsevpr(rs.getInt("salsevpr"));
				savVO.setStatus(rs.getInt("status"));

				list.add(savVO);

//				System.out.println("���e�A�ȶ��ظ��X"+rs.getString("salSevNo"));
//				System.out.println("�d������:"+rs.getInt("petcat"));
//				System.out.println("�A�ȦW��"+rs.getString("salSevName"));
//				System.out.println("�A�Ȥ��e:"+rs.getString("salSevInfo"));
//				System.out.println("�һݮɶ�"+ rs.getInt("salSevTime"));
//				System.out.println("����: $"+rs.getInt("salSevPr"));
//				System.out.println("�A�Ȫ��A:"+rs.getString("status"));

			}
			con.setAutoCommit(true);
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	@Override
	public SalsevVO findByPrimaryKey(String salsevno) {
		SalsevVO savVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_SEV);

			pstmt.setString(1, salsevno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				savVO = new SalsevVO();
				savVO.setSalsevno(rs.getString("salsevno"));
				savVO.setPetcat(rs.getInt("petcat"));
				savVO.setSalsevname(rs.getString("salsevname"));
				savVO.setSalSevInfo(rs.getString("salsevinfo"));
				savVO.setSalsevtime(rs.getInt("salsevtime"));
				savVO.setSalsevpr(rs.getInt("salsevpr"));
				savVO.setStatus(rs.getInt("status"));

			}
			con.setAutoCommit(true);
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		return savVO;
	}

}
