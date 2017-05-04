package com.deppon.foss.module.pickup.waybill.server.service.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerWaybillExpInfoRequest;
//import com.deppon.esb.inteface.domain.foss2ptp.ExpressWaybillInfoRequest;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;

public class WaybillInfoToPtpServiceTest {

	private IWaybillInfoToPtpService waybillInfoToPtpService = null ;
	
	private Logger logger = LoggerFactory.getLogger(WaybillInfoToPtpServiceTest.class);
	
	@Before
	public void setUp(){
		waybillInfoToPtpService = TestHelper.get().getBeanByClass(WaybillInfoToPtpService.class);
	}
			
	@Test
	public void testAsynSendWaybillInfoToPtp() {
//		try {
//			ExpressWaybillInfoRequest expressWaybillInfoRequest = ExpressWaybillInfoRequest.class.newInstance();
//			expressWaybillInfoRequest.setWaybillNo("123456");
//			ExpressWaybillInfoRequestTrans trans = ExpressWaybillInfoRequestTrans.class.newInstance();
//			Log.info("fromMessage:"+trans.fromMessage(expressWaybillInfoRequest));
//			logger.info("toString:"+ReflectionToStringBuilder.toString(ExpressWaybillInfoRequest.class));
//			waybillInfoToPtpService.asynSendWaybillInfoToPtp();
		
		
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	
	}

}
