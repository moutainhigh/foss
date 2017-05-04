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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/waybill/StlPaymentSettlementServiceTest.java
 * 
 * FILE NAME        	: StlPaymentSettlementServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.waybill;

import java.math.BigDecimal;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.consumer.api.server.service.IPaymentSettlementService;
import com.deppon.foss.module.settlement.web.test.util.TestUtil;

import junit.framework.TestCase;

/**
 * 到付转临欠月结 反到付转临欠月结 实收货款 反实收货款 Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-31 下午8:40:40
 * @since
 * @version
 */
public class StlPaymentSettlementServiceTest extends TestCase {

	/**
	 * 
	 */
	private PaymentSettlementDto dto;

	/**
	 * 
	 */
	private IPaymentSettlementService paymentSettlemntService;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 运单到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 到达部门操作者编码
	 */
	private String destUserCode;

	/**
	 * 到达部门操作者名称
	 */
	private String destUserName;

	/**
	 * 实收代收货款
	 * 
	 */
	private BigDecimal codFee;

	/**
	 * 实收到付运费
	 */
	private BigDecimal toPayFee;

	/**
	 * 汇款编号
	 */
	private String paymentNo;

	/**
	 * 实收单号
	 */
	private String sourceBillNo;

	/**
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午8:41:46
	 * @throws Exception
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * 到付运费转月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午8:42:26
	 */
	public void testConfirmToBillReceivableCt() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDtoToConfirmRece(waybillNo, 
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						destOrgCode, destOrgName);

		paymentSettlemntService.confirmToBillReceivable(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	/**
	 * 到付运费转临欠
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午4:49:29
	 */
	public void testConfirmToBillReceivableDt() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDtoToConfirmRece(waybillNo, paymentType,
						destOrgCode, destOrgName);

		paymentSettlemntService.confirmToBillReceivable(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}

	/**
	 * 反到付运费结转临欠/月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午3:15:48
	 */
	public void testReversConfirmToBillReceiveable() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDtoToConfirmRece(waybillNo, paymentType,
						destOrgCode, destOrgName);
		paymentSettlemntService.reversConfirmToBillReceiveable(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}

	/**
	 * 实收货款,付款方式现金
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午1:52:23
	 */
	public void testConfirmToPaymentForCash() {
		PaymentSettlementDto dto = TestUtil.getPaymentSettlementDto(waybillNo,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
				destOrgCode, destOrgName, this.getCodFee(), this.getToPayFee(),
				""//汇款编号为空
				, this.getSourceBillNo());
		paymentSettlemntService.confirmToPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	/**
	 * 实收货款,付款方式银行卡
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午1:52:23
	 */
	public void testConfirmToPaymentForCard() {
		PaymentSettlementDto dto = TestUtil.getPaymentSettlementDto(waybillNo,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				destOrgCode, destOrgName, this.getCodFee(), this.getToPayFee(),
				""//汇款编号为空
				, this.getSourceBillNo());
		paymentSettlemntService.confirmToPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	
	/**
	 * 实收货款,付款方式电汇
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午1:52:23
	 */
	public void testConfirmToPaymentForTT() {
		PaymentSettlementDto dto = TestUtil.getPaymentSettlementDto(waybillNo,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER,
				destOrgCode, destOrgName, this.getCodFee(), this.getToPayFee(),
				this.getPaymentNo(), this.getSourceBillNo());
		paymentSettlemntService.confirmToPayment(
				dto,TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	/**
	 * 实收货款,付款方式支票
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午1:52:23
	 */
	public void testConfirmToPaymentForNT() {
		PaymentSettlementDto dto = TestUtil.getPaymentSettlementDto(waybillNo,
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE,
				destOrgCode, destOrgName, this.getCodFee(), this.getToPayFee(),
				this.getPaymentNo(), this.getSourceBillNo());
		paymentSettlemntService.confirmToPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	
	

	/**
	 * 反实收货款 ---现金
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午3:19:47
	 */
	public void testReversConfirmPaymentForCash() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDto(waybillNo, 
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
						destOrgCode,destOrgName, this.getCodFee(), this.getToPayFee(),
						"", this.getSourceBillNo());
		paymentSettlemntService.reversConfirmPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	/**
	 * 反实收货款--银行卡
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午3:19:47
	 */
	public void testReversConfirmPaymentForCard() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDto(waybillNo, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD, destOrgCode,
						destOrgName, this.getCodFee(), this.getToPayFee(),
						"", this.getSourceBillNo());
		paymentSettlemntService.reversConfirmPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}
	
	/**
	 * 反实收货款-电汇
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午5:33:55
	 */
	public void testReversConfirmPaymentForTT() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDto(waybillNo, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER, 
						destOrgCode,
						destOrgName, this.getCodFee(), this.getToPayFee(),
						"", this.getSourceBillNo());
		paymentSettlemntService.reversConfirmPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}

	
	/**
	 * 反实收货款-支票
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午5:33:55
	 */
	public void testReversConfirmPaymentForNT() {
		PaymentSettlementDto dto = TestUtil
				.getPaymentSettlementDto(waybillNo, SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE, 
						destOrgCode,
						destOrgName, this.getCodFee(), this.getToPayFee(),
						"", this.getSourceBillNo());
		paymentSettlemntService.reversConfirmPayment(
				dto,
				TestUtil.getCurrentInfo(this.getDestUserCode(),
						this.getDestUserName(), this.getDestOrgCode(),
						this.getDestOrgName()));
	}

	
	/**
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-31 下午8:41:46
	 * @throws Exception
	 */
	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	/**
	 * @return the dto
	 */
	public PaymentSettlementDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(PaymentSettlementDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the paymentSettlemntService
	 */
	public IPaymentSettlementService getPaymentSettlemntService() {
		return paymentSettlemntService;
	}

	/**
	 * @param paymentSettlemntService
	 *            the paymentSettlemntService to set
	 */
	public void setPaymentSettlemntService(
			IPaymentSettlementService paymentSettlemntService) {
		this.paymentSettlemntService = paymentSettlemntService;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 *            the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 *            the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return the destUserCode
	 */
	public String getDestUserCode() {
		return destUserCode;
	}

	/**
	 * @param destUserCode
	 *            the destUserCode to set
	 */
	public void setDestUserCode(String destUserCode) {
		this.destUserCode = destUserCode;
	}

	/**
	 * @return the destUserName
	 */
	public String getDestUserName() {
		return destUserName;
	}

	/**
	 * @param destUserName
	 *            the destUserName to set
	 */
	public void setDestUserName(String destUserName) {
		this.destUserName = destUserName;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the codFee
	 */
	public BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * @param codFee
	 *            the codFee to set
	 */
	public void setCodFee(BigDecimal codFee) {
		this.codFee = codFee;
	}

	/**
	 * @return the toPayFee
	 */
	public BigDecimal getToPayFee() {
		return toPayFee;
	}

	/**
	 * @param toPayFee
	 *            the toPayFee to set
	 */
	public void setToPayFee(BigDecimal toPayFee) {
		this.toPayFee = toPayFee;
	}

	/**
	 * @return the paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * @param paymentNo
	 *            the paymentNo to set
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * @return the sourceBillNo
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	/**
	 * @param sourceBillNo
	 *            the sourceBillNo to set
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

}
