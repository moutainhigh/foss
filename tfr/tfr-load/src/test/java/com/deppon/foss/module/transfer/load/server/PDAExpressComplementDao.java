/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: PDAExpressDeliverLoadDao.java
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

import com.deppon.foss.module.transfer.load.api.server.dao.IPDAComlementDao;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-7-25 下午2:58:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDAExpressComplementDao {
	@Autowired
	IPDAComlementDao pdaComlementDao;
	@Test
	public void test(){
		pdaComlementDao.queryComplement(new Date(), null, true,"W011302020409");
		pdaComlementDao.queryComplement(new Date(), null, false,"W011302020409");
		pdaComlementDao.queryComplement(new Date(), "sdfsd", false,"W011302020409");
	}
}
