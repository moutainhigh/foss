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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/AdministrativeRegionsDaoTest.java
 * 
 * FILE NAME        	: AdministrativeRegionsDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.AdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 行政区域 DAO测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class AdministrativeRegionsDaoTest {
    /**
     * 测试新增方法
     */
    @Test
    public void testAddAdministrativeRegions() {
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	AdministrativeRegionsEntity result = loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE删除
     */
    @Test
    public void testDeleteAdministrativeRegions(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);

	AdministrativeRegionsEntity result = loadAndUnloadSquadDao.deleteAdministrativeRegions(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testDeleteAdministrativeRegionsMore(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity =  getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);

	String[] codes = {addEntity.getCode()};
	AdministrativeRegionsEntity result = loadAndUnloadSquadDao.deleteAdministrativeRegionsMore(codes,null);
	Assert.assertTrue(result != null);
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testUpdateAdministrativeRegions(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	AdministrativeRegionsEntity result = loadAndUnloadSquadDao.updateAdministrativeRegions(addEntity);
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
    public void testQueryAdministrativeRegionsByCode(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getCode();
	
	AdministrativeRegionsEntity  result = loadAndUnloadSquadDao.queryAdministrativeRegionsByCode(code);
	Assert.assertTrue(result !=null);	
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsBatchByCode(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	String[] codes = addEntity.getCode().split(",");
	
	List<AdministrativeRegionsEntity>  result = null;
	result = loadAndUnloadSquadDao.queryAdministrativeRegionsBatchByCode(codes);
	Assert.assertTrue(result !=null);	
    }
    
    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsExactByEntity(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	AdministrativeRegionsEntity entity=new AdministrativeRegionsEntity();
	entity.setCode(addEntity.getCode());
	
	List<AdministrativeRegionsEntity>  result = null;
	result = loadAndUnloadSquadDao.queryAdministrativeRegionsExactByEntity(entity,1,0);
	Assert.assertTrue(result !=null);	
    }
    
    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsExactByEntityCount(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	AdministrativeRegionsEntity entity=new AdministrativeRegionsEntity();
	entity.setCode(addEntity.getCode());
	
	long  result = 0;
	result = loadAndUnloadSquadDao.queryAdministrativeRegionsExactByEntityCount(entity);
	Assert.assertTrue(result >= 0);	
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsByEntity(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	
	List<AdministrativeRegionsEntity>  result = loadAndUnloadSquadDao.queryAdministrativeRegionsByEntity(entity,1,0);
	Assert.assertTrue(result !=null);
    }

    /**
     * 查询queryAdministrativeRegionsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsByEntityCount(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	// 创建查询条件：
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setActive(FossConstants.ACTIVE);
	
	long result = loadAndUnloadSquadDao.queryAdministrativeRegionsByEntityCount(entity);
	Assert.assertTrue(result >=0);
    }


    /**
     * 下面是特殊查询
     */
    
    /**
     * 根据行政区域上级编码查询行政区域列表
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    @Test
    public void testQueryAdministrativeRegionsByParentDistrictCode(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	String code = System.currentTimeMillis()+"";
	addEntity.setParentDistrictCode(code);
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	List<AdministrativeRegionsEntity>  result = null;
	result = loadAndUnloadSquadDao.queryAdministrativeRegionsByParentDistrictCode(code);
	Assert.assertTrue(result !=null);
    }
    
    /**
     * 查询行政区域根结点
     * 
     * @author 087584-lijun
     * @date 2012-10-12 上午10:19:29
     */
    @Test
    public void testQueryRoot(){
	// 先添加一个实体，用于测试
	AdministrativeRegionsEntity addEntity = getEntity();
	addEntity.setParentDistrictCode(null);
	loadAndUnloadSquadDao.addAdministrativeRegions(addEntity);
	
	List<AdministrativeRegionsEntity>  result = null;
	result = loadAndUnloadSquadDao.queryRoot();
	Assert.assertTrue(result !=null);
    }


 
    
    
    
    /**
     * 下面是测试所需要的工具
     */
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;

    private IAdministrativeRegionsDao loadAndUnloadSquadDao;

    private AdministrativeRegionsEntity loadAndUnloadSquadEntity;

    public AdministrativeRegionsEntity getAdministrativeRegionsEntity() {
	return loadAndUnloadSquadEntity;
    }

    public void setAdministrativeRegionsEntity(
	    AdministrativeRegionsEntity loadAndUnloadSquadEntity) {
	this.loadAndUnloadSquadEntity = loadAndUnloadSquadEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	
	initTest();	
	loadAndUnloadSquadDao = (IAdministrativeRegionsDao) SpringTestHelper.get()
		.getBeanByClass(AdministrativeRegionsDao.class);
    }

    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest(){
	// jdbc.execute("delete from t_bas_district");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    
    public static AdministrativeRegionsEntity getEntity(){
	AdministrativeRegionsEntity entity=(AdministrativeRegionsEntity)createObj(AdministrativeRegionsEntity.class.getName(), 2);
	entity.setCode(System.currentTimeMillis()+"");
	entity.setActive(FossConstants.ACTIVE);
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584");
	entity.setPinYin("AAAAAA");
	entity.setPinYinAbbr("AAA");
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
