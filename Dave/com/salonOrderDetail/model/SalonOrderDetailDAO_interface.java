package com.salonOrderDetail.model;

import java.util.List;


public interface SalonOrderDetailDAO_interface {
	public void insert(SalonOrderDetailVO salonOrderDetailVO);
	public void delete(String salOrderNo);
	public List<SalonOrderDetailVO> getall();

}
