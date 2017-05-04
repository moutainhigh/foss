/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.test.service
 * FILE    NAME: UpdateVehicleStateTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.test.service;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBInitializationException;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfo;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationRequest;

/**
 *
 * @author 046130-foss-xuduowei
 * @date 2012-12-7 下午6:17:33
 */
public class UpdateVehicleStateTest {
	
	@Before
	public void init() {
		try {
			ESBJMSAccessor.launch();
		} catch (ESBInitializationException e) {
			e.printStackTrace();
		}
	}
	@Test
 	public void testUpdateVehicleState() throws Exception {
		CarStateInfocationRequest request = new CarStateInfocationRequest();
		//修改车辆状态对象
		CarStateInfo carInfo = new CarStateInfo();
		carInfo.setArriveDateTime(new Date());
		carInfo.setDepartureTime(new Date());
		carInfo.setCarNumber("沪AB1333");
		carInfo.setCarState("OK");
		carInfo.setCity("上海");
		request.setCarStateInfo(carInfo);
		//设置头信息
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_UPDATE_VEHICLESTATE");
		header.setVersion("1.0");
		header.setBusinessId(UUID.randomUUID().toString());
		header.setBusinessDesc1("更新车辆状态");
		System.err.println("发送请求");
		try {
			//发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			System.err.println("发送完成");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	} 
}
