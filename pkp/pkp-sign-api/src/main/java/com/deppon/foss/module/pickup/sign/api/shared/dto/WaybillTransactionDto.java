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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/WaybillTransactionDto.java
 * 
 * FILE NAME        	: WaybillTransactionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 运单完结、财务完结状态Dto
 * @author 043258-foss-zhaobin
 * @date 2012-12-24 上午11:57:06
 */
public class WaybillTransactionDto implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 *  状态
	 */
	private String state;

	/**
	 *  运单单号list
	 */
	private List<String> waybillList;

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state the state to see
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return the waybillList
	 */
	public List<String> getWaybillList()
	{
		return waybillList;
	}

	/**
	 * @param waybillList the waybillList to see
	 */
	public void setWaybillList(List<String> waybillList)
	{
		this.waybillList = waybillList;
	}
}