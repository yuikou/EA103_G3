package com.adoPet.model;

import java.util.List;

import com.adoPetAlbum.model.AdoPetAlbumVO;


public interface AdoPetDAO_interface {

	public void insert(List<AdoPetAlbumVO> picArr,AdoPetVO adoPetVO);
	public void update(AdoPetVO adoPetVO);
	public void update_status (Integer adoStatus,String empNo,String adoPetNo);
	public void insertAppForm(String memNo,String adoPetNo, byte[] appForm);
	public AdoPetVO findByPrimaryKey(String adoPetNo);		//管理員查詢單一寵物
	public List<AdoPetVO> getAll();
	public List<AdoPetVO> SearchAdpPet(String sentence);
	public List<AdoPetVO> findByAdoStatus(Integer adoStatus);
	

}
