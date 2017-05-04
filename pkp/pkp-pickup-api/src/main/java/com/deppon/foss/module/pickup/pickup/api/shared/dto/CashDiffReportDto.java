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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/CashDiffReportDto.java
 * 
 * FILE NAME        	: CashDiffReportDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查看现金差异报表查询条件DTO
 *
 */
public class CashDiffReportDto implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;
	//接货时间begin
	private Date pickupTimeBegin;
	
	//接货时间end
	private Date pickupTimeEnd;
		
	//制单人。
	private String createUserCode;
	
	//车牌号
	private String vehicleNo;
	
	//收货部门
	private String receiveOrgCode;
	
	//接货司机工号   -- (姓名 not show)
	private String driverNo;
	
	//司机所属部门（为F7控件，支持输入）
	private String driverDepartmentCode;

	public Date getPickupTimeBegin() {
		return pickupTimeBegin;
	}

	public void setPickupTimeBegin(Date pickupTimeBegin) {
		this.pickupTimeBegin = pickupTimeBegin;
	}

	public Date getPickupTimeEnd() {
		return pickupTimeEnd;
	}

	public void setPickupTimeEnd(Date pickupTimeEnd) {
		this.pickupTimeEnd = pickupTimeEnd;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public String getDriverDepartmentCode() {
		return driverDepartmentCode;
	}

	public void setDriverDepartmentCode(String driverDepartmentCode) {
		this.driverDepartmentCode = driverDepartmentCode;
	}
	
	
	
}