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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/CreateLoaderWorkloadDao.java
 *  
 *  FILE NAME          :CreateLoaderWorkloadDao.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: CreateLoaderWorkloadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillAndPkgCountDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderParticipationDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.unload.api.server.dao.IBatchSaveProcessDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;

/**
 * 创建装卸车工作量
 * @author dp-duyi
 * @date 2012-12-24 上午11:39:42
 */
@SuppressWarnings("unchecked")
public class CreateLoaderWorkloadDao extends iBatis3DaoImpl implements ICreateLoaderWorkloadDao{
	private static final String NAMESPACE = "foss.load.createworkload.";
	private IBatchSaveProcessDao batchSaveProcessDao;
	public void setBatchSaveProcessDao(IBatchSaveProcessDao batchSaveProcessDao) {
		this.batchSaveProcessDao = batchSaveProcessDao;
	}
	/** 
	 * 查询未生成装卸车工作量卸车任务
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:40:33
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryUnCreateWorkLoadUnloadTask()
	 */
	@Override
	public List<LoaderWorkloadDetailDto> queryUnCreateWorkLoadUnloadTask(Date bizJobStartTime,	Date bizJobEndTime,int threadNo, int threadCount) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("threadCount", threadCount);
		paramsMap.put("threadNo", threadNo);
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnCreateWorkLoadUnloadTask",paramsMap);
	}

	/** 
	 * 查询未生成装卸车工作量装车任务
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:40:33
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryUnCreateWorkLoadLoadTask()
	 */
	@Override
	public List<LoaderWorkloadDetailDto> queryUnCreateWorkLoadLoadTask(Date bizJobStartTime,Date bizJobEndTime,int threadNo, int threadCount) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("threadCount", threadCount);
		paramsMap.put("threadNo", threadNo);
		paramsMap.put("bizJobStartTime", bizJobStartTime);
		paramsMap.put("bizJobEndTime", bizJobEndTime);
		return this.getSqlSession().selectList(NAMESPACE+"queryUnCreateWorkLoadLoadTask",paramsMap);
	}

	/** 
	 * 根据任务id查询装车参与情况
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:40:33
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryLoaderParticipationDtoByTaskId()
	 */
	@Override
	public List<LoaderParticipationDto> queryLoadParticipationDtoByTaskId(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadParticipationDtoByTaskId",taskId);
	}
	
	/** 
	 * 根据任务id查询卸车参与情况
	 * @author dp-duyi
	 * @date 2012-12-24 下午3:34:19
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryUnloadParticipationDtoByTaskId(java.lang.String)
	 */
	@Override
	public List<LoaderParticipationDto> queryUnloadParticipationDtoByTaskId(
			String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadParticipationDtoByTaskId",taskId);
	}

	/** 
	 * 插入工作量
	 * @author dp-duyi
	 * @date 2012-12-24 上午11:40:33
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#insertWorkLoad(java.util.List)
	 */
	@Override
	public int insertWorkLoad(List<LoaderWorkloadEntity> workloads) {
		return batchSaveProcessDao.batchSaveProcess(UnloadConstants.BATCH_SAVE_TYPE_INSERT, NAMESPACE+"insertWorkLoad", workloads);
	}

	/** 
	 * 更新是否生成装车工作量
	 * @author dp-duyi
	 * @date 2012-12-31 上午10:03:03
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#updateLoadTaskBeCreateWorkLoad(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateLoadTaskBeCreateWorkLoad(String taskId,
			String beCreateWorkLoad) {
		Map<String,String> condition  = new HashMap<String,String>();
		condition.put("taskId", taskId);
		condition.put("beCreateWorkLoad", beCreateWorkLoad);
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskBeCreateWorkLoad", condition);
	}

	/** 
	 * 更新是否生成卸车工作量
	 * @author dp-duyi
	 * @date 2012-12-31 上午10:03:03
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#updateUnloadTaskBeCreateWorkLoad(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateUnloadTaskBeCreateWorkLoad(String taskId,
			String beCreateWorkLoad) {
		Map<String,String> condition  = new HashMap<String,String>();
		condition.put("taskId", taskId);
		condition.put("beCreateWorkLoad", beCreateWorkLoad);
		return this.getSqlSession().update(NAMESPACE+"updateUnloadTaskBeCreateWorkLoad", condition);
	}
	/** 
	 * 查询卸车货物类型
	 * @author dp-duyi
	 * @date 2013-7-10 下午2:46:15
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryUnloadGoodsType(java.lang.String)
	 */
	@Override
	public List<String> queryUnloadGoodsType(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadGoodsType", taskId);
	}
	/** 
	 * @Title: queryBillNosByTaskNo 
	 * @Description: 根据卸车任务编号获取当前卸车任务下所有的单据号以 "xxxxxx,xxxxxxx" 这样的方式展示 
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ICreateLoaderWorkloadDao#queryBillNosByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-18下午5:12:10
	 */ 
	@Override
	public String queryBillNosByTaskNo(String taskNo) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryBillNosByTaskNo", taskNo);
	}
	
	/**
	 * 根据装车任务ID查询理货员装车票数和包数  
	 * 271297 2016-1-12 12:00:30
	 */
	@Override
	public List<BillAndPkgCountDto> queryBillAndPkgCountByTaskID(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryBillAndPkgCountByTaskID", taskId);
	}

	/**
	 * 根据卸车任务ID查询理货员卸车票数和包数  
	 * 271297 2016-1-12 12:00:30
	 */
	@Override
	public List<BillAndPkgCountDto> queryUnLoadBillAndPkgCountByTaskID(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnLoadBillAndPkgCountByTaskID", taskId);
	}
	/**
	 * 更新未生成装卸车工作量load任务状态为W  
	 * 271297 2016-3-2612:00:30
	 */
	@Override
	public int updateunCreateLoadTask(String taskId)
	{
		return this.getSqlSession().update(NAMESPACE+"updateunCreateLoadTask",taskId);
	}
	/**
	 * 更新未生成装卸车工作量unload任务状态为W  
	 * 271297 2016-3-2612:00:30
	 */
	@Override
	public int updateunCreateUnLoadTask(String taskId)
	{
		return this.getSqlSession().update(NAMESPACE+"updateunCreateUnLoadTask",taskId);
	}

}