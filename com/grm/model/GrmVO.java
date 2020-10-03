package com.grm.model;

public class GrmVO implements java.io.Serializable{
	private String groomerNo;
	private String salNo;
	private String groomerName;
	private String groomerInfo;
	private Integer isDelete;
	private byte[] groomerPic;
	
	public byte[] getGroomerPic() {
		return groomerPic;
	}
	public void setGroomerPic(byte[] groomerPic) {
		this.groomerPic = groomerPic;
	}
	public String getGroomerNo() {
		return groomerNo;
	}
	public void setGroomerNo(String groomerNo) {
		this.groomerNo = groomerNo;
	}
	public String getSalNo() {
		return salNo;
	}
	public void setSalNo(String salNo) {
		this.salNo = salNo;
	}
	public String getGroomerName() {
		return groomerName;
	}
	public void setGroomerName(String groomerName) {
		this.groomerName = groomerName;
	}
	public String getGroomerInfo() {
		return groomerInfo;
	}
	public void setGroomerInfo(String groomerInfo) {
		this.groomerInfo = groomerInfo;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDel) {
		this.isDelete = isDel;
	}
}
