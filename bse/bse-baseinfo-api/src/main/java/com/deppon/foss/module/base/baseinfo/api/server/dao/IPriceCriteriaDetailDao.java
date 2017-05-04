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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPriceCriteriaDetailDao.java
 * 
 * FILE NAME        	: IPriceCriteriaDetailDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
	/**
	 * (计价方式明细,负责对所有相关价格明细管理)
	 * @author dujunhui-187862
	 * @date 2014-10-10 下午3:34:06
	 * @since
	 * @version
	 */
public interface IPriceCriteriaDetailDao {
 
	/**
	 * <p>(插入一条计价方式明细)</p>
	 * @author dujunhui-187862
	 * @date 2014-10-10 下午3:43:04
	 * @param record
	 * @see
	 */
	int insertSelective(PriceCriteriaDetailEntity record);

	/**
	 * <p>(原装插入)</p>
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:25:14
	 * @param record
	 * @see
	 */
	void copyOriginalSelective(PriceCriteriaDetailEntity record);

	/**
	 * 
	 * @Description: 根据主键查询
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:18:09
	 * @param id
	 * @return
	 * @version 
	 */
	PriceCriteriaDetailEntity selectByPrimaryKey(String id);

	/**
	 * <p>修改计价方式明细，查询条件是ID</p>
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:33:04
	 * @param record
	 * @see
	 */
	int updateCriteriaDetailByPrimaryKey(PriceCriteriaDetailEntity record);

	/**
	 * <p>根据计费规则ID删除计价规则明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午9:41:25
	 * @param valuationId
	 * @see
	 */
	void deleteCriteriaDetailByValuationId(String valuationId);
}