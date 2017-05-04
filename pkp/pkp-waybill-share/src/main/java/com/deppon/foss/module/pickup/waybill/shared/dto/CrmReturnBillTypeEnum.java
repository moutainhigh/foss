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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmReturnBillTypeEnum.java
 * 
 * FILE NAME        	: CrmReturnBillTypeEnum.java
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
 *返单类型
 *
 */
public enum CrmReturnBillTypeEnum {
	//无需返单s
	NORETURNSIGNED("NO_RETURN_SIGNED",     "无需返单", WaybillConstants.NOT_RETURN_BILL ), //无需返单
	//客户签收单原件返回
	CUSTOMERSIGNEDORIGINAL("CUSTOMER_SIGNED_ORIGINAL", "客户签收单原件返回", WaybillConstants.RETURNBILLTYPE_ORIGINAL ),//客户签收单原件返回 
	
	//客户签收单传真返回
	CUSTOMERSIGNEDFAX("CUSTOMER_SIGNED_FAX", "客户签收单传真返回",  WaybillConstants.RETURNBILLTYPE_FAX),//客户签收单传真返回
	//运单签收联原件返回
	BILLSIGNEDORIGINAL("BILL_SIGNED_ORIGINAL", "运单签收联原件返回",  WaybillConstants.RETURNBILLTYPE_ORIGINAL),//运单签收联原件返回
	//运单到达联传真返回
	BILLSIGNEDFAX("BILL_SIGNED_FAX", "运单到达联传真返回",  WaybillConstants.RETURNBILLTYPE_ARRIVE),//运单到达联传真返回
	//派送代理签收单原件返回
	AGENTSIGNEDORIGINAL("AGENT_SIGNED_ORIGINAL", "派送代理签收单原件返回",  WaybillConstants.RETURNBILLTYPE_ORIGINAL),//派送代理签收单原件返回
	//派送代理签收单传真件返回
	AGENTSIGNEDFAX("AGENT_SIGNED_FAX", " 派送代理签收单传真件返回",  WaybillConstants.RETURNBILLTYPE_FAX);//派送代理签收单传真件返回
	
	/**
	 * crm传过来的返单类型code
	 */
	private String crmCode;
	
	/**
	 * 中文名称
	 */
	private String name;
	
	/**
	 * foss返单类型code
	 */
	private String fossCode;
	
	/**
	 * 构造方法
	 * @param crmCode
	 * @param name
	 * @param fossCode
	 */
	private CrmReturnBillTypeEnum(String crmCode, String name, String fossCode ){
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