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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MonitorStatusDataService.java
 * 
 * FILE NAME        	: MonitorStatusDataService.java
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
 * FILE    NAME: MonitorStatusDataService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.redis.RedisClient;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgLayerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorAppEntityDao;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorStatusDataDao;
import com.deppon.foss.module.base.common.api.server.service.IMonitorDataService;
import com.deppon.foss.module.base.common.api.server.service.IMonitorIndicatorService;
import com.deppon.foss.module.base.common.api.server.service.IMonitorStatusDataService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorAppEntity;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;
import com.deppon.foss.module.base.common.api.shared.dto.MonitorIndicatorDataDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 监控状态数据服务
 * @author ibm-zhuwei
 * @date 2013-2-22 下午2:16:10
 */
public class MonitorStatusDataService implements IMonitorStatusDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MonitorStatusDataService.class);

	/**
	 * CONNECTIONTIMEOUT
	 */
	private static final int CONNECTION_TIMEOUT = 5000;
	
	/**
	 * Jedis客户端
	 */
	private RedisClient client;
	
	/**
	 * 监控状态DAO
	 */
	private IMonitorStatusDataDao monitorStatusDataDao;
	
	/**
	 * 监控指标信息服务
	 */
	private IMonitorIndicatorService monitorIndicatorService;

	/**
	 * 监控数据服务
	 */
	private IMonitorDataService monitorDataService;

	/**
	 * 组织层级服务
	 */
	private IOrgLayerService orgLayerService;
	
	/**
	 * 监控应用配置DAO
	 */
	private IMonitorAppEntityDao monitorAppEntityDao;

	/** 
	 * REDIS持久化在线用户数
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午11:39:01
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorStatusDataService#processOnlineMonitor()
	 */
	@Override
	public void processOnlineMonitor() {

		Jedis jedis = null;

		try {
			
			jedis = client.getResource();
			
			// 遍历在线用户数据
			Set<String> keys = jedis.keys(BusinessMonitorService.ONLINE_COUNTER_PREFIX
					+ BusinessMonitorService.MONITOR_SEPARATOR + "*");
			
			if (CollectionUtils.isEmpty(keys)) {
				return;
			}
			
			// 组织在线数【组织编码、在线用户数量】
			Map<String, Long> map = new HashMap<String, Long>();
			
			// 遍历HASH，数据持久化
			for (String key : keys) {
				// 通过KEY键，获取维度信息
				// KEY键规则：ONLINE#部门#用户
				String[] strs = key.split(BusinessMonitorService.MONITOR_SEPARATOR);
				
				if (strs == null || strs.length != NumberConstants.NUMERAL_THREE) {
					continue;
				}
				
				String orgCode = strs[1];
				
				if (map.containsKey(orgCode)) {
					Long size = map.get(orgCode);
					map.put(orgCode, size + 1);	// HashMap覆盖替换值
				} else {
					map.put(orgCode, 1l);
				}
			}

			// 初始化数据
			List<MonitorDataEntity> list = new ArrayList<MonitorDataEntity>(map.size());
			
			// 设置日期
			Calendar calendar = Calendar.getInstance();
			// 监控日期
			Date monitorDate = DateUtils.truncate(calendar.getTime(), Calendar.DATE);
			
			// 设置时间，动态计算分钟间隔
			long result = DateUtils.truncate(calendar, Calendar.DATE).getTimeInMillis();
			result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
			result += (calendar.get(Calendar.MINUTE) / BusinessMonitorService.MONITOR_MINUTES)
					* BusinessMonitorService.MONITOR_MINUTES
					* DateUtils.MILLIS_PER_MINUTE;
			
			Date startTime = new Date(result);
			Date endTime = DateUtils.addMinutes(startTime, BusinessMonitorService.MONITOR_MINUTES);
			
			SimpleDateFormat timeFormat = new SimpleDateFormat(BusinessMonitorService.MONITOR_TIME_FORMAT);
			String monitorTimeRange = timeFormat.format(startTime)
					+ BusinessMonitorService.MONITOR_TIME_SEPARATOR + timeFormat.format(endTime);
			
			// 构造监控数据
			for (Map.Entry<String, Long> entry : map.entrySet()) {
				String orgCode = entry.getKey();
				Long value = entry.getValue();
				
				MonitorDataEntity monitorData = new MonitorDataEntity();
				monitorData.setId(UUIDUtils.getUUID());
				monitorData.setOrgCode(orgCode);
				
				OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, calendar.getTime());
				if (layer != null) {
					monitorData.setLevel2OrgCode(layer.getLevel4());	// 事业部是四级部门
					monitorData.setLevel3OrgCode(layer.getLevel5());	// 大区是五级部门
				}
				
				monitorData.setMonitorDate(monitorDate);
				monitorData.setMonitorTimeRange(monitorTimeRange);
				monitorData.setIndicatorCode(BusinessMonitorIndicator.ORG_ONLINE_COUNT.getCode());
				monitorData.setIndicatorValue(value);
				monitorData.setCreateTime(calendar.getTime());
				monitorData.setMonitorStartTime(startTime);
				monitorData.setMonitorEndTime(endTime);
				
				list.add(monitorData);
			}
			
			// 持久化数据，批量插入
			monitorDataService.batchAddMonitorData(list);
			
		} catch (JedisConnectionException je) {
			LOGGER.error(je.getMessage(), je);
			client.returnBrokenResource(jedis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				client.returnResource(jedis);
			}
		}
		
	}

	/** 
	 * 模拟登陆各个模块应用
	 * @author ibm-zhuwei
	 * @date 2013-3-19 上午10:35:16
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorStatusDataService#processSimulateLogin()
	 */
	@Override
	public void processSimulateLogin() {
		
		// 是否需要监控的开关
		if (!monitorIndicatorService
				.isMonitorIndicator(BusinessMonitorIndicator.SIMULATE_LOGIN_COUNT
						.getCode())) {
			return;
		}
		
		List<MonitorAppEntity> list = monitorAppEntityDao.queryActiveMonitorApps();
		
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		
		Calendar calendar = Calendar.getInstance();

		List<MonitorDataEntity> monitors = new ArrayList<MonitorDataEntity>();
		
		for (MonitorAppEntity entity : list) {
			boolean result = simulateLogin(entity.getAppUrl());
			
			MonitorDataEntity monitorData = buildMonitorData(calendar);

			monitorData.setOrgCode(FossConstants.ROOT_ORG_CODE);
			monitorData.setIndicatorCode(BusinessMonitorIndicator.SIMULATE_LOGIN_COUNT.getCode());
			monitorData.setIndicatorSubCode(entity.getAppCode());
			if (result) {
				monitorData.setIndicatorValue(1l);
			} else {
				monitorData.setIndicatorValue(0l);
			}
			
			monitors.add(monitorData);
		}
		
		// 批量插入最新数据
		monitorDataService.batchAddMonitorData(monitors);
		
	}

	/** 
	 * 监控未处理订单
	 * @author ibm-zhuwei
	 * @date 2013-2-22 下午2:16:38
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorStatusDataService#monitorPendingOrder()
	 */
	@Override
	public void monitorPendingOrder() {
		
		// 是否需要监控的开关
		if (!monitorIndicatorService
				.isMonitorIndicator(BusinessMonitorIndicator.ORDER_PENDING_COUNT
						.getCode())) {
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		
		// 查询接送货未处理订单数
		List<MonitorIndicatorDataDto> orders = monitorStatusDataDao.queryPendingOrder();
		
		if (CollectionUtils.isNotEmpty(orders)) {

			List<MonitorDataEntity> monitors = new ArrayList<MonitorDataEntity>(orders.size());
			
			for (MonitorIndicatorDataDto order : orders) {
				
				MonitorDataEntity monitorData = buildMonitorData(calendar);

				monitorData.setOrgCode(order.getOrgCode());
				monitorData.setIndicatorCode(BusinessMonitorIndicator.ORDER_PENDING_COUNT.getCode());
				monitorData.setIndicatorValue(order.getCounterValue());

				OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(order.getOrgCode(), calendar.getTime());
				if (layer != null) {
					monitorData.setLevel2OrgCode(layer.getLevel4());	// 事业部是四级部门
					monitorData.setLevel3OrgCode(layer.getLevel5());	// 大区是五级部门
				}
				
				monitors.add(monitorData);
			}
			
			// 批量插入最新数据
			monitorDataService.batchAddMonitorData(monitors);
		}
		
	}
	
	/**
	 * 构造MonitorDataEntity实体
	 * @author ibm-zhuwei
	 * @date 2013-3-19 下午1:55:45
	 * @param calendar
	 * @return
	 */
	private MonitorDataEntity buildMonitorData(Calendar calendar) {
		
		// 设置时间，动态计算分钟间隔
		long result = DateUtils.truncate(calendar, Calendar.DATE).getTimeInMillis();
		result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
		result += (calendar.get(Calendar.MINUTE) / BusinessMonitorService.MONITOR_MINUTES)
				* BusinessMonitorService.MONITOR_MINUTES
				* DateUtils.MILLIS_PER_MINUTE;
		
		Date startTime = new Date(result);
		Date endTime = DateUtils.addMinutes(startTime, BusinessMonitorService.MONITOR_MINUTES);
		
		MonitorDataEntity monitorData = new MonitorDataEntity();
		
		monitorData.setId(UUIDUtils.getUUID());
		monitorData.setMonitorDate(DateUtils.truncate(calendar.getTime(), Calendar.DATE));
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(BusinessMonitorService.MONITOR_TIME_FORMAT);
		String timeRange = timeFormat.format(startTime)
				+ BusinessMonitorService.MONITOR_TIME_SEPARATOR
				+ timeFormat.format(endTime);
		monitorData.setMonitorTimeRange(timeRange);
		
		monitorData.setCreateTime(new Date());
		monitorData.setMonitorStartTime(startTime);
		monitorData.setMonitorEndTime(endTime);
		
		return monitorData;
	}
	
	/**
	 * 模拟登陆链接地址
	 * @author ibm-zhuwei
	 * @date 2013-3-19 下午1:57:02
	 * @param url
	 * @return
	 */
	private boolean simulateLogin(String strUrl) {
		
		boolean result = false;
		
		HttpURLConnection con = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			URL url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(CONNECTION_TIMEOUT);
			con.setUseCaches(false);
			con.setRequestProperty("content-type", "text/html;charset=UTF-8");
			con.connect();
			
			StringBuffer token = new StringBuffer();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpStatus.OK.value()) {
				InputStream is = con.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				
				String line = null;
				while ((line = br.readLine()) != null) {
					token.append(line);
				}
				
				if (token.indexOf("true") >= 0) {
					result = true;
				}
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		
		return result;
	}

	/**
	 * @param monitorStatusDataDao
	 */
	public void setMonitorStatusDataDao(IMonitorStatusDataDao monitorStatusDataDao) {
		this.monitorStatusDataDao = monitorStatusDataDao;
	}
	
	/**
	 * @param monitorIndicatorService
	 */
	public void setMonitorIndicatorService(
			IMonitorIndicatorService monitorIndicatorService) {
		this.monitorIndicatorService = monitorIndicatorService;
	}

	/**
	 * @param monitorDataService
	 */
	public void setMonitorDataService(IMonitorDataService monitorDataService) {
		this.monitorDataService = monitorDataService;
	}

	/**
	 * @param orgLayerService
	 */
	public void setOrgLayerService(IOrgLayerService orgLayerService) {
		this.orgLayerService = orgLayerService;
	}

	/**
	 * @param client
	 */
	public void setClient(RedisClient client) {
		this.client = client;
	}
	
	/**
	 * @param monitorAppEntityDao
	 */
	public void setMonitorAppEntityDao(IMonitorAppEntityDao monitorAppEntityDao) {
		this.monitorAppEntityDao = monitorAppEntityDao;
	}

}
