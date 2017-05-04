package com.deppon.foss.module.transfer.platform.server.server;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.IStockPairService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class StockPairServiceTest extends BaseTestCase {
	
	@Autowired IStockPairService stockPairService;
	
	
//	@Test
//	public void testAddStockPair() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteStockPair() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockPairEntity() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateStockPair() {
//		fail("Not yet implemented");
//	}

	//@Test
	public void testDoStockPairJob() {
		try {
			stockPairService.doStockPairJob(new Date(), 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryAllTransOrg(){
		List<TransCenterOrgEntity> xx = stockPairService.queryAllTransOrg();
		for (TransCenterOrgEntity transCenterOrgEntity : xx) {
			System.out.println(transCenterOrgEntity.getName()+" == "+transCenterOrgEntity.getDivision() +"  == "+transCenterOrgEntity.getBigdept());
		}
	}

}
