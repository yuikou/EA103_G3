package com.salon.model;

import java.util.List;

public interface SalonDAO_interface {
	public void insert(SalonVO salonVO);
	public void update(SalonVO salonVO);
	public void delete(String salNo);
	public SalonVO findByPrimaryKey(String salNo);
	public List<SalonVO> getall();
}
