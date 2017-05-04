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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/HisDeliveryCusEntity.java
 * 
 * FILE NAME        	: HisDeliveryCusEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 历史发货客户
 * @author 026123-foss-lifengteng
 * @date 2012-12-24 下午8:29:20
 */
public class HisDeliveryCusEntity extends BaseEntity {

	/**
	 * 生成序列化标识
	 */
	private static final long serialVersionUID = 9049146915398272213L;

	/**
	 * 发货客户手机号码
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户电话号码
	 */
	private String deliveryCustomerPhone;

	/**
	 * 发货联系人
	 */
	private String deliveryCustomerContact;

	/**
	 * 发货国家
	 */
	private String deliveryCustomerNationCode;

	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;

	/**
	 * 发货城市
	 */
	private String deliveryCustomerCityCode;

	/**
	 * 发货区域
	 */
	private String deliveryCustomerDisCode;

	/**
	 * 发货地址
	 */
	private String deliveryCustomerAddress;
	
	/**
	 * 发货地址备注
	 */
	private String deliveryCustomerAddressNote;

	/**
	 * 创建时间
	 */
	private Date createTime;

	
	/**
	 * @return the deliveryCustomerMobilephone .
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	
	/**
	 *@param deliveryCustomerMobilephone the deliveryCustomerMobilephone to set.
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	
	/**
	 * @return the deliveryCustomerPhone .
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	
	/**
	 *@param deliveryCustomerPhone the deliveryCustomerPhone to set.
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	
	/**
	 * @return the deliveryCustomerContact .
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	
	/**
	 *@param deliveryCustomerContact the deliveryCustomerContact to set.
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	
	/**
	 * @return the deliveryCustomerNationCode .
	 */
	public String getDeliveryCustomerNationCode() {
		return deliveryCustomerNationCode;
	}

	
	/**
	 *@param deliveryCustomerNationCode the deliveryCustomerNationCode to set.
	 */
	public void setDeliveryCustomerNationCode(String deliveryCustomerNationCode) {
		this.deliveryCustomerNationCode = deliveryCustomerNationCode;
	}

	
	/**
	 * @return the deliveryCustomerProvCode .
	 */
	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	
	/**
	 *@param deliveryCustomerProvCode the deliveryCustomerProvCode to set.
	 */
	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}

	
	/**
	 * @return the deliveryCustomerCityCode .
	 */
	public String getDeliveryCustomerCityCode() {
		return deliveryCustomerCityCode;
	}

	
	/**
	 *@param deliveryCustomerCityCode the deliveryCustomerCityCode to set.
	 */
	public void setDeliveryCustomerCityCode(String deliveryCustomerCityCode) {
		this.deliveryCustomerCityCode = deliveryCustomerCityCode;
	}

	
	/**
	 * @return the deliveryCustomerDisCode .
	 */
	public String getDeliveryCustomerDisCode() {
		return deliveryCustomerDisCode;
	}

	
	/**
	 *@param deliveryCustomerDisCode the deliveryCustomerDisCode to set.
	 */
	public void setDeliveryCustomerDisCode(String deliveryCustomerDisCode) {
		this.deliveryCustomerDisCode = deliveryCustomerDisCode;
	}

	
	/**
	 * @return the deliveryCustomerAddress .
	 */
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	
	/**
	 *@param deliveryCustomerAddress the deliveryCustomerAddress to set.
	 */
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	
	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	
	/**
	 *@param createTime the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getDeliveryCustomerAddressNote() {
		return deliveryCustomerAddressNote;
	}


	public void setDeliveryCustomerAddressNote(String deliveryCustomerAddressNote) {
		this.deliveryCustomerAddressNote = deliveryCustomerAddressNote;
	}
}