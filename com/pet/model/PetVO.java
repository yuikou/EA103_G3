package com.pet.model;

import java.sql.Date;

public class PetVO {
	private String petNo;
	private String memNo;
	private String petName;
	private Integer petType;
	private Integer petCat;
	private Integer petSex;
	private Date petBirth;
	private String petChar;
	private byte[] petPhoto;
	private Integer isDel;
	
	public String getPetNo() {
		return petNo;
	}
	public void setPetNo(String petNo) {
		this.petNo = petNo;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public Integer getPetType() {
		return petType;
	}
	public void setPetType(Integer petType) {
		this.petType = petType;
	}
	public Integer getPetCat() {
		return petCat;
	}
	public void setPetCat(Integer petCat) {
		this.petCat = petCat;
	}
	public Integer getPetSex() {
		return petSex;
	}
	public void setPetSex(Integer petSex) {
		this.petSex = petSex;
	}
	public Date getPetBirth() {
		return petBirth;
	}
	public void setPetBirth(Date petBirth) {
		this.petBirth = petBirth;
	}
	public String getPetChar() {
		return petChar;
	}
	public void setPetChar(String petChar) {
		this.petChar = petChar;
	}
	public byte[] getPetPhoto() {
		return petPhoto;
	}
	public void setPetPhoto(byte[] petPhoto) {
		this.petPhoto = petPhoto;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}	
}
