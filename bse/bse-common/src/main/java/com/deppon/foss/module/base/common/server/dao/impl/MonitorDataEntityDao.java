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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/MonitorDataEntityDao.java
 * 
 * FILE NAME        	: MonitorDataEntityDao.java
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
 * FILE    NAME: MonitorDataEntityDaoImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorDataEntityDao;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;


/**
 * 监控数据DAO
 * @author ibm-zhuwei
 * @date 2013-2-1 下午5:14:33
 */
public class MonitorDataEntityDao extends iBatis3DaoImpl implements IMonitorDataEntityDao {

	private static final String NAMESPACE = "foss.bse.MonitorDataEntityDao.";// 命名空间路径

	/** 
	 * 批量新增监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-1 下午5:15:30
	 * @param list 监控数据
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorDataEntityDao#batchAddMonitorData(java.util.List)
	 */
	@Override
	public void batchAddMonitorData(List<MonitorDataEntity> list) {
		
		for (MonitorDataEntity entity : list) {
			this.getSqlSession().insert(NAMESPACE + "insertByBatch", entity);
		}
	}

	/** 
	 * 批量删除监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-1 下午5:20:34
	 * @param monitorDate 监控时间
	 * @param orgCode 监控网点
	 * @param indicatorCodes 指标组
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorDataEntityDao#batchDeleteMonitorData(java.util.Date, java.lang.String)
	 */
	@Override
	public int batchDeleteMonitorData(Date monitorDate, String monitorTimeRange, String orgCode, List<String> indicatorCodes) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monitorDate", monitorDate);
		map.put("monitorTimeRange", monitorTimeRange);
		map.put("orgCode", orgCode);
		map.put("indicatorCodes", indicatorCodes);
		
		return this.getSqlSession().delete(NAMESPACE + "deleteByBatch", map);
	}

}
