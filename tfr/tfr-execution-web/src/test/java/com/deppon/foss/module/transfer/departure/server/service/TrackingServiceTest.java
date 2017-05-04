package com.deppon.foss.module.transfer.departure.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO;

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
public class TrackingServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests
{

	@Autowired
	private ITrackingService trackingService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{
		HandoverBillDTO handoverBillDTO = new HandoverBillDTO();
		handoverBillDTO.setWaybillNo("44448909");
//		trackingService.getInStockTrackingByWayBillNo("52100043",DepartureHandle.getDate("2013-02-12 11:11:11"));
		trackingService.getHandoverBillByWayBillNo(handoverBillDTO,null);
//		trackingService.getTaskTrackingByWayBillNo("186885500"); 
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
