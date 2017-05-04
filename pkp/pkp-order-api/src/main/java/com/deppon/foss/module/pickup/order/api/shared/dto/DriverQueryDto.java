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

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;

/**
 * PDA签到-订单可视化,查询对象DTO
 * 
 * @author 045925
 * @date 2014-06-18 下午9:15:47
 */
public class DriverQueryDto extends PdaSignEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 车队编码
	 */
	private String cadeCode;
	/**
	 * 接送货大区编码
	 */
	private String bigZoneCode;
	private List<String> bigZoneCodes;
	/**
	 * 接送货小区编码
	 */
	private String smallZoneCode;
	private List<String> smallZoneCodes;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 司机编码
	 */
	private String driverCode;
	/**
	 * 司机暂停/开启状态(STOP/OPEN)
	 */
	private String recieveOrderStatus;
	/**
	 * 大小区编码集合
	 */
	private List<String> regionZoneList;
	/**
	 * 车队编码集合
	 */
	private List<String> fleetCodeList;
	
	/**
	 * 车牌号编码集合
	 */
	private List<String> vehicleNoList;
	
	/**
	 * 重量和体积总和
	 */
	private String weightAndVolume;
	
	 /**
	  * 体积
	  */
	private BigDecimal volumn;
	
	/**
	 * 重量
	 */
	private BigDecimal weight;
	
	/**
	 * 运输性质集合
	 */
	private List<String> productCodes;
	
	public String getCadeCode() {
		return cadeCode;
	}
	public void setCadeCode(String cadeCode) {
		this.cadeCode = cadeCode;
	}
	public String getBigZoneCode() {
		return bigZoneCode;
	}
	public void setBigZoneCode(String bigZoneCode) {
		this.bigZoneCode = bigZoneCode;
	}
	public String getSmallZoneCode() {
		return smallZoneCode;
	}
	public void setSmallZoneCode(String smallZoneCode) {
		this.smallZoneCode = smallZoneCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getRecieveOrderStatus() {
		return recieveOrderStatus;
	}
	public void setRecieveOrderStatus(String recieveOrderStatus) {
		this.recieveOrderStatus = recieveOrderStatus;
	}
	public List<String> getRegionZoneList() {
		return regionZoneList;
	}
	public void setRegionZoneList(List<String> regionZoneList) {
		this.regionZoneList = regionZoneList;
	}
	public List<String> getFleetCodeList() {
		return fleetCodeList;
	}
	public void setFleetCodeList(List<String> fleetCodeList) {
		this.fleetCodeList = fleetCodeList;
	}
	public List<String> getVehicleNoList() {
		return vehicleNoList;
	}
	public void setVehicleNoList(List<String> vehicleNoList) {
		this.vehicleNoList = vehicleNoList;
	}
	public String getWeightAndVolume() {
		return weightAndVolume;
	}
	public void setWeightAndVolume(String weightAndVolume) {
		this.weightAndVolume = weightAndVolume;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public List<String> getBigZoneCodes() {
		return bigZoneCodes;
	}
	public void setBigZoneCodes(List<String> bigZoneCodes) {
		this.bigZoneCodes = bigZoneCodes;
	}
	public List<String> getSmallZoneCodes() {
		return smallZoneCodes;
	}
	public void setSmallZoneCodes(List<String> smallZoneCodes) {
		this.smallZoneCodes = smallZoneCodes;
	}
	/**
	 * @return the productCodes
	 */
	public List<String> getProductCodes() {
		return productCodes;
	}
	/**
	 * @param productCodes the productCodes to set
	 */
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	
}