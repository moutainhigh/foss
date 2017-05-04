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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ITruckDepartPlanDetailService.java
 * 
 *  FILE NAME     :ITruckDepartPlanDetailService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: ITruckDepartPlanService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyVehicleEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoPageDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlanVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;

/**
 * 计划明细service接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午6:18:06
 */
public interface ITruckDepartPlanDetailService extends IService {
	/**
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:37
	 */
	void addTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) throws TruckDepartPlanException;

	/**
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午6:15:46
	 */
	void deleteTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) throws TruckDepartPlanException;

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
	void updateTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) throws TruckDepartPlanException;

	/**
	 * 查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 上午10:04:31
	 */
	List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetail(TruckDepartPlanDto planDto, int limit, int start)
			throws TruckDepartPlanException;

	/**
	 * 查询车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 下午5:00:02
	 */
	CarInfoPageDto queryCarList(CarInfoDto carDto, int limit, int start) throws TruckDepartPlanException;

	/**
	 * 新增或更新计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-27 下午8:05:41
	 */
	void saveOrUpdateInnerPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto, CurrentInfo user);

	/**
	 * 下发修改后保存备注信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-17 下午3:11:19
	 */
	void saveRemarkAfterSaveSuccess(TruckDepartPlanDetailDto truckDepartPlanDetailDto, CurrentInfo user);

	/**
	 * 根据出发部门、到达部门查询线路列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-28 上午8:43:55
	 */
	List<TruckDepartPlanDetailDto> queryLineListByOrigDestCode(TruckDepartPlanDetailDto detailDto);

	/**
	 * 通过车牌号查询车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-29 下午7:00:54
	 */
	PlanVehicleDto queryVehicleByVechileNo(TruckDepartPlanDetailDto detailDto);

	/**
	 * 查询发车计划明细总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午2:18:26
	 */
	Long queryTruckDepartPlanDetailTotal(TruckDepartPlanDto planDto);

	/**
	 * 通过出发部门、到达部门、车牌号查询最近的、未出车的发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午1:45:51
	 */
	TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetail(String origOrgCode, String destOrgCode, String vehicleNo,
			Date departDate) throws TruckDepartPlanException;
	/**
	 * 通过发车计划明细id和状态查询发车计划
	 * 
	 * @author 105869-foss-heyongdong
	 * @date 2013-7-26 下午1:45:51
	 */
	TruckDepartPlanDetailDto queryTruckDepartPlanDetailById(String truckDepartPlanDetailId,String status) throws TruckDepartPlanException;
	
	/**
	 * 通过出发部门、到达部门、货柜号查询最近的、未出车的发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午1:45:51
	 */
	TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetailByContainerNo(String origOrgCode, String destOrgCode, String containerNo,
			Date departDate);

	/**
	 * 根据 出发部门、到达部门、日期查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-7 上午9:03:14
	 */
	Long queryPlanDetailCount(String origOrgCode, String destOrgCode, Date departDate) throws TruckDepartPlanException;

	/**
	 * 下发发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-15 下午4:51:30
	 */
	void releaseTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto, CurrentInfo user);

	/**
	 * 根据外场编码 、车型查询装卸时效
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 下午8:32:15
	 */
	LoadAndUnloadEfficiencyVehicleEntity queryVehicleTime(String truckModel, String orgCode)
			throws TruckDepartPlanException;

	/**
	 * 根据 出发部门、到达部门、日期、线路、班次查询，用于排除当天线路班次唯一
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-23 下午4:28:21
	 */
	void queryCurrentDayLineFrequencyOnly(TruckDepartPlanDetailDto planDetailDto);
	/**
	 * 根据 出发部门、到达部门、日期、挂牌号查询当天的班次
	 * 
	 * @author heyongdong
	 * @date 2014年9月1日 15:28:05
	 */
	TruckDepartPlanDetailDto queryDepartPlanDetailByTrailerVehicleNo(String orgCode, String arriveDeptCode,
			String trailerVehicleNo,Date departDate);

	/**
	 * 根据车牌号查询已占用该车牌号部门
	 * @author 352203
	 * @data 2016年10月8日 10:35:37
	 * @param truckDepartPlanDetailDto
	 * @return
	 */
	List<String> queryDepartPlanDetailOrig(TruckDepartPlanDetailDto truckDepartPlanDetailDto);

}