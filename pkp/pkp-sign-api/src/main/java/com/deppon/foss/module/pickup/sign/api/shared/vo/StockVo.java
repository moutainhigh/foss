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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/StockVo.java
 * 
 * FILE NAME        	: StockVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;

/***
 * 签收出库Vo
 * @author foss-meiying
 * @date 2012-10-18 下午1:58:49
 * @since
 * @version
 */
public class StockVo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 661899754344315743L;
	/**
	 *  中转库存Dto
	 */
	private StockDto stockDto;
	/**
	 *  中转库存Dto集合
	 */
	private List<StockDto> stockDtoList;

	/**
	 * Gets the 中转库存Dto.
	 *
	 * @return the 中转库存Dto
	 */
	public StockDto getStockDto() {
		return stockDto;
	}

	/**
	 * Sets the 中转库存Dto.
	 *
	 * @param stockDto the new 中转库存Dto
	 */
	public void setStockDto(StockDto stockDto) {
		this.stockDto = stockDto;
	}

	/**
	 * Gets the 中转库存Dto集合.
	 *
	 * @return the 中转库存Dto集合
	 */
	public List<StockDto> getStockDtoList() {
		return stockDtoList;
	}

	/**
	 * Sets the 中转库存Dto集合.
	 *
	 * @param stockDtoList the new 中转库存Dto集合
	 */
	public void setStockDtoList(List<StockDto> stockDtoList) {
		this.stockDtoList = stockDtoList;
	}

}