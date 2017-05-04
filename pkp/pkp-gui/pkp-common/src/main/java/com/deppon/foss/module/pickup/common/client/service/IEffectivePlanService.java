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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IEffectivePlanService.java
 * 
 * FILE NAME        	: IEffectivePlanService.java
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

import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity;

/**
 * 时效方案主信息服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-jiangfei,date:2012-10-31 下午4:09:00, </p>
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午4:09:00
 * @since
 * @version
 */
public interface IEffectivePlanService {
	
	/**
	 * 插条记录
	 */
	void addEffectivePlan(EffectivePlanEntity effectivePlan);
	
	/**
	 * 更新条记录
	 */
	void updateEffectivePlan(EffectivePlanEntity effectivePlan);
	
	 /**
	 * 新增或更新记录
	 */
	void saveOrUpdate(EffectivePlanEntity effectivePlan);
	
	/**
	 * 删除记录
	 */
	void deleteByRegionID(String regionID);

}