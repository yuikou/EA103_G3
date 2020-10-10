package com.sitFollow.model;

import java.util.List;

public class SitFollowService {
	private SitFollowDAO_interface sfDAO;
	
	public SitFollowService() {
		sfDAO = new SitFollowDAO();
	}
	
	public Boolean add(String memNo, String sitNo) {
		return sfDAO.add(memNo, sitNo);
	}
	
	public Boolean del(String memNo, String sitNo) {
		return sfDAO.del(memNo, sitNo);
	}
	
	public List<String> getAllByMemNo(String memNo) {
		return sfDAO.getAllByMemNo(memNo);
	}
}
