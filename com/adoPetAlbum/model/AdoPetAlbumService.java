package com.adoPetAlbum.model;

import java.util.List;



public class AdoPetAlbumService {

	private AdoPetAlbumInterface dao;

	public AdoPetAlbumService() {
		dao = new AdoPetAlbumDAO();
	}

	public AdoPetAlbumVO AdoPetAlbumInsert(String adoPetNo, byte[] adoPetPic) {

		AdoPetAlbumVO adoPetAlbumVO = new AdoPetAlbumVO();
		adoPetAlbumVO.setAdoPetNo(adoPetNo);
		adoPetAlbumVO.setAdoPetPic(adoPetPic);
		dao.insert(adoPetAlbumVO);
		
		return adoPetAlbumVO;
	}

	public void delete(String adoPicNo) {
		dao.delete(adoPicNo);
	}

	public List<String> getPicNoByAdoPetNo(String adoPetNo) {
		return dao.getPicNoByAdoPetNo(adoPetNo);
	}
	
	public AdoPetAlbumVO getPic(AdoPetAlbumVO adoPetAlbumVO) {
		return dao.getPic(adoPetAlbumVO);
	}
}
