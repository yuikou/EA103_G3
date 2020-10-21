package com.petSitter.model;

import java.util.List;

public interface PetSitterDAO_interface {
	
	public void insert(PetSitterVO petSitterVO);
	public void update(PetSitterVO petSitterVO);
	public void update_totalComm(PetSitterVO petSitterVO);
	public void update_repeat_totalCus(PetSitterVO petSitterVO);
	public PetSitterVO getByPK(String sitNo);
	public PetSitterVO getByFK(String memNo);
	public List<PetSitterVO> getAll();
}
