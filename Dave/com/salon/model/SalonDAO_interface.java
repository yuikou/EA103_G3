package com.salon.model;

import java.util.List;

public interface SalonDAO_interface {
	void insert(SalonVO salonVO); // 加入會員
	void update(SalonVO salonVO); // 會員自己更改資料
	void delete(String salNo); // 從資料庫完全刪除會員
	void updateStatus(String salNo,Integer salStatus);
	SalonVO findByPrimaryKey(String salNo);
	List<SalonVO> getall();
	SalonVO login(String salAc, String salPw);
	boolean checkAc(String salAc);
	

}
