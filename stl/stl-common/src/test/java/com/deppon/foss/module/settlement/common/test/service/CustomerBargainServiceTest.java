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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/CustomerBargainServiceTest.java
 * 
 * FILE NAME        	: CustomerBargainServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao;
import com.deppon.foss.module.settlement.common.api.server.dao.ICreditOrgEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.DebitDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;

/**
 * 
 * 客户额度扣减测试用例
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-22 下午5:57:45
 */
public class CustomerBargainServiceTest extends BaseTestCase {

	@Autowired
	private ICreditCustomerEntityDao customerPaymentBeEntityDao;

	@Autowired
	private ICreditOrgEntityDao creditOrgEntityDao;

	@Autowired
	private ICustomerBargainService customerBargainService;

	public ICreditCustomerEntityDao getCustomerPaymentBeEntityDao() {
		return customerPaymentBeEntityDao;
	}

	/**
	 * 
	 * 判断能否按客户进行月结欠款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-22 下午6:03:44
	 */
	@Test
	public void testIsBeDebtByCustomer() {

		addCustomerBalance();

		// 正确的客户，正确的余额
		DebitDto debitDto = this.customerBargainService
				.isBeBebt(
						"customer001",
						"",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						new BigDecimal(400));
		Assert.assertEquals(true, debitDto.isBeBebt());
		

		// 正确的客户，错误的余额
		debitDto = this.customerBargainService
				.isBeBebt(
						"customer001",
						"",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						new BigDecimal(4000));
		Assert.assertEquals(false, debitDto.isBeBebt());

		// 错误的客户，错误的余额
		debitDto = this.customerBargainService
				.isBeBebt(
						"customer002",
						"",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						new BigDecimal(4000));
		Assert.assertEquals(false, debitDto.isBeBebt());

		// 正确的客户，错误的余额，错误的付款方式
		try {
			debitDto = this.customerBargainService
					.isBeBebt(
							"customer001",
							"",
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
							new BigDecimal(4000));
		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}

	}

	/**
	 * 添加客户组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午8:40:00
	 */
	private void addCustomerBalance() {
		// 测试能否月结
		CreditCustomerEntity customerEntity = new CreditCustomerEntity();
		customerEntity.setId(UUID.randomUUID().toString());
		customerEntity.setCustomerCode("customer001");
		customerEntity.setUsedAmount(new BigDecimal(0));
		// 插入行数
		int rows = this.getCustomerPaymentBeEntityDao().addCreditCustomer(
				customerEntity);
		Assert.assertEquals(1, rows);
	}

	/**
	 * 
	 * 判断能否按组织进行临时欠款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-22 下午6:41:49
	 */
	@Test
	public void testIsBeDebtByOrgCode() {
		addOrgBalance();

		// 判断能否进行欠款

		// 正确的客户，正确的余额
		DebitDto debitDto = this.customerBargainService
				.isBeBebt(
						"",
						"foss0001",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
						new BigDecimal(400));
		Assert.assertEquals(true, debitDto.isBeBebt());

		// 正确的客户，错误的余额
		debitDto = this.customerBargainService
				.isBeBebt(
						"",
						"foss0001",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
						new BigDecimal(40000));
		Assert.assertEquals(false, debitDto.isBeBebt());

		// 错误的客户，错误的余额
		debitDto = this.customerBargainService
				.isBeBebt(
						"",
						"foss0001",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
						new BigDecimal(40000));
		Assert.assertEquals(false, debitDto.isBeBebt());

		// 正确的客户，错误的余额，错误的付款方式
		try {
			debitDto = this.customerBargainService
					.isBeBebt(
							"",
							"foss0001",
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							new BigDecimal(40000));
		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}
	}

	/**
	 * 添加组织平衡表
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午8:39:09
	 */
	private void addOrgBalance() {
		CreditOrgEntity entity = new CreditOrgEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setOrgCode("foss0001");
		entity.setUsedAmount(new BigDecimal(0));
		entity.setIsOverdue("N");

		// 写入
		int rows = this.creditOrgEntityDao.addCreditOrg(entity);
		Assert.assertEquals(1, rows);
	}

	/**
	 * 
	 * 按客户扣减可用额度
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午8:24:49
	 */
	@Test
	public void testDeductionCreditByCustomerCode() {

		// 新加客户信息
		this.addCustomerBalance();

		// 用户信息
		CurrentInfo currentInfo = CommonTestUtil.getCurrentInfo();

		// 正确的扣减 正确的客户，正确的付款方式 正确的金额
		boolean result = this.customerBargainService
				.updateUsedAmount(
						"customer001",
						"",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						new BigDecimal(100), currentInfo);
		// 验证扣减结果
		Assert.assertEquals(true, result);
		// 正确的断定
		DebitDto debitDto = this.customerBargainService
				.isBeBebt(
						"customer001",
						"",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
						new BigDecimal(100));
		Assert.assertEquals(true, debitDto.isBeBebt());
//		try {
//			// 错误的断定
//			debitDto = this.customerBargainService
//					.isBeBebt(
//							"customer001",
//							"",
//							SettlementDictionaryConstants.BILL_RECEIVABLE__PAYMENT_TYPE__CREDIT,
//							new BigDecimal(1000));
//			Assert.assertEquals(false, debitDto.isBeBebt());
//		} catch (SettlementException ex) {
//			Assert.assertNotNull(ex);
//		}
//		try {
//			// 错误的扣减 正确的客户，错误的付款方式 正确的金额
//
//			result = this.customerBargainService
//					.updateUsedAmount(
//							"customer001",
//							"",
//							SettlementDictionaryConstants.BILL_RECEIVABLE__PAYMENT_TYPE__DEBT,
//							new BigDecimal(100), currentInfo);
//		} catch (SettlementException ex) {
//			Assert.assertNotNull(ex);
//		}
//
//		try {
//			// 错误的扣减 错误的客户，正确的付款方式 错误的金额
//			result = this.customerBargainService
//					.updateUsedAmount(
//							"customer002",
//							"",
//							SettlementDictionaryConstants.BILL_RECEIVABLE__PAYMENT_TYPE__CREDIT,
//							new BigDecimal(900000), currentInfo);
//		} catch (SettlementException ex) {
//			Assert.assertNotNull(ex);
//		}

	}

	/**
	 * 
	 * 测试组织进行扣款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午9:05:14
	 */
	@Test
	@Ignore
	public void testDecucationCreditByOrgCode() {

		// 用户信息
		CurrentInfo currentInfo = CommonTestUtil.getCurrentInfo();

		// 添加组织信息
		this.addOrgBalance();

		// 验证扣减加信息
		boolean result = this.customerBargainService
				.updateUsedAmount(
						"",
						"foss0001",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
						new BigDecimal(100), currentInfo);
		Assert.assertEquals(true, result);
		// 判断能否进行再次欠款
		DebitDto debitDto = this.customerBargainService
				.isBeBebt(
						"",
						"foss0001",
						SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
						new BigDecimal(999999));
		Assert.assertEquals(false, debitDto.isBeBebt());

		try {
			// 错误的客户，正确的付款方式，正确的欠款金额
			result = this.customerBargainService
					.updateUsedAmount(
							"",
							"foss0001123",
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
							new BigDecimal(100), currentInfo);
			Assert.assertEquals(false, result);
		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}
		try {

			// 正确的客户，错误的付款方式，正确的金额

			result = this.customerBargainService
					.updateUsedAmount(
							"",
							"foss0001",
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							new BigDecimal(100), currentInfo);

		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}

		// 正确的客户，正确的付款方式，错误的金额
		try {
			result = this.customerBargainService
					.updateUsedAmount(
							"",
							"foss0001",
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							new BigDecimal(999900), currentInfo);
		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}

	}

	public void setCustomerPaymentBeEntityDao(
			ICreditCustomerEntityDao customerPaymentBeEntityDao) {
		this.customerPaymentBeEntityDao = customerPaymentBeEntityDao;
	}

	public void setOrgPaymentBalanceEntityDao(
			ICreditOrgEntityDao creditOrgEntityDao) {
		this.creditOrgEntityDao = creditOrgEntityDao;
	}

	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

}
