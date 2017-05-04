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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/TodoActionServiceTest.java
 * 
 * FILE NAME        	: TodoActionServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.waybill.api.server.service.ITodoActionService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.TodoActionService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillStockService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodTodoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoActionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockDto;


public class TodoActionServiceTest{
	
	  private ITodoActionService todoActionService;
	  private IWaybillStockService waybillStockService;

	    @Before
	    public void setUp() throws Exception {
	    	//todoActionService = (ITodoActionService) TestHelper.get().getBeanByClass(TodoActionService.class);
		waybillStockService = (IWaybillStockService) TestHelper.get().getBeanByClass(WaybillStockService.class);
	    }

	   // @Test
	    public void testQueryTodoActionsByCondition() {
	    	TodoConditionDto dto = new TodoConditionDto();
	    	dto.setWaybillNo("10001");
	    	dto.setDarftOrgName("111");
	    	dto.setOperateTimeBegin(new Date());
	    	dto.setOperateTimeEnd(new Date());
	    	dto.setStatus("123");
    		List<TodoActionDto> res = todoActionService.queryTodoActionsByCondition(dto, 1, 10);
    		Assert.assertNotNull(res);
	    }
	    
	    //@Test
	    public void testQueryGoodsInfoCount() {
	    	TodoConditionDto dto = new TodoConditionDto();
	    	dto.setWaybillNo("10001");
	    	dto.setDarftOrgName("111");
	    	dto.setOperateTimeBegin(new Date());
	    	dto.setOperateTimeEnd(new Date());
	    	dto.setStatus("123");
    		Long result = todoActionService.queryGoodsInfoCount(dto);
    		Assert.assertNotNull(result);
	    }
	    
	    //@Test
	    public void testUpdateLabeledPringStatus() {
    		//todoActionService.updateLabeledPringStatus("123","234","345");
    		Assert.assertNotNull("");
	    }

	    //@Test
	    public void testQueryTodoAction() {
    		@SuppressWarnings("unused")
    		LabeledGoodTodoDto todo = todoActionService.queryTodoAction("123","12");
    		Assert.assertTrue(true);
	    }
	    
	    @Test
	    public void testQueryWaybillStockDtoByWaybillNo() {
    		@SuppressWarnings("unused")
    		List<WayBillNoLocusDTO> wayList = waybillStockService.queryWaybillStockDtoByWaybillNo("03090001");
    		if(wayList!=null && wayList.size()>0){
    	    	for(int i=0;i<wayList.size();i++){
    	    		WayBillNoLocusDTO wayEntity=wayList.get(i);
    	    		if(wayEntity!=null){
    	    			System.out.println("运单号---------------------------"+wayEntity.getWaybillNo());
    	        		System.out.println("当前状态---------------------------"+wayEntity.getCurrentStatus());
    	        		System.out.println("备注---------------------------"+wayEntity.getNotes());
    	    		}
    	    	}
    		
    	    }
    		
    		Assert.assertTrue(true);
	    }
}