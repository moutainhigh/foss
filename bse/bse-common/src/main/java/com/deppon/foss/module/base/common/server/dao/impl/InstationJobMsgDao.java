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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/InstationJobMsgDao.java
 * 
 * FILE NAME        	: InstationJobMsgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IInstationJobMsgDao;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;

/**
 * 站内消息Job分发Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-10 上午9:03:39
 */
public class InstationJobMsgDao extends iBatis3DaoImpl implements IInstationJobMsgDao {
	
	private static final String NAMESPACE = "foss.bse.bse-common.InstationJobMsgDao.";
	 
	/**
	 * 新增人员对组织发的站内消息
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午9:12:00
	 */
	@Override
	public int insertInstationJobMsg(InstationJobMsgEntity entity) {
		 return getSqlSession().insert(NAMESPACE + "insertInstationJobMsg", entity);
	}
	
	/**
	 * 根据状态查询出所有分发站内消数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-12-13 上午11:16:51
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InstationJobMsgEntity> queryInstationJobMsgByStatus(String status) {
		return getSqlSession().selectList(NAMESPACE + "queryInstationJobMsgByStatus", status);
	}

	@Override
	public int updateInstationJobMsgStatusById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id); 
		map.put("status",MessageConstants.MSG__STATUS__PROCESSED);
		return getSqlSession().update(NAMESPACE + "updateInstationJobMsgStatusById", map);
	}
}
