/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service
 * FILE    NAME: UnloadTaskServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.unload.api.server.dao.IPDASortingDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SortingScanEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SortingScanVo;


/**
 * 卸车任务测试
 * @author dp-duyi
 * @date 2012-11-26 下午3:34:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDASortingDaoTest {
	final Logger logger = LoggerFactory.getLogger(PDASortingDaoTest.class);

	@Autowired
	IPDASortingDao pdaSortingDao;

	@BeforeTransaction
	public void beforeTransaction() {

	}

	@AfterTransaction
	public void afterTransaction() {

	}

	@Test
	public void insert() {
		SortingScanEntity sortingScanEntity = new SortingScanEntity();
		sortingScanEntity.setCreateTime(new Date());
		sortingScanEntity.setDeviceNo("2343");
		sortingScanEntity.setId("sdfdsf");
		sortingScanEntity.setOperatorCode("sdfds");
		sortingScanEntity.setOperatorName("sdfdsf");
		sortingScanEntity.setOrgCode("sdfds");
		sortingScanEntity.setOrgName("sdfds");
		sortingScanEntity.setScanTime(new Date());
		sortingScanEntity.setScanType("sdfs");
		sortingScanEntity.setSerialNo("sdfds");
		sortingScanEntity.setWayBillNo("sdfsd");
		pdaSortingDao.insertSortingScan(sortingScanEntity);
		
	}
	@Test
	public void select() {
		SortingScanVo vo = new SortingScanVo();
		vo.setOperatorCode("sdfs");
		vo.setOrgCode("sdfds");
		vo.setQueryTimeBegin(new Date());
		vo.setQueryTimeEnd(new Date());
		vo.setScanType("sdfs");
		vo.setSerialNo("sdfsd");
		vo.setWayBillNo("rtr");
		pdaSortingDao.selectSortingScan(vo, 20, 1);
		
	}

}
