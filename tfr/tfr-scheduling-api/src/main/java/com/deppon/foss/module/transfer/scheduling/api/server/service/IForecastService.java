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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IForecastService.java
 * 
 *  FILE NAME     :IForecastService.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BillingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.RealWeightAndVolumeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.ForecastVO;

/**
 * 预测货量service类
 * 
 * @author huyue
 * @date 2012-10-31 下午5:24:06
 */
public interface IForecastService extends IService {

	/**
	 * 分页查询 并且根据数据进行统计
	 * 
	 * @author huyue
	 * @date 2012-11-22 上午10:53:39
	 */
	List<ForecastQuantityEntity> queryByPage(
			ForecastQuantityEntity forecastQuantityEntity, int limit, int start);

	/**
	 * 分页getCount
	 * 
	 * @author huyue
	 * @date 2012-11-27 下午3:36:31
	 */
	Long getCount(ForecastQuantityEntity forecastQuantityEntity);

	/**
	 * 查询外场某线路总货量预测
	 * 
	 * @author huyue
	 * @date 2012-11-15 下午5:17:54
	 */
	List<ForecastQuantityEntity> queryForecastQuantityList(
			ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 查询外场所有线路总货量预测
	 * 
	 * @author huyue
	 * @date 2012-12-4 下午3:42:11
	 */
	List<ForecastQuantityEntity> queryTotalList(
			ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 根据特定时间点查询货量预测.如果为总量则进行统计
	 * 
	 * @author huyue
	 * @date 2012-12-4 下午5:22:09
	 */
	ForecastQuantityEntity querySpecHourList(
			ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 查询某外场,某线路/全部,出发或到达货物实际重量&体积
	 * 
	 * @author huyue
	 * @date 2012-12-4 下午9:52:59
	 */
	RealWeightAndVolumeDto countRealWeightAndVolume(
			ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 计算营业部平均重量体积
	 * 
	 * @author huyue
	 * @date 2012-12-6 下午4:03:03
	 */
	void calculateAverageWeightAndVolume(Date time)
			throws TfrBusinessException;

	/**
	 * 查询外场某线路开单货量预测
	 * 
	 * @author huyue
	 * @date 2012-11-15 下午5:16:14
	 */
	List<BillingEntity> queryBillingList(BillingEntity billingEntity)
			throws TfrBusinessException;

	/**
	 * 查询外场某线路在途中货量预测
	 * 
	 * @author huyue
	 * @date 2012-11-15 下午5:17:17
	 */
	List<InTransitEntity> queryInTransitList(
			InTransitEntity inTransitEntity) throws TfrBusinessException;

	/**
	 * 查询合入本线路的货量
	 * 
	 * @author huyue
	 * @date 2012-12-5 下午8:36:50
	 */
	List<ChangeQuantityEntity> queryChangeInByDate(Date modifyDate,
			String orgCode, String destorgCode) throws TfrBusinessException;

	/**
	 * 查询合出本线路的货量
	 * 
	 * @author huyue
	 * @date 2012-12-5 下午8:37:07
	 */
	List<ChangeQuantityEntity> queryChangeOutByDate(Date modifyDate,
			String orgCode, String destorgCode) throws TfrBusinessException;

	/**
	 * 更新货量预测合车调整
	 * 
	 * @author huyue
	 * @date 2012-12-5 下午7:45:37
	 */
	void changeQuantity(
			List<ForecastQuantityEntity> forecastQuantityList,
			ChangeQuantityEntity changeQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 预测外场货量方法 分出发,到达 调用预测各外场方法
	 * 
	 * @author huyue
	 * @date 2012-12-17 下午8:19:16
	 */
	void forecastTransferCenterTotal(String action, Date statistics)
			throws TfrBusinessException ;
	
	/**
	 * 预测营业部货量方法 分出发,到达 调用预测各营业部方法
	 * @author huyue
	 * @date 2012-12-27 下午1:52:02
	 */
	void forecastSalesDepartmentTotal(String action, Date statistics)
			throws TfrBusinessException ;

	/**
	 * 预测货量 各外场出发,到达 调用预测各线路方法
	 * 
	 * @author huyue
	 * @date 2012-12-17 下午8:19:36
	 */
	void forecastTransferCenter(String action, Date statistics,
			String orgCode) throws TfrBusinessException;

	/**
	 * 预测货量 各营业部出发,到达 调用预测各线路方法
	 * 
	 * @author huyue
	 * @date 2012-12-17 下午8:20:06
	 */
	void forecastSalesDepartment(String action, Date statistics,
			String orgCode) throws TfrBusinessException;

	/**
	 * 根据外场部门统计条目信息 新增到主表
	 * 
	 * @author huyue
	 * @date 2012-11-27 下午9:30:10
	 */
	void forecast(String action, Date forecastTime, Date statistics,
			String orgCode, List<ForecastDto> billingList,
			List<ForecastDto> inTransitList, List<ForecastDto> inventoryList,
			String relevantOrgCode) throws TfrBusinessException;

	/**
	 * 根据总表UUID,现部门,到达部门,始发配载部门list等生成开单表信息
	 * 
	 * @author huyue
	 * @date 2012-11-28 下午8:59:33
	 */
	void billingForecast(String forecastQuantityId, String action,
			Date statistics, String orgCode, String relevantOrgCode,
			String region, List<ForecastDto> billingBeLongList,
			Set<String> setSalesDepartmentCode) throws TfrBusinessException;

	/**
	 * 根据总表UUID,现部门,到达部门,车牌号list等生成在途表信息
	 * 
	 * @author huyue
	 * @date 2012-11-28 下午9:00:07
	 */
	void inTransitForecast(String forecastQuantityId, String action,
			Date statistics, String orgCode, String relevantOrgCode,
			String region, List<ForecastDto> inTransitList,
			Set<String> setVehicleCode) throws TfrBusinessException;

	/**
	 * 查询到达开单部门LIST
	 * 
	 * @author huyue
	 * @date 2012-12-18 下午3:03:09
	 */
	List<String> arriveRelevantOrgCode(String orgCode,
			Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException;

	/**
	 * 查询到达开单LIST
	 * 
	 * @author huyue
	 * @date 2012-11-27 下午3:20:37
	 */
	List<PathDetailEntity> arriveBilling(String objectiveOrgCode,
			String origOrgCode, Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException;

	/**
	 * 查询到达在途LIST
	 * 
	 * @author huyue
	 * @date 2012-11-27 下午3:25:32
	 */
	List<PathDetailEntity> arriveInTransit(String objectiveOrgCode,
			String origOrgCode, Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException;

	/**
	 * 查询出发开单部门LIST
	 * 
	 * @author huyue
	 * @date 2012-12-18 下午3:04:00
	 */
	List<String> departRelevantOrgCode(String orgCode,
			Date forecastStartTime, Date forecastEndTime)
			throws TfrBusinessException;

	/**
	 * 查询出发开单LIST
	 * 
	 * @author huyue
	 * @date 2012-11-29 上午11:54:25
	 */
	List<PathDetailEntity> departBilling(String origOrgCode,
			String objectiveOrgCode, Date forecastStartTime,
			Date forecastEndTime) throws TfrBusinessException;

	/**
	 * 查询出发在途LIST
	 * 
	 * @author huyue
	 * @date 2012-11-29 上午11:54:27
	 */
	List<PathDetailEntity> departInTransit(String origOrgCode,
			String objectiveOrgCode, Date forecastStartTime,
			Date forecastEndTime) throws TfrBusinessException;

	/**
	 * 查询出发在库LIST
	 * 
	 * @author huyue
	 * @date 2012-11-29 上午11:54:29
	 */
	List<PathDetailEntity> departInventory(String origOrgCode,
			String objectiveOrgCode, Date forecastStartTime,
			Date forecastEndTime) throws TfrBusinessException;

	/**
	 * 根据到达营业部统计条目信息 新增到主表
	 * 
	 * @author huyue
	 * @date 2012-11-29 下午7:51:50
	 */
	void forecastSalesDeptArrive(String forecastArrive,
			Date forecastTime, Date statistics, String orgCode,
			List<ForecastDto> billingList, List<ForecastDto> inTransitList,
			String relevantOrgCode);

	/**
	 * 根据出发营业部统计条目信息 新增到主表
	 * 
	 * @author huyue
	 * @date 2012-11-29 下午8:02:58
	 */
	void forecastSalesDeptDepart(String forecastDepart,
			Date forecastTime, Date statistics, String orgCode,
			List<ForecastDto> billingList, String relevantOrgCode);

	/**
	 * 查询某外场最新一批货量预测的预测天数LIST
	 * 
	 * @author huyue
	 * @date 2012-11-30 下午2:43:17
	 */
	List<Date> queryForecastTimeList(
			ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 查询某外场最新一批货量预测的预测时间
	 * 
	 * @author huyue
	 * @date 2012-12-14 下午9:50:56
	 */
	Date selectMaxStatisticsTime(ForecastQuantityEntity forecastQuantityEntity)
			throws TfrBusinessException;

	/**
	 * 根据出发,到达部门判断发车计划类型
	 * @author huyue
	 * @date 2013-1-5 下午6:51:21
	 */
	String queryDepartureType(String origOrgCode, String destOrgCode)
			throws TfrBusinessException;

	/**
	 * 生成导出文件名称
	 * @author huyue
	 * @date 2013-1-7 下午7:03:33
	 */
	String encodeFileName(String string) throws TfrBusinessException;

	/**
	 * 导出预测数据到Excel
	 * @author huyue
	 * @date 2013-1-7 下午7:03:50
	 */
	InputStream exportExcelStream(
			ForecastQuantityEntity forecastQuantityEntity)throws TfrBusinessException;

	/**
	 * 导出预测明细数据到Excel
	 * @author huyue
	 * @date 2013-1-8 下午2:05:57
	 */
	InputStream detailExportExcelStream(String forecastQuantityId)throws TfrBusinessException;

	/**
	 * 根据地区分页获取全部地区count
	 * @author huyue
	 * @date 2013-3-15 上午10:33:40
	 */
	int queryByPageCount(ForecastQuantityEntity forecastQuantityEntity);
	
	/**
	 * 统计货量查询
	 * @author yuyongxiang
	 * @date 2013年7月8日 15:37:54
	 */
	ForecastVO queryStatisticalInquiries(ForecastVO forecastVO);
	/**
	 * 导出统计货量查询到Excel
	 * @author yuyongxiang
	 * @date 2013年7月10日 19:37:00
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#detailExportExcelStream(java.lang.String)
	 */
	InputStream queryStatisticalInquiriesExcelStream(ForecastVO forecastVO);
	
	/**
	 * 查询数据字典，查出快递货物体积的比率
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-9
	 * @return
	 */
	BigDecimal queryForecastParameter(String orgCode);
}
