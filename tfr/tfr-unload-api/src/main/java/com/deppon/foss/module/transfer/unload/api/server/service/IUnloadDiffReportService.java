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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/service/IUnloadDiffReportService.java
 *  
 *  FILE NAME          :IUnloadDiffReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadDiffReportConditionDto;

/** 
 * @className: IUnloadDiffReportService
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告service接口
 * @date: 2012-12-10 上午10:06:17
 * 
 */
public interface IUnloadDiffReportService extends IService {

	/**
	 * 传入查询条件，返回卸车差异报告结果列表
	 * @param queryCondition查询条件dto对象
	 * @return List<UnloadDiffReportEntity>差异报告实体list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-10 上午10:08:21
	 */
	List<UnloadDiffReportEntity> queryUnloadDiffReportList(QueryUnloadDiffReportConditionDto queryCondition,int limit,int start);
	
	/**
	 * 获取卸车差异报告的总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午10:41:24
	 */
	Long queryUnloadDiffReportCount(QueryUnloadDiffReportConditionDto queryCondition);
	
	/**
	 * 根据卸车差异报告编号获取报告基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午10:42:07
	 */
	UnloadDiffReportEntity queryUnloadDiffReportBasicInfo(String diffReportNo);
	
	/**
	 * 根据卸车差异报告ID，获取差异报告详情
	 * @author 045923-foss-shiwei
	 * @date 2012-12-11 上午11:04:51
	 */
	List<UnloadDiffReportDetailEntity> queryUnloadDiffReportDetail(String diffReportId);
	
	/**
	 * 生成卸车差异报告
	 * @author 045923-foss-shiwei
	 * @param jobBizEndTime 
	 * @param jobBizEndTime 
	 * @date 2012-12-22 下午4:42:46
	 */
	int createUnloadDiffReport(Date bizJobStartTime);
	
	/**
	 * 处理卸车差异报告
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-25 下午2:50:59
	 */
	void executeUnloadDiffReportTask(int threadNo, int threadCount);
	
	
	/**
	 * 处理卸车差异报告到QMS
	 * @author 283244-FOSS-中转
	 * @date 2015-12-25 下午12:25:59
	 */
	void executeUnloadDiffReportToQMSTask();
	
	
	/**
	 * 手动处理卸货差异中的少货异常
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:28:04
	 */
	int handleUnloadLackGoodsException(String diffReportId,String waybillNo,String serialNo,String note);
	
	/**
	 * 获取未处理的少货异常，使用场景：
	 * 1、卸车差异报告，手动处理少货界面，首先调用该方法，传入报告ID，运单号、流水号，看该差异是否已被处理
	 * 			处理后再次调用该方法，传入报告ID，看该报告下是否有未处理的少货差异；
	 * 			如果刚处理过的少货差异已经上报OA，则再次传入报告ID、运单号，调用该方法；
	 * 2、单票入库，少货找到时，需传入卸车部门code、运单号、流水号
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 上午10:34:47
	 */
	List<UnloadDiffReportDetailEntity> queryUnresolvedLackGoodsException(UnloadDiffReportDetailEntity detailEntity);
	
	/**
	 * 更新卸货差异明细信息，可能更新的字段包括：
	 * 异常处理时间、备注、处理人编号、处理人名称、处理部门编号、处理部门名称
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 上午10:53:32
	 */
	int updateUnloadDiffReportDetail(UnloadDiffReportDetailEntity detailEntity);
	
	/**
	 * 将卸车差异报告的处理状态更新为“已处理”
	 * @author 045923-foss-shiwei
	 * @date 2012-12-26 下午3:43:58
	 */
	int updateUnloadDiffReportHandleState(String diffReportId,String handleState);
	
	/**
	 * 处理少货差异，共stock模块调用
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午9:24:27
	 */
	int handleUnloadLackDiffReport(UnloadDiffReportDetailEntity detailEntity,String userCode,String userName,String orgCode);
	
	/**
	 * 上报oa少货已找到差错
	 * @author 045923-foss-shiwei
	 * @date 2013-7-3 下午4:24:54
	 */
	int reportUnloadLackGoodsFoundToOA();

	/** 
	 * @Title: exportUnloadDifferencesReportDetail 
	 * @Description: 卸车差异报告明细导出 
	 * @param diffReportNo
	 * @return    
	 * @return InputStream    返回类型 
	 * exportUnloadDifferencesReportDetail
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-8下午5:40:14
	 */ 
	InputStream exportUnloadDifferencesReportDetail(String diffReportNo);
	/**
	 * @author niuly
	 * @date 2014-6-17上午10:39:43
	 * @function 判断运单是否少货且上报过OA少货差错
	 * @param waybillNo
	 * @return
	 */
	boolean hasLackError(String waybillNo);
	
	/**
	 * 处理少货差异，二程接驳卸车使用
	 * @author 205109-foss-zenghaibin
	 * @date 2015-05-26 上午8:41:27
	 */
	public int handleSCUnloadLackDiffReport(UnloadDiffReportDetailEntity detailEntity,String userCode,String userName,String currentOrgCode,String outFieldCode);
	/**
	 * 执行一次跑批
	 * @param bizJobStartTime
	 * @return
	 */
	int createUnloadDiffReportOne(Date startTime,Date endTime);
	
	
	
}