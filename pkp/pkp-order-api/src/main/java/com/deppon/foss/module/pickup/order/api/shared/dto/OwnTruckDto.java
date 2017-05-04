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
import java.math.BigDecimal;

/**
 * 自有车DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午9:15:23
 */
public class OwnTruckDto implements Serializable {

	private static final long serialVersionUID = -1308875987489613182L;
	/** 
	 * 车牌号
	 */
	private String vehicleNo;
	/** 
	 * 剩余重量
	 */
	private BigDecimal remainingWeight;
	/** 
	 * 剩余体积
	 */
	private BigDecimal remainingVolume;
	/** 
	 * 车型
	 */
	private String vehicleType;
	/** 
	 * 净重
	 */
	private BigDecimal netWeight;
	/** 
	 * 净空
	 */
	private BigDecimal netVolume;
	/** 
	 * 司机code
	 */
	private String driverCode;
	/** 
	 * 司机姓名
	 */
	private String driverName;
	/** 
	 * 设备号
	 */
	private String deviceNo;
	/** 
	 * 是否PDA绑定
	 */
	private String isPdaBundle;
	/** 
	 * 所属区域名称
	 */
	private String ownedZoneName;
	/** 
	 * 所属区域Code
	 */
	private String ownedZoneCode;
	/** 
	 * 司机手机
	 */
	private String driverMobile;

	/**
	 * @return the driverMobile
	 */
	public String getDriverMobile() {
		return driverMobile;
	}

	/**
	 * @param driverMobile the driverMobile to set
	 */
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
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
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the isPdaBundle
	 */
	public String getIsPdaBundle() {
		return isPdaBundle;
	}

	/**
	 * @param isPdaBundle the isPdaBundle to set
	 */
	public void setIsPdaBundle(String isPdaBundle) {
		this.isPdaBundle = isPdaBundle;
	}

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
		OwnTruckDto other = (OwnTruckDto) obj;
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
}