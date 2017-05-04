package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;

/**
 * WaybillRelateDetailEntityService 测试类
 * @author 272311-sangwenhao
 * 2015.09.07
 */
public class WaybillRelateDetailEntityServiceTest {

	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;

	@Before
	public void setUp() throws Exception {
		waybillRelateDetailEntityService = (IWaybillRelateDetailEntityService) TestHelper
				.get().getBeanByClass(WaybillRelateDetailEntityService.class);

	}

	@Test
	public void testQueryWaybillRelateDetailByWaybillNos() { 
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("5820151119");
		waybillNoList.add("422324789");  

		List<WaybillRelateDetailEntity> waybillRelateDetailEntities = waybillRelateDetailEntityService.queryWaybillRelateDetailByWaybillNos(
				waybillNoList, 0, 0, false);
		if(CollectionUtils.isNotEmpty(waybillRelateDetailEntities)){
			System.out.println("返回的记录总数："+waybillRelateDetailEntities.size());
		}else{
			System.out.println("返回的记录为空");
		}
		for(WaybillRelateDetailEntity entity : waybillRelateDetailEntities){
			System.out.println("母件运单号："+entity.getParentWaybillNo()+"; 子运单号："+entity.getWaybillNo()+";"+entity.getIsPicPackage());
		}
		
	}

	@Test
	public void testQueryWaybillRelateDetailsByWaybillNos() {
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("5820151119");
		waybillNoList.add("422324789");
		List<WaybillRelateDetailEntity> waybillRelateDetailEntities = waybillRelateDetailEntityService.queryWaybillRelateDetailsByWaybillNos(
				waybillNoList, 0, 0, false);
		if(CollectionUtils.isNotEmpty(waybillRelateDetailEntities)){
			System.out.println("返回的记录总数："+waybillRelateDetailEntities.size());
		}else{
			System.out.println("返回的记录为空");
		}
		for(WaybillRelateDetailEntity entity : waybillRelateDetailEntities){
			System.out.println("母件运单号："+entity.getParentWaybillNo()+"; 子运单号："+entity.getWaybillNo()+";"+entity.getIsPicPackage());
		}
	}
	
	@After
	public void tearDown() throws Exception{
		waybillRelateDetailEntityService = null ;
	}

}
