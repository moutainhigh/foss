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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/BranchQueryVo.java
 * 
 * FILE NAME        	: BranchQueryVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.vo;

import java.io.Serializable;


/**
 * 
 * 提货网点
 * 
 * @author WangQianJin
 * @date 2013-07-25
 */
public class BranchQueryVo implements Serializable {

	
	/**
	 * 查询网点名称
	 */
	private String queryName;
	
	/**
	 * 网点名称
	 */
	private String name;


	/**
	 * 网点编号
	 */
	private String code;
	
	/**
	 * 目的站
	 */
	private String targetOrgName;
	
	/**
	 * 省份
	 */
	private String province;
	
	
	/**
	 * 城市Code
	 */
	private String city;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 网点类型
	 */
	private String branchType;
	
	/**
	 * 正单名称
	 */
	private String airBillName;
	
	/**
	 * 正单联系电话
	 */
	private String airBillPhone;
	
	
	/**
	 * 网点联系电话
	 */
	private String phone;
	
	/**
	 * 网点地址
	 */
	private String branchAddress;
	
	/**
	 * 搜索关键字
	 */
	private String key;
	

	/**
	 * 查询时网点类型
	 */
	private String branchTypeSeach;
	
	/**
	 * UI绑定查询条件 自提
	 */
	private Boolean chbPickup;
	
	/**
	 * UI绑定查询条件 派送
	 */
	private Boolean chbDeliver;
	
	/**
	 * 查询结果  自提
	 */
	private String chbPickupTwo;
	
	
	/**
	 *  UI绑定查询结果显示  派送
	 */
	private Boolean chbDeliverUi;
	
	/**
	 * UI绑定查询结果显示 自提
	 */
	private Boolean chbPickupUi;
	
	
	/**
	 *  查询结果  派送
	 */
	private String chbDeliverTwo;
	
	/**
	 * 查询结果 ：货到付款
	 */
	private String chbArrivePayment;
	
	/**
	 * 查询结果 ：返单签收
	 */
	private String chbReturnBill;
	
	/**
	 * 查询结果 ：代收货款
	 */
	private String chbCod;
	
	/**
	 * 绑定页面结果：货到付款
	 */	
	private Boolean chbArrivePaymentUi;
	
	/**
	 * 绑定页面结果：返单签收
	 */
	private Boolean chbReturnBillUi;
	
	/**
	 * 绑定页面结果：代收货款
	 */
	private Boolean chbCodUi;
	
	/**
	 * 是否可开装卸费
	 */
	private String chbPayServicefee;
	
	/**
	 * 精准卡航
	 */
	private Boolean chbFLF;
	
	/**
	 * 精准城运
	 */
	private Boolean chbFSF;
	
	/**
	 * 精准汽运（长）
	 */
	private Boolean chbLRF;
	
	/**
	 * 精准汽运（短）
	 */
	private Boolean chbSRF;
	
	/**
	 * 精准空运
	 */
	private Boolean chbAF;
	
	/**
	 * 自提区域
	 */
	private String pickupAreaDesc;
	
	/**
	 * 派送区域
	 */
	private String deliveryAreaDesc;
	
	private int index;
	
	private int rowIndex;
	
	private String isFind;
	
	//用于中文显示的网点类别
	private String branchTypeCH;
	
	private String isAirport;
	
	//自提区域扩展标志
	private String pickUpAreaIsExpand;
	
	//派送区域扩展标志
	private String deliveryAreaIsExpand;
    
	/**
	 * @return the targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}

	/**
	 * @param targetOrgName the targetOrgName to set
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	/**
	 * 网点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 网点名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 网点编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 网点编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public String getAirBillName() {
		return airBillName;
	}

	public void setAirBillName(String airBillName) {
		this.airBillName = airBillName;
	}

	public String getAirBillPhone() {
		return airBillPhone;
	}

	public void setAirBillPhone(String airBillPhone) {
		this.airBillPhone = airBillPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBranchTypeSeach() {
		return branchTypeSeach;
	}

	public void setBranchTypeSeach(String branchTypeSeach) {
		this.branchTypeSeach = branchTypeSeach;
	}

	public Boolean getChbFLF() {
		return chbFLF;
	}

	public void setChbFLF(Boolean chbFLF) {
		this.chbFLF = chbFLF;
	}

	public Boolean getChbFSF() {
		return chbFSF;
	}

	public void setChbFSF(Boolean chbFSF) {
		this.chbFSF = chbFSF;
	}

	public Boolean getChbLRF() {
		return chbLRF;
	}

	public void setChbLRF(Boolean chbLRF) {
		this.chbLRF = chbLRF;
	}

	public Boolean getChbSRF() {
		return chbSRF;
	}

	public void setChbSRF(Boolean chbSRF) {
		this.chbSRF = chbSRF;
	}

	public Boolean getChbAF() {
		return chbAF;
	}

	public void setChbAF(Boolean chbAF) {
		this.chbAF = chbAF;
	}

	public String getPickupAreaDesc() {
		return pickupAreaDesc;
	}

	public void setPickupAreaDesc(String pickupAreaDesc) {
		this.pickupAreaDesc = pickupAreaDesc;
	}

	public String getDeliveryAreaDesc() {
		return deliveryAreaDesc;
	}

	public void setDeliveryAreaDesc(String deliveryAreaDesc) {
		this.deliveryAreaDesc = deliveryAreaDesc;
	}

	public String getChbPickupTwo() {
		return chbPickupTwo;
	}

	public void setChbPickupTwo(String chbPickupTwo) {
		this.chbPickupTwo = chbPickupTwo;
	}

	public String getChbDeliverTwo() {
		return chbDeliverTwo;
	}

	public void setChbDeliverTwo(String chbDeliverTwo) {
		this.chbDeliverTwo = chbDeliverTwo;
	}

	public String getChbArrivePayment() {
		return chbArrivePayment;
	}

	public void setChbArrivePayment(String chbArrivePayment) {
		this.chbArrivePayment = chbArrivePayment;
	}

	public String getChbReturnBill() {
		return chbReturnBill;
	}

	public void setChbReturnBill(String chbReturnBill) {
		this.chbReturnBill = chbReturnBill;
	}

	public String getChbCod() {
		return chbCod;
	}

	public void setChbCod(String chbCod) {
		this.chbCod = chbCod;
	}

	public String getChbPayServicefee() {
		return chbPayServicefee;
	}

	public void setChbPayServicefee(String chbPayServicefee) {
		this.chbPayServicefee = chbPayServicefee;
	}

	public Boolean getChbPickup() {
		return chbPickup;
	}

	public void setChbPickup(Boolean chbPickup) {
		this.chbPickup = chbPickup;
	}
	
	public String getBranchTypeCH() {
		return branchTypeCH;
	}

	public void setBranchTypeCH(String branchTypeCH) {
		this.branchTypeCH = branchTypeCH;
	}

	public Boolean getChbDeliver() {
		return chbDeliver;
	}

	public void setChbDeliver(Boolean chbDeliver) {
		this.chbDeliver = chbDeliver;
	}

	public Boolean getChbDeliverUi() {
		return chbDeliverUi;
	}

	public void setChbDeliverUi(Boolean chbDeliverUi) {
		this.chbDeliverUi = chbDeliverUi;
	}

	public Boolean getChbPickupUi() {
		return chbPickupUi;
	}

	public void setChbPickupUi(Boolean chbPickupUi) {
		this.chbPickupUi = chbPickupUi;
	}

	public Boolean getChbArrivePaymentUi() {
		return chbArrivePaymentUi;
	}

	public void setChbArrivePaymentUi(Boolean chbArrivePaymentUi) {
		this.chbArrivePaymentUi = chbArrivePaymentUi;
	}

	public Boolean getChbReturnBillUi() {
		return chbReturnBillUi;
	}

	public void setChbReturnBillUi(Boolean chbReturnBillUi) {
		this.chbReturnBillUi = chbReturnBillUi;
	}

	public Boolean getChbCodUi() {
		return chbCodUi;
	}

	public void setChbCodUi(Boolean chbCodUi) {
		this.chbCodUi = chbCodUi;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getIsFind() {
		return isFind;
	}

	public void setIsFind(String isFind) {
		this.isFind = isFind;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getIsAirport() {
		return isAirport;
	}

	public void setIsAirport(String isAirport) {
		this.isAirport = isAirport;
	}

	public String getPickUpAreaIsExpand() {
		return pickUpAreaIsExpand;
	}

	public void setPickUpAreaIsExpand(String pickUpAreaIsExpand) {
		this.pickUpAreaIsExpand = pickUpAreaIsExpand;
	}

	public String getDeliveryAreaIsExpand() {
		return deliveryAreaIsExpand;
	}

	public void setDeliveryAreaIsExpand(String deliveryAreaIsExpand) {
		this.deliveryAreaIsExpand = deliveryAreaIsExpand;
	}
	
}