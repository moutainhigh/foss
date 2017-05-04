package com.deppon.foss.module.stock.server;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAStockService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.server.service.impl.PDAStockService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@Transactional
public class StockServiceTest extends BaseTestCase {
	@Autowired
	private IStockService stockService;
	private IStockDao stockDao;
	
	//测试更新是否已经建包 service
	//@Test
		public void testUpdateIsPackageService() {
			
			stockService.updateIsPackage("11111117","0001","W01140402");
			
		}
		
	//测试更新是否已经建包 dao
		//@Test
			public void testUpdateIsPackageDao() {
				
				stockDao.updateIsPackage("11111117","0001","W01140402");
				
			}
		
	//@Test
	public void testInStockUnload() {
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("11111117");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setInOutStockType(StockConstants.UNLOAD_GOODS_IN_STOCK_TYPE);
		inOutStockEntity.setOperatorCode("卸车入库");
		inOutStockEntity.setOperatorName("卸车入库");
		stockService.inStockUnload(inOutStockEntity);
	}
	//@Test
	public void testIsExistOtherGoodsAreaStock(){
		if( stockService.isExistOtherGoodsAreaStock("W01140402", "11111117", "0001", "561")){
			System.out.println("===");
		}else{
			System.out.println("|||");
		}
	}
	
	/**
	* 
	* @description  库位操作
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-4 上午9:45:55
	*/
	//@Test
	public void testUpdateStockStockPosition(){
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO("11111117");
		inOutStockEntity.setSerialNO("0001");
		inOutStockEntity.setOrgCode("W01140402");
		inOutStockEntity.setGoodsAreaCode("561");
		inOutStockEntity.setPosition("120");
		inOutStockEntity.setOperatorCode("005556");
		inOutStockEntity.setOperatorName("阿玉顺");
		inOutStockEntity.setInOutStockType("sss");
		inOutStockEntity.setDeviceType("pc");
		//设置当前调用时间为扫描时间
		inOutStockEntity.setInOutStockTime(new Date());
		inOutStockEntity.setScanTime(new Date());
		//int back = stockService.updateStockStockPosition(inOutStockEntity, "0001");
		//System.out.println("out======================"+back);
	}
	
	@Test
	public void testAreaByOrgcodeList(){
		IPDAStockService pDAStockService = new PDAStockService(); 
		List<BaseDataDictDto> xx = pDAStockService.areaByOrgcodeList("W3100020616");
		if(xx!=null && xx.size()>0){
			System.out.println("dddd");
		}else{
			System.out.println("====");
		}
	}
}
