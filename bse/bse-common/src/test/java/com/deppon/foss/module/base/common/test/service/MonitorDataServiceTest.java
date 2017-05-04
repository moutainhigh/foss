/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.test.service
 * FILE    NAME: MonitorDataServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.common.api.server.service.IMonitorDataService;
import com.deppon.foss.module.base.common.api.shared.domain.MonitorDataEntity;
import com.deppon.foss.module.base.common.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;


/**
 * TODO（描述类的职责）
 * @author ibm-zhuwei
 * @date 2013-3-8 下午3:34:32
 */
public class MonitorDataServiceTest extends BaseTestCase {
	
	@Autowired
	private IMonitorDataService monitorDataService;
	
	@Test
	@Rollback(false)
	public void testBatchAdd() {
		
		List<MonitorDataEntity> list = new ArrayList<MonitorDataEntity>();
		for (int i = 0; i < 100; ++i) {
			MonitorDataEntity entity = new MonitorDataEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setCreateTime(new Date());
			entity.setOrgCode("1");
			entity.setMonitorDate(new Date());
			entity.setMonitorTimeRange("11");
			entity.setMonitorStartTime(new Date());
			entity.setMonitorEndTime(new Date());
			entity.setIndicatorCode("TEST");
			entity.setIndicatorValue(Long.valueOf(i));
			
			list.add(entity);
		}
		
		long begin = System.currentTimeMillis();

		monitorDataService.batchDeleteMonitorData(DateUtils.convert("2013-03-12"), "11", "1", Arrays.asList(new String("SIMULATE_LOGIN_COUNT")));
		
		monitorDataService.batchAddMonitorData(list);
		
		long end = System.currentTimeMillis();
		
		logger.info("total seconds: " + (end - begin / 1000.0));
		
	}
}
