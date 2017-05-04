/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/AirlinesValueAddVo.java
 * 
 * FILE NAME        	: AirlinesValueAddVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.AirlinesValueAddDto;

/**
 * (航空公司增值服务VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-12-05 下午15:13:10
 * </p>
 * 
 */
public class AirlinesValueAddVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6170416968983141976L;
	/**
	 * 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
	 */
	private AirlinesValueAddEntity airlinesValueAddEntity;
	/**
	 * List
	 */
	private List<AirlinesValueAddEntity> airlinesValueAddEntityList;
	/**
	 * 激活/删除的IDS
	 */
	private List<String> airlinesValueAddIds;
	/**
	 * dto查询条件
	 */
	private AirlinesValueAddDto airlinesValueAddDto;
	

	/**
	 * 获取 dto查询条件.
	 *
	 * @return the dto查询条件
	 */
	public AirlinesValueAddDto getAirlinesValueAddDto() {
		return airlinesValueAddDto;
	}
	
	/**
	 * 设置 dto查询条件.
	 *
	 * @param airlinesValueAddDto the new dto查询条件
	 */
	public void setAirlinesValueAddDto(AirlinesValueAddDto airlinesValueAddDto) {
		this.airlinesValueAddDto = airlinesValueAddDto;
	}
	
	/**
	 * 获取 激活/删除的IDS.
	 *
	 * @return the 激活/删除的IDS
	 */
	public List<String> getAirlinesValueAddIds() {
		return airlinesValueAddIds;
	}
	
	/**
	 * 设置 激活/删除的IDS.
	 *
	 * @param airlinesValueAddIds the new 激活/删除的IDS
	 */
	public void setAirlinesValueAddIds(List<String> airlinesValueAddIds) {
		this.airlinesValueAddIds = airlinesValueAddIds;
	}
	
	/**
	 * 获取 list.
	 *
	 * @return the list
	 */
	public List<AirlinesValueAddEntity> getAirlinesValueAddEntityList() {
		return airlinesValueAddEntityList;
	}
	
	/**
	 * 设置 list.
	 *
	 * @param airlinesValueAddEntityList the new list
	 */
	public void setAirlinesValueAddEntityList(
			List<AirlinesValueAddEntity> airlinesValueAddEntityList) {
		this.airlinesValueAddEntityList = airlinesValueAddEntityList;
	}
	
	/**
	 * 获取 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额.
	 *
	 * @return the 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
	 */
	public AirlinesValueAddEntity getAirlinesValueAddEntity() {
		return airlinesValueAddEntity;
	}
	
	/**
	 * 设置 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额.
	 *
	 * @param airlinesValueAddEntity the new 代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
	 */
	public void setAirlinesValueAddEntity(
			AirlinesValueAddEntity airlinesValueAddEntity) {
		this.airlinesValueAddEntity = airlinesValueAddEntity;
	}
	
}