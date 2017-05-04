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
package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.NetGroupSiteDto;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StatisticalInquiriesEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.SerialnoDto;


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
	
	/**
	 * 计算四周均量
	 * （1）预测营业部未来货量需要根据历史数据预测，
	 * 		例如预测周一某营业部的开单货量，需要计算其前四周的周一平均货量来预测，并按照目的站显示
	 * （2）预测集中接货的未来货量需要根据历史数据预测，
	 * 		例如预测周一的集中接货量，需要计算其前四周的周一平均货量来预测，并按照目的站显示
	 * （3）预测未来货量的时间范围同转运场所配置查询的货量统计保持一致
	 * @author 163580
	 * @date 2014-3-25 下午7:42:06
	 * @return
	 * @see
	 */
	ForecastQuantityEntity countForecastQty(ForecastQuantityEntity forecastQuantityParam);

	/**
	 * 提供给派送率的接口
	 * 查询预计某日派送到达货量（票数、重量、体积）
	 * @author 163580
	 * @date 2014-3-26 下午3:14:10
	 * @param deptCodes 部门编号
	 * @param forecastDate 预测日期
	 * @return
	 * @see
	 */
	List<ForecastQuantityEntity> queryForecastQtyByRelevantOrgCode(List<String> deptCodes,
			Date forecastDate);

	/**
	 * 根据车次号获取当前运单的所有流水
	 * (在交接单相关表中查询)
	 * @author 163580
	 * @date 2014-4-3 上午10:35:32
	 * @param deptCode
	 * @param vehicleassembleNo
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	List<SerialnoDto> querySerialnoByVehicleassembleNo(String deptCode,
			String vehicleassembleNo, String wayBillNo);

	/**
	 * 根据运单号获取当前运单在库存中的所有流水
	 * (在库存表中查询) 
	 * @author 163580
	 * @date 2014-4-3 下午3:42:49
	 * @param deptCode
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	List<SerialnoDto> querySerialnoByStock(String deptCode, String wayBillNo);

	/**
	 * 根据下一到达部门, 运单号在走货路径中查流水号
	 * @author 163580
	 * @date 2014-4-3 下午4:37:26
	 * @param nextLine
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	List<SerialnoDto> querySerialnoByPathDetail(String nextLine,
			String wayBillNo);

	/**
	 * 根据运单号在运单货签中查询流水号
	 * @author 163580
	 * @date 2014-4-3 下午4:39:45
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	List<SerialnoDto> querySerialnoByLabeledGoods(String wayBillNo);

	/**
	 * 提供给仓库饱和度的接口
	 * 查询预计某日的操作货量(重量)
	 * 未来一天操作货量 = 未来一天到达该外场货量+未来一天该外场出发货量+该外场辐射营业部过去一个月内对应星期X的出发货量平均值
	 * @author 163580
	 * @date 2014-4-4 上午9:55:56
	 * @param deptCodes 外场code
	 * @param forecastDate
	 * @return
	 * @see
	 */
	ForecastQuantityEntity queryForecastWeightByRelevantOrgCode(
			String deptCode, List<NetGroupSiteDto> netGroupSites,
			Date forecastDate);

	/**
	 * 查询未开单货物(data_type = 1)
	 * @author 163580
	 * @date 2014-4-11 下午3:24:56
	 * @param forecastQuantityEntity
	 * @return
	 * @see
	 */
	List<ForecastQuantityEntity> queryUnbillingQuantity(
			ForecastQuantityEntity forecastQuantityEntity);

	/**
	 * 统计某日货量
	 * @author 163580
	 * @date 2014-4-12 上午10:53:18
	 * @param forecastQuantityParam
	 * @return
	 * @see
	 */
	ForecastQuantityEntity countForecastQtyOneDay(
			ForecastQuantityEntity forecastQuantityParam);

	/**
	 * 统计某周货量
	 * 日期必须从周期开始
	 * @author 163580
	 * @date 2014-4-16 下午2:35:39
	 * @param forecastQuantityParam
	 * @return
	 * @see
	 */
	ForecastQuantityEntity countForecastQtyOneWeek(
			ForecastQuantityEntity forecastQuantityParam);
}