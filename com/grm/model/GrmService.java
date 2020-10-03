package com.grm.model;

import java.util.List;

public class GrmService {
	
	private GrmDAO_interface grmDAO;
			
	public GrmService() {
		grmDAO = new GrmDAO();
	}

	public GrmVO addGrmVO(String salNo, String groomerName, String groomerInfo, byte[] groomerPic, Integer isDelete) {
		
		GrmVO grmvo = new GrmVO();
		grmvo.setSalNo(salNo);
		grmvo.setGroomerName(groomerName);
		grmvo.setGroomerInfo(groomerInfo);
		grmvo.setGroomerPic(groomerPic);
		grmvo.setIsDelete(isDelete);
		
		grmDAO.insert(grmvo);
		return grmvo;
	}
	
	public GrmVO updateGrmVO(String groomerNo, String groomerName, String groomerInfo, byte[] groomerPic, Integer isDelete) {
		
		GrmVO grmvo = new GrmVO();
		grmvo.setGroomerNo(groomerNo);
		grmvo.setGroomerName(groomerName);
		grmvo.setGroomerInfo(groomerInfo);
		grmvo.setGroomerPic(groomerPic);
		grmvo.setIsDelete(isDelete);
		
		grmDAO.update(grmvo);
		return grmvo;
	}
	
	public void deleteGrmVO(String groomerNo) {
		grmDAO.delete(groomerNo);
	}
	
	public List<GrmVO> getGrm(String salNo) {
		return grmDAO.findByPrimaryKey(salNo);
	}
	
	public GrmVO getOneGrm(String groomerNo) {
		return grmDAO.findByGroomerNo(groomerNo);
	}
}
