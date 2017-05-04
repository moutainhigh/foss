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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/api/server/sample/dao/SampleDao.java
 * 
 * FILE NAME        	: SampleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.sample.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.sample.SampleEntity;

@Repository
public class SampleDao extends iBatis3DaoImpl implements ISampleDao {

	@Override
	public SampleEntity getById(String id) {
		return (SampleEntity) getSqlSession().selectOne("com.deppon.foss.module.base.baseinfo.api.server.sample.SampleEntity.getById",id);
	}

	@Override
	public List<SampleEntity> getAll() {
		return this.getSqlSession().selectList("com.deppon.foss.module.base.baseinfo.api.server.sample.SampleEntity.getAll");
	}

}
