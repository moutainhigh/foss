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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/EmployeeDaoTest.java
 * 
 * FILE NAME        	: EmployeeDaoTest.java
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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.EmployeeDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * DAO测试类
 * @author 087584-foss-lijun
 * @date 2012-10-16 上午9:36:37
 */
@Ignore
public class EmployeeDaoTest {
    /**
     * 测试新增方法
     */
    
    @Test
    public void testAddEmployee() {
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	EmployeeEntity result = employeeDao.addEmployee(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE删除
     */
    
    @Test
    public void testDeleteEmployee(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);

	// 创建查询条件
	EmployeeEntity entity=new EmployeeEntity();
	List<EmployeeEntity> entitys=employeeDao.queryEmployeeByEntity(entity , 0, 1);
	addEntity=entitys.get(0);
	
	EmployeeEntity result = employeeDao.deleteEmployee(addEntity);
	Assert.assertTrue(result != null);
    }

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testDeleteEmployeeMore(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity =  getEntity();
	employeeDao.addEmployee(addEntity);

	String[] codes = {addEntity.getEmpCode()};
	EmployeeEntity result = employeeDao.deleteEmployeeMore(codes,null);
	Assert.assertTrue(result != null);
    }

    /**
     * 更新
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testUpdateEmployee(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);

	// 创建查询条件
	EmployeeEntity entity=new EmployeeEntity();
	List<EmployeeEntity> entitys=employeeDao.queryEmployeeByEntity(entity , 0, 1);
	addEntity=entitys.get(0);
	
	EmployeeEntity result = employeeDao.updateEmployee(addEntity);
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
    public void testQueryEmployeeByEmpCode(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	String code=addEntity.getEmpCode();
	
	EmployeeEntity  result = employeeDao.queryEmployeeByEmpCode(code);
	Assert.assertTrue(result != null);	
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryEmployeeBatchByEmpCode(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	EmployeeEntity entity=new EmployeeEntity();
	List<EmployeeEntity> entitys=employeeDao.queryEmployeeByEntity(entity , 0, 1);
	addEntity=entitys.get(0);
	String[] codes = addEntity.getEmpCode().split(",");
	
	List<EmployeeEntity>  result = null;
	result = employeeDao.queryEmployeeBatchByEmpCode(codes);
	Assert.assertTrue(result != null);	
    }
    
    /**
     * 根据实体精确查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryEmployeeExactByEntity(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	EmployeeEntity entity=new EmployeeEntity();
	List<EmployeeEntity> entitys=employeeDao.queryEmployeeByEntity(entity , 0, 1);
	addEntity=entitys.get(0);
	entity.setEmpCode(addEntity.getEmpCode());
	
	List<EmployeeEntity>  result = null;
	result = employeeDao.queryEmployeeExactByEntity(entity,0, 1);
	Assert.assertTrue(result != null);	
    }
    
    /**
     * 根据实体精确查询-总条数
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryEmployeeExactByEntityCount(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	EmployeeEntity entity=new EmployeeEntity();
	List<EmployeeEntity> entitys=employeeDao.queryEmployeeByEntity(entity , 0, 1);
	addEntity=entitys.get(0);
	entity.setEmpCode(addEntity.getEmpCode());
	
	long  result = 0;
	result = employeeDao.queryEmployeeExactByEntityCount(entity);
	Assert.assertTrue(result >= 0);	
    }

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryEmployeeByEntity(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	EmployeeEntity entity = new EmployeeEntity();
	
	List<EmployeeEntity> result = employeeDao.queryEmployeeByEntity(entity,0, 1);
	Assert.assertTrue(result != null);
    }

    /**
     * 查询queryEmployeeByEntity返回的记录总数,用于分页
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午5:52:35
     */
    
    @Test
    public void testQueryEmployeeByEntityCount(){
	// 先添加一个实体，用于测试
	EmployeeEntity addEntity = getEntity();
	employeeDao.addEmployee(addEntity);
	
	// 创建查询条件：
	EmployeeEntity entity = new EmployeeEntity();
	entity.setActive("1");
	
	long result = employeeDao.queryEmployeeByEntityCount(entity);
	Assert.assertTrue(result >=0);
    }
    

    
    
    /**
     * 下面是测试所需要的工具
     */
    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;

    private IEmployeeDao employeeDao;

    private EmployeeEntity employeeEntity;

    public EmployeeEntity getEmployeeEntity() {
	return employeeEntity;
    }

    public void setEmployeeEntity(
	    EmployeeEntity employeeEntity) {
	this.employeeEntity = employeeEntity;
    }

    @Before
    public void setup() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	
	initTest();
	employeeDao = (IEmployeeDao) SpringTestHelper.get()
		.getBeanByClass(EmployeeDao.class);
    }

    public void teardown() {
	// initTest();
    }

    public void initTest(){
	// jdbc.execute("delete from T_BAS_EMPLOYEE");
    }

    /**
     * 下面全部是测试工具
     * ================================================================
     */
    public static void main(String[] args) {
	
    }
    
    public static EmployeeEntity getEntity(){
	EmployeeEntity entity=(EmployeeEntity)createObj(EmployeeEntity.class.getName());
	entity.setEmpCode(System.currentTimeMillis()+"");
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
			    String paramClsName=innerClasses[0].getName();
			    if("java.util.Collection".equals(paramClsName)){
				o2 =new ArrayList<Object>();
			    }else if("java.lang.Integer".equals(paramClsName)){
				o2 =new Integer(1);
			    }else if("java.lang.Byte".equals(paramClsName)){
				o2 =new Byte("1");
			    }else if("boolean".equals(paramClsName)){
				o2 =new Boolean("True") ;
			    }else if("java.lang.Long".equals(paramClsName)){
				o2 =System.currentTimeMillis();
			    }else if("java.lang.Boolean".equals(paramClsName)){
				o2 =new Boolean("True") ;
			    }else{
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
