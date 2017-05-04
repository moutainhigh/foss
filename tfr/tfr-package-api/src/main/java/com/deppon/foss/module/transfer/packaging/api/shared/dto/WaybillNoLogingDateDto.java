/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.dto
 * FILE    NAME: WaybillNoLogingDateDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.dto;

import java.util.Date;

/**
 * @author 046130-foss-xuduowei
 * 
 */
public class WaybillNoLogingDateDto {
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 登入或登出时间
	 */
	private Date logingDate;
	/**
	 * 登入或登出(in登入，out登出)
	 */
	private String type;
	
	/**
	 * 修改时间
	 */
	private Date modifyDate;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Date getLogingDate() {
		return logingDate;
	}

	public void setLogingDate(Date logingDate) {
		this.logingDate = logingDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
