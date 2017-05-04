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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODOnlineResultServiceTest.java
 * 
 * FILE NAME        	: BillPayCODOnlineResultServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillCODStateService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineResultService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 代收货款线上汇款结果服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-8 下午6:58:12
 */
public class BillPayCODOnlineResultServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillPickupService waybillPickupService;

	@Autowired
	private IBillCODStateService billCODStateService;

	@Autowired
	private IBillPayCODOnlineService billPayCODOnlineService;

	@Autowired
	private IBillPayCODOnlineResultService billPayCODOnlineResultService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillPaymentService billPaymentService;

	@Autowired
	private IBillWriteoffService billWriteoffService;

	@Autowired
	private ICodCommonService codCommonService;
	/*
	@Test
	public void testProcessPayCODResultSuccessService() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		// 设置签收时间
//		List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndOrgCodes(Arrays.asList(new String[]{entity.getWaybillNo()}),null, FossConstants.ACTIVE ,ConsumerTestUtil.getCurrentInfo());
//		BillPayableEntity billPayableEntity = list.get(0);
//		billPayableEntity.setSignDate(new Date());
//		billPayableService.updateBillPayableSignDateByConfirmIncome(list.get(0),  ConsumerTestUtil.getCurrentInfo());
		
		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

		// 发送代收货款到银企
//		String batchNo = billPayCODOnlineService.doWithSendBillPaybleToBank(entityIds,
//				ConsumerTestUtil.getCurrentInfo());

		// 生成付款单
		billPayCODOnlineService.generateCODPaymentBill(entity,
				ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(entity.getWaybillNo());

		// 应付单
		List<BillPayableEntity> billPayables = billPayableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.YES);

		Assert.assertNotNull(billPayables);
		Assert.assertEquals(billPayables.size(), 1);

		// 付款单
		List<String> payableBillNos = new ArrayList<String>();
		payableBillNos.add(billPayables.get(0).getPayableNo());
		List<BillPaymentEntity> billPayments = billPaymentService
				.queryBillPaymentBySourceBillNOs(
						payableBillNos,
						SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT,
						FossConstants.ACTIVE);

		Assert.assertNotNull(billPayments);
		Assert.assertEquals(billPayments.size(), 1);

		BillPaymentEntity billPayment = billPayments.get(0);
		Assert.assertEquals(billPayment.getBankBranchCode(),
				entity.getBankBranchCode());
		Assert.assertEquals(billPayment.getAmount(), billPayables.get(0)
				.getUnverifyAmount());

		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("046644");
		employee.setEmpName("zbw");
		user.setEmployee(employee);

		List<BillCODOnlineResultDto> results = new ArrayList<BillCODOnlineResultDto>();
		BillCODOnlineResultDto dto = new BillCODOnlineResultDto();
		dto.setWaybillNo(entity.getWaybillNo());
		dto.setResult(BillCODOnlineResultEnum.SUCCESS);
//		dto.setBatchNo(batchNo);
		results.add(dto);

		// 线上汇款成功
		billPayCODOnlineResultService.processPayResultService(results,
				ConsumerTestUtil.getCurrentInfo());

		List<BillWriteoffEntity> billWriteoffs = billWriteoffService
				.queryBillWriteoffByBeginOrEndNo(
						billPayment.getPaymentNo(),
						FossConstants.ACTIVE,
						SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		Assert.assertNotNull(billWriteoffs);
		Assert.assertEquals(billWriteoffs.size(), 1);

		BillWriteoffEntity billWriteoff = billWriteoffs.get(0);
		Assert.assertEquals(billWriteoff.getAmount(), billPayment.getAmount());
	}

	@Test
	public void testProcessPayCODResultFailureService() {

		WaybillPickupInfoDto waybillDto = ConsumerTestUtil.buildRandomWaybillDto();
		waybillPickupService.addWaybill(waybillDto,ConsumerTestUtil.getCurrentInfo());

		CODEntity entity = codCommonService.queryByWaybill(waybillDto
				.getWaybillNo());

		// 设置签收时间
//		List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndOrgCodes(Arrays.asList(new String[]{entity.getWaybillNo()}),null, FossConstants.ACTIVE ,ConsumerTestUtil.getCurrentInfo());
//		BillPayableEntity billPayableEntity = list.get(0);
//		billPayableEntity.setSignDate(new Date());
//		billPayableService.updateBillPayableSignDateByConfirmIncome(list.get(0),  ConsumerTestUtil.getCurrentInfo());
		
		List<String> entityIds = new ArrayList<String>();
		entityIds.add(entity.getId());

		billCODStateService.fundFreezeCOD(entityIds,
				ConsumerTestUtil.getCurrentInfo());

//		String batchNo = billPayCODOnlineService.doWithSendBillPaybleToBank(entityIds, ConsumerTestUtil.getCurrentInfo());

		// 生成付款单
		billPayCODOnlineService.generateCODPaymentBill(entity,
				ConsumerTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(entity.getWaybillNo());

		// 应付单
		List<BillPayableEntity> billPayables = billPayableService
				.queryBySourceBillNOs(
						waybillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.YES);

		Assert.assertNotNull(billPayables);
		Assert.assertEquals(billPayables.size(), 1);

		// 付款单
		List<String> payableBillNos = new ArrayList<String>();
		payableBillNos.add(billPayables.get(0).getPayableNo());
		List<BillPaymentEntity> billPayments = billPaymentService
				.queryBillPaymentBySourceBillNOs(
						payableBillNos,
						SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT,
						FossConstants.ACTIVE);

		Assert.assertNotNull(billPayments);
		Assert.assertEquals(billPayments.size(), 1);

		BillPaymentEntity billPayment = billPayments.get(0);
		Assert.assertEquals(billPayment.getBankBranchCode(),
				entity.getBankBranchCode());
		Assert.assertEquals(billPayment.getAmount(), billPayables.get(0)
				.getUnverifyAmount());

		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("046644");
		employee.setEmpName("zbw");
		user.setEmployee(employee);

		List<BillCODOnlineResultDto> results = new ArrayList<BillCODOnlineResultDto>();
		BillCODOnlineResultDto dto = new BillCODOnlineResultDto();
		dto.setWaybillNo(entity.getWaybillNo());
		dto.setResult(BillCODOnlineResultEnum.FAILURE);
		dto.setFailNotes("测试");
//		dto.setBatchNo(batchNo);
		results.add(dto);

		// 线上汇款成功
		billPayCODOnlineResultService.processPayResultService(results,
				ConsumerTestUtil.getCurrentInfo());

		billPayments = billPaymentService
				.queryBillPaymentBySourceBillNOs(
						payableBillNos,
						SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT,
						FossConstants.ACTIVE);

		Assert.assertNotNull(billPayments);
		Assert.assertEquals(billPayments.size(), 0);
	}
	*/
}
