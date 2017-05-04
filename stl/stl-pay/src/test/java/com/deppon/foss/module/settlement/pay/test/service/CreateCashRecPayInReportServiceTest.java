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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/CreateCashRecPayInReportServiceTest.java
 * 
 * FILE NAME        	: CreateCashRecPayInReportServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.server.service.impl.BillDepositReceivedService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 生成现金收入报表
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-30 上午11:21:27
 */
public class CreateCashRecPayInReportServiceTest extends BaseTestCase {
	@Autowired
	private IReportCashRecPayInService reportCashRecPayInService;

	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IBillCashCollectionService billCashCollectionService;
	@Autowired
	private IBillRepaymentService billRepaymentService;
	@Autowired
	private IBillDepositReceivedService billDepositReceivedService;
	private static final Logger logger = LogManager
			.getLogger(BillDepositReceivedService.class);

	/**
	 * 
	 * 生成现金收款单测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	private void getBillCashCollectionEntity() {

		BillCashCollectionEntity entity = new BillCashCollectionEntity();

		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.XS1);
		entity.setCashCollectionNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(DateUtils.addDays(now, 10));
		entity.setBusinessDate(now);
		entity.setStatus("STATUS");
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION);
		entity.setSourceBillNo("12345678");
		entity.setProductCode("PRODUCT");

		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);

		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setCreateOrgCode("ORG");
		entity.setCreateOrgName("部门");
		entity.setGeneratingOrgCode("ORG");
		entity.setGeneratingOrgName("部门");
		entity.setCollectionOrgCode("ORG");
		entity.setCollectionOrgName("部门");
		entity.setAmount(NumberUtils.createBigDecimal("100.00"));
		entity.setTransportFee(NumberUtils.createBigDecimal("100.00")); // 公布价运费
		entity.setPickupFee(BigDecimal.ZERO); // 接货费
		entity.setDeliveryGoodsFee(BigDecimal.ZERO); // 送货费
		entity.setPackagingFee(BigDecimal.ZERO); // 包装手续费
		entity.setCodFee(BigDecimal.ZERO); // 代收货款费
		entity.setInsuranceFee(BigDecimal.ZERO); // 保价费
		entity.setOtherFee(BigDecimal.ZERO); // 其他费用
		entity.setValueAddFee(BigDecimal.ZERO); // 增值费用
		entity.setPromotionsFee(BigDecimal.ZERO); // 优惠费用
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setCollectionType("会员费");

		entity.setOrigOrgCode("ch1");
		entity.setOrigOrgName("出发部门");

		entity.setDestOrgCode("DD1");// 到达部门
		entity.setDestOrgName("到达部门");// 到达部门
		billCashCollectionService.addBillCashCollection(entity,
				CommonTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 生成还款单测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	private void getBillRepaymentEntity() {
		BillRepaymentEntity entity = new BillRepaymentEntity();
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HK1);
		entity.setRepaymentNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(DateUtils.addDays(now, 10));
		entity.setBusinessDate(now);
		entity.setConrevenDate(now);
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		// TODO
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__CONFIRM);
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setOnlinePaymentNo("PAYMENT_NO");
		entity.setOaPaymentNo("OA_PAYMENT_NO");
		entity.setIsInit(FossConstants.NO);

		entity.setSourceBillNo("12345678");
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__WAYBILL);

		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setCreateOrgCode("ORG");
		entity.setCreateOrgName("部门");
		entity.setGeneratingOrgCode("ORG");
		entity.setGeneratingOrgName("部门");
		entity.setCollectionOrgCode("ORG");
		entity.setCollectionOrgName("部门");
		entity.setAmount(NumberUtils.createBigDecimal("101.53"));
		entity.setTrueAmount(NumberUtils.createBigDecimal("101.53"));
		entity.setBverifyAmount(BigDecimal.ZERO);
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setSourceBillType("PL");// 外发单
		billRepaymentService.addBillRepayment(entity,
				CommonTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 生成预收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	private void getBillDepositReceivedEntity() {

		// 模拟数据
		BillDepositReceivedEntity entity = new BillDepositReceivedEntity();

		entity.setId(UUIDUtils.getUUID());
		entity.setDepositReceivedNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.UF));
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setAmount(new BigDecimal("100"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(new BigDecimal("100"));
		entity.setCreateOrgCode("ORG");
		entity.setCreateOrgName("ORG");
		entity.setCollectionOrgCode("ORG");
		entity.setCollectionOrgName("ORG");
		entity.setCollectionCompanyCode("ORG");
		entity.setCollectionCompanyName("ORG");
		entity.setGeneratingOrgCode("ORG");
		entity.setGeneratingOrgName("ORG");
		entity.setGeneratingCompanyCode("ORG");
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setGeneratingCompanyName("ORG");
		entity.setRefundStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__REFUND_STATUS__NOT_REFUND);
		entity.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(FossConstants.NO);
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		entity.setVersionNo(Short.valueOf("1"));
		entity.setCreateUserCode("000000");
		entity.setCreateUserName("000000");
		entity.setCreateTime(new Date());
		entity.setModifyUserCode("000000");
		entity.setModifyUserName("000000");
		entity.setModifyTime(new Date());

		entity.setBusinessDate(new Date());
		entity.setAccountDate(DateUtils.addDays(new Date(), 10));
		entity.setIsInit("N");
		entity.setStatus("TT");
		entity.setPaymentNo("FK99999900");
		int i = billDepositReceivedService.addBillDepositReceived(entity,
				CommonTestUtil.getCurrentInfo());

	}

	/**
	 * 生成现金收入报表测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	@Test
	@Ignore
	public void uploadAllReportCashRecPayInTest() {
		getBillCashCollectionEntity();
		getBillRepaymentEntity();
		getBillDepositReceivedEntity();
		List<CashCollectionRptEntity> list = reportCashRecPayInService
				.queryUploadCashAllAmount(com.deppon.foss.util.DateUtils
						.addDayToDate(new Date(), 9),
						com.deppon.foss.util.DateUtils.addDayToDate(new Date(),
								11));
		if (CollectionUtils.isNotEmpty(list)) {
			Assert.assertEquals(1, list.size());
		}

	}

	/**
	 * 
	 * 生成所有网点的现金收入报表
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-19 下午5:06:32
	 */
	@Test
	public void createAllReportCashRecPayInTest() {
		getBillCashCollectionEntity();
		getBillRepaymentEntity();
		getBillDepositReceivedEntity();
		reportCashRecPayInService.createAllReportCashRecPayIn(
				com.deppon.foss.util.DateUtils.addDayToDate(new Date(), 9),
				com.deppon.foss.util.DateUtils.addDayToDate(new Date(), 11));
	}

	/**
	 * 
	 * 生成单个网点的现金收入报表
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-12-19 下午5:06:32
	 */
	@Test
	public void createOneReportCashRecPayInTest(){
		getBillCashCollectionEntity();
		getBillRepaymentEntity();
		getBillDepositReceivedEntity();
		//reportCashRecPayInService.createOneReportCashRecPayIn(com.deppon.foss.util.DateUtils
		//				.addDayToDate(new Date(), 9), com.deppon.foss.util.DateUtils.addDayToDate(new Date(),11),"ORG",SettlementConstants.SYSTEM_USER_CODE,
		//				SettlementConstants.SYSTEM_USER_NAME,new Date());
	}
}
