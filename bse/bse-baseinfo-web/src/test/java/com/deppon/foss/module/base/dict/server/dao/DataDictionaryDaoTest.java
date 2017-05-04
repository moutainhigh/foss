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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/dao/DataDictionaryDaoTest.java
 * 
 * FILE NAME        	: DataDictionaryDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.server.dao.impl.DataDictionaryDao;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 数据字典-词 测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class DataDictionaryDaoTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;

    private IDataDictionaryDao dataDictionaryDao;

    private DataDictionaryEntity dataDictionaryEntity;

    public DataDictionaryEntity getDataDictionaryEntity() {
	return dataDictionaryEntity;
    }

    public void setDataDictionaryEntity(
	    DataDictionaryEntity dataDictionaryEntity) {
	this.dataDictionaryEntity = dataDictionaryEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	dataDictionaryDao = (IDataDictionaryDao) SpringTestHelper.get()
		.getBeanByClass(DataDictionaryDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from T_BAS_DATA_DICTIONARY");
    }
    /**
     * 下面全部是测试方法：
     * =======================================================================
     */

    /**
     * 测试新增方法
     */
    
    @Test
    public void testAddDataDictionary() {
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	DataDictionaryEntity result = dataDictionaryDao.addDataDictionary(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE删除
     */
    
    @Test
    public void testDeleteDataDictionary(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);

	DataDictionaryEntity result = dataDictionaryDao.deleteDataDictionary(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testDeleteDataDictionaryMore(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity =  getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);
	
	DataDictionaryEntity result = dataDictionaryDao.deleteDataDictionaryMore(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testUpdateDataDictionary(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);
	
	DataDictionaryEntity result = dataDictionaryDao.updateDataDictionary(addEntity);
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
    public void testQueryDataDictionaryByTermsCode(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);	
	// 创建查询条件：
	String code=addEntity.getTermsCode();
	
	DataDictionaryEntity  result = dataDictionaryDao.queryDataDictionaryByTermsCode(code);
	Assert.assertTrue(result !=null);	
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryByEntity(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);
	
	// 创建查询条件：
	DataDictionaryEntity entity = new DataDictionaryEntity();
	
	List<DataDictionaryEntity>  result = dataDictionaryDao.queryDataDictionaryByEntity(entity,0, 1);
	Assert.assertTrue(result !=null);
    }

    /**
     * 查询queryDataDictionaryByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryByEntityCount(){
	// 先添加一个实体，用于测试
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);
	// 创建查询条件：
	DataDictionaryEntity entity = new DataDictionaryEntity();
	
	long result = dataDictionaryDao.queryDataDictionaryByEntityCount(entity);
	Assert.assertTrue(result >=0);
    }

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryCount(){
	DataDictionaryEntity addEntity = getEntity();
	dataDictionaryDao.addDataDictionary(addEntity);
	
	long  result = dataDictionaryDao.queryDataDictionaryCount("ACTIVE",FossConstants.ACTIVE);
	Assert.assertTrue(result >=0);
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    
    public static DataDictionaryEntity getEntity(){
	DataDictionaryEntity entity=(DataDictionaryEntity)createObj(DataDictionaryEntity.class.getName());
	entity.setTermsCode(System.currentTimeMillis()+"");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setVirtualCode(id);
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
