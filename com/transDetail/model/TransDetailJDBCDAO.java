package com.transDetail.model;

import java.sql.*;
import java.util.*;

import com.member.model.MemJDBCDAO;
import com.member.model.MemVO;

public class TransDetailJDBCDAO implements TransDetailDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "RICHY";
	String password = "123456";
	
	private static final String INSERT = 
			"INSERT INTO TRANSDETAIL (TRANSNO, MEMNO, SITORDERNO, SALORDERNO, TRANSTIME, TRANSAMOUNT, TRANSTYPE, DEPOSITTYPE) VALUES ('T'||LPAD(TRANS_SEQ.NEXTVAL, 7, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_POINT = 
			"UPDATE TRANSDETAIL SET TRADESTATUS=1 WHERE TRANSNO = ?";
	private static final String GET_ONE_DETAIL = 
			"SELECT * FROM TRANSDETAIL WHERE TRANSNO=?";
	private static final String GET_MEM_DETAIL = 
			"SLELCT * FROM TRANSDEYAIL WHERE MEMNO=?";
	private static final String GET_ALL = 
			"SELECT * FROM TRANSDETAIL";
	private static final String GET_SITORDER = 
			"SELECT * FROM TRANSDETAIL WHERE MEMNO=? AND SITORDERNO=?";
	private static final String GET_SALORDER = 
			"SELECT * FROM TRANSDETAIL WHERE MEMNO=? AND SALORDERNO=?";
	private static final String GET_BY_TRANSTYPE = 
			"SELECT * FROM TRANSDETAIL WHERE MEMNO=? AND TRANSTYPE=?";
	@Override
	public String insert(TransDetailVO transdetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// �n���^���ۼW�D���
		String next_transNo = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			con.setAutoCommit(false);
			
			String[] col = {"transNo"};
			pstmt = con.prepareStatement(INSERT, col);
			
			pstmt.setString(1, transdetailVO.getMemNo());
			pstmt.setString(2, transdetailVO.getSitOrderNo());
			pstmt.setString(3, transdetailVO.getSalOrderNo());
			pstmt.setTimestamp(4, transdetailVO.getTransTime());
			pstmt.setInt(5, transdetailVO.getTransAmount());
			pstmt.setInt(6, transdetailVO.getTransType());
			pstmt.setInt(7, transdetailVO.getDepositTpye());
			pstmt.executeUpdate();
			
			// ���^ �ۼW�D���
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_transNo = rs.getString(1);
				System.out.println("transNo : " + next_transNo);
			}else {
				System.out.println("�����o�ۼW�D���");
			}
			rs.close();
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("transNo : " + next_transNo);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database erro occured. " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return next_transNo;
	}

	@Override
	public void updatePoint(TransDetailVO tsVO, Integer memPoint) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
		try {	
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			con.setAutoCommit(false);
			// ��s '������A'
			pstmt = con.prepareStatement(UPDATE_POINT);
			pstmt.setString(1, tsVO.getTransNo());
			pstmt.executeUpdate();
			
			// �N�x�Ȫ��B��J �|�� [�q�l���]]
			MemJDBCDAO memdao = new MemJDBCDAO();
			memdao.deposit(tsVO, memPoint, con);
			
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��TransDetail");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public TransDetailVO getOneDetail(String transNo) {
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_ONE_DETAIL);
			
			pstmt.setString(1, transNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return transdetailVO;
	}

	@Override
	public List<TransDetailVO> getMemDetail(String memNo) {
		List<TransDetailVO> list = new ArrayList<TransDetailVO>();
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_MEM_DETAIL);
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));

				list.add(transdetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<TransDetailVO> getAll() {
		List<TransDetailVO> list = new ArrayList<TransDetailVO>();
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));

				list.add(transdetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<TransDetailVO> getSitOrder(String memNo, String sitOrderNo) {
		List<TransDetailVO> list = new ArrayList<TransDetailVO>();
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_SITORDER);
			pstmt.setString(1, memNo);
			pstmt.setString(2, sitOrderNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));

				list.add(transdetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<TransDetailVO> getSalorderNo(String memNo, String salOrderNo) {
		List<TransDetailVO> list = new ArrayList<TransDetailVO>();
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_SALORDER);
			pstmt.setString(1, memNo);
			pstmt.setString(2, salOrderNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));

				list.add(transdetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<TransDetailVO> getByTransTyype(String memNo, Integer transType) {
		List<TransDetailVO> list = new ArrayList<TransDetailVO>();
		TransDetailVO transdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pstmt = con.prepareStatement(GET_SALORDER);
			pstmt.setString(1, memNo);
			pstmt.setInt(2, transType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				transdetailVO = new TransDetailVO();
				transdetailVO.setTransNo(rs.getString("transNo"));
				transdetailVO.setMemNo(rs.getString("memNo"));
				transdetailVO.setSitOrderNo(rs.getString("sitOrderNo"));
				transdetailVO.setSalOrderNo(rs.getString("salOrderNo"));
				transdetailVO.setTransTime(rs.getTimestamp("transTime"));
				transdetailVO.setTransAmount(rs.getInt("transAmount"));
				transdetailVO.setTransType(rs.getInt("transType"));
				transdetailVO.setDepositTpye(rs.getInt("depositType"));
				transdetailVO.setTradeStatus(rs.getInt("tradeStatus"));

				list.add(transdetailVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	//test
	public static void main(String[] args) {
		TransDetailJDBCDAO dao = new TransDetailJDBCDAO();
		
		//insert
//		TransDetailVO tsVO1 = new TransDetailVO();
//		tsVO1.setMemNo("M001");
//		tsVO1.setSitOrderNo(null);
//		tsVO1.setSalOrderNo(null);
//		tsVO1.setTransTime(new java.sql.Timestamp(System.currentTimeMillis()));
//		tsVO1.setTransAmount(1000);
//		tsVO1.setTransType(1);
//		tsVO1.setDepositTpye(0);
//		
//		System.out.println(dao.insert(tsVO1));
		
		//deposit
//		TransDetailVO tsVO2 = new TransDetailVO();
//		tsVO2.setMemNo("M001");
//		tsVO2.setTransAmount(1000);
//		tsVO2.setTransNo("T0000002");
//		MemVO memVO = new MemVO();
//		memVO.setMemPoint(1000);
//		dao.update(tsVO2, memVO);
		
		// get one datail
		TransDetailVO tsVO3 = dao.getOneDetail("T0000002");
		System.out.println("transNo:"+tsVO3.getTransNo());
		System.out.println("memNo:"+tsVO3.getMemNo());
		System.out.println("transAmount:"+tsVO3.getTransAmount());
		
	}
}
