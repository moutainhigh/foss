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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/MotorcadeDaoTest.java
 * 
 * FILE NAME        	: MotorcadeDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.MotorcadeDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * DAO测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class MotorcadeDaoTest {
    /**
     * 测试新增方法
     */
    @Test
    public void testAddMotorcade() {
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	MotorcadeEntity result = motorcadeDao.addMotorcade(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteMotorcade(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);

	MotorcadeEntity result = motorcadeDao.deleteMotorcade(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testDeleteMotorcadeMore(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity =  getEntity();
	motorcadeDao.addMotorcade(addEntity);

	String[] codes = { addEntity.getCode() };
	MotorcadeEntity result = motorcadeDao.deleteMotorcadeMore(codes, null);
	Assert.assertTrue(result != null);
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testUpdateMotorcade(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);

	MotorcadeEntity result = motorcadeDao.updateMotorcade(addEntity);
	Assert.assertTrue(result != null);	
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
    public void testQueryMotorcadeByCode(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getCode();
	
	MotorcadeEntity  result = motorcadeDao.queryMotorcadeByCode(code);
	Assert.assertTrue(result !=null);	
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryMotorcadeBatchByCode(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	String[] codes = addEntity.getCode().split(",");
	
	List<MotorcadeEntity>  result = null;
	result = motorcadeDao.queryMotorcadeBatchByCode(codes);
	Assert.assertTrue(result !=null);	
    }
    
    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryMotorcadeExactByEntity(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	MotorcadeEntity entity=new MotorcadeEntity();
	entity.setCode(addEntity.getCode());
	
	List<MotorcadeEntity>  result = null;
	result = motorcadeDao.queryMotorcadeExactByEntity(entity,1,0);
	Assert.assertTrue(result !=null);	
    }
    
    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryMotorcadeExactByEntityCount(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	MotorcadeEntity entity=new MotorcadeEntity();
	entity.setCode(addEntity.getCode());
	
	long  result = 0;
	result = motorcadeDao.queryMotorcadeExactByEntityCount(entity);
	Assert.assertTrue(result >= 0);	
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryMotorcadeByEntity(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	MotorcadeEntity entity = new MotorcadeEntity();
	entity.setCode(addEntity.getCode());
	
	List<MotorcadeEntity>  result = motorcadeDao.queryMotorcadeByEntity(entity,1,0);
	Assert.assertTrue(result !=null);
    }

    /**
     * 查询queryMotorcadeByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryMotorcadeByEntityCount(){
	// 先添加一个实体，用于测试
	MotorcadeEntity addEntity = getEntity();
	motorcadeDao.addMotorcade(addEntity);
	
	// 创建查询条件：
	MotorcadeEntity entity = new MotorcadeEntity();
	entity.setCode(addEntity.getCode());
	
	long result = motorcadeDao.queryMotorcadeByEntityCount(entity);
	Assert.assertTrue(result >=0);
    }
    
    
    
    /**
     * 下面是测试所需要的工具
     */
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;

    private IMotorcadeDao motorcadeDao;

    private MotorcadeEntity motorcadeEntity;

    public MotorcadeEntity getMotorcadeEntity() {
	return motorcadeEntity;
    }

    public void setMotorcadeEntity(
	    MotorcadeEntity motorcadeEntity) {
	this.motorcadeEntity = motorcadeEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	
	initTest();	
	motorcadeDao = (IMotorcadeDao) SpringTestHelper.get()
		.getBeanByClass(MotorcadeDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from T_BAS_TRANS_DEPARTMENT");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    
    public static MotorcadeEntity getEntity(){
	MotorcadeEntity entity=(MotorcadeEntity)createObj(MotorcadeEntity.class.getName(), 2);
	entity.setCode(System.currentTimeMillis()+"");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584");
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
