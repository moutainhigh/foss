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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmRefundTypeEnum.java
 * 
 * FILE NAME        	: CrmRefundTypeEnum.java
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
package com.deppon.foss.module.pickup.waybill.shared.dto;

import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;

/**
 * 退款类型
 *
 */
public enum CrmRefundTypeEnum {

	NORMAL("NORMAL",     "三日退", WaybillConstants.REFUND_TYPE_THREE_DAY ), //三日退
	INTRADAY("INTRADAY", "即日退", WaybillConstants.REFUND_TYPE_ONE_DAY ), //即日退
	NOTHING("NOTHING", "无", null);//无
	
	/**
	 * crm传过来的退款类型code
	 */
	private String crmCode;
	
	/**
	 * 中文名称
	 */
	private String name;
	
	/**
	 * foss退款类型code
	 */
	private String fossCode;
	
	/**
	 * 构造方法
	 * @param crmCode
	 * @param name
	 * @param fossCode
	 */
	private CrmRefundTypeEnum(String crmCode, String name, String fossCode ){
		this.crmCode= crmCode;
		this.name = name;
		this.fossCode=fossCode;
	}


	/**
	 * @return the crmCode
	 */
	public String getCrmCode() {
		return crmCode;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the fossCode
	 */
	public String getFossCode() {
		return fossCode;
	}
	
}