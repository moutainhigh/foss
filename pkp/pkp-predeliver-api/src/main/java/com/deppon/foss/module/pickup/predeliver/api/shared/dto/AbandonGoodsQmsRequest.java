package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

public class AbandonGoodsQmsRequest implements Serializable{
	
	/**
	 * 类的序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 当前登录人工号
	 */
	private String currentEmpCode;
	
	/**
	 * 当前登录部门(qms传过来的是标杆编码)
	 */
	private String currentDeptCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getCurrentEmpCode() {
		return currentEmpCode;
	}

	public void setCurrentEmpCode(String currentEmpCode) {
		this.currentEmpCode = currentEmpCode;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	
	

}
