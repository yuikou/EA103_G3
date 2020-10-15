package com.member.model;

import java.util.List;

public class MemService {
	
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemDAO();
	}
	
	public MemVO addMem(String memName, java.sql.Date memBirth, Integer memSex, String memPhone, String memEmail, String memAddress, String memId, String memPsw, String memNickname, byte[] memPhoto) {
		MemVO memVO = new MemVO();
		memVO.setMemName(memName);
		memVO.setMemBirth(memBirth);
		memVO.setMemSex(memSex);
		memVO.setMemPhone(memPhone);
		memVO.setMemEmail(memEmail);
		memVO.setMemAddress(memAddress);
		memVO.setMemId(memId);
		memVO.setMemPsw(memPsw);
		memVO.setMemNickname(memNickname);
		memVO.setMemPhoto(memPhoto);
		dao.insert(memVO);
		
		return memVO;
	}
	
	public MemVO updateMem(String memName, java.sql.Date memBirth, Integer memSex, String memPhone, String memEmail, String memAddress, String memNickname, byte[] memPhoto, String memNo) {
		MemVO memVO = new MemVO();
		
		memVO.setMemName(memName);
		memVO.setMemBirth(memBirth);
		memVO.setMemSex(memSex);
		memVO.setMemPhone(memPhone);
		memVO.setMemEmail(memEmail);
		memVO.setMemAddress(memAddress);
		memVO.setMemNickname(memNickname);
		memVO.setMemPhoto(memPhoto);
		memVO.setMemNo(memNo);
		dao.update(memVO);
		
		return memVO;
	}
	
	public void deleteMem(String memNo) {
		dao.delete(memNo);
	}
	
	public void upgradeMemToSit(String memNo) {
		dao.upgradeAuth(memNo);
	}
	
	public void changeMemPsw(String memPsw, String memNo) {
		dao.updatePsw(memPsw, memNo);
	}
	
	public MemVO getOneMem(String memNo) {
		return dao.findByPrimaryKey(memNo);
	}
	
	public List<MemVO> getAll(){
		return dao.getAll();
	}
	
	public MemVO login(String memId, String memPsw) {
		return dao.login(memId, memPsw);
	}
}
