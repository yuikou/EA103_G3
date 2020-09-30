package com.member.model;

import java.util.*;

public interface MemDAO_interface {
	public void insert(MemVO memVO); // 註冊新會員
	public void update(MemVO memVO); // 會員修改資料
	public void delete(String memNo); // 會員停權
	public void upgradeAuth(String memNo); // 升級托養者
	public void updatePsw(String memPsw, String memNo); // 會員修改密碼
	public MemVO login(String memId, String memPsw); // 登入
	public List<MemVO> getAll(); // 列出所有會員
	public MemVO findByPrimaryKey(String memNo); // 尋找單一會員
}
