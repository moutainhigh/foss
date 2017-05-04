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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillPayableServiceTest.java
 * 
 * FILE NAME        	: BillPayableServiceTest.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.esb.inteface.domain.payable.add.PayableBills;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 应付单服务测试类
 * 
 * @author ibm-zhuwei
 * @date 2012-10-19 上午9:34:49
 */
public class BillPayableServiceTest extends BaseTestCase {

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 理赔状态消息Service
	 */
	@Autowired
	private IClaimStatusMsgService claimStatusMsgService;
	
	/**
	 * 财务变更消息实体Service
	 */
	@Autowired
	private IWaybillChangeMsgService waybillChangeMsgService;

	@Before
	public void setUp() {
		// this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
		// this.executeSqlScript("classpath:com/deppon/foss/module/settlement/common/test/META-INF/sql/bill_cash_collection.sql",
		// true);
	}

	@After
	public void tearDown() {
		// this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
	}

	/**
	 * 获取应付单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:51:37
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	public BillPayableEntity getBillPayableEntity() {

		BillPayableEntity entity = new BillPayableEntity();

		String waybillNo = CommonTestUtil.getWaybillNO();
		String payableNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);

		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo(payableNo);
		entity.setWaybillNo(waybillNo);
		entity.setWaybillId(waybillNo);

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(billType);
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 整车尾款应付单-到达部门来付钱
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)) {
			entity.setPayableOrgCode("CUST_CODE");
		} else {
			entity.setPayableOrgCode("ORIG_CODE");
		}

		entity.setPayableOrgName("上海营业部");

		entity.setPayableComCode("GS1");
		entity.setPayableComName("上海德邦物流");

		// 设置出发部门编码、名称
		entity.setOrigOrgCode("CF1");
		entity.setOrigOrgName("上海青浦营业部");

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode("DEST1");
		entity.setDestOrgName("北京大营门营业部");

		// 设置应付客户编码、名称
		entity.setCustomerCode("YFKH");
		entity.setCustomerName("张三");
		entity.setCustomerContact("");
		entity.setCustomerContactName("");
		entity.setCustomerPhone("10000");

		// 设置金额、已核销金额、未核销金额
		entity.setAmount(NumberUtils.createBigDecimal("10000"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS");
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN");

		// 是否有效、是否红单、是否初始化、版本号
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setIsInit(FossConstants.NO);
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);

		return entity;
	}
	
	public BillPayableEntity getBillPayableEntityTwo(String billType) {

		BillPayableEntity entity = new BillPayableEntity();

		String waybillNo = CommonTestUtil.getWaybillNO();
		String payableNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);

		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo(payableNo);
		entity.setWaybillNo(waybillNo);
		entity.setWaybillId(waybillNo);

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(billType);
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE);

		// 整车尾款应付单-到达部门来付钱
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)) {
			entity.setPayableOrgCode("CUST_CODE");
		} else {
			entity.setPayableOrgCode("ORIG_CODE");
		}

		entity.setPayableOrgName("上海营业部");

		entity.setPayableComCode("GS1");
		entity.setPayableComName("上海德邦物流");

		// 设置出发部门编码、名称
		entity.setOrigOrgCode("CF1");
		entity.setOrigOrgName("上海青浦营业部");

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode("DEST1");
		entity.setDestOrgName("北京大营门营业部");

		// 设置应付客户编码、名称
		entity.setCustomerCode("YFKH");
		entity.setCustomerName("张三");
		entity.setCustomerContact("");
		entity.setCustomerContactName("");
		entity.setCustomerPhone("10000");

		// 设置金额、已核销金额、未核销金额
		entity.setAmount(NumberUtils.createBigDecimal("10000"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS");
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN");

		// 是否有效、是否红单、是否初始化、版本号
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setIsInit(FossConstants.NO);
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);

		return entity;
	}

	@Test
	@Rollback(true)
	public void testWriteoffBillPayable() {

		BillPayableEntity entity = getBillPayableEntity();
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
		billPayableService.writeoffBillPayable(entity,
				NumberUtils.createBigDecimal("30.50"),
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testEnableBillPayable() {

		BillPayableEntity entity = getBillPayableEntity();
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
		billPayableService.enableBillPayable(entity, new Date(),
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testDisableBillPayable() {
		BillPayableEntity entity = getBillPayableEntity();
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
		billPayableService.disableBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testWriteBackBackPayable() {
		BillPayableEntity entity = getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
		billPayableService.writeBackBillPayable(entity,
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testQueryBillPayableByCondition() {
		BillPayableConditionDto dto = new BillPayableConditionDto();
		dto.setWaybillNo("11282003");
		// dto.setSourceBillNo("400830003");
		// dto.setPayableNo("YF400830003");
		dto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE });

		List<BillPayableEntity> list = billPayableService
				.queryBillPayableByCondition(dto);
		logger.info("size:" + list.size());
	}

	/**
	 * 批量审核应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-1 上午8:45:35
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testBatchAuditBillPayable() {
		BillPayableConditionDto conDto = new BillPayableConditionDto();
		conDto.setWaybillNo("50321425");
		List<BillPayableEntity> lists = this.billPayableService
				.queryBillPayableByCondition(conDto);

		if (CollectionUtils.isNotEmpty(lists)) {
			BillPayableDto dto = new BillPayableDto();
			dto.setBillPayables(lists);
			dto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);// 已审核

			Date date = new Date();
			dto.setAuditDate(date);
			dto.setAuditUserCode("TODOID");
			dto.setAuditUserName("TODO");

			dto.setModifyTime(date);
			dto.setModifyUserCode("TODOMID");
			dto.setModifyUserCode("TODOM");
			try {
				this.billPayableService.batchAuditBillPayable(dto,
						CommonTestUtil.getCurrentInfo());
			} catch (SettlementException e) {
				logger.error(e.getErrorCode(),e);
			}
		}
	}

	/**
	 * 批量反审核应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:26:07
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testBatchReverseAuditBillPayable() {
		BillPayableConditionDto conDto = new BillPayableConditionDto();
		conDto.setWaybillNo("50321425");
		List<BillPayableEntity> lists = this.billPayableService
				.queryBillPayableByCondition(conDto);

		if (CollectionUtils.isNotEmpty(lists)) {
			BillPayableDto dto = new BillPayableDto();
			dto.setBillPayables(lists);
			dto.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);// 未审核
			Date date = new Date();
			dto.setAuditDate(null);
			dto.setAuditUserCode("");
			dto.setAuditUserName("");
			dto.setModifyTime(date);
			dto.setModifyUserCode("TODOMID");
			dto.setModifyUserCode("TODOM");
			try {
				this.billPayableService.batchReverseAuditBillPayable(
						dto, CommonTestUtil.getCurrentInfo());
			} catch (SettlementException e) {
				logger.error(e.getErrorCode(),e);
			}
		}
	}

	/**
	 * 冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:24:59
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testFrozenBillPayable() {
		BillPayableEntity entity = getBillPayableEntity();
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());

		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN);
		entity.setFrozenTime(new Date());
		entity.setFrozenUserCode("TODOID");
		entity.setFrozenUserName("TODONAME");
		try {
			this.billPayableService.frozenBillPayable(entity,
					CommonTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}

	}

	/**
	 * 取消冻结应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 下午6:24:59
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testCancelFrozenBillPayable() {
		BillPayableEntity entity = getBillPayableEntity();
		billPayableService.addBillPayable(entity,
				CommonTestUtil.getCurrentInfo());

		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setFrozenTime(null);
		entity.setFrozenUserCode(null);
		entity.setFrozenUserName(null);
		try {
			this.billPayableService.cancelFrozenBillPayable(entity,
					CommonTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 修改应付单的支付状态 和付款单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午2:26:12
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testPayForBillPayable() {
		try {
			BillPayableEntity entity = getBillPayableEntity();
			billPayableService.addBillPayable(entity,
					CommonTestUtil.getCurrentInfo());
			BillPayableEntity entityTwo = getBillPayableEntity();
			billPayableService.addBillPayable(entityTwo,
					CommonTestUtil.getCurrentInfo());

			BillPayableEntity entityThree = getBillPayableEntity();
			billPayableService.addBillPayable(entityThree,
					CommonTestUtil.getCurrentInfo());

			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(entity.getWaybillNo());
			sourceBillNos.add(entityTwo.getWaybillNo());
			sourceBillNos.add(entityThree.getWaybillNo());
			List<BillPayableEntity> list = this.billPayableService
					.queryBySourceBillNOs(
							sourceBillNos,
							SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
							FossConstants.ACTIVE);

			if (CollectionUtils.isNotEmpty(list)) {
				for (BillPayableEntity payableEntity : list) {
					payableEntity.setPaymentAmount(new BigDecimal("1000"));
					payableEntity.setPaymentNotes("测试");
					BillPayableDto payableDto = new BillPayableDto();
					org.springframework.beans.BeanUtils.copyProperties(payableEntity, payableDto);
					payableDto.setPaymentNo("FK123456");
					this.billPayableService.payForBillPayable(payableDto,
							CommonTestUtil.getCurrentInfo());
				}
			}
			
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}

	}

	/**
	 * 批量取消应付单的支付状态和付款单号和付款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-6 下午2:31:46
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testCancelPayForBillPayable() {
		
		try {
			BillPayableEntity entity = getBillPayableEntity();
			billPayableService.addBillPayable(entity,
					CommonTestUtil.getCurrentInfo());
			BillPayableEntity entityTwo = getBillPayableEntity();
			billPayableService.addBillPayable(entityTwo,
					CommonTestUtil.getCurrentInfo());

			BillPayableEntity entityThree = getBillPayableEntity();
			billPayableService.addBillPayable(entityThree,
					CommonTestUtil.getCurrentInfo());

			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(entity.getWaybillNo());
			sourceBillNos.add(entityTwo.getWaybillNo());
			sourceBillNos.add(entityThree.getWaybillNo());
			List<BillPayableEntity> list = this.billPayableService
					.queryBySourceBillNOs(
							sourceBillNos,
							SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
							FossConstants.ACTIVE);

			if (CollectionUtils.isNotEmpty(list)) {
				for (BillPayableEntity payableEntity : list) {
					payableEntity.setPaymentAmount(new BigDecimal("1000"));
					payableEntity.setPaymentNotes("测试");
					BillPayableDto payableDto = new BillPayableDto();
					org.springframework.beans.BeanUtils.copyProperties(payableEntity, payableDto);
					payableDto.setPaymentNo("FK123456");
					this.billPayableService.payForBillPayable(payableDto,
							CommonTestUtil.getCurrentInfo());
				}
				
				list = this.billPayableService
						.queryBySourceBillNOs(
								sourceBillNos,
								SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL,
								FossConstants.ACTIVE);
				for (BillPayableEntity payableEntity : list) {
					payableEntity.setPaymentAmount(new BigDecimal("1000"));
					payableEntity.setPaymentNotes("测试");
					BillPayableDto payableDto = new BillPayableDto();
					org.springframework.beans.BeanUtils.copyProperties(payableEntity, payableDto);
					payableDto.setPaymentNo("");
					payableDto.setPaymentAmount(null);
					payableEntity.setPaymentNotes("");
					this.billPayableService.cancelPayForBillPayable(payableDto,
							CommonTestUtil.getCurrentInfo());
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 签收时，不能生效应付单的情况下，修改代收货款应付单的签收日期
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午7:33:49
	 */
	@Test
	public void testUpdateBillPayableSignDateByConfirmIncome() {
		BillPayableConditionDto dto = new BillPayableConditionDto(
				"1352783220078");
		dto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE,
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST });
		List<BillPayableEntity> list = this.billPayableService
				.queryBillPayableByCondition(dto);
		CurrentInfo currentInfo = new CurrentInfo(null, null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (BillPayableEntity entity : list) {

				try {
					entity.setSignDate(new Date());
					this.billPayableService
							.updateBillPayableSignDateByConfirmIncome(entity,
									currentInfo);
				} catch (SettlementException e) {
					logger.error(e.getErrorCode(),e);
				}
			}
		}
	}

	/**
	 * 根据运单号集合和单据类型集合查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-23 上午8:01:22
	 */
	@Test
	@Rollback(true)
	public void testQueryByWaybillNosAndByBillTypes() {
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payable,
				CommonTestUtil.getCurrentInfo());

		BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableTwo,
				CommonTestUtil.getCurrentInfo());

		BillPayableEntity payableThree = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableThree,
				CommonTestUtil.getCurrentInfo());

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(payable.getWaybillNo());
		waybillNos.add(payableTwo.getWaybillNo());
		waybillNos.add(payableThree.getWaybillNo());

		List<String> billTypes = new ArrayList<String>();
		billTypes
				.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);

		List<BillPayableEntity> list = this.billPayableService 
				.queryByWaybillNosAndByBillTypes(waybillNos, billTypes);
		Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		
		BillPayableConditionDto dto=new BillPayableConditionDto();
		dto.setWaybillNo(payable.getWaybillNo());//运单号
		dto.setSourceBillNo(payable.getSourceBillNo());//来源单号
		list=this.billPayableService.queryBillPayableByCondition(dto);
		Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		
	}

	/**
	 * 查询应付单是否已核销
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:52:54
	 */
	@Test
	@Rollback(true)
	public void testQueryBillPayableIsWriteOff() {
		try{
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo());
			
			BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST);
			payableTwo.setSourceBillNo(payable.getSourceBillNo());
			this.billPayableService.addBillPayable(payableTwo,
					CommonTestUtil.getCurrentInfo());

			this.billPayableService.queryBillPayableIsWriteOff(
					payable.getSourceBillNo(),FossConstants.YES);
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 批量修改应付单的对账单单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:53:37
	 */
	@Test
	@Rollback(true)
	public void testBatchUpdateByMakeStatement(){
		try{
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo());
			
			BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payableTwo,
					CommonTestUtil.getCurrentInfo());
			
			BillPayableEntity payableThree = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payableThree,
					CommonTestUtil.getCurrentInfo());
			
			//对账单号
			String statementBillNo=CommonTestUtil.getStatementBillNo();
			BillPayableDto dto=new BillPayableDto();
			dto.setStatementBillNo(statementBillNo);//对账单号
			List<BillPayableEntity> list=new ArrayList<BillPayableEntity>();
			list.add(payable);
			list.add(payableTwo);
			list.add(payableThree);
			dto.setBillPayables(list);
			this.billPayableService.batchUpdateByMakeStatement(dto, CommonTestUtil.getCurrentInfo());
			List<String> payableNos=new ArrayList<String>();
			payableNos.add(payable.getPayableNo());
			payableNos.add(payableTwo.getPayableNo());
			payableNos.add(payableThree.getPayableNo());
			List<BillPayableEntity> lists=this.billPayableService.queryBySourceBillNOs(payableNos, "", FossConstants.ACTIVE);
			if(CollectionUtils.isNotEmpty(lists)){
				for(BillPayableEntity entity:lists){
					Assert.assertEquals(entity.getStatementBillNo(), statementBillNo);
				}
			}
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 保存应付单信息，查看是否添加了财务变更信息 +1
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午3:25:29
	 */
	@Test
	@Rollback(true)
	public void testAddPayableToWaybillChangeMsg(){
		try{
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo());
			
			List<WaybillChangeMsgEntity> list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
			for(WaybillChangeMsgEntity entity:list){
				Assert.assertEquals(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING 
						, entity.getMsgAction());
			}
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 红冲应付单信息，添加财务变更信息 -1
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午4:49:08
	 */
	@Test
	@Rollback(true)
	public void testWriteBackBillPayableToWaybillChangeMsg(){
		try{
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo()); //+1
			
			//红冲应付单信息
			this.billPayableService.writeBackBillPayable(payable, CommonTestUtil.getCurrentInfo()); //-1
			
			//查询该运单对应的-财务变更信息
			List<WaybillChangeMsgEntity> list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==2);
			
			int k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;
				}
				
			}
			Assert.assertEquals(k,30);
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 核销应付单信息，添加财务变更信息 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午5:03:19
	 */
	@Test
	@Rollback(true)
	public void testWriteoffBillPayableToWaybillChangeMsg(){
		try{
			//SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM 理赔应付单
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo()); //+1
			
			//设置应付单的数据全部核销
			
			payable.setUnverifyAmount(BigDecimal.ZERO);
			payable.setVerifyAmount(payable.getAmount());
			
			//核销操作-核销金额传入值为总金额
			this.billPayableService.writeoffBillPayable(payable, payable.getAmount(), CommonTestUtil.getCurrentInfo());
			
			//查询该运单对应的-财务变更信息
			List<WaybillChangeMsgEntity> list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==2);
			
			int k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;
				}
				
			}
			Assert.assertEquals(k,30);
			
			//保存的为理赔应付单---核销成功后发送理赔信息
			List<ClaimStatusMsgEntity> clList=this.claimStatusMsgService.queryClaimStatusMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(clList));
			for(ClaimStatusMsgEntity entity:clList){
				Assert.assertEquals(SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__PASS, entity.getMsgAction());
			}
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 反核销应付单信息，添加财务变更信息 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午5:03:19
	 */
	@Test
	@Rollback(true)
	public void testAntiWriteoffBillPayableToWaybillChangeMsg(){
		try{
			//SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM 理赔应付单
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo()); //+1
			
			//设置应付单的数据全部核销
			
			payable.setUnverifyAmount(BigDecimal.ZERO);
			payable.setVerifyAmount(payable.getAmount());
			
			//核销操作-核销金额传入值为总金额
			this.billPayableService.writeoffBillPayable(payable, payable.getAmount(), CommonTestUtil.getCurrentInfo());
			
			//查询该运单对应的-财务变更信息
			List<WaybillChangeMsgEntity> list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==2);
			
			int k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;//-1 的一次
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;//+1 的一次
				}
				
			}
			Assert.assertEquals(k,30);
			
			//保存的为理赔应付单---核销成功后发送理赔信息
			List<ClaimStatusMsgEntity> clList=this.claimStatusMsgService.queryClaimStatusMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(clList));
			for(ClaimStatusMsgEntity entity:clList){
				Assert.assertEquals(SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__PASS, entity.getMsgAction());
			}
			
			
			//调用反核销操作
			payable.setUnverifyAmount(payable.getAmount());
			payable.setVerifyAmount(BigDecimal.ZERO);
			
			//反核销操作-核销金额传入值为总金额  为负数
			this.billPayableService.writeoffBillPayable(payable, 
					payable.getAmount().multiply(new BigDecimal("-1")),
					//new BigDecimal("100"),
					CommonTestUtil.getCurrentInfo());
			
			list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==3);
			
			k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;//-1 的一次
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;//+1的两次
				}
				
			}
			Assert.assertEquals(k,50);

		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 反核销应付单信息，添加财务变更信息 
	 * 
	 * 反核销（未核销金额为0）的金额和传入的实体的未核销金额不相等的情况下，反核销时不生成财务变更信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午5:03:19
	 */
	@Test
	@Rollback(true)
	public void testAntiWriteoffBillPayableToWaybillChangeMsgTwo(){
		try{
			//SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM 理赔应付单
			BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
			this.billPayableService.addBillPayable(payable,
					CommonTestUtil.getCurrentInfo()); //+1
			
			//设置应付单的数据全部核销
			
			payable.setUnverifyAmount(BigDecimal.ZERO);
			payable.setVerifyAmount(payable.getAmount());
			
			//核销操作-核销金额传入值为总金额
			this.billPayableService.writeoffBillPayable(payable, payable.getAmount(), CommonTestUtil.getCurrentInfo());
			
			//查询该运单对应的-财务变更信息
			List<WaybillChangeMsgEntity> list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==2);
			
			int k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;//-1 的一次
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;//+1 的一次
				}
			}
			Assert.assertEquals(k,30);
			
			//保存的为理赔应付单---核销成功后发送理赔信息
			List<ClaimStatusMsgEntity> clList=this.claimStatusMsgService.queryClaimStatusMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(clList));
			for(ClaimStatusMsgEntity entity:clList){
				Assert.assertEquals(SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__PASS, entity.getMsgAction());
			}
			
			//调用反核销操作
			payable.setUnverifyAmount(payable.getAmount());//总金额
			payable.setVerifyAmount(BigDecimal.ZERO);
			
			//反核销操作-核销金额传入值为总金额  为负数
			this.billPayableService.writeoffBillPayable(payable, 
					new BigDecimal("-100"),//传入的反核销金额为负100
					CommonTestUtil.getCurrentInfo());
			
			list=this.waybillChangeMsgService.queryChangeMsgByWaybillNO(payable.getWaybillNo());
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)&&list.size()==2);
			
			k=0;
			for(WaybillChangeMsgEntity entity:list){
				if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE.equals(entity.getMsgAction())){
					k+=10;//-1 的一次
				}else if(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING.equals(entity.getMsgAction())){
					k+=20;//+1的两次
				}
			}
			Assert.assertEquals(k,30);
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	@Test
	public void testQueryByWaybillNosAndOrgCodes(){
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payable,
				CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity twoPayable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(twoPayable,
				CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity threePayable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(threePayable,
				CommonTestUtil.getCurrentInfo());
		
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add(payable.getWaybillNo());
		waybillNos.add(twoPayable.getWaybillNo());
		waybillNos.add(threePayable.getWaybillNo());
		
		List<String> orgCodes=new ArrayList<String>();
		orgCodes.add(payable.getPayableOrgCode());
		
//		this.billPayableService.queryByWaybillNosAndOrgCodes(waybillNos, orgCodes, "",CommonTestUtil.getCurrentInfo());
		
	}
	
	/**
	 * 批量生效应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午10:38:28
	 */
	@Test
	@Rollback(true)
	public void testUpdateByBatchTakeEffect(){
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payable,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableTwo,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableThree = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableThree,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableFour = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableFour,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableFive = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
		this.billPayableService.addBillPayable(payableFive,CommonTestUtil.getCurrentInfo());
		
		BillPayableDto dto=new BillPayableDto();
		List<BillPayableEntity> list=new ArrayList<BillPayableEntity>();
		list.add(payable);
		list.add(payableTwo);
		list.add(payableThree);
		list.add(payableFour);
		list.add(payableFive);
		dto.setBillPayables(list);
		this.billPayableService.batchUpdateByTakeEffect(dto, CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 根据运单号集合和单据类型查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午3:01:51
	 */
	@Test
	@Rollback(true)
	public void testQueryByWaybillNosAndBillType(){
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		this.billPayableService.addBillPayable(payable,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payableTwo.setSignDate(new Date());
		this.billPayableService.addBillPayable(payableTwo,CommonTestUtil.getCurrentInfo());
		
		
		BillPayableEntity payableThree = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		this.billPayableService.addBillPayable(payableThree,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableFour = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		this.billPayableService.addBillPayable(payableFour,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableFive = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		this.billPayableService.addBillPayable(payableFive,CommonTestUtil.getCurrentInfo());
		
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add(payable.getWaybillNo());
		waybillNos.add(payableTwo.getWaybillNo());
		waybillNos.add(payableThree.getWaybillNo());
		waybillNos.add(payableFour.getWaybillNo());
		waybillNos.add(payableFive.getWaybillNo());
		
		this.billPayableService.queryByWaybillNosAndBillType(waybillNos, SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
	}
	
	/**
	 * 根据运单号集合和单据类型查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午3:03:56
	 */
	@Test
	@Rollback(true)
	public void testSelectByWayBillNOsAndCustomerCodeAndBillType(){
		List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payable.setCustomerCode("W04061620");
		payable.setWaybillNo("0001");
		payable.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP);
		list.add(payable);
		this.billPayableService.addBillPayable(payable,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payableTwo.setCustomerCode("W04061621");
		payableTwo.setWaybillNo("0002");
		payableTwo.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP);
		list.add(payableTwo);
		this.billPayableService.addBillPayable(payableTwo,CommonTestUtil.getCurrentInfo());
				
		
		List<BillPayableEntity> payalbeList = this.billPayableService.selectBySourceBillNOsAndCustomerCodeAndBillType(list,FossConstants.ACTIVE);
	
		Assert.assertTrue(CollectionUtils.isNotEmpty(payalbeList));
	}
	
	/**
	 * 根据运单号集合和单据类型查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 下午3:03:56
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsAndCustomerCode(){
		BillPayableEntity payable = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payable.setCustomerCode("W04061620");
		payable.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP);
		this.billPayableService.addBillPayable(payable,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableTwo = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payableTwo.setCustomerCode("W04061620");
		payableTwo.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP);
		this.billPayableService.addBillPayable(payableTwo,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableThree = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payableThree.setCustomerCode("W04061620");
		payableThree.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP);
		this.billPayableService.addBillPayable(payableThree,CommonTestUtil.getCurrentInfo());
		
		BillPayableEntity payableFour = this.getBillPayableEntityTwo(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		payableFour.setCustomerCode("W04061620");
		payableFour.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP);
		this.billPayableService.addBillPayable(payableFour,CommonTestUtil.getCurrentInfo());
		
		List<String> sourceBillNos=new ArrayList<String>();
		sourceBillNos.add(payable.getWaybillNo());
		sourceBillNos.add(payableTwo.getWaybillNo());
		sourceBillNos.add(payableThree.getWaybillNo());
		sourceBillNos.add(payableFour.getWaybillNo());
       
		List<BillPayableEntity> list = this.billPayableService
				.queryBySourceBillNOsAndCustomerCode(
						sourceBillNos,
						"W04061620",
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP,
						FossConstants.ACTIVE);
	
		Assert.assertTrue(CollectionUtils.isNotEmpty(list));
	}
	
	@Test
	public void testCheckPayableBillRepeated(){
		PayableBills payableBill = new PayableBills();
		payableBill.setSourceBillNo("81006505");
		payableBill.setCustomerCode("W04061620");
		payableBill.setBillType("PB");
		payableBill.setAmount(new BigDecimal(1500));
		
		//查询合伙人奖罚应付单是否重复条件
		BillPayableEntity queryEntity = new BillPayableEntity();
		//来源单号
		queryEntity.setSourceBillNo(payableBill.getSourceBillNo());
		//客户编码（合伙人部门编码）
		queryEntity.setCustomerCode(payableBill.getCustomerCode());
		//单据子类型
		queryEntity.setBillType(payableBill.getBillType());
		//总金额
		BigDecimal amount = payableBill.getAmount()==null ? BigDecimal.ZERO : payableBill.getAmount().multiply(new BigDecimal(100));
		queryEntity.setAmount(amount);
		List<BillPayableEntity> payableList = billPayableService.checkPayableBillRepeated(queryEntity);
		if(CollectionUtils.isNotEmpty(payableList)){
			String errMsg = "校验合伙人应付单重复，已存在[来源单据号："
					+ payableBill.getSourceBillNo() + ",单据子类型："
					+ payableBill.getBillType() + ",合伙人编码："
					+ payableBill.getCustomerCode() + ",金额:"
					+ payableBill.getAmount() + "]一样的合付人应收单";
			throw new SettlementException(errMsg);
		}
	}
}
