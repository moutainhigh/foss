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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/OwnTruckVo.java
 * 
 * FILE NAME        	: OwnTruckVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;

/**
 * 自有车VO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-23 上午10:29:01
 */
public class OwnTruckVo implements Serializable {
	// 序列
	private static final long serialVersionUID = -2788619473766825035L;
	/**
	 * 自有车DTO
	 */
	private OwnTruckDto ownTruckDto;

	/**
	 * @return the ownTruckDto
	 */
	public OwnTruckDto getOwnTruckDto() {
		return ownTruckDto;
	}

	/**
	 * @param ownTruckDto the ownTruckDto to set
	 */
	public void setOwnTruckDto(OwnTruckDto ownTruckDto) {
		this.ownTruckDto = ownTruckDto;
	}

}