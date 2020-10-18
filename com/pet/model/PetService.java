package com.pet.model;

import java.util.List;

public class PetService {

	private PetDAO_interface dao;
	
	public PetService() {
		dao = new PetDAO();
	}
	
	public PetVO addPet(String memNo, String petName, Integer petType, Integer petCat, Integer petSex, java.sql.Date petBirth, String petChar, byte[] petPhoto) {
		PetVO petVO = new PetVO();
		petVO.setMemNo(memNo);
		petVO.setPetName(petName);
		petVO.setPetBirth(petBirth);
		petVO.setPetSex(petSex);
		petVO.setPetType(petType);
		petVO.setPetChar(petChar);
		petVO.setPetCat(petCat);
		petVO.setPetPhoto(petPhoto);
		dao.insert(petVO);

		return petVO;
	}
	
	public PetVO updatePet(String petName, Integer petType, Integer petCat, Integer petSex, java.sql.Date petBirth, String petChar, String petNo, byte[] petPhoto) {
		PetVO petVO = new PetVO();

		petVO.setPetName(petName);
		petVO.setPetType(petType);
		petVO.setPetCat(petCat);
		petVO.setPetSex(petSex);
		petVO.setPetBirth(petBirth);
		petVO.setPetChar(petChar);
		petVO.setPetNo(petNo);
		petVO.setPetPhoto(petPhoto);
		dao.update(petVO);
	
		return petVO;
	}
	
	public void deletePet(String petNo) {
		dao.delete(petNo);
	}
	
	public PetVO getOnePet(String petNo) {
		return dao.findByPrimaryKey(petNo);
	}
	
	public List<PetVO> getAllPet(String memNo){
		return dao.getAllPet(memNo);
	}
	public List<PetVO> getAll(){
		return dao.getAll();
	}
}
