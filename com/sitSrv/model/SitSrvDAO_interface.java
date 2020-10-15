package com.sitSrv.model;

import java.util.List;

public interface SitSrvDAO_interface {
	
	Boolean add(SitSrvVO sitSrv);
	Boolean update(SitSrvVO sitSrv);
	Boolean del(String sitSrvNo);
	SitSrvVO 		get_OneSit_OneSrv(String sitSrvNo);
	List<SitSrvVO>	get_OneSit_AllSrv(String sitNo);
	List<SitSrvVO> 	choose_SitSrv(String sitSrvCode, Integer acpPetNum, Integer[] acpPetTyp, String appendSQL);
	
}
