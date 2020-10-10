package com.sitSrv.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitSrvJDBCDAO implements SitSrvDAO_interface {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "EA103G3";
	private static String passwd = "123456";

	private static final String ADD_PSTMT = "INSERT INTO sitSrv VALUES ('SS' || lpad(sitSrv_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE sitSrv "
			+ "SET sitSrvName=?, sitSrvCode=?, sitNo=?, srvFee=?, srvInfo=?, srvArea=?, acpPetNum=?, acpPetTyp=?, "
			+ "careLevel=?, stayLoc=?, overnightLoc=?, SmkFree=?, hasChild=?, walkTime=?, eqpt=?, addBathing=?, addPickup=?, "
			+ "outOfSrv=?, isDel=? WHERE sitSrvNo=? ";
	private static final String ISDEL_PSTMT = "UPDATE sitSrv SET isDel=1 WHERE sitSrvNo=? ";
	private static final String GET_SIT_SRV = "SELECT * FROM sitSrv WHERE sitSrvNo=? ";
	private static final String GET_SIT_ALL_SRV = "SELECT * FROM sitSrv WHERE sitNo=? ";
	private static StringBuffer CHOOSE_SIT_FROM_SRV = new StringBuffer(
			"SELECT * From sitSrv WHERE sitSrvCode=? AND acpPetNum>=? AND acpPetTyp = ANY (");

	@Override
	public Boolean add(SitSrvVO sitSrv) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(ADD_PSTMT);

			pstmt.setString(1, sitSrv.getSitSrvName());
			pstmt.setString(2, sitSrv.getSitSrvCode());
			pstmt.setString(3, sitSrv.getSitNo());
			pstmt.setInt(4, sitSrv.getSrvFee());
			pstmt.setString(5, sitSrv.getSrvInfo());
			pstmt.setInt(6, sitSrv.getSrvArea());
			pstmt.setInt(7, sitSrv.getAcpPetNum());
			pstmt.setInt(8, sitSrv.getAcpPetTyp());
			pstmt.setObject(9, sitSrv.getCareLevel());
			pstmt.setObject(10, sitSrv.getStayLoc());
			pstmt.setObject(11, sitSrv.getOvernightLoc());
			pstmt.setObject(12, sitSrv.getSmkFree());
			pstmt.setObject(13, sitSrv.getHasChild());
			pstmt.setObject(14, sitSrv.getWalkTime());
			pstmt.setObject(15, sitSrv.getEqpt());
			pstmt.setObject(16, sitSrv.getAddBathing());
			pstmt.setObject(17, sitSrv.getAddPickup());
			pstmt.setInt(18, 2);
			pstmt.setInt(19, 0);

			if (pstmt.executeUpdate() == 1) {
				addOK = true;
			}
			con.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("新增失敗: " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return addOK;
	}

	@Override
	public Boolean update(SitSrvVO sitSrv) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, sitSrv.getSitSrvName());
			pstmt.setString(2, sitSrv.getSitSrvCode());
			pstmt.setString(3, sitSrv.getSitNo());
			pstmt.setInt(4, sitSrv.getSrvFee());
			pstmt.setString(5, sitSrv.getSrvInfo());
			pstmt.setInt(6, sitSrv.getSrvArea());
			pstmt.setInt(7, sitSrv.getAcpPetNum());
			pstmt.setInt(8, sitSrv.getAcpPetTyp());
			pstmt.setObject(9, sitSrv.getCareLevel());
			pstmt.setObject(10, sitSrv.getStayLoc());
			pstmt.setObject(11, sitSrv.getOvernightLoc());
			pstmt.setObject(12, sitSrv.getSmkFree());
			pstmt.setObject(13, sitSrv.getHasChild());
			pstmt.setObject(14, sitSrv.getWalkTime());
			pstmt.setObject(15, sitSrv.getEqpt());
			pstmt.setObject(16, sitSrv.getAddBathing());
			pstmt.setObject(17, sitSrv.getAddPickup());
			pstmt.setObject(18, sitSrv.getOutOfSrv());
			pstmt.setObject(19, sitSrv.getIsDel());
			pstmt.setString(20, sitSrv.getSitSrvNo());

			if (pstmt.executeUpdate() == 1) {
				updateOK = true;
			}
			con.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				con.rollback();
				throw new RuntimeException("修改失敗: " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return updateOK;
	}

	// 看似刪除其實是改狀態
	@Override
	public Boolean del(String sitSrvNo) {
		Boolean delOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(ISDEL_PSTMT);
			pstmt.setString(1, sitSrvNo);
			if (pstmt.executeUpdate() == 1) {
				delOK = true;
			}
			con.commit();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				con.rollback();
				throw new RuntimeException("刪除失敗: " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return delOK;
	}

	@Override
	public SitSrvVO get_OneSit_OneSrv(String sitSrvNo) {
		SitSrvVO sitSrv = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_SIT_SRV);
			pstmt.setString(1, sitSrvNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitSrv = new SitSrvVO();
				sitSrv.setSitSrvNo(rs.getString("sitSrvNo"));
				sitSrv.setSitSrvName(rs.getString("sitSrvName"));
				sitSrv.setSitSrvCode(rs.getString("sitSrvCode"));
				sitSrv.setSitNo(rs.getString("sitNo"));
				sitSrv.setSrvFee(rs.getInt("srvFee"));
				sitSrv.setSrvInfo(rs.getString("srvInfo"));
				sitSrv.setSrvArea(rs.getInt("srvArea"));
				sitSrv.setAcpPetNum(rs.getInt("acpPetNum"));
				sitSrv.setAcpPetTyp(rs.getInt("acpPetTyp"));
				sitSrv.setCareLevel(rs.getInt("careLevel"));
				sitSrv.setStayLoc(rs.getInt("stayLoc"));
				sitSrv.setOvernightLoc(rs.getInt("overnightLoc"));
				sitSrv.setSmkFree(rs.getInt("smkFree"));
				sitSrv.setHasChild(rs.getInt("hasChild"));
				sitSrv.setWalkTime(rs.getInt("walkTime"));
				sitSrv.setEqpt(rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sitSrv;
	}

	@Override
	public List<SitSrvVO> get_OneSit_AllSrv(String sitNo) {
		List<SitSrvVO> list = new ArrayList<SitSrvVO>();
		SitSrvVO sitSrv = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			pstmt = con.prepareStatement(GET_SIT_ALL_SRV);
			pstmt.setString(1, sitNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitSrv = new SitSrvVO();
				sitSrv.setSitSrvNo(rs.getString("sitSrvNo"));
				sitSrv.setSitSrvName(rs.getString("sitSrvName"));
				sitSrv.setSitSrvCode(rs.getString("sitSrvCode"));
				sitSrv.setSitNo(rs.getString("sitNo"));
				sitSrv.setSrvFee(rs.getInt("srvFee"));
				sitSrv.setSrvInfo(rs.getString("srvInfo"));
				sitSrv.setSrvArea(rs.getInt("srvArea"));
				sitSrv.setAcpPetNum(rs.getInt("acpPetNum"));
				sitSrv.setAcpPetTyp(rs.getInt("acpPetTyp"));
				sitSrv.setCareLevel(rs.getInt("careLevel"));
				sitSrv.setStayLoc(rs.getInt("stayLoc"));
				sitSrv.setOvernightLoc(rs.getInt("overnightLoc"));
				sitSrv.setSmkFree(rs.getInt("smkFree"));
				sitSrv.setHasChild(rs.getInt("hasChild"));
				sitSrv.setWalkTime(rs.getInt("walkTime"));
				sitSrv.setEqpt(rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
				list.add(sitSrv);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<SitSrvVO> choose_SitSrv(String sitSrvCode, Integer acpPetNum, Integer[] acpPetTyp, String appendSQL) {
		List<SitSrvVO> list = new ArrayList<SitSrvVO>();
		SitSrvVO sitSrv = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, passwd);
			for (int i = 0; i < acpPetTyp.length; i++) {
				CHOOSE_SIT_FROM_SRV.append("?,");
			}
			CHOOSE_SIT_FROM_SRV.deleteCharAt(CHOOSE_SIT_FROM_SRV.length()-1);
			CHOOSE_SIT_FROM_SRV.append(")");
//			System.out.println(CHOOSE_SIT_FROM_SRV);
			pstmt = con.prepareStatement(CHOOSE_SIT_FROM_SRV + appendSQL);
			pstmt.setString(1, sitSrvCode);
			pstmt.setInt(2, acpPetNum);
			for (int i = 0; i < acpPetTyp.length; i++) {
				pstmt.setInt(i+3, acpPetTyp[i]);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitSrv = new SitSrvVO();
				sitSrv.setSitSrvNo(rs.getString("sitSrvNo"));
				sitSrv.setSitSrvName(rs.getString("sitSrvName"));
				sitSrv.setSitSrvCode(rs.getString("sitSrvCode"));
				sitSrv.setSitNo(rs.getString("sitNo"));
				sitSrv.setSrvFee(rs.getInt("srvFee"));
				sitSrv.setSrvInfo(rs.getString("srvInfo"));
				sitSrv.setSrvArea(rs.getInt("srvArea"));
				sitSrv.setAcpPetNum(rs.getInt("acpPetNum"));
				sitSrv.setAcpPetTyp(rs.getInt("acpPetTyp"));
				sitSrv.setCareLevel(rs.getInt("careLevel"));
				sitSrv.setStayLoc(rs.getInt("stayLoc"));
				sitSrv.setOvernightLoc(rs.getInt("overnightLoc"));
				sitSrv.setSmkFree(rs.getInt("smkFree"));
				sitSrv.setHasChild(rs.getInt("hasChild"));
				sitSrv.setWalkTime(rs.getInt("walkTime"));
				sitSrv.setEqpt(rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
				list.add(sitSrv);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查詢失敗: " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		SitSrvJDBCDAO jdbcdao = new SitSrvJDBCDAO();

		// 新增
//		SitSrvVO vo1 = new SitSrvVO();
//		vo1.setSitSrvName("Day Care");
//		vo1.setSitSrvCode("DayCare");
//		vo1.setSitNo("S003");
//		vo1.setSrvFee(500);
//		vo1.setSrvInfo("狗狗安親服務，安親4小時為單位，超過每小時50塊錢");
//		vo1.setSrvArea(0);
//		vo1.setAcpPetNum(5);
//		vo1.setAcpPetTyp(4);
//		vo1.setCareLevel(0);
//		vo1.setStayLoc(0);
//		vo1.setOvernightLoc(null);
//		vo1.setSmkFree(1);
//		vo1.setHasChild(0);
//		vo1.setWalkTime(null);
//		vo1.setEqpt(null);
//		vo1.setAddBathing(1);
//		vo1.setAddPickup(1);
//		if (jdbcdao.add(vo1)) {
//			System.out.println("新增成功");
//		}

		// 修改
//		SitSrvVO vo2 = new SitSrvVO();
//		vo2.setSitSrvName("郭老師的高級BMW轎車");
//		vo2.setSitSrvCode("PetTaxi");
//		vo2.setSitNo("S001");
//		vo2.setSrvFee(30);
//		vo2.setSrvInfo("車輛款式:多元計程車(房車)，服務區域:皆可發車,下車地不拘" + 
//				"服務特色:" + 
//				"1.1次1組，不併車。" + 
//				"2.長途接送，不暈車。" + 
//				"3.不必關籠，好自在。" + 
//				"4.合格駕駛，最安心。" + 
//				"5.官方認證，有保障。" + 
//				"6.哩程計價，很合理。" + 
//				"7.24H預約，超便利。" + 
//				"8.定期保養，請放心。" + 
//				"9.車上設備，很週到。" + 
//				"0.跑馬顯示，不被叭。");
//		vo2.setSrvArea(3);
//		vo2.setAcpPetNum(3);
//		vo2.setAcpPetTyp(4);
//		vo2.setOutOfSrv(2);
//		vo2.setIsDel(0);
//		vo2.setSitSrvNo("SS005");
//		if (jdbcdao.update(vo2)) {
//			System.out.println("修改成功");
//		}

		// 刪除
//		if (jdbcdao.del("SS009")) {
//			System.out.println("刪除成功");
//		}
		// 查詢一個保姆的其中一個服務
//		SitSrvVO vo = jdbcdao.get_OneSit_OneSrv("SS005");
//		System.out.println("SitSrvNo = " + vo.getSitSrvNo());
//		System.out.println("SitSrvName = " + vo.getSitSrvName());
//		System.out.println("SitSrvCode = " + vo.getSitSrvCode());
//		System.out.println("SitNo = " + vo.getSitNo());
//		System.out.println("SrvFee = " + vo.getSrvFee());
//		System.out.println("SrvInfo = " + vo.getSrvInfo());
//		System.out.println("SrvArea = " + vo.getSrvArea());
//		System.out.println("AcpPetNum = " + vo.getAcpPetNum());
//		System.out.println("AcpPeTyp = " + vo.getAcpPetTyp());
//		System.out.println("CareLevel = " + vo.getCareLevel());
//		System.out.println("StayLoc = " + vo.getStayLoc());
//		System.out.println("OvernightLoc = " + vo.getOvernightLoc());
//		System.out.println("SmkFree = " + vo.getSmkFree());
//		System.out.println("HasChild = " + vo.getHasChild());
//		System.out.println("WalkTime = " + vo.getWalkTime());
//		System.out.println("EQPT = " + vo.getEqpt());
//		System.out.println("AddBathing = " + vo.getAddBathing());
//		System.out.println("AddPickup = " + vo.getAddPickup());
//		System.out.println("OutOfSrv = " + vo.getOutOfSrv());
//		System.out.println("isDel = " + vo.getIsDel());

		// 查詢一個保姆的全部服務
//		List<SitSrvVO> list = new ArrayList<SitSrvVO>();
//		list = jdbcdao.get_OneSit_AllSrv("S001");
//		System.out.println("--------------------以下是1個保姆的全部服務----------------------");
//		for (SitSrvVO sitsrv : list) {
//			System.out.println("SitSrvNo = " + sitsrv.getSitSrvNo());
//			System.out.println("SitSrvName = " + sitsrv.getSitSrvName());
//			System.out.println("SitSrvCode = " + sitsrv.getSitSrvCode());
//			System.out.println("SitNo = " + sitsrv.getSitNo());
//			System.out.println("SrvFee = " + sitsrv.getSrvFee());
//			System.out.println("SrvInfo = " + sitsrv.getSrvInfo());
//			System.out.println("SrvArea = " + sitsrv.getSrvArea());
//			System.out.println("AcpPetNum = " + sitsrv.getAcpPetNum());
//			System.out.println("AcpPeTyp = " + sitsrv.getAcpPetTyp());
//			System.out.println("CareLevel = " + sitsrv.getCareLevel());
//			System.out.println("StayLoc = " + sitsrv.getStayLoc());
//			System.out.println("OvernightLoc = " + sitsrv.getOvernightLoc());
//			System.out.println("SmkFree = " + sitsrv.getSmkFree());
//			System.out.println("HasChild = " + sitsrv.getHasChild());
//			System.out.println("WalkTime = " + sitsrv.getWalkTime());
//			System.out.println("EQPT = " + sitsrv.getEqpt());
//			System.out.println("AddBathing = " + sitsrv.getAddBathing());
//			System.out.println("AddPickup = " + sitsrv.getAddPickup());
//			System.out.println("OutOfSrv = " + sitsrv.getOutOfSrv());
//			System.out.println("isDel = " + sitsrv.getIsDel());
//			System.out.println("----------------------------------");
//		}
//		System.out.println("--------------------以上是1個保姆的全部服務----------------------");

		// 查詢符合條件的保姆
		List<SitSrvVO> list2 = new ArrayList<SitSrvVO>();
		Integer[] types = { 0, 1, 2, 3, 4 };

		list2 = jdbcdao.choose_SitSrv("DayCare", 1, types, "");

		System.out.println("--------------------以下是符合條件的保姆服務----------------------");
		for (SitSrvVO sitsrv : list2) {
			System.out.println("SitSrvNo = " + sitsrv.getSitSrvNo());
			System.out.println("SitSrvName = " + sitsrv.getSitSrvName());
			System.out.println("SitSrvCode = " + sitsrv.getSitSrvCode());
			System.out.println("SitNo = " + sitsrv.getSitNo());
			System.out.println("SrvFee = " + sitsrv.getSrvFee());
			System.out.println("SrvInfo = " + sitsrv.getSrvInfo());
			System.out.println("SrvArea = " + sitsrv.getSrvArea());
			System.out.println("AcpPetNum = " + sitsrv.getAcpPetNum());
			System.out.println("AcpPeTyp = " + sitsrv.getAcpPetTyp());
			System.out.println("CareLevel = " + sitsrv.getCareLevel());
			System.out.println("StayLoc = " + sitsrv.getStayLoc());
			System.out.println("OvernightLoc = " + sitsrv.getOvernightLoc());
			System.out.println("SmkFree = " + sitsrv.getSmkFree());
			System.out.println("HasChild = " + sitsrv.getHasChild());
			System.out.println("WalkTime = " + sitsrv.getWalkTime());
			System.out.println("EQPT = " + sitsrv.getEqpt());
			System.out.println("AddBathing = " + sitsrv.getAddBathing());
			System.out.println("AddPickup = " + sitsrv.getAddPickup());
			System.out.println("OutOfSrv = " + sitsrv.getOutOfSrv());
			System.out.println("isDel = " + sitsrv.getIsDel());
			System.out.println("----------------------------------");
		}
		System.out.println("--------------------以上是符合條件的保姆服務----------------------");
	}

}
