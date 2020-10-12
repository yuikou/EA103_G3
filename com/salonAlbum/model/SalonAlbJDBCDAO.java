package com.salonAlbum.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalonAlbJDBCDAO implements SalonAlbDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G3";
	String passwd = "123456";

	// 準備SQL指令
	private static final String INSERT_STMT = "INSERT INTO SALONALBUM (salAlbNo, salNo, salPic) VALUES ('BP' || LPAD(SALALBNO_SEQ.nextval,3,'0'), ?, ?) ";
	//INSERT INTO salonalbum(salalbno,SALNO,SALPORTINFO)VALUES('BP' || LPAD(SALALBNO_SEQ.nextval,3,'0'),'B001','我們是B001美容店');
	// 顯示美容店的所有相片
	private static final String GET_ALL_STMT = "SELECT salAlbNo, SALPIC, salPortInfo FROM SALONALBUM where salno=?";
	private static final String GET_ONE_PIC = "SELECT SALPIC, salPortInfo FROM SALONALBUM where salAlbNo=?";
	private static final String DELETE = "DELETE FROM SALONALBUM where salAlbNo=?";
//	private static final String UPDATE = "UPDATE SALONALBUM set salNo=?, salPic=?, salPortInfo=? where salAlbNo=?";
	
	//測試用main方法
//	public static void main(String[] args) {
//		SalonAlbVO vo = new SalonAlbVO();
//		vo.setSalNo("B002");
//		vo.setSalAlbNo("BP007");
		//塞圖測試用
//		byte[] pic = null;
//		try {
//			pic = getPictureByteArray("images/123.jpeg");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//以上測試用
//		vo.setSalPic(pic);
//		vo.setSalPortInfo("大熊變身年輕帥哥!比熊犬改裝!!!");
		
//		SalonAlbJDBCDAO test = new SalonAlbJDBCDAO();
//		test.insert(vo);
//		test.delete(vo.getSalAlbNo());
//		test.getOnePic("BP007");
//	}

	@Override
	public void insert(SalonAlbVO salAVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, salAVO.getSalNo());
			pstmt.setBytes(2, salAVO.getSalAlbPic());
//			pstmt.setString(3, salAVO.getSalPortInfo());
			
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setString(1, salAVO.getSalNo());
//			
//			pstmt.setBytes(2, salAVO.getSalPic());
//			pstmt.setString(3, salAVO.getSalPortInfo());
//			pstmt.setString(4, salAVO.getSalAlbNo());
//			
//			pstmt.executeUpdate();
//			pstmt.clearParameters();
//			
//			con.commit();
//			con.setAutoCommit(true);
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		}  catch (SQLException se) {
//			try {
//				con.rollback();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salAlbNo);

			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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

	@Override
	public List<SalonAlbVO> findBySalonKey(String salno) {
		 List<SalonAlbVO> list = new  ArrayList<SalonAlbVO>();
		SalonAlbVO salbvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1, salno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
								
				salbvo = new SalonAlbVO();
				salbvo.setSalAlbNo(rs.getString("alAlbNo"));
				salbvo.setSalAlbPic(rs.getBytes("salpic")); //存取DB裡面的BLOB資料
				salbvo.setSalPortInfo(rs.getString("salPortInfo"));
				
				list.add(salbvo);
			}
			con.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_PIC);
			
			pstmt.setString(1, salAlbNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				salp = new SalonAlbVO();
				salp.setSalAlbPic(rs.getBytes("salpic"));
				salp.setSalPortInfo(rs.getString("salPortInfo"));
			}
			
		}catch (SQLException | ClassNotFoundException se) {
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
	
//***************移到controller
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
