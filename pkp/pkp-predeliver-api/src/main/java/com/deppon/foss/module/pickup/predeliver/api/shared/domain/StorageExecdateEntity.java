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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/StorageExecdateEntity.java
 * 
 * FILE NAME        	: StorageExecdateEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 仓储费日期执行表
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 10:26:59 AM
 */
public class StorageExecdateEntity extends BaseEntity {
	private static final long serialVersionUID = -7985201661412127761L;

	/**
	 * 仓储日期
	 */
	private Date storageDate;

	/**
	 * 执行状态
	 */
	private String execStatus;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 操作日期
	 */
	private Date operatorTime;

	/**
	 * 获取 仓储日期.
	 * 
	 * @return the 仓储日期
	 */
	public Date getStorageDate() {
		return storageDate;
	}

	/**
	 * 设置 仓储日期.
	 * 
	 * @param storageDate the new 仓储日期
	 */
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}

	/**
	 * 获取 执行状态.
	 * 
	 * @return the 执行状态
	 */
	public String getExecStatus() {
		return execStatus;
	}

	/**
	 * 设置 执行状态.
	 * 
	 * @param execStatus the new 执行状态
	 */
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	/**
	 * 获取 创建日期.
	 * 
	 * @return the 创建日期
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建日期.
	 * 
	 * @param createTime the new 创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取 操作日期.
	 * 
	 * @return the 操作日期
	 */
	public Date getOperatorTime() {
		return operatorTime;
	}

	/**
	 * 设置 操作日期.
	 * 
	 * @param operatorTime the new 操作日期
	 */
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	/**
	 * 
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}