package com.eventRep.model;

import java.util.List;

public class EventRepService {
	private EventRepDAO_interface evRepDAO;
	
	public EventRepService() {
		evRepDAO = new EventRepDAO();
	}
	
	public Boolean add(String memNo, String eventNo, String eventRep) {
		EventRepVO eventRepVO = new EventRepVO();
		eventRepVO.setMemNo(memNo);
		eventRepVO.setEventNo(eventNo);
		eventRepVO.setEventRep(eventRep);
		eventRepVO.setEventRepResult(0);
		return evRepDAO.add(eventRepVO);
	}
	
	public Boolean update(String eventRepNo, Integer eventRepStatus, Integer eventRepResult) {
		EventRepVO eventRepVO = new EventRepVO();
		eventRepVO.setEventRepStatus(eventRepStatus);
		eventRepVO.setEventRepResult(eventRepResult);
		eventRepVO.setEventRepNo(eventRepNo);
		return evRepDAO.update(eventRepVO);
	}
	
	public EventRepVO getOne(String eventRepNo) {
		return evRepDAO.getOne(eventRepNo);
	}
	
	public List<EventRepVO> getAll(Integer eventRepStatus, Integer eventRepResult) {
		return evRepDAO.getAll(eventRepStatus, eventRepResult);
	}
}
