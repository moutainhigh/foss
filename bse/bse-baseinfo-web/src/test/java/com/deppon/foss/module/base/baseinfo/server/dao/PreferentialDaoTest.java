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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/PreferentialDaoTest.java
 * 
 * FILE NAME        	: PreferentialDaoTest.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户优惠信息Dao单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-22 下午3:45:59 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-22 下午3:45:59
 * @since
 * @version
 */
public class PreferentialDaoTest {

    private JdbcTemplate jdbc;
    
    private IPreferentialDao preferentialDao;
    private PreferentialEntity entity = new PreferentialEntity();
    
    //编码
    private String id;
    
    private BigDecimal crmId;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	preferentialDao = SpringTestHelper.get().getBeanByInterface(IPreferentialDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午5:15:34
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from BSE.T_BAS_CUS_PREFERENTIAL where id = '"+id+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PreferentialDao#addPreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)}.
     */
    @Ignore
    @Test
    public void testAddPreferential() {
	//生成UUID
	id = UUIDUtils.getUUID();
	crmId = new BigDecimal(201202339);
	entity.setId(id);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCusBargainId(new BigDecimal(223));
	entity.setEffectiveDate(new Date());
	entity.setExpirationDate(new Date());
	entity.setCrmId(crmId);
	
	
	int result = preferentialDao.addPreferential(entity);
	
	Assert.assertTrue(result > 0);		
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PreferentialDao#deletePreferentialByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeletePreferentialByCode() {
	//添加一条数据
	testAddPreferential();
	
	int result = preferentialDao.deletePreferentialByCode(crmId, "王辉");
	Assert.assertTrue(result > 0);		
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PreferentialDao#updatePreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)}.
     */
    @Ignore
    @Test
    public void testUpdatePreferential() {
	//添加一条数据
	testAddPreferential();
	entity.setId(id);
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setCusBargainId(new BigDecimal(143));
	entity.setCrmId(new BigDecimal(201202339));
	int result = preferentialDao.updatePreferential(entity);
	
	Assert.assertTrue(result > 0);
    }
    
    /**
     * <p>根据客户编码、时间查询客户当前时间内的客户优惠信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 上午11:46:20
     * @see
     */
    @Ignore
    @Test
    public void testQueryPreferentialInfo() {
	PreferentialEntity entity = preferentialDao.queryPreferentialInfo("400181479", new Date(2012,11,11,12,13,00));
	
	Assert.assertNotNull(entity);
    }
    
    @Ignore
    @Test
    public void testqueryPreferentialInfoDtosByCustCode(){
	List<PreferentialInfoDto> list = preferentialDao.queryPreferentialInfoDtosByCustCode("400331816");
	
	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>根据客户编码查询客户合同优惠信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-23 下午1:53:16
     * @see
     */
    @Ignore
    @Test
    public void testQueryPreferentialInfoDtosByCustCode(){
	List<PreferentialInfoDto> list = preferentialDao.queryPreferentialInfoDtosByCustCode("400181479");
	
	Assert.assertTrue(list.size() > 0);
    }
    
}
