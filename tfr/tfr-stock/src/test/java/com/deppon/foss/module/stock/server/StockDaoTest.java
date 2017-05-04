package com.deppon.foss.module.stock.server;

import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.server.dao.ITogetherTruckStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class StockDaoTest extends BaseTestCase {
	@Autowired
	private IStockDao stockDao;
	@Autowired
	private ITogetherTruckStockDao togeDao;

	
	@Test
	public void testAdd(){
		StockEntity s = new StockEntity();
		s.setWaybillNO("444444441");
		s.setSerialNO("1006");
		s.setInStockTime(new Date());
		s.setGoodsAreaName("------99999-----");
		s.setGoodsAreaCode("99999");
		s.setOperatorName("zhansj");
		s.setDeviceType("PDA");
		s.setOperatorCode("11111");
		s.setCreateTime(new Date());
		s.setBePackage("Y");
		s.setOrgCode("W111111111");
		s.setNextOrgCode("W22222");
		stockDao.addStock(s);
	}

	@Test
	public void testDelete(){
		InOutStockEntity s = new InOutStockEntity();
		s.setWaybillNO("44444444");
		s.setSerialNO("0001");
		s.setOrgCode("W00001");
		stockDao.deleteStock(s);
	}

	@Test
	public void testDeleteBatch(){
		List<InOutStockEntity> list = new ArrayList<InOutStockEntity>();
		InOutStockEntity s = new InOutStockEntity();
		s.setWaybillNO("44444444");
		s.setSerialNO("0001");
		list.add(s);
		stockDao.outStockBatchPC(list);
	}

	@Test
	public void testAddSale(){
		StockSaleEntity s = new StockSaleEntity();
		s.setWaybillNo("555555555");
		s.setSerialNO("0002");
		s.setOrgCode("W55555000444");
		s.setGoodsAreaCode("555555");
		s.setInStockTime(new Date());
		s.setScanTime(new Date());
		s.setOperatorCode("335284");
		s.setOperatorName("dddddddd");
		s.setDeviceType("PDA");
		stockDao.addStockSale(s);
	}

	@Test
	public void testAddToge(){
		TogetherTruckStockEntity s = new TogetherTruckStockEntity();
		s.setOrgCode("W22222");
		s.setWaybillNO("88888888888");
		s.setSerialNO("333");
		togeDao.addTogetherTruckStock(s);
	}
}
