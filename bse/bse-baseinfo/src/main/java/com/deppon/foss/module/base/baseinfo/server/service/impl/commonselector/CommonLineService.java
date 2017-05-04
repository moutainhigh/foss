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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonLineService.java
 * 
 * FILE NAME        	: CommonLineService.java
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
 * FILE    NAME: CommonLineService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;

/**
 * 线路查询service实现.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午10:27:40
 */
public class CommonLineService implements ICommonLineService {
	
	/** The line dao. */
	private ILineDao lineDao;
	
	/**
	 * 根据实体条件分页查询线路.
	 *
	 * @param lineEntity the line entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-1 上午10:27:40
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLineService#queryLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity, int, int)
	 */
	@Override
	public List<LineEntity> queryLineListByCondition(LineEntity lineEntity,
			int start, int limit) {
		return lineDao.queryLineListByCondition(lineEntity, start, limit);
	}

	/**
	 * 根据实体条件查询线路总数.
	 *
	 * @param lineEntity the line entity
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-1 上午10:27:40
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonLineService#countLineListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
	 */
	@Override
	public Long countLineListByCondition(LineEntity lineEntity) {
		return lineDao.countLineListByCondition(lineEntity);
	}

	/**
	 * Sets the line dao.
	 *
	 * @param lineDao the new line dao
	 */
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}

}
