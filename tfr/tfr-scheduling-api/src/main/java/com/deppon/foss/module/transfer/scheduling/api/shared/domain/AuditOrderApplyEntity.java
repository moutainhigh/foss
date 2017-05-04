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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/AuditOrderApplyEntity.java
 * 
 *  FILE NAME     :AuditOrderApplyEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;

import java.util.Date;

/**
 * 审核约车log实体
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午3:13:16
 */
public class AuditOrderApplyEntity extends BaseEntity {
	
	private static final long serialVersionUID = 8101342109345268042L;

	/** 约车编号 */
	private String orderNo;
	
	/** 受理时间 */
	private Date auditTime;
		
	/** 受理序号 */
	private Integer auditNo;
		
	/** 审核结果备注  */
	private String notes;
		
	/** 审核车队名称 */
	private String auditOrgName;
		
	/** 审核车队编码 */
	private String auditOrgCode;
		
	/** 审核人员名称 */
	private String auditEmpName;
		
	/** 审核人员编码 */
	private String auditEmpCode;
	
	/** 操作状态 */
	private String status;
		
	/**
	 * 获得orderNo
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
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
		if (notes == null) {
			notes = StringUtil.EMPTY_STRING;
		}
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
	 * 设置status
	 * @param status the status to set
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置orderNo
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	 * 设置status
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[orderNo,").append(orderNo).append("]");
		stringBuilder.append("[auditTime,").append(auditTime).append("]");
		stringBuilder.append("[auditNo,").append(auditNo).append("]");
		stringBuilder.append("[notes,").append(notes).append("]");
		stringBuilder.append("[auditOrgName,").append(auditOrgName).append("]");
		stringBuilder.append("[auditOrgCode,").append(auditOrgCode).append("]");
		stringBuilder.append("[auditEmpName,").append(auditEmpName).append("]");
		stringBuilder.append("[auditEmpCode,").append(auditEmpCode).append("]");
		stringBuilder.append("[status,").append(status).append("]");
		return stringBuilder.toString();
	}
}