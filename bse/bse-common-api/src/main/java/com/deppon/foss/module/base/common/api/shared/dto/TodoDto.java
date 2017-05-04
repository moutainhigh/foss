package com.deppon.foss.module.base.common.api.shared.dto;

import java.io.Serializable;

public class TodoDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909985235341246944L;

	private String currentDept;
	private String handleOrgCode;
	private String lastLoadOrgCode;
	
	/**
	 * @return the lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	/**
	 * @param lastLoadOrgCode the lastLoadOrgCode to set
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public String getCurrentDept() {
		return currentDept;
	}
	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}
	public String getHandleOrgCode() {
		return handleOrgCode;
	}
	public void setHandleOrgCode(String handleOrgCode) {
		this.handleOrgCode = handleOrgCode;
	}
	
}
