/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/PODServiceTest.java
 * 
 * FILE NAME        	: PODServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 财务签收记录Service 测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-10 下午2:36:17
 * @since
 * @version
 */
public class PODServiceTest extends BaseTestCase{
	
	@Autowired
	private IPODService podService;
	
	public void setUp(){
		
	}
	
	
	@After
	public void tearDown(){
		
	}
	
	private PODEntity getPODEntity(String podType){
		PODEntity entity=new PODEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(CommonTestUtil.getWaybillNO());//运单号
		Date date=new Date();
		entity.setPodDate(date);
		entity.setPodType(podType);//签收类型
		entity.setPodUserCode("123");
		entity.setPodUserName("管理员");
		entity.setPodOrgCode("1");
		entity.setPodOrgName("到达部门1");
		entity.setCreateDate(date);
		return entity;
	}
	
	/**
	 * 新增财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午2:36:38
	 */
	@Test
	@Rollback(true)
	public void testAddPOD(){
		PODEntity entity=this.getPODEntity(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY);
		this.podService.addPOD(entity, CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 根据运单号查询运单的财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午2:44:28
	 */
	@Test
	@Rollback(true)
	public void testQueryByWaybillNo(){
		PODEntity entity=this.getPODEntity(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY);
		this.podService.addPOD(entity, CommonTestUtil.getCurrentInfo());
		
		List<PODEntity> list=this.podService.queryByWaybillNo(entity.getWaybillNo(), null);
		Assert.assertTrue(CollectionUtils.isNotEmpty(list));
	}
}
