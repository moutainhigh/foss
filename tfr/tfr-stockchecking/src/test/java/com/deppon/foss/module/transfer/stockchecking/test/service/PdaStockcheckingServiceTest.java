package com.deppon.foss.module.transfer.stockchecking.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.stockchecking.test.BaseTestCase;

public class PdaStockcheckingServiceTest extends BaseTestCase{
	
	@Autowired
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Test
	public void testQueryGoodsAreaCode(){
		pdaStockcheckingService.queryGoodsAreaCode("", "", "");
	}
}