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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/CustomerCreditQueryDaoTest.java
 * 
 * FILE NAME        	: CustomerCreditQueryDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerCreditGridDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 客户信用额度还原，应收单查收DaoTest
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-24 下午5:15:43
 */
public class CustomerCreditQueryDaoTest extends BaseTestCase {

	@Autowired
	private ICustomerCreditQueryDao customerCreditQueryDao = null;

	@Autowired
	private IBillReceivableService billReceivableservice = null;

	/**
	 * 测试获取超期的临时欠款运单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午5:49:16
	 */
	@Test
	@Ignore
	public void testQueryCreditOverdueNumber() {

		Calendar busiDate = Calendar.getInstance();
		busiDate.set(2011, 1, 1);

		// 添加应收单
		BillReceivableEntity billReceivable = getBillReceivableEntity(busiDate);

		billReceivable
				.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		billReceivable.setActive("Y");
		billReceivable.setIsRedBack("N");
		billReceivable.setCustomerCode("foss007");
		// 审核状态
		billReceivable.setApproveStatus("N");

		// 插入
		this.getBillReceivableservice().addBillReceivable(billReceivable,
				ConsumerTestUtil.getCurrentInfo());

		// 判断是否已经插入成功
		BillReceivableEntity insertResult = this.getBillReceivableservice()
				.queryByReceivableNO(billReceivable.getReceivableNo(), "Y");
		Assert.assertNotNull(insertResult);

		Calendar overdueDate = Calendar.getInstance();
		overdueDate.set(2011, 2, 1);
		// 验证应收单
		String overdueWaybill = this.getCustomerCreditQueryDao()
				.queryCreditOverdueNumber("foss007", overdueDate.getTime());
		Assert.assertEquals("YD0001", overdueWaybill);

	}

	/**
	 * 
	 * 测试获得红冲相关金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 上午11:36:42
	 */
	@Test
	@Ignore
	public void testQueryCreditWriteBackAmount() {

		Calendar busiDate = Calendar.getInstance();
		busiDate.set(2011, 1, 1);

		// 添加应收单
		BillReceivableEntity billReceivable = getBillReceivableEntity(busiDate);

		// 付款方式
		billReceivable
				.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
		billReceivable.setProductCode("productType");
		billReceivable.setCustomerCode("foss007");

		billReceivable.setActive("Y");
		billReceivable.setIsRedBack("Y");
		// 审核状态
		billReceivable.setApproveStatus("N");

		// 插入数据
		this.getBillReceivableservice().addBillReceivable(billReceivable,
				ConsumerTestUtil.getCurrentInfo());

		// 判断是否已经插入成功
		BillReceivableEntity insertResult = this.getBillReceivableservice()
				.queryByReceivableNO(billReceivable.getReceivableNo(), "Y");
		Assert.assertNotNull(insertResult);

		Calendar inceptDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		inceptDate.set(2011, 1, 1);
		endDate.set(2011, 1, 2);

		BigDecimal result = this.getCustomerCreditQueryDao()
				.queryCreditWriteBackAmount("foss007", inceptDate.getTime(),
						endDate.getTime());
		Assert.assertEquals(new BigDecimal("5000"), result);

	}

	/**
	 * 获取综合管理的总的组织个数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午3:48:43
	 */
	@Test
	@Ignore
	public void testQueryOrgTotalsFromBse() {

		int orgRows = this.customerCreditQueryDao.queryTotalOrgsFromBse();

		Assert.assertEquals(true, orgRows >= 0);

	}

	/**
	 * 
	 * 通过分页的形式查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午3:50:23
	 */
	@Test
	@Ignore
	public void testQueryOrgByPageFromBse() {

		int orgRows = this.customerCreditQueryDao.queryTotalOrgsFromBse();
		// 大于0
		if (orgRows > 0) {
			List<CustomerCreditGridDto> result = this.customerCreditQueryDao
					.queryOrgFromBse(0, 5);

			Assert.assertNotNull(result);
			Assert.assertEquals(true, orgRows >= result.size());

		}
	}

	/**
	 * 获得客户的总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午3:57:10
	 */
	@Test
	@Ignore
	public void queryCustomerRows() {
		int customerRows = this.customerCreditQueryDao
				.queryTotalCustomersFromBse();
		Assert.assertEquals(true, customerRows >= 0);
	}


	/**
	 * 测试获取超期的临时欠款运单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午5:49:16
	 */
	@Test
	@Ignore
	public void testQueryDebtOverdueNumber() {

		Calendar busiDate = Calendar.getInstance();
		busiDate.set(2011, 1, 1);

		// 添加应收单
		BillReceivableEntity billReceivable = getBillReceivableEntity(busiDate);

		billReceivable
				.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		billReceivable.setActive("Y");
		billReceivable.setIsRedBack("N");
		// 审核状态
		billReceivable.setApproveStatus("N");

		// 插入
		this.getBillReceivableservice().addBillReceivable(billReceivable,
				ConsumerTestUtil.getCurrentInfo());

		// 判断是否已经插入成功
		BillReceivableEntity insertResult = this.getBillReceivableservice()
				.queryByReceivableNO(billReceivable.getReceivableNo(), "Y");
		Assert.assertNotNull(insertResult);

	}

	/**
	 * 
	 * 测试获得临欠红冲相关金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 上午11:36:42
	 */
	@Test
	@Ignore
	public void testQueryDebtWriteBackAmount() {

		Calendar busiDate = Calendar.getInstance();
		busiDate.set(2011, 1, 1);

		BillReceivableEntity billReceivable = addBillReceivable(busiDate);

		// 判断是否已经插入成功
		BillReceivableEntity insertResult = this.getBillReceivableservice()
				.queryByReceivableNO(billReceivable.getReceivableNo(), "Y");
		Assert.assertNotNull(insertResult);

		Calendar inceptDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		inceptDate.set(2011, 1, 1);
		endDate.set(2011, 1, 2);

		BigDecimal result = this.getCustomerCreditQueryDao()
				.queryDebtWriteBackAmount("foss007", inceptDate.getTime(),
						endDate.getTime());
		Assert.assertEquals(new BigDecimal("5000"), result);

	}

	/**
	 * 添加应收单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-12 下午5:24:14
	 */
	private BillReceivableEntity addBillReceivable(Calendar busiDate) {
		// 添加应收单
		BillReceivableEntity billReceivable = getBillReceivableEntity(busiDate);

		billReceivable
				.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT);
		billReceivable.setProductCode("productType");

		billReceivable.setActive("Y");
		billReceivable.setIsRedBack("Y");
		// 审核状态
		billReceivable.setApproveStatus("N");

		// 插入数据
		this.getBillReceivableservice().addBillReceivable(billReceivable,
				ConsumerTestUtil.getCurrentInfo());
		return billReceivable;
	}

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午2:26:57
	 */
	private BillReceivableEntity getBillReceivableEntity(Calendar busiDate) {
		BillReceivableEntity billReceivable = new BillReceivableEntity();
		billReceivable.setId(UUIDUtils.getUUID());
		billReceivable.setReceivableOrgCode("foss007");
		billReceivable.setBusinessDate(busiDate.getTime());

		billReceivable.setReceivableNo("YD0001");
		billReceivable.setWaybillNo("YD0001");
		billReceivable.setWaybillId(UUIDUtils.getUUID());
		billReceivable.setCreateType("1");
		billReceivable.setSourceBillNo("YD0001");
		billReceivable.setSourceBillType("12313");
		billReceivable.setBillType("billType");
		billReceivable.setAmount(new BigDecimal("5000"));
		billReceivable.setUnverifyAmount(new BigDecimal("5000"));
		billReceivable.setVerifyAmount(new BigDecimal("0"));
		billReceivable.setCurrencyCode("RMB");
		billReceivable.setAccountDate(busiDate.getTime());
		billReceivable.setCreateTime(busiDate.getTime());
		Short versionNo = 5;
		billReceivable.setVersionNo(versionNo);

		billReceivable.setIsInit("N");
		billReceivable.setProductCode("helloworld");
		return billReceivable;
	}

	public ICustomerCreditQueryDao getCustomerCreditQueryDao() {
		return customerCreditQueryDao;
	}

	public void setCustomerCreditQueryDao(
			ICustomerCreditQueryDao customerCreditQueryDao) {
		this.customerCreditQueryDao = customerCreditQueryDao;
	}

	public IBillReceivableService getBillReceivableservice() {
		return billReceivableservice;
	}

	public void setBillReceivableservice(
			IBillReceivableService billReceivableservice) {
		this.billReceivableservice = billReceivableservice;
	}

}
