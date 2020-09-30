package com.sitOffDay.model;

import java.sql.Date;
import java.util.Set;

public class SitOffDayService {
	private SitOffDayDAO_interface sodDAO;
	
	public SitOffDayService() {
		sodDAO = new SitOffDayDAO();
	}
	
	public Boolean add (String sitSrvNo, Date offDay, String offTime, Integer offDayTyp) {
		SitOffDayVO sod = new SitOffDayVO();
		
		sod.setSitSrvNo(sitSrvNo);
		sod.setOffDay(offDay);
		sod.setOffTime(offTime);
		sod.setOffDayTyp(offDayTyp);
		
		return sodDAO.add(sod);
	}
	
	public Boolean update (String offDayNo, Date offDay, String offTime, Integer offDayTyp) {
		SitOffDayVO sod = new SitOffDayVO();
		
		sod.setOffDayNo(offDayNo);
		sod.setOffDay(offDay);
		sod.setOffTime(offTime);
		sod.setOffDayTyp(offDayTyp);
		
		return sodDAO.update(sod);
	}
	
	public Boolean del (String offDayNo) {
		return sodDAO.del(offDayNo);
	}
	
	public SitOffDayVO getByPK(String offDayNo) {
		return sodDAO.getByPK(offDayNo);
	}
	
	public Set<String> getSitByDate (String sitSrvCode, String start_date, String end_date, String time) {
		return sodDAO.getSitByDate(sitSrvCode, start_date, end_date, time);
	}
}
