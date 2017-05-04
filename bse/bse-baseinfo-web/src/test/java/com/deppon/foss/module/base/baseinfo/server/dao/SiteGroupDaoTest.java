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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/SiteGroupDaoTest.java
 * 
 * FILE NAME        	: SiteGroupDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 站点组DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-31 下午3:49:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-31 下午3:49:11
 * @since
 * @version
 */
public class SiteGroupDaoTest {
   
    private JdbcTemplate jdbc;
    
    private ISiteGroupDao siteGroupDao;
    private SiteGroupEntity entity = new SiteGroupEntity();
    
    //虚拟编码
    private String virtualCode;
    //Id
    private String id;


    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	siteGroupDao = SpringTestHelper.get().getBeanByInterface(ISiteGroupDao.class);
    }

    /**
     * 清空测试数据
     * @author 094463-foss-xieyantao
     * @date 2012-10-31 下午3:49:11
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from T_BAS_SITEGROUP");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupDao#addSiteGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity)}.
     */
    @Ignore
    @Test
    public void testAddSiteGroup() {
	id = UUIDUtils.getUUID();
	virtualCode = id;
	entity.setId(id);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setOrgCode("长沙外场");
	entity.setName("华南组");
	entity.setType("到达");
	entity.setVirtualCode(virtualCode);
	int result = siteGroupDao.addSiteGroup(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupDao#deleteSiteGroupByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSiteGroupByCode() {
	//添加一条数据
	testAddSiteGroup();
	
	String[] codes = {virtualCode};
	int result = siteGroupDao.deleteSiteGroupByCode(codes, "陈飞");
	
	Assert.assertTrue(result > 0);		
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupDao#updateSiteGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSiteGroup() {
	//添加一条数据
	testAddSiteGroup();
	
	entity.setModifyUser("张龙");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setOrgCode("长沙外场");
	entity.setName("华南组");
	entity.setType("到达");
	entity.setVirtualCode(virtualCode);
	int result = siteGroupDao.updateSiteGroup(entity);
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupDao#querySiteGroups(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySiteGroups() {
	//添加一条数据
//	testAddSiteGroup();
	entity.setActive(FossConstants.ACTIVE);
	List<SiteGroupEntity> list = siteGroupDao.querySiteGroups(entity,10, 0);
	Assert.assertTrue(list.size()>0);		
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条数据
	testAddSiteGroup();
	entity.setActive(FossConstants.ACTIVE);
	Long result = siteGroupDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);		
    }
    @Ignore
    @Test
    public void testQuerySiteGroupByCode(){
	//添加一条数据
	testAddSiteGroup();
	SiteGroupEntity entity = siteGroupDao.querySiteGroupByCode(virtualCode);
	
	Assert.assertNotNull(entity);
    }
    
    /**
     * <p>根据站点组站点（外场）的部门编码查询所属的站点组信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-7 下午3:34:29
     * @see
     */
    @Test
    public void testQuerySiteGroupsBySiteCode(){
	List<SiteGroupEntity> list = siteGroupDao.querySiteGroupsBySiteCode("W1200030122");
	
	Assert.assertTrue(list.size() > 0);
    }

}
