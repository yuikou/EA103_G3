package com.adoPetAlbum.model;

import java.io.Serializable;

public class AdoPetAlbumVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String adoPicNo;
	private String adoPetNo;
	private byte[] adoPetPic;
	
	
	public String getAdoPicNo() {
		return adoPicNo;
	}
	public void setAdoPicNo(String adoPicNo) {
		this.adoPicNo = adoPicNo;
	}
	public String getAdoPetNo() {
		return adoPetNo;
	}
	public void setAdoPetNo(String adoPetNo) {
		this.adoPetNo = adoPetNo;
	}
	public byte[] getAdoPetPic() {
		return adoPetPic;
	}
	public void setAdoPetPic(byte[] adoPetPic) {
		this.adoPetPic = adoPetPic;
	}
	
	
}
