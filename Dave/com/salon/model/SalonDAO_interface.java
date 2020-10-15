package com.salon.model;

import java.util.List;

public interface SalonDAO_interface {
	void insert(SalonVO salonVO); // �[�J�|��
	void update(SalonVO salonVO); // �|���ۤv�����
	void delete(String salNo); // �q��Ʈw�����R���|��
	void updateStatus(String salNo,Integer salStatus);
	SalonVO findByPrimaryKey(String salNo);
	List<SalonVO> getall();
	SalonVO login(String salAc, String salPw);
	boolean checkAc(String salAc);
	

}
