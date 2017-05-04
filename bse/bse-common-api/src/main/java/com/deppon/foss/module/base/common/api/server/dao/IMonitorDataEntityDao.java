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
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.server.dao
 * FILE    NAME: IMonitorDataEntityDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;


/**
 * 监控数据DAO
 * @author ibm-zhuwei
 * @date 2013-2-1 下午4:50:02
 */
public interface IMonitorDataEntityDao {

	/**
	 * 批量新增监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-1 下午5:16:44
	 * @param list 监控数据
	 */
	void batchAddMonitorData(List<MonitorDataEntity> list);
	
	/**
	 * 批量删除监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-1 下午5:19:17
	 * @param monitorDate 监控时间
	 * @param orgCode 监控网点
	 * @param indicatorCodes 指标组
	 * @return
	 */
	int batchDeleteMonitorData(Date monitorDate, String monitorTimeRange, String orgCode, List<String> indicatorCodes);
	
}
