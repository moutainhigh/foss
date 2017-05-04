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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IPlatformDispatchService.java
 * 
 *  FILE NAME     :IPlatformDispatchService.java
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
 * FILE    NAME: IPlatformDispatchService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryProgressResultDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TransferDeptInfo;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.PlatformDispatchException;

/**
 * 月台Service
 * @author 104306-foss-wangLong
 * @date 2012-10-31 下午3:21:16
 */
public interface IPlatformDispatchService extends IService {
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-9 上午10:30:01
	 * @param platformDistributeEntity
	 * @return 受影响的行数 
	 * @see com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity
	 * @throws PlatformDispatchException
	 */
	int addPlatformDistribute(PlatformDistributeEntity platformDistributeEntity) 
			throws PlatformDispatchException;
	
	/**
	 * 新增月台计划停靠任务  提供给车辆到达
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午7:47:41
	 * @param virtualCode 		月台虚拟编号  非月台编号  {@link PlatformEntity#getVirtualCode}
	 * @param useStartTime 		使用开始时间
	 * @param useEndTime 		使用结束时间
	 * @param vehicleModel 		车型
	 * @param vehicleNo			车牌号
	 * @return  受影响的行数
	 * @throws PlatformDispatchException
	 */
	int addPlatformDistributeForArrival(String virtualCode, Date useStartTime, Date useEndTime, String vehicleNo);

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午1:47:52
	 * @param platformDistributeEntity
	 * @return 受影响的行数
	 * @throws PlatformDispatchException
	 */
	int updatePlatformDistribute(PlatformDistributeEntity platformDistributeEntity)
			throws PlatformDispatchException;
	
	/**
	 * 查询月台使用情况 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-3 下午5:04:40
	 * @param platformDistributeDto
	 * @return java.util.List
	 */
	List<PlatformDistributeDto> queryPlatformUseInfo(PlatformDistributeDto platformDistributeDto)
			throws PlatformDispatchException;

	/**
	 * 查询月台详情
	 * @author 104306-foss-wangLong
	 * @date 2012-11-9 上午10:09:09
	 * @param platformNo 月台号
	 * @return com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity
	 * @see com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService#queryPlatformByVirtualCode
	 */
	PlatformEntity queryPlatformDetail(String platformNo);
	
	/**
	 * 查询单个对象（按主键查询）
	 * <br>------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午11:40:55
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity
	 */
	PlatformDistributeEntity queryPlatformDistribute(String id);
	
	/**
	 * 月台使用时间是否大于当前时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午8:28:39
	 * @param id 月台使用主键id
	 * @return 如果月台使用结束时间  大于当前时间  返回false  否则true
	 */
	boolean doUseTimeIfGreaterThanOrEqualCurrentTime(String id);

	/**
	 * 清空月台
	 * <br>--------------------<br>
	 * 1、如果为计划停靠的车辆，则只清空该车辆的计划停靠信息；</br>
	 * 2、如果为实际使用的车辆，则生成历史记录，记录使用车牌号，开始使用时间和结束使用时间，开始使用时间为实际开始使用时间，结束使用时间为点击清空月台按钮时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 上午10:33:32
	 * @param platformDistributeEntity
	 */
	void clearPlatform(PlatformDistributeEntity platformDistributeEntity);

	/**
	 * 修改月台使用情况
	 * <br>--------------------<br>
	 * 根据{@link PlatformDistributeEntity#getType}确定“实际停靠”,“计划停靠” 判断规则<br>
	 * 结束时间不能小于当前时间, 结束时间不能小于开始时间  
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午2:57:58
	 * @param platformDistributeEntity
	 */
	void updatePlatformUseInfo(PlatformDistributeEntity platformDistributeEntity);
	
	/**
	 * 使用月台(分配月台任务)
	 * @author 104306-foss-wangLong
	 * @date 2012-11-14 下午4:35:20
	 * @param platformDistributeEntity  
	 */
	void dispatchPlatform(PlatformDistributeEntity platformDistributeEntity);
	
	/**
	 * 查询当前正在使用的月台
	 * <br>--------------------<br>
	 * key 月台虚拟编号<br>
	 * value 月台实体
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午9:23:09
	 * @return {@link java.util.Map}
	 */
	Map<String, PlatformDistributeEntity> queryUseingPlatform();
	
	/**
	 * 查询月台结束时间 即可用开始时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-5 上午9:04:16
	 * @param platformDistributeDto
	 * @return 
	 */
	Map<String, Date> queryPlatformDistributeEndTimeMap(PlatformDistributeDto platformDistributeDto);
	
	/**
	 * 根据任务编号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-12-3 上午10:41:00
	 * @param loadTaskNo
	 * @return {@link java.util.List}
	 */
	List<PlatformDistributeEntity> queryPlatformDistributeByLoadTaskNo(String loadTaskNo);
	
	/**
	 * 开启月台使用   - 装卸车
	 * @author 104306-foss-wangLong
	 * @date 2012-11-26 下午8:38:23
	 * @param platformDistributeEntity.platformNo 	                     月台虚拟编号
	 * @param platformDistributeEntity.vehicleNo 	 		车牌号
	 * @param platformDistributeEntity.loadTaskNo			任务编号
	 * @param platformDistributeEntity.useBeginTime			月台使用开始时间
	 * @param platformDistributeEntity.useEndTime			月台使用结束时间
	 * @param platformDistributeEntity.transferCenterNo		部门编码
	 * @param platformDistributeEntity.scheduleSource       来源（装货，卸货，发车计划，车辆到达）
	 * @param platformDistributeEntity.type					停靠类型
	 * @throws PlatformDispatchException
	 */
	void updatePlatformStatusForUsing(PlatformDistributeEntity platformDistributeEntity) throws PlatformDispatchException;
	
	/**
	 * 结束月台使用   - 装卸车
	 * @author 104306-foss-wangLong
	 * @date 2012-11-26 下午8:38:23
	 * @param loadTaskNo 			任务编号
	 * @param useEndTime			月台使用结束时间
	 * @throws PlatformDispatchException
	 */
	void updatePlatformStatusForEnd(String loadTaskNo, Date useEndTime) throws PlatformDispatchException;
	
	/**
	 * 查询装卸车任务和进度   - 服务
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 * @throws PlatformDispatchException
	 */
	QueryProgressResultDto queryTaskProgressByPaltformNo(String deptCode, String platformNo) throws PlatformDispatchException;
	/**
	 * 查询月台安排计划   - 服务
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 * @throws PlatformDispatchException
	 */
	List<PlatformDistributeEntity> queryPlatformPlanByPaltformNo(String deptCode, String platformNo) throws PlatformDispatchException;
	
	/**
	 * 获取当前部门所属外场信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * 
	 */
	TransferDeptInfo queryTransferDept();
	/**
	 * 获取当前部门所属外场信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-05-20 下午8:38:23
	 * @param deptCode 部门编码
	 * @param platformno 月台号
	 */
	int forceDeletePlatFrom(String deptCode,String platFormNo);
}