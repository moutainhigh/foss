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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IOutfieldService.java
 * 
 * FILE NAME        	: IOutfieldService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;



/**
 * 外场服务接口
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:40:46
 */
public interface IOutfieldService {
	
	/**
     * 插条记录
     */
	void addOutfield(OutfieldEntity outfield);
	/**
	 * 更新条记录
	 */
	void updateOutfield(OutfieldEntity outfield);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(OutfieldEntity outfield);
	
	
	/**
     * 
     * <p>通过空运总调组织编码查询外场实体</p> 
     * @author foss-zhujunyong
     * @date Mar 4, 2013 9:51:05 AM
     * @param airDispatchCode
     * @return
     * @see
     */
    OutfieldEntity queryOutfieldEntityByAirDispatchCode(String airDispatchCode);	
    
    /**
     * 通过空运总调查外场
     * 
     * @author foss-zhangxiaohui
     * @date Jan 31, 2013 10:32:29 AM
     */
    String queryTransferCenterByAirDispatchCode(String airDispatchCode);
    /**
	 * @param outfield
	 */
	void delete(OutfieldEntity outfield);
}