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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/SoaRepaymentManageServiceTest.java
 * 
 * FILE NAME        	: SoaRepaymentManageServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;

/**
 * 对账单还款单测试
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-29 下午5:22:45
 */
public class SoaRepaymentManageServiceTest extends BaseTestCase {

	@Autowired
	private ISoaRepaymentManageService soaRepaymentManageService;

	// 新增对账单还款单关系数据
	@Test
	@Rollback(true)
	public void add() {
		SoaRepaymentEntity entity = addEntity();
		int i = soaRepaymentManageService.add(entity);
		Assert.assertEquals(i, 1);
	}

	// 根据还款单号查询还款单关系数据
	@Test
	@Rollback(true)
	public void queryListByRepaymentNo() {
		
		add();
		String repaymentNo = "HK00000111";
		List<SoaRepaymentEntity> list = soaRepaymentManageService.queryListByRepaymentNo(repaymentNo);
		Assert.assertEquals(list.size(), 1);
	}

	//生产实体
	private SoaRepaymentEntity addEntity(){
		
		SoaRepaymentEntity entity = new SoaRepaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setStatementBillNo("DZ00000111");
		entity.setRepaymentNo("HK00000111");
		entity.setPaymentDate(new Date());
		entity.setRepaymentAmount(BigDecimal.valueOf(10000));
		
		return entity;
	}
	
}
