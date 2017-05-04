/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.test.service
 * FILE    NAME: BillWriteoffServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionIncomeEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;

/**
 * 现金收入(缴款)报表测试
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-17 上午10:47:30
 */
public class BillCashPayInManageServiceTest extends BaseTestCase {

	/**
	 * 现金收入(缴款)报表测试Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(BillCashPayInManageServiceTest.class);

	// 查询现金收入（缴款）service
	@Autowired
	private IReportCashRecPayInService reportCashRecPayInService;

	/**
	 * 测试，查询现金收入（缴款）报表所有部门应缴款信息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-17 上午10:50:48
	 */
	@Test
	@Ignore
	public void testQueryReportUploadCashPayIn() {
		//BillCashRecPayInDto billCashRecDto = new BillCashRecPayInDto();
		//List<CashCollectionRptEntity> cashList = reportCashRecPayInService
		//		.queryUploadCashinComerpt(billCashRecDto);
		//Assert.assertNotNull(cashList.size());
		//logger.info("现金收入(缴款)报表条数为：" + cashList.size());
	}

	/**
	 * 测试根据财务自助返回的实缴情况，更新现金收入报表
	 * 1、先根据财务自助传递信息，按部门查询现金汇总信息的应缴信息
	 * 2、根据现金收入报表汇总，查询明细
	 * 3、更新现金收入报表明细，已缴款金额和未缴款金额
	 */
	@Test
	@Rollback(true)
	public void testUpdateCashIncomerptForProcessor() {
		logger.info("----------注意咯注意咯 Junit已经开始上路 ~！！");
		UpdateCashIncomerptDto dto = new UpdateCashIncomerptDto();
		dto.setSerialNO("BCLTEST001");
		dto.setPayAmount(new BigDecimal("38279355.27"));
		System.out.println(dto.getPayAmount());
		dto.setPayMethod("CH");
		dto.setDeptCd("W011302020515");
		dto.setUnifiedCode("DP02076");
		
		Map<String ,Object> map = reportCashRecPayInService.updateCashIncomerptForProcessor(dto);
		
		System.out.println(map.get("num"));
		System.out.println(map.get("amountUse"));
		logger.info("----------注意咯注意咯 Junit已经开始回家 ~！！");
	}
}
