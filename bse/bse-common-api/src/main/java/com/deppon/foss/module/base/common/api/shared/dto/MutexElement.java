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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/dto/MutexElement.java
 * 
 * FILE NAME        	: MutexElement.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.dto
 * FILE    NAME: MutexElement.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.dto;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;

/**
 * 互斥对象
 * 
 * @author ibm-zhuwei
 * @date 2013-1-17 下午4:25:27
 */
public class MutexElement {

	/**
	 * 锁定业务对象编号，如运单号
	 */
	private String businessNo;

	/**
	 * 锁定业务对象描述，如开单
	 */
	private String businessDesc;

	/**
	 * 锁定业务对象类型
	 */
	private MutexElementType type;

	/**
	 * 锁定时间，超时后自动解除锁定 单位：秒，默认1分钟
	 */
	private int ttl = NumberConstants.NUMBER_60;

	/**
	 * 构造函数
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-1-17 下午5:22:54
	 */
	public MutexElement(String businessNo, String businessDesc,
			MutexElementType type) {
		this.businessNo = businessNo;
		this.businessDesc = businessDesc;
		this.type = type;
	}

	/**
	 * @return businessNo
	 */
	public String getBusinessNo() {
		return businessNo;
	}

	/**
	 * @return businessDesc
	 */
	public String getBusinessDesc() {
		return businessDesc;
	}

	/**
	 * @return type
	 */
	public MutexElementType getType() {
		return type;
	}

	/**
	 * @return ttl
	 */
	public int getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 */
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

}
