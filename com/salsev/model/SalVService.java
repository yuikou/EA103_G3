package com.salsev.model;

import java.util.List;

public class SalVService {
	
	private SalsevDAO_interface dao;
	
	public SalVService(){
		dao = new SalSevDAO();
	}
	
	public SalsevVO addSalV(String salNo, Integer petCat, String salSevName, String salSevInfo, Integer salSevTime, Integer salSevPr, Integer status) {
		
		SalsevVO salv = new SalsevVO();
		salv.setSalno(salNo);
		salv.setPetcat(petCat);
		salv.setSalsevname(salSevName);
		salv.setSalSevInfo(salSevInfo);
		salv.setSalsevtime(salSevTime);
		salv.setSalsevpr(salSevPr);
		salv.setStatus(status);
		
		dao.insert(salv);
		return salv;
	}
	
	public SalsevVO updateSalV(String salSevNo, Integer petCat, String salSevName, String salSevInfo, Integer salSevTime, Integer salSevPr, Integer status) {
		SalsevVO salv = new SalsevVO();
		salv.setSalsevno(salSevNo);
		salv.setPetcat(petCat);
		salv.setSalsevname(salSevName);
		salv.setSalSevInfo(salSevInfo);
		salv.setSalsevtime(salSevTime);
		salv.setSalsevpr(salSevPr);
		salv.setStatus(status);
		
		dao.update(salv);
		return salv;
	}
	
	public void deleteSalv(String salsevno) {
		dao.delete(salsevno);
	}
	
	public SalsevVO getOneSalv(String salsevno) {
		return dao.findByPrimaryKey(salsevno);
	}
	
	public List<SalsevVO> getAll(String salno){
		return dao.getAll(salno);
	}
}
