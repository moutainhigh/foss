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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/OrgInfoDao.java
 * 
 * FILE NAME        	: OrgInfoDao.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.common.client.dao.IOrgInfoDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:32:32,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:32:32
 * @since
 * @version
 */
public class OrgInfoDao implements IOrgInfoDao {
	/**
	 * 激活状态
	 */
	private static final String ACTIVE = "active";
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.OrgInfoEntityMapper.";
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
	 * 功能：按id查询
	 * 
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	public OrgAdministrativeInfoEntity queryById(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * (功能：按code查询)
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-7 下午7:24:32
	 * @param id
	 * @return
	 * @see
	 */
	public OrgAdministrativeInfoEntity queryByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("billDate", new Date());
		List<OrgAdministrativeInfoEntity> alist = sqlSession.selectList(
				NAMESPACE + "selectByOrgcode", map);
		if (alist.size() == 0) {
			return null;
		} else {
			return alist.get(0);
		}
	}
	
	/**
	 * 该方法用于根据服务器时间查询组织信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-20 下午6:26:49
	 */
	@Override
	public OrgAdministrativeInfoEntity queryByCodeAndServerTime(String code,Date time) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		
		if(null == time){
			return queryByCode(code);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("billDate", time);
		List<OrgAdministrativeInfoEntity> alist = sqlSession.selectList(NAMESPACE + "selectByOrgcode", map);
		if (alist.size() == 0) {
			return null;
		} else {
			return alist.get(0);
		}
	}

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addOrgInfo(OrgAdministrativeInfoEntity orgInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", orgInfo.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertOrgAdministrativeInfo", orgInfo);
			sqlSession.delete(NAMESPACE + "insertOrgAdministrativeInfoByCodeAndExcludeId", orgInfo);
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
	public void updateOrgInfo(OrgAdministrativeInfoEntity orgInfo) {
		sqlSession.update(NAMESPACE + "updateOrgAdministrativeInfo", orgInfo);
	}

	/**
	 * 功能：查询记录
	 * 
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<OrgAdministrativeInfoEntity> queryAll() {
		return sqlSession.selectList(NAMESPACE + "getAll");
	}

	/**
	 * 根据标杆编码查询组织信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 上午9:48:55
	 */
	public OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTIVE, FossConstants.ACTIVE);
		map.put("unifiedCode", unifiedCode);
		return sqlSession.selectOne(NAMESPACE + "selectOrgByUnifiedCode",map);
	}
	
	
	/**
	 * 
	 * 查询空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-21 下午07:29:51
	 * @return
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgAirDepartment(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ACTIVE, FossConstants.ACTIVE);
		map.put("doAirDispatch", FossConstants.ACTIVE);
		map.put("airDispatch", FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectAirDepartment",map);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.IOrgInfoDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
	 */
	public void delete(OrgAdministrativeInfoEntity orgInfoEntity) {
		sqlSession.delete(NAMESPACE + "delete", orgInfoEntity);
	}
	
	/**
	 * 
	 * 根据部门名称查询部门信息
	 * @author WangQianJin
	 * @date 2013-05-16
	 * @return
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgInfoByName(String name){
		OrgAdministrativeInfoEntity org=new OrgAdministrativeInfoEntity();
		org.setActive(FossConstants.ACTIVE);
		org.setName(name);
		return sqlSession.selectList(NAMESPACE + "queryOrgInfoByName", org);
	}
}