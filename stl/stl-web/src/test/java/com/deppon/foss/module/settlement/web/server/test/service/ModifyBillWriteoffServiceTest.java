/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.test.service
 * FILE    NAME: BillWriteoffServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.web.server.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffResultDto;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;

/**
 * 更改单的测试
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-13 下午6:15:04
 */
public class ModifyBillWriteoffServiceTest extends BaseTestCase {

	/**
	 * 更改单 测试用例logger
	 */
	private static final Logger logger = LogManager
			.getLogger(ModifyBillWriteoffServiceTest.class);

	// 调用接送货，更改单服务
	@Autowired
	private IWaybillRfcService waybillRfcService;

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	// 核销不通过
	@Test
	@Ignore
	public void testWriteoffModifyBillFail() {
		// 更改单ID
		List<String> waybillIds = new ArrayList<String>();
		waybillIds.add("CC9DF835E331781BE0430B0AA8C067BA");
		waybillIds.add("bac938d5-7fcb-4c11-8fa6-be3953b2acf9");
		String notes = "核销通过测试数据";
		// 调用接送货的接口 设置核销状态为核销不通过 1、核销通过 2、核销不通过
		CurrentInfo cInfo = StlWebTestUtil.getCurrentInfo();
		this.waybillRfcService.writeOffWaybillChange(waybillIds, notes, "2",
				cInfo);
		Assert.assertNotNull(true);
	}

	// 核销通过
	@Test
	@Ignore
	public void testWriteoffModifyBillSuccess() {
		// 更改单ID
		List<String> waybillIds = new ArrayList<String>();
		waybillIds.add("CC9DF835E331781BE0430B0AA8C067BA");
		waybillIds.add("bac938d5-7fcb-4c11-8fa6-be3953b2acf9");
		String notes = "核销不通过测试数据";
		// 调用接送货的接口 设置核销状态为核销不通过 1、核销通过 2、核销不通过
		CurrentInfo cInfo = StlWebTestUtil.getCurrentInfo();
		this.waybillRfcService.writeOffWaybillChange(waybillIds, notes, "1",
				cInfo);
		Assert.assertNotNull(true);
	}

	// 测试更改单查询
	@Test
//	@Ignore
	public void testQueryModifyBillList() {
		int start = 2;
		int limit = 2;
		List<String> waybillNumbers = new ArrayList<String>();
		// 运单号
		waybillNumbers.add("12345678");
		ModifyBillWriteoffDto modifyBillWriteoffDto = new ModifyBillWriteoffDto();
		// 核销状态
		modifyBillWriteoffDto.setWriteoffStatus("");
		modifyBillWriteoffDto.setWaybillNumbers(waybillNumbers);
		// 起草部门
		modifyBillWriteoffDto.setDarftOrgCode("123");
		// 开始日期
		String startDateStr = "2012-10-12 17:37:31";
		// 结束日期
		String endDateStr = "2012-11-12 17:38:05";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			modifyBillWriteoffDto.setAcceptStartDate(sf.parse(startDateStr));
			modifyBillWriteoffDto.setAcceptEndDate(sf.parse(endDateStr));
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		List<ModifyBillWriteoffResultDto> ResultList = this.waybillRfcService
				.queryModifyBillWriteoffResult(modifyBillWriteoffDto, start,
						limit);
		logger.info("更改单条数：" + ResultList.size());
		Assert.assertNotNull(true);
	}
	
	//测试导出更改单的方法
	@Test
	@Ignore
	public void queryExportWaybillChange(){
		
	}
	
	
	

	// 反核销更改单测试
	@Test
	@Ignore
	public void reverseModifyBill() {
		List<String> waybillIds = new ArrayList<String>();
		// 反核销状态
		waybillIds.add("CC9DF835E331781BE0430B0AA8C067BA");
		waybillIds.add("bac938d5-7fcb-4c11-8fa6-be3953b2acf9");
		String notes = "dddfdfdf,ddfdfdf'dfdfdfdfdfdfdfdfdfdfdfdf";
		// 获取当前登录人信息
		CurrentInfo cInfo = StlWebTestUtil.getCurrentInfo();
		// 反核销运单
		this.waybillRfcService.reverseWaybillChange(waybillIds, notes, cInfo);
		Assert.assertNotNull(true);
	}

}
