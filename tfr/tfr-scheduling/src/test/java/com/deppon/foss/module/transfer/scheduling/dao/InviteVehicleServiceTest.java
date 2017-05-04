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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateOptimalPlatformService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;

/**
 * TODO（描述类的职责）
 * @author 104306-foss-wangLong
 * @date 2012-12-26 上午9:44:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/platformDispatch.xml",
		"classpath:com/deppon/foss/module/transfer/scheduling/server/spring-datasource.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml"
		})
@TransactionConfiguration(defaultRollback = false)
public class InviteVehicleServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Logger LOGGER = LogManager.getLogger(InviteVehicleServiceTest.class);
	
	@Autowired
	private IInviteVehicleService inviteVehicleService;
	
	@Autowired
	IForecastService forecastService;
	
	@Autowired
	ICalculateTransportPathService calculateTransportPathService;
	
	@Autowired
	ICalculateOptimalPlatformService calculateOptimalPlatformService;
	
	//快递 补码更新走货路径
	@Test
	public void testChangeDestinationPathForExpress() {
		calculateTransportPathService.changeDestinationPathForExpress("1101011010","W011302020515");
	}
	
	//快递 补码更新走货路径
//		@Test
//		public void testChangePathDetailEntityNextOrgCode() {
//			calculateTransportPathService.changePathDetailEntityNextOrgCode("1101011010","W011302020515","0001");
//		}
	
	public void testCalcOptimalPlatform() {
		List<String> handOverBillNoList = new ArrayList<String>();
		//handOverBillNoList.add("");
		calculateOptimalPlatformService.calcOptimalPlatform(handOverBillNoList);
	}
	
	
	
	/**
	 * 计算营业部平均重量体积 JOB 测试
	 * @author huyue
	 * @date 2012-12-26 上午10:28:40
	 */
	@Test
	public void testCalculateAverageWeightAndVolume() {
		forecastService.calculateAverageWeightAndVolume(new Date());
	}
	
	/**
	 * 测试提供给综合查询的接口,返回运单的状态明细, group by状态 ,出发部门,到达部门,出发时间,到达时间,下一到达时间
	 * @author huyue
	 * @date 2012-12-27 下午3:18:37
	 */
	@Test
	public void testQueryWaybillStatusByWaybillNoForPkp() {
		calculateTransportPathService.queryWaybillStatusByWaybillNoForPkp("651221123");
	}
	
	/**
	 * 预测货量 各外场出发,到达 调用预测各线路方法 测试
	 * @author huyue
	 * @date 2012-12-26 上午10:28:40
	 */
	@Test
	public void testForecastTransferCenter() {
		forecastService.forecastTransferCenter(ForecastConstants.FORECAST_ARRIVE,new Date(),"W040002060401");
	}
	
	
	@Test
	public void testQueryInviteCostByInviteNo() {
		BigDecimal bigDecimal = null;
		try {
			bigDecimal = inviteVehicleService.queryInviteCostByInviteNo("W32");
		} catch (InviteVehicleException e) {
			LOGGER.error(e.getMessage());
			Assert.fail();
			
		}
		Assert.assertNotNull(bigDecimal);
		logger.info(bigDecimal);
	}
	
	@Test
	public void testCheckInviteNoIsExists() {
//		String inviteNo = "W101";
		String inviteNo = "W33";
		try {
			inviteVehicleService.checkInviteNoIsExists(inviteNo, "GS00002");
		} catch (InviteVehicleException e) {
			LOGGER.error("失败:" + e.getErrorCode() + ", " + getArgumentMessage(e.getErrorArguments()));
			Assert.fail();
		}
	}
	
	@Test
	public void testUpdateInviteVehicleForFinishBill() {
		String inviteNo = "W33";
		try {
			inviteVehicleService.updateInviteVehicleForFinishBill(inviteNo);
		} catch (InviteVehicleException e) {
			LOGGER.error("失败:" + e.getErrorCode() + ", " + getArgumentMessage(e.getErrorArguments()));
			Assert.fail();
		}
	}
	
	
	public String getArgumentMessage(Object[] argumentMessage) {
		if (argumentMessage == null) {
			return StringUtil.EMPTY_STRING;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (Object object : argumentMessage) {
			stringBuilder.append(object.toString());
		}
		return stringBuilder.toString();
	}

	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	public void setForecastService(IForecastService forecastService) {
		this.forecastService = forecastService;
	}


	/**
	 * 设置inviteVehicleService
	 * @param inviteVehicleService the inviteVehicleService to set
	 */
	public void setInviteVehicleService(IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}
	
}
