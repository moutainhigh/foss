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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MonitorQueryService.java
 * 
 * FILE NAME        	: MonitorQueryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.service.impl
 * FILE    NAME: MonitorQueryService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.server.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bsh.EvalError;
import bsh.Interpreter;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgLayerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorQueryDao;
import com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.define.MonitorConstant;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorDataDimensionDto;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorDataDto;
import com.deppon.foss.module.base.common.api.shared.exception.BusinessMonitorException;
import com.deppon.foss.module.base.common.api.shared.vo.MonitorVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 监控查询服务
 * 
 * @author ibm-zhuwei
 * @date 2013-2-27 下午3:09:40
 */
public class MonitorQueryService implements IMonitorQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorIndicatorService.class);

	/**
	 * 根组织名称
	 */
	public static final String ROOT_ORG_NAME = "全国";
	
	/**
	 * 日期常量
	 */
	public static final String MONITOR_DATE = "monitorDate";
	
	/**
	 * 时间段常量
	 */
	public static final String MONITOR_TIME_RANGE = "monitorTimeRange";
	
	/**
	 * 监控查询DAO
	 */
	private IMonitorQueryDao monitorQueryDao;

	/**
	 * 组织层级服务
	 */
	private IOrgLayerService orgLayerService;

	/**
	 * 通过类别查询指标组列表
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午4:25:30
	 * @param indicatorCategory
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryIndicatorGroupByCategory(java.lang.String)
	 */
	@Override
	public List<String> queryIndicatorGroupByCategory(String indicatorCategory) {

		LOGGER.debug("通过类别查询指标组列表,类别:" + indicatorCategory);

		// 输入参数校验
		if (StringUtils.isEmpty(indicatorCategory)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		return monitorQueryDao.queryIndicatorGroupByCategory(indicatorCategory);

	}

	/**
	 * 通过指标类别查询常规业务表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:18:46
	 * @param indicatorGroups
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryCommonIndicatorHeader(java.util.List)
	 */
	@Override
	public LinkedHashMap<String, List<MonitorIndicatorEntity>> queryCommonIndicatorHeader(List<String> indicatorGroups) {

		LOGGER.debug("通过指标类别查询常规业务表头,指标组:" + indicatorGroups);

		// 输入参数校验
		if (CollectionUtils.isEmpty(indicatorGroups)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		List<MonitorIndicatorEntity> indicators = monitorQueryDao.queryIndicatorsByIndicatorGroup(indicatorGroups);

		LinkedHashMap<String, List<MonitorIndicatorEntity>> map = new LinkedHashMap<String, List<MonitorIndicatorEntity>>();
		for (MonitorIndicatorEntity entity : indicators) {
			String group = entity.getIndicatorGroup();
			if (map.containsKey(group)) {
				map.get(group).add(entity);
			} else {
				List<MonitorIndicatorEntity> list = new ArrayList<MonitorIndicatorEntity>();
				list.add(entity);
				map.put(group, list);
			}
		}

		return map;
	}

	/**
	 * 按日查询常规业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午2:18:46
	 * @param monitorDate
	 * @param indicatorGroups
	 * @param orgLevel
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryDailyCommonIndicatorData(java.util.Date,
	 *      java.util.List, int, java.lang.String)
	 */
	@Override
	public List<Map<String, String>> queryDailyCommonIndicatorData(Date monitorDate, List<String> indicatorGroups,
			String orgCode) {

		// 输入参数校验
		if (monitorDate == null || CollectionUtils.isEmpty(indicatorGroups) || StringUtils.isEmpty(orgCode)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		LOGGER.debug("按日查询常规业务表数据,参数:" + monitorDate + ", " + indicatorGroups + ", " + orgCode);

		OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, monitorDate);
		if (layer == null) {
			throw new BusinessMonitorException("网点错误");
		}
		
		// 截取到当天00:00
		Date monitorStartTime = org.apache.commons.lang.time.DateUtils.truncate(monitorDate, Calendar.DATE);

		// 查询监控数据
		List<MonitorDataDto> monitorData = monitorQueryDao.queryDailyCommonIndicators(monitorStartTime, indicatorGroups,
				getOrgLevel(layer), orgCode);

		// 返回值
		if (CollectionUtils.isEmpty(monitorData)) {
			return null;
		}

		// 构造返回值
		Map<MonitorDataDimensionDto, Map<String, String>> maps = new LinkedHashMap<MonitorDataDimensionDto, Map<String, String>>();
		
		// 生成时间段列表
		// 00:00 00:30 01:00 ...
		LinkedHashMap<String, String> result = buildTimeranges();
		for (String timerange : result.keySet()) {
			MonitorDataDimensionDto key = new MonitorDataDimensionDto(monitorStartTime, timerange);
			Map<String, String> value = new LinkedHashMap<String, String>();
			value.put(MONITOR_DATE, DateUtils.convert(monitorStartTime, "yyyy-MM-dd"));
			value.put(MONITOR_TIME_RANGE, timerange);
			maps.put(key, value);
		}

		// 查询数据
		for (MonitorDataDto dto : monitorData) {
			MonitorDataDimensionDto key = new MonitorDataDimensionDto(dto.getMonitorDate(), dto.getMonitorTimeRange());
			Map<String, String> value = maps.get(key);
			if (value == null) {
				value = new LinkedHashMap<String, String>();

				value.put(MONITOR_DATE, DateUtils.convert(dto.getMonitorDate(), "yyyy-MM-dd"));
				value.put(MONITOR_TIME_RANGE, dto.getMonitorTimeRange());
				// 指标数据
				value.put(dto.getIndicatorCode(), String.valueOf(dto.getIndicatorValue()));

				maps.put(key, value);
			} else {
				// 指标数据
				value.put(dto.getIndicatorCode(), String.valueOf(dto.getIndicatorValue()));
			}
		}

		LOGGER.info("常规业务功能数据量：" + maps.values().size());
		
		return new ArrayList<Map<String, String>>(maps.values());
	}

	/**
	 * 按月查询常规业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-5 下午2:58:31
	 * @param monitorDate
	 * @param indicatorGroups
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryMonthlyCommonIndicatorData(java.util.Date,
	 *      java.util.List, java.lang.String)
	 */
	@Override
	public List<Map<String, String>> queryMonthlyCommonIndicatorData(Date monitorDate, List<String> indicatorGroups,
			String orgCode) {

		LOGGER.debug("按月查询常规业务表数据,参数:" + monitorDate + ", " + indicatorGroups + ", " + orgCode);

		// 输入参数校验
		if (monitorDate == null || CollectionUtils.isEmpty(indicatorGroups) || StringUtils.isEmpty(orgCode)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, monitorDate);
		if (layer == null) {
			throw new BusinessMonitorException("网点错误");
		}

		Date monitorBeginDate = org.apache.commons.lang.time.DateUtils.truncate(monitorDate, Calendar.MONTH);
		Date monitorEndDate = org.apache.commons.lang.time.DateUtils.addMonths(monitorBeginDate, 1);

		// 查询监控数据
		List<MonitorDataDto> monitorData = monitorQueryDao.queryMonthlyCommonIndicators(monitorBeginDate,
				monitorEndDate, indicatorGroups, getOrgLevel(layer), orgCode);

		// 返回值
		if (CollectionUtils.isEmpty(monitorData)) {
			return null;
		}

		// 构造返回值
		Map<MonitorDataDimensionDto, Map<String, String>> maps = new LinkedHashMap<MonitorDataDimensionDto, Map<String, String>>();

		for (MonitorDataDto dto : monitorData) {
			MonitorDataDimensionDto key = new MonitorDataDimensionDto(dto.getMonitorDate(), dto.getMonitorTimeRange());
			Map<String, String> value = maps.get(key);
			if (value == null) {
				value = new LinkedHashMap<String, String>();

				value.put(MONITOR_DATE, DateUtils.convert(dto.getMonitorDate(), "yyyy-MM-dd"));
				value.put(MONITOR_TIME_RANGE, dto.getMonitorTimeRange());
				// 指标数据
				value.put(dto.getIndicatorCode(), String.valueOf(dto.getIndicatorValue()));

				maps.put(key, value);
			} else {
				// 指标数据
				value.put(dto.getIndicatorCode(), String.valueOf(dto.getIndicatorValue()));
			}
		}

		return new ArrayList<Map<String, String>>(maps.values());
	}

	/**
	 * 通过指标类别查询新业务表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-6 下午2:09:43
	 * @param indicatorGroups
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryNewIndicatorHeader(java.util.List)
	 */
	@Override
	public LinkedHashMap<String, List<MonitorIndicatorEntity>> queryNewIndicatorHeader(List<String> indicatorGroups) {

		LOGGER.debug("通过指标类别查询新业务表头,指标组:" + indicatorGroups);

		// 输入参数校验
		if (CollectionUtils.isEmpty(indicatorGroups)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		List<MonitorIndicatorEntity> indicators = monitorQueryDao.queryIndicatorsByIndicatorGroup(indicatorGroups);

		LinkedHashMap<String, List<MonitorIndicatorEntity>> map = new LinkedHashMap<String, List<MonitorIndicatorEntity>>();

		/*
		 * String orgGroup = DateUtils.convert(new Date(), "yyyy-MM-dd");
		 * MonitorIndicatorEntity en = new MonitorIndicatorEntity();
		 * en.setIndicatorCode(MONITOR_DATE); en.setIndicatorName(orgGroup);
		 * map.put(orgGroup, Arrays.asList(en));
		 */

		for (MonitorIndicatorEntity entity : indicators) {
			String group = entity.getIndicatorGroup();
			if (map.containsKey(group)) {
				map.get(group).add(entity);
			} else {
				List<MonitorIndicatorEntity> list = new ArrayList<MonitorIndicatorEntity>();
				list.add(entity);
				map.put(group, list);
			}
		}

		return map;

	}

	/**
	 * 按日查询新业务表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-6 下午2:49:02
	 * @param monitorDate
	 * @param indicatorGroup
	 * @param orgCodes
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryDailyNewIndicatorData(java.util.Date,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, String>> queryDailyNewIndicatorData(Date monitorDate, List<String> indicatorGroups,
			List<String> orgCodes) {

		LOGGER.debug("按日查询常规业务表数据,参数:" + monitorDate + ", " + indicatorGroups + ", " + orgCodes);

		// 输入参数校验
		if (monitorDate == null || CollectionUtils.isEmpty(indicatorGroups) || CollectionUtils.isEmpty(orgCodes)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		// 截取到当天00:00
		Date date = org.apache.commons.lang.time.DateUtils.truncate(monitorDate, Calendar.DATE);

		// 构造返回值
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		 
		for (String orgCode : orgCodes) {

			OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, date);
			if (layer == null) {
				throw new BusinessMonitorException("网点错误");
			}

			int orgLevel = getOrgLevel(layer);

			// 查询监控数据
			List<MonitorDataDto> monitorData = monitorQueryDao.queryDailyNewIndicators(date, indicatorGroups,
					orgLevel, orgCode);
			List<MonitorDataDto> dynamicMonitorData = new ArrayList<MonitorDataDto>();

			// 返回值
			if (CollectionUtils.isEmpty(monitorData)) {
				return null;
			}

			// 数据值以Map方式存放
			Map<String, String> value = new LinkedHashMap<String, String>();
			if (orgLevel == 1) {
				value.put(MONITOR_DATE, ROOT_ORG_NAME); // 全国名称写死
			} else {
				value.put(MONITOR_DATE, layer.getName()); // 第一列存放部门
			}

			// 构造一个解释器
			Interpreter interpreter = new Interpreter();

			for (MonitorDataDto dto : monitorData) {

				if (StringUtils.isNotEmpty(dto.getFormula())) {
					dynamicMonitorData.add(dto);
				} else {
					// 指标数据
					if (dto.getIndicatorValue() == null) {
						value.put(dto.getIndicatorCode(), null);
					} else {
						value.put(dto.getIndicatorCode(), String.valueOf(dto.getIndicatorValue()));
					}
					try {
						interpreter.set(dto.getIndicatorCode(), dto.getIndicatorValue());
					} catch (EvalError e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}

			for (MonitorDataDto dto : dynamicMonitorData) {
				try {
					interpreter.eval("value=" + dto.getFormula());
					Object obj = interpreter.get("value");
					// 指标数据
					value.put(dto.getIndicatorCode(), String.valueOf(obj));
				} catch (EvalError e) {
					LOGGER.error(e.getMessage());
				}
			}

			result.add(value);
		}

		LOGGER.info("新业务功能数据量：" + result.size());
		
		return result;
	}

	/**
	 * 查询应用数据表头
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午8:54:49
	 * @param indicatorGroup
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryApplicationIndicatorHeader(java.lang.String)
	 */
	@Override
	public LinkedHashMap<String, String> queryApplicationIndicatorHeader(String indicatorGroup) {

		LOGGER.debug("通过指标类别查询新业务表头,指标组:" + indicatorGroup);

		// 输入参数校验
		if (StringUtils.isEmpty(indicatorGroup)) {
			throw new BusinessMonitorException("输入参数错误");
		}

		// 构造返回值
		LinkedHashMap<String, String> result = buildTimeranges();

		return result;
	}

	/**
	 * 按日查询应用数据表数据
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-3-11 上午8:54:49
	 * @param monitorDate
	 * @param indicatorGroup
	 * @param orgCodes
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#queryDailyApplicationIndicatorData(java.util.Date,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public List<Map<String, String>> queryDailyApplicationIndicatorData(Date monitorDate, String indicatorGroup,
			List<String> orgCodes,Date startTime,Date endTime) {

		LOGGER.debug("按日查询应用数据表数据,参数:" + monitorDate + ", " + indicatorGroup + ", " + orgCodes);

		// 输入参数校验monitorDate == null ||
		//313353 sonar
		this.sonarSplitOne(indicatorGroup, orgCodes);

		// 返回应用数据指标列表
		//根据页面所选的查询类型，先去数据监控表中查询这个类型的监控情况
		List<MonitorIndicatorEntity> list = monitorQueryDao.queryIndicatorsByIndicatorGroup(Arrays
				.asList(indicatorGroup));
		//当监控数据空中查不当这个类型，给出提示信息
		if (CollectionUtils.isEmpty(list) || list.size() != 1) {
			throw new BusinessMonitorException("应用数据指标配置错误");
		}

		// 构造返回值
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		Date date = org.apache.commons.lang.time.DateUtils.truncate(monitorDate, Calendar.DATE);

		// 获取部门信息
		Map<OrganizationLayerEntity, Integer> orgMaps = new LinkedHashMap<OrganizationLayerEntity, Integer>();
		String level1OrgCode = null;
		List<String> level2OrgCodes = new ArrayList<String>();
		List<String> level3OrgCodes = new ArrayList<String>();
		//根据页面传输过来的组织编码遍历操作
		//313353 sonar
		level1OrgCode = sonarSplitTwo(level2OrgCodes, level3OrgCodes, orgMaps , orgCodes, date);

		// 获取查询的应用指标
		//应为数据监控每种类型只有一种，这里只需要获取其中一个对象的数据监控类型
		String indicatorCode = list.get(0).getIndicatorCode();
		// 系统功能使用情况
		//判断当前查询的是页面查过来的那种信息
		if (BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode().equals(indicatorCode)) {
			//根据时间，所选组织的等级查询数据监控情况
			List<MonitorDataDto> data = monitorQueryDao.queryDailyApplicationResource(date, level1OrgCode,
					level2OrgCodes, level3OrgCodes, startTime, endTime);

			// 构造返回值
			Map<String, Map<String, String>> maps = new LinkedHashMap<String, Map<String, String>>();

			if (CollectionUtils.isEmpty(data)) {
				return result;
			}
			//遍历查看数据监控返回的结果
			for (MonitorDataDto dto : data) {
				//获取每个对象中的监控名称
				//指标名称getIndicatorName
				String name = dto.getIndicatorName();
				//将maps中以及构建了存放数据监控数据的map，那么就直接在map中放入数据
				if (maps.containsKey(name)) {
					 //监控时间段MonitorTimeRange、指标值 IndicatorValue
					maps.get(name).put(dto.getMonitorTimeRange(), String.valueOf(dto.getIndicatorValue()));
				} else {
					//不存就新建一个指标名称对应的map集合，然后将数据放到map中
					Map<String, String> m = new HashMap<String, String>();
					m.put(MONITOR_DATE, name); // 设置第一列功能名称
					m.put(dto.getMonitorTimeRange(), String.valueOf(dto.getIndicatorValue()));
					maps.put(name, m);
				}
			}
			
			//返回整理好的maps.values（）；---->指的是从数据监控表中查询的所有value(指的是指标值)
			result = new ArrayList<Map<String, String>>(maps.values());

		} // 组织在线情况
		else if (BusinessMonitorIndicator.ORG_ONLINE_COUNT.getCode().equals(indicatorCode)) {
			for (Map.Entry<OrganizationLayerEntity, Integer> entry : orgMaps.entrySet()) {
				Map<String, String> m = new HashMap<String, String>();

				// 设置第一列组织名称
				if (FossConstants.ROOT_ORG_CODE.equals(entry.getKey().getCode())) {
					m.put(MONITOR_DATE, ROOT_ORG_NAME);
				} else {
					m.put(MONITOR_DATE, entry.getKey().getName());
				}

				List<MonitorDataDto> data = monitorQueryDao.queryDailyApplicationOnline(date, entry.getValue(), entry
						.getKey().getCode(),startTime, endTime);
				if (CollectionUtils.isNotEmpty(data)) {
					for (MonitorDataDto dto : data) {
						m.put(dto.getMonitorTimeRange(), String.valueOf(dto.getIndicatorValue()));
					}
				}

				result.add(m);
			}
		} // 模拟登陆情况
		else if (BusinessMonitorIndicator.SIMULATE_LOGIN_COUNT.getCode().equals(indicatorCode)) {

			// 构造返回值
			Map<String, Map<String, String>> maps = new LinkedHashMap<String, Map<String, String>>();

			List<MonitorDataDto> data = monitorQueryDao.queryDailyApplicationLogin(date,startTime, endTime);
			
			if (CollectionUtils.isNotEmpty(data)) {
				for (MonitorDataDto dto : data) {
					String key = dto.getIndicatorCode();
					if (maps.containsKey(key)) {
						maps.get(key).put(dto.getMonitorTimeRange(), dto.getIndicatorName());
					} else {
						Map<String, String> m = new HashMap<String, String>();
						// 设置第一列应用系统名称
						m.put(MONITOR_DATE, key);
						m.put(dto.getMonitorTimeRange(), dto.getIndicatorName());
						maps.put(key, m);
					}
				}
			}
			
			result = new ArrayList<Map<String, String>>(maps.values());

		}

		return result;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(String indicatorGroup, List<String> orgCodes) {
		if ( StringUtils.isEmpty(indicatorGroup) || CollectionUtils.isEmpty(orgCodes)) {
			throw new BusinessMonitorException("输入参数错误");
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private String sonarSplitTwo(List<String> level2OrgCodes, List<String> level3OrgCodes,
			Map<OrganizationLayerEntity, Integer> orgMaps ,List<String> orgCodes, Date date) {
		String result = null;
		for (String orgCode : orgCodes) {
			//根据组织编码去制止表中获取组织信息
			OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, date);
			if (layer == null) {
				throw new BusinessMonitorException("网点错误");
			}
			//将获取过来的组织信息，进行转换，事业部（五级）--》二级，大区（六级）--》三级
			int orgLevel = getOrgLevel(layer);
			//分别将不同等级的编码，分别放到对应的集合中
			if (orgLevel == NumberConstants.NUMERAL_THREE) {
				level3OrgCodes.add(orgCode);
			} else if (orgLevel == NumberConstants.NUMERAL_TWO) {
				level2OrgCodes.add(orgCode);
			} else if (orgLevel == NumberConstants.NUMERAL_ONE) {
				result = orgCode;
			}
			//将组织的等级以及根据组织编码查询的结果存放到map中
			orgMaps.put(layer, orgLevel);
		}
		return result;
	}

	/**
	 * 获取网点层级
	 * @author ibm-zhuwei
	 * @date 2013-3-19 下午3:32:16
	 * @param layer
	 * @return
	 */
	private int getOrgLevel(OrganizationLayerEntity layer) {

		int orgLevel = 0;
		if (StringUtils.isNotEmpty(layer.getLevel5())) {	// 大区五级
			orgLevel = NumberConstants.NUMERAL_THREE;
		} else if (StringUtils.isNotEmpty(layer.getLevel4())) {	// 事业部四级
			orgLevel = NumberConstants.NUMERAL_TWO;
		} else if (StringUtils.isNotEmpty(layer.getLevel1())) {
			orgLevel = NumberConstants.NUMERAL_ONE;//国家
		}

		return orgLevel;
	}

	/**
	 * 构造时间列表
	 * 00:00-00:30
	 * 00:30-01:00
	 * ...
	 * 23:30-00:00
	 * @author ibm-zhuwei
	 * @date 2013-3-22 上午9:49:46
	 * @return
	 */
	private LinkedHashMap<String, String> buildTimeranges() {

		// 构造返回值
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

		// 生成时间段列表
		// 00:00 00:30 01:00 ...
		List<String> timeranges = new ArrayList<String>();
		Date startTime = org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.DATE);
		Date endTime = org.apache.commons.lang.time.DateUtils.addDays(startTime, 1);
		while (endTime.after(startTime)) {
			SimpleDateFormat timeFormat = new SimpleDateFormat(BusinessMonitorService.MONITOR_TIME_FORMAT);
			timeranges.add(timeFormat.format(startTime));
			startTime = org.apache.commons.lang.time.DateUtils.addMinutes(startTime,
					BusinessMonitorService.MONITOR_MINUTES);
		}

		// 生成Map
		// [00:00-00:30, 00:00]
		for (int i = 0; i < timeranges.size(); ++i) {
			result.put(
					timeranges.get(i) + BusinessMonitorService.MONITOR_TIME_SEPARATOR
							+ timeranges.get((i + 1) % timeranges.size()), timeranges.get(i));
		}

		return result;
	}
	
	/**
	 * @param monitorQueryDao
	 */
	public void setMonitorQueryDao(IMonitorQueryDao monitorQueryDao) {
		this.monitorQueryDao = monitorQueryDao;
	}

	/**
	 * @param orgLayerService
	 */
	public void setOrgLayerService(IOrgLayerService orgLayerService) {
		this.orgLayerService = orgLayerService;
	}

	/**
	 * 导出数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午2:00:50
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService#exportExcelStream(com.deppon.foss.module.base.common.api.shared.vo.MonitorVo)
	 */
	@Override
	public InputStream exportExcelStream(MonitorVo vo, Date monitorDate,String sheetName,String total) {
		// 导出数据流文件
		InputStream excelStream = null;
		// 定义表头
		String[] rowHeads = null;
		// 查询列头
		LinkedHashMap<String, String> columnMap = queryHeaders(vo);
		// 转换为数组
		rowHeads = makeColumnArray(columnMap);

		// Excel数据
		SheetData sheetData = new SheetData();
		// excel头
		sheetData.setRowHeads(rowHeads);

		// 查询出的所有数据
		List<List<String>> rowList = null;
		// 按照表头的顺序将数据取出
		rowList = makeExcelData(vo, columnMap, vo.getOrgCode(), monitorDate,total);
		// 设置数据
		sheetData.setRowList(rowList);
		// 导出工具
		ExcelExport excelExportUtil = new ExcelExport();
		// 导出成文件
		excelStream = excelExportUtil.inputToClient(excelExportUtil.exportExcel(sheetData, sheetName,
				MonitorConstant.EXCEL_DEFAULT_SHEET_SIZE));
		return excelStream;
	}

	/**
	 * 构建导入数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午4:41:09
	 */
	private List<List<String>> makeExcelData(MonitorVo vo, LinkedHashMap<String, String> columnMap, String orgCode,
			Date monitorDate,String total) {
		if (vo != null) {
			// 查询数据
			List<Map<String, String>> datas = null;
			// 常规
			if (BusinessMonitorIndicator.CATEGORY_COMMON.equals(vo.getBussinessType())) {

				// 常规 月度范围
				if (MonitorConstant.ANALYSISTYPE_MONITORDATE.equals(vo.getAnalysisType())) {
					datas = this.queryMonthlyCommonIndicatorData(Calendar.getInstance().getTime(),
							vo.getIndicatorGroups(), orgCode);
				}// 常规 月度
				else {
					datas = this.queryDailyCommonIndicatorData(Calendar.getInstance().getTime(),
							vo.getIndicatorGroups(), orgCode);
				}

			}
			// 新业务
			else if (BusinessMonitorIndicator.CATEGORY_NEW.equals(vo.getBussinessType())) {
				datas = this.queryDailyNewIndicatorData(monitorDate, vo.getIndicatorGroups(), vo.getOrgCodes());
			} else
			// 应用
			if (BusinessMonitorIndicator.CATEGORY_APPLICATION.equals(vo.getBussinessType())) {
				Date defaultDate = Calendar.getInstance().getTime();
				Date startTime = null;
				Date endTime=new Date();
				 
				if(total.endsWith("dayTotal")){
					//日
					startTime = org.apache.commons.lang.time.DateUtils.truncate(defaultDate, Calendar.DATE);
					
				}else{
					//月
					startTime = org.apache.commons.lang.time.DateUtils.truncate(defaultDate, Calendar.MONTH);
					endTime = org.apache.commons.lang.time.DateUtils.addMonths(startTime, 1);
					
				}
				datas = this.queryDailyApplicationIndicatorData(monitorDate, vo.getIndicatorGroup(), vo.getOrgCodes(),startTime,endTime);
			}
			// 结果排序
			return sortRowData(columnMap, datas);
		}
		return null;

	}

	/**
	 * 顺序构建数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午4:54:30
	 */
	private List<List<String>> sortRowData(LinkedHashMap<String, String> columnMap, List<Map<String, String>> datas) {
		List<List<String>> rs = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(datas)) {
			// 临时行数据
			Map<String, String> rowMap = null;
			List<String> newRowMap = null;
			// 行数据循环
			for (int i = 0; i < datas.size(); i++) {
				// 新顺序的行数据
				newRowMap = new ArrayList<String>();
				// 旧的的行数据
				rowMap = datas.get(i);
				if (rowMap != null) {
					if (columnMap != null && !columnMap.isEmpty()) {
						// 迭代
						Iterator<String> itCol = columnMap.keySet().iterator();
						String key = null;
						// 循环获取数据
						while (itCol.hasNext()) {
							key = itCol.next();
							newRowMap.add(rowMap.get(key));
						}
					}
				}
				// 加入队列
				rs.add(newRowMap);
			}
		}
		return rs;
	}

	/**
	 * 列头数组
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午3:36:36
	 */
	private String[] makeColumnArray(LinkedHashMap<String, String> columnMap) {
		String[] tempCol = null;

		// 不为空，则返回数组
		if (columnMap != null && !columnMap.isEmpty()) {
			tempCol = new String[columnMap.size()];
			Object[] tempVal = columnMap.values().toArray();
			if (tempVal != null) {
				for (int i = 0; i < tempVal.length; i++) {
					tempCol[i] = (String) tempVal[i];
				}
			}

		}
		return tempCol;
	}

	/**
	 * 查询表头数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午2:04:20
	 */
	private LinkedHashMap<String, String> queryHeaders(MonitorVo vo) {
		// 表头数据列名关系映射
		LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();
		// 判空
		if (vo != null) {
			// 常规、新业务
			if (BusinessMonitorIndicator.CATEGORY_NEW.equals(vo.getBussinessType())
					|| BusinessMonitorIndicator.CATEGORY_COMMON.equals(vo.getBussinessType())) {
				makeHeaderMap(vo, columnMap);
			} else
			// 应用
			if (BusinessMonitorIndicator.CATEGORY_APPLICATION.equals(vo.getBussinessType())) {
				// 第一列的处理
				fetchFirstColumn(vo, columnMap);
				// 查询其他列名
				LinkedHashMap<String, String> tempMap = this.queryApplicationIndicatorHeader(vo.getIndicatorGroup());
				if (tempMap != null) {
					columnMap.putAll(tempMap);
				}
			}
		}
		return columnMap;
	}

	/**
	 * 创建表头map
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-13 下午3:29:54
	 */
	private void makeHeaderMap(MonitorVo vo, LinkedHashMap<String, String> columnMap) {
		// 查询列
		LinkedHashMap<String, List<MonitorIndicatorEntity>> map = this.queryCommonIndicatorHeader(vo
				.getIndicatorGroups());

		// 第一列的处理
		fetchFirstColumn(vo, columnMap);

		// 其他列
		if (map != null && !map.isEmpty()) {
			Iterator<String> keys = map.keySet().iterator();
			// 临时列表
			List<MonitorIndicatorEntity> tempList = null;
			// 指标
			MonitorIndicatorEntity entity = null;
			// 循环获取列名对应关系
			while (keys.hasNext()) {
				// 列表
				tempList = map.get(keys.next());
				if (CollectionUtils.isNotEmpty(tempList)) {
					// 列编码、列名
					for (int i = 0; i < tempList.size(); i++) {
						entity = tempList.get(i);
						// 指标信息
						if (entity != null) {
							columnMap.put(entity.getIndicatorCode(), entity.getIndicatorName());
						}
					}
				}

			}
		}
	}

	/**
	 * 第一列名的处理
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-14 下午3:37:18
	 */
	private void fetchFirstColumn(MonitorVo vo, LinkedHashMap<String, String> columnMap) {
		// 第一列的处理

		// 常规
		if (BusinessMonitorIndicator.CATEGORY_COMMON.equals(vo.getBussinessType())) {
			// 如果是日期范围
			if (MonitorConstant.ANALYSISTYPE_MONITORTIMERANGE.equals(vo.getAnalysisType())) {
				columnMap.put(MonitorConstant.ANALYSISTYPE_MONITORTIMERANGE, queryOrgName(vo));
			}
			// 月度范围
			else {
				columnMap.put(MonitorConstant.ANALYSISTYPE_MONITORDATE, queryOrgName(vo));
			}
		}// 新业务
		else if (BusinessMonitorIndicator.CATEGORY_NEW.equals(vo.getBussinessType())) {
			// monitorDate
			columnMap.put(MonitorConstant.ANALYSISTYPE_MONITORDATE, MonitorConstant.EXCEL_FIRST_HEADER);
		}
		// 应用数据
		else if (BusinessMonitorIndicator.CATEGORY_APPLICATION.equals(vo.getBussinessType())) {
			columnMap.put(MonitorConstant.ANALYSISTYPE_MONITORDATE, vo.getIndicatorGroup());
		}

	}

	/**
	 * 查询部门名称
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-14 下午5:03:50
	 */
	private String queryOrgName(MonitorVo vo) {
		// 部门名称
		String orgName = "";
		// 如果是全国
		if (FossConstants.ROOT_ORG_CODE.equals(vo.getOrgCode())) {
			orgName = MonitorConstant.ROOT_ORG_NAME;
		} else {
			// 查询部门
			OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(vo.getOrgCode(), Calendar
					.getInstance().getTime());
			// 部门信息
			if (layer != null) {
				orgName = layer.getName();
			}
		}
		return orgName;
	}
	

}
