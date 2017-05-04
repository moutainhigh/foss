/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/MonitorDataEntity.java
 * 
 * FILE NAME        	: MonitorDataEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 监控应用配置
 * 
 * @author ibm-zhuwei
 * @date 2013-3-19 上午10:55:16
 */
public class MonitorAppEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7784972327765814810L;

	/**
	 * 应用系统编码
	 */
	private String appCode;

	/**
	 * 应用系统类型
	 */
	private String appType;

	/**
	 * 应用系统名称
	 */
	private String appName;

	/**
	 * 应用系统URL
	 */
	private String appUrl;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * @return appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return appUrl
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * @param appUrl
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

}