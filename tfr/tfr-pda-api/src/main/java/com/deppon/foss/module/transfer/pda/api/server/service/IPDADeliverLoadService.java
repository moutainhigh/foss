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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPDADeliverLoadService.java
 *  
 *  FILE NAME          :IPDADeliverLoadService.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPDALoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadSaleTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAWaybillReturnDto;

/**
 * PDA派送装车接口
 * @author dp-duyi
 * @date 2012-11-6 下午12:20:41
 */
public interface IPDADeliverLoadService extends IPDATransferLoadService {
	//查询已分配派送装车任务
	public List<PDAAssignLoadTaskEntity> queryAssignedLoadTask(QueryAssignedLoadTaskEntity condition);
	//派送装车未装车备注
	public String deliverLoadNotes(LoadScanDetailDto notesRecord);
	/**
	 * 派送装车运单退回
	 *
	 * @param PDAWaybillReturnDto   PDA派送装车运单退回dto
	 * @date 2015-05-04 上午10:29:40
	 */
	public void waybillReturn(PDAWaybillReturnDto pdaWaybillReturnDto);
	
	/**
	 * 建立派送装车任务.
	 * @param loadTask the load task
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-21
	 */
	public LoadSaleTaskResultDto createSaleLoadTask(LoadSaleTaskDto loadTask);
	
	/**
	 * 刷新派送装车任务.
	 * @param taskNo the task no
	 * @return the list
	 * @author dp-332219
	 * @date 2016-11-21
	 */
	public List<LoadSaleGoodsDetailDto> refrushSaleLoadTaskDetail(String taskNo);
}