package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationSmsDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationSmsEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;
@TransactionConfiguration(defaultRollback = false, transactionManager = "transactionManager")
public class StockSaturationSmsDaoTest extends BaseTestCase {
	@Autowired IStockSaturationSmsDao stockSaturationSmsDao;

	//@Test
	public void testInsertStockSaturationSms() {
		StockSaturationSmsEntity stockSaturationSmsEntity = new StockSaturationSmsEntity();
		stockSaturationSmsEntity.setOrgCode("W1110");
		stockSaturationSmsEntity.setSmssendtime("2014-04-24");
		stockSaturationSmsDao.insertStockSaturationSms(stockSaturationSmsEntity);
	}

	@Test
	public void testQueryStockSaturationSms() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("orgCode", "W1110");
		map.put("smssendtime", "2014-04-24");
		StockSaturationSmsEntity  pojo = stockSaturationSmsDao.queryStockSaturationSms(map);
		if(pojo!=null){
			System.out.println("部门："+pojo.getOrgCode()+"日期："+pojo.getSmssendtime());
		}else{
			fail("查询空记录");
		}
	}

}
