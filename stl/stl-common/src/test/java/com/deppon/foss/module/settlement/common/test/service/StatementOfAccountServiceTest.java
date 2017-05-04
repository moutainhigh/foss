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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/StatementOfAccountServiceTest.java
 * 
 * FILE NAME        	: StatementOfAccountServiceTest.java
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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单公共服务测试类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-26 下午1:54:59
 */
public class StatementOfAccountServiceTest extends BaseTestCase {

	@Autowired
	private IStatementOfAccountService statementOfAccountService;

	@Autowired
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 
	 * 新增应收单
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午4:43:21
	 */
	private BillReceivableEntity getBillReceivableEntity() {
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
		entity.setAmount(new BigDecimal(1000));// 总金额
		entity.setUnverifyAmount(entity.getAmount());// 未核销金额
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
		entity.setStatementBillNo("DZ1000001");
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
		entity.setAmount(NumberUtils.createBigDecimal("1000"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(new Date());
		entity.setBusinessDate(new Date());

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS");
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN");

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
		entity.setStatementBillNo("DZ1000001");
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
		BillDepositReceivedEntity entity = new BillDepositReceivedEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setDepositReceivedNo("US10000001");
		entity.setCustomerCode("test");
		entity.setCustomerName("test");
		entity.setAmount(new BigDecimal("10000"));
		entity.setVerifyAmount(new BigDecimal("5000"));
		entity.setUnverifyAmount(new BigDecimal("5000"));
		entity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(FossConstants.NO);
		entity.setPaymentType("00");
		entity.setCreateUserCode("test");
		entity.setBusinessDate(new Date());
		entity.setAccountDate(new Date());
		entity.setCreateTime(new Date());
		entity.setIsInit(FossConstants.NO);
		entity.setStatementBillNo("DZ1000001");
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
		BillAdvancedPaymentEntity entity = new BillAdvancedPaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setAdvancesNo("UF10000001");
		entity.setCustomerCode("test");
		entity.setCustomerName("test");
		entity.setAmount(new BigDecimal("10000"));
		entity.setVerifyAmount(new BigDecimal("5000"));
		entity.setUnverifyAmount(new BigDecimal("5000"));
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
//		entity.setAccountUserName("123");
		entity.setPublicPrivateFlag("123");
//		entity.setAccountBankProvince("123");
//		entity.setBankName("123");
		entity.setBankBranchName("123");
//		entity.setBankCode("123");
		entity.setVersionNo((short) 1);
		entity.setStatementBillNo("DZ1000001");

		return entity;

	}

	/**
	 * 新增对账单明细信息测试
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
		soadYSEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		soadYSEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadYSEntity.setAmount(new BigDecimal(10000));
		soadYSEntity.setVerifyAmount(new BigDecimal(2000));
		soadYSEntity.setUnverifyAmount(new BigDecimal(8000));
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
		soadYFEntity.setAmount(new BigDecimal("10000"));
		soadYFEntity.setVerifyAmount(new BigDecimal("2000"));
		soadYFEntity.setUnverifyAmount(new BigDecimal("8000"));
		soadYFEntity.setOrgCode("test100001");
		soadYFEntity.setOrgName("test10001");
		soadYFEntity.setCustomerCode("test123456");
		soadYFEntity.setCustomerName("test123456");
		soadYFEntity.setAccountDate(new Date());
		soadYFEntity.setBusinessDate(new Date());
		soadYFEntity.setCreateDate(new Date());
		soadYFEntity.setCreateTime(new Date());
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
		soadUSEntity.setAmount(new BigDecimal(10000));
		soadUSEntity.setVerifyAmount(new BigDecimal(2000));
		soadUSEntity.setUnverifyAmount(new BigDecimal(8000));
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
		soadUFEntity.setAmount(new BigDecimal("10000"));
		soadUFEntity.setVerifyAmount(new BigDecimal("2000"));
		soadUFEntity.setUnverifyAmount(new BigDecimal("8000"));
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
		soaEntity.setSettleNum((short) 0);
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
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),Calendar.SECOND);
		soaEntity.setBusinessDate(DateUtils.addDays(now, 2));
		soaEntity.setCreateTime(DateUtils.addDays(now, 2));
		soaEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		soaEntity.setPeriodBeginDate(now);
		soaEntity.setPeriodEndDate(DateUtils.addDays(now, 10));
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);
		int rows = statementOfAccountService.add(soaEntity);
		Assert.assertEquals(1, rows);
		return soaEntity;
	}

	/**
	 * 测试根据对账单号判断对账单是否确认 被测试的方法：queryConfirmStatmentOfAccount
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:23:44
	 */
	@Test
	public void testStatementOfAccountService() {
		// 新增对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();

		// 如果传入的单号存在
		List<String> yList = new ArrayList<String>();
		yList.add(soaEntity.getStatementBillNo());
		Assert.assertEquals(0, statementOfAccountService
				.queryConfirmStatmentOfAccount(yList).size());

		// 如果传入的单号不存在
		List<String> nlist = new ArrayList<String>();
		nlist.add("DZ1000000");
		Assert.assertEquals(0, statementOfAccountService
				.queryConfirmStatmentOfAccount(nlist).size());

		// 如果集合为空
		List<String> list = new ArrayList<String>();
		Assert.assertEquals(0, statementOfAccountService
				.queryConfirmStatmentOfAccount(list).size());

		// 如果集合的长度超过1000
		List<String> mlist = new ArrayList<String>();
		mlist.add("DZ1000001");
		for (int i = 0; i < 2000; i++) {
			mlist.add("DZ" + i);
		}
		Assert.assertEquals(0, statementOfAccountService
				.queryConfirmStatmentOfAccount(nlist).size());
	}

	/**
	 * 测试核销、反核销、红冲时，修改对账单及对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-8 下午3:43:25
	 */
	@Test
	public void testUpdateStatementAndDetailForWriteOff() {
		CurrentInfo info=CommonTestUtil.getCurrentInfo();
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		// 新增对账单明细
		addStatementOfAccountDEntity();
		StatementOfAccountUpdateDto dto = new StatementOfAccountUpdateDto();
		// 测试根据对账单号查询对账单明细单据
		List<StatementOfAccountDEntity> soadList = statementOfAccountDService
				.queryByStatementBillNo(soaEntity.getStatementBillNo());
		Assert.assertEquals(4, soadList.size());
		// 测试测试核销、反核销、红冲时，修改对账单及对账单明细方法
		// 首先为DTO赋值
		// 获取应收单实体
		List<BillReceivableEntity> rsList = new ArrayList<BillReceivableEntity>();
		rsList.add(getBillReceivableEntity());
		dto.setReceivableEntityList(rsList);
		// 获取应付单实体
		List<BillPayableEntity> yfList = new ArrayList<BillPayableEntity>();
		yfList.add(getBillPayableEntity());
		dto.setPayableEntityList(yfList);
		// 获取预收单信息
		List<BillDepositReceivedEntity> usList = new ArrayList<BillDepositReceivedEntity>();
		usList.add(getBillDepositReceivedEntity());
		dto.setDepositReceivedEntityList(usList);
		// 获取预收单信息
		List<BillAdvancedPaymentEntity> ufList = new ArrayList<BillAdvancedPaymentEntity>();
		ufList.add(getBillAdvancedPaymentEntity());
		dto.setAdvancePaymentEntityList(ufList);
		// 测试核销时修改对账单信息
		statementOfAccountService.updateStatementAndDetailForWriteOff(dto,info);
		// 测试反核销时修改对账单信息
		statementOfAccountService.updateStatementAndDetailForBackWriteOff(dto,info);
		// 测试红冲时，修改对账单信息
		statementOfAccountService.updateStatementAndDetailForRedBack(dto,info);
	}

	/**
	 * 测试按ID查询对账单方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 上午8:43:12
	 */
	@Test
	public void testQueryByPrimaryKey() {
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		// 测试根据对账单ID查询对账单
		StatementOfAccountEntity soaEntity2 = statementOfAccountService
				.queryByPrimaryKey(soaEntity.getId());
		Assert.assertNotNull(soaEntity2);
	}

	/**
	 * 测试确认对账单方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 上午9:53:40
	 */
	@Test
	public void testConfirmStatement() {

		// 正确条件测试
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		List<StatementOfAccountEntity> list = new ArrayList<StatementOfAccountEntity>();
		list.add(soaEntity);
	    CurrentInfo	info1=CommonTestUtil.getCurrentInfo();
		try {
			statementOfAccountService.confirmStatement(list, info1);
		} catch (BusinessException e) {
			Assert.assertNull(e);
		}
		// 错误条件测试
		// 新增对账单信息
		StatementOfAccountEntity soaEntity2 = addStatementAccountEntity();
		List<StatementOfAccountEntity> list2 = new ArrayList<StatementOfAccountEntity>();
		soaEntity2
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
		list2.add(soaEntity2);
		CurrentInfo	info2=CommonTestUtil.getCurrentInfo();
		try {
			statementOfAccountService.confirmStatement(list2, info2);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}

	}

	/**
	 * 测试反确认对账单方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 上午9:53:40
	 */
	@Test
	public void testUnConfirmStatement() {
		// 正确条件测试
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		List<StatementOfAccountEntity> list = new ArrayList<StatementOfAccountEntity>();
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM);
		list.add(soaEntity);
		CurrentInfo	info=CommonTestUtil.getCurrentInfo();
		try {
			statementOfAccountService.unConfirmStatement(list, info);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}
		// 错误条件测试
		// 新增对账单信息
		StatementOfAccountEntity soaEntity2 = addStatementAccountEntity();
		List<StatementOfAccountEntity> list2 = new ArrayList<StatementOfAccountEntity>();
		soaEntity2.setUnlockTime(org.apache.commons.lang.time.DateUtils
				.addDays(new Date(), 10));
		soaEntity2.setVersionNo((short) 2);
		list.add(soaEntity2);
		CurrentInfo	info2=CommonTestUtil.getCurrentInfo();
		try {
			statementOfAccountService.unConfirmStatement(list2, info2);
		} catch (BusinessException e) {
			Assert.assertNotNull(e);
		}
	}

	/**
	 * 新增和删除对账单明细时，修改对账单金额方法测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 上午10:45:20
	 */
	@Test
	public void testUpdateStatementForAddDetail() {
		CurrentInfo	info=CommonTestUtil.getCurrentInfo();
		// 新增对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		try {
			//测试新增时修改对账单信息
			statementOfAccountService.updateStatementForAddDetail(soaEntity,info);
		} catch (BusinessException e) {
			Assert.assertNull(e);
		}
		

	}
	@Test
	public void testUpdateStatementForDelDetail() {
		CurrentInfo	info=CommonTestUtil.getCurrentInfo();
		// 新增对账单
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		try {
			//测试新增时修改对账单信息
			statementOfAccountService.updateStatementForDeleteDetail(soaEntity,info);
		} catch (BusinessException e) {
			Assert.assertNull(e);
		}

	}

	/**
	 * 测试按ID查询对账单方法
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-9 上午8:43:12
	 */
	@Test
	public void testQueryByStatementNoAndVersion() {
		// 新增对账单信息
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		// 测试根据对账单单号和版本号查询对账单
		StatementOfAccountEntity soaEntity3 = statementOfAccountService
				.queryByStatementNoAndVersion(soaEntity.getStatementBillNo(),soaEntity.getVersionNo());
		Assert.assertNotNull(soaEntity3);
	}
}
