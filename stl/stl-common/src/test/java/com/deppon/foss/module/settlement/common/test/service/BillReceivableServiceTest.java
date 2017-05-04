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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillReceivableServiceTest.java
 * 
 * FILE NAME        	: BillReceivableServiceTest.java
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.esb.inteface.domain.receivable.add.ReceivableBills;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 应收单服务测试类
 * 
 * @author ibm-zhuwei
 * @date 2012-10-19 上午9:34:49
 */
public class BillReceivableServiceTest extends BaseTestCase {

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	// 财务变更消息实体Service
	@Autowired
	private IWaybillChangeMsgService waybillChangeMsgService;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	/**
	 * 
	 * 根据传入的单据类型获取应收单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:40:06
	 * @return
	 * @see
	 */
	private String getReceivableNO(String billType) {
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS2);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS6);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS1);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS90);
		}
		return "";
	}

	/**
	 * 设置测试应收单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 上午10:34:09
	 * @param waybillNo
	 *            运单号
	 * @param receivableNo
	 *            应收单号
	 * @param billType
	 *            应收单类型
	 * @param paymentType
	 *            付款类型
	 * @return
	 * @see
	 */
	public BillReceivableEntity getBillReceivableEntity(String waybillNo,
			String billType, String paymentType) {
		BillReceivableEntity entity = new BillReceivableEntity();

		Date now = DateUtils.truncate(new Date(), Calendar.SECOND);

		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId(waybillNo);// 运单Id
		entity.setWaybillNo(waybillNo);// 运单号
		entity.setReceivableNo(getReceivableNO(billType));// 应收单号

		entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType("1");// 来源单据类型
		entity.setBillType(billType);// 单据子类型 -到付应收单
		entity.setPaymentType(paymentType);// 付款方式-到付
		entity.setReceivableOrgCode("YS1");// 应收部门编码
		entity.setReceivableOrgName("应收上海营业部");// 应收部门名称
		entity.setGeneratingOrgCode("SR1");// 收入部门编码
		entity.setGeneratingOrgName("收入上海营业部");// 收入部门名称
		entity.setGeneratingComCode("SRCOM1");// 收入子公司编码
		entity.setGeneratingComName("收入公司上海德邦物流");// 收入子公司名称
		entity.setDunningOrgCode("CK1");// 催款部门编码
		entity.setDunningOrgName("催款上海青浦营业部");// 催款部门名称
		entity.setOrigOrgCode("CFORG1");// 出发部门编码
		entity.setOrigOrgName("出发上海徐泾营业部");// 出发部门名称
		entity.setDestOrgCode("DDORG1");// 到达部门编码
		entity.setDestOrgName("到达北京营业部");// 到达部门名称
		entity.setCustomerCode("CUST1");// 客户编码
		entity.setCustomerName("客户1");// 客户名称

		// 设置金额 部分
		entity.setAmount(new BigDecimal(600));// 总金额
		entity.setUnverifyAmount(entity.getAmount());// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(100));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(0));// 其他费用

		entity.setValueAddFee(new BigDecimal(500));// 增值费用
		entity.setPromotionsFee(new BigDecimal(0));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(now);// 设置业务日期
		entity.setAccountDate(now);// 记账日期
		entity.setConrevenDate(null);// 确认收入日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		return entity;
	}

	@Test
	@Rollback(true)
	@Ignore
	public void testWriteBackBillReceivable() {

		BillReceivableEntity entity = getBillReceivableEntity(
				"0001",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);

		billReceivableService.writeoffBillReceivable(entity,
				NumberUtils.createBigDecimal("-20.33"),
				CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	@Ignore
	public void testQueryBillReceivableByCondition() {
		BillReceivableConditionDto dto = new BillReceivableConditionDto();
		dto.setWaybillNo("YD001");
		dto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });

		List<BillReceivableEntity> list = billReceivableService
				.queryBillReceivableByCondition(dto);
		logger.info("size:" + list.size());
	}

	/**
	 * 批量审核空运应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:34:58
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testAuditAirOtherBillReceivable() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());

			BillReceivableEntity entityTwo = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entityTwo,
					CommonTestUtil.getCurrentInfo());

			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			dto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			Assert.assertEquals(list != null ? list.size() > 0 : false, true);
			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableDto billDto = new BillReceivableDto();
				billDto.setBillReceivables(list);
				billDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);// 已审核

				Date date = new Date();
				billDto.setAuditDate(date);
				billDto.setAuditUserCode("TODOID");
				billDto.setAuditUserName("TODO");

				billDto.setModifyTime(date);
				billDto.setModifyUserCode("TODOMID");
				billDto.setModifyUserCode("TODOM");

				this.billReceivableService.auditAirOtherBillReceivable(billDto,
						CommonTestUtil.getCurrentInfo());

			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 反审核空运其他应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午8:55:50
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testReverseAuditAirOtherBillReceivable() {

		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());

			BillReceivableEntity entityTwo = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entityTwo,
					CommonTestUtil.getCurrentInfo());

			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			dto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			Assert.assertEquals(list != null ? list.size() > 0 : false, true);
			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableDto billDto = new BillReceivableDto();
				billDto.setBillReceivables(list);
				billDto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);// 已审核

				Date date = new Date();
				billDto.setAuditDate(date);
				billDto.setAuditUserCode("TODOID");
				billDto.setAuditUserName("TODO");

				billDto.setModifyTime(date);
				billDto.setModifyUserCode("TODOMID");
				billDto.setModifyUserCode("TODOM");
				this.billReceivableService.auditAirOtherBillReceivable(billDto,
						CommonTestUtil.getCurrentInfo());

				list = this.billReceivableService
						.queryBillReceivableByCondition(dto);

				BillReceivableDto billDtot = new BillReceivableDto();
				billDtot.setBillReceivables(list);
				billDtot.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);// 反审核

				Date datet = new Date();
				billDtot.setAuditDate(null);
				billDtot.setAuditUserCode("");
				billDtot.setAuditUserName("");

				billDtot.setModifyTime(datet);
				billDtot.setModifyUserCode("TODOMID");
				billDtot.setModifyUserCode("TODOM");

				try {
					this.billReceivableService
							.reverseAuditAirOtherBillReceivable(billDtot,
									CommonTestUtil.getCurrentInfo());
				} catch (SettlementException e) {
					logger.error(e.getErrorCode());
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * 根据运单号查询客户的应收单到付金额和应收代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-2 上午9:04:22
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryReceivableAmountByCondition() {
		BillReceivableConditionDto dto = new BillReceivableConditionDto(
				"1350911439046");
		try {
			List<BillReceivableEntity> list = this.billReceivableService
					.queryReceivableAmountByCondition(dto);
			Assert.assertEquals(list.size() > 0 ? true : false, true);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 锁定应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:44:25
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testLockBillReceivable() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity1 = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entity1,
					CommonTestUtil.getCurrentInfo());
			BillReceivableEntity entityTwo = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entityTwo,
					CommonTestUtil.getCurrentInfo());
			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableDto receDto = new BillReceivableDto();
				receDto.setBillReceivables(list);
				Date date = DateUtils.addMinutes(new Date(), 30);
				receDto.setUnlockDateTime(date);
				receDto.setLockCustomerCode("TODOC");
				receDto.setLockCustomerName("TODON");
				this.billReceivableService.lockBillReceivable(receDto,
						CommonTestUtil.getCurrentInfo());
				// 加锁之后，判断应收单的锁定时间是否为空
				List<BillReceivableEntity> lists = this.billReceivableService
						.queryBillReceivableByCondition(dto);
				for (BillReceivableEntity entity : lists) {
					Assert.assertTrue(entity.getUnlockDateTime() != null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 解锁应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 上午10:51:34
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testUnlockBillReceivable() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity1 = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entity1,
					CommonTestUtil.getCurrentInfo());
			BillReceivableEntity entityTwo = this
					.getBillReceivableEntity(
							waybillNo,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_RECEIVABLE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
			this.billReceivableService.addBillReceivable(entityTwo,
					CommonTestUtil.getCurrentInfo());
			BillReceivableConditionDto dto = new BillReceivableConditionDto(
					waybillNo);
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(dto);
			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableDto receDto = new BillReceivableDto();
				receDto.setBillReceivables(list);
				Date date = DateUtils.addMinutes(new Date(), 30);
				receDto.setUnlockDateTime(date);
				receDto.setLockCustomerCode("TODOC");
				receDto.setLockCustomerName("TODON");
				this.billReceivableService.lockBillReceivable(receDto,
						CommonTestUtil.getCurrentInfo());
				// 加锁之后，判断应收单的锁定时间是否为空
				List<BillReceivableEntity> lists = this.billReceivableService
						.queryBillReceivableByCondition(dto);
				for (BillReceivableEntity entity : lists) {
					Assert.assertTrue(entity.getUnlockDateTime() != null);
				}
				BillReceivableDto receDtot = new BillReceivableDto();
				receDtot.setBillReceivables(lists);
				receDtot.setUnlockDateTime(null);
				receDtot.setLockCustomerCode("");
				receDtot.setLockCustomerName("");
				this.billReceivableService.unlockBillReceivable(receDtot,
						CommonTestUtil.getCurrentInfo());
				lists = this.billReceivableService
						.queryBillReceivableByCondition(dto);
				for (BillReceivableEntity entity : lists) {
					Assert.assertTrue(entity.getUnlockDateTime() == null);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 根据ID（或应收单）与分区键查询应收单
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-29 下午2:47:44
	 */
	@Test
	@Rollback(true)
	public void testQueryPartitionsByConditions() {
		BillReceivableEntity entity1 = getBillReceivableEntity(
				"422",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);

		BillReceivableEntity entity2 = getBillReceivableEntity(
				"422",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);

		billReceivableService.addBillReceivable(entity1,
				CommonTestUtil.getCurrentInfo());
		billReceivableService.addBillReceivable(entity2,
				CommonTestUtil.getCurrentInfo());

		List<BillReceivableEntity> list1 = new ArrayList<BillReceivableEntity>();
		list1.add(entity1);
		list1.add(entity2);

		BillReceivableDto dtos1 = new BillReceivableDto();
		dtos1.setBillReceivables(list1);

		List<BillReceivableEntity> results1 = billReceivableService
				.queryPartitionsByConditions(dtos1);
		Assert.assertEquals(results1.size(), 2);

		dtos1.getBillReceivables().get(0).setId(null);
		List<BillReceivableEntity> results2 = billReceivableService
				.queryPartitionsByConditions(dtos1);
		Assert.assertEquals(results2.size(), 2);
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-29 上午9:12:59
	 * @return
	 */
	private String getWaybillNO() {
		return "YD" + new Date().getTime();
	}

	/**
	 * 批量修改应收单的对账单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午7:08:52
	 */
	@Test
	@Rollback(true)
	public void testBatchUpdateByMakeStatement() {
		try {
			String waybillNo = this.getWaybillNO();

			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());

			BillReceivableEntity entityTwo = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entityTwo,
					CommonTestUtil.getCurrentInfo());

			// 对账单号
			String statementBillNo = CommonTestUtil.getStatementBillNo();
			BillReceivableDto dto = new BillReceivableDto();
			dto.setStatementBillNo(statementBillNo);

			List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
			list.add(entity);
			list.add(entityTwo);
			dto.setBillReceivables(list);
			this.billReceivableService.batchUpdateByMakeStatement(dto,
					CommonTestUtil.getCurrentInfo());

			BillReceivableConditionDto conDto = new BillReceivableConditionDto(
					waybillNo);
			List<BillReceivableEntity> lists = this.billReceivableService
					.queryBillReceivableByCondition(conDto);
			if (CollectionUtils.isNotEmpty(lists)) {
				for (BillReceivableEntity rece : lists) {
					Assert.assertEquals(rece.getStatementBillNo(),
							statementBillNo);
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 验证插入的数据存在
	 * 
	 * 新增的是20、完结的是10
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午7:52:54
	 */
	private void validateWaybillChangeMsg(String waybillNo, int count, int value) {
		List<WaybillChangeMsgEntity> list = this.waybillChangeMsgService
				.queryChangeMsgByWaybillNO(waybillNo);
		Assert.assertTrue(CollectionUtils.isNotEmpty(list)
				&& list.size() == count);
		int k = 0;
		for (WaybillChangeMsgEntity entity : list) {
			if (SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESS_DONE
					.equals(entity.getMsgAction())) {
				k += 10;// -1 的一次
			} else if (SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING
					.equals(entity.getMsgAction())) {
				k += 20;// +1 的一次
			}
		}
		Assert.assertEquals(k, value);
	}

	/**
	 * 添加应收单生成一条财务变更消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午7:42:55
	 */
	@Test
	@Rollback(true)
	public void testAddBillReceivableToWaybillChangeMsg() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());
			this.validateWaybillChangeMsg(waybillNo, 1, 20);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 红冲应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午8:05:15
	 */
	@Test
	@Rollback(true)
	public void testWritebackBillReceivableToWaybillChangeMsg() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());// 新增 +1

			// 红冲之后生成一条财务变更消息
			this.billReceivableService.writeBackBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());// -1

			this.validateWaybillChangeMsg(waybillNo, 2, 30);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 核销应收单，且应收单的未核销金额等于0的情况下
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-5 下午8:13:12
	 */
	@Test
	@Rollback(true)
	public void testWriteoffReceivableToWaybillChangeMsg() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());// 新增 +1

			entity.setVerifyAmount(entity.getAmount());// 已核销金额
			entity.setUnverifyAmount(BigDecimal.ZERO);// 设置未核销金额为0

			this.billReceivableService.writeoffBillReceivable(entity,
					entity.getAmount(), CommonTestUtil.getCurrentInfo());

			// 新增是：20，完结是 10 =30
			this.validateWaybillChangeMsg(waybillNo, 2, 30);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 核销应收单核销金额小于应收单的未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-6 上午8:20:49
	 */
	@Test
	@Rollback(true)
	public void testWriteoffReceivableToWaybillChangeMsgTwo() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());// 新增 +1

			BigDecimal b = new BigDecimal("300");
			entity.setVerifyAmount(b);// 已核销金额
			entity.setUnverifyAmount(b);// 设置未核销金额为0

			// 未全部核销，不会产生新的财务变更记录
			this.billReceivableService.writeoffBillReceivable(entity, b,
					CommonTestUtil.getCurrentInfo());

			// 1 代表 只有一条记录 20 代表还是原来新增的数据
			this.validateWaybillChangeMsg(waybillNo, 1, 20);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 反核销应收单
	 * 
	 * 应收单未核销金额等于0，然后进行反核销应收单，新增一条财务运单变更信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-6 上午8:17:50
	 */
	@Test
	@Rollback(true)
	public void testAntiWriteoffReceivableToWaybillChangeMsg() {
		try {
			for (int i = 0; i < 300; i++) {
				String waybillNo = this.getWaybillNO();
				BillReceivableEntity entity = getBillReceivableEntity(
						waybillNo,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
				this.billReceivableService.addBillReceivable(entity,
						CommonTestUtil.getCurrentInfo());// 新增 +1

				entity.setVerifyAmount(entity.getAmount());// 已核销金额
				entity.setUnverifyAmount(BigDecimal.ZERO);// 设置未核销金额为0

				// 完成全部核销
				this.billReceivableService.writeoffBillReceivable(entity,
						entity.getAmount(), CommonTestUtil.getCurrentInfo());

				// 新增是：20，完结是 10 =30
				this.validateWaybillChangeMsg(waybillNo, 2, 30);

				// 已全部核销，在第一次反核销时生成财务变更信息

				// 总金额600--先反核销300
				BigDecimal b = new BigDecimal("300");

				entity.setVerifyAmount(b);// 已核销金额
				entity.setUnverifyAmount(b);// 设置未核销金额为300

				this.billReceivableService.writeoffBillReceivable(entity,
						b.multiply(new BigDecimal("-1")),
						CommonTestUtil.getCurrentInfo());

				// 新增是：20
				this.validateWaybillChangeMsg(waybillNo, 3, 50);

				// 第二次反核销 300 不产生财务财务变更信息
				entity.setVerifyAmount(BigDecimal.ZERO);// 已核销金额
				entity.setUnverifyAmount(entity.getAmount());// 设置未核销金额为原来金额

				this.billReceivableService.writeoffBillReceivable(entity,
						b.multiply(new BigDecimal("-1")),
						CommonTestUtil.getCurrentInfo());

				// 还是用上面的数据进行判断
				this.validateWaybillChangeMsg(waybillNo, 3, 50);
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 反核销应收单
	 * 
	 * 只核销部分金额，还有部分金额未核销，只反已核销的金额 预计只有在新增的时候生成一条财务变更消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-6 上午8:42:12
	 */
	@Test
	@Rollback(true)
	public void testAntiWriteoffReceivableToWaybillChangeMsgTwo() {
		try {
			String waybillNo = this.getWaybillNO();
			BillReceivableEntity entity = getBillReceivableEntity(
					waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());// 新增 +1

			// 总金额为600
			BigDecimal b = new BigDecimal("300");

			entity.setVerifyAmount(b);// 已核销金额
			entity.setUnverifyAmount(b);// 设置未核销金额为300

			this.billReceivableService.writeoffBillReceivable(entity, b,
					CommonTestUtil.getCurrentInfo());

			// 未全部核销，不会产生新的财务变更记录
			this.validateWaybillChangeMsg(waybillNo, 1, 20);

			// 进行反核销操作
			entity.setVerifyAmount(BigDecimal.ZERO);// 已核销金额
			entity.setUnverifyAmount(entity.getAmount());// 设置未核销金额为0

			this.billReceivableService.writeoffBillReceivable(entity,
					b.multiply(new BigDecimal("-1")),
					CommonTestUtil.getCurrentInfo());

			// 接上面未完全核销，因此在反核销时也不会产生记录信息
			this.validateWaybillChangeMsg(waybillNo, 1, 20);
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	@Test
	@Rollback(true)
	public void testQueryByWaybillNosAndOrgCodes() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		this.billReceivableService.addBillReceivable(entity,
				CommonTestUtil.getCurrentInfo());// 新增 +1

		String waybillNoTwo = this.getWaybillNO();
		BillReceivableEntity entityTwo = getBillReceivableEntity(
				waybillNoTwo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		this.billReceivableService.addBillReceivable(entityTwo,
				CommonTestUtil.getCurrentInfo());// 新增 +1

		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add(waybillNo);
		waybillNos.add(waybillNoTwo);

		List<String> orgCodes = new ArrayList<String>();
		orgCodes.add(entity.getReceivableOrgCode());

		this.billReceivableService.queryByWaybillNosAndOrgCodes(waybillNos,
				orgCodes, null, CommonTestUtil.getCurrentInfo());

	}

	/**
	 * 根据运单号，查询开单付款方式为网上支付的应收单的未核销金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 上午10:11:44
	 */
	@Test
	public void testQueryReceivableAmountByWaybillNO() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		try {
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());

			this.billReceivableService
					.queryReceivableAmountByWaybillNO(waybillNo);

		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void testQueryByWaybillNosOrSourceBillNosAndBillTypes() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		try {
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());
			List<String> waybillNos = new ArrayList<String>();
			waybillNos.add(waybillNo);

			this.billReceivableService
					.queryByWaybillNosOrSourceBillNosAndBillTypes(
							waybillNos,
							null,
							SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,
							Arrays.asList(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE),
							FossConstants.ACTIVE, FossConstants.NO);

		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void testQueryIsExistsReceivableAmountByCustomerCode() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		try {
			this.billReceivableService.addBillReceivable(entity,
					CommonTestUtil.getCurrentInfo());
			List<String> waybillNos = new ArrayList<String>();
			waybillNos.add(waybillNo);

			this.billReceivableService
					.queryIsExistsReceivableAmountByCustomerCode(entity
							.getCustomerCode());

		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 红冲应收单，测试添加财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 下午4:17:24
	 */
	@Test
	@Rollback(true)
	public void testWriteBack() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		entity.setReceivableOrgCode("W04061620");
		entity.setReceivableOrgName("test");
		this.billReceivableService.addBillReceivable(entity,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.writeBackBillReceivable(entity,
				CommonTestUtil.getCurrentInfo());

		BillReceivableEntity entityYueJie = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		entityYueJie.setCustomerCode("400233719");
		entityYueJie.setCustomerName("吴华娜");
		this.billReceivableService.addBillReceivable(entityYueJie,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.writeBackBillReceivable(entityYueJie,
				CommonTestUtil.getCurrentInfo());

		BillReceivableEntity dr = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		dr.setCustomerCode("400233719");
		dr.setCustomerName("吴华娜");
		this.billReceivableService.addBillReceivable(dr,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.writeBackBillReceivable(dr,
				CommonTestUtil.getCurrentInfo());

	}

	/**
	 * 测试应收单核销反核销，测试添加财务收支平衡消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-18 下午4:22:21
	 */
	@Test
	@Rollback(true)
	public void testWriteoff() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		entity.setReceivableOrgCode("W04061620");
		entity.setReceivableOrgName("test");
		this.billReceivableService.addBillReceivable(entity,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.writeoffBillReceivable(entity,
				new BigDecimal("100"), CommonTestUtil.getCurrentInfo());

		BillReceivableEntity entityYueJie = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		entityYueJie.setCustomerCode("400233719");
		entityYueJie.setCustomerName("吴华娜");
		this.billReceivableService.addBillReceivable(entityYueJie,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.writeoffBillReceivable(entityYueJie,
				new BigDecimal("-100"), CommonTestUtil.getCurrentInfo());
	}

	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:50:19
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsAndOrgCodes() {
		String waybillNo = this.getWaybillNO();
		BillReceivableEntity entity = getBillReceivableEntity(
				waybillNo,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		entity.setReceivableOrgCode("W04061620111111");
		entity.setReceivableOrgName("test");

		this.billReceivableService.addBillReceivable(entity,
				CommonTestUtil.getCurrentInfo());

		List<String> sourceBillNos = new ArrayList<String>();
		List<String> orgCodes = new ArrayList<String>();

		sourceBillNos.add(entity.getSourceBillNo());// 来源单号
		orgCodes.add(entity.getReceivableOrgCode());// 应收部门编码

		this.billReceivableService.queryBySourceBillNOsAndOrgCodes(
				sourceBillNos, orgCodes, null, FossConstants.ACTIVE,
				CommonTestUtil.getCurrentInfo());

	}

	@Test
	@Ignore
	public void testQueryByReceivableNO() {
		try {
			String receivableNo = "YS600048430";
			BillReceivableEntity entity = this.billReceivableService
					.queryByReceivableNO(receivableNo, FossConstants.ACTIVE);

			if (entity != null) {
				System.out.println(entity.getWaybillNo());
			}
		} catch (SettlementException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * 根据来源单号集合和应收部门编码集合，查询应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 下午4:50:19
	 */
	@Test
	@Rollback(true)
	public void testUpdateStampByNumbers() {
		BillReceivableEntity entityOne = getBillReceivableEntity(
				"M00000001",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		
		BillReceivableEntity entityTwo = getBillReceivableEntity(
				"M00000002",
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		this.billReceivableService.addBillReceivable(entityOne,
				CommonTestUtil.getCurrentInfo());
		this.billReceivableService.addBillReceivable(entityTwo,
				CommonTestUtil.getCurrentInfo());
		BillReceivableDto dto = new BillReceivableDto();
		List<BillReceivableEntity> list= new ArrayList<BillReceivableEntity>();
		list.add(entityTwo);
		list.add(entityOne);
		dto.setBillReceivables(list);
		dto.setStamp(FossConstants.ACTIVE);
		dto.setActive(FossConstants.ACTIVE);
		this.billReceivableService.updateStampByNumbers(dto, CommonTestUtil.getCurrentInfo());
	}
	
	@Test
	public void testCheckReceivableBillRepeated() {
		ReceivableBills receivableBill = new ReceivableBills();
		receivableBill.setSourceBillNo("81006505");
		receivableBill.setBillType("PER");
		receivableBill.setCustomerCode("400002775");
		receivableBill.setAmount(new BigDecimal(1));
		
		// 应收单是否重复查询entity
		BillReceivableEntity queryEntity = new BillReceivableEntity();
		// 来源单据编号
		queryEntity.setSourceBillNo(receivableBill.getSourceBillNo());
		// 单据子类型
		queryEntity.setBillType(receivableBill.getBillType());
		// 总金额
		BigDecimal amount = receivableBill.getAmount() == null ? BigDecimal.ZERO
				: receivableBill.getAmount().multiply(new BigDecimal(100));
		queryEntity.setAmount(amount);
		// 客户编码
		queryEntity.setCustomerCode(receivableBill.getCustomerCode());
		// 根据条件查询应收单是否重复
		List<BillReceivableEntity> entityList = billReceivableService
				.checkReceivableBillRepeated(queryEntity);
		if (CollectionUtils.isNotEmpty(entityList)) {
			String errMsg = "校验合伙人应收单重复，已存在[来源单据号："
					+ receivableBill.getSourceBillNo() + ",单据子类型："
					+ receivableBill.getBillType() + ",合伙人编码："
					+ receivableBill.getCustomerCode() + ",金额:"
					+ receivableBill.getAmount() + "]一样的合伙人应收单";
			throw new SettlementException(errMsg);
		}
	}

}
