/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server
 * FILE    NAME: AssignUnloadTaskDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.unload.api.server.dao.IAssignUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ArriveBillDto;

/**
 * @author dp-duyi
 * @date 2013-4-20 上午10:32:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class AssignUnloadTaskDaoTest {
	final Logger logger = LoggerFactory.getLogger(AssignUnloadTaskDaoTest.class);
	@Autowired
	IAssignUnloadTaskDao assignUnloadTaskDao;
	@Test
	public void updateArriveTransferBillState_test() {
		List<ArriveBillDto> bills = new ArrayList<ArriveBillDto>();
		ArriveBillDto bill = new ArriveBillDto();
		bill.setBillNo("00005842");
		bill.setAssignState("test");
		bills.add(bill);
		bill = new ArriveBillDto();
		bill.setBillNo("00005862");
		bill.setAssignState("test");
		bills.add(bill);
		bill = new ArriveBillDto();
		bill.setBillNo("00005882");
		bill.setAssignState("test");
		bills.add(bill);
		int c = assignUnloadTaskDao.updateArriveTransferBillState(bills);
		//logger.info(String.valueOf(c));
		System.out.print(c);
	}
}
