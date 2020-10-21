package com.sitLic.model;

import java.sql.Date;
import java.util.List;

public class SitLicService {
	private SitLicDAO_interface slDAO;

	public SitLicService() {
		slDAO = new SitLicDAO();
	}

	public void add(String sitNo, String licName, byte[] licPic, Date licEXP, Integer licStatus) {
		SitLicVO sitLic = new SitLicVO();

		sitLic.setSitNo(sitNo);
		sitLic.setLicName(licName);
		sitLic.setLicPic(licPic);
		sitLic.setLicEXP(licEXP);
		sitLic.setLicStatus(licStatus);
		slDAO.add(sitLic);
	}
	
	public SitLicVO addFromSitSrv(String sitNo, String licName, byte[] licPic, Date licEXP, Integer licStatus) {
		SitLicVO sitLic = new SitLicVO();

		sitLic.setSitNo(sitNo);
		sitLic.setLicName(licName);
		sitLic.setLicPic(licPic);
		sitLic.setLicEXP(licEXP);
		sitLic.setLicStatus(licStatus);
		return sitLic;
	}

	public void update(String licNo, String licName, byte[] licPic, Date licEXP, Integer licStatus, String sitSrvNo) {
		SitLicVO sitLic = new SitLicVO();

		sitLic.setLicNo(licNo);
		sitLic.setLicName(licName);
		sitLic.setLicPic(licPic);
		sitLic.setLicEXP(licEXP);
		sitLic.setLicStatus(licStatus);
		slDAO.update(sitLic, sitSrvNo);
	}
	
	public Boolean updateStatus(String licNo, Integer licStatus, String sitSrvNo) {
		return slDAO.updateStatus(licNo, licStatus, sitSrvNo);
	}

	public SitLicVO getOneLicByPK(String licNo) {
		return slDAO.getOneLicByPK(licNo);
	}

	public List<SitLicVO> getSitAllLic (String sitNo) {
		return slDAO.getSitAllLic(sitNo);
	}
	
	public List<SitLicVO> getUnverifiedLic (Integer licStatus) {
		return slDAO.getAllForEmp(licStatus);
	}
}
