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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/LabeledGoodPDAEntity.java
 * 
 * FILE NAME        	: LabeledGoodPDAEntity.java
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
 * PDA提交货签信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-12-11 下午2:18:01,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-12-11 下午2:18:01
 * @since
 * @version
 */
public class LabeledGoodPDAEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5362281412091714573L;

	/**
	 * 流水号
	 */
	private String serialNo;

	/**
	 * 
	 * 状态
	 */
	private String active;

	/**
	 * 运单ID
	 */
	private String waybillPDAId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 收货时间
	 */
	private Date billTime;

	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
     * 创建机构
     */
    private String createOrgCode;
    
    /**
     * 修改机构
     */
    private String modifyOrgCode;
    
    /**
     * 修改人姓名
     */
    private String modifyUserCode;
    
    /**
     * 创建人人姓名
     */
    private String createUserCode;

	/**
	 * @return the serialNo .
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set.
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the waybillPDAId .
	 */
	public String getWaybillPDAId() {
		return waybillPDAId;
	}

	/**
	 * @param waybillPDAId
	 *            the waybillPDAId to set.
	 */
	public void setWaybillPDAId(String waybillPDAId) {
		this.waybillPDAId = waybillPDAId;
	}

	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime
	 *            the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return  the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	
	/**
	 * @param createOrgCode the createOrgCode to set
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	
	/**
	 * @return  the modifyOrgCode
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	
	/**
	 * @param modifyOrgCode the modifyOrgCode to set
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	
	/**
	 * @return  the modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	
	/**
	 * @param modifyUserCode the modifyUserCode to set
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	
	/**
	 * @return  the createUserCode
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

}