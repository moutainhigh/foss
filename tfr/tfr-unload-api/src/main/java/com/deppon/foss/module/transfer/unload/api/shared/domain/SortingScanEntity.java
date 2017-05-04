/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.domain
 * FILE    NAME: SortingScanEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;

/**
 * 分拣扫描
 * @author dp-duyi
 * @date 2013-7-26 上午11:15:34
 */
public class SortingScanEntity extends SortingScanDto{

	private static final long serialVersionUID = 3709953628823532065L;
	
	private String operatorName;
	private String orgName;
	private Date createTime;
	private String id;
	private String scanMode;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getScanMode() {
		return scanMode;
	}
	public void setScanMode(String scanMode) {
		this.scanMode = scanMode;
	}
	@Override
	public String toString() {
		return "SortingScanEntity [waybillNo="+this.getWayBillNo()+",operatorName=" + operatorName + ", orgName="
				+ orgName + ", scanMode=" + scanMode + ", createTime=" + createTime + ", id=" + id + "]";
	}
	
}
