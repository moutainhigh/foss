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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/DispatchVehicleRecordEntity.java
 * 
 * FILE NAME        	: DispatchVehicleRecordEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 派车记录实体
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午9:56:13
 */
public class DispatchVehicleRecordEntity extends BaseEntity {

	private static final long serialVersionUID = -1062751519116132103L;
	/**
	 * 调度订单id
	 */
	private String tSrvDispatchOrderId;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 司机手机
	 */
	private String driverMobile;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 派车时间
	 */
	private Date deliverTime;
	/**
	 * 定人定区code
	 */
	private String pickupRegionCode;
	/**
	 * 是否使用PDA
	 */
	private String pdaStatus;
	/**
	 * 定人定区名字
	 */
	private String pickupRegionName;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 订单受理状态
	 */
	private String processStatus;

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * Gets the orderStatus.
	 * 
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the orderStatus.
	 * 
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Gets the tSrvDispatchOrderId.
	 * 
	 * @return the tSrvDispatchOrderId
	 */
	public String gettSrvDispatchOrderId() {
		return tSrvDispatchOrderId;
	}

	/**
	 * Sets the tSrvDispatchOrderId.
	 * 
	 * @param tSrvDispatchOrderId the tSrvDispatchOrderId to set
	 */
	public void settSrvDispatchOrderId(String tSrvDispatchOrderId) {
		this.tSrvDispatchOrderId = tSrvDispatchOrderId;
	}

	/**
	 * Gets the driverName.
	 * 
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Sets the driverName.
	 * 
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Gets the driverCode.
	 * 
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the driverCode.
	 * 
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the vehicleNo.
	 * 
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicleNo.
	 * 
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the deliverTime.
	 * 
	 * @return the deliverTime
	 */
	public Date getDeliverTime() {
		return deliverTime;
	}

	/**
	 * Sets the deliverTime.
	 * 
	 * @param deliverTime the deliverTime to set
	 */
	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	/**
	 * Gets the pickupRegionCode.
	 * 
	 * @return the pickupRegionCode
	 */
	public String getPickupRegionCode() {
		return pickupRegionCode;
	}

	/**
	 * Sets the pickupRegionCode.
	 * 
	 * @param pickupRegionCode the pickupRegionCode to set
	 */
	public void setPickupRegionCode(String pickupRegionCode) {
		this.pickupRegionCode = pickupRegionCode;
	}

	/**
	 * Gets the pickupRegionName.
	 * 
	 * @return the pickupRegionName
	 */
	public String getPickupRegionName() {
		return pickupRegionName;
	}

	/**
	 * Sets the pickupRegionName.
	 * 
	 * @param pickupRegionName the pickupRegionName to set
	 */
	public void setPickupRegionName(String pickupRegionName) {
		this.pickupRegionName = pickupRegionName;
	}

	/**
	 * Gets the pdaStatus.
	 * 
	 * @return the pdaStatus
	 */
	public String getPdaStatus() {
		return pdaStatus;
	}

	/**
	 * Sets the pdaStatus.
	 * 
	 * @param pdaStatus the pdaStatus to set
	 */
	public void setPdaStatus(String pdaStatus) {
		this.pdaStatus = pdaStatus;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

}