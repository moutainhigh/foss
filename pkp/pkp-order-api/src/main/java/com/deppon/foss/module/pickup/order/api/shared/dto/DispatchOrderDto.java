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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/DispatchOrderDto.java
 * 
 * FILE NAME        	: DispatchOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;

/**
 * 订单查询结果Dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-29 下午2:59:47
 */
public class DispatchOrderDto implements Serializable {

	private static final long serialVersionUID = -1822085549640225252L;
	/**
	 *  查询订单结果实体集合
	 */
	private List<DispatchOrderEntity> dispatchOrderEntities;
	/**
	 *  订单总数
	 */
	private Long count;

	/**
	 * @return the dispatchOrderEntities
	 */
	public List<DispatchOrderEntity> getDispatchOrderEntities() {
		return dispatchOrderEntities;
	}

	/**
	 * @param dispatchOrderEntities the dispatchOrderEntities to set
	 */
	public void setDispatchOrderEntities(List<DispatchOrderEntity> dispatchOrderEntities) {
		this.dispatchOrderEntities = dispatchOrderEntities;
	}

	/**
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Long count) {
		this.count = count;
	}

}