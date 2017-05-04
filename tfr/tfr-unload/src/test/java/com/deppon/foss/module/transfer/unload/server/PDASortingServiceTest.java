package com.deppon.foss.module.transfer.unload.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDASortingService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDASortingServiceTest {
	IPDASortingService pdaSortingService;
	@Test
	public void test(){
		SortingScanDto record = new SortingScanDto();
		pdaSortingService.scan(record);
	}
}
