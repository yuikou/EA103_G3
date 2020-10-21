package com.transDetail.model;

import java.sql.*;

public class TransDetailVO {
	private String transNo;
	private String memNo;
	private String sitOrderNo;
	private String salOrderNo;
	private Timestamp transTime;
	private Integer transAmount;
	private Integer transType;
	private Integer depositTpye;
	private Integer tradeStatus;
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getSitOrderNo() {
		return sitOrderNo;
	}
	public void setSitOrderNo(String sitOrderNo) {
		this.sitOrderNo = sitOrderNo;
	}
	public String getSalOrderNo() {
		return salOrderNo;
	}
	public void setSalOrderNo(String salOrderNo) {
		this.salOrderNo = salOrderNo;
	}
	public Timestamp getTransTime() {
		return transTime;
	}
	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}
	public Integer getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Integer transAmount) {
		this.transAmount = transAmount;
	}
	public Integer getTransType() {
		return transType;
	}
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	public Integer getDepositTpye() {
		return depositTpye;
	}
	public void setDepositTpye(Integer depositTpye) {
		this.depositTpye = depositTpye;
	}
	public Integer getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
}
