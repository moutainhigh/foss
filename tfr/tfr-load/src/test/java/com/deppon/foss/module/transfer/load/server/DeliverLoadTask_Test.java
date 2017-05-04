/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: DeliverLoadTask_Test.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2012-12-27 下午12:55:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class DeliverLoadTask_Test {
	final Logger logger = LoggerFactory.getLogger(DeliverLoadTask_Test.class);
	@Autowired
	IDeliverLoadTaskService deliverLoadTaskService;
	@Autowired
	IDeliverLoadTaskDao deliverLoadTaskDao;
	@Autowired
	IAssignLoadTaskDao assignLoadTaskDao;
	@BeforeTransaction
	public void beforeTransaction() {
	}
	@Test
	public void queryLastLoadSerialNosDao_test(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("deliverNo", "fdsf");
		condition.put("wayBillNo", "fdsfds");
		deliverLoadTaskDao.queryLastLoadSerialNos(condition);
	}
	@Test
	public void queryLastLoadSerialNosService_test(){
		List<String> serials = deliverLoadTaskService.queryLastLoadSerialNos("P1354104250078", "2012117");
		for(String str : serials)
		logger.info(str);
	}
	@Test
	public void test(){
		assignLoadTaskDao.queryAssignState("dfdsfd", "dfdsfs");
	}
}
