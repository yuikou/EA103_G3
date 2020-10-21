package com.sitSrv.model;

import java.util.List;
import java.util.Map;

import com.sitLic.model.SitLicVO;

public class SitSrvService {
	private SitSrvDAO_interface ssDAO;

	public SitSrvService() {
		ssDAO = new SitSrvDAO();
	}

	public Boolean add(String ssName, String ssCode, String sitNo, Integer srvFee, String srvInfo,
					   Integer srvArea, Integer acpPetNum, Integer acpPetTyp, Integer careLevel,
					   Integer stayLoc, Integer overnightLoc, Integer smkFree, Integer hasChild,
					   String srvTime, Integer Eqpt, Integer addBathing, Integer addPicking,
					   Integer addBathingFee, Integer addPickupFee) {
		SitSrvVO ssVO = new SitSrvVO();
		
		ssVO.setSitSrvName(ssName);
		ssVO.setSitSrvCode(ssCode);
		ssVO.setSitNo(sitNo);
		ssVO.setSrvFee(srvFee);
		ssVO.setSrvInfo(srvInfo);
		ssVO.setSrvArea(srvArea);
		ssVO.setAcpPetNum(acpPetNum);
		ssVO.setAcpPetTyp(acpPetTyp);
		ssVO.setCareLevel(careLevel);
		ssVO.setStayLoc(stayLoc);
		ssVO.setOvernightLoc(overnightLoc);
		ssVO.setSmkFree(smkFree);
		ssVO.setHasChild(hasChild);
		ssVO.setSrvTime(srvTime);
		ssVO.setEqpt(Eqpt);
		ssVO.setAddBathing(addBathing);
		ssVO.setAddPickup(addPicking);
		Boolean addOK = ssDAO.add(ssVO, addBathingFee, addPickupFee);
		
		return addOK;
	}

	public Boolean addWithSitLic(String ssName, String ssCode, String sitNo, Integer srvFee, String srvInfo,
					   Integer srvArea, Integer acpPetNum, Integer acpPetTyp, Integer careLevel,
					   Integer stayLoc, Integer overnightLoc, Integer smkFree, Integer hasChild,
					   String srvTime, Integer Eqpt, Integer addBathing, Integer addPicking,
					   Integer addBathingFee, Integer addPickupFee, SitLicVO sitLic) {
		SitSrvVO ssVO = new SitSrvVO();
		
		ssVO.setSitSrvName(ssName);
		ssVO.setSitSrvCode(ssCode);
		ssVO.setSitNo(sitNo);
		ssVO.setSrvFee(srvFee);
		ssVO.setSrvInfo(srvInfo);
		ssVO.setSrvArea(srvArea);
		ssVO.setAcpPetNum(acpPetNum);
		ssVO.setAcpPetTyp(acpPetTyp);
		ssVO.setCareLevel(careLevel);
		ssVO.setStayLoc(stayLoc);
		ssVO.setOvernightLoc(overnightLoc);
		ssVO.setSmkFree(smkFree);
		ssVO.setHasChild(hasChild);
		ssVO.setSrvTime(srvTime);
		ssVO.setEqpt(Eqpt);
		ssVO.setAddBathing(addBathing);
		ssVO.setAddPickup(addPicking);
		Boolean addOK = ssDAO.addWithSitLic(ssVO, addBathingFee, addPickupFee, sitLic);
		
		return addOK;
		
	}
	
	public SitSrvVO update(String sitSrvNo, String ssName, String ssCode, String sitNo, Integer srvFee, String srvInfo,
			   Integer srvArea, Integer acpPetNum, Integer acpPetTyp, Integer careLevel,
			   Integer stayLoc, Integer overnightLoc, Integer smkFree, Integer hasChild,
			   String srvTime, Integer Eqpt, Integer addBathing, Integer addPicking,
			   SitSrvVO sitSrv2, SitSrvVO sitSrv3) {
		SitSrvVO sitSrv = new SitSrvVO();
		
		sitSrv.setSitSrvNo(sitSrvNo);
		sitSrv.setSitSrvName(ssName);
		sitSrv.setSitSrvCode(ssCode);
		sitSrv.setSitNo(sitNo);
		sitSrv.setSrvFee(srvFee);
		sitSrv.setSrvInfo(srvInfo);
		sitSrv.setSrvArea(srvArea);
		sitSrv.setAcpPetNum(acpPetNum);
		sitSrv.setAcpPetTyp(acpPetTyp);
		sitSrv.setCareLevel(careLevel);
		sitSrv.setStayLoc(stayLoc);
		sitSrv.setOvernightLoc(overnightLoc);
		sitSrv.setSmkFree(smkFree);
		sitSrv.setHasChild(hasChild);
		sitSrv.setSrvTime(srvTime);
		sitSrv.setEqpt(Eqpt);
		sitSrv.setAddBathing(addBathing);
		sitSrv.setAddPickup(addPicking);
		ssDAO.update(sitSrv, sitSrv2, sitSrv3);
		
		return sitSrv;
	}

	public Boolean updateStatus(String sitSrvNo, Integer outOfSrv, Integer isDel) {
		return ssDAO.updateStatus(sitSrvNo, outOfSrv, isDel);
	}
	
	public SitSrvVO get_OneSit_OneSrv(String sitSrvNo) {
		return ssDAO.get_OneSit_OneSrv(sitSrvNo);
	}
	
	public List<SitSrvVO> get_OneSit_AllSrv(String sitNo) {
		return ssDAO.get_OneSit_AllSrv(sitNo);
	}
	
	public List<SitSrvVO2> choose_SitSrv(String sitSrvCode, Object[] acpPetTyp, Map<String, String[]> map) {
		return ssDAO.choose_SitSrv(sitSrvCode, acpPetTyp, map);
	}
	
}
