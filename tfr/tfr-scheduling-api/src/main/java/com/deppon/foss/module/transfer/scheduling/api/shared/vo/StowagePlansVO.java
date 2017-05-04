/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
 ND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/StowagePlansVO.java
 * 
 *  FILE NAME     :StowagePlansVO.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 2013年7月30日 16:42:38
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto;

/**
 * @author 134019-foss-yuyongxiang
 * @date 2013年7月30日 16:42:19
 */
public class StowagePlansVO implements java.io.Serializable {

	private static final long serialVersionUID = 7256801620012574126L;

	/**
	 * 配载方案配置  List
	 */
	private List<StowagePlansEntity> stowagePlansList;
	/**
	 * 配载方案配置 明细  List
	 */
	private List<StowagePlansDetailEntity> stowagePlansDetailList;

	/**
	 * 配载方案配置 DTO
	 */
	private StowagePlansDto stowagePlansDto;
	
	/**
	 * 设置配载方案限制条数
	 */
	private String stowagePlanDefault;

	/**
	 * @return the stowagePlanslist
	 */
	public List<StowagePlansEntity> getStowagePlansList() {
		return stowagePlansList;
	}

	/**
	 * @param stowagePlanslist
	 *            the stowagePlanslist to set
	 */
	public void setStowagePlansList(List<StowagePlansEntity> stowagePlansList) {
		this.stowagePlansList = stowagePlansList;
	}

	/**
	 * @return the stowagePlansDto
	 */
	public StowagePlansDto getStowagePlansDto() {
		return stowagePlansDto;
	}

	/**
	 * @param stowagePlansDto the stowagePlansDto to set
	 */
	public void setStowagePlansDto(StowagePlansDto stowagePlansDto) {
		this.stowagePlansDto = stowagePlansDto;
	}

	/**
	 * @return the stowagePlansDetailList
	 */
	public List<StowagePlansDetailEntity> getStowagePlansDetailList() {
		return stowagePlansDetailList;
	}

	/**
	 * @param stowagePlansDetailList the stowagePlansDetailList to set
	 */
	public void setStowagePlansDetailList(
			List<StowagePlansDetailEntity> stowagePlansDetailList) {
		this.stowagePlansDetailList = stowagePlansDetailList;
	}

	/**
	 * @return the stowagePlanDefault
	 */
	public String getStowagePlanDefault() {
		return stowagePlanDefault;
	}

	/**
	 * @param stowagePlanDefault the stowagePlanDefault to set
	 */
	public void setStowagePlanDefault(String stowagePlanDefault) {
		this.stowagePlanDefault = stowagePlanDefault;
	}

}