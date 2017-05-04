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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/OutfieldDao.java
 * 
 * FILE NAME        	: OutfieldDao.java
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
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.pickup.common.client.dao.IOutfieldDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外场数据访问类
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:51:02
 */
public class OutfieldDao implements IOutfieldDao {
	
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.OutfieldEntityMapper.";
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
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addOutfield(OutfieldEntity outfield) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", outfield.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSelective", outfield);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateOutfield(OutfieldEntity outfield) {
		sqlSession.update(NAMESPACE + "updateByPrimaryKeySelective", outfield);

	}
	  /**
     * 精确查询
     * 根据orgCode 查询
     * 
     * @author foss-jiangfei
     * @date 2012-11-27 下午2:56:36
     */
	public OutfieldEntity queryOutfieldByOrgCode(String orgCode) {
		if (StringUtils.isBlank(orgCode)) {
		    return null;
		}
		
		// 构造查询条件：
		OutfieldEntity entity=new OutfieldEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setOrgCode(orgCode);
		
		List<OutfieldEntity> entitys = sqlSession.selectList(
			NAMESPACE + "queryOutfieldByOrgCodeLocal", entity);
		if (entitys.size()==0) {
		    return null;
		} else {
		    return entitys.get(0);
		}
	}
	
	
	/**
	 * 通过空运总调查外场
	 * 
	 * @author foss-zhangxiaohui
	 * @date Jan 31, 2013 10:48:29 AM
	 */
	public String queryTransferCenterByAirDispatchCode(String airDispatchCode){
    	//声明传参的map
		Map<String, Object> map = new HashMap<String, Object>();	
		//添加空运总调编码到map
		map.put("airDispatchCode",airDispatchCode);	
		//添加是否有效
		map.put("active",FossConstants.ACTIVE);	
		//返回查询结果
		return (String) sqlSession.selectOne(NAMESPACE + "queryTransferCenterByAirDispatchCode", map);
    }

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.common.client.dao.IOutfieldDao#delete(com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity)
	 */
	@Override
	public void delete(OutfieldEntity outfield) {
		sqlSession.delete(NAMESPACE + "delete",outfield);
		
	}
}