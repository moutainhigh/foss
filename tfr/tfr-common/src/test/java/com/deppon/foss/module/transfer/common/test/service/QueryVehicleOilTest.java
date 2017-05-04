/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.test.service
 * FILE    NAME: UpdateVehicleStateTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.test.service;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.exception.ESBInitializationException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vehicle.OilCostSyncRequest;

/**
 *
 * @author 046130-foss-xuduowei
 * @date 2012-12-7 下午6:17:33
 */
public class QueryVehicleOilTest {
	
	/*@Before
	public void init() {
		
	}
	@Test
 	public void testQueryVehicleOil() throws Exception {
		try {
			ESBJMSAccessor.launch();
		} catch (ESBInitializationException e) {
			e.printStackTrace();
		}
		OilCostSyncRequest request = new OilCostSyncRequest();
		request.setCarNo("沪BK9540");
		request.setBeginTime(new Date());
		request.setEndTime(new Date());
		
		//设置头信息
		AccessHeader header = new AccessHeader();
		header.setESBServiceCode("ESB_LMS2ESB_SEND_OILCONSUMPTION");
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
	} */
	private static final Log log = LogFactory.getLog(QueryVehicleOilTest.class);
	@Before
	public void init() {
		try {
			ESBJMSAccessor.addProcess("FOSS_ESB2FOSS_SEND_OILCONSUMPTION", new IProcess() {
				public Object process(Object req) throws ESBBusinessException {
					log.info("req is :" + req);
					return null;
				}

				public Object errorHandler(Object req) throws ESBBusinessException {
					log.info("err req is :" + req);
					return null;
				}
			});
			ESBJMSAccessor.launch();
		} catch (ESBInitializationException e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	@Test
 	public void testQueryVehicleOil() throws Exception {
		OilCostSyncRequest request = new OilCostSyncRequest();
		request.setCarNo("沪BK9540");
		request.setBeginTime(new Date());
		request.setEndTime(new Date());

		// 设置头信息
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode("FOSS_ESB2FOSS_SEND_OILCONSUMPTION");
		header.setVersion("1.0");
		header.setBusinessId(UUID.randomUUID().toString());
		header.setBusinessDesc1("更新车辆状态");
		System.err.println("发送请求");
		try {
			// 发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			System.err.println("发送完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
