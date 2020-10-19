package com.sitOrder.model;

import java.io.Serializable;
import java.sql.Date;

public class SitOrderVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sitOrderNo;
	private String memNo;
	private String sitNo;
	private Date sitSDate;
	private Date sitEDate;
	private String sitOTime;
	private Integer totalPrice;
	private Integer orderStatus;
	private Integer refund;
	private Integer coupon;
	private Integer commStar;
	private String sitComm;
	private String pickupFrom;
	private String pickupTo;
	
	public SitOrderVO() {
		super();
	}

	public SitOrderVO(String sitOrderNo, String memNo, String sitNo, Date sitSDate, Date sitEDate, String sitOTime,
			Integer totalPrice, Integer orderStatus, Integer refund, Integer coupon, Integer commStar, String sitComm,
			String pickupFrom, String pickupTo) {
		super();
		this.sitOrderNo = sitOrderNo;
		this.memNo = memNo;
		this.sitNo = sitNo;
		this.sitSDate = sitSDate;
		this.sitEDate = sitEDate;
		this.sitOTime = sitOTime;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.refund = refund;
		this.coupon = coupon;
		this.commStar = commStar;
		this.sitComm = sitComm;
		this.pickupFrom = pickupFrom;
		this.pickupTo = pickupTo;
	}

	public String getSitOrderNo() {
		return sitOrderNo;
	}

	public void setSitOrderNo(String sitOrderNo) {
		this.sitOrderNo = sitOrderNo;
	}

	public String getMemNo() {
		return memNo;
	}

	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}

	public String getSitNo() {
		return sitNo;
	}

	public void setSitNo(String sitNo) {
		this.sitNo = sitNo;
	}

	public Date getSitSDate() {
		return sitSDate;
	}

	public void setSitSDate(Date sitSDate) {
		this.sitSDate = sitSDate;
	}

	public Date getSitEDate() {
		return sitEDate;
	}

	public void setSitEDate(Date sitEDate) {
		this.sitEDate = sitEDate;
	}

	public String getSitOTime() {
		return sitOTime;
	}

	public void setSitOTime(String sitOTime) {
		this.sitOTime = sitOTime;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
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

	public Integer getCommStar() {
		return commStar;
	}

	public void setCommStar(Integer commStar) {
		this.commStar = commStar;
	}

	public String getSitComm() {
		return sitComm;
	}

	public void setSitComm(String sitComm) {
		this.sitComm = sitComm;
	}

	public String getPickupFrom() {
		return pickupFrom;
	}

	public void setPickupFrom(String pickupFrom) {
		this.pickupFrom = pickupFrom;
	}

	public String getPickupTo() {
		return pickupTo;
	}

	public void setPickupTo(String pickupTo) {
		this.pickupTo = pickupTo;
	}
	
}
