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
 * PROJECT NAME	: stl-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/StatementOfAccountDServiceTest.java
 * 
 * FILE NAME        	: StatementOfAccountDServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单明细公共服务测试类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-26 下午1:54:59
 */
public class StatementOfAccountDServiceTest extends BaseTestCase {

	@Autowired
	private IStatementOfAccountService statementOfAccountService;

	@Autowired
	private IStatementOfAccountDService statementOfAccountDService;

	/**
	 * 新增对账单明细信息测试
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:04
	 */

	private void addStatementOfAccountDEntity() {
		// 新增应收明细
		StatementOfAccountDEntity soadYSEntity = new StatementOfAccountDEntity();
		soadYSEntity.setId(UUIDUtils.getUUID());
		soadYSEntity.setStatementBillNo("DZ1000001");
		soadYSEntity.setSourceBillNo("YS10000001");
		soadYSEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE);
		soadYSEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadYSEntity.setAmount(new BigDecimal(10000));
		soadYSEntity.setVerifyAmount(new BigDecimal(2000));
		soadYSEntity.setUnverifyAmount(new BigDecimal(8000));
		soadYSEntity.setOrgCode("test100001");
		soadYSEntity.setOrgName("test10001");
		soadYSEntity.setCustomerCode("test123456");
		soadYSEntity.setCustomerName("test123456");
		soadYSEntity.setAccountDate(new Date());
		soadYSEntity.setBusinessDate(new Date());
		soadYSEntity.setCreateDate(new Date());
		soadYSEntity.setCreateTime(new Date());
		soadYSEntity.setIsDelete(FossConstants.NO);
		int syRows = statementOfAccountDService.add(soadYSEntity);
		Assert.assertEquals(1, syRows);
		// 新增应付明细
		StatementOfAccountDEntity soadYFEntity = new StatementOfAccountDEntity();
		soadYFEntity.setId(UUIDUtils.getUUID());
		soadYFEntity.setStatementBillNo("DZ1000001");
		soadYFEntity.setSourceBillNo("YF10000001");
		soadYFEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE);
		soadYFEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadYFEntity.setAmount(new BigDecimal("10000"));
		soadYFEntity.setVerifyAmount(new BigDecimal("2000"));
		soadYFEntity.setUnverifyAmount(new BigDecimal("8000"));
		soadYFEntity.setOrgCode("test100001");
		soadYFEntity.setOrgName("test10001");
		soadYFEntity.setCustomerCode("test123456");
		soadYFEntity.setCustomerName("test123456");
		soadYFEntity.setAccountDate(new Date());
		soadYFEntity.setBusinessDate(new Date());
		soadYFEntity.setCreateDate(new Date());
		soadYFEntity.setCreateTime(new Date());
		soadYFEntity.setIsDelete(FossConstants.NO);
		int yfRows = statementOfAccountDService.add(soadYFEntity);
		Assert.assertEquals(1, yfRows);
		// 新增预收明细
		StatementOfAccountDEntity soadUSEntity = new StatementOfAccountDEntity();
		soadUSEntity.setId(UUIDUtils.getUUID());
		soadUSEntity.setStatementBillNo("DZ1000001");
		soadUSEntity.setSourceBillNo("US10000001");
		soadUSEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED);
		soadUSEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadUSEntity.setAmount(new BigDecimal(10000));
		soadUSEntity.setVerifyAmount(new BigDecimal(2000));
		soadUSEntity.setUnverifyAmount(new BigDecimal(8000));
		soadUSEntity.setOrgCode("test100001");
		soadUSEntity.setOrgName("test10001");
		soadUSEntity.setCustomerCode("test123456");
		soadUSEntity.setCustomerName("test123456");
		soadUSEntity.setAccountDate(new Date());
		soadUSEntity.setBusinessDate(new Date());
		soadUSEntity.setCreateDate(new Date());
		soadUSEntity.setCreateTime(new Date());
		soadUSEntity.setIsDelete(FossConstants.NO);
		int usRows = statementOfAccountDService.add(soadUSEntity);
		Assert.assertEquals(1, usRows);
		// 新增预付明细
		StatementOfAccountDEntity soadUFEntity = new StatementOfAccountDEntity();
		soadUFEntity.setId(UUIDUtils.getUUID());
		soadUFEntity.setStatementBillNo("DZ1000001");
		soadUFEntity.setSourceBillNo("UF10000001");
		soadUFEntity
				.setBillParentType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		soadUFEntity
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE);
		soadUFEntity.setAmount(new BigDecimal("10000"));
		soadUFEntity.setVerifyAmount(new BigDecimal("2000"));
		soadUFEntity.setUnverifyAmount(new BigDecimal("8000"));
		soadUFEntity.setOrgCode("test100001");
		soadUFEntity.setOrgName("test10001");
		soadUFEntity.setCustomerCode("test123456");
		soadUFEntity.setCustomerName("test123456");
		soadUFEntity.setAccountDate(new Date());
		soadUFEntity.setBusinessDate(new Date());
		soadUFEntity.setCreateDate(new Date());
		soadUFEntity.setCreateTime(new Date());
		soadUFEntity.setIsDelete(FossConstants.NO);
		int ufRows = statementOfAccountDService.add(soadUFEntity);
		Assert.assertEquals(1, ufRows);
	}

	/**
	 * 新增对账单信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:27
	 */
	public StatementOfAccountEntity addStatementAccountEntity() {
		StatementOfAccountEntity soaEntity = new StatementOfAccountEntity();
		soaEntity.setId(UUIDUtils.getUUID());
		soaEntity.setStatementBillNo("DZ1000001");
		soaEntity.setCreateOrgCode("test10001");
		soaEntity.setCreateOrgName("test10001");
		soaEntity.setCustomerCode("test100001");
		soaEntity.setBillType("test客户对账单");
		soaEntity.setBeginPeriodAmount(BigDecimal.ZERO);
		soaEntity.setBeginPeriodAdvAmount(BigDecimal.ZERO);
		soaEntity.setBeginPeriodPayAmount(BigDecimal.ZERO);
		soaEntity.setBeginPeriodPreAmount(BigDecimal.ZERO);
		soaEntity.setBeginPeriodRecAmount(BigDecimal.ZERO);
		soaEntity.setPeriodAmount(BigDecimal.ZERO);
		soaEntity.setPeriodAdvAmount(BigDecimal.ZERO);
		soaEntity.setPeriodPayAmount(BigDecimal.ZERO);
		soaEntity.setPeriodPreAmount(BigDecimal.ZERO);
		soaEntity.setPeriodRecAmount(BigDecimal.ZERO);
		soaEntity.setPeriodUnverifyAdvAmount(BigDecimal.ZERO);
		soaEntity.setPeriodUnverifyPayAmount(BigDecimal.ZERO);
		soaEntity.setPeriodUnverifyPreAmount(BigDecimal.ZERO);
		soaEntity.setPeriodUnverifyRecAmount(BigDecimal.ZERO);
		soaEntity.setUnifiedCode("test0001");
		soaEntity.setBusinessDate(new Date());
		soaEntity.setCreateTime(new Date());
		soaEntity.setVersionNo((short) 1);
		soaEntity
				.setConfirmStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);
		int rows = statementOfAccountService.add(soaEntity);
		Assert.assertEquals(1, rows);
		return soaEntity;
	}

	/**
	 * 测试根据对账单明细来源单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:27
	 */
	@Test
	public void testQueryByResourceNos() {
		// 新增对账单明细
		addStatementOfAccountDEntity();
		List<String> list = new ArrayList<String>();
		list.add("YS10000001");
		list.add("YF10000001");
		List<StatementOfAccountDEntity> soadList = statementOfAccountDService
				.queryByResourceNos(list);
		Assert.assertEquals(2, soadList.size());
	}

	/**
	 * 测试删除对账单明细时修改明细信息（逻辑删除）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 下午3:19:27
	 */
	@Test
	public void testUpdateStatementDetailForDeleteDetail() {
		// 新增对账单明细
		addStatementOfAccountDEntity();
		List<String> list = new ArrayList<String>();
		list.add("YS10000001");
		list.add("YF10000001");
		List<StatementOfAccountDEntity> soadList = statementOfAccountDService
				.queryByResourceNos(list);
		for (StatementOfAccountDEntity soadEntity : soadList) {
			try {
				statementOfAccountDService
						.updateStatementDetailForDeleteDetail(soadEntity);
				// 对账单明细金额发生变法修改对账单明细信息
				statementOfAccountDService
						.updateStatementDetailByAmount(soadEntity);
			} catch (BusinessException e) {
				Assert.assertNotNull(e);
			}
		}
		// 根据对账单号查询对账单明细
		List<StatementOfAccountDEntity> soadList2 = statementOfAccountDService
				.queryByStatementBillNo("DZ1000001");
		Assert.assertEquals(4, soadList2.size());
		// 根据对账单号、来源单号、及删除标记查询对账单明细单据
		StatementOfAccountDEntity entity = statementOfAccountDService
				.queryByResourceAndStatementNo("YS10000001", "DZ1000001",
						FossConstants.NO);
		Assert.assertNotNull(entity);

	}

	/**
	 * 
	 * 根据对账单号分页查询对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 下午5:44:09
	 */
	@Test
	public void testQueryByStatementBillNo() {
		// 新增对账及对账单明细
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity();
		int pageNo = 0;
		int pageSize = 3;
		List<StatementOfAccountDEntity> soadList = statementOfAccountDService
				.queryByStatementBillNo(soaEntity.getStatementBillNo(), pageNo,
						pageSize);
		Assert.assertEquals(3, soadList.size());
	}

	/**
	 * 
	 * 根据对账单号查询对账单明细总条数
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 下午5:44:09
	 */
	@Test
	public void testCountQueryByStatementBillNo() {
		// 新增对账及对账单明细
		StatementOfAccountEntity soaEntity = addStatementAccountEntity();
		addStatementOfAccountDEntity();
		StatementOfAccountDetailCountDto dto = statementOfAccountDService
				.queryCountByStatementBillNo(soaEntity.getStatementBillNo());
		Assert.assertEquals(4, dto.getCountNum());
	}

}
