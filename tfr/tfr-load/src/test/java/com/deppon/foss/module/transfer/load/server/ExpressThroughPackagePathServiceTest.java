package com.deppon.foss.module.transfer.load.server;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.service.IExpressThroughPackagePathService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml",
		"classpath:com/deppon/foss/module/transfer/load/server/test/spring.xml" })
@TransactionConfiguration
@Transactional
public class ExpressThroughPackagePathServiceTest {

	@Autowired
	private IExpressThroughPackagePathService expressThroughPackagePathService;
	
	@Test
	public void testCreateThroughPackagePath() {
		//fail("Not yet implemented");
		expressThroughPackagePathService.createThroughPackagePath("B00000000001");
	}

}
