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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/writeoff/test/service/StatementWriteoffServiceTest.java
 * 
 * FILE NAME        	: StatementWriteoffServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.test.BaseTestCase;
import com.deppon.foss.module.settlement.writeoff.test.CommonTestUtil;

public class StatementWriteoffServiceTest extends BaseTestCase {

	@Autowired
	private IStatementWriteoffService statementWriteoffService;

	private StatementOfAccountMakeQueryResultDto getStatementDto() {
		StatementOfAccountMakeQueryResultDto statementDto = new StatementOfAccountMakeQueryResultDto();

		List<StatementOfAccountEntity> sList = new ArrayList<StatementOfAccountEntity>();

		StatementOfAccountEntity entity1 = new StatementOfAccountEntity();
		entity1.setStatementBillNo("DZ00000221");
		sList.add(entity1);

		StatementOfAccountEntity entity2 = new StatementOfAccountEntity();
		entity2.setStatementBillNo("DZ00000321");
		sList.add(entity2);

		StatementOfAccountEntity entity3 = new StatementOfAccountEntity();
		entity3.setStatementBillNo("DZ00000273");
		sList.add(entity3);

		statementDto.setStatementOfAccountList(sList);

		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("testUserCode");
		employee.setEmpName("testUserName");
		user.setEmployee(employee);
		statementDto.setUser(user);

		return statementDto;
	}

	@Test
	@Rollback(true)
	public void testWriteoffStatement() {
		try {
			logger.info("do write off depostie and receivable...");

			StatementOfAccountMakeQueryResultDto statementDto = this
					.getStatementDto();
			statementWriteoffService.writeoffStatement(statementDto,
					CommonTestUtil.getCurrentInfo());

			logger.info("write off depostie and receivable success...");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.info("testing...");
		}
	}

}
