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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/org/OrgAdministrativeInfoServiceTest.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.org;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;



/**
 * 行政组织-测试类
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:53:20 AM
 * @version 1.0
 */
@Ignore
public class OrgAdministrativeInfoServiceTest {
    /**
     * 测试新增
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:11
     */
    @Test
    public void addOrgAdministrativeInfo() {
	OrgAdministrativeInfoEntity entity = getEntity();
	
	try{
	    entity = orgAdministrativeInfoService
		    .addOrgAdministrativeInfo(entity);
	    Assert.assertNotNull(entity);

	}finally{
	    deleteById(entity.getId());
	}
    }
    
    /**
     * 测试作废
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    
    @Test
    public void deleteOrgAdministrativeInfo() {
	OrgAdministrativeInfoEntity entity = getEntity();
	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
	entity = orgAdministrativeInfoService.deleteOrgAdministrativeInfo(entity);
	Assert.assertNotNull(entity);
	
	deleteById(entity.getId());
    }    
    
    /**
     * 测试批量作废
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    
    @Test
    public void deleteOrgAdministrativeInfoMore() {
	OrgAdministrativeInfoEntity entity = getEntity();
	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
//	entity = orgAdministrativeInfoService.deleteOrgAdministrativeInfoMore(entity
//		.getCode().split(SymbolConstants.EN_COMMA), entity
//		.getCreateUser());
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }  
    
    /**
     * 测试更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void updateOrgAdministrativeInfo() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
	
	OrgAdministrativeInfoEntity entityResult = null; 
	try{
	    entityResult = orgAdministrativeInfoService
		    .updateOrgAdministrativeInfo(entity, false);
	    Assert.assertNotNull(entity);
	}finally{
	    deleteById(entity.getId());
	    if(entityResult!=null){
		deleteById(entity.getId());
	    }
	}
    }
    
    
    
    /**
     * 以下全为查询
     */  
    
    /**
     * 测试
     * 精确查询 
     * 通过 CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoByCode() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
	
	try{
	    entity = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getCode());
	    Assert.assertNotNull(entity);

	}finally{
	    deleteById(entity.getId());
	}
    }    
    
    /**
     * 测试
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoBatchByCode() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);
	
	List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoService.queryOrgAdministrativeInfoBatchByCode(entity.getCode().split(SymbolConstants.EN_COMMA));
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    }
   
    
    /**
     * 测试
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoExactByEntity() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);

	List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoExactByEntity(new OrgAdministrativeInfoEntity(), 0, 1);
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    }   
    
    /**
     * 测试
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoExactByEntityCount() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);

	long result = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoExactByEntityCount(new OrgAdministrativeInfoEntity());
	Assert.assertTrue(result>0);

	deleteById(entity.getId());
    }    
    
    
    /**
     * 测试
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoByEntity() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);

	List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoByEntity(new OrgAdministrativeInfoEntity(), 0, 1);
	Assert.assertNotNull(CollectionUtils.isEmpty(entitys));

	deleteById(entity.getId());
    } 
    
    /**
     * 测试
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryOrgAdministrativeInfoByEntityCount() {
	// 准备测试数据
	OrgAdministrativeInfoEntity entity = getEntity();	
	entity = orgAdministrativeInfoService.addOrgAdministrativeInfo(entity);

	long result = orgAdministrativeInfoService
		.queryOrgAdministrativeInfoByEntityCount(new OrgAdministrativeInfoEntity());
	Assert.assertTrue(result>0);

	deleteById(entity.getId());
    }

    
    
    
    
    /**
     * 下面是测试所使用的工具
     */
    private static int count=0;
    private JdbcTemplate jdbc;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public static OrgAdministrativeInfoEntity getEntity(){
	OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
	String virtualCode=System.currentTimeMillis()+(++count)+"";
	entity.setId(virtualCode);
	entity.setCode(virtualCode);
	entity.setName("087584-test-needDelete");
	entity.setCreateUser("087584-test");
	entity.setSubsidiaryCode("Z00001");
	entity.setCountyCode("001001002003");
	entity.setProvCode("001001002");
	entity.setCityCode("001001");
	entity.setCountryRegion("001");
	
	return entity;
    }  
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_ORG where id = '" + id + "'");
	
    }
    
    @Test
    public void testQueryCommonNameByCommonCodeFromCache() {
	String code = "KYNJJC001";
	String name = orgAdministrativeInfoService.queryCommonNameByCommonCodeFromCache(code);
	Assert.assertNotNull(name);
	System.out.println(name);
    }
    

    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	orgAdministrativeInfoService = (IOrgAdministrativeInfoService) SpringTestHelper.get().getBeanByClass(OrgAdministrativeInfoService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    
}
