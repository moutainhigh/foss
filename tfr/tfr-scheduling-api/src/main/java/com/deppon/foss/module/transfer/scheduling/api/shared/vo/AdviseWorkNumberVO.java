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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/vo/AdviseWorkNumberVO.java
 * 
 *  FILE NAME     :AdviseWorkNumberVO.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import java.math.BigDecimal;

import com.deppon.foss.module.transfer.scheduling.api.shared.dto.AdviseWorkNumberDto;

/**
 * 计算上班人数VO
 */
public class AdviseWorkNumberVO implements java.io.Serializable{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2675931990243271502L;
	/**
	 * 组织代码
	 */
	private String orgCode;
	/**
	 * 人工装卸效率
	 */
	private BigDecimal efficiencyPerPeople;
	
	/**
	 * 计算上班人数DTO
	 */
	private AdviseWorkNumberDto adviseWorkNumberDto;

	/**
	 * 获取 组织代码.
	 *
	 * @return the 组织代码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 组织代码.
	 *
	 * @param orgCode the new 组织代码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 人工装卸效率.
	 *
	 * @return the 人工装卸效率
	 */
	public BigDecimal getEfficiencyPerPeople() {
		return efficiencyPerPeople;
	}

	/**
	 * 设置 人工装卸效率.
	 *
	 * @param efficiencyPerPeople the new 人工装卸效率
	 */
	public void setEfficiencyPerPeople(BigDecimal efficiencyPerPeople) {
		this.efficiencyPerPeople = efficiencyPerPeople;
	}

	/**
	 * 获取 计算上班人数DTO.
	 *
	 * @return the 计算上班人数DTO
	 */
	public AdviseWorkNumberDto getAdviseWorkNumberDto() {
		return adviseWorkNumberDto;
	}

	/**
	 * 设置 计算上班人数DTO.
	 *
	 * @param adviseWorkNumberDto the new 计算上班人数DTO
	 */
	public void setAdviseWorkNumberDto(AdviseWorkNumberDto adviseWorkNumberDto) {
		this.adviseWorkNumberDto = adviseWorkNumberDto;
	}
	
}