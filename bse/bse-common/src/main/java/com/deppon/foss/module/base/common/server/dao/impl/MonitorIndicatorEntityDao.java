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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/MonitorIndicatorEntityDao.java
 * 
 * FILE NAME        	: MonitorIndicatorEntityDao.java
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
 * FILE    NAME: MonitorIndicatorEntityDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorIndicatorEntityDao;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;


/**
 * 监控指标信息DAO
 * @author ibm-zhuwei
 * @date 2013-2-18 下午2:21:56
 */
public class MonitorIndicatorEntityDao extends iBatis3DaoImpl implements IMonitorIndicatorEntityDao {

	private static final String NAMESPACE = "foss.bse.MonitorIndicatorEntityDao.";// 命名空间路径
	
	/** 
	 * 通过指标编码，查询指标信息
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午2:22:21
	 * @param indicatorCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorIndicatorEntityDao#queryByCode(java.lang.String)
	 */
	@Override
	public MonitorIndicatorEntity queryByCode(String indicatorCode) {
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorCode", indicatorCode);
		
		return (MonitorIndicatorEntity) this.getSqlSession().selectOne(NAMESPACE + "selectByCode", map);
	}

}
