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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/SignArriveSheetDto.java
 * 
 * FILE NAME        	: SignArriveSheetDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;

/**
 * 签收出库查询返回结果
 * @author foss-meiying
 * @date 2012-10-17 上午11:03:31
 * @since
 * @version
 */
public class SignArriveSheetDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  到达联编号
	 */
	private String arrivesheetNo;
	/**
	 *  提货人名称
	 */
	private String deliverymanName; 
	/**
	 *  证件号码
	 */
	private String identifyCode; 
	/**
	 *  证件类型
	 */
	private String identifyType;
	/**
	 *  到达联件数
	 */
	private Integer arriveSheetGoodsQty;
	/**
	 *  是否整车运单
	 */
	private String isWholeVehicle;
	/**
	 *  运输性质
	 */
	private String productCode;
	/**
	 *  库存件数
	 */
	private Integer stockGoodsQty;
	/**
	 * 订单号
	 */
	private String orderNo;
	private String isCurrentOrgCodeReceiveOrgCodeWVH;//  Y表示当前部门是收货部门 
	
	/**
	 *  收货客户联系人名称
	 */
	private String receiveCustomerContact;
	
	/**
	 *服务器当前时间 
	 */
	private String serviceTime;
	
	/**
	 * 特殊增值服务字段
	 */
	private String  specialValueAddedService;
	
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 提货人名称.
	 *
	 * @return the 提货人名称
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the 提货人名称.
	 *
	 * @param deliverymanName the new 提货人名称
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the 证件号码.
	 *
	 * @return the 证件号码
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * Sets the 证件号码.
	 *
	 * @param identifyCode the new 证件号码
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * Gets the 到达联件数.
	 *
	 * @return the 到达联件数
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 到达联件数.
	 *
	 * @param arriveSheetGoodsQty the new 到达联件数
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * Gets the 是否整车运单.
	 *
	 * @return the 是否整车运单
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * Sets the 是否整车运单.
	 *
	 * @param isWholeVehicle the new 是否整车运单
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 库存件数.
	 *
	 * @return the 库存件数
	 */
	public Integer getStockGoodsQty() {
		return stockGoodsQty;
	}

	/**
	 * Sets the 库存件数.
	 *
	 * @param stockGoodsQty the new 库存件数
	 */
	public void setStockGoodsQty(Integer stockGoodsQty) {
		this.stockGoodsQty = stockGoodsQty;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * Sets the 证件类型.
	 *
	 * @param identifyType the new 证件类型
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 收货客户联系人名称.
	 *
	 * @return the 收货客户联系人名称
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * Sets the 收货客户联系人名称.
	 *
	 * @param receiveCustomerContact the new 收货客户联系人名称
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	
	public String getIsCurrentOrgCodeReceiveOrgCodeWVH() {
		return isCurrentOrgCodeReceiveOrgCodeWVH;
	}

	public void setIsCurrentOrgCodeReceiveOrgCodeWVH(
			String isCurrentOrgCodeReceiveOrgCodeWVH) {
		this.isCurrentOrgCodeReceiveOrgCodeWVH = isCurrentOrgCodeReceiveOrgCodeWVH;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getSpecialValueAddedService() {
		return specialValueAddedService;
	}

	public void setSpecialValueAddedService(String specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

}