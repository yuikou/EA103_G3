package com.petSitter.model;

import java.io.Serializable;

public class PetSitterVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sitNo;
	private String memNo;
	private String sitInfo;
	private String srvSTime;
	private String srvETime;
	private String bankCode;
	private String bankAcc;
	private Integer sitAccStatus;
	private Integer totalComm;
	private Integer totalCus;
	private Integer repeatCus;
	
	public PetSitterVO() {
		super();
	}	

	public PetSitterVO(String sitNo, String memNo, String sitInfo, String srvSTime, String srvETime, String bankCode,
			String bankAcc, Integer sitAccStatus, Integer totalComm, Integer totalCus, Integer repeatCus) {
		super();
		this.sitNo = sitNo;
		this.memNo = memNo;
		this.sitInfo = sitInfo;
		this.srvSTime = srvSTime;
		this.srvETime = srvETime;
		this.bankCode = bankCode;
		this.bankAcc = bankAcc;
		this.sitAccStatus = sitAccStatus;
		this.totalComm = totalComm;
		this.totalCus = totalCus;
		this.repeatCus = repeatCus;
	}



	public String getSitNo() {
		return sitNo;
	}

	public void setSitNo(String sitNo) {
		this.sitNo = sitNo;
	}

	public String getMemNo() {
		return memNo;
	}

	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}

	public String getSitInfo() {
		return sitInfo;
	}

	public void setSitInfo(String sitInfo) {
		this.sitInfo = sitInfo;
	}

	public String getSrvSTime() {
		return srvSTime;
	}

	public void setSrvSTime(String srvSTime) {
		this.srvSTime = srvSTime;
	}

	public String getSrvETime() {
		return srvETime;
	}

	public void setSrvETime(String srvETime) {
		this.srvETime = srvETime;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public Integer getSitAccStatus() {
		return sitAccStatus;
	}


	public void setSitAccStatus(Integer sitAccStatus) {
		this.sitAccStatus = sitAccStatus;
	}


	public Integer getTotalComm() {
		return totalComm;
	}


	public void setTotalComm(Integer totalComm) {
		this.totalComm = totalComm;
	}


	public Integer getTotalCus() {
		return totalCus;
	}


	public void setTotalCus(Integer totalCus) {
		this.totalCus = totalCus;
	}

	public Integer getRepeatCus() {
		return repeatCus;
	}

	public void setRepeatCus(Integer repeatCus) {
		this.repeatCus = repeatCus;
	}	
}
