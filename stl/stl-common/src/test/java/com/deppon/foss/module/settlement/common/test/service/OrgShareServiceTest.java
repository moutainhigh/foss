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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/OrgShareServiceTest.java
 * 
 * FILE NAME        	: OrgShareServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IOrgShareService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 理赔成本划分服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 下午2:45:05
 */
public class OrgShareServiceTest extends BaseTestCase {

	@Autowired
	private IOrgShareService orgShareService;

	@Test
	@Rollback(true)
	public void testAddOrgShare() {

		OrgShareEntity entity = new OrgShareEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setAmount(new BigDecimal("300"));
		entity.setOrgCode("001");
		entity.setOrgName("德邦物流");
		entity.setSourceBillNo("YF14552909");
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);

		orgShareService.addOrgShare(entity);
	}
}
