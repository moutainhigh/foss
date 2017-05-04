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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/AbandonGoodsApplicationDto.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationDto.java
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
 * 弃货展现dto
 */
public class AbandonGoodsApplicationDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	// 异常_ID--页面
	private String exceptionId;
	// 运单号
	private String waybillNo;
	// 始发部门CODE
	private String origOrgCode;
	// 始发部门名称
	private String origOrgName;
	// 发货人(运单--发货客户名称)
	private String deliveryCustomerName;
	// 发货人(运单--发货客户名称)
	private String deliveryCustomerContact;
	// 发货人手机(运单--发货客户手机)
	private String deliveryCustomerMobilephone;
	// 体积(方)--(运单--货物总体积)
	private BigDecimal goodsVolumeTotal;
	// 入库时间--(运单库存--入库时间)
	private Date inStockTime;
	// 仓储时长(天)--前台页面获得
	private String storageDay;
	// 操作人
	private String operator;

	// 弃货类型--（页面提交==客户弃货类型）
	private String abandonedgoodsType;

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode the origOrgCode to see
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName the origOrgName to see
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return the deliveryCustomerName
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * @param deliveryCustomerName the deliveryCustomerName to see
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * @return the deliveryCustomerMobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * @param deliveryCustomerMobilephone the deliveryCustomerMobilephone to see
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * @return the goodsVolumeTotal
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * @param goodsVolumeTotal the goodsVolumeTotal to see
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * @return the inStockTime
	 */
	public Date getInStockTime() {
		return inStockTime;
	}

	/**
	 * @param inStockTime the inStockTime to see
	 */
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to see
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the exceptionId
	 */
	public String getExceptionId() {
		return exceptionId;
	}

	/**
	 * @param exceptionId the exceptionId to see
	 */
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	/**
	 * @return the storageDay
	 */
	public String getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to see
	 */
	public void setStorageDay(String storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the abandonedgoodsType
	 */
	public String getAbandonedgoodsType() {
		return abandonedgoodsType;
	}

	/**
	 * @param abandonedgoodsType the abandonedgoodsType to see
	 */
	public void setAbandonedgoodsType(String abandonedgoodsType) {
		this.abandonedgoodsType = abandonedgoodsType;
	}

	/**
	 * @return deliveryCustomerContact : return the property deliveryCustomerContact.
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * @param deliveryCustomerContact : set the property deliveryCustomerContact.
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

}