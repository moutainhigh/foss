package com.deppon.foss.services.tracking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWayBillNoLocusService;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ 
		"classpath*:com/deppon/foss/services/tracking/test/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfo.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/administrativeRegions.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizations.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionary.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryCache.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryValue.xml"
})
@TransactionConfiguration
@Transactional
public class WayBillNoLocusServiceTest
{

	@Autowired
	private IWayBillNoLocusService wayBillNoLocusService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{
//		wayBillNoLocusService.getWayBillNoLocus("222333149"); 
//		wayBillNoLocusService.getWayBillNoLocus("44449901");
		wayBillNoLocusService.getWayBillNoLocus("44448909"); 
//		WayBillTrackService.
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
