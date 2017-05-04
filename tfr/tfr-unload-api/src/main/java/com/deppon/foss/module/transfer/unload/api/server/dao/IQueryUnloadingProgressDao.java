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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IQueryUnloadingProgressDao.java
 *  
 *  FILE NAME          :IQueryUnloadingProgressDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.server.dao
 * FILE    NAME: IQueryUnloadingProgressDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryBillInfoResultDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;

/**
 * 查询卸车进度DAO
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 下午3:23:22
 */
public interface IQueryUnloadingProgressDao {
	
	/**
	 * 
	 * 查询卸车进度信息
	 * @param queryUnloadingProgressConditionDto 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:25:46
	 */
	List<QueryUnloadingProgressResultDto> 
	queryUnloadingProgressInfo(QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto,int limit,int start);
	
	/**
	 * 
	 * 查询卸车进度信息 count
	 * @param queryUnloadingProgressConditionDto 查询条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:10:04
	 */
	Long queryUnloadingProgressInfoCount(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto);
	
	/**
	 * 
	 * 查询单据信息
	 * @param map{id:卸车任务id，cancel：取消状态}
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-13 下午4:52:42
	 */
	List<QueryBillInfoResultDto> queryBillInfo(Map<String,String> map);
	
}