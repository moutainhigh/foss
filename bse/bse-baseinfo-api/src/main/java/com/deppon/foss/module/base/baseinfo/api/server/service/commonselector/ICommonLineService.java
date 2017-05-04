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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonLineService.java
 * 
 * FILE NAME        	: ICommonLineService.java
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
 * FILE    NAME: ICommonLineService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;

/**
 * 公共选择器--线路查询
 * @author panGuangJun
 * @date 2012-12-1 上午10:25:48
 */
public interface ICommonLineService {

	/**
	 * 查询线路
	 * @author panGuangJun
	 * @date 2012-12-1 上午10:26:13
	 */
	List<LineEntity> queryLineListByCondition(LineEntity lineEntity, int start,
			int limit);

	/**
	 * 查询线路总数
	 * @author panGuangJun
	 * @date 2012-12-1 上午10:26:18
	 */
	Long countLineListByCondition(LineEntity lineEntity);

}
