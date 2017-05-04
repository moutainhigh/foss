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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/dao/VehicleAgencyDeptDaoTest.java
 * 
 * FILE NAME        	: VehicleAgencyDeptDaoTest.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 偏线代理网点Dao测试类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-22 上午9:38:43 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-22 上午9:38:43
 * @since
 * @version
 */
public class VehicleAgencyDeptDaoTest {
    
    private JdbcTemplate jdbc;
    
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
    private OuterBranchEntity entity = new OuterBranchEntity();
    
    //虚拟编码
    private String virtualCode;
    //Id
    private String id;

    @Before
    public void setUp() throws Exception {
	
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	vehicleAgencyDeptDao = SpringTestHelper.get().getBeanByInterface(IVehicleAgencyDeptDao.class);
    }

    /**
     * 清空测试数据 
     * @author 094463-foss-xieyantao
     * @date 2012-10-22 上午9:38:44
     * @throws java.lang.Exception
     * @see
     */
   /* @After
    public void tearDown() throws Exception {
	
	jdbc.execute("delete from T_BAS_OUTER_BRANCH WHERE branchtype = 'PX'");
	
    }*/

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao#addVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}.
     */
    @Ignore
    @Test
    public void testAddVehicleAgencyDept() {
	id = UUIDUtils.getUUID();
	virtualCode = id;
	entity.setId(id);
	entity.setVirtualCode(virtualCode);
	entity.setActive(FossConstants.ACTIVE);
	entity.setCreateUser("张龙");
	entity.setCreateDate(new Date());
	entity.setModifyUser(entity.getModifyUser());
	entity.setModifyDate(entity.getCreateDate());
	entity.setAgentDeptCode("0000001");
	entity.setAgentDeptName("榆林环西物流");
	entity.setAgentCompany("环西物流");
	entity.setManagement("广州汽运偏线");
	entity.setProvCode("陕西");
	entity.setCityCode("西安");
	entity.setCountyCode("榆林");
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	entity.setPickupSelf(FossConstants.YES);
	
	int result = vehicleAgencyDeptDao.addVehicleAgencyDept(entity);
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao#deleteVehicleAgencyDeptByCode(java.lang.String[], java.lang.String)}.
     */
    @Ignore
    @Test
    public void testDeleteVehicleAgencyDeptByCode() {
	
	//添加一条数据
	testAddVehicleAgencyDept();
	
	String[] codes = {virtualCode};
	int result = vehicleAgencyDeptDao.deleteVehicleAgencyDeptByCode(codes,"张飞");
	
	Assert.assertTrue(result > 0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao#updateVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}.
     */
    @Ignore
    @Test
    public void testUpdateVehicleAgencyDept() {
	
	entity.setActive(FossConstants.ACTIVE);
	entity.setModifyUser("李四");
	entity.setAgentDeptCode("0000111");
	entity.setAgentDeptName("榆林环西物流11");
	entity.setAgentCompany("环西物流");
	entity.setManagement("深圳汽运偏线");
	entity.setProvCode("陕西");
	entity.setCityCode("西安");
	entity.setCountyCode("榆林");
	entity.setPickupSelf(FossConstants.NO);
	entity.setVirtualCode("8af5c4f5-3c24-40f3-ad77-7031770819e1");
	
	//定义一个虚拟编码code
	String[] codes = {entity.getVirtualCode()};
	    
	//作废历史记录
	int flag = vehicleAgencyDeptDao.deleteVehicleAgencyDeptByCode(codes, entity.getModifyUser());
	
	Assert.assertTrue(flag == 1);
	
	if(flag == 1){
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    int res = vehicleAgencyDeptDao.addVehicleAgencyDept(entity);
	    Assert.assertTrue(res == 1);
	}		
	
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao#queryVehicleAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)}.
     */
    @Ignore
    @Test
    public void testQueryVehicleAgencyDepts() {
	
	entity.setActive(FossConstants.ACTIVE);
	List<OuterBranchEntity> list = vehicleAgencyDeptDao.queryVehicleAgencyDepts(entity,10, 0);
	
	Assert.assertTrue(list.size()>0);
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.dao.impl.VehicleAgencyDeptDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)}.
     */
    @Ignore
    @Test
    public void testQueryRecordCount() {
	
	entity.setActive(FossConstants.ACTIVE);
	
	Long result = vehicleAgencyDeptDao.queryRecordCount(entity);
	
	Assert.assertTrue(result >  0);
	
    }
    
    /**
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-24 上午11:58:51
     * @see
     */
    @Ignore
    @Test
    public void testQueryAgencyBranchCompanyInfo(){
	
	AgencyBranchOrCompanyDto dto = vehicleAgencyDeptDao.queryAgencyBranchCompanyInfo("KY00400001");
	
	Assert.assertNotNull(dto);
	
    }
    
    /**
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点）
     * @author 094463-foss-xieyantao
     * @date 2012-11-2 上午11:18:33
     * @see
     */
    @Ignore
    @Test
    public void testQueryOuterBranchs(){
	//添加一条数据
	testAddVehicleAgencyDept();
	
	OuterBranchParamsDto dto = new OuterBranchParamsDto();
	dto.setAgentDeptName("榆林环西物流");
	dto.setBranchType(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	List<OuterBranchEntity> list = vehicleAgencyDeptDao.queryOuterBranchs(dto);
	
	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>下载外部网点数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午2:01:00
     * @see
     */
    @Ignore
    @Test
    public void testQueryOuterBranchsForDownload(){
	//添加一条数据
	testAddVehicleAgencyDept();
	OuterBranchEntity entity = new OuterBranchEntity();
	entity.setManagement("广州汽运偏线");
	
	List<OuterBranchEntity> list = vehicleAgencyDeptDao.queryOuterBranchsForDownload(entity);
	
	Assert.assertTrue(list.size() > 0);
    }
    
    /**
     * <p>根据外部网点、网点类别查询外部网点是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 下午6:34:46
     * @see
     */
    @Ignore
    @Test
    public void testQueryOuterBranchByBranchCode(){
	OuterBranchEntity entity = vehicleAgencyDeptDao.queryOuterBranchByBranchCode("0001", null);
	
	Assert.assertNotNull(entity);
    }
    
}
