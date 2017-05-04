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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/AccountServiceTest.java
 * 
 * FILE NAME        	: AccountServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosRequest;
import com.deppon.esb.inteface.domain.accounting.QueryWaybillInfosResponse;
import com.deppon.esb.inteface.domain.accounting.WayBillDetailInfo;
import com.deppon.foss.accountservice.AccountService;
import com.deppon.foss.accountservice.CommonException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOAErrorService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.OAErrorService;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;

/**
 * 
 * 官网接口测试
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-12-1 下午2:37:52
 */
public class AccountServiceTest {

	AccountService accountService;

	
	
	
	@Before
	public void setUpBeforeClass() throws Exception {
		accountService = TestHelper.get().getBeanByClass(AccountService.class);
	}

	//@Test
	public void testOAErrorService() {

		QueryWaybillInfosRequest request=new QueryWaybillInfosRequest();
		request.setName("123456");
		
		try {
			QueryWaybillInfosResponse wayBillDetailInfos=	 accountService.queryWaybillInfos(null, request);
		for(WayBillDetailInfo wayBillDetailInfo: wayBillDetailInfos.getWayBillList())
		{
			System.out.println(wayBillDetailInfo.getWaybillNum()+"");
		}
		
		
		
		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}