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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/ICreateLoaderWorkloadDao.java
 *  
 *  FILE NAME          :ICreateLoaderWorkloadDao.java
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
 * FILE    NAME: ICreateLoaderWorkloadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillAndPkgCountDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderParticipationDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;

/**
 * 创建装卸车工作量
 * @author dp-duyi
 * @date 2012-12-24 上午11:18:00
 */
public interface ICreateLoaderWorkloadDao {
	//查询未生成装卸车工作量卸车任务
	public List<LoaderWorkloadDetailDto> queryUnCreateWorkLoadUnloadTask(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	//查询未生成装卸车工作量装车任务
	public List<LoaderWorkloadDetailDto>  queryUnCreateWorkLoadLoadTask(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount);
	//根据任务id查询装车参与情况
	public List<LoaderParticipationDto> queryLoadParticipationDtoByTaskId(String taskId);
	//根据任务id查询卸车参与情况
	public List<LoaderParticipationDto> queryUnloadParticipationDtoByTaskId(String taskId);
	//插入工作量
	public int insertWorkLoad(List<LoaderWorkloadEntity> workloads);
	//更新是否生成装车工作量
	public int updateLoadTaskBeCreateWorkLoad(String taskId,String beCreateWorkLoad);
	//更新是否生成卸车工作量
	public int updateUnloadTaskBeCreateWorkLoad(String taskId,String beCreateWorkLoad);
	public List<String> queryUnloadGoodsType(String taskId);
	//更新未生成装卸车工作量装车任务状态为w
	public int  updateunCreateLoadTask(String taskId);
    //更新未生成装卸车工作量卸车任务状态为w
	public int updateunCreateUnLoadTask(String taskId);/** 
	 * @Title: queryBillNosByTaskNo 
	 * @Description: 根据卸车任务编号获取当前卸车任务下所有的单据号以 "xxxxxx,xxxxxxx" 这样的方式展示
	 * @param taskNo
	 * @return    
	 * @return String    返回类型 
	 * queryBillNosByTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-18下午5:11:07
	 */ 
	public String queryBillNosByTaskNo(String taskNo);
	
	//根据装车任务ID查询理货员装车票数和包数  271297 2016-1-12 12:00:30
	public List<BillAndPkgCountDto> queryBillAndPkgCountByTaskID(String taskId);
	
	//根据卸任务ID查询理货员装车票数和包数  271297 2016-1-12 12:00:30
	public List<BillAndPkgCountDto> queryUnLoadBillAndPkgCountByTaskID(String taskId);
	
	
	
}