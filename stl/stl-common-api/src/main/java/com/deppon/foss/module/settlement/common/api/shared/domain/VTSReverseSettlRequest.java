package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;

/**
 * @author 218392 zhangyongxue
 *  2016-06-16 06:42:30 
 *	FOSS结算开发组
 * 自动反签收查询参数实体
 */
public class VTSReverseSettlRequest implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 单号
	 */
	private String waybillNo;

	/**
	 * 当前登陆部门编码
	 */
	private String currentDeptCode;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	
	
	
	
	
}
