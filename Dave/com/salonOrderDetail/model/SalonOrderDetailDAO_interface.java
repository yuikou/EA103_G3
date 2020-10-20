package com.salonOrderDetail.model;

import java.util.List;

import com.salonOrder.model.SalonOrderVO;


public interface SalonOrderDetailDAO_interface {
	 void insert(SalonOrderDetailVO salonOrderDetailVO);
	 void delete(String salOrderNo);
	 void insertSalonOrderDetail(SalonOrderDetailVO salonOrderDetailVO,java.sql.Connection con); 
	 List<SalonOrderDetailVO> getall();
	 List<SalonOrderDetailVO> getAllBySalOrderNo(String salOrderNo);
	 List<SalonOrderDetailVO> getAllByMemNo(String memNo);
	 

}
