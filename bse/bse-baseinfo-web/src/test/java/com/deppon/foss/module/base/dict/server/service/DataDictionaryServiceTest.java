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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/service/DataDictionaryServiceTest.java
 * 
 * FILE NAME        	: DataDictionaryServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;


import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.server.service.impl.DataDictionaryService;


/**
 * 数据字典测试类
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-10 上午11:05:41
 */
//@Ignore
public class DataDictionaryServiceTest {

    /**
     * 数据字典-词 跟值关联查询
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    
    @Test
    public void queryDataDictionaryByCond() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String user="087584-toDelete";
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY(id, TERMS_CODE, TERMS_NAME, CREATE_USER_CODE, ACTIVE) " +
		"values('"+a+"', '"+a+"', '"+a+"','"+user+"', 'Y')");
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE, CREATE_USER_CODE, ACTIVE) " +
		"values('"+b+"', '"+b+"', '"+a+"','"+b+"', '"+b+"', '"+user+"', 'Y')");
	
	List<DataDictionaryEntity> reusult = dataDictionary.queryDataDictionaryByCond(a, a, b, b, 0, 1);
	Assert.assertTrue(!CollectionUtils.isEmpty(reusult));
	deleteDataDictionaryById(a);
	deleteDataDictionaryValueById(b);
    }
    
    /**
     * 数据字典-词 跟值关联查询
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    
    @Test
    public void queryDataDictionaryByCondCount() {
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String user="087584-test";
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY(id, TERMS_CODE, TERMS_NAME, CREATE_USER_CODE, ACTIVE) " +
		"values('"+a+"', '"+a+"', '"+a+"','"+user+"', 'Y')");
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE, CREATE_USER_CODE, ACTIVE) " +
		"values('"+b+"', '"+b+"', '"+a+"','"+b+"', '"+b+"', '"+user+"', 'Y')");
	
	long reusult = dataDictionary.queryDataDictionaryByCondCount(a, a, b, b);
	Assert.assertTrue(reusult > 0);
    }

    
    
    
    /**
     * 下面是测试使用的工具方法
     */
    int count=0;
    private JdbcTemplate jdbc;
    private IDataDictionaryService dataDictionary;
    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteDataDictionaryById(String id){
	jdbc.execute("delete from BSE.T_BAS_DATA_DICTIONARY where id = '" + id + "'");
	
    }    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteDataDictionaryValueById(String id){
	jdbc.execute("delete from BSE.T_BAS_DATA_DICTIONARY_VALUE where id = '" + id + "'");
	
    }
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	dataDictionary = (IDataDictionaryService) SpringTestHelper.get().getBeanByClass(DataDictionaryService.class);
    }
    

    @After
    public void tearDown() throws Exception {
    }
    
}
