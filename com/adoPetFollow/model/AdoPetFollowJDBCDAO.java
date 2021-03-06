package com.adoPetFollow.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adoPet.model.AdoPetVO;

public class AdoPetFollowJDBCDAO implements AdoPetFollowDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA103G3";
	private static final String PASSWORD = "123456";

	private static final String INSERT_AdoPetFollow = "INSERT INTO ADOPETFOLLOW(MEMNO,ADOPETNO) VALUES (?,?)";
	private static final String DELETE_AdoPetFollow = "DELETE FROM ADOPETFOLLOW WHERE MEMNO=? AND ADOPETNO=?";
	private static final String GET_ALL_AdoPetFollow = "SELECT ROWNUM,A.ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET A "
			+ "JOIN ADOPETFOLLOW AW ON A.ADOPETNO = AW.ADOPETNO WHERE AW.MEMNO IN(SELECT MEMNO FROM MEMBER_TABLE WHERE MEMNO=?) AND ADOSTATUS='0' ORDER BY ROWNUM DESC";

	@Override
	public Boolean insert(AdoPetFollowVO adoPetFollowVO) {
		Boolean insertFinished = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_AdoPetFollow);

			pstmt.setString(1, adoPetFollowVO.getMemNo());
			pstmt.setString(2, adoPetFollowVO.getAdoPetNo());

			// 新增一筆追蹤成功時，回傳true給Controller
			if (pstmt.executeUpdate() == 1) {
				insertFinished = true;
				con.commit();
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());

		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		}
		return insertFinished;
	}

	@Override
	public Boolean delete(String memNo, String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Boolean deleteFinished = false;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_AdoPetFollow);
			pstmt.setString(1, memNo);
			pstmt.setString(2, adoPetNo);

			if (pstmt.executeUpdate() == 1) {
				deleteFinished = true;
				con.commit();
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver." + ce.getMessage());
		} catch (SQLException se) {
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return deleteFinished;
	}

	@Override
	public List<AdoPetFollowVO> getAll(String memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdoPetFollowVO> list = new ArrayList<AdoPetFollowVO>();
		AdoPetFollowVO adoPetFollowVO = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_AdoPetFollow);

			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetFollowVO = new AdoPetFollowVO();

				adoPetFollowVO.setMemNo(rs.getString("MEMNO"));
				adoPetFollowVO.setAdoPetNo(rs.getString("ADOPETNO"));
				list.add(adoPetFollowVO);
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver." + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	public static void main(String[] args) {
		AdoPetFollowJDBCDAO adoPetFollowDAO = new AdoPetFollowJDBCDAO();

//		// 新增一筆
//
//		AdoPetFollowVO adoPetFollowVO = new AdoPetFollowVO();
//		adoPetFollowVO.setMemNo("M004");
//		adoPetFollowVO.setAdoPetNo("A005");
//		adoPetFollowDAO.insert(adoPetFollowVO);
//
//		// 刪除
//	
//		adoPetFollowDAO.delete("M004","A003");

		// 查詢全部追蹤待領養寵物
//		List<AdoPetVO> list = adoPetFollowDAO.getAll("M004");
		/*
		 * for (AdoPetVO adoPetVO3 : list) { System.out.print(adoPetVO3.getAdoPetNo() +
		 * ","); System.out.print(adoPetVO3.getMemNo() + ",");
		 * System.out.print(adoPetVO3.getEmpNo() + ",");
		 * System.out.print(adoPetVO3.getAdoStatus() + ",");
		 * System.out.print(adoPetVO3.getPetType() + ",");
		 * System.out.print(adoPetVO3.getPetName() + ",");
		 * System.out.print(adoPetVO3.getPetBreed() + ",");
		 * System.out.print(adoPetVO3.getPetSex() + ",");
		 * System.out.print(adoPetVO3.getPetBirth() + ",");
		 * System.out.print(adoPetVO3.getAge() + ",");
		 * System.out.print(adoPetVO3.getPetWeight() + ",");
		 * System.out.print(adoPetVO3.getPetCat() + ",");
		 * System.out.print(adoPetVO3.getPetChar() + ",");
		 * System.out.print(adoPetVO3.getLocation() + ",");
		 * System.out.print(adoPetVO3.getAppForm() + ","); System.out.println();
		 */
	}

}
