package com.adoReservation.model;

import java.sql.Date;
import java.util.List;

public class AdoReservationService {

	private AdoReservationDAO_interface dao = null;

	AdoReservationService() {
		dao = new AdoReservationDAO();
	}

	public AdoReservationVO AdoReservationInsert(String adoPetNo, String memNo, Date visitDate) {

		AdoReservationVO adoReservationVO = new AdoReservationVO();
		adoReservationVO.setAdoPetNo(adoPetNo);
		adoReservationVO.setMemNo(memNo);
		adoReservationVO.setVisitDate(visitDate);
		dao.insert(adoReservationVO);

		return adoReservationVO;
	}

	public AdoReservationVO AdoReservationUpdate(Date visitDate, Integer reservationStatus, String reservationNO) {

		AdoReservationVO adoReservationVO = new AdoReservationVO();
		adoReservationVO.setVisitDate(visitDate);
		adoReservationVO.setReservationStatus(reservationStatus);
		adoReservationVO.setReservationNO(reservationNO);
		dao.update(adoReservationVO);

		return adoReservationVO;
	}

	public List<AdoReservationVO> findByStatus(Integer reservationStatus, String memNo) {
		return dao.findByStatus(reservationStatus, memNo);
	}

	public List<AdoReservationVO> getAll(String memNo) {
		return dao.getAll(memNo);
	}
}
