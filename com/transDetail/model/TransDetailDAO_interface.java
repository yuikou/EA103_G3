package com.transDetail.model;

import java.util.*;

import com.member.model.MemVO;

public interface TransDetailDAO_interface {
	public String insert(TransDetailVO transdetailVO);
	public void updatePoint(TransDetailVO tsVO, Integer memPoint);
	public TransDetailVO getOneDetail(String transNo);
	public List<TransDetailVO> getSitOrder(String memNo, String sitOrderNo);
	public List<TransDetailVO> getSalorderNo(String memNo, String salOrderNo);
	public List<TransDetailVO> getByTransTyype(String memNo, Integer transType);
	public List<TransDetailVO> getMemDetail(String memNo);
	public List<TransDetailVO> getAll();
}
