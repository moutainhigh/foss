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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/api/server/sample/SampleDaoTest.java
 * 
 * FILE NAME        	: SampleDaoTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.sample;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.deppon.foss.module.base.baseinfo.api.server.sample.dao.ISampleDao;
import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

/**
 * 使用Oracle数据库测试时，需要：
 * 1. 修改spring-test.xml中，
 *    1.1 使用BasicDataSource配置DataSource；
 *    1.2 对于dbunitHelper的bean，注入schema的值；
 *    1.3 对于dbunitHelper的bean，properties属性的值，使用Oracle10DataTypeFactory的dataType；
 * 2. 在executeInitSql方法中执行create.sql脚本；
 * 3. 在executeDestorySql方法中的执行drop.sql脚本；
 * 
 * 使用HSQL数据库测试时，需要：
 * 1. 修改spring-test.xml中：
 *    1.1 使用jdbc:embedded-database定义DataSource；
 *    1.2 对于dbunitHelper的bean，取消schema的值的注入；
 *    1.3 对于dbunitHelper的bean，properties属性的值，使用HsqldbDataTypeFactory的dataType；
 * 2. 注释掉executeInitSql和executeDestorySql两个方法；
 * 
 * @author zhengwl
 *
 */
@Ignore
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/base/baseinfo/api/server/spring-test.xml" })
@TransactionConfiguration(defaultRollback =true)
public class SampleDaoTest extends DaoDBUnitSupportUnitTests {
	@Autowired
	ISampleDao sampleDao;
	
	@Override
	public void executeInitSql() {
		// 使用Oracle测试时，取消以下语句的注释，以创建供测试的表；
		//this.executeSqlScript("classpath:com/deppon/foss/module/base/baseinfo/api/server/sql/oracle/create.sql", true);
	}
	
	@Override
	public void executeDestorySql() {
		// 使用Oracle测试时，取消以下语句的注释，以删除供测试的表；
		//this.executeSqlScript("classpath:com/deppon/foss/module/base/baseinfo/api/server/sql/oracle/drop.sql", true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map getDatasetReplacements() {
		Map replacements = new HashMap();
		replacements.put("[SYSDATE]", new Date());
		
		Date date2 = new Date();
		date2.setYear(2012);
		date2.setMonth(11);
		date2.setDate(23);
		
		replacements.put("[JUDGEMENTDAY]", date2);
		
		return replacements;
	}
	
	/**
	 * 
	 * 可以使用rollback为false的注解来把CSV里的数据写到数据库里；
	 * @Rollback(false)
	 * 
	 * @author zhengwl
	 * @date 2012-10-17 上午10:31:52
	 */
	@Test
	@Rollback(true)
	public void initData() {
		
	}
	@Test
	public void testGetAll(){
		
		List<SampleEntity> samples = sampleDao.getAll();
		Assert.assertEquals(2, samples.size());
		
		SampleEntity sample1 = samples.get(0);
		Assert.assertEquals("1", sample1.getId());
		Assert.assertEquals("ZHENGWL", sample1.getName());
		
		SampleEntity sample2 = samples.get(1);
		Assert.assertEquals("2", sample2.getId());
		Assert.assertEquals("ZHENGWENLI", sample2.getName());
		
		Date date = sample2.getCreateDate();
		Assert.assertEquals(2012, date.getYear());
		Assert.assertEquals(12, date.getMonth() + 1);
		Assert.assertEquals(23, date.getDate());
	}
}
