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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmReceiveMethodEnum.java
 * 
 * FILE NAME        	: CrmReceiveMethodEnum.java
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


/**
 * crm提货方式- foss提货方式
 */
public enum CrmReceiveMethodEnum {
    	//送货(不含上楼)
	PICKNOTUPSTAIRS("PICKNOTUPSTAIRS", "送货(不含上楼)"), 
	//送货上楼
	PICKUPSTAIRS("PICKUPSTAIRS", "送货上楼"), 
	//自提
	PICKSELF("PICKSELF", "自提"), 
	//机场自提
	PICKONAIEPORT("PICKONAIEPORT", "机场自提"),
	//送货进仓
	DELIVERY_STORE("DELIVERY_STOCK","送货进仓"),
	//内部带货自提
	INNER_PICKUP("INNER_PICKUP","内部带货自提"),
	//免费送货
	FREE_DELIVERY("FREE_DELIVERY","免费送货"),
	//自提（不含机场提货费）
	SELF_PICKUP("SELF_PICKUP","自提（不含机场提货费）"),
	//免费自提
	FREE_PICKUP("FREE_PICKUP","免费自提"),
	//送货上门
	PICKFOOR("PICKFOOR", "送货上门"),
	//快递送货上楼
	DELIVER_UP("DELIVER_UP", "送货上楼");
	
	/**
	 * crm传过来的提货方式-code
	 */
	private String crmCode;
	
	/**
	 * 中文名称
	 */
	private String name;
	
	/**
	 * 构造方法
	 * @param crmCode
	 * @param name
	 */
	private CrmReceiveMethodEnum(String crmCode, String name ){
		this.crmCode= crmCode;
		this.name = name;
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


}