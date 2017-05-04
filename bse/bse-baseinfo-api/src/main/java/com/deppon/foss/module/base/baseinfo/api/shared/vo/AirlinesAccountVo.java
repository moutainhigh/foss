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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AirlinesAccountVo.java
 * 
 * FILE NAME        	: AirlinesAccountVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AirlinesAccountDto;

/**
 * (航空公司账户VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:078838-foss-zhangbin,date:2012-12-03 下午6:15:07
 * </p>
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-12-03 下午6:15:07
 * @since
 * @version
 */
public class AirlinesAccountVo implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 3875916350859197349L;
	//航空公司账户实体
	private AirlinesAccountEntity airlinesAccountEntity;
	//航空公司账户实体LIST
	private List<AirlinesAccountEntity> airlinesAccountEntityList;
	//航空公司账户实体DTO list
	private List<AirlinesAccountDto> airlinesAccountDtoList;
	
	public List<AirlinesAccountDto> getAirlinesAccountDtoList() {
		return airlinesAccountDtoList;
	}
	public void setAirlinesAccountDtoList(
			List<AirlinesAccountDto> airlinesAccountDtoList) {
		this.airlinesAccountDtoList = airlinesAccountDtoList;
	}
	//航空公司账户ID实体
	private List<String> ids;
	
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<AirlinesAccountEntity> getAirlinesAccountEntityList() {
		return airlinesAccountEntityList;
	}
	public void setAirlinesAccountEntityList(
			List<AirlinesAccountEntity> airlinesAccountEntityList) {
		this.airlinesAccountEntityList = airlinesAccountEntityList;
	}
	public AirlinesAccountEntity getAirlinesAccountEntity() {
		return airlinesAccountEntity;
	}
	public void setAirlinesAccountEntity(AirlinesAccountEntity airlinesAccountEntity) {
		this.airlinesAccountEntity = airlinesAccountEntity;
	}
	
}
