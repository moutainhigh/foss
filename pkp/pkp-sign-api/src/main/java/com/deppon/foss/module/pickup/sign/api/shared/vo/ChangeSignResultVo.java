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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/ChangeSignResultVo.java
 * 
 * FILE NAME        	: ChangeSignResultVo.java
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

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignResultDto;

/**
 * 签收变更的查询和结果VO
 * @author ibm-lizhiguo
 * @date 2012-11-5 上午11:46:31
 */
public class ChangeSignResultVo implements Serializable{
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1L;
	/**
	 * 专线详细信息
	 */
	private SignResultDto signResultDto;
	/**
	 * 偏线和空运详细信息
	 */
	private WaybillSignResultEntity waybillSignResultEntity;
	/**
	 * 申请签收结果
	 */
	private List<SignRfcEntity> signRfcList;
	/**
	 * @return signResultDto : return the property signResultDto.
	 */
	public SignResultDto getSignResultDto() {
		return signResultDto;
	}
	/**
	 * @param signResultDto : set the property signResultDto.
	 */
	public void setSignResultDto(SignResultDto signResultDto) {
		this.signResultDto = signResultDto;
	}
	/**
	 * @return waybillSignResultEntity : return the property waybillSignResultEntity.
	 */
	public WaybillSignResultEntity getWaybillSignResultEntity() {
		return waybillSignResultEntity;
	}
	/**
	 * @param waybillSignResultEntity : set the property waybillSignResultEntity.
	 */
	public void setWaybillSignResultEntity(
			WaybillSignResultEntity waybillSignResultEntity) {
		this.waybillSignResultEntity = waybillSignResultEntity;
	}
	/**
	 * @return signRfcList : return the property signRfcList.
	 */
	public List<SignRfcEntity> getSignRfcList() {
		return signRfcList;
	}
	/**
	 * @param signRfcList : set the property signRfcList.
	 */
	public void setSignRfcList(List<SignRfcEntity> signRfcList) {
		this.signRfcList = signRfcList;
	}
}