package com.salonAlbum.model;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.grm.model.GrmVO;

public class SalonAlbDAO implements SalonAlbDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 準備SQL指令
	private static final String INSERT_STMT = "INSERT INTO SALONALBUM (salAlbNo, salNo, salPic) VALUES ('BP' || LPAD(SALALBNO_SEQ.nextval,3,'0'), ?, ?) ";
	//INSERT INTO salonalbum(salalbno,SALNO,SALPORTINFO)VALUES('BP' || LPAD(SALALBNO_SEQ.nextval,3,'0'),'B001','我們是B001美容店');
	// 顯示美容店的所有相片
	private static final String DELETE = "DELETE FROM SALONALBUM where salAlbNo=?";
	private static final String GET_ALL_STMT = "SELECT salAlbNo, SALPIC, salPortInfo FROM SALONALBUM where salno=?";
	private static final String GET_ONE_PIC = "SELECT salAlbNo, SALPIC, salPortInfo FROM SALONALBUM where salAlbNo=?";

	@Override
	public void insert(SalonAlbVO salAVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, salAVO.getSalNo());
			pstmt.setBytes(2, salAVO.getSalAlbPic());
//			pstmt.setString(3, salAVO.getSalPortInfo());
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

//	@Override
//	public void update(SalonAlbVO salAVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setString(1, salAVO.getSalNo());
//			pstmt.setBytes(2, salAVO.getSalPic());
//			pstmt.setString(3, salAVO.getSalPortInfo());
//			pstmt.setString(4, salAVO.getSalAlbNo());
//			
//			pstmt.executeUpdate();
//			pstmt.clearParameters();
//			
//			con.commit();
//			con.setAutoCommit(true);
//		} catch (SQLException se) {
//			try {
//				con.rollback();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
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
//	}

	@Override
	public void delete(String salAlbNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salAlbNo);

			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	//	@Override
	//	public void update(SalonAlbVO salAVO) {
	//		Connection con = null;
	//		PreparedStatement pstmt = null;
	//
	//		try {
	//
	//			con = ds.getConnection();
	//			con.setAutoCommit(false);
	//			pstmt = con.prepareStatement(UPDATE);
	//			
	//			pstmt.setString(1, salAVO.getSalNo());
	//			pstmt.setBytes(2, salAVO.getSalPic());
	//			pstmt.setString(3, salAVO.getSalPortInfo());
	//			pstmt.setString(4, salAVO.getSalAlbNo());
	//			
	//			pstmt.executeUpdate();
	//			pstmt.clearParameters();
	//			
	//			con.commit();
	//			con.setAutoCommit(true);
	//		} catch (SQLException se) {
	//			try {
	//				con.rollback();
	//			} catch (SQLException e) {
	//				e.printStackTrace();
	//			}
	//			throw new RuntimeException("A database error occured. "
	//					+ se.getMessage());
	//			// Clean up JDBC resources
	//		} finally {
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
	//	}
	
		@Override
	public List<SalonAlbVO> findBySalonKey(String salno) {
		List<SalonAlbVO> list = new ArrayList<SalonAlbVO>();
		SalonAlbVO salbvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1, salno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
								
				salbvo = new SalonAlbVO();
				salbvo.setSalAlbNo(rs.getString("salAlbNo"));
				salbvo.setSalAlbPic(rs.getBytes("salpic")); //存取DB裡面的BLOB資料
				salbvo.setSalPortInfo(rs.getString("salPortInfo"));
				
				list.add(salbvo);
			}
			con.setAutoCommit(true);
		} catch (SQLException se) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public SalonAlbVO getOnePic(String salAlbNo){
		
		SalonAlbVO salp = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_PIC);
			
			pstmt.setString(1, salAlbNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				salp = new SalonAlbVO();
				salp.setSalAlbNo(rs.getString("salAlbNo"));
				salp.setSalAlbPic(rs.getBytes("salpic"));
				salp.setSalPortInfo(rs.getString("salPortInfo"));
			}
			
			
		}catch (SQLException se) {
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
		return salp;
		
	}
	
	
	
	// 使用InputStream資料流方式
		public static InputStream getPictureStream(String path) throws IOException {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			return fis;
		}

		// 使用byte[]方式
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
