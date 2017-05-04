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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/org/WorkdayServiceTest.java
 * 
 * FILE NAME        	: WorkdayServiceTest.java
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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.MotorcadeServeDistrictService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 营业部适用产品-服务-测试类
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:53:20 AM
 * @version 1.0
 */
@Ignore
public class WorkdayServiceTest {
    /**
     * 测试新增
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:11
     */
    @Test
    public void addMotorcadeServeDistrict() {
	MotorcadeServeDistrictEntity entity = getEntity();
	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	Assert.assertNotNull(entity);
	deleteById(entity.getId());
    }
    
    /**
     * 测试作废
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    
    @Test
    public void deleteMotorcadeServeDistrict() {
	MotorcadeServeDistrictEntity entity = getEntity();
	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	entity = motorcadeServeDistrictService.deleteMotorcadeServeDistrict(entity);
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
    public void deleteMotorcadeServeDistrictMore() {
	MotorcadeServeDistrictEntity entity = getEntity();
	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	entity = motorcadeServeDistrictService.deleteMotorcadeServeDistrictMore(entity
		.getVirtualCode().split(SymbolConstants.EN_COMMA), entity
		.getCreateUser());
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
    public void updateMotorcadeServeDistrict() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	
	entity = motorcadeServeDistrictService.updateMotorcadeServeDistrict(entity);
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
    }
    
    
    
    /**
     * 以下全为查询
     */  
    
    /**
     * 测试
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void queryMotorcadeServeDistrictByVirtualCode() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	
	entity = motorcadeServeDistrictService.queryMotorcadeServeDistrictByVirtualCode(entity.getVirtualCode());
	Assert.assertNotNull(entity);

	deleteById(entity.getId());
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
    public void queryMotorcadeServeDistrictBatchByVirtualCode() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);
	
	List<MotorcadeServeDistrictEntity> entitys = motorcadeServeDistrictService.queryMotorcadeServeDistrictBatchByVirtualCode(entity.getVirtualCode().split(SymbolConstants.EN_COMMA));
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
    public void queryMotorcadeServeDistrictExactByEntity() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);

	List<MotorcadeServeDistrictEntity> entitys = motorcadeServeDistrictService
		.queryMotorcadeServeDistrictExactByEntity(new MotorcadeServeDistrictEntity(), 0, 1);
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
    public void queryMotorcadeServeDistrictExactByEntityCount() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);

	long result = motorcadeServeDistrictService
		.queryMotorcadeServeDistrictExactByEntityCount(new MotorcadeServeDistrictEntity());
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
    public void queryMotorcadeServeDistrictByEntity() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);

	List<MotorcadeServeDistrictEntity> entitys = motorcadeServeDistrictService
		.queryMotorcadeServeDistrictByEntity(new MotorcadeServeDistrictEntity(), 0, 1);
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
    public void queryMotorcadeServeDistrictByEntityCount() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);

	long result = motorcadeServeDistrictService
		.queryMotorcadeServeDistrictByEntityCount(new MotorcadeServeDistrictEntity());
	Assert.assertTrue(result>0);

	deleteById(entity.getId());
    }
    
    
    
    /**
     * 下面是特殊方法
     */
    
    /**
     * 测试
     * 
     * 通过MotorcadeCode车队编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void deleteMotorcadeServeDistrictByMotorcadeCode() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	entity = motorcadeServeDistrictService.addMotorcadeServeDistrict(entity);

	MotorcadeServeDistrictEntity result = motorcadeServeDistrictService
		.deleteMotorcadeServeDistrictByMotorcadeCode(entity);
	Assert.assertTrue(result!=null);

	deleteById(entity.getId());
    }
    
    
    /**
     * 测试
     * 
     * 通过salesdeptCode营业部编码,产品的业务部门类型SALES_TYPE（是出发部门还是到达部门），来删除
     * 
     * 如果SALES_TYPE为空，则作废营业部的所有产品
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    @Test
    public void addMotorcadeServeDistrictMore() {
	// 准备测试数据
	MotorcadeServeDistrictEntity entity = getEntity();	
	List<MotorcadeServeDistrictEntity> entitys=new ArrayList<MotorcadeServeDistrictEntity>();
	entitys.add(entity);
	List<MotorcadeServeDistrictEntity> result = motorcadeServeDistrictService
		.addMotorcadeServeDistrictMore(entitys);
	Assert.assertTrue(!CollectionUtils.isEmpty(result));

	deleteById(entity.getId());
    }
    
    
    
    
    
    /**
     * 下面是测试所使用的工具
     */
    private static int count=0;
    private JdbcTemplate jdbc;
    private IMotorcadeServeDistrictService motorcadeServeDistrictService;
    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public static MotorcadeServeDistrictEntity getEntity(){
	MotorcadeServeDistrictEntity entity = new MotorcadeServeDistrictEntity();
	String virtualCode=System.currentTimeMillis()+(++count)+"";
	entity.setId(virtualCode);
	entity.setVirtualCode(virtualCode);
	entity.setMotorcadeCode(virtualCode);
	entity.setDistrictCode(virtualCode);
	entity.setCreateUser("087584-test");
	return entity;
    }  
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_MOTORCADE_DISTRICT where id = '" + id + "'");
	
    }
    
    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	motorcadeServeDistrictService = (IMotorcadeServeDistrictService) SpringTestHelper.get().getBeanByClass(MotorcadeServeDistrictService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    
}
