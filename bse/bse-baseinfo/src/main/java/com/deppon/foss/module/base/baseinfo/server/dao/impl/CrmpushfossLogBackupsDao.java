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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/CrmpushfossLogBackupsDao.java
 * 
 * FILE NAME        	: CrmpushfossLogBackupsDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICrmpushfossLogBackupsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CrmpushfossLogBackupsEntity;

/**
 * 主客户数据转储日志信息 * 
 * version:V1.0,author:261997-foss-css,date:2015-07-11 下午17:27:49
 * </p>
 * 
 * @author 261997-foss-css
 * @date 2015-07-11 下午17:27:49
 * @since
 * @version
 */
public class CrmpushfossLogBackupsDao   extends SqlSessionDaoSupport  implements ICrmpushfossLogBackupsDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.CrmpushfossLogBackups.";

	@Override
	public Long countByCrmpushfossLogBackups(CrmpushfossLogBackupsEntity example) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "countByCrmpushfossLogBackups",
				example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		
		return 0;
	}

	@Override
	public int insert(CrmpushfossLogBackupsEntity record) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", record);
	}

	@Override
	public CrmpushfossLogBackupsEntity selectByPrimaryKey(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		List<CrmpushfossLogBackupsEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryById", map);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}		
	}

}
