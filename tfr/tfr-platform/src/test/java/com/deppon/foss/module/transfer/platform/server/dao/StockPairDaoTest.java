package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.dao.IStockPairDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockPairEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TransCenterOrgEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class StockPairDaoTest extends BaseTestCase {
	@Autowired IStockPairDao stockPairDao;
	
	//@Test
	public void testAddStockPair() {
		fail("Not yet implemented");
	}

	//@Test
	public void testDeleteStockPair() {
		fail("Not yet implemented");
	}

	//@Test
	public void testQueryStockPairEntity() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("threadCount", "0");
		map.put("threadNo", "0");
		try{
			List<StockPairEntity> xx = stockPairDao.queryStockPairJob(map);
			System.out.println(xx.size());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	//@Test
	public void testUpdateStockPair() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testQueryAllTransOrg(){
		List<TransCenterOrgEntity> xx = stockPairDao.queryAllTransOrg();
		for (TransCenterOrgEntity transCenterOrgEntity : xx) {
			System.out.println(transCenterOrgEntity.getName()+" == "+transCenterOrgEntity.getDivision() +"  == "+transCenterOrgEntity.getBigdept());
		}
	}

}
