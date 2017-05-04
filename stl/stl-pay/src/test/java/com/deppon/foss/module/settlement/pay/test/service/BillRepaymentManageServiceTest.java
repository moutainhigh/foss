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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/BillRepaymentManageServiceTest.java
 * 
 * FILE NAME        	: BillRepaymentManageServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.CommonTestUtil;

public class BillRepaymentManageServiceTest extends BaseTestCase {

	private final Logger logger = LogManager.getLogger(getClass());
	@Autowired
	private IBillRepaymentManageService billRepaymentManageService;

	// 作废
	@Test
	@Rollback(true)
	public void disableBillRepayment() {

		// 还款单号
		String selectBillRepayNos = "HK100003502";

		BillRepaymentManageDto dto = new BillRepaymentManageDto();
		dto.setSelectBillRepayNos(selectBillRepayNos);

		// 获取用户信息
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		
		try{
			billRepaymentManageService.disableBillRepayment(dto, info);
		}catch(BusinessException e){
			logger.debug(e.getMessage());
		}
		

	}

	// 还款
	@Test
	@Rollback(true)
	public void repaymentTest() {

		String[] statementBillNos = { "DZ00000941" };

		BillStatementToPaymentQueryDto dto = new BillStatementToPaymentQueryDto();
		dto.setCustomerCode("0004");
		dto.setCustomerName("旺达科技");
		dto.setDescription("测试只能先这样了");
		dto.setRemittanceNumber("6225 8821 1827 3421");
		dto.setRepaymentAmount(BigDecimal.valueOf(100));
		dto.setStatementBillNos(statementBillNos);
		dto.setRepaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);

		// 获取用户信息
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		try {
			billRepaymentManageService.repayment(dto, info);
		}catch(BusinessException e){
			logger.debug(e.getMessage());
		}

	}
}
