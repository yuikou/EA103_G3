package com.salonOrder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.salonOrderDetail.model.SalonOrderDetailJDBCDAO;
import com.salonOrderDetail.model.SalonOrderDetailVO;

public class SalonOrderJDBCDAO implements SalonOrderDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G3";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO SALONORDER(salorderno,memno,petno,salno,salorderdate,saltp,orderstatus) VALUES ('BO' || LPAD(SALORDER_SEQ.NEXTVAL,3,'0'),?,?,?,CURRENT_TIMESTAMP,?,?)";
	private static final String DELETE = 
			"DELETE FROM SALONORDER WHERE salorderno = ?";
	private static final String UPDATE = 
			"UPDATE SALONORDER SET memno=?, petno=?, salno=? , salorderdate= current_timestamp, saltp=?, orderstatus=? WHERE salorderno=?";	
	private static final String GET_ALL_BY_SALNO = 
			"SELECT * FROM SALONORDER  WHERE SALNO = ? ORDER BY salorderdate";
	private static final String GET_ALL_BY_MEMNO = 
			"SELECT * FROM SALONORDER WHERE MEMNO = ? ORDER BY salorderdate";
	private static final String UPDATE_ORDERSTATUS_STMT = 
			"UPDATE SALONORDER SET ORDERSTATUS = ? WHERE  SALORDERNO= ?";
	private static final String SCOREANDCOMMENT =
			"UPDATE SALONORDER SET ORDERSTATUS = ?, SALSTAR = ?, SALCOMMENT = ? WHERE SALORDERNO = ?";
//�s�W�q��P�ɷs�W�q�����	
	@Override
	public void insertWithOrderDetail(SalonOrderVO salonOrderVO, List<SalonOrderDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			// ���s�W�q��
			String cols[] = { "salorderno" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, salonOrderVO.getMemNo());
			pstmt.setString(2, salonOrderVO.getPetNo());
			pstmt.setString(3, salonOrderVO.getSalNo());
			pstmt.setInt(4, salonOrderVO.getSalTp());
			pstmt.setInt(5, salonOrderVO.getOrderStatus());
			pstmt.executeUpdate();
			// �����������ۼW�D���
			String salOrderNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				salOrderNo = rs.getString(1);
				System.out.println("�ۼW�D���= " + salOrderNo + "(��s�W���\���q��s��)");
			} else {
				System.out.println("�����o�ۼW�D���");
			}
			rs.close();
			// �P�ɷs�W�q�����
			SalonOrderDetailJDBCDAO dao = new SalonOrderDetailJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for (SalonOrderDetailVO salOD : list) {
				salOD.setSalOrderNo(new String(salOrderNo));
				dao.insertSalonOD(salOD, con);
			}
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);

			System.out.println("list.size()-B=" + list.size());
			System.out.println("�s�W�q��s��" + salOrderNo + "��,�@������" + list.size() + "���P�ɳQ�s�W");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-Order");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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

//�ק�q��	
	@Override
	public void update(SalonOrderVO salonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, salonOrderVO.getMemNo());
			pstmt.setString(2, salonOrderVO.getPetNo());
			pstmt.setString(3, salonOrderVO.getSalNo());
			
			pstmt.setInt(4, salonOrderVO.getSalTp());
			pstmt.setInt(5, salonOrderVO.getOrderStatus());
			pstmt.setString(6, salonOrderVO.getSalOrderNo());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

//�q��Ʈw�R���q��	
	@Override
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salOrderNo);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

//���o��@���e�����q��	
	@Override
	public List<SalonOrderVO> getAllBySalNo(String salNo) {
		List<SalonOrderVO> list = new ArrayList<SalonOrderVO>();
		SalonOrderVO salonOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_SALNO);

			pstmt.setString(1, salNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				salonOrderVO = new SalonOrderVO();
				salonOrderVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonOrderVO.setMemNo(rs.getString("memNo"));
				salonOrderVO.setPetNo(rs.getString("petNo"));
				salonOrderVO.setSalNo(rs.getString("salNo"));
				salonOrderVO.setSalOrderDate(rs.getTimestamp("salOrderDate"));
				salonOrderVO.setSalTp(rs.getInt("salTp"));
				salonOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				salonOrderVO.setRefund(rs.getInt("refund"));
				salonOrderVO.setCoupon(rs.getInt("coupon"));
				salonOrderVO.setSalOrderCT(rs.getTimestamp("salOrderCT"));
				salonOrderVO.setSalStar(rs.getInt("salStar"));
				salonOrderVO.setSalComment(rs.getString("salComment"));

				list.add(salonOrderVO);
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

//���o��@�|�����q��	
	@Override
	public List<SalonOrderVO> getAllByMemNo(String memNo) {
		List<SalonOrderVO> list = new ArrayList<SalonOrderVO>();
		SalonOrderVO salonOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_MEMNO);

			pstmt.setString(1, memNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				salonOrderVO = new SalonOrderVO();
				salonOrderVO.setSalOrderNo(rs.getString("salOrderNo"));
				salonOrderVO.setMemNo(rs.getString("memNo"));
				salonOrderVO.setPetNo(rs.getString("petNo"));
				salonOrderVO.setSalNo(rs.getString("salNo"));
				salonOrderVO.setSalOrderDate(rs.getTimestamp("salOrderDate"));
				salonOrderVO.setSalTp(rs.getInt("salTp"));
				salonOrderVO.setOrderStatus(rs.getInt("orderStatus"));
				salonOrderVO.setRefund(rs.getInt("refund"));
				salonOrderVO.setCoupon(rs.getInt("coupon"));
				salonOrderVO.setSalOrderCT(rs.getTimestamp("salOrderCT"));
				salonOrderVO.setSalStar(rs.getInt("salStar"));
				salonOrderVO.setSalComment(rs.getString("salComment"));

				list.add(salonOrderVO);
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

//�ܧ�q�檬�A	
	@Override
	public void changeOrderStatus(String salOrderNo, Integer orderStatus) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_ORDERSTATUS_STMT);

			pstmt.setInt(1, orderStatus);
			pstmt.setString(2, salOrderNo);

			pstmt.executeUpdate();
			
			con.commit();

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
//����	
	@Override
	public void scoreAndComment(SalonOrderVO salonOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(SCOREANDCOMMENT);
			
			pstmt.setInt(1, salonOrderVO.getOrderStatus());
			pstmt.setInt(2, salonOrderVO.getSalStar());
			pstmt.setString(3, salonOrderVO.getSalComment());
			pstmt.setString(4, salonOrderVO.getSalOrderNo());
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
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

	public static void main(String[] args) {
		SalonOrderJDBCDAO dao = new SalonOrderJDBCDAO();
		
//�s�W�q��P�ɷs�W����		
		SalonOrderVO salonOrderVO = new  SalonOrderVO();
		salonOrderVO.setMemNo("M001");
		salonOrderVO.setPetNo("P00001");
		salonOrderVO.setSalNo("B001");
		salonOrderVO.setSalTp(new Integer(1000));
		salonOrderVO.setOrderStatus(0);
				
		List<SalonOrderDetailVO> testList = new ArrayList<SalonOrderDetailVO>();
		SalonOrderDetailVO salOrderDetailxx = new SalonOrderDetailVO();
		salOrderDetailxx.setGroomerNo("G001");		
		salOrderDetailxx.setSalSevNo("BS001");
		salOrderDetailxx.setSalSevPr(new Integer(500));
		
				
		SalonOrderDetailVO salOrderDetailyy = new SalonOrderDetailVO();
		salOrderDetailyy.setGroomerNo("G002");		
		salOrderDetailyy.setSalSevNo("BS001");
		salOrderDetailyy.setSalSevPr(new Integer(500));
		
		
		testList.add(salOrderDetailxx);
		testList.add(salOrderDetailyy);
		
		dao.insertWithOrderDetail(salonOrderVO, testList);
		System.out.println("�s�W���\");
//�ק�
//		SalonOrderVO salonOrderVO2 = new SalonOrderVO();		
//		salonOrderVO2.setSalOrderNo("BO001");
//		salonOrderVO2.setMemNo("M001");
//		salonOrderVO2.setPetNo("P001");
//		salonOrderVO2.setSalNo("B002");
//		salonOrderVO2.setSalTp(1750);
//		salonOrderVO2.setOrderStatus(1); 
//				
//		dao.update(salonOrderVO2);
//		System.out.println("�ק令�\");
//�R��		
//		dao.delete("BO006");		
//�d��@���e���q��
//		List<SalonOrderVO> list = dao.getAllBySalNo("B001");
//		
//		for(SalonOrderVO asalonOrder :list) {
//			System.out.println(asalonOrder.getSalOrderNo() +",");
//			System.out.println(asalonOrder.getMemNo() +",");
//			System.out.println(asalonOrder.getPetNo() +",");
//			System.out.println(asalonOrder.getSalNo() +",");
//			System.out.println(asalonOrder.getSalOrderDate() +",");
//			System.out.println(asalonOrder.getSalTp() +",");
//			System.out.println(asalonOrder.getOrderStatus() +",");
//			System.out.println("=========================================");
//		}
//�ȤH�d�ߦۤv���q��
//		List<SalonOrderVO> list = dao.getAllByMemNo("M003");
//
//		for (SalonOrderVO asalonOrder :list) {
//			System.out.println(asalonOrder.getSalOrderNo() +",");
//			System.out.println(asalonOrder.getMemNo() +",");
//			System.out.println(asalonOrder.getPetNo() +",");
//			System.out.println(asalonOrder.getSalNo() +",");
//			System.out.println(asalonOrder.getSalOrderDate() +",");
//			System.out.println(asalonOrder.getSalTp() +",");
//			System.out.println(asalonOrder.getOrderStatus() +",");
//			System.out.println("=========================================");
//		}
//�ܧ�q�檬�A				
//		dao.changeOrderStatus("BO002", 2);
//		System.out.println("�s�W���\");
//����
//		SalonOrderVO salonOrderVO = new SalonOrderVO();
//		salonOrderVO.setOrderStatus(2);
//		salonOrderVO.setSalStar(4);
//		salonOrderVO.setSalComment("�A�ȥi�H�A�[�j");
//		salonOrderVO.setSalOrderNo("BO008");	
//		
//		dao.scoreAndComment(salonOrderVO);
//		System.out.println("�s�W���\");
	}	
}
