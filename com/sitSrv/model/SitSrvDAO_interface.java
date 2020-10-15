package com.sitSrv.model;

import java.util.List;

import com.sitLic.model.SitLicVO;

public interface SitSrvDAO_interface {
	
	Boolean add(SitSrvVO sitSrv, Integer addBathingFee, Integer addPickupFee);
	Boolean addWithSitLic(SitSrvVO sitSrv, Integer addBathingFee, Integer addPickupFee, SitLicVO sitLic);
	Boolean update(SitSrvVO sitSrv);
	Boolean updateStatus(String sitSrvNo, Integer outOfSrv, Integer isDel);
	SitSrvVO 		get_OneSit_OneSrv(String sitSrvNo);
	List<SitSrvVO>	get_OneSit_AllSrv(String sitNo);
	List<SitSrvVO> 	choose_SitSrv(String sitSrvCode, Object[] acpPetTyp, String appendSQL);
//	Boolean addPlus(String sitSrvName, String sitSrvCode, String sitNo, Integer srvFee);
}
