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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/ILoaderWorkloadDao.java
 *  
 *  FILE NAME          :ILoaderWorkloadDao.java
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

import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 查询装卸车工作量dao
 * @author ibm-zhangyixin
 * @date 2012-11-28 下午4:23:38
 */
public interface ILoaderWorkloadDao {

	/**
	 * 个人统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:51:04
	 */
	List<LoaderWorkloadDto> queryPersonCount(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start);
	/**
	 * 个人统计tab(快递) 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:51:04
	 */
	List<LoaderWorkloadDto> queryPersonCountExpress(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start);


	/**
	 * 个人统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:54:28
	 */
	Long getTotCountPersonCount(LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 个人统计tab 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-11-27 上午10:54:28
	 */
	Long getTotCountPersonCountExpress(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 队统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:41:17
	 */
	List<LoaderWorkloadDto> queryTeamCount(LoaderWorkloadDto loaderWorkloadDto,
			int limit, int start);
	
	/**
	 * 队统计tab 查询方法
	 * @author zhangpeng
	 * @date 2015-12-10 下午4:41:17
	 */
	List<LoaderWorkloadDto> queryTeamCountExpress(LoaderWorkloadDto loaderWorkloadDto,
			int limit, int start);

	/**
	 * 队统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:41:30
	 */
	Long getTotCountTeamCount(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 个人统计tab 查询方法_查询出所有的(无分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:20:11
	 */
	List<LoaderWorkloadDto> queryPersonCount(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 队统计tab 查询方法_查询出所有(无分页)
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:40:52
	 */
	List<LoaderWorkloadDto> queryTeamCount(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 个人统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午3:40:29
	 */
	List<LoaderWorkloadDto> queryPersonCountSummary(
			LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 个人统计查询合计(快递)
	 * @author zhangpeng
	 * @date 2015-11-26 下午3:40:29
	 */
	List<LoaderWorkloadDto> queryPersonCountSummaryExpress(
			LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 队统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午4:16:04
	 */
	LoaderWorkloadDto queryTeamCountSummary(LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 队统计查询合计(快递)
	 * @author zhangpeng
	 * @date 2015-12-09 下午4:16:04
	 */
	LoaderWorkloadDto queryTeamCountSummaryExpress(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 通过运单查询需要重新计算装车工作量的任务
	 * @author heyongdong
	 * @param waybillNo
	 * @return
	 */
	List<LoaderWorkloadDetailDto> queryReCreateWorkLoadLoadTask(String waybillNo);

	/**
	 * 通过运单查询需要重新计算卸车工作量的任务
	 * @author heyongdong
	 * @param waybillNo
	 * @return
	 */
	List<LoaderWorkloadDetailDto> queryReCreateWorkLoadUnLoadTask(String waybillNo);
	
	/**
	 * 删除原有的工作量数据
	 * @param taskNo
	 * @return
	 */
	int deleteOldWorkLoadLoadTask(String taskNo);
	
	/**
	 * 新增一条货量扣除
	 * @param errorVolumeDeductionDto
	 * @return
	 */
	int addErrorVolumnDeduction(ErrorVolumeDeductionDto errorVolumeDeductionDto);
	/**
	 * 差错货量扣除 页面查询
	 * @param errorVolumeDeductionDto
	 * @param limit
	 * @param start
	 * @return
	 */
	List<ErrorVolumeDeductionDto> queryErrorVolumeDeductionList(
			ErrorVolumeDeductionDto errorVolumeDeductionDto, int limit,
			int start);
}