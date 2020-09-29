package com.sitLic.model;

import java.util.List;

public interface SitLicDAO_interface {
	// 此介面定義了對資料庫的資料存取抽象方法
	void add(SitLicVO sitLic);
	void update(SitLicVO sitLic);
	SitLicVO getOneLicByPK(String licNo);
	List<SitLicVO> getSitAllLic(String sitNo);
	List<SitLicVO> getAllForEmp(Integer licStatus);

}
