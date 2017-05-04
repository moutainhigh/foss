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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonSubSidiaryVo.java
 * 
 * FILE NAME        	: CommonSubSidiaryVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSubSidiaryDto;

 
/**
 * 公共选择器--子公司VO
 * @author 101911-foss-zhouChunlai
 * @date 2013-2-2 上午9:07:59
 */
public class CommonSubSidiaryVo implements Serializable {

 
	private static final long serialVersionUID = 6863629130329998125L;

	/**
	 * 子公司Dto
	 */
	private CommonSubSidiaryDto dto;
	
	/**
	 * 子公司信息列表
	 */
	private List<CommonSubSidiaryDto> subSidiaryList;
	
	
	public List<CommonSubSidiaryDto> getSubSidiaryList() {
		return subSidiaryList;
	}


	
	public void setSubSidiaryList(List<CommonSubSidiaryDto> subSidiaryList) {
		this.subSidiaryList = subSidiaryList;
	}


	public CommonSubSidiaryDto getDto() {
		return dto;
	}

	
	public void setDto(CommonSubSidiaryDto dto) {
		this.dto = dto;
	}

	
	 
}
