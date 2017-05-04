package com.deppon.foss.module.transfer.scheduling.dao;

/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: TruckSchedulingTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.server.dao.impl.TruckSchedulingTaskDao;
import com.deppon.foss.util.UUIDUtils;

/**
 * 排班表单元测试
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 上午9:21:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/scheduling/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class TruckSchedulingTaskTest extends AbstractTransactionalJUnit4SpringContextTests {
	final Logger logger = LoggerFactory.getLogger(TruckSchedulingTaskTest.class);

	@Autowired
	TruckSchedulingTaskDao truckSchedulingTaskDao;
	@Autowired
	ITruckSchedulingDao truckSchedulingDao;
	@Autowired
	ITruckSchedulingTaskService truckSchedulingTaskService;

	@BeforeTransaction
	public void beforeTransaction() {
		// generateTruckSchedulingTask();
		// testDelteTaskAndModifySchedule();

	}

	@AfterTransaction
	public void afterTransaction() {

	}

	/**
	 * 按照月份查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午2:33:53
	 */
	public void generateTruckSchedulingTask() {
		TruckSchedulingEntity tsEntity = new TruckSchedulingEntity();
		tsEntity.setYearMonth("2012-11");// 排班年月
		tsEntity.setSchedulingType("TFR");// 短途排班
		// tsEntity.setPlanType("1");//
		List<TruckSchedulingEntity> resList = truckSchedulingDao.queryTruckScheduling(tsEntity);
		TruckSchedulingTaskEntity entity = null;
		List<TruckSchedulingTaskEntity> genList = new ArrayList<TruckSchedulingTaskEntity>();
		int i = 0;
		for (TruckSchedulingEntity tempEntity : resList) {
			entity = new TruckSchedulingTaskEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setSchedulingId(tempEntity.getId());
			entity.setVehicleNo("沪123456" + i);
			entity.setTruckModel("7.6米");
			entity.setFrequencyNo("0" + i % 2);
			entity.setLineNo("线路" + i);
			entity.setDepartTime(new Date());
			entity.setCarOwner("徐静车队");
			entity.setTaskStatus("1");
			// entity.setZoneCode("123456");
			genList.add(entity);
			i++;
			if (genList.size() > 100) {
				truckSchedulingTaskDao.batchInsertTruckSchedulingTask(genList);
				genList = new ArrayList<TruckSchedulingTaskEntity>();
			}
		}
		if (genList.size() > 0) {
			truckSchedulingTaskDao.batchInsertTruckSchedulingTask(genList);
		}

	}

	public void testDelteTaskAndModifySchedule() {
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		simDto.setDriverOrgCode("1351497843609");
		simDto.setDriverCode("096582");
		simDto.setYmd("2012-11-01");
		simDto.setSchedulingtype("TFR");
		simDto.setSchedulingStatus("1");
		simDto.setPlanType(ScheduleConstants.PLAN_TYPE_REST);
		// truckSchedulingTaskService.delteTaskAndModifySchedule(simDto);
	}

	/**
	 * 查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午2:46:42
	 */
	public void querySchedulingAndTask() {
		SimpleTruckScheduleDto entity = new SimpleTruckScheduleDto();
		entity.setSchedulingStatus("1");
		entity.setSchedulingtype("1");
		truckSchedulingTaskDao.querySchedulingAndTask(entity);
	}

	@Test
	public void testQuerySchedulingForExport() {
		// 查询条件
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		simDto.setSchedulingtype("TFR");
		simDto.setYearMonth("2012-11");
		simDto.setDriverOrgCode("1351497843609");
		simDto.setSchedulingStatus("1");

		List<String> list = new ArrayList<String>();
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		simDto.setList(list);
		// 执行查询
		truckSchedulingTaskService.querySchedulingForExport(simDto);

		truckSchedulingTaskService.exportExcelStream(simDto);
	}

}
