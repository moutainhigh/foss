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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/dao/impl/MonitorQueryDao.java
 * 
 * FILE NAME        	: MonitorQueryDao.java
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
 * FILE    NAME: MonitorQueryDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorDataDto;


/**
 * 监控查询DAO
 * @author ibm-zhuwei
 * @date 2013-2-27 下午2:34:53
 */
public class MonitorQueryDao extends iBatis3DaoImpl implements IMonitorQueryDao {

	private static final String NAMESPACE = "foss.bse.MonitorQueryDao.";// 命名空间路径
	
	/** 
	 * 通过类别查询指标组列表
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:35:38
	 * @param indicatorCategory
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryIndicatorGroupByCategory(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryIndicatorGroupByCategory(String indicatorCategory) {
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorCategory", indicatorCategory);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryIndicatorGroupByCategory", map);
		
	}

	/** 
	 * 通过指标组查询指标列表
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:35:38
	 * @param indicatorGroups
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryIndicatorsByIndicatorGroup(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorIndicatorEntity> queryIndicatorsByIndicatorGroup(
			List<String> indicatorGroups) {

		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorGroups", indicatorGroups);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryIndicatorsByIndicatorGroup", map);
		
	}

	/** 
	 * 按日查询常规业务指标
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:35:38
	 * @param monitorDate
	 * @param indicatorGroups
	 * @param orgLevel
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryDailyCommonIndicators(java.util.Date, java.util.List, int, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryDailyCommonIndicators(Date monitorDate,
			List<String> indicatorGroups, int orgLevel, String orgCode) {
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("monitorDate", monitorDate);
		map.put("indicatorGroups", indicatorGroups);
		map.put("indicatorCategory", BusinessMonitorIndicator.CATEGORY_COMMON);
		map.put("orgLevel", orgLevel);
		map.put("orgCode", orgCode);

		return this.getSqlSession().selectList(NAMESPACE + "queryDailyCommonIndicators", map);
		
	}

	/** 
	 * 按月查询常规业务指标
	 * @author ibm-zhuwei
	 * @date 2013-3-5 下午3:02:05
	 * @param monitorDate
	 * @param indicatorGroups
	 * @param orgLevel
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryMonthlyCommonIndicators(java.util.Date, java.util.List, int, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryMonthlyCommonIndicators(Date monitorBeginDate,
			Date monitorEndDate, List<String> indicatorGroups, int orgLevel,
			String orgCode) {

		Map<String, Object> map = new HashMap<String , Object>();
		map.put("monitorBeginDate", monitorBeginDate);
		map.put("monitorEndDate", monitorEndDate);
		map.put("indicatorGroups", indicatorGroups);
		map.put("indicatorCategory", BusinessMonitorIndicator.CATEGORY_COMMON);
		map.put("orgLevel", orgLevel);
		map.put("orgCode", orgCode);

		return this.getSqlSession().selectList(NAMESPACE + "queryMonthlyCommonIndicators", map);
		
	}

	/** 
	 * 按日查询新业务指标
	 * @author ibm-zhuwei
	 * @date 2013-3-6 下午3:00:56
	 * @param monitorDate
	 * @param indicatorGroup
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryDailyNewIndicators(java.util.Date, java.lang.String, java.lang.String, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryDailyNewIndicators(Date monitorDate,
			List<String>  indicatorGroups, int orgLevel, String orgCode) {

		Map<String, Object> map = new HashMap<String , Object>();
		map.put("monitorDate", monitorDate);
		map.put("indicatorCategory", BusinessMonitorIndicator.CATEGORY_NEW);
		map.put("indicatorGroups", indicatorGroups);
		map.put("orgLevel", orgLevel);
		map.put("orgCode", orgCode);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryDailyNewIndicators", map);
		
	}

	/** 
	 * 按日查询系统功能使用情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午10:04:22
	 * @param monitorDate
	 * @param level1OrgCode
	 * @param level2OrgCodes
	 * @param level3OrgCodes
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryDailyApplicationResource(java.util.Date, java.lang.String, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryDailyApplicationResource(Date monitorDate,
			String level1OrgCode, List<String> level2OrgCodes,
			List<String> level3OrgCodes,Date startTime, Date endTime) {
		
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorCode", BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode());
		map.put("monitorDate", monitorDate);
		map.put("level1OrgCode", level1OrgCode);
		map.put("level2OrgCodes", level2OrgCodes);
		map.put("level3OrgCodes", level3OrgCodes);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return this.getSqlSession().selectList(NAMESPACE + "queryDailyApplicationResource", map);
		
	}

	/** 
	 * 按日查询组织在线情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午10:04:22
	 * @param monitorDate
	 * @param orgLevel
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryDailyApplicationOnline(java.util.Date, java.lang.String, java.util.List, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryDailyApplicationOnline(Date monitorDate,
			int orgLevel, String orgCode,Date startTime, Date endTime) {

		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorCode", BusinessMonitorIndicator.ORG_ONLINE_COUNT.getCode());
		map.put("monitorDate", monitorDate);
		map.put("orgLevel", orgLevel);
		map.put("orgCode", orgCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getSqlSession().selectList(NAMESPACE + "queryDailyApplicationOnline", map);
		
	}

	/** 
	 * 按日查询模拟登陆情况
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午10:04:22
	 * @param monitorDate
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao#queryDailyApplicationLogin(java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MonitorDataDto> queryDailyApplicationLogin(Date monitorDate,Date startTime, Date endTime) {

		Map<String, Object> map = new HashMap<String , Object>();
		map.put("indicatorCode", BusinessMonitorIndicator.SIMULATE_LOGIN_COUNT);
		map.put("monitorDate", monitorDate);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getSqlSession().selectList(NAMESPACE + "queryDailyApplicationLogin", map);
		
	}


}
