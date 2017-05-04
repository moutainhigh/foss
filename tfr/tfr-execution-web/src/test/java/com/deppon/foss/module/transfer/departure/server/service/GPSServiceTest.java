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

import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;

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
public class GPSServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests
{

	@Autowired
	private IGpsService gpsService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{
//		gPSToFOSSService.notifyArrivaltime("123",new Date());
//		gPSToFOSSService.notifyStarttime("123",new Date());
		GpsNotifyDTO gpsNotifyDTO=  new GpsNotifyDTO();
		gpsNotifyDTO.setVehicleId("c99e3b97-ab22-4a1f-abd3-bdb664e3b825");
		gpsNotifyDTO.setPreArrivalDate(new Date());
		gpsService.updateVehicleTrack(gpsNotifyDTO); 
		gpsService.notifyStarttime("c99e3b97-ab22-4a1f-abd3-bdb664e3b825", new Date());
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
