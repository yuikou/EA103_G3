package com.salonOrder.model;

import java.sql.Timestamp;
import java.util.List;

public class SalonOrderService {
	
	private SalonOrderDAO_interface dao;
	
	public SalonOrderService() {
		dao  = new SalonOrderDAO();		
	} 
	
	public SalonOrderVO addSalonOrder(String memNo,String petNo,String salNo,Timestamp salOrderDate,Integer salTp,Integer orderStatus) {
		
		SalonOrderVO salonOrderVO = new SalonOrderVO();
		salonOrderVO.setMemNo(memNo);
		salonOrderVO.setPetNo(petNo);
		salonOrderVO.setSalNo(salNo);
		salonOrderVO.setSalOrderDate(salOrderDate);
		salonOrderVO.setSalTp(salTp);
		salonOrderVO.setOrderStatus(orderStatus);
		
		dao.insert(salonOrderVO);
		
		return salonOrderVO;
	} 
	
	public SalonOrderVO updateSalonOrder(String salOrderNo,String memNo,String petNo,String salNo,Timestamp salOrderDate,Integer salTp,Integer orderStatus) {
		
		SalonOrderVO salonOrderVO = new SalonOrderVO();
		salonOrderVO.setSalOrderNo(salOrderNo);
		salonOrderVO.setMemNo(memNo);
		salonOrderVO.setPetNo(petNo);
		salonOrderVO.setSalNo(salNo);
		salonOrderVO.setSalOrderDate(salOrderDate);
		salonOrderVO.setSalTp(salTp);
		salonOrderVO.setOrderStatus(orderStatus);
		
		dao.insert(salonOrderVO);
		
		return salonOrderVO;
	} 
	
	public void deleteSalonOrder(String salOrderNo) {
		dao.delete(salOrderNo);
	}
	
	public List<SalonOrderVO> getAll(){
		return dao.getall();
	}

}
