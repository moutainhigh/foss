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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillQueryInfoDto.java
 * 
 * FILE NAME        	: WaybillQueryInfoDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 运单查询Dto
 * @author 043258-foss-zhaobin
 * @date 2013-1-6 下午5:30:15
 */
public class WaybillQueryInfoDto implements Serializable
{
	
	/** The Constant serialVersionUID. */
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
	 * 是否有效
	 */
	private String active;
	/**
	 *  PDA补录处理类型
	 */
	private List<String> pendingTypes;
	/**
	 * 付款方式
	 */
	private String paidMethod;

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * Gets the waybill list.
	 *
	 * @return the waybill list
	 */
	public List<String> getWaybillList()
	{
		return waybillList;
	}

	/**
	 * Sets the waybill list.
	 *
	 * @param waybillList the new waybill list
	 */
	public void setWaybillList(List<String> waybillList)
	{
		this.waybillList = waybillList;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<String> getPendingTypes() {
		return pendingTypes;
	}

	public void setPendingTypes(List<String> pendingTypes) {
		this.pendingTypes = pendingTypes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	
}