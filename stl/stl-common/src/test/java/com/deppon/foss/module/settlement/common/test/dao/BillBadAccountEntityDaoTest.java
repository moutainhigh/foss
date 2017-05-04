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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/dao/BillBadAccountEntityDaoTest.java
 * 
 * FILE NAME        	: BillBadAccountEntityDaoTest.java
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

import com.deppon.foss.module.settlement.common.api.shared.domain.BillBadAccountEntity;
import com.deppon.foss.module.settlement.common.server.dao.impl.BillBadAccountEntityDao;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class BillBadAccountEntityDaoTest extends BaseTestCase {

	@Autowired
	private BillBadAccountEntityDao billBadAccountEntityDao;

	@Test
	@Rollback(true)
	public void testAdd() {

		BillBadAccountEntity entity = new BillBadAccountEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setBadDebatBillNo("HZ99999999");
		entity.setReceivableNo("YS99999999");
		entity.setWaybillNo("YD99999999");
		entity.setSourceBillType("YS");
		entity.setBadAmount(BigDecimal.TEN);
		entity.setCreateTime(new Date());
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);

		int insertRows = billBadAccountEntityDao.add(entity);
		Assert.assertEquals(1, insertRows);
	}

}
