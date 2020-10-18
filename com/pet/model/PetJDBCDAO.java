package com.pet.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.pet.model.*;

public class PetJDBCDAO implements PetDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "RICHY";
	String password = "123456";

//	SQL
	private static final String INSERT = "INSERT into Pet (PetNo, memNo, petName, petType, petCat, petSex, petBirth, petChar, petPhoto) "
			+ "values ('P'||LPAD(pet_seq.nextval, 5,'0'), ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE Pet set PetName=?, petType=?, petCat=?, petSex=?, petBirth=?, petChar=?, petPhoto=? where petNo=?";
	private static final String DELETE = "UPDATE Pet set isDel = 1 WHERE petNo=?";
	private static final String GET_ONE_PET = "SELECT * FROM Pet WHERE PETNO=?";
	private static final String GET_ALL_PET_BY_MEMNO = "SELECT * FROM PET WHERE MEMNO=? ORDER BY PETNO";
	private static final String GET_ALL = "SELECT * FROM PET";

	@Override
	public void insert(PetVO petVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(INSERT);
//			"INSERT into Pet (PetNo, memNo, petName, petType, petCat, petSex, petBirth, petChar) values ('P'||LPAD(pet_seq, 5,'0'), ?, ?, ?, ?, ?, ?, ?)";

			pstmt.setString(1, petVO.getMemNo());
			pstmt.setString(2, petVO.getPetName());
			pstmt.setInt(3, petVO.getPetType());
			pstmt.setInt(4, petVO.getPetCat());
			pstmt.setInt(5, petVO.getPetSex());
			pstmt.setDate(6, petVO.getPetBirth());
			pstmt.setString(7, petVO.getPetChar());
			pstmt.setBytes(8, petVO.getPetPhoto());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void update(PetVO petVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(UPDATE);
//			"UPDATE Pet set PetName=?, petType=?, petCat=?, petSex=?, petBirth=?, petChar=?";

			pstmt.setString(1, petVO.getPetName());
			pstmt.setInt(2, petVO.getPetType());
			pstmt.setInt(3, petVO.getPetCat());
			pstmt.setInt(4, petVO.getPetSex());
			pstmt.setDate(5, petVO.getPetBirth());
			pstmt.setString(6, petVO.getPetChar());
			pstmt.setString(7, petVO.getPetNo());
			pstmt.setBytes(8, petVO.getPetPhoto());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public void delete(String petNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(DELETE);
//			"UPDATE Pet set isDel = 1 WHERE petNo=?";
			pstmt.setString(1, petNo);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
	public PetVO findByPrimaryKey(String petNo) {
		PetVO petVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_ONE_PET);
			
			pstmt.setString(1, petNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				petVO = new PetVO();
				petVO.setPetName(rs.getString("petName"));
				petVO.setPetBirth(rs.getDate("petBirth"));
				petVO.setPetCat(rs.getInt("petCat"));
				petVO.setPetType(rs.getInt("petType"));
				petVO.setPetSex(rs.getInt("petSex"));
				petVO.setPetChar(rs.getString("petChar"));	
				petVO.setPetNo(rs.getString("petNo"));
				petVO.setMemNo(rs.getString("memNo"));
				petVO.setPetPhoto(rs.getBytes("petPhoto"));
				petVO.setIsDel(rs.getInt("isDel"));

			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return petVO;
	}

	@Override
	public List<PetVO> getAllPet(String memNo) {
		List<PetVO> list = new ArrayList<PetVO>();
		PetVO petVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_ALL_PET_BY_MEMNO);
			
			pstmt.setString(1, memNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				petVO = new PetVO();
				petVO.setPetName(rs.getString("petName"));
				petVO.setPetBirth(rs.getDate("petBirth"));
				petVO.setPetCat(rs.getInt("petCat"));
				petVO.setPetType(rs.getInt("petType"));
				petVO.setPetSex(rs.getInt("petSex"));
				petVO.setPetChar(rs.getString("petChar"));	
				petVO.setPetNo(rs.getString("petNo"));
				petVO.setMemNo(rs.getString("memNo"));
				petVO.setPetPhoto(rs.getBytes("petPhoto"));
				petVO.setIsDel(rs.getInt("isDel"));
				list.add(petVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	@Override
	public List<PetVO> getAll() {
		List<PetVO> list = new ArrayList<PetVO>();
		PetVO petVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_ALL);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				petVO = new PetVO();
				petVO.setPetName(rs.getString("petName"));
				petVO.setPetBirth(rs.getDate("petBirth"));
				petVO.setPetCat(rs.getInt("petCat"));
				petVO.setPetType(rs.getInt("petType"));
				petVO.setPetSex(rs.getInt("petSex"));
				petVO.setPetChar(rs.getString("petChar"));	
				petVO.setPetNo(rs.getString("petNo"));
				petVO.setMemNo(rs.getString("memNo"));
				petVO.setPetPhoto(rs.getBytes("petPhoto"));
				petVO.setIsDel(rs.getInt("isDel"));
	
				list.add(petVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	public static void main(String[] args) throws IOException {
		PetJDBCDAO dao = new PetJDBCDAO();
		//insert
		PetVO petVO1 = new PetVO();
		petVO1.setPetName("歐拉歐拉");
		petVO1.setPetSex(1);
		petVO1.setMemNo("M015");
		petVO1.setPetBirth(java.sql.Date.valueOf("2002-07-07"));
		petVO1.setPetCat(2);
		petVO1.setPetChar("我家貓咪會後空翻!");
		petVO1.setPetType(1);
		byte[] petPhoto = getPictureByteArray("D:/User/Desktop/petphoto/dog31.jpg");
		petVO1.setPetPhoto(petPhoto);
		dao.insert(petVO1);
		
		//update
//		PetVO petVO2 = new PetVO();
//		petVO2.setPetName("小黑");
//		petVO2.setPetSex(1);
//		petVO2.setMemNo("M016");
//		petVO2.setPetBirth(java.sql.Date.valueOf("2012-02-29"));
//		petVO2.setPetCat(2);
//		petVO2.setPetChar("我家貓貓會後空翻");
//		petVO2.setPetType(2);
//		petVO2.setPetNo("P00002");
//		dao.update(petVO2);
		
		//delete
//		dao.delete("P00002");
		
		//findByPrimaryKey
//		PetVO petVO3 = dao.findByPrimaryKey("P00002");
//		System.out.print(petVO3.getPetNo()+",");
//		System.out.print(petVO3.getMemNo()+",");
//		System.out.print(petVO3.getPetName()+",");
//		System.out.print(petVO3.getPetType()+",");
//		System.out.print(petVO3.getPetCat()+",");
//		System.out.print(petVO3.getPetSex()+",");
//		System.out.println(petVO3.getPetBirth());
//		System.out.println("-----------------------------------------");
		
		//getAll
//		List<PetVO> list = dao.getAllPet("M015");
//		for(PetVO aPet:list) {
//			System.out.print(aPet.getPetNo()+",");
//			System.out.print(aPet.getMemNo()+",");
//			System.out.print(aPet.getPetName()+",");
//			System.out.print(aPet.getPetType()+",");
//			System.out.print(aPet.getPetCat()+",");
//			System.out.print(aPet.getPetSex()+",");
//			System.out.println(aPet.getPetBirth());
//			System.out.println("-----------------------------------------");
//		}
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
