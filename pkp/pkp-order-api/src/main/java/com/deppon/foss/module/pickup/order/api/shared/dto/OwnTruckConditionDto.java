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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/OwnTruckConditionDto.java
 * 
 * FILE NAME        	: OwnTruckConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 自有车查询Dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-15 下午4:57:43
 */
public class OwnTruckConditionDto implements Serializable {
	private static final long serialVersionUID = -2520138120262572174L;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 车型
	 */
	private String vehicleType;
	/** 
	 * 接送货车队
	 */
	private String serviceFleetCode;
	/** 
	 * 司机编码
	 */
	private String driverCode;
	/** 
	 * 区域类型（接货or送货）
	 */
	private String regionType;
	/** 
	 * 区域大小
	 */
	private String regionNature;
	/** 
	 * 是否激活
	 */
	private String active;
	/** 
	 * 绑定状态
	 */
	private String bundleStatus;
	/** 
	 * 排班类型
	 */
	private String schedulingType;
	/** 
	 * 排班状态
	 */
	private String schedulingStatus;
	/** 
	 * 计划状态
	 */
	private String schedulingPlanType;
	/** 
	 * 物流班车类型
	 */
	private String departPlanType;
	/** 
	 * 组织code（营业部或车队）
	 */
	private String orgCode;
	/** 
	 * 发车计划状态
	 */
	private String departPlanStatus;
	/**
	 * 约车状态
	 */
	private List<String> orderVehicleStatus;
	/**
	 * 车队下的所有车队组
	 */
	private List<String> fleetCodeList;
	/**
	 * 快递员对应区域code
	 */
	private String expressOrderCountyCode;
	/**
	 * 登录人所属快递大区下的所有行政区域（市一级单位）
	 */
	private List<String> expressOrderCityCodes;
	/**
	 * 登录人所属快递大区下的所有行政区域（区县一级单位）
	 */
	private List<String> expressOrderCountyCodes;
	/**
	 * 登录人查询录入的行政区域（区县一级单位，必须在所属对应的快递大区下）
	 */
	private List<String> expressOrderNewCountyCodes;
	
	/**
	 * pda签到类型
	 */
	private String pdaSignUserType;
	/*
	 * 电话号码
	 */
	private String driverMobile;
	private String driverName;

	public String getPdaSignUserType() {
		return pdaSignUserType;
	}

	public void setPdaSignUserType(String pdaSignUserType) {
		this.pdaSignUserType = pdaSignUserType;
	}

	public String getExpressOrderCountyCode() {
		return expressOrderCountyCode;
	}

	public void setExpressOrderCountyCode(String expressOrderCountyCode) {
		this.expressOrderCountyCode = expressOrderCountyCode;
	}

	public List<String> getExpressOrderCountyCodes() {
		return expressOrderCountyCodes;
	}

	public void setExpressOrderCountyCodes(List<String> expressOrderCountyCodes) {
		this.expressOrderCountyCodes = expressOrderCountyCodes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getFleetCodeList() {
		return fleetCodeList;
	}

	public void setFleetCodeList(List<String> fleetCodeList) {
		this.fleetCodeList = fleetCodeList;
	}

	public List<String> getOrderVehicleStatus() {
		return orderVehicleStatus;
	}

	public void setOrderVehicleStatus(List<String> orderVehicleStatus) {
		this.orderVehicleStatus = orderVehicleStatus;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the serviceFleetCode
	 */
	public String getServiceFleetCode() {
		return serviceFleetCode;
	}

	/**
	 * @param serviceFleetCode the serviceFleetCode to set
	 */
	public void setServiceFleetCode(String serviceFleetCode) {
		this.serviceFleetCode = serviceFleetCode;
	}

	/**
	 * @return the regionType
	 */
	public String getRegionType() {
		return regionType;
	}

	/**
	 * @param regionType the regionType to set
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	/**
	 * @return the regionNature
	 */
	public String getRegionNature() {
		return regionNature;
	}

	/**
	 * @param regionNature the regionNature to set
	 */
	public void setRegionNature(String regionNature) {
		this.regionNature = regionNature;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the bundleStatus
	 */
	public String getBundleStatus() {
		return bundleStatus;
	}

	/**
	 * @param bundleStatus the bundleStatus to set
	 */
	public void setBundleStatus(String bundleStatus) {
		this.bundleStatus = bundleStatus;
	}

	/**
	 * @return the departPlanStatus
	 */
	public String getDepartPlanStatus() {
		return departPlanStatus;
	}

	/**
	 * @param departPlanStatus the departPlanStatus to set
	 */
	public void setDepartPlanStatus(String departPlanStatus) {
		this.departPlanStatus = departPlanStatus;
	}

	/**
	 * @return the schedulingType
	 */
	public String getSchedulingType() {
		return schedulingType;
	}

	/**
	 * @param schedulingType the schedulingType to set
	 */
	public void setSchedulingType(String schedulingType) {
		this.schedulingType = schedulingType;
	}

	/**
	 * @return the schedulingStatus
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * @param schedulingStatus the schedulingStatus to set
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * @return the schedulingPlanType
	 */
	public String getSchedulingPlanType() {
		return schedulingPlanType;
	}

	/**
	 * @param schedulingPlanType the schedulingPlanType to set
	 */
	public void setSchedulingPlanType(String schedulingPlanType) {
		this.schedulingPlanType = schedulingPlanType;
	}

	/**
	 * @return the departPlanType
	 */
	public String getDepartPlanType() {
		return departPlanType;
	}

	/**
	 * @param departPlanType the departPlanType to set
	 */
	public void setDepartPlanType(String departPlanType) {
		this.departPlanType = departPlanType;
	}

	/**
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public List<String> getExpressOrderCityCodes() {
		return expressOrderCityCodes;
	}

	public void setExpressOrderCityCodes(List<String> expressOrderCityCodes) {
		this.expressOrderCityCodes = expressOrderCityCodes;
	}

	public List<String> getExpressOrderNewCountyCodes() {
		return expressOrderNewCountyCodes;
	}

	public void setExpressOrderNewCountyCodes(List<String> expressOrderNewCountyCodes) {
		this.expressOrderNewCountyCodes = expressOrderNewCountyCodes;
	}

}