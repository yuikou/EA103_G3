package com.salonOrder.model;

import java.util.List;
import com.salonOrderDetail.model.SalonOrderDetailVO;


public interface SalonOrderDAO_interface {	 
	 void update(SalonOrderVO salonOrderVO);
	 void delete(String salOrderNo);
	 void insertWithOrderDetail(SalonOrderVO salonOrderVO ,SalonOrderDetailVO salonOrderDetailVO,Integer memPoint );
	 void changeOrderStatus(String salOrderNo,Integer orderStatus);	 
	 void scoreAndComment(SalonOrderVO salonOrderVO);
	 List<SalonOrderVO> getAllBySalNo(String salNo);
	 List<SalonOrderVO> getAllByMemNo(String memNo);
	 
}
