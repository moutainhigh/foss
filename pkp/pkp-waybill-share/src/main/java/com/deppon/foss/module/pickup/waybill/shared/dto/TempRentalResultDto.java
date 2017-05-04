/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/ResultDto.java
 * 
 * FILE NAME        	: ResultDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 临时租车查询结果
 * @author HeHaiSu
 * @date 2014-07-23 下午3:48:45
 * @since
 * @version
 */
public class TempRentalResultDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -116616542647967358L;

	private String billNo;//单号
	
	private String billType;//单据类型
	
	private String vehicleNo;//车牌号
	
	private String driver;//司机姓名
	
	private String departureOrg;//出发部门
	
	private String arriveOrg;//到达部门
	
	private String weight;//重量
	
	private String volume;//体积
	
	private String goodsName;//货物品名
	
	private String goodsPackage;//货物包装
	
	private String pickupToDoor;//是否上门接货
	
	private String deliveryCustomerName;//发货客户名称
	
	private String deliveryCustomerAddress;//发货客户地址
	
	private String targetOrgCode;//目的站
	
	private String receiveMethod;//提货方式
	
	private String receiveCustomerContact;//收货联系人
	
	private String receiveCustomerAddress;//收货联系人地址
	
	private Date billGenerateTime;//单据生成时间
	
	private String tempRentalMarkNo;//租车编号
	
	private String rentalUseType;//租车用途
	
	private String markDepartCode;//租车标记部门
	
	private String markOperater;//租车操作人
	
	private String markOperateTime;//租车标记时间

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDepartureOrg() {
		return departureOrg;
	}

	public void setDepartureOrg(String departureOrg) {
		this.departureOrg = departureOrg;
	}

	public String getArriveOrg() {
		return arriveOrg;
	}

	public void setArriveOrg(String arriveOrg) {
		this.arriveOrg = arriveOrg;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getPickupToDoor() {
		return pickupToDoor;
	}

	public void setPickupToDoor(String pickupToDoor) {
		this.pickupToDoor = pickupToDoor;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public Date getBillGenerateTime() {
		return billGenerateTime;
	}

	public void setBillGenerateTime(Date billGenerateTime) {
		this.billGenerateTime = billGenerateTime;
	}

	public String getTempRentalMarkNo() {
		return tempRentalMarkNo;
	}

	public void setTempRentalMarkNo(String tempRentalMarkNo) {
		this.tempRentalMarkNo = tempRentalMarkNo;
	}

	public String getRentalUseType() {
		return rentalUseType;
	}

	public void setRentalUseType(String rentalUseType) {
		this.rentalUseType = rentalUseType;
	}

	public String getMarkDepartCode() {
		return markDepartCode;
	}

	public void setMarkDepartCode(String markDepartCode) {
		this.markDepartCode = markDepartCode;
	}

	public String getMarkOperater() {
		return markOperater;
	}

	public void setMarkOperater(String markOperater) {
		this.markOperater = markOperater;
	}

	public String getMarkOperateTime() {
		return markOperateTime;
	}

	public void setMarkOperateTime(String markOperateTime) {
		this.markOperateTime = markOperateTime;
	}
	
}