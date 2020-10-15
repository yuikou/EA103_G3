package com.salonReport.model;

import java.util.List;

public class SalonReportService {
	private SalonReportDAO_interface dao;
	
	public SalonReportService() {
		dao = new SalonReportDAO();
	}
	
	public SalonReportVO addSalonReport(String salOrderNo,String memNo,String salRep,String empNo) {
		SalonReportVO salonReportVO =new SalonReportVO();
		
		salonReportVO.setSalOrderNo(salOrderNo);
		salonReportVO.setMemNo(memNo);
		salonReportVO.setSalRep(salRep);
		salonReportVO.setEmpNo(empNo);
		
		dao.insert(salonReportVO);
		
		return salonReportVO;		
	}
	
	public SalonReportVO updateSalonReport(String salRepNo,String salOrderNo,String memNo,String salRep,String empNo) {
		SalonReportVO salonReportVO =new SalonReportVO();
		
		salonReportVO.setSalRepNo(salRepNo);
		salonReportVO.setSalOrderNo(salOrderNo);
		salonReportVO.setMemNo(memNo);
		salonReportVO.setSalRep(salRep);
		salonReportVO.setEmpNo(empNo);
		
		dao.insert(salonReportVO);
		
		return salonReportVO;		
	}
	
	public void delete(String salRepNo) {
		dao.delete(salRepNo);
	}
	
	public List<SalonReportVO> getAll(){
		return dao.getall();
	}

}
