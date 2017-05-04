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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AirlinesVo.java
 * 
 * FILE NAME        	: AirlinesVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;

/**
 * (航空公司VO)
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
public class AirlinesVo implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 3875916350859197349L;
	//航空公司
	private AirlinesEntity airlinesEntity;
	//航空公司LIST
	private List<AirlinesEntity> airlinesEntityList;
	//航空公司ID集合String
	private String idsStr;
	
	public String getIdsStr() {
		return idsStr;
	}
	public void setIdsStr(String idsStr) {
		this.idsStr = idsStr;
	}
	public List<AirlinesEntity> getAirlinesEntityList() {
		return airlinesEntityList;
	}
	public void setAirlinesEntityList(List<AirlinesEntity> airlinesEntityList) {
		this.airlinesEntityList = airlinesEntityList;
	}
	public AirlinesEntity getAirlinesEntity() {
		return airlinesEntity;
	}
	public void setAirlinesEntity(AirlinesEntity airlinesEntity) {
		this.airlinesEntity = airlinesEntity;
	}
	
}
