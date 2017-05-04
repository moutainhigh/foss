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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/SignInAndLogOutVo.java
 * 
 * FILE NAME        	: SignInAndLogOutVo.java
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

import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;

/**
 * 调度解除签到VO
 * 
 * @author 097972-foss-dengtingting
 */
public class SignInAndLogOutVo implements Serializable {
	// 序列
	private static final long serialVersionUID = 3069706152749408428L;

	/** 
	 * 查询Pda签到信息DTO
	 */
	private PdaSignDto dto;

	/** 
	 * 查询Pda签到信息集合
	 */
	private List<PdaSignDto> pdasignList;

	/** 
	 * PDA签到信息
	 */
	private PdaSignEntity pdaEntity;

	/**
	 * @return the dto
	 */
	public PdaSignDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(PdaSignDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the pdasignList
	 */
	public List<PdaSignDto> getPdasignList() {
		return pdasignList;
	}

	/**
	 * @param pdasignList the pdasignList to set
	 */
	public void setPdasignList(List<PdaSignDto> pdasignList) {
		this.pdasignList = pdasignList;
	}

	/**
	 * @return the pdaEntity
	 */
	public PdaSignEntity getPdaEntity() {
		return pdaEntity;
	}

	/**
	 * @param pdaEntity the pdaEntity to set
	 */
	public void setPdaEntity(PdaSignEntity pdaEntity) {
		this.pdaEntity = pdaEntity;
	}
}