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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/service/DictDownloadServiceTest.java
 * 
 * FILE NAME        	: DictDownloadServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.dict.server.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.module.base.dict.api.server.service.IDictDownloadService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * 下载服务测试类
 * @author foss-zhujunyong
 * @date Oct 31, 2012 11:36:02 AM
 * @version 1.0
 */
//@Ignore
public class DictDownloadServiceTest {

    /**
     * 数据字典-词 数据下载 
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    
    @Test
    public void testDownloadDataDictionaryData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = dictDownloadService.downloadDataDictionaryData(client);
	List<DataDictionaryEntity> list = (List<DataDictionaryEntity>) (db.getObject());
	for (DataDictionaryEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 数据字典-值 数据下载 
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryValueData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    
    @Test
    public void testDownloadDataDictionaryValueData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = dictDownloadService.downloadDataDictionaryValueData(client);
	List<DataDictionaryValueEntity> list = (List<DataDictionaryValueEntity>) (db.getObject());
	for (DataDictionaryValueEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 系统配置参数 数据下载 
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadConfigurationParamsData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    
    @Test
    public void testDownloadConfigurationParamsData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = dictDownloadService.downloadConfigurationParamsData(client);
	List<ConfigurationParamsEntity> list = (List<ConfigurationParamsEntity>) (db.getObject());
	for (ConfigurationParamsEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }
    
    
    
    /**
     * 下面是测试使用的工具方法
     */

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IDictDownloadService dictDownloadService;
    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	dictDownloadService = (IDictDownloadService) SpringTestHelper.get().getBeanByClass(DictDownloadService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    
    
}
