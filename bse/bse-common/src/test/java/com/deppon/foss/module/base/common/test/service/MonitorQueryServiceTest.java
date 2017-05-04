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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/test/service/MonitorQueryServiceTest.java
 * 
 * FILE NAME        	: MonitorQueryServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.test.service
 * FILE    NAME: MonitorQueryServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.test.service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.common.api.server.service.IMonitorQueryService;
import com.deppon.foss.module.base.common.api.shared.define.BusinessMonitorIndicator;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorIndicatorEntity;
import com.deppon.foss.module.base.common.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;


/**
 * 监控查询服务
 * @author ibm-zhuwei
 * @date 2013-2-27 下午4:41:25
 */
public class MonitorQueryServiceTest extends BaseTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorQueryServiceTest.class);

	@Autowired
	private IMonitorQueryService monitorQueryService;

	@Test
	public void testQueryIndicatorGroupByCategory() {
		List<String> list = monitorQueryService
				.queryIndicatorGroupByCategory(BusinessMonitorIndicator.CATEGORY_COMMON);
		
		LOGGER.info("数据:" + list.toArray());
	}

	@Test
	public void testQueryCommonIndicatorHeader() {
		LinkedHashMap<String, List<MonitorIndicatorEntity>> indicatorGroups = monitorQueryService
				.queryCommonIndicatorHeader(Arrays.asList("开单运单量（付款方式）",
						"开单运单金额"));
		
		for (Map.Entry<String, List<MonitorIndicatorEntity>> indicatorGroup : indicatorGroups.entrySet()) {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("KEY: " + indicatorGroup.getKey() + " ; VALUE: ");
			
			for (MonitorIndicatorEntity entity : indicatorGroup.getValue()) {
				sb.append("\n");
				sb.append(ToStringBuilder.reflectionToString(entity, ToStringStyle.SIMPLE_STYLE));
			}
			
			LOGGER.info(sb.toString());
		}
	}
	
	@Test
	public void testQueryDailyCommonIndicatorData() {
		
		List<Map<String, String>> result = monitorQueryService
				.queryDailyCommonIndicatorData(DateUtils.convert("2013-02-25"),
						Arrays.asList("开单运单量（付款方式）", "开单运单金额"), "DIP");
		
		for (Map<String, String> map : result) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("\nkey: " + entry.getKey() + " , value: " + entry.getValue());
			}
			LOGGER.info(sb.toString());
		}
		
	}

	@Test
	public void testQueryMonthlyCommonIndicatorData() {
		
		List<Map<String, String>> result = monitorQueryService
				.queryMonthlyCommonIndicatorData(DateUtils.convert("2013-02-25"),
						Arrays.asList("开单运单量（付款方式）", "开单运单金额"), "DIP");
		
		for (Map<String, String> map : result) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("\nkey: " + entry.getKey() + " , value: " + entry.getValue());
			}
			LOGGER.info(sb.toString());
		}
		
	}

	@Test
	public void testQueryNewIndicatorHeader() {
		LinkedHashMap<String, List<MonitorIndicatorEntity>> indicatorGroups = monitorQueryService
				.queryNewIndicatorHeader(Arrays.asList("更改单内部更改比率", "更改单自动受理比率"));
		
		for (Map.Entry<String, List<MonitorIndicatorEntity>> indicatorGroup : indicatorGroups.entrySet()) {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("KEY: " + indicatorGroup.getKey() + " ; VALUE: ");
			
			for (MonitorIndicatorEntity entity : indicatorGroup.getValue()) {
				sb.append("\n");
				sb.append(ToStringBuilder.reflectionToString(entity, ToStringStyle.SIMPLE_STYLE));
			}
			
			LOGGER.info(sb.toString());
		}
	}

	@Test
	public void testQueryDailyNewIndicatorData() {
		
		List<Map<String, String>> result = monitorQueryService
				.queryDailyNewIndicatorData(DateUtils.convert("2013-02-25"),
						Arrays.asList("订单改派率", "订单处理效率", "自动预分配识别率", "更改单内部更改比率"), 
						Arrays.asList("DIP", "W01", "W3100020619"));
		
		for (Map<String, String> map : result) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("\nkey: " + entry.getKey() + " , value: " + entry.getValue());
			}
			LOGGER.info(sb.toString());
		}
		
	}
	
	@Test
	public void testQueryApplicationIndicatorHeader() {
		LinkedHashMap<String, String> headers = monitorQueryService
				.queryApplicationIndicatorHeader("系统功能使用情况");
		
		for (Map.Entry<String, String> header : headers.entrySet()) {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("KEY: " + header.getKey() + " ; VALUE: " + header.getValue());
			
			LOGGER.info(sb.toString());
		}
	}

	@Test
	public void testQueryDailyApplicationIndicatorData() {
		List<Map<String, String>> result1 = monitorQueryService.queryDailyApplicationIndicatorData(
				DateUtils.convert("2013-03-11"), "系统功能使用情况", Arrays.asList("DIP"),new Date(),new Date());

		print(result1);

		List<Map<String, String>> result2 = monitorQueryService.queryDailyApplicationIndicatorData(
				DateUtils.convert("2013-03-11"), "组织在线情况", Arrays.asList("DIP", "W01", "W0133"),new Date(),new Date());

		print(result2);

		List<Map<String, String>> result3 = monitorQueryService.queryDailyApplicationIndicatorData(
				DateUtils.convert("2013-03-18"), "模拟登陆情况", Arrays.asList("DIP"),new Date(),new Date());

		print(result3);
	}
	
	private void print(List<Map<String, String>> result) {

		if (result == null) {
			return;
		}
		
		for (Map<String, String> map : result) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("\nkey: " + entry.getKey() + " , value: " + entry.getValue());
			}
			LOGGER.info(sb.toString());
		}

	}
	
}
