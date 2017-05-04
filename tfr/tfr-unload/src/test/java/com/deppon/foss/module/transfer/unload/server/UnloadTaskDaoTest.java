/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service
 * FILE    NAME: UnloadTaskServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server;


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

import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao;


/**
 * 卸车任务测试
 * @author dp-duyi
 * @date 2012-11-26 下午3:34:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class UnloadTaskDaoTest {
	final Logger logger = LoggerFactory.getLogger(UnloadTaskDaoTest.class);

	@Autowired
	IUnloadTaskDao unloadTaskDao;

	@BeforeTransaction
	public void beforeTransaction() {

	}

	@AfterTransaction
	public void afterTransaction() {

	}

	@Test
	public void test() {
		unloadTaskDao.queryHandOverAndUnloadByWayBillNo("2012004");
		
	}


}
