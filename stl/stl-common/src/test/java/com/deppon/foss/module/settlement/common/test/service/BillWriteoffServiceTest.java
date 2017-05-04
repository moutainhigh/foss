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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillWriteoffServiceTest.java
 * 
 * FILE NAME        	: BillWriteoffServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillWriteoffServiceTest extends BaseTestCase {

	@Autowired
	private IBillWriteoffService billWriteoffService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillDepositReceivedService billDepositReceivedService;

	private List<BillDepositReceivedEntity> getBillDepostReceiveEntityList() {
		List<BillDepositReceivedEntity> list = new ArrayList<BillDepositReceivedEntity>();

		BillDepositReceivedEntity entity1 = new BillDepositReceivedEntity();
		entity1.setId(UUIDUtils.getUUID());
		entity1.setDepositReceivedNo("USTEST000001");
		entity1.setCustomerCode("customercode1");
		entity1.setCustomerName("customername1");
		entity1.setAccountDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setUnverifyAmount(new BigDecimal("1200.16"));
		entity1.setVerifyAmount(new BigDecimal("1300"));
		entity1.setAmount(entity1.getVerifyAmount().add(
				entity1.getUnverifyAmount()));
		entity1.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED);
		entity1.setBusinessDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setCreateTime(new Date());
		entity1.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity1.setActive(FossConstants.ACTIVE);
		entity1.setIsRedBack(FossConstants.NO);
		entity1.setIsInit(FossConstants.NO);
		entity1.setVersionNo(FossConstants.INIT_VERSION_NO);

		billDepositReceivedService.addBillDepositReceived(entity1,
				CommonTestUtil.getCurrentInfo());
		list.add(entity1);

		BillDepositReceivedEntity entity2 = new BillDepositReceivedEntity();
		entity2.setId(UUIDUtils.getUUID());
		entity2.setDepositReceivedNo("USTEST00002");
		entity2.setCustomerCode("customercode2");
		entity2.setCustomerName("customername2");
		entity2.setAccountDate(DateUtils.convert("2012-10-02",
				DateUtils.DATE_FORMAT));
		entity2.setUnverifyAmount(new BigDecimal("560.01"));
		entity2.setVerifyAmount(new BigDecimal("800"));
		entity2.setAmount(entity2.getVerifyAmount().add(
				entity2.getUnverifyAmount()));
		entity2.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED);
		entity2.setBusinessDate(DateUtils.convert("2012-10-02",
				DateUtils.DATE_FORMAT));
		entity2.setCreateTime(new Date());
		entity2.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity2.setActive(FossConstants.ACTIVE);
		entity2.setIsRedBack(FossConstants.NO);
		entity2.setIsInit(FossConstants.NO);
		entity2.setVersionNo(FossConstants.INIT_VERSION_NO);

		billDepositReceivedService.addBillDepositReceived(entity2,
				CommonTestUtil.getCurrentInfo());
		list.add(entity2);

		BillDepositReceivedEntity entity3 = new BillDepositReceivedEntity();
		entity3.setId(UUIDUtils.getUUID());
		entity3.setDepositReceivedNo("USTEST00003");
		entity3.setCustomerCode("customercode3");
		entity3.setCustomerName("customername3");
		entity3.setAccountDate(DateUtils.convert("2012-10-08",
				DateUtils.DATE_FORMAT));
		entity3.setUnverifyAmount(new BigDecimal("890"));
		entity3.setVerifyAmount(new BigDecimal("2100"));
		entity3.setAmount(entity3.getVerifyAmount().add(
				entity3.getUnverifyAmount()));
		entity3.setBillType(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__BILL_TYPE__DEPOSIT_RECEIVED);
		entity3.setBusinessDate(DateUtils.convert("2012-10-08",
				DateUtils.DATE_FORMAT));
		entity3.setCreateTime(new Date());
		entity3.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity3.setActive(FossConstants.ACTIVE);
		entity3.setIsRedBack(FossConstants.NO);
		entity3.setIsInit(FossConstants.NO);
		entity3.setVersionNo(FossConstants.INIT_VERSION_NO);

		billDepositReceivedService.addBillDepositReceived(entity3,
				CommonTestUtil.getCurrentInfo());
		list.add(entity3);

		return list;
	}

	private List<BillReceivableEntity> getBillReceivableEntityList() {
		List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();

		BillReceivableEntity entity1 = new BillReceivableEntity();
		entity1.setId(UUIDUtils.getUUID());
		entity1.setReceivableNo("YSTEST00001");
		entity1.setCustomerCode("customercode1");
		entity1.setCustomerName("customername1");
		entity1.setAccountDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setUnverifyAmount(new BigDecimal("700.16"));
		entity1.setVerifyAmount(new BigDecimal("1600"));
		entity1.setBusinessDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity1.setWaybillNo("YDTEST0000001");
		entity1.setWaybillId(UUIDUtils.getUUID());
		entity1.setSourceBillNo("YDTEST0000001");
		entity1.setValueAddFee(entity1.getUnverifyAmount().add(
				entity1.getVerifyAmount()));
		entity1.setOtherFee(entity1.getValueAddFee());
		entity1.setAmount(entity1.getValueAddFee());
		entity1.setPromotionsFee(BigDecimal.ZERO);
		entity1.setTransportFee(BigDecimal.ZERO);
		entity1.setReceivableOrgCode("testOrgCode");
		entity1.setReceivableOrgName("testOrgName");
		entity1.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		entity1.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		entity1.setProductCode("TMP");
		entity1.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity1.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// entity1.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		entity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity1.setActive(FossConstants.ACTIVE);
		entity1.setIsRedBack(FossConstants.NO);
		entity1.setIsInit(FossConstants.NO);

		billReceivableService.addBillReceivable(entity1,
				CommonTestUtil.getCurrentInfo());
		list.add(entity1);

		BillReceivableEntity entity2 = new BillReceivableEntity();
		entity2.setId(UUIDUtils.getUUID());
		entity2.setReceivableNo("YSTEST00002");
		entity2.setCustomerCode("customercode2");
		entity2.setCustomerName("customername2");
		entity2.setAccountDate(DateUtils.convert("2012-10-04",
				DateUtils.DATE_FORMAT));
		entity2.setUnverifyAmount(new BigDecimal("1500.16"));
		entity2.setVerifyAmount(new BigDecimal("900"));
		entity2.setBusinessDate(DateUtils.convert("2012-10-04",
				DateUtils.DATE_FORMAT));
		entity2.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity2.setWaybillNo("YDTEST0000002");
		entity2.setWaybillId(UUIDUtils.getUUID());
		entity2.setSourceBillNo("YDTEST0000002");
		entity2.setValueAddFee(entity2.getUnverifyAmount().add(
				entity2.getVerifyAmount()));
		entity2.setOtherFee(entity2.getValueAddFee());
		entity2.setAmount(entity2.getValueAddFee());
		entity2.setPromotionsFee(BigDecimal.ZERO);
		entity2.setTransportFee(BigDecimal.ZERO);
		entity2.setReceivableOrgCode("testOrgCode");
		entity2.setReceivableOrgName("testOrgName");
		entity2.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		entity2.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		entity2.setProductCode("TMP");
		entity2.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity2.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// entity2.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		entity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity2.setActive(FossConstants.ACTIVE);
		entity2.setIsRedBack(FossConstants.NO);
		entity2.setIsInit(FossConstants.NO);

		billReceivableService.addBillReceivable(entity2,
				CommonTestUtil.getCurrentInfo());
		list.add(entity2);

		BillReceivableEntity entity3 = new BillReceivableEntity();
		entity3.setId(UUIDUtils.getUUID());
		entity3.setReceivableNo("YSTEST00003");
		entity3.setCustomerCode("customercode3");
		entity3.setCustomerName("customername3");
		entity3.setAccountDate(DateUtils.convert("2012-10-03",
				DateUtils.DATE_FORMAT));
		entity3.setUnverifyAmount(new BigDecimal("300.16"));
		entity3.setVerifyAmount(new BigDecimal("2900"));
		entity3.setBusinessDate(DateUtils.convert("2012-10-03",
				DateUtils.DATE_FORMAT));
		entity3.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity3.setWaybillNo("YDTEST0000003");
		entity3.setWaybillId(UUIDUtils.getUUID());
		entity3.setSourceBillNo("YDTEST0000003");
		entity3.setValueAddFee(entity3.getUnverifyAmount().add(
				entity3.getVerifyAmount()));
		entity3.setOtherFee(entity3.getValueAddFee());
		entity3.setAmount(entity3.getValueAddFee());
		entity3.setPromotionsFee(BigDecimal.ZERO);
		entity3.setTransportFee(BigDecimal.ZERO);
		entity3.setReceivableOrgCode("testOrgCode");
		entity3.setReceivableOrgName("testOrgName");
		entity3.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE);
		entity3.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		entity3.setProductCode("TMP");
		entity3.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity3.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// entity3.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		entity3.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity3.setActive(FossConstants.ACTIVE);
		entity3.setIsRedBack(FossConstants.NO);
		entity3.setIsInit(FossConstants.NO);

		billReceivableService.addBillReceivable(entity3,
				CommonTestUtil.getCurrentInfo());
		list.add(entity3);

		return list;
	}

	private List<BillPayableEntity> getBillPayableEntityList() {
		List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();

		BillPayableEntity entity1 = new BillPayableEntity();
		entity1.setId(UUIDUtils.getUUID());
		entity1.setPayableNo("YFTEST00001");
		entity1.setCustomerCode("customercode1");
		entity1.setCustomerName("customername1");
		entity1.setPayableOrgCode("orgcode1");
		entity1.setPayableOrgName("orgname1");
		entity1.setAccountDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setUnverifyAmount(new BigDecimal("725.16"));
		entity1.setVerifyAmount(new BigDecimal("1600"));
		entity1.setBusinessDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity1.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity1.setWaybillNo("YDTEST0000001");
		entity1.setWaybillId(UUIDUtils.getUUID());
		entity1.setSourceBillNo("YDTEST0000001");
		entity1.setAmount(entity1.getUnverifyAmount().add(
				entity1.getVerifyAmount()));
		entity1.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE);
		entity1.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL);
		entity1.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity1.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity1.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity1.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity1.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity1.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity1.setActive(FossConstants.ACTIVE);
		entity1.setIsRedBack(FossConstants.NO);
		entity1.setIsInit(FossConstants.NO);

		billPayableService.addBillPayable(entity1,
				CommonTestUtil.getCurrentInfo());
		list.add(entity1);

		BillPayableEntity entity2 = new BillPayableEntity();
		entity2.setId(UUIDUtils.getUUID());
		entity2.setPayableNo("YFTEST00002");
		entity2.setCustomerCode("customercode2");
		entity2.setCustomerName("customername2");
		entity2.setPayableOrgCode("orgcode2");
		entity2.setPayableOrgName("orgname2");
		entity2.setAccountDate(DateUtils.convert("2012-10-02",
				DateUtils.DATE_FORMAT));
		entity2.setUnverifyAmount(new BigDecimal("1300.16"));
		entity2.setVerifyAmount(new BigDecimal("900"));
		entity2.setBusinessDate(DateUtils.convert("2012-10-02",
				DateUtils.DATE_FORMAT));
		entity2.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity2.setWaybillNo("YDTEST0000002");
		entity2.setWaybillId(UUIDUtils.getUUID());
		entity2.setSourceBillNo("YDTEST0000002");
		entity2.setAmount(entity2.getUnverifyAmount().add(
				entity2.getVerifyAmount()));
		entity2.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE);
		entity2.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL);
		entity2.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity2.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity2.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity2.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity2.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity2.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity2.setActive(FossConstants.ACTIVE);
		entity2.setIsRedBack(FossConstants.NO);
		entity2.setIsInit(FossConstants.NO);

		billPayableService.addBillPayable(entity2,
				CommonTestUtil.getCurrentInfo());
		list.add(entity2);

		BillPayableEntity entity3 = new BillPayableEntity();
		entity3.setId(UUIDUtils.getUUID());
		entity3.setPayableNo("YFTEST00003");
		entity3.setCustomerCode("customercode3");
		entity3.setCustomerName("customername3");
		entity3.setPayableOrgCode("orgcode3");
		entity3.setPayableOrgName("orgname3");
		entity3.setAccountDate(DateUtils.convert("2012-10-01",
				DateUtils.DATE_FORMAT));
		entity3.setUnverifyAmount(new BigDecimal("500.16"));
		entity3.setVerifyAmount(new BigDecimal("2900"));
		entity3.setBusinessDate(DateUtils.convert("2012-10-01",
				DateUtils.DATE_FORMAT));
		entity3.setVersionNo(FossConstants.INIT_VERSION_NO);

		entity3.setWaybillNo("YDTEST0000003");
		entity3.setWaybillId(UUIDUtils.getUUID());
		entity3.setSourceBillNo("YDTEST0000003");
		entity3.setAmount(entity3.getUnverifyAmount().add(
				entity3.getVerifyAmount()));
		entity3.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST);
		entity3.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL);
		entity3.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		entity3.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity3.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity3.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity3.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity3.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity3.setActive(FossConstants.ACTIVE);
		entity3.setIsRedBack(FossConstants.NO);
		entity3.setIsInit(FossConstants.NO);

		billPayableService.addBillPayable(entity3,
				CommonTestUtil.getCurrentInfo());
		list.add(entity3);

		return list;
	}

	private List<BillAdvancedPaymentEntity> getBillAdvancedEntityList() {
		List<BillAdvancedPaymentEntity> list = new ArrayList<BillAdvancedPaymentEntity>();

		BillAdvancedPaymentEntity entity1 = new BillAdvancedPaymentEntity();
		entity1.setId(UUIDUtils.getUUID());
		entity1.setAdvancesNo("UFTEST00001");
		entity1.setCustomerCode("customercode1");
		entity1.setCustomerName("customername1");
		entity1.setAccountDate(DateUtils.convert("2012-10-11",
				DateUtils.DATE_FORMAT));
		entity1.setUnverifyAmount(new BigDecimal("390.16"));
		entity1.setVerifyAmount(new BigDecimal("1600"));
		entity1.setBusinessDate(DateUtils.convert("2012-10-11",
				DateUtils.DATE_FORMAT));
		entity1.setVersionNo(FossConstants.INIT_VERSION_NO);
		list.add(entity1);

		BillAdvancedPaymentEntity entity2 = new BillAdvancedPaymentEntity();
		entity2.setId(UUIDUtils.getUUID());
		entity2.setAdvancesNo("UFTEST00002");
		entity2.setCustomerCode("customercode2");
		entity2.setCustomerName("customername2");
		entity2.setAccountDate(DateUtils.convert("2012-10-08",
				DateUtils.DATE_FORMAT));
		entity2.setUnverifyAmount(new BigDecimal("900.16"));
		entity2.setVerifyAmount(new BigDecimal("900"));
		entity2.setBusinessDate(DateUtils.convert("2012-10-08",
				DateUtils.DATE_FORMAT));
		entity2.setVersionNo(FossConstants.INIT_VERSION_NO);
		list.add(entity2);

		BillAdvancedPaymentEntity entity3 = new BillAdvancedPaymentEntity();
		entity3.setId(UUIDUtils.getUUID());
		entity3.setAdvancesNo("UFTEST00003");
		entity3.setCustomerCode("customercode3");
		entity3.setCustomerName("customername3");
		entity3.setAccountDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity3.setUnverifyAmount(new BigDecimal("1340.16"));
		entity3.setVerifyAmount(new BigDecimal("2900"));
		entity3.setBusinessDate(DateUtils.convert("2012-10-05",
				DateUtils.DATE_FORMAT));
		entity3.setVersionNo(FossConstants.INIT_VERSION_NO);
		list.add(entity3);

		return list;
	}

	private BillRepaymentEntity getBillRepaymentEntity() {
		BillRepaymentEntity entity = new BillRepaymentEntity();

		entity.setRepaymentNo("HKTEST00001");
		entity.setCustomerCode("customercode1");
		entity.setCustomerName("customername1");
		entity.setAmount(new BigDecimal("1234"));

		return entity;
	}

	private BillWriteoffOperationDto getBillWriteoffOperationDto() {
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();

		billWriteoffOperationDto.setBillReceivableEntitys(this
				.getBillReceivableEntityList());
		billWriteoffOperationDto.setBillDepositReceivedEntitys(this
				.getBillDepostReceiveEntityList());
		billWriteoffOperationDto.setBillPayableEntitys(this
				.getBillPayableEntityList());
		billWriteoffOperationDto.setBillAdvancedPaymentEntitys(this
				.getBillAdvancedEntityList());
		billWriteoffOperationDto.setBillRepaymentEntity(this
				.getBillRepaymentEntity());

		String batchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		billWriteoffOperationDto.setWriteoffBatchNo(batchNo);
		billWriteoffOperationDto.setStatementBillNo("statementno");
		billWriteoffOperationDto
				.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		return billWriteoffOperationDto;
	}

	@Test
	@Rollback(true)
	public void testWriteoffDepositAndReceivable() {
		logger.info("do write off depostie and receivable...");

		BillWriteoffOperationDto billWriteoffOperationDto = this
				.getBillWriteoffOperationDto();
		billWriteoffService.writeoffDepositAndReceivable(
				billWriteoffOperationDto, CommonTestUtil.getCurrentInfo());

		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testWriteoffReceivableAndPayable() {
		logger.info("do write off depostie and receivable...");

		BillWriteoffOperationDto billWriteoffOperationDto = this
				.getBillWriteoffOperationDto();
		billWriteoffService.writeoffReceibableAndPayable(
				billWriteoffOperationDto, CommonTestUtil.getCurrentInfo());

		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testWriteoffAdvancedAndPayable() {
		logger.info("do write off depostie and receivable...");

		BillWriteoffOperationDto billWriteoffOperationDto = this
				.getBillWriteoffOperationDto();
		billWriteoffService.writeoffAdvancedAndPayable(
				billWriteoffOperationDto, CommonTestUtil.getCurrentInfo());

		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testWriteoffRepaymentAndReceibable() {
		logger.info("do write off depostie and receivable...");

		BillWriteoffOperationDto billWriteoffOperationDto = this
				.getBillWriteoffOperationDto();
		billWriteoffService.writeoffRepaymentAndReceibable(
				billWriteoffOperationDto, CommonTestUtil.getCurrentInfo());

		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testQueryBillWriteoffByBeginOrEndNo() {
		logger.info("do write off depostie and receivable...");

		List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffService
				.queryBillWriteoffByBeginOrEndNo(
						"YS200000300",
						FossConstants.ACTIVE,
						SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

		logger.info("resule size : " + billWriteoffEntitys.size());
		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testQueryByRepayment() {
		billWriteoffService.queryByRepayment("123", FossConstants.ACTIVE);
	}

	@Test
	@Rollback(true)
	public void testWriteBackByRecNoAndPayNo() {
		billWriteoffService.writeBackByRecNoAndPayNo("123", "123", 
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	@Ignore
	public void testQueryHandWriteoffReceivableByWaybillNo(){
		List<BillWriteoffEntity> bills = billWriteoffService.queryHandWriteoffReceivableByWaybillNo("555504135");
		System.out.println(CollectionUtils.isEmpty(bills));
	}
}
