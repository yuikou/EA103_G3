package com.sitSrv.model;

import java.util.List;

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
	
	public SitSrvVO update(String ssName, String ssCode, String sitNo, Integer srvFee, String srvInfo,
			   Integer srvArea, Integer acpPetNum, Integer acpPetTyp, Integer careLevel,
			   Integer stayLoc, Integer overnightLoc, Integer smkFree, Integer hasChild,
			   String srvTime, Integer Eqpt, Integer addBathing, Integer addPicking,
			   Integer outOfSrv, Integer isDel) {
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
		ssVO.setOutOfSrv(outOfSrv);
		ssVO.setIsDel(isDel);
		ssDAO.update(ssVO);
		
		return ssVO;
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
	
	public List<SitSrvVO> choose_SitSrv(String sitSrvCode, Object[] acpPetTyp, String appendSQL) {
		return ssDAO.choose_SitSrv(sitSrvCode, acpPetTyp, appendSQL);
	}
	
}
