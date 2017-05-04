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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/dao/BillWriteoffEntityDaoTest.java
 * 
 * FILE NAME        	: BillWriteoffEntityDaoTest.java
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

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillWriteoffEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 核销单Dao测试类
 * 
 * @author foss-wangxuemin
 * @date 2012-10-26 下午3:18:45
 */
public class BillWriteoffEntityDaoTest extends BaseTestCase {

	@Autowired
	private IBillWriteoffEntityDao billWriteoffEntityDao;

	private BillWriteoffEntity getWriteoffEntity() {
		BillWriteoffEntity entity = new BillWriteoffEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWriteoffBatchNo("HXBN99999999");
		entity.setWriteoffBillNo("HX9999999999");
		entity.setBeginNo("B999999");
		entity.setEndNo("E999999");
		entity.setCreateType("M");
		entity.setWriteoffType("OR");
		entity.setOrgCode("testOrgCode");
		entity.setOrgName("testOrgName");
		entity.setAmount(BigDecimal.TEN);
		entity.setWriteoffTime(new Date());
		entity.setAccountDate(new Date());
		entity.setIsInit(FossConstants.NO);
		entity.setIsRedBack(FossConstants.NO);
		entity.setActive(FossConstants.ACTIVE);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		billWriteoffEntityDao.add(entity);
		return entity;
	}

	/**
	 * 测试根据ID更新核销单生效标志
	 * 
	 * @author foss-wangxuemin
	 * @date 2012-10-26 下午3:22:27
	 */
	@Test
	@Rollback(true)
	public void testUpdateActiveById() {

		BillWriteoffEntity billWriteoffEntity = getWriteoffEntity();

		billWriteoffEntity.setActive(FossConstants.INACTIVE);

		int updateResult = billWriteoffEntityDao
				.updateActiveById(billWriteoffEntity);

		// 判断更新结果
		//Assert.assertEquals(1, updateResult);

	}
}
