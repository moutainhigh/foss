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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/complex/LoadAndUnloadEfficiencyVehicleComplexServiceTest.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyVehicleComplexServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.complex;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.complex.LoadAndUnloadEfficiencyVehicleDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

//@Ignore
public class LoadAndUnloadEfficiencyVehicleComplexServiceTest {
    
    /**
     * 根据车辆的车牌号，部门编码，查询装卸车标准（卸一车需要多长时间）
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:49:37
     */
    @Test
    public void gainLoadAndUnloadEfficiencyVehicle() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+a+"', '"+a+"', '"+a+"','"+a+"', '"+a+"','"+a+"', '"+b+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, PARENT_ORG_UNIFIED_CODE, active) " +
		"values('"+b+"', '"+b+"', '"+b+"','"+b+"', '"+b+"','"+b+"','"+c+"', 'Y')");
	jdbc.execute("insert into t_bas_org (id, CODE, NAME, ARRANGE_OUTFIELD, ARRANGE_BIZ_TYPE, UNIFIED_CODE, TRANS_DEPARTMENT, active) " +
		"values('"+c+"', '"+c+"', '"+c+"','"+c+"', '"+c+"','"+c+"', 'Y', 'Y')");

	List<OrgAdministrativeInfoEntity> entityResults;
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(a,false);
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(b,true);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(b,false);
	Assert.assertTrue(!CollectionUtils.isEmpty(entityResults));
	entityResults=orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpDown(c,true);
	Assert.assertTrue(CollectionUtils.isEmpty(entityResults));


	deleteById(a);
	deleteById(b);
	deleteById(c);
    }    
  
    
    
    
    
    /**
     * 下面是测试用的工具
     */
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_LOAD_UNLOAD_STD_VEHICLE where id = '" + id + "'");
	
    }

    static int count=0;
    private JdbcTemplate jdbc;
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    
    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	// jdbc.execute("delete from t_bas_storage");
	orgAdministrativeInfoComplexService = (IOrgAdministrativeInfoComplexService) SpringTestHelper
		.get().getBeanByClass(OrgAdministrativeInfoComplexService.class);
    }
    
    @After
    public void teardown() {
//	jdbc.execute("delete from t_bas_storage");
    }
}
