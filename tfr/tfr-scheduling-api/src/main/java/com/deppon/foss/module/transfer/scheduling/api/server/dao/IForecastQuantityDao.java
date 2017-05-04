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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IForecastQuantityDao.java
 * 
 *  FILE NAME     :IForecastQuantityDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StatisticalInquiriesEntity;


/**
 * 货量预测总表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public interface IForecastQuantityDao{

	/**
	 * 分页查询货量预测总表信息
	 * @author huyue
	 * @date 2012-11-22 上午10:52:29
	 */
	List<ForecastQuantityEntity> queryByPage(
			ForecastQuantityEntity forecastQuantityEntity, int limit, int start);
	
	/**
	 * 查询货量预测总表信息 根据最后更新时间
	 * @author huyue
	 * @date 2012-12-10 上午11:34:40
	 */
	List<ForecastQuantityEntity> queryByStatistics(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 分页getCount
	 * @author huyue
	 * @date 2012-11-22 下午5:29:22
	 */
	Long getCount(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 查询外场货量预测货区的LIST
	 * @author huyue
	 * @date 2012-11-30 下午5:28:18
	 */
	List<ForecastQuantityEntity> queryByRegionList(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 查询某外场最新一批货量预测的预测时间
	 * @author huyue
	 * @date 2012-12-14 下午9:48:18
	 */
	Date selectMaxStatisticsTime(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 查询某外场最新一批货量预测的预测天数LIST
	 * @author huyue
	 * @date 2012-11-30 下午2:41:25
	 */
	List<Date> queryForecastTimeList(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 新增货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:31:57
	 */
	int addforecastQuantity(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 新增货量预测总表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:33:09
	 */
	int addforecastQuantitySelective(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 更新货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:34:46
	 */
	int updateforecastQuantity(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 更新货量预测总表信息 选择插入
	 * @author huyue
	 * @date 2012-11-14 下午8:34:09
	 */
	int updateforecastQuantitySelective(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 查询货量预测总表信息
	 * @author huyue
	 * @date 2012-11-14 下午8:44:32
	 */
	ForecastQuantityEntity queryforecastQuantity(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 查询货量预测总表信息 批量
	 * @author huyue
	 * @date 2012-11-14 下午8:50:30
	 */
	List<ForecastQuantityEntity> queryforecastQuantityList(ForecastQuantityEntity forecastQuantityEntity);

	/**
	 * 根据周期和其他参数查询总表信息 返回list
	 * @author huyue
	 * @date 2012-12-14 下午8:52:25
	 */
	List<ForecastQuantityEntity> selectByPeriod(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 根据某一库存状态 查询下一个库存
	 * BUG-36705
	 * @author yuyongxiang
	 * @date 2013年6月25日 15:54:36
	 *
	 */
	List<String> queryStockWhitInStock(Map<String, Object> map);
	
	/**
	 * 更具运单号以及运输性质查询运单详细信息
	 * @author yuyongxiang
	 * @date 2013年7月8日 20:53:03
	 */
	List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithWayBillNo(Map<String, Object> map);
	/**
	 * 更具运单号以及运输性质查询运单详细信息 count
	 * @author yuyongxiang
	 * @date 2013年7月8日 20:53:03
	 */
	Map<String, Object> queryStatisticalInquiriesWithWayBillNoCount(Map<String, Object> map);
	/**
	 * 根据交接单号的状态 查出对应的交接单号里面的运单和(实际交接的重量和体积)
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithHandover(Map<String, Object> map);
	/**
	 * 根据交接单号的状态 查出对应的交接单号里面的运单和(实际交接的重量和体积) count
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	Map<String, Object> queryStatisticalInquiriesWithHandoverCount(Map<String, Object> map);
	/**
	 * 根据当前部门和货物到达部门查询在库的运单
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithInLibrary(Map<String, Object> map);
	/**
	 *  根据当前部门和货物到达部门查询在库的运单 count
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	Map<String, Object> queryStatisticalInquiriesWithInLibraryCount(Map<String, Object> map);
	/**
	 * 到达未卸车
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	List<StatisticalInquiriesEntity> queryStatisticalInquiriesWithUnloadingNotReach(Map<String, Object> map);
	/**
	 *  到达未卸车 count
	 * @author yuyongxiang
	 * @date 2013年7月9日 15:28:53
	 */
	Map<String, Object> queryStatisticalInquiriesWithUnloadingNotReachCount(Map<String, Object> map);
}