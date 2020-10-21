package com.adoPetAlbum.model;

import java.util.List;



public interface AdoPetAlbumInterface {

	public void insert(AdoPetAlbumVO adoPetAlbumVO);
	public void delete(String adoPicNo);

	public List<String> getPicNoByAdoPetNo(String adoPetNo);
	public AdoPetAlbumVO getPic(AdoPetAlbumVO adoPetAlbumVO);
	public List<AdoPetAlbumVO> getPicList(String adoPetNo);
}
