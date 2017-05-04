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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/HisReceiveCusEntity.java
 * 
 * FILE NAME        	: HisReceiveCusEntity.java
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
 * 历史收货客户
 * @author foss-sunrui
 * @date 2012-10-31 上午10:54:52
 */
public class HisReceiveCusEntity extends BaseEntity{

    /** 
     * 序列化标识.
     */
    private static final long serialVersionUID = 3718207026554641946L;

    /**
     * 历史客户ID.
     */
    private String id;

    /**
     * 收货客户手机
     */
    private String receiveCustomerMobilephone;

    /** 
     * 收货客户电话号码
     */
    private String receiveCustomerPhone;

    /**
     *  收货联系人
     */
    private String receiveCustomerContact;

    /**
     *  收货客户国家编码
     */
    private String receiveCustomerNationCode;

    /**
     *  收货客户省份编码
     */
    private String receiveCustomerProvCode;

    /**
     *  收货客户城市编码
     */
    private String receiveCustomerCityCode;

    /**
     *  收货客户区域编码
     */
    private String receiveCustomerDisCode;

    /**
     *  收货客户地址
     */
    private String receiveCustomerAddress;
    
    /**
     *  收货客户地址备注
     */
    private String receiveCustomerAddressNote;

    /** 
     * 创建时间
     */
    private Date createTime;

	
	/**
	 * @return the id .
	 */
	public String getId() {
		return id;
	}

	
	/**
	 *@param id the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return the receiveCustomerMobilephone .
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	
	/**
	 *@param receiveCustomerMobilephone the receiveCustomerMobilephone to set.
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	
	/**
	 * @return the receiveCustomerPhone .
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	
	/**
	 *@param receiveCustomerPhone the receiveCustomerPhone to set.
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	
	/**
	 * @return the receiveCustomerContact .
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	
	/**
	 *@param receiveCustomerContact the receiveCustomerContact to set.
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	
	/**
	 * @return the receiveCustomerNationCode .
	 */
	public String getReceiveCustomerNationCode() {
		return receiveCustomerNationCode;
	}

	
	/**
	 *@param receiveCustomerNationCode the receiveCustomerNationCode to set.
	 */
	public void setReceiveCustomerNationCode(String receiveCustomerNationCode) {
		this.receiveCustomerNationCode = receiveCustomerNationCode;
	}

	
	/**
	 * @return the receiveCustomerProvCode .
	 */
	public String getReceiveCustomerProvCode() {
		return receiveCustomerProvCode;
	}

	
	/**
	 *@param receiveCustomerProvCode the receiveCustomerProvCode to set.
	 */
	public void setReceiveCustomerProvCode(String receiveCustomerProvCode) {
		this.receiveCustomerProvCode = receiveCustomerProvCode;
	}

	
	/**
	 * @return the receiveCustomerCityCode .
	 */
	public String getReceiveCustomerCityCode() {
		return receiveCustomerCityCode;
	}

	
	/**
	 *@param receiveCustomerCityCode the receiveCustomerCityCode to set.
	 */
	public void setReceiveCustomerCityCode(String receiveCustomerCityCode) {
		this.receiveCustomerCityCode = receiveCustomerCityCode;
	}

	
	/**
	 * @return the receiveCustomerDisCode .
	 */
	public String getReceiveCustomerDisCode() {
		return receiveCustomerDisCode;
	}

	
	/**
	 *@param receiveCustomerDisCode the receiveCustomerDisCode to set.
	 */
	public void setReceiveCustomerDisCode(String receiveCustomerDisCode) {
		this.receiveCustomerDisCode = receiveCustomerDisCode;
	}

	
	/**
	 * @return the receiveCustomerAddress .
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	
	/**
	 *@param receiveCustomerAddress the receiveCustomerAddress to set.
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
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


	public String getReceiveCustomerAddressNote() {
		return receiveCustomerAddressNote;
	}


	public void setReceiveCustomerAddressNote(String receiveCustomerAddressNote) {
		this.receiveCustomerAddressNote = receiveCustomerAddressNote;
	}
}