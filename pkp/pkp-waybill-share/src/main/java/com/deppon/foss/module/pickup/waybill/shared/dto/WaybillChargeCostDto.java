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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillChargeCostDto.java
 * 
 * FILE NAME        	: WaybillChargeCostDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 运单费用dto
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午9:11:54
 */
public class WaybillChargeCostDto implements Serializable
{

	private static final long serialVersionUID = 1L;
	/**
	 * 费用代码
	 */
    protected String costType;
    /**
     * 费用名称
     */
    protected String costName;
    /**
     * 金额
     */
    protected BigDecimal costMoney;
    
    
	public String getCostType()
	{
		return costType;
	}
	public void setCostType(String costType)
	{
		this.costType = costType;
	}
	public String getCostName()
	{
		return costName;
	}
	public void setCostName(String costName)
	{
		this.costName = costName;
	}
	public BigDecimal getCostMoney()
	{
		return costMoney;
	}
	public void setCostMoney(BigDecimal costMoney)
	{
		this.costMoney = costMoney;
	}
	
}