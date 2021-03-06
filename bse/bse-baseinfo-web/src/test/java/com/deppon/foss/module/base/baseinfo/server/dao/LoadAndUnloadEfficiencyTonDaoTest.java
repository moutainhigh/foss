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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LoadAndUnloadEfficiencyTonDaoTest.java
 * 
 * FILE NAME        	: LoadAndUnloadEfficiencyTonDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILoadAndUnloadEfficiencyTonDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LoadAndUnloadEfficiencyTonDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * DAO测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class LoadAndUnloadEfficiencyTonDaoTest {
    /**
     * 测试新增方法
     */
    @Test
    public void testAddLoadAndUnloadEfficiencyTon() {
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	LoadAndUnloadEfficiencyTonEntity result = loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	Assert.assertTrue(result != null);
	
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteLoadAndUnloadEfficiencyTon(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	LoadAndUnloadEfficiencyTonEntity result = loadAndUnloadSquadDao.deleteLoadAndUnloadEfficiencyTon(addEntity);
	Assert.assertTrue(result != null);
	
	deleteById(addEntity.getId());
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testDeleteLoadAndUnloadEfficiencyTonMore() {
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);

	String[] codes = { addEntity.getOrgCode() };
	LoadAndUnloadEfficiencyTonEntity result = loadAndUnloadSquadDao
		.deleteLoadAndUnloadEfficiencyTonMore(codes, null);
	Assert.assertTrue(result != null);

	deleteById(addEntity.getId());
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testUpdateLoadAndUnloadEfficiencyTon(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);

	LoadAndUnloadEfficiencyTonEntity result = loadAndUnloadSquadDao.updateLoadAndUnloadEfficiencyTon(addEntity);
	Assert.assertTrue(result != null);

	deleteById(addEntity.getId());
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
    public void testQueryLoadAndUnloadEfficiencyTonByOrgCode(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getOrgCode();
	
	LoadAndUnloadEfficiencyTonEntity  result = loadAndUnloadSquadDao.queryLoadAndUnloadEfficiencyTonByOrgCode(code);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadEfficiencyTonBatchByOrgCode(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	String[] codes = addEntity.getOrgCode().split(",");
	
	List<LoadAndUnloadEfficiencyTonEntity>  result = null;
	result = loadAndUnloadSquadDao.queryLoadAndUnloadEfficiencyTonBatchByOrgCode(codes);
	Assert.assertTrue(result !=null);	

	deleteById(addEntity.getId());
    }
    
    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadEfficiencyTonExactByEntity(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	addEntity = new LoadAndUnloadEfficiencyTonEntity();
	addEntity.setOrgCode(addEntity.getOrgCode());
	
	List<LoadAndUnloadEfficiencyTonEntity>  result = null;
	result = loadAndUnloadSquadDao.queryLoadAndUnloadEfficiencyTonExactByEntity(addEntity,0, 1);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }
    
    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadEfficiencyTonExactByEntityCount(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	addEntity.setOrgCode(addEntity.getOrgCode());
	
	long  result = 0;
	result = loadAndUnloadSquadDao.queryLoadAndUnloadEfficiencyTonExactByEntityCount(addEntity);
	Assert.assertTrue(result >= 0);	

	deleteById(addEntity.getId());
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadEfficiencyTonByEntity(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	addEntity = new LoadAndUnloadEfficiencyTonEntity();
	
	List<LoadAndUnloadEfficiencyTonEntity>  result = loadAndUnloadSquadDao
		.queryLoadAndUnloadEfficiencyTonByEntity(addEntity,0, 1);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 查询queryLoadAndUnloadEfficiencyTonByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLoadAndUnloadEfficiencyTonByEntityCount(){
	// 先添加一个实体，用于测试
	LoadAndUnloadEfficiencyTonEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLoadAndUnloadEfficiencyTon(addEntity);
	
	// 创建查询条件：
	addEntity = new LoadAndUnloadEfficiencyTonEntity();
	
	long result = loadAndUnloadSquadDao.queryLoadAndUnloadEfficiencyTonByEntityCount(addEntity);
	Assert.assertTrue(result >=0);

	deleteById(addEntity.getId());
    }
    
    
    
    /**
     * 下面是测试所需要的工具
     */
    private JdbcTemplate jdbc;

    private ILoadAndUnloadEfficiencyTonDao loadAndUnloadSquadDao;

    private LoadAndUnloadEfficiencyTonEntity loadAndUnloadSquadEntity;

    public LoadAndUnloadEfficiencyTonEntity getLoadAndUnloadEfficiencyTonEntity() {
	return loadAndUnloadSquadEntity;
    }

    public void setLoadAndUnloadEfficiencyTonEntity(
	    LoadAndUnloadEfficiencyTonEntity loadAndUnloadSquadEntity) {
	this.loadAndUnloadSquadEntity = loadAndUnloadSquadEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	
	initTest();	
	loadAndUnloadSquadDao = (ILoadAndUnloadEfficiencyTonDao) SpringTestHelper.get()
		.getBeanByClass(LoadAndUnloadEfficiencyTonDao.class);
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
    
    
    /**
     * 删除测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_LOAD_UNLOAD_STD_TON where id = '" + id + "'");
	
    }
    
    public static LoadAndUnloadEfficiencyTonEntity getEntity(){
	LoadAndUnloadEfficiencyTonEntity entity=(LoadAndUnloadEfficiencyTonEntity)createObj(LoadAndUnloadEfficiencyTonEntity.class.getName(), 2);
	entity.setOrgCode(System.currentTimeMillis()+"");
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
