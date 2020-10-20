package com.salon.model;

import java.util.ArrayList;
import java.util.List;

public class SalonService {

	private SalonDAO_interface dao;

	public SalonService() {
		dao = new SalonDAO();
	}

	public SalonVO addsalon(String salName, String salOwner, String salPh, String salMail, String salCity,
			String salDist, String salAdr, String salAc, String salPw, String salSTime, String salETime,
			String salRemit, String bankCode, Integer salStatus, String salInfo, Integer salPetType, byte[] salCertif,byte[] salPic) {
		SalonVO salonVO = new SalonVO();

		salonVO.setSalName(salName);
		salonVO.setSalOwner(salOwner);
		salonVO.setSalPh(salPh);
		salonVO.setSalMail(salMail);
		salonVO.setSalCity(salCity);
		salonVO.setSalDist(salDist);
		salonVO.setSalAdr(salAdr);
		salonVO.setSalAc(salAc);
		salonVO.setSalPw(salPw);
		salonVO.setSalSTime(salSTime);
		salonVO.setSalETime(salETime);
		salonVO.setSalRemit(salRemit);
		salonVO.setBankCode(bankCode);
		salonVO.setSalStatus(salStatus);
		salonVO.setSalInfo(salInfo);
		salonVO.setSalPetType(salPetType);
		salonVO.setSalCertif(salCertif);
		salonVO.setSalPic(salPic);
		dao.insert(salonVO);

		return salonVO;
	}

	public SalonVO updatesalon(String salNo,String salName, String salOwner, String salPh, String salMail, String salCity,
			String salDist, String salAdr, String salSTime, String salETime, String salRemit, String bankCode,
			String salInfo, Integer salPetType,byte[] salCertif,byte[] salPic) {
		SalonVO salonVO = new SalonVO();

		salonVO.setSalNo(salNo);
		salonVO.setSalName(salName);
		salonVO.setSalOwner(salOwner);
		salonVO.setSalPh(salPh);
		salonVO.setSalMail(salMail);
		salonVO.setSalCity(salCity);
		salonVO.setSalDist(salDist);
		salonVO.setSalAdr(salAdr);
		salonVO.setSalSTime(salSTime);
		salonVO.setSalETime(salETime);
		salonVO.setSalRemit(salRemit);
		salonVO.setBankCode(bankCode);
		salonVO.setSalInfo(salInfo);
		salonVO.setSalPetType(salPetType);
		salonVO.setSalCertif(salCertif);
		salonVO.setSalPic(salPic);
		dao.update(salonVO);

		return salonVO;
	}

	public void deleteSalon(String salNo) {
		dao.delete(salNo);
	}

	public List<SalonVO> getAll() {		
		return dao.getall();
	}
	
	public List<SalonVO> getAllUnaccept() {		
		List<SalonVO> salonList = new ArrayList<SalonVO>();
				
		for(SalonVO salonVO:dao.getall()) {
			if(salonVO.getSalStatus() == 0) {
				salonList.add(salonVO);
			}
		}		
		return salonList;
	}
	
	public List<SalonVO> getAllForShow() {		
		List<SalonVO> salonList = new ArrayList<SalonVO>();
				
		for(SalonVO salonVO:dao.getall()) {
			if(salonVO.getSalStatus() == 1) {
				salonList.add(salonVO);
			}
		}		
		return salonList;
	}
	

	public SalonVO getonesalon(String salNo) {
		return dao.findByPrimaryKey(salNo);
	}
	
	public SalonVO login(String salAc, String salPw) {
		return dao.login(salAc, salPw);
	}
	public void approve(String salNo, Integer salStatus) {
		salStatus = 1;
		dao.updateStatus(salNo, salStatus);
		
	}
	public void notApprove(String salNo, Integer salStatus) {
		salStatus = 2;
		dao.updateStatus(salNo, salStatus);
		
	}
	
	public boolean checkAc(String salAc) {
		return dao.checkAc(salAc);
	}
	
	
	
	
	
	
}
