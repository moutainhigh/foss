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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/dao/DataDictionaryValueDaoTest.java
 * 
 * FILE NAME        	: DataDictionaryValueDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.server.dao.impl.DataDictionaryValueDao;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 数据字典 测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
//@Ignore
public class DataDictionaryValueDaoTest {
    /**
     * 测试新增方法
     */
    @Test
    public void testAddDataDictionaryValue() {
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	DataDictionaryValueEntity result = dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	Assert.assertTrue(result !=null);
	
	deleteById(addEntity.getId());
    }

    /**
     * 根据CODE删除
     */
    
    @Test
    public void testDeleteDataDictionaryValue(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	DataDictionaryValueEntity result = dataDictionaryValueDao.deleteDataDictionaryValue(addEntity);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
  
    @Test
    public void testDeleteDataDictionaryValueMore(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity =  getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	DataDictionaryValueEntity result = dataDictionaryValueDao.deleteDataDictionaryValueMore(addEntity);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testUpdateDataDictionaryValue(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);

	DataDictionaryValueEntity result = dataDictionaryValueDao.updateDataDictionaryValue(addEntity);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
	deleteById(result.getId());
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
    public void testQueryDataDictionaryValueByVirtualCode(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getVirtualCode();
	
	DataDictionaryValueEntity  result = dataDictionaryValueDao.queryDataDictionaryValueByVirtualCode(code);
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
    public void testqueryDataDictionaryValueBatchByVirtualCode(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	String[] codes = addEntity.getVirtualCode().split(",");
	
	List<DataDictionaryValueEntity>  result = null;
	result = dataDictionaryValueDao.queryDataDictionaryValueBatchByVirtualCode(codes);
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
    public void testQueryDataDictionaryValueExactByEntity(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entity=new DataDictionaryValueEntity();
	entity.setVirtualCode(addEntity.getVirtualCode());
	
	List<DataDictionaryValueEntity>  result = null;
	result = dataDictionaryValueDao.queryDataDictionaryValueExactByEntity(entity,0, 1);
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
    public void testQueryDataDictionaryValueExactByEntityCount(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entity=new DataDictionaryValueEntity();
	entity.setVirtualCode(addEntity.getVirtualCode());
	
	long  result = 0;
	result = dataDictionaryValueDao.queryDataDictionaryValueExactByEntityCount(entity);
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
    public void testQueryDataDictionaryValueByEntity(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	
	List<DataDictionaryValueEntity>  result = dataDictionaryValueDao.queryDataDictionaryValueByEntity(entity,0, 1);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 查询queryDataDictionaryValueByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryValueByEntityCount(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	
	long result = dataDictionaryValueDao.queryDataDictionaryValueByEntityCount(entity);
	Assert.assertTrue(result >=0);

	deleteById(addEntity.getId());
    }

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryValueCount(){
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	long  result = dataDictionaryValueDao.queryDataDictionaryValueCount("ACTIVE",FossConstants.ACTIVE);
	Assert.assertTrue(result >=0);

	deleteById(addEntity.getId());
    }
    
    
    /**
     * 以下为特殊查询 
     */

    
    /**
     * 动态的查询条件。 
     * 
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @param termsCode 上级词条 精确查询
     * @param valueCode 值代码 模糊查询
     * @param valueName 值名称 模糊查询
     */
    @Test
    public void queryDataDictionaryValueForView() {
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entityCondition = new DataDictionaryValueEntity();
	entityCondition.setTermsCode(addEntity.getTermsCode());
	entityCondition.setValueCode(addEntity.getValueCode());
	entityCondition.setValueName(addEntity.getValueName());
	
	List<DataDictionaryValueEntity> results = dataDictionaryValueDao.queryDataDictionaryValueForView(entityCondition, NumberConstants.ZERO, Integer.MAX_VALUE);
	Assert.assertTrue(CollectionUtils.isNotEmpty(results));

	deleteById(addEntity.getId());
    }

    /**
     * 动态的查询条件-查询总条数。
     * 
     *  如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @param termsCode 上级词条 精确查询
     * @param valueCode 值代码 模糊查询
     * @param valueName 值名称 模糊查询
     */
    @Test
    public void queryDataDictionaryValueForViewCount() {
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	DataDictionaryValueEntity entityCondition = new DataDictionaryValueEntity();
	entityCondition.setTermsCode(addEntity.getTermsCode());
	entityCondition.setValueCode(addEntity.getValueCode());
	entityCondition.setValueName(addEntity.getValueName());
	
	long result = dataDictionaryValueDao.queryDataDictionaryValueForViewCount(entityCondition);
	Assert.assertTrue(result > 0);

	deleteById(addEntity.getId());
    }
    
    /**
     * 根据 TermsCode 编码查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryValueByTermsCode(){
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getTermsCode();
	
	List<DataDictionaryValueEntity> result = dataDictionaryValueDao.queryDataDictionaryValueByTermsCode(code);
	Assert.assertTrue(result !=null);

	deleteById(addEntity.getId());
    }

    /**
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象：
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryDataDictionaryValueByTermsCodeValueCode() {
	// 先添加一个实体，用于测试
	DataDictionaryValueEntity addEntity = getEntity();
	dataDictionaryValueDao.addDataDictionaryValue(addEntity);

	try {
	    // 创建查询条件：
	    String code = addEntity.getTermsCode();
	    String valueCode = addEntity.getValueCode();

	    DataDictionaryValueEntity result = dataDictionaryValueDao
		    .queryDataDictionaryValueByTermsCodeValueCode(code,
			    valueCode);
	    Assert.assertTrue(result != null);
	} finally {
	    deleteById(addEntity.getId());
	}
    }

    

    private JdbcTemplate jdbc;

    private IDataDictionaryValueDao dataDictionaryValueDao;

    private DataDictionaryValueEntity dataDictionaryValueEntity;

    public DataDictionaryValueEntity getDataDictionaryValueEntity() {
	return dataDictionaryValueEntity;
    }

    public void setDataDictionaryValueEntity(
	    DataDictionaryValueEntity dataDictionaryValueEntity) {
	this.dataDictionaryValueEntity = dataDictionaryValueEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	
	initTest();
	dataDictionaryValueDao = (IDataDictionaryValueDao) SpringTestHelper.get()
		.getBeanByClass(DataDictionaryValueDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from T_BAS_DATA_DICTIONARY_VALUE");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    

    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_DATA_DICTIONARY_VALUE where id = '" + id + "'");
    }
    
    public static DataDictionaryValueEntity getEntity(){
	DataDictionaryValueEntity entity=(DataDictionaryValueEntity)createObj(DataDictionaryValueEntity.class.getName(), 2);
	entity.setTermsCode(System.currentTimeMillis()+"");
	entity.setValueCode(System.currentTimeMillis()+"");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setVirtualCode(id);
	entity.setCreateUser("087584-test-toDelete");
	return entity;
    }

    static int inLevel = 0;

    @SuppressWarnings("rawtypes")
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
			}if ("java.util.Collection".equals(paramClsName)) {
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
