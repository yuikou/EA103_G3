package com.goffday.model;

public class GODayVO implements java.io.Serializable{
	private String offNo;
	private String groomerNo;
	private String offDay;
	private String offTime;
	private Integer offDayType;
	
	public String getOffNo() {
		return offNo;
	}
	public void setOffNo(String offNo) {
		this.offNo = offNo;
	}
	public String getGroomerNo() {
		return groomerNo;
	}
	public void setGroomerNo(String groomerNo) {
		this.groomerNo = groomerNo;
	}
	public String getOffDay() {
		return offDay;
	}
	public void setOffDay(String offDay) {
		this.offDay = offDay;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public Integer getOffDayType() {
		return offDayType;
	}
	public void setOffDayType(Integer offDayType) {
		this.offDayType = offDayType;
	}
	
}
