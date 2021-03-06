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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IMonitorIndicatorEntityDao.java
 * 
 * FILE NAME        	: IMonitorIndicatorEntityDao.java
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
 * FILE    NAME: IMonitorIndicatorEntityDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.dao;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;


/**
 * 监控指标信息DAO
 * @author ibm-zhuwei
 * @date 2013-2-18 下午1:49:08
 */
public interface IMonitorIndicatorEntityDao {

	/**
	 * 通过指标编码，查询指标信息
	 * @author ibm-zhuwei
	 * @date 2013-2-18 下午2:20:30
	 * @param indicatorCode
	 * @return
	 */
	MonitorIndicatorEntity queryByCode(String indicatorCode);
}
