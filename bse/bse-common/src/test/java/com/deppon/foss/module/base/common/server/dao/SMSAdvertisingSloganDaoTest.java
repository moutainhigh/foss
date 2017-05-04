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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/server/dao/SMSAdvertisingSloganDaoTest.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganDaoTest.java
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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.common.api.server.dao.ISMSAdvertisingSloganDao;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;


/**
 * 短信广告语Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-17 下午4:11:44 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-17 下午4:11:44
 * @since
 * @version
 */
public class SMSAdvertisingSloganDaoTest {
    
    private JdbcTemplate jdbc;
    
    private ISMSAdvertisingSloganDao sMSAdvertisingSloganDao;
    private SMSSloganEntity entity = new SMSSloganEntity();

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午4:11:44
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	sMSAdvertisingSloganDao = SpringTestHelper.get().getBeanByInterface(ISMSAdvertisingSloganDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-10-17 下午4:11:44
     * @throws java.lang.Exception
     * @see
     */
    /*@After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_SLOGAN");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganDao#addSMSAdvertisingSlogan(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)}.
     */
    @Ignore
    @Test
    public void testAddSMSAdvertisingSlogan() {
	
	entity.setCreateUser("张龙");
	entity.setSloganCode("START_SMS_ADV1");
	entity.setSloganName("出发短信广告");
	entity.setSloganSort("SLOGAN_TYPE_SMS");
	entity.setSubSystem("接送货");
	entity.setSubSystemModule("开单收货");
	entity.setContent("发货积分换礼品！");
	
	int result = sMSAdvertisingSloganDao.addSMSAdvertisingSlogan(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganDao#deleteSMSAdvertisingSloganByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteSMSAdvertisingSloganByCode() {
	
	String[] codes = {"df12435b-8095-41fc-9842-9d0c54e67a31","b8eddbb9-c0c3-4300-8858-d705bca5cc0f"};
	int result = sMSAdvertisingSloganDao.deleteSMSAdvertisingSloganByCode(codes, "李四");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganDao#updateSMSAdvertisingSlogan(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateSMSAdvertisingSlogan() {
	
	entity.setCreateUser("张飞");
	entity.setSloganCode("START_SMS_ADV1");
	entity.setSloganName("出发短信广告所属");
	entity.setSubSystem("接送货11");
	entity.setSubSystemModule("开单收货11");
	entity.setContent("发货积分换礼品111！");
	entity.setVirtualCode("96c9ec5c-3222-44e8-b98b-f95987ffd701");
	
	int result = sMSAdvertisingSloganDao.updateSMSAdvertisingSlogan(entity);
	Assert.assertTrue(result == 1);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganDao#querySMSAdvertisingSlogans(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQuerySMSAdvertisingSlogans() {
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("1");
	List<SMSSloganEntity> list = sMSAdvertisingSloganDao.querySMSAdvertisingSlogans(entity,10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.common.server.dao.impl.SMSAdvertisingSloganDao#getCount(com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("1");
	Long result = sMSAdvertisingSloganDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }
    
    /**
     * <p>根据短信广告语代码查询短信广告语</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午5:17:03
     * @see
     */
    @Ignore
    @Test
    public void testQuerySmsSloganBySmsSloganCode(){
	SMSSloganEntity entity = sMSAdvertisingSloganDao.querySmsSloganBySmsSloganCode("11",null);
	
	Assert.assertNotNull(entity);
    }

}
