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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/PlatformDispatchService.java
 * 
 *  FILE NAME     :PlatformDispatchService.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: PlatformDispatchService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.classes.ReflectionUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.load.api.server.service.IQueryProgressService;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;
import com.deppon.foss.module.transfer.scheduling.api.define.PlatformDispatchConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPlatformDispatchDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPlatformDispatchService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TransferDeptInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.PlatformDispatchException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;

/**
 * 月台Service实现
 * 
 * SUC-50查询月台信息
 * 查询月台信息步骤
 * 					1、	每个月台分两行显示，
 * 
 * 						第一行为计划停靠信息，
 * 
 * 						第二行为实际停靠信息；
 * 
 *					2、	月台显示按预计安排的车牌和实际使用的车牌显示，
 *
 * 						计划停靠车牌号用蓝底显示，
 * 
 *						实际停靠车牌号用灰底显示，
 *
 *						车牌号所占长度为所用时间长度，
 *
 *						计划停靠所用时间均为计划时间；
 *
 *					3、	只有小于等于当前时间的时间段才有实际停靠车牌号，
 *
 *						大于当前时间段的只有计划停靠车牌号；
 *
 *					4、	离当前时间点最近的半点纵轴高亮显示；
 *
 *
 * 查看月台步骤
 * 					1、	外场调度已查询出月台信息；
 * 
 *					2、	弹出月台详情，参见图2 月台
 *					
 *						详情界面；
 *						
 *					3、	界面信息均来自月台基础信息，
 * 					
 *						参见SUC-184新增_修改_作废_查询月台号；
 *
 * 清空月台步骤 		
 * 					1、	外场调度已查询出月台信息；
 * 
 *					2、	如果点击的车牌号所占时间段大于等于当前时间，
 *
 *						则弹出清空月台和修改月台界面，界面包含两个页签，否则不响应；
 *
 *					3、	只有当前时间占用该月台的车辆才有装卸货进度；
 *
 *					4、	根据车牌号，查询本外场目前的装卸货任务，
 *
 *						根据装卸车任务类型显示该车辆的装货进度或卸货进度；
 *
 *					5、	卸货进度可参考SUC-94查询卸车进度用例，
 *
 *						装货进度可参考SUC-232查询装车进度用例；
 *
 *					6、	时间段默认为当前该车辆所占用的时间段，不可修改；
 *
 *					7、	如果为计划停靠的车辆，则只清空该车辆的计划停靠信息；
 *
 *					8、	如果为实际使用的车辆，则生成历史记录，记录使用车牌号，
 *
 *						开始使用时间和结束使用时间，开始使用时间为实际开始使用时间，
 *
 *						结束使用时间为点击清空月台按钮时间；
 *
 *						关闭清空月台和修改月台界面；
 *
 * 修改月台步骤  	
 * 					1、	外场调度已查询出月台信息；
 * 
 *					2、	如果点击的车牌号所占时间段大于或包含当前时间，
 *
 *						则弹出清空月台和修改月台界面（即不能修改历史记录），
 *
 *						界面包含两个页签；
 *
 *					3、	只有当前时间使用该月台的车辆才有装卸货进度；
 *
 *					4、	根据车牌号，查询本外场目前的装卸货任务，
 *
 *						根据装卸车任务类型显示该车辆的装货进度或卸货进度；
 *
 *					5、	卸货进度可参考SUC-94查询卸车进度用例，
 *
 *						装货进度可参考SUC-232查询装车进度用例；
 *
 *					6、	时间段默认为当前该车辆所占用的时间段；
 *
 *					7、	月台号默认为当前该车辆所使用或计划停靠的月台号；
 *
 *					8、	如果步骤1选择的车牌号为实际使用的车牌号，
 *
 *						则月台号不能修改，起始时间不能修改，
 *
 *						只能修改结束时间，且结束时间不能小于当前时间；
 *
 *					9、	如果步骤1选择的车牌号为计划停靠信息的车牌号，
 *
 *						则时间和月台号均可以修改，起始时间不能小于当前时间；
 *
 *					10、	如果修改的是从从发车计划、
 *
 *						卸货月台分配生成过来的计划停靠信息，
 *
 *						则提示“该车辆计划停靠的是发车计划或卸货月台分配的月台号，
 *
 *						是否确认修改”，选择是，则进行修改，否则不修改，
 *
 *						如果修改了月台号，则需要关联更新发车计划月台号或卸货分配月台号；
 *
 *					11、	不保留原计划停靠信息；
 *
 *						关闭清空月台和修改月台界面；
 *
 * 使用月台
 * 					1、	弹出使用月台界面；
 * 
 *					2、	月台号为点击行所在的月台号；
 *
 *					3、	车牌号来源车辆基础信息；
 *
 *					4、	时间段默认为当前时间点所在的最小时间间隔，
 *
 *						如当前时间点为2012-06-11 17:06，
 *
 *						则最小时间间隔为2012-06-11 17:00至2012-06-11 17:30；
 *
 *					5、	时间段选择范围为当前时间点前后12小时，以半点刻度为一项；
 *
 *					7、	起始时间不能大于等于截止时间；
 *
 *					8、	截止时间不能小于当前时间；
 *
 *					9、	车牌号不能为空；
 *
 *					10、	如果所选时间段最小时间大于当前时间，
 *
 *						则该车辆为计划停靠信息，记录计划停靠车牌号，
 *
 *						预计开始使用时间和预计结束时间用时间，
 *
 *						预计开始使用时间为所选时间段最小时间，
 *
 *						预计结束时间为所选时间段最大时间；
 *
 *					11、	如果所选时间段的最小时间小于当前时间，
 *
 *						则月台状态改为使用中，开始时间为步骤2选择的起始时间，
 *
 *						预计结束时间为步骤2选择的结束时间；
 *
 *					12、	如果当前月台有正在使用的车辆，
 *
 *						则最所选时间段的最小时间不能小于当前正在使用的车辆的预计结束时间，
 *
 *						否则，给出提示“所选时间中已有车辆正在使用，请修改开始时间”；
 *
 *					13、关闭使用月台界面，同时如果有对月台占用的操作，则刷新该月台的信息；
 *
 * @author 104306-foss-wangLong
 * @date 2012-10-31 下午3:43:00
 * 

 */
public class PlatformDispatchService implements IPlatformDispatchService {
	//日志
	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformDispatchService.class);
	//月台DAO
	private IPlatformDispatchDao platformDispatchDao;
	//综合月台接口
	private IPlatformService platformService;
	//车辆接口
	private IVehicleService vehicleService;
	//组织信息接口
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//未知接口
	private IOutfieldService outfieldService;
	/** 装卸车进度service */
	private IQueryProgressService queryProgressService;
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-9 上午10:30:01
	 * @param platformDistributeEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity
	 * @throws PlatformDispatchException
	 */
	@Transactional
	public int addPlatformDistribute(PlatformDistributeEntity distributeEntity)
			throws PlatformDispatchException {
		String startschedule = PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_STARTSCHEDULE;
		String load = PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_LOAD;
		String unload = PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD;
		String scheduleSource = distributeEntity.getScheduleSource();
		// 如果为发车计划 || 装卸车任务  不检查参数
		if (!(StringUtil.equals(scheduleSource, startschedule) 
				|| StringUtil.equals(scheduleSource, load) 
				|| StringUtil.equals(scheduleSource, unload))) {
			// 参数检查
			checkParameters(distributeEntity);
		}
		return platformDispatchDao.addPlatformDistribute(distributeEntity);
	}
	
	/**
	 * 新增月台计划停靠任务  提供给车辆到达
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午7:47:41
	 * @param virtualCode 		月台虚拟编号  非月台编号  {@link PlatformEntity#getVirtualCode}
	 * @param useStartTime 		使用开始时间
	 * @param useEndTime 		使用结束时间
	 * @param vehicleModel 		车型
	 * @param vehicleNo			车牌号
	 * @return 受影响的行数
	 * @throws PlatformDispatchException
	 */
	@Transactional
	public int addPlatformDistributeForArrival(String virtualCode, Date useStartTime, Date useEndTime, String vehicleNo) 
			throws PlatformDispatchException {
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		// 获得外场
		String deptCode = FossUserContext.getCurrentDeptCode();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = getTransferCenter(deptCode);
		// 外场编码
		platformDistributeEntity.setTransferCenterNo(orgAdministrativeInfoEntity.getCode());
		// 外场名称
		platformDistributeEntity.setTransferCenterName(orgAdministrativeInfoEntity.getName());
		// 月台虚拟编码
		platformDistributeEntity.setPlatformNo(virtualCode);
		// 使用启始时间 
		platformDistributeEntity.setUseStartTime(useStartTime);
		// 使用结束时间 
		platformDistributeEntity.setUseEndTime(useEndTime);
		// 车牌号 
		platformDistributeEntity.setVehicleNo(vehicleNo);
		// 装车任务编号
		platformDistributeEntity.setLoadTaskNo(null);
		// 状态
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		// 分配类型  计划 
		platformDistributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		// 计划来源  车辆到达
		platformDistributeEntity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_ARRIVAL);
		return addPlatformDistribute(platformDistributeEntity);
	}

	
	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午1:47:52
	 * @param platformDistributeEntity
	 * @return 受影响的行数
	 * @throws PlatformDispatchException
	 */
	@Transactional
	public int updatePlatformDistribute(PlatformDistributeEntity platformDistributeEntity) 
			throws PlatformDispatchException {
		return platformDispatchDao.updatePlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 修改状态
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午1:47:52
	 * @param ids  id集合
	 * @param status  状态
	 * @return 受影响的行数
	 */
	@Transactional
	private int updatePlatformDistributeStatusByIds(List<String> ids, String status) {
		return platformDispatchDao.updatePlatformDistributeStatusByIds(ids, status);
	}
	
	/**
	 * 查询月台使用情况 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-3 下午5:04:40
	 * @param platformDistributeDto
	 * @return java.util.List
	 * @see #queryPlatformDistributeList 根据计划类型 查询月台使用情况
	 * @see #convertMapByPlatformNo		   转换map
	 * @see #handleScheduleCall			   计划停靠
	 * @see #handleActualCall			   实际停靠
	 */
	public List<PlatformDistributeDto> queryPlatformUseInfo(PlatformDistributeDto platformDistributeDto) {
		if (platformDistributeDto == null) {
			platformDistributeDto = new PlatformDistributeDto();
		}
		PlatformDistributeEntity platformDistributeEntity = platformDistributeDto.getPlatformDistributeEntity();
		// 如果查询条件 部门编码为空   默认位当前用户所在部门
		if (StringUtil.isBlank(platformDistributeEntity.getTransferCenterNo())) {
			// 当前部门
			String currentDeptCode = FossUserContext.getCurrentDeptCode();
			LOGGER.info("当前部门编码: " + currentDeptCode);
			// 通过当前部门查询对应的外场信息
			String transferCenterNo = getTransferCenterCode(currentDeptCode);
			platformDistributeEntity.setTransferCenterNo(transferCenterNo);
		}
		LOGGER.info("外场编号:" + platformDistributeEntity.getTransferCenterNo());
		// 综合月台信息List
		List<PlatformEntity> list = queryPlatformListByCondition(platformDistributeDto);
		// 月台信息为空
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<PlatformDistributeDto>();
		}
		List<PlatformEntity> platformList = new CopyOnWriteArrayList<PlatformEntity>(list);
		// 月台分配信息
		// 计划停靠
		List<PlatformDistributeEntity> plansPlatformDistributeList = queryPlatformDistributeList(platformDistributeDto, PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		// 实际停靠  如果只显示可用月台  actualPlatformDistributeList 返回的是正在使用中的月台
		List<PlatformDistributeEntity> actualPlatformDistributeList = queryPlatformDistributeList(platformDistributeDto, PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		// 按月台编号分组  key为月台的编码  value为当前月台使用情况
		Map<String, List<PlatformDistributeEntity>> plansPlatformDistributeMap = convertMapByPlatformNo(plansPlatformDistributeList);
		// 实际停靠
		Map<String, List<PlatformDistributeEntity>> actualPlatformDistributeMap = convertMapByPlatformNo(actualPlatformDistributeList);
		// 只显示可用月台
		boolean showAvailable = platformDistributeDto.isShowAvailable();
		if (showAvailable) {
			// 只显示可用月台处理   删除platformList中 正在使用的月台信息
			removeUsingPlatform(platformList, actualPlatformDistributeMap);
			actualPlatformDistributeMap = new HashMap<String, List<PlatformDistributeEntity>>();
		}
		// 最终返回结果
		List<PlatformDistributeDto> resultList = new ArrayList<PlatformDistributeDto>();
		PlatformDistributeDto distributeDto = null;
		// 依综合月台信息为主  可能会出现  基础信息月台作废  无法查询出历史记录
		for (PlatformEntity platformEntity : platformList) {
			// 月台号 显示
			String platformCode = platformEntity.getPlatformCode();
			// 虚拟编号  外键关联
			String virtualCode = platformEntity.getVirtualCode();
			// 计划停靠
			distributeDto = handleScheduleCall(plansPlatformDistributeMap, platformCode, virtualCode);
			resultList.add(distributeDto);
			// 实际停靠
			distributeDto = handleActualCall(actualPlatformDistributeMap, platformCode, virtualCode);
			resultList.add(distributeDto);
		}
		// 按车牌号查询时  只显示相关月台 过滤掉其他月台  BUG-802
		// 只显示可用月台
		if (StringUtil.isNotBlank(platformDistributeEntity.getVehicleNo()) && !showAvailable) {
			resultList = doFilterNotVehicleRecord(resultList);
		}
		sortResult(resultList);
		return resultList;
	}
	
	/**
	 * 输入车牌号时 过滤掉与此车牌号无关的月台
	 * @author 104306-foss-wangLong
	 * @date 2013-1-8 下午5:08:04
	 * @param resultList
	 * @return {@link java.util.List<doFilterNotVehicleRecord>}
	 */
	private List<PlatformDistributeDto> doFilterNotVehicleRecord(List<PlatformDistributeDto> resultList) {
		if (CollectionUtils.isEmpty(resultList)) {
			return resultList;
		}
		Reflector reflector = Reflector.forClass(PlatformDistributeDto.class);
		String[] readablePropertyNames = reflector.getGetablePropertyNames();
		if (ArrayUtils.isEmpty(readablePropertyNames)) {
			return resultList;
		}
		Set<String> platformVirtualCodeSet = new HashSet<String>();
		for (PlatformDistributeDto platformDistributeDto : resultList) {
			for (String fieldName : readablePropertyNames) {
				if (fieldName.indexOf("hour") == -1) {
					continue;
				}
				Invoker invoker = reflector.getGetInvoker(fieldName);
				Object value = null;
				try {
					value = invoker.invoke(platformDistributeDto, null);
				} catch (Exception e) {
					LOGGER.error("错误信息:{}", e);
				}
				if (value != null && StringUtil.isNotBlank(value.toString())) {
					platformVirtualCodeSet.add(platformDistributeDto.getPlatformVirtualCode());
					break;
				}
			}
		}
		List<PlatformDistributeDto> filterResult = new ArrayList<PlatformDistributeDto>();
		if (platformVirtualCodeSet.isEmpty()) {
			return filterResult;
		}
		for (PlatformDistributeDto platformDistributeDto : resultList) {
			if (platformVirtualCodeSet.contains(platformDistributeDto.getPlatformVirtualCode())) {
				filterResult.add(platformDistributeDto);
			}
		}
		return filterResult;
	}
	
	/**
	 * 查询月台详情(基础信息)
	 * @author 104306-foss-wangLong
	 * @date 2012-11-9 上午10:09:09
	 * @param platformNo 月台号
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService#queryPlatformByVirtualCode
	 */
	public PlatformEntity queryPlatformDetail(String platformNo) {
		// PlatformDistributeEntity#platformNo 属性对应 PlatformEntity#virtualCode
		return platformService.queryPlatformByVirtualCode(platformNo);
	}
	
	/**
	 * 查询单个对象（按主键查询）
	 * <br>------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午11:40:55
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity
	 */
	public PlatformDistributeEntity queryPlatformDistribute(String id) {
		return platformDispatchDao.queryPlatformDistribute(id);
	}
	
	/**
	 * 查询当前正在使用的月台
	 * <br>--------------------<br>
	 * key 月台虚拟编号<br>
	 * value 月台实体
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午9:23:09
	 * @return {@link java.util.Map}
	 */
	public Map<String, PlatformDistributeEntity> queryUseingPlatform() {
		PlatformDistributeDto platformDistributeDto = new PlatformDistributeDto();
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		// 状态 为正在使用
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		// 类型位  当前停靠
		platformDistributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		platformDistributeDto.setPlatformDistributeEntity(platformDistributeEntity);
		Map<String, PlatformDistributeEntity> resultMap = new HashMap<String, PlatformDistributeEntity>();
		List<PlatformDistributeEntity>  list = platformDispatchDao.queryPlatformDistributeList(platformDistributeDto);
		if (list == null || list.isEmpty()) {
			return resultMap;
		}
		for (PlatformDistributeEntity entity : list) {
			resultMap.put(entity.getPlatformNo(), platformDistributeEntity);
		}
		return resultMap;
	}
	
	/**
	 * 月台使用时间是否大于当前时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午8:28:39
	 * @param id 月台使用主键id
	 * @return 如果月台使用结束时间  大于当前时间  返回false  否则true(月台未使用, 使用中都会返回true)
	 */
	public boolean doUseTimeIfGreaterThanOrEqualCurrentTime(String id) {
		PlatformDistributeEntity platformDistributeEntity = queryPlatformDistribute(id);
		if (platformDistributeEntity == null) {
			// 异常
			return false;
		}
		if (PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED.equals(platformDistributeEntity.getStatus())) {
			// 已清空过
			return false;
		}
		// 月台正在使用  返回true  可以清空 BUG-3974
		if (PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING.equals(platformDistributeEntity.getStatus())) {
			return true;
		}
		Date endTime = platformDistributeEntity.getUseEndTime();
		Date nowTime = new Date();
		//小于 -1  大于 1  等于0
		return endTime.compareTo(nowTime) >= 0;
	}
	
	/**
	 * 清空月台
	 * <br>--------------------<br>
	 * 1、如果为计划停靠的车辆，则只清空该车辆的计划停靠信息；</br>
	 * 2、如果为实际使用的车辆，则生成历史记录，记录使用车牌号，开始使用时间和结束使用时间，开始使用时间为实际开始使用时间，结束使用时间为点击清空月台按钮时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午10:33:32
	 * @param platformDistributeEntity
	 */
	@Transactional
	public void clearPlatform(PlatformDistributeEntity platformDistributeEntity) {
		if (platformDistributeEntity == null) {
			//异常
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"参数错误"});
		}
		// 1.计划停靠
		if (PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN.equals(platformDistributeEntity.getType()) ) {
			List<String> ids = new ArrayList<String>();
			ids.add(platformDistributeEntity.getId());
			updatePlatformDistributeStatusByIds(ids, PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED);
			return;
		} 
		//2. 实际停靠
		String id = platformDistributeEntity.getId();
		Date nowDate = new Date();
		platformDistributeEntity = new PlatformDistributeEntity();
		platformDistributeEntity.setId(id);
		platformDistributeEntity.setUseEndTime(nowDate);
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED);
		updatePlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 修改月台使用情况
	 * <br>--------------------<br>
	 * 校验结束时间不能小于当前时间, 结束时间不能小于开始时间  
	 * 根据{@link PlatformDistributeEntity#getType}确定是“实际停靠”,“计划停靠” <br>
	 * 如果为计划停靠   修改了月台号， 校验修改的时间段是否有其他车辆停靠， 关联更新发车计划月台号或卸货分配月台号
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午2:57:58
	 * @param platformDistributeEntity
	 */
	@Transactional
	public void updatePlatformUseInfo(PlatformDistributeEntity platformDistributeEntity) {
		if (platformDistributeEntity == null) {
			// 异常
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"参数错误"});
		}
		String type = platformDistributeEntity.getType();
		Date userStartTime = platformDistributeEntity.getUseStartTime();
		Date useEndTime = platformDistributeEntity.getUseEndTime();
		String platformNo = platformDistributeEntity.getPlatformNo();
		if (StringUtil.isBlank(platformNo)) {
			// 异常  月台编号错误.
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"月台号不能为空!"});
		}
		if (userStartTime == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"起始时间不能为空!"});	
		}
		if (useEndTime == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"结束时间不能为空!"});	
		}
		Calendar useStartCalendar = DateUtils.toCalendar(userStartTime);
		Calendar useEndCalendar = DateUtils.toCalendar(useEndTime);
		Calendar nowCalendar = DateUtils.toCalendar(new Date());
		// 为计划停靠信息，起始时间不能小于当前时间
		if(StringUtil.equals(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN, type) &&  useStartCalendar.before(nowCalendar)) {
    		throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"起始时间不能小于当前时间!"});
    	}
    	// 结束时间不能小于当前时间
    	if(useEndCalendar.before(nowCalendar)) {
    		throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"结束时间不能小于当前时间!"});
    	}
    	// 开始时间不能大于等于结束时间
    	if(useStartCalendar.after(useEndCalendar) || useStartCalendar.compareTo(useEndCalendar) == 0) {
    		throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"结束时间不能小于等于开始时间!"});
    	}
		updatePlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 使用月台(分配月台任务)
	 * @author 104306-foss-wangLong
	 * @date 2012-11-14 下午4:35:20
	 * @param platformDistributeEntity  
	 */
	@Transactional
	public void dispatchPlatform(PlatformDistributeEntity platformDistributeEntity) {
		checkParameters(platformDistributeEntity);
		Calendar useStartCalendar = DateUtils.toCalendar(platformDistributeEntity.getUseStartTime());
		Calendar nowCalendar = DateUtils.toCalendar(new Date());
		// 如果所选时间段最小时间大于当前时间，则该车辆为计划停靠
		platformDistributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		if (useStartCalendar.after(nowCalendar)) {
			platformDistributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		} 
		// 如果所选时间段的最小时间小于当前时间，则月台状态改为使用中，
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		if (useStartCalendar.before(nowCalendar)) {
			platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		}
		// 月台虚拟编号
		String platformVirtualCode = platformDistributeEntity.getPlatformNo();
		PlatformEntity platformEntity = platformService.queryPlatformByVirtualCode(platformVirtualCode);
		if (platformEntity == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"月台基础信息不存在!"});
		}
		//外场编码
		String transferCenterNo = platformEntity.getOrganizationCode();
		if (StringUtil.isBlank(transferCenterNo)) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"当前月台所在的外场编码为空."});
		}
		platformDistributeEntity.setTransferCenterNo(transferCenterNo);
		// 外场名称 
		String transferCenterName = platformEntity.getOrganizationName();
		if (StringUtil.isBlank(transferCenterName)) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"当前月台所在的外场名称为空."});
		}
		platformDistributeEntity.setTransferCenterName(transferCenterName);
		// 如果为实际停靠 && 月台正在使用中  检查月台使用情况
		// 如果当前月台有正在使用的车辆，给出提示“所选时间中已有车辆正在使用，请修改开始时间”；
		if (PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING.equals(platformDistributeEntity.getStatus()) 
				&& PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE.equals(platformDistributeEntity.getType())) {
			// 月台虚拟编号
			String virtualCode = platformDistributeEntity.getPlatformNo();
			boolean using = checkPlatformUseInfo(transferCenterNo, virtualCode, PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
			if (using) {
				throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"所选时间中已有车辆正在使用，请修改开始时间!"});
			}
		}
		// 计划来源   手动输入
		String scheduleSource = PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_HANDADD;
		platformDistributeEntity.setScheduleSource(scheduleSource);
		addPlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 查询月台结束时间 即可用开始时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-5 上午9:04:16
	 * @param platformDistributeDto
	 * @return 
	 */
	public Map<String, Date> queryPlatformDistributeEndTimeMap(PlatformDistributeDto platformDistributeDto) {
		Map<String, Date> result = new HashMap<String, Date>();
		List<PlatformDistributeEntity>  list = platformDispatchDao.queryPlatformDistributeEndTime(platformDistributeDto);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		for (PlatformDistributeEntity platformDistributeEntity : list) {
			result.put(platformDistributeEntity.getPlatformNo(), platformDistributeEntity.getUseEndTime());
		}
		return result;
	}
	
	/**
	 * 根据任务编号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 上午10:41:00
	 * @param loadTaskNo
	 * @return {@link java.util.List}
	 */
	public List<PlatformDistributeEntity> queryPlatformDistributeByLoadTaskNo(String loadTaskNo) {
		PlatformDistributeDto platformDistributeDto = new PlatformDistributeDto();
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		platformDistributeEntity.setLoadTaskNo(loadTaskNo);
		platformDistributeEntity.setType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		//046130-foss-xuduowei,2013-4-24,为查询条件设值
		platformDistributeDto.setPlatformDistributeEntity(platformDistributeEntity);
		return platformDispatchDao.queryPlatformDistributeList(platformDistributeDto);
	}
	
	/**
	 * 开启月台使用   - 装卸车
	 * @author 104306-foss-wangLong
	 * @date 2012-11-26 下午8:38:23
	 * @param platformVirtualCode 	月台虚拟编号
	 * @param vehicleNo 	 		车牌号
	 * @param loadTaskNo			任务编号
	 * @param useBeginTime			月台使用开始时间
	 * @param useEndTime			月台使用结束时间
	 * @param departmentCode		部门编码
	 * @throws PlatformDispatchException
	 */
	@Transactional
	public void updatePlatformStatusForUsing(PlatformDistributeEntity distributeEntity) 
			throws PlatformDispatchException {
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		// 获得外场
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = getTransferCenter(distributeEntity.getTransferCenterNo());
		// 外场编码
		platformDistributeEntity.setTransferCenterNo(orgAdministrativeInfoEntity.getCode());
		// 外场名称
		platformDistributeEntity.setTransferCenterName(orgAdministrativeInfoEntity.getName());
		// 月台虚拟编码
		platformDistributeEntity.setPlatformNo(distributeEntity.getPlatformNo());
		// 使用启始时间 
		platformDistributeEntity.setUseStartTime(distributeEntity.getUseStartTime());
		// 使用结束时间 
		platformDistributeEntity.setUseEndTime(distributeEntity.getUseEndTime());
		// 车牌号 
		platformDistributeEntity.setVehicleNo(distributeEntity.getVehicleNo());
		// 装车任务编号
		platformDistributeEntity.setLoadTaskNo(distributeEntity.getLoadTaskNo());
		// 状态 - 默认为使用中
		if(StringUtils.isBlank(distributeEntity.getStatus())){
			platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		}else{
			platformDistributeEntity.setStatus(distributeEntity.getStatus());
		}
		
		// 分配类型  默认为实际停靠
		if(StringUtils.isBlank(distributeEntity.getType())){
			platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		}else{
			platformDistributeEntity.setType(distributeEntity.getType());
		}
		
		// 计划来源  默认为卸货
		if(StringUtils.isBlank(distributeEntity.getScheduleSource())){
			platformDistributeEntity.setScheduleSource(PlatformDispatchConstants.PLATFORMDISPATCH_SCHEDULESOURCE_UNLOAD);
		}else{
			platformDistributeEntity.setScheduleSource(distributeEntity.getScheduleSource());
		}
		
		// 车辆信息不存在
		VehicleAssociationDto vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(platformDistributeEntity.getVehicleNo());
		if (vehicleAssociationDto == null) {
			LOGGER.info("车辆信息不存在,车牌号:{}", distributeEntity.getVehicleNo());
			//throw new PlatformDispatchException("车辆信息不存在,车牌号:" + distributeEntity.getVehicleNo());
			// 车型  主要为迁移修改的，迁移过来的车牌号可能不存在，此时给他个默认值
			platformDistributeEntity.setVehicleModel("CX0001");
		}else if(StringUtils.isBlank(vehicleAssociationDto.getVehicleLengthCode())){
			//拖头可能不没有车型所以这边
			LOGGER.info("车辆车型信息不存在,车牌号:{}", distributeEntity.getVehicleNo());
			platformDistributeEntity.setVehicleModel("CX0001");
		}else{
			// 车型
			platformDistributeEntity.setVehicleModel(vehicleAssociationDto.getVehicleLengthCode());
		}
		
		addPlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 结束月台使用   - 装卸车
	 * @author 104306-foss-wangLong
	 * @date 2012-11-26 下午8:38:23
	 * @param loadTaskNo 	任务编号
	 * @param useEndTime	月台使用结束时间
	 * @throws PlatformDispatchException
	 */
	//@Transactional
	public void updatePlatformStatusForEnd(String loadTaskNo, Date useEndTime)
			throws PlatformDispatchException {
		List<PlatformDistributeEntity> list = queryPlatformDistributeByLoadTaskNo(loadTaskNo);
		if (CollectionUtils.isEmpty(list)) {
			LOGGER.info("不存在月台任务,任务编号:{}", loadTaskNo);
			throw new PlatformDispatchException("不存在月台任务,任务编号:" + loadTaskNo);
		}
		PlatformDistributeEntity platformDistributeEntity = list.get(0);
		String id = platformDistributeEntity.getId();
		platformDistributeEntity = new PlatformDistributeEntity();
		platformDistributeEntity.setId(id);
		platformDistributeEntity.setUseEndTime(useEndTime);
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED);
		updatePlatformDistribute(platformDistributeEntity);
	}
	
	/**
	 * 参数检查
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午8:20:31
	 * @param platformDistributeEntity
	 * @exception PlatformDispatchException
	 */
	private void checkParameters(PlatformDistributeEntity platformDistributeEntity) {
		if (platformDistributeEntity == null) {
			 throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"参数错误!"});
		}
		// 月台号非空检查
		if (StringUtil.isBlank(platformDistributeEntity.getPlatformNo())) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"月台号不能为空!"});
		}
		Date useStartTime = platformDistributeEntity.getUseStartTime();
		// 月台使用开始时间
		if (useStartTime == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"月台开始使用时间不能为空!"});
		}
		Date useEndTime = platformDistributeEntity.getUseEndTime();
		// 月台使用结束时间
		if (useEndTime == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"月台结束时间不能为空!"});
		}
		// 车牌号
		if (StringUtil.isBlank(platformDistributeEntity.getVehicleNo())) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"车牌号不能为空!"});
		}
		//起始时间不能大于等于结束时间
		Calendar useStartCalendar = DateUtils.toCalendar(useStartTime);
		Calendar useEndCalendar = DateUtils.toCalendar(useEndTime);
		if (useStartCalendar.after(useEndCalendar) || useStartCalendar.compareTo(useEndCalendar) == 0) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"起始时间不能大于等于结束时间!"});
		}
		// 截止时间不能小于当前时间
		Calendar nowCalendar = DateUtils.toCalendar(new Date());
		if (useEndCalendar.before(nowCalendar)) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"结束时间不能小于当前时间!"});
		}
		// 车辆信息不存在
		VehicleAssociationDto vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(platformDistributeEntity.getVehicleNo());
		if (vehicleAssociationDto == null) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"车辆信息不存在!"});
		}
		if (StringUtil.isBlank(vehicleAssociationDto.getVehicleLengthCode())) {
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"车型不能为空!"});
		}
		// 车型
		platformDistributeEntity.setVehicleModel(vehicleAssociationDto.getVehicleLengthCode());
	}
	
	/**
	 * 检查外场当前月台  指定时间 是否有车辆停靠
	 * @author 104306-foss-wangLong
	 * @date 2012-11-21 下午3:48:29
	 * @param transferCenterNo  外场编码
	 * @param virtualCode		月台虚拟编号
	 * @param type 				停靠类型
	 * @return true: 有车辆正在使用  false：可以使用
	 */
	private boolean checkPlatformUseInfo(String transferCenterNo, String virtualCode, String type) {
		PlatformDistributeDto platformDistributeDto = new PlatformDistributeDto();
		PlatformDistributeEntity platformDistributeEntity = new PlatformDistributeEntity();
		platformDistributeEntity.setTransferCenterNo(transferCenterNo);
		platformDistributeEntity.setPlatformNo(virtualCode);
		platformDistributeEntity.setType(type);
		platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		
		platformDistributeDto.setPlatformDistributeEntity(platformDistributeEntity);
		
		List<PlatformDistributeEntity> list = platformDispatchDao.queryPlatformDistributeList(platformDistributeDto);
		return !(list == null || list.isEmpty());
	}

	/**
	 * 查询可用月台
	 * @author 104306-foss-wangLong
	 * @date 2012-11-16 上午12:32:54
	 * @param platformList					综合月台信息
	 * @param actualPlatformDistributeMap   正在使用中的月台
	 */
	private void removeUsingPlatform(List<PlatformEntity> platformList,
			Map<String, List<PlatformDistributeEntity>> actualPlatformDistributeMap) {
		for (PlatformEntity platformEntity : platformList) {
			String virtualCode = platformEntity.getVirtualCode();
			// 如果月台正在使用  就从List之中删除
			if (actualPlatformDistributeMap.containsKey(virtualCode)) {
				platformList.remove(platformEntity);
			}
		}
	}

	/**
	 * 获取当前部门对应的外场
	 * @author 104306-foss-wangLong
	 * @date 2012-11-28 下午6:30:53
	 */
	private OrgAdministrativeInfoEntity getTransferCenter(String deptCode) {
		List<String> bizTypes = new ArrayList<String>();
		// 外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
		try {
			String transferCode = outfieldService
					.queryTransferCenterByAirDispatchCode(deptCode);
			if (StringUtils.isEmpty(transferCode)) {
				transferCode = deptCode;
			}
			orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(transferCode, bizTypes);
		} catch (Exception e) {
			LOGGER.info("获取部门对应的外场出错，错误信息:" + e.getMessage());
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"调用综合接口获取部门对应的外场出错 , 错误信息：" + e.getMessage()});
		}
		if (orgAdministrativeInfoEntity == null) {
			LOGGER.info("获取部门对应的外场出错，返回空, 部门编码 ：" + deptCode);
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"获取部门对应的外场出错，综合返回空, 部门编码 ：" + deptCode});
		}
		LOGGER.info("获取部门对应的外场. 部门编码: " + deptCode + ",综合接口返回  外场名称:" + orgAdministrativeInfoEntity.getName() + ", 外场编码:" + orgAdministrativeInfoEntity.getCode());
		return orgAdministrativeInfoEntity;
	}
	
	/**
	 * 获取当前部门对应的外场编号
	 * @author 104306-foss-wangLong
	 * @date 2012-11-28 上午12:57:17
	 * @param deptCode
	 * @return 外场编码
	 */
	private String getTransferCenterCode(String deptCode) {
		// 外场编码
		return getTransferCenter(deptCode).getCode();
	}
	
	/**
	 * 综合月台数据
	 * @author 104306-foss-wangLong
	 * @date 2012-11-14 下午2:31:02
	 * @param platformDistributeDto
	 * @return
	 */
	private List<PlatformEntity> queryPlatformListByCondition(PlatformDistributeDto platformDistributeDto) {
		PlatformDistributeEntity distributeEntity = platformDistributeDto.getPlatformDistributeEntity();
		PlatformEntity platform = new PlatformEntity();
		//外场编码  == 部门编码
		platform.setOrganizationCode(distributeEntity.getTransferCenterNo());
		// 月台基础数据,.
		List<PlatformEntity> list = platformService.queryPlatformListByCondition(platform, Integer.MIN_VALUE, Integer.MAX_VALUE);
		if (list == null) {
			list = new ArrayList<PlatformEntity>();
		}
		//按车型查询
		//车型
		String vehicleType = platformDistributeDto.getVehicleType();
		List<PlatformEntity> vehicleTypeQueryList = null; 
		if (StringUtil.isNotBlank(vehicleType)) {
			vehicleTypeQueryList = platformService.queryPlatformListByVehicleType(distributeEntity.getTransferCenterNo(), vehicleType);
		}
		if (!CollectionUtils.isEmpty(vehicleTypeQueryList)) {
			Map<String, PlatformEntity> resultMap = remove(list, vehicleTypeQueryList);
			list = new ArrayList<PlatformEntity>(resultMap.values());
		}
		// 月台区间查询 未输入.
		String startCode = platformDistributeDto.getStartPlatformCode();
		String endCode = platformDistributeDto.getEndPlatformCode();
		if (StringUtil.isBlank(startCode) && StringUtil.isBlank(endCode)) {
			return list;
		}
		// 由于综合提供的接口  缺失查询条件  利用map过滤来实现月台号区间查询
		if (StringUtil.isNotBlank(startCode) && StringUtil.isBlank(endCode)) {
			endCode = startCode;
		}
		if (StringUtil.isNotBlank(endCode) && StringUtil.isBlank(startCode)) {
			startCode = endCode;
		}
		List<PlatformEntity> platformIntervalList = platformService.queryPlatformListByOrgCodeAndPlatformCodeLimit(distributeEntity.getTransferCenterNo(), startCode, endCode);
		Map<String, PlatformEntity> resultMap = remove(list, platformIntervalList);
		return new ArrayList<PlatformEntity>(resultMap.values());
	}

	/**
	 * list转换为map, 根据月台虚拟编码
	 * @author 104306-foss-wangLong
	 * @date 2012-11-16 上午9:15:19
	 * @param platformIntervalList  
	 * @return 
	 */
	private Map<String, PlatformEntity> convertMapByVirtualCode(List<PlatformEntity> platformIntervalList) {
		Map<String, PlatformEntity> map = new ConcurrentHashMap<String, PlatformEntity>();
		for (PlatformEntity platformEntity : platformIntervalList) {
			map.put(platformEntity.getVirtualCode(), platformEntity);
		}
		return map;
	}

	/**
	 * 删除map1中不在map2中存在的
	 * @author 104306-foss-wangLong
	 * @date 2012-11-16 上午9:17:01
	 * @param platformList
	 * @param platformList2
	 * @return 
	 */
	private Map<String, PlatformEntity> remove(List<PlatformEntity> platformList, List<PlatformEntity> platformList2) {
		Map<String, PlatformEntity> map1 = convertMapByVirtualCode(platformList);
		Map<String, PlatformEntity> map2 = convertMapByVirtualCode(platformList2);
		Map<String, PlatformEntity> resultMap = new ConcurrentHashMap<String, PlatformEntity>();
		Set<Map.Entry<String, PlatformEntity>> entries = map1.entrySet();
		for (Entry<String, PlatformEntity> entry : entries) {
			String virtualCode = entry.getKey();
			if (map2.containsKey(virtualCode)) {
				resultMap.put(virtualCode, entry.getValue());
			}
		}
		return resultMap;
	}

	/**
	 * 处理月台实际停靠 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午11:36:41
	 * @param actualPlatformDistributeMap	实际使用
	 * @param platformCode	月台编码
	 * @param virtualCode	月台虚拟编码
	 * @return 
	 */
	private PlatformDistributeDto handleActualCall(
			Map<String, List<PlatformDistributeEntity>> plansPlatformDistributeMap, String platformCode, String virtualCode) {
		List<PlatformDistributeEntity> platformUseList = plansPlatformDistributeMap.get(virtualCode);
		PlatformDistributeDto distributeDto = new PlatformDistributeDto(); 
		// 月台编号
		distributeDto.setPlatformNo(platformCode);
		// 月台虚拟编号
		distributeDto.setPlatformVirtualCode(virtualCode);
		distributeDto.setUseType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		if (platformUseList == null) {
			platformUseList = new ArrayList<PlatformDistributeEntity>();
		}
		class ActualCallHandler implements IHandler {
			public boolean handleAction(PlatformDistributeEntity platformDistribute) {
				Date useStartTime = platformDistribute.getUseStartTime();
				Date useEndTime = platformDistribute.getUseEndTime();
				int value = ConstantsNumberSonar.SONAR_NUMBER_59;
				Date nowDate = DateUtils.setSeconds(new Date(), value);
				//月台已使用过  ||  月台正在使用 							
				return useEndTime.compareTo(nowDate) == -1
					|| (useStartTime.compareTo(nowDate) <= 0 && useEndTime.compareTo(nowDate) >= 0);
			}
		}
		Map<String, String> timeQuantumMap = getTimeQuantum(platformUseList, new ActualCallHandler());
		settingValue(timeQuantumMap, distributeDto);
		return distributeDto;
	}

	/**
	 * 处理月台计划停靠
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午11:34:32
	 * @param actualPlatformDistributeMap
	 * @param platformCode				月台编码
	 * @param virtualCode				月台虚拟编码
	 * @return 
	 */
	private PlatformDistributeDto handleScheduleCall(
			Map<String, List<PlatformDistributeEntity>> actualPlatformDistributeMap, String platformCode, String virtualCode) {
		//计划停靠
		List<PlatformDistributeEntity> platformUseList = actualPlatformDistributeMap.get(virtualCode);
		PlatformDistributeDto distributeDto = new PlatformDistributeDto();
		// 月台编号
		distributeDto.setPlatformNo(platformCode);
		// 月台虚拟编号
		distributeDto.setPlatformVirtualCode(virtualCode);
		distributeDto.setUseType(PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		if (platformUseList == null) {
			platformUseList = new ArrayList<PlatformDistributeEntity>();
		}
		class PlanCallHandler implements IHandler {
			public boolean handleAction(PlatformDistributeEntity platformDistribute) {
				return true;
			}
		}
		Map<String, String> timeQuantumMap = getTimeQuantum(platformUseList, new PlanCallHandler());
		settingValue(timeQuantumMap, distributeDto);
		return distributeDto;
	}
	
	/**
	 * List转换位map  根据月台编号分组
	 * key -> 月台编号   value 相同月台编号的list
	 * @author 104306-foss-wangLong
	 * @date 2012-11-8 上午9:40:29
	 * @param plansPlatformDistributeList
	 * @return
	 */
	private Map<String, List<PlatformDistributeEntity>> convertMapByPlatformNo(
			List<PlatformDistributeEntity> plansPlatformDistributeList) {
		Map<String, List<PlatformDistributeEntity>> map = new HashMap<String, List<PlatformDistributeEntity>>();
		for (PlatformDistributeEntity platformDistributeEntity : plansPlatformDistributeList) {
			String platformNo = platformDistributeEntity.getPlatformNo();
			List<PlatformDistributeEntity> list = map.get(platformNo);
			if (list == null) {
				list = new ArrayList<PlatformDistributeEntity>();
				map.put(platformNo, list);
			}
			list.add(platformDistributeEntity);
			
		}
		return map;
	}

	/**
	 * 通过时间给实体属性赋值
	 * @author 104306-foss-wangLong
	 * @date 2012-11-7 上午12:48:08
	 * @param timeQuantumMap 
	 * @param distributeDto
	 * @see com.deppon.foss.framework.shared.util.classes.ReflectionUtils#setFieldValue
	 */
	private void settingValue(Map<String, String> timeQuantumMap, PlatformDistributeDto distributeDto) {
		Set<Map.Entry<String, String>> setEntries = timeQuantumMap.entrySet();
		for (Entry<String, String> entry : setEntries) {
			String key = entry.getKey();
			String value = entry.getValue();
			ReflectionUtils.setFieldValue(distributeDto, key, value);
		}
	}
	
	/**
	 * 获得时间段</br>
	 * value为车牌号, 如果map的value为null,说明当前时间段不停车,
	 * @author 104306-foss-wangLong
	 * @date 2012-11-6 下午2:26:19
	 * @param platformDistribute
	 * @return
	 */
	private Map<String, String> getTimeQuantum(List<PlatformDistributeEntity> list, IHandler handler) {
		if (list == null || list.isEmpty()) {
			return new ConcurrentHashMap<String, String>();
		}
		Map<String, String> resultMap = new ConcurrentHashMap<String, String>();
		for (PlatformDistributeEntity platformDistributeEntity : list) {
			if (!handler.handleAction(platformDistributeEntity)) {
				continue;
			}
			Map<String, String> map = getTimeQuantum(platformDistributeEntity);
			resultMap.putAll(map);
			
		}
		return resultMap;
	}
	
	/**
	 * 获得时间段</br>
	 * value为车牌号, 如果map的value为null,说明当前时间段不停车, 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-6 下午2:26:19
	 * @param platformDistribute
	 * @return
	 * @see PlatformDispatchService#getBetweenTwoDateList
	 */
	private Map<String, String> getTimeQuantum(PlatformDistributeEntity platformDistribute) {
		Date useStartTime = platformDistribute.getUseStartTime();
		Date useEndTime = platformDistribute.getUseEndTime();
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		//返回每半个小时为点的List
		List<String> list = getBetweenDateHalfhourList(useStartTime, useEndTime);
		String propertyName = null;
		String value = null;
		for (String strDate : list) {
			int index = strDate.indexOf(":");
			if (index == -1)  {
				continue;
			}
			propertyName = "hour" + strDate.replaceAll(":", "");
			value = platformDistribute.getVehicleNo() + "_" + platformDistribute.getId();
			map.put(propertyName, value);
		}
		return map;
	}
	
	/**
	 * 根据时间区间返回每半个小时为点的集合
	 * <br>--------------------<br>
	 * @author 104306-foss-wangLong
	 * @date 2012-11-8 上午10:01:08
	 * @param useStartTime 开始时间
	 * @param useEndTime   结束时间
	 * @return 
	 */
	private List<String> getBetweenDateHalfhourList(Date useStartTime, Date useEndTime) {
		List<String> list = new LinkedList<String>();  
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(useStartTime);
		endCalendar.setTime(useEndTime);
		int minute = startCalendar.get(Calendar.MINUTE);
		
		if (minute < ConstantsNumberSonar.SONAR_NUMBER_15) {
			startCalendar.set(Calendar.MINUTE, 0);
		}  
		if(minute < ConstantsNumberSonar.SONAR_NUMBER_30 && minute >= ConstantsNumberSonar.SONAR_NUMBER_15) {
			startCalendar.set(Calendar.MINUTE, ConstantsNumberSonar.SONAR_NUMBER_30);
		}
		if (minute >= ConstantsNumberSonar.SONAR_NUMBER_30 && minute <= ConstantsNumberSonar.SONAR_NUMBER_45) {
			startCalendar.set(Calendar.MINUTE, ConstantsNumberSonar.SONAR_NUMBER_30);
		}
		if (minute > ConstantsNumberSonar.SONAR_NUMBER_45) {
			startCalendar.set(Calendar.MINUTE, 0);
			int hour = startCalendar.get(Calendar.HOUR_OF_DAY);
			hour++;
			startCalendar.set(Calendar.HOUR_OF_DAY, hour);
		}
		
		minute = endCalendar.get(Calendar.MINUTE);
		if (minute < ConstantsNumberSonar.SONAR_NUMBER_15) {
			endCalendar.set(Calendar.MINUTE, 0);
		}  
		if(minute < ConstantsNumberSonar.SONAR_NUMBER_30 && minute >= ConstantsNumberSonar.SONAR_NUMBER_15) {
			endCalendar.set(Calendar.MINUTE, ConstantsNumberSonar.SONAR_NUMBER_30);
		}
		if (minute >= ConstantsNumberSonar.SONAR_NUMBER_30 && minute <= ConstantsNumberSonar.SONAR_NUMBER_45) {
			endCalendar.set(Calendar.MINUTE, ConstantsNumberSonar.SONAR_NUMBER_30);
		}
		if (minute > ConstantsNumberSonar.SONAR_NUMBER_45) {
			endCalendar.set(Calendar.MINUTE, 0);
			int hour = endCalendar.get(Calendar.HOUR_OF_DAY);
			hour++;
			endCalendar.set(Calendar.HOUR_OF_DAY, hour);
		}
		String result = null;
		while (startCalendar.compareTo(endCalendar) <= 0) {
			useStartTime = startCalendar.getTime();
			result = new SimpleDateFormat("HH:mm").format(useStartTime);
			result = result.substring(0, result.length());
			list.add(result);
			startCalendar.add(Calendar.MINUTE, ConstantsNumberSonar.SONAR_NUMBER_30);
		}
		return list;
	}
	
	/**
	 * 查询月台使用信息
	 * <br>--------------------<br>
	 * @author 104306-foss-wangLong
	 * @date 2012-11-6 上午11:42:18
	 * @param platformDistributeDto  查询对象
	 * @param type					   停靠类型
	 * @return 
	 */
	private List<PlatformDistributeEntity> queryPlatformDistributeList(PlatformDistributeDto platformDistributeDto, String type) {
		PlatformDistributeEntity platformDistributeEntity = platformDistributeDto.getPlatformDistributeEntity();
		List<String> statusList = new ArrayList<String>();
		// 查询可用， 正在使用状态的月台分配信息
		statusList.add(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_AVAILABLE);
		statusList.add(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		// 如果为实际停靠  则查询出清空月台的记录
		if (PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE.equals(type)) {
			statusList.add(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED);	
		}
		platformDistributeDto.setStatusList(statusList);
		// 月台任务类型
		platformDistributeEntity.setType(type);
		// 查询条件 时间 如果没有选择  默认当天时间
		if (platformDistributeDto.getUseBeginTime() == null) {
			Date beginDate = DateUtils.setSeconds(new Date(), 1);
			platformDistributeDto.setUseBeginTime(beginDate);
		}
		// 如果没有选择 结束实际  默认为次日时间  即begin day + 1
		if (platformDistributeDto.getUseEndTime() == null) {
			platformDistributeDto.setUseEndTime(getNextDay(platformDistributeDto.getUseBeginTime()));
		}
		// 只显示可用月台
		boolean showAvailable = platformDistributeDto.isShowAvailable();
		// 实际使用
		boolean actualUse = PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE.equals(type);
		if (showAvailable && actualUse) {
			// 查询到正在使用的月台
			platformDistributeEntity.setStatus(PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
			// 如果选择了只显示可用月台  只按状态查询
			platformDistributeDto.setUseBeginTime(null);
			platformDistributeDto.setUseEndTime(null);
			platformDistributeEntity.setVehicleNo(null);
		}
		//046130-foss-xuduowei,2013-4-24,为查询条件设值
		platformDistributeDto.setPlatformDistributeEntity(platformDistributeEntity);
		return platformDispatchDao.queryPlatformDistributeList(platformDistributeDto);
	}
	
	/**
	 * 获取次日时间
	 * @author 104306-foss-wangLong
	 * @date 2012-12-12 上午11:19:16
	 */
	private Date getNextDay(Date date) {
		int nextDayNumber = 1;
		return DateUtils.addDays(date, nextDayNumber);
	}

	/**
	 * 结果排序
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 上午8:26:09
	 * @param resultList
	 */
	private void sortResult(List<PlatformDistributeDto> resultList) {
		if (CollectionUtils.isEmpty(resultList)) {
			return;
		}
		Collections.sort(resultList,  new Comparator<PlatformDistributeDto>() {
			public int compare(PlatformDistributeDto o1, PlatformDistributeDto o2) {
				if (StringUtil.isBlank(o1.getPlatformNo()) || StringUtil.isBlank(o2.getPlatformNo())) {
					return Integer.MIN_VALUE;
				}
				return o1.getPlatformNo().compareTo(o2.getPlatformNo());
			}
		});
	}
	
	
	/**
	 * 查询装卸车任务和进度   - 服务
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 * @throws PlatformDispatchException
	 */
	@Override
	public QueryProgressResultDto queryTaskProgressByPaltformNo(String deptCode,
			String platformNo) throws PlatformDispatchException {
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		//部门编码
		map.put("deptCode", deptCode);
		//月台编号
		map.put("platformNo", platformNo);
		//状态，正在使用
		map.put("status", PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		//类型，实际停靠
		map.put("type", PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		//获取月台信息
		PlatformDistributeEntity platformDistributeEntity = platformDispatchDao.queryTaskProgressByPaltformNo(map);
		//装卸车任务和进度
		QueryProgressResultDto queryProgressResultDto = 
				queryProgressService.queryProgressResult(platformDistributeEntity.getLoadTaskNo());
		//车牌号
//		QueryProgressResultDto.setVehicleNo(platformDistributeEntity.getVehicleNo());
		//预计结束时间
		queryProgressResultDto.setTaskEndTime(platformDistributeEntity.getUseEndTime());
		return queryProgressResultDto;
	}
	
	/**
	 * 查询月台安排计划   - 服务
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 * @throws PlatformDispatchException
	 */
	@Override
	public List<PlatformDistributeEntity> queryPlatformPlanByPaltformNo(
			String deptCode, String platformNo)
			throws PlatformDispatchException {
		//查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		//部门编码
		map.put("deptCode", deptCode);
		//月台编号
		map.put("platformNo", platformNo);
		//类型，计划
		map.put("type", PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_PLAN);
		//时间
		map.put("time", new Date());
		return platformDispatchDao.queryPlatformPlanByPaltformNo(map);
	}
	
	
	
	/**
	 * 获取当前部门所属外场信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * 
	 */
	@Override
	public TransferDeptInfo queryTransferDept() {
		TransferDeptInfo deptDto = new TransferDeptInfo();
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		//当前组织信息
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		//原始组织信息
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
						bizTypes);
		if(org != null){
			//部门编码
			deptDto.setDeptCode(org.getCode());
			//部门名称
			deptDto.setDeptName(org.getName());
			return deptDto;
		}else{
			throw new PlatformDispatchException(PlatformDispatchConstants.PLATFORMDISPATCH_PARAMETERERROR_MESSAGE, new Object[]{"获取部门对应的外场出错，部门编码 ：" + activeDeptInfo.getCode()});
		}
	}
	
	
	/**
	 * 获取当前部门所属外场信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-05-20 下午8:38:23
	 * @param deptCode 部门编码
	 * @param platformno 月台号
	 */
	@Override
	public int forceDeletePlatFrom(String deptCode, String platformNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		//更新后的状态
		map.put("updateStatus", PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_DISABLED);
		//部门编码
		map.put("deptCode", deptCode);
		//月台编号
		map.put("platformNo", platformNo);
		//原状态，正在使用
		map.put("originalStatus", PlatformDispatchConstants.PLATFORMDISPATCH_STATUS_USING);
		//类型，实际停靠
		map.put("type", PlatformDispatchConstants.PLATFORMDISPATCH_TYPE_ACTUALUSE);
		//修改时间
		map.put("useEndTime", new Date());
		//获取月台信息
		return platformDispatchDao.forceDeletePlatFrom(map);
	}

	/**
	 * 设置platformDispatchDao
	 * @param platformDispatchDao the platformDispatchDao to set
	 */	
	public void setPlatformDispatchDao(IPlatformDispatchDao platformDispatchDao) {
		this.platformDispatchDao = platformDispatchDao;
	}
	
	/**
	 * 设置platformService
	 * @param platformService the platformService to set
	 */	
	public void setPlatformService(IPlatformService platformService) {
		this.platformService = platformService;
	}
	
	/**
	 * 设置vehicleService
	 * @param vehicleService the vehicleService to set
	 */		
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	
	/**
	 * 设置orgAdministrativeInfoComplexService
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	public interface IHandler {
	
		boolean handleAction(PlatformDistributeEntity platformDistribute);
	}

	public void setQueryProgressService(IQueryProgressService queryProgressService) {
		this.queryProgressService = queryProgressService;
	}
	
	
}