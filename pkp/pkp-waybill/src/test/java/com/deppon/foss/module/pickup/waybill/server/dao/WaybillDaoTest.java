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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/dao/WaybillDaoTest.java
 * 
 * FILE NAME        	: WaybillDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-predeliver
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: QueryGoodsDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单追踪DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
public class WaybillDaoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillDaoTest.class);
	private IWaybillDao waybillDao = null;
	AdjustPlanSearcherDto condition;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[] { "com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml"
//			, "com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml" 
			};

	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillDao = ctx.getBean(WaybillDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面查询
	 * 
	 * @author ibm-wangfei
	 * @date Oct 24, 2012 5:32:43 PM
	 */
	@Test
	public void testQueryAdjustPlanCount() {
		condition = new AdjustPlanSearcherDto();
		Long count = waybillDao.queryAdjustPlanCount(condition);
		Assert.assertTrue(count > 0);
	}
	
	@Test
	public void testQueryAdjustPlan() {
		condition = new AdjustPlanSearcherDto();
//		condition.setWaybillNo("80000009");
		condition.setOperateTimeEnd(new Date());
		List<AdjustPlanResultDto> adjustPlanResultDtoList = waybillDao.queryAdjustPlan(condition, 0 ,99999);
		Assert.assertTrue(adjustPlanResultDtoList.size() > 0);
	}
	
	@Test
	public  void testUserDefinedQuery(){
		SimpleWaybillDto simpleWaybillDto = new SimpleWaybillDto();
		simpleWaybillDto.setWhereSql("where waybill_no = '12333333'");
		simpleWaybillDto.setActive(FossConstants.ACTIVE);
		waybillDao.queryWayBillListByUserDefinedQuery(simpleWaybillDto, 10);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testQueryWayBillSignDetail() {
		
//		Date startTime = new Date() ;
		Date startTime = DateUtils.convert("2015-10-01");
		Date endTime = new Date(System.currentTimeMillis());
		 
		System.out.println("startTime="+startTime.toString()+" endTime="+endTime.toString());
		 
		WaybillSignDetailQueryVo waybillSignDetailQueryVo = new WaybillSignDetailQueryVo() ;
		waybillSignDetailQueryVo.setDeliveryCustomerCode("40008282");
		waybillSignDetailQueryVo.setStartTime(startTime);
		waybillSignDetailQueryVo.setEndTime(endTime);
		
		List<WaybillSignDetailVo> list = waybillDao.queryWayBillSignDetail(waybillSignDetailQueryVo,0,10,true);
		
		System.out.println("list.size="+list.size());
		 
		for(int i=0 ; i<list.size();i++){
			WaybillSignDetailVo waybillSignDetailVo = list.get(i);
			System.out.println(waybillSignDetailVo.getId()+" "+waybillSignDetailVo.getCreateTime()
			+" "+waybillSignDetailVo.getWaybillNo()+" "
			+waybillSignDetailVo.getDeliveryCustomerName()+" "
			+waybillSignDetailVo.getReceiveCustomerName()+" "
			+waybillSignDetailVo.getPreCustomerPickupTime()) ; 
		}
	}
	
	@Test
	public void testCountQueryWayBillSignDetail(){
		Date startTime = DateUtils.convert("2013-01-01");
		Date endTime = new Date(System.currentTimeMillis());
		
		System.out.println("startTime="+startTime.toString()+" endTime="+endTime.toString());
		
		WaybillSignDetailQueryVo waybillSignDetailQueryVo = new WaybillSignDetailQueryVo() ;
		waybillSignDetailQueryVo.setDeliveryCustomerCode("649750");
		waybillSignDetailQueryVo.setStartTime(startTime);
		waybillSignDetailQueryVo.setEndTime(endTime);
		
		int count = waybillDao.countQueryWayBillSignDetail(waybillSignDetailQueryVo);
		
		System.out.println("count="+count);
		
	}
	
	@Test
	public void testQueryWaybillInvalid(){
		
		List<String> list = new ArrayList<String>();
		list.add("99400011") ;//asd
		list.add("201202212");//测试包装费
		list.add("88888005");//萨达
		
		int count = waybillDao.queryWaybillInvalid(list);
		
		System.out.println("count:"+count);
		
	}
	
	@Test
	public void testQueryWaybillBack(){
		List<String> list = new ArrayList<String>();
		list.add("6666666668") ;//asd
		list.add("9876543211");//测试包装费
		list.add("5522000035");//萨达
		
		int count = waybillDao.queryWaybillBack(list);
		
		System.out.println("count:"+count);
	}
	
	@Test
	public void testQueryWaybillDeliverGoodsList(){   
		 
		Date startTime = DateUtils.convert("2014-09-13");
		Date endTime = DateUtils.convert("2015-09-19");
//		Date endTime = new Date(System.currentTimeMillis());
		
		System.out.println("startTime="+startTime.toString()+" endTime="+endTime.toString());
		
		List<String> waybillNoList = new ArrayList<String>() ;
//		String[] str = {"5755555555","5655555555","5599999999","5522999999","5522999998",
//		"5522999988","5089789682","235556428","235556422","15081001" } ;
		String[] str = {"5023292436", "5025490436","149465855" ,  "135638173" ,"101511128"} ;
		for(String res : str){
			waybillNoList.add(res) ;
		} 
		
		DeliverGoodsListQueryVo deliverGoodsListQueryVo = new DeliverGoodsListQueryVo();
		deliverGoodsListQueryVo.setDeliveryCustomerCode("400037946");
		deliverGoodsListQueryVo.setStartTime(startTime);
		deliverGoodsListQueryVo.setEndTime(endTime);
//		deliverGoodsListQueryVo.setGoodsStatus("SIGN_STATUS_ALL");
//		deliverGoodsListQueryVo.setWaybillNoList(waybillNoList);
		
//		System.out.println(DeliverGoodsListQueryVo.toString());
		
		int count = waybillDao.queryWaybillDeliverGoodsListCount(deliverGoodsListQueryVo);
		System.out.println("count="+count);
		
		List<DeliverGoodsListVo> deliverGoodsListVos = waybillDao.queryWaybillDeliverGoodsList(deliverGoodsListQueryVo, 0, 20);
		System.out.println("deliverGoodsListVos.size()="+deliverGoodsListVos.size());
		for(DeliverGoodsListVo vo : deliverGoodsListVos){
			System.out.println(vo.toString());
		}

	}
	
}