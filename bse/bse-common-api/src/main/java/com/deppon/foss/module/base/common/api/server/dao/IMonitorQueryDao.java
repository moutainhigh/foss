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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/dao/IMonitorQueryDao.java
 * 
 * FILE NAME        	: IMonitorQueryDao.java
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
 * FILE    NAME: IMonitorQueryDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorDataDto;


/**
 * 监控查询DAO
 * @author ibm-zhuwei
 * @date 2013-2-27 上午11:53:39
 */
public interface IMonitorQueryDao {

	/**
	 * 通过类别查询指标组列表
	 * @author ibm-zhuwei
	 * @date 2013-2-27 上午11:56:17
	 * @param category
	 * @return
	 */
	List<String> queryIndicatorGroupByCategory(String indicatorCategory);
	
	/**
	 * 通过指标组查询指标列表
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午1:56:50
	 * @param indicatorGroup
	 * @return
	 */
	List<MonitorIndicatorEntity> queryIndicatorsByIndicatorGroup(List<String> indicatorGroups);
	
	/**
	 * 按日查询常规业务指标
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:24:38
	 * @return
	 */
	List<MonitorDataDto> queryDailyCommonIndicators(Date monitorDate,
			List<String> indicatorGroups, int orgLevel, String orgCode);

	/**
	 * 按月查询常规业务指标
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:24:38
	 * @return
	 */
	List<MonitorDataDto> queryMonthlyCommonIndicators(Date monitorBeginDate,
			Date monitorEndDate, List<String> indicatorGroups, int orgLevel,
			String orgCode);

	/**
	 * 按日查询新业务指标
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:24:38
	 * @return
	 */
	List<MonitorDataDto> queryDailyNewIndicators(Date monitorDate,
			List<String>  indicatorGroups, int orgLevel, String orgCode);

	/**
	 * 按日查询系统功能使用情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午9:54:53
	 * @param monitorDate
	 * @param level1OrgCode
	 * @param level2OrgCodes
	 * @param level3OrgCodes
	 * @return
	 */
	List<MonitorDataDto> queryDailyApplicationResource(Date monitorDate,
			String level1OrgCode, List<String> level2OrgCodes, List<String> level3OrgCodes,Date startTime, Date endTime);

	/**
	 * 按日查询组织在线情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午9:54:53
	 * @param monitorDate
	 * @param orgLevel
	 * @param orgCode
	 * @return
	 */
	List<MonitorDataDto> queryDailyApplicationOnline(Date monitorDate,
			int orgLevel, String orgCode,Date startTime, Date endTime);

	/**
	 * 按日查询模拟登陆情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午9:54:53
	 * @param monitorDate
	 * @return
	 */
	List<MonitorDataDto> queryDailyApplicationLogin(Date monitorDate,Date startTime, Date endTime);
	
}
