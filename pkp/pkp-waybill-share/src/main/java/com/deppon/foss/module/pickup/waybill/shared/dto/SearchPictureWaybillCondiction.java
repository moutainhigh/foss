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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillPendingDto.java
 * 
 * FILE NAME        	: WaybillPendingDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: waybillPendingDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 图片查询条件
 * @author hehaisu
 *
 */
public class SearchPictureWaybillCondiction implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7332280116867975688L;

	// 运单号/
	private String mixNo;
	
	// 订单号
	private String orderNo;
	
	//收货部门
	private String receiveOrgCode;
	
	//收货部门是否选了全部
	private String isAll;
	
	//制单部门
	private String createOrgCode;
	
	//运单图片类型
	private List<String> waybillPictureType;
	
	// Foss提交开始时间
	private Date createStartTime;

	// Foss提交结束时间
	private Date createEndTime;
	
	//开单人
	private String operator;
	
	//运输性质
	private String transType;
	
	//司机工号
	private String driverWorkNo;
	
	//司机姓名
	private String driverName;

	public String getMixNo() {
		return mixNo;
	}

	public void setMixNo(String mixNo) {
		this.mixNo = mixNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public List<String> getWaybillPictureType() {
		return waybillPictureType;
	}

	public void setWaybillPictureType(List<String> waybillPictureType) {
		this.waybillPictureType = waybillPictureType;
	}
	
	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getDriverWorkNo() {
		return driverWorkNo;
	}

	public void setDriverWorkNo(String driverWorkNo) {
		this.driverWorkNo = driverWorkNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	
}