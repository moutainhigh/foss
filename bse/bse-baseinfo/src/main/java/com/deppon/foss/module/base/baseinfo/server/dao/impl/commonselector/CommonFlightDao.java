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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonFlightDao.java
 * 
 * FILE NAME        	: CommonFlightDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonFlightDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FlightDto;

/**
* 公共查询组件--“航班信息”的数据库对应数据访问DAO接口
* @author 101911-foss-zhouChunlai
* @date 2013-1-8 下午2:44:33
*/
public class CommonFlightDao extends SqlSessionDaoSupport implements ICommonFlightDao {
    
    /** The Constant NAMESPACE. */
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonFlight.";

	 /**
     * 根据传入对象查询符合条件所有航班信息 
     * @author 101911-foss-zhouChunlai
    * @date 2013-1-8 下午2:44:44
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<FlightEntity> queryFlightListByCondition(FlightDto dto,int limit,int start) {
		RowBounds bounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryFlightListByCondition", dto, bounds);
	}

	 /**
     * 统计总记录数 
     * @author 101911-foss-zhouChunlai
     * @param 
     * @date 2013-1-8 下午2:53:10
     * @return 
     */
	@Override
	public Long countFlightListByCondition(FlightDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countFlightListByCondition", dto);
	}


}
