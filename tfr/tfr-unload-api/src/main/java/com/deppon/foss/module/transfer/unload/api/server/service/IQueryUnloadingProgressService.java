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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/service/IQueryUnloadingProgressService.java
 *  
 *  FILE NAME          :IQueryUnloadingProgressService.java
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
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.service
 * FILE    NAME: IQueryUnloadingProgressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoadAndUnloadStandardDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;

/**
 * 查询卸车进度SERVICE
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 下午3:14:44
 */
public interface IQueryUnloadingProgressService extends IService {
	
	/**
	 * 
	 * 查询卸车进度
	 * @param queryUnloadingProgressConditionDto 查询卸车进度条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:16:30
	 */
	List<QueryUnloadingProgressResultDto> 
	queryUnloadingProgressInfo(QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto,int limit,int start);
	/**
	 * 
	 * 查询卸车进度 count
	 * @param queryUnloadingProgressConditionDto 查询卸车进度条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:13:54
	 */
	Long queryUnloadingProgressInfoCount(QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto);
	
	/**
	 * 
	 * 查询当前部门装卸车标准
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午9:16:13
	 */
	LoadAndUnloadStandardDto queryLoadAndUnloadStd();
	
	
}