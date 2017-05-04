/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/dto/QueryPackedDto.java
 *  
 *  FILE NAME          :QueryPackedDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.dto
 * FILE    NAME: QueryPackedDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;

/**
 * 根据运单号查询包装录入，流水号和包装人员的dto
 * @author 046130-foss-xuduowei
 * @date 2012-11-8 上午11:20:39
 */
public class QueryPackedDto {
	
	/**
	 * 包装录入主信息
	 */
	private WaybillPackEntity waybillPackEntity;
	/**
	 * 包装人员
	 */
	private List<PackedPersonEntity> packedPersonList;

	/**
	 * 新旧流水号关系
	 */
	private List<SerialRelationEntity> serialRelationList;

	/**
	 * 新流水号
	 */
	private List<SerialRelationEntity> newSerialRelationList;

	/**
	 * 最大流水号
	 */
	private String maxSerialNo;

	/**
	 * 获取 包装录入主信息.
	 *
	 * @return the 包装录入主信息
	 */
	public WaybillPackEntity getWaybillPackEntity() {
		return waybillPackEntity;
	}

	/**
	 * 设置 包装录入主信息.
	 *
	 * @param waybillPackEntity the new 包装录入主信息
	 */
	public void setWaybillPackEntity(WaybillPackEntity waybillPackEntity) {
		this.waybillPackEntity = waybillPackEntity;
	}

	/**
	 * 获取 包装人员.
	 *
	 * @return the 包装人员
	 */
	public List<PackedPersonEntity> getPackedPersonList() {
		return packedPersonList;
	}

	/**
	 * 设置 包装人员.
	 *
	 * @param packedPersonList the new 包装人员
	 */
	public void setPackedPersonList(List<PackedPersonEntity> packedPersonList) {
		this.packedPersonList = packedPersonList;
	}

	/**
	 * 获取 新旧流水号关系.
	 *
	 * @return the 新旧流水号关系
	 */
	public List<SerialRelationEntity> getSerialRelationList() {
		return serialRelationList;
	}

	/**
	 * 设置 新旧流水号关系.
	 *
	 * @param serialRelationList the new 新旧流水号关系
	 */
	public void setSerialRelationList(List<SerialRelationEntity> serialRelationList) {
		this.serialRelationList = serialRelationList;
	}

	/**
	 * 获取 新流水号.
	 *
	 * @return the 新流水号
	 */
	public List<SerialRelationEntity> getNewSerialRelationList() {
		return newSerialRelationList;
	}

	/**
	 * 设置 新流水号.
	 *
	 * @param newSerialRelationList the new 新流水号
	 */
	public void setNewSerialRelationList(
			List<SerialRelationEntity> newSerialRelationList) {
		this.newSerialRelationList = newSerialRelationList;
	}

	/**
	 * 获取 最大流水号.
	 *
	 * @return the 最大流水号
	 */
	public String getMaxSerialNo() {
		return maxSerialNo;
	}

	/**
	 * 设置 最大流水号.
	 *
	 * @param maxSerialNo the new 最大流水号
	 */
	public void setMaxSerialNo(String maxSerialNo) {
		this.maxSerialNo = maxSerialNo;
	}



	
}