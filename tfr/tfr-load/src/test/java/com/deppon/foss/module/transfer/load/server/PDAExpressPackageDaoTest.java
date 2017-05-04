/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: PDALoadDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressPackageDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;


/**
 * PDALoadDaoTest
 * @author dp-duyi
 * @date 2012-12-14 下午1:44:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDAExpressPackageDaoTest {
	@Autowired
	IPDAExpressPackageDao pdaExpressPackageDao;
	final Logger logger = LoggerFactory.getLogger(PDAExpressPackageDaoTest.class);
	@Test
	public void listTest(){
		List<ExpressPackageDetailDto> wayBillGoodsList  = new ArrayList<ExpressPackageDetailDto>();
		ExpressPackageDetailDto g = new ExpressPackageDetailDto();
		wayBillGoodsList.add(g);
		if(CollectionUtils.isNotEmpty(wayBillGoodsList.get(0).getSerialNos())){
			System.out.println("非空");
			wayBillGoodsList.get(0).getSerialNos().add("0000");
		}else{
			List<String> s = new ArrayList<String>();
			s.add("0001");
			wayBillGoodsList.get(0).setSerialNos(s);
		}
		System.out.println(wayBillGoodsList.get(0).getSerialNos());
	}
	@Test
	public void queryPackage(){
		List<String> l = new ArrayList<String>();
		l.add("s");
		pdaExpressPackageDao.queryPackage("sdfdsf", new Date(), new Date(), l);
	}
	@Test
	public void queryPackageByNo(){
		pdaExpressPackageDao.queryPackageByNo("sdfds");
		pdaExpressPackageDao.queryPackageByNo("B00000006");
	}
	@Test
	public void insertExpressPackage(){
		ExpressPackageEntity packageEntity = new ExpressPackageEntity();
		packageEntity.setArriveOrgCode("sdfs");
		packageEntity.setArriveOrgName("sdfsf");
		packageEntity.setCreateTime(new Date());
		packageEntity.setCreateUserCode("sdfdsf");
		packageEntity.setCreateUserName("sfdsfs");
		packageEntity.setDepartOrgCode("sdfsdfs");
		packageEntity.setDepartOrgName("sdfds");
		packageEntity.setId("sdfds");
		packageEntity.setPackageNo("sfdsf");
		packageEntity.setStatus("sdfds");
		pdaExpressPackageDao.insertExpressPackage(packageEntity);
	}
	@Test
	public void updateExpressPackage(){
		ExpressPackageEntity packageEntity = new ExpressPackageEntity();
		packageEntity.setPackageNo("sdfds");
		packageEntity.setStatus("sfddsf");
		packageEntity.setEndTime(new Date());
		pdaExpressPackageDao.updateExpressPackage(packageEntity);
	}
	@Test
	public void insertExpressPackageDetail(){
		ExpressPackageDetailEntity packageDetailEntity = new ExpressPackageDetailEntity();
		packageDetailEntity.setCreateTime(new Date());
		packageDetailEntity.setDeviceNo("sdfsdf");
		packageDetailEntity.setGoodsName("sdfsdf");
		packageDetailEntity.setGoodsQty(1);
		packageDetailEntity.setGoodsState("sdfsdf");
		packageDetailEntity.setId("sfsdf");
		packageDetailEntity.setNotes("sfsfd");
		packageDetailEntity.setPack("sdfsdf");
		packageDetailEntity.setPackageNo("sfsfd");
		packageDetailEntity.setReachOrgName("sfsdf");
		packageDetailEntity.setRecieveOrgName("sdfsdf");
		packageDetailEntity.setScanState("sdfsdf");
		packageDetailEntity.setScanTime(new Date());
		packageDetailEntity.setSerialNo("sdfsdf");
		packageDetailEntity.setTransportTypeCode("sdfsdf");
		packageDetailEntity.setTransportTypeName("sdfsdfr");
		packageDetailEntity.setVolume(1);
		packageDetailEntity.setWayBillNo("sdfsf");
		packageDetailEntity.setWeight(1);
		pdaExpressPackageDao.insertExpressPackageDetail(packageDetailEntity);
	}
	@Test
	public void queryStockPackageGoods(){
		pdaExpressPackageDao.queryStockPackageGoods("sdfsdf");
	}
	@Test
	public void queryScanPackageGoods(){
		pdaExpressPackageDao.queryScanPackageGoods("sdfsd");
	}
	@Test
	public void queryPackageQty(){
		pdaExpressPackageDao.queryPackageQty("sdfsdf");
	}
	@Test
	public void statisticalData(){
		pdaExpressPackageDao.statisticalData("sdfsd");
	}
	@Test
	public void queryExpressPackageDetail(){
		pdaExpressPackageDao.queryExpressPackageDetail("sdfdsf", "sdfsdf", "sdfs");
	}
	@Test
	public void updateExpressPackageDetail(){
		ExpressPackageDetailEntity packageDetailEntity = new ExpressPackageDetailEntity();
		packageDetailEntity.setCreateTime(new Date());
		packageDetailEntity.setDeviceNo("sdfsdf");
		packageDetailEntity.setGoodsName("sdfsdf");
		packageDetailEntity.setGoodsQty(1);
		packageDetailEntity.setGoodsState("sdfsdf");
		packageDetailEntity.setId("sfsdf");
		packageDetailEntity.setNotes("sfsfd");
		packageDetailEntity.setPack("sdfsdf");
		packageDetailEntity.setPackageNo("sfsfd");
		packageDetailEntity.setReachOrgName("sfsdf");
		packageDetailEntity.setRecieveOrgName("sdfsdf");
		packageDetailEntity.setScanState("sdfsdf");
		packageDetailEntity.setScanTime(new Date());
		packageDetailEntity.setSerialNo("sdfsdf");
		packageDetailEntity.setTransportTypeCode("sdfsdf");
		packageDetailEntity.setTransportTypeName("sdfsdfr");
		packageDetailEntity.setVolume(1);
		packageDetailEntity.setWayBillNo("sdfsf");
		packageDetailEntity.setWeight(1);
		int updateCount = pdaExpressPackageDao.updateExpressPackageDetail(packageDetailEntity);
		System.out.println("updateCount1 "+String.valueOf(updateCount));
		packageDetailEntity.setPackageNo("B00000012");
		packageDetailEntity.setWayBillNo("10000000004");
		packageDetailEntity.setSerialNo("0001");
		updateCount = pdaExpressPackageDao.updateExpressPackageDetail(packageDetailEntity);
		System.out.println("updateCount2 "+String.valueOf(updateCount));
	}
	@Test
	public void updateStockIsPackage(){
		pdaExpressPackageDao.updateStockIsPackage("sdfds", "sdfsd");
	}
	@Test
	public void queryScanPackageDetails(){
		pdaExpressPackageDao.queryScanPackageDetails("sdfsd");
	}
	@Test
	public void queryPackageCountByNo(){
		int a = pdaExpressPackageDao.queryPackageCountByNo("ddddd");
		System.out.println("a1"+String.valueOf(a));
		a = pdaExpressPackageDao.queryPackageCountByNo("B00000006");
		System.out.println("a2"+String.valueOf(a));
	}
	@Test
	public void deletePackageDetail(){
		int a = pdaExpressPackageDao.deletePackageDetail("1");
		System.out.println("deleteCount1 "+String.valueOf(a));
		a = pdaExpressPackageDao.deletePackageDetail("B000000699");
		System.out.println("deleteCount2 "+String.valueOf(a));
	}
}
