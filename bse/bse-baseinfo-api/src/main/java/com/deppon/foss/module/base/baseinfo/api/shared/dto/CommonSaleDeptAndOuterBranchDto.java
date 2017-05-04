/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonSaleDeptAndOuterBranchDto.java
 * 
 * FILE NAME        	: CommonSaleDeptAndOuterBranchDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;

/**
 * 查询营业部和偏线代理网点
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-26 下午6:06:52
 */
public class CommonSaleDeptAndOuterBranchDto extends CommonOrgEntity {

	private static final long serialVersionUID = -4283419574247593427L;

	private String id;
	/**
	 * 查询参数
	 */
	private String queryParam;
	/**
	 * 部门编码
	 */
	private String code;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 网点类型
	 */
	private String type;

	/**
	 * 网点类型集合
	 */
	private List<String> types;
	/**
	 * 代理简称
	 */
	private String simpleName;
	/**
	 * 拼音
	 */
	private String pinYin;
	/**
	 * 可出发
	 */
	private String leave;
	/**
	 * 可到达
	 */
	private String arrive;
	/**
	 * 是否驻地部门
	 */
	private String station;
	/**
	 * 可中转
	 */
	private String transfer;
	/**
	 * 所属集中开单组
	 */
	private String billingGroup;
	/**
	 * 驻地营业部所属外场
	 */
	private String transferCenter;
	/**
	 * 可自提
	 */
	private String pickupSelf;
	/**
	 * 可派送
	 */
	private String delivery;
	/**
	 * 可空运到达
	 */
	private String airArrive;
	/**
	 * 可汽运到达
	 */
	private String truckArrive;
	/**
	 * 是否启用
	 */
	private String active;
	/**
	 * 是否在集中接送货范围内
	 */
	private String inCentralizedShuttle;
	/**
	 * 是否可开装卸费
	 */
	private String canPayServiceFee;
	/**
	 * 是否可返回签单
	 */
	private String canReturnSignBill;
	/**
	 * 是否可货到付款
	 */
	private String canCashOnDelivery;
	/**
	 * 是否可代收货款
	 */
	private String canAgentCollected;
	/**
	 * 转货部门
	 */
	private String transferGoodDept;
	/**
	 * 提货网点编码，为4位数字，当为到达部门时必填,用户打标签使用
	 */
	private String stationNumber;
	/**
	 * 提货网点编码，为4位数字，当为到达部门时必填,用户打标签使用
	 */
	private String saleDeptCode;
	/**
	 * 部门编码集合
	 */
	private List<String> codes;
	/**
	 * 产品编码
	 */
	private String productCode;

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public String getSaleDeptCode() {
		return saleDeptCode;
	}

	public void setSaleDeptCode(String saleDeptCode) {
		this.saleDeptCode = saleDeptCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getBillingGroup() {
		return billingGroup;
	}

	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	public String getPickupSelf() {
		return pickupSelf;
	}

	public void setPickupSelf(String pickupSelf) {
		this.pickupSelf = pickupSelf;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getAirArrive() {
		return airArrive;
	}

	public void setAirArrive(String airArrive) {
		this.airArrive = airArrive;
	}

	public String getTruckArrive() {
		return truckArrive;
	}

	public void setTruckArrive(String truckArrive) {
		this.truckArrive = truckArrive;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getInCentralizedShuttle() {
		return inCentralizedShuttle;
	}

	public void setInCentralizedShuttle(String inCentralizedShuttle) {
		this.inCentralizedShuttle = inCentralizedShuttle;
	}

	public String getCanPayServiceFee() {
		return canPayServiceFee;
	}

	public void setCanPayServiceFee(String canPayServiceFee) {
		this.canPayServiceFee = canPayServiceFee;
	}

	public String getCanReturnSignBill() {
		return canReturnSignBill;
	}

	public void setCanReturnSignBill(String canReturnSignBill) {
		this.canReturnSignBill = canReturnSignBill;
	}

	public String getCanCashOnDelivery() {
		return canCashOnDelivery;
	}

	public void setCanCashOnDelivery(String canCashOnDelivery) {
		this.canCashOnDelivery = canCashOnDelivery;
	}

	public String getCanAgentCollected() {
		return canAgentCollected;
	}

	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}

	public String getTransferGoodDept() {
		return transferGoodDept;
	}

	public void setTransferGoodDept(String transferGoodDept) {
		this.transferGoodDept = transferGoodDept;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

}
