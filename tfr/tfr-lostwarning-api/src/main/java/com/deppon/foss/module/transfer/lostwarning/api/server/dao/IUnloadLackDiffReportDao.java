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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/server/dao/IUnloadDiffReportDao.java
 *  
 *  FILE NAME          :IUnloadDiffReportDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.lostwarning.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ReportOaErrorLogEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportDetailEntity;

/** 
 * @className: IUnloadDiffReportDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告dao接口
 * @date: 2012-12-10 上午9:55:50
 * 
 */
public interface IUnloadLackDiffReportDao {
	
	/**
	 * 查询未处理完毕的差异报告ID
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 上午9:36:34
	 */
	List<String> queryUnResolvedDiffReport(int threadNo, int threadCount,Date bizDate,Date delverBizDate);
	
	/**
	 * @author nly
	 * @date 2014年12月13日 上午8:59:13
	 * @function 查询长短途卸车需上报的差异数量
	 * @param reportId
	 * @return
	 */
	int queryUnResolvedDiffReportDetailCount(String reportId);
	
	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:51:03
	 * @function 更新是否需要上报OA差错
	 * @param reportId
	 * @return
	 */
	int updateUnloadDiffReportReportOa(String reportId);
	
	/**
	 * @author wwb
	 * @date 2016年6月27日16:06:44
	 * @function 更新是否需要上报OA差错
	 * @param reportId
	 * @return
	 */
	int addUnloadDiffReportReportIdTemp(String reportId);
	
	/**
	 * 根据差异报告ID查询符合上报OA的差异明细
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午7:47:14
	 */
	List<UnloadDiffReportDetailEntity> queryUnResolvedDiffReportDetail(String reportId,BigDecimal timeLimit);
	
	/**
	 * @author nly
	 * @date 2014年12月12日 上午10:26:34
	 * @function 查询接送货卸车需上报的差异数量
	 * @param reportId
	 * @return
	 */
	int queryUnResolvedLackDetailCount(String reportId);
	

	/**
	 * @author nly
	 * @date 2014年12月12日 上午11:08:25
	 * @function 查询接货差异明细
	 * @param reportId
	 * @param timeLimit
	 * @return
	 */
	List<UnloadDiffReportDetailEntity> queryUnResolvedLackDetail(String reportId, BigDecimal timeLimit);
	
	/**
	 * @author niuly
	 * @date 2014-6-9下午4:42:42
	 * @function 保存上报OA差错失败日志 
	 * @param logEntity
	 */
	int addReportOaErrorLog(ReportOaErrorLogEntity logEntity);
	
	/**
	 * 查询运单是否已经已作废、已中止
	 */
	boolean isInvalidWaybillNo(String waybillNo);
	
	/**
	 * 查询运单流水号是否已经已签收
	 */
	boolean isSignedWaybillNoSerialNo(String waybillNo, String serialNo);
	
	/**
	 * 查询运单是否已经已装车扫描
	 */
	boolean isLoadDataWaybillNo(UnloadDiffReportDetailEntity reportDetail);
	

	/**
	 * 查询流水号是否已经无效
	 */
	boolean isInvalidSerialNo(String waybillNo, String serialNo);
	/**
	 * 根据运单号查询是否进行卸车扫描、单票入库、清仓扫描
	 */
	boolean isSanAndSignleInStockLessGoods(String waybillNo);
	
}