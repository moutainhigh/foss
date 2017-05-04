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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/ShareJobDao.java
 * 
 * FILE NAME        	: ShareJobDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IShareJobDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 定时任务相关DAO实现类
 * 
 * @author ibm-wangfei
 * @date Feb 27, 2013 11:07:10 AM
 */
public class ShareJobDao extends iBatis3DaoImpl implements IShareJobDao {

	private static final String NAMESPACE_STORAGE_EXE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateMapper.";
	private static final String NAMESPACE_STORAGE_EXE_DETAIL = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.StorageExecdateDetailMapper.";
	private static final String NAMESPACE_JOB_EXCEPTION = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobExceptionLogMapper.";
	private static final String NAMESPACE_JOB_SUCCESS = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.JobSuccessLogMapper.";

	/**
	 * 
	 * 新增仓储费日期执行记录
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:26:37 AM
	 */
	@Override
	public int addStorageExecdateEntity(StorageExecdateEntity storageExecdateEntity) {
		storageExecdateEntity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE_STORAGE_EXE + "insertSelective", storageExecdateEntity);
	}

	/**
	 * 
	 * 根据执行日期查询记录
	 * 
	 * @param curDate
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:29:59 AM
	 */
	@Override
	public StorageExecdateEntity queryStorageExecdateEntityByStorageDate(StorageExecdateEntity storageExecdateEntity) {
		return (StorageExecdateEntity)this.getSqlSession().selectOne(NAMESPACE_STORAGE_EXE + "selectOneByStorageDate", storageExecdateEntity);
	}

	/**
	 * 
	 * 根据执行状态查询记录列表
	 * 
	 * @param curDate
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:29:59 AM
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StorageExecdateEntity> queryStorageExecdateEntityByExeStatus(StorageExecdateEntity storageExecdateEntity) {
		return this.getSqlSession().selectList(NAMESPACE_STORAGE_EXE + "selectByExeStatus", storageExecdateEntity);
	}
	
	/**
	 * 
	 * 更新仓储费日期执行记录
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:13 AM
	 */
	@Override
	public int updateStorageExecdateEntity(StorageExecdateEntity storageExecdateEntity) {
		return getSqlSession().update(NAMESPACE_STORAGE_EXE + "updateByPrimaryKeySelective", storageExecdateEntity);
	}

	/**
	 * 
	 * 批量插入仓储费待执行明细表
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:40 AM
	 */
	@Override
	public void batchAddStorageExecdateDetailEntity(String execdateId, String[] productCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("execdateId", execdateId);
		map.put("productCodes", productCodes);
		map.put("active", FossConstants.ACTIVE);
		//根据是否快递字段  过滤快递运单  add  by   yangkang
		map.put("isExpress", FossConstants.YES);
//		//开单付款方式
//		map.put("payMethod", DeliverbillConstants.PAYMETHOD_MONTHPAY);
		//结清状态
		map.put("settleSTATUS",FossConstants.YES);
//		//提货方式为自提都包含的字符
//		map.put("receiveMethod",DeliverbillConstants.RECEIVE_METHOD);
		this.getSqlSession().selectOne(NAMESPACE_STORAGE_EXE_DETAIL + "batchAdd", map);
	}
	
	/**
	 * 
	 * 查询符合记录的条数
	 * 
	 * @param storageExecdateEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:30:40 AM
	 */
	@Override
	public Long selectStorageExecdateDetailEntityCountByParam(String execdateId, String execStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("execdateId", execdateId);
		map.put("execStatus", execStatus);
		return (Long) this.getSqlSession().selectOne(NAMESPACE_STORAGE_EXE_DETAIL + "selectCountByParam", map);
	}
	
	/**
	 * 更新
	 * @author ibm-wangfei
	 * @date Nov 30, 2012 3:37:04 PM
	 */
	@Override
	public int updateStorageExecdateDetailEntityByParam(String execdateId, String oldExecStatus, String newExecStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("execdateId", execdateId);
		map.put("oldExecStatus", oldExecStatus);
		map.put("newExecStatus", newExecStatus);
		return this.getSqlSession().update(NAMESPACE_STORAGE_EXE_DETAIL + "batchUpdateByParam", map);
	}

	/**
	 * 
	 * 查询仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<StorageExecdateDetailEntity> queryStorageExecdateDetailEntity(String execdateId, String execStatus, int rowNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("execdateId", execdateId);
		map.put("execStatus", execStatus);
		map.put("rowNum", rowNum);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE_STORAGE_EXE_DETAIL + "selectByParam", map);
	}

	/**
	 * 
	 * 更新仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	@Override
	public int updateStorageExecdateDetailEntity(StorageExecdateDetailEntity storageExecdateDetailEntity) {
		return getSqlSession().update(NAMESPACE_STORAGE_EXE_DETAIL + "updateByPrimaryKeySelective", storageExecdateDetailEntity);
	}

	/**
	 * 
	 * 删除仓储费待执行明细表
	 * 
	 * @param storageExecdateDetailEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:33:25 AM
	 */
	@Override
	public int deleteStorageExecdateDetailEntity(StorageExecdateDetailEntity storageExecdateDetailEntity) {
		return getSqlSession().delete(NAMESPACE_STORAGE_EXE_DETAIL + "deleteByPrimaryKey", storageExecdateDetailEntity);
	}

	/**
	 * 
	 * 新增成功日志信息
	 * 
	 * @param jobSuccessLogEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:39:12 AM
	 */
	@Override
	public int addJobSuccessLogEntity(JobSuccessLogEntity jobSuccessLogEntity) {
		jobSuccessLogEntity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE_JOB_SUCCESS + "insertSelective", jobSuccessLogEntity);
	}

	/**
	 * 
	 * 新增错误日志信息
	 * 
	 * @param jobExceptionLogEntity
	 * @return
	 * @author ibm-wangfei
	 * @date Feb 27, 2013 10:39:29 AM
	 */
	@Override
	public int addJobExceptionLogEntity(JobExceptionLogEntity jobExceptionLogEntity) {
		jobExceptionLogEntity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE_JOB_EXCEPTION + "insertSelective", jobExceptionLogEntity);
	}
}