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
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/TruckSchedulingTaskDao.java
 * 
 *  FILE NAME     :TruckSchedulingTaskDao.java
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
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: TruckSchedulingTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingZoneEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleExcelDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingTaskDto;

/**
 * 排班任务DAO
 * 
 * @author 096598-foss-zhongyubings
 * @date 2012-10-31 上午10:57:55
 */
public class TruckSchedulingTaskDao extends iBatis3DaoImpl implements ITruckSchedulingTaskDao {

	/**
	 * 前缀
	 */
	private static String prefix = "Foss.scheduling.truckSchedulingTask.";

	/**
	 * 新增任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#insertTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	public void insertTruckSchedulingTask(TruckSchedulingTaskEntity entity) {
		this.getSqlSession().insert(prefix + "insertTruckSchedulingTask", entity);

	}

	/**
	 * 查询任务通过ID
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */

	@Override
	public SimpleTruckScheduleDto queryTruckSchedulingTask(TruckSchedulingTaskEntity entity) {
		@SuppressWarnings("unchecked")
		List<SimpleTruckScheduleDto> tempList = this.getSqlSession().selectList(
				prefix + "queryTruckSchedulingTaskById", entity);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 联合排班计划、排班任务多表查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#querySchedulingAndTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleTruckScheduleDto> querySchedulingAndTask(SimpleTruckScheduleDto dto) {
		return this.getSqlSession().selectList(prefix + "querySchedulingAndTask", dto);
	}

	/**
	 * 更新任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#updateTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	public void updateTruckSchedulingTask(TruckSchedulingTaskEntity entity) {
		this.getSqlSession().update(prefix + "updateTruckSchedulingTask", entity);
	}

	/**
	 * 删除任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午10:57:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#deleteTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@Override
	public void deleteTruckSchedulingTask(TruckSchedulingTaskEntity entity) {
		this.getSqlSession().delete(prefix + "deleteTasksByScheduleId", entity);

	}

	/**
	 * 批量插入任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 上午11:16:13
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#batchInsertTruckSchedulingTask(java.util.List)
	 */
	@Override
	public void batchInsertTruckSchedulingTask(List<TruckSchedulingTaskEntity> list) {
		// 由原来的批量batchInsertTruckSchedulingTask插入转为单条插入batchInsertTruckSchedulingTaskOne
		if (CollectionUtils.isNotEmpty(list)) {
			// 循环
			for (TruckSchedulingTaskEntity entity : list) {
				// 插入
				this.getSqlSession().insert(prefix + "batchInsertTruckSchedulingTaskOne", entity);
			}
		}
	}

	/**
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:18:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#querySchedulingAndTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleTruckScheduleDto> querySchedulingAndTaskByWork(SimpleTruckScheduleDto simDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		// 工作列表
		List<String> list = new ArrayList<String>();
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		list.add(ScheduleConstants.PLAN_TYPE_REST);
		list.add(ScheduleConstants.PLAN_TYPE_UNDERGO);
		simDto.setList(list);
		return this.getSqlSession().selectList(prefix + "querySchedulingAndTaskByWork", simDto, rowBounds);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-31 下午3:18:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public Long queryCount(SimpleTruckScheduleDto simDto) {
		// 工作列表
		List<String> list = new ArrayList<String>();
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		list.add(ScheduleConstants.PLAN_TYPE_REST);
		list.add(ScheduleConstants.PLAN_TYPE_UNDERGO);
		simDto.setList(list);
		return (Long) this.getSqlSession().selectOne(prefix + "queryCount", simDto);
	}

	/**
	 * 批量导入任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午3:49:36
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#batchImportTruckSchedulingTask(java.util.List)
	 */
	@Override
	public void batchImportTruckSchedulingTask(List<TruckSchedulingTaskDto> list) {
		// 有原来的batchImportTruckSchedulingTask变为单条batchImportTruckSchedulingTaskOne
		if (CollectionUtils.isNotEmpty(list)) {
			// 循环
			for (TruckSchedulingTaskDto dto : list) {
				// 插入
				this.getSqlSession().insert(prefix + "batchImportTruckSchedulingTaskOne", dto);
			}
		}
	}

	/**
	 * 批量删除非工作状态的相应任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 上午10:36:37
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#deleteTasksForUnworkType(java.util.List)
	 */
	@Override
	public void deleteTasksForUnworkType(List<SimpleTruckScheduleDto> list) {
		// 由原来的deleteTasksForUnworkType改为单条deleteTasksForUnworkTypeOne
		if (CollectionUtils.isNotEmpty(list)) {
			for (SimpleTruckScheduleDto dto : list) {
				this.getSqlSession().delete(prefix + "deleteTasksForUnworkTypeOne", dto);
			}
		}
	}

	/**
	 * 查询对应的排班数据导出
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 下午3:13:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#querySchedulingForExport(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleExcelDto> querySchedulingForExport(SimpleTruckScheduleDto simDto) {
		return this.getSqlSession().selectList(prefix + "querySchedulingForExport", simDto);
	}

	/**
	 * 查询短途排班，车队小组、当天、线路、班次是否已经存在本班次的排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-15 上午9:43:54
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryHasLineNoFrequencyNoCurrentDay(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingTaskEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleExcelDto> queryHasLineNoFrequencyNoCurrentDay(SimpleTruckScheduleDto simpleTruckScheduleDto) {
		return this.getSqlSession().selectList(prefix + "queryHasLineNoFrequencyNoCurrentDay", simpleTruckScheduleDto);
	}

	/**
	 * 根据ID列表删除计划任务列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-16 下午3:30:56
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#deleteTasksByScheduleTaskIds(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public void deleteTasksByScheduleTaskIds(SimpleTruckScheduleDto truckScheduling) {
		// 根据排班任务ID 列表删除排班任务
		if (truckScheduling != null && CollectionUtils.isNotEmpty(truckScheduling.getList())) {
			this.getSqlSession().delete(prefix + "deleteTasksByScheduleTaskIds", truckScheduling);
		}
	}

	/**
	 * 根据计划ID查询任务表中是否存在计划对应的任务数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-6 下午1:53:03
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryTruckSchedulingTaskByScheduleId(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public Long queryTruckSchedulingTaskByScheduleId(TruckSchedulingEntity truckScheduling) {
		return (Long) this.getSqlSession().selectOne(prefix + "queryTruckSchedulingTaskByScheduleId", truckScheduling);
	}

	
	/**
	 * 根据部门code查询部门名称
	 * 
	 * @author gongjp
	 * @date 2015-09-14 下午21:55:03
	 */
	@Override
	public String searchOrgInfoByCode(String code) {
		return (String) this.getSqlSession().selectOne(prefix + "queryOrgInfoByCode", code);
	}
	
	/**
	 * 查询接送货排班，车队小组、当天、司机是否已经安排过
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-7 上午10:32:32
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryPKPDuliVehicleCurrentDay(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleExcelDto> queryPKPDuliVehicleCurrentDay(SimpleTruckScheduleDto simpleTruckScheduleDto) {
		return this.getSqlSession().selectList(prefix + "queryPKPDuliVehicleCurrentDay", simpleTruckScheduleDto);
	}

	/**
	 * 根据线路列表和日期 ，查询短途排班对应的排班列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-18 下午1:57:43
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryFrequencyNosBylineVirtualCode(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleTruckScheduleDto> queryFrequencyNosBylineVirtualCode(SimpleTruckScheduleDto dto) {
		return this.getSqlSession().selectList(prefix + "queryFrequencyNosByline", dto);
	}

	/**
	 * 查询某司机、某月的排班任务
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 下午3:34:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryLineInfoAndVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public SimpleTruckScheduleDto queryLineInfoAndVehicleNo(TruckSchedulingEntity tsEntity) {
		if (tsEntity != null) {
			// 出车
			tsEntity.setPlanType(ScheduleConstants.PLAN_TYPE_WORK);
			@SuppressWarnings("unchecked")
			List<SimpleTruckScheduleDto> tempList = this.getSqlSession().selectList(
					prefix + "queryLineInfoAndVehicleNo", tsEntity);
			if (CollectionUtils.isNotEmpty(tempList)) {
				return tempList.get(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 通过车牌号及约车受理时间，读取短途排版表中的司机，如果获取多个司机
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午2:57:24
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryTaskByVehicleNoAndDate(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleTruckScheduleDto> queryTaskByVehicleNoAndDate(SimpleTruckScheduleDto simpleTruckScheduleDto) {
		if (simpleTruckScheduleDto != null) {
			return this.getSqlSession().selectList(prefix + "queryTaskByVehicleNoAndDate", simpleTruckScheduleDto);
		} else {
			return null;
		}
	}
	
	/**
	 * 通过小区编码及日期查询是否节假日，排班车牌号
	 * 
	 * @author foss-zyr
	 * @date 2015-5-21 11:04:07
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryTaskByZoneCodeAndDate(String,String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SimpleTruckScheduleDto queryTaskByZoneCodeAndDate(String zoneCode,String ymd) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("zoneCode", zoneCode);
		map.put("Ymd",ymd);
		List<SimpleTruckScheduleDto> temp =this.getSqlSession().selectList(prefix+"queryTaskByZoneCodeAndDate",map);
		if(CollectionUtils.isNotEmpty(temp)&&temp.size()>0){
			return temp.get(0);
		}else{
			return  null;
		}
	}

	/** 
	 * @Title: delteTask 
	 * @Description: 删除单个排班任务
	 * @param scheduleTaskId    
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#delteTask(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-23下午4:08:32
	 */ 
	@Override
	public void delteTask(String scheduleTaskId) {
		this.getSqlSession().delete(prefix + "delteTask", scheduleTaskId);
	}

	/** 
	 * @Title: validateTruckSchedulingTask 
	 * @Description: 根据排班id, 排班任务id, 车牌号, 线路号, 班次号查询排班任务. 
	 * @param simDto
	 * @return    
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#validateTruckSchedulingTask(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-24上午10:23:42
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingTaskEntity> validateTruckSchedulingTask(
			SimpleTruckScheduleDto simDto) {
		return this.getSqlSession().selectList(prefix + "validateTruckSchedulingTask", simDto);
	}
	/**
	 * 通过排班车辆id删除非工作日区域
	 * @author heyongdong
	 * @date 2014年5月15日 14:45:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#deleteTaskspkpAreabyId(java.lang.String)
	 */
	@Override
	public int deleteTaskspkpAreabyId(String scheduleTaskId) {
		
		return this.getSqlSession().delete(prefix+"deleteTaskspkpAreabyId", scheduleTaskId);
	}
	
	/**
	 * 通过排班车辆任务id和接送货类型删除非工作日接货小区或者送货小区
	 * @author zyr
	 * @date 2015年08月22日 11:37:44
	 * @param scheduleTaskId
	 * @return int
	 */
	@Override
	public int deleteTaskspkpArea(String scheduleTaskId,String regionType) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("scheduleTaskId", scheduleTaskId);
		map.put("regionType",regionType);
		return this.getSqlSession().delete(prefix+"deleteTaskspkpArea", map);
	}
	
	/**
	 * 批量插入非工作日接货小区
	 * @author heyongdong
	 * @date 2014年5月15日 14:46:00
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#addTaskspkpArea(java.util.List)
	 */
	@Override
	public int addTaskspkpArea(List<TruckSchedulingZoneEntity> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for(TruckSchedulingZoneEntity entity:list){
				 this.getSqlSession().insert(prefix+"addTaskspkpArea", entity);
			}
		}
		return 0;
	}
	/**
	 * 根据车牌号查询非工作日最新的绑定信息
	 * @author foss-heyongdong
	 * @date 2014年5月21日 10:28:30
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryPkpAreaInfoByVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingZoneEntity> queryPkpAreaInfoByVehicleNo(SimpleTruckScheduleDto smDto){
		return this.getSqlSession().selectList(prefix+"queryPkpAreaInfoByVehicleNo", smDto);
	}
	/**
	 * 根据区域编号和时间查询当日绑定的车辆
	 * @author foss-heyongdong
	 * @param zoneCode
	 * @param queryTime
	 * @return
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingTaskDao#queryVehicleByZoneCode(String,String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TruckSchedulingZoneEntity queryVehicleByZoneCode(String zoneCode,
			String queryTime) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("zoneCode", zoneCode);
		map.put("queryTime",queryTime);
		List<TruckSchedulingZoneEntity> temp =this.getSqlSession().selectList(prefix+"querySchedulingZoneInfo",map);
		if(CollectionUtils.isNotEmpty(temp)&&temp.size()>0){
			return temp.get(0);
		}else{
			return  null;
		}
		
	}
	/**
	 * 根据车牌号和时间查询当日车辆绑定司机
	 * @author heyongdong
	 * @date 2014年5月27日 14:36:33
	 * @param vehilceNo
	 * @param queryTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TruckSchedulingZoneEntity queryDriverByVehicle(String vehilceNo,
			String queryTime) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("vehilceNo", vehilceNo);
		map.put("queryTime",queryTime);
		List<TruckSchedulingZoneEntity> temp =this.getSqlSession().selectList(prefix+"queryDriverByVehicle",map);
		if(CollectionUtils.isNotEmpty(temp)&&temp.size()>0){
			return temp.get(0);
		}else{
			return  null;
		}
	}
	
	/**
	 *  根据车牌号和时间查询车辆净空，车辆载重，是否带快递货， 带快递货方数，班次
	 * @author foss-zyr
	 * @date 2015年5月12日 09:56:14
	 * @param vehicleNo
	 * @param cqueryTime 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SimpleTruckScheduleDto queryTruckByVehicle(String vehilceNo,String queryTime) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("vehilceNo", vehilceNo);
		map.put("queryTime",queryTime);
		List<SimpleTruckScheduleDto> temp =this.getSqlSession().selectList(prefix+"queryTruckByVehicle",map);
		if(CollectionUtils.isNotEmpty(temp)&&temp.size()>0){
			return temp.get(0);
		}else{
			return  null;
		}
	}

	/**
	 *  根据车牌号和时间查询车辆净空，车辆载重， 带快递货方数，班次,出车任务，预计出车时间，带货部门，转货部门，预计二次出车时间
	 * @author gongjp
	 * @date 2015年07月27日 14:33
	 * @param vehicleNo
	 * @param diliverGoodsTime 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SimpleTruckScheduleDto queryTruckByVehicleAndDiliverGoodsTime(String vehilceNo,String diliverGoodsTime) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("vehilceNo", vehilceNo);
		map.put("diliverGoodsTime",diliverGoodsTime);
		List<SimpleTruckScheduleDto> temp =this.getSqlSession().selectList(prefix+"queryTruckByVehicleAndDiliverGoodsTime",map);
		if(CollectionUtils.isNotEmpty(temp)&&temp.size()>0){
			return temp.get(0);
		}else{
			return  null;
		}
	}
	
	/**
	 * 查询排班任务
	 * @author 105869
	 * @date 2015年1月15日 15:59:33
	 * @param simDto
	 * @return list
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingTaskEntity> queryTruckSchedulingTask(SimpleTruckScheduleDto simDto){
		return this.getSqlSession().selectList(prefix+"queryTruckSchedulingTask", simDto);
	}
	
	/**
	 * 查询接送货排班任务
	 * @author 105869
	 * @date 2015年1月16日 15:24:38
	 * @param simDto
	 * @return list
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingZoneEntity> queryTruckSchedulingPkpAreaInfo(SimpleTruckScheduleDto simDto){
		return this.getSqlSession().selectList(prefix+"queryTruckSchedulingPkpAreaInfo",simDto);
	}
	/**
	 * 通过车牌号和时间查询接送货小区code，接送货类型，接送货区域名称 -
	 * 
	 * @author gongjp
	 * @date 2015-7-31 11:04:07
	 */
	@SuppressWarnings("unchecked")
	public List<TruckSchedulingZoneEntity> queryReceivingAreaByVehicleAndDate(String vehicleNo,String ymd){
		Map<String,String> map =new HashMap<String,String>();
		map.put("vehicleNo", vehicleNo);
		map.put("Ymd",ymd);
		return this.getSqlSession().selectList(prefix+"queryReceivingAreaByVehicleAndDate",map);
	}
}