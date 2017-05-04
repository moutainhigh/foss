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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IDeliverLoadTaskDao.java
 *  
 *  FILE NAME          :IDeliverLoadTaskDao.java
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
 * FILE    NAME: IDeliverLoadTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;

/**
 * IDeliverLoadTaskDao
 * @author dp-duyi
 * @date 2012-10-30 上午11:02:26
 */
public interface IDeliverLoadTaskDao {
	//<!--根据派送单号查询装车任务 -->
	public List<LoadTaskEntity> queryLoadTasksByDeliverBillNo(LoadTaskEntity task);
	//<!-- 确认、打回派送单:根据派送单号更新装车任务状态 -->
	public int updateDeliverLoadTaskStateByDeliverBillNo(LoadTaskEntity loadTask);
	//根据派送单号、运单号，返回最新的装车任务流水号列表
	public List<String> queryLastLoadSerialNos(Map<String,String> condition);
	
	/**
	 * 根据派送单号获取派送单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:27:44
	 */
	public DeliverBillEntity queryDeliverBillByNo(String billNo);
	
	/**
	 * 根据派送单获取派送单明细(运单)
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:29:41
	 */
	public List<LoadWaybillDetailEntity> queryDeliverBillDetailListByNo(DeliverBillQueryConditionDto queryDto);
}