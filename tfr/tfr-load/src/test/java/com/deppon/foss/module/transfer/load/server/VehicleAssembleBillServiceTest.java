package com.deppon.foss.module.transfer.load.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao;

/** 
 * @className: VehicleAssembleBillServiceTest
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单测试，测试给接送货、综合提供的服务
 * @date: 2013-1-7 上午9:42:17
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class VehicleAssembleBillServiceTest {

	@Autowired
	private IVehicleAssembleBillDao vehicleAssembleBillDao;
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	 * 提供给综合的接口，根据配载车次号获取车次下所有运单号
	 * @param vehicleAssembleNo配载车次号
	 * @return 运单号的集合
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午9:45:36
	 */
	@Test
	public void queryWaybillNoByVehicleAssembleNo(){
		vehicleAssembleBillDao.queryWaybillNoListByVehicleAssembleNo("SHBJ12120401");
	}
	
	/**
	 * 提供给接送货的接口，根据运单号获取汽运配载记录
	 * @param waybillNo 运单号
	 * @return 配载记录对象list
	 * @author 045923-foss-shiwei
	 * @date 2013-1-17 上午9:50:34
	 */
	@Test
	public void queryVehicleAssembleRecordsByWaybillNo(){
		vehicleAssembleBillDao.queryVehicleAssembleRecordsByWaybillNo("200500001");
	}
	
}
