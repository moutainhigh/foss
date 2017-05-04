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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LoadAndUnloadSquadDaoTest.java
 * 
 * FILE NAME        	: LoadAndUnloadSquadDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadSquadDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LoadAndUnloadSquadDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 装卸车小队DAO测试类
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class LoadAndUnloadSquadDaoTest {

    private static final Logger log = Logger.getLogger(LoadAndUnloadSquadDaoTest.class);

    /**
     * 测试新增方法
     */
    @Test
    public void testAddLoadAndUnloadSquad() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	LoadAndUnloadSquadEntity result = loadAndUnloadSquadDao
		.addLoadAndUnloadSquad(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteLoadAndUnloadSquad() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	LoadAndUnloadSquadEntity result = loadAndUnloadSquadDao
		.deleteLoadAndUnloadSquad(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testDeleteLoadAndUnloadSquadMore() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	String[] codes = { addEntity.getCode() };
	LoadAndUnloadSquadEntity result = loadAndUnloadSquadDao
		.deleteLoadAndUnloadSquadMore(codes, null);
	Assert.assertTrue(result != null);
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testUpdateLoadAndUnloadSquad() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	LoadAndUnloadSquadEntity result = loadAndUnloadSquadDao
		.updateLoadAndUnloadSquad(addEntity);
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
    public void testQueryLoadAndUnloadSquadByCode() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	// 创建查询条件：
	String code = addEntity.getCode();

	LoadAndUnloadSquadEntity result = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadByCode(code);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadSquadBatchByCode() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	String[] codes = addEntity.getCode().split(",");

	List<LoadAndUnloadSquadEntity> result = null;
	result = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadBatchByCode(codes);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadSquadExactByEntity() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	// 创建查询条件：
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();
	entity.setCode(addEntity.getCode());

	List<LoadAndUnloadSquadEntity> result = null;
	result = loadAndUnloadSquadDao.queryLoadAndUnloadSquadExactByEntity(
		entity, 0, 1);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadSquadExactByEntityCount() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	// 创建查询条件：
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();
	entity.setCode(addEntity.getCode());

	long result = 0;
	result = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadExactByEntityCount(entity);
	Assert.assertTrue(result >= 0);
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadSquadByEntity() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	// 创建查询条件：
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();
	entity.setCode(addEntity.getCode());
	
	List<LoadAndUnloadSquadEntity> result = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadByEntity(entity, 0, 1);
	Assert.assertTrue(result != null);
    }

    /**
     * 查询queryLoadAndUnloadSquadByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadSquadByEntityCount() {
	// 先添加一个实体，用于测试
	LoadAndUnloadSquadEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadSquad(addEntity);

	// 创建查询条件：
	LoadAndUnloadSquadEntity entity = new LoadAndUnloadSquadEntity();

	long result = loadAndUnloadSquadDao
		.queryLoadAndUnloadSquadByEntityCount(entity);
	Assert.assertTrue(result >= 0);
    }

    /**
     * 下面是测试所需要的工具
     */
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;

    private ILoadAndUnloadSquadDao loadAndUnloadSquadDao;

    private LoadAndUnloadSquadEntity loadAndUnloadSquadEntity;

    public LoadAndUnloadSquadEntity getLoadAndUnloadSquadEntity() {
	return loadAndUnloadSquadEntity;
    }

    public void setLoadAndUnloadSquadEntity(
	    LoadAndUnloadSquadEntity loadAndUnloadSquadEntity) {
	this.loadAndUnloadSquadEntity = loadAndUnloadSquadEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	loadAndUnloadSquadDao = (ILoadAndUnloadSquadDao) SpringTestHelper.get()
		.getBeanByClass(LoadAndUnloadSquadDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest() {
	// jdbc.execute("delete from T_BAS_TRANS_DEPARTMENT");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {

    }

    public static LoadAndUnloadSquadEntity getEntity() {
	LoadAndUnloadSquadEntity entity = (LoadAndUnloadSquadEntity) createObj(
		LoadAndUnloadSquadEntity.class.getName());
	entity.setCode(System.currentTimeMillis() + "");
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
	    log.error(cls,e);
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
			    String paramClsName = innerClasses[0].getName();
			    if ("java.util.Collection".equals(paramClsName)) {
				o2 = new ArrayList<Object>();
			    } else if ("java.lang.Integer".equals(paramClsName)) {
				o2 = new Integer(1);
			    }else if ("java.util.List".equals(paramClsName)) {
				o2 = new ArrayList();
			    } else if ("java.lang.Byte".equals(paramClsName)) {
				o2 = new Byte("1");
			    } else if ("java.lang.Boolean".equals(paramClsName)) {
				o2 = new Boolean("True");
			    } else {
				o2 = Class.forName(paramClsName).newInstance();
			    }
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
