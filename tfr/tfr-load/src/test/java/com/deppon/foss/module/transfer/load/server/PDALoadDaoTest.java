/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: PDALoadDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.deppon.foss.module.transfer.load.api.server.dao.IAssignLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDALoadDao;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.PDATaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadDestOrgEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDALoadDaoTest
 * @author dp-duyi
 * @date 2012-12-14 下午1:44:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDALoadDaoTest {
	final Logger logger = LoggerFactory.getLogger(PDALoadDaoTest.class);
	@Autowired
	IPDALoadDao pdaLoadDao;
	@Autowired
	IAssignLoadTaskDao assignLoadTaskDao;
	@BeforeTransaction
	public void beforeTransaction() {

	}
	@Test
	public void queryLoadedGoods(){
		Map<String,String> queryCondition = new HashMap<String,String>();
		queryCondition.put("taskId", "f0af0aab-c2e5-4e45-9125-241e078a2d0c");
		queryCondition.put("beLoaded", FossConstants.YES);
		pdaLoadDao.queryLoadedGoods(queryCondition);
	}
	@Test
	public void stockQtyIsBiggerThanLoadQty(){
		int i = pdaLoadDao.stockQtyIsBiggerThanLoadQty("71c92008-9cd3-4c86-a111-30c27397edb8", "P10003374", "000007525");
		System.out.println(i);
	}
	@Test
	public void updateDeliverBillState(){
		DeliverBillEntity bill = new DeliverBillEntity();
		bill.setBillNo("sdfds");
		bill.setState("sdfdsf");
		assignLoadTaskDao.updateDeliverBillState(bill, "test");
		assignLoadTaskDao.updateDeliverBillState(bill, null);
	}
	@Test
	public void queryLoadDestOrgsTest(){
		List<LoadDestOrgEntity> o = pdaLoadDao.queryLoadDestOrgs("1e8522b4-91e0-47fb-96b9-c368158a7844");
		logger.info("df",o);
	}
	@Test
	 public void queryScanedUnloadSerialNosTest(){
		List<String> scanUnloadState = new ArrayList<String>();
		scanUnloadState.add(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
		scanUnloadState.add(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);
		pdaLoadDao.queryScanedUnloadSerialNos("d1bc74ee-f608-452d-b33a-25c5c1f8123b");
	}
	@Test
	 public void queryStockUnloadSerialNosTest(){
		pdaLoadDao.queryStockUnloadSerialNos("156068590", "W3100020616", "d1bc74ee-f608-452d-b33a-25c5c1f8123b", 2);
	}
	@Test
	 public void insertTransferLoadTaskTest(){
		LoadTaskEntity loadTask = new LoadTaskEntity();
		loadTask.setId("11111dudududu");
		loadTask.setCreateDate(new Date());
		loadTask.setCreateUser("dudu");
		loadTask.setDeliverBillNo("12345");
		loadTask.setDestOrgNames("fdfsfs");
		loadTask.setGoodsType("ALL");
		loadTask.setLine("fdsf");
		loadTask.setLoaderQty(5);
		loadTask.setLoadStartTime("2012-11-11 11:11:11");
		pdaLoadDao.insertTransferLoadTask(loadTask);
	}
	
	 public void insertTransferLoadDestOrgTest(){
		List<LoadDestOrgEntity> loadDestOrgs = new ArrayList<LoadDestOrgEntity>();
		LoadDestOrgEntity loadDestOrg = new LoadDestOrgEntity();
		loadDestOrg.setBeCreateHandOver("N");
		loadDestOrg.setDestOrgCode("123");
		loadDestOrg.setDestOrgName("dudu");
		loadDestOrg.setId("dudu1235");
		loadDestOrg.setTruckDepartPlanDetailId("12");
		loadDestOrgs.add(loadDestOrg);
		loadDestOrg = new LoadDestOrgEntity();
		loadDestOrg.setBeCreateHandOver("N");
		loadDestOrg.setDestOrgCode("123");
		loadDestOrg.setDestOrgName("dudu");
		loadDestOrg.setId("dudu12355");
		//loadDestOrg.setTruckDepartPlanDetailId("12");
		loadDestOrgs.add(loadDestOrg);
		pdaLoadDao.insertTransferLoadDestOrg(loadDestOrgs);
	}
	
	 public void insertTransferLoaderParticipationTest(){
		List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
		LoaderParticipationEntity loader = new LoaderParticipationEntity();
		loader.setBeCreator("Y");
		loader.setId("dudududud12432");
		loader.setJoinTime(new Date());
		//loader.setLeaveTime(leaveTime);
		loader.setLoaderCode("q2");
		loader.setLoaderName("dfsd");
		loader.setLoadOrgCode("sfdsf");
		loader.setLoadOrgName("were");
		loader.setTaskId("45t5ygtyhjhy");
		loader.setTaskType("fgdgfhyg");
		loaders.add(loader);
		loader = new LoaderParticipationEntity();
		loader.setBeCreator("N");
		loader.setId("dudududud12432s");
		loader.setJoinTime(new Date());
		//loader.setLeaveTime(leaveTime);
		loader.setLoaderCode("q2");
		loader.setLoaderName("dfsd");
		loader.setLoadOrgCode("sfdsf");
		loader.setLoadOrgName("were");
		loader.setTaskId("45t5ygtyhjhy");
		loader.setTaskType("fgdgfhyg");
		loaders.add(loader);
		pdaLoadDao.insertTransferLoaderParticipation(loaders);
	}
	@Test
	 public void insertLoadWayBillDetailEntityTest(){
		LoadWaybillDetailEntity wayBillDetail = new LoadWaybillDetailEntity();
		wayBillDetail.setBeJoinCar("Y");
		wayBillDetail.setGoodsName("fds");
		wayBillDetail.setId("fdsfs");
		wayBillDetail.setLoadQty(3);
		wayBillDetail.setLoadTaskId("fdsfds");
		wayBillDetail.setLoadVolumeTotal(123);
		wayBillDetail.setLoadWeightTotal(123);
		wayBillDetail.setNotes("sfdsfdsf");
		wayBillDetail.setWaybillNo("fdsfsdf");
		wayBillDetail.setTransportType("sfdsf");
		wayBillDetail.setTaskBeginTime(new Date());
		wayBillDetail.setStockQty(2);
		wayBillDetail.setScanQty(3);
		wayBillDetail.setReceiveOrgName("dfdsf");
		wayBillDetail.setReachOrgName("fdfgg");
		wayBillDetail.setPack("fdsfds");
		wayBillDetail.setOrigOrgCode("fdsgrg");
		pdaLoadDao.insertLoadWayBillDetailEntity(wayBillDetail);
	}
	@Test
	 public void insertLoadSerialNoEntityTest(){
		LoadSerialNoEntity serialNo = new LoadSerialNoEntity();
		serialNo.setId("234324dududu");
		serialNo.setBeLoaded("Y");
		serialNo.setCreateTime(new Date());
		serialNo.setDeviceNo("fdsfsdf");
		serialNo.setGoodsState("fdsfdsf");
		serialNo.setLoadTime(new Date());
		serialNo.setLoadWaybillDetailId("fdsfsd");
		serialNo.setOrigOrgCode("dfsd");
		serialNo.setScanState("fdsf");
		serialNo.setSerialNo("fdsf");
		serialNo.setTaskBeginTime(new Date());
		pdaLoadDao.insertLoadSerialNoEntity(serialNo);
	}
	@Test
	 public void insertPDATaskTest(){
		PDATaskEntity pda = new PDATaskEntity();
		pda.setBeCreator("Y");
		pda.setDeviceNo("23432");
		pda.setId("fdsfds");
		pda.setJoinTime(new Date());
		pda.setLeaveTime(new Date());
		pda.setTaskNo("fdsfsd");
		pda.setTaskType("fdsfds");
		pdaLoadDao.insertPDATask(pda);
	}
	@Test
	 public void updateLoadTaskTest(){
		LoadTaskEntity loadTask = new LoadTaskEntity();
		loadTask.setId("11111dudududu");
		loadTask.setCreateDate(new Date());
		loadTask.setCreateUser("dudu");
		loadTask.setDeliverBillNo("12345");
		loadTask.setDestOrgNames("fdfsfs");
		loadTask.setGoodsType("ALL");
		loadTask.setLine("fdsf");
		loadTask.setLoaderQty(5);
		loadTask.setLoadStartTime("2012-11-11 11:11:11");
		loadTask.setTaskNo("12432");
		loadTask.setLoadEndTime("2012-11-11 11:11:11");
		loadTask.setState("fdsfds");
		pdaLoadDao.updateLoadTask(loadTask);
		loadTask.setDeliverBillNo("12345678");
		pdaLoadDao.updateLoadTask(loadTask);
	}
	@Test
	 public void updateLoaderParticipationByLoadTaskTest(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", "fdsf");
		param.put("endTime", new Date());
		int count = pdaLoadDao.updateLoaderParticipationByLoadTask(param);
		System.out.println(count);
		param = new HashMap<String,Object>();
		param.put("id", "edb338cc-b0e3-45f4-8218-2f7f5c3bcdf0");
		param.put("endTime", new Date());
		count = pdaLoadDao.updateLoaderParticipationByLoadTask(param);
		System.out.println(count);
	}
	
	 public void updateLoaderParticipationByLoaderTest(){
		List<LoaderParticipationEntity> loaders = new ArrayList<LoaderParticipationEntity>();
		LoaderParticipationEntity loader = new LoaderParticipationEntity();
		loader.setBeCreator("Y");
		loader.setId("dudududud12432");
		loader.setJoinTime(new Date());
		loader.setLeaveTime(new Date());
		loader.setLoaderCode("q2");
		loader.setLoaderName("dfsd");
		loader.setLoadOrgCode("sfdsf");
		loader.setLoadOrgName("were");
		loader.setTaskId("45t5ygtyhjhy");
		loader.setTaskType("fgdgfhyg");
		loaders.add(loader);
		loader = new LoaderParticipationEntity();
		loader.setBeCreator("N");
		loader.setId("dudududud12432s");
		loader.setJoinTime(new Date());
		loader.setLeaveTime(new Date());
		loader.setLoaderCode("q2");
		loader.setLoaderName("dfsd");
		loader.setLoadOrgCode("sfdsf");
		loader.setLoadOrgName("were");
		loader.setTaskId("45t5ygtyhjhy");
		loader.setTaskType("fgdgfhyg");
		loaders.add(loader);
		pdaLoadDao.updateLoaderParticipationByLoader(loaders);
	}
	@Test
	 public void updateLoadTaskWayBillDetailTest(){
		LoadWaybillDetailEntity wayBillDetail = new LoadWaybillDetailEntity();
		wayBillDetail.setBeJoinCar("Y");
		wayBillDetail.setGoodsName("fds");
		wayBillDetail.setId("fdsfs");
		wayBillDetail.setLoadQty(3);
		wayBillDetail.setLoadTaskId("fdsfds");
		wayBillDetail.setLoadVolumeTotal(123);
		wayBillDetail.setLoadWeightTotal(123);
		wayBillDetail.setNotes("sfdsfdsf");
		wayBillDetail.setWaybillNo("fdsfsdf");
		wayBillDetail.setTransportType("sfdsf");
		wayBillDetail.setTaskBeginTime(new Date());
		wayBillDetail.setStockQty(2);
		wayBillDetail.setScanQty(3);
		wayBillDetail.setReceiveOrgName("dfdsf");
		wayBillDetail.setReachOrgName("fdfgg");
		wayBillDetail.setPack("fdsfds");
		wayBillDetail.setOrigOrgCode("fdsgrg");
		wayBillDetail.setNotes("dsgfdg");
		int count = pdaLoadDao.updateLoadTaskWayBillDetail(wayBillDetail);
		System.out.println(count);
		wayBillDetail = new LoadWaybillDetailEntity();
		wayBillDetail.setBeJoinCar("Y");
		wayBillDetail.setGoodsName("fds");
		wayBillDetail.setId("dca5cb88-6bed-470b-b902-2366c6355a9a");
		wayBillDetail.setLoadQty(3);
		wayBillDetail.setLoadTaskId("fdsfds");
		wayBillDetail.setLoadVolumeTotal(123);
		wayBillDetail.setLoadWeightTotal(123);
		wayBillDetail.setNotes("sfdsfdsf");
		wayBillDetail.setWaybillNo("fdsfsdf");
		wayBillDetail.setTransportType("sfdsf");
		wayBillDetail.setTaskBeginTime(new Date());
		wayBillDetail.setStockQty(2);
		wayBillDetail.setScanQty(3);
		wayBillDetail.setReceiveOrgName("dfdsf");
		wayBillDetail.setReachOrgName("fdfgg");
		wayBillDetail.setPack("fdsfds");
		wayBillDetail.setOrigOrgCode("fdsgrg");
		wayBillDetail.setNotes("dsgfdg");
		count = pdaLoadDao.updateLoadTaskWayBillDetail(wayBillDetail);
		System.out.println(count);
	}
	@Test
	 public void updateLoadTaskSerialNoTest(){
		LoadSerialNoEntity serialNo = new LoadSerialNoEntity();
		serialNo.setId("234324");
		serialNo.setBeLoaded("Y");
		serialNo.setCreateTime(new Date());
		serialNo.setDeviceNo("fdsfsdf");
		serialNo.setGoodsState("fdsfdsf");
		serialNo.setLoadTime(new Date());
		serialNo.setLoadWaybillDetailId("fdsfsd");
		serialNo.setOrigOrgCode("dfsd");
		serialNo.setScanState("fdsf");
		serialNo.setSerialNo("fdsf");
		serialNo.setTaskBeginTime(new Date());
		pdaLoadDao.updateLoadTaskSerialNo(serialNo);
	}
	@Test
	 public void updatePDATaskEntityTest(){
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("taskNo", "052013040700008");
		parameter.put("deviceNo", "7800000602");
		parameter.put("leaveTime", new Date());
		pdaLoadDao.updatePDATaskEntity(parameter);
	}
	@Test
	 public void deleteLoadWaybillDetailEntityTest(){
		pdaLoadDao.deleteLoadWaybillDetailEntity("234324");
	}
	@Test
	 public void deleteLoadSerialNoEntityTest(){
		pdaLoadDao.deleteLoadSerialNoEntity("fds");
	}
	@Test
	 public void queryPDATaskByTaskNoTest(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskNo", "243242");
		condition.put("beCreator", "Y");
		pdaLoadDao.queryPDATaskByTaskNo(condition);
	}
	@Test
	 public void queryUnSubmitPDAQtyTest(){
		pdaLoadDao.queryUnSubmitPDAQty("fdsfs");
	}
	@Test
	 public void queryScanSerialNoQTYByTaskIdTest(){
		pdaLoadDao.queryScanSerialNoQTYByTaskId("rewr");
	}
	@Test
	 public void queryDeliverLoadLackWayBillTest(){
		Map<String,String> lackWayBillQC = new HashMap<String,String>();
		lackWayBillQC.put("taskNo", "fdsfds");
		pdaLoadDao.queryDeliverLoadLackWayBill(lackWayBillQC);
	}
	@Test
	 public void queryDeliverLoadLackSerialTest(){
		Map<String,Object> lackSerialQC = new HashMap<String,Object>();
		lackSerialQC.put("loadWayBillDetailId", "67499ff6-e044-4213-9c7d-6f6965007ddd");
		lackSerialQC.put("wayBillNo", "fdsfdsfs");
		lackSerialQC.put("rowNum", 1);
		lackSerialQC.put("goodsType", "CANCELED");
		lackSerialQC.put("goodsType1", "NOT_LOADING");
		lackSerialQC.put("origOrgCode", "fdsfdsfs");
		pdaLoadDao.queryDeliverLoadLackSerial(lackSerialQC);
	}
	@Test
	 public void queryGoodsByGoodsStatesTest(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("taskId", "fdsfs");
		List<String> goodsStates = new ArrayList<String>();
		//TODO 查询多货(查多货还是查未预配？纠结！)
		goodsStates.add(LoadConstants.LOAD_GOODS_STATE_MORE);
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_EXTRA_UNPREWIRED);
		condition.put("goodsStates", goodsStates);
		pdaLoadDao.queryGoodsByGoodsStates(condition);
	}
	@Test
	 public void queryLoadDestOrgCodesByIdTest(){
		pdaLoadDao.queryLoadDestOrgCodesById("fdsfs");
	}
	@Test
	 public void queryValidLabeledCountTest(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", "fdsfs");
		condition.put("serialNo", "fdsfs");
		condition.put("active", "Y");
		pdaLoadDao.queryValidLabeledCount(condition);
	}
	@Test
	 public void queryWayBillSignedStateTest(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("wayBillNo", "fdsfs");
		condition.put("serialNo", "fdsfs");
		condition.put("active", "Y");
		pdaLoadDao.queryWayBillSignedState(condition);
	}
	@Test
	 public void queryOutStockGoodsTest(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskId", "fdsfs");
		condition.put("beLoaded", "Y");
		pdaLoadDao.queryOutStockGoods(condition);
	}
	@Test
	 public void queryLoadOaReportMoreGoodsTest(){
		pdaLoadDao.queryLoadOaReportMoreGoods("fdsfs",new Date());
		pdaLoadDao.queryLoadOaReportMoreGoods("fdsfs",null);
	}
//	@Test
//	 public void refrushNormalTransferLoadTaskDetailTest(){
//		pdaLoadDao.refrushNormalTransferLoadTaskDetail("012013040700013",null);
//		List<String> l = new ArrayList<String>();
//		l.add("PX0405WD");
//		l.add("PX0405WD");
//		pdaLoadDao.refrushNormalTransferLoadTaskDetail("012013052300013",l);
//	}
//	@Test
//	 public void refrushTogetherTransferLoadTaskDetailTest(){
//		pdaLoadDao.refrushTogetherTransferLoadTaskDetail("012013040700013",null);
//		List<String> l = new ArrayList<String>();
//		l.add("PX0405WD");
//		l.add("PX0405WD");
//		pdaLoadDao.refrushTogetherTransferLoadTaskDetail("012013052300013",l);
//	}
	@Test
	public void refrushMoreGoodsLoadTaskDetailTest(){
		pdaLoadDao.refrushMoreGoodsLoadTaskDetail("012013031400008", null);
	}
	@Test
	 public void queryTogetherDestOrgCodesTest(){
		Map<String,String> queryTogetherDestQC = new HashMap<String,String>();
		queryTogetherDestQC.put("wayBillNo","fdsfsdf");
		queryTogetherDestQC.put("serialNo", "fdsfsdf");
		queryTogetherDestQC.put("orgCode", "fdsfsdf");
		pdaLoadDao.queryTogetherDestOrgCodes(queryTogetherDestQC);
	}
	@Test
	 public void queryLoadTaskByTaskNoTest(){
		pdaLoadDao.queryLoadTaskByTaskNo("fdsfs");
	}
	@Test
	 public void queryLoadWaybillDetailEntityByWayBillNoTest(){
		LoadWaybillDetailEntity loadTaskWayBillTemp = new LoadWaybillDetailEntity();
		loadTaskWayBillTemp.setLoadTaskId("fdsfds");
		loadTaskWayBillTemp.setWaybillNo("fdsfs");
		pdaLoadDao.queryLoadWaybillDetailEntityByWayBillNo(loadTaskWayBillTemp);
	}
	@Test
	 public void queryLoadSerialNoEntityBySerialNoTest(){
		LoadSerialNoEntity loadSerialNoEntityTemp = new LoadSerialNoEntity();
		loadSerialNoEntityTemp.setLoadWaybillDetailId("fdsfs");
		loadSerialNoEntityTemp.setSerialNo("fdsfs");
		pdaLoadDao.queryLoadSerialNoEntityBySerialNo(loadSerialNoEntityTemp);
	}
	@Test
	 public void queryLoadTaskSerialNoDtoByConditionTest(){
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("taskNo", "fdsfs");
		condition.put("taskState", "fdsfs");
		condition.put("wayBillNo", "fdsfs");
		condition.put("serialNo", "fdsfs");
		condition.put("goodsState", "fdsfs");
		pdaLoadDao.queryLoadTaskSerialNoDtoByCondition(condition);
	}
	@Test
	public void queryAssignedLoadTaskTest(){
		QueryAssignedLoadTaskEntity condition = new QueryAssignedLoadTaskEntity();
		condition.setLoaderCode("2");
		condition.setLoaderOrgCode("123");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			condition.setQueryTimeBegin(format.parse("2012-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		condition.setQueryTimeEnd(new Date());
		pdaLoadDao.queryAssignedLoadTask(condition);
	}
	@Test
	public void queryUnfinishedLoadTaskTest(){
		QueryAssignedLoadTaskEntity condition = new QueryAssignedLoadTaskEntity();
		condition.setLoaderCode("001321");
		condition.setLoaderOrgCode("123");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			condition.setQueryTimeBegin(format.parse("2012-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		condition.setQueryTimeEnd(new Date());
		pdaLoadDao.queryUnfinishedLoadTask(condition);
	}
	@Test
	public void refrushNormalDeliverLoadTaskDetailTest(){
		pdaLoadDao.refrushNormalDeliverLoadTaskDetail("123");
	}
	@Test
	public void queryLoaderParticipationTest(){
		//pdaLoadDao.queryLoaderParticipation("123","sfdsf");
	}
	@Test
	public void queryWayBillStockQty(){
		pdaLoadDao.queryWayBillStockQty("114857653","W01140402");
		pdaLoadDao.queryWayBillStockQty("aaa","aaa");
	}
	@Test
	public void queryDeliverArrangeQty(){
		pdaLoadDao.queryDeliverArrangeQty("P10002942","106542916");
		pdaLoadDao.queryDeliverArrangeQty("aaa","aaa");
	}
	@Test
	public void queryHandOverBillNoByTaskNo(){
		List<String> s = pdaLoadDao.queryHandOverBillNoByTaskNo("012013040700022");
		pdaLoadDao.queryHandOverBillNoByTaskNo("0120130407000");
		System.out.println(s);
	}
	@Test
	public void queryLoadTaskByTaskNoForUpdate(){
		pdaLoadDao.queryLoadTaskByTaskNoForUpdate("0120130407000");
	}
}
