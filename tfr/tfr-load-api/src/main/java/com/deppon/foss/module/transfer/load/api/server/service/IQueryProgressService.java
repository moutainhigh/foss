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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IQueryProgressService.java
 *  
 *  FILE NAME          :IQueryProgressService.java
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
 * FILE    NAME: IQueryProgressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;

/**
 * 提供给外部的查询装卸车进度的接口
 * @author 046130-foss-xuduowei
 * @date 2012-11-26 上午9:10:27
 */
public interface IQueryProgressService extends IService {
	/**
	 * 
	 * 提供给月台查询调用，显示车辆的装车或卸车进度 包括（任务类型，体积进度，重量进度和完成时间）
	 * @param 任务号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 上午9:10:27
	 */
	QueryProgressResultDto queryProgressResult(String taskNo);
}