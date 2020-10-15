package com.adoPet.model;

import java.io.Serializable;
import java.sql.Date;

public class AdoPetVO implements Serializable {

private String adoPetNo;
private String memNo;
private String empNo;
private Integer adoStatus;
private Integer petType;
private String petName;
private String	petBreed;
private Integer	petSex;
private Date petBirth;
private Double petWeight;
private Integer petCat;
private String petChar;
private String location;
private byte[] appForm;

private Double age;

public String getAdoPetNo() {
	return adoPetNo;
}
public void setAdoPetNo(String adoPetNo) {
	this.adoPetNo = adoPetNo;
}
public String getMemNo() {
	return memNo;
}
public void setMemNo(String memNo) {
	this.memNo = memNo;
}
public String getEmpNo() {
	return empNo;
}
public void setEmpNo(String empNo) {
	this.empNo = empNo;
}
public Integer getAdoStatus() {
	return adoStatus;
}
public void setAdoStatus(Integer adoStatus) {
	this.adoStatus = adoStatus;
}
public Integer getPetType() {
	return petType;
}
public void setPetType(Integer petType) {
	this.petType = petType;
}
public String getPetName() {
	return petName;
}
public void setPetName(String petName) {
	this.petName = petName;
}
public String getPetBreed() {
	return petBreed;
}
public void setPetBreed(String petBreed) {
	this.petBreed = petBreed;
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
public Double getPetWeight() {
	return petWeight;
}
public void setPetWeight(Double petWeight) {
	this.petWeight = petWeight;
}
public Integer getPetCat() {
	return petCat;
}
public void setPetCat(Integer petCat) {
	this.petCat = petCat;
}
public String getPetChar() {
	return petChar;
}
public void setPetChar(String petChar) {
	this.petChar = petChar;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public byte[] getAppForm() {
	return appForm;
}
public void setAppForm(byte[] appForm) {
	this.appForm = appForm;
}
public Double getAge() {
	return age;
}
public void setAge(Double age) {
	this.age = age;
}

}
