package com.sitOffDay.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class SitOffDayService {
	private SitOffDayDAO_interface sodDAO;
	
	public SitOffDayService() {
		sodDAO = new SitOffDayDAO();
	}
	
	public Boolean commit(Boolean addOK) {
		return sodDAO.commit(addOK);
	}
	
	public Boolean add (String sitSrvNo, Date offDay, String offTime, Integer offDayTyp, String groupID) {
		SitOffDayVO sod = new SitOffDayVO();
//		System.out.println(sitSrvNo+";"+offDay+";"+offTime+";"+offDayTyp);
		sod.setSitSrvNo(sitSrvNo);
		sod.setOffDay(offDay);
		sod.setOffTime(offTime);
		sod.setOffDayTyp(offDayTyp);
		sod.setGroupID(groupID);
		
		return sodDAO.add(sod);
	}
	
	public Boolean del (String groupId) {
		return sodDAO.del(groupId);
	}
	
	public SitOffDayVO getByPK(String offDayNo) {
		return sodDAO.getByPK(offDayNo);
	}
	
	public List<SitOffDayVO> getByFK(String sitSrvNo) {
		return sodDAO.getByFK(sitSrvNo);
	}
	
	public Set<String> getSitByDate (String sitSrvCode, String start_date, String end_date, String time) {
		return sodDAO.getSitByDate(sitSrvCode, start_date, end_date, time);
	}
}
