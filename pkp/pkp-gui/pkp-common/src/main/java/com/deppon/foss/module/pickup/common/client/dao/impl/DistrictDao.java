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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/DistrictDao.java
 * 
 * FILE NAME        	: DistrictDao.java
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

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.common.client.dao.IDistrictDao;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 行政区域数据持久层实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-16 下午5:26:35,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-16 下午5:26:35
 * @since
 * @version
 */
public class DistrictDao implements IDistrictDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "pkp.districtMapper.";
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
	 * <p>
	 * 通过主键获取行政区域
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午3:10:38
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDistrictDao#selectByPrimaryKey(java.lang.String)
	 */
	public AdministrativeRegionsEntity queryByPrimaryKey(String id) {
		return sqlSession.selectOne(NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * <p>
	 * 获取指定级别的行政区域
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午3:10:47
	 * @param degree
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDistrictDao#selectByDegree(java.lang.String)
	 */
	public List<AdministrativeRegionsEntity> queryByDegree(String degree) {
		AdministrativeRegionsEntity dictrict = new AdministrativeRegionsEntity();
		dictrict.setDegree(degree);
		dictrict.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectDistrictByDegree", dictrict);
	}
	
	/**
	 * <p>
	 * 获取指定级别的行政区域
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午3:10:47
	 * @param dictrict
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDistrictDao#selectByDegree(java.lang.String)
	 */
	public AdministrativeRegionsEntity queryByName(AdministrativeRegionsEntity dictrict) {
		dictrict.setActive(FossConstants.ACTIVE);
		List<AdministrativeRegionsEntity> list=sqlSession.selectList(NAMESPACE + "selectDistrictByName", dictrict);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}		
	}

	/**
	 * <p>
	 * 通过父级别行政区域代码获取子级别行政区域
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-16 下午3:10:52
	 * @param parentDistCode
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.dao.IDistrictDao#selectByParentDistCode(java.lang.String)
	 */
	public List<AdministrativeRegionsEntity> queryByParentDistCode(
			String parentDistCode) {
		AdministrativeRegionsEntity dictrict = new AdministrativeRegionsEntity();
		dictrict.setParentDistrictCode(parentDistCode);
		dictrict.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectByParentDistCode",dictrict);
	}

}