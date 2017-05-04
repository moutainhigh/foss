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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/SalesStatementByComplexCondationDto.java
 * 
 * FILE NAME        	: SalesStatementByComplexCondationDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.util.Date;

/**
 * 根据不同的查询条件组合查询结果列表，结果为封装为dto的集合：1、 派送出库时间开始日期： 2、 派送出库时间结束日期： 3、 收货日期开始时间： 4、
 * 收货日期结束时间： 5、 托运人： 实际发货人 6、 运输性质（产品）： 7、 付款方式： 8、 收货部门： 9、 制单部门： 10、
 * 派送部门：到达派送部门 11、 配载部门：始发配载部门 12、 收货人：开单收货人 13、 目的站：开单目的站 14、 运单号：.
 * 
 * @author 026113-foss-linwensheng
 */
public class SalesStatementByComplexCondationDto {

	/** 派送出库时间开始日期. */
	private Date startDeliverTime;

	/** 派送出库时间结束日期. */
	private Date endDeliverTime;

	/** 收货日期开始时间. */
	private Date startBillTime;

	/** 收货结束日期时间. */
	private Date endBillTime;

	/** 托运人： 实际发货人. */
	private String deliveryCustomerContact;

	/** 运输性质（产品）. */
	private String productCode;

	/** 付款方式. */
	private String paidMethod;

	/** 制单部门. */
	private String createOrgCode;

	/** 派送部门. */
	private String lastLoadOrgCode;

	/** 配载部门：始发配载部门. */
	private String loadOrgCode;

	/** 收货人：开单收货人. */
	private String receiveCustomerContact;

	/** 目的站：开单目的站. */
	private String targetOrgCode;

	/** 运单号. */
	private String waybillNo;

	/**
	 * Gets the start deliver time.
	 * 
	 * @return the start deliver time
	 */
	public Date getStartDeliverTime() {
		return startDeliverTime;
	}

	/**
	 * Sets the start deliver time.
	 * 
	 * @param startDeliverTime
	 *            the new start deliver time
	 */
	public void setStartDeliverTime(Date startDeliverTime) {
		this.startDeliverTime = startDeliverTime;
	}

	/**
	 * Gets the end deliver time.
	 * 
	 * @return the end deliver time
	 */
	public Date getEndDeliverTime() {
		return endDeliverTime;
	}

	/**
	 * Sets the end deliver time.
	 * 
	 * @param endDeliverTime
	 *            the new end deliver time
	 */
	public void setEndDeliverTime(Date endDeliverTime) {
		this.endDeliverTime = endDeliverTime;
	}

	/**
	 * Gets the start bill time.
	 * 
	 * @return the start bill time
	 */
	public Date getStartBillTime() {
		return startBillTime;
	}

	/**
	 * Sets the start bill time.
	 * 
	 * @param startBillTime
	 *            the new start bill time
	 */
	public void setStartBillTime(Date startBillTime) {
		this.startBillTime = startBillTime;
	}

	/**
	 * Gets the end bill time.
	 * 
	 * @return the end bill time
	 */
	public Date getEndBillTime() {
		return endBillTime;
	}

	/**
	 * Sets the end bill time.
	 * 
	 * @param endBillTime
	 *            the new end bill time
	 */
	public void setEndBillTime(Date endBillTime) {
		this.endBillTime = endBillTime;
	}

	/**
	 * Gets the delivery customer contact.
	 * 
	 * @return the delivery customer contact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * Sets the delivery customer contact.
	 * 
	 * @param deliveryCustomerContact
	 *            the new delivery customer contact
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * Gets the product code.
	 * 
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 * 
	 * @param productCode
	 *            the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the paid method.
	 * 
	 * @return the paid method
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * Sets the paid method.
	 * 
	 * @param paidMethod
	 *            the new paid method
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * Gets the creates the org code.
	 * 
	 * @return the creates the org code
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * Sets the creates the org code.
	 * 
	 * @param createOrgCode
	 *            the new creates the org code
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * Gets the last load org code.
	 * 
	 * @return the last load org code
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * Sets the last load org code.
	 * 
	 * @param lastLoadOrgCode
	 *            the new last load org code
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * Gets the load org code.
	 * 
	 * @return the load org code
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * Sets the load org code.
	 * 
	 * @param loadOrgCode
	 *            the new load org code
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * Gets the receive customer contact.
	 * 
	 * @return the receive customer contact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * Sets the receive customer contact.
	 * 
	 * @param receiveCustomerContact
	 *            the new receive customer contact
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * Gets the target org code.
	 * 
	 * @return the target org code
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * Sets the target org code.
	 * 
	 * @param targetOrgCode
	 *            the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * Gets the waybill no.
	 * 
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 * 
	 * @param waybillNo
	 *            the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

}