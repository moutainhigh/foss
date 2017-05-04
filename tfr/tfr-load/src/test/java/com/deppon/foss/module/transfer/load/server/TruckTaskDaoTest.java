/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: TruckTaskTest.java
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskCallESBDao;
import com.deppon.foss.module.transfer.load.api.server.dao.ITruckTaskDao;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.HandOverBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskHandOverDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * trucktask测试
 * @author dp-duyi
 * @date 2012-12-19 下午3:51:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class TruckTaskDaoTest {
	final Logger logger = LoggerFactory.getLogger(TruckTaskDaoTest.class);
	@Autowired
	private ITruckTaskDao truckTaskDao;
	@Autowired
	private ITruckTaskCallESBDao truckTaskCallESBDao;
	@BeforeTransaction
	public void beforeTransaction() {

	}
	@Test
	public void queryBillCountByTruckTaskDetail(){
		int count = truckTaskDao.queryBillCountByTruckTaskDetail("1276a573-cc5b-44bc-8eed-68e9b3a31ff1");
		logger.info("单据数:"+count);
		System.out.println("单据数:"+count);
	}
	@Test
	public void selectBillForUpdateByTruckTaskId(){
		truckTaskDao.selectBillForUpdateByTruckTaskId("8a4dbe1b-99e9-4b6f-87c1-043a00708472");
	}
	@Test
	public void updateHandOverBillBeUpdateCRM(){
		truckTaskCallESBDao.updateHandOverBillBeUpdateCRM("12321");
	}
	@Test
	public void queryOnTheWayHandOverBillBySerialNo_test(){
		Map<String, Object> condition = new HashMap<String,Object>();
		condition.put("wayBillNo", "2343242");
		condition.put("serialNo", "234");
		List<String> states = new ArrayList<String>();
		states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART));
		states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE));
		states.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER));
		condition.put("states", states);
		truckTaskDao.queryOnTheWayHandOverBillBySerialNo(condition);
	}
	@Test
	public void queryBeCreateTruckTask_test(){
		TruckTaskHandOverDto truckTaskHandOverDto = new TruckTaskHandOverDto();
		truckTaskHandOverDto.setVehicleNo("沪UAT001");
		truckTaskHandOverDto.setOrigOrgCode("sdfdsf");
		truckTaskHandOverDto.setDestOrgCode("fdsfs");
		truckTaskHandOverDto.setTruckTaskId("fdsfsd");
		truckTaskDao.queryBeCreateTruckTask(truckTaskHandOverDto);
	}
	@Test
	public void queryBeCreateTruckTaskDetail_test(){
		TruckTaskHandOverDto truckTaskHandOverDto = new TruckTaskHandOverDto();
		truckTaskHandOverDto.setVehicleNo("fdsf");
		truckTaskHandOverDto.setOrigOrgCode("sdfdsf");
		truckTaskHandOverDto.setDestOrgCode("fdsfs");
		truckTaskHandOverDto.setTruckTaskId("fdsfsd");
		truckTaskDao.queryBeCreateTruckTaskDetail(truckTaskHandOverDto);
	}
	@Test
	public void queryUnCreateTaskTruckHandOver_test(){
		truckTaskDao.queryUnCreateTaskTruckHandOver(new Date(),new Date(),0, 1,null);
		truckTaskDao.queryUnCreateTaskTruckHandOver(null,null,0, 1,"sdfds");
	}
	@Test
	public void queryUnCreateTruckTaskAssembleBill_test(){
		truckTaskDao.queryUnCreateTruckTaskAssembleBill(new Date(),new Date(),0, 1,null);
		truckTaskDao.queryUnCreateTaskTruckHandOver(null,null,0, 1,"sdfds");
	}
	@Test
	public void queryUnUpdateTransportPathHandOverBill_test(){
		truckTaskDao.queryUnUpdateTransportPathHandOverBill(new Date(),new Date(),0, 1);
	}
	@Test
	public void insertTruckTaskBill_test(){
		TruckTaskBillEntity truckTaskBill = new TruckTaskBillEntity();
		truckTaskBill.setId(UUIDUtils.getUUID());
		truckTaskBill.setBillNo("gfdgdf");
		truckTaskBill.setAssignState(TaskTruckConstant.BILL_ASSIGN_STATE_UNASSIGN);
		truckTaskBill.setBillLevel(TaskTruckConstant.BILL_LEVEL_VALID);
		truckTaskBill.setBillType("gfdgd");
		truckTaskBill.setParentId("gfdgfdg");
		truckTaskBill.setBillingTime(new Date());
		truckTaskBill.setLoadTaskNo("fdsfsd");
		truckTaskBill.setCreateTime(new Date());
		truckTaskDao.insertTruckTaskBill(truckTaskBill);
	}
	@Test
	public void queryWayBillHandOverInfo_test(){
		truckTaskDao.queryWayBillHandOverInfo("651221123");
	}
	@Test
	public void updateHandOverBillState_test(){
		Map<String,String> handOverBill = new HashMap<String,String>();
		//交接单号
		handOverBill.put("billNo", "00000534");
		//新增车辆任务
		handOverBill.put("beCreateTruckTask", FossConstants.YES);
		//更新交接单是否生成任务车辆状态
		//若已出发，则更新交接单状态
		handOverBill.put("HANDOVERBILL_STATE", "30");
		truckTaskDao.updateHandOverBillState(handOverBill);
	}
	@Test
	public void updateAssembleBillState_test(){
		Map<String,String> assembleBillMap = new HashMap<String,String>();
		assembleBillMap.put("billNo", "SZQD13012301");
		assembleBillMap.put("beCreateTruckTask", FossConstants.YES);
		//如果车辆已经出发，则更新配载单状态为在途
		assembleBillMap.put("state", "20");
		truckTaskDao.updateAssembleBillState(assembleBillMap);
	}
	@Test
	public void updateHandOverBeUpdateTransportPath(){
		List<HandOverBillDetailDto> handOverDetails = new ArrayList<HandOverBillDetailDto>();
		HandOverBillDetailDto h = new HandOverBillDetailDto();
		h.setHandOverBillNo("00006212");
		handOverDetails.add(h);
		truckTaskDao.updateHandOverBeUpdateTransportPath("00006212");
	}
	@Test
	public void queryLastedCreateTruckTaskBill(){
		truckTaskDao.queryLastedCreateTruckTaskBill("af2033a2-a4fa-49bd-b149-d85a5a307c32",null,null);
	}
}
