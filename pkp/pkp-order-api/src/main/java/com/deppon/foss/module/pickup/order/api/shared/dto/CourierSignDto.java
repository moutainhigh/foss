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


public class CourierSignDto implements Serializable {
	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
		
	/** 
	 * 签到快递员姓名
	 */
	private String courierName;

	/** 
	 * 签到快递员编号
	 */
	private String courierCode;

	/** 
	 * 签到快递员手机
	 */
	private String courierMobile;
	
	/** 
	 * 签到快递员属性
	 */
	private String courierType;
	
	/** 
	 * 所属收派大区名称
	 */
	private String bigZoneName;
	/** 
	 * 所属收派大区Code
	 */
	private String bigZoneCode;
	
	/** 
	 * 所属收派小区名称
	 */
	private String smallZoneName;
	/** 
	 * 所属收派小区Code
	 */
	private String smallZoneCode;
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public String getCourierCode() {
		return courierCode;
	}
	public void setCourierCode(String courierCode) {
		this.courierCode = courierCode;
	}
	public String getCourierMobile() {
		return courierMobile;
	}
	public void setCourierMobile(String courierMobile) {
		this.courierMobile = courierMobile;
	}
	public String getBigZoneName() {
		return bigZoneName;
	}
	public void setBigZoneName(String bigZoneName) {
		this.bigZoneName = bigZoneName;
	}
	public String getBigZoneCode() {
		return bigZoneCode;
	}
	public void setBigZoneCode(String bigZoneCode) {
		this.bigZoneCode = bigZoneCode;
	}
	public String getSmallZoneName() {
		return smallZoneName;
	}
	public void setSmallZoneName(String smallZoneName) {
		this.smallZoneName = smallZoneName;
	}
	public String getSmallZoneCode() {
		return smallZoneCode;
	}
	public void setSmallZoneCode(String smallZoneCode) {
		this.smallZoneCode = smallZoneCode;
	}
	public String getCourierType() {
		return courierType;
	}
	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}
	
}