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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/GoodsInfoDto.java
 * 
 * FILE NAME        	: GoodsInfoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The Class GoodsInfoDto.
 *
 * @货量查询DTO
 * @author 043258-foss-zhaobin
 * @2012-10-11
 */
public class GoodsInfoDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 货物名称. */
	private String goodsName;
	
	/** 货物重量. */
	private BigDecimal goodsWeight;
	
	/** 货物体积. */
	private BigDecimal goodsVolume;
	
	/** 货物总件数. */
	private Integer goodsQtyTotal;
	
	/** 在途总件数. */
	private Integer goodsHandoverTotal;
	
	/** 到付金额. */
	private BigDecimal toPayAmount;
	
	/** 货物库存总件数. */
	private Integer goodsStoreTotal;
	
	/** 货物库位总件数. */
	private Integer goodsStoringTotal;
	
	/** 异常总件数. */
	private Integer exceptionTotal;
	
	/** 运输方式. */
	private String transportType;
	
	/** 收货客户名称. */
	private String receiveCustomerName;
	
	/** 收货客户手机. */
	private String receiveCustomerMobilephone;
	
	/** 收货客户电话. */
	private String receiveCustomerPhone;
	
	/** 收货省份. */
	private String receiveCustomerProvCode;
	
	/** 收货市. */
	private String receiveCustomerCityCode;
	
	/** 收货区. */
	private String receiveCustomerDistCode;
	
	/** 收货具体地址. */
	private String receiveCustomerAddress;
	
	/** 收货具体地址备注. */
	private String receiveCustomerAddressNote;
	
	/** 预计到达时间. */
	private Date preArriveTime;
	
	/** 是否联系客户. */
	private String notificationResult;
	
	/** 预计派送/提货时间. */
	private Date preCustomerPickupTime;
	
	/** 提货方式. */
	private String receiveMethod;
	
	/** 排单件数. */
	private Integer arrangementTotal;

	/**
	 * Gets the goods handover total.
	 *
	 * @return the goodsHandoverTotal
	 */
	public Integer getGoodsHandoverTotal() {
		return goodsHandoverTotal;
	}

	/**
	 * Sets the goods handover total.
	 *
	 * @param goodsHandoverTotal the goodsHandoverTotal to see
	 */
	public void setGoodsHandoverTotal(Integer goodsHandoverTotal) {
		this.goodsHandoverTotal = goodsHandoverTotal;
	}

	/**
	 * Gets the to pay amount.
	 *
	 * @return the toPayAmount
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * Sets the to pay amount.
	 *
	 * @param toPayAmount the toPayAmount to see
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the goods name.
	 *
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * Sets the goods name.
	 *
	 * @param goodsName the goodsName to see
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * Gets the goods qty total.
	 *
	 * @return the goodsQtyTotal
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the goods qty total.
	 *
	 * @param goodsQtyTotal the goodsQtyTotal to see
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the receive customer name.
	 *
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 *
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer mobilephone.
	 *
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 *
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the receive customer phone.
	 *
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 *
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the receive customer address.
	 *
	 * @return the receiveCustomerAddress
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	/**
	 * Sets the receive customer address.
	 *
	 * @param receiveCustomerAddress the receiveCustomerAddress to see
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	/**
	 * Gets the pre customer pickup time.
	 *
	 * @return the preCustomerPickupTime
	 */
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}

	/**
	 * Sets the pre customer pickup time.
	 *
	 * @param preCustomerPickupTime the preCustomerPickupTime to see
	 */
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}

	/**
	 * Gets the receive method.
	 *
	 * @return the receiveMethod
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the receive method.
	 *
	 * @param receiveMethod the receiveMethod to see
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public String getProduct() {
		return transportType;
	}

	/**
	 * Sets the product.
	 *
	 * @param product the product to see
	 */
	public void setProduct(String product) {
		this.transportType = product;
	}

	/**
	 * Gets the pre arrive time.
	 *
	 * @return the preArriveTime
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}

	/**
	 * Sets the pre arrive time.
	 *
	 * @param preArriveTime the preArriveTime to see
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	/**
	 * Gets the transport type.
	 *
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * Sets the transport type.
	 *
	 * @param transportType the transportType to see
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * Gets the notificationtype.
	 *
	 * @return the notificationtype
	 */
	public String getNotificationtype() {
		return notificationResult;
	}

	/**
	 * Sets the notificationtype.
	 *
	 * @param notificationtype the notificationtype to see
	 */
	public void setNotificationtype(String notificationtype) {
		this.notificationResult = notificationtype;
	}

	/**
	 * Gets the goods store total.
	 *
	 * @return the goodsStoreTotal
	 */
	public Integer getGoodsStoreTotal() {
		return goodsStoreTotal;
	}

	/**
	 * Sets the goods store total.
	 *
	 * @param goodsStoreTotal the goodsStoreTotal to see
	 */
	public void setGoodsStoreTotal(Integer goodsStoreTotal) {
		this.goodsStoreTotal = goodsStoreTotal;
	}

	/**
	 * Gets the exception total.
	 *
	 * @return the exceptionTotal
	 */
	public Integer getExceptionTotal() {
		return exceptionTotal;
	}

	/**
	 * Sets the exception total.
	 *
	 * @param exceptionTotal the exceptionTotal to see
	 */
	public void setExceptionTotal(Integer exceptionTotal) {
		this.exceptionTotal = exceptionTotal;
	}

	/**
	 * Gets the goods weight.
	 *
	 * @return the goodsWeight
	 */
	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	/**
	 * Sets the goods weight.
	 *
	 * @param goodsWeight the goodsWeight to see
	 */
	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	/**
	 * Gets the goods volume.
	 *
	 * @return the goodsVolume
	 */
	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	/**
	 * Sets the goods volume.
	 *
	 * @param goodsVolume the goodsVolume to see
	 */
	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}

	/**
	 * Gets the arrangement total.
	 *
	 * @return the arrangementTotal
	 */
	public Integer getArrangementTotal() {
		return arrangementTotal;
	}

	/**
	 * Sets the arrangement total.
	 *
	 * @param arrangementTotal the arrangementTotal to see
	 */
	public void setArrangementTotal(Integer arrangementTotal) {
		this.arrangementTotal = arrangementTotal;
	}

	/**
	 * Gets the receive customer prov code.
	 *
	 * @return the receive customer prov code
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	/**
	 * Sets the receive customer prov code.
	 *
	 * @param receiveCustomerProvCode the new receive customer prov code
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	/**
	 * Gets the receive customer city code.
	 *
	 * @return the receive customer city code
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	/**
	 * Sets the receive customer city code.
	 *
	 * @param receiveCustomerCityCode the new receive customer city code
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	/**
	 * Gets the receive customer dist code.
	 *
	 * @return the receive customer dist code
	 */
	public String getReceiveCustomerDistCode() {
		return receiveCustomerDistCode;
	}

	/**
	 * Sets the receive customer dist code.
	 *
	 * @param receiveCustomerDistCode the new receive customer dist code
	 */
	public void setReceiveCustomerDistCode(String receiveCustomerDistCode) {
		this.receiveCustomerDistCode = receiveCustomerDistCode;
	}

	public Integer getGoodsStoringTotal() {
		return goodsStoringTotal;
	}

	public void setGoodsStoringTotal(Integer goodsStoringTotal) {
		this.goodsStoringTotal = goodsStoringTotal;
	}

	public String getNotificationResult() {
		return notificationResult;
	}

	public void setNotificationResult(String notificationResult) {
		this.notificationResult = notificationResult;
	}

	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}

	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
}