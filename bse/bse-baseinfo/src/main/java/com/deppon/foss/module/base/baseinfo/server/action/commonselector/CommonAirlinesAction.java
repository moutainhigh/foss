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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonAirlinesAction.java
 * 
 * FILE NAME        	: CommonAirlinesAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonAirAgencyDeptAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;

/**
 * 公共查询组件--航空公司ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-6 下午2:10:45
 */
public class CommonAirlinesAction extends AbstractAction implements
		IQueryAction {
	// search condition
	/** The airlines entity. */
	private AirlinesEntity airlinesEntity = new AirlinesEntity();
	// result
	/** The airlines entities. */
	private List<AirlinesEntity> airlinesEntities;
	// servie
	/** The common airlines service. */
	private ICommonAirlinesService  commonAirlinesService;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = -7738175841138885451L;

	/**
	 * 空运代理网点.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午2:10:45
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		airlinesEntities = commonAirlinesService
				.queryAirlines(airlinesEntity, start,limit);
		setTotalCount(commonAirlinesService
				.queryRecordCount(airlinesEntity));
		return returnSuccess();
	}

	/**
	 * getter.
	 *
	 * @return the airlines entities
	 */
	public List<AirlinesEntity> getAirlinesEntities() {
		return airlinesEntities;
	}

	/**
	 * setter.
	 *
	 * @param airlinesEntity the new airlines entity
	 */
	public void setAirlinesEntity(AirlinesEntity airlinesEntity) {
		this.airlinesEntity = airlinesEntity;
	}

	/**
	 * setter.
	 *
	 * @param commonAirlinesService the new common airlines service
	 */
	public void setCommonAirlinesService(
			ICommonAirlinesService commonAirlinesService) {
		this.commonAirlinesService = commonAirlinesService;
	}

	/**
	 * getter.
	 *
	 * @return the airlines entity
	 */
	public AirlinesEntity getAirlinesEntity() {
		return airlinesEntity;
	}


}
