/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.service
 * FILE    NAME: InviteVehicleServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;
import com.deppon.foss.module.transfer.scheduling.server.dao.impl.TruckDepartPlanDetailDao;

/**
 * TODO（描述类的职责）
 * @author 104306-foss-wangLong
 * @date 2012-12-26 上午9:44:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath*:com/deppon/foss/module/transfer/scheduling/server/spring-test.xml",
		"classpath:com/deppon/foss/module/transfer/scheduling/server/spring-datasource.xml"})
@TransactionConfiguration(defaultRollback = true)
public class TruckDepartPlanDetailDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOGGER = LogManager.getLogger(TruckDepartPlanDetailDaoTest.class);
	
	private ITruckDepartPlanDetailDao truckDepartPlanDetailDao;

	/**
	 * 长途
	 */
	@Test
	@Transactional
	public void testQueryTruckDepartPlanInfoDetail() {
		
		truckDepartPlanDetailDao = (ITruckDepartPlanDetailDao) this.applicationContext.getBean("truckDepartPlanDetailDao");
		
		insertDataLong();
		//查询实体条件
		TruckDepartPlanDetailDto entity = new TruckDepartPlanDetailDto();
		//查询条件DTO封装
		// 车牌
		entity.setVehicleNo("沪BT9182");
		// 发车日期
		entity.setDepartDate(new Date(2013,4,8,00,00,00));
		// 出发部门
		entity.setOrigOrgCode("W1200030122");
		// 到达部门
		entity.setDestOrgCode("W1000030124");
		// 可用
		entity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 长途下发
		entity.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
		// 长途
		entity.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_LONG);
		// 未出发
		entity.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		entity.setList(list);
		// 返回结果(发车计划详细信息)
		TruckDepartPlanDetailDto resultInfoDto = null;
		
		try {
			resultInfoDto = truckDepartPlanDetailDao.queryTruckDepartPlanInfoDetail(entity);
			if (resultInfoDto == null) {
				Assert.fail();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
		Assert.assertNotNull(resultInfoDto);
		Assert.assertEquals(resultInfoDto.getDriverCode1(), "030443");
		Assert.assertEquals(resultInfoDto.getDriverName1(), "吴家录");
		Assert.assertEquals(resultInfoDto.getDriverPhone1(), "13258122960");
		Assert.assertEquals(resultInfoDto.getFrequencyNo(), "1");
		Assert.assertEquals(resultInfoDto.getContainerNo(), "129");
		Assert.assertEquals(resultInfoDto.getTrailerVehicleNo(), "14564515");
		Assert.assertEquals(resultInfoDto.getTruckModel(), "CX0020");
		Assert.assertEquals(resultInfoDto.getLineNo(), "319");
		logger.info(resultInfoDto);
	}
	
	/**
	 * 短途
	 */
	@Test
	@Transactional
	public void testQueryTruckDepartPlanInfoDetailShort() {
		
		truckDepartPlanDetailDao = (ITruckDepartPlanDetailDao) this.applicationContext.getBean("truckDepartPlanDetailDao");
		
		insertDataShort();
		//查询实体条件
		TruckDepartPlanDetailDto entity = new TruckDepartPlanDetailDto();
		//查询条件DTO封装
		// 车牌
		entity.setVehicleNo("沪BT9182");
		// 发车日期
		entity.setDepartDate(new Date(2013,4,8,00,00,00));
		// 出发部门
		entity.setOrigOrgCode("W1200030122");
		// 到达部门
		entity.setDestOrgCode("W1000030124");
		// 可用
		entity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 长途下发
		entity.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_NEW);
		// 长途
		entity.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 未出发
		entity.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		entity.setList(list);
		// 返回结果(发车计划详细信息)
		TruckDepartPlanDetailDto resultInfoDto = null;
		
		try {
			resultInfoDto = truckDepartPlanDetailDao.queryTruckDepartPlanInfoDetail(entity);
			if (resultInfoDto == null) {
				Assert.fail();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
		Assert.assertNotNull(resultInfoDto);
		Assert.assertEquals(resultInfoDto.getDriverCode1(), "030443");
		Assert.assertEquals(resultInfoDto.getDriverName1(), "吴家录");
		Assert.assertEquals(resultInfoDto.getDriverPhone1(), "13258122960");
		Assert.assertEquals(resultInfoDto.getFrequencyNo(), "1");
		Assert.assertEquals(resultInfoDto.getContainerNo(), "129");
		Assert.assertEquals(resultInfoDto.getTrailerVehicleNo(), "14564515");
		Assert.assertEquals(resultInfoDto.getTruckModel(), "CX0020");
		Assert.assertEquals(resultInfoDto.getLineNo(), "319");
		logger.info(resultInfoDto);
	}
	
	/**
	 * 查询结果为0条记录
	 */
	@Test
	@Transactional
	public void testQueryTruckDepartPlanInfoDetailResultNull() {
		
		truckDepartPlanDetailDao = (ITruckDepartPlanDetailDao) this.applicationContext.getBean("truckDepartPlanDetailDao");
		
		insertDataLong();
		//查询实体条件
		TruckDepartPlanDetailDto entity = new TruckDepartPlanDetailDto();
		//查询条件DTO封装
		// 车牌
		entity.setVehicleNo("沪BTB1B2");
		// 发车日期
		entity.setDepartDate(new Date(2013,4,8,00,00,00));
		// 出发部门
		entity.setOrigOrgCode("W1200030122");
		// 到达部门
		entity.setDestOrgCode("W1000030124");
		// 可用
		entity.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 长途下发
		entity.setPlanStatusRelease(TruckDepartPlanConstants.PLAN_STATUS_NEW);
		// 长途
		entity.setPlanTypeLong(TruckDepartPlanConstants.PLAN_TYPE_SHORT);
		// 未出发
		entity.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
		// 加发和正常的
		List<String> list = new ArrayList<String>();
		// 加发
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		list.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		// 列表
		entity.setList(list);
		// 返回结果(发车计划详细信息)
		TruckDepartPlanDetailDto resultInfoDto = null;
		
		try {
			resultInfoDto = truckDepartPlanDetailDao.queryTruckDepartPlanInfoDetail(entity);
			if (resultInfoDto == null) {
				Assert.assertNull(resultInfoDto);
				LOGGER.info("查询结果是0条");
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void insertDataLong() {
		
		TruckDepartPlanDetailDto entity = new TruckDepartPlanDetailDto();
		entity.setId("b7609f0f-9860-4bdd-91bb-db5bb81a9c99");
		entity.setTruckDepartPlanId("b7609f0f-9860-4bdd-91bb-db5bb81a9c99");
		entity.setPlanType("LONG");
		entity.setVehicleNo("沪BT9182");
		entity.setLineNo("319");
		entity.setDepartDate(new Date(2013,4,8,00,00,00));
		entity.setPlanDepartTime(new Date());
		entity.setFrequencyNo("1");
		entity.setOrigOrgCode("W1200030122");
		entity.setDestOrgCode("W1000030124");
		entity.setPlatformDistributeId("39abe378-c6e3-4299-b523-713d194baf99");
		entity.setIsOnScheduling("Y");
		entity.setFrequencyType("NORMAL");
		entity.setTruckModel("CX0020");
		entity.setTruckType("OWN");
		entity.setContainerNo("129");
		entity.setMaxLoadWeight(new BigDecimal(32.00));
		entity.setActualMaxLoadWeight(new BigDecimal(32.00));
		entity.setTruckVolume(new BigDecimal(112.690));
		entity.setPlanLoadWeight(new BigDecimal(32.000));
		entity.setPlanLoadVolume(new BigDecimal(112.690));
		entity.setDriverCode1("030443");
		entity.setDriverName1("吴家录");
		entity.setDriverPhone1("13258122960");
		entity.setStatus("Y");
		entity.setCreateTime(new Date());
		entity.setCreateUserCode("114548");
		entity.setCreateUserName("杨晨");
		entity.setCreateOrgCode("W1200030122");
		entity.setUpdateTime(new Date());
		entity.setUpdateUserCode("W1200030122");
		entity.setUpdateUserName("杨晨");
		entity.setUpdateOrgCode("W1200030122");
		entity.setLineName("北京转运场-厦门转运场");
		entity.setPlanArriveTime(new Date());
		entity.setPlanStatus("RELEASE");
		entity.setPlatformTimeStart(new Date());
		entity.setPlatformTimeEnd(new Date());
		entity.setLongCarGroup("W01140702");
		entity.setInitFlag("Y");
		entity.setHasLeft("N");
		entity.setTrailerVehicleNo("14564515");
		entity.setPlatformNo("YT149");
		truckDepartPlanDetailDao.addTruckDepartPlanDetail(entity);
	}
	
	@SuppressWarnings("deprecation")
	public void insertDataShort() {
		
		TruckDepartPlanDetailDto entity = new TruckDepartPlanDetailDto();
		entity.setId("b7609f0f-9860-4bdd-91bb-db5bb81a9c99");
		entity.setTruckDepartPlanId("b7609f0f-9860-4bdd-91bb-db5bb81a9c99");
		entity.setPlanType("SHORT");
		entity.setVehicleNo("沪BT9182");
		entity.setLineNo("319");
		entity.setDepartDate(new Date(2013,4,8,00,00,00));
		entity.setPlanDepartTime(new Date());
		entity.setFrequencyNo("1");
		entity.setOrigOrgCode("W1200030122");
		entity.setDestOrgCode("W1000030124");
		entity.setPlatformDistributeId("39abe378-c6e3-4299-b523-713d194baf99");
		entity.setIsOnScheduling("Y");
		entity.setFrequencyType("NORMAL");
		entity.setTruckModel("CX0020");
		entity.setTruckType("OWN");
		entity.setContainerNo("129");
		entity.setMaxLoadWeight(new BigDecimal(32.00));
		entity.setActualMaxLoadWeight(new BigDecimal(32.00));
		entity.setTruckVolume(new BigDecimal(112.690));
		entity.setPlanLoadWeight(new BigDecimal(32.000));
		entity.setPlanLoadVolume(new BigDecimal(112.690));
		entity.setDriverCode1("030443");
		entity.setDriverName1("吴家录");
		entity.setDriverPhone1("13258122960");
		entity.setStatus("Y");
		entity.setCreateTime(new Date());
		entity.setCreateUserCode("114548");
		entity.setCreateUserName("杨晨");
		entity.setCreateOrgCode("W1200030122");
		entity.setUpdateTime(new Date());
		entity.setUpdateUserCode("W1200030122");
		entity.setUpdateUserName("杨晨");
		entity.setUpdateOrgCode("W1200030122");
		entity.setLineName("北京转运场-厦门转运场");
		entity.setPlanArriveTime(new Date());
		entity.setPlanStatus("NEW");
		entity.setPlatformTimeStart(new Date());
		entity.setPlatformTimeEnd(new Date());
		entity.setLongCarGroup("W01140702");
		entity.setInitFlag("Y");
		entity.setHasLeft("N");
		entity.setTrailerVehicleNo("14564515");
		entity.setPlatformNo("YT149");
		truckDepartPlanDetailDao.addTruckDepartPlanDetail(entity);
	}
}
