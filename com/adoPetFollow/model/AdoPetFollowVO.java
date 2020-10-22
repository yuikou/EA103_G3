package com.adoPetFollow.model;

import java.io.Serializable;

public class AdoPetFollowVO implements Serializable {

	private String memNo;
	private String adoPetNo;
	
	
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public String getAdoPetNo() {
		return adoPetNo;
	}
	public void setAdoPetNo(String adoPetNo) {
		this.adoPetNo = adoPetNo;
	}
	
	
	/*用來比較現有頁面寵物是否在追蹤清單中*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adoPetNo == null) ? 0 : adoPetNo.hashCode());
		result = prime * result + ((memNo == null) ? 0 : memNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdoPetFollowVO other = (AdoPetFollowVO) obj;
		if (adoPetNo == null) {
			if (other.adoPetNo != null)
				return false;
		} else if (!adoPetNo.equals(other.adoPetNo))
			return false;
		if (memNo == null) {
			if (other.memNo != null)
				return false;
		} else if (!memNo.equals(other.memNo))
			return false;
		return true;
	}
	
	
}
