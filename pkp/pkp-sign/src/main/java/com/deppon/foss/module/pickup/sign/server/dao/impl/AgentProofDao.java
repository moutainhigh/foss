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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/AgentProofDao.java
 * 
 * FILE NAME        	: AgentProofDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IAgentProofDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.AgentProofEntity;

/**
 * 
 * 凭证文件附件操作Dao类
 * @date 2012-11-20 下午6:16:18
 */
public class AgentProofDao extends iBatis3DaoImpl implements IAgentProofDao{

	// AgentProof命名空间
	private static final String AGENTPROOFNAMESPACE = 
				"com.deppon.foss.module.pickup.sign.api.shared.domain.AgentProofEntity.";
		
	// 插入数据库
	private static final String INSERT = "insert";
	
	// 插入数据库
	private static final String INSERTSELECT = "insertSelective";
	
	//更新数据库
	private static final String UPDATE = "updateByPrimaryKey";
	
	//更新数据库
	private static final String UPDATESELECTIVE = "updateByPrimaryKeySelective";
	
	//删除数据库
	private static final String DELETE = "deleteByPrimaryKey";
	
	//查询
	private static final String SELECTBYID = "selectByPrimaryKey";
	
	/**
	 * 删除凭证文件附
	 */
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(
				AGENTPROOFNAMESPACE+DELETE,id);
	}

	/**
	 * 凭证文件附件插入数据库
	 */
	public int insert(AgentProofEntity record) {
		return this.getSqlSession().insert(
				AGENTPROOFNAMESPACE+INSERT,record);
	}

	/**
	 * 凭证文件附件插入数据库
	 */
	public int insertSelective(AgentProofEntity record) {
		return this.getSqlSession().insert(
				AGENTPROOFNAMESPACE+INSERTSELECT,record);
	}

	/**
	 * 查询凭证文件附件
	 */
	public AgentProofEntity selectByPrimaryKey(String id) {
		return (AgentProofEntity)this.getSqlSession().selectOne(
				AGENTPROOFNAMESPACE+SELECTBYID,id);
	}

	/**
	 * 更新凭证文件附件
	 */
	@Override
	public int updateByPrimaryKeySelective(AgentProofEntity record) {
		return this.getSqlSession().update(
				AGENTPROOFNAMESPACE+UPDATESELECTIVE,record);
	}

	/**
	 * 凭证文件附件更新数据库
	 */
	public int updateByPrimaryKey(AgentProofEntity record) {
		return this.getSqlSession().update(
				AGENTPROOFNAMESPACE+UPDATE,record);
	}

	

}