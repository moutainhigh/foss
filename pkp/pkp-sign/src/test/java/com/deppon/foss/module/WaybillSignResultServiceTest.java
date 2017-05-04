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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/WaybillSignResultServiceTest.java
 * 
 * FILE NAME        	: WaybillSignResultServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


public class WaybillSignResultServiceTest {
	private IWaybillManagerService waybillManagerService = null;
	private IWaybillSignResultService waybillSignResultService = null;
	private IArrivesheetDao arrivesheetDao = null;
	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/sign/test/META-INF/spring.xml"};
		
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillManagerService == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillManagerService = ctx.getBean(IWaybillManagerService.class);
			}
			if(ctx == null || waybillSignResultService == null){
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillSignResultService = ctx.getBean(IWaybillSignResultService.class);
			}
			if(ctx == null || arrivesheetDao == null){
				ctx = new ClassPathXmlApplicationContext(xmls);
				arrivesheetDao = ctx.getBean(IArrivesheetDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void testrepayment() 
//	{	
//		WaybillSignResultDto dto = new WaybillSignResultDto();
//		
//		//起始时间
//		dto.setSignTimeStart(DateUtils.addDayToDate(new Date(), -1000));
//		//结束时间
//		dto.setSignTimeEnd(new Date());
//		//生效
//		dto.setActive(FossConstants.ACTIVE);
//		// 签收状态--全部签收
//		dto.setSignStatus(SignConstants.SIGN_STATUS_ALL);
//		AirWaybillCondition condition = new AirWaybillCondition();
//		condition.setWaybillNo("201314111");
//		condition.setActive("Y");
//		AirWaybillDto airWaybillDto = waybillSignResultDao.queryWaybillInfoForEdi(condition);
//		System.out.println(airWaybillDto.getWeight());
////		for (String string : dd) {
////			System.out.println("-------");
////			System.out.println(string);
////		}
//	}
//	
//	
//	@Test
//	public void testrep3ayment() 
//	{	
//		WaybillSignResultDto dto = new WaybillSignResultDto();
//		
//		//起始时间
//		dto.setSignTimeStart(DateUtils.addDayToDate(new Date(), -1000));
//		//结束时间
//		dto.setSignTimeEnd(new Date());
//		//生效
//		dto.setActive(FossConstants.ACTIVE);
//		// 签收状态--全部签收
//		dto.setSignStatus(SignConstants.SIGN_STATUS_ALL);
//		AirWaybillCondition condition = new AirWaybillCondition();
//		condition.setWaybillNo("201314111");
//		condition.setActive("Y");
//		AirWaybillDto airWaybillDto = waybillSignResultDao.queryWaybillInfoForEdi(condition);
//		System.out.println(airWaybillDto.getWeight());
//	}
	
	@Test
	public void sendmessage(){
		String waybillNo = "9909061306";
		ArriveSheetEntity entity = new ArriveSheetEntity();
		entity.setWaybillNo(waybillNo);
		WaybillSignResultDto dto = new WaybillSignResultDto();
//		entity.setId("0968567e-568f-4b27-bf9e-be1ed300e270");
//		entity = arrivesheetDao.queryArriveSheetByEntity(entity);
		BeanUtils.copyProperties(entity,dto);//复制代码
		CurrentInfo currentinfo = FossUserContext.getCurrentInfo();	
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
		System.out.println(entity);
		System.out.println(dto);
		System.out.println(waybill);
		System.out.println(currentinfo);
		//调用短信接口
//		waybillSignResultService.sendMessNotice(waybill,currentinfo,dto);
	}
}