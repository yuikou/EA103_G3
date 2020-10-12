package com.salonAlbum.model;

import java.util.List;

public class SalonAlbService {
	private SalonAlbDAO_interface dao;
	
	public SalonAlbService(){
		dao = new SalonAlbDAO();
	}
	
	public SalonAlbVO addSalAlb(String salNo, byte[] salPic) {
		SalonAlbVO salb = new SalonAlbVO();
		salb.setSalNo(salNo);
		salb.setSalAlbPic(salPic);
//		salb.setSalPortInfo(salPortInfo);
		
		dao.insert(salb);
		return salb;
	}
	
//	public SalonAlbVO updateSalAlb(String salAlbNo, String salNo, byte[] salPic, String salPortInfo) {
//		SalonAlbVO salb = new SalonAlbVO();
//		salb.setSalAlbNo(salAlbNo);
//		salb.setSalNo(salNo);
//		salb.setSalPic(salPic);
//		salb.setSalPortInfo(salPortInfo);
//		
//		dao.update(salb);
//		return salb;
//	}
	
	public void deleteSalAlb(String salAlbNo) {
		dao.delete(salAlbNo);
	}
	
	public SalonAlbVO getOnePic(String salAlbNo) {
		return dao.getOnePic(salAlbNo);
	}
	
	public List<SalonAlbVO> getOneSalAlb(String salno) {
		return dao.findBySalonKey(salno);
	}
}
