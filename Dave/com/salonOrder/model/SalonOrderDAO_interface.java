package com.salonOrder.model;

import java.util.List;

import com.salon.model.SalonVO;

public interface SalonOrderDAO_interface {
	public void insert(SalonOrderVO salonOrderVO);
	public void update(SalonOrderVO salonOrderVO);
	public void delete(String salOrderNo);
	public List<SalonOrderVO> getall();

}
