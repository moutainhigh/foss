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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/shared/dto/ChangeNodeDto.java
 * 
 * FILE NAME        	: ChangeNodeDto.java
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

import java.io.Serializable;

 

public class ToWicsDto implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	//单号
	private String waybillNo;
	//第一 到二外场时效
	private Long AgingOne;
	//第一到最后外场时效
    private Long AgingTwo;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public Long getAgingOne() {
		return AgingOne;
	}
	public void setAgingOne(Long agingOne) {
		AgingOne = agingOne;
	}
	public Long getAgingTwo() {
		return AgingTwo;
	}
	public void setAgingTwo(Long agingTwo) {
		AgingTwo = agingTwo;
	}
	
}