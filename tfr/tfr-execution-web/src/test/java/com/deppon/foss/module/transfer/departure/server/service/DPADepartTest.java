package com.deppon.foss.module.transfer.departure.server.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDADepartService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDADepartDto;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath*:com/deppon/foss/module/departrue/test/META-INF/spring.xml" })
@TransactionConfiguration
@Transactional
public class DPADepartTest extends
		AbstractTransactionalJUnit4SpringContextTests
{

	@Autowired
	private IPDADepartService pdaDepartService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{ 
		PDADepartDto pdaDto = new PDADepartDto();
		pdaDto.setVehicleNo("HU-C-00005");
		pdaDto.setOperatingTime(new Date());
		pdaDto.setOperator("123");
		pdaDto.setOrgCode("123");
		pdaDto.setPdaTerminalNo("123");
		pdaDepartService.writeDepartData(pdaDto);  
	} 

	/**
	 * 
	 * 测试新增运单客户通知记录
	 * 
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	@Transactional 
	public void testWriteDepartData()
	{
//		sharedService.queryDriverInfoByVehicleNo("123");
	}

}
