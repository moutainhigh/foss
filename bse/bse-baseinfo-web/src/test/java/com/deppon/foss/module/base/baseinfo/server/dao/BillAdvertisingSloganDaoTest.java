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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/BillAdvertisingSloganDaoTest.java
 * 
 * FILE NAME        	: BillAdvertisingSloganDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 单据广告语测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-18 下午2:32:39 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-18 下午2:32:39
 * @since
 * @version
 */
public class BillAdvertisingSloganDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IBillAdvertisingSloganDao billAdvertisingSloganDao;
    private BillSloganEntity entity = new BillSloganEntity();
    
    private String id;
    private String virtualCode;

    /**
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 下午2:32:39
     * @throws java.lang.Exception
     * @see
     */
    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	billAdvertisingSloganDao = SpringTestHelper.get().getBeanByInterface(IBillAdvertisingSloganDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 下午2:32:39
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from T_BAS_SLOGAN");
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganDao#addBillAdvertisingSlogan(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testAddBillAdvertisingSlogan() {
	id = UUIDUtils.getUUID();
	virtualCode = id;
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("张龙");
	entity.setSloganCode("arriveSheetAdCode");
	entity.setSloganName("出发短信广告");
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	entity.setSubSystem("接送货");
	entity.setSubSystemModule("开单收货");
	entity.setContent("发货积分换礼品！");
	entity.setId(id);
	entity.setVirtualCode(virtualCode);
	entity.setCreateDate(new Date());
	entity.setModifyDate(new Date());
	
	int result = billAdvertisingSloganDao.addBillAdvertisingSlogan(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganDao#deleteBillAdvertisingSloganByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteBillAdvertisingSloganByCode() {
	String[] codes = {"7cccfdaa-b4b6-4e0d-8c12-53cbc23cd043","9a597bbb-aabc-4c49-b3a8-8c669f0640a2"};
	int result = billAdvertisingSloganDao.deleteBillAdvertisingSloganByCode(codes, "张飞");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganDao#updateBillAdvertisingSlogan(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateBillAdvertisingSlogan() {
	
	entity.setCreateUser("张飞");
	entity.setSloganCode("WAYBILL_ARRBILL_ADV1");
	entity.setSloganName("运单到达联广告");
	entity.setSubSystem("接送货11");
	entity.setSubSystemModule("打印到达联");
	entity.setContent("发货积分换礼品111！");
	entity.setVirtualCode("9a597bbb-aabc-4c49-b3a8-8c669f0640a2");
	
	int result = billAdvertisingSloganDao.updateBillAdvertisingSlogan(entity);
	Assert.assertTrue(result == 1);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganDao#queryBillAdvertisingSlogans(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryBillAdvertisingSlogans() {
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("2");
	List<BillSloganEntity> list = billAdvertisingSloganDao.queryBillAdvertisingSlogans(entity,10, 0);
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.BillAdvertisingSloganDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)}.
     */
    @Ignore
    @Test
    public void testGetCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setSloganSort("2");
	
	Long result = billAdvertisingSloganDao.queryRecordCount(entity);
	Assert.assertTrue(result >  0);
    }
    
    /**
     * <p>根据单据广告语代码查询广告语内容</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-4 下午2:10:39
     * @see
     */
    @Ignore
    @Test
    public void testQueryBillSloganContent(){
	BillSloganEntity entity = billAdvertisingSloganDao.queryBillSloganContent("arriveSheetAdCode",null);
	Assert.assertNotNull(entity);
    }

}
