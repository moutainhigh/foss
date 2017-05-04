/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.service
 * FILE    NAME: InviteVehicleServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.dao;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;

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
public class InviteVehicleInfoServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOGGER = LogManager.getLogger(InviteVehicleInfoServiceTest.class);
	
	@Autowired
	private IInviteVehicleInfoService inviteVehicleInfoService;
	
	// 受理外请车Dao
	@Autowired
	private IPassInviteApplyDao passInviteApplyDao;
	
	// 外请车约车Dao
	@Autowired
	private IInviteVehicleDao inviteVehicleDao;
	
	@Test
	@Transactional
	public void testQueryInviteVehicleInfo() {
		insertData();
		//查询实体条件
		InviteVehicleQueryParmEntity entity = new InviteVehicleQueryParmEntity();
		// 车牌号
		entity.setVehicleNo("豫SYE257");
		// 部门编码
		entity.setApplyOrgCode("W011302020515");
		// 报道状态
		entity.setStatus("VERIFY_ARRIVE");
		// 未使用状态
		entity.setUseStatus("UNUSED");
		// 返回结果(约车详细信息)
		InviteVehicleInfoDto resultInfoDto = null;
		
		try {
			resultInfoDto = inviteVehicleInfoService.queryInviteVehicleInfo(entity);
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
		Assert.assertNotNull(resultInfoDto);
		Assert.assertEquals(resultInfoDto.getInviteCost(), new BigDecimal(333300));
		Assert.assertEquals(resultInfoDto.getInviteNo(), "W9989");
		Assert.assertEquals(resultInfoDto.getVehicleNo(), "豫SYE257");
		Assert.assertEquals(resultInfoDto.getApplyOrgCode(), "W011302020515");
		Assert.assertEquals(resultInfoDto.getApplyOrgName(), "上海闵行区浦江镇营业部");
		logger.info(resultInfoDto);
	}
	
	@Test
	@Transactional
	public void testQueryInviteVehicleInfoApplyOrgCodeIsNull() {
		
		insertData();
		//查询实体条件
		InviteVehicleQueryParmEntity entity = new InviteVehicleQueryParmEntity();
		// 车牌号
		entity.setVehicleNo("豫SYE257");
		// 报道状态
		entity.setStatus("VERIFY_ARRIVE");
		// 未使用状态
		entity.setUseStatus("UNUSED");
		// 返回结果(约车详细信息)
		InviteVehicleInfoDto resultInfoDto = null;
		
		try {
			resultInfoDto = inviteVehicleInfoService.queryInviteVehicleInfo(entity);
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
		Assert.assertNotNull(resultInfoDto);
		Assert.assertEquals(resultInfoDto.getInviteCost(), new BigDecimal(333300));
		Assert.assertEquals(resultInfoDto.getInviteNo(), "W9989");
		Assert.assertEquals(resultInfoDto.getVehicleNo(), "豫SYE257");
		Assert.assertEquals(resultInfoDto.getApplyOrgCode(), "W011302020515");
		Assert.assertEquals(resultInfoDto.getApplyOrgName(), "上海闵行区浦江镇营业部");
		logger.info(resultInfoDto);
	}
	
	@Test
	@Transactional
	public void testQueryInviteVehicleInfoParIsNull() {
		insertData();
		//查询实体条件
		InviteVehicleQueryParmEntity entity = new InviteVehicleQueryParmEntity();
		// 报道状态
		entity.setStatus("VERIFY_ARRIVE");
		// 未使用状态
		entity.setUseStatus("UNUSED");
		// 返回结果(约车详细信息)
		InviteVehicleInfoDto resultInfoDto = null;
		
		try {
			resultInfoDto = inviteVehicleInfoService.queryInviteVehicleInfo(entity);
			if (resultInfoDto == null) {
				Assert.assertNull(resultInfoDto);
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testUpdateInviteVehicleInfo() {
		insertData();
		//更新车辆实体
		InviteVehicleEditParmEntity entity = new InviteVehicleEditParmEntity();
		// 车牌号
		entity.setVehicleNo("豫SYE257");
		// 部门编码
		entity.setInviteNo("W9989");
		// 使用状态
		entity.setUseStatus("USING");
		
		//更新为使用状态
		try {
			
			int resultFlag = inviteVehicleInfoService.updateInviteVehicleStatus(entity);
			
			if (resultFlag > 0) {
				LOGGER.debug("更新条数" + resultFlag);
			} else {
				Assert.fail();
			}
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
	}
	
	@Test
	@Transactional
	public void testUpdateInviteVehicleInfoUnUsed() {
		
		insertDataUsing();
		//更新车辆实体
		InviteVehicleEditParmEntity entity = new InviteVehicleEditParmEntity();
		// 车牌号
		entity.setVehicleNo("豫SYE257");
		// 部门编码
		entity.setInviteNo("W9989");
		// 未使用状态
		entity.setUseStatus("UNUSED");
		
		// 更新为未使用状态
		try {
			int resultFlag = inviteVehicleInfoService.updateInviteVehicleStatus(entity);
			
			if (resultFlag > 0) {
				LOGGER.debug("更新条数" + resultFlag);
			} else {
				Assert.fail();
			}
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
	}
	
	@Test
	@Transactional
	public void testUpdateInviteVehicleInfoParNull() {
		
		insertDataUsing();
		//更新车辆实体
		InviteVehicleEditParmEntity entity = new InviteVehicleEditParmEntity();
		// 车牌号
		entity.setVehicleNo("");
		// 部门编码
		entity.setInviteNo("");
		// 未使用状态
		entity.setUseStatus("");
		
		// 更新为未使用状态
		try {
			int resultFlag = inviteVehicleInfoService.updateInviteVehicleStatus(entity);
			
			if (resultFlag > 0) {
				Assert.fail();
			} else {
				LOGGER.debug("更新条数是" + resultFlag);
			}
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
		}
	}
	
	public void insertData() {
		
		// 受理外请车数据
		PassInviteApplyEntity applyEntity = new PassInviteApplyEntity();
		applyEntity.setId("2e280488-38d3-4824-9825-fb4e8183d198");
		applyEntity.setInviteNo("W9989");
		applyEntity.setPerdictArriveTime(new Date());
		applyEntity.setInviteCost(new BigDecimal(3333));
		applyEntity.setVehicleNo("豫SYE257");
		applyEntity.setAcceptOrgName("上海车队");
		applyEntity.setAcceptOrgCode("W3100020619");
		applyEntity.setAcceptEmpName("彭洁");
		applyEntity.setAcceptEmpCode("092444");
		applyEntity.setPassStatus("COMMITTED");
		applyEntity.setPassTime(new Date());
		applyEntity.setCurrencyCode("RMB");
		applyEntity.setUseStatus("UNUSED");
		passInviteApplyDao.addPassInviteApply(applyEntity);
		
		// 外请车约车实体
		InviteVehicleEntity entity = new InviteVehicleEntity();
		entity.setId("2e280488-38d3-4824-9825-fb4e8183d198");
		entity.setStatus("VERIFY_ARRIVE");
		entity.setInviteNo("W9989");
		entity.setApplyTime(new Date());
		entity.setUsePurpose("INVITE_ORDER");
		entity.setUseType("TO_TRANSIT");
		entity.setOrderVehicleModel("CX0011");
		entity.setPredictUseTime(new Date());
		entity.setUseAddress("明珠路32号");
		entity.setGoodsQty(0);
		entity.setWeight(new BigDecimal(0.000));
		entity.setVolume(new BigDecimal(0.000));
		entity.setDispatchTransDept("W3100020619");
		entity.setApplyOrgName("上海闵行区浦江镇营业部");
		entity.setApplyOrgCode("W011302020515");
		entity.setApplyEmpName("彭洁");
		entity.setApplyEmpCode("092444");
		entity.setMobilephoneNo("15001710200");
		entity.setIsPassByArrivedDept("N");
		entity.setArrivedDeptCode("W3100020616");
		entity.setIsReturn("N");
		entity.setReturnCost(new BigDecimal(0));
		entity.setIsContractVehicle("N");
		entity.setUseStatus("UNUSED");
		entity.setTopFleetCode("W3100020619");
		entity.setArrivalTime(new Date());
		inviteVehicleDao.addInviteVehicle(entity);
	}
	
	public void insertDataUsing() {
		
		// 受理外请车数据
		PassInviteApplyEntity applyEntity = new PassInviteApplyEntity();
		applyEntity.setId("2e280488-38d3-4824-9825-fb4e8183d198");
		applyEntity.setInviteNo("W9989");
		applyEntity.setPerdictArriveTime(new Date());
		applyEntity.setInviteCost(new BigDecimal(3333));
		applyEntity.setVehicleNo("豫SYE257");
		applyEntity.setAcceptOrgName("上海车队");
		applyEntity.setAcceptOrgCode("W3100020619");
		applyEntity.setAcceptEmpName("彭洁");
		applyEntity.setAcceptEmpCode("092444");
		applyEntity.setPassStatus("COMMITTED");
		applyEntity.setPassTime(new Date());
		applyEntity.setCurrencyCode("RMB");
		applyEntity.setUseStatus("USING");
		passInviteApplyDao.addPassInviteApply(applyEntity);
	}

	/**
	 * 设置inviteVehicleService
	 * @param inviteVehicleService the inviteVehicleService to set
	 */
	public void setInviteVehicleInfoService(
			IInviteVehicleInfoService inviteVehicleInfoService) {
		this.inviteVehicleInfoService = inviteVehicleInfoService;
	}
	
	/**
	 * 设置passInviteApplyDao
	 * @param passInviteApplyDao the passInviteApplyDao to set
	 */
	public void setPassInviteApplyDao(IPassInviteApplyDao passInviteApplyDao) {
		this.passInviteApplyDao = passInviteApplyDao;
	}

	/**
	 * 设置inviteVehicleDao
	 * @param inviteVehicleDao the inviteVehicleDao to set
	 */
	public void setInviteVehicleDao(IInviteVehicleDao inviteVehicleDao) {
		this.inviteVehicleDao = inviteVehicleDao;
	}
	
}
