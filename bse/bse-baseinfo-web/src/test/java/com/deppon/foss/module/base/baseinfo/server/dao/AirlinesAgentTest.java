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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/AirlinesAgentTest.java
 * 
 * FILE NAME        	: AirlinesAgentTest.java
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

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-15 下午3:36:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-15 下午3:36:35
 * @since
 * @version
 */
public class AirlinesAgentTest{

    //“航空公司代理人”数据访问类
    private IAirlinesAgentDao airlinesAgentDao;
    
    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“航空公司代理人”对象
    private AirlinesAgentEntity airlinesAgent = new AirlinesAgentEntity();
    
    @Before
    public void setUp() {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	airlinesAgentDao = SpringTestHelper.get().getBeanByInterface(IAirlinesAgentDao.class);
    }
    
    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete T_BAS_AIRLINES_AGENT");
    }
    
    /**
     * <p>删除一个“航空公司代理人”</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午3:52:07
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#deleteAirlinesAgent(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteAirlinesAgent() {
    }

    /**
     * <p>新增一个“航空公司代理人”</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 下午3:52:07
     * @see Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#addAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)}.
     */
    @Ignore
    @Test
    public void testAddAirlinesAgent() {
	airlinesAgent.setId(UUIDUtils.getUUID()); 
	airlinesAgent.setAssemblyDeptId(UUIDUtils.getUUID());
	airlinesAgent.setAirlinesCode(UUIDUtils.getUUID());
	airlinesAgent.setAgentCode(UUIDUtils.getUUID());
	airlinesAgent.setAgentName("广州默默代理");
	
	airlinesAgent.setActive(FossConstants.ACTIVE);
	airlinesAgent.setCreateUser("gaopeng");
	airlinesAgent.setCreateDate(new Date());
	airlinesAgentDao.addAirlinesAgent(airlinesAgent);
	
	AirlinesAgentDao airlinesAgentDao = (AirlinesAgentDao) this.airlinesAgentDao;
	Assert.assertNotNull(airlinesAgentDao.queryAirlinesAgentBySelective(airlinesAgent));
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#addAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)}.
     */
    @Ignore
    @Test
    public void testAddAirlinesAgentBySelective() {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#queryAirlinesAgent(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryAirlinesAgent() {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#queryAirlinesAgentListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryAirlinesAgentListBySelectiveCondition() {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#updateAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateAirlinesAgentBySelective() {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirlinesAgentDao#updateAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateAirlinesAgent() {
    }
}
