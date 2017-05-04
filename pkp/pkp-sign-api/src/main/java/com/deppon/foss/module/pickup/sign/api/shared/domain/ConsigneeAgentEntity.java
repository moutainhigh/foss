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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/ConsigneeAgentEntity.java
 * 
 * FILE NAME        	: ConsigneeAgentEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改他人签收凭证
 * 
 * @date 2012-10-16 上午10:13:42
 * @since
 * @version
 */
public class ConsigneeAgentEntity implements Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    private String id;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 凭证人
     */
    private String realConsignee;

    /**
     * 凭证人电话
     */
    private String consigneePhone;

    /**
     * 创建人名字
     */
    private String createUserName;

    /**
     * 创建人编码
     */
    private String createUserCode;

    /**
     * 创建 时间
     */
    private Date createTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo the waybillNo to set
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
	 * @param realConsignee the realConsignee to set
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
	 * @param consigneePhone the consigneePhone to set
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
	 * @param createUserName the createUserName to set
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
	 * @param createUserCode the createUserCode to set
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
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



}