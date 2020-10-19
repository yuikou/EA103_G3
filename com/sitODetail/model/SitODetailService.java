package com.sitODetail.model;

import java.util.List;

public class SitODetailService {
	
	private SitODetailDAO_interface dao;
	
	public SitODetailService() {
		dao = new SitODetailDAO();
	}
	
	public SitODetailVO insert(String sitOrderNo, String sitSrvNo, String petNo, Integer sitOpPrice, Integer sitSrvTimes,
			String sitSrvUnit) {
		
		SitODetailVO sitODetailVO = new SitODetailVO();
		sitODetailVO.setSitOrderNo(sitOrderNo);
		sitODetailVO.setSitSrvNo(sitSrvNo);
		sitODetailVO.setPetNo(petNo);
		sitODetailVO.setSitOpPrice(sitOpPrice);
		sitODetailVO.setSitSrvTimes(sitSrvTimes);
		sitODetailVO.setSitSrvUnit(sitSrvUnit);
		dao.insert(sitODetailVO);
		
		return sitODetailVO;
	}
	
	public SitODetailVO getByPKFK(String sitOrderNo) {
		return dao.getByPKFK(sitOrderNo);
	}
	
	public List<SitODetailVO> getAll(){
		return dao.getAll();
	}
}
