/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server
 * FILE    NAME: CreateLoaderWorkloadTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

import com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao;
import com.deppon.foss.module.transfer.load.api.server.service.ICreateLoaderWorkloadService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * CreateLoaderWorkloadTest
 * @author dp-duyi
 * @date 2012-12-25 上午9:24:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class CreateLoaderWorkloadTest {
	final Logger logger = LoggerFactory.getLogger(CreateLoaderWorkloadTest.class);
	@Autowired
	ICreateLoaderWorkloadDao createLoaderWorkloadDao;
	@Autowired
	ICreateLoaderWorkloadService createLoaderWorkloadService;
	@BeforeTransaction
	public void beforeTransaction() {
	}
	@Test
	public void queryUnCreateWorkLoadUnloadTask_test(){
		createLoaderWorkloadDao.queryUnCreateWorkLoadUnloadTask(new Date(new Date().getTime()-24*60*60*1000),new Date(),0,1);
	}
	@Test
	public void queryUnCreateWorkLoadLoadTask_test(){
		List<LoaderWorkloadDetailDto> loadTasks = createLoaderWorkloadDao.queryUnCreateWorkLoadLoadTask(new Date(new Date().getTime()-24*60*60*1000),new Date(),0,1);
	}
	@Test
	public void queryLoadParticipationDtoByTaskId_test(){
		createLoaderWorkloadDao.queryLoadParticipationDtoByTaskId("fdsfdsfd");
	}
	@Test
	public void queryUnloadParticipationDtoByTaskId_test(){
		createLoaderWorkloadDao.queryUnloadParticipationDtoByTaskId("fsdfsd");
	}
	
	public void insertWorkLoad_test(){
		List<LoaderWorkloadEntity> workloads = new ArrayList<LoaderWorkloadEntity>();
		LoaderWorkloadEntity workLoad = new LoaderWorkloadEntity();
		workLoad.setId(UUIDUtils.getUUID());
		workLoad.setHandleType("fdsfsdfsd");
		workLoad.setJoinTime(new Date());
		workLoad.setLeaveTime(new Date());
		workLoad.setLoaderCode("fsfds");
		workLoad.setLoaderName("fsdfs");
		workLoad.setLoadOrgCode("fdsfds");
		workLoad.setLoadOrgName("fdsfdsf");
		workLoad.setOrgCode("fdsfds");
		workLoad.setOrgName("fdsfds");
		workLoad.setTaskId("fdsfdsf");
		workLoad.setTaskNo("fdsfdsfr");
		workLoad.setTaskType("fdsfds");
		workLoad.setVolume(BigDecimal.ZERO);
		workLoad.setWeight(BigDecimal.ZERO);
		workLoad.setGoodsQty(0);
		workLoad.setWaybillQty(0);
		workLoad.setCreateTime(new Date());
		workLoad.setGoodsType("ALL");
		workLoad.setHandoverNo("12345678p");
		workLoad.setVehicleNo("沪ABCDEF");
		workloads.add(workLoad);
		createLoaderWorkloadDao.insertWorkLoad(workloads);
	}
	@Test
	public void updateLoadTaskBeCreateWorkLoad_test(){
		createLoaderWorkloadDao.updateLoadTaskBeCreateWorkLoad("fsfsd","Y");
	}
	@Test
	public void updateUnloadTaskBeCreateWorkLoad_test(){
		createLoaderWorkloadDao.updateUnloadTaskBeCreateWorkLoad("fsfsd","Y");
	}
	@Test
	public void createLoaderWorkload_test(){
		createLoaderWorkloadService.createLoaderWorkLoad(new Date(),new Date(),0,1);
	}
	@Test
	public void queryUnloadGoodsType(){
		List<String> l = createLoaderWorkloadDao.queryUnloadGoodsType("f6ffea30-1936-4ef0-a036-9b70a5db69f0");
		if(CollectionUtils.isNotEmpty(l)){
			for(String s : l){
				System.out.println(s);
			}
		}
	}
}
