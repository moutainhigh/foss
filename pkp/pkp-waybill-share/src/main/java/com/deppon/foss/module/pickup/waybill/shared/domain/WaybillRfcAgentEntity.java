/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcAgentEntity.java
 * 
 * FILE NAME        	: WaybillRfcAgentEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 运单变更代理
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-shaohongliang,date:2012-10-11 下午5:09:25</p>
  * @date 2012-10-11 下午5:09:25
 * @since
 * @version
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:45:13
 */
public class WaybillRfcAgentEntity extends BaseEntity {
	/**
     * 序列化版本号
     */
	private static final long serialVersionUID = -4685570300241683786L;
	// 委托人code
	private String entrustEmpCode;
	//委托人name
	private String entrustEmpName;
	// 代理人
	private List<WaybillRfcAgentPersonEntity> agentEmpList;
	// 状态
	private String status;
	// 生效时间
	private Date validTime;
	// 终止时间
	private Date overdueTime;
	// 代理原因
	private String agentReason;
	//有效性
	private String active;
	//标示首次新增或修改已生效的新增
	private String flagAdd;
	
	//代理类型 如果为空就是零担  如果是EXPRESS就是快递
	private String type;
	
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlagAdd() {
		return flagAdd;
	}
	
	public void setFlagAdd(String flagAdd) {
		this.flagAdd = flagAdd;
	}
	/**
	 * @return entrustEmpCode : set the property entrustEmpCode.
	 */
	public String getEntrustEmpCode() {
		return entrustEmpCode;
	}
	/**
	 * @param entrustEmpCode : return the property entrustEmpCode.
	 */
	public void setEntrustEmpCode(String entrustEmpCode) {
		this.entrustEmpCode = entrustEmpCode;
	}
	/**
	 * @return entrustEmpName : set the property entrustEmpName.
	 */
	public String getEntrustEmpName() {
		return entrustEmpName;
	}
	/**
	 * @param entrustEmpName : return the property entrustEmpName.
	 */
	public void setEntrustEmpName(String entrustEmpName) {
		this.entrustEmpName = entrustEmpName;
	}
	/**
	 * @return agentEmpList : set the property agentEmpList.
	 */
	public List<WaybillRfcAgentPersonEntity> getAgentEmpList() {
		return agentEmpList;
	}
	/**
	 * @param agentEmpList : return the property agentEmpList.
	 */
	public void setAgentEmpList(List<WaybillRfcAgentPersonEntity> agentEmpList) {
		this.agentEmpList = agentEmpList;
	}
	/**
	 * @return status : set the property status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status : return the property status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return validTime : set the property validTime.
	 */
	public Date getValidTime() {
		return validTime;
	}
	/**
	 * @param validTime : return the property validTime.
	 */
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	/**
	 * @return overdueTime : set the property overdueTime.
	 */
	public Date getOverdueTime() {
		return overdueTime;
	}
	/**
	 * @param overdueTime : return the property overdueTime.
	 */
	public void setOverdueTime(Date overdueTime) {
		this.overdueTime = overdueTime;
	}
	/**
	 * @return agentReason : set the property agentReason.
	 */
	public String getAgentReason() {
		return agentReason;
	}
	/**
	 * @param agentReason : return the property agentReason.
	 */
	public void setAgentReason(String agentReason) {
		this.agentReason = agentReason;
	}
	/**
	 * @return active : set the property active.
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active : return the property active.
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
}