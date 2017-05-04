package com.deppon.foss.module.transfer.common.api.shared.domain;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * 约车编号实体
 * @date 2014-12-18上午8:23:36
 * @author 084853
 */
@XmlRootElement(name="OrderVehicleRequestEntity")
public class OrderVehicleRequestEntity {

	//约车编号
	private String orderNumber;
	//操作类型
	private String operateType;
	//约车类型
	private String orderType;
	//269701--foss--20151021-begin
	/** 申请网点名称[用车部门] */
	private String applyOrgName;
	
	/** 申请网点编码[用车部门] */
	private String applyOrgCode;
	//269701--foss--20151021-end
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * 申请网点名称[用车部门]
	 * @return the applyOrgName
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	/**
	 * 申请网点名称[用车部门]
	 * @param applyOrgName the applyOrgName to set
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	/**
	 * 申请网点编码[用车部门]
	 * @return the applyOrgCode
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	/**
	 * 申请网点编码[用车部门]
	 * @param applyOrgCode the applyOrgCode to set
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	
}
