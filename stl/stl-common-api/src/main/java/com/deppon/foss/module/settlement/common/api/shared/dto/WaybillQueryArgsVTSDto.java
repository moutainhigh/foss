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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillQueryArgsDto.java
 * 
 * FILE NAME        	: WaybillQueryArgsDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: WaybillQueryArgsDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;


/**
 * 用于封装运单查询参数的DTO
 * @author 218392 张永雪
 * @date 2016-06-16 下午2:39:52
 */
public class WaybillQueryArgsVTSDto implements Serializable{
	/**
	 * 序列化标识 
	 */
	private static final long serialVersionUID = 4415497196785932002L;
	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 订单编号
	 */
	private String orderNo;
	
	/**
	 * 打印流水号
	 */
	private String serialNo;
	
	/**
	 * 状态
	 */
	private String active;
	
	/**
	 * 运单状态
	 */
	private String waybillStatus;

	/**
	 * 类型
	 */
	private String type;	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * Gets the order no.
	 *
	 * @return the orderNo .
	 */
	public String getOrderNo() {
		return orderNo;
	}

	
	/**
	 * Sets the order no.
	 *
	 * @param orderNo the orderNo to set.
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	/**
	 * Gets the active.
	 *
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * Sets the active.
	 *
	 * @param active the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * Gets the serial no.
	 *
	 * @return the serial no
	 */
	public String getSerialNo()
	{
		return serialNo;
	}


	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the new serial no
	 */
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}


	/**
	  * @return  the waybillStatus
	 */
	public String getWaybillStatus() {
		return waybillStatus;
	}


	/**
	 * @param waybillStatus the waybillStatus to set
	 */
	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}
	
}