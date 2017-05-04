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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/CommonSaleDeptAndOuterBranchVo.java
 * 
 * FILE NAME        	: CommonSaleDeptAndOuterBranchVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonSaleDeptAndOuterBranchDto;

/**
 * 营业部与偏线代理Vo
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-28 上午11:41:24
 */
public class CommonSaleDeptAndOuterBranchVo implements Serializable {

	private static final long serialVersionUID = -71728098909174984L;

	/**
	 * 营业部与偏线代理Dto
	 */
	private CommonSaleDeptAndOuterBranchDto dto;

	/**
	 * 营业部与偏线代理List
	 */
	private List<CommonSaleDeptAndOuterBranchDto> saleDeptAndOuterBranchList;

	public CommonSaleDeptAndOuterBranchDto getDto() {
		return dto;
	}

	public void setDto(CommonSaleDeptAndOuterBranchDto dto) {
		this.dto = dto;
	}

	public List<CommonSaleDeptAndOuterBranchDto> getSaleDeptAndOuterBranchList() {
		return saleDeptAndOuterBranchList;
	}

	public void setSaleDeptAndOuterBranchList(
			List<CommonSaleDeptAndOuterBranchDto> saleDeptAndOuterBranchList) {
		this.saleDeptAndOuterBranchList = saleDeptAndOuterBranchList;
	}

}
