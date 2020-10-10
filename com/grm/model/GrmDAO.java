package com.grm.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GrmDAO implements GrmDAO_interface {

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
	private static DataSource ds = null;

	static {
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PJDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// �ǳ�SQL���O
	private static final String INSERT_STMT = "INSERT INTO GROOMER(GROOMERNO, SALNO, GROOMERNAME, GROOMERINFO, GROOMERPIC, ISDELETE) VALUES ('G' || lpad(GROOMER_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE GROOMER set groomername=?, groomerinfo=?, groomerpic=?, isdelete=? WHERE GROOMERNO=? ";
	// �R�����e�v==isDel(1), DB���R��, �u�O�e�ݭ��������
	private static final String DELETE = "UPDATE GROOMER set isdelete=1  WHERE GROOMERNO=?";
//	private static final String GET_ALL_STMT = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO FROM GROOMER ORDER BY GROOMERNO";
	private static final String GET_ONE_STMT = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO, GROOMERPIC FROM GROOMER WHERE SALNO=? AND ISDELETE=0";
	private static final String GET_ONE_GRM = "SELECT GROOMERNO, GROOMERNAME, GROOMERINFO, GROOMERPIC FROM GROOMER WHERE GROOMERNO=?";
	
	
	@Override
	public void insert(GrmVO grmvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, groomerNo);

			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, salNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				grmvo = new GrmVO();
				grmvo.setGroomerNo(rs.getString("groomerno"));
				grmvo.setGroomerName(rs.getString("groomername"));
				grmvo.setGroomerInfo(rs.getString("groomerinfo"));
//				grmvo.setGroomerPic(rs.getBytes("groomerpic"));
				list.add(grmvo);
			}
			con.setAutoCommit(true);

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
	public GrmVO findByGroomerNo(String groomerNo) {

		GrmVO grmvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
		} catch (SQLException se) {
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
		return grmvo;
	}

}

// ��X���x�W�Ҧ����e�v
//	@Override
//	public List<GrmVO> getAll() {
//		List<GrmVO> list = new ArrayList<GrmVO>();
//		GrmVO grm = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				grm = new GrmVO();
//				grm.setGroomerName(rs.getString("groomerno"));
//				grm.setGroomerName(rs.getString("groomername"));
//				grm.setGroomerInfo(rs.getString("groomerinfo"));
//				list.add(grm);
//			}
//			
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
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
