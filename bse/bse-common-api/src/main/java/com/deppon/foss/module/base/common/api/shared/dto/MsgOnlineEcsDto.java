package com.deppon.foss.module.base.common.api.shared.dto;

import java.io.Serializable;
/** 
 * 查询快递交接单
 * @author 273311 
 * @date 2017-3-1 上午9:46:54
 * @since
 * @version
 */

public class MsgOnlineEcsDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6966179000318513315L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 受理部门
	 */
	private String receiveOrgCode;
	/**
	 * 受理部门
	 */
	private String receiveOrgName;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	
	
	
	
	
}
