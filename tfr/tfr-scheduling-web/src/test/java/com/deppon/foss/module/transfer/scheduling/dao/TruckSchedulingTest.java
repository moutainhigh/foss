package com.deppon.foss.module.transfer.scheduling.dao;

/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: TruckSchedulingTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 排班表测试
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 上午9:21:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/scheduling/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class TruckSchedulingTest extends AbstractTransactionalJUnit4SpringContextTests {
	final Logger logger = LoggerFactory.getLogger(TruckSchedulingTest.class);

	@Autowired
	ITruckSchedulingDao truckSchedulingDao;

	@BeforeTransaction
	public void beforeTransaction() {
		// testTruckScheduling(true);
		// testProductData();

	}

	@AfterTransaction
	public void afterTransaction() {

	}

	/**
	 * 生成司机的本月排班图
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午11:34:22
	 */

	public void testProductData() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cdr = Calendar.getInstance();

		String[] driverCodes = { "096591", "096592", "096593", "096594", "096595", "096596", "096597", "096598",
				"096599", "096580", "096581", "096582" };//
		List<TruckSchedulingEntity> tempList = new ArrayList<TruckSchedulingEntity>();
		// 上月-后面12个月的数据一起生成
		for (int j = -1; j < 11; j++) {
			cdr.add(Calendar.MONTH, j);
			for (int i = 0; i < driverCodes.length; i++) {
				String driverCode = driverCodes[i];
				// 本月最大天数
				int actualMaximum = cdr.getActualMaximum(Calendar.DATE);
				for (int day = 1; day <= actualMaximum; day++) {
					cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
					String planType = "REST";
					if (day % 2 == 0) {
						planType = "WORK";
					}
					if (day % 3 == 0) {
						planType = "DUTY";
					}
					if (day % 5 == 0) {
						planType = "TRAINING";
					}
					if (day % 7 == 0) {
						planType = "LEAVE";
					}
					if (day % 11 == 0) {
						planType = "REST";
					}
					tempList.add(makeTruckScheduling(driverCode, "138166" + String.valueOf(Math.rint(10000)),
							cdr.getTime(), planType, f.format(cdr.getTime())));
					// 每隔100提交一次
					if (tempList.size() == 100) {
						truckSchedulingDao.batchInsertTruckScheduling(tempList);
						tempList = new ArrayList<TruckSchedulingEntity>();
					}
				}

			}
			// 回到当前时间
			cdr.setTime(new Date());
		}
		if (tempList.size() > 0) {
			truckSchedulingDao.batchInsertTruckScheduling(tempList);
		}

	}

	/**
	 * 按照月份查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午2:33:53
	 */
	@Test
	public void queryTruckSchedulingList() {
		TruckSchedulingEntity tsEntity = new TruckSchedulingEntity();
		tsEntity.setYearMonth("2012-10");// 排班年月
		tsEntity.setSchedulingType("1");// 短途排班
		tsEntity.setPlanType("1");//
		List<TruckScheduleGridDto> resList = truckSchedulingDao.queryTruckSchedulingList(tsEntity);
		for (TruckScheduleGridDto tempEntity : resList) {
			logger.info(tempEntity.toString());
		}

	}

	/**
	 * 排班数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 上午8:55:38
	 */
	private TruckSchedulingEntity makeTruckScheduling(String driverCode, String phone, Date schedulingDate,
			String planType, String ymd) {
		TruckSchedulingEntity ts = new TruckSchedulingEntity();
		ts.setDriverCode(driverCode);
		ts.setDriverOrgCode("1351497843609");
		ts.setDriverPhone(phone);
		ts.setId(UUIDUtils.getUUID());
		ts.setPlanType(planType);
		ts.setSchedulingDate(schedulingDate);
		ts.setSchedulingStatus("1");// 可用
		ts.setDateNum(Integer.parseInt(ymd.substring(8, 10)));// 日期数字
		ts.setYearMonth(ymd.substring(0, 7));
		ts.setSchedulingType("TFR");// 排班类型（TFR短途PKP接送货）排班
		return ts;
	}

	public static void main(String agr[]) {
		TruckSchedulingTest t = new TruckSchedulingTest();
		TruckSchedulingEntity ts = new TruckSchedulingEntity();
		ts.setDriverCode("1111");
		// SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		// simDto.setDriverCode("2222");
		t.chage(ts.getClass().getName(), ts);
		System.out.println(ts.getDriverCode());
	}

	private void chage(String className, Object o) {
		if (TruckSchedulingEntity.class.getName().equals(className)) {
			TruckSchedulingEntity e = (TruckSchedulingEntity) o;
			e.setDriverCode("3333");
		} else {
			SimpleTruckScheduleDto d = (SimpleTruckScheduleDto) o;
			d.setDriverCode("5555");
		}

	}
}
