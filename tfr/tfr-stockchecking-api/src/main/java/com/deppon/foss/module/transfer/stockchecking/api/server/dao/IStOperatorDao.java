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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStOperatorDao.java
 *  
 *  FILE NAME          :IStOperatorDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;

/**
 * 清仓操作人的crud处理
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:42:48
 */
public interface IStOperatorDao {

	/**
	 * 新增一个清仓人，与清仓任务绑定
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午10:16:51
	 * @param entity
	 * @return
	 */
	void addStOperatorEntity(StOperatorEntity entity);
	
	/**
	 * 批量新增清仓人，与清仓任务绑定
	 * @author foss-wuyingjie
	 * @date 2012-11-1 下午3:33:06
	 * @param stOperatorList
	 * @return
	 */
	void addStOperatorEntityBatch(List<StOperatorEntity> stOperatorList);

	/**
	 * 通过清仓任务ID查询相关的清仓人
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午10:17:11
	 * @param stTaskId
	 * @return List<StOperatorEntity>
	 */
	List<StOperatorEntity> queryOperatorsByStTaskId(String stTaskId);

	/**
	 * 通过清仓任务ID删除清仓人
	 * @author foss-wuyingjie
	 * @date 2012-11-1 上午11:51:52
	 * @param stTaskId
	 * @return
	 */
	void deleteOperatorByStTaskId(String stTaskId);
}