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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/writeoff/test/service/StatementModifyServiceTest.java
 * 
 * FILE NAME        	: StatementModifyServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
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
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.shared.define.SettlementWriteoffConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementToPaymentResultDto;
import com.deppon.foss.module.settlement.writeoff.test.BaseTestCase;
import com.deppon.foss.module.settlement.writeoff.test.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 修改对账单
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-9 下午7:00:12
 */
public class StatementModifyServiceTest extends BaseTestCase {

	@Autowired
	private IStatementOfAccountService statementOfAccountService;

	@Autowired
	private IStatementOfAccountDService statementOfAccountDService;

	@Autowired
	private IStatementModifyService statementModifyService;

	@Autowired
	private IBillReceivableService billReceivableService;

	@Autowired
	private IBillPayableService billPayableService;

	@Autowired
	private IBillDepositReceivedService billDepositReceivedService;

	@Autowired
	private IBillAdvancedPaymentService billAdvancedPaymentService;

	/**
	 * 
	 * 新增应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:43:21
	 */
	private BillReceivableEntity getBillReceivableEntity() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
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
	private BillReceivableEntity getBillBeginReceivableEntity() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
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
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		BillPayableEntity entity = new BillPayableEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo("YF10000001");
		entity.setWaybillNo("12345678");
		entity.setWaybillId("12345678");

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__AUDIT_AGREE);

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
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

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
	private BillDepositReceivedEntity getBillDepositReceivedEntity() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
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
	private BillAdvancedPaymentEntity getBillAdvancedPaymentEntity() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
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
	 * 新增对账单期初明细信息测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:04
	 */
	private List<StatementOfAccountDEntity> addBeginStatementOfAccountDEntity() {
		// 新增应收明细
		StatementOfAccountDEntity soadYSEntity = new StatementOfAccountDEntity();
		soadYSEntity.setId(UUIDUtils.getUUID() + "**test***");
		soadYSEntity.setStatementBillNo("N/A");
		soadYSEntity.setSourceBillNo("YS10000002");
		soadYSEntity.setWaybillNo("123456789");
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
		soadYSEntity.setAccountDate(new Date());
		soadYSEntity.setBusinessDate(new Date());
		soadYSEntity.setCreateDate(new Date());
		soadYSEntity.setCreateTime(new Date());
		soadYSEntity.setIsDelete(FossConstants.NO);
		List<StatementOfAccountDEntity> list = new ArrayList<StatementOfAccountDEntity>();
		list.add(soadYSEntity);
		Assert.assertEquals(1, list.size());
		return list;
	}

	/**
	 * 新增对账单本期明细信息测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:04
	 */

	private void addStatementOfAccountDEntity() {
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
		soadYSEntity.setAccountDate(new Date());
		soadYSEntity.setBusinessDate(new Date());
		soadYSEntity.setCreateDate(new Date());
		soadYSEntity.setCreateTime(new Date());
		soadYSEntity.setIsDelete(FossConstants.NO);
		int syRows = statementOfAccountDService.add(soadYSEntity);
		Assert.assertEquals(1, syRows);
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
		soadYFEntity.setAccountDate(new Date());
		soadYFEntity.setBusinessDate(new Date());
		soadYFEntity.setCreateDate(new Date());
		soadYFEntity.setCreateTime(new Date());
		soadYFEntity.setIsDelete(FossConstants.NO);
		soadYFEntity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
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
		soadUSEntity.setAccountDate(new Date());
		soadUSEntity.setBusinessDate(new Date());
		soadUSEntity.setCreateDate(new Date());
		soadUSEntity.setCreateTime(new Date());
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
		soadUFEntity.setAccountDate(new Date());
		soadUFEntity.setBusinessDate(new Date());
		soadUFEntity.setCreateDate(new Date());
		soadUFEntity.setCreateTime(new Date());
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
		soaEntity.setBillType("test客户对账单");
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
		soaEntity.setUnpaidAmount(new BigDecimal("40"));
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);
		soaEntity.setBusinessDate(DateUtils.addDays(now, 2));
		soaEntity.setCreateTime(DateUtils.addDays(now, 2));
		soaEntity.setVersionNo((short) 1);
		soaEntity.setPeriodBeginDate(now);
		soaEntity.setPeriodEndDate(DateUtils.addDays(now, 10));
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);

		return soaEntity;
	}

	/**
	 * 
	 * 测试新增对账单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-12 下午2:31:37
	 */
	@Test
	@Rollback(true)
	public void testAddStatementOfAccount() {
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		int rows = statementOfAccountService.add(soaEntity);
		Assert.assertEquals(1, rows);
	}

	/**
	 * 测试查询对账单方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 下午7:08:15
	 */
	@SuppressWarnings("unchecked")
	@Test
	@Rollback(true)
	public void testQueryStatement() {
		// 新增对账单明细和对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		int rows = statementOfAccountService.add(soaEntity);
		addStatementOfAccountDEntity();
		// 按日期查询
		StatementOfAccountMakeQueryDto queryDto = new StatementOfAccountMakeQueryDto();
		queryDto.setQueryPage(SettlementConstants.TAB_QUERY_BY_DATE);
		queryDto.setCustomerCode(soaEntity.getCustomerCode());
		queryDto.setOrgCode("test10001");
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);
		queryDto.setPeriodBeginDate(now);
		queryDto.setPeriodEndDate(DateUtils.addDays(now, 10));
		queryDto.setSettleStatus(SettlementWriteoffConstants.STATEMENT_SETTLEMENTSTATE_SETTLETYPE);
		StatementOfAccountMakeQueryResultDto dto = statementModifyService
				.queryStatement(queryDto,0,0);
		List<StatementOfAccountEntity> soaEntityList = dto
				.getStatementOfAccountList();
		Assert.assertEquals(0, soaEntityList.size());
		// 按对账单号查询
		StatementOfAccountMakeQueryDto queryDto2 = new StatementOfAccountMakeQueryDto();
		queryDto2.setQueryPage(SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO);
		String string[] = new String[] { soaEntity.getStatementBillNo() };
		queryDto2.setBillDetailNos(string);
		queryDto2.setOrgCode(soaEntity.getCreateOrgCode());
		StatementOfAccountMakeQueryResultDto dto2 = statementModifyService
				.queryStatement(queryDto,0,0);
		List<StatementOfAccountEntity> soaEntityList2 = dto2
				.getStatementOfAccountList();
		Assert.assertEquals(1, soaEntityList2.size());
		// 按运单号查询
		StatementOfAccountMakeQueryDto queryDto3 = new StatementOfAccountMakeQueryDto();
		queryDto3.setQueryPage(SettlementConstants.TAB_QUERY_BY_WAYBILL_NO);
		String string2[] = new String[] { "123456", "YS10000001" };
		queryDto3.setBillDetailNos(string2);
		queryDto3.setOrgCode(soaEntity.getCreateOrgCode());
		StatementOfAccountMakeQueryResultDto dto3 = statementModifyService
				.queryStatement(queryDto,0,0);
		List<StatementOfAccountEntity> soaEntityList3 = dto3
				.getStatementOfAccountList();
		Assert.assertEquals(1, soaEntityList3.size());
	}

	private void initOriDate() {
		BillReceivableEntity ysEntity = getBillReceivableEntity();
		BillPayableEntity yfEntity = getBillPayableEntity();
		BillDepositReceivedEntity usEntity = getBillDepositReceivedEntity();
		BillAdvancedPaymentEntity ufEntity = getBillAdvancedPaymentEntity();
		BillReceivableEntity ysBeginEntity = getBillBeginReceivableEntity();
	}

	/**
	 * 删除对账单明细时初始化对账单数据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-12 上午9:50:58
	 */
	private StatementOfAccountMakeQueryResultDto initDate() {
		// 初始化原始单据
		initOriDate();
		// 新增对账单明细
		addStatementOfAccountDEntity();
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		int rows = statementOfAccountService.add(soaEntity);
		// 创建结果DTO
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		// 获取期初信息
		List<StatementOfAccountDEntity> beginList = addBeginStatementOfAccountDEntity();
		// 获取本期明细信息
		List<StatementOfAccountDEntity> list = statementOfAccountDService
				.queryByStatementBillNo(soaEntity.getStatementBillNo());
		// 获取操作明细集合
		List<String> billNos = new ArrayList<String>();
		billNos.add("YS10000001");
		billNos.add("YF10000001");
		List<StatementOfAccountDEntity> operateList = statementOfAccountDService
				.queryByResourceNos(billNos);
		Assert.assertEquals(2, operateList.size());
		resultDto.setStatementOfAccountDBeginPeriodList(beginList);
		resultDto.setStatementOfAccountDPeriodList(list);
		List<String> statementNos = new ArrayList<String>();
		statementNos.add(soaEntity.getStatementBillNo());
		StatementOfAccountEntity entity = (StatementOfAccountEntity) statementOfAccountService
				.queryByStatementNos(statementNos).get(0);
		resultDto.setStatementOfAccount(entity);
		resultDto.setStatementOfAccountOperateList(operateList);
		return resultDto;
	}

	/**
	 * 增加对账单明细时初始化对账单数据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-12 上午9:50:58
	 */
	private StatementOfAccountMakeQueryResultDto initAddDate() {
		// 初始化原始单据
		initOriDate();
		// 新增对账单明细
		addStatementOfAccountDEntity();
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		int rows = statementOfAccountService.add(soaEntity);
		// 创建结果DTO
		StatementOfAccountMakeQueryResultDto resultDto = new StatementOfAccountMakeQueryResultDto();
		// 获取期初信息
		List<StatementOfAccountDEntity> beginList = addBeginStatementOfAccountDEntity();
		// 获取本期明细信息
		List<StatementOfAccountDEntity> list = statementOfAccountDService
				.queryByStatementBillNo(soaEntity.getStatementBillNo());
		// 获取操作明细集合
		List<StatementOfAccountDEntity> operateList = beginList;
		resultDto.setStatementOfAccountDBeginPeriodList(beginList);
		resultDto.setStatementOfAccountDPeriodList(list);
		resultDto.setStatementOfAccount(soaEntity);
		resultDto.setStatementOfAccountOperateList(operateList);
		return resultDto;
	}

	/**
	 * 测试删除对账单明细接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-12 上午8:45:56
	 */
	@Test
	@Rollback(true)
	public void testDeleteStatementDetail() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		StatementOfAccountMakeQueryResultDto resultDto = initDate();

		StatementOfAccountMakeQueryResultDto deleteResultDto = statementModifyService
				.deleteStatementDetail(resultDto, info);

		// 期初发生金额
		Assert.assertEquals(new BigDecimal("150"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodAmount());
		Assert.assertEquals(new BigDecimal("200"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodRecAmount());
		Assert.assertEquals(new BigDecimal("50"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodPayAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodPreAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodAdvAmount());

		// 本期发生金额
		Assert.assertEquals(new BigDecimal("-10"), deleteResultDto
				.getStatementOfAccount().getPeriodAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodRecAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodPayAmount());
		Assert.assertEquals(new BigDecimal("30"), deleteResultDto
				.getStatementOfAccount().getPeriodPreAmount());
		Assert.assertEquals(new BigDecimal("20"), deleteResultDto
				.getStatementOfAccount().getPeriodAdvAmount());

		// 本期剩余发生金额
		Assert.assertEquals(new BigDecimal("10"), deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyPreAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyRecAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodPayAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyAdvAmount());

		Assert.assertEquals(3, deleteResultDto
				.getStatementOfAccountDBeginPeriodList().size());
		Assert.assertEquals(2, deleteResultDto
				.getStatementOfAccountDPeriodList().size());

	}

	/**
	 * 测试增加对账单明细接口
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-12 上午8:45:56
	 */
	@Test
	@Rollback(true)
	public void testAddStatementDetail() {
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		StatementOfAccountMakeQueryResultDto resultDto = initAddDate();
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("test");
		employee.setEmpName("test");
		user.setEmployee(employee);
		resultDto.setUser(user);
		StatementOfAccountMakeQueryResultDto deleteResultDto = statementModifyService
				.addStatementDetail(resultDto, info);

		// 期初发生金额
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodRecAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodPayAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodPreAmount());
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getBeginPeriodAdvAmount());

		// 本期发生金额
		Assert.assertEquals(new BigDecimal("140"), deleteResultDto
				.getStatementOfAccount().getPeriodAmount());
		Assert.assertEquals(new BigDecimal("200"), deleteResultDto
				.getStatementOfAccount().getPeriodRecAmount());
		Assert.assertEquals(new BigDecimal("50"), deleteResultDto
				.getStatementOfAccount().getPeriodPayAmount());
		Assert.assertEquals(new BigDecimal("30"), deleteResultDto
				.getStatementOfAccount().getPeriodPreAmount());
		Assert.assertEquals(new BigDecimal("20"), deleteResultDto
				.getStatementOfAccount().getPeriodAdvAmount());

		// 本期剩余发生金额
		Assert.assertEquals(new BigDecimal("0"), deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyPreAmount());
		Assert.assertEquals(new BigDecimal("120"), deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyRecAmount());
		Assert.assertEquals(BigDecimal.ZERO, deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyPayAmount());
		Assert.assertEquals(new BigDecimal("20"), deleteResultDto
				.getStatementOfAccount().getPeriodUnverifyAdvAmount());

		Assert.assertEquals(0, deleteResultDto
				.getStatementOfAccountDBeginPeriodList().size());
		Assert.assertEquals(5, deleteResultDto
				.getStatementOfAccountDPeriodList().size());

	}

	/**
	 * 测试跳转到对账单还款界面
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-27 下午6:28:24
	 */
	@Test
	@Rollback(true)
	public void repaymentBatchTest() {

		// 获取用户信息
		CurrentInfo info = CommonTestUtil.getCurrentInfo();
		StatementOfAccountMakeQueryResultDto queryDto = new StatementOfAccountMakeQueryResultDto();

		// 生成对账单号
		List<String> statementNos = new ArrayList<String>();

		statementNos.add("DZ00000941");
		statementNos.add("DZ00000901");

		// 根据对账单号查询对账单列表
		List<StatementOfAccountEntity> statementList = statementOfAccountService
				.queryByStatementNos(statementNos);
		queryDto.setStatementOfAccountList(statementList);

		try {
			StatementToPaymentResultDto rtnDto = statementModifyService
					.repaymentBatch(queryDto, info);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}

	}
}
