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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/server/service/IMonitorQueryService.java
 * 
 * FILE NAME        	: IMonitorQueryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.server.service
 * FILE    NAME: IMonitorQueryService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.module.base.common.api.shared.vo.MonitorVo;

/**
 * 监控查询服务
 * 
 * @author ibm-zhuwei
 * @date 2013-2-27 上午11:51:21
 */
public interface IMonitorQueryService {

	/**
	 * 通过类别查询指标组列表
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午2:30:47
	 * @param indicatorCategory
	 *            指标类别，常规业务/新业务
	 * @return
	 */
	List<String> queryIndicatorGroupByCategory(String indicatorCategory);

	/**
	 * 通过指标类别查询常规业务表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:11:09
	 * @param indicatorGroups
	 *            指标组
	 * @return 【指标组，组内指标列表】
	 */
	LinkedHashMap<String, List<MonitorIndicatorEntity>> queryCommonIndicatorHeader(List<String> indicatorGroups);

	/**
	 * 按日查询常规业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:17:41
	 * @param monitorDate
	 *            监控日期
	 * @param indicatorGroups
	 *            指标组
	 * @param orgCode
	 *            网点编码
	 * @return 【指标编码，指标值】
	 */
	List<Map<String, String>> queryDailyCommonIndicatorData(Date monitorDate, List<String> indicatorGroups,
			String orgCode);

	/**
	 * 按月查询常规业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:17:41
	 * @param monitorDate
	 *            监控日期
	 * @param indicatorGroups
	 *            指标组
	 * @param orgCode
	 *            网点编码
	 * @return 【指标编码，指标值】
	 */
	List<Map<String, String>> queryMonthlyCommonIndicatorData(Date monitorDate, List<String> indicatorGroups,
			String orgCode);

	/**
	 * 通过指标类别查询新业务表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:11:09
	 * @param indicatorGroups
	 *            指标组
	 * @return 【指标组，组内指标列表】
	 */
	LinkedHashMap<String, List<MonitorIndicatorEntity>> queryNewIndicatorHeader(List<String> indicatorGroups);

	/**
	 * 按日查询新业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:17:41
	 * @param monitorDate
	 *            监控日期
	 * @param indicatorGroups
	 *            指标组
	 * @param orgCodes
	 *            网点编码列表
	 * @return 【指标编码，指标值】
	 */
	List<Map<String, String>> queryDailyNewIndicatorData(Date monitorDate, List<String> indicatorGroups,List<String> orgCodes);

	/**
	 * 查询应用数据表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:11:09
	 * @return 【表头编码，表头名称】
	 */
	LinkedHashMap<String, String> queryApplicationIndicatorHeader(String indicatorGroup);

	/**
	 * 按日查询应用数据表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:17:41
	 * @param monitorDate
	 *            监控日期
	 * @param indicatorGroup
	 *            指标组
	 * @param orgCodes
	 *            网点编码列表
	 * @return 【指标编码，指标值】
	 */
	List<Map<String, String>> queryDailyApplicationIndicatorData(Date monitorDate, String indicatorGroup,
			List<String> orgCodes,Date startTime,Date endTime);

	/**
	 * 导出数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午2:00:20
	 */
	InputStream exportExcelStream(MonitorVo vo, Date monitorDate,String sheetName,String total);

}
