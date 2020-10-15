package com.salonOrder.model;

import java.util.List;

import com.salon.model.SalonVO;

public interface SalonOrderDAO_interface {
	 void insert(SalonOrderVO salonOrderVO);
	 void update(SalonOrderVO salonOrderVO);
	 void delete(String salOrderNo);
	 List<SalonOrderVO> getall();
	 List<SalonOrderVO> getAllBySalNo(String salNo);
	 List<SalonOrderVO> getAllByMemNo(String memNo);
	

}
