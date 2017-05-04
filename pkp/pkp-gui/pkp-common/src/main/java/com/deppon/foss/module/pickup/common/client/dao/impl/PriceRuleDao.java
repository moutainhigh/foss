/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/PriceRuleDao.java
 * 
 * FILE NAME        	: PriceRuleDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.common.client.dto.PriceRuleDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRuleEntity;
import com.google.inject.Inject;

/**
 * 
 * 价格规则数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-8 上午9:55:26, </p>
 * @author foss-sunrui
 * @date 2012-11-8 上午9:55:26
 * @since
 * @version
 */
public class PriceRuleDao implements IPriceRuleDao {
	/**
	 * 名称空间
	 */
    private static final String NAMESPACE = "foss.pkp.PriceRuleEntityMapper.";
    /**
	 * 数据库连接
	 */
    private SqlSession sqlSession;
    /**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
    @Inject
    public void setSqlSession(SqlSession sqlSession) {
    	this.sqlSession = sqlSession;
    }
    
    /**
     * 
     * <p>通过主键查询</p> 
     * @author foss-sunrui
     * @date 2012-11-8 上午9:52:14
     * @param id
     * @return
     * @see
     */
    public PriceRuleEntity queryByPrimaryKey(String id) {
    	return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
    }
    /**
     * 
     * <p>通过价格编号查询</p> 
     * @author foss-sunrui
     * @date 2012-11-8 上午9:54:35
     * @param priceRule
     * @return
     * @see
     */
    public PriceRuleEntity queryByCode(PriceRuleDto priceRule) {
    	return sqlSession.selectOne(NAMESPACE + "selectPriceRuleByCode", priceRule);
    }
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addPriceRule(PriceRuleEntity priceRule) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", priceRule.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert( NAMESPACE + "insertPriceRule", priceRule);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * 功能：更新记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public void updatePriceRule(PriceRuleEntity priceRule) {
		sqlSession.update( NAMESPACE + "updatePriceRuleByPrimaryKey", priceRule); 
	}

}