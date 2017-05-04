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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IAssignLoadTaskDao.java
 *  
 *  FILE NAME          :IAssignLoadTaskDao.java
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
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.AssignLoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AssignLoadTaskQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderQueryConditionDto;




/**
 * IAssignLoadTaskDao
 * @author dp-duyi
 * @date 2012-10-11 下午3:05:12
 * @since
 * @version
 */
public interface IAssignLoadTaskDao {
	public List<LoaderEntity> queryWorkLoadByTime(LoaderQueryConditionDto loaderQCVo);
	public List<LoaderEntity> queryUnFinishWorkLoad(LoaderQueryConditionDto loaderQCVo);
	public int insert(AssignLoadTaskEntity assignLoadTask);
	public List<AssignLoadTaskEntity> queryUnstartAssignLoadTask(AssignLoadTaskQueryConditionDto assignLoadTask,int limit,int start);
	public Long getUnstartAssignLoadTaskCount(AssignLoadTaskQueryConditionDto assignLoadTask);
	public AssignLoadTaskEntity getAssignTaskStateById(String assignTaskId);
	public int cancelAssign(AssignLoadTaskEntity assignLoadTask);
	public String queryDeliverBillState(String assignBillNo);
	public int updateDeliverBillState(DeliverBillEntity bill,String beforeState);
	public List<DeliverBillEntity> queryDeliverBill(DeliverBillQueryConditionDto deliverBillQC,int limit,int start);
	public Long getDeliverBillCount(DeliverBillQueryConditionDto deliverBillQC);
	public List<AssignLoadTaskEntity> queryAssignLoadTaskByCondition(AssignLoadTaskQueryConditionDto assignLoadTask,int limit,int start);
	public Long queryAssignLoadTaskByConditionCount(AssignLoadTaskQueryConditionDto assignLoadTask);
	public int updateAssignedLoadTaskState(Map<String,String> item);
	public String queryAssignState(String deliverNo,String loaderCode);
}