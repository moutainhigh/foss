/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PublishPriceDao.java
 * 
 * FILE NAME        	: PublishPriceDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;
/**
 * 
 * 计费规则dao
 * @author 078838 DP-Foss-zhangbin
 * @date 2012-11-12 下午4:33:08
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPublishPriceExpressDao;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceExpressDto;

public class PublishPriceExpressDao extends SqlSessionDaoSupport implements IPublishPriceExpressDao {
	
	// 计费规则ibatis命名空间
	private static final String PRICING_ENTITY_VALUATION = "foss.pricing.publishPriceExpressEntityMapper.";
	/**
	 * 查询公布价价格区域 
	 * @author sz
	 * @date 2012-11-29 下午3:45:49
	 * @param startDeptNo
	 * @param destinationCode
	 * @param receiveDate
	 * @param type
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PublishPriceExpressDto> queryPublishPriceValuationByCalculaCondition(PublishPriceExpressDto dto) {
	    return getSqlSession().selectList(PRICING_ENTITY_VALUATION+"findPublishPriceByCodition", dto);
	}
	/**
	 * 
	 * @Description: 根据出发和到达区域ID集合查询公布价价格区域
	 * @author FOSSDP-sz
	 * @date 2013-1-25 下午4:20:20
	 * @param deptRegionIds
	 * @param arrvieRegionIds
	 * @param type
	 * @param active
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PublishPriceExpressDto> queryPublishPriceByRegionIds(List<String> deptRegionIds, List<String> arrvieRegionIds,
			String type, String active, Date billDate) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("deptRegionIds", deptRegionIds);
		map.put("arrvRegionIds", arrvieRegionIds);
		map.put("type", type);
		map.put("active", active);
		map.put("billDate", billDate);
		return getSqlSession().selectList(PRICING_ENTITY_VALUATION+"findPublishPriceByRegionIds", map);
	}
}