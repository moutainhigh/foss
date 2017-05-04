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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/CreditMsgServiceTest.java
 * 
 * FILE NAME        	: CreditMsgServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgAlarmDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

/**
 * 
 * 部门信用额度服务测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2013-4-8 下午2:44:24
 */
public class CreditOrgServiceTest extends BaseTestCase {

	@Autowired
	private ICreditOrgService creditOrgService;

	@Test
	@Ignore
	public void testQueryOrgAlarm() {

		String msg = creditOrgService.queryOrgAlarm("W011303070303");

		System.out.println(msg);
	}
}
