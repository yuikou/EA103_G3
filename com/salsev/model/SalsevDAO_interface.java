package com.salsev.model;

import java.util.*;

public interface SalsevDAO_interface {
	 public void insert(SalsevVO salsevVO);
     public void update(SalsevVO salsevVO);
     public void delete(String salsevno);
     //��X��@�A�ȶ���
     public SalsevVO findByPrimaryKey(String salsevno);
     //�@�����e��show�X�Ҧ��A�ȶ���
     public List<SalsevVO> getAll(String salno);
}
