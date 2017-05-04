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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/server/dao/SMSTempleteDaoTest.java
 * 
 * FILE NAME        	: SMSTempleteDaoTest.java
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
package com.deppon.foss.module.base.common.server.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteDao;
import com.deppon.foss.module.base.common.api.server.dao.ISMSTempleteForDeptDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.common.api.shared.domain.TemplateAppOrgEntity;
import com.deppon.foss.module.base.common.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 短信模板Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-19 下午3:01:18 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-19 下午3:01:18
 * @since
 * @version
 */
public class SMSTempleteDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ISMSTempleteDao sMSTempleteDao;
    private SMSTemplateEntity entity = new SMSTemplateEntity();

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	sMSTempleteDao = SpringTestHelper.get().getBeanByInterface(ISMSTempleteDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-19 下午3:01:18
     * @throws java.lang.Exception
     * @see
     */
    /*@After
    public void tearDown() throws Exception {
	
	jdbc.execute("delete from T_BAS_SMS_TEMPLATE");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteDao#addSMSTemplete(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)}.
     */
    @Ignore
    @Test
    public void testAddSMSTemplete() {
	
	entity.setCreateUser("张龙");
	entity.setSmsCode("del_goods_notice");
	entity.setSmsName("接送货通知模板");
	entity.setSubSystem("接送货");
	entity.setSubSystemModule("开单收货");
	entity.setContent("您好，您的货物已到！");
	
	int result = sMSTempleteDao.addSMSTemplete(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteDao#deleteSMSTempleteByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSMSTempleteByCode() {
	
	String[] codes = {"75fe0ebc-57cf-4e72-8c79-000dedbafa34","57f4cd9b-c323-4ee6-9f5b-34a6d04d3d3d"};
	int result = sMSTempleteDao.deleteSMSTempleteByCode(codes, "成龙");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteDao#updateSMSTemplete(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSMSTemplete() {
	
	entity.setVirtualCode("57f4cd9b-c323-4ee6-9f5b-34a6d04d3d3d");
	entity.setModifyUser("张龙");
	entity.setSmsCode("del_goods_notice");
	entity.setSmsName("接送货通知模板11");
	entity.setSubSystem("接送货11");
	entity.setSubSystemModule("开单收货11");
	entity.setContent("您好，您的货物已到11！");
	
	int result = sMSTempleteDao.updateSMSTemplete(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteDao#querySMSTempletes(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySMSTempletes() {
	
	entity.setActive(FossConstants.ACTIVE);
	List<SMSTemplateEntity> list = sMSTempleteDao.querySMSTempletes(entity,10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSTempleteDao#getCount(com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	Long result = sMSTempleteDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }
    
    @Ignore
    @Test
    public void testQuerySmsBySmsCode(){
	SMSTemplateEntity entity = sMSTempleteDao.querySmsBySmsCode("del_goods_notice1",null);
	
	Assert.assertNotNull(entity);
    }

}
