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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonGoodsAreaService.java
 * 
 * FILE NAME        	: CommonGoodsAreaService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl
 * FILE    NAME: CommonGoodsAreaService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;

/**
 * 公共选择器--货区查询service.
 *
 * @author panGuangJun
 * @date 2012-12-1 上午9:31:00
 */
public class CommonGoodsAreaService implements ICommonGoodsAreaService {

	/** The goods area dao. */
	private IGoodsAreaDao goodsAreaDao;
	
	/**
	 * 查询货区.
	 *
	 * @param goodsArea the goods area
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:31:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGoodsAreaService#countGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
	 */
	@Override
	public long countGoodsAreaByCondition(GoodsAreaEntity goodsArea) {
		return goodsAreaDao.countGoodsAreaByCondition(goodsArea);
	}

	/**
	 * 查询获取总条数.
	 *
	 * @param goodsArea the goods area
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-1 上午9:31:00
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonGoodsAreaService#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity, int, int)
	 */
	@Override
	public List<GoodsAreaEntity> queryGoodsAreaByCondition(
			GoodsAreaEntity goodsArea, int start, int limit) {
		return goodsAreaDao.queryGoodsAreaByCondition(goodsArea, start, limit);
	}

	/**
	 * Sets the goods area dao.
	 *
	 * @param goodsAreaDao the new goods area dao
	 */
	public void setGoodsAreaDao(IGoodsAreaDao goodsAreaDao) {
		this.goodsAreaDao = goodsAreaDao;
	}

}
