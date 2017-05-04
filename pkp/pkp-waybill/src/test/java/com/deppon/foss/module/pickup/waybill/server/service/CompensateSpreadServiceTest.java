package com.deppon.foss.module.pickup.waybill.server.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.service.ICompensateSpreadService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CompensateSpreadService;

public class CompensateSpreadServiceTest {

	private ICompensateSpreadService waybillExpressService = null;
	AdjustPlanSearcherDto condition;
	private static ApplicationContext ctx = null;
	String[] xmls = new String[] { "com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml",
			"classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/billCalculate.xml"};
	
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillExpressService == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillExpressService = ctx.getBean(CompensateSpreadService.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testcalculateSpread() throws Exception {
		waybillExpressService.calculateSpread("711517801");
	}
}
