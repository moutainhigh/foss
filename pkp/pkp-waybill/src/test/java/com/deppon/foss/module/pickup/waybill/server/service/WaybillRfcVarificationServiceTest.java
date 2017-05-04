/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillRfcVarificationServiceTest.java
 * 
 * FILE NAME        	: WaybillRfcVarificationServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;

/**
 * 更改单服务测试
 * 
 * @author 026113-foss-linwensheng
 * 
 */
@SuppressWarnings("unused")
public class WaybillRfcVarificationServiceTest {
	List<WaybillRfcChangeDto> list  = null;
	private IWaybillRfcVarificationService waybillRfcService;

	@Before
	public void setUp() throws Exception {
		waybillRfcService = TestHelper.get().getBeanByClass(WaybillRfcVarificationService.class);
	}
	
	@After
	public void tearDown(){
		list = null;
	}
	@Test 
	public void testQueryWaybillRfcEntity(){
		
		WaybillRfcCondition condition = new WaybillRfcCondition();
		
		condition.setDeptCode("GS00002");
		condition.setStartDate(new Date());
		condition.setEndDate(null);
		try{
			list =  waybillRfcService.queryWaybillRfcVarifyDto(condition);			
		}catch(BusinessException e){
			Assert.assertNull(list);
		}
		
		condition.setStartDate(null);
		condition.setEndDate(new Date());
		try{
			list =  waybillRfcService.queryWaybillRfcVarifyDto(condition);			
		}catch(BusinessException e){
			Assert.assertNull(list);
		}
		
		condition.setStartDate(new Date());
		condition.setEndDate(new Date());
		
		list =  waybillRfcService.queryWaybillRfcVarifyDto(condition);			
		
		
		Calendar c1 = Calendar.getInstance();
		Date d1 = new Date();
		Date d2 = new Date();
		c1.setTime(d1);
		c1.set(Calendar.DATE, 1);
		d1.setTime(c1.getTimeInMillis());
		condition.setStartDate(d1);
		condition.setEndDate(d2);
		list = null;
		try{
			list =  waybillRfcService.queryWaybillRfcVarifyDto(condition);			
		}catch(BusinessException e){
			Assert.assertNull(list);
		}
	}
	
	@Test
	public void testQueryWaybillRfcProofByFilePath(){
		String filePath = "IMGP3481.jpg";
		String str = waybillRfcService.queryWaybillRfcProofByFilePath(filePath);
		System.out.println(str);
	}
}