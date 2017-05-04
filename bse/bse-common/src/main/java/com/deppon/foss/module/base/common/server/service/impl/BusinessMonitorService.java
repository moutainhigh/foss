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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/BusinessMonitorService.java
 * 
 * FILE NAME        	: BusinessMonitorService.java
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
 * FILE    NAME: BusinessMonitorService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.redis.RedisClient;
import com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.exception.BusinessMonitorException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.NumberUtils;


/**
 * 业务监控服务
 * @author ibm-zhuwei
 * @date 2013-1-30 下午2:33:06
 */
public class BusinessMonitorService implements IBusinessMonitorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessMonitorService.class);

	/**
	 * Jedis客户端
	 */
	private RedisClient client;

	/**
	 * 监控分钟跨度：30分钟
	 */
	public static final int MONITOR_MINUTES = 30;
	
	/**
	 * 监控日期格式
	 */
	public static final String MONITOR_DATE_FORMAT = "yyyyMMdd";

	/**
	 * 监控时间格式
	 */
	public static final String MONITOR_TIME_FORMAT = "HH:mm";

	/**
	 * 监控时间分隔符
	 */
	public static final String MONITOR_TIME_SEPARATOR = "-";
	
	/**
	 * HASH KEY分隔符
	 */
	public static final String MONITOR_SEPARATOR = "#";
	
	/**
	 * 资源前缀
	 */
	public static final String RESOURCE_COUNTER_PREFIX = "RESOURCE";

	/**
	 * 在线部门
	 */
	public static final String ONLINE_COUNTER_PREFIX = "ONLINE";
	
	/**
	 * 在线分钟
	 */
	public static final int ONLINE_MINUTES = 24*60;
	
	/** 
	 * 简单计数器
	 * 应用场景：单个指标计数器
	 * @author ibm-zhuwei
	 * @date 2013-1-30 下午2:33:31
	 * @param indicator
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService#counter(com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void counter(BusinessMonitorIndicator indicator,
			CurrentInfo currentInfo) {
		
		LOGGER.debug("monitor indicator: " + indicator);
		
		// 输入参数校验
		if (indicator == null || currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		
		// 获取hash名称
		String hash = buildHash(currentInfo);
		
		Jedis jedis = null;

		try {
			
			jedis = client.getResource();

			Long i = jedis.hincrBy(hash, indicator.getCode(), 1);
			
			if (i <= 0) {
				LOGGER.error("error monitor indicator: " + indicator);
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
	 * 多个计数器
	 * 应用场景：针对一个业务肯能存在多个指标计数器，调用此方法批量计数；例如开单、签收
	 * @author ibm-zhuwei
	 * @date 2013-1-30 下午2:33:31
	 * @param indicators
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService#counter(java.util.Map, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void counter(Map<BusinessMonitorIndicator, Number> indicators,
			CurrentInfo currentInfo) {

		LOGGER.debug("monitor indicator" + indicators.keySet());
		
		// 输入参数校验
		if (CollectionUtils.isEmpty(indicators) || currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		
		// 获取hash名称
		String hash = buildHash(currentInfo);
		
		Jedis jedis = null;

		try {
			
			jedis = client.getResource();
			
			// 构建pipeline，批量提交
			Pipeline pipeline = jedis.pipelined();
			List<BusinessMonitorIndicator> list = new ArrayList<BusinessMonitorIndicator>();
			
			// 执行多个指标计数
			for (Map.Entry<BusinessMonitorIndicator, Number> entity : indicators.entrySet()) {
				BusinessMonitorIndicator indicator = entity.getKey();
				Number number = entity.getValue();
				
				long value = 1;	// 计数器默认+1
				if (number != null && number.longValue() > 0) {
					value = number.longValue();
				}
				
				if (BusinessMonitorIndicator.TYPE_TOTALIZER.equals(indicator.getType())) {
					value = NumberUtils.multiply(NumberUtils.createBigDecimal("100"), number).longValue();
				}
				
				list.add(indicator);
				pipeline.hincrBy(hash, indicator.getCode(), value);
			}
			
			// 提交redis执行计数
			List<Object> results = pipeline.syncAndReturnAll();
			
			for (int i = 0; i < results.size(); ++i) {
				
				Long result = (Long) results.get(i);

				if (result <= 0) {
					LOGGER.error("error monitor indicator: " + list.get(i));
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
	 * 菜单功能使用计数器
	 * @author ibm-zhuwei
	 * @date 2013-2-27 下午3:19:25
	 * @param resourceCode 功能编码
	 * @param currentInfo 当前操作人信息
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService#counterResource(java.lang.String, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void counterResource(String resourceCode, CurrentInfo currentInfo) {

		LOGGER.debug("monitor resource: " + resourceCode);
		
		// 输入参数校验
		if (resourceCode == null || currentInfo == null
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		
		// 获取hash名称
		String hash = RESOURCE_COUNTER_PREFIX + MONITOR_SEPARATOR + buildHash(currentInfo);
		
		Jedis jedis = null;

		try {
			
			jedis = client.getResource();

			Long i = jedis.hincrBy(hash, resourceCode, 1);
			
			if (i <= 0) {
				LOGGER.error("error monitor indicator: " + resourceCode);
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
	 * 构建Hash
	 * 日期_时间_部门编码  20130101#01:00-01:30#ORGCODE1
	 * @author ibm-zhuwei
	 * @date 2013-1-30 下午3:53:32
	 * @param currentInfo
	 * @return
	 */
	private String buildHash(CurrentInfo currentInfo) {

		// 设置日期
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(MONITOR_DATE_FORMAT);

		StringBuffer hash = new StringBuffer();
		hash.append(dateFormat.format(calendar.getTime()));
		
		// 设置时间，动态计算分钟间隔
		long result = DateUtils.truncate(calendar, Calendar.DATE).getTimeInMillis();
		result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
		result += ( calendar.get(Calendar.MINUTE) / MONITOR_MINUTES ) * MONITOR_MINUTES * DateUtils.MILLIS_PER_MINUTE;
		
		Date startTime = new Date(result);
		Date endTime = DateUtils.addMinutes(startTime, MONITOR_MINUTES);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(MONITOR_TIME_FORMAT);
		hash.append(MONITOR_SEPARATOR);
		hash.append(timeFormat.format(startTime)).append(MONITOR_TIME_SEPARATOR).append(timeFormat.format(endTime));
		
		// 设置部门
		hash.append(MONITOR_SEPARATOR);
		hash.append(currentInfo.getCurrentDeptCode());
		
		return hash.toString();
	}

	/** 
	 * 用户上线
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午9:41:56
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService#online(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void online(CurrentInfo currentInfo, String message) {

		LOGGER.debug("monitor online: " + currentInfo);
		
		// 输入参数校验
		if (currentInfo == null || StringUtils.isEmpty(currentInfo.getEmpCode())
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		
		// 获取key名称
		String key = ONLINE_COUNTER_PREFIX + MONITOR_SEPARATOR + currentInfo.getCurrentDeptCode()
				 + MONITOR_SEPARATOR + currentInfo.getEmpCode();
		
		Jedis jedis = null;

		try {
			
			jedis = client.getResource();

			jedis.setex(key, ONLINE_MINUTES*NumberConstants.NUMBER_60, message);
			
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
	 * 用户下线
	 * @author ibm-zhuwei
	 * @date 2013-3-7 上午9:41:56
	 * @param currentInfo
	 * @see com.deppon.foss.module.base.common.api.server.service.IBusinessMonitorService#offline(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void offline(CurrentInfo currentInfo) {

		LOGGER.debug("monitor online: " + currentInfo);
		
		// 输入参数校验
		if (currentInfo == null || StringUtils.isEmpty(currentInfo.getEmpCode())
				|| StringUtils.isEmpty(currentInfo.getCurrentDeptCode())) {
			throw new BusinessMonitorException("输入参数错误");
		}
		
		// 获取key名称
		String key = ONLINE_COUNTER_PREFIX + MONITOR_SEPARATOR + currentInfo.getCurrentDeptCode()
				 + MONITOR_SEPARATOR + currentInfo.getEmpCode();
		
		Jedis jedis = null;

		try {
			
			jedis = client.getResource();
			
			jedis.del(key);

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
	 * @param client
	 */
	public void setClient(RedisClient client) {
		this.client = client;
	}

}
