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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/OuterBranchDao.java
 * 
 * FILE NAME        	: OuterBranchDao.java
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
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外部网点数据访问类
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:51:02
 */
public class OuterBranchDao implements IOuterBranchDao {

	/**
	 * 代理网点
	 */
	private static final String AGENCYBRANCHCODE = "agencyBranchCode";
	/**
	 * 激活状态
	 */
	private static final String ACTIVE = "active";
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
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addOuterBranch(OuterBranchEntity outerBranch) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", outerBranch.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", outerBranch);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateOuterBranch(OuterBranchEntity outerBranch) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective",outerBranch);
	}

	/**
	 * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-24 上午9:21:53
	 * @param agencyBranchCode
	 *            代理网点编码
	 * @return AgencyBranchOrCompanyDto
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryAgencyBranchCompanyInfo(java.lang.String)
	 */
	public OuterBranchEntity queryAgencyBranchCompanyInfo(
			String agencyBranchCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AGENCYBRANCHCODE, agencyBranchCode);
		map.put(ACTIVE, FossConstants.ACTIVE);
		return (OuterBranchEntity) sqlSession.selectOne(NAMESPACE+ "queryAgencyBranchCompanyInfoLocal", map);
	}
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午4:30:20
	 * @see com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao#queryAgencyBranchInfo(java.lang.String)
	 */
	public OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode) {
	    	//编码为空则返回空
	    	if(StringUtil.isEmpty(agencyBranchCode)){
	    	    return null;
	    	}
	    	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(AGENCYBRANCHCODE, agencyBranchCode);
		map.put(ACTIVE, FossConstants.ACTIVE);
		return (OuterBranchEntity) sqlSession.selectOne(NAMESPACE+ "queryAgencyBranchInfoLocal", map);
	}

	/**
	 * 
	 * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author 094463-foss-xieyantao
     * @date 2012-11-2 上午9:00:56
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
     * @return
	 * @see com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
	 */
	public List<OuterBranchEntity> queryOuterBranchs(OuterBranchParamsDto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put(ACTIVE, FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "queryOuterBranchs", map);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
	 */
	@Override
	public void delete(OuterBranchEntity outerBranch) {
		sqlSession.delete(NAMESPACE + "delete",outerBranch);
	}

	@Override
	public List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity) {
		return sqlSession.selectList(NAMESPACE +"queryListByBranch",entity);
	}
}