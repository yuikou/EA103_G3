package com.adoPetFollow.model;

import java.util.List;

import com.adoPet.model.AdoPetVO;

public interface AdoPetFollowDAO_interface {

	public Boolean insert(AdoPetFollowVO adoPetFollowVO);
	public Boolean delete(String memNo,String adoPetNo);
	public List<AdoPetFollowVO> getAll(String memNo);
	
}
