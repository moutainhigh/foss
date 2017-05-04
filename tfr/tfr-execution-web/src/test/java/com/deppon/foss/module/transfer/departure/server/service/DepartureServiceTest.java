//package com.deppon.foss.module.transfer.departure.server.service;
//
//import java.util.Date;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.BeforeTransaction;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.deppon.esb.inteface.domain.gps.NotifyArrivaltimeRequestType;
//import com.deppon.esb.inteface.domain.gps.NotifyStarttimeRequestType;
//import com.deppon.foss.module.transfer.departure.api.server.service.IGPSToFOSSService;
//
///**
// * 
// * 通知客户DAO测试类
// * 
// * @author ibm-wangfei
// * @date Oct 24, 2012 5:06:00 PM
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations =
//{ "classpath*:com/deppon/foss/module/departrue/test/META-INF/spring.xml" })
//@TransactionConfiguration
//@Transactional
//public class DepartureServiceTest extends
//		AbstractTransactionalJUnit4SpringContextTests
//{
//
//	@Autowired
//	private IGPSToFOSSService departureService = null;
//
//	@BeforeTransaction
//	public void beforeTransaction()
//	{
//		
//	}
//
//	/**
//	 * 
//	 * 测试新增运单客户通知记录
//	 * 
//	 * @author ibm-liubinbin
//	 * @date Oct 24, 2012 5:29:55 PM
//	 */
//	@Test
//	@Transactional
//	public void testWriteDepartData()
//	{
//		NotifyStarttimeRequestType parameters = new NotifyStarttimeRequestType();
//		parameters.setVehicleId("123");
//		departureService.notifyStarttime(parameters);
//		NotifyArrivaltimeRequestType notifyArrivaltimeRequestType = new NotifyArrivaltimeRequestType();
////		notifyArrivaltimeRequestType.setArrivalTime(new Date());
//		notifyArrivaltimeRequestType.setVehicleId("123");
//		departureService.notifyArrivaltime(notifyArrivaltimeRequestType);
//	}
//
//}
