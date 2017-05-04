/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/FailOrderDto.java
 * 
 * FILE NAME        	: FailOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;


/**
 * 处理失败的订单
 * @author 038590-foss-wanghui
 * @date 2012-12-28 上午11:35:23
 */
public class FailOrderDto implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 756533990894069799L;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 失败类型
	 */
	private String failType;
	
	/**
	 * 失败原因
	 */
	private String failReason;
	
	public FailOrderDto(String orderNo, String failType, String failReason) {
		super();
		this.orderNo = orderNo;
		this.failType = failType;
		this.failReason = failReason;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFailType() {
		return failType;
	}

	public void setFailType(String failType) {
		this.failType = failType;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	
}