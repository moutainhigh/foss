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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonPriceRegionDao.java
 * 
 * FILE NAME        	: CommonPriceRegionDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl
 * FILE    NAME: CommonPriceReginDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共查询组件--价格区域查询dao实现.
 *
 * @author panGuangJun
 * @date 2012-12-4 上午10:31:00
 */
public class CommonPriceRegionDao extends  SqlSessionDaoSupport implements
		ICommonPriceRegionDao {
	// 价格区域
	/** The Constant PRICING_REGION_NAMESPACE. */
	private static final String PRICING_REGION_NAMESPACE = "foss.bse.bse-baseinfo.pricingRegion.";
	// 根据条件查询区域
	/** The Constant SEARCH_REGION. */
	private static final String SEARCH_REGION = "searchRegionByCondition";
	// 根据条件查询区域个数
	/** The Constant COUNT_REGION. */
	private static final String COUNT_REGION = "countRegionByCondition";

	/**
	 * 根据条件查询价格区域.
	 *
	 * @param regionEntity the region entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-12-4 上午10:32:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRegionEntity> searchRegionByCondition(
			PriceRegionEntity regionEntity, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		String address=null;
		if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
			address = "searchAirRegionByCondition";
		}else{
			address = SEARCH_REGION;
		}
		List<PriceRegionEntity> regionEntityList = getSqlSession().selectList(
				PRICING_REGION_NAMESPACE + address, regionEntity,
				rowBounds);
		return regionEntityList;
	}

	/**
	 * 查询总条数.
	 *
	 * @param regionEntity the region entity
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-4 上午10:32:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPriceRegionDao#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
	 */
	@Override
	public Long countRegionByCondition(PriceRegionEntity regionEntity) {
		String address=null;
		if(StringUtils.equals(FossConstants.YES, regionEntity.getAirPriceFlag())){
			address = "countAirRegionByCondition";
		}else{
			address = COUNT_REGION;
		} 
		Object obj = getSqlSession().selectOne(
				PRICING_REGION_NAMESPACE + address, regionEntity);
		if (null==obj) {
			return 0L;
		}else{
			return (Long) obj;
		}
	}

}
