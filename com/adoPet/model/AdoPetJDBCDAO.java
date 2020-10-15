package com.adoPet.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.adoPetAlbum.model.AdoPetAlbumVO;

public class AdoPetJDBCDAO implements AdoPetDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "EA103G3";
	private static final String PASSWORD = "123456";

	private static final String INSERT_ADOPET = "INSERT INTO ADOPET (ADOPETNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,PETWEIGHT,PETCAT,PETCHAR,LOCATION) VALUES('A'||lpad(ADOPET_SEQ.NEXTVAL,3,'0'),'0',?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_APPFORM = "UPDATE ADOPET SET MEMNO=?, APPFORM=? , ADOSTATUS='1' WHERE ADOPETNO=?";

	private static final String UPDATE_ADOPET = "UPDATE ADOPET SET EMPNO=?, ADOSTATUS=?, PETTYPE=?,PETNAME=?,PPETBREED=?,PETSEX=?,PETBIRTH=?,PETWEIGHT=?,PETCAT=?,PETCHAR=?,LOCATION=? WHERE ADOPETNO=?";
	private static final String UPDATE_ADOPET_STATUS = "UPDATE ADOPET SET ADOSTATUS=?,EMPNO=? WHERE ADOPETNO=?";
	private static final String SEARCH_ADOPET = "SELECT ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM (SELECT ADOPETNO,PETTYPE,PETNAME,ADOSTATUS,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET) WHERE ADOSTATUS='0'";

	private static final String MANAGE_BY_STATUS = "SELECT ADOPETNO,MEMNO,EMPNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION,APPFORM FROM ADOPET WHERE ADOSTATUS=?";
	// 管理員查詢單一寵物
	private static final String GET_ONE_ADOPET = "SELECT ADOPETNO,MEMNO,EMPNO,ADOSTATUS,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION,APPFORM FROM ADOPET WHERE ADOPETNO=?";

	// 查詢全部未領養寵物
	private static final String GET_ALL_ADOPET = "SELECT ADOPETNO,PETTYPE,PETNAME,PPETBREED,PETSEX,PETBIRTH,TRUNC(months_between(sysdate, PETBIRTH)/12,1)AS age,PETWEIGHT,PETCAT,PETCHAR,LOCATION FROM ADOPET WHERE ADOSTATUS='0' ORDER BY ADOPETNO DESC";


	private static final String INSERT_ADOPETALBUM = "INSERT INTO ADOPETALBUM(ADOPICNO,ADOPETNO,ADOPETPIC) VALUES('AP'||lpad(ADOPETALBUM_SEQ.NEXTVAL,3,'0'),?,?)";
//	private static final String INSERT_ADOPETALBUM = "INSERT INTO ADOPETALBUM(ADOPICNO,ADOPETNO,ADOPETPIC) VALUES('AP'||lpad(ADOPETALBUM_SEQ.NEXTVAL,3,'0'),?,?)";
//	private static final String UPDATE_ADOPETALBUM = "UPDATE ADOPETALBUM SET ADOPETPIC=? WHERE ADOPICNO=? AND ADOPETNO=?)";

	@Override
	public void insert(List<AdoPetAlbumVO> picArr, AdoPetVO adoPetVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String adoPetNo = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

//			con.setAutoCommit(false);

			/* 綁定自增主鍵，並insert進ADOPET table */
			String cols[] = { "ADOPETNO" };
			pstmt = con.prepareStatement(INSERT_ADOPET, cols);

			pstmt.setInt(1, adoPetVO.getPetType());
			pstmt.setString(2, adoPetVO.getPetName());
			pstmt.setString(3, adoPetVO.getPetBreed());
			pstmt.setInt(4, adoPetVO.getPetSex());
			pstmt.setDate(5, adoPetVO.getPetBirth());
			pstmt.setDouble(6, adoPetVO.getPetWeight());
			pstmt.setInt(7, adoPetVO.getPetCat());
			pstmt.setString(8, adoPetVO.getPetChar());
			pstmt.setString(9, adoPetVO.getLocation());

			pstmt.executeUpdate();

			/* 取得自增主鍵 ADOPETNO */
			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				adoPetNo = rs.getString(1);
				System.out.println("adoPetNo" + adoPetNo);
			} else {
				System.out.println("未取得寵物自增主鍵");
			}
			rs.close();

//			/* 將自增主鍵新增給ADOPETALBUM，要同時完成insert才會commit */
			for (AdoPetAlbumVO adoPic : picArr) {
				pstmt.clearParameters();
				pstmt = con.prepareStatement(INSERT_ADOPETALBUM);
				pstmt.setString(1, adoPetNo);

				pstmt.setBytes(2, adoPic.getAdoPetPic());
				pstmt.executeUpdate();

			}

			con.commit();
			con.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException sq) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("A database error occured. " + sq.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}

		}

	}

	@Override
	public void update(AdoPetVO adoPetVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_ADOPET);

			pstmt.setString(1, adoPetVO.getEmpNo());
			pstmt.setInt(2, adoPetVO.getAdoStatus());
			pstmt.setInt(3, adoPetVO.getPetType());
			pstmt.setString(4, adoPetVO.getPetName());
			pstmt.setString(5, adoPetVO.getPetBreed());
			pstmt.setInt(6, adoPetVO.getPetSex());
			pstmt.setDate(7, adoPetVO.getPetBirth());
			pstmt.setDouble(8, adoPetVO.getPetWeight());
			pstmt.setInt(9, adoPetVO.getPetCat());
			pstmt.setString(10, adoPetVO.getPetChar());
			pstmt.setString(11, adoPetVO.getLocation());
			pstmt.setString(12, adoPetVO.getAdoPetNo());
			pstmt.executeUpdate();

//			pstmt.setInt(1, adoPetVO.getAdoStatus());
//			pstmt.setInt(2, adoPetVO.getPetType());
//			pstmt.setString(3, adoPetVO.getPetName());
//			pstmt.setString(4, adoPetVO.getPetBreed());
//			pstmt.setInt(5, adoPetVO.getPetSex());
//			pstmt.setDate(6, adoPetVO.getPetBirth());
//			pstmt.setDouble(7, adoPetVO.getPetWeight());
//			pstmt.setInt(8, adoPetVO.getPetCat());
//			pstmt.setString(9, adoPetVO.getPetChar());
//			pstmt.setString(10, adoPetVO.getLocation());
//			pstmt.setString(11, adoPetVO.getAdoPetNo());
//			pstmt.executeUpdate();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Could't load database driver." + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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

	}

	@Override
	public void insertAppForm(String memNo, String adoPetNo, byte[] appForm) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			pstmt = con.prepareStatement(INSERT_APPFORM);

			pstmt.setString(1, memNo);
			pstmt.setBytes(2, appForm);
			pstmt.setString(3, adoPetNo);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException sq) {

			throw new RuntimeException("A database error occured. " + sq.getMessage());

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqq) {

					sqq.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqt) {

					sqt.printStackTrace(System.err);
				}

			}
		}
	}

	@Override
	public AdoPetVO findByPrimaryKey(String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdoPetVO adoPetVO = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_ADOPET);
			pstmt.setString(1, adoPetNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adoPetVO = new AdoPetVO();
				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setMemNo(rs.getString("MEMNO"));
				adoPetVO.setEmpNo(rs.getString("EMPNO"));
				adoPetVO.setAdoStatus(rs.getInt("ADOSTATUS"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));

				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				adoPetVO.setAppForm(rs.getBytes("APPFORM"));

			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver" + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured" + se.getMessage());
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

		return adoPetVO;
	}

	@Override
	public List<AdoPetVO> SearchAdpPet(String sentence) {

		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SEARCH_ADOPET + sentence);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));
				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				list.add(adoPetVO);
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

	@Override
	public List<AdoPetVO> getAll() {
		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_ADOPET);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));

				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));

				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				list.add(adoPetVO);
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

	public static InputStream getPictureStream(String path) {

		FileInputStream fis = null;
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Couldn't find picture" + e.getMessage());

		}

		return fis;
	}

	@Override
	public List<AdoPetVO> findByAdoStatus(Integer adoStatus) {
		List<AdoPetVO> list = new ArrayList<AdoPetVO>();
		AdoPetVO adoPetVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(MANAGE_BY_STATUS);
			pstmt.setInt(1, adoStatus);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				adoPetVO = new AdoPetVO();

				adoPetVO.setAdoPetNo(rs.getString("ADOPETNO"));
				adoPetVO.setPetType(rs.getInt("PETTYPE"));
				adoPetVO.setPetName(rs.getString("PETNAME"));
				adoPetVO.setPetBreed(rs.getString("PPETBREED"));
				adoPetVO.setPetSex(rs.getInt("PETSEX"));
				adoPetVO.setPetBirth(rs.getDate("PETBIRTH"));
				adoPetVO.setAge(rs.getDouble("age"));
				adoPetVO.setPetWeight(rs.getDouble("PETWEIGHT"));
				adoPetVO.setPetCat(rs.getInt("PETCAT"));
				adoPetVO.setPetChar(rs.getString("PETCHAR"));
				adoPetVO.setLocation(rs.getString("LOCATION"));
				adoPetVO.setAppForm(rs.getBytes("APPFORM"));
				list.add(adoPetVO);
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

	@Override
	public void update_status(Integer adoStatus, String empNo, String adoPetNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_ADOPET_STATUS);
			pstmt.setInt(1, adoStatus);
			pstmt.setString(2, empNo);
			pstmt.setString(3, adoPetNo);
			pstmt.executeQuery();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver." + ce.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	}

	public static void main(String[] args) {

		AdoPetJDBCDAO dao = new AdoPetJDBCDAO();

//		 新增至ADOPET table (待領養寵物)
//		AdoPetVO adoPetVO1 = new AdoPetVO();
//		adoPetVO1.setPetType(1);
//		adoPetVO1.setPetName("測試貓yyy");
//		adoPetVO1.setPetBreed("混種貓hhhhh");
//		adoPetVO1.setPetSex(0);
//		adoPetVO1.setPetBirth(java.sql.Date.valueOf("2020-09-26"));
//		adoPetVO1.setPetWeight(new Double(7.5));
//		adoPetVO1.setPetCat(1);
//		adoPetVO1.setPetChar("123"); // 測試若欄位可為空值的，沒有set進去也可以正常執行SQL
//		adoPetVO1.setLocation("320桃園市中壢區中大路300號");
//		dao.insert(adoPetVO1);

//		// insert修改retrn adoPetNoA後，是可以讓自增主鍵給別人用的
//		String nono = dao.insert(adoPetVO1);
//		System.out.println("測試跟查詢" + nono);

		// 修改ADOPET table (照片尚未修改)
//		AdoPetVO adoPetVO2 = new AdoPetVO();
//		adoPetVO2.setEmpNo("");
//		adoPetVO2.setAdoStatus(1);
//		adoPetVO2.setPetType(1);
//		adoPetVO2.setPetName("測試貓貓修改");
//		adoPetVO2.setPetBreed("混種貓12");
//		adoPetVO2.setPetSex(0);
//		adoPetVO2.setPetBirth(java.sql.Date.valueOf("2020-09-27"));
//		adoPetVO2.setPetWeight(new Double(10.9));
//		adoPetVO2.setPetCat(1);
//		adoPetVO2.setPetChar("修改修改XXX"); // 測試若欄位可為空值的，沒有set進去也可以正常執行SQL
//		adoPetVO2.setLocation("桃園號123");
//		adoPetVO2.setAdoPetNo("A021");
////
//		dao.update(adoPetVO2);

		// 單一查詢
//		 AdoPetVO adoPetVO3 = dao.findByPrimaryKey(nono); 可以用insert return 的adoPetNo查詢
//		AdoPetVO adoPetVO3 = dao.findByPrimaryKey("A003");
//		System.out.print(adoPetVO3.getAdoPetNo() + ",");
//		System.out.print(adoPetVO3.getMemNo() + ",");
//		System.out.print(adoPetVO3.getEmpNo() + ",");
//		System.out.print(adoPetVO3.getAdoStatus() + ",");
//		System.out.print(adoPetVO3.getPetType() + ",");
//		System.out.print(adoPetVO3.getPetName() + ",");
//		System.out.print(adoPetVO3.getPetBreed() + ",");
//		System.out.print(adoPetVO3.getPetSex() + ",");
//		System.out.print(adoPetVO3.getPetBirth() + ",");
//		System.out.print(adoPetVO3.getAge() + ",");
//		System.out.print(adoPetVO3.getPetWeight() + ",");
//		System.out.print(adoPetVO3.getPetCat() + ",");
//		System.out.print(adoPetVO3.getPetChar() + ",");
//		System.out.print(adoPetVO3.getLocation() + ",");
//		System.out.print(adoPetVO3.getAppForm() + ",");

		// 查全部待領養

		List<AdoPetVO> list = dao.getAll();
		for(AdoPetVO adoPetVO4 : list) {
		System.out.print(adoPetVO4.getAdoPetNo() + ",");
		System.out.print(adoPetVO4.getMemNo() + ",");
		System.out.print(adoPetVO4.getEmpNo() + ",");
		System.out.print(adoPetVO4.getAdoStatus() + ",");
		System.out.print(adoPetVO4.getPetType() + ",");
		System.out.print(adoPetVO4.getPetName() + ",");
		System.out.print(adoPetVO4.getPetBreed() + ",");
		System.out.print(adoPetVO4.getPetSex() + ",");
		System.out.print(adoPetVO4.getPetBirth() + ",");
		System.out.print(adoPetVO4.getAge() + ",");
		System.out.print(adoPetVO4.getPetWeight() + ",");
		System.out.print(adoPetVO4.getPetCat() + ",");
		System.out.print(adoPetVO4.getPetChar() + ",");
		System.out.print(adoPetVO4.getLocation() + ",");
		System.out.println();
		}

//		 繳交form是OK的，先用圖片測試
//		InputStream is = getPictureStream(
//				"C:/EA103_WebApp/eclipse_WTP_workspace1/adoPet/WebContent/adoPetAlbum/A010.jpg");
//		byte[] formArr = null;
//		try {
//			formArr = new byte[is.available()];
//			is.read(formArr);
//			is.close();
//			dao.insertAppForm("M002","A019", formArr);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		List<AdoPetVO> list = dao.SearchAdpPet("AND PETTYPE='1' AND age >=1");
//		for (AdoPetVO adoPetVO5 : list) {
//			System.out.print(adoPetVO5.getAdoPetNo() + ",");
//			System.out.print(adoPetVO5.getMemNo() + ",");
//			System.out.print(adoPetVO5.getEmpNo() + ",");
//			System.out.print(adoPetVO5.getAdoStatus() + ",");
//			System.out.print(adoPetVO5.getPetType() + ",");
//			System.out.print(adoPetVO5.getPetName() + ",");
//			System.out.print(adoPetVO5.getPetBreed() + ",");
//			System.out.print(adoPetVO5.getPetSex() + ",");
//			System.out.print(adoPetVO5.getPetBirth() + ",");
//			System.out.print(adoPetVO5.getAge() + ",");
//			System.out.print(adoPetVO5.getPetWeight() + ",");
//			System.out.print(adoPetVO5.getPetCat() + ",");
//			System.out.print(adoPetVO5.getPetChar() + ",");
//			System.out.print(adoPetVO5.getLocation() + ",");
//	
//			System.out.println();
//			

//		List<AdoPetVO> list = dao.findByAdoStatus(1);
//		for (AdoPetVO adoPetVO6 : list) {
//			System.out.print(adoPetVO6.getAdoPetNo() + ",");
//			System.out.print(adoPetVO6.getMemNo() + ",");
//			System.out.print(adoPetVO6.getEmpNo() + ",");
//			System.out.print(adoPetVO6.getAdoStatus() + ",");
//			System.out.print(adoPetVO6.getPetType() + ",");
//			System.out.print(adoPetVO6.getPetName() + ",");
//			System.out.print(adoPetVO6.getPetBreed() + ",");
//			System.out.print(adoPetVO6.getPetSex() + ",");
//			System.out.print(adoPetVO6.getPetBirth() + ",");
//			System.out.print(adoPetVO6.getAge() + ",");
//			System.out.print(adoPetVO6.getPetWeight() + ",");
//			System.out.print(adoPetVO6.getPetCat() + ",");
//			System.out.print(adoPetVO6.getPetChar() + ",");
//			System.out.print(adoPetVO6.getLocation() + ",");
//	
//			System.out.println();

//	}
	}

}
