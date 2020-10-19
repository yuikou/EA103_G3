package com.sitOrder.model;

import java.util.List;
import java.util.Set;

import com.sitODetail.model.SitODetailVO;

public interface SitOrderDAO_interface {
	
	public void insert(SitOrderVO sitOrderVO);
	public void update(SitOrderVO sitOrderNo);
	public void update_orderStatus(SitOrderVO sitOrderVO); // 更新訂單狀態
	public void update_sitCommStar(SitOrderVO sitOrderVO); // 更新評分及評價
	public SitOrderVO getByPK(String sitOrderNo);
	public Set<SitOrderVO> getByFK_memNo(String memNo);
	public Set<SitOrderVO> getByFK_sitNo(String sitNo);
	public List<SitOrderVO> getAll();
	
	public void insertWithODetail(SitOrderVO sitOrderVO , List<SitODetailVO> list);	
}
