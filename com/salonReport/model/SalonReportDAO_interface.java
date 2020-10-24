package com.salonReport.model;

import java.util.List;

import com.salon.model.SalonVO;

public interface SalonReportDAO_interface {
	public void insert(SalonReportVO salonReportVO);
	public void update(SalonReportVO salonReportVO);
	public void delete(String salRepNo);
	public List<SalonReportVO> getall();

}
