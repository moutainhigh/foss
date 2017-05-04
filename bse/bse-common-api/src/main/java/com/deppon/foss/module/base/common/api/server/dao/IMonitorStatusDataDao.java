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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IMonitorStatusDataDao.java
 * 
 * FILE NAME        	: IMonitorStatusDataDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.server.dao
 * FILE    NAME: IMonitorStatusDataDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.dto.MonitorIndicatorDataDto;


/**
 * 监控状态数据DAO
 * @author ibm-zhuwei
 * @date 2013-2-22 上午11:16:49
 */
public interface IMonitorStatusDataDao {

	/**
	 * 查询未处理订单数据
	 * @author ibm-zhuwei
	 * @date 2013-2-22 上午11:56:13
	 * @return
	 */
	List<MonitorIndicatorDataDto> queryPendingOrder();
}
