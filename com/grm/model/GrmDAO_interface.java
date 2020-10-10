package com.grm.model;

import java.util.List;

public interface GrmDAO_interface {
	public void insert(GrmVO grmvo);
	public void update(GrmVO grmvo);
	//刪除美容師==isDel(1), DB未刪除, 只是前端頁面不顯示
	public void delete(String groomerNo);
	public GrmVO findByGroomerNo(String groomerNo);
	//找出一間店的所有美容師
	public List<GrmVO> findByPrimaryKey(String salNo);
}
