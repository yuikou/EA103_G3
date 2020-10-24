package com.salonOrder.model;

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

import com.member.model.MemDAO;
import com.salonOrderDetail.model.SalonOrderDetailJDBCDAO;
import com.salonOrderDetail.model.SalonOrderDetailVO;

public class SalonOrderDAO implements SalonOrderDAO_interface {
	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G3DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = 
			"INSERT INTO SALONORDER(salorderno,memno,petno,salno,salorderdate,saltp,orderstatus) VALUES ('BO' || LPAD(SALORDER_SEQ.NEXTVAL,3,'0'),?,?,?,CURRENT_TIMESTAMP,?,?)";
	private static final String DELETE = 
			"DELETE FROM SALONORDER WHERE salorderno = ?";
	private static final String UPDATE = 
			"UPDATE SALONORDER SET memno=?, petno=?, salno=? , salorderdate=?, saltp=?, orderstatus=? WHERE salorderno=?";
	private static final String GET_ALL_BY_SALNO = 
			"SELECT * FROM SALONORDER WHERE SALNO=? ORDER BY salorderdate";
	private static final String GET_ALL_BY_MEMNO = 
			"SELECT * FROM SALONORDER WHERE MEMNO=? ORDER BY salorderdate";
	private static final String UPDATE_ORDERSTATUS_STMT = 
			"UPDATE SALONORDER SET ORDERSTATUS = ? WHERE  SALORDERNO= ?";
	private static final String SCOREANDCOMMENT = 
			"UPDATE SALONORDER SET ORDERSTATUS = ?, SALSTAR = ?, SALCOMMENT = ? WHERE SALORDERNO = ?";

//�s�W�q��P�ɷs�W�q�����	�� ����
	@Override
	public void insertWithOrderDetail(SalonOrderVO salonOrderVO, SalonOrderDetailVO salonOrderDetailVO,Integer memPoint) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

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
//			System.out.println("list.size()-A=" + list.size());
//			for (SalonOrderDetailVO salOD : list) {				
				
			salonOrderDetailVO.setSalOrderNo(new String(salOrderNo));
				dao.insertSalonOD(salonOrderDetailVO, con);				
//			}
				
			MemDAO memDAO = new MemDAO();			
			memDAO.transaction(salonOrderVO.getMemNo(), memPoint, salonOrderVO.getSalTp(), con);//�|���I�Ʀ���
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);

//			System.out.println("list.size()-B=" + list.size());
//			System.out.println("�s�W�q��s��" + salOrderNo + "��,�@������" + list.size() + "���P�ɳQ�s�W");

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, salonOrderVO.getMemNo());
			pstmt.setString(2, salonOrderVO.getPetNo());
			pstmt.setString(3, salonOrderVO.getSalNo());
			pstmt.setTimestamp(4, salonOrderVO.getSalOrderDate());
			pstmt.setInt(5, salonOrderVO.getSalTp());
			pstmt.setInt(6, salonOrderVO.getOrderStatus());
			pstmt.setString(7, salonOrderVO.getSalOrderNo());

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

//�q��Ʈw�R���q��
	@Override
	public void delete(String salOrderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, salOrderNo);

			pstmt.executeUpdate();
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

//���o��@���e���q��
	@Override
	public List<SalonOrderVO> getAllBySalNo(String salNo) {
		List<SalonOrderVO> list = new ArrayList<SalonOrderVO>();
		SalonOrderVO salonOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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

//���o��@�|���q��
	@Override
	public List<SalonOrderVO> getAllByMemNo(String memNo) {
		List<SalonOrderVO> list = new ArrayList<SalonOrderVO>();
		SalonOrderVO salonOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORDERSTATUS_STMT);

			pstmt.setInt(1, orderStatus);
			pstmt.setString(2, salOrderNo);

			pstmt.executeUpdate();

			con.commit();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(SCOREANDCOMMENT);

			pstmt.setInt(1, salonOrderVO.getOrderStatus());
			pstmt.setInt(2, salonOrderVO.getSalStar());
			pstmt.setString(3, salonOrderVO.getSalComment());
			pstmt.setString(4, salonOrderVO.getSalOrderNo());

			pstmt.executeUpdate();
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
}
