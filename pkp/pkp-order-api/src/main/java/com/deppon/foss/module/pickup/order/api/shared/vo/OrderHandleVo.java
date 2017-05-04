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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/OrderHandleVo.java
 * 
 * FILE NAME        	: OrderHandleVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.dto.OrderHandleDto;

/**
 * 订单处理Vo
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-25 上午9:07:41
 */
public class OrderHandleVo implements Serializable {
	// 序列
	private static final long serialVersionUID = -2493787083207374074L;
	/** 
	 * 拒绝Dto
	 */
	private OrderHandleDto orderHandleDto;
	/** 
	 * 受理Dto
	 */
	private List<OrderHandleDto> orderHandleDtoList;

	/**
	 * @return the orderHandleDto
	 */
	public OrderHandleDto getOrderHandleDto() {
		return orderHandleDto;
	}

	/**
	 * @param orderHandleDto the orderHandleDto to set
	 */
	public void setOrderHandleDto(OrderHandleDto orderHandleDto) {
		this.orderHandleDto = orderHandleDto;
	}

	/**
	 * @return the orderHandleDtoList
	 */
	public List<OrderHandleDto> getOrderHandleDtoList() {
		return orderHandleDtoList;
	}

	/**
	 * @param orderHandleDtoList the orderHandleDtoList to set
	 */
	public void setOrderHandleDtoList(List<OrderHandleDto> orderHandleDtoList) {
		this.orderHandleDtoList = orderHandleDtoList;
	}

}