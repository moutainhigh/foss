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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillCostInfo.java
 * 
 * FILE NAME        	: WaybillCostInfo.java
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;

/**
 * 
 * <p>
 * Crm系统查询运单信息之费用信息
 * </p>
 * @title WaybillCostInfo.java
 * @package com.deppon.foss.module.pickup.waybill.shared.domain 
 * @author suyujun
 * @version 0.1 2012-12-18
 */
public class WaybillCostInfo {
	/**
	 * 费用类型
	 */
    private String costType;
    /**
     * 费用名称
     */
    private String costName;
    /**
     * 金额
     */
    private BigDecimal costMoney;
	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}
	/**
	 * @param costType the costType to set
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}
	/**
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}
	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}
	/**
	 * @return the costMoney
	 */
	public BigDecimal getCostMoney() {
		return costMoney;
	}
	/**
	 * @param costMoney the costMoney to set
	 */
	public void setCostMoney(BigDecimal costMoney) {
		this.costMoney = costMoney;
	}
	
}