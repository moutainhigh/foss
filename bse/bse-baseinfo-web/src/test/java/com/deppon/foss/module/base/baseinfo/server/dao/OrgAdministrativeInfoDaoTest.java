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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/OrgAdministrativeInfoDaoTest.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoDaoTest.java
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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门基本信息DAO 测试类
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class OrgAdministrativeInfoDaoTest {
    
    private static final Logger log = Logger.getLogger(OrgAdministrativeInfoDaoTest.class);
    
    /**
     * 测试根据行政区域查询组织机构方法
     * zhangedongping 
     */
    @Test
    public void testQueryOrgAdministrativeInfoByDistrictCode() {
	 
	 
	@SuppressWarnings({ "unused", "rawtypes" })
	List result = orgAdministrativeInfoDao
		.queryOrgAdministrativeInfoByDistrictCode("210", new Date());
	 
	 
    }
    
    /**
     * 测试新增方法
     */
    
    @Test
    public void testAddOrgAdministrativeInfo() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	try{
	    OrgAdministrativeInfoEntity result = orgAdministrativeInfoDao
		    .addOrgAdministrativeInfo(addEntity);
	    Assert.assertTrue(result != null);
	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 根据CODE删除
     */
    
    @Test
    public void testDeleteOrgAdministrativeInfo() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);
	try{
	    OrgAdministrativeInfoEntity result = orgAdministrativeInfoDao
		    .deleteOrgAdministrativeInfo(addEntity);
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
    public void testDeleteOrgAdministrativeInfoMore() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    String[] codes = { addEntity.getCode() };
	    OrgAdministrativeInfoEntity result = orgAdministrativeInfoDao
		    .deleteOrgAdministrativeInfoMore(codes, null);
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
    public void testUpdateOrgAdministrativeInfo() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    OrgAdministrativeInfoEntity result = orgAdministrativeInfoDao
		    .updateOrgAdministrativeInfo(addEntity);
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
    public void testQueryOrgAdministrativeInfoByCode() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    String code = addEntity.getCode();

	    OrgAdministrativeInfoEntity result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoByCode(code);
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
    public void testQueryOrgAdministrativeInfoBatchByCode() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    String[] codes = addEntity.getCode().split(",");

	    List<OrgAdministrativeInfoEntity> result = null;
	    result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoBatchByCode(codes);
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
    public void testQueryOrgAdministrativeInfoExactByEntity() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    addEntity = new OrgAdministrativeInfoEntity();

	    List<OrgAdministrativeInfoEntity> result = null;
	    result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoExactByEntity(addEntity, 0, 1);
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
    public void testQueryOrgAdministrativeInfoExactByEntityCount() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
	    List<OrgAdministrativeInfoEntity> entitys = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoByEntity(entity, 0, 1);
	    addEntity = entitys.get(0);
	    entity.setCode(addEntity.getCode());

	    long result = 0;
	    result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoExactByEntityCount(entity);
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
    public void testQueryOrgAdministrativeInfoByEntity() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
	    entity.setDeptArea(new BigDecimal(12));
	    List<OrgAdministrativeInfoEntity> result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoByEntity(entity, 0, 1);
	    Assert.assertNotNull(result);
	
	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 查询queryOrgAdministrativeInfoByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryOrgAdministrativeInfoByEntityCount() {
	// 先添加一个实体，用于测试
	OrgAdministrativeInfoEntity addEntity = getEntity();
	orgAdministrativeInfoDao.addOrgAdministrativeInfo(addEntity);

	try{
	    // 创建查询条件：
	    OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();

	    long result = orgAdministrativeInfoDao
		    .queryOrgAdministrativeInfoByEntityCount(entity);
	    Assert.assertTrue(result >= 0);
	}finally{
	    deleteById(addEntity.getId());
	}
    }

    /**
     * 下面是测试所需要的工具
     */
    private JdbcTemplate jdbc;

    private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;

    private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;

    public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
	return orgAdministrativeInfoEntity;
    }

    public void setOrgAdministrativeInfoEntity(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
	this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);

	initTest();
	orgAdministrativeInfoDao = (IOrgAdministrativeInfoDao) SpringTestHelper
		.get().getBeanByClass(OrgAdministrativeInfoDao.class);
    }

    @Test
    public void testQueryLastestOrgAdministrativeInfoByCode() {
	String code = "W01";
	OrgAdministrativeInfoEntity entity = orgAdministrativeInfoDao.queryLastestOrgAdministrativeInfoByCode(code);
	Assert.assertNotNull(entity);
    }
    
    
    public void teardown() {
	// initTest();
    }

    // 初始化表数据，将脏数据删除
    public void initTest() {
	// jdbc.execute("delete from T_BAS_ORG");
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
	jdbc.execute("delete from BSE.T_BAS_ORG where id = '" + id + "'");
	
    }

    public static OrgAdministrativeInfoEntity getEntity() {
	OrgAdministrativeInfoEntity entity = (OrgAdministrativeInfoEntity) createObj(
		OrgAdministrativeInfoEntity.class.getName());
	entity.setCode(System.currentTimeMillis() + "");
	entity.setActive(FossConstants.ACTIVE);
	entity.setBeginTime(new Date());
	entity.setEndTime(new Date(NumberConstants.ENDTIME));
	entity.setDeptArea(new BigDecimal(1));

	entity.setSubsidiaryCode("ZGS001");
	String id = UUIDUtils.getUUID();
	entity.setId(id);
	entity.setCreateUser("087584-test-needDelete");
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
			    o2 = "Y";
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
			    } else if("boolean".equals(paramClsName)){
				o2 =new Boolean("True") ;
			    }else if("java.lang.Long".equals(paramClsName)){
				o2 =System.currentTimeMillis();
			    }else if ("java.lang.Boolean".equals(paramClsName)) {
				o2 = new Boolean("True");
			    } else if ("java.math.BigDecimal".equals(paramClsName)) {
				o2 = new BigDecimal(123);
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
	    log.error(cls + "\n" + inLevel, e);
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
