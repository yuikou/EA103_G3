package com.adoPet.model;

import java.sql.Date;
import java.util.List;

import com.adoPetAlbum.model.AdoPetAlbumVO;

public class AdoPetService {

	private AdoPetDAO_interface dao;

	public AdoPetService() {
		dao = new AdoPetDAO();
	}

	public AdoPetVO addAdoPet(List<AdoPetAlbumVO> picArr,Integer petType, String petName, String petBreed, Integer petSex, Date petBirth,
							  Double petWeight, Integer petCat, String petChar, String location) {

		AdoPetVO adoPetVO = new AdoPetVO();
		adoPetVO.setPetType(petType);
		adoPetVO.setPetName(petName);
		adoPetVO.setPetBreed(petBreed);
		adoPetVO.setPetSex(petSex);
		adoPetVO.setPetBirth(petBirth);
		adoPetVO.setPetWeight(petWeight);
		adoPetVO.setPetCat(petCat);
		adoPetVO.setPetChar(petChar);
		adoPetVO.setLocation(location);
		dao.insert(picArr,adoPetVO);
//		adoPetVO.setAdoPetNo(dao.insert(adoPetVO));
		
		return adoPetVO;
	}

	public AdoPetVO updateAdoPet(Integer adoStatus, Integer petType, String petName, String petBreed, Integer petSex,
								 Date petBirth, Double petWeight, Integer petCat, String petChar, String location, String adoPetNo) {

		AdoPetVO adoPetVO = new AdoPetVO();
		adoPetVO.setAdoStatus(adoStatus);
		adoPetVO.setPetType(petType);
		adoPetVO.setPetName(petName);
		adoPetVO.setPetBreed(petBreed);
		adoPetVO.setPetSex(petSex);
		adoPetVO.setPetBirth(petBirth);
		adoPetVO.setPetWeight(petWeight);
		adoPetVO.setPetCat(petCat);
		adoPetVO.setPetChar(petChar);
		adoPetVO.setLocation(location);
		adoPetVO.setAdoPetNo(adoPetNo);
		dao.update(adoPetVO);

		return adoPetVO;
	}
	
	public void update_status(Integer adoStatus,String empNo,String adoPetNo) {
		dao.update_status(adoStatus,empNo,adoPetNo);
	}

	public void insertAppForm(String memNo,String adoPetNo, byte[] appForm) {
		dao.insertAppForm(memNo,adoPetNo, appForm);
	}

	public AdoPetVO findByPrimaryKey(String adoPetNo) {
		return dao.findByPrimaryKey(adoPetNo);
	}

	public List<AdoPetVO> getAll() {
		return dao.getAll();
	}

	public List<AdoPetVO> SearchAdpPet(String sentence) {
		return dao.SearchAdpPet(sentence);
	}

	public List<AdoPetVO> findByAdoStatus(Integer adoStatus) {
		return dao.findByAdoStatus(adoStatus);
	}

}
