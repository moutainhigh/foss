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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/OnlinePaymentStatementsServiceTest.java
 * 
 * FILE NAME        	: OnlinePaymentStatementsServiceTest.java
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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IOnlinePaymentStatementsService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.PayTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 网上支付对账单测试类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-30 上午11:21:27
 */
public class OnlinePaymentStatementsServiceTest extends BaseTestCase {
	@Autowired
	private IOnlinePaymentStatementsService onlinePaymentStatementsService;
	@Autowired
	private IStatementOfAccountService statementOfAccountService;
	@Autowired
	private IStatementOfAccountDService statementOfAccountDService;
	@Autowired
	private IBillAdvancedPaymentService billAdvancedPaymentService;
	@Autowired
	private IBillReceivableService billReceivableService;
	@Autowired
	private IBillPayableService billPayableService;
	@Autowired
	private IBillDepositReceivedService billDepositReceivedService;
	/**
	 * 
	 * 新增应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:43:21
	 */
	private BillReceivableEntity getBillReceivableEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId("12345678");// 运单Id
		entity.setWaybillNo("12345678");// 运单号
		entity.setReceivableNo("YS10000001");// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo("12345678");
		entity.setSourceBillType("1");// 来源单据类型
		entity.setBillType("1");// 单据子类型 -到付应收单
		entity.setPaymentType("1");// 付款方式-到付
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
		entity.setAmount(new BigDecimal("100"));// 总金额
		entity.setUnverifyAmount(new BigDecimal("100"));// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(100));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(700));// 增值费用
		entity.setPromotionsFee(new BigDecimal(100));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(new Date());// 设置业务日期
		entity.setAccountDate(new Date());// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billReceivableService.addBillReceivable(entity, info);
		return entity;
	}

	/**
	 * 
	 * 新增应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:43:21
	 */
	@SuppressWarnings("unused")
	private BillReceivableEntity getBillBeginReceivableEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillReceivableEntity entity = new BillReceivableEntity();
		String uuid = UUIDUtils.getUUID();
		entity.setId(uuid);// 主键
		entity.setWaybillId("12345678");// 运单Id
		entity.setWaybillNo("12345678");// 运单号
		entity.setReceivableNo("YS10000002");// 应收单号
		entity.setCreateType("1");// 系统自动生成
		entity.setSourceBillNo("12345678");
		entity.setSourceBillType("1");// 来源单据类型
		entity.setBillType("1");// 单据子类型 -到付应收单
		entity.setPaymentType("1");// 付款方式-到付
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
		entity.setAmount(new BigDecimal("100"));// 总金额
		entity.setUnverifyAmount(new BigDecimal("100"));// 未核销金额
		entity.setVerifyAmount(new BigDecimal(0));// 已核销金额
		entity.setTransportFee(new BigDecimal(100));// 公布价运费
		entity.setPickupFee(new BigDecimal(100));// 接货费
		entity.setDeliveryGoodsFee(new BigDecimal(100));// 送货费
		entity.setPackagingFee(new BigDecimal(100));// 包装手续费
		entity.setCodFee(new BigDecimal(100));// 代收货款手续费
		entity.setInsuranceFee(new BigDecimal(100));// 保价费
		entity.setOtherFee(new BigDecimal(100));// 其他费用

		entity.setValueAddFee(new BigDecimal(700));// 增值费用
		entity.setPromotionsFee(new BigDecimal(100));// 优惠费用
		entity.setCurrencyCode("1");// 货币 1 人民币

		// 提货方式
		entity.setReceiveMethod("1");
		entity.setBusinessDate(new Date());// 设置业务日期
		entity.setAccountDate(new Date());// 记账日期
		entity.setProductCode("1");// 产品类型 1精准汽运
		entity.setActive("Y");// 是否有效 默认为Y
		entity.setIsRedBack("N");// 是否为红单 默认为否
		entity.setIsInit("N");// 是否初始化 否
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billReceivableService.addBillReceivable(entity, info);
		return entity;
	}

	/**
	 * 
	 * 新增应付单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:56:02
	 */
	private BillPayableEntity getBillPayableEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillPayableEntity entity = new BillPayableEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo("YF10000001");
		entity.setWaybillNo("12345678");
		entity.setWaybillId("12345678");

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType("1");
		entity.setSourceBillNo("12345678");
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 整车尾款应付单-到达部门来付钱
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals("1")) {
			entity.setPayableOrgCode("CUST_CODE");
		} else {
			entity.setPayableOrgCode("ORIG_CODE");
		}

		entity.setPayableOrgName("上海营业部"); // TODO

		entity.setPayableComCode("GS1"); // TODO
		entity.setPayableComName("上海德邦物流");

		// 设置出发部门编码、名称
		entity.setOrigOrgCode("CF1");
		entity.setOrigOrgName("上海青浦营业部"); // TODO

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode("DEST1");
		entity.setDestOrgName("北京大营门营业部"); // TODO

		// 设置应付客户编码、名称
		entity.setCustomerCode("YFKH");
		entity.setCustomerName("张三");
		entity.setCustomerContact(""); // TODO
		entity.setCustomerContactName(""); // TODO
		entity.setCustomerPhone("100");
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);

		// 设置金额、已核销金额、未核销金额
		entity.setAmount(NumberUtils.createBigDecimal("50"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(new Date());
		entity.setBusinessDate(new Date());

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS"); // TODO
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN"); // TODO

		// 是否有效、是否红单、是否初始化、版本号
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setIsInit(FossConstants.NO);
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		entity.setCreateTime(new Date());
		entity.setModifyTime(new Date());
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		billPayableService.addBillPayable(entity, info);
		return entity;
	}

	/**
	 * 
	 * 新增预收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午5:21:03
	 */
	@SuppressWarnings("unused")
	private BillDepositReceivedEntity getBillDepositReceivedEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setDepositReceivedNo("US10000001");
		entity.setCustomerCode("test");
		entity.setCustomerName("test");
		entity.setAmount(new BigDecimal("30"));
		entity.setVerifyAmount(new BigDecimal("0"));
		entity.setUnverifyAmount(new BigDecimal("30"));
		entity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(FossConstants.NO);
		entity.setPaymentType("00");
		entity.setCreateUserCode("test");
		entity.setBusinessDate(new Date());
		entity.setAccountDate(new Date());
		entity.setCreateTime(new Date());
		entity.setIsInit(FossConstants.NO);
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号 1
		int row = billDepositReceivedService.addBillDepositReceived(entity,
				info);
		return entity;

	}

	/**
	 * 
	 * 新增预付单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午5:21:03
	 */
	@SuppressWarnings("unused")
	private BillAdvancedPaymentEntity getBillAdvancedPaymentEntity() {
		CurrentInfo info = PayTestUtil.getCurrentInfo();
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setAdvancesNo("UF10000001");
		entity.setCustomerCode("test");
		entity.setCustomerName("test");
		entity.setAmount(new BigDecimal("20"));
		entity.setVerifyAmount(new BigDecimal("0"));
		entity.setUnverifyAmount(new BigDecimal("20"));
		entity.setApplyOrgCode("test");
		entity.setApplyOrgName("test");
		entity.setPaymentOrgCode("test");
		entity.setPaymentOrgName("test");

		entity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(FossConstants.NO);
		entity.setPaymentType("00");
		entity.setCreateUserCode("test");
		entity.setBusinessDate(new Date());
		entity.setAccountDate(new Date());
		entity.setCreateTime(new Date());
		entity.setIsInit(FossConstants.NO);
		entity.setMobilePhone("123456789");
		entity.setAccountNo("123");
		entity.setPayeeName("123");
		entity.setPublicPrivateFlag("123");
		entity.setProvinceName("123");
		entity.setProvinceCode("234");
		entity.setCityName("123");
		entity.setCityCode("123");
		entity.setBankHqName("123");
		entity.setBankBranchName("123");
		entity.setBankBranchCode("123");
		entity.setVersionNo((short) 1);
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		int row = billAdvancedPaymentService.addAdvancedPaymentEntity(entity,
				info);

		return entity;

	}
	/**
	 * 新增对账单本期明细信息测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:04
	 */

	@SuppressWarnings("unused")
	private void addStatementOfAccountDEntity(Date createTime) {
		// 新增应收明细
		StatementOfAccountDEntity soadYSEntity = new StatementOfAccountDEntity();
		soadYSEntity.setId(UUIDUtils.getUUID());
		soadYSEntity.setStatementBillNo("DZ1000001");
		soadYSEntity.setSourceBillNo("YS10000001");
		soadYSEntity.setWaybillNo("123456");
		soadYSEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		soadYSEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadYSEntity.setAmount(new BigDecimal("100"));
		soadYSEntity.setVerifyAmount(new BigDecimal("0"));
		soadYSEntity.setUnverifyAmount(new BigDecimal("100"));
		soadYSEntity.setOrgCode("test100001");
		soadYSEntity.setOrgName("test10001");
		soadYSEntity.setCustomerCode("test123456");
		soadYSEntity.setCustomerName("test123456");
		soadYSEntity.setAccountDate(createTime);
		soadYSEntity.setBusinessDate(createTime);
		soadYSEntity.setCreateDate(createTime);
		soadYSEntity.setCreateTime(createTime);
		soadYSEntity.setIsDelete(FossConstants.NO);
		int syRows = statementOfAccountDService.add(soadYSEntity);
		// 新增应付明细
		StatementOfAccountDEntity soadYFEntity = new StatementOfAccountDEntity();
		soadYFEntity.setId(UUIDUtils.getUUID());
		soadYFEntity.setStatementBillNo("DZ1000001");
		soadYFEntity.setSourceBillNo("YF10000001");
		soadYFEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE);
		soadYFEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadYFEntity.setAmount(new BigDecimal("50"));
		soadYFEntity.setVerifyAmount(new BigDecimal("0"));
		soadYFEntity.setUnverifyAmount(new BigDecimal("50"));
		soadYFEntity.setOrgCode("test100001");
		soadYFEntity.setOrgName("test10001");
		soadYFEntity.setCustomerCode("test123456");
		soadYFEntity.setCustomerName("test123456");
		soadYFEntity.setAccountDate(createTime);
		soadYFEntity.setBusinessDate(createTime);
		soadYFEntity.setCreateDate(createTime);
		soadYFEntity.setCreateTime(createTime);
		soadYFEntity.setIsDelete(FossConstants.NO);
		int yfRows = statementOfAccountDService.add(soadYFEntity);
		Assert.assertEquals(1, yfRows);
		// 新增预收明细
		StatementOfAccountDEntity soadUSEntity = new StatementOfAccountDEntity();
		soadUSEntity.setId(UUIDUtils.getUUID());
		soadUSEntity.setStatementBillNo("DZ1000001");
		soadUSEntity.setSourceBillNo("US10000001");
		soadUSEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		soadUSEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadUSEntity.setAmount(new BigDecimal("30"));
		soadUSEntity.setVerifyAmount(new BigDecimal("0"));
		soadUSEntity.setUnverifyAmount(new BigDecimal("30"));
		soadUSEntity.setOrgCode("test100001");
		soadUSEntity.setOrgName("test10001");
		soadUSEntity.setCustomerCode("test123456");
		soadUSEntity.setCustomerName("test123456");
		soadUSEntity.setAccountDate(createTime);
		soadUSEntity.setBusinessDate(createTime);
		soadUSEntity.setCreateDate(createTime);
		soadUSEntity.setCreateTime(createTime);
		soadUSEntity.setIsDelete(FossConstants.NO);
		int usRows = statementOfAccountDService.add(soadUSEntity);
		Assert.assertEquals(1, usRows);
		// 新增预付明细
		StatementOfAccountDEntity soadUFEntity = new StatementOfAccountDEntity();
		soadUFEntity.setId(UUIDUtils.getUUID());
		soadUFEntity.setStatementBillNo("DZ1000001");
		soadUFEntity.setSourceBillNo("UF10000001");
		soadUFEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		soadUFEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadUFEntity.setAmount(new BigDecimal("20"));
		soadUFEntity.setVerifyAmount(new BigDecimal("0"));
		soadUFEntity.setUnverifyAmount(new BigDecimal("20"));
		soadUFEntity.setOrgCode("test100001");
		soadUFEntity.setOrgName("test10001");
		soadUFEntity.setCustomerCode("test123456");
		soadUFEntity.setCustomerName("test123456");
		soadUFEntity.setAccountDate(createTime);
		soadUFEntity.setBusinessDate(createTime);
		soadUFEntity.setCreateDate(createTime);
		soadUFEntity.setCreateTime(createTime);
		soadUFEntity.setIsDelete(FossConstants.NO);
		int ufRows = statementOfAccountDService.add(soadUFEntity);
		Assert.assertEquals(1, ufRows);
	}

	/**
	 * 新增对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:27
	 */

	private StatementOfAccountEntity addStatementAccountEntity() {
		StatementOfAccountEntity soaEntity = new StatementOfAccountEntity();
		soaEntity.setId(UUIDUtils.getUUID());
		soaEntity.setStatementBillNo("DZ1000001");
		soaEntity.setCreateOrgCode("test10001");
		soaEntity.setCreateOrgName("test10001");
		soaEntity.setCustomerCode("test100001");
		soaEntity.setCustomerName("test100001");
		soaEntity.setBillType("test客户对账单");
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
		soaEntity.setBeginPeriodAmount(new BigDecimal("100"));
		soaEntity.setBeginPeriodAdvAmount(new BigDecimal("0"));
		soaEntity.setBeginPeriodPayAmount(new BigDecimal("0"));
		soaEntity.setBeginPeriodPreAmount(new BigDecimal("0"));
		soaEntity.setBeginPeriodRecAmount(new BigDecimal("100"));
		soaEntity.setPeriodAmount(new BigDecimal("40"));
		soaEntity.setPeriodAdvAmount(new BigDecimal("20"));
		soaEntity.setPeriodPayAmount(new BigDecimal("50"));
		soaEntity.setPeriodPreAmount(new BigDecimal("30"));
		soaEntity.setPeriodRecAmount(new BigDecimal("100"));
		soaEntity.setPeriodUnverifyAdvAmount(new BigDecimal("20"));
		soaEntity.setPeriodUnverifyPayAmount(new BigDecimal("0"));
		soaEntity.setPeriodUnverifyPreAmount(new BigDecimal("0"));
		soaEntity.setPeriodUnverifyRecAmount(new BigDecimal("20"));
		soaEntity.setUnifiedCode("test0001");
		soaEntity.setUnpaidAmount(new BigDecimal("20"));
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);
		soaEntity.setBusinessDate(DateUtils.addDays(now, 2));
		soaEntity.setCreateTime(DateUtils.addDays(now, 2));
		soaEntity.setVersionNo((short) 1);
		soaEntity.setPeriodBeginDate(now);
		soaEntity.setSettleNum((short)0);
		soaEntity.setPeriodEndDate(DateUtils.addDays(now, 10));
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);

		return soaEntity;
	}

	/**
	 * 
	 * 网上支付查询对账单信息(按日期)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	@SuppressWarnings("unused")
	@Test
	public void queryStatementOnlineByDateTest() {
		// 新增对账单明细和对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity(soaEntity.getCreateTime());
		int rows = statementOfAccountService.add(soaEntity);
		StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
		queryDto.setPageNo(0);
		queryDto.setPageSize(10);
		queryDto.setCustomerCode(soaEntity.getCustomerCode());
		queryDto.setBeginDate(DateUtils.addDays(soaEntity.getBusinessDate(), -2));
		queryDto.setEndDate(DateUtils.addDays(soaEntity.getBusinessDate(), 4));
		BillSOAOnlineResultListDto resultDto = onlinePaymentStatementsService
				.queryStatementOnline(queryDto);
		Assert.assertNotNull(resultDto);
		Assert.assertEquals(1, resultDto.getBillSOAOnlineResultDtoList().size());
		Assert.assertEquals(1, resultDto.getBillSOAOnlineResultDtoList().get(0)
				.getCountDetailNum());
		Assert.assertEquals(1, resultDto.getCountNum());
	}

	/**
	 * 
	 * 网上支付查询对账单信息（单号）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	@SuppressWarnings("unused")
	@Test
	public void queryStatementOnlineByNoTest() {
		// 新增对账单明细和对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity(soaEntity.getCreateTime());
		int rows = statementOfAccountService.add(soaEntity);
		StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
		queryDto.setPageNo(0);
		queryDto.setPageSize(10);
		queryDto.setCustomerCode(soaEntity.getCustomerCode());
		queryDto.setStatementBillNo(soaEntity.getStatementBillNo());
		BillSOAOnlineResultListDto resultDto = onlinePaymentStatementsService
				.queryStatementOnline(queryDto);
		Assert.assertNotNull(resultDto);
		Assert.assertEquals(1, resultDto.getBillSOAOnlineResultDtoList().size());
		Assert.assertEquals(1, resultDto.getBillSOAOnlineResultDtoList().get(0)
				.getCountDetailNum());
		Assert.assertEquals(1, resultDto.getCountNum());
	}

	/**
	 * 
	 * 网上支付锁定对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	@SuppressWarnings("unused")
	@Test
	public void lockStatementOnlineByNoTest() {
		// 新增对账单明细和对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity(soaEntity.getCreateTime());
		int rows = statementOfAccountService.add(soaEntity);
		StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
		queryDto.setStatementBillNo(soaEntity.getStatementBillNo());
		try {
			onlinePaymentStatementsService.lockStatementOnline(queryDto);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}

	}
	
	/**
	 * 
	 * 网上支付按对账单还款
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午11:33:38
	 */
	@SuppressWarnings("unused")
	@Test
	public void repaymentStatementOnlineByNoTest() {
		// 应收、应付、预收、预付
		BillReceivableEntity rsEntity=getBillReceivableEntity();
		BillPayableEntity yfEntity=getBillPayableEntity();
		BillDepositReceivedEntity usEntity=getBillDepositReceivedEntity();
		BillAdvancedPaymentEntity ufEntity=getBillAdvancedPaymentEntity();
		// 新增对账单明细和对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity(soaEntity.getCreateTime());
		int rows = statementOfAccountService.add(soaEntity);
		StatementOnlineQueryDto queryDto = new StatementOnlineQueryDto();
		queryDto.setStatementBillNo(soaEntity.getStatementBillNo());
		queryDto.setAmount(soaEntity.getUnpaidAmount());
		queryDto.setOnlineNo("123456");
		try {
			onlinePaymentStatementsService.paymentStatementOnline(queryDto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

}
