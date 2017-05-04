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
 * PROJECT NAME	: stl-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillPayablePtpServiceTest.java
 * 
 * FILE NAME        	: BillPayablePtpServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2016  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayablePtpService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.NewBillPayablePtpEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillNewPayablePtpDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

/**
 * ptp应付单测试类
 * @author 302346-foss-jiang xun
 * @date 2016-01-29 上午10:01:49
 */
public class BillPayablePtpServiceTest extends BaseTestCase {
	
	/**
	 * ptp应付单service
	 */
	@Autowired
	private IBillPayablePtpService billPayablePtpService;

	/**
	 * 验证ptp应付单是否可以发更改测试类
	 * @author 302346-foss-jiang xun
	 * @date 2016-01-29 上午10:04:49
	 */
	@Test
	@Rollback(true)
	public void testIsStated() {
		String wayBillNo = "201402013";
		billPayablePtpService.isStated(wayBillNo);
	}
	
	
	/**
	 * 校验合伙人应付单是否重复 测试方法
	 */
	@Test
	@Rollback(true)
	public void generatPtpPayableBillTest() {
		BillNewPayablePtpDto dto = new BillNewPayablePtpDto();
		List<NewBillPayablePtpEntity> newPayablePtpEntityList = new ArrayList<NewBillPayablePtpEntity>();
		NewBillPayablePtpEntity newEntity = new NewBillPayablePtpEntity();
		
		BillPayableEntity entity = new BillPayableEntity();
		entity.setWaybillNo("999876533");
		entity.setBillType("PFCP");
		
		newEntity.setBillPayableEntity(entity);
		newPayablePtpEntityList.add(newEntity);
		
		dto.setNewPayablePtpEntityList(newPayablePtpEntityList);
		billPayablePtpService.generatPtpPayableBill(dto);
	}

	
}
