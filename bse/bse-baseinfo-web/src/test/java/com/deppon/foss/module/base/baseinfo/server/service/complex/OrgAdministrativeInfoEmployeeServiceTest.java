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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/complex/OrgAdministrativeInfoEmployeeServiceTest.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoEmployeeServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.complex;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoEmployeeService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


public class OrgAdministrativeInfoEmployeeServiceTest {
    @Test
    public void getPorter() {
	//编造测试数据到数据库
	String str1= System.currentTimeMillis()+(++count)+"";
	String str2= System.currentTimeMillis()+(++count)+"";
	insertData(str1,str2);
	
	// 组装查询条件
	OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=new OrgAdministrativeInfoEntity();
	orgAdministrativeInfoEntity.setCode(str1);
	EmployeeEntity employeeEntity = new EmployeeEntity();
	employeeEntity.setEmpCode(str2);
	
	
	List<OrgAdministrativeInfoEmployeeDto> result;
	result=orgAdministrativeInfoEmployeeService.getPorter(orgAdministrativeInfoEntity, employeeEntity,0,1);
	Assert.assertTrue(!CollectionUtils.isEmpty(result));

    }
    
    @Test
    public void getPorterCount() {
	//编造测试数据到数据库
	String str1= System.currentTimeMillis()+(++count)+"";
	String str2= System.currentTimeMillis()+(++count)+"";
	insertData(str1,str2);
	
	// 组装查询条件
	OrgAdministrativeInfoEntity orgAdministrativeInfoEntity=new OrgAdministrativeInfoEntity();
	orgAdministrativeInfoEntity.setCode(str1);
	EmployeeEntity employeeEntity = new EmployeeEntity();
	employeeEntity.setEmpCode(str2);
	
	
	long result;
	result=orgAdministrativeInfoEmployeeService.getPorterCount(orgAdministrativeInfoEntity, employeeEntity);
	Assert.assertTrue(result != 0);

    }
    
    
    
    /**
     * 下面是测试用的工具
     */

    static int count=0;
    private JdbcTemplate jdbc;
    private IOrgAdministrativeInfoEmployeeService orgAdministrativeInfoEmployeeService;
    
    private void insertData(String str1,String str2){
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, active) " +
		"values('"+str1+"', '"+str1+"', '"+str1+"','"+str1+"', '"+str1+"','"+str2+"', 'Y')");
	jdbc.execute("insert into t_bas_employee (id, EMP_CODE, EMP_NAME, TITLE, UNIFIELD_CODE, active) " +
		"values('"+str2+"', '"+str2+"', '"+str2+"', '"+str2+"','"+str2+"', 'Y')");
    }
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	//jdbc.execute("delete from t_bas_storage");
	orgAdministrativeInfoEmployeeService = (IOrgAdministrativeInfoEmployeeService) SpringTestHelper.get().getBeanByClass(OrgAdministrativeInfoEmployeeService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }
}
