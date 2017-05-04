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
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/closing/test/service/WaybillChangeMsgProcessServiceTest.java
 * 
 * FILE NAME        	: WaybillChangeMsgProcessServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.test.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.closing.api.server.service.IWaybillChangeMsgProcessService;
import com.deppon.foss.module.settlement.closing.test.BaseTestCase;


/**
 * 财务完结处理服务测试类
 * @author ibm-zhuwei
 * @date 2012-10-25 下午6:08:12
 */
public class WaybillChangeMsgProcessServiceTest extends BaseTestCase {

	@Autowired
	private IWaybillChangeMsgProcessService waybillChangeMsgProcessService;
	
	@Before
	public void setUp() {
//		this.simpleJdbcTemplate.update("delete from t_stl_bill_cash_collection where source_bill_no = ?", WAYBILL_NO);
	}
	
	/**
	 * 财务完结处理
	 * @author ibm-zhuwei
	 * @date 2012-10-25 下午6:07:04
	 */
	@Test
	@Rollback(false)
	@Ignore
	public void testProcessChangeMsg() {
		this.logger.info("begin test ...");
		
		waybillChangeMsgProcessService.processChangeMsg();
		
		this.logger.info("end test ...");
	}

}
