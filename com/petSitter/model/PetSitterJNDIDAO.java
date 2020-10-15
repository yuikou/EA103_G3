package com.petSitter.model;

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

public class PetSitterJNDIDAO implements PetSitterDAO_interface {
	
	private static DataSource ds = null;
	static {
		Context ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_PSTMT = "INSERT INTO petSitter(sitNo, memNo, sitInfo, "
			+ "srvSTime, srvETime, bankCode, bankAcc, sitAccStatus, totalComm, totalCus, repeatCus) VALUES ("
			+ "'S' || lpad(sitRep_SEQ.NEXTVAL, 3, '0'),?,?,?,?,?,?,0,0,0,0)";
	private static final String UPDATE_PSTMT = "UPDATE petSitter SET memNo=?, sitInfo=?, "
			+ "srvSTime=? , srvETime=?, bankCode=?, bankAcc=?, sitAccStatus=?, totalComm=?, totalCus=?, repeatCus=? WHERE sitNo=?";
	private static final String GETbyPK_PSTMT = "SELECT sitNo, memNo, sitInfo, bankCode, srvSTime, srvETime,"
			+ "bankAcc, sitAccStatus, totalComm, totalCus, repeatCus FROM petSitter WHERE sitNo=?";
	private static final String GETbyFK_PSTMT = "SELECT sitNo, memNo, sitInfo, bankCode, srvSTime, srvETime,"
			+ "bankAcc, sitAccStatus, totalComm, totalCus, repeatCus FROM petSitter WHERE memNo=?";
	private static final String GETALL_PSTMT = "SELECT * FROM petSitter";

	@Override
	public void insert(PetSitterVO petSitterVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_PSTMT);

			pstmt.setString(1, petSitterVO.getMemNo());
			pstmt.setString(2, petSitterVO.getSitInfo());
			pstmt.setString(3, petSitterVO.getSrvSTime());
			pstmt.setString(4, petSitterVO.getSrvETime());
			pstmt.setString(5, petSitterVO.getBankCode());
			pstmt.setString(6, petSitterVO.getBankAcc());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(PetSitterVO petSitterVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);

			pstmt.setString(1, petSitterVO.getMemNo());
			pstmt.setString(2, petSitterVO.getSitInfo());
			pstmt.setString(3, petSitterVO.getSrvSTime());
			pstmt.setString(4, petSitterVO.getSrvETime());
			pstmt.setString(5, petSitterVO.getBankCode());
			pstmt.setString(6, petSitterVO.getBankAcc());
			pstmt.setInt(7, petSitterVO.getSitAccStatus());
			pstmt.setInt(8, petSitterVO.getTotalComm());
			pstmt.setInt(9, petSitterVO.getTotalCus());
			pstmt.setInt(10, petSitterVO.getRepeatCus());
			pstmt.setString(11, petSitterVO.getSitNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public PetSitterVO getByPK(String sitNo) {
		PetSitterVO petSitterVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETbyPK_PSTMT);

			pstmt.setString(1, sitNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				petSitterVO = new PetSitterVO();
				petSitterVO.setSitNo(rs.getString("sitNo"));
				petSitterVO.setMemNo(rs.getString("memNo"));
				petSitterVO.setSitInfo(rs.getString("sitInfo"));
				petSitterVO.setSrvSTime(rs.getString("srvSTime"));
				petSitterVO.setSrvETime(rs.getString("srvETime"));
				petSitterVO.setBankCode(rs.getString("bankCode"));
				petSitterVO.setBankAcc(rs.getString("bankAcc"));
				petSitterVO.setSitAccStatus(rs.getInt("sitAccStatus"));
				petSitterVO.setTotalComm(rs.getInt("totalComm"));
				petSitterVO.setTotalCus(rs.getInt("totalCus"));
				petSitterVO.setRepeatCus(rs.getInt("repeatCus"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return petSitterVO;
	}
	
	@Override
	public PetSitterVO getByFK(String memNo) {
		
		PetSitterVO petSitterVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETbyFK_PSTMT);

			pstmt.setString(1, memNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				petSitterVO = new PetSitterVO();
				petSitterVO.setSitNo(rs.getString("sitNo"));
				petSitterVO.setMemNo(rs.getString("memNo"));
				petSitterVO.setSitInfo(rs.getString("sitInfo"));
				petSitterVO.setSrvSTime(rs.getString("srvSTime"));
				petSitterVO.setSrvETime(rs.getString("srvETime"));
				petSitterVO.setBankCode(rs.getString("bankCode"));
				petSitterVO.setBankAcc(rs.getString("bankAcc"));
				petSitterVO.setSitAccStatus(rs.getInt("sitAccStatus"));
				petSitterVO.setTotalComm(rs.getInt("totalComm"));
				petSitterVO.setTotalCus(rs.getInt("totalCus"));
				petSitterVO.setRepeatCus(rs.getInt("repeatCus"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		
		return petSitterVO;
	}

	@Override
	public List<PetSitterVO> getAll() {

		List<PetSitterVO> list = new ArrayList<PetSitterVO>();
		PetSitterVO petSitterVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				petSitterVO = new PetSitterVO();
				petSitterVO.setSitNo(rs.getString("sitNo"));
				petSitterVO.setMemNo(rs.getString("memNo"));
				petSitterVO.setSitInfo(rs.getString("sitInfo"));
				petSitterVO.setSrvSTime(rs.getString("srvSTime"));
				petSitterVO.setSrvETime(rs.getString("srvETime"));
				petSitterVO.setBankCode(rs.getString("bankCode"));
				petSitterVO.setBankAcc(rs.getString("bankAcc"));
				petSitterVO.setSitAccStatus(rs.getInt("sitAccStatus"));
				petSitterVO.setTotalComm(rs.getInt("totalComm"));
				petSitterVO.setTotalCus(rs.getInt("totalCus"));
				petSitterVO.setRepeatCus(rs.getInt("repeatCus"));
				list.add(petSitterVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
}
