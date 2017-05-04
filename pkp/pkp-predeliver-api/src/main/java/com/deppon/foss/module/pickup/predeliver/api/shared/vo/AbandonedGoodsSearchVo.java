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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/AbandonedGoodsSearchVo.java
 * 
 * FILE NAME        	: AbandonedGoodsSearchVo.java
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

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;

/**
 * AbandonedGoodsSearchVo 弃货dto
 * @author ibm-lizhiguo
 * @date 2012-10-26 上午11:24:09
 */
public class AbandonedGoodsSearchVo implements Serializable {

	// 序列化版本号
	private static final long serialVersionUID = 1L;

	// 查询条件数据DTO
	private AbandonedGoodsSearchDto abandonedGoodsSearchDto = new AbandonedGoodsSearchDto();

	// 查询结果数据DTO
	private List<AbandonGoodsResultDto> abandonGoodsResultDtoList;

	// 详细弃货信息DTO
	private AbandonedGoodsDetailDto abandonedGoodsDetailDto = new AbandonedGoodsDetailDto();

	// 弃货ID
	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the abandonedGoodsSearchDto
	 */
	public AbandonedGoodsSearchDto getAbandonedGoodsSearchDto() {
		return abandonedGoodsSearchDto;
	}

	/**
	 * @param abandonedGoodsSearchDto the abandonedGoodsSearchDto to see
	 */
	public void setAbandonedGoodsSearchDto(AbandonedGoodsSearchDto abandonedGoodsSearchDto) {
		this.abandonedGoodsSearchDto = abandonedGoodsSearchDto;
	}

	/**
	 * @return the abandonGoodsResultDtoList
	 */
	public List<AbandonGoodsResultDto> getAbandonGoodsResultDtoList() {
		return abandonGoodsResultDtoList;
	}

	/**
	 * @param abandonGoodsResultDtoList the abandonGoodsResultDtoList to see
	 */
	public void setAbandonGoodsResultDtoList(List<AbandonGoodsResultDto> abandonGoodsResultDtoList) {
		this.abandonGoodsResultDtoList = abandonGoodsResultDtoList;
	}

	/**
	 * @return the abandonedGoodsDetailDto
	 */
	public AbandonedGoodsDetailDto getAbandonedGoodsDetailDto() {
		return abandonedGoodsDetailDto;
	}

	/**
	 * @param abandonedGoodsDetailDto the abandonedGoodsDetailDto to see
	 */
	public void setAbandonedGoodsDetailDto(AbandonedGoodsDetailDto abandonedGoodsDetailDto) {
		this.abandonedGoodsDetailDto = abandonedGoodsDetailDto;
	}
}