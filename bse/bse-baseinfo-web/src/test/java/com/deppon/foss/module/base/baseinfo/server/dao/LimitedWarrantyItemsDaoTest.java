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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LimitedWarrantyItemsDaoTest.java
 * 
 * FILE NAME        	: LimitedWarrantyItemsDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LimitedWarrantyItemsDao;
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
public class LimitedWarrantyItemsDaoTest {

    /**
     * 测试新增方法
     */
    @Test
    public void testAddLimitedWarrantyItems() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	LimitedWarrantyItemsEntity result = loadAndUnloadSquadDao
		.addLimitedWarrantyItems(addEntity);

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
    public void testDeleteLimitedWarrantyItems() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    LimitedWarrantyItemsEntity result = loadAndUnloadSquadDao
		    .deleteLimitedWarrantyItems(addEntity);
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
    public void testDeleteLimitedWarrantyItemsMore() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    String[] codes = { addEntity.getVirtualCode() };
	    LimitedWarrantyItemsEntity result = loadAndUnloadSquadDao
		    .deleteLimitedWarrantyItemsMore(codes, null);
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
    public void testUpdateLimitedWarrantyItems() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    LimitedWarrantyItemsEntity result = loadAndUnloadSquadDao
		    .updateLimitedWarrantyItems(addEntity);
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
    public void testQueryLimitedWarrantyItemsByVirtualCode() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    // 创建查询条件：
	    String code = addEntity.getVirtualCode();
	    
	    LimitedWarrantyItemsEntity result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsByVirtualCode(code);
	    
	    LimitedWarrantyItemsEntity e=result;
	    e.setLimitedAmount(e.getLimitedAmount().multiply(new BigDecimal(100)));

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
    public void testQueryLimitedWarrantyItemsBatchByVirtualCode() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    String[] codes = addEntity.getVirtualCode().split(",");

	    List<LimitedWarrantyItemsEntity> result = null;
	    result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsBatchByVirtualCode(codes);
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
    public void testQueryLimitedWarrantyItemsExactByEntity() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    // 创建查询条件：
	    LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();
	    entity.setVirtualCode(addEntity.getVirtualCode());

	    List<LimitedWarrantyItemsEntity> result = null;
	    result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsExactByEntity(entity, 0, 1);
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
    public void testQueryLimitedWarrantyItemsExactByEntityCount() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    // 创建查询条件：
	    LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();
	    entity.setVirtualCode(addEntity.getVirtualCode());

	    long result = 0;
	    result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsExactByEntityCount(entity);
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
    public void testQueryLimitedWarrantyItemsByEntity() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    // 创建查询条件：
	    LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();
	    entity.setVirtualCode(addEntity.getVirtualCode());

	    List<LimitedWarrantyItemsEntity> result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsByEntity(entity, 0, 1);
	    Assert.assertTrue(result != null);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 查询queryLimitedWarrantyItemsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryLimitedWarrantyItemsByEntityCount() {
	// 先添加一个实体，用于测试
	LimitedWarrantyItemsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addLimitedWarrantyItems(addEntity);

	try{
	    // 创建查询条件：
	    LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();

	    long result = loadAndUnloadSquadDao
		    .queryLimitedWarrantyItemsByEntityCount(entity);
	    Assert.assertTrue(result >= 0);

	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 下面是测试所需要的工具
     */
    private JdbcTemplate jdbc;

    private ILimitedWarrantyItemsDao loadAndUnloadSquadDao;

    private LimitedWarrantyItemsEntity loadAndUnloadSquadEntity;

    public LimitedWarrantyItemsEntity getLimitedWarrantyItemsEntity() {
	return loadAndUnloadSquadEntity;
    }

    public void setLimitedWarrantyItemsEntity(
	    LimitedWarrantyItemsEntity loadAndUnloadSquadEntity) {
	this.loadAndUnloadSquadEntity = loadAndUnloadSquadEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	loadAndUnloadSquadDao = (ILimitedWarrantyItemsDao) SpringTestHelper.get()
		.getBeanByClass(LimitedWarrantyItemsDao.class);
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
    
    /**
     * 删除测试数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_ROLE where id = '" + id + "'");
	
    }
    
    public static void main(String[] args) {

    }

    public static LimitedWarrantyItemsEntity getEntity() {
	LimitedWarrantyItemsEntity entity = (LimitedWarrantyItemsEntity) createObj(
		LimitedWarrantyItemsEntity.class.getName());
	entity.setVirtualCode(System.currentTimeMillis() + "");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584");
	entity.setLimitedAmount(new BigDecimal(432.24));
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
			    } else if("boolean".equals(paramClsName)){
				o2 =new Boolean("True") ;
			    }else if("java.math.BigDecimal".equals(paramClsName)){
				o2 =new BigDecimal(1234.321) ;
			    }else if("java.lang.Long".equals(paramClsName)){
				o2 =System.currentTimeMillis();
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
