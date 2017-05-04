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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/DispatchOrderVo.java
 * 
 * FILE NAME        	: DispatchOrderVo.java
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

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.FailOrderDto;

/**
 * 调度订单Vo
 * 
 * @author 038590-foss-wanghui
 * @date 2012-12-25 上午10:27:05
 */
public class DispatchOrderVo implements Serializable {
	// 序列
	private static final long serialVersionUID = 3984515917526814054L;
	/**
	 * 返回查询结果实体集合
	 */
	private List<DispatchOrderEntity> orderList;

	/**
	 * 失败订单集合
	 */
	private List<FailOrderDto> failOrderList;

	/**
	 * @return the failOrderList
	 */
	public List<FailOrderDto> getFailOrderList() {
		return failOrderList;
	}

	/**
	 * @param failOrderList the failOrderList to set
	 */
	public void setFailOrderList(List<FailOrderDto> failOrderList) {
		this.failOrderList = failOrderList;
	}

	/**
	 * @return the orderList
	 */
	public List<DispatchOrderEntity> getOrderList() {
		return orderList;
	}

	/**
	 * @param orderList the orderList to set
	 */
	public void setOrderList(List<DispatchOrderEntity> orderList) {
		this.orderList = orderList;
	}

}