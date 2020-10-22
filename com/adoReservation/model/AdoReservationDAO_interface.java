package com.adoReservation.model;

import java.util.List;

public interface AdoReservationDAO_interface {

	public String insert(AdoReservationVO adoReservationVO);  
	
	public void update(AdoReservationVO adoReservationVO);
	public void delete( Integer reservationStatus,String reservationNO);
	public List<AdoReservationVO> findByStatus(Integer reservationStatus, String memNo);
	public List<AdoReservationVO> getAll(String memNo);
	public AdoReservationVO getOneRes(String reservationNO);
	public List<AdoReservationVO> getAllResByPetno(String adoPetNo);
	public void resSchedule();
	
}
