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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/AbandonGoodsActionHistoryEntity.java
 * 
 * FILE NAME        	: AbandonGoodsActionHistoryEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * @ClassName: WorkFlowEntity
 * @Description: 工作流
 * @author 136892
 * @date 2012-10-25 上午10:05:55
 */
public class WorkFlowEntity extends BaseEntity {
	/**
	 * @Fields serialVersionUID : 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 工作流号
	 */
	private String flowCode;

	/**
	 * 客户配合状态
	 */
	private String customerCooperateStatus;

	/**
	 * 所属区域
	 */
	private String respectiveRegional;

	/**
	 * 所属区域名称
	 */
	private String respectiveRegionalName;

	/**
	 * 申请人编码
	 */
	private String createUserCode;

	/**
	 * 申请人
	 */
	private String createUserName;

	/**
	 * 申请人部门
	 */
	private String createOrgCode;

	/**
	 * 申请人部门名称
	 */
	private String createOrgName;

	/**
	 * 申请人职位
	 * 
	 * @return
	 */
	private String createUserTitle;

	/**
	 * 业务编码
	 */
	private String bizCode;

	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 流水号
	 */
	private String serialNumber;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public String getRespectiveRegional() {
		return respectiveRegional;
	}

	public void setRespectiveRegional(String respectiveRegional) {
		this.respectiveRegional = respectiveRegional;
	}

	public String getRespectiveRegionalName() {
		return respectiveRegionalName;
	}

	public void setRespectiveRegionalName(String respectiveRegionalName) {
		this.respectiveRegionalName = respectiveRegionalName;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserTitle() {
		return createUserTitle;
	}

	public void setCreateUserTitle(String createUserTitle) {
		this.createUserTitle = createUserTitle;
	}

	public String getCustomerCooperateStatus() {
		return customerCooperateStatus;
	}

	public void setCustomerCooperateStatus(String customerCooperateStatus) {
		this.customerCooperateStatus = customerCooperateStatus;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}