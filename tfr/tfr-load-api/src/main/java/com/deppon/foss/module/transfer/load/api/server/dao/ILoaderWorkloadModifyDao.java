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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/ILoaderWorkloadModifyDao.java
 *  
 *  FILE NAME          :ILoaderWorkloadModifyDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 修改装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-12-20 下午5:28:26
 */
public interface ILoaderWorkloadModifyDao {

	

	/**
	 * 查询装卸车工作量查询方法
	 * @author ibm-zhangyixin
	 * @param start 
	 * @param limit 
	 * @date 2012-12-24 上午10:21:57
	 */
	List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit, int start);
	/**
	 * 查询装卸车工作量查询方法
	 * @author zhangpeng
	 * @param start 
	 * @param limit 
	 * @date 2015-12-02 上午10:21:57
	 */
	List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit, int start);

	/**
	 * 查询装卸车工作量获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午4:58:36
	 */
	Long getTotCount(LoaderWorkloadDetailDto loaderWorkloadDetailDto);

	/**
	 * 统计查询的总票数, 总重量, 总件数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午5:19:56
	 */
	LoaderWorkloadDetailDto querySummaryDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto);
	
	/**
	 * 统计查询的总票数, 总重量, 总件数（快递）
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午5:19:56
	 */
	LoaderWorkloadDetailDto querySummaryDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto);

	/**
	 * 根据任务编号查询出当前任务的工作量
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:32:02
	 */
	List<LoaderWorkloadDto> queryLoaderWorksByTaskNo(String taskNo);
	
	
	/**
	 * 根据任务编号查询出当前任务的工作量
	 * @author zhangpeng
	 * @date 2015-12-05 上午11:32:02
	 */
	List<LoaderWorkloadDto> queryLoaderWorksByTaskNoExpress(String taskNo);

	/**
	 * 新增理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:25:20
	 */
	void saveLoaderWork(LoaderWorkloadEntity loaderWorkload);
	

	
	
	/**
	 * 新增理货员货量信息WK
	 * @author 336540
	 * @date 2016-10-17 20:42:30
	 */
	void saveLoaderWorkWK(LoaderWorkloadEntity loaderWorkload);
	

	/**
	 * 新增理货员货量信息
	 * @author zhangpeng
	 * @date 2015-12-09 下午4:25:20
	 */
	void saveLoaderWorkExpress(LoaderWorkloadEntity loaderWorkload);

	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	LoaderWorkloadEntity getLoaderWorkloadByTaskNo(String taskNo);
	
	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息()快递
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	LoaderWorkloadEntity getLoaderWorkloadByTaskNoExpress(String taskNo);

	/**
	 * 根据装卸车工作量ID获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	LoaderWorkloadEntity queryLoaderWorksById(String id);
	
	
	/**
	 * 根据装卸车工作量ID获取装卸车工作量信息（快递）
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	LoaderWorkloadEntity queryLoaderWorksByIdExpress(String id);

	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:08:22
	 */
	void modifyLoaderWork(LoaderWorkloadEntity loaderWorkload);
	
	/**
	 * 修改理货员货量信息（悟空）
	 * @author 336540
	 * @date 2016-10-20 20:06:05	
	 * 
	 */
	void modifyLoaderWorkWK(LoaderWorkloadEntity loaderWorkload);
	

	/** 
	 * @Title: getUnloadTaskByUnloadTaskNo 
	 * @Description: 根据卸车任务号查询卸车任务  
	 * @param taskNO
	 * @return    
	 * @return UnloadTaskDto    返回类型 
	 * getUnloadTaskByUnloadTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-23下午2:22:46
	 */ 
	UnloadTaskEntity getUnloadTaskByUnloadTaskNo(String taskNO);
}