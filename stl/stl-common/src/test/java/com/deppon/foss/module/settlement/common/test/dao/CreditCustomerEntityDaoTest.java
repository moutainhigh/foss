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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/dao/CreditCustomerEntityDaoTest.java
 * 
 * FILE NAME        	: CreditCustomerEntityDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import junit.framework.Assert;

import com.deppon.foss.module.settlement.common.api.server.dao.ICreditCustomerEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditCustomerDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

/**
 * 客户信用额度Dao测试
 * 
 * @author dp-huangxb
 * @date 2012-10-20 下午6:18:54
 */
public class CreditCustomerEntityDaoTest extends BaseTestCase {

	@Autowired
	private ICreditCustomerEntityDao creditCustomerEntityDao;

	/**
	 * 添加测试客户收支平衡信息用例
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-20 下午6:20:11
	 */
	@Test
	public void addCustomerBalanceTest() {
		int insertRows = 0;
		insertRows = addCustomerBalance();
		Assert.assertEquals(1, insertRows);

	}

	private int addCustomerBalance() {
		int insertRows;
		CreditCustomerEntity item = new CreditCustomerEntity();
		item.setCustomerCode("00001");
		item.setCustomerName("18M");
		item.setUsedAmount(new BigDecimal(0));
		item.setBusinessDate(new Date());
		item.setModifyTime(new Date());
		item.setCreateTime(new Date());
		item.setId(UUID.randomUUID().toString());

		insertRows = this.getCreditCustomerEntityDao()
				.addCreditCustomer(item);
		return insertRows;
	}

	/**
	 * 
	 * 测试查询可用余额Dao
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 上午10:26:50
	 */
	@Test
	public void queryCustomerBanlanceTest() {
		int insertRows = 0;
		insertRows = addCustomerBalance();
		Assert.assertEquals(1, insertRows);

		// 验证查询
		CreditCustomerEntity queryResult = this
				.getCreditCustomerEntityDao().queryByCustomerCode("00001");
		Assert.assertNotNull(queryResult);
	}

	/**
	 * 客户编码查询可用信用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 上午9:38:15
	 */
	@Test
	public void queryDebitByCustomerCode() {
		addCustomerBalance();

		// 验证查询
		CreditCustomerDto queryResult = this.getCreditCustomerEntityDao()
				.queryDebitByCustomerCode("00001");
		Assert.assertNotNull(queryResult);
		Assert.assertNotNull(queryResult.getIsOverdue());
		Assert.assertNotNull(queryResult.getTotalAmount());
		Assert.assertNotNull(queryResult.getUsedAmount());
		Assert.assertNotNull(queryResult.getCustomerCode());
		// 判断查询结果
		Assert.assertEquals(new BigDecimal(10000),
				queryResult.getTotalAmount());
		Assert.assertEquals("N", queryResult.getIsOverdue());
		Assert.assertEquals(new BigDecimal(0), queryResult.getUsedAmount());
		Assert.assertEquals("00001", queryResult.getCustomerCode());

	}

	/**
	 * 更新已用额度
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 上午9:40:33
	 */
	@Test
	public void updateUsedAmount() {
		addCustomerBalance();

		// 验证查询
		CreditCustomerDto queryResult = this.getCreditCustomerEntityDao()
				.queryDebitByCustomerCode("00001");
		// 第一次验证
		// 验证扣减已用额度
		CreditCustomerEntity customerDebitDto = new CreditCustomerEntity();
		customerDebitDto.setCustomerCode(queryResult.getCustomerCode());
		customerDebitDto.setUsedAmount(new BigDecimal(500));
		customerDebitDto.setCustomerCode(queryResult.getCustomerCode());

		int updateResult = this.getCreditCustomerEntityDao()
				.updateUsedAmount(queryResult.getCustomerCode(),new BigDecimal("500"));
		Assert.assertEquals(1, updateResult); // 受影响行数

		// 查询，获得更新结果
		queryResult = this.getCreditCustomerEntityDao()
				.queryDebitByCustomerCode("00001");
		// 判断已用额度
		Assert.assertEquals(new BigDecimal(500), queryResult.getUsedAmount());
		// 总额度
		Assert.assertEquals(new BigDecimal(10000),
				queryResult.getTotalAmount());

		// 第二次验证
		customerDebitDto.setCustomerCode(queryResult.getCustomerCode());
		customerDebitDto.setUsedAmount(new BigDecimal(200));
		customerDebitDto.setCustomerCode(queryResult.getCustomerCode());

		updateResult = this.getCreditCustomerEntityDao().updateUsedAmount(
				queryResult.getCustomerCode(),new BigDecimal("200"));
		Assert.assertEquals(1, updateResult); // 受影响行数

		// 查询，获得更新结果
		queryResult = this.getCreditCustomerEntityDao()
				.queryDebitByCustomerCode("00001");
		// 判断已用额度
		Assert.assertEquals(new BigDecimal(700), queryResult.getUsedAmount());
		// 总额度
		Assert.assertEquals(new BigDecimal(10000),
				queryResult.getTotalAmount());
	}

	/**
	 * 
	 * 测试更新客户的超期欠款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午3:59:44
	 */
	@Test
	public void updateUserOverdue() {

		// 新加
		this.addCustomerBalance();

		// 更新超期欠款标记
		CreditCustomerEntity item = new CreditCustomerEntity();
	
		this.getCreditCustomerEntityDao().updateOverdueStatus("00001","Y","");

		// 验证
		item = this.getCreditCustomerEntityDao()
				.queryByCustomerCode("00001");
		Assert.assertNotNull(item);
	}

	/**
	 * 测试获得总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午5:15:52
	 */
	@Test
	public void queryTotalRows() {

		//this.addCustomerBalance();
		//this.addCustomerBalance();

		//long totalRows = this.getCustomerPaymentBEEntityDao().queryTotalRows();
		//Assert.assertEquals(2, totalRows);

	}

	/**
	 * 通过分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午5:28:42
	 */
	@Test
	public void queryCreditCustomerByPage() {
		this.addCustomerBalance();
		this.addCustomerBalance();
		this.addCustomerBalance();

		//int totalRows = this.getCustomerPaymentBEEntityDao().queryTotalRows();
		//Assert.assertEquals(3, totalRows);
		List<CreditCustomerDto> resultSet = this.getCreditCustomerEntityDao()
				.queryCreditCustomerByPage(0,3);
		Assert.assertEquals(3, resultSet.size());
		///Assert.assertEquals("N", resultSet.get(0).getIsOverdue());
		//Assert.assertEquals("00001", resultSet.get(0).getCustomerCode());
	}

	public ICreditCustomerEntityDao getCreditCustomerEntityDao() {
		return creditCustomerEntityDao;
	}

	public void setCreditCustomerEntityDao(
			ICreditCustomerEntityDao creditCustomerEntityDao) {
		this.creditCustomerEntityDao = creditCustomerEntityDao;
	}
}
