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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/PayToCostcontrolSendServiceTest.java
 * 
 * FILE NAME        	: PayToCostcontrolSendServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.deppon.esb.api.util.ESBInitUtil;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.module.settlement.pay.server.service.impl.PayToCostcontrolSendService;


/**
 * 测试汇款给费控接口
 * @author 045738-foss-maojianqiang
 * @date 2012-11-30 上午11:30:55
 */
public class PayToCostcontrolSendServiceTest {
	
	@Before
	public void init() throws Exception{
		ESBInitUtil util = new ESBInitUtil();
		util.process();
	}
	@Test
	@Ignore
	public void testPayToCostcontrol() throws Exception{
		PayToCostcontrolSendService service = new PayToCostcontrolSendService();
		//声明dto
		PayToCostcontrolDto dto = new PayToCostcontrolDto();
		List<String> list= new ArrayList<String>();
		list.add("1");
		//dto.setPaymentBillNo(list);
		dto.setPayBillCelephone("111");
		dto.setPayBillAmount(new BigDecimal("100"));
		dto.setPayBillAppNo("1");
		dto.setPayBillBankNo("1");
		dto.setPayBillComNo("1");
		dto.setPayBillDeptNo("1");
		dto.setPayBillDeptName("1");
		dto.setPayBillLastTime(new Date());
		dto.setPayBillPayeeName("1");
		//dto.setPayBillExpDate(new Date());
	//	dto.setPayBillExpDesc("1");
		//dto.setExpensesType("1");
		//dto.setPayBillAppType(1);
		dto.setBatchNo("1");
		service.payToCostcontrol(dto);
	}
}
