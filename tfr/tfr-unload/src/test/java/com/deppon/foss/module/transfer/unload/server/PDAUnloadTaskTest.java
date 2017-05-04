/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service
 * FILE    NAME: UnloadTaskServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPDAUnloadTaskDao;

import com.deppon.foss.module.transfer.unload.api.shared.define.ChangeLabelGoodsConstants;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.AssignUnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ChangeLabelGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadGoodsSerialDetailDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 卸车任务测试
 * @author dp-duyi
 * @date 2012-11-26 下午3:34:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/unload/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class PDAUnloadTaskTest {
	final Logger logger = LoggerFactory.getLogger(PDAUnloadTaskTest.class);

	@Autowired
	IPDAUnloadTaskService pdaUnloadTaskService;
	@Autowired
	IPDAUnloadTaskDao pdaUnloadTaskDao;
	@BeforeTransaction
	public void beforeTransaction() {

	}

	@AfterTransaction
	public void afterTransaction() {

	}
	@Test 
	public void queryPickUpBillByGoods(){
		List<String> l = new ArrayList<String>();
		l.add("10000901s");
		pdaUnloadTaskDao.queryPickUpBillByGoods(l,"155118248","0001");
	}
	@Test 
	public void queryHandOverBillByGoods(){
		List<String> l = new ArrayList<String>();
		l.add("00007910");
		pdaUnloadTaskDao.queryHandOverBillByGoods(l,"106011852","0001");
	}
	@Test 
	public void getLoaderParticipationByTaskId(){
		LoaderParticipationEntity loaderParticipation = pdaUnloadTaskDao.getLoaderParticipationByTaskId("1e8522b4-91e0-47fb-96b9-c368158a7844");
		System.err.println(loaderParticipation);
	}
	@Test 
	public void queryAssembleBillByGoods(){
		List<String> l = new ArrayList<String>();
		l.add("BJBJHD13040901");
		pdaUnloadTaskDao.queryAssembleBillByGoods(l,"106011852","0001");
	}
	@Test
	public void queryGoodsBeInPickUpBill(){
		pdaUnloadTaskDao.queryGoodsBeInPickUpBill("022013040700001", "040799991", "0001");
	}
	@Test
	public void queryUnArriveBillInVehicle(){
		List<String> l = new ArrayList<String>();
		l.add("00011368");
		List<String> ll = new ArrayList<String>();
		ll.add("W3100020616");
		pdaUnloadTaskDao.queryUnArriveBillInVehicle(ll,l);
	}
	@Test
	public void queryGoodsBeInHandOverBill(){
		List<String> l = new ArrayList<String>();
		l.add("00011368");
		pdaUnloadTaskDao.queryGoodsBeInHandOverBill(l,"20090421","0020");
	}
	@Test
	public void queryGoodsBeInAssembleBill(){
		List<String> l = new ArrayList<String>();
		l.add("CDCBCD13040701");
		pdaUnloadTaskDao.queryGoodsBeInAssembleBill(l,"115821598","0001");
	}
	@Test
	public void queryUnloadGoodsDetail(){
		pdaUnloadTaskDao.queryUnloadGoodsDetail("115821598","0001","aaa");
	}
	@Test
	public void queryUnloadMoreGoods(){
		List<UnloadGoodsSerialDetailDto> l = pdaUnloadTaskDao.queryUnloadMoreGoods("022013040900002");
		for(UnloadGoodsSerialDetailDto s : l){
			System.out.println(s.getBillNo()+" "+s.getWayBillNo()+" "+s.getSerialNo());
		}
	}
	@Test
	public void updateAssignUnloadTaskStateByState_test() {
		AssignUnloadTaskEntity assignedTask = new AssignUnloadTaskEntity();
		assignedTask.setBillNo("sdfdsf");
		assignedTask.setBeCanceled(FossConstants.NO);
		assignedTask.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_PROCESSING);
		assignedTask.setUnloadStartTime(new Date());
		pdaUnloadTaskDao.updateAssignUnloadTaskStateByState(assignedTask,"dsfdsf");
	}
	@Test
	public void querySeal_test() {
		SealEntity sealQC = new SealEntity();
		sealQC.setVehicleNo("fdss");
		sealQC.setSealState(SealConstant.SEAL_STATE_UNCHECK);
		sealQC.setSealType("q342");
		pdaUnloadTaskDao.querySeal(sealQC);
	}
	@Test
	public void queryLoaderByTaskId_test() {
		LoaderParticipationEntity loaderQC = new LoaderParticipationEntity();
		loaderQC.setTaskId("fdsfsd");
		loaderQC.setBeCreator(FossConstants.YES);
		pdaUnloadTaskDao.queryLoaderByTaskId(loaderQC);
	}
	@Test
	public void pdaQueryAssignedUnloadTask_test() {
		pdaUnloadTaskService.pdaQueryAssignedUnloadTask("1353379647497", "1353379647496");
	}
	@Test
	public void queryUnloadTaskByTaskNo_test() {
		pdaUnloadTaskDao.queryUnloadTaskByTaskNo("2343242");
	}
	@Test
	public void queryUnloadBillsByBillNo_test() {
		List<String> billNos = new ArrayList<String>();
		billNos.add("fds");
		pdaUnloadTaskDao.queryUnloadBillsByBillNo(billNos);
	}
	@Test
	public void queryUnloadBillsByTaskId_test() {
		pdaUnloadTaskDao.queryUnloadBillsByTaskId("42423");
	}
	@Test
	public void queryHandOverUnloadDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("00000743");
		billNos.add(bill);
		List<UnloadGoodsSerialDetailDto> l = pdaUnloadTaskDao.queryHandOverUnloadDetail(billNos,"bdaafa6f-055a-4866-9325-7a2e34e692de");
		logger.info(String.valueOf(l.size()), l);
	}
	@Test
	public void queryAssembleUnloadDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("SHBJ13041702");
		billNos.add(bill);
		pdaUnloadTaskDao.queryAssembleUnloadDetail(billNos,"bdaafa6f-055a-4866-9325-7a2e34e692de");
	}
	@Test
	public void queryPickUpUnloadDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("23432");
		billNos.add(bill);
		pdaUnloadTaskDao.queryPickUpUnloadDetail(billNos,"bdaafa6f-055a-4866-9325-7a2e34e692de");
	}
	@Test
	public void updateAssignUnloadTaskState_test() {
		List<AssignUnloadTaskEntity> assignedUnloadTasks = new ArrayList<AssignUnloadTaskEntity>();
		AssignUnloadTaskEntity assignedTask;
		assignedTask = new AssignUnloadTaskEntity();
		assignedTask.setBillNo("fdsfs");
		assignedTask.setBeCanceled(FossConstants.NO);
		assignedTask.setState(UnloadConstants.ASSIGN_UNLOAD_TASK_STATE_PROCESSING);
		assignedTask.setUnloadStartTime(new Date());
		assignedTask.setUnloadEndTime(new Date());
		assignedUnloadTasks.add(assignedTask);
		pdaUnloadTaskDao.updateAssignUnloadTaskState(assignedUnloadTasks);
	}
	@Test
	public void queryScanSerialNoQTYByTaskId_test() {
		pdaUnloadTaskDao.queryScanSerialNoQTYByTaskId("fdsfsdfsd");
	}
	@Test
	public void updateUnloadTask_test() {
		UnloadTaskEntity unloadTask = new UnloadTaskEntity();
		unloadTask.setId("fsdfdsf");
		unloadTask.setTaskState(UnloadConstants.UNLOAD_TASK_STATE_CANCELED);
		unloadTask.setUnloadEndTime(new Date());
		pdaUnloadTaskDao.updateUnloadTask(unloadTask);
	}
	@Test
	public void queryUnfinishUnloadedValideBillQty_test() {
		pdaUnloadTaskDao.queryUnfinishUnloadedValideBillQty("fdsfsdfsf");
	}
	@Test
	public void updateTruckTaskDetailState_test() {
		TruckTaskDetailEntity truckTaskDetail = new TruckTaskDetailEntity();
		truckTaskDetail.setId("fdsfsdfsdf");
		truckTaskDetail.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
		pdaUnloadTaskDao.updateTruckTaskDetailState(truckTaskDetail);
	}
	@Test
	public void queryUnfinishUnloadedTruckTaskDetailQty_test() {
		pdaUnloadTaskDao.queryUnfinishUnloadedTruckTaskDetailQty("fdsfsdf");
	}
	@Test
	public void updateTruckTaskState_test() {
		TruckTaskEntity truckTaskEntity = new TruckTaskEntity();
		truckTaskEntity.setId("fdsfsd");
		truckTaskEntity.setState(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED);
		pdaUnloadTaskDao.updateTruckTaskState(truckTaskEntity);
	}
	@Test
	public void insertUnloadSerialNoEntity_test() {
		UnloadSerialNoDetailEntity seialNoDetail = new UnloadSerialNoDetailEntity();
		seialNoDetail.setId(UUIDUtils.getUUID());
		//serialNoEntity.setDeviceId(deviceId);
		seialNoDetail.setDeviceNo("fdsfsdf");
		seialNoDetail.setGoodsStatus("fdsfsdf");
		seialNoDetail.setOptTime(new Date());
		seialNoDetail.setScanStatus("fsfdsf");
		seialNoDetail.setSerialNo("fsdfdsf");
		seialNoDetail.setUnloadWaybillDetailId("fsdfdsf");
		seialNoDetail.setCreateDate(new Date());
		seialNoDetail.setTaskCreateTime(new Date());
		seialNoDetail.setCreateOrgCode("dfdsfds");
		pdaUnloadTaskDao.insertUnloadSerialNoEntity(seialNoDetail);
	}
	@Test
	public void insertUnloadWayBillEntity_test() {
		UnloadWaybillDetailEntity wayBIllEntity = new UnloadWaybillDetailEntity();
		wayBIllEntity.setId(UUIDUtils.getUUID());
		wayBIllEntity.setBillNo("fdsfdsfs");
		wayBIllEntity.setDestOrgCode("fdsfdsfs");
		wayBIllEntity.setGoodsName("fdsfdsfs");
		wayBIllEntity.setOrigOrgCode("fdsfdsfs");
		wayBIllEntity.setPack("fdsfdsfs");
		
		wayBIllEntity.setTaskBeginTime(new Date());
		wayBIllEntity.setTransportType("fdsfdsfs");
		wayBIllEntity.setUnloadOrgCode("fdsfdsfs");
		wayBIllEntity.setUnloadTaskId("fdsfdsfs");
		wayBIllEntity.setWaybillNo("fdsfdsfs");
		wayBIllEntity.setOperationGoodsQty(0);
		wayBIllEntity.setScanGoodsQty(0);
		wayBIllEntity.setUnloadVolumeTotal(BigDecimal.ZERO);
		wayBIllEntity.setUnloadWeightTotal(BigDecimal.ZERO);
		pdaUnloadTaskDao.insertUnloadWayBillEntity(wayBIllEntity);
	}
	@Test
	public void updateUnloadWayBillEntity_test() {
		UnloadWaybillDetailEntity wayBIllEntity = new UnloadWaybillDetailEntity();
		wayBIllEntity.setId("2de2e419-2054-4a7c-96ec-beccfcd4983c");
		wayBIllEntity.setOperationGoodsQty(0);
		wayBIllEntity.setScanGoodsQty(0);
		wayBIllEntity.setHandOverPieces(1);
		wayBIllEntity.setWeight(BigDecimal.ZERO);
		wayBIllEntity.setVolume(BigDecimal.ZERO);
		wayBIllEntity.setUnloadVolumeTotal(BigDecimal.valueOf(0.0));
		wayBIllEntity.setUnloadWeightTotal(BigDecimal.valueOf(0.0));
		pdaUnloadTaskDao.updateUnloadWayBillEntity(wayBIllEntity);
		/*wayBIllEntity = new UnloadWaybillDetailEntity();
		wayBIllEntity.setId("2a220659-2627-406a-8983-f3a7654470e5");
		wayBIllEntity.setUnloadVolumeTotal(BigDecimal.valueOf(-1.1));
		wayBIllEntity.setUnloadWeightTotal(BigDecimal.valueOf(-1.1));
		wayBIllEntity.setOperationGoodsQty(-1);
		wayBIllEntity.setScanGoodsQty(-1);
		wayBIllEntity.setHandOverPieces(0);
		pdaUnloadTaskDao.updateUnloadWayBillEntity(wayBIllEntity);
		wayBIllEntity = new UnloadWaybillDetailEntity();
		wayBIllEntity.setId("2a220659-2627-406a-8983-f3a7654470e5");
		wayBIllEntity.setUnloadVolumeTotal(BigDecimal.valueOf(1.1));
		wayBIllEntity.setUnloadWeightTotal(BigDecimal.valueOf(1.1));
		wayBIllEntity.setOperationGoodsQty(1);
		wayBIllEntity.setScanGoodsQty(1);
		wayBIllEntity.setHandOverPieces(0);
		pdaUnloadTaskDao.updateUnloadWayBillEntity(wayBIllEntity);*/
	}
	@Test
	public void updateUnloadSerialNoEntity_test() {
		UnloadSerialNoDetailEntity seialNoDetail = new UnloadSerialNoDetailEntity();
		seialNoDetail.setId("fdsfdsf");
		seialNoDetail.setGoodsStatus("fdsfdsf");
		seialNoDetail.setOptTime(new Date());
		seialNoDetail.setScanStatus("fdsfdsf");
		pdaUnloadTaskDao.updateUnloadSerialNoEntity(seialNoDetail);
	}
	@Test
	public void queryUnloadWayBillDetail_test() {
		UnloadWaybillDetailEntity wayBillEntityQC = new UnloadWaybillDetailEntity();
		wayBillEntityQC.setBillNo("fdsfsdfs");
		wayBillEntityQC.setWaybillNo("fdsfdsfds");
		pdaUnloadTaskDao.queryUnloadWayBillDetail(wayBillEntityQC);
	}
	@Test
	public void queryUnloadSerialNoEntity_test() {
		UnloadSerialNoDetailEntity serialNoEntityQC = new UnloadSerialNoDetailEntity();
		serialNoEntityQC.setSerialNo("fdsfds");
		serialNoEntityQC.setUnloadWaybillDetailId("fsdfdsfs");
		pdaUnloadTaskDao.queryUnloadSerialNoEntity(serialNoEntityQC);
	}
	@Test
	public void queryHandOverUnloadSerialDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("23432");
		billNos.add(bill);
		pdaUnloadTaskDao.queryHandOverUnloadSerialDetail(billNos);
	}
	@Test
	public void queryAssembleUnloadSerialDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("23432");
		billNos.add(bill);
		pdaUnloadTaskDao.queryAssembleUnloadSerialDetail(billNos);
	}
	@Test
	public void queryPickUpUnloadSerialDetail_test() {
		List<UnloadBillDetailDto> billNos = new ArrayList<UnloadBillDetailDto>();
		UnloadBillDetailDto bill = new UnloadBillDetailDto();
		bill.setBillNo("23432");
		billNos.add(bill);
		pdaUnloadTaskDao.queryPickUpUnloadSerialDetail(billNos);
	}
	@Test
	public void queryUnloadScanSerialDetailByTaskId_test() {
		pdaUnloadTaskDao.queryUnloadScanSerialDetailByTaskId("fdsfds");
	}
	@Test
	public void insertChangeLabelGoodsEntity() {
		ChangeLabelGoodsEntity changeLabelGoods = new ChangeLabelGoodsEntity();
		changeLabelGoods.setId(UUIDUtils.getUUID());
		changeLabelGoods.setBillNo("fdsfs");
		changeLabelGoods.setWaybillNo("fdsfs");
		changeLabelGoods.setSerialNo("fdsfs");
		changeLabelGoods.setChangeReason("fdsfs");
		changeLabelGoods.setDiscoverTache(ChangeLabelGoodsConstants.FIND_SCENE_UNLOAD);
		changeLabelGoods.setDiscoverTime(new Date());
		changeLabelGoods.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_UNTREATED);
		changeLabelGoods.setOrgCode("fdsfs");
		changeLabelGoods.setOrgName("fdsfs");
		pdaUnloadTaskDao.insertChangeLabelGoodsEntity(changeLabelGoods);
	}
	@Test
	public void deleteChangeLabelGoodsEntity_test() {
		ChangeLabelGoodsEntity changeLabelGoods = new ChangeLabelGoodsEntity();
		changeLabelGoods.setOrgCode("fdsfdsf");
		changeLabelGoods.setDiscoverTache(ChangeLabelGoodsConstants.FIND_SCENE_UNLOAD);
		changeLabelGoods.setHandleStatus(ChangeLabelGoodsConstants.HANDLESTATUS_UNTREATED);
		changeLabelGoods.setBillNo("fdsfds");
		changeLabelGoods.setWaybillNo("fdsfsdf");
		changeLabelGoods.setSerialNo("fsfdsfds");
		pdaUnloadTaskDao.deleteChangeLabelGoodsEntity(changeLabelGoods);
	}
}
