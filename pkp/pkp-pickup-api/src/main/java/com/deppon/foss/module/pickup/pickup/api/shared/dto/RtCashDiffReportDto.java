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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/RtCashDiffReportDto.java
 * 
 * FILE NAME        	: RtCashDiffReportDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查看现金差异报表查询结果DTO
 *
 */
public class RtCashDiffReportDto implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;
	//id
	private String id;
	
	//运单号
	private String waybillNo;
	
	//接货司机姓名/工号
	private String driverNoAndName;
	
	//接货司部门code
	private String driverDepartmentCode;
	
	//接货司部门name
	private String driverDepartmentName;
	
	//车牌号
	private String vehicleNo;
	
	//接货时间
	private Date pickupTime;
	
	//收货部门Code
	private String receiveOrgCode;
	
	//收货部门name
	private String receiveOrgName;
	
	//PDA录入收款金额
	private BigDecimal pdaInputAmount;
	
	//运单手写现付金额
	private BigDecimal waybillHandwriteAmount;
	
	//开单应收现付金额 
	private BigDecimal waybillPayAmount;
	
	// PDA录入收款金额 - 运单手写现付金额
	private BigDecimal pdaMinusHandwriteAmount;
	
	// 运单手写现付金额与开单应收现付金额之差
	private BigDecimal handwriteMinusPayAmount;
	
	//制单人code
	private String createUserCode;
	
	//制单人name
	private String createUserName;

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDriverNoAndName() {
		return driverNoAndName;
	}

	public void setDriverNoAndName(String driverNoAndName) {
		this.driverNoAndName = driverNoAndName;
	}

	public String getDriverDepartmentCode() {
		return driverDepartmentCode;
	}

	public void setDriverDepartmentCode(String driverDepartmentCode) {
		this.driverDepartmentCode = driverDepartmentCode;
	}

	public String getDriverDepartmentName() {
		return driverDepartmentName;
	}

	public void setDriverDepartmentName(String driverDepartmentName) {
		this.driverDepartmentName = driverDepartmentName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public BigDecimal getPdaInputAmount() {
		return pdaInputAmount;
	}

	public void setPdaInputAmount(BigDecimal pdaInputAmount) {
		this.pdaInputAmount = pdaInputAmount;
	}

	public BigDecimal getWaybillHandwriteAmount() {
		return waybillHandwriteAmount;
	}

	public void setWaybillHandwriteAmount(BigDecimal waybillHandwriteAmount) {
		this.waybillHandwriteAmount = waybillHandwriteAmount;
	}

	public BigDecimal getWaybillPayAmount() {
		return waybillPayAmount;
	}

	public void setWaybillPayAmount(BigDecimal waybillPayAmount) {
		this.waybillPayAmount = waybillPayAmount;
	}

	public BigDecimal getPdaMinusHandwriteAmount() {
		return pdaMinusHandwriteAmount;
	}

	public void setPdaMinusHandwriteAmount(BigDecimal pdaMinusHandwriteAmount) {
		this.pdaMinusHandwriteAmount = pdaMinusHandwriteAmount;
	}

	public BigDecimal getHandwriteMinusPayAmount() {
		return handwriteMinusPayAmount;
	}

	public void setHandwriteMinusPayAmount(BigDecimal handwriteMinusPayAmount) {
		this.handwriteMinusPayAmount = handwriteMinusPayAmount;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	
	
}