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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/server/service/BaseInfoDownloadServiceTest.java
 * 
 * FILE NAME        	: BaseInfoDownloadServiceTest.java
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
package com.deppon.foss.module.base.baseinfo.server.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;



/**
 * 下载服务测试类
 * @author foss-zhujunyong
 * @date Oct 31, 2012 11:36:02 AM
 * @version 1.0
 */
public class BaseInfoDownloadServiceTest {

    @SuppressWarnings("unused")
    private JdbcTemplate jdbc;
    private IBaseInfoDownloadService baseInfoDownloadService;
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	baseInfoDownloadService = (IBaseInfoDownloadService) SpringTestHelper.get().getBeanByClass(BaseInfoDownloadService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadBankData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @Ignore
    @Test
    public void testDownloadBankData() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadDistrictData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    /**
     * @author 087584-foss-lijun
     * @date 2012-11-10 上午11:29:27
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadDistrictData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadDistrictData(client);
	List<AdministrativeRegionsEntity> list = (List<AdministrativeRegionsEntity>) (db.getObject());
	for (AdministrativeRegionsEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadFreightRouteData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadFreightRouteData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	client.setOrgCode("W0113080505");
	DataBundle db = baseInfoDownloadService.downloadFreightRouteData(client);
	List<FreightRouteEntity> list = (List<FreightRouteEntity>) (db.getObject());
	for (FreightRouteEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadFreightRouteLineData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadFreightRouteLineData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	client.setOrgCode("W0113080505");
	DataBundle db = baseInfoDownloadService.downloadFreightRouteLineData(client);
	List<FreightRouteLineEntity> list = (List<FreightRouteLineEntity>) (db.getObject());
	for (FreightRouteLineEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 限保物品
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadInsurGoodsData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadInsurGoodsData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadInsurGoodsData(client);
	List<LimitedWarrantyItemsEntity> list = (List<LimitedWarrantyItemsEntity>) (db.getObject());
	for (LimitedWarrantyItemsEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadLineData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadLineData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadLineData(client);
	List<LineEntity> list = (List<LineEntity>) (db.getObject());
	for (LineEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadLineDataList() {
//	ClientUpdateDataPack client = new ClientUpdateDataPack();
//	client.setOrgCode("W0113080505");
	
	ClientUpdateDataPack client2 = new ClientUpdateDataPack();
	client2.setOrgCode("W011304060906");
	Date d = new Date();
	d = DateUtils.addDays(d, -1);
	client2.setLastUpdateTime(d);
	List<ClientUpdateDataPack> lista = new ArrayList<ClientUpdateDataPack>();
//	lista.add(client);
	lista.add(client2);

	DataBundle db = baseInfoDownloadService.downloadLineData(lista);
	List<LineEntity> list = (List<LineEntity>) (db.getObject());
	for (LineEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadNetGroupData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadNetGroupData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadNetGroupData(client);
	List<NetGroupEntity> list = (List<NetGroupEntity>) (db.getObject());
	for (NetGroupEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadNetGroupData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadNetGroupDataList() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	client.setOrgCode("W0113080505");
	
	ClientUpdateDataPack client2 = new ClientUpdateDataPack();
	client2.setOrgCode("W011304060906");
	
	List<ClientUpdateDataPack> lista = new ArrayList<ClientUpdateDataPack>();
	lista.add(client);
	lista.add(client2);

	DataBundle db = baseInfoDownloadService.downloadNetGroupData(lista);
	List<NetGroupEntity> list = (List<NetGroupEntity>) (db.getObject());
	for (NetGroupEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	    System.out.println(entity.getVersion());
	}
    }

    
    
    /**
     * 组织机构数据下载 
     * 
     * 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadOrganizationData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午6:51:52
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadOrganizationData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadOrganizationData(client);
	List<OrgAdministrativeInfoEntity> list = (List<OrgAdministrativeInfoEntity>) (db.getObject());
	for (OrgAdministrativeInfoEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadOuterBranchData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @Ignore
    @Test
    public void testDownloadOuterBranchData() {
	fail("Not yet implemented");
    }

    /**
     * 禁运数据下载 
     * @author 087584-foss-lijun
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadProhibitGoodsData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */	
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadProhibitGoodsData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadProhibitGoodsData(client);

	List<ProhibitedArticlesEntity> list = (List<ProhibitedArticlesEntity>) (db.getObject());
	for (ProhibitedArticlesEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 营业部数据下载
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadSalesDepartmentData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadSalesDepartmentData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadSalesDepartmentData(client);
	List<SaleDepartmentEntity> list = (List<SaleDepartmentEntity>) (db.getObject());
	for (SaleDepartmentEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 外场数据下载 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadTransferCenterData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadTransferCenterData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadTransferCenterData(client);
	List<OutfieldEntity> list = (List<OutfieldEntity>) (db.getObject());
	for (OutfieldEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadGoodsAreaData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadGoodsAreaData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadGoodsAreaData(client);
	List<GoodsAreaEntity> list = (List<GoodsAreaEntity>) (db.getObject());
	for (GoodsAreaEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }


    /**
     * 权限 数据下载 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadResourceData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadResourceData(client);
	List<ResourceEntity> list = (List<ResourceEntity>) (db.getObject());
	for (ResourceEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }
    
    /**
     * 营业部适合产品 数据下载 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadSalesProductData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadSalesProductData(client);
	List<SalesProductEntity> list = (List<SalesProductEntity>) (db.getObject());
	if(list != null){
	    for (SalesProductEntity entity : list) {
		Assert.assertNotNull(entity.getId());
	    }
	}
    }
    
    /**
     * 财务组织 数据下载 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadResourceData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDownloadFinancialOrganizationsData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadFinancialOrganizationsData(client);
	List<FinancialOrganizationsEntity> list = (List<FinancialOrganizationsEntity>) (db.getObject());
	if(list != null){
	    for (FinancialOrganizationsEntity entity : list) {
		Assert.assertNotNull(entity.getId());
	    }
	}
    }
    
    /**
     * 角色 数据下载 
     * Test method for {@link com.deppon.foss.module.base.baseinfo.server.service.impl.BaseInfoDownloadService#downloadRoleData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void testDownloadRoleData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadRoleData(client);
	List<RoleEntity> list = (List<RoleEntity>) (db.getObject());
	for (RoleEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }

    /**
     * 角色权限 数据下载 
     */
    @SuppressWarnings("unchecked")
    //@Ignore
    @Test
    public void testDownloadRoleResourceData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	DataBundle db = baseInfoDownloadService.downloadRoleResourceData(client);
	List<RoleResourceEntity> list = (List<RoleResourceEntity>) (db.getObject());
	for (RoleResourceEntity entity : list) {
	    Assert.assertNotNull(entity.getId());
	}
    }
    
    @Test
    public void testDownloadNetGroupMixData() {
	ClientUpdateDataPack client = new ClientUpdateDataPack();
	client.setOrgCode("W03000305060513");
	client.setLastUpdateTime(new Date(0));
	List<ClientUpdateDataPack> list = new ArrayList<ClientUpdateDataPack>();
	list.add(client);
	DataBundle db = baseInfoDownloadService.downloadNetGroupMixData(list, ComnConst.ORG_TYPE_TARGET);
    }
    
}
