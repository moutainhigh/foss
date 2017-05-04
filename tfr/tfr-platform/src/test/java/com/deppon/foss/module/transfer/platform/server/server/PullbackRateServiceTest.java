package com.deppon.foss.module.transfer.platform.server.server;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.IPullbackRateService;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class PullbackRateServiceTest extends BaseTestCase {
	@Autowired IPullbackRateService pullbackRateService;
	
//	@Test
//	public void testQueryPullbackRateList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryPullbackRateListCount() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testDoPullbackRateJobList() {
		try {
			pullbackRateService.doPullbackRateJobList(new Date(), 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
