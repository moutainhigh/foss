/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: DeliverLoadGapReprotDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO（描述类的职责）
 * @author dp-duyi
 * @date 2013-6-24 上午9:54:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class DeliverLoadGapReprotDaoTest {
	@Autowired
	IDeliverLoadGapReportDao deliverLoadGapReprotDao;
	@Test
	public void queryDeliverLoadGapReportWayBillsById(){
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setId("sdfds");
		deliverLoadGapReprotDao.queryDeliverLoadGapReportWayBillsById(report);
		report.setId("DA5E616A-33A8-54E5-E043-3770AA8C0ACD");
		deliverLoadGapReprotDao.queryDeliverLoadGapReportWayBillsById(report);
	}
	@Test
	public void queryDeliverLoadGapReportWayBills(){
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setDeliverBillNo("sdfds");
		deliverLoadGapReprotDao.queryDeliverLoadGapReportWayBills(report);
		report.setDeliverBillNo("P10003127");
		List<DeliverLoadGapReportWayBillEntity> l = deliverLoadGapReprotDao.queryDeliverLoadGapReportWayBills(report);
		for(DeliverLoadGapReportWayBillEntity d :l){
			System.out.println(d.getLackGoodsQty()+" "+d.getPlanLoadQty()+" "+d.getActualLoadQty()+" "+d.getWayBillNo()+" "+d.getGapType()+" "+d.getNotes()+" "+d.getTransportType());
		}
	}
	@Test
	public void queryDeliverLoadGapReport(){
		Map<String,Object> condition = new HashMap<String,Object>();
		DeliverLoadGapReportEntity report = new DeliverLoadGapReportEntity();
		report.setBeValid(FossConstants.ACTIVE);
		report.setOrigOrgCode("1234");
		condition.put("report", report);
		condition.put("queryTimeBegin", "2013-07-02 00:00:00");
		condition.put("queryTimeEnd", "2013-07-02 23:00:00");
		deliverLoadGapReprotDao.queryDeliverLoadGapReport(condition, 100, 1);
		List<String> orgCodes = new ArrayList<String>();
		orgCodes.add("sds");
		orgCodes.add("222");
		report.setOrgCodes(orgCodes);
		deliverLoadGapReprotDao.queryDeliverLoadGapReport(condition, 100, 1);
		report.setOrigOrgCode(null);
		deliverLoadGapReprotDao.queryDeliverLoadGapReport(condition, 100, 1);
	}
}
