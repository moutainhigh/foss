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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/commonselector/CommonTransferCenterDao.java
 * 
 * FILE NAME        	: CommonTransferCenterDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferCenterEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto;


/**
 * 公共选择器--查询外场Dao
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午10:17:25
 */
public class CommonTransferCenterDao extends SqlSessionDaoSupport implements ICommonTransferCenterDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.commonTransferCenter.";
   
	/** 
	 * 根据条件查询外场信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-30 下午4:17:51
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao#queryTransferCenterByCondition(com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TransferCenterEntity> queryTransferCenterByCondition(TransferCenterDto dto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryTransferCenterByCondition",dto, rowBounds);
	}
 
	/** 
	 * 根据条件查询外场信息总条数
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-30 下午4:18:08
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTransferCenterDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.TransferCenterDto)
	 */
	@Override
	public Long queryRecordCount(TransferCenterDto dto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryRecordCount", dto);
	}
  

}
