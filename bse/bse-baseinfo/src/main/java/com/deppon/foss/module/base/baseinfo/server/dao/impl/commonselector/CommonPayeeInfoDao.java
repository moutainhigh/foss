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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonPayeeInfoDao.java
 * 
 * FILE NAME        	: CommonPayeeInfoDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector
 * FILE    NAME: CommonPayeeInfoDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 公共查询选择器--付款方信息查询.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-15 上午11:42:32
 */
public class CommonPayeeInfoDao extends SqlSessionDaoSupport implements
		ICommonPayeeInfoDao {
	
	/** The Constant NAEMSPACE. */
	private static final String NAEMSPACE = "foss.bse.bse-baseinfo.commmonPayeeInfo.";

	/**
	 * 查询付款方信息.
	 *
	 * @param payeeInfoEntity the payee info entity
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 上午11:42:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao#searchPayeeInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PayeeInfoEntity> searchPayeeInfoByCondition(
			PayeeInfoEntity payeeInfoEntity, int start, int limit) {

		RowBounds bounds = new RowBounds(start, limit);
		if(null != payeeInfoEntity.getAccountTypes())
			return this.getSqlSession().selectList(NAEMSPACE + "queryAllInfosNew",
				payeeInfoEntity, bounds);
		else
			return this.getSqlSession().selectList(NAEMSPACE + "queryAllInfos",
					payeeInfoEntity, bounds);
	}

	/**
	 * 查询总条数.
	 *
	 * @param payeeInfoEntity the payee info entity
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 上午11:42:32
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPayeeInfoDao#countPayeeInfoByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)
	 */
	@Override
	public long countPayeeInfoByCondition(PayeeInfoEntity payeeInfoEntity) {
		if(null != payeeInfoEntity.getAccountTypes())
			return (Long) this.getSqlSession().selectOne(NAEMSPACE + "queryCountNew",
				payeeInfoEntity);
		else
			return (Long) this.getSqlSession().selectOne(NAEMSPACE + "queryCount",
					payeeInfoEntity);

	}

}
