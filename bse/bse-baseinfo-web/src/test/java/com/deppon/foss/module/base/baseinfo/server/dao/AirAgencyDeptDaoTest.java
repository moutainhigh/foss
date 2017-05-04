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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/AirAgencyDeptDaoTest.java
 * 
 * FILE NAME        	: AirAgencyDeptDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运代理网点Dao测试类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-22
 * 下午2:48:12
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-22 下午2:48:12
 * @since
 * @version
 */
public class AirAgencyDeptDaoTest {

    private JdbcTemplate jdbc;

    private IAirAgencyDeptDao airAgencyDeptDao;

    private OuterBranchEntity entity = new OuterBranchEntity();

    // 虚拟编码
    private String virtualCode;

    // Id
    private String id;

    @Before
    public void setUp() throws Exception {

	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
		JdbcTemplate.class);
	airAgencyDeptDao = SpringTestHelper.get().getBeanByInterface(
		IAirAgencyDeptDao.class);
    }

    /**
     * 清空测试数据
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-22 下午2:48:12
     * @throws java.lang.Exception
     * @see
     */
    @After
    public void tearDown() throws Exception {
//	jdbc.execute("delete from T_BAS_OUTER_BRANCH WHERE AIR_WAYBILL_NAME IS NULL");
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyDeptDao#addAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}
     * .
     */
    @Ignore
    @Test
    public void testAddAirAgencyDept() {
	id = UUIDUtils.getUUID();
	virtualCode = id;
	entity.setId(id);
	entity.setVirtualCode(virtualCode);
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("王菲");
	entity.setAgentDeptCode("0000007");
	entity.setAgentDeptName("南京福佑物流");
	entity.setAgentCompany("南京福佑物流有限公司");
	entity.setManagement("广州空运总调");
	entity.setProvCode("江苏");
	entity.setCityCode("南京");
	entity.setCountyCode("江宁区");
	entity.setAirWaybillName("南京福佑物流有限公司");
	entity.setAirWaybillTel("025-68905540/1/2");
	entity.setPickupSelf(FossConstants.YES);

	int result = airAgencyDeptDao.addAirAgencyDept(entity);

	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyDeptDao#deleteAirAgencyDeptByCode(java.lang.String[], java.lang.String)}
     * .
     */
    @Ignore
    @Test
    public void testDeleteAirAgencyDeptByCode() {
	// 添加一条数据
	testAddAirAgencyDept();

	String[] codes = { virtualCode };
	int result = airAgencyDeptDao.deleteAirAgencyDeptByCode(codes, "刘德华");

	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyDeptDao#udpateAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}
     * .
     */
    @Ignore
    @Test
    public void testUpdateAirAgencyDept() {
	// 添加一条数据
	testAddAirAgencyDept();

	entity.setActive(FossConstants.ACTIVE);
	entity.setModifyUser("成龙");
	entity.setAgentDeptCode("0000010");
	entity.setAgentDeptName("南京福佑物流ww11");
	entity.setAgentCompany("南京福佑物流有限公司ww11");
	entity.setManagement("深圳空运总调");
	entity.setProvCode("江苏");
	entity.setCityCode("南京");
	entity.setCountyCode("江宁区");
	entity.setAirWaybillName("南京福佑物流有限公司ww11");
	entity.setAirWaybillTel("025-68905540/1/2");
	entity.setPickupSelf(FossConstants.YES);
	entity.setVirtualCode(virtualCode);

	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };

	// 作废历史记录
	int flag = airAgencyDeptDao.deleteAirAgencyDeptByCode(codes,
		entity.getModifyUser());

	Assert.assertTrue(flag == 1);

	if (flag == 1) {
	    // 记录ID有工具类生成ＵＵＩＤ
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    // 新增时修改时间与创建时间相同
	    entity.setModifyDate(entity.getCreateDate());
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(virtualCode);
	    // 设置新增记录默认生效
	    entity.setActive(FossConstants.ACTIVE);

	    int res = airAgencyDeptDao.addAirAgencyDept(entity);
	    Assert.assertTrue(res == 1);
	}

    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyDeptDao#queryAirAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)}
     * .
     */
    @Ignore
    @Test
    public void testQueryAirAgencyDepts() {
	//添加一条数据
	testAddAirAgencyDept();

	entity.setActive(FossConstants.ACTIVE);
	List<OuterBranchEntity> list = airAgencyDeptDao.queryAirAgencyDepts(
		entity, 10, 0);
	Assert.assertTrue(list.size() > 0);
    }

    /**
     * Test method for
     * {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.AirAgencyDeptDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}
     * .
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	//添加一条数据
	testAddAirAgencyDept();

	entity.setActive(FossConstants.ACTIVE);

	Long result = airAgencyDeptDao.queryRecordCount(entity);

	Assert.assertTrue(result > 0);
    }

}
