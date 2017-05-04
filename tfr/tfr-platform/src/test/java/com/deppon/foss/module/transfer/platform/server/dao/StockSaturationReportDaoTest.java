package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.dao.IStockSaturationReportDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationReportEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class StockSaturationReportDaoTest  extends BaseTestCase {
	@Autowired IStockSaturationReportDao stockSaturationReportDao;
	
	//@Test
	public void testQueryStockSaturationReport() {
		List<StockSaturationReportEntity> xx = stockSaturationReportDao.queryStockSaturationReport("2014-04-01", "2014-05-01", -1, -1);
		if(xx!=null && xx.size()>0){
			for (StockSaturationReportEntity stockSaturationReportEntity : xx) {
				System.out.println(stockSaturationReportEntity.getOrgName()
						+"Bigdept:"+stockSaturationReportEntity.getBigdept()
						+"Division:"+stockSaturationReportEntity.getDivision()
						+"BigArea:"+stockSaturationReportEntity.getBigArea()
						+"day:"+stockSaturationReportEntity.getSaturationDay()
						+"month:"+stockSaturationReportEntity.getSaturationMonth());
			}
		}else{
			fail("空集合");
		}
	}
	
	@Test
	public void queryStockSaturationReportCount(){
		int  xx= stockSaturationReportDao.queryStockSaturationReportCount();
		System.out.println("reportCount:"+xx);
	}

}
