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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/LeasedWhitelistAuditDaoTest.java
 * 
 * FILE NAME        	: LeasedWhitelistAuditDaoTest.java
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
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来测试交互“外请白名单（司机、车）”的数据库对应数据访问DAO接口：SUC-750、SUC-104 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-31 下午2:33:51</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-31 下午2:33:51
 * @since
 * @version
 */
public class LeasedWhitelistAuditDaoTest {
    
    //“外请白名单（司机、车）”数据访问类
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;

    //JDBC辅助数据访问类
    private JdbcTemplate jdbc;
    
    //“外请白名单（司机、车）”对象
    private WhitelistAuditEntity whitelistAudit = new WhitelistAuditEntity();
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	leasedWhitelistAuditDao = SpringTestHelper.get().getBeanByInterface(ILeasedWhitelistAuditDao.class);
    }

    @After
    public void tearDown() throws Exception {
	//jdbc.execute("delete BSE.T_BAS_WHITELIST_AUDIT");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#addLeasedWhitelistAudit(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedWhitelistAudit() {
	whitelistAudit.setDriverName("赵六");
	whitelistAudit.setDriverPhone("12536521458");
	whitelistAudit.setDriverIdCard("666626598541236541");
	whitelistAudit.setQualification("CYZG-0980");
	whitelistAudit.setDriverLicense("JSZ-687934");
	whitelistAudit.setDrivingLicense("XSZ-00001");
	whitelistAudit.setEndTimeDrivingLic(new Date());
	whitelistAudit.setCurrentApplication("未入库");
	whitelistAudit.setStatus("不可用");
	whitelistAudit.setApplyNotes("测试司机数据");
	whitelistAudit.setApplyUser("100847-foss-GaoPeng");
	whitelistAudit.setApplyTime(new Date());
	whitelistAudit.setCreateUser("100847-foss-GaoPeng");
	
	int result = leasedWhitelistAuditDao.addLeasedWhitelistAudit(whitelistAudit);
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#addLeasedWhitelistAuditBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testAddLeasedWhitelistAuditBySelective() {
	whitelistAudit.setDriverName("刘七");
	whitelistAudit.setDriverIdCard("26534568254685213");
	whitelistAudit.setQualification("CYZG-0000");
	whitelistAudit.setDriverLicense("JSZ-555555");
	whitelistAudit.setCurrentApplication(null);
	whitelistAudit.setStatus("可用");
	whitelistAudit.setApplyNotes("测试司机数据");
	whitelistAudit.setApplyUser("100847-foss-GaoPeng");
	whitelistAudit.setApplyTime(new Date());
	whitelistAudit.setCreateUser("100847-foss-GaoPeng");
	
	whitelistAudit.setDriverPhone(null);
	
	int result = leasedWhitelistAuditDao.addLeasedWhitelistAuditBySelective(whitelistAudit);
	Assert.assertTrue(result > 0);
	
	LeasedWhitelistAuditDao leasedWhitelistAuditDao = (LeasedWhitelistAuditDao) this.leasedWhitelistAuditDao;
	WhitelistAuditEntity whitelistAuditTemp = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit);
	Assert.assertNotNull(whitelistAuditTemp);
	Assert.assertNull(whitelistAuditTemp.getDriverPhone());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#deleteLeasedWhitelistAudit(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteLeasedWhitelistAudit() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	int result = leasedWhitelistAuditDao.deleteLeasedWhitelistAudit(whitelistAudit.getId());
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#updateLeasedWhitelistAuditBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedWhitelistAuditBySelective() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setDriverName(null);
	
	int result = leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(whitelistAudit);
	Assert.assertTrue(result > 0);
	
	LeasedWhitelistAuditDao leasedWhitelistAuditDao = (LeasedWhitelistAuditDao) this.leasedWhitelistAuditDao;
	WhitelistAuditEntity whitelistAuditTemp = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit);
	Assert.assertNotNull(whitelistAuditTemp);
	Assert.assertNotNull(whitelistAuditTemp.getDriverName());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#updateLeasedWhitelistAudit(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateLeasedWhitelistAudit() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setDriverName(null);
	
	int result = leasedWhitelistAuditDao.updateLeasedWhitelistAudit(whitelistAudit);
	Assert.assertTrue(result > 0);
	
	LeasedWhitelistAuditDao leasedWhitelistAuditDao = (LeasedWhitelistAuditDao) this.leasedWhitelistAuditDao;
	WhitelistAuditEntity whitelistAuditTemp = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit); 
	Assert.assertNotNull(whitelistAuditTemp);
	Assert.assertNull(whitelistAuditTemp.getDriverName());
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#queryLeasedWhitelistAudit(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedWhitelistAudit() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	WhitelistAuditEntity whitelistAuditTemp = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit);
	Assert.assertNotNull(whitelistAuditTemp);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#queryLeasedWhitelistAuditRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedWhitelistAuditRecordCountBySelectiveCondition() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setActive(FossConstants.ACTIVE);
	
	long recordCount = leasedWhitelistAuditDao.queryLeasedWhitelistAuditRecordCountBySelectiveCondition(whitelistAudit);
	Assert.assertTrue(recordCount > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#queryLeasedWhitelistAuditListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedWhitelistAuditListBySelectiveCondition() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setActive(FossConstants.ACTIVE);
	
	List<WhitelistAuditEntity> whitelistAuditTemps = leasedWhitelistAuditDao.queryLeasedWhitelistAuditListBySelectiveCondition(whitelistAudit, 0, 2);
	Assert.assertNotNull(whitelistAuditTemps);
	Assert.assertTrue(whitelistAuditTemps.size() > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#queryLeasedWhitelistApplyRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedWhitelistApplyRecordCountBySelectiveCondition() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setActive(FossConstants.ACTIVE);
	WhitelistAuditQueryDto whitelistAuditQueryDto = new WhitelistAuditQueryDto();
	BeanUtils.copyProperties(whitelistAudit, whitelistAuditQueryDto);
	long recordCount = leasedWhitelistAuditDao.queryLeasedWhitelistApplyRecordCountBySelectiveCondition(whitelistAuditQueryDto);
	Assert.assertTrue(recordCount > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.LeasedWhitelistAuditDao#queryLeasedWhitelistApplyListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryLeasedWhitelistApplyListBySelectiveCondition() {
	//准备数据
	testAddLeasedWhitelistAudit();
	
	whitelistAudit.setActive(FossConstants.ACTIVE);
	WhitelistAuditQueryDto whitelistAuditQueryDto = new WhitelistAuditQueryDto();
	BeanUtils.copyProperties(whitelistAudit, whitelistAuditQueryDto);
	List<WhitelistAuditEntity> whitelistAuditTemps = leasedWhitelistAuditDao.queryLeasedWhitelistApplyListBySelectiveCondition(whitelistAuditQueryDto, 0, 2);
	Assert.assertNotNull(whitelistAuditTemps);
	Assert.assertTrue(whitelistAuditTemps.size() > 0);
    }
}
