package com.sitPhoto.model;

import java.util.List;

public interface SitPhotoDAO_interface {
	
	public void add(SitPhotoVO sitPhotoVO);
	public void delete(String sitPNo);
	public SitPhotoVO getByPK(String sitPNo);
	public List<SitPhotoVO> getAll(String sitNo);
	
}