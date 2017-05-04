/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/OutWarehouseExceptionServiceTest.java
 * 
 * FILE NAME        	: OutWarehouseExceptionServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOutWarehouseExceptionService;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;


/**
 * 异常出库测试
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-8 下午1:30:49
 */
public class OutWarehouseExceptionServiceTest extends BaseTestCase {

	private static final Logger log = LogManager
			.getLogger(OutWarehouseExceptionServiceTest.class);

	@Autowired
	private IOutWarehouseExceptionService outWarehouseExceptionService;

	/**
	 * 异常出库测试
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午1:34:55
	 */
	@Test
	@Rollback(false)
	public void outWarehouseExceptionTest() {

		log.info("测试开始.......");
		
		// 正确结果
		try {
			String waybillNo = "42132028";
			String expType = SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS;

			outWarehouseExceptionService.outWarehouseException(waybillNo,
					expType, ConsumerTestUtil.getCurrentInfo());
		} catch (SettlementException ex) {
			Assert.assertNull(ex);
		}

		// 异常结果,错误类型
//		try {
//			String waybillNo = "69324078";
//			String expType = "正常";
//			outWarehouseExceptionService.outWarehouseException(waybillNo,
//					expType, ConsumerTestUtil.getCurrentInfo());
//		} catch (SettlementException ex) {
//			Assert.assertNotNull(ex);
//		}
//
//		// 异常结果,错误运单号
//		try {
//			String waybillNo = "6*3p40x812#41";
//			String expType = "正常";
//			outWarehouseExceptionService.outWarehouseException(waybillNo,
//					expType,ConsumerTestUtil.getCurrentInfo());
//		} catch (SettlementException ex) {
//			Assert.assertNotNull(ex);
//		}

		log.info("测试结束.......");
	}
}
