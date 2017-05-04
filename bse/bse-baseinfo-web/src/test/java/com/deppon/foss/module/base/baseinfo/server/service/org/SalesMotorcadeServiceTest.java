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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/org/SalesMotorcadeServiceTest.java
 * 
 * FILE NAME        	: SalesMotorcadeServiceTest.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 营业部适用产品-服务-测试类
 * @author foss-zhujunyong
 * @date Oct 30, 2012 10:53:20 AM
 * @version 1.0
 */
@Ignore
public class SalesMotorcadeServiceTest {
    /**
     * 测试新增
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:11
     */
    
    @Test
    public void addSalesMotorcade() {
	SalesMotorcadeEntity entity = getEntity();
	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
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
    public void deleteSalesMotorcade() {
	SalesMotorcadeEntity entity = getEntity();
	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
	entity = salesMotorcadeService.deleteSalesMotorcade(entity);
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
    public void deleteSalesMotorcadeMore() {
	SalesMotorcadeEntity entity = getEntity();
	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
	entity = salesMotorcadeService.deleteSalesMotorcadeMore(entity
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
    public void updateSalesMotorcade() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
	
	entity = salesMotorcadeService.updateSalesMotorcade(entity);
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
    public void querySalesMotorcadeByVirtualCode() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
	
	entity = salesMotorcadeService.querySalesMotorcadeByVirtualCode(entity.getVirtualCode());
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
    public void querySalesMotorcadeBatchByVirtualCode() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);
	
	List<SalesMotorcadeEntity> entitys = salesMotorcadeService.querySalesMotorcadeBatchByVirtualCode(entity.getVirtualCode().split(SymbolConstants.EN_COMMA));
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
    public void querySalesMotorcadeExactByEntity() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);

	List<SalesMotorcadeEntity> entitys = salesMotorcadeService
		.querySalesMotorcadeExactByEntity(new SalesMotorcadeEntity(), 0, 1);
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
    public void querySalesMotorcadeExactByEntityCount() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);

	long result = salesMotorcadeService
		.querySalesMotorcadeExactByEntityCount(new SalesMotorcadeEntity());
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
    public void querySalesMotorcadeByEntity() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);

	List<SalesMotorcadeEntity> entitys = salesMotorcadeService
		.querySalesMotorcadeByEntity(new SalesMotorcadeEntity(), 0, 1);
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
    public void querySalesMotorcadeByEntityCount() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);

	long result = salesMotorcadeService
		.querySalesMotorcadeByEntityCount(new SalesMotorcadeEntity());
	Assert.assertTrue(result>0);

	deleteById(entity.getId());
    }   
    
    
    
    /**
     * 下面是特殊方法
     */
    
    /**
     * 测试
     * 
     * 通过salesdeptCode营业部编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    
    @Test
    public void deleteSalesMotorcadeBySalesdeptCode() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	entity = salesMotorcadeService.addSalesMotorcade(entity);

	SalesMotorcadeEntity result = salesMotorcadeService
		.deleteSalesMotorcadeBySalesdeptCode(entity);
	Assert.assertTrue(result!=null);

	deleteById(entity.getId());
    }
    
    
    /**
     * 测试
     * 
     * 批量插入
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */
    
    @Test
    public void addSalesMotorcadeMore() {
	// 准备测试数据
	SalesMotorcadeEntity entity = getEntity();	
	List<SalesMotorcadeEntity> entitys=new ArrayList<SalesMotorcadeEntity>();
	entitys.add(entity);
	List<SalesMotorcadeEntity> result = salesMotorcadeService
		.addSalesMotorcadeMore(entitys);
	Assert.assertTrue(!CollectionUtils.isEmpty(result));

	deleteById(entity.getId());
    }
    
    
    
    /**
     * 下面是测试所使用的工具
     */
    private static int count=0;
    private JdbcTemplate jdbc;
    private ISalesMotorcadeService salesMotorcadeService;
    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public static SalesMotorcadeEntity getEntity(){
	SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
	String virtualCode=System.currentTimeMillis()+(++count)+"";
	entity.setId(virtualCode);
	entity.setVirtualCode(virtualCode);
	entity.setMotorcadeCode(virtualCode);
	entity.setSalesdeptCode(virtualCode);
	entity.setCreateUser("087584-test");
	return entity;
    }    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_SALESDEPT_MOTORCADE where id = '" + id + "'");
	
    }
    
    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	salesMotorcadeService = (ISalesMotorcadeService) SpringTestHelper.get().getBeanByClass(SalesMotorcadeService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    
}
