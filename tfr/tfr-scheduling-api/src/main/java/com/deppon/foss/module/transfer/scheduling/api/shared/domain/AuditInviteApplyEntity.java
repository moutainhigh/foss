/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/AuditInviteApplyEntity.java
 * 
 *  FILE NAME     :AuditInviteApplyEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.domain
 * FILE    NAME: AuditInviteApplyEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;
/**
 * 外请车log
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:47:47
 */
public class AuditInviteApplyEntity extends BaseEntity {
	
	private static final long serialVersionUID = 49839582070690992L;
		
	private String inviteNo;
		
	private Date auditTime;
		
	private Integer auditNo;
		
	private String notes;
	
	/** 是否营业部自请车*/
	private String isSaleDepartmentCompany;
	
	/** 拼车类型*/
	private String carpoolingType;
		
	private String auditOrgName;
		
	private String auditOrgCode;
		
	private String auditEmpName;
		
	private String auditEmpCode;
	
	private String status;
		
	/**
	 * 获得inviteNo
	 * @return the inviteNo
	 */
	public String getInviteNo() {
		return inviteNo;
	}
	
	/**
	 * 获得auditTime
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	
	/**
	 * 获得auditNo
	 * @return the auditNo
	 */
	public Integer getAuditNo() {
		return auditNo;
	}
	
	/**
	 * 获得notes
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * 获得auditOrgName
	 * @return the auditOrgName
	 */
	public String getAuditOrgName() {
		return auditOrgName;
	}
	
	/**
	 * 获得auditOrgCode
	 * @return the auditOrgCode
	 */
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	
	/**
	 * 获得auditEmpName
	 * @return the auditEmpName
	 */
	public String getAuditEmpName() {
		return auditEmpName;
	}
	
	/**
	 * 获得auditEmpCode
	 * @return the auditEmpCode
	 */
	public String getAuditEmpCode() {
		return auditEmpCode;
	}
	
		/**
	 * 设置inviteNo
	 * @param inviteNo the inviteNo to set
	 */
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
	
	/**
	 * 设置auditTime
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	/**
	 * 设置auditNo
	 * @param auditNo the auditNo to set
	 */
	public void setAuditNo(Integer auditNo) {
		this.auditNo = auditNo;
	}
	
	/**
	 * 设置notes
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * 设置auditOrgName
	 * @param auditOrgName the auditOrgName to set
	 */
	public void setAuditOrgName(String auditOrgName) {
		this.auditOrgName = auditOrgName;
	}
	
	/**
	 * 设置auditOrgCode
	 * @param auditOrgCode the auditOrgCode to set
	 */
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
	
	/**
	 * 设置auditEmpName
	 * @param auditEmpName the auditEmpName to set
	 */
	public void setAuditEmpName(String auditEmpName) {
		this.auditEmpName = auditEmpName;
	}
	
	/**
	 * 设置auditEmpCode
	 * @param auditEmpCode the auditEmpCode to set
	 */
	public void setAuditEmpCode(String auditEmpCode) {
		this.auditEmpCode = auditEmpCode;
	}
	
	/**
	 * 获得status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSaleDepartmentCompany() {
		return isSaleDepartmentCompany;
	}

	public void setIsSaleDepartmentCompany(String isSaleDepartmentCompany) {
		this.isSaleDepartmentCompany = isSaleDepartmentCompany;
	}

	public String getCarpoolingType() {
		return carpoolingType;
	}

	public void setCarpoolingType(String carpoolingType) {
		this.carpoolingType = carpoolingType;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[inviteNo,").append(inviteNo).append("]");
		stringBuilder.append("[auditTime,").append(auditTime).append("]");
		stringBuilder.append("[auditNo,").append(auditNo).append("]");
		stringBuilder.append("[notes,").append(notes).append("]");
		stringBuilder.append("[isSaleDepartmentCompany,").append(isSaleDepartmentCompany).append("]");
		stringBuilder.append("[carpoolingType,").append(carpoolingType).append("]");
		stringBuilder.append("[auditOrgName,").append(auditOrgName).append("]");
		stringBuilder.append("[auditOrgCode,").append(auditOrgCode).append("]");
		stringBuilder.append("[auditEmpName,").append(auditEmpName).append("]");
		stringBuilder.append("[auditEmpCode,").append(auditEmpCode).append("]");
		stringBuilder.append("[status,").append(status).append("]");
		return stringBuilder.toString();
	}
}