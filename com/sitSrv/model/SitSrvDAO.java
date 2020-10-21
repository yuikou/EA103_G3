package com.sitSrv.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.*;
import javax.sql.DataSource;

import com.sitLic.model.SitLicJDBCDAO;
import com.sitLic.model.SitLicVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_sitSrv;

public class SitSrvDAO implements SitSrvDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String ADD_PSTMT = "INSERT INTO sitSrv VALUES ('SS' || lpad(sitSrv_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_PLUS_PSTMT = "INSERT INTO sitSrv (SITSRVNO, SITSRVNAME, SITSRVCODE, SITNO, SRVFEE, OUTOFSRV, ISDEL) VALUES ('SS' || lpad(sitSrv_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?,'0','0')";
	private static final String UPDATE_PSTMT = "UPDATE sitSrv "
			+ "SET sitSrvName=?, sitSrvCode=?, sitNo=?, srvFee=?, srvInfo=?, srvArea=?, acpPetNum=?, acpPetTyp=?, "
			+ "careLevel=?, stayLoc=?, overnightLoc=?, SmkFree=?, hasChild=?, SrvTime=?, eqpt=?, addBathing=?, addPickup=?, "
			+ "outOfSrv=?, isDel=? WHERE sitSrvNo=? ";
	private static final String UPDATE_PSTMT_PLUS_PSTMT = "UPDATE sitSrv SET SRVFEE=? WHERE sitSrvNo=?";
	private static final String UPDATE_STATUS_PSTMT = "UPDATE sitSrv SET outOfSrv=?,isDel=? WHERE sitSrvNo=? ";
	private static final String GET_SIT_SRV = "SELECT * FROM sitSrv WHERE sitSrvNo=? AND isDel=0";
	private static final String GET_SIT_ALL_SRV = "SELECT * FROM sitSrv WHERE sitNo=? AND isDel=0";
//	private static final String CHOOSE_SIT_FROM_SRV = "SELECT memNickname, memPhoto, sitSrvName, srvFee, srvInfo, totalComm, totalcCus, repeatCus "
//			+ "From member_table m "
//			+ "JOIN petSitter ps ON ps.memNo = m.memNo "
//			+ "JOIN sitSrv ss ON ps.sitNo = ss.sitNo "
//			+ "WHERE sitSrvCode=? AND memAddress Like ? AND acpPetTyp=?";

	@Override
	public Boolean add(SitSrvVO sitSrv, Integer addBathingFee, Integer addPickupFee) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			int[] pk = {1};

			pstmt = con.prepareStatement(ADD_PSTMT,pk);
			pstmt.setString(1, sitSrv.getSitSrvName());
			pstmt.setString(2, sitSrv.getSitSrvCode());
			pstmt.setString(3, sitSrv.getSitNo());
			pstmt.setInt(4, sitSrv.getSrvFee());
			pstmt.setString(5, sitSrv.getSrvInfo());
			pstmt.setObject(6, sitSrv.getSrvArea());
			pstmt.setObject(7, sitSrv.getAcpPetNum());
			pstmt.setObject(8, sitSrv.getAcpPetTyp());
			pstmt.setObject(9, sitSrv.getCareLevel());
			pstmt.setObject(10, sitSrv.getStayLoc());
			pstmt.setObject(11, sitSrv.getOvernightLoc());
			pstmt.setObject(12, sitSrv.getSmkFree());
			pstmt.setObject(13, sitSrv.getHasChild());
			pstmt.setObject(14, sitSrv.getSrvTime());
			pstmt.setObject(15, sitSrv.getEqpt());
			pstmt.setObject(16, sitSrv.getAddBathing());
			pstmt.setObject(17, sitSrv.getAddPickup());
			pstmt.setInt(18, 0);
			pstmt.setInt(19, 0);
			pstmt.executeUpdate();
			
			
			String pkNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				pkNo = rs.getString(1);
				System.out.println("自增主鍵值= " + pkNo +"(剛新增成功的服務編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			pstmt2 = con.prepareStatement(ADD_PLUS_PSTMT);
			// 再同時新增加價服務
			if (sitSrv.getAddBathing() != null && sitSrv.getAddBathing() == 1) {
				pstmt2.setString(1, "加價洗澡"+pkNo);
				pstmt2.setString(2, "Bathing");
				pstmt2.setString(3, sitSrv.getSitNo());
				pstmt2.setInt(4, addBathingFee);
				pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			if (sitSrv.getAddBathing() != null && sitSrv.getAddPickup() == 1) {
				pstmt2.setString(1, "加價接送"+pkNo);
				pstmt2.setString(2, "Pickup");
				pstmt2.setString(3, sitSrv.getSitNo());
				pstmt2.setInt(4, addPickupFee);
				pstmt2.executeUpdate();
			}
			con.commit();
			addOK = true;
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try {
					System.err.println("rolled back-由-sitSrv_add");
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RuntimeException("rollback error occured. "
							+ e1.getMessage());
				}
			}
			throw new RuntimeException("新增失敗: " + e.getMessage());
		} finally {
			if (pstmt2 != null) {
				try {
					pstmt2.close();
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
		return addOK;
	}

	@Override
	public Boolean update(SitSrvVO sitSrv, SitSrvVO sitSrv2, SitSrvVO sitSrv3) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
			con = ds.getConnection();
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
			pstmt.setString(14, sitSrv.getSrvTime());
			pstmt.setObject(15, sitSrv.getEqpt());
			pstmt.setInt(16, sitSrv.getAddBathing());
			pstmt.setInt(17, sitSrv.getAddPickup());
			pstmt.setInt(18, 0);
			pstmt.setInt(19, 0);
			pstmt.setString(20, sitSrv.getSitSrvNo());
				
			pstmt.executeUpdate();
			
			pstmt2 = con.prepareStatement(UPDATE_PSTMT_PLUS_PSTMT);
			// 之前【有】加價洗澡，現在還是【有】加價洗澡才會重新update
			if (sitSrv2 != null) {
				pstmt2.setInt(1, sitSrv2.getSrvFee());
				pstmt2.setString(2, sitSrv2.getSitSrvNo());
				pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			// 之前有加價接送，現在還是有加價接送才會重新update
			if (sitSrv3 != null) {
				pstmt2.setInt(1, sitSrv3.getSrvFee());
				pstmt2.setString(2, sitSrv3.getSitSrvNo());
				pstmt2.executeUpdate();
			}
			
			updateOK = true;
			con.commit();
			
			

		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try {
					System.err.println("rolled back-由-sitSrv_update");
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RuntimeException("rollback error occured. "
							+ e1.getMessage());
				}
			}
			throw new RuntimeException("新增失敗: " + e.getMessage());
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

	@Override
	public Boolean updateStatus(String sitSrvNo, Integer outOfSrv, Integer isDel) {
		Boolean updateOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_STATUS_PSTMT);
			pstmt.setString(3, sitSrvNo);
			pstmt.setInt(1, outOfSrv);
			pstmt.setInt(2, isDel);
			if (pstmt.executeUpdate() == 1) {
				updateOK = true;
				con.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
				throw new RuntimeException("A database error occured. "+e.getMessage());
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

	@Override
	public SitSrvVO get_OneSit_OneSrv(String sitSrvNo) {
		SitSrvVO sitSrv = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
				sitSrv.setCareLevel(rs.getObject("careLevel")==null?null:rs.getInt("careLevel"));
				sitSrv.setStayLoc(rs.getObject("stayLoc")==null?null:rs.getInt("stayLoc"));
				sitSrv.setOvernightLoc(rs.getObject("overnightLoc")==null?null:rs.getInt("overnightLoc"));
				sitSrv.setSmkFree(rs.getObject("smkFree")==null?null:rs.getInt("smkFree"));
				sitSrv.setHasChild(rs.getObject("hasChild")==null?null:rs.getInt("hasChild"));
				sitSrv.setSrvTime(rs.getString("srvTime"));
				sitSrv.setEqpt(rs.getObject("eqpt")==null?null:rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGYY...出現SQL問題" + e.getMessage());
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
			con = ds.getConnection();
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
				sitSrv.setCareLevel(rs.getObject("careLevel")==null?null:rs.getInt("careLevel"));
				sitSrv.setStayLoc(rs.getObject("stayLoc")==null?null:rs.getInt("stayLoc"));
				sitSrv.setOvernightLoc(rs.getObject("overnightLoc")==null?null:rs.getInt("overnightLoc"));
				sitSrv.setSmkFree(rs.getObject("smkFree")==null?null:rs.getInt("smkFree"));
				sitSrv.setHasChild(rs.getObject("hasChild")==null?null:rs.getInt("hasChild"));
				sitSrv.setSrvTime(rs.getString("srvTime"));
				sitSrv.setEqpt(rs.getObject("eqpt")==null?null:rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
				list.add(sitSrv);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGYY...出現SQL問題" + e.getMessage());
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
	public List<SitSrvVO2> choose_SitSrv(String sitSrvCode, Object[] acpPetTyp, Map<String, String[]> map) {
		List<SitSrvVO2> list = new ArrayList<SitSrvVO2>();
		SitSrvVO2 sitSrv = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer choose_sit_from_srv = new StringBuffer(
				"SELECT m.memNo, memName, memAddress, totalComm, totalCus, repeatCus, "
				+ "sitSrvNo, sitSrvName, sitSrvCode, ss.sitNo, srvFee, srvInfo, srvArea, acpPetNum, "
				+ "acpPetTyp, careLevel, stayLoc, overnightLoc, smkFree, hasChild, srvTime, eqpt, "
				+ "addBathing, addPickup, outOfSrv, isDel "
				+ "From sitSrv ss "
				+ "JOIN petSitter ps ON ps.sitNo = ss.sitNo "
				+ "JOIN member_table m ON ps.memNo = m.memNo "
				+ "WHERE isDel=0 AND sitsrvcode NOT IN ('Bathing' ,'Pickup') AND outOfSrv=0 AND sitSrvCode=? ");

		try {
			con = ds.getConnection();
			
			// 如果有選typ
			if (acpPetTyp.length>0) {
				choose_sit_from_srv.append("AND acpPetTyp = ANY (");
				for (int i = 0; i < acpPetTyp.length; i++) {
					choose_sit_from_srv.append("?,");
				}
				choose_sit_from_srv.deleteCharAt(choose_sit_from_srv.length()-1);
				choose_sit_from_srv.append(") ");
			}
			
			
			String sql = choose_sit_from_srv + jdbcUtil_CompositeQuery_sitSrv.get_WhereCondition(map);
			
			System.out.println("SitSrvDAO_441.finalSQL = " + sql);
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, sitSrvCode);
			// 如果有選typ
			if (acpPetTyp.length>0) {
				for (int i = 0; i < acpPetTyp.length; i++) {
					pstmt.setObject(i+2, acpPetTyp[i]);
				}
			}
			

//			pstmt.setString(2, memAddress + '%');
			// 看似字串，其實是數字串接的變數

			rs = pstmt.executeQuery();
			while (rs.next()) {
				sitSrv = new SitSrvVO2();
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
				sitSrv.setSrvTime(rs.getString("srvTime"));
				sitSrv.setEqpt(rs.getInt("eqpt"));
				sitSrv.setAddBathing(rs.getInt("addBathing"));
				sitSrv.setAddPickup(rs.getInt("addPickup"));
				sitSrv.setOutOfSrv(rs.getInt("outOfSrv"));
				sitSrv.setIsDel(rs.getInt("isDel"));
				sitSrv.setMemNo(rs.getString("memNO"));
				sitSrv.setMemName(rs.getString("memName"));
				sitSrv.setMemAddress(rs.getString("memAddress"));
				sitSrv.setTotalComm(rs.getDouble("totalComm"));
				sitSrv.setTotalCus(rs.getDouble("totalCus"));
				sitSrv.setRepeatCus(rs.getInt("repeatCus"));
				list.add(sitSrv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("GGYY...出現SQL問題" + e.getMessage());
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
	public Boolean addWithSitLic(SitSrvVO sitSrv, Integer addBathingFee, Integer addPickupFee, SitLicVO sitLic) {
		Boolean addOK = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			int[] pk = {1};

			pstmt = con.prepareStatement(ADD_PSTMT,pk);
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
			pstmt.setObject(14, sitSrv.getSrvTime());
			pstmt.setObject(15, sitSrv.getEqpt());
			pstmt.setObject(16, sitSrv.getAddBathing());
			pstmt.setObject(17, sitSrv.getAddPickup());
			pstmt.setInt(18, 2);
			pstmt.setInt(19, 0);
			pstmt.executeUpdate();
			
			
			String pkNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				pkNo = rs.getString(1);
				System.out.println("自增主鍵值= " + pkNo +"(剛新增成功的服務編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			pstmt2 = con.prepareStatement(ADD_PLUS_PSTMT);
			// 再同時新增加價服務
			if (sitSrv.getAddBathing() == 1) {
				pstmt2.setString(1, "加價洗澡"+pkNo);
				pstmt2.setString(2, "Bathing");
				pstmt2.setString(3, sitSrv.getSitNo());
				pstmt2.setInt(4, addBathingFee);
				pstmt2.executeUpdate();
				pstmt2.clearParameters();
			}
			if (sitSrv.getAddPickup() == 1) {
				pstmt2.setString(1, "加價接送"+pkNo);
				pstmt2.setString(2, "Pickup");
				pstmt2.setString(3, sitSrv.getSitNo());
				pstmt2.setInt(4, addPickupFee);
				pstmt2.executeUpdate();
			}
			
			// 再同時新增證書
			SitLicJDBCDAO sldao = new SitLicJDBCDAO();
			sldao.addFromSitSrv(sitLic, con);
			
			con.commit();
			addOK = true;
			con.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
				try {
					System.err.println("rolled back-由-sitSrv_addWithSitLic");
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
					throw new RuntimeException("rollback error occured. "
							+ e1.getMessage());
				}
			}
			throw new RuntimeException("新增失敗: " + e.getMessage());
		} finally {
			if (pstmt2 != null) {
				try {
					pstmt2.close();
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
		return addOK;
	}

	
}
