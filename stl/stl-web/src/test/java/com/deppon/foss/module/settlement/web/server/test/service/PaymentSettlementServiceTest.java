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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/service/PaymentSettlementServiceTest.java
 * 
 * FILE NAME        	: PaymentSettlementServiceTest.java
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
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.interceptor.annotations.Before;

/**
 * 到达运费应收单 到付转 临欠/月结 实收货款/反实收货款 测试类
 * 
 * @author dp-wujiangtao
 * @date 2012-10-17 上午11:44:28
 * @since
 * @version
 */
public class PaymentSettlementServiceTest extends BaseTestCase {


	@Autowired
	private IPaymentSettlementService paymentSettlementService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private IBillRepaymentService billRepaymentService;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	private String getWaybillNo() {
		return new Date().getTime() + "";
	}

	/**
	 * 到付转临欠/月结(传入参数符合规范)
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午10:27:04
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testWriteBackBillReceivableTest() {
		// 生成运单号
		String waybillNo = this.getWaybillNo();

		// 保存到付应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		billReceivableService
				.addBillReceivable(
						StlWebTestUtil
								.getBillReceivableEntity(
										waybillNo,
										this.getReceivableNO(billType),
										billType,
										SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT),
						StlWebTestUtil.getCurrentInfo());

		PaymentSettlementDto dto = new PaymentSettlementDto();
		// 业务日期
		dto.setBusinessDate(new Date());
		// 运单号
		dto.setWaybillNo(waybillNo);
		// 到达部门
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("0005");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式为临时欠款
		try {
			// 到付转临欠/月结
			paymentSettlementService.confirmToBillReceivable(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询数据进行比对
			BillReceivableConditionDto conDto = new BillReceivableConditionDto(
					waybillNo);
			conDto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(conDto);
			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableEntity entity = list.get(0);
				if (entity != null) {
					// 判断付款方式是否更改 成功
					Assert.assertEquals(
							entity.getPaymentType(),
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式对比
				}
			}

			// 到付转临欠/月结成功后，调用反到付转临欠/月结方法
			testReversWriteBackBillReceivable(waybillNo);
		} catch (BusinessException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 开始-反 到达运费应收单到付转临欠/月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午10:27:32
	 * @see
	 */
	public void testReversWriteBackBillReceivable(String waybillNo) {
		PaymentSettlementDto dto = new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);

		// 业务日期
		dto.setBusinessDate(new Date());

		// 到达部门
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("0005");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);// 设置付款方式-到付

		try {
			// 反到付转临欠/月结
			paymentSettlementService.reversConfirmToBillReceiveable(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询数据进行比对
			BillReceivableConditionDto conDto = new BillReceivableConditionDto(
					waybillNo);
			conDto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE });
			List<BillReceivableEntity> list = this.billReceivableService
					.queryBillReceivableByCondition(conDto);

			if (CollectionUtils.isNotEmpty(list)) {
				BillReceivableEntity entity = list.get(0);
				if (entity != null) {

					// 判断付款方式是否更改 成功
					Assert.assertEquals(
							entity.getPaymentType(),
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
				}
			}
		} catch (BusinessException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 实收货款
	 * 
	 * @author dp-wujiangtao
	 * @date 2012-10-20 下午2:31:49
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testConfirmToPayMent() {
		// 生成运单号
		String waybillNo = this.getWaybillNo();
		waybillNo="222222246";

		// 保存到付应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		billReceivableService
				.addBillReceivable(
						StlWebTestUtil
								.getBillReceivableEntity(
										waybillNo,
										this.getReceivableNO(billType),
										billType,
										SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT),
						StlWebTestUtil.getCurrentInfo());

		// 保存代收货款应收单
		String codBillType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
		billReceivableService
				.addBillReceivable(
						StlWebTestUtil
								.getBillReceivableEntity(
										waybillNo,
										this.getReceivableNO(codBillType),
										codBillType,
										SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT),
						StlWebTestUtil.getCurrentInfo());

		PaymentSettlementDto dto = new PaymentSettlementDto();
		
		dto.setWaybillNo(waybillNo);
		dto.setBusinessDate(new Date());
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		dto.setCustomerCode("0002333122");
		dto.setCustomerName("张三");

		// 设置动态获取还款批次号
		String pcNo = getPaymentNo();
		dto.setSourceBillNo(pcNo);

		dto.setToPayFee(new BigDecimal("1000"));// 实收运费金额
		dto.setCodFee(new BigDecimal("1000"));// 实收代收货款金额
		try {

			// 调用实收货款方法
 			paymentSettlementService.confirmToPayment(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询是否存在还款单记录
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(pcNo);
			List<BillRepaymentEntity> list = billRepaymentService
					.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);

			// 存在还款单记录
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));

			// 还款冲应收后，查询运单对应应收单的已核销金额
			BillReceivableConditionDto conDto = new BillReceivableConditionDto(
					waybillNo);

			conDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE });

			List<BillReceivableEntity> receList = this.billReceivableService
					.queryBillReceivableByCondition(conDto);
			if (CollectionUtils.isNotEmpty(receList)) {
				for (int i = 0; i < receList.size(); i++) {
					BillReceivableEntity entity = receList.get(i);
					if (entity != null) {
						// 判断已核销金额是否大于0
						Assert.assertTrue(entity.getVerifyAmount() != null ? entity
								.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0
								: false);
					}
				}
			}

			// 实收货款成功，进行反实收操作
			testReversConfirmPayment(waybillNo, pcNo, dto.getToPayFee(),
					dto.getCodFee());
		} catch (BusinessException e) {
			logger.error(e.getErrorCode(),e);
		}

	}

	/**
	 * 模拟获取实收单号方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午4:11:55
	 * @return
	 * @see
	 */
	private String getPaymentNo() {
		return new Date().getTime() + "";
	}

	/**
	 * 反实收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 上午11:00:23
	 * @see toPayFee:实收货款费用；codFee实收代收货款费用 ，根据查询出来的应收单进行比较
	 */
	public void testReversConfirmPayment(String waybillNo, String sourceBillNo,
			BigDecimal toPayFee, BigDecimal codFee) {
		PaymentSettlementDto dto = new PaymentSettlementDto();
		dto.setWaybillNo(waybillNo);
		dto.setBusinessDate(new Date());
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("0002333122");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		dto.setSourceBillNo(sourceBillNo);
		try {
			paymentSettlementService.reversConfirmPayment(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询是否存在有效的还款单记录
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add(sourceBillNo);
			List<BillRepaymentEntity> list = billRepaymentService
					.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);

			// 存在提示错误信息
			Assert.assertEquals(
					CollectionUtils.isNotEmpty(list) ? false : true, true);

		} catch (BusinessException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 制定一个运单号和一个实收批次号进行反实收货款操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-5 上午10:59:01
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testReversConfirmPaymentTwo() {
		PaymentSettlementDto dto = new PaymentSettlementDto();
		dto.setWaybillNo("1352083966421");
		dto.setBusinessDate(new Date());
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("0002333122");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		dto.setSourceBillNo("1352083966437");
		try {
			paymentSettlementService.reversConfirmPayment(dto,
					StlWebTestUtil.getCurrentInfo());

			// 查询是否存在有效的还款单记录
			List<String> sourceBillNos = new ArrayList<String>();
			sourceBillNos.add("1352083966437");
			List<BillRepaymentEntity> list = billRepaymentService
					.queryBySourceBillNOs(sourceBillNos, FossConstants.ACTIVE);

			// 存在提示错误信息
			Assert.assertEquals(
					CollectionUtils.isNotEmpty(list) ? false : true, true);

		} catch (BusinessException e) {
			logger.error(e.getErrorCode(),e);
		}
	}

	/******************************************* 测试程序中对异常程序的判断 *******************************/
	/**
	 * 测试到付转临欠月结，部分参数为空情况
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午9:34:49
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testWriteBackBillReceivableWaybillNoIsNull() {
		// 生成运单号
		String waybillNo = this.getWaybillNo();

		// 保存到付应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		billReceivableService
				.addBillReceivable(
						StlWebTestUtil
								.getBillReceivableEntity(
										waybillNo,
										this.getReceivableNO(billType),
										billType,
										SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT),
						StlWebTestUtil.getCurrentInfo());

		PaymentSettlementDto dto = new PaymentSettlementDto();

		// 业务日期
		dto.setBusinessDate(new Date());
		// 运单号为空
		dto.setWaybillNo(null);
		// 到达部门
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式
		// 到付转临欠/月结
		try {
			paymentSettlementService.confirmToBillReceivable(dto,
					StlWebTestUtil.getCurrentInfo());
		} catch (BusinessException e) {
			// 运单号为空抛出异常
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 部门为空
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午9:58:51
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testWriteBackBillReceivableDestOrgCodeIsNull() {
		// 生成运单号
		String waybillNo = this.getWaybillNo();

		// 保存到付应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		billReceivableService
				.addBillReceivable(
						StlWebTestUtil
								.getBillReceivableEntity(
										waybillNo,
										this.getReceivableNO(billType),
										billType,
										SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT),
						StlWebTestUtil.getCurrentInfo());
		PaymentSettlementDto dto = new PaymentSettlementDto();

		// 业务日期
		dto.setBusinessDate(new Date());
		// 运单号为空
		dto.setWaybillNo(waybillNo);
		// 到达部门编码为空
		dto.setDestOrgCode("");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式
		// 到付转临欠/月结
		try {
			paymentSettlementService.confirmToBillReceivable(dto,
					StlWebTestUtil.getCurrentInfo());
		} catch (BusinessException e) {
			// 到达部门为空抛出异常
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
	 * 根据运单号查询不到应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午10:04:39
	 * @see
	 */
	@Test
	@Rollback(true)
	public void testWriteBackBillReceivableIsEmpty() {
		PaymentSettlementDto dto = new PaymentSettlementDto();

		// 业务日期
		dto.setBusinessDate(new Date());
		// 运单号为空
		dto.setWaybillNo("yd1232000000000000");
		// 到达部门
		dto.setDestOrgCode("CUST_CODE");
		dto.setDestOrgName("上海营业部");
		dto.setCustomerCode("1");
		dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);// 付款方式
		// 到付转临欠/月结
		try {
			paymentSettlementService.confirmToBillReceivable(dto,
					StlWebTestUtil.getCurrentInfo());
		} catch (BusinessException e) {
			// 到付应收单不存在信息
			logger.error(e.getErrorCode(),e);
		}
	}

	/**
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
		}
		return "";
	}
}
