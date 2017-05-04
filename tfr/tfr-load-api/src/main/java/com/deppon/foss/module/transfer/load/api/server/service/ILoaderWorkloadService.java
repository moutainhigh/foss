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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/ILoaderWorkloadService.java
 *  
 *  FILE NAME          :ILoaderWorkloadService.java
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

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.ErrorVolumeDeductionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;

/**
 * 查询装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-11-28 下午4:23:03
 */
public interface ILoaderWorkloadService extends IService {

	/**
	 * 个人统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:50:10
	 */
	List<LoaderWorkloadDto> queryPersonCount(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start);
	/**
	 * 个人统计tab(快递) 查询方法
	 * @author zhangpeng
	 * @date 2015-11-26 上午10:50:10
	 */
	List<LoaderWorkloadDto> queryPersonCountExpress(
			LoaderWorkloadDto loaderWorkloadDto, int limit, int start);

	/**
	 * 个人统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-17 上午10:53:14
	 */
	
	Long getTotCountPersonCount(LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 个人统计tab 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-11-27 上午10:53:14
	 */
	Long getTotCountPersonCountExpress(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 队统计tab 查询方法
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:39:55
	 */
	List<LoaderWorkloadDto> queryTeamCount(LoaderWorkloadDto loaderWorkloadDto,
			int limit, int start);
	
	/**
	 * 队统计tab 查询方法
	 * @author zhangpeng
	 * @date 2015-12-10下午4:39:55
	 */
	List<LoaderWorkloadDto> queryTeamCountExpress(LoaderWorkloadDto loaderWorkloadDto,
			int limit, int start);

	/**
	 * 队统计tab 获取查询的总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-18 下午4:40:28
	 */
	Long getTotCountTeamCount(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 导出个人统计查询报表
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:16:52
	 */
	InputStream exportPersonCountExcel(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 导出队统计查询报表
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午2:38:26
	 */
	InputStream exportTeamCountExcel(LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 将文件名转成UTF-8编码以防止乱码
	 * @param 文件名
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-26 下午2:01:05
	 */
	String encodeFileName(String fileName);

	/**
	 * 个人统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午3:39:48
	 */
	List<LoaderWorkloadDto> queryPersonCountSummary(
			LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 个人统计查询合计
	 * @author zhangpeng
	 * @date 2015-11-26 下午3:39:48
	 */
	List<LoaderWorkloadDto> queryPersonCountSummaryExpress(
			LoaderWorkloadDto loaderWorkloadDto);

	/**
	 * 队统计查询合计
	 * @author ibm-zhangyixin
	 * @date 2012-12-27 下午4:15:18
	 */
	LoaderWorkloadDto queryTeamCountSummary(LoaderWorkloadDto loaderWorkloadDto);
	
	/**
	 * 队统计查询合计(快递)
	 * @author zhagnpeng
	 * @date 2015-12-09 下午4:15:18
	 */
	LoaderWorkloadDto queryTeamCountSummaryExpress(LoaderWorkloadDto loaderWorkloadDto);

	/** 
	 * @Title: querySuperiorOrgByOrgCode 
	 * @Description: 根据部门code找顶级组织 
	 * @param orgCode
	 * @return    
	 * @return OrgAdministrativeInfoEntity    返回类型 
	 * querySuperiorOrgByOrgCode
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-31上午10:30:38
	 */ 
	OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode);
	/**
	 * 补录运单时重新计算装卸车工作量
	 * @param billEntity
	 * @return
	 */
	boolean reCalculateWorkLoad(MakeUpWaybillEntity billEntity);

	int addErrorVolumnDeduction(ErrorVolumeDeductionDto errorVolumeDeductionDto);
	/**
	 * 差错货量扣除查询
	 * @param errorVolumeDeductionDto
	 * @author 257220
	 * @return
	 */
	List<ErrorVolumeDeductionDto> queryErrorVolumeDeductionList(ErrorVolumeDeductionDto errorVolumeDeductionDto,int limit, int start);
	
}