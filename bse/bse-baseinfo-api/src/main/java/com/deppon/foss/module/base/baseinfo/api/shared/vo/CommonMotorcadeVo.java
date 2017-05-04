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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonMotorcadeVo.java
 * 
 * FILE NAME        	: CommonMotorcadeVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonMotorcadeDto;

/**
 * 车队vo
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-11 下午4:31:16
 */
public class CommonMotorcadeVo implements Serializable {

	private static final long serialVersionUID = -21757479290459330L;
	
	/**
	 * 车队信息Dto
	 */
	private CommonMotorcadeDto commonMotorcadeDto;
	/**
	 * 车队信息列表
	 */
	private List<MotorcadeEntity> motorcadeEntityList;

	public List<MotorcadeEntity> getMotorcadeEntityList() {
		return motorcadeEntityList;
	}

	public void setMotorcadeEntityList(List<MotorcadeEntity> motorcadeEntityList) {
		this.motorcadeEntityList = motorcadeEntityList;
	}

	
	public CommonMotorcadeDto getCommonMotorcadeDto() {
		return commonMotorcadeDto;
	}

	
	public void setCommonMotorcadeDto(CommonMotorcadeDto commonMotorcadeDto) {
		this.commonMotorcadeDto = commonMotorcadeDto;
	}
	
	
}
