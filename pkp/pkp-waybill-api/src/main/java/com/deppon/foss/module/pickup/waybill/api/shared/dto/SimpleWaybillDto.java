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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/SimpleWaybillDto.java
 * 
 * FILE NAME        	: SimpleWaybillDto.java
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
package com.deppon.foss.module.pickup.waybill.api.shared.dto;

/**
 * 综合查询-运单单号
 * 
 * @author 038590-foss-wanghui
 * @date 2013-3-1 上午9:17:46
 */
public class SimpleWaybillDto {

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * where语句
	 */
	private String whereSql;

	/**
	 * 是否有效
	 */
	private String active;

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
	 * @return the whereSql
	 */
	public String getWhereSql() {
		return whereSql;
	}

	/**
	 * @param whereSql the whereSql to set
	 */
	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

}