package com.salonF.model;

import java.util.*;

public interface SalonFDAO_interface {

	public void insert(SalonFVO sfVO);
	public void delete(String memNo, String salNo);
	//�|����X�ۤv�Ҧ�follow�����e��
	public List<SalonFVO> findBymemKey(String memNo);
}
