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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IGuiMonitorService.java
 * 
 * FILE NAME        	: IGuiMonitorService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: IGuiMonitorService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.GuiMonitorEntity;


/**
 * GUI登陆信息采集service
 * @author 026123-foss-lifengteng
 * @date 2013-3-13 下午5:56:38
 */
public interface IGuiMonitorService extends IService {
	/**
	 * 插入用户登陆信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-13 下午6:03:27
	 */
	void insert(GuiMonitorEntity record);

	
	/**
	 * 根据不同条件插入用户登陆信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-13 下午6:03:46
	 */
	void insertSelective(GuiMonitorEntity record);
}