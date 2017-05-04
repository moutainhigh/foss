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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirWaybillCondition.java
 * 
 * FILE NAME        	: AirWaybillCondition.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 空运运单查询条件DTO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-26 上午9:51:15
 */
public class AirWaybillCondition implements Serializable{
	private static final long serialVersionUID = -2458275618466739781L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 是否有效.
	 *
	 * @return the 是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 是否有效.
	 *
	 * @param active the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

}