package com.member.model;

import java.sql.Date;

public class MemVO {
	private String memNo;
	private String memName;	
	private Date memBirth;
	private Integer memSex;
	private String memPhone;
	private String memEmail;
	private String memAddress;
	private String memId;
	private String memPsw;
	private Integer memPoint;
	private Integer memAuthority;
	private Integer memStatus;
	private String memNickname;
	private byte[] memPhoto;
	
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemNickname() {
		return memNickname;
	}
	public void setMemNickname(String memNickname) {
		this.memNickname = memNickname;
	}
	public Date getMemBirth() {
		return memBirth;
	}
	public void setMemBirth(Date memBirth) {
		this.memBirth = memBirth;
	}
	public Integer getMemSex() {
		return memSex;
	}
	public void setMemSex(Integer memSex) {
		this.memSex = memSex;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getMemAddress() {
		return memAddress;
	}
	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPsw() {
		return memPsw;
	}
	public void setMemPsw(String memPsw) {
		this.memPsw = memPsw;
	}
	public Integer getMemPoint() {
		return memPoint;
	}
	public void setMemPoint(Integer memPoint) {
		this.memPoint = memPoint;
	}
	public Integer getMemAuthority() {
		return memAuthority;
	}
	public void setMemAuthority(Integer memAuthority) {
		this.memAuthority = memAuthority;
	}
	public Integer getMemStatus() {
		return memStatus;
	}
	public void setMemStatus(Integer memStatus) {
		this.memStatus = memStatus;
	}
	public byte[] getMemPhoto() {
		return memPhoto;
	}
	public void setMemPhoto(byte[] memPhoto) {
		this.memPhoto = memPhoto;
	}
	
	
}
