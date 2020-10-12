package com.salonAlbum.model;

import java.io.InputStream;
import java.util.List;

public interface SalonAlbDAO_interface {
	public void insert(SalonAlbVO salAVO);
//    public void update(SalonAlbVO salAVO);
    public void delete(String salAlbNo);
    public SalonAlbVO getOnePic(String salAlbNo);
    public List<SalonAlbVO> findBySalonKey(String salno);
}
