package com.salonOrder.model;

import java.util.List;
import com.salonOrderDetail.model.SalonOrderDetailVO;


public interface SalonOrderDAO_interface {
	 void insert(SalonOrderVO salonOrderVO);
	 void update(SalonOrderVO salonOrderVO);
	 void delete(String salOrderNo);
	 void insertWithOrderDetail(SalonOrderVO salonOrderVO , List<SalonOrderDetailVO> list);
	 List<SalonOrderVO> getall();
	 List<SalonOrderVO> getAllBySalNo(String salNo);
	 List<SalonOrderVO> getAllByMemNo(String memNo);
	

}
