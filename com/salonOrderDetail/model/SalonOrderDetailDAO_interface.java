package com.salonOrderDetail.model;

import java.util.List;

public interface SalonOrderDetailDAO_interface {
	void insertSalonOD(SalonOrderDetailVO salonOrderDetailVO,java.sql.Connection con); 
	void delete(String salOrderNo);	 
	List<SalonOrderDetailVO> getAllBySalOrderNo(String salOrderNo);
}
