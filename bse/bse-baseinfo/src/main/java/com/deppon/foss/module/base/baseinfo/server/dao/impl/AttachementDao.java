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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AttachementDao.java
 * 
 * FILE NAME        	: AttachementDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.server.web.upload.AttachementEntity;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAttachementDao;

/**
 * 附件信息DAO接口实现：对附件信息增删改查操作
 * @author DPAP
 * @date 2013-02-27
 */
public class AttachementDao extends SqlSessionDaoSupport implements IAttachementDao {

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.attachement.";

	@Override
	public int addAttachementInfo(AttachementEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "addAttachementInfo", entity);
	}
	
	@Override
	public int deleteAttachementInfo(String id, String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", "N");
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("id", id);
		return this.getSqlSession().insert(NAMESPACE + "deleteAttachementInfo", map);
	}

	@Override
	public int updateAttachementInfo(AttachementEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateAttachementInfo", entity);
	}

	@Override
	public AttachementEntity queryAttachementInfoById(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("active", "Y");
		return (AttachementEntity)this.getSqlSession().selectOne(NAMESPACE + "getAttachementInfoById", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AttachementEntity> getAttachementInfos(String relatedKey, String modulePath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relatedKey", relatedKey);
		map.put("modulePath", modulePath);
		map.put("active", "Y");
		return this.getSqlSession().selectList(NAMESPACE + "getAttachementInfos", map);
	}
	
}
