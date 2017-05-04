/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/writeoff/test/service/StatementReceiptServiceTest.java
 * 
 * FILE NAME        	: StatementReceiptServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IPdaStatementManageSerive;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.test.BaseTestCase;

public class PdaStatementTest extends BaseTestCase {

	@Autowired
	private IPdaStatementManageSerive pdaStatementManageSerive;

	// 查询对账单
	@Test
	@Rollback(true)
	public void testQueryStatementList() {
		try {
			String dz = "400026159";
			String empCode = "146128";
			CommonQueryParamDto dto = new CommonQueryParamDto();
			List<String> numbers = new ArrayList<String>();
			numbers.add(dz);
			dto.setNumbers(numbers);
			dto.setEmpCode(empCode);
			//dto.setOrgCode("W011302020515");
			PdaStatementManageDto d = pdaStatementManageSerive
					.queryStatementByNo(dto);
			/*System.out.println("对账单信息："
					+ d.getStatementEntitys().get(0).getStatementNo() + ":"
					+ d.getStatementEntitys().get(0).getUnVerifyAmount()+":"+d.getStatementEntitys().get(0).getVersion());*/
			//System.out.println(d.getStatementEntitys().get(0));
		} catch (SettlementException e) {
			System.out.println(e.getErrorCode());
		}
	}

	// 核销
	@Test
	@Rollback(false)
	public void testRepayment() {
		try {
			List<PdaStatementManageDto> dtos = new ArrayList<PdaStatementManageDto>();
			
			PdaStatementManageDto dto = new PdaStatementManageDto();
			// 封装还款的参数
			dto.setCustomerName("周禹安");// 客户名称
			dto.setCustomerCode("123456789");// 客户编码
			String[] s = new String[] { "DZ002599939" };// 对账单号
			String[] v = new String[] { "6" };// 版本号
			dto.setStatementBillNos(s);
			dto.setVersionNos(v);
			dto.setRemittanceNumber("12323232");// 交易流水号
			dto.setRepaymentAmount(BigDecimal.valueOf(1));// 还款金额
			dto.setRemittanceName("周禹安");// 汇款人
			dto.setRepaymentType("CD");

			dto.setEmpCode("086299");
			dto.setEmpName("石安生");
			dto.setCurrentDeptName("上海虹桥营业区");
			dto.setCurrentDeptCode("W040621");
			
			dtos.add(dto);
			
			List<PdaStatementManageDto> ds = pdaStatementManageSerive.statementRepayment(dtos);
			System.out.println("交易流水号："+ds.get(0).getRemittanceNumber());
			System.out.println("未核销完的数据："+ds.get(0).getRemittanceNumber());
			/*System.out.println("错误单号集合:"+ds.get(0).getStatementNos().get(0));
			System.out.println("错误单号Map信息:"+ds.get(0).getMsgMap().get("DZ002599939"));*/
		} catch (SettlementException e) {
			System.out.println(e.getErrorCode());
		}
	}
}
