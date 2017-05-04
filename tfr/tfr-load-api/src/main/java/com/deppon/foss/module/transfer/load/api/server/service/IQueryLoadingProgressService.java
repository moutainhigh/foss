/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IQueryLoadingProgressService.java
 *  
 *  FILE NAME          :IQueryLoadingProgressService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: IQueryLoadingProgressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressResultDto;

/**
 * 查询装车进度接口
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 上午8:16:40
 */
public interface IQueryLoadingProgressService extends IService {
	/**
	 * 
	 * 查询装车进度service
	 * @param queryLoadingProgressConditionDto 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:41:58
	 */
	List<QueryLoadingProgressResultDto> queryLoadingProgressInfo(
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto,int limit,int start);
	/**
	 * 
	 * 查询装车进度总数service
	 * @param queryLoadingProgressConditionDto 查询条件
	 * @author 046130-foss-yuyongxiang
	 * @date 2013年4月15日 11:26:48
	 */
	Integer queryLoadingProgressInfoCount(
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto,int limit,int start);
	
	
}