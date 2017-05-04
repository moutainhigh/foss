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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonOwnDriverDao.java
 * 
 * FILE NAME        	: CommonOwnDriverDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverDto;

/**
 * 公共查询选择器--公司司机DAO接口.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-10-29 下午4:11:14
 * @since
 * @version
 */
public class CommonOwnDriverDao extends SqlSessionDaoSupport implements
		ICommonOwnDriverDao {
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonOwndriver.";

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>.
	 *
	 * @param ownDriver 以“公司司机”实体承载的条件参数实体
	 * @param offset 开始记录数
	 * @param limit 限制记录数
	 * @return 符合条件的“公司司机”实体列表
	 * @author 078823-foss-panGuangJun
	 * @date 2012-10-29 下午4:24:23
	 */
	@SuppressWarnings("unchecked")
	public List<OwnDriverEntity> queryOwnDriverListBySelectiveCondition(
			OwnDriverEntity ownDriver, int offset, int limit) {
		RowBounds bounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOwnDriversByCondition", ownDriver, bounds);
	}

	/**
	 * 根据条件查询符合条件的公司车辆总数.
	 *
	 * @param ownDriver the own driver
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:01:03
	 * @retrun long
	 */
	public long queryOwnDriverRecordByCondition(OwnDriverEntity ownDriver) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "countOwnDriverByCondition", ownDriver);
	}

	/**
	 * 查询所有司机信息 (外请，公司司机).
	 *
	 * @param ownDriver the own driver
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:49:07
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnDriverDao#queryDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverDto> queryDriverListBySelectiveCondition(
			OwnDriverEntity ownDriver, int offset, int limit) {
		RowBounds bounds = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryDriversByCondition", ownDriver, bounds);
	}

	/**
	 * 查询所有司机信息 (外请，公司司机) 总数.
	 *
	 * @param ownDriver the own driver
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-28 上午10:49:07
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOwnDriverDao#queryDriverRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
	 */
	@Override
	public long queryDriverRecordByCondition(OwnDriverEntity ownDriver) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "countDriverByCondition", ownDriver);
	}
}
