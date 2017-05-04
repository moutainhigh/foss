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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/CommonOrgServiceTest.java
 * 
 * FILE NAME        	: CommonOrgServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrgVo;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

public class CommonOrgServiceTest {
	@Autowired
	ICommonOrgService commonOrgService;
    
//    private JdbcTemplate jdbc;
    @Before
    public void setUp() throws Exception {
//	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
    	commonOrgService = SpringTestHelper.get().getBeanByInterface(ICommonOrgService.class);
    }
	@Test
	public void testQueryOrgByCondition(){
		OrgSearchCondition con = new OrgSearchCondition();
		con.setName("南京");
		con.setDivision("Y");
		CommonOrgVo vo = new CommonOrgVo();
		BeanUtils.copyProperties(con, vo);
		List<CommonOrgEntity> list = commonOrgService.searchOrgByCondition(vo);
		Assert.assertTrue(list.size()>=0);
		
		con = new OrgSearchCondition();
		con.setName("南京");
		con.setDivision("Y");
		con.setCode("1353");
		vo = new CommonOrgVo();
		BeanUtils.copyProperties(con, vo);
		list = commonOrgService.searchOrgByCondition(vo);
		Assert.assertTrue(list.size()>=0);
		
		con.setActive("Y");
		con.setAirDispatch("Y");
		con.setBigRegion("Y");
		con.setBillingGroup("Y");
		con.setCode("1354444455");
		con.setDispatchTeam("Y");
		con.setDivision("Y");
		con.setDoAirDispatch("Y");
		con.setIsArrangeGoods("Y");
		con.setIsDeliverSchedule("Y");
		con.setIsEntityFinance("Y");
		con.setLimit(10);
		con.setName("南京");
		con.setPinYin("nanjin");
		con.setSalesDepartment("Y");
		con.setSmallRegion("Y");
		con.setStart(0);
		con.setTransDepartment("Y");
		con.setTransferCenter("Y");
		con.setTransTeam("Y");
		con.setType("Y");
		vo = new CommonOrgVo();
		BeanUtils.copyProperties(con, vo);
		list = commonOrgService.searchOrgByCondition(vo);
		Assert.assertTrue(list.size()>=0);
	}
	@Test
	public void testCountOrgByCondition(){
		OrgSearchCondition con = new OrgSearchCondition();
		CommonOrgVo vo = new CommonOrgVo();
		BeanUtils.copyProperties(con, vo);
		con.setName("南京");
		Long count = commonOrgService.countOrgByCondition(vo);
		Assert.assertTrue(count>=0);
		
		con = new OrgSearchCondition();
		con.setName("南京");
		vo = new CommonOrgVo();
		BeanUtils.copyProperties(con, vo);
		count = commonOrgService.countOrgByCondition(vo);
		Assert.assertTrue(count>=0);
	}
}
