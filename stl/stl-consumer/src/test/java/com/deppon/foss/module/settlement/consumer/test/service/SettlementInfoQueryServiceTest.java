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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/SettlementInfoQueryServiceTest.java
 * 
 * FILE NAME        	: SettlementInfoQueryServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISettlementInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.DateUtils;

/**
 * 查询运单的到付金额、代收货款、装卸费、发票信息Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 下午2:57:19
 * @since
 * @version
 */
public class SettlementInfoQueryServiceTest extends BaseTestCase {
	@Autowired
	private ISettlementInfoQueryService settlementInfoQueryService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillPaymentService billPaymentService;

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private ISettlementCommonService settlementCommonService;

	@Autowired
	private IInvoiceDao invoiceDao;

	/**
	 * 根据运单号和开单日期查询运单的代收货款信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 下午2:59:50
	 */
	@Test
	@Rollback(false)
	public void testQueryCodFeeByWaybillNO() {
		try {
			Date startDate = new Date();
			Date endDate = new Date();
			// String waybillNo=ConsumerTestUtil.getWaybillNO();
			//
			// String
			// receivableType=SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
			//
			// String receivableNo=this.getReceivableNO(receivableType);
			// BillReceivableEntity
			// receEntity=ConsumerTestUtil.getBillReceivableEntity(waybillNo,
			// receivableNo, receivableType,
			// SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
			// billReceivableService.addBillReceivable(receEntity,
			// ConsumerTestUtil.getCurrentInfo());
			//
			// receEntity=this.billReceivableService.queryByReceivableNO(receivableNo,
			// FossConstants.ACTIVE);
			// this.billReceivableService.writeoffBillReceivable(receEntity, new
			// BigDecimal("1000"), ConsumerTestUtil.getCurrentInfo());
			//
			// String paymentNo=this.getPaymentNo("");
			// String
			// payableType=SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD;
			// String payableNo=this.getPayableNO(payableType);
			// BillPayableEntity
			// payableEntity=ConsumerTestUtil.getBillPayableEntityToPayment(waybillNo,payableNo
			// ,payableType , startDate, new BigDecimal("100"), paymentNo);
			// payableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
			// this.billPayableService.addBillPayable(payableEntity,ConsumerTestUtil.getCurrentInfo());
			//
			// BillPaymentEntity
			// paymentEntity=ConsumerTestUtil.getBillPaymentEntity(paymentNo,
			// "PL", payableNo);
			// this.billPaymentService.addBillPayment(paymentEntity,
			// ConsumerTestUtil.getCurrentInfo());
			//
			// payableEntity=this.billPayableService.queryByPayableNO(payableNo,
			// FossConstants.ACTIVE);
			//
			// BillPayableDto payableDto=new BillPayableDto();
			// BeanUtils.copyProperties(payableEntity, payableDto);
			// payableDto.setPaymentNo(paymentNo);
			// this.billPayableService.payForBillPayable(payableDto,
			// ConsumerTestUtil.getCurrentInfo());
			//
			// payableEntity=this.billPayableService.queryByPayableNO(payableNo,
			// FossConstants.ACTIVE);
			// this.billPayableService.writeoffBillPayable(payableEntity, new
			// BigDecimal("1000"), ConsumerTestUtil.getCurrentInfo());

			WaybillSettlementInfoDto dto = this.settlementInfoQueryService.queryCodFeeByWaybillNO(
					"110652544", startDate, endDate);
			if (dto != null) {
				Assert.assertTrue(dto.getCodFee() != null);
				logger.info(" 实收代收货款：" + dto.getCodFee() + " 应付金额：  " + dto.getPayableAmount()
						+ " 核销金额: " + dto.getWriteoffAmount() + " 可退金额 ：" + dto.getRetreatAmount()
						+ " 退款状态 ：" + dto.getCodRefundStatus() + " 代收货款退款说明 ："
						+ dto.getCodRefundNotes());
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据运单号和开单日期查询运单的装卸费信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 下午3:13:47
	 */
	@Test
	public void testQueryServiceFeeByWaybillNO() {
		try {
			Date startDate = new Date();
			Date endDate = new Date();
			// String waybillNo=ConsumerTestUtil.getWaybillNO();
			// String paymentNo=this.getPaymentNo("");
			// String
			// payableType=SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE;
			// String payableNo=this.getPayableNO(payableType);
			// BillPayableEntity
			// payableEntity=ConsumerTestUtil.getBillPayableEntityToPayment(waybillNo,payableNo
			// ,payableType , startDate, new BigDecimal("100"), paymentNo);
			// payableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
			// this.billPayableService.addBillPayable(payableEntity,ConsumerTestUtil.getCurrentInfo());
			//
			// BillPaymentEntity
			// paymentEntity=ConsumerTestUtil.getBillPaymentEntity(paymentNo,
			// "PL", payableNo);
			// this.billPaymentService.addBillPayment(paymentEntity,
			// ConsumerTestUtil.getCurrentInfo());
			//
			// payableEntity=this.billPayableService.queryByPayableNO(payableNo,
			// FossConstants.ACTIVE);
			//
			// BillPayableDto payableDto=new BillPayableDto();
			// BeanUtils.copyProperties(payableEntity, payableDto);
			// payableDto.setPaymentNo(paymentNo);
			// this.billPayableService.payForBillPayable(payableDto,
			// ConsumerTestUtil.getCurrentInfo());
			//
			// payableEntity=this.billPayableService.queryByPayableNO(payableNo,
			// FossConstants.ACTIVE);
			// this.billPayableService.writeoffBillPayable(payableEntity, new
			// BigDecimal("1000"), ConsumerTestUtil.getCurrentInfo());

			WaybillSettlementInfoDto dto = this.settlementInfoQueryService
					.queryServiceFeeByWaybillNO("800900566", startDate, endDate);
			if (dto != null) {
				Assert.assertTrue(dto.getSfRefundAmount() != null);
				logger.info("装卸费退款类型：" + dto.getSfRefundType() + "  装卸费退款金额 :"
						+ dto.getSfRefundAmount() + "  装卸费退款状态： " + dto.getSfRecundStatus()
						+ "   装卸费退款说明 " + dto.getSfRefundNotes());
			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据运单号和开单日期查询运单的发票信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 下午3:17:54
	 */
	@Test
	public void testQueryInvoiceByWaybillNO() {
		try {
			Date startDate = new Date();
			Date endDate = new Date();
			String waybillNo = ConsumerTestUtil.getWaybillNO();
			String sourceBillType = SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL;
			String origOrgCode = "123";
			String destOrgCode = "456";
			InvoiceEntity entity = ConsumerTestUtil.getInvoiceEntity(waybillNo, sourceBillType,
					new BigDecimal("600"), origOrgCode, "始发部门");
			invoiceDao.addInvoice(entity);
			InvoiceEntity entityTwo = ConsumerTestUtil.getInvoiceEntity(waybillNo, sourceBillType,
					new BigDecimal("400"), destOrgCode, "到达部门");
			invoiceDao.addInvoice(entityTwo);

			InvoiceEntity entityThree = ConsumerTestUtil.getInvoiceEntity(waybillNo,
					sourceBillType, new BigDecimal("100"), destOrgCode, "到达部门");
			invoiceDao.addInvoice(entityThree);
			WaybillSettlementInfoDto dto = this.settlementInfoQueryService.queryInvoiceByWaybillNO(
					waybillNo, origOrgCode, destOrgCode, startDate, endDate);
			if (dto != null) {
				Assert.assertTrue(dto.getOrigInvoiceAmount() != null);
				logger.info(" 始发部门金额： " + dto.getOrigInvoiceAmount() + "  到达部门开票金额： "
						+ dto.getDestInvoiceAmount());

			}
		} catch (SettlementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据部门编码和部门创建日期查找该部门的应收、应付、预收、预付未核销的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午8:59:09
	 */
	@Test
	public void testQueryOrgSettlementInfo() {
		String orgCode = "GS00002";
		Date orgCreateTime = DateUtils.convert("2012-10-13", "yyyy-MM-dd");

		OrgSettlementInfoDto dto = this.settlementInfoQueryService.queryOrgSettlementInfo(orgCode,
				orgCreateTime);
		if (dto != null) {
			logger.info(" 应付未核销金额： " + dto.getPayableAmount() + " 应收未核销金额 "
					+ dto.getReceivableAmount() + " 预收未核销金额： " + dto.getDepositReceivedAmount()
					+ " 预付未核销金额 " + dto.getAdvancedPaymentAmount());
		}
	}

	/**
	 * 获取应付单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-3 上午11:08:38
	 * @param billType
	 * @return
	 * @see
	 */
	private String getPayableNO(String billType) {

		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD // 代收货款应付单
				.equals(billType)) {
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
				.equals(billType)) {
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF2);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST // 整车尾款应付单
				.equals(billType)) {
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF62);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(billType)) {// 理赔应付
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF3);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND.equals(billType)) {// 退运费应付
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF4);
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION
				.equals(billType)) {// 服务补救应付
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF5);
		}
		return "";
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
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS2);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS6);
		} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
				.equals(billType)) {
			return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS1);
		}
		return "";
	}

	/**
	 * 获取付款单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 下午4:07:11
	 * @param billType
	 * @return
	 */
	private String getPaymentNo(String billType) {
		return settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK2);
	}

	@Test
	@Ignore
	public void testQueryWaybillFinanceInfo() {
		String waybillNo = "201251806";
		WaybillFinanceInfoDto dto = settlementInfoQueryService.queryWaybillFinanceInfo(waybillNo);
		System.out.println(String.format("运单号:%s", dto.getWaybillNo()));
		if (dto.getOrigFeeInfo() != null) {
			System.out.println(String.format("【始发运费】\r\n付款方式：%s,实收始发运费:%s,收款日期:%s", dto
					.getOrigFeeInfo().getPaymentType(), dto.getOrigFeeInfo().getReceivedAmount(),
					DateUtils.convert(dto.getOrigFeeInfo().getReceivedDate())));
		}

		if (dto.getDestFeeInfo() != null) {
			DestFeeInfo df = dto.getDestFeeInfo();
			System.out.println(String.format("【到付费用】\r\n到付总额：%s,实收运费：%s，付款方式：%s,付款日期：%s",
					df.getTotalAmount(), df.getTransportAmount(),
					Arrays.toString(df.getPaymentTypes()), DateUtils.convert(df.getPaymentDate())));
		}

		if (dto.getCodFeeInfo() != null) {
			CODFeeInfo cf = dto.getCodFeeInfo();
			System.out.println(String.format(
					"【代收货款】\r\n实收代收货款：%s,付款方式:%s,冲应收金额:%s,应退金额:%s,付款状态:%s", cf.getCodAmount(),
					cf.getPaymentType(), cf.getVerifyRcvAmount(), cf.getReturnableAmount(),
					cf.getPaymentStatus()));
		}

		if (CollectionUtils.isNotEmpty(dto.getOtherFeeInfos())) {

			List<OtherFeeInfo> infos = dto.getOtherFeeInfos();
			System.out.println("【其它费用】");
			for (OtherFeeInfo info : infos) {
				System.out.println(String.format(
						"应付单单据类型:%s,应付金额:%s,付款方式:%s,付款状态:%s,冲应收金额:%s,应退金额:%s",
						info.getPayableBillType(), info.getAmount(), info.getPaymentType(),
						info.getPaymentStatus(), info.getVerifyRcvAmount(),
						info.getReturnableAmount()));
			}

		}

		if (CollectionUtils.isNotEmpty(dto.getInvoiceFeeInfos())) {

			List<InvoiceFeeInfo> infos = dto.getInvoiceFeeInfos();
			System.out.println("【开发票费用】");
			for (InvoiceFeeInfo info : infos) {
				System.out.println(String.format("开发票金额:%s,部门编码:%s,部门名称:%s", info.getAmount(),
						info.getOrgCode(), info.getOrgName()));
			}
		}
	}
}
