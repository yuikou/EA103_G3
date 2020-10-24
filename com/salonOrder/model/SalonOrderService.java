package com.salonOrder.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.member.model.MemService;
import com.member.model.MemVO;
import com.salonOrderDetail.model.*;

public class SalonOrderService {
	
	private SalonOrderDAO_interface dao;
	
	public SalonOrderService() {
		dao  = new SalonOrderDAO();		
	} 
	
	public SalonOrderVO addSalonOrder(String memNo,String petNo,String salNo,Integer salTp,Integer orderStatus,//璹虫把计
			                          String salSevNo,String groomerNo,Integer salSevPr,//灿把计
			                          Integer memPoint){ //穦Ι蹿把计 		
		
		SalonOrderVO salonOrderVO = new SalonOrderVO();				
		salonOrderVO.setMemNo(memNo);
		salonOrderVO.setPetNo(petNo);
		salonOrderVO.setSalNo(salNo);	
		salonOrderVO.setSalTp(salTp);
		salonOrderVO.setOrderStatus(orderStatus);
		
		SalonOrderDetailVO salonOrderDetailVO = new SalonOrderDetailVO();
//		List<SalonOrderDetailVO> list = new ArrayList<SalonOrderDetailVO>();
		salonOrderDetailVO.setSalSevNo(salSevNo);
		salonOrderDetailVO.setGroomerNo(groomerNo);
		salonOrderDetailVO.setSalSevPr(salSevPr);				
//		list.add(salonOrderDetailVO);
		
		
		dao.insertWithOrderDetail(salonOrderVO,salonOrderDetailVO,memPoint);
		
		
		
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
		
		dao.update(salonOrderVO);
				
		return salonOrderVO;
	} 
	
	public void deleteSalonOrder(String salOrderNo) {
		dao.delete(salOrderNo);
	}		
	
	public List<SalonOrderVO> getAllBYSalNo(String salNo){
		return dao.getAllBySalNo(salNo);
	}
	
	public List<SalonOrderVO> getALLBYMemNo(String memNo){
		return dao.getAllByMemNo(memNo);
	}

}
