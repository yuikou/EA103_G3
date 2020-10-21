package com.sitLic.model;

import java.sql.Connection;
import java.util.List;

public interface SitLicDAO_interface {
	// �������w�q�F���Ʈw����Ʀs����H��k
	void add(SitLicVO sitLic);
	void update(SitLicVO sitLic, String sitSrvNo);
	Boolean updateStatus(String licNo, Integer licStatus, String sitSrvNo);
	SitLicVO getOneLicByPK(String licNo);
	List<SitLicVO> getSitAllLic(String sitNo);
	List<SitLicVO> getAllForEmp(Integer licStatus);
	void addFromSitSrv(SitLicVO sitLic, Connection con);
}
