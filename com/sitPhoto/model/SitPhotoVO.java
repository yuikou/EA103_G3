package com.sitPhoto.model;

import java.io.Serializable;

public class SitPhotoVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String sitPNo;
	private String sitNo;
	private byte[] sitPhoto;
	
	public SitPhotoVO() {
		super();
	}

	public SitPhotoVO(String sitPNo, String sitNo, byte[] sitPhoto) {
		super();
		this.sitPNo = sitPNo;
		this.sitNo = sitNo;
		this.sitPhoto = sitPhoto;
	}

	public String getSitPNo() {
		return sitPNo;
	}

	public void setSitPNo(String sitPNo) {
		this.sitPNo = sitPNo;
	}

	public String getSitNo() {
		return sitNo;
	}

	public void setSitNo(String sitNo) {
		this.sitNo = sitNo;
	}

	public byte[] getSitPhoto() {
		return sitPhoto;
	}

	public void setSitPhoto(byte[] sitPhoto) {
		this.sitPhoto = sitPhoto;
	}
}
