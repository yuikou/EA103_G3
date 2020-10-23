package com.member.model;

import java.sql.Connection;
import java.util.*;

import com.transDetail.model.TransDetailVO;

public interface MemDAO_interface {
	public void insert(MemVO memVO); // ���U�s�|��
	public void update(MemVO memVO); // �|���ק���
	public void delete(String memNo); // �|�����v
	public void upgradeAuth(String memNo); // �ɯŦ��i��
	public void updatePsw(String memPsw, String memNo); // �|���ק�K�X
	public MemVO login(String memId, String memPsw); // �n�J
	public List<MemVO> getAll(); // �C�X�Ҧ��|��
	public MemVO findByPrimaryKey(String memNo); // �M���@�|��
	public boolean isDup(String memId);
	public void deposit(TransDetailVO tsVO, Integer memPoint, java.sql.Connection con); // �x��
	public void transaction(String memNo, Integer memPoint, Integer bill, Connection con);
}
