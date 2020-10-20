package com.sitPhoto.model;

import java.util.List;

public class SitPhotoService {
	
	private SitPhotoDAO_interface dao;
	
	public SitPhotoService() {
		dao = new SitPhotoDAO();
	}
	
	public SitPhotoVO add(String sitNo, byte[] sitPhoto) {
		
		SitPhotoVO sitPhotoVO = new SitPhotoVO();
		sitPhotoVO.setSitNo(sitNo);
		sitPhotoVO.setSitPhoto(sitPhoto);
		dao.add(sitPhotoVO);
		
		return sitPhotoVO;
		
	}
	
	public void delete(String sitPNo) {
		dao.delete(sitPNo);
	}
	
	public SitPhotoVO getByPK(String sitPNo) {
		return dao.getByPK(sitPNo);
	}
	
	public List<SitPhotoVO> getAll(String sitNo) {
		return dao.getAll(sitNo);
	}

}
