package com.salonF.model;

import java.util.List;

public class SalFService {
	private SalonFDAO_interface dao;
	
	public SalFService(){
		dao = new SalonFDAO();
	}
	
	public SalonFVO addSalF(String memNo, String salNo) {
		SalonFVO safvo = new SalonFVO ();
		safvo.setMemNo(memNo);
		safvo.setSalNo(salNo);
		
		dao.insert(safvo);
		return safvo;
	}
	
	public void deleteSalF(String memNo, String salNo) {
		dao.delete(memNo, salNo);
	}
	
	public List<SalonFVO> getAll(String memNo) {
		return dao.findBymemKey(memNo);
	}
}
