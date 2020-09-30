package com.sitOffDay.model;

import java.sql.Date;

public class SitOffDayVO {
	private String offDayNo;
	private String sitSrvNo;
	private Date offDay;
	private String offTime;
	private Integer offDayTyp;
	
	public String getOffDayNo() {
		return offDayNo;
	}
	public void setOffDayNo(String offDayNo) {
		this.offDayNo = offDayNo;
	}
	public String getSitSrvNo() {
		return sitSrvNo;
	}
	public void setSitSrvNo(String sitSrvNo) {
		this.sitSrvNo = sitSrvNo;
	}
	public Date getOffDay() {
		return offDay;
	}
	public void setOffDay(Date offDay) {
		this.offDay = offDay;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public Integer getOffDayTyp() {
		return offDayTyp;
	}
	public void setOffDayTyp(Integer offDayTyp) {
		this.offDayTyp = offDayTyp;
	}
	
	
}
