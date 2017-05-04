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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IAverageCalculateDao.java
 * 
 *  FILE NAME     :IAverageCalculateDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.platform.api.server.dao;

import com.deppon.foss.module.transfer.platform.api.shared.domain.AverageCalculateEntity;

import java.util.List;

/**
 * 平均重量体积dao
 * @author huyue
 * @date 2012-10-11 下午9:16:55
 */
public interface IAverageCalculateDao {

	/**
	 * 新增平均重量体积信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:50:41
	 */
	int  addAverageCalculateSelective(AverageCalculateEntity averageCalculateEntity);
	
	/**
	 * 新增平均重量体积信息
	 * @author huyue
	 * @date 2012-10-18 下午6:50:51
	 */
	int addAverageCalculate(AverageCalculateEntity averageCalculateEntity);

	/**
	 * 根据PKID更新平均重量体积信息 自动筛选有值字段
	 * @author huyue
	 * @date 2012-10-18 下午6:51:10
	 */
	int updateAverageCalculateSelective(AverageCalculateEntity averageCalculateEntity);
	
	/**
	 * 根据PKID更新平均重量体积信息
	 * @author huyue
	 * @date 2012-10-18 下午6:51:19
	 */
	int updateAverageCalculate(AverageCalculateEntity averageCalculateEntity);
	
	/**
	 * 查询平均重量体积
	 * @author huyue
	 * @date 2012-11-8 上午10:13:55
	 */
	AverageCalculateEntity queryAverageCalculate(AverageCalculateEntity averageCalculateEntity);
	/**
	 * 查询平均重量体积LIST
	 * @author huyue
	 * @date 2012-11-9 下午3:10:56
	 */
	List<AverageCalculateEntity> queryAverageCalculateList(AverageCalculateEntity averageCalculateEntity);
}