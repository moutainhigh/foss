/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: AutoGenerateHandOverBillDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;
import com.deppon.foss.module.transfer.load.api.server.service.IWKTfrBillService;
import com.deppon.foss.module.transfer.load.api.shared.domain.WKTfrBillEntity;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-6-21 下午2:41:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
//@TransactionConfiguration
//@Transactional
public class WKTfrBillServiceTest {

	
	@Autowired
	private IWKTfrBillService wKTfrBillService;
	
	@Autowired
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	
	
	/**
	* @description 测试表T_WK_TRF_BILL插入数据
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年5月11日 下午4:42:04
	*/
	public void testBillInsert(){
		
//		WKTfrBillEntity wKTfrBillEntity = new WKTfrBillEntity();
//		wKTfrBillEntity.setHandoverBillNo("ceshi12312dd3");
//		wKTfrBillEntity.setHandoverState("1");
//		wKTfrBillEntity.setHandoverTime(new Date());
//		wKTfrBillEntity.setHandoverType("2");
//		
//		wKTfrBillEntity.setDepartOrgCode("123321");
//		wKTfrBillEntity.setDepartOrgName("零担业务组");
//		wKTfrBillEntity.setArriveOrgName("系统架构");
//		wKTfrBillEntity.setArriveOrgCode("22222");
//		
//		wKTfrBillEntity.setTransportType("2");
//		wKTfrBillEntity.setVehicleNo("sb449ss44");
		
//		int val = wKTfrBillService.insertBill(wKTfrBillEntity);
//		System.out.println("插入结果" + val);
	}
	
	
	
	/**
	* @description 测试向表T_WK_TRF_BILL更新数据
	* @version 1.0
	* @author 328864-foss-xieyang
	* @update 2016年4月22日 下午4:27:33
	*/
	public void testBillUpdate(){
//		WKTfrBillEntity wKTfrBillEntity = new WKTfrBillEntity();
//		wKTfrBillEntity.setHandoverBillNo("ceshi123123");
//		wKTfrBillEntity.setHandoverState("1");
//		wKTfrBillEntity.setHandoverTime(new Date());
//		wKTfrBillEntity.setHandoverType("2");
//		
//		wKTfrBillEntity.setDepartOrgCode("123321");
//		wKTfrBillEntity.setDepartOrgName("零担业务组");
//		wKTfrBillEntity.setArriveOrgName("系统架构");
//		wKTfrBillEntity.setArriveOrgCode("22222");
//		
//		wKTfrBillEntity.setTransportType("2");
//		wKTfrBillEntity.setVehicleNo("sb44944");
//		
//		int val = wKTfrBillService.updateBill(wKTfrBillEntity);
//		System.out.println("更新结果是:" + val);
	}
	
	
	public void testSelect() {
//		WKTfrBillEntity wKTfrBillEntity = new WKTfrBillEntity();
//		wKTfrBillEntity.setHandoverBillNo("ceshi1231ss23");
//		
//		wKTfrBillEntity = wKTfrBillService.getWKTfrBillEntity(wKTfrBillEntity);
//		System.out.println(wKTfrBillEntity.getAboutVehicleNo());
	}
	
	@Test
	public void mainTest() {
//		HeavyBubbleRatioEntity entity = new HeavyBubbleRatioEntity();
//		heavyBubbleRatioService.addHeavyBubbleRatio(entity );
//		testBillInsert();
//		testBillUpdate();
		testSelect();
	}



	public void setHeavyBubbleRatioService(IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}
	
	
	
	
	
}
