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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/ITruckSchedulingTaskService.java
 * 
 *  FILE NAME     :ITruckSchedulingTaskService.java
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
 * FILE    NAME: ITruckSchedulingTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleExcelDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ShortScheduleException;

/**
 * 排班任务Service
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-31 上午10:59:12
 */
public interface ITruckSchedulingTaskService {

	/**
	 * 新增任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:55:13
	 */
	void insertTruckSchedulingTask(TruckSchedulingTaskEntity entity) throws ShortScheduleException;

	/**
	 * 查询任务通过ID
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:55:49
	 */
	SimpleTruckScheduleDto queryTruckSchedulingTask(TruckSchedulingTaskEntity entity) throws ShortScheduleException;

	/**
	 * 联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:56:24
	 */
	List<SimpleTruckScheduleDto> querySchedulingAndTask(SimpleTruckScheduleDto dto) throws ShortScheduleException;

	/**
	 * 更新任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:04
	 */
	void updateTruckSchedulingTask(TruckSchedulingTaskEntity entity) throws ShortScheduleException;

	/**
	 * 删除任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:14
	 */
	void deleteTruckSchedulingTask(TruckSchedulingTaskEntity entity) throws ShortScheduleException;

	/**
	 * 根据ID列表删除计划任务列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-16 下午3:29:28
	 */
	void deleteTasksByScheduleTaskIds(SimpleTruckScheduleDto truckScheduling, CurrentInfo user);

	/**
	 * 分页查询联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:11:00
	 */
	List<SimpleTruckScheduleDto> querySchedulingAndTask(SimpleTruckScheduleDto simDto, int limit, int start)
			throws ShortScheduleException;

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:12:32
	 */
	Long queryCount(SimpleTruckScheduleDto simDto) throws ShortScheduleException;

	/**
	 * 批量导入排班任务表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午12:49:21
	 */
	void batchImportTaskList(List<ScheduleExcelDto> excelDtos, Map<String, DriverAssociationDto> existDriverMap,
			TruckSchedulingEntity truckSchedulingEntity, UserEntity user) throws ShortScheduleException;

	/**
	 * 根据车队小组、年月日、司机查询排班任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @return
	 * @date 2012-11-5 下午3:36:23
	 */
	List<SimpleTruckScheduleDto> queryShortScheduleTaskByDriver(SimpleTruckScheduleDto simDto)
			throws ShortScheduleException;

	/**
	 * 保存新的任务，并查询返回本任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午2:41:59
	 */
	List<SimpleTruckScheduleDto> saveOrUpdateTaskAndFetchTask(SimpleTruckScheduleDto simDto, UserEntity user)
			throws ShortScheduleException;

	/**
	 * 修改计划状态，并删除对应任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午8:14:48
	 */
	void delteTaskAndModifySchedule(SimpleTruckScheduleDto simDto, CurrentInfo user) throws ShortScheduleException;

	/**
	 * 通过"线路"、"班次"查询"发车时间"
	 * 
	 * @param lineNo
	 *            线路编号
	 * @param frequencyNo
	 *            班次
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-5 下午3:36:01
	 */
	SimpleTruckScheduleDto queryDepartTimeByLineNoAndFrequenceNo(String lineNo, String frequencyNo)
			throws ShortScheduleException;

	/**
	 * 根据车牌号查询车辆定人定区
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-12 上午11:43:03
	 */
	RegionVehicleEntity queryZoneCodeByVehicleNo(String vehicleNo) throws ShortScheduleException;
	
	/**
	 * 根据车牌号查询车辆定人定区送货区域信息
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @author 218442-foss-zhuyunrong
	 * @date 2015-04-20 上午11:45:47 
	 */
	RegionVehicleEntity queryDeliveryCodeByVehicleNo(String vehicleNo) throws ShortScheduleException;

	/**
	 * 根据车牌号获取车辆信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-12 下午3:33:44
	 */
	SimpleTruckScheduleDto queryCarInfoByVehicleNo(String vehicleNo) throws ShortScheduleException;

	/**
	 * 通过司机编号查询司机信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-13 下午2:06:02
	 */
	DriverAssociationDto queryOwnDriveByDriverCode(String driverCode) throws OwnDriverException;

	/**
	 * 导出排班表信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-13 下午5:17:59
	 */
	InputStream exportExcelStream(SimpleTruckScheduleDto simDto) throws ShortScheduleException;

	/**
	 * 查询对应的排班数据导出 ,主要是通过车队小组编码，年月进行查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 下午3:12:25
	 */
	List<ScheduleExcelDto> querySchedulingForExport(SimpleTruckScheduleDto truckSchedulingDto)
			throws ShortScheduleException;

	/**
	 * 查询车队小组、当天、线路、班次是否已经存在本班次的排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-15 上午9:37:33
	 */
	List<ScheduleExcelDto> queryHasLineNoFrequencyNoCurrentDay(SimpleTruckScheduleDto simpleTruckScheduleDto)
			throws ShortScheduleException;

	/**
	 * 根据线路列表和日期 ，查询短途排班对应的排班列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-18 下午1:40:06
	 */
	List<SimpleTruckScheduleDto> queryFrequencyNosBylineVirtualCode(List<String> lineVirtualCode, Date planDate);

	/**
	 * 通过车牌号及约车受理时间，读取短途排版表中的司机，如果获取多个司机
	 * 
	 * @param ymd
	 *            排班日期
	 * @param vehicleNo
	 *            车牌号
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午2:50:01
	 */
	List<SimpleTruckScheduleDto> queryTaskByVehicleNoAndDate(SimpleTruckScheduleDto simpleTruckScheduleDto);

	/**
	 * 校验数据合法性
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-10 下午2:19:40
	 */
	public StringBuffer validateVehicleAndLineInfo(List<ScheduleExcelDto> excelDtos,
			TruckSchedulingEntity truckSchedulingEntity);

	/** 
	 * @Title: delteTask 
	 * @Description: 删除单个任务 
	 * @param simDto
	 * @param user    
	 * @return void    返回类型 
	 * delteTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-23下午3:18:57
	 */ 
	void deleteTask(String scheduleTaskId);

	/**
	 *
	 * 根据区域编号和时间查询当日绑定的车辆
	 * @author foss-heyongdong
	 * @date 2014年5月8日 14:56:14
	 * @param zoneCode
	 * @param queryTime 
	 * */
	TruckSchedulingZoneEntity queryVehicleByZoneCode(String zoneCode, Date queryTime);
	
	/**
	 *
	 * 根据车牌号和时间查询当日车辆绑定司机
	 * @author foss-heyongdong
	 * @date 2014年5月8日 14:56:14
	 * @param vehicleNo
	 * @param queryTime 
	 * */
	TruckSchedulingZoneEntity queryDriverByVehicle(String vehilceNo,Date queryTime);
	
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
	 * @date 2015年07月27日 14:13
	 * @param vehicleNo
	 * @param diliverGoodsTime 
	 */
	SimpleTruckScheduleDto queryTruckByVehicleAndDiliverGoodsTime(String vehilceNo,String diliverGoodsTime);
	
	 /**
	 * 根据车牌号查询非工作日最新的绑定信息
	 * @author foss-heyongdong
	 * @date 2014年5月21日 10:28:30
	 * @param vehicleNo
	 * @param queryTime 
	 * */
	List<TruckSchedulingZoneEntity> queryPkpAreaInfoByVehicleNo(SimpleTruckScheduleDto simDto);
	/**
	 * 通过小区编码及日期查询是否节假日，排班车牌号
	 * 
	 * @param zoneCode 小区编码
	 * @param Ymd      日期
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
}