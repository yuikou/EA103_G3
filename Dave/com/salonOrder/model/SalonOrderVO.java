package com.salonOrder.model;


import java.sql.Timestamp;

public class SalonOrderVO {
	private String salOrderNo;
	private String memNo;
	private String petNo;
	private String salNo;
	private Timestamp salOrderDate;
	private Integer salTp;
	private Integer orderStatus;
	private Integer refund;
	private Integer coupon;
	private Timestamp salOrderCT;
	private Double salStar;
	private String salComment;
	public String getSalOrderNo() {
		return salOrderNo;
	}
	public void setSalOrderNo(String salOrderNo) {
		this.salOrderNo = salOrderNo;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getPetNo() {
		return petNo;
	}
	public void setPetNo(String petNo) {
		this.petNo = petNo;
	}
	public String getSalNo() {
		return salNo;
	}
	public void setSalNo(String salNo) {
		this.salNo = salNo;
	}
	public Timestamp getSalOrderDate() {
		return salOrderDate;
	}
	public void setSalOrderDate(Timestamp salOrderDate) {
		this.salOrderDate = salOrderDate;
	}
	public Integer getSalTp() {
		return salTp;
	}
	public void setSalTp(Integer salTp) {
		this.salTp = salTp;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getRefund() {
		return refund;
	}
	public void setRefund(Integer refund) {
		this.refund = refund;
	}
	public Integer getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	public Timestamp getSalOrderCT() {
		return salOrderCT;
	}
	public void setSalOrderCT(Timestamp salOrderCT) {
		this.salOrderCT = salOrderCT;
	}
	public Double getSalStar() {
		return salStar;
	}
	public void setSalStar(Double salStar) {
		this.salStar = salStar;
	}
	public String getSalComment() {
		return salComment;
	}
	public void setSalComment(String salComment) {
		this.salComment = salComment;
	}
	
	
	

}
