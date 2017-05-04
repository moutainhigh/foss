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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/ActualFreightServiceTest.java
 * 
 * FILE NAME        	: ActualFreightServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.ActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;


public class ActualFreightServiceTest {
    
    private IActualFreightService actualFreightService;

    @Before
    public void setUpBeforeClass() throws Exception {
	actualFreightService = (ActualFreightService) TestHelper.get().getBeanByClass(ActualFreightService.class);
    }

    //@Test
    public void test() {
	ResultDto res = actualFreightService.updateGoodsNum("2012123", 30);
	Assert.assertTrue(res.getCode().equals("1"));
	System.out.println(res.getMsg());
    }

}