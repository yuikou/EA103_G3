package com.salonOrderDetail.model;

import java.util.List;

public class SalonOrderDetailService {
	private SalonOrderDetailDAO_interface dao;
	
	public SalonOrderDetailService() {
		dao = new SalonOrderDetailDAO();
	}
	
	public SalonOrderDetailVO addSalonOrderDetail(String salOrderNo,String salSevNo,String groomerNo,Integer salSevPr) {
		
		SalonOrderDetailVO salonOrderDetailVO = new SalonOrderDetailVO();
		salonOrderDetailVO.setSalOrderNo(salOrderNo);
		salonOrderDetailVO.setSalSevNo(salSevNo);
		salonOrderDetailVO.setGroomerNo(groomerNo);
		salonOrderDetailVO.setSalSevPr(salSevPr);
		dao.insert(salonOrderDetailVO);
		
		return salonOrderDetailVO;
	}
	
	public void deleteSalonOrderDetail(String salOrderNo) {
		dao.delete(salOrderNo);
	}
	
	public List<SalonOrderDetailVO> getAll(){
		return dao.getall();
	}

}
