package com.adoPetFollow.model;

import java.util.List;

import com.adoPet.model.AdoPetVO;

public interface AdoPetFollowDAO_interface {

	public void insert(AdoPetFollowVO adoPetFollowVO);
	public void delete(String memNo,String adoPetNo);
	public List<AdoPetVO> getAll(String memNo);
	
}
