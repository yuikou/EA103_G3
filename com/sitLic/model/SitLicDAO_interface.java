package com.sitLic.model;

import java.sql.Connection;
import java.util.List;

public interface SitLicDAO_interface {
	// 此介面定義了對資料庫的資料存取抽象方法
	void add(SitLicVO sitLic);
	void update(SitLicVO sitLic);
	Boolean updateStatus(String licNo, Integer licStatus);
	SitLicVO getOneLicByPK(String licNo);
	List<SitLicVO> getSitAllLic(String sitNo);
	List<SitLicVO> getAllForEmp(Integer licStatus);
	void addFromSitSrv(SitLicVO sitLic, Connection con);
}
