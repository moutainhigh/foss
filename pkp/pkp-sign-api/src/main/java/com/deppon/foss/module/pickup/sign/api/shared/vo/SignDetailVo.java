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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/SignDetailVo.java
 * 
 * FILE NAME        	: SignDetailVo.java
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

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignListDto;

/***
 * 签收明细
 * @author foss-meiying
 * @date 2012-10-24 上午9:37:31
 * @since
 * @version
 */
public class SignDetailVo implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 签收明细实体
	 */
	private SignDetailEntity signDetailEntity;
	/**
	 * 签收明细集合
	 */
	private List<SignDetailEntity> signDetailList;
	
	/**
	 * 签收情况
	 */
	private List<String> situationList;
	
	/**
	 * 子母件签收列表
	 */
	private List<CzmSignListDto> czmSignList;
	
	/**
	 * Gets the 签收明细集合.
	 *
	 * @return the 签收明细集合
	 */
	public List<SignDetailEntity> getSignDetailList() {
		return signDetailList;
	}

	/**
	 * Sets the 签收明细集合.
	 *
	 * @param signDetailList the new 签收明细集合
	 */
	public void setSignDetailList(List<SignDetailEntity> signDetailList) {
		this.signDetailList = signDetailList;
	}

	/**
	 * Gets the 签收明细实体.
	 *
	 * @return the 签收明细实体
	 */
	public SignDetailEntity getSignDetailEntity() {
		return signDetailEntity;
	}

	/**
	 * Sets the 签收明细实体.
	 *
	 * @param signDetailEntity the new 签收明细实体
	 */
	public void setSignDetailEntity(SignDetailEntity signDetailEntity) {
		this.signDetailEntity = signDetailEntity;
	}

	public List<String> getSituationList() {
		return situationList;
	}

	public void setSituationList(List<String> situationList) {
		this.situationList = situationList;
	}

	public List<CzmSignListDto> getCzmSignList() {
		return czmSignList;
	}

	public void setCzmSignList(List<CzmSignListDto> czmSignList) {
		this.czmSignList = czmSignList;
	}
	
}