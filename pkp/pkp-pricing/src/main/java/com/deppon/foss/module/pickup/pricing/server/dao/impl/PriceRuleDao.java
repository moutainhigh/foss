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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceRuleDao.java
 * 
 * FILE NAME        	: PriceRuleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * @Description: 计价条目
 * PriceRuleDao.java Create on 2013-2-1 下午5:41:16
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PriceRuleDao extends SqlSessionDaoSupport implements IPriceRuleDao {
	//mybatis namespace
	private static final String NAME_SPACE = "com.deppon.foss.module.pickup.api.server.dao.PriceRuleEntityMapper.";
	/**
	 * 
	 * <p>查询计价价条目</p> 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-25 下午4:12:44
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao#selectPriceRuleByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceRuleEntity> selectPriceRuleByCondition(PriceRuleEntity record) {		
		return getSqlSession().selectList(NAME_SPACE + "selectByCondition", record);
	}
	
	 /**
     * @Description: 根据计价规则CODE与适用时间查询计价规则实体
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:38:11
     * @param code
     * @param billDate
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PriceRuleEntity queryPriceRuleByCode(String code, Date billDate) {
		Map parameterMap = new HashMap();
		parameterMap.put("active", FossConstants.ACTIVE);
		parameterMap.put("code", code);
		parameterMap.put("billDate", billDate);
		return (PriceRuleEntity)getSqlSession().selectOne(NAME_SPACE + "queryPriceRuleByCode", parameterMap);
	}
	
    /**
     * @Description: 根据计价规则CODE查询计价规则实体集合
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:38:11
     * @param code
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PriceRuleEntity> queryPriceRuleByCode(String code) {
		Map parameterMap = new HashMap();
		parameterMap.put("active", FossConstants.ACTIVE);
		parameterMap.put("code", code);
		return getSqlSession().selectList(NAME_SPACE + "queryPriceRuleByCode", parameterMap);
	}

}