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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/CommonOrgDaoTest.java
 * 
 * FILE NAME        	: CommonOrgDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

public class CommonOrgDaoTest {
	@Autowired
	ICommonOrgDao commonOrgDao;
    
//    private JdbcTemplate jdbc;
    @Before
    public void setUp() throws Exception {
//	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	commonOrgDao = SpringTestHelper.get().getBeanByInterface(ICommonOrgDao.class);
    }
	@Test
	public void testQueryOrgByCondition(){
		OrgSearchCondition con = new OrgSearchCondition();
		con.setName("南京");
		con.setDivision("Y");
		List<CommonOrgEntity> list = commonOrgDao.queryOrgByCondition(con);
		Assert.assertTrue(list.size()>=0);
		
		con = new OrgSearchCondition();
		con.setDivision("Y");
		list = commonOrgDao.queryOrgByCondition(con);
		Assert.assertTrue(list.size()>=0);
		
		con = new OrgSearchCondition();
		List<String> types = new ArrayList<String>();
		con.setName("南京");
		types.add("RG");
		types.add("OTH");
		con.setTypes(types);
		list = commonOrgDao.queryOrgByCondition(con);
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
		con.setExistsSql("select 1 from dual");
		con.setStart(0);
		con.setTransDepartment("Y");
		con.setTransferCenter("Y");
		con.setTransTeam("Y");
		con.setType("ORG");
		
		list = commonOrgDao.queryOrgByCondition(con);
		Assert.assertTrue(list.size()>=0);
	}
	@Test
	public void testCountOrgByCondition(){
		OrgSearchCondition con = new OrgSearchCondition();
		con.setName("南京");
		long count = commonOrgDao.countOrgByCondition(con);
		Assert.assertTrue(count>=0);
		
		con = new OrgSearchCondition();
		con.setName("南京");
		con.setDivision("Y");
		count = commonOrgDao.countOrgByCondition(con);
		Assert.assertTrue(count>=0);
	}
}
