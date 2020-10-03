package com.grm.model;

import java.util.List;

public interface GrmDAO_interface {
	public void insert(GrmVO grmvo);
	public void update(GrmVO grmvo);
	//�R�����e�v==isDel(1), DB���R��, �u�O�e�ݭ��������
	public void delete(String groomerNo);
	public GrmVO findByGroomerNo(String groomerNo);
	//��X�@�������Ҧ����e�v
	public List<GrmVO> findByPrimaryKey(String salNo);
}
