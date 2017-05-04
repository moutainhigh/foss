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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillAdvancedPaymentServiceTest.java
 * 
 * FILE NAME        	: BillAdvancedPaymentServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预付单服务测试类
 * 
 * @author foss-pengzhen
 * @date 2012-10-19 上午9:34:49
 */
public class BillAdvancedPaymentServiceTest extends BaseTestCase {

	@Autowired
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	@Before
	public void setUp() {
		
	}

	@After
	public void tearDown() {

	}
	
	/**
	 * 修改审批状态
	 * @author foss-pengzhen
	 * @date 2013-1-7 下午4:35:22
	 * @see
	 */
	@Test
	@Rollback(false)
	public void testUpdateAdvancePaymentAuditStatus() {
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		entity.setId("a598fde1-3da6-4bec-a947-ffaa6b9cafc0");
		entity.setAuditStatus("AA");
		entity.setVersionNo((short)1);
		billAdvancedPaymentService.updateAdvancePaymentAuditStatus(entity);
	}
	
	/**
	 * 新增预收单信息
	 * @author foss-pengzhen
	 * @date 2012-12-18 上午9:39:19
	 * @see
	 */
	@Test
	@Rollback(false)
	public void testadd(){
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setAdvancesNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF90));
		entity.setAmount(new BigDecimal(5));
		entity.setUnverifyAmount(new BigDecimal(5));
		entity.setVerifyAmount(new BigDecimal(0));
		entity.setCustomerCode("customerCode");
		entity.setCustomerName("customerName");
		entity.setApplyOrgCode("applyOrgCode");
		entity.setApplyOrgName("applyOrgName");
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(FossConstants.NO);
		Date now = new Date();
		entity.setBusinessDate(now);
		entity.setAccountDate(now);
		entity.setCreateTime(now) ;
		entity.setPaymentOrgCode("paymentOrgCode");
		entity.setPaymentOrgName("paymentOrgName");
		entity.setPaymentType("paymentType");
		entity.setBillType(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR);
		entity.setIsInit(FossConstants.NO);
		entity.setVersionNo((short) 1);
		billAdvancedPaymentService.addAdvancedPaymentEntity(entity, CommonTestUtil.getCurrentInfo());
	}
	/**
	 * 审批预付单
	 * @author foaa-pengzhen
	 * @date 2012-11-29 下午8:45:17
	 * @see
	 */
	@Test
	@Rollback(false)
	public void testUpdatePaymentBillResult() {
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		Date date = new Date();
		entity.setBillType("TODO");
		entity.setAuditUserCode("TODOID");
		entity.setAuditUserName("TODO");

		entity.setModifyTime(date);
		entity.setModifyUserCode("TODOMID");
		entity.setModifyUserCode("TODOM");
		try {
			this.billAdvancedPaymentService.updatePaymentBillResult(entity,
					CommonTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}
	
	/**
	 * 作废预付单
	 * @author foss-pengzhen
	 * @date 2012-11-29 下午8:46:04
	 * @see
	 */
	@Test
	@Rollback(false)
	public void testWriteBackAdvancePay() {
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		Date date = new Date();
		entity.setBillType("TODO");
		entity.setAuditUserCode("TODOID");
		entity.setAuditUserName("TODO");

		entity.setModifyTime(date);
		entity.setModifyUserCode("TODOMID");
		entity.setModifyUserCode("TODOM");
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersionNo((short) 11);
		try {
			this.billAdvancedPaymentService.writeBackAdvancePay(entity,
					CommonTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

}
