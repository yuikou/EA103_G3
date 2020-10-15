package com.adoReservation.model;

import java.io.Serializable;
import java.sql.Date;

public class AdoReservationVO implements Serializable{

	
	private String reservationNO;
	private String adoPetNo;
	private String memNo;
	private Date visitDate;
	private Integer reservationStatus;
	
	
	public String getReservationNO() {
		return reservationNO;
	}
	public void setReservationNO(String reservationNO) {
		this.reservationNO = reservationNO;
	}
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
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public Integer getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(Integer reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	
}
