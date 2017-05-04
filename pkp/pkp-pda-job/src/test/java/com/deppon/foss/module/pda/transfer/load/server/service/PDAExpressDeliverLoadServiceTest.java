package com.deppon.foss.module.pda.transfer.load.server.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverLoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath*:com/deppon/foss/module/pda/test/META-INF/spring.xml" }) 
@TransactionConfiguration 
@Transactional 
public class PDAExpressDeliverLoadServiceTest extends 
AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	@Test
	public void queryUnFinishedPackage(){
		pdaExpressDeliverLoadService.queryExpressDeliverLoadTask("123", "123", new Date(), new Date());
	}
	@Test
	public void createTask(){
		ExpressDeliverLoadTaskDto task = new ExpressDeliverLoadTaskDto();
		task.setCourier("042795");
		task.setCreateOrgCode("W3100020616");
		task.setCreateTime(new Date());
		task.setDeviceNo("123456");
		task.setGoodsType("ALL");
		task.setOperatorCode("042795");
		task.setPlatformNo("001");
		task.setVehicleNo("沪BE2442");
		pdaExpressDeliverLoadService.createTask(task);
	}
	@Test
	public void cancelLoadTask(){
		pdaExpressDeliverLoadService.cancelLoadTask("123", "sdf", "sdfs", new Date());
	}
	@Test
	public void submitLoadTask(){
		pdaExpressDeliverLoadService.submitLoadTask("123", new Date(), "123", "123");
	}
	@Test
	public void scan(){
		ExpressDeliverScanDto scanDto = new ExpressDeliverScanDto();
		scanDto.setDeviceNo("123");
		scanDto.setScanState("sdf");
		scanDto.setScanTime(new Date());
		scanDto.setSerialNo("1232");
		scanDto.setTaskNo("sdfsf");
		scanDto.setType("sdfsf");
		scanDto.setWayBillNo("123");
		pdaExpressDeliverLoadService.scan(scanDto);
	}
}
