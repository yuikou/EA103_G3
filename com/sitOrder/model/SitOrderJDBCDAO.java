package com.sitOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.sitODetail.model.SitODetailJDBCDAO;
import com.sitODetail.model.SitODetailVO;

public class SitOrderJDBCDAO implements SitOrderDAO_interface{
	
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String userid = "EA103G3";
	private static final String pwd = "123456";
	
	private static final String INSERT_PSTMT = "INSERT INTO sitOrder(sitOrderNo, memNo, sitNo, sitSDate, sitEDate, sitOTime, "
			+ "totalPrice, orderStatus, refund, coupon, commStar, sitComm, pickupFrom, pickupTo) "
			+ "VALUES('SO' || lpad(sitOrder_SEQ.NEXTVAL, 3, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String UPDATE_PSTMT = "UPDATE sitOrder SET memNo=?, sitNo=?, sitSDate=?, sitEDate=?, sitOTime=?,"
			+ "totalPrice=?,orderStatus=?,refund=?,coupon=?,commStar=?,sitComm=?,pickupFrom=?,pickupTo=? WHERE sitOrderNo=?";
	private static final String UPDATE_STATUS_PSTMT = "UPDATE sitOrder SET orderStatus=? WHERE sitOrderNo=?";
	private static final String UPDATE_SITCOMM_PSTMT = "UPDATE sitOrder SET orderStatus=? ,commStar=?, sitComm=? WHERE sitOrderNo=?";
	
	private static final String GET_BY_PK_PSTMT = "SELECT sitOrderNo, memNo, sitNo, sitSDate, sitEDate, sitOTime, "
			+ "totalPrice, orderStatus, refund, coupon, commStar, sitComm, pickupFrom, pickupTo FROM sitOrder WHERE sitOrderNo=?";
	private static final String GET_BY_MEMNO_PSTMT = "SELECT sitOrderNo, memNo, sitNo, sitSDate, sitEDate, sitOTime, "
			+ "totalPrice, orderStatus, refund, coupon, commStar, sitComm, pickupFrom, pickupTo FROM sitOrder WHERE memNo=?";
	private static final String GET_BY_SITNO_PSTMT = "SELECT sitOrderNo, memNo, sitNo, sitSDate, sitEDate, sitOTime, "
			+ "totalPrice, orderStatus, refund, coupon, commStar, sitComm, pickupFrom, pickupTo FROM sitOrder WHERE sitNo=?";
	
	private static final String GETALL_PSTMT = "SELECT * FROM sitOrder";
	
	private static final String COUNT_AVG_STAR = "SELECT SUM(commStar)/COUNT(commStar) FROM sitOrder WHERE sitNo = ? AND commStar > 0";
	
	@Override
	public void insert(SitOrderVO sitOrderVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,pwd);
			pstmt = con.prepareStatement(INSERT_PSTMT);
			
			pstmt.setString(1,sitOrderVO.getMemNo());
			pstmt.setString(2,sitOrderVO.getSitNo());
			pstmt.setDate(3,sitOrderVO.getSitSDate());
			pstmt.setDate(4,sitOrderVO.getSitEDate());
			pstmt.setString(5,sitOrderVO.getSitOTime());
			pstmt.setInt(6,sitOrderVO.getTotalPrice());
			pstmt.setInt(7,sitOrderVO.getOrderStatus());
			pstmt.setInt(8,sitOrderVO.getRefund());
			pstmt.setInt(9,sitOrderVO.getCoupon());
			pstmt.setInt(10,sitOrderVO.getCommStar());
			pstmt.setString(11,sitOrderVO.getSitComm());
			pstmt.setString(12,sitOrderVO.getPickupFrom());
			pstmt.setString(13,sitOrderVO.getPickupTo());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
 		} catch (SQLException se) {
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

	@Override
	public void update(SitOrderVO sitOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(UPDATE_PSTMT);
			
			pstmt.setString(1,sitOrderVO.getMemNo());
			pstmt.setString(2,sitOrderVO.getSitNo());
			pstmt.setDate(3,sitOrderVO.getSitSDate());
			pstmt.setDate(4,sitOrderVO.getSitEDate());
			pstmt.setString(5,sitOrderVO.getSitOTime());
			pstmt.setInt(6,sitOrderVO.getTotalPrice());
			pstmt.setInt(7,sitOrderVO.getOrderStatus());
			pstmt.setInt(8,sitOrderVO.getRefund());
			pstmt.setInt(9,sitOrderVO.getCoupon());
			pstmt.setInt(10,sitOrderVO.getCommStar());
			pstmt.setString(11,sitOrderVO.getSitComm());
			pstmt.setString(12,sitOrderVO.getPickupFrom());
			pstmt.setString(13,sitOrderVO.getPickupTo());
			pstmt.setString(14, sitOrderVO.getSitOrderNo());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
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
	
	@Override
	public void update_orderStatus(SitOrderVO sitOrderVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(UPDATE_STATUS_PSTMT);
			
			
			pstmt.setInt(1,sitOrderVO.getOrderStatus());
			pstmt.setString(2, sitOrderVO.getSitOrderNo());

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
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
	
	@Override
	public void update_sitCommStar(SitOrderVO sitOrderVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(UPDATE_SITCOMM_PSTMT);
			
			
			pstmt.setInt(1,sitOrderVO.getOrderStatus());
			pstmt.setInt(2,sitOrderVO.getCommStar());
			pstmt.setString(3,sitOrderVO.getSitComm());
			pstmt.setString(4, sitOrderVO.getSitOrderNo());

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
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

	@Override
	public SitOrderVO getByPK(String sitOrderNo) {
		
		SitOrderVO sitOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GET_BY_PK_PSTMT);

			pstmt.setString(1, sitOrderNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitOrderVO = new SitOrderVO();
				sitOrderVO.setSitOrderNo(rs.getString("sitOrderNo"));
				sitOrderVO.setMemNo(rs.getString("memNo"));
				sitOrderVO.setSitNo(rs.getString("sitNo"));
				sitOrderVO.setSitSDate(rs.getDate("sitSDate"));
				sitOrderVO.setSitEDate(rs.getDate("sitEDate"));
				sitOrderVO.setSitOTime(rs.getString("sitOTime"));
				sitOrderVO.setTotalPrice(rs.getInt("totalPrice"));
				sitOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				sitOrderVO.setRefund(rs.getInt("refund"));
				sitOrderVO.setCoupon(rs.getInt("coupon"));
				sitOrderVO.setCommStar(rs.getInt("commStar"));
				sitOrderVO.setSitComm(rs.getString("sitComm"));
				sitOrderVO.setPickupFrom(rs.getString("pickupFrom"));
				sitOrderVO.setPickupTo(rs.getString("pickupTo"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return sitOrderVO;
	}

	public Set<SitOrderVO> getByFK_memNo(String memNo){
		Set<SitOrderVO> set = new LinkedHashSet<SitOrderVO>();
		SitOrderVO sitOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GET_BY_MEMNO_PSTMT);		
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitOrderVO = new SitOrderVO();
				sitOrderVO.setSitOrderNo(rs.getString("sitOrderNo"));
				sitOrderVO.setMemNo(rs.getString("memNo"));
				sitOrderVO.setSitNo(rs.getString("sitNo"));
				sitOrderVO.setSitSDate(rs.getDate("sitSDate"));
				sitOrderVO.setSitEDate(rs.getDate("sitEDate"));
				sitOrderVO.setSitOTime(rs.getString("sitOTime"));
				sitOrderVO.setTotalPrice(rs.getInt("totalPrice"));
				sitOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				sitOrderVO.setRefund(rs.getInt("refund"));
				sitOrderVO.setCoupon(rs.getInt("coupon"));
				sitOrderVO.setCommStar(rs.getInt("commStar"));
				sitOrderVO.setSitComm(rs.getString("sitComm"));
				sitOrderVO.setPickupFrom(rs.getString("pickupFrom"));
				sitOrderVO.setPickupTo(rs.getString("pickupTo"));
				set.add(sitOrderVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}
	
	@Override
	public Set<SitOrderVO> getByFK_sitNo(String sitNo) {
		Set<SitOrderVO> set = new LinkedHashSet<SitOrderVO>();
		SitOrderVO sitOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GET_BY_SITNO_PSTMT);		
			pstmt.setString(1, sitNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitOrderVO = new SitOrderVO();
				sitOrderVO.setSitOrderNo(rs.getString("sitOrderNo"));
				sitOrderVO.setMemNo(rs.getString("memNo"));
				sitOrderVO.setSitNo(rs.getString("sitNo"));
				sitOrderVO.setSitSDate(rs.getDate("sitSDate"));
				sitOrderVO.setSitEDate(rs.getDate("sitEDate"));
				sitOrderVO.setSitOTime(rs.getString("sitOTime"));
				sitOrderVO.setTotalPrice(rs.getInt("totalPrice"));
				sitOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				sitOrderVO.setRefund(rs.getInt("refund"));
				sitOrderVO.setCoupon(rs.getInt("coupon"));
				sitOrderVO.setCommStar(rs.getInt("commStar"));
				sitOrderVO.setSitComm(rs.getString("sitComm"));
				sitOrderVO.setPickupFrom(rs.getString("pickupFrom"));
				sitOrderVO.setPickupTo(rs.getString("pickupTo"));
				set.add(sitOrderVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return set;
	}

	@Override
	public List<SitOrderVO> getAll() {


		List<SitOrderVO> list = new ArrayList<SitOrderVO>();
		SitOrderVO sitOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(GETALL_PSTMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sitOrderVO = new SitOrderVO();
				sitOrderVO.setSitOrderNo(rs.getString("sitOrderNo"));
				sitOrderVO.setMemNo(rs.getString("memNo"));
				sitOrderVO.setSitNo(rs.getString("sitNo"));
				sitOrderVO.setSitSDate(rs.getDate("sitSDate"));
				sitOrderVO.setSitEDate(rs.getDate("sitEDate"));
				sitOrderVO.setSitOTime(rs.getString("sitOTime"));
				sitOrderVO.setTotalPrice(rs.getInt("totalPrice"));
				sitOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				sitOrderVO.setRefund(rs.getInt("refund"));
				sitOrderVO.setCoupon(rs.getInt("coupon"));
				sitOrderVO.setCommStar(rs.getInt("commStar"));
				sitOrderVO.setSitComm(rs.getString("sitComm"));
				sitOrderVO.setPickupFrom(rs.getString("pickupFrom"));
				sitOrderVO.setPickupTo(rs.getString("pickupTo"));
				list.add(sitOrderVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void insertWithODetail(SitOrderVO sitOrderVO, List<SitODetailVO> list) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			
			// 1.設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增訂單
			String cols[] = {"sitOrderNo"};
			pstmt = con.prepareStatement(INSERT_PSTMT , cols);
			pstmt.setString(1,sitOrderVO.getMemNo());
			pstmt.setString(2,sitOrderVO.getSitNo());
			pstmt.setDate(3,sitOrderVO.getSitSDate());
			pstmt.setDate(4,sitOrderVO.getSitEDate());
			pstmt.setString(5,sitOrderVO.getSitOTime());
			pstmt.setInt(6,sitOrderVO.getTotalPrice());
			pstmt.setInt(7,sitOrderVO.getOrderStatus());
			pstmt.setInt(8,sitOrderVO.getRefund());
			pstmt.setInt(9,sitOrderVO.getCoupon());
			pstmt.setInt(10,sitOrderVO.getCommStar());
			pstmt.setString(11,sitOrderVO.getSitComm());
			pstmt.setString(12,sitOrderVO.getPickupFrom());
			pstmt.setString(13,sitOrderVO.getPickupTo());
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_sitOrderNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_sitOrderNo = rs.getString(1);
				System.out.println("自增主鍵值= " + next_sitOrderNo +"(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增員工
			SitODetailJDBCDAO dao = new SitODetailJDBCDAO();
			for (SitODetailVO aSitODetail : list) {
				aSitODetail.setSitOrderNo(next_sitOrderNo) ;
				dao.insertfromOrder(aSitODetail,con);
			}

			// 2.設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-sitOrder");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
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
	public Double countAvgStar(String sitNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Double starred = 0.0;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, pwd);
			pstmt = con.prepareStatement(COUNT_AVG_STAR);
			
			pstmt.setString(1, sitNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				starred = rs.getDouble(1);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return starred;
	}

	
	
	
	public static void main(String[] args) {

		SitOrderJDBCDAO dao = new SitOrderJDBCDAO();
//
//		// 新增
//		SitOrderVO sitOrderVO = new SitOrderVO();
//		sitOrderVO.setMemNo("M002");
//		sitOrderVO.setSitNo("S002");
//		sitOrderVO.setSitSDate(java.sql.Date.valueOf("2016-01-01"));
//		sitOrderVO.setSitEDate(java.sql.Date.valueOf("2016-01-01"));
//		sitOrderVO.setTotalPrice(200);
//		sitOrderVO.setOrderStatus(3);
//		sitOrderVO.setRefund(200);
//		sitOrderVO.setCoupon(0);
//		sitOrderVO.setCommStar(4);
//		sitOrderVO.setSitComm("good");
//		sitOrderVO.setPickupFrom("taipei");
//		sitOrderVO.setPickupTo("zhonli");
//		dao.insert(sitOrderVO);
//		
//
//		// 修改
//		SitOrderVO sitOrderVO = new SitOrderVO();
//		sitOrderVO.setMemNo("M002");
//		sitOrderVO.setSitNo("S002");
//		sitOrderVO.setSitSDate(java.sql.Date.valueOf("2020-01-01"));
//		sitOrderVO.setSitEDate(java.sql.Date.valueOf("2020-01-11"));
//		sitOrderVO.setSitOTime(null);
//		sitOrderVO.setTotalPrice(200);
//		sitOrderVO.setOrderStatus(3);
//		sitOrderVO.setRefund(200);
//		sitOrderVO.setCoupon(0);
//		sitOrderVO.setCommStar(4);
//		sitOrderVO.setSitComm("excellent petsitter");
//		sitOrderVO.setPickupFrom("taipei");
//		sitOrderVO.setPickupTo("zhonli");
//		sitOrderVO.setSitOrderNo("SO003");
//		dao.update(sitOrderVO);
//		
//		//修改訂單狀態
//		SitOrderVO sitOrderVO = new SitOrderVO();
//		sitOrderVO.setOrderStatus(1);
//		sitOrderVO.setSitOrderNo("SO008");
//		dao.update_orderStatus(sitOrderVO);
//		
//		SitOrderVO sitOrderVO = new SitOrderVO();
//		sitOrderVO.setOrderStatus(2);
//		sitOrderVO.setCommStar(4);
//		sitOrderVO.setSitComm("環境很乾淨、舒適，有其他友善的狗狗可以交朋友，朱小毛離開前剪了很漂亮又可愛的造型，三天兩夜被照顧的很好呢！");
//		sitOrderVO.setSitOrderNo("SO001");
//		dao.update_sitCommStar(sitOrderVO);
//
//		// 查詢 getByPK
//		SitOrderVO sitOrderVO = dao.getByPK("SO002");
//		System.out.println(sitOrderVO.getSitOrderNo() + ",");
//		System.out.println(sitOrderVO.getMemNo() + ",");
//		System.out.println(sitOrderVO.getSitNo() + ",");
//		System.out.println(sitOrderVO.getSitSDate() + ",");
//		System.out.println(sitOrderVO.getSitEDate() + ",");
//		System.out.println(sitOrderVO.getTotalPrice() + ",");
//		System.out.println(sitOrderVO.getOrderStatus() + ",");
//		System.out.println(sitOrderVO.getRefund() + ",");
//		System.out.println(sitOrderVO.getCoupon() + ",");
//		System.out.println(sitOrderVO.getCommStar() + ",");
//		System.out.println(sitOrderVO.getSitComm() + ",");
//		System.out.println(sitOrderVO.getPickupFrom() + ",");
//		System.out.println(sitOrderVO.getPickupTo());
//		
//		// 查詢 getByFK_memNo
//		Set<SitOrderVO> set = dao.getByFK_memNo("M002");
//		for(SitOrderVO sitOrderVO : set) {
//		System.out.println(sitOrderVO.getSitOrderNo() + ",");
//		System.out.println(sitOrderVO.getMemNo() + ",");
//		System.out.println(sitOrderVO.getSitNo() + ",");
//		System.out.println(sitOrderVO.getSitSDate() + ",");
//		System.out.println(sitOrderVO.getSitEDate() + ",");
//		System.out.println(sitOrderVO.getTotalPrice() + ",");
//		System.out.println(sitOrderVO.getOrderStatus() + ",");
//		System.out.println(sitOrderVO.getRefund() + ",");
//		System.out.println(sitOrderVO.getCoupon() + ",");
//		System.out.println(sitOrderVO.getCommStar() + ",");
//		System.out.println(sitOrderVO.getSitComm() + ",");
//		System.out.println(sitOrderVO.getPickupFrom() + ",");
//		System.out.println(sitOrderVO.getPickupTo());
//		System.out.println();
//		}
//		
//		// 查詢 getByFK_sitNo
//		Set<SitOrderVO> set = dao.getByFK_sitNo("S011");
//		for(SitOrderVO sitOrderVO : set) {
//		System.out.println(sitOrderVO.getSitOrderNo() + ",");
//		System.out.println(sitOrderVO.getMemNo() + ",");
//		System.out.println(sitOrderVO.getSitNo() + ",");
//		System.out.println(sitOrderVO.getSitSDate() + ",");
//		System.out.println(sitOrderVO.getSitEDate() + ",");
//		System.out.println(sitOrderVO.getTotalPrice() + ",");
//		System.out.println(sitOrderVO.getOrderStatus() + ",");
//		System.out.println(sitOrderVO.getRefund() + ",");
//		System.out.println(sitOrderVO.getCoupon() + ",");
//		System.out.println(sitOrderVO.getCommStar() + ",");
//		System.out.println(sitOrderVO.getSitComm() + ",");
//		System.out.println(sitOrderVO.getPickupFrom() + ",");
//		System.out.println(sitOrderVO.getPickupTo());
//		System.out.println();
//		}
//		
//		
//		// 查詢
//		List<SitOrderVO> list = dao.getAll();
//		for (SitOrderVO sitOrder : list) {
//			System.out.println(sitOrder.getSitOrderNo() + ",");
//			System.out.println(sitOrder.getMemNo() + ",");
//			System.out.println(sitOrder.getSitNo() + ",");
//			System.out.println(sitOrder.getSitSDate() + ",");
//			System.out.println(sitOrder.getSitEDate() + ",");
//			System.out.println(sitOrder.getTotalPrice() + ",");
//			System.out.println(sitOrder.getOrderStatus() + ",");
//			System.out.println(sitOrder.getRefund() + ",");
//			System.out.println(sitOrder.getCoupon() + ",");
//			System.out.println(sitOrder.getCommStar() + ",");
//			System.out.println(sitOrder.getSitComm() + ",");
//			System.out.println(sitOrder.getPickupFrom() + ",");
//			System.out.println(sitOrder.getPickupTo());
//			System.out.println();
//		}
//		
//		SitOrderVO sitOrderVO = new SitOrderVO();
//		sitOrderVO.setMemNo("M003");
//		sitOrderVO.setSitNo("S002");
//		sitOrderVO.setSitSDate(java.sql.Date.valueOf("2016-01-01"));
//		sitOrderVO.setSitEDate(java.sql.Date.valueOf("2016-01-01"));
//		sitOrderVO.setTotalPrice(200);
//		sitOrderVO.setOrderStatus(3);
//		sitOrderVO.setRefund(200);
//		sitOrderVO.setCoupon(0);
//		sitOrderVO.setCommStar(4);
//		sitOrderVO.setSitComm("good");
//		sitOrderVO.setPickupFrom("Chie Ding");
//		sitOrderVO.setPickupTo("Jia Li");
//		
//		List<SitODetailVO> list = new ArrayList<SitODetailVO>();
//		SitODetailVO sitODetailVO = new SitODetailVO();
//		sitODetailVO.setSitSrvNo("SS002");
//		sitODetailVO.setPetNo("P00012");
//		sitODetailVO.setSitOpPrice(250);
//		sitODetailVO.setSitSrvTimes(1);
//		sitODetailVO.setSitSrvUnit("天");
//		
//		list.add(sitODetailVO);
//		
//		dao.insertWithODetail(sitOrderVO, list);
		
		
//		String sitNo = "S002";
//		System.out.println(dao.countAvgStar(sitNo));
		
	}



}
