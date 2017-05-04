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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/BankDao.java
 * 
 * FILE NAME        	: BankDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.pickup.common.client.dao.IBankDao;


/**
 * 银行 数据访问类
 * @author 105089-foss-yangtong
 * @date 2012-11-6 下午3:33:56
 */
public class BankDao implements IBankDao{
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.BankEntityMapper";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 
	 * 功能：按id查询
	 * @param:
	 * @return Department
	 * @since:1.6
	 */
	public BankEntity queryById(String id) {
		return sqlSession.selectOne(NAMESPACE + ".selectByPrimaryKey" + id);
	}
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addBank(BankEntity bank) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", bank.getId());
		String id = sqlSession.selectOne(NAMESPACE + ".selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + ".insertSelective", bank);
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
	public void updateBank(BankEntity bank) {
		sqlSession.update(NAMESPACE + ".updateByPrimaryKeySelective", bank);
		
	}
	
	/**
	 * 功能：查询记录
	 * @param:
	 * @return List<Department>
	 * @since:1.6
	 */
	public List<BankEntity> queryAll() {
		return sqlSession.selectList(NAMESPACE + ".getAll");
	}

}