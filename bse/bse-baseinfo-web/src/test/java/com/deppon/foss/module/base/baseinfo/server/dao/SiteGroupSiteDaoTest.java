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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/SiteGroupSiteDaoTest.java
 * 
 * FILE NAME        	: SiteGroupSiteDaoTest.java
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

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SiteGroupDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 站点组站点Dao单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-31 下午5:34:21 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-31 下午5:34:21
 * @since
 * @version
 */
public class SiteGroupSiteDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ISiteGroupSiteDao siteGroupSiteDao;
    private SiteGroupSiteEntity entity = new SiteGroupSiteEntity();
    
    //虚拟编码
    private String virtualCode;
    //Id
    private String id;
    //站点组ID
    private String groupId;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	siteGroupSiteDao = SpringTestHelper.get().getBeanByInterface(ISiteGroupSiteDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-31 下午5:34:21
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from T_BAS_SITEGROUP_SITE");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupSiteDao#addSiteGroupSite(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)}.
     */
    @Ignore
    @Test
    public void testAddSiteGroupSite() {
	
	id = UUIDUtils.getUUID();
	virtualCode = id;
	groupId = "00001";
	entity.setId(id);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setSeq(1);
	entity.setSite("广州外场");
	entity.setParentOrgCode(groupId);
	entity.setVirtualCode(virtualCode);
	int result = siteGroupSiteDao.addSiteGroupSite(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupSiteDao#deleteSiteGroupSiteByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSiteGroupSiteByCode() {
	//添加一条数据
	testAddSiteGroupSite();
	
	String[] codes = {virtualCode};
	int result = siteGroupSiteDao.deleteSiteGroupSiteByCode(codes, "陈飞");
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupSiteDao#updateSiteGroupSite(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSiteGroupSite() {
	//添加一条数据
	testAddSiteGroupSite();
	
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setSeq(2);
	entity.setSite("广州外场");
	entity.setVirtualCode(virtualCode);
	int result = siteGroupSiteDao.updateSiteGroupSite(entity);
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupSiteDao#querySiteGroupSites(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySiteGroupSites() {
	//添加一条数据
	testAddSiteGroupSite();
	entity = new SiteGroupSiteEntity();
	entity.setActive(FossConstants.ACTIVE);
	List<SiteGroupSiteEntity> list = siteGroupSiteDao.querySiteGroupSites(entity,10, 0);
	Assert.assertTrue(list.size()>0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.SiteGroupSiteDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条数据
	testAddSiteGroupSite();
	entity = new SiteGroupSiteEntity();
	entity.setActive(FossConstants.ACTIVE);
	Long result = siteGroupSiteDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);		
    }
    
    @Ignore
    @Test
    public void testQueryAllSitesByCode(){
	//添加一条数据
	testAddSiteGroupSite();
	
	List<SiteGroupSiteEntity> list = siteGroupSiteDao.queryAllSitesByCode(groupId);
	
	Assert.assertTrue(list.size() > 0);
    }
   

}
