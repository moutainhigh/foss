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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PricingValueAddedDao.java
 * 
 * FILE NAME        	: PricingValueAddedDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;

/**
 * 
 * 增值服务dao 里面只有两个方法，可以将这个去掉，讲方法组合到别的dao中
 * @author 078838 DP-Foss-zhangbin
 * @date 2012-11-12 下午4:33:08
 */
@Transactional
public class PricingValueAddedDao extends SqlSessionDaoSupport implements IPricingValueAddedDao {
	//计价条目ibatis命名空间
	private static final String PRICING_ENTITY_NAMESPACE = "foss.pkp.pkp-pricing.priceEntityMapper.";
	//查询增值服务类型
	private static final String SEARCHVALUEADDEDTYPR = "searchValueAddedType";

	/**
	 * .
	 * <p>
	 * 查询增值服务类型<br/>
	 *  方法名：searchValueAddedType
	 * </p>
	 * 
	 * @author 张斌
	 * @时间 2012-10-18
	 * @since JDK1.6
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PriceEntity> searchValueAddedType(PriceEntity priceEntity) {
		return getSqlSession().selectList(PRICING_ENTITY_NAMESPACE+SEARCHVALUEADDEDTYPR,priceEntity);
	}

}