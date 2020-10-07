package com.salsev.model;

public class SalsevVO implements java.io.Serializable{
	
	private String salSevNo;
	private String salNo;
	private Integer petCat;
	private String salSevName;
	private String salSevInfo;
	private Integer salSevTime; //服務花費時間
	private Integer salSevPr; //服務價格
	private Integer status; //項目是否刪除
	
	public String getSalsevno() {
		return salSevNo;
	}
	public void setSalsevno(String salsevno) {
		this.salSevNo = salsevno;
	}
	public String getSalno() {
		return salNo;
	}
	public void setSalno(String salno) {
		this.salNo = salno;
	}
	public Integer getPetcat() {
		return petCat;
	}
	public void setPetcat(Integer petcat) {
		this.petCat = petcat;
	}
	public String getSalsevname() {
		return salSevName;
	}
	public void setSalsevname(String salsevname) {
		this.salSevName = salsevname;
	}
	public String getSalSevInfo() {
		return salSevInfo;
	}
	public void setSalSevInfo(String salSevInfo) {
		this.salSevInfo = salSevInfo;
	}
	public Integer getSalsevtime() {
		return salSevTime;
	}
	public void setSalsevtime(Integer salsevtime) {
		this.salSevTime = salsevtime;
	}
	public Integer getSalsevpr() {
		return salSevPr;
	}
	public void setSalsevpr(Integer salsevpr) {
		this.salSevPr = salsevpr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
