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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/service/IStReportService.java
 *  
 *  FILE NAME          :IStReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferReportEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StReportVO;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
/**
 * 处理与清仓差异报告相关的业务
 * @author foss-wuyingjie
 * @date 2012-11-22 上午11:39:57
 */
public interface IStReportService extends IService{

	/**
	 * 查询清仓差异报告数据，带分页
	 * @author foss-wuyingjie
	 * @date 2012-11-22 上午11:38:22
	 * @param stReportDto
	 * @param start
	 * @param limit
	 * @return List<StReportDto>
	 */
	List<StReportDto> queryStReportDtoList(StReportDto stReportDto, int start, int limit);

	/**
	 * 查询清仓差异报告数据总数
	 * @author foss-wuyingjie
	 * @date 2012-11-22 上午11:38:54
	 * @param stReportDto
	 * @return long
	 */
	Long queryStReportDtoListCount(StReportDto stReportDto);

	/**
	 * 通过清仓差异报告ID，获取清仓差异报告明细列表
	 * @author foss-wuyingjie
	 * @date 2012-11-22 上午11:39:11
	 * @param stReportId
	 * @return List<StReportDetailDto>
	 */
	List<StReportDetailDto> queryStReportDetailDtoListById(String stReportId);

	/**
	 * 通过清仓差异报告ID，获取相关联的差异报告明细列表
	 * @author foss-wuyingjie
	 * @date 2012-11-22 上午11:39:31
	 * @param stReportId
	 * @return List<StReportDetailDto>
	 */
	List<StReportDetailDto> queryStReportRelativeDetailDtoListById(String stReportId);

	/**
	 * <pre>
	 * 更新清仓差异明细报告明细的状态
	 * 若此条记录为最后一条"未处理"状态的清仓差异明细，在标记此条记录为"已处理"后，同时更新对应的清仓差异报告状态为"已处理"状态
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-26 下午2:45:54
	 * @param stDifferDetail
	 * @param userName
	 * @param userCode
	 */
	void updateReportDetail(StDifferDetailEntity stDifferDetail, String userName, String userCode);

	/**
	 * <pre>
	 * 生成清仓差异报告及差异明细
	 * 1、按业务起止处理时间段，处理此时间段内某切片的清仓任务 ORA_HASH(FINISHTIME, threadCount - 1) = threadNo
	 * 2、业务比对各清仓任务差异，得到相关的差异报告统计信息以及差异报告明细信息
	 * 3、批量生成各清仓任务对应的清仓任务差异报告
	 * 4、批量生成清仓差异报告明细
	 * 5、执行成功后，若存在明细都已处理完毕的清仓差异报告，需更新这些清仓报告状态为TransferConstants.STOCK_CHECKING_REPORT_DONE
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-11-29 下午7:32:37
	 * @param bizJobStartTime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @return
	 */
	void executeStReportTask(Date bizJobStartTime, Date bizJobEndTime, int threadNo, int threadCount);

	/**
	 * 
	 * <pre>
	 * 处理某一时间段内的需报告OA的差错单业务
	 * 1、按业务起止处理时间段，处理此时间段内某切片的清仓差异明细信息
	 * 2、业务比对各清仓任务差异，得到需要生成OA差错单的数据
	 * 3、逐条生成差错单
	 * 4、批量更新清仓差异明细信息，更新差错单号
	 * </pre>
	 * 
	 * @author foss-wuyingjie
	 * @date 2012-12-5 下午6:52:22
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @return
	 */
	void executeStOAErrorTask(Date bizJobEndTime,	int threadNo, int threadCount);

	/**
	 * 创建导出文件名
	 * @author foss-wuyingjie
	 * @date 2012-12-7 下午2:05:18
	 * @param fileName
	 * @return String
	 * 
	 */
	String createFileName(String fileName);

	/**
	 * 导出差异明细记录
	 * @author foss-wuyingjie
	 * @date 2012-12-7 下午2:07:35
	 * @param stReportId
	 * @return InputStream
	 */
	InputStream exportStDifferDetailById(String stReportId);
	
	/**
	 * 通过运单号、流水号、部门编号获取清仓差异明细列表
	 * @author foss-wuyingjie
	 * @date 2012-12-12 下午2:33:11
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return List<StDifferDetailEntity>
	 */
	List<StDifferDetailEntity> queryStDifferDetailEntityList(String waybillNo, String serialNo, String deptCode);

	/**
	 * <pre>
	 * 更新清仓差异明细
	 * 需传入字段：
	 *  handleTime：当前时间
	 *  handleStatus：TransferConstants.STOCK_CHECKING_REPORT_DONE
	 *  id:明细ID
	 *  stDifferReportId:清仓差异报告ID
	 *  userCode:若有处理人编号，则更新
	 * </pre>
	 * @author foss-wuyingjie
	 * @date 2012-12-12 下午2:35:17
	 * @param differDetailEntity
	 * @return
	 */
	void updateReportDetailList(StDifferDetailEntity differDetailEntity);
	
	/**
	 * 通过运单号，查询清仓任务中存在的少货但未处理（未找到）的库存
	 * @param waybillNo
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-18 下午1:48:39
	 */
	List<StDifferDetailEntity> queryStDifferDetailListByWaybillNo(String waybillNo);

	List<StReportDetailDto> queryStReportDetailDtoListsById(String stReportId, int start, int limit);

	/**
	 * @author niuly
	 * @function 获取差异明细的总数
	 * @param id
	 * @return long
	 */
	Long getStReportDetailDtoCount(String stReportId);
	/**
	 * @author niuly
	 * @function 查询一个差异明细中一个单、一个流水号两天内的关联差异报告
	 * @param id
	 * @return List<StReportDetailDto>
	 */
	List<StReportDetailDto> queryReportRelativeDetail(String stReportId, String waybillNo,List<String> serailNosList);

	/**
	 * <pre>
	 * 自动处理清仓差异报告明细中，未处理的数据且未做OA少货上报的数据
     * 1、已经签收出库的，标记为已处理
     * 2、在明细的差异报告生成时间之后，存在有入库历史数据的
     * 
     * </pre>
	 * 
	 * @author foss-wuyingjie
	 * 
	 * @param bizEndTime
	 * @param scheduledFireTime
	 * @param threadNo
	 * @param threadCount
	 * @param timeInterval 
	 */
	void executeStReportGapRepair(Date bizEndTime, Date scheduledFireTime, int threadNo, int threadCount, int timeInterval);
	
	/**
	 * 上报oa清仓少货已找到差错
	 * @author 045923-foss-shiwei
	 * @date 2013-7-6 上午10:41:29
	 * @param
	 * @return int
	 */
	int reportStLackGoodsFoundToOA();
	/**
	 * @author niuly
	 * @date 2013-07-10
	 * @function 查询外场code
	 * @return String
	 */
	String queryTransferCode();
	
	/**
	 * @author zenghaibin
	 * @date 2014-10-29
	 * @function 查询是否是外场
	 * @return String
	 */
	public String queryTransferCenter();
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
	 * @date 2013-07-25 17:12:25
	 * @function 批量更新清仓差异报告明细
	 * @param stReportDetailDtoList
	 * @param empName
	 * @param empCode
	 * @return
	 */
	void updateBatchReportDetail(List<StDifferDetailEntity> stDifferDetailList,	String empName, String empCode);
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
	 * @date 2013-08-02 14:24:21
	 * @function 根据运单号查询运单基本信息
	 * @param waybillNo
	 * @return WaybillEntity
	 */
	WaybillEntity queryWaybillBasicByNo(String waybillNo);
	/**
	 * @author niuly
	 * @date 2013-08-05 13:55:36
	 * @function 根据差异报告id查询差异报告信息
	 * @param id
	 * @return
	 */
	StDifferReportEntity queryStReportEntityById(String id);
	/**
	 * @author niuly
	 * @date 2013-08-05 14:19:48
	 * @function 查询扫描时间
	 * @param taskId
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	Date queryOperateTime(String reportId, String waybillNo,	String serialNo);

	/**
	 * @author niuly
	 * @date 2014-5-19下午2:56:33
	 * @function 根据产品编码查询产品名称
	 * @param code
	 * @return
	 */
	String queryProductNameByCode(String code);
	
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:04:43
	 * @function 判断运单是否少货且成功上报OA差错
	 * @param waybillNo
	 * @return
	 */
	boolean hasLackError(String waybillNo);
	/**
	 * @author niuly
	 * @date 2014-6-17上午11:40:38
	 * @function 清仓及卸车是否少货且成功上报OA少货差错,为pkp提供接口
	 * @param waybillNo
	 * @return
	 */
	boolean isLack(String waybillNo);
	
	/**
	 * @author zenghaibin
	 * @date 2014-10-20上午11:40:38
	 * @function 查询转运场的清仓差异的少货多货件数
	 * @return
	 */
	StReportVO goodsCountInfo();
	
}