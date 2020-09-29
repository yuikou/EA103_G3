package com.sitLic.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

public class SitLicDAO implements SitLicDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String ADD_PSTMT = "INSERT INTO sitLic VALUES ('SL' || lpad(licNo_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE_PSTMT = "UPDATE sitLic SET LICNAME=?, LICPIC=?, LICEXP=?, LICSTATUS=? WHERE LICNO=?";
	private static final String GET_ALL_LIC = "SELECT * FROM sitLic WHERE SITNO=? ORDER BY LICNO";
	private static final String GET_ONE_LIC = "SELECT * FROM sitLic WHERE LICNO=?";
	private static final String GET_UNVERIFIED_LIC = "SELECT * FROM sitLic WHERE LICSTATUS=?";
	
	@Override
	public void add(SitLicVO sitLic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt =con.prepareStatement(ADD_PSTMT);
			pstmt.setString(1, sitLic.getSitNo());
			pstmt.setString(2, sitLic.getLicName());
			pstmt.setBytes(3, sitLic.getLicPic());
			pstmt.setDate(4, sitLic.getLicEXP());
			pstmt.setInt(5, sitLic.getLicStatus());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//console印出例外原因位置，個人比較好debug
			e.printStackTrace();
			throw new RuntimeException("資料庫問題發生!!! " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					//最後的例外用Exception捕捉，避免發生Driver本身的錯誤
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(SitLicVO sitLic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSTMT);
			pstmt.setString(1, sitLic.getLicName());
			pstmt.setBytes(2, sitLic.getLicPic());
			pstmt.setDate(3, sitLic.getLicEXP());
			pstmt.setInt(4, sitLic.getLicStatus());
			pstmt.setString(5, sitLic.getLicNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("資料庫問題發生!!! " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public SitLicVO getOneLicByPK(String licNo) {
		SitLicVO sitLic = null;
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LIC);
			pstmt.setString(1, licNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sitLic = new SitLicVO();
				sitLic.setLicNo(rs.getString("LICNO"));
				sitLic.setSitNo(rs.getString("SITNO"));
				sitLic.setLicName(rs.getString("LICNAME"));
				sitLic.setLicPic(rs.getBytes("LICPIC"));
				sitLic.setLicEXP(rs.getDate("LICEXP"));
				sitLic.setLicStatus(rs.getInt("LICSTATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("資料庫問題發生!!! "+ e.getMessage());
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
		return sitLic;
	}

	@Override
	public List<SitLicVO> getSitAllLic(String sitNo) {
		List<SitLicVO> list = new ArrayList<SitLicVO>();
		SitLicVO sitLic = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LIC);
			pstmt.setString(1, sitNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				sitLic = new SitLicVO();
				sitLic.setLicNo(rs.getString("LICNO"));
				sitLic.setSitNo(rs.getString("SITNO"));
				sitLic.setLicName(rs.getString("LICNAME"));
				sitLic.setLicPic(rs.getBytes("LICPIC"));
				sitLic.setLicEXP(rs.getDate("LICEXP"));
				sitLic.setLicStatus(rs.getInt("LICSTATUS"));
				list.add(sitLic);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("資料庫問題發生!!! "+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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
	public List<SitLicVO> getAllForEmp(Integer licStatus) {
		List<SitLicVO> list = new ArrayList<SitLicVO>();
		SitLicVO sitLic = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_UNVERIFIED_LIC);
			pstmt.setInt(1, licStatus);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitLic = new SitLicVO();
				sitLic.setLicNo(rs.getString("LICNO"));
				sitLic.setSitNo(rs.getString("SITNO"));
				sitLic.setLicName(rs.getString("LICNAME"));
				sitLic.setLicPic(rs.getBytes("LICPIC"));
				sitLic.setLicEXP(rs.getDate("LICEXP"));
				sitLic.setLicStatus(rs.getInt("LICSTATUS"));
				list.add(sitLic);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查詢失敗： " + e.getMessage());
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
}
