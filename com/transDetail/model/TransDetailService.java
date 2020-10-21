package com.transDetail.model;

import java.util.*;


public class TransDetailService {
	
	private TransDetailDAO_interface dao;
	
	public TransDetailService() {
		dao = new TransDetailDAO();
	}
	
	public String addTransDetail(String memNo, String salOrderNo, String sitOrderNo, Integer transType, Integer depositType, java.sql.Timestamp transTime, Integer transAmount) {
		TransDetailVO tsVO = new TransDetailVO();
		tsVO.setMemNo(memNo);
		tsVO.setSalOrderNo(salOrderNo);
		tsVO.setSitOrderNo(salOrderNo);
		tsVO.setTransType(transType);
		tsVO.setDepositTpye(depositType);
		tsVO.setTransTime(transTime);
		tsVO.setTransAmount(transAmount);
		
		String transNo = dao.insert(tsVO);
		return transNo;
	}
	
	public void deposit(TransDetailVO tsVO, Integer memPoint) {
		dao.updatePoint(tsVO, memPoint);
	}
	
	public TransDetailVO getOneDetail(String transNo) {
		return dao.getOneDetail(transNo);
	}
	
	public List<TransDetailVO> getAlllBymemNo(String memNo){
		return dao.getMemDetail(memNo);
	}
	
	public List<TransDetailVO> getAll(){
		return dao.getAll();
	}
	
	public List<TransDetailVO> getAllBySitOrder(String memNo, String sitOrderNo){
		return dao.getSitOrder(memNo, sitOrderNo);
	}
	
	public List<TransDetailVO> getAllBySalOrder(String memNo, String salOrderNo){
		return dao.getSalorderNo(memNo, salOrderNo);
	}
	
	public List<TransDetailVO> getAllByType(String memNo, Integer transType){
		return dao.getByTransTyype(memNo, transType);
	}
}
