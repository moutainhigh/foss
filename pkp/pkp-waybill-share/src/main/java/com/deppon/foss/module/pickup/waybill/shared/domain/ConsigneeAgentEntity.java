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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/ConsigneeAgentEntity.java
 * 
 * FILE NAME        	: ConsigneeAgentEntity.java
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
 * 
 * 收货人代理
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:41:44
 */
public class ConsigneeAgentEntity extends BaseEntity {


    /**
     *生成序列化标识
     *（用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = -1167714724096523247L;

    // 运单号
    private String waybillNo;

    // 真实收货人
    private String realConsignee;

    // 收货人电话
    private String consigneePhone;

    // 创建人
    private String createUserName;

    // 创建人编号
    private String createUserCode;

    // 创建时间
    private Date createTime;

    /**
     * @return the waybillNo
     */
    public String getWaybillNo() {
	return waybillNo;
    }

    /**
     * @param waybillNo
     *            the waybillNo to set
     */
    public void setWaybillNo(String waybillNo) {
	this.waybillNo = waybillNo;
    }

    /**
     * @return the realConsignee
     */
    public String getRealConsignee() {
	return realConsignee;
    }

    /**
     * @param realConsignee
     *            the realConsignee to set
     */
    public void setRealConsignee(String realConsignee) {
	this.realConsignee = realConsignee;
    }

    /**
     * @return the consigneePhone
     */
    public String getConsigneePhone() {
	return consigneePhone;
    }

    /**
     * @param consigneePhone
     *            the consigneePhone to set
     */
    public void setConsigneePhone(String consigneePhone) {
	this.consigneePhone = consigneePhone;
    }

    /**
     * @return the createUserName
     */
    public String getCreateUserName() {
	return createUserName;
    }

    /**
     * @param createUserName
     *            the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
    }

    /**
     * @return the createUserCode
     */
    public String getCreateUserCode() {
	return createUserCode;
    }

    /**
     * @param createUserCode
     *            the createUserCode to set
     */
    public void setCreateUserCode(String createUserCode) {
	this.createUserCode = createUserCode;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
	return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

}