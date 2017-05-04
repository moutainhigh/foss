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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/domain/WinFormSettingEntity.java
 * 
 * FILE NAME        	: WinFormSettingEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.api.shared.domain;

import java.sql.Timestamp;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 消息弹出设置
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-24 上午8:53:47
 */
public class WinFormSettingEntity extends BaseEntity {

	private static final long serialVersionUID = 6729798596607055076L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 员工编码
	 */
	private String userCode;
	/**
	 * 设置间隔
	 */
	private int intervalTime;
	/**
	 * 是否自动弹出
	 */
	private String autoAlertFlag;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	 
	public int getIntervalTime() {
		return intervalTime;
	}
	
	public String getAutoAlertFlag() {
		return autoAlertFlag;
	}
	
	public void setAutoAlertFlag(String autoAlertFlag) {
		this.autoAlertFlag = autoAlertFlag;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	
	public String getUserCode() {
		return userCode;
	}

	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
