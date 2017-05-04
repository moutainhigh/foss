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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/GoodsInfoConditionVo.java
 * 
 * FILE NAME        	: GoodsInfoConditionVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;

/**
 * The Class GoodsInfoConditionVo.
 *
 * @货量查询条件VO
 * @author 043258-foss-zhaobin
 * @2012-10-11
 */
public class GoodsInfoConditionVo implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 查询货量查询条件. */
	private GoodsInfoConditionDto goodsInfoConditionDto;
	
	/** 查询货量返回DTO. */
	private List<GoodsInfoDto> goodsInfoDtoList;

	/**
	 * Gets the goods info condition dto.
	 *
	 * @return the goodsInfoConditionDto
	 */
	public GoodsInfoConditionDto getGoodsInfoConditionDto() {
		return goodsInfoConditionDto;
	}

	/**
	 * Sets the goods info condition dto.
	 *
	 * @param goodsInfoConditionDto the goodsInfoConditionDto to see
	 */
	public void setGoodsInfoConditionDto(GoodsInfoConditionDto goodsInfoConditionDto) {
		this.goodsInfoConditionDto = goodsInfoConditionDto;
	}

	/**
	 * Gets the goods info dto list.
	 *
	 * @return the goodsInfoDtoList
	 */
	public List<GoodsInfoDto> getGoodsInfoDtoList() {
		return goodsInfoDtoList;
	}

	/**
	 * Sets the goods info dto list.
	 *
	 * @param goodsInfoDtoList the goodsInfoDtoList to see
	 */
	public void setGoodsInfoDtoList(List<GoodsInfoDto> goodsInfoDtoList) {
		this.goodsInfoDtoList = goodsInfoDtoList;
	}

}