package com.sitLic.model;

import java.sql.Date;

public class SitLicVO {
	private String licNo;
	private String sitNo;
	private String licName;
	private byte[] licPic;
	private Date licEXP;
	private Integer licStatus;
	
	
	public String getLicNo() {
		return licNo;
	}
	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}
	public String getSitNo() {
		return sitNo;
	}
	public void setSitNo(String sitNo) {
		this.sitNo = sitNo;
	}
	public String getLicName() {
		return licName;
	}
	public void setLicName(String licName) {
		this.licName = licName;
	}
	public byte[] getLicPic() {
		return licPic;
	}
	public void setLicPic(byte[] licPic) {
		this.licPic = licPic;
	}
	public Date getLicEXP() {
		return licEXP;
	}
	public void setLicEXP(Date licEXP) {
		this.licEXP = licEXP;
	}
	public Integer getLicStatus() {
		return licStatus;
	}
	public void setLicStatus(Integer licStatus) {
		this.licStatus = licStatus;
	}
	
	
}
