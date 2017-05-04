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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillBadAccountServiceTest.java
 * 
 * FILE NAME        	: BillBadAccountServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.IBillBadAccountService;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

/**
 * 坏账Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-24 下午1:47:29
 * @since
 * @version
 */
public class BillBadAccountServiceTest extends BaseTestCase{
	@Autowired
	private IBillBadAccountService billBadAccountService;
	
	/**
	 * 根据运单号查询是否存在坏账信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-24 下午1:50:40
	 */
	@Test
	public void testQueryByWaybillNO(){
		String waybillNo="50330003";
		int i=this.billBadAccountService.queryByWaybillNO(waybillNo);
		logger.info(i+" 是否存在 ");
	}
}
