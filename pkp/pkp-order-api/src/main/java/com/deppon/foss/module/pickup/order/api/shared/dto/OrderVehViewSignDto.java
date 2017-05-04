/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/PdaSignDto.java
 * 
 * FILE NAME        	: PdaSignDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

/**
 * PDA签到-订单可视化,查询出订单对应实体对象
 * 
 * @author 045925
 * @date 2014-06-18 下午9:15:47
 */
public class OrderVehViewSignDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单ID
	 */
	private String id;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 约车部门名称
	 */
	private String orderVehicleOrgName;
	/**
	 * 约车部门code
	 */
	private String orderVehicleOrgCode;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 接货地址
	 */
	private String pickupAddress;
	/**
	 * 客户手机||电话
	 */
	private String customerTelephone;
	/**
	 * 部门电话
	 */
	private String depTelephone;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderVehicleOrgName() {
		return orderVehicleOrgName;
	}
	public void setOrderVehicleOrgName(String orderVehicleOrgName) {
		this.orderVehicleOrgName = orderVehicleOrgName;
	}
	public String getOrderVehicleOrgCode() {
		return orderVehicleOrgCode;
	}
	public void setOrderVehicleOrgCode(String orderVehicleOrgCode) {
		this.orderVehicleOrgCode = orderVehicleOrgCode;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	public String getDepTelephone() {
		return depTelephone;
	}
	public void setDepTelephone(String depTelephone) {
		this.depTelephone = depTelephone;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
}