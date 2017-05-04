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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/test/service/BusinessLockServiceTest.java
 * 
 * FILE NAME        	: BusinessLockServiceTest.java
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
 * FILE    NAME: LockRedisServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.common.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.common.test.BaseTestCase;


/**
 * 业务分布式锁服务测试类
 * @author ibm-zhuwei
 * @date 2013-1-17 下午7:25:11
 */
public class BusinessLockServiceTest extends BaseTestCase {

	@Autowired
	private IBusinessLockService businessLockService;

	@Test
	public void testLock1() {
		
		MutexElement mutex = new MutexElement(String.valueOf(RandomUtils
				.nextInt(100000000)), "测试", MutexElementType.WAYBILL_NO);
		
		boolean b1 = businessLockService.lock(mutex, 0);
		Assert.assertTrue(b1);

		boolean b2 = businessLockService.lock(mutex, 0);
		Assert.assertFalse(b2);
		
		boolean b3 = businessLockService.lock(mutex, mutex.getTtl() + 1);
		Assert.assertTrue(b3);

		businessLockService.unlock(mutex);
		
	}
	

	@Test
	public void testLock2() {

		MutexElement mutex = new MutexElement(String.valueOf(RandomUtils
				.nextInt(100000000)), "测试", MutexElementType.WAYBILL_NO);
		
		long begin = System.currentTimeMillis();
		
		for (int i = 0; i < 1000; ++i) {

			boolean b4 = businessLockService.lock(mutex, 0);
			Assert.assertTrue(b4);
			businessLockService.unlock(mutex);
		}
		
		long end = System.currentTimeMillis();
		
		logger.info("total seconds: " + (end - begin / 1000.0));
	}

	@Test
	public void testBatchLock1() {
		
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		for (int i = 0; i < 100; ++i) {
			MutexElement mutex = new MutexElement(String.valueOf(RandomUtils
					.nextInt(100000000)), "测试" + i, MutexElementType.WAYBILL_NO);
			mutexes.add(mutex);
		}
		
		boolean b1 = businessLockService.lock(mutexes, 0);
		Assert.assertTrue(b1);

		boolean b2 = businessLockService.lock(mutexes, 0);
		Assert.assertFalse(b2);
		
		boolean b3 = businessLockService.lock(mutexes, mutexes.get(0).getTtl() + 1);
		Assert.assertTrue(b3);

		businessLockService.unlock(mutexes);
		
	}

	@Test
	public void testBatchLock2() {
		
		List<MutexElement> mutexes1 = new ArrayList<MutexElement>();
		List<MutexElement> mutexes2 = new ArrayList<MutexElement>();
		for (int i = 0; i < 100; ++i) {
			MutexElement mutex = new MutexElement(String.valueOf(RandomUtils
					.nextInt(100000000)), "测试" + i, MutexElementType.WAYBILL_NO);
			mutexes1.add(mutex);
		}
		mutexes2.add(mutexes1.get(0));
		mutexes2.add(new MutexElement(String.valueOf(RandomUtils
				.nextInt(100000000)), "测试", MutexElementType.WAYBILL_NO));
		
		boolean b1 = businessLockService.lock(mutexes1, 0);
		Assert.assertTrue(b1);

		boolean b2 = businessLockService.lock(mutexes2, 0);
		Assert.assertFalse(b2);
		
		boolean b3 = businessLockService.lock(mutexes2, mutexes2.get(0).getTtl() + 1);
		Assert.assertTrue(b3);

		businessLockService.unlock(mutexes1);
		businessLockService.unlock(mutexes2);
	}

	@Test
	public void testBatchLock3() {

		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		for (int i = 0; i < 100; ++i) {
			MutexElement mutex = new MutexElement(String.valueOf(RandomUtils
					.nextInt(100000000)), "测试", MutexElementType.WAYBILL_NO);
			mutexes.add(mutex);
		}
		
		long begin = System.currentTimeMillis();
		
		for (int i = 0; i < 100; ++i) {

			boolean b4 = businessLockService.lock(mutexes, 0);
			Assert.assertTrue(b4);
			businessLockService.unlock(mutexes);
		}
		
		long end = System.currentTimeMillis();
		
		logger.info("total seconds: " + (end - begin / 1000.0));
	}

	@Test
	public void testBatchLock4() {
		
		List<MutexElement> mutexes = new ArrayList<MutexElement>();
		
		MutexElement mutex1 = new MutexElement(String.valueOf(RandomUtils
				.nextInt(100000000)), "测试", MutexElementType.WAYBILL_NO);
		MutexElement mutex2 = new MutexElement(mutex1.getBusinessNo(), 
				mutex1.getBusinessDesc(), mutex1.getType());
		mutexes.add(mutex1);
		mutexes.add(mutex2);
		
		boolean b1 = businessLockService.lock(mutexes, 0);
		Assert.assertTrue(b1);

		businessLockService.unlock(mutexes);
		
	}

	@Test
	public void testDate() {
		
		Calendar calendar = Calendar.getInstance();
		
		Calendar startTime = DateUtils.truncate(calendar, Calendar.DATE);

		logger.info("------------startTime: " + startTime.getTime());
		
		int minutes = 30;
		long result = startTime.getTime().getTime();
		logger.info("------------date: " + new Date(result));
		
		result += calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR;
		logger.info("------------hour: " + new Date(result));
		
		result += ( calendar.get(Calendar.MINUTE) / minutes ) * minutes * DateUtils.MILLIS_PER_MINUTE;
		logger.info("------------minute: " + new Date(result));

		logger.info("------------hour: " + calendar.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR);
		logger.info("------------minute: " + ( calendar.get(Calendar.MINUTE) / minutes ) * DateUtils.MILLIS_PER_MINUTE);
		logger.info("------------minute: " + calendar.get(Calendar.MINUTE));
		
		logger.info("------------date: " + new Date(result));
	}
}
