/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/AirlinesAccountServiceTest.java
 * 
 * FILE NAME        	: AirlinesAccountServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressSaleDepartmentResultDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;

public class ExpressPartSalesDeptServiceTest {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;

	private IExpressPartSalesDeptService expressPartSalesDeptService;

	private static final Logger log = Logger
			.getLogger(ExpressPartSalesDeptServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		expressPartSalesDeptService = (IExpressPartSalesDeptService) SpringTestHelper
				.get().getBeanByClass(ExpressPartSalesDeptService.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}

	/**
	 * 根据营业部编码和时间查询快递点部信息(可以查询无效信息)
	 * 
	 * @author foss-zhangjiheng
	 * @date 2013-7-25 上午10:06:34
	 * @param expressPartSalesDeptQueryDto
	 * @return
	 */
	@Ignore
	@Test
	public void testQueryExpressPartSalesDeptBySalesCodeAndTime() {
		ExpressPartSalesDeptResultDto dto = new ExpressPartSalesDeptResultDto();
//		dto = expressPartSalesDeptService
//				.queryExpressPartSalesDeptBySalesCodeAndTime("W00001",null);
		Assert.assertNull(dto);
	}
	
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系总条数
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午2:42:51
	 * @param expressPartSalesDeptQueryDto
	 */
	@Ignore
	@Test
	public void testqueryOneExpressCityByCondition(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setExpressPartCode("W01130500102");
		dto.setSalesDeptCode("W011305080605");
		
		long count = expressPartSalesDeptService.queryTotalByCondition(dto);
		Assert.assertEquals(count, 1);
	}
	
	/**
	 * 根据快递点部名称查询快递点部营业部映射关系
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午2:47:05
	 */
	@Ignore
	@Test
	public void testQueryExpressPartSalesDeptByCondition(){
		
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setExpressPartCode("W01130500102");
		dto.setSalesDeptCode("W011305080605");
		
		List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptService.queryExpressPartSalesDeptByCondition(dto,0,10);
		Assert.assertEquals(rtnList.size(), 1);
	}
	
	/**
	 * 根据快递点部编码查询快递点部营业部映射关系
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午2:51:58
	 */
	@Ignore
	@Test
	public void testQueryExpressPartSalesDeptByExpressPartCode(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setExpressPartCode("W01130500102");
		
		List<ExpressPartSalesDeptResultDto> rtnList = expressPartSalesDeptService.queryExpressPartSalesDeptByExpressPartCode(dto);
		Assert.assertNotNull(rtnList);
	}
	
	/**
	 * 根据营业部编码列表查询营业部扩展dto
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午2:59:41
	 */
	@Ignore
	@Test
	public void testQuerySaleDepartmentResultDtoBySalesDeptCodeList(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		List<String> selectedCodeList = new ArrayList<String>();
		selectedCodeList.add("W011305080605");
		dto.setSelectedCodeList(selectedCodeList);
		
		List<ExpressSaleDepartmentResultDto> rtnList = expressPartSalesDeptService.querySaleDepartmentResultDtoBySalesDeptCodeList(dto);
		Assert.assertNotNull(rtnList);
	}
	
	/**
	 * 根据营业部条件查部营业部信息,多条件模糊查询
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:01:34
	 */
	@Ignore
	@Test
	public void testQuerySaleDepartmentResultDtoByCondition(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		List<String> selectedCodeList = new ArrayList<String>();
		selectedCodeList.add("W011305080605");
		dto.setSelectedCodeList(selectedCodeList);
		
		List<ExpressSaleDepartmentResultDto> rtnList = expressPartSalesDeptService.querySaleDepartmentResultDtoByCondition(dto);
		Assert.assertNotNull(rtnList);
	}
	
	/**
	 * 修改快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:01:34
	 */
	@Ignore
	@Test
	public void testUpdateExpressPartSalesDept(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setExpressPartCode("W01130500102");
		dto.setSalesDeptCode("W011305080605");
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressPartSalesDeptService.updateExpressPartSalesDept(dto, currentInfo);
	}
	
	/**
	 * 新增快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:01:34
	 */
	@Ignore
	@Test
	public void testAddExpressPartSalesDept(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setExpressPartCode("W01130500102");
		dto.setSalesDeptCode("W011305080605");
	
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressPartSalesDeptService.addExpressPartSalesDept(dto, currentInfo);
	}
	
	/**
	 * 删除快递点部营业部映射信息
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:01:34
	 */
	@Ignore
	@Test
	public void testDeleteExpressPartSalesDept(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setSelectedIds("12312312312312");
		
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		
		expressPartSalesDeptService.deleteExpressPartSalesDept(dto, currentInfo);
	}
	
	/**
	 * 根据快递点部编码获取快递大区信息
	 * @author foss-qiaolifeng
	 * @date 2013-9-6 下午3:01:34
	 */
	@Ignore
	@Test
	public void testGetExpressPartBigRegionByPartCode(){
		ExpressPartSalesDeptQueryDto dto = new ExpressPartSalesDeptQueryDto();
		dto.setExpressPartCode("W01130500102");
	
		OrgAdministrativeInfoEntity entity = expressPartSalesDeptService.getExpressPartBigRegionByPartCode(dto);
		Assert.assertNotNull(entity);
	}
	
}
