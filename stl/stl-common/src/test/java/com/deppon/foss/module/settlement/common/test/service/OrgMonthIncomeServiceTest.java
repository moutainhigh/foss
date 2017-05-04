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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/OrgMonthIncomeServiceTest.java
 * 
 * FILE NAME        	: OrgMonthIncomeServiceTest.java
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

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IOrgMonthIncomeService;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgMonthIncomeEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 部门收入记录测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-18 下午4:17:01
 * @since
 * @version
 */
public class OrgMonthIncomeServiceTest extends BaseTestCase {
	
	@Autowired
	private IOrgMonthIncomeService orgMonthIncomeService;
	
	/**
	 * 获取一条营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:14:04
	 * @return
	 */
	
	private OrgMonthIncomeEntity getOrgMonthIncomeEntity(){
		OrgMonthIncomeEntity entity=new OrgMonthIncomeEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setOrgCode("123456");
		entity.setCtMonth(DateUtils.convert("2013-02", "yyyy-MM"));
		
		entity.setIncomeAmount(new BigDecimal("100000000"));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	/**
	 * 获取营业部收入记录
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:14:21
	 * @param orgCode
	 * @param ctMonth
	 * @param amount
	 * @return
	 */
	private OrgMonthIncomeEntity getOrgMonthIncomeEntity(String orgCode,
			Date ctMonth,
			BigDecimal amount){
		OrgMonthIncomeEntity entity=new OrgMonthIncomeEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setOrgCode(orgCode);//统计部门编码
		entity.setCtMonth(ctMonth);//统计上月份
		entity.setIncomeAmount(amount);//统计金额数
		entity.setCreateTime(new Date());//统计日期
		return entity;
	}
	
	/**
	 * 新增一条营业部收入记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:04:18
	 */
	@Test
	@Rollback(true)
	public void testAddOrgMonthIncome(){
		this.orgMonthIncomeService.addOrgMonthIncome(
				this.getOrgMonthIncomeEntity());
	}
	
	/**
	 * 新增批量营业部收入记录
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:13:06
	 */
	@Test
	@Rollback(true)
	public void testBatchAddOrgMonthIncome(){
		List<OrgMonthIncomeEntity> list=new ArrayList<OrgMonthIncomeEntity>();
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2013-01", "yyyy-MM"), new BigDecimal("100000000")));
		
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2012-12", "yyyy-MM"), new BigDecimal("800000")));
		
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2012-11", "yyyy-MM"), new BigDecimal("110000000")));
		this.orgMonthIncomeService.batchAddOrgMonthIncome(list);
	}
	
	/**
	 * 根据传入的营业部编码集合和日期，
	 * 查询日期最近三个月内营业部的最大月收入金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午4:17:16
	 */
	@Test
	@Rollback(true)
	public void testQueryMaxMonthIncomeAmountByOrgCodes(){
		List<OrgMonthIncomeEntity> list=new ArrayList<OrgMonthIncomeEntity>();
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2013-01", "yyyy-MM"), new BigDecimal("110000000")));
		
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2012-12", "yyyy-MM"), new BigDecimal("800000")));
		
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2012-11", "yyyy-MM"), new BigDecimal("110000000")));
		
		list.add(this.getOrgMonthIncomeEntity("123456", 
				DateUtils.convert("2012-10", "yyyy-MM"), new BigDecimal("120000000")));
		this.orgMonthIncomeService.batchAddOrgMonthIncome(list);
		
		List<String> orgCodes=new ArrayList<String>();
		orgCodes.add("123456");
		Date date=new Date();
		
		List<OrgMonthIncomeEntity> listTwo=this.orgMonthIncomeService.queryMaxMonthIncomeAmountByOrgCodes(orgCodes, date);
		Assert.assertTrue(CollectionUtils.isNotEmpty(listTwo));
	}
	
	/**
	 * 每个月初，定时统计上月营业部的收入情况，进行记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-18 下午5:26:25
	 */
	@Test
	public void testSumStilBillToOrgMonthIncome(){
		this.orgMonthIncomeService.sumStilBillToOrgMonthIncome();
	}
	
}
