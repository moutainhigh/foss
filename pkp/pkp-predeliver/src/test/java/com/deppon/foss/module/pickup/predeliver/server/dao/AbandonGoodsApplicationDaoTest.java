/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/dao/AbandonGoodsApplicationDaoTest.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.AbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.server.util.SpringTestHelper;

public class AbandonGoodsApplicationDaoTest {
	private AbandonGoodsApplicationDao abandonGoodsApplicationDao;

	@Before
	public void init() throws Exception {
		abandonGoodsApplicationDao = SpringTestHelper.get().getBean(AbandonGoodsApplicationDao.class);
	}

	// @Test
	public void testinsertAbandonGoodsApplication() {
		AbandonGoodsApplicationEntity entity = new AbandonGoodsApplicationEntity();
		// 运单号
		entity.setWaybillNo("12306");

		entity.setId("101011");
		// 异常_ID
		entity.settSrvExceptionId("123456");
		// 弃货类型
		entity.setAbandonedgoodsType("1");
		// 预弃货人
		entity.setCreateUser("赵斌");
		// 预弃货人编码
		entity.setCreateUserCode("12345");
		// 预弃货部门
		entity.setCreateOrgName("徐金");
		// 预弃货部门编码
		entity.setCreateOrgCode("999");
		// 预弃货时间
		entity.setPreabandonedgoodsTime(new Date(System.currentTimeMillis()));
		// 弃货事由
		entity.setNotes("娱乐");
		// 状态
		entity.setStatus("1");
		// 操作人
		entity.setOperator("wahaha");

		// 创建人
		entity.setCreateUserName("赵斌");
		//
		abandonGoodsApplicationDao.insertAbandonGoodsApplication(entity);
	}

	@Test
	public void testsearchAbandonGoodsList() {
		AbandonedGoodsSearchDto dto = new AbandonedGoodsSearchDto();
		dto.setWaybillNo("2012123");
		dto.setStatus("1");
		dto.setAbandonedgoodsType("1");
		dto.setReceiveOrgCode("");
		dto.setDeliveryCustomerName("1");
		dto.setCreateUserName("");
		dto.setPreabandonedgoodsTimeBegin(new Date("2012/10/01"));
		dto.setPreabandonedgoodsTimeEnd(new Date("2012/10/30"));
		abandonGoodsApplicationDao.searchAbandonGoodsList(dto);
	}

	@Test
	public void testgetAbandonGoodsDetail() {
		abandonGoodsApplicationDao.getAbandonGoodsDetailById("101011");
	}

	@Test
	public void testgetInStockTimeByWaybillNoAndOrgCode() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", "2012003");
		map.put("orgCode", "888");
		abandonGoodsApplicationDao.getInStockTimeByWaybillNoAndOrgCode(map);
	}
}