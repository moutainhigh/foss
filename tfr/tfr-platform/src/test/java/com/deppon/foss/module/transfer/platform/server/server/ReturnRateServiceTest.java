package com.deppon.foss.module.transfer.platform.server.server;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.IReturnRateService;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class ReturnRateServiceTest extends BaseTestCase {
	@Autowired IReturnRateService returnRateService;
//	
//	@Test
//	public void testQueryReturnRateList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryReturnRateListCount() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testDoReturnRateJobList() {
		try {
			returnRateService.doReturnRateJobList(new Date(), 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
