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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/baseinfo/test/service/OrgLayerServiceTest.java
 * 
 * FILE NAME        	: OrgLayerServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgLayerService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrganizationLayerEntity;
import com.deppon.foss.module.base.baseinfo.test.BaseTestCase;


/**
 * 组织层级测试类
 * @author ibm-zhuwei
 * @date 2013-1-17 下午7:25:11
 */ 
public class OrgLayerServiceTest extends BaseTestCase {

	@Autowired
	private IOrgLayerService orgLayerService;
    private IOrgAdministrativeInfoService OrgAdministrativeInfoService;
	@Test
	public void test() {
		OrganizationLayerEntity entity=orgLayerService.getOrgLayerEntityByCache("W01000201083006", null);
		logger.info("------------: " + entity.getCode());
	}
	@Test
	public void test2() {
		OrganizationLayerEntity entity=orgLayerService.getOrgLayerEntityByCache("W01000201083006", null);
		logger.info("------------: " + entity.getCode());
	}
}
