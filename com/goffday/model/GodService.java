package com.goffday.model;

import java.util.List;

public class GodService {

	private GODayDAO_interface godDAO;
	
	public GodService() {
		godDAO = new GODayDAO();
	}
	
	public GODayVO addGOD(String groomerNo, String offDay, String offTime, Integer offDayType) {
		
		GODayVO godayVO = new GODayVO();
		godayVO.setGroomerNo(groomerNo);
		godayVO.setOffDay(offDay);
		godayVO.setOffTime(offTime);
		godayVO.setOffDayType(offDayType);
	
		godDAO.insert(godayVO);
		
		return godayVO;
	}
	
	public GODayVO updateGOD(String offNo, String groomerNo, String offDay, String offTime, Integer offDayType) {
		
		GODayVO godayVO = new GODayVO();
		godayVO.setOffNo(offNo);
		godayVO.setGroomerNo(groomerNo);
		godayVO.setOffDay(offDay);
		godayVO.setOffTime(offTime);
		godayVO.setOffDayType(offDayType);
	
		godDAO.update(godayVO);
		
		return godayVO;
	}
	public void deleteGOD(String offno) {
		godDAO.delete(offno);
	}
	
	public List<GODayVO> getOneGod(String groomerno) {
		return godDAO.getAll(groomerno);
	}
	
}
