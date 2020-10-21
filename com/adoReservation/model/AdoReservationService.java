package com.adoReservation.model;

import java.sql.Date;
import java.util.List;

public class AdoReservationService {

	private AdoReservationDAO_interface dao;

	public AdoReservationService() {
		dao = new AdoReservationDAO();
	}

	public String adoReservationInsert(String adoPetNo, String memNo, Date visitDate) {

		AdoReservationVO adoReservationVO = new AdoReservationVO();
		adoReservationVO.setAdoPetNo(adoPetNo);
		adoReservationVO.setMemNo(memNo);
		adoReservationVO.setVisitDate(visitDate);
		String reservationNO = dao.insert(adoReservationVO);

		return reservationNO;
	}

	public AdoReservationVO adoReservationUpdate(Date visitDate, Integer reservationStatus, String reservationNO) {

		AdoReservationVO adoReservationVO = new AdoReservationVO();
		adoReservationVO.setVisitDate(visitDate);
		adoReservationVO.setReservationStatus(reservationStatus);
		adoReservationVO.setReservationNO(reservationNO);
		dao.update(adoReservationVO);

		return adoReservationVO;
	}

	public void delete(Integer reservationStatus, String reservationNO) {
		dao.delete(reservationStatus, reservationNO);

	}

	public List<AdoReservationVO> findByStatus(Integer reservationStatus, String memNo) {
		return dao.findByStatus(reservationStatus, memNo);
	}

	public List<AdoReservationVO> getAll(String memNo) {
		return dao.getAll(memNo);
	}

	public AdoReservationVO getOneRes(String reservationNO) {
		return dao.getOneRes(reservationNO);
	}

	public List<AdoReservationVO> getAllResByPetno(String adoPetNo) {
		return dao.getAllResByPetno(adoPetNo);
	}

	public void resSchedule() {
		dao.resSchedule();
	}
}
