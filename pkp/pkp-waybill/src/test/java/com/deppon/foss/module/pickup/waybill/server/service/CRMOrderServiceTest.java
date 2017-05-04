/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/CRMOrderServiceTest.java
 * 
 * FILE NAME        	: CRMOrderServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CrmOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmOrderQueryDto;

public class CRMOrderServiceTest {

	private ICrmOrderService crmOrderService;

	@Before
	public void setUpBeforeClass() throws Exception {
		crmOrderService = TestHelper.get()
				.getBeanByClass(CrmOrderService.class);
	}
 
	//@Test
	public void test1() throws ParseException {
		CrmOrderQueryDto query = new CrmOrderQueryDto();
		// query.setShipperLinkman("asdfasfd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		query.setBeginTime(sdf1.parse("2010-01-01 00:00:00"));
		query.setEndTime(new Date());
		query.setPageNum(1);
		query.setPageSize(10);
		List<CrmOrderInfo> list = crmOrderService.searchOrder(query)
				.getCrmOrderInfoList();
		System.out.println(list.size());
	}

	//@Test
	public void test2() {
		CrmOrderDetailDto order = crmOrderService.importOrder("1000007");
		System.out.println(order.getOrderNumber());
	}

	// @Test
	public void test3() throws ParseException {

		Date curDate = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String temp = sdf1.format(curDate);
		Date curDate2 = sdf1.parse(temp);
		System.out.println(curDate2);
	}
	
	//@Test
	public void test4() {
		CouponInfoDto request = new CouponInfoDto();
		request.setCouponNumber("121213087157699");
		request.setUsed(false);
		CouponWaybillInfoDto waybill = new CouponWaybillInfoDto();
		waybill.setWaybillNumber("99999999");
		waybill.setCustNumber("873846");
		waybill.setArrivedDept("3255234");
		waybill.setArrivedOutDept("3455345");
		waybill.setLeaveDept("564643");
		waybill.setLeaveMobile("13308080097");
		waybill.setLeaveOutDept("743354");
		waybill.setLeavePhone("010-85881470");
		waybill.setOrderNumber("9387393");
		waybill.setOrderSource("OUTER");
		waybill.setProduceType("C10003");
		waybill.setTotalAmount(BigDecimal.valueOf(256d));
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		AmountInfoDto amount = new AmountInfoDto();
		amount.setValuationCode("10001");
		amount.setValuationAmonut(BigDecimal.valueOf(12d));
		amountInfoList.add(amount);
		amount.setValuationCode("10002");
		amount.setValuationAmonut(BigDecimal.valueOf(15d));
		amountInfoList.add(amount);
		waybill.setAmountInfoList(amountInfoList);
		request.setWaybillInfo(waybill);
		CouponInfoResultDto response = crmOrderService.validateCoupon(request);
		System.out.println(response.getCouponAmount());
		System.out.println(response.getCanNotUseReason());
	}
	
	//@Test
	public void test5() {
		boolean response = crmOrderService.effectCouponState("121213087157699");
		//Assert.assertFalse(response);
		Assert.assertTrue(response);
	}

}