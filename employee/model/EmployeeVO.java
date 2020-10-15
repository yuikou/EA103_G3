package com.employee.model;

public class EmployeeVO implements java.io.Serializable{
	private String empNo;
	private String eName;
	private String empId;
	private String empPsw;
	private String eNickname;
	private Integer eAccStatus;
	private String empEmail;
	private byte[] empPhoto;
	
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpPsw() {
		return empPsw;
	}
	public void setEmpPsw(String empPsw) {
		this.empPsw = empPsw;
	}
	public String geteNickname() {
		return eNickname;
	}
	public void seteNickname(String eNickname) {
		this.eNickname = eNickname;
	}
	public Integer geteAccStatus() {
		return eAccStatus;
	}
	public void seteAccStatus(Integer eAccStatus) {
		this.eAccStatus = eAccStatus;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public byte[] getEmpPhoto() {
		return empPhoto;
	}
	public void setEmpPhoto(byte[] empPhoto) {
		this.empPhoto = empPhoto;
	}
}
