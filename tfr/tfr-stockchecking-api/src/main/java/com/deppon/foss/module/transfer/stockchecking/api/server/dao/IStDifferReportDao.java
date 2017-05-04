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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStDifferReportDao.java
 *  
 *  FILE NAME          :IStDifferReportDao.java
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
import java.util.Set;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StReportVO;

/**
 * 处理清仓差异报告数据
 * @author foss-wuyingjie
 * @date 2012-11-26 下午3:20:10
 */
public interface IStDifferReportDao {

	/**
	 * 查询清仓差异报告列表
	 * @author foss-wuyingjie
	 * @date 2012-11-26 下午3:10:29
	 * @param stReportDto
	 * @param start
	 * @param limit
	 * @return List<StReportDto>
	 */
	List<StReportDto> queryStReportDtoList(StReportDto stReportDto, int start, int limit);

	/**
	 * 查询清仓差异报告列表总数
	 * @author foss-wuyingjie
	 * @date 2012-11-26 下午3:10:52
	 * @param stReportDto
	 * @return long
	 */
	Long queryStReportDtoListCount(StReportDto stReportDto);

	/**
	 * 更新清仓差异报告的非空项字段
	 * @author foss-wuyingjie
	 * @date 2012-11-26 下午3:19:29
	 * @param stDifferReportEntity
	 * @return
	 */
	void updateByPrimaryKeySelective(StDifferReportEntity stDifferReportEntity);

	/**
	 * 批量新增清仓差异报告
	 * @author foss-wuyingjie
	 * @date 2012-12-3 上午10:12:58
	 * @param stDifferReportListForBatch
	 * @return
	 */
	void addBatchStDifferReport(List<StDifferReportEntity> stDifferReportListForBatch);

	/**
	 * 通过清仓差异报告ID取得差异报告实体信息
	 * @author foss-wuyingjie
	 * @date 2012-12-12 上午10:49:44
	 * @param id
	 * @return StDifferReportEntity
	 */
	StDifferReportEntity queryStReportEntityById(String id);

	/**
	 * 通过清仓差异报告ID获取其下的所有差异已全部处理完毕的清仓差异报告列表
	 * @author foss-wuyingjie
	 * @date 2012-12-12 下午3:37:08
	 * @param reportIdSet
	 * @return List<StDifferReportEntity>
	 */
	List<StDifferReportEntity> queryStReportForDone(Set<String> reportIdSet);

	/**
	 * 批量更新清仓任务报告
	 * @author foss-wuyingjie
	 * @date 2012-12-12 下午5:03:09
	 * @param reportList
	 * @return
	 */
	void updateBatchByPrimaryKeySelective(List<StDifferReportEntity> reportList);

	/**
	 * 默认查询X天内存在未上报的OA差错单的清仓差异报告记录，按照参数分片获取数据
	 * @param beginReportOATime 上报OA时间开关 （只查询差异报告生成时间大于此时间的报告）
	 * @param bizJobEndTime 业务数据截止时间
	 * @param threadNo      线程数
	 * @param threadCount   线程号
	 * @param oaReportHourRule 业务规则定义的差错单上报时限
	 * @return 清仓差异报告列表
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-26 下午4:34:32
	 */
	List<StDifferReportEntity> queryStReportForOAError(Date beginReportOATime, Date bizJobEndTime, int threadNo, int threadCount, int oaReportHourRule);

	/**
	 * 新增差异报告记录
	 *
	 * @param stDiffer
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-14 上午10:40:31
	 */
	void addStDifferReport(StDifferReportEntity stDiffer);

	/**
	 * 通过清仓任务编号ID获取对应的差异报告数量
	 * @param taskId
	 * @return
	 */
	int queryStReportCountByTaskId(String taskId);
	
	/**
	 * 上报OA少货差异报告
	 * @param beginReportOATime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @param oaReportHourRule
	 * @param bundleNum
	 * @return List<StDifferReportEntity>
	 * 
	 */
	List<StDifferReportEntity> queryLackStReportForOAError(Date beginReportOATime, Date bizJobEndTime, int threadNo, int threadCount, int oaReportHourRule, int bundleNum);
	/**
	 * 上报OA多货差异报告
	 * @param beginReportOATime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 */
	List<StDifferReportEntity> queryMoreStReportForOAError(Date beginReportOATime, Date bizJobEndTime, int threadNo, int threadCount);
	/**
	 * 差异报告明细数量
	 * @param stReportId
	 * @return
	 */
	Long getStReportDetailDtoCount(String stReportId);

	/**
	 * @author niuly
	 * @date 2013-08-05 14:19:48
	 * @function 查询扫描时间
	 * @param taskId
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	Date queryOperateTime(String reportId, String waybillNo, String serialNo);
	
	
	/**
	 * 查询外场的清仓差异统计信息
	 * @author 097457-foss-zenghaibin
	 * @date 2014-10-20 下午2:39:38
	 * @param ordcode 当前外场编码
	 * @return String
	 */
	public StReportVO goodsCountInfo(String orgCode);
}	
