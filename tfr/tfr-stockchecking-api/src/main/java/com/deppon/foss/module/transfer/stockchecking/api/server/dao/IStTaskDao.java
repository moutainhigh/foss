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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/server/dao/IStTaskDao.java
 *  
 *  FILE NAME          :IStTaskDao.java
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

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StTaskExportDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StWaybillInfoDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.vo.StockcheckingVO;

/**
 * 处理清仓任务相关crud操作
 * @author foss-wuyingjie
 * @date 2012-12-17 下午4:00:14
 */
public interface IStTaskDao {

	/**
	 * 查询清仓任务列表(非外场)
	 * @author foss-wuyingjie
	 * @date 2012-10-18 下午1:31:09
	 */
	List<StTaskDto> queryStTaskDtoList(StTaskDto stTaskDto, int start, int limit);
	/**
	 * 查询清仓任务列表(外场)
	 *
	 * @param stTaskDto
	 * @param start
	 * @param limit
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-22 下午3:45:11
	 */
	List<StTaskDto> queryTransferCenterStTaskDtoList(StTaskDto stTaskDto, int start, int limit);
	
	/**
	 * 查询清仓任务列表对应的总数(非外场)
	 * @author foss-wuyingjie
	 * @date 2012-10-18 下午1:32:09
	 */
	Long queryStTaskDtoListCount(StTaskDto stTaskDto);
	/**
	 * 查询清仓任务列表对应的总数(外场)
	 *
	 * @param stTaskDto
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-22 下午3:45:54
	 */
	Long queryTransferCenterStTaskDtoListCount(StTaskDto stTaskDto);

	/**
	 * 查询库区信息列表
	 * @author foss-wuyingjie
	 * @date 2012-10-24 上午9:11:43
	 */
	List<GoodsStockDto> queryGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime);

	/**
	 * 创建一个清仓任务
	 * @author foss-wuyingjie
	 * @date 2012-10-24 下午5:46:22
	 */
	void addStTaskEntity(StTaskEntity stTaskEntity);

	/**
	 * <pre>
	 * 获取某部门当前的库区状态
	 * 货区清仓状态定义：若货区无任务则为未清仓
	 * </pre>
	 * @author foss-wuyingjie
	 * @param goodsAreaCode
	 * @param deptNo 
	 * @date 2012-10-29 上午11:14:41
	 */
	List<StTaskEntity> queryStTaskStatus(String goodsAreaCode, String deptNo);

	/**
	 * 更新清仓任务状态
	 * @author foss-wuyingjie
	 * @date 2012-11-2 上午9:04:20
	 */
	void updateStTaskStatus(StTaskEntity stTaskEntity);

	/**
	 * 通过ID获取清仓任务信息
	 * @author foss-wuyingjie
	 * @date 2012-11-7 上午11:18:42
	 */
	StTaskEntity queryStTaskById(String stTaskId);

	/**
	 * 通过清仓任务ID获取清仓人结果的运单统计信息
	 * @author foss-wuyingjie
	 * @date 2012-11-14 下午4:01:55
	 */
	List<StWaybillInfoDto> queryStWaybillInfoDtoByStTaskId(String stTaskId);

	/**
	 * 通过清仓任务ID获取清仓任务详细统计信息，供导出使用
	 * @author foss-wuyingjie
	 * @date 2012-11-16 上午10:56:35
	 */
	List<StTaskExportDto> queryStTaskExportInfoById(String stTaskId);

	/**
	 * 更新清仓任务实体信息
	 * @author foss-wuyingjie
	 * @date 2012-11-19 下午2:02:19
	 */
	void updateStTaskEntity(StTaskEntity stTaskEntity);

	/**
	 * 按业务起止处理时间段，查询某时间段内某切片的清仓任务 ORA_HASH(TASK_NO, threadCount - 1) = threadNo
	 * @author foss-wuyingjie
	 * @date 2012-11-30 上午10:26:34
	 */
	List<StTaskEntity> queryStTaskByBatch(Date bizJobStartTime, Date bizJobEndTime, int threadNo, int threadCount);

	/**
	 * 通过清仓任务编号返回清仓任务信息
	 *
	 * @param stTaskNo  清仓任务编号
	 * @return StTaskEntity 清仓任务记录
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 上午10:45:18
	 */
	StTaskEntity queryStTaskByNo(String stTaskNo);

	/**
	 * 按照非空字段更新清仓任务
	 *
	 * @param stTaskEntity
	 *
	 * @author foss-wuyingjie
	 * @date 2012-12-18 下午4:18:00
	 */
	void updateByPrimaryKeySelective(StTaskEntity stTaskEntity);

	/**
	 * 登陆人为外场的查询此外场各库区分组的运单信息
	 *
	 * @param goodsStockDto
	 * @return
	 *
	 * @author foss-wuyingjie
	 * @date 2013-1-16 上午11:29:46
	 */
	List<GoodsStockDto> queryTransferCenterGoodsStockDtoList(GoodsStockDto goodsStockDto,int beforeTime);
	/**
	 * 通过清仓差异报告ID获取清仓任务信息
	 * @param reportId
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-5 下午3:32:47
	 */
	StTaskEntity queryStTaskByReportId(String reportId);
	/**
	 * 通过部门编号、库区编号获取当前正在清仓的任务列表
	 * @param deptCode 必填
	 * @param goodsAreaCode 如果传入的为空，则不需要作为查询条件
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-20 下午2:13:35
	 */
	List<StTaskDto> queryTaskInProcess(String deptCode, String goodsAreaCode);
	/**
	 * @author niuly
	 * @date 2013-7-17 14:40:20
	 * @function 根据件数查询库存信息
	 * @param goodsStockDto
	 * @return
	 */
//	List<GoodsStockDto> queryTransferStockDtoListByQty(GoodsStockDto goodsStockDto,int beforeTime);
	/**
	 * @author niuly
	 * @date 2013-7-17 18:15:56
	 * @function 按照件数建立清仓任务
	 * @param task
	 */
//	void addStTaskEntityByQty(StTaskEntity stTaskEntity);
	/**
	 * @author niuly
	 * @date 2013-07-18 14:25:32
	 * @function 按件建立清仓任务时查询任务列表
	 * @param goodsAreaCode
	 * @param deptNo
	 * @return
	 */
//	List<StTaskEntity> queryStTaskStatusByQty(String goodsAreaCode,	String deptNo);
	/**
	 * @author niuly
	 * @date 2014-6-16下午4:37:14
	 * @function 更新清仓任务 
	 */
	int updateTask(int hours);
	/**
	 * @author zenghaibin
	 * @date 2014-9-19下午2:37:14
	 * @function 统计转运场数据
	 */
	public StockcheckingVO statistics(String orgCode);
	
	/**
	 *@author 205109 zenghaibin
	 *@date 2014-11-01
	 *@function 查询清仓的状态为DOING的清仓任务的部门编码
	 *@param configHours 配置时间
	 *@param deptCodeList 配置的部门编码
	 ***/
	public void updateTaskByDeptCode(String configHours,List<String> deptCodeList);
	/** 
	 * @author 268084
	 * @date 
	 * @functon 查询外场任务列表(快递)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#updateByPrimaryKeySelective(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskEntity)
	 */
	public List<GoodsStockDto> queryTransferCenterGoodsStockDtoListExpress(
			GoodsStockDto goodsStockDto, int beforeTime);
	/** 
	 * @author 268084
	 * @date 
	 * @function 营业部建立清仓任务时查询库存信息(快递)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	public List<GoodsStockDto> queryExpressGoodsStockDtoList(
			GoodsStockDto goodsStockDto, int beforeTime);
	/** 
	 * @author 268084
	 * @date 
	 * @function 查询外场任务列表(零担)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	List<GoodsStockDto> queryTransferCenterGoodsStockDtoListNoExpress(
			GoodsStockDto goodsStockDto, int beforeTime);
	/** 
	 * @author 268084
	 * @date 
	 * @function 营业部建立清仓任务时查询库存信息(零担)
	 * @param goodsStockDto
	 * @param beforeTime
	 * @return List<GoodsStockDto>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskDao#queryGoodsStockDtoList(com.deppon.foss.module.transfer.stockchecking.api.shared.dto.GoodsStockDto)
	 */
	List<GoodsStockDto> queryNoExpressGoodsStockDtoList(
			GoodsStockDto goodsStockDto, int beforeTime);
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交包数量
	 * @param stTaskNo
	 */
	public int getNoSubmitPackageNumByStTaskNo(String stTaskNo); 
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交运单数量
	 * @param stTaskNo
	 */
	public int getNoSubmitWaybillNumByStTaskNo(String stTaskNo); 
	/** 
	 * @author 332153
	 * @date 
	 * @function 根据清仓任务号获取未提交流水数量
	 * @param stTaskNo
	 */
	public int getNoSubmitSerialNumByStTaskNo(String stTaskNo); 
	
}