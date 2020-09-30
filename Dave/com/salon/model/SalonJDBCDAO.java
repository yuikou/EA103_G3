package com.salon.model;

import java.sql.*;
import java.util.*;



public class SalonJDBCDAO  implements SalonDAO_interface{
	String driver ="oracle.jdbc.driver.OracleDriver" ;
	String url = "jdbc:oracle:thin:@localhost:1521:XE";	
	String userid = "EA103G3";
	String passwd = "123456";

	private static final String INSERT_STMT =
			"INSERT INTO SALON(SALNO,SALNAME,SALOWNER,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALINFO,SALPETTYPE) VALUES('B' || lpad(SALNO_SEQ.NEXTVAL,3,'0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE =
			"DELETE FROM SALON WHERE SALNO = ?";
	private static final String UPDATE =
			"UPDATE SALON SET salname=?, salowner=?, salph=? , salmail=?,SALCITY=?,SALDIST=?, saladr=?, salac=?, salpw=? , salstime=?, saletime=?, salremit=?, bankcode=?, salstatus=?, salinfo=?,SALPETTYPE=? WHERE salno=?" ;
	private static final String GET_ALL_STMT=
			"SELECT SALNO,SALNAME,SALOWNER,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALINFO,SALPETTYPE FROM SALON ORDER BY SALNO";
	private static final String GET_ONE_STMT=
			"SELECT SALNO,SALNAME,SALOWNER,SALPH,SALMAIL,SALCITY,SALDIST,SALADR,SALAC,SALPW,SALSTIME,SALETIME,SALREMIT,BANKCODE,SALSTATUS,SALINFO,SALPETTYPE FROM SALON  WHERE SALNO = ?";

	
	@Override
	public void insert(SalonVO salonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, salonVO.getSalName());
			pstmt.setString(2,salonVO.getSalOwner() );
			pstmt.setString(3, salonVO.getSalPh());
			pstmt.setString(4, salonVO.getSalMail());			
			pstmt.setString(5, salonVO.getSalCity());
			pstmt.setString(6, salonVO.getSalDist());
			pstmt.setString(7, salonVO.getSalAdr());
			pstmt.setString(8, salonVO.getSalAc());
			pstmt.setString(9, salonVO.getSalPw());			
			pstmt.setString(10, salonVO.getSalSTime());
			pstmt.setString(11, salonVO.getSalETime());
			pstmt.setString(12, salonVO.getSalRemit());
			pstmt.setString(13, salonVO.getBankCode());
			pstmt.setInt(14, salonVO.getSalStatus());
			pstmt.setString(15, salonVO.getSalInfo());
			pstmt.setInt(16, salonVO.getSalPetType());
			
			
			pstmt.executeUpdate();
						
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {			
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void update(SalonVO salonVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, salonVO.getSalName());
			pstmt.setString(2,salonVO.getSalOwner() );
			pstmt.setString(3, salonVO.getSalPh());
			pstmt.setString(4, salonVO.getSalMail());
			pstmt.setString(5, salonVO.getSalCity());
			pstmt.setString(6, salonVO.getSalDist());
			pstmt.setString(7, salonVO.getSalAdr());
			pstmt.setString(8, salonVO.getSalAc());
			pstmt.setString(9, salonVO.getSalPw());			
			pstmt.setString(10, salonVO.getSalSTime());
			pstmt.setString(11, salonVO.getSalETime());
			pstmt.setString(12, salonVO.getSalRemit());
			pstmt.setString(13, salonVO.getBankCode());
			pstmt.setInt(14, salonVO.getSalStatus());		
			pstmt.setString(15, salonVO.getSalInfo());
			pstmt.setInt(16, salonVO.getSalPetType());
			pstmt.setString(17, salonVO.getSalNo());
			
			
			
			
			pstmt.executeUpdate();
			
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void delete(String salNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, salNo);
			
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch (SQLException se) {
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
	public List<SalonVO> getall() {
		List<SalonVO> list = new ArrayList<SalonVO>();
		SalonVO salonVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
		
			
			while(rs.next()) {
				salonVO = new SalonVO();
				salonVO.setSalNo(rs.getString("salNo"));
				salonVO.setSalName(rs.getString("salName"));
				salonVO.setSalOwner(rs.getString("salOwner"));
				salonVO.setSalPh(rs.getString("salPh"));
				salonVO.setSalMail(rs.getString("salMail"));
				salonVO.setSalCity(rs.getString("salCity"));
				salonVO.setSalDist(rs.getString("salDist"));
				salonVO.setSalAdr(rs.getString("salAdr"));
				salonVO.setSalAc(rs.getString("salAc"));
				salonVO.setSalPw(rs.getString("salPw"));
				salonVO.setSalSTime(rs.getString("salSTime"));
				salonVO.setSalETime(rs.getString("salETime"));
				salonVO.setSalRemit(rs.getString("salRemit"));
				salonVO.setBankCode(rs.getString("bankCode"));
				salonVO.setSalStatus(rs.getInt("salStatus"));
				salonVO.setSalInfo(rs.getString("salInfo"));
				salonVO.setSalPetType(rs.getInt("salPetType"));
				
				list.add(salonVO);
				
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public SalonVO findByPrimaryKey(String salNo) {
	SalonVO salonVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);
		pstmt = con.prepareStatement(GET_ONE_STMT);

		pstmt.setString(1, salNo);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			salonVO = new SalonVO();
			
			salonVO.setSalNo(rs.getString("salNo"));
			salonVO.setSalName(rs.getString("salName"));
			salonVO.setSalOwner(rs.getString("salOwner"));
			salonVO.setSalPh(rs.getString("salPh"));
			salonVO.setSalMail(rs.getString("salMail"));
			salonVO.setSalCity(rs.getString("salCity"));
			salonVO.setSalDist(rs.getString("salDist"));
			salonVO.setSalAdr(rs.getString("salAdr"));
			salonVO.setSalAc(rs.getString("salAc"));
			salonVO.setSalPw(rs.getString("salPw"));
			salonVO.setSalSTime(rs.getString("salSTime"));
			salonVO.setSalETime(rs.getString("salETime"));
			salonVO.setSalRemit(rs.getString("salRemit"));
			salonVO.setBankCode(rs.getString("bankCode"));
			salonVO.setSalStatus(rs.getInt("salStatus"));
			salonVO.setSalInfo(rs.getString("salInfo"));
			salonVO.setSalPetType(rs.getInt("salPetType"));			
		}
	}catch (ClassNotFoundException e) {
		throw new RuntimeException("Couldn't load database driver. "
				+ e.getMessage());
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
		return salonVO;
	}

//測試用
public static void main(String [] args) {
	SalonJDBCDAO dao = new SalonJDBCDAO();
	
	
	//新增
//	SalonVO salVO1 =new SalonVO();
//	salVO1.setSalName("腳丫星球毛孩生活館");
//	salVO1.setSalOwner("小小吳");
//	salVO1.setSalPh("0226933008");
//	salVO1.setSalMail("abc5412@hotmail.com");
//	salVO1.setSalCity("新北市");
//	salVO1.setSalDist("汐止區");
//	salVO1.setSalAdr("康寧街409號");
//	salVO1.setSalAc("ijk6654");
//	salVO1.setSalPw("ijk6654");
//	salVO1.setSalSTime("1000");
//	salVO1.setSalETime("2000");
//	salVO1.setSalRemit("1234-51123");
//	salVO1.setBankCode("055");
//	salVO1.setSalStatus(1);
//	salVO1.setSalInfo("專業摩氧浴、頂級精油SPA，腳丫星球不只是一間寵物美容，更希望可以由內而外的照顧你捧在手心上的孩子。");
//	salVO1.setSalPetType(1);
//	dao.insert(salVO1);
	
	//修改
//	SalonVO salVO2 = new SalonVO();
//	salVO2.setSalNo("B001");
//	salVO2.setSalName("汪汪寵物澡堂");
//	salVO2.setSalOwner("老郭");
//	salVO2.setSalPh("0227976057");
//	salVO2.setSalMail("abc5412@hotmail.com");
//	salVO2.setSalCity("台北市");
//	salVO2.setSalDist("內湖區");
//	salVO2.setSalAdr("內湖路二段150號");
//	salVO2.setSalAc("ijk6654");
//	salVO2.setSalPw("ijk6654");
//	salVO2.setSalSTime("1300");
//	salVO2.setSalETime("1900");
//	salVO2.setSalRemit("1234-51123");
//	salVO2.setBankCode("067");
//	salVO2.setSalStatus(1);
//	salVO2.setSalInfo("一個給寵愛自家毛寶貝的洗澡美容場所，讓您跟您的毛寶貝能更加的親密 我們有提供自助洗跟包月美容服務!!");	
//	salVO2.setSalPetType(1);
//	dao.update(salVO2);
	
	//刪除
	
//	dao.delete("B008");	
	
	//查詢
//	List<SalonVO> list =dao.getall();
//	for(SalonVO asalon : list) {
//		System.out.println(asalon.getSalNo() + ",");
//		System.out.println(asalon.getSalName() + ",");
//		System.out.println(asalon.getSalOwner() + ",");
//		System.out.println(asalon.getSalPh() + ",");
//		System.out.println(asalon.getSalMail() + ",");
//		System.out.println(asalon.getSalCity() + ",");
//		System.out.println(asalon.getSalDist() + ",");
//		System.out.println(asalon.getSalAdr() + ",");
//		System.out.println(asalon.getSalAc() + ",");
//		System.out.println(asalon.getSalPw() + ",");
//		System.out.println(asalon.getSalSTime() + ",");
//		System.out.println(asalon.getSalETime() + ",");
//		System.out.println(asalon.getSalRemit() + ",");
//		System.out.println(asalon.getBankCode() + ",");
//		System.out.println(asalon.getSalStatus() + ",");
//		System.out.println(asalon.getSalInfo() + ",");
//		System.out.println(asalon.getSalPetType() + ",");
//		System.out.println("============================");
//	}
	
	//查詢
	SalonVO salVO3 = dao.findByPrimaryKey("B001");
	System.out.println(salVO3.getSalNo() + ",");
	System.out.println(salVO3.getSalName() + ",");
	System.out.println(salVO3.getSalOwner() + ",");
	System.out.println(salVO3.getSalPh() + ",");
	System.out.println(salVO3.getSalMail() + ",");
	System.out.println(salVO3.getSalCity() + ",");
	System.out.println(salVO3.getSalDist() + ",");
	System.out.println(salVO3.getSalAdr() + ",");
	System.out.println(salVO3.getSalAc() + ",");
	System.out.println(salVO3.getSalPw() + ",");
	System.out.println(salVO3.getSalSTime() + ",");
	System.out.println(salVO3.getSalETime() + ",");
	System.out.println(salVO3.getSalRemit() + ",");
	System.out.println(salVO3.getBankCode() + ",");
	System.out.println(salVO3.getSalStatus() + ",");
	System.out.println(salVO3.getSalInfo() + ",");
	System.out.println(salVO3.getSalPetType() + ",");
}
}
