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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonZoneVo.java
 * 
 * FILE NAME        	: CommonZoneVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonAllZoneDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonBigZoneDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSmallZoneDto;

/**
 * 公共选择器--集中接送货区域VO
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:10:40
 */
public class CommonZoneVo implements Serializable {

	private static final long serialVersionUID = -4229496524447906306L;

	/**
	 * 集中接送货小区Dto
	 */
	private CommonSmallZoneDto commonSmallZoneDto;

	/**
	 * 集中接送货大区Dto
	 */
	private CommonBigZoneDto commonBigZoneDto;

	/**
	 * 大小区Dto
	 */
	private CommonAllZoneDto commonAllZoneDto;

	/**
	 * 集中接送货小区列表
	 */
	private List<SmallZoneEntity> commonSmallZoneList;

	/**
	 * 集中接送货大区列表
	 */
	private List<BigZoneEntity> commonBigZoneList;

	/**
	 * 大小区列表
	 */
	private List<CommonAllZoneDto> commonAllZoneList;

	
	public CommonSmallZoneDto getCommonSmallZoneDto() {
		return commonSmallZoneDto;
	}

	
	public void setCommonSmallZoneDto(CommonSmallZoneDto commonSmallZoneDto) {
		this.commonSmallZoneDto = commonSmallZoneDto;
	}

	
	public CommonBigZoneDto getCommonBigZoneDto() {
		return commonBigZoneDto;
	}

	
	public void setCommonBigZoneDto(CommonBigZoneDto commonBigZoneDto) {
		this.commonBigZoneDto = commonBigZoneDto;
	}

	
	public CommonAllZoneDto getCommonAllZoneDto() {
		return commonAllZoneDto;
	}

	
	public void setCommonAllZoneDto(CommonAllZoneDto commonAllZoneDto) {
		this.commonAllZoneDto = commonAllZoneDto;
	}

	
	public List<SmallZoneEntity> getCommonSmallZoneList() {
		return commonSmallZoneList;
	}

	
	public void setCommonSmallZoneList(List<SmallZoneEntity> commonSmallZoneList) {
		this.commonSmallZoneList = commonSmallZoneList;
	}

	
	public List<BigZoneEntity> getCommonBigZoneList() {
		return commonBigZoneList;
	}

	
	public void setCommonBigZoneList(List<BigZoneEntity> commonBigZoneList) {
		this.commonBigZoneList = commonBigZoneList;
	}

	
	public List<CommonAllZoneDto> getCommonAllZoneList() {
		return commonAllZoneList;
	}

	
	public void setCommonAllZoneList(List<CommonAllZoneDto> commonAllZoneList) {
		this.commonAllZoneList = commonAllZoneList;
	} 
}
