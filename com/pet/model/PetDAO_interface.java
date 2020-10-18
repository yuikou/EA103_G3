package com.pet.model;

import java.util.*;

import com.pet.model.*;

public interface PetDAO_interface {
	public void insert(PetVO petVO);
	public void update(PetVO petVO);
	public void delete(String petNo);
	public PetVO findByPrimaryKey(String PetNo);
	public List<PetVO> getAllPet(String memNo);
	public List<PetVO> getAll();
}
