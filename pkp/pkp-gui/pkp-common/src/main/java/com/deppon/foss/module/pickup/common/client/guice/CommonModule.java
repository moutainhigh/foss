/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/guice/CommonModule.java
 * 
 * FILE NAME        	: CommonModule.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.guice;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.module.pickup.common.client.dao.IAddressDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.AddressDao;
import com.deppon.foss.module.pickup.common.client.service.IAddressService;
import com.deppon.foss.module.pickup.common.client.service.IPickupToDoMsgService;
import com.deppon.foss.module.pickup.common.client.service.impl.AddressService;
import com.deppon.foss.module.pickup.common.client.service.impl.PickupToDoMsgService;
import com.google.inject.Binder;

/**
 * 
 * 基本信息模块
 * 该模块在启动的时候加载 用于注入guice
 * 等必要的注入信息
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-5-14 linws 新增 </div>
 ******************************************** 
 */
public class CommonModule extends GuiceModule {
	
	/**
	 * 功能：configure
	 * 基本信息模块
	 * 该模块在启动的时候加载 用于注入guice
	 * 等必要的注入信息
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public void configure(Binder binder) {
		//地址栏Service
		binder.bind(IAddressService.class).to(AddressService.class).asEagerSingleton();
		//地址栏DAO
		binder.bind(IAddressDao.class).to(AddressDao.class).asEagerSingleton();
		//待办消息Service
		binder.bind(IPickupToDoMsgService.class).to(PickupToDoMsgService.class).asEagerSingleton();
	}
}