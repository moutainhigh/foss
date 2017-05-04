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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/domain/PdaSignEntity.java
 * 
 * FILE NAME        	: PdaSignEntity.java
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
 * PDA签到实体
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 下午6:58:24
 */
public class PdaSignEntity extends BaseEntity {

	private static final long serialVersionUID = 4488612585994170504L;

	/** 
	 * 设备号
	 */
	private String deviceNo;
	/** 
	 * 司机姓名
	 */
	private String driverName;
	/** 
	 * 司机编码
	 */
	private String driverCode;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 创建时间
	 */
	private Date createTime;
	/**
	 *  解绑人
	 */
	private String unbundler;
	/** 
	 * 解绑人编码
	 */
	private String unbundlerCode;
	/** 
	 * 解绑原因
	 */
	private String unbundleReason;
	/** 
	 * 解绑时间
	 */
	private Date unbundleTime;
	/** 
	 * 状态
	 */
	private String status;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 所属组织
	 */
	private String orgCode;
	
	/**
	 * Gets the orgCode.
	 * 
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the orgCode.
	 * 
	 * @param orgCode the orgCode to see
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Instantiates a new pda sign entity.
	 */
	public PdaSignEntity() {

	}

	/**
	 * Instantiates a new pda sign entity.
	 * 
	 * @param deviceNo the device no
	 * @param driverCode the driver code
	 * @param vehicleNo the vehicle no
	 * @param status the status
	 */
	public PdaSignEntity(String deviceNo, String driverCode, String vehicleNo, String status) {
		this.deviceNo = deviceNo;
		this.driverCode = driverCode;
		this.vehicleNo = vehicleNo;
		this.status = status;
	}
	/**
	 * add  by 329757 外请车查询签到信息用
	 * @param deviceNo
	 * @param vehicleNo
	 * @param status
	 */
	public PdaSignEntity(String deviceNo, String vehicleNo, String status) {
		this.deviceNo = deviceNo;
		this.vehicleNo = vehicleNo;
		this.status = status;
	}
	/**
	 * Gets the deviceNo.
	 * 
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * Sets the deviceNo.
	 * 
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
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
	 * Gets the createTime.
	 * 
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the createTime.
	 * 
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the unbundler.
	 * 
	 * @return the unbundler
	 */
	public String getUnbundler() {
		return unbundler;
	}

	/**
	 * Sets the unbundler.
	 * 
	 * @param unbundler the unbundler to set
	 */
	public void setUnbundler(String unbundler) {
		this.unbundler = unbundler;
	}

	/**
	 * Gets the unbundlerCode.
	 * 
	 * @return the unbundlerCode
	 */
	public String getUnbundlerCode() {
		return unbundlerCode;
	}

	/**
	 * Sets the unbundlerCode.
	 * 
	 * @param unbundlerCode the unbundlerCode to set
	 */
	public void setUnbundlerCode(String unbundlerCode) {
		this.unbundlerCode = unbundlerCode;
	}

	/**
	 * Gets the unbundleReason.
	 * 
	 * @return the unbundleReason
	 */
	public String getUnbundleReason() {
		return unbundleReason;
	}

	/**
	 * Sets the unbundleReason.
	 * 
	 * @param unbundleReason the unbundleReason to set
	 */
	public void setUnbundleReason(String unbundleReason) {
		this.unbundleReason = unbundleReason;
	}

	/**
	 * Gets the unbundleTime.
	 * 
	 * @return the unbundleTime
	 */
	public Date getUnbundleTime() {
		return unbundleTime;
	}

	/**
	 * Sets the unbundleTime.
	 * 
	 * @param unbundleTime the unbundleTime to set
	 */
	public void setUnbundleTime(Date unbundleTime) {
		this.unbundleTime = unbundleTime;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the userType.
	 * 
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the userType.
	 * 
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	

}