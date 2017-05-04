package com.deppon.foss.module.pickup.waybill.server.service;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillSignService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillSignService;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
import com.deppon.foss.util.DateUtils;

public class WaybillSignServiceTest {
	
	IWaybillSignService waybillSignService;

	@Before
	public void setUp() throws Exception {
		waybillSignService = (IWaybillSignService) TestHelper.get()
				.getBeanByClass(WaybillSignService.class);

	}

	@Test
	public void testQueryWayBillSignDetail() throws ParseException {
		Date startTime = DateUtils.convert("2013-01-01");
		Date endTime = new Date(System.currentTimeMillis());
		
		System.out.println("startTime:"+startTime.toString()+" endTime:"+endTime.toString());
		
		WaybillSignDetailQueryVo waybillSignDetailQueryVo = new WaybillSignDetailQueryVo() ;
		waybillSignDetailQueryVo.setDeliveryCustomerCode("649750");
		waybillSignDetailQueryVo.setStartTime(startTime);
		waybillSignDetailQueryVo.setEndTime(endTime);
		
		List<WaybillSignDetailVo> list = waybillSignService.queryWayBillSignDetail(waybillSignDetailQueryVo,0,10,true);
		
		System.out.println("list.size="+list.size());
		
		for(int i=0 ; i<list.size();i++){
			WaybillSignDetailVo waybillSignDetailVo = list.get(i);
			System.out.println(waybillSignDetailVo.getId()+" "+waybillSignDetailVo.getCreateTime()
			+" "+waybillSignDetailVo.getWaybillNo()+" "
			+waybillSignDetailVo.getDeliveryCustomerName()+" "
			+waybillSignDetailVo.getReceiveCustomerName() ) ; 
		}
	}
	
	@Test
	public void testCountQueryWayBillSignDetail(){
		Date startTime = DateUtils.convert("2013-01-01");
		Date endTime = new Date(System.currentTimeMillis());
		
		System.out.println("startTime:"+startTime.toString()+" endTime:"+endTime.toString());
		
		WaybillSignDetailQueryVo waybillSignDetailQueryVo = new WaybillSignDetailQueryVo() ;
		waybillSignDetailQueryVo.setDeliveryCustomerCode("649750");
		waybillSignDetailQueryVo.setStartTime(startTime);
		waybillSignDetailQueryVo.setEndTime(endTime);
		
		int count = waybillSignService.countQueryWayBillSignDetail(waybillSignDetailQueryVo);
		
		System.out.println("count="+count);
		
	}
	
	@Test
	public void testQueryWaybillInvalid(){
		
		List<String> list = new ArrayList<String>();
		list.add("99400011") ;//asd
		list.add("201202212");//测试包装费
		list.add("88888005");//萨达
		
		int count = waybillSignService.queryWaybillInvalid(list);
		
		System.out.println("count:"+count);
		
	}
	
	@Test
	public void testQueryWaybillBack(){
		List<String> list = new ArrayList<String>();
		list.add("6666666668") ;//asd
		list.add("");//测试包装费 9876543211
		list.add("5522000035");//萨达
		
		int count = waybillSignService.queryWaybillBack(list);
		
		System.out.println("count:"+count);
	}

}
