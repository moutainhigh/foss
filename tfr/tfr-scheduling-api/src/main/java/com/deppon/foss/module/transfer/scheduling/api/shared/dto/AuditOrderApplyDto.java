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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/AuditOrderApplyDto.java
 * 
 *  FILE NAME     :AuditOrderApplyDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: AuditOrderApplyDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;

/**
 * 约车审核log
 * @author 104306-foss-wangLong
 * @date 2012-11-29 下午12:19:36
 */
public class AuditOrderApplyDto implements Serializable {

	private static final long serialVersionUID = 8738059428864129043L;
	
	private AuditOrderApplyEntity auditOrderApplyEntity;
	
	/** 审核人名称 */
	private String auditEmpName;
	
	/** 受理时间 */
	private Date auditTime;
	
	/** 操作状态 */
	private String status;
	
	/** 审核结果备注  */
	private String notes;
	
	/** 预计到达时间 */
	private Date perdictArriveTime;
	
	/** 是否生成放行单 */
	private String ifNeedReleaseBill;

	/**
	 * 
	 *
	 * @return 
	 */
	public AuditOrderApplyEntity getAuditOrderApplyEntity() {
		return auditOrderApplyEntity;
	}

	/**
	 * 获得auditEmpName
	 * @return the auditEmpName
	 */
	public String getAuditEmpName() {
		return auditEmpName;
	}

	/**
	 * 设置auditEmpName
	 * @param auditEmpName the auditEmpName to set
	 */
	public void setAuditEmpName(String auditEmpName) {
		this.auditEmpName = auditEmpName;
	}

	/**
	 * 获得auditTime
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 设置auditTime
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
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

	/**
	 * 获得notes
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * 设置notes
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获得perdictArriveTime
	 * @return the perdictArriveTime
	 */
	public Date getPerdictArriveTime() {
		return perdictArriveTime;
	}

	/**
	 * 设置perdictArriveTime
	 * @param perdictArriveTime the perdictArriveTime to set
	 */
	public void setPerdictArriveTime(Date perdictArriveTime) {
		this.perdictArriveTime = perdictArriveTime;
	}

	/**
	 * 获得ifNeedReleaseBill
	 * @return the ifNeedReleaseBill
	 */
	public String getIfNeedReleaseBill() {
		return ifNeedReleaseBill;
	}

	/**
	 * 设置ifNeedReleaseBill
	 * @param ifNeedReleaseBill the ifNeedReleaseBill to set
	 */
	public void setIfNeedReleaseBill(String ifNeedReleaseBill) {
		this.ifNeedReleaseBill = ifNeedReleaseBill;
	}

	/**
	 * 设置auditOrderApplyEntity
	 * @param auditOrderApplyEntity the auditOrderApplyEntity to set
	 */
	public void setAuditOrderApplyEntity(AuditOrderApplyEntity auditOrderApplyEntity) {
		this.auditOrderApplyEntity = auditOrderApplyEntity;
	}
}