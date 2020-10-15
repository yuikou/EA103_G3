package com.adoReservation.model;

import java.util.List;

public interface AdoReservationDAO_interface {

	public void insert(AdoReservationVO adoReservationVO);  
	
	public void update(AdoReservationVO adoReservationVO);
	public List<AdoReservationVO> findByStatus(Integer reservationStatus, String memNo);
	public List<AdoReservationVO> getAll(String memNo);
	
	
}
