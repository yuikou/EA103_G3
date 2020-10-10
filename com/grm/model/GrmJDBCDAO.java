package com.grm.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrmJDBCDAO implements GrmDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "LIZ";
	String passwd = "LIZ";

	// �ǳ�SQL���O
	private static final String INSERT_STMT = "INSERT INTO GROOMER(GROOMERNO, SALNO, GROOMERNAME, GROOMERINFO, GROOMERPIC, ISDELETE) VALUES ('G' || lpad(GROOMER_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE GROOMER set groomername=?, groomerinfo=?, groomerpic=?, isdelete=? WHERE GROOMERNO=? ";
	// �R�����e�v==isDel(1), DB���R��, �u�O�e�ݭ��������
	private static final String DELETE = "UPDATE GROOMER set isdelete=1  WHERE GROOMERNO=?";
//	private static final String GET_ALL_STMT = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO FROM GROOMER ORDER BY GROOMERNO";
	private static final String GET_ONE_STMT = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO, GROOMERPIC FROM GROOMER WHERE SALNO=? AND ISDELETE=0";
	private static final String GET_ONE_GRM = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO, GROOMERPIC FROM GROOMER WHERE GROOMERNO=?";
	
	
	 //���ե�main��k
//	public static void main(String[] args) {
//		GrmVO vo = new GrmVO();
//		vo.setGroomerNo("G006");
//		vo.setGroomerName("Sara");
//		vo.setSalNo("B003");
//		vo.setGroomerInfo("2006 TGA ��ڤ��{���e�v C���ҷ�\r\n" + "2009 KCT��ڤ��{���e�vC���ҷ�\r\n" + "2013 KCT��ڵ׭^��B�� ���u�q�޳N��+�z�ƪ��� ���a�x"
//				+ "2014 KCT��ڤ��{���e�vA�� ���u�q�޳N��+�z�ƪ��� ���a�x");
//		vo.setIsDelete(0);
//
//		// �W�ϴ��ե�
//		byte[] pic = null;
//		try {
//			pic = getPictureByteArray("images/logo1.png");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		vo.setGroomerPic(pic);
//		GrmJDBCDAO test = new GrmJDBCDAO();
//////		test.insert(vo);
//		test.update(vo);
//		List<GrmVO> glist= test.findByPrimaryKey(vo.getSalNo());
//		for(GrmVO i : glist) {
//			System.out.println("Name:"+i.getGroomerName());
//			System.out.println("No:"+i.getGroomerNo());
//			System.out.println("info:"+i.getGroomerInfo());
//			
//		}
//	}

	@Override
	public void insert(GrmVO grmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			con.setAutoCommit(false);

			// �ݭn�ʺA���o���e���s��
			pstmt.setString(1, grmvo.getSalNo());
			pstmt.setString(2, grmvo.getGroomerName());
			pstmt.setString(3, grmvo.getGroomerInfo());
			pstmt.setBytes(4, grmvo.getGroomerPic());
			pstmt.setInt(5, grmvo.getIsDelete());

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("insert OK");
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					try {
						con.rollback();
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
	public void update(GrmVO grmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, grmvo.getGroomerName());
			pstmt.setString(2, grmvo.getGroomerInfo());
			pstmt.setBytes(3, grmvo.getGroomerPic());
			pstmt.setInt(4, grmvo.getIsDelete());
			pstmt.setString(5, grmvo.getGroomerNo());

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("update OK");
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
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
	public void delete(String groomerNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, groomerNo);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("delete OK(set isDel)");
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
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

	// ��X�@�����e�����Ҧ����e�v
	@Override
	public List<GrmVO> findByPrimaryKey(String salNo) {
		List<GrmVO> list = new ArrayList<GrmVO>();
		GrmVO grmvo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, salNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				grmvo = new GrmVO();
				grmvo.setGroomerNo(rs.getString("groomerno"));
				grmvo.setGroomerName(rs.getString("groomername"));
				grmvo.setGroomerInfo(rs.getString("groomerinfo"));
				grmvo.setGroomerPic(rs.getBytes("groomerpic"));
				list.add(grmvo);

//				System.out.println("���e�v�W�r:"+ rs.getString("groomername"));
//				System.out.println("���e�v²��:"+ rs.getString("groomerinfo"));
//				System.out.println("�R�����A"+rs.getInt("isdelete"));
			}
			con.setAutoCommit(true);

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
	
	public GrmVO findByGroomerNo(String groomerNo) {

		GrmVO grmvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_GRM);

			pstmt.setString(1, groomerNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				grmvo = new GrmVO();
				grmvo.setGroomerNo(rs.getString("groomerno"));
				grmvo.setGroomerName(rs.getString("groomername"));
				grmvo.setGroomerInfo(rs.getString("groomerinfo"));
				grmvo.setGroomerPic(rs.getBytes("groomerpic"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}finally {
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
		return grmvo;
	}
	
//	public List<GrmVO> getAll() {
//		List<GrmVO> list = new ArrayList<GrmVO>();
//		GrmVO grm = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				grm = new GrmVO();
//				grm.setGroomerNo(rs.getString("groomerno"));
//				grm.setGroomerName(rs.getString("groomername"));
//				grm.setGroomerInfo(rs.getString("groomerinfo"));
//				list.add(grm);
//			}
//			
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//		
//		return list;
//	}
//	

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
