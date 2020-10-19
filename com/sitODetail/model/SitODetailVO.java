package com.sitODetail.model;

import java.io.Serializable;

public class SitODetailVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String sitOrderNo;
	private String sitSrvNo;
	private String petNo;
	private Integer sitOpPrice;
	private Integer sitSrvTimes;
	private String sitSrvUnit;

	public SitODetailVO() {
		super();
	}

	public SitODetailVO(String sitOrderNo, String sitSrvNo, String petNo, Integer sitOpPrice, Integer sitSrvTimes,
			String sitSrvUnit) {
		super();
		this.sitOrderNo = sitOrderNo;
		this.sitSrvNo = sitSrvNo;
		this.petNo = petNo;
		this.sitOpPrice = sitOpPrice;
		this.sitSrvTimes = sitSrvTimes;
		this.sitSrvUnit = sitSrvUnit;
	}


	public String getSitOrderNo() {
		return sitOrderNo;
	}

	public void setSitOrderNo(String sitOrderNo) {
		this.sitOrderNo = sitOrderNo;
	}

	public String getSitSrvNo() {
		return sitSrvNo;
	}

	public void setSitSrvNo(String sitSrvNo) {
		this.sitSrvNo = sitSrvNo;
	}

	public String getPetNo() {
		return petNo;
	}

	public void setPetNo(String petNo) {
		this.petNo = petNo;
	}

	public Integer getSitOpPrice() {
		return sitOpPrice;
	}

	public void setSitOpPrice(Integer sitOpPrice) {
		this.sitOpPrice = sitOpPrice;
	}

	public Integer getSitSrvTimes() {
		return sitSrvTimes;
	}

	public void setSitSrvTimes(Integer sitSrvTimes) {
		this.sitSrvTimes = sitSrvTimes;
	}

	public String getSitSrvUnit() {
		return sitSrvUnit;
	}

	public void setSitSrvUnit(String sitSrvUnit) {
		this.sitSrvUnit = sitSrvUnit;
	}	
	
}
