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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/BillBadAccountWriteoffServiceTest.java
 * 
 * FILE NAME        	: BillBadAccountWriteoffServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillBadWriteoffDto;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillBadAccountWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillBadAccountQueryDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillBadAccountWriteoffServiceTest extends BaseTestCase {

	@Autowired
	private IBillBadAccountWriteoffService billBadAccountWriteoffService;

	@Autowired
	private IBillReceivableService billReceivableService;

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
				StlWebTestUtil.getCurrentInfo());
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
				StlWebTestUtil.getCurrentInfo());
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
				StlWebTestUtil.getCurrentInfo());
		list.add(entity3);

		return list;
	}

	private BillBadWriteoffDto getBadWriteoffDto() {
		BillBadWriteoffDto dto = new BillBadWriteoffDto();
		dto.setSerialNo("testserialno");
		dto.setWriteoffAmount(BigDecimal.valueOf(2000));
		List<BillReceivableEntity> list = this.getBillReceivableEntityList();
		List<String> recNos = new ArrayList<String>();
		for (BillReceivableEntity entity : list) {
			recNos.add(entity.getReceivableNo());
		}
		dto.setReceivableNos(recNos);
		return dto;
	}

	@Test
	@Rollback(true)
	public void testWriteoffBadAccount() {
		logger.info("do write off depostie and receivable...");

		BillBadWriteoffDto badWriteoffDto = this.getBadWriteoffDto();
		billBadAccountWriteoffService.writeoffBadAccount(badWriteoffDto);

		logger.info("write off depostie and receivable success...");
	}

	@Test
	@Rollback(true)
	public void testQueryBadAccountReceiableList() {
		logger.info("do write off depostie and receivable...");

		List<BillReceivableEntity> list = this.getBillReceivableEntityList();
		BillBadAccountQueryDto dto = new BillBadAccountQueryDto();
		dto.setCustomerCode(list.get(0).getCustomerCode());
		dto.setBeginTime(DateUtils.convert("2012-10-01", DateUtils.DATE_FORMAT));
		dto.setEndTime(DateUtils.convert("2012-10-30", DateUtils.DATE_FORMAT));
		List<String> wayBillNos = new ArrayList<String>();
		wayBillNos.add("YDTEST0000001");
		wayBillNos.add("YDTEST0000002");
		wayBillNos.add("YDTEST0000003");
		dto.setWayBillNum(wayBillNos);

		dto.setQueryType(SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__CUSTOMER);
		billBadAccountWriteoffService.queryBadAccountReceivableList(dto);

		dto.setQueryType(SettlementConstants.BAD_ACCOUNT__QUERY_TYPE__WAYBILL);
		billBadAccountWriteoffService.queryBadAccountReceivableList(dto);

		logger.info("write off depostie and receivable success...");
	}

}
