package com.deppon.foss.module.transfer.scheduling.dao;

/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module
 * FILE    NAME: TruckSchedulingTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.ws.policy.Assertor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IChangePathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.server.dao.impl.TruckSchedulingTaskDao;
import com.deppon.foss.util.UUIDUtils;

/**
 * 排班表单元测试
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 上午9:21:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/scheduling/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class TruckSchedulingTaskTest extends AbstractTransactionalJUnit4SpringContextTests {
	final Logger logger = LoggerFactory.getLogger(TruckSchedulingTaskTest.class);

/*	@Autowired
	TruckSchedulingTaskDao truckSchedulingTaskDao;
	@Autowired
	ITruckSchedulingDao truckSchedulingDao;
	@Autowired
	ITruckSchedulingTaskService truckSchedulingTaskService;*/
	@Autowired
	ICalculateTransportPathService calculateTransportPathService;
	@Autowired
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	@Autowired
	ITransportationPathDao transportationPathDao;
	@Autowired
	IWaybillManagerService waybillManagerService;
	
	@Autowired
	IWaybillDao waybillDao;
	@Autowired
	IFreightRouteService freightRouteService;
	
	@Autowired
	IFreightRouteLineDao freightRouteLineDao;
	@Autowired
	IChangePathDao changePathDao;
	
	@BeforeTransaction
	public void beforeTransaction() {
		// generateTruckSchedulingTask();
		// testDelteTaskAndModifySchedule();

	}

	@AfterTransaction
	public void afterTransaction() {

	}

/*	*//**
	 * 开单新增走货路径
	 * 
	 * @author huyue
	 * @date 2012-11-29 下午9:33:57
	 *//*
//	@Test
	public void createTransportPathTask() {
		TransportPathEntity transportPathEntity = new TransportPathEntity();
		transportPathEntity.setCurrentOrgCode("GS00002");
		transportPathEntity.setBillingOrgCode("GS00002");
		transportPathEntity.setDestOrgCode("W06071401");
		transportPathEntity.setTransportModel("C30001");
		transportPathEntity.setGoodsQtyTotal(7);
		transportPathEntity.setWaybillNo("N00001");
		transportPathEntity.setBillingTime(new Date());
		calculateTransportPathService.createTransportPath(transportPathEntity);
	}

	*//**
	 * 按照月份查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午2:33:53
	 *//*
	public void generateTruckSchedulingTask() {
		TruckSchedulingEntity tsEntity = new TruckSchedulingEntity();
		tsEntity.setYearMonth("2012-11");// 排班年月
		tsEntity.setSchedulingType("TFR");// 短途排班
		// tsEntity.setPlanType("1");//
		List<TruckSchedulingEntity> resList = truckSchedulingDao.queryTruckScheduling(tsEntity);
		TruckSchedulingTaskEntity entity = null;
		List<TruckSchedulingTaskEntity> genList = new ArrayList<TruckSchedulingTaskEntity>();
		int i = 0;
		for (TruckSchedulingEntity tempEntity : resList) {
			entity = new TruckSchedulingTaskEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setSchedulingId(tempEntity.getId());
			entity.setVehicleNo("沪123456" + i);
			entity.setTruckModel("7.6米");
			entity.setFrequencyNo("0" + i % 2);
			entity.setLineNo("线路" + i);
			entity.setDepartTime(new Date());
			entity.setCarOwner("徐静车队");
			entity.setTaskStatus("1");
			// entity.setZoneCode("123456");
			genList.add(entity);
			i++;
			if (genList.size() > 100) {
				truckSchedulingTaskDao.batchInsertTruckSchedulingTask(genList);
				genList = new ArrayList<TruckSchedulingTaskEntity>();
			}
		}
		if (genList.size() > 0) {
			truckSchedulingTaskDao.batchInsertTruckSchedulingTask(genList);
		}

	}

	public void testDelteTaskAndModifySchedule() {
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		simDto.setDriverOrgCode("1351497843609");
		simDto.setDriverCode("096582");
		simDto.setYmd("2012-11-01");
		simDto.setSchedulingtype("TFR");
		simDto.setSchedulingStatus("1");
		simDto.setPlanType(ScheduleConstants.PLAN_TYPE_REST);
		// truckSchedulingTaskService.delteTaskAndModifySchedule(simDto);
	}

	*//**
	 * 查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午2:46:42
	 *//*
	public void querySchedulingAndTask() {
		SimpleTruckScheduleDto entity = new SimpleTruckScheduleDto();
		entity.setSchedulingStatus("1");
		entity.setSchedulingtype("1");
		// List<SimpleTruckScheduleDto> list =
		// truckSchedulingTaskDao.querySchedulingAndTask(entity);
	}

//	@Test
	public void testQuerySchedulingForExport() {
		// 查询条件
		SimpleTruckScheduleDto simDto = new SimpleTruckScheduleDto();
		simDto.setSchedulingtype("TFR");
		simDto.setYearMonth("2012-11");
		simDto.setDriverOrgCode("1351497843609");
		simDto.setSchedulingStatus("1");

		List<String> list = new ArrayList<String>();
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		simDto.setList(list);
		truckSchedulingTaskService.querySchedulingForExport(simDto);

		truckSchedulingTaskService.exportExcelStream(simDto);
	}

//	@Test
	public void queryWaybillStatusByWaybillNoForPkp(){
		
		String waybillNo="99400007";
		List<GeneralQueryDto> generalQueryDtoList=calculateTransportPathService.queryWaybillStatusByWaybillNoForPkp(waybillNo);
		if(CollectionUtils.isNotEmpty(generalQueryDtoList)){
			for(GeneralQueryDto generalQueryDto:generalQueryDtoList){
				System.out.println("------------------------:"+generalQueryDto.getAction());
			}
		}
	}
	
	//@Test
	public void modifyTransportPathTask(){
		
		TransportPathEntity transportPathEntity=new TransportPathEntity();
		transportPathEntity.setWaybillNo("00072260");
		transportPathEntity.setTransportPathId("8f474d31-1ab8-4b7d-b717-4dc13795de8e");
		transportPathEntity.setDestOrgCode("W0113080202");
		transportPathEntity.setAction("INSTORE");
		
		List<String> serialNo=new ArrayList<String>();
		serialNo.add("1");
		String nowOrgCode="W0113080202";
		String operatorCode="w2081904217";
		String operatorName="test";
		calculateTransportPathService.modifyTransportPath(transportPathEntity, serialNo, nowOrgCode, operatorCode, operatorName);
	}
	*//**
	 * 
	* @Title: queryWaybillStatusByWaybillNoForPkpTask 
	* @Description: 
	* @return void   
	* @see queryWaybillStatusByWaybillNoForPkpTask
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 上午9:45:02   
	* @throws
	 *//*
	@Test
	public void queryWaybillStatusByWaybillNoForPkpTask(){
		calculateTransportPathService.queryWaybillStatusByWaybillNoForPkp("00072260");
	}*/
	
	/**
	 * 
	* @Title: migrateTransportPathDataTest 
	* @Description: 测试走货路径数据迁移
	* @return void    返回类型 
	* @see migrateTransportPathDataTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 上午9:45:36   
	* @throws
	 */
//	@Test
	public void migrateTransportPathDataTest(){
		calculateTransportPathService.migrateTransportPathData();
	}
	
	/**
	 * 
	* @Title: queryTransportPathMigrationTest 
	* @Description: 查询走货路径数据迁移表，查看是否已经被数据迁移
	* @return void    返回类型 
	* @see queryTransportPathMigrationTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-28 下午12:56:04   
	* @throws
	 */
	//@Test
	public void queryTransportPathMigrationTest(){
		calculateTransportPathService.queryTransportPathMigration("000070201");
	}
	
	/**
	 * 
	* @Title: queryWaybillStatusByWaybillNoForPkpTest 
	* @Description: 提供给综合查询的接口,返回运单的状态明细, group by状态 ,出发部门,到达部门,出发时间,到达时间,下一到达时间
	* @return void    返回类型 
	* @see queryWaybillStatusByWaybillNoForPkpTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-2 上午11:11:12   
	* @throws
	 */
	//@Test
	public void queryWaybillStatusByWaybillNoForPkpTest(){
		calculateTransportPathService.queryWaybillStatusByWaybillNoForPkp("201309371");
	}
	
	/**
	 * 
	* @Title: queryTransportPathTest 
	* @Description:查询走货路径
	* @return void    返回类型 
	* @see queryTransportPathTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-7 下午6:43:53   
	* @throws
	 */
	//@Test
	public void queryTransportPathTest(){
		//050311112  抛出异常有多个
		//000000723  返回正常
		TransportPathEntity transportPathEntity=transportationPathDao.queryTransportPath("000000723");
	}
	
	/**
	 * 
	* @Title: getNextOrgAndTimeForDetailResultTest 
	* @Description: TODO  设定文件 
	* @return void    返回类型 
	* @see getNextOrgAndTimeForDetailResultTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-7 下午6:57:42   
	* @throws
	 */
	//@Test
	public void getNextOrgAndTimeForDetailResultTest(){

		// waybillNo, goodsNo, correntOrgCode
		//返回走货路径无记录：77777723 ;返回一条记录 000000723(非分批配载 );  返回一条记录114824376 分批配载   ;返回多条： 050311112
		String waybillNo = "77777723";
		String goodsNo = "001";
		String correntOrgCode = "W3100020616";
		FeedbackDto feedbackDto = calculateTransportPathService.getNextOrgAndTimeForDetailResult(waybillNo, goodsNo, correntOrgCode);
	}
	
	/**
	 * 
	* @Title: createTransportPathForStorageTest 
	* @Description: TODO  设定文件 
	* @return void    返回类型 
	* @see createTransportPathForStorageTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-7 下午7:17:46   
	* @throws
	 */
	@Test
	public void createTransportPathForStorageTest(){
		//返回走货路径无记录：77777723 ;返回一条记录 000000723(非分批配载 );  返回一条记录114824376 分批配载   ;返回多条： 050311112
		String waybillNo = "102680159";
		String goodsNo = "001";
		
		String correntOrgCode = "W3100020616";
		calculateTransportPathService.createTransportPathForStorage(waybillNo,correntOrgCode,goodsNo);
	}
	
	/**
	 * 
	* @Title: queryByTimeTest 
	* @Description: TODO  设定文件 
	* @return void    返回类型 
	* @see queryByTimeTest
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-9 上午11:56:02   
	* @throws
	 */
	//@Test
	public void queryByTimeTest(){
		
		// 无效的列类型: 1111; nested exception is java.sql.SQLException: 无效的列类型: 1111
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("origOrgCode", "sdfssdf");
		// 货区编码
		map.put("origGoodsAreaCode", "sdfsdf");
		// 修改时间
		map.put("executeTime", null);
		// 路径调整状态 非合车调整
		map.put("changeType", TransportPathConstants.CHANGEPATH_STATUS_MODIFY);
		
		
		changePathDao.queryByTime(map);
	}
}
