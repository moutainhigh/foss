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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IQueryProgressDao.java
 *  
 *  FILE NAME          :IQueryProgressDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: IQueryProgressDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressMapperResultDto;

/**
 * 提供给外部的查询装卸车进度的接口
 * @author 046130-foss-xuduowei
 * @date 2012-11-27 下午2:02:31
 */
public interface IQueryProgressDao {
	/**
	 * 
	 * 给月台查询是调用显示车辆的装车进度
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 上午9:10:27
	 */
	List<QueryProgressMapperResultDto> queryLoadProgressResult(Map<String,Object> queryMap);
	
	/**
	 * 
	 * 给月台查询是调用显示车辆的装车或卸车进度
	 * @param 车牌号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 上午9:10:27
	 */
	List<QueryProgressMapperResultDto> queryUnloadProgressResult(Map<String,Object> queryMap);
}