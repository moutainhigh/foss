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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/NonfixedCusAccountDaoTest.java
 * 
 * FILE NAME        	: NonfixedCusAccountDaoTest.java
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

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 临欠散客开户银行账户DAO单元测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-21 下午5:15:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-21 下午5:15:34
 * @since
 * @version
 */
public class NonfixedCusAccountDaoTest {

    private JdbcTemplate jdbc;
    
    private INonfixedCusAccountDao nonfixedCusAccountDao;
    private NonfixedCusAccountEntity entity = new NonfixedCusAccountEntity();
    
    //编码
    private String id;
    private BigInteger crmId;
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	nonfixedCusAccountDao = SpringTestHelper.get().getBeanByInterface(INonfixedCusAccountDao.class);
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
//	jdbc.execute("delete from BSE.T_BAS_NONFIXED_ACCOUNT where id = '"+id+"'");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusAccountDao#addCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)}.
     */
    @Ignore
    @Test
    public void testAddCusAccount() {
	//生成UUID
	id = UUIDUtils.getUUID();
	crmId = new BigInteger("3321");
	entity.setId(id);
	entity.setCreateUser("谢艳涛");
	entity.setCreateDate(new Date());
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(entity.getCreateDate());
	entity.setActive(FossConstants.ACTIVE);
	entity.setOtherBranchBankName("中国工商银行青浦区支行");
	entity.setCrmId(crmId);
	
	int result = nonfixedCusAccountDao.addCusAccount(entity);
	
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusAccountDao#deleteCusAccountByCode(java.lang.String, java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteCusAccountByCode() {
	//添加一条数据
	testAddCusAccount();
	
	int result = nonfixedCusAccountDao.deleteCusAccountByCode(crmId, "王辉");
	Assert.assertTrue(result > 0);	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.CusAccountDao#updateCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateCusAccount() {
	//添加一条数据
	testAddCusAccount();
	entity.setId(id);
	entity.setModifyUser("谢艳涛");
	entity.setModifyDate(new Date());
	entity.setActive(FossConstants.ACTIVE);
	entity.setOtherBranchBankName("中国工商银行青浦区分行徐泾镇支行");
	
	int result = nonfixedCusAccountDao.updateCusAccount(entity);
	Assert.assertTrue(result > 0);	
    }
    
    /**
     * <p>根据临欠散客的客户编码查询散客的银行账号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-21 上午10:36:13
     * @see
     */
    @Ignore
    @Test
    public void testQueryCusAccountByCustCode(){
	List<NonfixedCusAccountEntity> list = nonfixedCusAccountDao.queryCusAccountByCustCode("1");
	
	Assert.assertTrue(list.size() > 0);
    }
    
    @Test
    public void testQueryAccountInfoByCustCode(){
	
	List<NonfixedCusAccountEntity>  list = nonfixedCusAccountDao.queryAccountInfoByCustCode("20120810-11681692");
	
	Assert.assertTrue(list.size() > 0);
    }

}
