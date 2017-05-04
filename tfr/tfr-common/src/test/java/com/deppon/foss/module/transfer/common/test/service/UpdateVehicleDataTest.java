/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.test.service
 * FILE    NAME: UpdateVehicleStateTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBInitializationException;
import com.deppon.esb.inteface.domain.vehicle.CarRunInfo;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationRequest;

/**
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-12-7 下午6:17:33
 */
public class UpdateVehicleDataTest {
	
	@Before
	public void init() {
		try {
			ESBJMSAccessor.launch();
		} catch (ESBInitializationException e) {
			e.printStackTrace();
		}
	}
	@Test
 	public void testUpdateVehicleData() throws Exception {
		
		CarRuncationRequest request = new CarRuncationRequest();
		CarRunInfo carRunInfo = new CarRunInfo();
		//车牌号
		carRunInfo.setCarNumber("沪A2312");
		//车辆使用类型
		carRunInfo.setCarUsage("PICK_GOODS");
		//货柜号
		carRunInfo.setContainerNo("g12334");
		//原始公里数据
		carRunInfo.setOriginalKilometer(new BigDecimal("2334"));
		//目的公里数据
		carRunInfo.setPurposeKilometer(new BigDecimal("4334"));
		//
		carRunInfo.setReachDateTime(new Date());
		carRunInfo.setSoleNumber("u12345");
		request.setCarRunInfo(carRunInfo);
		
		//设置头信息
		//获取头信息
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_DRIVE_KM");
		header.setVersion("1.0");
		header.setBusinessId(UUID.randomUUID().toString());
		header.setBusinessDesc1("更新车辆行驶数据");
		System.err.println("发送开始");
		try {
			//发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			System.err.println("发送完成");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	} 
}
