package com.petSitter.model;

import java.util.List;

public class PetSitterService {
	
	private PetSitterDAO_interface dao;
	
	public PetSitterService() {
		dao = new PetSitterDAO();
	}
	
	public PetSitterVO insert(String memNo, String sitInfo, String srvSTime, String srvETime, String bankCode,
			String bankAcc, Integer sitAccStatus, Double totalComm, Double totalCus, Integer repeatCus) {
		
		PetSitterVO petSitterVO = new PetSitterVO();
		petSitterVO.setMemNo(memNo);
		petSitterVO.setSitInfo(sitInfo);
		petSitterVO.setSrvSTime(srvSTime);
		petSitterVO.setSrvETime(srvETime);		
		petSitterVO.setBankCode(bankCode);
		petSitterVO.setBankAcc(bankAcc);
		petSitterVO.setSitAccStatus(sitAccStatus);
		petSitterVO.setTotalComm(totalComm);
		petSitterVO.setTotalCus(totalCus);
		petSitterVO.setRepeatCus(repeatCus);

		dao.insert(petSitterVO);
		
		return petSitterVO;
	}
	
	public PetSitterVO update(String sitNo, String memNo, String sitInfo, String srvSTime, String srvETime, String bankCode,
			String bankAcc, Integer sitAccStatus, Double totalComm, Double totalCus, Integer repeatCus) {
		
		PetSitterVO petSitterVO = new PetSitterVO();
		petSitterVO.setSitNo(sitNo);
		petSitterVO.setMemNo(memNo);
		petSitterVO.setSitInfo(sitInfo);
		petSitterVO.setSrvSTime(srvSTime);
		petSitterVO.setSrvETime(srvETime);		
		petSitterVO.setBankCode(bankCode);
		petSitterVO.setBankAcc(bankAcc);
		petSitterVO.setSitAccStatus(sitAccStatus);
		petSitterVO.setTotalComm(totalComm);
		petSitterVO.setTotalCus(totalCus);
		petSitterVO.setRepeatCus(repeatCus);
		dao.update(petSitterVO);
		
		return petSitterVO;	
	}
	
	public PetSitterVO update_totalComm(String sitNo, Double totalComm) {
		
		PetSitterVO petSitterVO = new PetSitterVO();
		petSitterVO.setSitNo(sitNo);
		petSitterVO.setTotalComm(totalComm);
		dao.update_totalComm(petSitterVO);
		
		return petSitterVO;	
	}
	
	public PetSitterVO update_repeat_totalCus(String sitNo, Double totalCus, Integer repeatCus) {
		
		PetSitterVO petSitterVO = new PetSitterVO();
		petSitterVO.setSitNo(sitNo);
		petSitterVO.setTotalCus(totalCus);
		petSitterVO.setRepeatCus(repeatCus);
		dao.update_repeat_totalCus(petSitterVO);
		
		return petSitterVO;	
	}
	
	public PetSitterVO getByPK(String sitNo) {
		return dao.getByPK(sitNo);
	}
	
	public PetSitterVO getByFK(String memNo) {
		return dao.getByFK(memNo);
	}
	
	public List<PetSitterVO> getAll() {
		return dao.getAll();
	}



}
