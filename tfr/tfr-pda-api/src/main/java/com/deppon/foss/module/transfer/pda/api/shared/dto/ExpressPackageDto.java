/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: ExpressPackageDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

/**
 * 快递包Dto
 * @author dp-duyi
 * @date 2013-7-22 上午9:05:11
 */
public class ExpressPackageDto implements Serializable{
	private static final long serialVersionUID = -6098620276299468695L;
	/**状态*/
	private String status;
	/**包号*/
	private String packageNo;
	/**到达部门*/
	private String arriveOrgCode;
	/**到达部门*/
	private String arriveOrgName;
	/**用户*/
	private String userCode;
	/**包类型*/
	private String expressPackageType;
	/**是否直达包*/
	private String isThrough;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}
	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getArriveOrgName() {
		return arriveOrgName;
	}
	public void setArriveOrgName(String arriveOrgName) {
		this.arriveOrgName = arriveOrgName;
	}
	public String getExpressPackageType() {
		return expressPackageType;
	}
	public void setExpressPackageType(String expressPackageType) {
		this.expressPackageType = expressPackageType;
	}
	public String getIsThrough() {
		return isThrough;
	}
	public void setIsThrough(String isThrough) {
		this.isThrough = isThrough;
	}
	
}
