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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/VehicleAgencyDeptDao.java
 * 
 * FILE NAME        	: VehicleAgencyDeptDao.java
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

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;

/**
 * 偏线代理网点Dao接口实现：对偏线代理网点信息的增删改查的基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-15
 * 下午2:14:19,
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 下午2:14:19
 * @since
 * @version
 */
public class VehicleAgencyDeptDao implements IVehicleAgencyDeptDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.OuterBranchEntityMapper.";
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
	 * 根据传入参数查询代理网点（空运代理网点和偏线代理网点）
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-2 上午9:00:56
	 * @param dto
	 *            参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
	 */
	public List<OuterBranchEntity> queryOuterBranchs(QueryPickupPointDto dto) {
		return sqlSession.selectList(NAMESPACE + "queryOuterBranchs", dto);
	}
	
	/**
     * 根据条件查询偏线信息
     * @author WangQianJin
     * @date 2013-7-19 下午1:52:21
     */
    public OuterBranchEntity queryOuterBranchByDto(QueryPickupPointDto dto2){
    	List<OuterBranchEntity> list = sqlSession.selectList(NAMESPACE + "queryOuterBranchByDto", dto2);
    	if(list!=null && list.size()>0){
    		return list.get(0);
    	}else{
    		return null;
    	}
    }

}