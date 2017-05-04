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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ITruckDepartPlanDetailDao.java
 * 
 *  FILE NAME     :ITruckDepartPlanDetailDao.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.dao
 * FILE    NAME: TruckDepartPlanDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;

/**
 * 计划接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:11:27
 */
public interface ITruckDepartPlanDetailDao {

	/**
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:37
	 */
	void addTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

	/**
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:37
	 */
	void batchAddTruckDepartPlanDetail(TruckDepartPlanDto truckDepartPlanDto);

	/**
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:46
	 */
	void deleteTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

	/**
	 * 查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:25
	 */
	List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto)
			throws TruckDepartPlanException;

	/**
	 * 更新发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:16:36
	 */
	void updateTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

	/**
	 * 查询明细条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午10:01:38
	 */
	Long queryTotalCount(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

	/**
	 * 分页查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 上午10:19:12
	 */
	List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetailBylimit(TruckDepartPlanDetailDto truckDepartPlanDetailDto,
			int limit, int start);

	/**
	 * 根据ID主键查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午5:26:17
	 */
	TruckDepartPlanDetailDto queryTruckDepartPlanDetailById(TruckDepartPlanDetailDto detailDto);

	/**
	 * 根据 车牌号、出发部门、到达部门查询最近未出发的发车计划
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @param origOrgCode
	 *            出发部门
	 * @param destOrgCode
	 *            到达部门
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-12 下午4:43:10
	 */
	TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto);
	/**
	 * 根据 车牌号、出发部门、到达部门查询最近未出发的发车计划
	 * 
	 * @param containerNo
	 *            货柜号
	 * @param origOrgCode
	 *            出发部门
	 * @param destOrgCode
	 *            到达部门
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-12 下午4:43:10
	 */
	TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetailByContainerNo(TruckDepartPlanDetailDto planDetailDto);

	/**
	 * 根据 出发部门、到达部门、日期查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-7 上午8:59:09
	 */
	Long queryPlanDetailCount(TruckDepartPlanDetailDto planDetailDto);

	/**
	 * 下发发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-15 下午4:51:30
	 */
	void releaseTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto);

	/**
	 * 通过车牌在发车计划表中查找当天的该车牌号是否有发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 下午4:34:01
	 */
	TruckDepartPlanDetailDto queryPlanDetailByVehicleNoAndDate(CarInfoDto carDto);

	/**
	 * 根据 出发部门、到达部门、日期、线路、班次查询，用于排除当天线路班次唯一
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-23 下午4:28:21
	 */
	TruckDepartPlanDetailDto queryCurrentDayLineFrequencyOnly(TruckDepartPlanDetailDto planDetailDto);

	/**
	 * 改变车辆归属类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 下午7:55:05
	 */
	void changePlanDetailTruckType(TruckDepartPlanDetailDto planDetail);

	/**
	 * 分页查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 上午10:19:12
	 */
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetailBylimitForExport(
			TruckDepartPlanDetailDto truckDepartPlanDetailDto);

	/**
	 * 通过挂牌号查询发车计划
	 * @author heyongdong
	 * @date 2014年9月1日 15:45:00
	 * */
	TruckDepartPlanDetailDto queryDepartPlanDetailByTrailerVehicleNo(TruckDepartPlanDetailDto detailDto);

	/**
	 * 
	* @description 查询长途或者短途的发车计划信息接口定义
	* @param truckDepartPlanDetailDto 发车计划明细
	* @return 发车计划明细
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:52:36
	 */
	TruckDepartPlanDetailDto queryTruckDepartPlanInfoDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

}