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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/BusinessMonitorProcessService.java
 * 
 * FILE NAME        	: BusinessMonitorProcessService.java
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
 * FILE    NAME: BusinessMonitorProcessService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.redis.RedisClient;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgLayerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorProcessService;
import com.deppon.foss.module.base.common.api.server.service.IMonitorDataService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;
import com.deppon.foss.util.UUIDUtils;


/**
 * 业务监控处理服务
 * @author ibm-zhuwei
 * @date 2013-1-31 下午2:28:16
 */
public class BusinessMonitorProcessService implements IBusinessMonitorProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessMonitorProcessService.class);

	/**
	 * Jedis客户端
	 */
	private RedisClient client;
	
	/**
	 * 监控数据服务
	 */
	private IMonitorDataService monitorDataService;
	
	/**
	 * 组织层级服务
	 */
	private IOrgLayerService orgLayerService;

	/** 
	 * REDIS内存数据持久化
	 * @author ibm-zhuwei
	 * @date 2013-1-31 下午2:28:35
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorProcessService#processBusinessMonitor()
	 */
	@Override
	public void processBusinessMonitor() {
		
		Jedis jedis = null;

		// 以当前时间前五分钟为时间节点
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, NumberConstants.NEGATIVE_NUMERAL_FIVE);
		
		// 动态计算分钟间隔，获取监控截止时间
		long result = DateUtils.truncate(calendar, Calendar.DATE).getTimeInMillis();
		result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
		result += ( calendar.get(Calendar.MINUTE) / BusinessMonitorService.MONITOR_MINUTES ) * BusinessMonitorService.MONITOR_MINUTES * DateUtils.MILLIS_PER_MINUTE;
		Date closeTime = new Date(result);
		
		// 获取需要遍历的日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(BusinessMonitorService.MONITOR_DATE_FORMAT);
		String today = dateFormat.format(calendar.getTime());
		String yesterday = dateFormat.format(DateUtils.addDays(calendar.getTime(), -1));
		List<String> dates = Arrays.asList(yesterday, today);
		
		try {
			
			LOGGER.info("持久化两日内业务监控数据");
			
			jedis = client.getResource();
			
			// 遍历两日内业务监控数据
			for (String date : dates) {
				Set<String> keys = jedis.keys(date + "*");
				
				if (CollectionUtils.isEmpty(keys)) {
					continue;
				}
				
				// 遍历HASH，数据持久化
				for (String key : keys) {
					Map<String, String> map = jedis.hgetAll(key);
					if (map == null || map.isEmpty()) {
						continue;
					}
					
					Date monitorStartTime = this.persistBusinessMonitor(key, map, true);
					
					// 截止时间前的监控数据不再变化，需要删除
					if (closeTime.after(monitorStartTime)) {
						jedis.expire(key, 1);
					}
				}
			}

			LOGGER.info("持久化两日内应用监控数据");
			
			// 遍历两日内应用监控数据
			for (String date : dates) {
				Set<String> keys = jedis.keys(BusinessMonitorService.RESOURCE_COUNTER_PREFIX
								+ BusinessMonitorService.MONITOR_SEPARATOR + date + "*");
				
				if (CollectionUtils.isEmpty(keys)) {
					continue;
				}
				
				// 遍历HASH，数据持久化
				for (String key : keys) {
					Map<String, String> map = jedis.hgetAll(key);
					if (map == null || map.isEmpty()) {
						continue;
					}
					
					String key2 = key.substring(BusinessMonitorService.RESOURCE_COUNTER_PREFIX.length()
							+ BusinessMonitorService.MONITOR_SEPARATOR.length());
					Date monitorStartTime = this.persistBusinessMonitor(key2, map, false);
					
					// 截止时间前的监控数据不再变化，需要删除
					if (closeTime.after(monitorStartTime)) {
						jedis.expire(key, 1);
					}
				}
			}

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
	 * 持久化监控数据
	 * @author ibm-zhuwei
	 * @date 2013-2-2 上午9:43:58
	 * @param key
	 * @param map
	 * @return 返回监控HASH的起始时间
	 * @throws ParseException 
	 */
	@Transactional
	private Date persistBusinessMonitor(String key, Map<String, String> map, boolean businessIndicator)
			throws ParseException {
		
		LOGGER.info("KEY: " + key);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			LOGGER.info(entry.getKey() + ": " + entry.getValue());
		}
		
		// 通过KEY键，获取维度信息
		// KEY键规则：日期#时间段#部门
		String[] strs = key.split(BusinessMonitorService.MONITOR_SEPARATOR);
		
		Date monitorDate = DateUtils.parseDate(strs[0],
				new String[] { BusinessMonitorService.MONITOR_DATE_FORMAT });
		
		String monitorTimeRange = strs[1];
		String orgCode = strs[2];

		Date now = new Date();
		OrganizationLayerEntity layer = orgLayerService.getOrgLayerEntityByCache(orgCode, now);
		
		String[] monitorTimes = monitorTimeRange.split(BusinessMonitorService.MONITOR_TIME_SEPARATOR);
		Date monitorStartTime = DateUtils.parseDate(strs[0] + monitorTimes[0],
				new String[] { BusinessMonitorService.MONITOR_DATE_FORMAT
						+ BusinessMonitorService.MONITOR_TIME_FORMAT });
		Date monitorEndTime = DateUtils.parseDate(strs[0] + monitorTimes[1],
				new String[] { BusinessMonitorService.MONITOR_DATE_FORMAT
				+ BusinessMonitorService.MONITOR_TIME_FORMAT });
		
		// 初始化数据
		List<MonitorDataEntity> list = new ArrayList<MonitorDataEntity>(map.size());
		List<String> indicatorCodes = new ArrayList<String>();
		
		// 构造监控数据
		for (Map.Entry<String, String> entry : map.entrySet()) {
			
			String code = entry.getKey();
			Long indicatorValue = Long.valueOf(entry.getValue());
			
			MonitorDataEntity monitorData = new MonitorDataEntity();
			monitorData.setId(UUIDUtils.getUUID());
			monitorData.setOrgCode(orgCode);
			if (layer != null) {
				monitorData.setLevel2OrgCode(layer.getLevel4());	// 事业部是四级部门
				monitorData.setLevel3OrgCode(layer.getLevel5());	// 大区是五级部门
			}
			monitorData.setMonitorDate(monitorDate);
			monitorData.setMonitorTimeRange(monitorTimeRange);
			monitorData.setIndicatorValue(indicatorValue);
			monitorData.setCreateTime(now);
			monitorData.setMonitorStartTime(monitorStartTime);
			monitorData.setMonitorEndTime(monitorEndTime);
			
			if (businessIndicator) {
				monitorData.setIndicatorCode(code);
				indicatorCodes.add(code);
			} else {
				monitorData.setIndicatorCode(BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode());
				indicatorCodes.add(BusinessMonitorIndicator.SYSTEM_RESOURCE_COUNT.getCode());
				monitorData.setIndicatorSubCode(code);
			}
			
			list.add(monitorData);
		}

		// 删除历史数据
		monitorDataService.batchDeleteMonitorData(monitorStartTime, monitorTimeRange, orgCode, indicatorCodes);
		// 批量插入最新数据
		monitorDataService.batchAddMonitorData(list);
    	
		return monitorStartTime;
	}
	

	/**
	 * @param client
	 */
	public void setClient(RedisClient client) {
		this.client = client;
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

}
