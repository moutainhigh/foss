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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/dao/ConfigurationParamsDaoTest.java
 * 
 * FILE NAME        	: ConfigurationParamsDaoTest.java
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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.server.dao.impl.ConfigurationParamsDao;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 数据字典 测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class ConfigurationParamsDaoTest {

    private JdbcTemplate jdbc;

    private IConfigurationParamsDao configurationParamsDao;

    private ConfigurationParamsEntity configurationParamsDaoEntity;

    public ConfigurationParamsEntity getConfigurationParamsEntity() {
	return configurationParamsDaoEntity;
    }

    public void setConfigurationParamsEntity(
	    ConfigurationParamsEntity configurationParamsDaoEntity) {
	this.configurationParamsDaoEntity = configurationParamsDaoEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	configurationParamsDao = (IConfigurationParamsDao) SpringTestHelper.get()
		.getBeanByClass(ConfigurationParamsDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from T_BAS_SYS_CONFIG");
    }
    /**
     * 下面全部是测试方法：
     * =======================================================================
     */

    /**
     * 测试新增方法
     */
    
    @Test
    public void testAddConfigurationParams() {
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	ConfigurationParamsEntity result = configurationParamsDao.addConfigurationParams(addEntity);
	Assert.assertTrue(result !=null);
	deleteById(addEntity.getId());
    }

    /**
     * 根据CODE删除
     */
    
    @Test
    public void testDeleteConfigurationParams(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);

	ConfigurationParamsEntity result = configurationParamsDao.deleteConfigurationParams(addEntity);
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
    public void testDeleteConfigurationParamsMore(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity =  getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	String[] virtualCode = {addEntity.getVirtualCode()};
	ConfigurationParamsEntity result = configurationParamsDao.deleteConfigurationParamsMore(virtualCode,"087584-test-toDelete");
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
    public void testUpdateConfigurationParams(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);

	// 创建查询条件
	ConfigurationParamsEntity entity=new ConfigurationParamsEntity();
	addEntity=entity;
	
	ConfigurationParamsEntity result = configurationParamsDao.updateConfigurationParams(addEntity);
	Assert.assertTrue(result !=null);	
	deleteById(addEntity.getId());
    }

    
    /**
     * 以下全为查询
     */


    /**
     * 根据virtualCode查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryConfigurationParamsByVirtualCode(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getVirtualCode();
	
	ConfigurationParamsEntity  result = configurationParamsDao.queryConfigurationParamsByVirtualCode(code);
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
    public void testQueryConfigurationParamsBatchByCode(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	String[] codes = addEntity.getCode().split(",");
	List<ConfigurationParamsEntity>  result = null;
	result = configurationParamsDao.queryConfigurationParamsBatchByVirtualCode(codes);
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
    public void testQueryConfigurationParamsExactByEntity(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	// 创建查询条件：
	ConfigurationParamsEntity entity=new ConfigurationParamsEntity();
	addEntity=entity;
	entity.setCode(addEntity.getCode());
	
	List<ConfigurationParamsEntity>  result = null;
	result = configurationParamsDao.queryConfigurationParamsExactByEntity(entity,1, 0);
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
    public void testQueryConfigurationParamsExactByEntityCount(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	// 创建查询条件：
	ConfigurationParamsEntity entity=new ConfigurationParamsEntity();
	addEntity=entity;
	entity.setCode(addEntity.getCode());
	
	long  result = 0;
	result = configurationParamsDao.queryConfigurationParamsExactByEntityCount(entity);
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
    public void testQueryConfigurationParamsByEntity(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	// 创建查询条件：
	ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
	
	List<ConfigurationParamsEntity>  result = configurationParamsDao.queryConfigurationParamsByEntity(entity,1, 0);
	Assert.assertTrue(result !=null);
	deleteById(addEntity.getId());
    }

    /**
     * 查询queryConfigurationParamsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryConfigurationParamsByEntityCount(){
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	configurationParamsDao.addConfigurationParams(addEntity);
	
	// 创建查询条件：
	ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
	entity.setActive(FossConstants.ACTIVE);
	
	long result = configurationParamsDao.queryConfigurationParamsByEntityCount(entity);
	Assert.assertTrue(result >=0);
	deleteById(addEntity.getId());
    }

    
    
    
    /**
     * 下面为特殊查询
     */
    
    /**
     * 精确查询
     * 通过 ConfigurationParams的CODE和OrgAdministrativeInfo的CODE查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryConfigurationParamsByOrgCode(){	
	// 创建查询条件：
	String  orgCode=System.currentTimeMillis()+"";
	jdbc.execute("insert into t_bas_org(id,code,name,active) " +
			"values('"+orgCode+"','"+orgCode+"','"+orgCode+"','"+FossConstants.ACTIVE+"')");
	
	// 先添加一个实体，用于测试
	ConfigurationParamsEntity addEntity = getEntity();
	addEntity.setOrgCode(orgCode);
	addEntity.setCode(System.currentTimeMillis()+"");
	configurationParamsDao.addConfigurationParams(addEntity);

	ConfigurationParamsEntity  result = configurationParamsDao.queryConfigurationParamsByOrgCode(addEntity.getCode(),addEntity.getOrgCode());
	Assert.assertTrue(result !=null);	
	deleteById(addEntity.getId());
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public void deleteById(String id){
	jdbc.execute("delete from BSE.T_BAS_SYS_CONFIG where id = '" + id + "'");
    }
    public static void main(String[] args) {
	
    }
    
    public static ConfigurationParamsEntity getEntity(){
	ConfigurationParamsEntity entity=(ConfigurationParamsEntity)createObj(ConfigurationParamsEntity.class.getName(), 2);
	entity.setCode(System.currentTimeMillis()+"");
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
	    inLevel =level;
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
