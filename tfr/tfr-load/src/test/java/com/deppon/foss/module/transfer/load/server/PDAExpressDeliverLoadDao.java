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

import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-7-25 下午2:58:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDAExpressDeliverLoadDao {
	@Autowired
	IPDAExpressDeliverLoadDao pdaExpressDeliverLoadDao;
	@Test
	public void queryUnfinishedLoadTask(){
		pdaExpressDeliverLoadDao.queryUnfinishedLoadTask("123", "123", new Date(), new Date());
	}
	@Test
	public void queryLoader(){
		pdaExpressDeliverLoadDao.queryLoader("50f3907f-39b8-4d8b-82b2-76078f3d9450");
	}
	@Test
	public void queryLoadWayBillQty(){
		pdaExpressDeliverLoadDao.queryLoadWayBillQty("50f3907f-39b8-4d8b-82b2-76078f3d9450");
	}
	@Test
	public void updateLoadWayBillQTY(){
		LoadWaybillDetailEntity loadWayBill = new LoadWaybillDetailEntity();
		loadWayBill.setId("8ff3075d-e2b1-4418-bcc2-0b8b18d33f4b");
		loadWayBill.setStockQty(0);
		loadWayBill.setLoadVolumeTotal(-1);
		loadWayBill.setLoadWeightTotal(-1);
		loadWayBill.setLoadQty(-1);
		loadWayBill.setScanQty(-1);
		pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, "0001", new Date());
		loadWayBill.setStockQty(0);
		loadWayBill.setLoadVolumeTotal(1);
		loadWayBill.setLoadWeightTotal(1);
		loadWayBill.setLoadQty(1);
		loadWayBill.setScanQty(1);
		pdaExpressDeliverLoadDao.updateLoadWayBillQTYByScanTime(loadWayBill, "0001", new Date());
	}
	@Test
	public void updateLoadSerialNoByLoadTime(){
		LoadSerialNoEntity loadSerialNo = new LoadSerialNoEntity();
		loadSerialNo.setId("1234");
		loadSerialNo.setBeLoaded(FossConstants.YES);
		loadSerialNo.setLoadTime(new Date());
		loadSerialNo.setGoodsState("sdfds");
		loadSerialNo.setScanState("sdfds");
		loadSerialNo.setDeviceNo("sdfds");
		pdaExpressDeliverLoadDao.updateLoadSerialNoByLoadTime(loadSerialNo);
	}
}
