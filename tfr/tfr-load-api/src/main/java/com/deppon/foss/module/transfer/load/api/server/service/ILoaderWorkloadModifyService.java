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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/ILoaderWorkloadModifyService.java
 *  
 *  FILE NAME          :ILoaderWorkloadModifyService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 修改装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-12-20 下午5:28:54
 */
public interface ILoaderWorkloadModifyService extends IService {

	/**
	 * 修改装卸车工作量查询方法
	 * @author ibm-zhangyixin
	 * @param start 
	 * @param limit 
	 * @date 2012-12-24 上午10:21:09
	 */
	List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit, int start);
	/**
	 * 修改装卸车工作量查询方法(快递)
	 * @author zhangpeng
	 * @param start 
	 * @param limit 
	 * @date 2015-12-02上午10:21:09
	 */
	List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit, int start);
	

	/**
	 * 修改装卸车工作量查询 获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午4:57:59
	 */
	Long getTotCount(LoaderWorkloadDetailDto loaderWorkloadDetailDto);

	/**
	 * 统计查询的总票数, 总重量, 总件数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午5:19:11
	 */
	LoaderWorkloadDetailDto querySummaryDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto);
	
	/**
	 * 统计查询的总票数, 总重量, 总件数(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午5:19:11
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
	 * 根据任务编号查询出当前任务的工作量(快递)
	 * @author ibm-zhangyixin
	 * @date 2015-12-05 上午11:32:02
	 */
	List<LoaderWorkloadDto> queryLoaderWorksByTaskNoExpress(String taskNo);

	/**
	 * 新增理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:19:10
	 */
	void saveLoaderWork(LoaderWorkloadDto loaderWorkloadDto);
	
	
	/**
	 * 新增理货员货量信息(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:19:10
	 */
	void saveLoaderWorkExpress(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:19:10
	 */
	void modifyLoaderWork(LoaderWorkloadDto loaderWorkloadDto);
	/**
	 * 修改理货员货量信息(快递)
	 * @author ibm-zhangyixin
	 * @date 2015-12-07 下午4:19:10
	 */
	void modifyLoaderWorkExpress(LoaderWorkloadDto loaderWorkloadDto);
	

	/**
	 * 根据装卸车任务ID 获取装卸车任务信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:27:00
	 */
	LoaderWorkloadEntity queryLoaderWorksById(String id);
	
	/**
	 * 根据装卸车任务ID 获取装卸车任务信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:27:00
	 */
	LoaderWorkloadEntity queryLoaderWorksByIdExpress(String id);

}