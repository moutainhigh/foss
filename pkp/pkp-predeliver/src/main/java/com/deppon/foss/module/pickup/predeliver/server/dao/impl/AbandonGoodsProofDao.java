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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/AbandonGoodsProofDao.java
 * 
 * FILE NAME        	: AbandonGoodsProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsProofDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsProofEntity;

/**
 * 凭证纪录dao
 * @author admin
 *
 */
public class AbandonGoodsProofDao extends iBatis3DaoImpl implements IAbandonGoodsProofDao {

	//凭证name space
	private static final String NAMESPACE 
		= "com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsProofEntity."; 
	
	//插入数据库sql
	private static final String INSERT = "insertSelective"; 
	
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	/**
	 * 加入数据库凭证纪录
	 * @date 2012-11-26 下午7:57:00
	 */
	public int insert(AbandonGoodsProofEntity record) {
		return this.getSqlSession().insert(
			NAMESPACE+INSERT,record);
		
	}

	/**
	 * 加入数据库凭证纪录
	 * @date 2012-11-26 下午7:57:00
	 */
	public int insertSelective(AbandonGoodsProofEntity record) {
		return 0;
	}

	/**
	 *选择数据库凭证纪录
	 * @date 2012-11-26 下午7:57:00
	 */
	public AbandonGoodsProofEntity selectByPrimaryKey(String id) {
		return null;
	}

	/**
	 *update数据库凭证纪录
	 * @date 2012-11-26 下午7:57:00
	 */
	public int updateByPrimaryKeySelective(AbandonGoodsProofEntity record) {
		return 0;
	}

	/**
	 *update数据库凭证纪录
	 * @date 2012-11-26 下午7:57:00
	 */
	public int updateByPrimaryKey(AbandonGoodsProofEntity record) {
		return 0;
	}

}