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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/writeoff/test/service/StatementReceiptServiceTest.java
 * 
 * FILE NAME        	: StatementReceiptServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService;
import com.deppon.foss.module.settlement.writeoff.test.BaseTestCase;
import com.deppon.foss.module.settlement.writeoff.test.CommonTestUtil;

public class StatementReceiptServiceTest extends BaseTestCase {

	@Autowired
	private IStatementReceiptService statementReceiptService;

	// 根据对账单号查询对账单回执
	@Test
	@Rollback(true)
	public void testQueryReceiptList() {

		// 正确查询
		String statementBillNo = "DZ00000643";
		List<StatementConfReceiptEntity> list = statementReceiptService
				.queryReceiptList(statementBillNo);
		Assert.assertNotNull(list);

		// 错误单号查询
		String statementBillNoE = "px0t0d06v3";
		List<StatementConfReceiptEntity> listE = statementReceiptService
				.queryReceiptList(statementBillNoE);
		Assert.assertEquals(listE.size(), 0);
	}

	// 根据对账单号查询对账单回执
	@Test
	@Rollback(true)
	public void updateStatementConfReceipt() {
		
		// 正确查询
		String statementBillNo = "DZ00000643";
		List<StatementConfReceiptEntity> list = statementReceiptService
				.queryReceiptList(statementBillNo);
		
		// 获取当前登录用户信息
		CurrentInfo cInfo = CommonTestUtil.getCurrentInfo();
		
		if(CollectionUtils.isNotEmpty(list)){
			for(StatementConfReceiptEntity entity : list){
				
				
				
				entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
				entity.setReceiveEmpName(cInfo.getEmpName());
				entity.setReceivedAmount(BigDecimal.valueOf(1000));
				entity.setPaymentDate(new Date(0));
				entity.setCustomerIdea("你猜你猜你猜猜猜");
			}
		}
		
	}
}
