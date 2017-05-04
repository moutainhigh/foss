package com.deppon.foss.module.transfer.departure.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.server.service.IWebDepartureService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;

/**
 * 
 * 通知客户DAO测试类
 * 
 * @author ibm-wangfei
 * @date Oct 24, 2012 5:06:00 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ "classpath*:com/deppon/foss/module/departrue/test/META-INF/spring.xml" })
@TransactionConfiguration
@Transactional
public class WebDepartTest extends
		AbstractTransactionalJUnit4SpringContextTests
{

	@Autowired
	private IWebDepartureService webDepartureService = null;

	@BeforeTransaction
	public void beforeTransaction()
	{ 
		AutoDepartDTO autoDto = new AutoDepartDTO();
		autoDto.setVehicleNo("HU-A-88888");
		autoDto.setDriverName("123");
		autoDto.setApplyOrgCode("123");
		autoDto.setAutoDepartType("123");
		autoDto.setCreateTime(DepartureHandle.getCurrentDate());
		autoDto.setApplyUserCode("123");
		autoDto.setApplyOrgCode("123");
		autoDto.setApplyUserName("123");
		autoDto.setUpdateUserCode("123");
		autoDto.setUpdateOrgCode("123");
		autoDto.setUpdateUserName("123");
		autoDto.setUpdateTime(DepartureHandle.getCurrentDate());
		// 申请方式，设为自动
		autoDto.setApplyType(DepartureConstant.DEPART_APPLY_TYPE_AUTO);
		autoDto.setApplyDepartTime(DepartureHandle.getCurrentDate());
		webDepartureService.autoDepart(autoDto);
	} 

	/**
	 * 
	 * 测试新增运单客户通知记录
	 * 
	 * @author ibm-liubinbin
	 * @date Oct 24, 2012 5:29:55 PM
	 */
	@Test
	@Transactional 
	public void testWriteDepartData()
	{
//		sharedService.queryDriverInfoByVehicleNo("123");
	}

}
