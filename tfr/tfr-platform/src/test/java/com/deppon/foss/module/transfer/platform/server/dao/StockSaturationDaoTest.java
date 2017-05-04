package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PersonForTransferEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
@TransactionConfiguration(defaultRollback = false, transactionManager = "transactionManager")
public class StockSaturationDaoTest extends BaseTestCase {
	
	@Autowired
	private IStockSaturationDao stockSaturationDao;

//	@Test
//	public void testQueryStockSaturationList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockSaturationListCount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockSaturationDayList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockSaturationDayListCount() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockSaturationMonthList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryStockSaturationMonthListCount() {
//		fail("Not yet implemented");
//	}
	
	
	//key:orgCode;queryDateA;queryDateB
	//@Test
	public void testStockSaturationJobQuery() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", "W3100020616");
		map.put("queryDateA", "2013-06-26");
		map.put("queryDateB", "2013-06-27");
		map.put("threadCount", "0");
		map.put("threadNo", "0");
		List<StockSaturationEntity> list = stockSaturationDao.stockSaturationJobQuery(map);
		if(list!=null && list.size()>0){
			int i=1;
			for (StockSaturationEntity stockSaturationEntity : list) {
				System.out.println("第"+i+stockSaturationEntity.getOrgName()+"==:"+stockSaturationEntity.getLoadMeasure()+":");
				i++;
			}
		}else{
			fail("集合为空");
		}
	}
	
	
	//@Test
	public void testBatchInsertStockSaturation() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", "W3100020616");
		map.put("queryDateA", "2013-06-26");
		map.put("queryDateB", "2013-06-27");
		map.put("threadCount", "0");
		map.put("threadNo", "0");
		List<StockSaturationEntity> list = stockSaturationDao.stockSaturationJobQuery(map);
		for (StockSaturationEntity stockSaturationEntity : list) {
			stockSaturationEntity.setSaturationId(UUIDUtils.getUUID());
			stockSaturationEntity.setStatisticsTimeTheory(DateUtils.convert("2013-06-26",DateUtils.DATE_FORMAT));
		}
		if(list!=null && list.size()>0){
			stockSaturationDao.batchInsertStockSaturation(list);
		}else{
			fail("集合为空");
		}
		
	}
	@Test
	public void testInsertStockSaturation() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", "W3100020616");
		map.put("queryDateA", "2013-06-24");
		map.put("queryDateB", "2013-06-25");
		map.put("threadCount", "0");
		map.put("threadNo", "0");
		//System.out.println(DateUtils.convert("2013-06-24 10:00:00",DateUtils.DATE_TIME_FORMAT));
		List<StockSaturationEntity> list = stockSaturationDao.stockSaturationJobQuery(map);
		for (StockSaturationEntity stockSaturationEntity : list) {
			stockSaturationEntity.setSaturationId(UUIDUtils.getUUID());
			stockSaturationEntity.setStatisticsTimeTheory(DateUtils.convert("2013-06-24 00:00:00",DateUtils.DATE_TIME_FORMAT));
			stockSaturationDao.insertStockSaturation(stockSaturationEntity);
		}
	}
	
	//@Test
	public void testQueryPersonForTranferCenter(){
		List<PersonForTransferEntity> xx = stockSaturationDao.queryPersonForTranferCenter();
		if(xx!=null && xx.size()>0){
			for (PersonForTransferEntity personForTransferEntity : xx) {
				
				System.out.println(personForTransferEntity.getOrgName()+":"+personForTransferEntity.getMobilePhone());
			}
		}else{
			fail("集合为空");
		}
	}
	
	//@Test
	public void testUpdateByPrimaryKey(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("orgCode", "W3100020616");
		map.put("queryDate", "2013-06-26");
		StockSaturationEntity dbPojo = stockSaturationDao.queryStockSaturationByOrgCodeAndTime(map);
		dbPojo.setDeliverBack(new BigDecimal(8888.80));
		dbPojo.setSmssendflag("Y");
		stockSaturationDao.updateByPrimaryKey(dbPojo);
	}
}
