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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/LoaderWorkloadModifyDao.java
 *  
 *  FILE NAME          :LoaderWorkloadModifyDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 修改装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-12-20 下午5:31:45
 */
public class LoaderWorkloadModifyDao extends iBatis3DaoImpl implements ILoaderWorkloadModifyDao {
	private static final String NAMESPACE = "foss.load.loaderworkload.";


	
	/**
	 * 查询装卸车工作量查询方法
	 * @author ibm-zhangyixin
	 * @param start 
	 * @param limit 
	 * @date 2012-12-24 上午10:21:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderWorkloadDetail", loaderWorkloadDetailDto, rowBounds);
	}
	/**
	 * 查询装卸车工作量查询方法
	 * @author zhangpeng
	 * @param start 
	 * @param limit 
	 * @date 2015-12-02 上午10:21:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderWorkloadDetailExpress", loaderWorkloadDetailDto, rowBounds);
		
	}

	/**
	 * 查询装卸车工作量获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午4:58:36
	 */
	@Override
	public Long getTotCount(LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCount", loaderWorkloadDetailDto);
	}

	/**
	 * 统计查询的总票数, 总重量, 总件数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午5:19:56
	 */
	@Override
	public LoaderWorkloadDetailDto querySummaryDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return (LoaderWorkloadDetailDto) this.getSqlSession().selectOne(NAMESPACE + "querySummaryDetail", loaderWorkloadDetailDto);
	}
	
	/**
	 * 统计查询的总票数, 总重量, 总件数(快递)
	 * @author zhangpeng
	 * @date 2015-12-05 下午5:19:56
	 */
	@Override
	public LoaderWorkloadDetailDto querySummaryDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return (LoaderWorkloadDetailDto) this.getSqlSession().selectOne(NAMESPACE + "querySummaryDetailExpress", loaderWorkloadDetailDto);
	}

	/**
	 * 根据任务编号查询出当前任务的工作量
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:32:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryLoaderWorksByTaskNo(String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderWorksByTaskNo", taskNo);
	}
	
	
	/**
	 * 根据任务编号查询出当前任务的工作量(快递)
	 * @author zhangpeng
	 * @date 2015-12-08 上午11:32:02
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoaderWorkloadDto> queryLoaderWorksByTaskNoExpress(String taskNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryLoaderWorksByTaskNoExpress", taskNo);
	}

	/**
	 * 新增理货员装卸车工作量
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:32:05
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#saveLoaderWork(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity)
	 */
	@Override
	public void saveLoaderWork(LoaderWorkloadEntity loaderWorkload) {
		this.getSqlSession().insert(NAMESPACE + "saveLoaderWork", loaderWorkload);
	}
	
	/**
	 * 悟空添加装卸车工作量
	 */
	@Override
	public void saveLoaderWorkWK(LoaderWorkloadEntity loaderWorkload) {
		this.getSqlSession().insert(NAMESPACE + "saveLoaderWorkWK", loaderWorkload);
	}

	/**
	 * 新增理货员装卸车工作量(快递)
	 * @author zhangpeng
	 * @date 2015-12-09 下午4:32:05
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#saveLoaderWork(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity)
	 */
	@Override
	public void saveLoaderWorkExpress(LoaderWorkloadEntity loaderWorkload) {
		this.getSqlSession().insert(NAMESPACE + "saveLoaderWorkExpress", loaderWorkload);
		
	}
	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:32:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#getLoaderWorkloadByTaskNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LoaderWorkloadEntity getLoaderWorkloadByTaskNo(String taskNo) {
		List<LoaderWorkloadEntity> loaderWorkloads = this.getSqlSession().selectList(NAMESPACE + "getLoaderWorkloadByTaskNo", taskNo);
		if(loaderWorkloads.size() > 0) {
			return loaderWorkloads.get(0);
		}
		return null;
	}

	/**
	 * 根据装卸车工作量ID获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:32:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#getLoaderWorkloadByTaskNo(java.lang.String)
	 */
	@Override
	public LoaderWorkloadEntity queryLoaderWorksById(String id) {
		return (LoaderWorkloadEntity) this.getSqlSession().selectOne(NAMESPACE + "queryLoaderWorksById", id);
	}

	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:09:06
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#modifyLoaderWork(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity)
	 */
	@Override
	public void modifyLoaderWork(LoaderWorkloadEntity loaderWorkload) {
		this.getSqlSession().update(NAMESPACE + "modifyLoaderWork", loaderWorkload);
	}
	
	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:09:06
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#modifyLoaderWork(com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity)
	 */
	@Override
	public void modifyLoaderWorkWK(LoaderWorkloadEntity loaderWorkload) {
		this.getSqlSession().update(NAMESPACE + "modifyLoaderWorkWK", loaderWorkload);
	}

	/** 
	 * @Title: getUnloadTaskByUnloadTaskNo 
	 * @Description: 根据卸车任务号查询卸车任务
	 * @param taskNO
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#getUnloadTaskByUnloadTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-23下午2:27:48
	 */ 
	@Override
	public UnloadTaskEntity getUnloadTaskByUnloadTaskNo(String taskNO) {
		return (UnloadTaskEntity) this.getSqlSession().selectOne(NAMESPACE + "getUnloadTaskByUnloadTaskNo", taskNO);
	}
	@Override
	public LoaderWorkloadEntity queryLoaderWorksByIdExpress(String id) {
		return (LoaderWorkloadEntity) this.getSqlSession().selectOne(NAMESPACE + "queryLoaderWorksByIdExpress", id);
	}
	
	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息（快递）
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:32:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao#getLoaderWorkloadByTaskNo(java.lang.String)
	 */
	@Override
	public LoaderWorkloadEntity getLoaderWorkloadByTaskNoExpress(String taskNo) {
		@SuppressWarnings("unchecked")
		List<LoaderWorkloadEntity> loaderWorkloads = this.getSqlSession().selectList(NAMESPACE + "getLoaderWorkloadByTaskNoExpress", taskNo);
		if(loaderWorkloads.size() > 0) {
			return loaderWorkloads.get(0);
		}
		return null;
	}

	
	

	
}