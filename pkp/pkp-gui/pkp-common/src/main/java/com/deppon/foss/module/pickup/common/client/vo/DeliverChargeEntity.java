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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/DeliverChargeEntity.java
 * 
 * FILE NAME        	: DeliverChargeEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.math.BigDecimal;

public class DeliverChargeEntity {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 费用名称
	 */
	private String name;
	
	/**
	 * 费用编码
	 */
	private String code;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 激活状态
	 */
	private String active;
	
	/**
	 * 币种
	 */
	private String currencyCode;

	/**
	 * id
	 */
	public String getId() {
		return id;
	}

	/**
	 * id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 费用编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 费用编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 激活状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 激活状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 
	 * 费用名称
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午06:37:01
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 费用名称
	 * @author 025000-FOSS-helong
	 * @date 2013-3-16 下午06:37:01
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

}