package com.deppon.foss.module.transfer.platform.server.server;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.ISendRateService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.SendRateEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class SendRateServiceTest extends BaseTestCase {
	@Autowired ISendRateService sendRateService;
	
//	@Test
//	public void testQuerySendRateList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQuerySendRateListCount() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testQuerySendRateLogList() {
		SendRateEntity sendRateEntity = new SendRateEntity();
		sendRateEntity.setBeginDate("2013-12-31");
		sendRateEntity.setEndDate("2014-03-20");
		sendRateEntity.setOrgCode("");
		List<SendRateEntity> xx= sendRateService.querySendRateLogList(sendRateEntity, -1, -1);
		for (SendRateEntity sendRateEntity2 : xx) {
			System.out.println(sendRateEntity2.getOrgName()+"=="+sendRateEntity2.getYesterdayStockWaybill());
		}
	}

	@Test
	public void testQuerySendRateLogListCount() {
		SendRateEntity sendRateEntity = new SendRateEntity();
		sendRateEntity.setBeginDate("2013-12-31");
		sendRateEntity.setEndDate("2014-03-20");
		sendRateEntity.setOrgCode("");
		
		System.out.println("==="+sendRateService.querySendRateLogListCount(sendRateEntity));
	}

	//@Test
	public void testDoSendRateJobList() {
		try {
			sendRateService.doSendRateJobList(new Date(), 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
