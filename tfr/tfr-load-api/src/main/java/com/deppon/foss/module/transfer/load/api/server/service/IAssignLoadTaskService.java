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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IAssignLoadTaskService.java
 *  
 *  FILE NAME          :IAssignLoadTaskService.java
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
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto;

/**
 * IAssignLoadTaskService
 * @author Administrator
 * @date 2012-10-11 下午2:29:58
 * @since
 * @version
 */
public interface IAssignLoadTaskService {
	public List<LoaderEntity> queryLoader(LoaderQueryConditionDto loaderQCVo,int limit,int start);
	public Long getLoaderCount(LoaderQueryConditionDto loaderQCVo);
	public List<AssignLoadTaskEntity> queryUnstartAssignLoadTask(int limit,int start);
	public Long getUnstartAssignLoadTaskCount();
	public int assign(AssignLoadTaskEntity assignLoadTask);
	public int cancelAssign(String assignTaskId, String assignBillNo);
	public List<DeliverBillEntity> queryDeliverBill(DeliverBillQueryConditionDto deliverBillQCVo,int limit,int start);
	public Long getDeliverBillCount(DeliverBillQueryConditionDto deliverBillQCVo);
	public List<AssignLoadTaskEntity> queryAssignLoadTaskByCondition(AssignLoadTaskQueryConditionDto assignLoadTaskVo,int limit,int start);
	public Long queryAssignLoadTaskByConditionCount(AssignLoadTaskQueryConditionDto assignLoadTaskVo);
	public LoaderEntity queryUnfinishedWorkLoad(String loaderCode);
	public String getBigDeptCode(String orgCode);
}