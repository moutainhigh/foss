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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/MonitorStatusDataDao.java
 * 
 * FILE NAME        	: MonitorStatusDataDao.java
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
 * FILE    NAME: MonitorStatusDataDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorStatusDataDao;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorIndicatorDataDto;


/**
 * 监控状态数据DAO
 * @author ibm-zhuwei
 * @date 2013-2-22 下午1:51:48
 */
public class MonitorStatusDataDao extends iBatis3DaoImpl implements IMonitorStatusDataDao {

	private static final String NAMESPACE = "foss.bse.MonitorStatusDataDao.";// 命名空间路径
	
	/** 
	 * 查询未处理订单数据
	 * @author ibm-zhuwei
	 * @date 2013-2-22 下午1:52:32
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorStatusDataDao#queryPendingOrder()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorIndicatorDataDto> queryPendingOrder() {
		return this.getSqlSession().selectList(NAMESPACE + "queryPendingOrder");
	}

}
