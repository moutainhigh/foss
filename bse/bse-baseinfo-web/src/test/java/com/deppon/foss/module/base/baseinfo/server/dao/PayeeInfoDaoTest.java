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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/PayeeInfoDaoTest.java
 * 
 * FILE NAME        	: PayeeInfoDaoTest.java
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPayeeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 收款方信息DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-14 下午3:18:35 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-14 下午3:18:35
 * @since
 * @version
 */
public class PayeeInfoDaoTest {

    private JdbcTemplate jdbc;
    
    private IPayeeInfoDao payeeInfoDao;
    private PayeeInfoEntity entity = new PayeeInfoEntity();
    
    //收款方编码
    private String payeeNo;

    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	payeeInfoDao = SpringTestHelper.get().getBeanByInterface(IPayeeInfoDao.class);
    }

    /**
     * <p>清空测试数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-21 下午3:12:11
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
	jdbc.execute("delete from BSE.T_BAS_PAYEEINFO t where t.PAYEE_CODE = '"+payeeNo+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PayeeInfoDao#addPayeeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity)}.
     */
    @Ignore
    @Test
    public void testAddPayeeInfo() {
	payeeNo = "000123";
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setPayeeNo(payeeNo);
	entity.setAccountbankcityCode("0233");
	entity.setAccountbankCode("002342");
	entity.setAccountbankstateCode("023");
	entity.setAccountNo("6222002354528888417");
	entity.setAccountType("内部员工账户");
	
	int result = payeeInfoDao.addPayeeInfo(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.PayeeInfoDao#deletePayeeInfoByPayeeNo(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeletePayeeInfoByPayeeNo() {
	//添加一条数据
	testAddPayeeInfo();
	int result = payeeInfoDao.deletePayeeInfoByPayeeNo(payeeNo, "张三");
	
	Assert.assertTrue(result > 0);
    }
    
    

}
