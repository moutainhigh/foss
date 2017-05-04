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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonLeasedDriverService.java
 * 
 * FILE NAME        	: ICommonLeasedDriverService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonLeasedDriverService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;

/**
 * 公共选择器 --外请司机查询service
 * @author panGuangJun
 * @date 2012-12-1 上午8:56:22
 */
public interface ICommonLeasedDriverService {
	/**
	 * 
	 * 根据条件查询外请司机
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:58:19
	 */
	 List<LeasedDriverEntity> queryLeasedDriverListByCondition(LeasedDriverEntity leasedDriver,int  start, int limit);
	/**
	 * 
	 * 根据条件查询符合外请司机总数
	 * @author panGuangJun
	 * @date 2012-12-1 上午8:58:19
	 */
	 long countLeasedDriverListByCondition(LeasedDriverEntity leasedDriver);
}
