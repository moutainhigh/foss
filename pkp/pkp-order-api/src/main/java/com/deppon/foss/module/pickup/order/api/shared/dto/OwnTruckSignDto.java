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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/OwnTruckDto.java
 * 
 * FILE NAME        	: OwnTruckDto.java
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
 * 自有车DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午9:15:23
 */
public class OwnTruckSignDto implements Serializable {

	private static final long serialVersionUID = -1308875987489613182L;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	
	/** 
	 * 车型
	 */
	private String vehicleNoType;
	
	/** 
	 * 司机code
	 */
	private String driverCode;
	/** 
	 * 司机姓名
	 */
	private String driverName;
	
	/** 
	 * 司机手机
	 */
	private String driverMobilePhone;
	
	/** 
	 * 车辆类型
	 */
	private String vehicleType;

	/** 
	 * 是否接收状态
	 */
	private String recieveOrderStatus;
	/** 
	 * 所属区域名称
	 */
	private String regionName;
	/** 
	 * 所属区域Code
	 */
	private String regionCode;
	
	/** 
	 * 所属接货小区区域GISID
	 */
	private String gisId;
	
	/** 
	 * 已经派送运单数
	 */
	private Integer signWaybillTotal;
	/** 
	 * 未派送运单数
	 */
	private Integer deliverWaybillTotal;
	/** 
	 * 未接货订单总数
	 */
	private Integer receiveOrderTotal;
	/** 
	 * 已完结接货总数
	 */
	private Integer receiveWaybillOrderTotal;
	
	/**
	 * 货物总重量和体积
	 */
	private String weightAndVolume;

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleNoType() {
		return vehicleNoType;
	}

	public void setVehicleNoType(String vehicleNoType) {
		this.vehicleNoType = vehicleNoType;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getRecieveOrderStatus() {
		return recieveOrderStatus;
	}

	public void setRecieveOrderStatus(String recieveOrderStatus) {
		this.recieveOrderStatus = recieveOrderStatus;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public Integer getSignWaybillTotal() {
		return signWaybillTotal;
	}

	public void setSignWaybillTotal(Integer signWaybillTotal) {
		this.signWaybillTotal = signWaybillTotal;
	}

	public Integer getDeliverWaybillTotal() {
		return deliverWaybillTotal;
	}

	public void setDeliverWaybillTotal(Integer deliverWaybillTotal) {
		this.deliverWaybillTotal = deliverWaybillTotal;
	}

	public Integer getReceiveOrderTotal() {
		return receiveOrderTotal;
	}

	public void setReceiveOrderTotal(Integer receiveOrderTotal) {
		this.receiveOrderTotal = receiveOrderTotal;
	}

	public Integer getReceiveWaybillOrderTotal() {
		return receiveWaybillOrderTotal;
	}

	public void setReceiveWaybillOrderTotal(Integer receiveWaybillOrderTotal) {
		this.receiveWaybillOrderTotal = receiveWaybillOrderTotal;
	}

	public String getGisId() {
		return gisId;
	}

	public void setGisId(String gisId) {
		this.gisId = gisId;
	}

	
	
	public String getDriverMobilePhone() {
		return driverMobilePhone;
	}

	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * equals方法
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:50:27
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OwnTruckSignDto other = (OwnTruckSignDto) obj;
		if (driverCode == null) {
			if (other.driverCode != null) {
				return false;
			}
		} else if (!driverCode.equals(other.driverCode)) {
			return false;
		}
		return true;
	}

	/**
	 * hash方法
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 上午9:50:27
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverCode == null) ? 0 : driverCode.hashCode());
		return result;
	}

	public String getWeightAndVolume() {
		return weightAndVolume;
	}

	public void setWeightAndVolume(String weightAndVolume) {
		this.weightAndVolume = weightAndVolume;
	}
}