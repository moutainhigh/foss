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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonAirlinesService.java
 * 
 * FILE NAME        	: CommonAirlinesService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector
 * FILE    NAME: CommonAirlinesService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;

/**
 * 航空公司查询--service实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-7 下午3:50:09
 */
public class CommonAirlinesService implements ICommonAirlinesService {
	
	/** The common airlines dao. */
	private ICommonAirlinesDao commonAirlinesDao;
	
	/**
	 * 航空公司查询.
	 *
	 * @param entity the entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:50:09
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirlinesService#queryAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity, int, int)
	 */
	@Override
	public List<AirlinesEntity> queryAirlines(AirlinesEntity entity, int start,
			int limit) {
		if(("true").equals(entity.getAll())){
			entity.setActive(null);
		}
		return commonAirlinesDao.queryAirlines(entity, start, limit);
	}

	/**
	 * 航空公司总数.
	 *
	 * @param entity the entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-7 下午3:50:09
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAirlinesService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
	 */
	@Override
	public Long queryRecordCount(AirlinesEntity entity) {
		if(("true").equals(entity.getAll())){
			entity.setActive(null);
		}
		return commonAirlinesDao.queryRecordCount(entity);
	}

	/**
	 * setter.
	 *
	 * @param commonAirlinesDao the new common airlines dao
	 */
	public void setCommonAirlinesDao(ICommonAirlinesDao commonAirlinesDao) {
		this.commonAirlinesDao = commonAirlinesDao;
	}

}
