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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillServiceTest.java
 * 
 * FILE NAME        	: WaybillServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.waybillservice.WaybillService;

/**
 * 更改单服务测试
 * 
 * @author 026113-foss-linwensheng
 * 
 */

public class WaybillServiceTest {

	IWaybillManagerService waybillManagerService;
	IWaybillService waybillService ;
	
	@Before
	public void setUp() throws Exception {
//		waybillManagerService = (IWaybillManagerService) TestHelper.get()
//				.getBeanByClass(WaybillManagerService.class);
		
		waybillService = (IWaybillService) TestHelper.get().getBeanByClass(WaybillService.class) ;

	}

	
	
	/**
	 * 测试通过运单号集合查询未处理更改单
	 */
	@Test
	public void testqueryByWaybillNo() {

		WaybillDto waybill = waybillManagerService.queryWaybillByNo("50001620");
		System.out.println(waybill.getMixNo());
	}
	
	/**
	 * 测试通过运单号集合查询未处理更改单
	 */
	@Test
	public void testqueryLeaveChangeByWaybillNo() {
		List waybillNoList=new ArrayList<String>();
		waybillNoList.add("32432");
		List<CrmWaybillServiceDto> crmServiceDto = waybillManagerService.queryWaybillDetail(waybillNoList);
		
		
		
		/*WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo("50001620");
		System.out.println(waybill.getActive());*/
	}
	
	@Test
	public void testQueryWaybillDeliverGoodsList (){
		
		Date startTime = DateUtils.convert("2015-08-09");
		Date endTime = new Date(System.currentTimeMillis());
		
		System.out.println("startTime="+startTime.toString()+" endTime="+endTime.toString());
		
		List<String> waybillNoList = new ArrayList<String>() ;
//		String[] str = {"5755555555","5655555555","5599999999","5522999999","5522999998",
//		"5522999988","5089789682","235556428","235556422","15081001" } ;
		String[] str = {"798997061","798997063","798997064"} ;
//		for(String res : str){
//			waybillNoList.add(res) ;
//		} 
		
		DeliverGoodsListQueryVo deliverGoodsListQueryVo = new DeliverGoodsListQueryVo();
		deliverGoodsListQueryVo.setDeliveryCustomerCode("400481106");
		deliverGoodsListQueryVo.setStartTime(startTime);
		deliverGoodsListQueryVo.setEndTime(endTime);
//		deliverGoodsListQueryVo.setGoodsStatus("SIGN_STATUS_ALL");
//		deliverGoodsListQueryVo.setWaybillNoList(waybillNoList);
		
		Map<String,Object> map = waybillService.queryWaybillDeliverGoodsList(deliverGoodsListQueryVo, 2, 5);
		int count = waybillService.queryWaybillDeliverGoodsListCount(deliverGoodsListQueryVo);
		System.out.println("count=" + count + ": " + map.get("count"));
		count = (Integer) map.get("count") ;
		if(count != 0){
			List<DeliverGoodsListVo> deliverGoodsListVos = (List<DeliverGoodsListVo>) map.get("resultList");
			System.out.println("deliverGoodsListVos.size()="+ deliverGoodsListVos.size());
			for (DeliverGoodsListVo vo : deliverGoodsListVos) {
				System.out.println(vo.toString());
			}
		}else{
			System.out.println("记录数为 0 ");
		}
		
	}
	
}