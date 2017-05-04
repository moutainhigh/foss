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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/CodLogServiceTest.java
 * 
 * FILE NAME        	: CodLogServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.jgroups.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.consumer.api.server.service.ICodLogService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;

/**
 * 代收货款日志测试
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-14 上午10:07:08
 */
public class CodLogServiceTest extends BaseTestCase {

	@Autowired
	private ICodLogService codLogService;

	/**
	 * 测试新加
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-14 上午10:09:04
	 */
	@Test
	public void testAddCodLog() {

		int rows = this.addCodLog();

		Assert.assertEquals(1, rows);
	}

	/**
	 * 按运单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-14 上午10:14:28
	 */
	@Test
	public void testQueryByWaybill() {
		//插入数据
		int rows = this.addCodLog();
		Assert.assertEquals(1, rows);
		
		//查询
		List<CODLogEntity> codLogSet = this.codLogService.queryByWaybill("w1231231");
		Assert.assertNotNull(codLogSet);
		Assert.assertEquals(1, codLogSet.size());
	}

	/**
	 * 新加操作日志
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午10:40:27
	 */
	private int addCodLog() {
		CODLogEntity entity = new CODLogEntity();

		entity.setId(UUID.randomUUID().toString()); // Id
		entity.setCodId("codId");
		entity.setWaybillNo("w1231231");
		entity.setBusinessDate(new Date());
		entity.setOperateTime(new Date());
		entity.setOperateBillType("operateBillType");
		entity.setOperateActiontype("operateActiontype");
		entity.setActive("1");
		// 加入
		int addResult = this.codLogService.addCodLog(entity);
		return addResult;
	}
}
