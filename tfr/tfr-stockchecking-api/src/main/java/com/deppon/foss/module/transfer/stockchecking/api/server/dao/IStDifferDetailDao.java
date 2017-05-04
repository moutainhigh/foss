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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStDifferDetailDao.java
 *  
 *  FILE NAME          :IStDifferDetailDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StDifferDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;

/**
 * 处理清仓差异报告明细数据
 * @author foss-wuyingjie
 * @date 2012-11-26 下午3:20:29
 */
public interface IStDifferDetailDao {

	/**
	 * 通过清仓差异报告ID获取清仓差异报告明细列表
	 * @author foss-wuyingjie
	 * @date 2012-11-22 下午2:19:57
	 * @param stReportId
	 * @return List<StReportDetailDto>
	 */
	List<StReportDetailDto> queryStReportDetailDtoListById(String stReportId);

	/**
	 * 通过查询"少货"、"放错货区"状态的明细，查找出一个月内的相匹配数据
	 * @author foss-wuyingjie
	 * 
	 * @param stReportId "少货"、"放错货区"状态的明细
	 * @param limitedDay 限制查询天数，限制为一个月
	 * @date 2012-11-26 上午11:22:34
	 * @param stReportId
	 * @param limitedDay
	 * @param scanResultLack
	 * @param scanResultSurplusErrorGoodsarea
	 * @return List<StReportDetailDto>
	 */
	List<StReportDetailDto> queryStReportRelativeDetailDtoList(String stReportId, Date limitedDay, String scanResultLack, String scanResultSurplusErrorGoodsarea);

	/**
	 * 更新差异明细的非空项字段
	 * @author foss-wuyingjie
	 * @date 2012-11-26 下午3:03:55
	 * @param stDifferDetail
	 * @return
	 */
	void updateByPrimaryKeySelective(StDifferDetailEntity stDifferDetail);

	/**
	 * 查询某一清仓差异报告下未处理任务明细的条数
	 * @author foss-wuyingjie
	 * @param stockCheckingReportDoing2 
	 * @date 2012-11-26 下午3:12:03
	 * @param id
	 * @param stDifferReportId
	 * @param stockCheckingReportDoing
	 * @return long
	 */
	long queryStReportDetailCountByReportId(String id, String stDifferReportId, String stockCheckingReportDoing);

	/**
	 * 批量新增清仓差异报告明细
	 * @author foss-wuyingjie
	 * @date 2012-12-3 上午10:16:24
	 * @param stDifferDetailListForBatch
	 * @return
	 */
	void addBatchStDifferReportDetail(List<StDifferDetailEntity> stDifferDetailListForBatch);

	/**
	 * 按清仓差异报告ID查询需上报的OA差错
	 * @author foss-wuyingjie
	 * @date 2012-12-6 上午10:53:21
	 * @param stReportId
	 * @return List<StDifferDetailDto>
	 */
	List<StDifferDetailDto> queryStOAErrorByBatch(String stReportId);

	/**
	 * 批量更新清仓差异明细的非空项字段
	 * @author foss-wuyingjie
	 * @date 2012-12-6 下午2:38:41
	 * @param stDifferDetailListForBatch
	 * @return
	 */
	void updateBatchByPrimaryKeySelective(List<StDifferDetailEntity> stDifferDetailListForBatch);

	/**
	 * 通过清仓差异明细获取清仓差异实体信息
	 * @author foss-wuyingjie
	 * @date 2012-12-12 上午10:53:59
	 * @param id
	 * @return StDifferDetailEntity
	 */
	StDifferDetailEntity queryStDifferDetailEntityById(String id);

	/**
	 * 通过运单号、流水号、部门编号获取清仓差异明细列表
	 * @author foss-wuyingjie
	 * @date 2012-12-12 下午2:52:15
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return List<StDifferDetailEntity>
	 */
	List<StDifferDetailEntity> queryStDifferDetailEntityList(String waybillNo, String serialNo, String deptCode);

	/**
	 * 增加清仓差异明细
	 *
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-28 下午5:30:02
	 * @param entity
	 * @return
	 */
	void addStDifferEntity(StDifferDetailEntity entity);

	/**
	 * 通过货件扫描结果类型及清仓明细确认状态获取清仓差异报告明细列表
	 *
	 * @param stReportId 清仓差异报告ID
	 * @param differenceType 货件差异类型
	 * @param handleStatus 处理状态
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @param stockCheckingReportDoing 
	 * @date 2013-1-15 上午11:44:17
	 */
	List<StDifferDetailEntity> queryStReportDetail(String stReportId, String differenceType, String handleStatus);

	/**
	 * 通过运单号，查询清仓任务中存在的少货但未处理（未找到）的库存
	 * @param waybillNo
	 * @param differenceType
	 * @param handleStatus
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-18 下午2:02:18
	 */
	List<StDifferDetailEntity> queryStDifferDetailListByWaybillNo(String waybillNo, String differenceType, String handleStatus);

	List<StReportDetailDto> queryStReportDetailDtoListsById(String id, int start, int limit);

	/**
	 * @author niuly
	 * @function 根据差异报告id、运单号、流水号查询两天内的关联差异报告
	 * @param stReportId
	 * @param limitedDay
	 * @param goodsStatusLack
	 * @param goodsStatusSurplusErrorGoodsarea
	 * @param waybillNo
	 * @param serialNos
	 * @return
	 */
	List<StReportDetailDto> queryReportRelativeDetail(String stReportId,Date limitedDay, String goodsStatusLack,String goodsStatusSurplusErrorGoodsarea, String waybillNo,List<String> serialNosList);
	/**
	 * @author niuly
	 * @function 根据任务id，运单号，差异明细处理DOING状态和少货获取其件数
	 * @param map
	 * @return
	 */
	int queryCountLastLackOfWaybill(String differReportId, String waybillNo);
	
	/**
	 * 以一个时间节点开始，查询状态为"DOING"、未上报OA差错且为少货状态的数据
	 * @param beginTime
	 * @param scheduledFireTime 
	 * @param threadNo
	 * @param threadCount
	 * @return
	 */
	List<StDifferDetailEntity> queryDetailForGapRepairList(Date beginTime, Date scheduledFireTime, int threadNo, int threadCount);

	/**
	 * 按运单号、流水号、差错编号、处理状态查询清仓差异报告明细数据
	 * @param entity
	 * @return
	 */
	List<StDifferDetailEntity> queryDifferDetailListForGapRepair(StDifferDetailEntity entity);
	/**
	 * @author niuly
	 * @date 2013-7-24 15:19:25
	 * @param stDifferReportId
	 * @param waybillNo
	 * @function 根据差异报告id和运单号查询差异明细信息
	 * @return
	 */
	List<StDifferDetailEntity> queryStDifferDetailsByStIdAndWaybillNo(String stDifferReportId, String waybillNo);
	/**
	 * @author niuly
	 * @date 2013-07-26 15:08:25
	 * @function 根据单号、差异报告id查询差异报告明细
	 * @param stReportId
	 * @param waybillNo
	 * @return
	 */
	List<StReportDetailDto> queryReportDetailByWaybillNo(String stReportId,	String waybillNo);
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:21:45
	 * @function 根据运单号查询少货且成功上报OA差错的数量
	 * @param waybillNo
	 * @return
	 */
	Long queryLackCountByWaybillNo(String waybillNo);

}