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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/domain/TruckTaskEntity.java
 *  
 *  FILE NAME          :TruckTaskEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 任务车辆实体
 * 
 * @author dp-liming
 * @date 2012-11-10 上午8:35:37
 */
public class TruckTaskEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3784206399164324566L;

	/**
	 * Id
	 */
	private String id;//

	/**
	 * 车牌号
	 */
	private String vehicleNo;//

	/**
	 * 线路
	 */
	private String lineNo;//

	/**
	 * 车辆业务类型
	 */
	private String businessType;//

	/**
	 * 司机编号1
	 */
	private String driverCode1;//

	/**
	 * 司机姓名1
	 */
	private String driverName1;//

	/**
	 * 司机联系方式
	 */
	private String driverPhone;//

	/**
	 * 车辆状态
	 */
	private String status;//

	/**
	 * 司机编号2
	 */
	private String driverCode2;//

	/**
	 * 司机姓名2
	 */
	private String driverName2;//

	/**
	 * 创建时间
	 */
	private Date createTime;//

	/**
	 * 车辆加载
	 */
	private String beCarLoad;

	/**
	 * 装载编号
	 */
	private String chargingAssmebleNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 车牌号.
	 * 
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 * 
	 * @param vehicleNo
	 *            the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 线路.
	 * 
	 * @return the 线路
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路.
	 * 
	 * @param lineNo
	 *            the new 线路
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 车辆业务类型.
	 * 
	 * @return the 车辆业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 车辆业务类型.
	 * 
	 * @param businessType
	 *            the new 车辆业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 获取 司机编号1.
	 * 
	 * @return the 司机编号1
	 */
	public String getDriverCode1() {
		return driverCode1;
	}

	/**
	 * 设置 司机编号1.
	 * 
	 * @param driverCode1
	 *            the new 司机编号1
	 */
	public void setDriverCode1(String driverCode1) {
		this.driverCode1 = driverCode1;
	}

	/**
	 * 获取 司机姓名1.
	 * 
	 * @return the 司机姓名1
	 */
	public String getDriverName1() {
		return driverName1;
	}

	/**
	 * 设置 司机姓名1.
	 * 
	 * @param driverName1
	 *            the new 司机姓名1
	 */
	public void setDriverName1(String driverName1) {
		this.driverName1 = driverName1;
	}

	/**
	 * 获取 司机联系方式.
	 * 
	 * @return the 司机联系方式
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置 司机联系方式.
	 * 
	 * @param driverPhone
	 *            the new 司机联系方式
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 车辆状态.
	 * 
	 * @return the 车辆状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 车辆状态.
	 * 
	 * @param status
	 *            the new 车辆状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 司机编号2.
	 * 
	 * @return the 司机编号2
	 */
	public String getDriverCode2() {
		return driverCode2;
	}

	/**
	 * 设置 司机编号2.
	 * 
	 * @param driverCode2
	 *            the new 司机编号2
	 */
	public void setDriverCode2(String driverCode2) {
		this.driverCode2 = driverCode2;
	}

	/**
	 * 获取 司机姓名2.
	 * 
	 * @return the 司机姓名2
	 */
	public String getDriverName2() {
		return driverName2;
	}

	/**
	 * 设置 司机姓名2.
	 * 
	 * @param driverName2
	 *            the new 司机姓名2
	 */
	public void setDriverName2(String driverName2) {
		this.driverName2 = driverName2;
	}

	/**
	 * 获取 创建时间.
	 * 
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 * 
	 * @param createTime
	 *            the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 车辆加载.
	 * 
	 * @return the 车辆加载
	 */
	public String getBeCarLoad() {
		return beCarLoad;
	}

	/**
	 * 设置 车辆加载.
	 * 
	 * @param beCarLoad
	 *            the new 车辆加载
	 */
	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}

	/**
	 * 获取 装载编号.
	 * 
	 * @return the 装载编号
	 */
	public String getChargingAssmebleNo() {
		return chargingAssmebleNo;
	}

	/**
	 * 设置 装载编号.
	 * 
	 * @param chargingAssmebleNo
	 *            the new 装载编号
	 */
	public void setChargingAssmebleNo(String chargingAssmebleNo) {
		this.chargingAssmebleNo = chargingAssmebleNo;
	}

}