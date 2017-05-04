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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/dao/CreditOrgEntityDaoTest.java
 * 
 * FILE NAME        	: CreditOrgEntityDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.dao.ICreditOrgEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

/**
 * 
 * 部门收支平衡Dao
 * 
 * @author dp-huangxb
 * @date 2012-10-22 下午1:56:37
 */
public class CreditOrgEntityDaoTest extends BaseTestCase {

	@Autowired
	private ICreditOrgEntityDao creditOrgEntityDao;

	/**
	 * 
	 * 测试新加
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 下午2:04:11
	 */
	@Test
	public void addOrgPaymentBalanceTest() {

		int result = addOrgBalance();
		Assert.assertEquals(1, result);
	}

	private int addOrgBalance() {
		CreditOrgEntity item = new CreditOrgEntity();
		item.setId(UUID.randomUUID().toString());
		item.setOrgCode("foss007");
		item.setOrgName("foss project group");
		item.setUsedAmount(new BigDecimal(0));
		item.setIsOverdue("N");

		int result = this.getCreditOrgEntityDao().addCreditOrg(
				item);
		return result;
	}

	/**
	 * 
	 * 查询
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 下午2:25:45
	 */
	@Test
	public void queryOrgPaymentBalanceTest() {
		// 新加组织初始信息
		this.addOrgBalance();

		CreditOrgEntity item = this.getCreditOrgEntityDao()
				.queryByOrgCode("foss007");
		Assert.assertNotNull(item);
		Assert.assertEquals("foss007", item.getOrgCode());
		Assert.assertEquals(new BigDecimal(0), item.getUsedAmount());
		Assert.assertEquals("N", item.getIsOverdue());

	}

	/**
	 * 
	 * 按组织编码
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 下午5:44:16
	 */
	@Test
	public void queryCreditByOrgCode() {

		this.addOrgBalance();

		CreditOrgDto result = this.getCreditOrgEntityDao()
				.queryDebitByOrgCode("foss007");
		Assert.assertNotNull(result);
		Assert.assertEquals(new BigDecimal(0), result.getUsedAmount());
		Assert.assertEquals("foss007", result.getOrgCode());
		Assert.assertEquals("N", result.getIsOverdue());

	}

	/**
	 * 
	 * 根据组织编码进行信用额度扣减
	 * 
	 * @author dp-huangxb
	 * @date 2012-10-22 下午3:30:25
	 */
	@Test
	public void updateUsedAmount() {
		// 初始行政
		this.addOrgBalance();
		CreditOrgEntity entity = new CreditOrgEntity();
		entity.setOrgCode("foss007");
		entity.setUsedAmount(new BigDecimal(500));
		// 扣减额度
		int updateRows = this.getCreditOrgEntityDao().updateUsedAmount(
				entity);
		Assert.assertEquals(1, updateRows);

		// 验证
		CreditOrgEntity item = this.getCreditOrgEntityDao()
				.queryByOrgCode("foss007");
		Assert.assertNotNull(item);
		Assert.assertEquals("foss007", item.getOrgCode());
		Assert.assertEquals(new BigDecimal(500), item.getUsedAmount());

	}

	/**
	 * 更新组织超期欠款标记
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 下午4:04:50
	 */
	@Test
	public void updateOrgOverdueState() {

		// 添加
		this.addOrgBalance();

		// 更新
		CreditOrgEntity item = new CreditOrgEntity();
		item.setIsOverdue("Y");
		item.setOrgCode("foss007");

		this.getCreditOrgEntityDao().updateOverdueState(item);

		// 验证
		item = this.getCreditOrgEntityDao().queryByOrgCode("foss007");
		Assert.assertEquals("Y", item.getIsOverdue());
	}

	/**
	 * 查询总行数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 上午11:56:33
	 */
	@Test
	public void queryTotalRows() {
		// 添加
		this.addOrgBalance();
		// 添加
		this.addOrgBalance();
	
	}

	/**
	 * 按页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-24 下午1:45:01
	 */
	@Test
	public void queryOrgByPage() {
		// 添加
		this.addOrgBalance();
		// 添加
		this.addOrgBalance();

		// 添加
		this.addOrgBalance();

		
		List<CreditOrgDto> result = this.getCreditOrgEntityDao().queryOrgCodeByPage(0,3);
		Assert.assertEquals(3, result.size());

	}

	public ICreditOrgEntityDao getCreditOrgEntityDao() {
		return creditOrgEntityDao;
	}

	public void setCreditOrgEntityDao(
			ICreditOrgEntityDao creditOrgEntityDao) {
		this.creditOrgEntityDao = creditOrgEntityDao;
	}

}
