package com.sitODetail.model;

import java.util.List;


public interface SitODetailDAO_interface {
	
	public void insert(SitODetailVO sitODetailVO);
	public SitODetailVO getByPKFK(String sitOrderNo);
	public List<SitODetailVO> getAll();
	
	public void insertfromOrder (SitODetailVO sitODetailVO, java.sql.Connection con);
}
