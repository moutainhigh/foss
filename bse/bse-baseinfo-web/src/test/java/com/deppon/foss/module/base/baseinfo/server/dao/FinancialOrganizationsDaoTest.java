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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/FinancialOrganizationsDaoTest.java
 * 
 * FILE NAME        	: FinancialOrganizationsDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * DAO测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */		
@Ignore
public class FinancialOrganizationsDaoTest {
    /**
     * 测试新增方法
     */
    @Test
    public void testAddFinancialOrganizations() {
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	
	try{
	    FinancialOrganizationsEntity result = financialOrganizationsDao.addFinancialOrganizations(addEntity);
	    Assert.assertTrue(result != null);
	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteFinancialOrganizations(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);

	try{
	    // 创建查询条件
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    List<FinancialOrganizationsEntity> entitys = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);

	    FinancialOrganizationsEntity result = financialOrganizationsDao
		    .deleteFinancialOrganizations(addEntity);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testDeleteFinancialOrganizationsMore(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity =  getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);

	try{
	    String[] codes = { addEntity.getCode() };
	    FinancialOrganizationsEntity result = financialOrganizationsDao
		    .deleteFinancialOrganizationsMore(codes, null);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testUpdateFinancialOrganizations(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    FinancialOrganizationsEntity result = financialOrganizationsDao
		    .updateFinancialOrganizations(addEntity);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    
    /**
     * 以下全为查询
     */

    /**
     * 根据编码查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsByCode(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    String code = addEntity.getCode();

	    FinancialOrganizationsEntity result = financialOrganizationsDao
		    .queryFinancialOrganizationsByCode(code);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsBatchByCode(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    List<FinancialOrganizationsEntity> entitys = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);
	    String[] codes = addEntity.getCode().split(",");

	    List<FinancialOrganizationsEntity> result = null;
	    result = financialOrganizationsDao
		    .queryFinancialOrganizationsBatchByCode(codes);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }
    
    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsExactByEntity(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    List<FinancialOrganizationsEntity> entitys = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);
	    entity.setCode(addEntity.getCode());

	    List<FinancialOrganizationsEntity> result = null;
	    result = financialOrganizationsDao
		    .queryFinancialOrganizationsExactByEntity(entity, 0, 1);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }
    
    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsExactByEntityCount(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    List<FinancialOrganizationsEntity> entitys = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);
	    entity.setCode(addEntity.getCode());

	    long result = 0;
	    result = financialOrganizationsDao
		    .queryFinancialOrganizationsExactByEntityCount(entity);
	    Assert.assertTrue(result >= 0);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsByEntity(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();

	    List<FinancialOrganizationsEntity> result = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 查询queryFinancialOrganizationsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryFinancialOrganizationsByEntityCount(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    entity.setActive("1");

	    long result = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntityCount(entity);
	    Assert.assertTrue(result >= 0);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    
    
    /**
     * 以下为特殊查询
     */
    
    /**
     * 模糊查询 根据name查询财务组织及财务组织的所有上级，上下级通过CODE,PARENT_ORG_CODE来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-22 下午5:19:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService#queryFinancialOrganizationsUpByName(java.lang.String)
     */
    @Test
    public void testQueryFinancialOrganizationsUpByName(){
	// 先添加一个实体，用于测试
	FinancialOrganizationsEntity addEntity = getEntity();
	financialOrganizationsDao.addFinancialOrganizations(addEntity);
	
	try{
	    // 创建查询条件：
	    FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();
	    List<FinancialOrganizationsEntity> entitys = financialOrganizationsDao
		    .queryFinancialOrganizationsByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);

	    List<FinancialOrganizationsEntity> result = financialOrganizationsDao
		    .queryFinancialOrganizationsUpByName(addEntity.getName());
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }
    
    
    /**
     * 下面是测试所需要的工具
     */
    private JdbcTemplate jdbc;

    private IFinancialOrganizationsDao financialOrganizationsDao;

    private FinancialOrganizationsEntity financialOrganizationsEntity;

    public FinancialOrganizationsEntity getFinancialOrganizationsEntity() {
	return financialOrganizationsEntity;
    }

    public void setFinancialOrganizationsEntity(
	    FinancialOrganizationsEntity financialOrganizationsEntity) {
	this.financialOrganizationsEntity = financialOrganizationsEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	financialOrganizationsDao = (IFinancialOrganizationsDao) SpringTestHelper.get()
		.getBeanByClass(FinancialOrganizationsDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from T_BAS_FIN_ORG");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    
    
    /**
     * 删除测试数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_FIN_ORG where id = '" + id + "'");
	
    }
    
    public static FinancialOrganizationsEntity getEntity(){
	FinancialOrganizationsEntity entity=(FinancialOrganizationsEntity)createObj(FinancialOrganizationsEntity.class.getName(), 2);
	entity.setCode(System.currentTimeMillis()+"");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584");
	entity.setParentOrgCode(null);
	entity.setFinOrgBenchmarkCode("TestFinOrgBenchmarkCode");
	entity.setParentFinOrgBenchmarkCode("TestParentFinOrgBenchmarkCode");
	return entity;
    }

    static int inLevel = 0;

    public static Object createObj(String cls, int level) {
	try {
	    ++inLevel;

	    Object o = Class.forName(cls).newInstance();
	    if (inLevel == 3) {
		return o;
	    }
	    if ("java.util.Date".equals(cls)) {
		return o;
	    }

	    @SuppressWarnings("rawtypes")
	    Set set = getSuperMethod(Object.class);
	    java.lang.reflect.Method[] ms = o.getClass().getMethods();
	    for (int i = 0, l = ms.length; i < l; i++) {
		java.lang.reflect.Method field = ms[i];
		String fieldName = field.getName();
		if (!set.contains(fieldName)) {
		    if (fieldName.startsWith("set")) {
			@SuppressWarnings("rawtypes")
			Class[] innerClasses = ms[i].getParameterTypes();
			String clsName = innerClasses[0].getName();
			Object o2 = null;
			if ("java.lang.String".equals(clsName)) {
			    o2 = "1";
			} else {
			    o2 = createObj(innerClasses[0].getName(), 2);
			}
			ms[i].invoke(o, o2);
		    }
		}
	    }
	    --inLevel;
	    return o;
	} catch (Exception e) {
	}
	return null;
    }

    @SuppressWarnings("rawtypes")
    public static Set getSuperMethod(Class cls) {
	Set<String> set = new HashSet<String>();
	Method[] m = cls.getMethods();
	for (int i = 0, l = m.length; i < l; i++) {
	    set.add(m[i].getName());
	}
	return set;
    }
}
