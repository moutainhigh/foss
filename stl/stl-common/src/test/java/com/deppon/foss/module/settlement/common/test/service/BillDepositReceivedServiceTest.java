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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillDepositReceivedServiceTest.java
 * 
 * FILE NAME        	: BillDepositReceivedServiceTest.java
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

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillDepositReceivedEntityDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillDepositReceivedServiceTest extends BaseTestCase {

	@Autowired
	private IBillDepositReceivedService billDepositReceivedService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	// 根据传入的多个预收单号，获取预收单信息
	@Test
	@Rollback(true)
	public void testQueryByDepositReceivedNOs() {
		logger.trace("do...");

		// 正常查询
		List<String> depositReceivedNos = new ArrayList<String>();
		depositReceivedNos.add("10000001");
		depositReceivedNos.add("10000002");

		String active = FossConstants.ACTIVE;

		List<BillDepositReceivedEntity> listResult = billDepositReceivedService
				.queryByDepositReceivedNOs(depositReceivedNos, active);

		Assert.assertEquals(listResult.size(), 2);

	}

	@Test
	@Rollback(false)
	// 根据传入的一个预收单号，获取一条预收单信息
	public void testQueryByDepositReceivedNO() {
		logger.trace("do...");

		String depositReceivedNo = "10000001";
		String active = FossConstants.ACTIVE;

		BillDepositReceivedEntity entity = billDepositReceivedService
				.queryByDepositReceivedNo(depositReceivedNo, active);

		Assert.assertNotNull(entity);

	}

	// 核销时修改预收单的金额等字段信息
	@Test
	@Rollback(true)
	public void testUpdateBillDepositReceivedByWriteOff() {

		// String id = "1";
		// BigDecimal writeOffAmount = new BigDecimal("50");
		//
		// CurrentInfo info = CommonTestUtil.getCurrentInfo();
		//
		// int i = billDepositReceivedService.writeoffBillDepositReceived(id,
		// writeOffAmount, info);
		//
		// Assert.assertEquals(i, 1);
	}

	// 作废预收单
	@Test
	@Rollback(true)
	public void testdisableBillDepositReceived() {

		BillDepositReceivedEntity entity = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		billDepositReceivedService.addBillDepositReceived(entity, info);

		try {
			billDepositReceivedService.disableBillDepositReceived(entity, info);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}

	}

	// 修改预收单支付状态
	@Test
	@Rollback(true)
	public void testpayForBillDepositReceived() {

		BillDepositReceivedEntity entity = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		billDepositReceivedService.addBillDepositReceived(entity, info);

		BillDepositReceivedEntity entity1 = billDepositReceivedService
				.queryByDepositReceivedNo(entity.getDepositReceivedNo(),
						FossConstants.ACTIVE);

		entity1.setPaymentNotes("ceshi");
		entity1.setPaymentAmount(BigDecimal.valueOf(99));
		entity1.setPaymentNo("FK99999999");

		try {
			billDepositReceivedService.payForBillDepositReceived(entity1, info);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}
	}

	// 取消修改预收单支付状态
	@Test
	@Rollback(true)
	public void testcancelPayForBillDepositReceived() {

		BillDepositReceivedEntity entity = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		billDepositReceivedService.addBillDepositReceived(entity, info);

		BillDepositReceivedEntity entity1 = billDepositReceivedService
				.queryByDepositReceivedNo(entity.getDepositReceivedNo(),
						FossConstants.ACTIVE);

		entity1.setPaymentNotes("ceshi");
		entity1.setPaymentAmount(BigDecimal.valueOf(99));
		entity1.setPaymentNo("FK99999999");

		try {
			billDepositReceivedService.payForBillDepositReceived(entity1, info);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}
	}

	// 根据付款单号查询预收单
	@Test
	@Rollback(true)
	public void testqueryByPaymentNo() {

		String paymentNo = "FK99999900";

		BillDepositReceivedEntity entity = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		billDepositReceivedService.addBillDepositReceived(entity, info);

		List<BillDepositReceivedEntity> list = billDepositReceivedService
				.queryByPaymentNo(paymentNo);

		Assert.assertNotNull(list);

	}

	// 批量修改应收单的对账单号及是否生成对账单字段值
	@Test
	@Rollback(true)
	public void testbatchUpdateBillDepositReceivedByMakeStatement() {

		// 正常查询
		List<String> depositReceivedNos = new ArrayList<String>();
		BillDepositReceivedEntity entity1 = add();
		BillDepositReceivedEntity entity2 = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		billDepositReceivedService.addBillDepositReceived(entity1, info);
		billDepositReceivedService.addBillDepositReceived(entity2, info);

		depositReceivedNos.add(entity1.getDepositReceivedNo());
		depositReceivedNos.add(entity2.getDepositReceivedNo());
		String active = FossConstants.ACTIVE;

		List<BillDepositReceivedEntity> listResult = billDepositReceivedService
				.queryByDepositReceivedNOs(depositReceivedNos, active);
		
		BillDepositReceivedEntityDto dto = new BillDepositReceivedEntityDto();
		dto.setBillDepositReceivedEntityList(listResult);
		dto.setStatementBillNo("DZ000000CS");
		
		if(listResult.size()>0){
			billDepositReceivedService.batchUpdateBillDepositReceivedByMakeStatement(dto, info);
		}
		
	}

	/**
	 * 新增预收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 上午11:43:19
	 */
	@Test
	@Rollback(true)
	public void addDepositReceivedTest() {

		// 创建数据
		BillDepositReceivedEntity entity = add();
		CurrentInfo info = CommonTestUtil.getCurrentInfo();

		int i = billDepositReceivedService.addBillDepositReceived(entity, info);

		Assert.assertEquals(i, 1);

	}

	/**
	 * 创建数据
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-12 上午11:58:31
	 */
	private BillDepositReceivedEntity add() {

		// 模拟数据
		BillDepositReceivedEntity entity = new BillDepositReceivedEntity();

		entity.setId(UUIDUtils.getUUID());
		entity.setDepositReceivedNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.UF));
		entity.setCustomerCode("CUST1");
		entity.setCustomerName("客户1");
		entity.setAmount(new BigDecimal("100"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(new BigDecimal("100"));
		entity.setCreateOrgCode("GS00002");
		entity.setCreateOrgName("GS00002");
		entity.setCollectionOrgCode("GS00002");
		entity.setCollectionOrgName("GS00002");
		entity.setCollectionCompanyCode("GS00002");
		entity.setCollectionCompanyName("GS00002");
		entity.setGeneratingOrgCode("GS00002");
		entity.setGeneratingOrgName("GS00002");
		entity.setGeneratingCompanyCode("GS00002");
		entity.setGeneratingCompanyName("GS00002");
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
		entity.setAccountDate(new Date(0));
		entity.setIsInit("N");
		entity.setStatus("TT");
		entity.setPaymentNo("FK99999900");

		return entity;
	}

}
