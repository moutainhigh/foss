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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IDiscountOrgDao.java
 * 
 * FILE NAME        	: IDiscountOrgDao.java
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
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;


/**
 * 
 * 折扣适用起始目的组织网点持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105089-foss-yangtong,date:2012-10-16 下午2:55:55, </p>
 * @author foss-yangtong
 * @date 2012-10-16 下午2:55:55
 * @since
 * @version
 */
public interface IDiscountOrgDao {
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addDiscountProgram(DiscountOrgEntity discountOrgEntity);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateDiscountProgram(DiscountOrgEntity discountOrgEntity);

}