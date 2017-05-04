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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/dao/ArriveSheetDaoTest.java
 * 
 * FILE NAME        	: ArriveSheetDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetActualFreightDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 到达联Dao 测试类
 * @author 097972-foss-dengtingting
 * @date 2012-12-14 下午3:10:47
 */
public class ArriveSheetDaoTest {
	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/predeliver/test/META-INF/spring.xml"
			};
	
	private static ApplicationContext ctx = null;
	
	private IArrivesheetDao arrivesheetDao;
	
	@Before
	public void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		arrivesheetDao = (IArrivesheetDao) ctx.getBean("arrivesheetDao");
	}
	
	@Test
	public void testQueryArriveSheetByWayblillNo(){
		ArriveSheetActualFreightDto dto = new ArriveSheetActualFreightDto();
		dto.setActive(FossConstants.YES);
		dto.setEndStockOrgCode("GS00002");
		dto.setWaybillNo("2012001");
		int num = arrivesheetDao.queryArriveSheetByWaybillNo(dto);
		Assert.assertEquals(3, num);
	}
	
	@Test
	public void testQueryArriveSheetByDriverCode(){
		ArrivesheetDeliverDto dto = new ArrivesheetDeliverDto();
		dto.setActive("Y");
		dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
		List<ArrivesheetDeliverDto> list = arrivesheetDao.queryArriveSheetByDriverCode(dto);
		Assert.assertEquals(5, list.size());
	}

}