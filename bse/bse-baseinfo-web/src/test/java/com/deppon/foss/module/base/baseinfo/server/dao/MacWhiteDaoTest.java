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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/MacWhiteDaoTest.java
 * 
 * FILE NAME        	: MacWhiteDaoTest.java
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
package com.deppon.foss.module.base.baseinfo.server.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IMacWhiteDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;


/**
 * MAC地址白名单Dao单元测试
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-3-12 下午1:43:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 下午1:43:11
 * @since
 * @version
 */
public class MacWhiteDaoTest {

    private JdbcTemplate jdbc;
    
    private IMacWhiteDao macWhiteDao;
    
    /**
     * <p>执行前加载程序</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 下午1:45:47
     * @throws Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	macWhiteDao = SpringTestHelper.get().getBeanByInterface(IMacWhiteDao.class);
    }

    /**
     * <p>执行之后处理程序</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 下午1:43:11
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.MacWhiteDao#queryMacAddressByMac(java.lang.String)}.
     */
    @Test
    public void testQueryMacAddressByMac() {
	boolean isFlag = macWhiteDao.queryMacAddressByMac("werw2424");
	
	Assert.assertTrue(isFlag);
    }

}
