/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: AutoGenerateHandOverBillDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-6-21 下午2:41:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class AutoGenerateHandOverBillDaoTest {
	@Autowired
	IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	@Test
	public void test1(){
		autoGenerateHandOverBillDao.createHandOverBill(new Date(), new Date(), 1, 0);
	}
	@Test
	public void test2(){
		/*autoGenerateHandOverBillDao.createHandOverBillByTaskNo("123");
		autoGenerateHandOverBillDao.createHandOverBillByTaskNo("012013040700022");*/
	}
}
