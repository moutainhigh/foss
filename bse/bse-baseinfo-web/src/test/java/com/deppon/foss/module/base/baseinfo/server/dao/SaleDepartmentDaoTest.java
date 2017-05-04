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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/SaleDepartmentDaoTest.java
 * 
 * FILE NAME        	: SaleDepartmentDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * DAO测试类
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class SaleDepartmentDaoTest {

    /**
     * 测试新增方法
     */
    @Test
    public void testAddSaleDepartment() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	SaleDepartmentEntity result = saleDepartmentDao
		.addSaleDepartment(addEntity);
	try{
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteSaleDepartment() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件
	    SaleDepartmentEntity entity = new SaleDepartmentEntity();
	    List<SaleDepartmentEntity> entitys = saleDepartmentDao
		    .querySaleDepartmentByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);

	    SaleDepartmentEntity result = saleDepartmentDao
		    .deleteSaleDepartment(addEntity);
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
    public void testDeleteSaleDepartmentMore() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    String[] codes = { addEntity.getCode() };
	    SaleDepartmentEntity result = saleDepartmentDao
		    .deleteSaleDepartmentMore(codes, null);
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
    public void testUpdateSaleDepartment() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);
	SaleDepartmentEntity result =null;
	
	String deleteId = addEntity.getId();
	try{
	    
	    result= saleDepartmentDao
		    .updateSaleDepartment(addEntity);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(deleteId);
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
    public void testQuerySaleDepartmentByCode() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    String code = addEntity.getCode();

	    SaleDepartmentEntity result = saleDepartmentDao
		    .querySaleDepartmentByCode(code);

	    SaleDepartmentEntity e=result;
	    e.setMaxTempArrears(e.getMaxTempArrears().multiply(new BigDecimal(100)));
	    e.setUsedTempArrears(e.getUsedTempArrears().multiply(new BigDecimal(100)));
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
    public void testQuerySaleDepartmentBatchByCode() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    String[] codes = addEntity.getCode().split(",");

	    List<SaleDepartmentEntity> result = null;
	    result = saleDepartmentDao.querySaleDepartmentBatchByCode(codes);
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
    public void testQuerySaleDepartmentExactByEntity() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    SaleDepartmentEntity entity = new SaleDepartmentEntity();
	    entity.setCode(addEntity.getCode());

	    List<SaleDepartmentEntity> result = null;
	    result = saleDepartmentDao.querySaleDepartmentExactByEntity(entity,
		    0, 1);
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
    public void testQuerySaleDepartmentExactByEntityCount() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    SaleDepartmentEntity entity = new SaleDepartmentEntity();
	    entity.setCode(addEntity.getCode());

	    long result = 0;
	    result = saleDepartmentDao
		    .querySaleDepartmentExactByEntityCount(entity);
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
    public void testQuerySaleDepartmentByEntity() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    SaleDepartmentEntity entity = new SaleDepartmentEntity();

	    List<SaleDepartmentEntity> result = saleDepartmentDao
		    .querySaleDepartmentByEntity(entity, 0, 1);
	    Assert.assertTrue(result != null);
	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 查询querySaleDepartmentByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQuerySaleDepartmentByEntityCount() {
	// 先添加一个实体，用于测试
	SaleDepartmentEntity addEntity = getEntity();
	saleDepartmentDao.addSaleDepartment(addEntity);

	try{
	    // 创建查询条件：
	    SaleDepartmentEntity entity = new SaleDepartmentEntity();

	    long result = saleDepartmentDao
		    .querySaleDepartmentByEntityCount(entity);
	    Assert.assertTrue(result >= 0);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 下面是测试所需要的工具
     */
    private JdbcTemplate jdbc;

    private ISaleDepartmentDao saleDepartmentDao;

    private SaleDepartmentEntity saleDepartmentEntity;

    public SaleDepartmentEntity getSaleDepartmentEntity() {
	return saleDepartmentEntity;
    }

    public void setSaleDepartmentEntity(
	    SaleDepartmentEntity saleDepartmentEntity) {
	this.saleDepartmentEntity = saleDepartmentEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	saleDepartmentDao = (ISaleDepartmentDao) SpringTestHelper.get()
		.getBeanByClass(SaleDepartmentDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest() {
	// jdbc.execute("delete from T_BAS_SALES_DEPARTMENT");
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
	jdbc.execute("delete from BSE.t_bas_sales_department where id = '" + id + "'");
	
    }
    
    public static SaleDepartmentEntity getEntity() {
	SaleDepartmentEntity entity = (SaleDepartmentEntity) createObj(SaleDepartmentEntity.class
		.getName());
	entity.setCode(System.currentTimeMillis() + "");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584");
	entity.setStationNumber(System.currentTimeMillis()%10000+"");
	entity.setMaxTempArrears(new BigDecimal(432.24));
	entity.setUsedTempArrears(new BigDecimal(432.24));
	return entity;
    }

    static int inLevel = 0;
    @SuppressWarnings("rawtypes")
    public static Object createObj(String cls, int level) {
	try {
	    inLevel = level;
	    ++inLevel;

	    Object o = Class.forName(cls).newInstance();
	    if (inLevel == 3) {
		return o;
	    }
	    if ("java.util.Date".equals(cls)) {
		return o;
	    }

	    Set set = getSuperMethod(Object.class);
	    java.lang.reflect.Method[] ms = o.getClass().getMethods();
	    for (int i = 0, l = ms.length; i < l; i++) {
		java.lang.reflect.Method field = ms[i];
		String fieldName = field.getName();
		if (!set.contains(fieldName)) {
		    if (fieldName.startsWith("set")) {
			Class[] innerClasses = ms[i].getParameterTypes();
			String clsName = innerClasses[0].getName();
			String paramClsName =clsName;
			Object o2 = null;
			if ("java.lang.String".equals(clsName)) {
			    o2 = "1";
			}else if ("java.util.Collection".equals(paramClsName)) {
			    o2 = new ArrayList<Object>();
			} else if ("java.lang.Integer".equals(paramClsName)) {
			    o2 = new Integer(1);
			} else if ("java.util.List".equals(paramClsName)) {
			    o2 = new ArrayList();
			} else if ("java.lang.Byte".equals(paramClsName)) {
			    o2 = new Byte("1");
			} else if ("boolean".equals(paramClsName)) {
			    o2 = new Boolean("True");
			} else if ("long".equals(paramClsName)) {
			    o2 = System.currentTimeMillis();
			}else if ("java.lang.Long".equals(paramClsName)) {
			    o2 = System.currentTimeMillis();
			} else if ("java.lang.Boolean".equals(paramClsName)) {
			    o2 = new Boolean("True");
			} else if ("java.math.BigDecimal".equals(paramClsName)) {
			    o2 = new BigDecimal(123);
			} else {
			    o2 = createObj(innerClasses[0].getName(), inLevel);
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
    public static Object createObj(String cls) {
	try {
	    ++inLevel;

	    Object o = Class.forName(cls).newInstance();
	    if (inLevel == 3) {
		return o;
	    }
	    if ("java.util.Date".equals(cls)) {
		return o;
	    }

	    Set set = getSuperMethod(Object.class);
	    java.lang.reflect.Method[] ms = o.getClass().getMethods();
	    for (int i = 0, l = ms.length; i < l; i++) {
		java.lang.reflect.Method field = ms[i];
		String fieldName = field.getName();
		if (!set.contains(fieldName)) {
		    if (fieldName.startsWith("set")) {
			Class[] innerClasses = ms[i].getParameterTypes();
			String clsName = innerClasses[0].getName();
			String paramClsName =clsName;
			Object o2 = null;
			if ("java.lang.String".equals(clsName)) {
			    o2 = "Y";
			} else if ("java.util.Collection".equals(paramClsName)) {
			    o2 = new ArrayList<Object>();
			} else if ("java.lang.Integer".equals(paramClsName)) {
			    o2 = new Integer(1);
			} else if ("java.util.List".equals(paramClsName)) {
			    o2 = new ArrayList();
			} else if ("java.lang.Byte".equals(paramClsName)) {
			    o2 = new Byte("1");
			} else if ("boolean".equals(paramClsName)) {
			    o2 = new Boolean("True");
			} else if ("java.lang.Long".equals(paramClsName)) {
			    o2 = System.currentTimeMillis();
			} else if ("long".equals(paramClsName)) {
			    o2 = System.currentTimeMillis();
			} else if ("java.lang.Boolean".equals(paramClsName)) {
			    o2 = new Boolean("True");
			} else if ("java.math.BigDecimal".equals(paramClsName)) {
			    o2 = new BigDecimal(123);
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
