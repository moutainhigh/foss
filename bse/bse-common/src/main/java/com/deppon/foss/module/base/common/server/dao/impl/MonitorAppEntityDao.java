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
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IMonitorDataEntityDao.java
 * 
 * FILE NAME        	: IMonitorDataEntityDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.dao.impl
 * FILE    NAME: MonitorAppEntityDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorAppEntityDao;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorAppEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 监控应用配置DAO
 * @author ibm-zhuwei
 * @date 2013-3-19 上午11:01:18
 */
public class MonitorAppEntityDao extends iBatis3DaoImpl implements IMonitorAppEntityDao {

	/**
	 * 命名空间路径
	 */
	private static final String NAMESPACE = "foss.bse.MonitorAppEntityDao.";

	/** 
	 * 查询所有有效的应用系统列表
	 * @author ibm-zhuwei
	 * @date 2013-3-19 上午11:01:52
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorAppEntityDao#queryActiveMonitorApps()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorAppEntity> queryActiveMonitorApps() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryActiveMonitorApps", map);
	}

}
