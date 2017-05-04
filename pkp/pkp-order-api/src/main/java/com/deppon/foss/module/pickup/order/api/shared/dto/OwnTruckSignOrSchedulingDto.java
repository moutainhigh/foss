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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/OwnTruckSignOrSchedulingDto.java
 * 
 * FILE NAME        	: OwnTruckSignOrSchedulingDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 自有车查询（带PDA签到和排班联合查询）
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-16 下午2:53:21
 */
public class OwnTruckSignOrSchedulingDto implements Serializable {

	private static final long serialVersionUID = -103693596179618373L;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 车型
	 */
	private String vehicleType;
	/** 
	 * 设备号
	 */
	private String deviceNo;
	/** 
	 * 签到司机姓名
	 */
	private String signDriverName;
	/** 
	 * 排班司机姓名
	 */
	private String schedulingDriverName;
	/** 
	 * 签到司机编号
	 */
	private String signDriverCode;
	/** 
	 * 排班司机编号
	 */
	private String schedulingDriverCode;
	/** 
	 * 签到司机手机
	 */
	private String signDriverMobile;
	/** 
	 * 排班司机手机
	 */
	private String schedulingDriverMobile;
	/** 
	 * 剩余重量
	 */
	private BigDecimal remainingWeight;
	/** 
	 * 剩余体积
	 */
	private BigDecimal remainingVolume;
	/** 
	 * 净重
	 */
	private BigDecimal netWeight;
	/** 
	 * 净空
	 */
	private BigDecimal netVolume;
	/** 
	 * 所属区域名称
	 */
	private String ownedZoneName;
	/** 
	 * 所属区域Code
	 */
	private String ownedZoneCode;

	/**
	 * @return the ownedZoneName
	 */
	public String getOwnedZoneName() {
		return ownedZoneName;
	}

	/**
	 * @param ownedZoneName the ownedZoneName to set
	 */
	public void setOwnedZoneName(String ownedZoneName) {
		this.ownedZoneName = ownedZoneName;
	}

	/**
	 * @return the ownedZoneCode
	 */
	public String getOwnedZoneCode() {
		return ownedZoneCode;
	}

	/**
	 * @param ownedZoneCode the ownedZoneCode to set
	 */
	public void setOwnedZoneCode(String ownedZoneCode) {
		this.ownedZoneCode = ownedZoneCode;
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
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the signDriverName
	 */
	public String getSignDriverName() {
		return signDriverName;
	}

	/**
	 * @param signDriverName the signDriverName to set
	 */
	public void setSignDriverName(String signDriverName) {
		this.signDriverName = signDriverName;
	}

	/**
	 * @return the schedulingDriverName
	 */
	public String getSchedulingDriverName() {
		return schedulingDriverName;
	}

	/**
	 * @param schedulingDriverName the schedulingDriverName to set
	 */
	public void setSchedulingDriverName(String schedulingDriverName) {
		this.schedulingDriverName = schedulingDriverName;
	}

	/**
	 * @return the signDriverCode
	 */
	public String getSignDriverCode() {
		return signDriverCode;
	}

	/**
	 * @param signDriverCode the signDriverCode to set
	 */
	public void setSignDriverCode(String signDriverCode) {
		this.signDriverCode = signDriverCode;
	}

	/**
	 * @return the schedulingDriverCode
	 */
	public String getSchedulingDriverCode() {
		return schedulingDriverCode;
	}

	/**
	 * @param schedulingDriverCode the schedulingDriverCode to set
	 */
	public void setSchedulingDriverCode(String schedulingDriverCode) {
		this.schedulingDriverCode = schedulingDriverCode;
	}

	/**
	 * @return the signDriverMobile
	 */
	public String getSignDriverMobile() {
		return signDriverMobile;
	}

	/**
	 * @param signDriverMobile the signDriverMobile to set
	 */
	public void setSignDriverMobile(String signDriverMobile) {
		this.signDriverMobile = signDriverMobile;
	}

	/**
	 * @return the schedulingDriverMobile
	 */
	public String getSchedulingDriverMobile() {
		return schedulingDriverMobile;
	}

	/**
	 * @param schedulingDriverMobile the schedulingDriverMobile to set
	 */
	public void setSchedulingDriverMobile(String schedulingDriverMobile) {
		this.schedulingDriverMobile = schedulingDriverMobile;
	}

	/**
	 * @return the remainingWeight
	 */
	public BigDecimal getRemainingWeight() {
		return remainingWeight;
	}

	/**
	 * @param remainingWeight the remainingWeight to set
	 */
	public void setRemainingWeight(BigDecimal remainingWeight) {
		this.remainingWeight = remainingWeight;
	}

	/**
	 * @return the remainingVolume
	 */
	public BigDecimal getRemainingVolume() {
		return remainingVolume;
	}

	/**
	 * @param remainingVolume the remainingVolume to set
	 */
	public void setRemainingVolume(BigDecimal remainingVolume) {
		this.remainingVolume = remainingVolume;
	}

	/**
	 * @return the netWeight
	 */
	public BigDecimal getNetWeight() {
		return netWeight;
	}

	/**
	 * @param netWeight the netWeight to set
	 */
	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return the netVolume
	 */
	public BigDecimal getNetVolume() {
		return netVolume;
	}

	/**
	 * @param netVolume the netVolume to set
	 */
	public void setNetVolume(BigDecimal netVolume) {
		this.netVolume = netVolume;
	}

}