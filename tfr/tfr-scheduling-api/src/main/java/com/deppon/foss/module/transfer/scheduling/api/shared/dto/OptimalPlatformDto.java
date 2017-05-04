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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/OptimalPlatformDto.java
 * 
 *  FILE NAME     :OptimalPlatformDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: OptimalPlatformDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;

/**
 * 最优月台Dto
 * @author 104306-foss-wangLong
 * @date 2012-11-30 下午3:42:56
 */
public class OptimalPlatformDto implements Serializable {
	
	private static final long serialVersionUID = -9108215517913925100L;

	/** 综合月台实体  */
	private PlatformEntity platformEntity;
	
	/** 是否可用 取值  'Y' or 'N' */
	private String isUsable;
	
	/** 最近可用时间   date or '未知' */
	private String usableTime;

	/**
	 * 获得platformEntity
	 * @return the platformEntity
	 */
	public PlatformEntity getPlatformEntity() {
		return platformEntity;
	}

	/**
	 * 设置platformEntity
	 * @param platformEntity the platformEntity to set
	 */
	public void setPlatformEntity(PlatformEntity platformEntity) {
		this.platformEntity = platformEntity;
	}

	/**
	 * 获得isUsable
	 * @return the isUsable
	 */
	public String getIsUsable() {
		return isUsable;
	}

	/**
	 * 设置isUsable
	 * @param isUsable the isUsable to set
	 */
	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}

	/**
	 * 获得usableTime
	 * @return the usableTime
	 */
	public String getUsableTime() {
		return usableTime;
	}

	/**
	 * 设置usableTime
	 * @param usableTime the usableTime to set
	 */
	public void setUsableTime(String usableTime) {
		this.usableTime = usableTime;
	}
}