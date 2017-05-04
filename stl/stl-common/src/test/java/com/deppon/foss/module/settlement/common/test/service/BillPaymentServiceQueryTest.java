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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillPaymentServiceQueryTest.java
 * 
 * FILE NAME        	: BillPaymentServiceQueryTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款单查询测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-20 下午2:02:12
 * @since
 * @version
 */
public class BillPaymentServiceQueryTest extends BaseTestCase{
	@Autowired
	private IBillPaymentService  billPaymentService;
	
	@Autowired
	private ISettlementCommonService settlementCommonService;

	
	@Before
	public void setUp() {

	}
	
	@After
	public void tearDown() {
		
	}
	
	
	private BillPaymentEntity getBillPaymentEntity() {
		
		BillPaymentEntity entity = new BillPaymentEntity();
		
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		entity.setPaymentNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);
		//TODO 
//		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		
		//TODO
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		entity.setSourceBillNo("12345678");
		//TODO
		entity.setBankHqName("BANK_HQ_NAME");
		entity.setBankHqCode("BANK_HQ_CODE");
		entity.setPaymentType("TEST");
		entity.setRemitStatus("TEST");
		entity.setAuditStatus("TEST");
		entity.setBillType("TEST");
		entity.setPaymentOrgCode("ORG");
		entity.setPaymentOrgName("部门");
		entity.setMobilePhone("MOBILE");
		entity.setAccountNo("BANK_NO");
		entity.setPayeeName("USER_NAME");
		entity.setAccountType(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__COMPANY);
		entity.setProvinceName("PROV");
		entity.setProvinceCode("PROVCODE");
		entity.setCityCode("CITY");
		entity.setCityName("CITYNAME");
		entity.setBankBranchCode("BANK_BRANCH_CODE");
		entity.setBankBranchName("BANK_BRANCH_NAME");
		entity.setApplyWorkFlowNo("APPLY_WORK_FLOW_NO");
		
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setAmount(NumberUtils.createBigDecimal("100.00"));
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setBatchNo("111");
		
		return entity;
	}

	/**
	 * 查询
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-20 下午2:04:46
	 */
	@Test
	@Rollback(false)
	public void testQuery(){
		
		BillPaymentEntity payment=this.getBillPaymentEntity();
		BillPaymentEntity paymentOne=this.getBillPaymentEntity();
		BillPaymentEntity paymentTwo=this.getBillPaymentEntity();
		
		this.billPaymentService.addBillPayment(payment, CommonTestUtil.getCurrentInfo());
		this.billPaymentService.addBillPayment(paymentOne, CommonTestUtil.getCurrentInfo());
		this.billPaymentService.addBillPayment(paymentTwo, CommonTestUtil.getCurrentInfo());
		
		
		List<String> paymentNos=new ArrayList<String>();
		paymentNos.add(payment.getPaymentNo());
		paymentNos.add(paymentOne.getPaymentNo());
		paymentNos.add(paymentTwo.getPaymentNo());
		List<BillPaymentEntity> list=this.billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
		Assert.assertEquals(list.size(), 3);
		
		
//		list=this.billPaymentService.queryPaymentRemitStatusByPaymentNOs(paymentNos, FossConstants.ACTIVE);
//		logger.info("  付款单的汇款状态： "+list.size());
//		List<String> sourceBillNos=new ArrayList<String>();
//		sourceBillNos.add("12345678");
//		
//		list=this.billPaymentService.queryBillPaymentBySourceBillNOs(sourceBillNos,"", FossConstants.ACTIVE);
//		
//		logger.info(" 按来源单号进行查询： "+list.size());
//		
//		BillPaymentConditionDto dto=new BillPaymentConditionDto();
//		dto.setPaymentNo(payment.getPaymentNo());
//		dto.setBillTypes(new String[]{"TEST"});
//		
//		list=this.billPaymentService.queryBillPaymentByCondition(dto);
//		
//		logger.info(" 按查询条件进行查询： "+list.size());
	}
}
