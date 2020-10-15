package com.adoPetFollow.model;

import java.util.List;

import com.adoPet.model.AdoPetVO;

public class AdoPetFollowService {

	private AdoPetFollowDAO_interface dao;

	public AdoPetFollowService() {
		dao = new AdoPetFollowDAO();
	}

	public AdoPetFollowVO adoPetFollowInsert(String memNo, String adoPetNo) {

		AdoPetFollowVO adoPetFollowVO = new AdoPetFollowVO();
		adoPetFollowVO.setMemNo(memNo);
		adoPetFollowVO.setAdoPetNo(adoPetNo);
		dao.insert(adoPetFollowVO);

		return adoPetFollowVO;

	}

	public void adoPetFollowDelete(String memNo, String adoPetNo) {

		dao.delete(memNo, adoPetNo);
	}

	public List<AdoPetVO> getAll(String memNo) {
		return dao.getAll(memNo);
	}
}
