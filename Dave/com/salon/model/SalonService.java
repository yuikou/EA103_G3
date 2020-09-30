package com.salon.model;

import java.sql.Timestamp;
import java.util.List;

public class SalonService {
	private SalonDAO_interface dao;
	
	public SalonService() {
		dao = new SalonDAO();
	}
	
	public SalonVO addsalon(String salName,String salOwner,String salPh,String salMail,String salCity,String salDist,String salAdr,String salAc,String salPw,String salSTime,String salETime,String salRemit,String bankCode,Integer salStatus,String salInfo,Integer salPetType) {
		SalonVO salonVO  =new SalonVO();
		
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
		dao.insert(salonVO);
		
		return salonVO;
	}
	
	public SalonVO updatesalon(String salNo,String salName,String salOwner,String salPh,String salMail,String salCity,String salDist,String salAdr,String salAc,String salPw,String salSTime,String salETime,String salRemit,String bankCode,Integer salStatus,String salInfo,Integer salPetType) {
		SalonVO salonVO  =new SalonVO();
		
		salonVO.setSalNo(salNo);
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
		dao.update(salonVO);
		
		return salonVO;
	}
	
	public void deleteSalon(String salNo) {
		dao.delete(salNo);
	}
	
	public List<SalonVO> getAll(){
		return dao.getall();
	}
	
	

}
