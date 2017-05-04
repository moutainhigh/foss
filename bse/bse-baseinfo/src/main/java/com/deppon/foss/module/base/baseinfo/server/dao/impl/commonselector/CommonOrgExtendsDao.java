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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonOrgExtendsDao.java
 * 
 * FILE NAME        	: CommonOrgExtendsDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgExtendsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;

/**
 * 公共查询选择器--营业部和偏线代理网点信息查询Dao.
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-28 上午10:27:14
 */
public class CommonOrgExtendsDao extends SqlSessionDaoSupport implements
		ICommonOrgExtendsDao {

	private static final String NAEMSPACE = "foss.bse.bse-baseinfo.commmonOrgExtends.";

	/**
	 * 组织查询扩展添加营业部信息查询
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午10:51:59
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonOrgDto> searchCommonOrgExtendsByCondition(CommonOrgDto dto, int start, int limit) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAEMSPACE + "queryCommonOrgExtendsByCondition", dto, bounds);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-28 上午11:08:51
	 * @return
	 */
	@Override
	public long countReocrd(CommonOrgDto dto) {
		return (Long) this.getSqlSession().selectOne(NAEMSPACE + "countCommonOrgExtendsByCondition", dto);
	}
}
