/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IShareJobDao.java
 * 
 * FILE NAME        	: IShareJobDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateEntity;

/**
 * 
 * 定时任务相关DAO接口
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 10:14:12 AM
 */
public interface IShareJobDao {

	/**
	 * 
	 * 新增仓储费日期执行记录
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:26:37 AM
	 */
	int addStorageExecdateEntity(StorageExecdateEntity storageExecdateEntity);
	
	/**
	 * 
	 * 根据执行日期查询记录
	 * 
	 * @param curDate
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:29:59 AM
	 */
	StorageExecdateEntity queryStorageExecdateEntityByStorageDate(StorageExecdateEntity storageExecdateEntity);

	/**
	 * 
	 * 根据执行状态查询记录列表
	 * 
	 * @param curDate
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:29:59 AM
	 */
	 List<StorageExecdateEntity> queryStorageExecdateEntityByExeStatus(StorageExecdateEntity storageExecdateEntity);

	/**
	 * 
	 * 更新仓储费日期执行记录
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:13 AM
	 */
	int updateStorageExecdateEntity(StorageExecdateEntity storageExecdateEntity);

	/**
	 * 
	 * 批量插入仓储费待执行明细表
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:40 AM
	 */
	void batchAddStorageExecdateDetailEntity(String execdateId, String[] productCodes);
	
	/**
	 * 
	 * 查询符合记录的条数
	 * 
	 * @param execdateId
	 * @param execStatus
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:05:16 PM
	 */
	Long selectStorageExecdateDetailEntityCountByParam(String execdateId, String execStatus);

	/**
	 * 
	 * 更新符合记录的条数
	 * 
	 * @param execdateId
	 * @param execStatus
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 5:08:14 PM
	 */
	public int updateStorageExecdateDetailEntityByParam(String execdateId, String oldExecStatus, String newExecStatus);
	
	/**
	 * 
	 * 查询仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	List<StorageExecdateDetailEntity> queryStorageExecdateDetailEntity(String execdateId, String execStatus, int rowNum);

	/**
	 * 
	 * 更新仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	int updateStorageExecdateDetailEntity(StorageExecdateDetailEntity storageExecdateDetailEntity);

	/**
	 * 
	 * 删除仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	int deleteStorageExecdateDetailEntity(StorageExecdateDetailEntity storageExecdateDetailEntity);

	/**
	 * 
	 * 新增成功日志信息
	 * 
	 * @param jobSuccessLogEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:39:12 AM
	 */
	int addJobSuccessLogEntity(JobSuccessLogEntity jobSuccessLogEntity);

	/**
	 * 
	 * 新增错误日志信息
	 * 
	 * @param jobExceptionLogEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:39:29 AM
	 */
	int addJobExceptionLogEntity(JobExceptionLogEntity jobExceptionLogEntity);
}