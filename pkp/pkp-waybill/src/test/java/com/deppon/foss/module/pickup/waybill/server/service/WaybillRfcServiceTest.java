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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillRfcServiceTest.java
 * 
 * FILE NAME        	: WaybillRfcServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillRfcService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.ModifyBillWriteoffDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcForAccountServiceDto;
import com.deppon.foss.util.DateUtils;

/**
 * 更改单服务测试
 * 
 * @author 026113-foss-linwensheng
 * 
 */
@SuppressWarnings("unused")
public class WaybillRfcServiceTest {

	IWaybillRfcService waybillRfcService;

	@Before
	public void setUp() throws Exception {
		waybillRfcService = (IWaybillRfcService) TestHelper.get()
				.getBeanByClass(WaybillRfcService.class);

	}

	/**
	 * 测试通过运单号集合查询未处理更改单
	 */
	@Test 
	public void testIsExsitsWayBillRfcs() {
		List<String> waybillNoList = new ArrayList<String>();
		waybillNoList.add("12345678");
		waybillNoList.add("12345679");
		waybillNoList.add("12345610");
		waybillRfcService.isExsitsWayBillRfcs(waybillNoList);
	}

	/**
	 * 测试通过运单号查询未处理更改单
	 */
	@Test
	public void testIsExsitsWayBillRfc() {

		waybillRfcService.isExsitsWayBillRfc("12345678");
	}

	/**
	 * 测试通过运单号查询未处理更改单
	 */
	 @Test
	public void testQueryModifyBillWriteoffResultByWaybillNo() {

		ModifyBillWriteoffDto modifyBillWriteoffDto = new ModifyBillWriteoffDto();
		int start = 1;
		int limit = 10;
		
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add("1534646");
		
		modifyBillWriteoffDto.setWaybillNumbers(waybillNos);
		waybillRfcService.queryModifyBillWriteoffResult(modifyBillWriteoffDto,start,limit);
	}

	/**
	 * 测试通受理日期查询未处理更改单
	 */
	 @Test
	public void testQueryModifyBillWriteoffResultByDate() {
  
		ModifyBillWriteoffDto modifyBillWriteoffDto = new ModifyBillWriteoffDto();
		modifyBillWriteoffDto.setAcceptStartDate(new Date());
		modifyBillWriteoffDto.setAcceptEndDate(DateUtils.getEndDatetime(
				new Date(), 10));
		int start = 1;
		int limit = 10;
		waybillRfcService.queryModifyBillWriteoffResult(modifyBillWriteoffDto,start,limit);
	}

	/**
	 * 测试通过查询未处理更改单
	 */
    @Test
	public void testQueryModifyBillWriteoffResultByALL() {

		ModifyBillWriteoffDto modifyBillWriteoffDto = new ModifyBillWriteoffDto();
		// modifyBillWriteoffDto.setWaybillNumber("12345678");
		modifyBillWriteoffDto.setAcceptStartDate(new Date());
		modifyBillWriteoffDto.setAcceptEndDate(DateUtils.getEndDatetime(
				new Date(), 10));
		modifyBillWriteoffDto.setDarftOrgCode("456456");
		int start = 1;
		int limit = 10;
		waybillRfcService.queryModifyBillWriteoffResult(modifyBillWriteoffDto,start,limit);
	}

	/**
	 * 
	 * 查询总条数
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-7 上午9:34:02
	 */
	
	@Test
	public void testqueryModifyBillWriteoffResultTotalNumber()
	{
		ModifyBillWriteoffDto modifyBillWriteoffDto = new ModifyBillWriteoffDto();
		// modifyBillWriteoffDto.setWaybillNumber("12345678");
		modifyBillWriteoffDto.setAcceptStartDate(DateUtils.convert("2010-01-01"));
		modifyBillWriteoffDto.setAcceptEndDate(DateUtils.convert("2012-12-01"));
		//modifyBillWriteoffDto.setDarftOrgCode("456456");
		waybillRfcService.queryModifyBillWriteoffResultTotalNumber(modifyBillWriteoffDto);
	}
	@Test
	public void testWriteOffWaybillChange() {

		List<String> waybillChangIDs = new ArrayList<String>();
		waybillChangIDs.add("4324234");
		waybillChangIDs.add("323");
		//waybillRfcService.writeOffWaybillChange(waybillChangIDs, "45464",
				//WaybillRfcConstants.WRITE_OFF_FAILURE);
	}

	@Test
	public void testReverseWaybillChange() {

		List<String> waybillChangIDs = new ArrayList<String>();
		waybillChangIDs.add("4324234");
		waybillChangIDs.add("323");
		//waybillRfcService.reverseWaybillChange(waybillChangIDs, "45464");
	}
 
	@Test
	public void testAccountService() {

		WaybillRfcForAccountServiceDto waybillRfcForAccountServiceDto = new WaybillRfcForAccountServiceDto();
		waybillRfcForAccountServiceDto.setApplyPerson("test111111");
		waybillRfcForAccountServiceDto.setApplyTime(new Date());
		waybillRfcForAccountServiceDto.setChangeContent("aaaaaaaaaa");
		waybillRfcForAccountServiceDto.setContactHandy("333");
		waybillRfcForAccountServiceDto.setContact("test2");
		waybillRfcForAccountServiceDto.setUnifieldCode("222");
		waybillRfcForAccountServiceDto.setWaybillNumber("111");
		//waybillRfcService.applyChangeOrder(waybillRfcForAccountServiceDto);
		
		WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition = new WaybillRfcForAccountServiceCondition();
		waybillRfcForAccountServiceCondition.setApplyPerson("test111111");
		//List<WaybillRfcForAccountServiceDto> list = waybillRfcService.queryChangeOrder(waybillRfcForAccountServiceCondition);
	}
}