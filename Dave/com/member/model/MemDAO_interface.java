package com.member.model;

import java.util.*;

public interface MemDAO_interface {
	public void insert(MemVO memVO); // ���U�s�|��
	public void update(MemVO memVO); // �|���ק���
	public void delete(String memNo); // �|�����v
	public void upgradeAuth(String memNo); // �ɯŦ��i��
	public void updatePsw(String memPsw, String memNo); // �|���ק�K�X
	public MemVO login(String memId, String memPsw); // �n�J
	public List<MemVO> getAll(); // �C�X�Ҧ��|��
	public MemVO findByPrimaryKey(String memNo); // �M���@�|��
}
