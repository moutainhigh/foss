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
 * PROJECT NAME	: pkp-deliver-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/deliver/server/service/SignChangeServiceTest.java
 * 
 * FILE NAME        	: SignChangeServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.deliver.server.BaseTestCase;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArrivesheetDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignResultDto;

public class SignChangeServiceTest extends BaseTestCase{
	@Autowired
	private ISignChangeService signChangeService;

	@Test
	public void testQueryArriveChangeByWaybillNo() {
		signChangeService.queryArriveChangeByWaybillNo("551234123");
	}
	
	@Test
	public void testsearchReverseSignInfo() {
		RepaymentArrivesheetDto dto = signChangeService.searchReverseSignDedicatedInfo("50001819");
		System.out.println("--------------------------------");
	}
	@Test
	public void testsearchRepaymentArrivesheet() {
		RepaymentArrivesheetDto dto = signChangeService.searchRepaymentArrivesheet("50001819");
		System.out.println("+++++++++++++++++++++++++++++++");
	}
	
	@Test
	public void testagree() {
		UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		EmployeeEntity employee = new EmployeeEntity();
		// 设置用户信息
		employee.setEmpName("芳芳");
		employee.setEmpCode("666666");
		user.setEmployee(employee);
		// 设置部门信息
		dept.setName("北京石景山营业部");
		dept.setCode("W011305080202");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		

		signChangeService.agree("ae1da00c-ed47-4037-98c2-48b99bb12517", "test", currentInfo);
		System.out.println("--------------------------------");
	}
	
//	@Test
	public void testsaveChangeDedicated() {
		SignResultDto dto = new SignResultDto();
		List<SignRfcChangeDetailEntity> changeDetailentityList = new ArrayList<SignRfcChangeDetailEntity>();
		SignRfcChangeDetailEntity signRfcChangeDetailEntity = new SignRfcChangeDetailEntity();
		signRfcChangeDetailEntity.setRfcItemsCode("actualFreight");
		signRfcChangeDetailEntity.setAfterRfcinfo("130000");
		signRfcChangeDetailEntity.setRfcType("CHANGEDETAIL_TYPE_REPAYMENT");
		SignRfcChangeDetailEntity signRfcChangeDetailEntity1 = new SignRfcChangeDetailEntity();
		signRfcChangeDetailEntity1.setRfcItemsCode("codAmount");
		signRfcChangeDetailEntity1.setAfterRfcinfo("20000");
		signRfcChangeDetailEntity1.setRfcType("CHANGEDETAIL_TYPE_REPAYMENT");
		changeDetailentityList.add(signRfcChangeDetailEntity);
		changeDetailentityList.add(signRfcChangeDetailEntity1);
		dto.setChangeDetailentity(changeDetailentityList);
		
		SignRfcEntity signRfcEntity = new SignRfcEntity();
		signRfcEntity.setWaybillNo("112545545");
//		signRfcEntity.settSrvRepaymentId("7c1071d2-4074-47af-bf04-9a0776bca7be");
		dto.setSignRfcEntity(signRfcEntity);
		dto.settSrvRepaymentId("7c1071d2-4074-47af-bf04-9a0776bca7be");
		/*UserEntity user = new UserEntity();
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		CurrentInfo currentInfo = new CurrentInfo(user,dept);*/
		signChangeService.saveChangeDedicated(dto, null);
	}
	@Test
	public void testsearchReverseSignAirPartial(){
		RepaymentArrivesheetDto dto = signChangeService.searchReverseSignAirPartial("640936571");
		System.out.println("------");
	}
	@Test
	public void testsearchSignRfcList(){
		SignRfcEntity entity = new SignRfcEntity();
		entity.setDraftTimeStart(new Date("20130123"));
		entity.setDraftTimeEnd(new Date("20130123"));
		signChangeService.searchSignRfcList(entity,1,6,null);
		System.out.println("------");
	}
}