package com.adoPetFollow.model;

import java.util.List;


public class AdoPetFollowService {

	private AdoPetFollowDAO_interface dao;

	public AdoPetFollowService() {
		dao = new AdoPetFollowDAO();
	}

	public Boolean adoPetFollowInsert(String memNo, String adoPetNo) {

		AdoPetFollowVO adoPetFollowVO = new AdoPetFollowVO();
		adoPetFollowVO.setMemNo(memNo);
		adoPetFollowVO.setAdoPetNo(adoPetNo);
		Boolean insertFinished =dao.insert(adoPetFollowVO);

		return insertFinished;

	}

	public Boolean adoPetFollowDelete(String memNo, String adoPetNo) {

		return dao.delete(memNo, adoPetNo);
	}

	public List<AdoPetFollowVO> getAll(String memNo) {
		return dao.getAll(memNo);
	}
}
