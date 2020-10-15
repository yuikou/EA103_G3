package com.salonF.model;

import java.util.*;

public interface SalonFDAO_interface {

	public void insert(SalonFVO sfVO);
	public void delete(String memNo, String salNo);
	//會員找出自己所有follow的美容店
	public List<SalonFVO> findBymemKey(String memNo);
}
