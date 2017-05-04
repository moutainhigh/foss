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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/ITruckSchedulingTaskDao.java
 * 
 *  FILE NAME     :ITruckSchedulingTaskDao.java
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
 * FILE    NAME: ITruckSchedulingTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleExcelDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingTaskDto;

/**
 * 排班任务DAO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-31 上午10:47:28
 */
public interface ITruckSchedulingTaskDao {

	/**
	 * 新增任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:55:13
	 */
	void insertTruckSchedulingTask(TruckSchedulingTaskEntity truckSchedulingTaskEntity);

	/**
	 * 批量插入任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:55
	 */
	void batchInsertTruckSchedulingTask(List<TruckSchedulingTaskEntity> list);

	/**
	 * 批量删除非工作状态的相应任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:55
	 */
	void deleteTasksForUnworkType(List<SimpleTruckScheduleDto> list);

	/**
	 * 根据ID列表删除计划任务列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-16 下午3:29:28
	 */
	void deleteTasksByScheduleTaskIds(SimpleTruckScheduleDto truckScheduling);

	/**
	 * 批量导入任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午9:59:55
	 */
	void batchImportTruckSchedulingTask(List<TruckSchedulingTaskDto> impTaskList);

	/**
	 * 查询任务通过ID
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:55:49
	 */
	SimpleTruckScheduleDto queryTruckSchedulingTask(TruckSchedulingTaskEntity truckSchedulingTaskEntity);

	/**
	 * 联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:56:24
	 */
	List<SimpleTruckScheduleDto> querySchedulingAndTask(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/**
	 * 更新任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:04
	 */
	void updateTruckSchedulingTask(TruckSchedulingTaskEntity truckSchedulingTaskEntity);

	/**
	 * 删除任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:14
	 */
	void deleteTruckSchedulingTask(TruckSchedulingTaskEntity truckSchedulingTaskEntity);

	/**
	 * 分页查询联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:17:30
	 */
	List<SimpleTruckScheduleDto> querySchedulingAndTaskByWork(SimpleTruckScheduleDto simpleTruckScheduleDto, int limit,
			int start);

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:17:38
	 */
	Long queryCount(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/**
	 * 查询对应的排班数据导出 ,主要是通过车队小组编码，年月进行查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 下午3:12:25
	 */
	List<ScheduleExcelDto> querySchedulingForExport(SimpleTruckScheduleDto truckSchedulingDto);

	/**
	 * 查询接送货排班，车队小组、当天、司机是否已经安排过
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-7 上午10:31:56
	 */
	public List<ScheduleExcelDto> queryPKPDuliVehicleCurrentDay(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/**
	 * 查询车队小组、当天、线路、班次是否已经存在本班次的排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-15 上午9:43:32
	 */
	List<ScheduleExcelDto> queryHasLineNoFrequencyNoCurrentDay(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/**
	 * 根据计划ID查询任务表中是否存在计划对应的任务数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-6 下午1:52:43
	 */
	Long queryTruckSchedulingTaskByScheduleId(TruckSchedulingEntity truckScheduling);

	/**
	 * 根据线路列表和日期 ，查询短途排班对应的排班列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-18 下午1:56:03
	 */
	List<SimpleTruckScheduleDto> queryFrequencyNosBylineVirtualCode(SimpleTruckScheduleDto dto);

	/**
	 * 查询某司机、某月的排班任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 下午3:32:09
	 */
	SimpleTruckScheduleDto queryLineInfoAndVehicleNo(TruckSchedulingEntity tsEntity);

	/**
	 * 通过车牌号及约车受理时间，读取短途排版表中的司机，如果获取多个司机
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午2:57:10
	 */
	List<SimpleTruckScheduleDto> queryTaskByVehicleNoAndDate(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/** 
	 * @Title: delteTask 
	 * @Description: 删除单个任务
	 * @param scheduleTaskId    
	 * @return void    返回类型 
	 * delteTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-23下午4:08:13
	 */ 
	void delteTask(String scheduleTaskId);

	/** 
	 * @Title: validateTruckSchedulingTask 
	 * @Description: 根据排班id, 排班任务id, 车牌号, 线路号, 班次号查询排班任务.
	 * @param simDto
	 * @return    
	 * @return List<TruckSchedulingTaskDto>    返回类型 
	 * validateTruckSchedulingTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-24上午10:23:20
	 */ 
	    
	List<TruckSchedulingTaskEntity> validateTruckSchedulingTask(
			SimpleTruckScheduleDto simDto);
	
	/**
	 * 通过排班车辆任务id删除非工作日接货小区
	 * @author heyongdong
	 * @date 2014年5月15日 11:37:44
	 * @param scheduleTaskId
	 * @return int
	 */
	int deleteTaskspkpAreabyId(String scheduleTaskId);
	
	/**
	 * 通过排班车辆任务id和接送货类型删除非工作日接货小区或者送货小区
	 * @author zyr
	 * @date 2015年08月22日 11:37:44
	 * @param scheduleTaskId
	 * @return int
	 */
	int deleteTaskspkpArea(String scheduleTaskId,String regionType);
	
	/**
	 * 批量插入非工作日接货小区
	 * @author heyongdong
	 * @date 2014年5月15日 11:37:44
	 * @param scheduleTaskId
	 * @return int
	 */
	int addTaskspkpArea(List<TruckSchedulingZoneEntity> list);
	
	 /**
	 * 根据车牌号查询非工作日最新的绑定信息
	 * @author foss-heyongdong
	 * @date 2014年5月21日 10:28:30
	 * @param vehicleNo
	 * @param queryTime 
	 * */
	List<TruckSchedulingZoneEntity> queryPkpAreaInfoByVehicleNo(
			SimpleTruckScheduleDto smDto);
	
	/**
	 * 根据区域编号和时间查询当日绑定的车辆
	 * @author foss-heyongdong
	 * @date 2014年5月26日 14:41:53 
	 * @param zoneCode
	 * @param queryTime
	 * @return
	 */
	TruckSchedulingZoneEntity queryVehicleByZoneCode(String zoneCode,
			String queryTime);
	/**
	 *  根据车牌号和时间查询当日车辆绑定司机
	 * @author foss-heyongdong
	 * @date 2014年5月26日 15:10:45 
	 * @param vehilceNo
	 * @param cqueryTime
	 * @return
	 */
	TruckSchedulingZoneEntity queryDriverByVehicle(String vehilceNo,
			String cqueryTime);
	/**
	 *  根据车牌号和时间查询车辆净空，车辆载重，是否带快递货， 带快递货方数，班次
	 * @author foss-zyr
	 * @date 2015年5月12日 09:56:14
	 * @param vehicleNo
	 * @param queryTime 
	 */
	SimpleTruckScheduleDto queryTruckByVehicle(String vehilceNo,String queryTime);
	
	
	
	/**
	 *  根据车牌号和送货时间查询车辆净空，车辆载重， 带快递货方数，班次，出车任务，预计出车时间，带货部门，转货部门,预计二次出车时间
	 * @author gongjp
	 * @date 2015年07月27日 14:31
	 * @param vehicleNo
	 * @param queryTime 
	 */
	SimpleTruckScheduleDto queryTruckByVehicleAndDiliverGoodsTime(String vehilceNo,String diliverGoodsTime);

	/**
	 * 查询排班任务
	 * @author 105869
	 * @date 2015年1月15日 15:59:33
	 * @param simDto
	 * @return list
	 * */
	List<TruckSchedulingTaskEntity> queryTruckSchedulingTask(SimpleTruckScheduleDto simDto);
	/**
	 * 查询接送货排班任务
	 * @author 105869
	 * @date 2015年1月16日 15:24:38
	 * @param simDto
	 * @return list
	 * */
	List<TruckSchedulingZoneEntity> queryTruckSchedulingPkpAreaInfo(SimpleTruckScheduleDto simDto);
	
	/**
	 * 通过小区编码及日期查询是否节假日，排班车牌号
	 * 
	 * @author foss-zyr
	 * @date 2015-5-21 11:04:07
	 */
	public SimpleTruckScheduleDto queryTaskByZoneCodeAndDate(String zoneCode,String ymd);
	

	/**
	 * 通过车牌号和时间查询接送货小区code，接送货类型，接送货区域名称 -
	 * 
	 * @author gongjp
	 * @date 2015-7-31 11:04:07
	 */
	public List<TruckSchedulingZoneEntity> queryReceivingAreaByVehicleAndDate(String vehicleNo,String ymd);
	
	/**
	 * 根据部门code查询部门名称
	 * 
	 * @author gongjp
	 * @date 2015-09-14 下午21:55:03
	 */
	public String searchOrgInfoByCode(String code);
	
}