package com.salsev.model;

import java.util.*;

public interface SalsevDAO_interface {
	 public void insert(SalsevVO salsevVO);
     public void update(SalsevVO salsevVO);
     public void delete(String salsevno);
     //找出單一服務項目
     public SalsevVO findByPrimaryKey(String salsevno);
     //一間美容店show出所有服務項目
     public List<SalsevVO> getAll(String salno);
}
