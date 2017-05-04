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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskPdaDao.java
 *  
 *  FILE NAME          :IStTaskPdaDao.java
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

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskPdaEntity;

/**
 * 
 * @author foss-wuyingjie
 * @date 2012-12-17 下午4:00:08
 */
public interface IStTaskPdaDao {

	/**
	 * 获取某清仓任务的各PDA分支状态列表，按Pda编号正序排列，按扫描时间倒序排列
	 *
	 * @param stTaskNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午3:16:18
	 */
	List<StTaskPdaEntity> queryBranchPdaTask(String stTaskNo);

	/**
	 * 获取上次pda清仓任务分支的最后一次扫描的信息
	 *
	 * @param stTaskNo
	 * @param pdaNo
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午5:08:25
	 */
	StTaskPdaEntity queryBranchPdaTask(String stTaskNo, String pdaNo);

	/**
	 * 增加一个pda清仓任务分支记录
	 *
	 * @param stTaskPdaEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午5:19:27
	 */
	void addStTaskPdaEntity(StTaskPdaEntity stTaskPdaEntity);

	/**
	 * 批量增加pda清仓任务分支记录
	 *
	 * @param lastScanTaskPdaList
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-21 上午10:11:32
	 */
	void addStTaskPdaEntityBatch(List<StTaskPdaEntity> lastScanTaskPdaList);

	/**
	 * 是否存在更改
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return
	 */
	int queryIsChanged(String waybillNo, String serialNo, String deptCode);
}