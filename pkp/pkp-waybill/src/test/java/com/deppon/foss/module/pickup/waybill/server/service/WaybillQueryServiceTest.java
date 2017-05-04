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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/service/WaybillQueryServiceTest.java
 * 
 * FILE NAME        	: WaybillQueryServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.inteface.domain.accounting.WayBillDetailInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.service.impl.LabeledGoodService;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.AppWayBillDetaillVo;
import com.deppon.foss.util.DateUtils;

/**
 * 运单查询服务测试
 * 
 * @author 026113-foss-linwensheng
 * 
 */

public class WaybillQueryServiceTest {

	IWaybillQueryService waybillQueryService;
	//货签Service
	ILabeledGoodService labeledGoodService;

	@Before
	public void setUp() throws Exception {
		waybillQueryService = (IWaybillQueryService) TestHelper.get()
				.getBeanByClass(WaybillQueryService.class);
		labeledGoodService = (ILabeledGoodService) TestHelper.get()
				.getBeanByClass(LabeledGoodService.class);

	}

	/**根据不同的查询条件返回运单单号集合
	 */
	//@Test
	public void testqueryWaybillListByComplexCondation1() {

		WaybillListByComplexCondationDto waybillListByComplexCondationDto = new WaybillListByComplexCondationDto();
	
		waybillListByComplexCondationDto.setStartWaybillNo("12345678");
		waybillListByComplexCondationDto.setEndWaybillNo("22345678");
		List<String> waybills=waybillQueryService
				.queryWaybillListByComplexCondation(waybillListByComplexCondationDto);
		for(String waybill:waybills)
		{
			System.out.println("运单："+waybill);
		}
	}
	
	/**
	 * 根据不同的查询条件返回运单单号集合
	 */
	//@Test
	public void testqueryWaybillListByComplexCondation2() {

		WaybillListByComplexCondationDto waybillListByComplexCondationDto = new WaybillListByComplexCondationDto();

		waybillListByComplexCondationDto.setStartBillTime(DateUtils
				.convert("2012-12-11 00:00:00"));
		waybillListByComplexCondationDto.setEndBilltime(DateUtils
				.convert("2012-12-11 23:55:55"));
		
		
		waybillListByComplexCondationDto.setReceiveOrgCode("GS00002");
		
		List<String> waybills=waybillQueryService
				.queryWaybillListByComplexCondation(waybillListByComplexCondationDto);
		
		for(String waybill:waybills)
		{
			System.out.println("运单："+waybill);
		}
	}
	
	
	
	
	
	/**
	 * 测试通过运单号集合查询未处理更改单
	 */
	//@Test
	public void testqueryLeaveChangeByWaybillNo() {

		waybillQueryService.queryLeaveChangeByWaybillNo("12345678");
	}
	
	//@Test
	public void testQuerySalesStatementByComplexCondation()
	{
		int start=1;
		int limit=100;
		WaybillListByComplexCondationDto dto=new WaybillListByComplexCondationDto();
		//dto.setWaybillNo("44441302");
		dto.setStartBillTime(DateUtils.convert("2013-06-07 00:00:00"));			
		dto.setEndBilltime(DateUtils.convert("2013-06-08 00:00:00"));
		//dto.setDeleteWaybillNos("'138176077','060711221'");
		Map<String,Object> list=waybillQueryService.querySalesStatementByComplexCondation(dto, start, limit);
		if(list!=null && list.size()>0){
			System.out.print("list.size=========================="+list.size());
			List<SalesStatementByComplexCondationResultDto> result=(List<SalesStatementByComplexCondationResultDto>)list.get("result");
			if(result.size()>0){
				System.out.print("result.size=========================="+result.size());
				SalesStatementByComplexCondationResultDto entity=result.get(0);
				if(entity!=null){
					System.out.println("代收货款=================================="+entity.getCodAmount());
					System.out.println("预付金额=================================="+entity.getPrePayAmount());
					System.out.println("到付金额=================================="+entity.getToPayAmount());
					
				}
			}
			CalcTotalFeeDto totalFeeDto=(CalcTotalFeeDto)list.get("totalFeeDto");
			if(totalFeeDto!=null){
				System.out.println("保价费2=================================="+totalFeeDto.getInsuranceFee());
				System.out.println("代收货款2=================================="+totalFeeDto.getCodAmountTotal());
				System.out.println("预付金额2=================================="+totalFeeDto.getPrePayAmountTotal());
				System.out.println("到付金额2=================================="+totalFeeDto.getToPayAmountTotal());
				System.out.println("收入总额2=================================="+totalFeeDto.getAmountTotal());
			}
		}		
	}
	
	//@Test
	public void queryWaybillInfo()
	{
		List<String> waybillList=new ArrayList();
		waybillList.add("12344589");
		List<WaybillInfoDto> result=waybillQueryService.queryWaybillInfo(waybillList);
		if(result!=null && result.size()>0){
			for(WaybillInfoDto dto:result){
				System.out.println("签收情况=================================="+dto.getSignSituation());
				System.out.println("签收时间=================================="+dto.getSignTime());
			}
		}
	}
	
	
	//@Test
	@SuppressWarnings("unchecked")
	public void testQueryWaybillInfos()
	{
		@SuppressWarnings("rawtypes")
		Map<String, Object> args = (Map<String, Object>) new HashMap();
		args.put("startDate", DateUtils
				.convert("2012-12-11 00:00:00"));
		args.put("endDate", DateUtils
				.convert("2012-12-11 23:55:55"));
		args.put("pageNum", 1);
		args.put("pageSize", 10);
		Map<String,Object> resultMap = waybillQueryService.queryWaybillInfos(args);
		print((List<CrmWaybillServiceDto>) resultMap.get("list"));
	}

	
	/**
	 * 官网接口
	 * @author WangQianJin
	 * @date 2013-6-3 下午2:44:54
	 */
	//@Test

	
	//@Test

	@SuppressWarnings("unchecked")
	public void testQueryWaybillInfosByStatus()
	{
		@SuppressWarnings("rawtypes")
		Map<String, Object> args = (Map<String, Object>) new HashMap();

//		args.put("startDate", DateUtils
//				.convert("2012-11-26 00:00:00"));
//		args.put("endDate", DateUtils
//				.convert("2013-05-28 23:55:55"));
		

//		args.put("startDate", DateUtils
//				.convert("2012-11-26 00:00:00"));
//		args.put("endDate", DateUtils
//				.convert("2013-05-28 23:55:55"));

		args.put("pageNum", 0);
		args.put("pageSize", 10);
		//args.put("waybillNumber", "910086041");

		//args.put("consigneeName","宋");
		args.put("linkmanId", "400194426");

		//args.put("consigneeName","宋");
		//args.put("userName", "239022");
		args.put("linkmanId", "400194426");

		//args.put("status", WaybillConstants.ACCOUNT_IN_TRANSIT);

		args.put("waybillNumber", "146804275");
		//args.put("userName", "wflixinli");
		

		args.put("waybillNumber", "146804275");
		Map<String,Object> resultMap = waybillQueryService.queryWaybillInfos(args);
		print((List<CrmWaybillServiceDto>) resultMap.get("list"));
	}
	
	

	
	/**
	 * 打印运单号
	 * @param list
	 */
	private void print(List<CrmWaybillServiceDto> list){
		List<WayBillDetailInfo> targetList = new ArrayList<WayBillDetailInfo>();
		WayBillDetailInfo info = null;
		for(CrmWaybillServiceDto dto : list){
			info = new WayBillDetailInfo();
			System.out.println("status:"+dto.getWaybillStatus());
			System.out.println("waybillNo:"+dto.getWaybillNo());
			System.out.println(dto.getConsigneeAddress());
			System.out.println(dto.getConsigneeMobile());
			System.out.println(dto.getConsignee());
			System.out.println(dto.getConsigneePhone());
			System.out.println(dto.getCubage());
			targetList.add(info);
		}

	}

	/**
	 * 根据运单号和流水号获取获取状态
	 * @author WangQianJin
	 * @date 2013-6-20 下午4:17:19
	 */
	//@Test
	public void getStautsByWaybillNoAndSerialNo()
	{
		String waybillNo="99492900";
		String serialNo="0001";
		String status=labeledGoodService.getStautsByWaybillNoAndSerialNo(waybillNo, serialNo);
		System.out.print(waybillNo+"次运单的流水号是否有效=========================="+status);
	}
	
	//@Test
	public void testwaybillQueryService_QueryLeaveChangeByWaybillNo()
	{
	 List<LeaveChangeByWaybillNoResultDto> 	leaveChangeByWaybillNoResultDtos=waybillQueryService.queryLeaveChangeByWaybillNo("146144746");
		
	 printw(leaveChangeByWaybillNoResultDtos);
	}
	
	/**
	 * 打印运单号
	 * @param list
	 */
	private void printw(List<LeaveChangeByWaybillNoResultDto> 	leaveChangeByWaybillNoResultDtos){
		List<WayBillDetailInfo> targetList = new ArrayList<WayBillDetailInfo>();
		WayBillDetailInfo info = null;
		for(LeaveChangeByWaybillNoResultDto dto : leaveChangeByWaybillNoResultDtos){
			info = new WayBillDetailInfo();
			System.out.println("waybillNo:"+dto.getWaybillNo()+"/n");
			//System.out.println("waybillNo:"+dto.getWaybillNo());
			System.out.println(dto.getDrafter()+"/n");
		
			System.out.println(dto.getNeedAcceptOrg());
		}
	}
	
	public void cc(List<String> A ,List<String> B ){
		
		List<String> otherList=new ArrayList<String>();
		for(String a:A){
			
			for(String b:B){
				
				a=b;
			}
			
		}
		
		
		
		
	}
	
	@Test
	public void testqueryWaybillInfoByWaybillNo() {
		String waybillNO="65121411";
		WaybillInfoByWaybillNoReultDto dto=waybillQueryService.queryWaybillInfoByWaybillNo(waybillNO);
		if(dto!=null){
			System.out.println("出发部门==============================="+dto.getStartOrgCode());
		}		
	}
	
	@Test
	public void testQueryAppWaybillInfos_exp(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("mobilePhone", "");
		map.put("type", "");		
		map.put("status", "");
		map.put("startDate","");
		map.put("endDate", "");
		map.put("pageNum", "");
		map.put("pageSize", "");
		map.put("keyWords", "");
		
		Map<String,Object> resultMap = waybillQueryService.queryAppWaybillInfosExp(map);
		int count = (Integer) resultMap.get("count");
		System.out.println("返回的记录数："+count);
		List<AppWayBillDetaillVo> lists = (List<AppWayBillDetaillVo>) resultMap.get("list");
		for(AppWayBillDetaillVo appWayBillDetaillVo : lists){
			System.out.println(appWayBillDetaillVo.getWaybillNum()+" "+appWayBillDetaillVo.getWaybillType()+" "+appWayBillDetaillVo.getWayBillState());
			
		}
		
	}
}