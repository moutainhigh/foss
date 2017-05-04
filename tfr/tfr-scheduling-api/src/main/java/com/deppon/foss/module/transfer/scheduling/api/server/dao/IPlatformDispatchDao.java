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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IPlatformDispatchDao.java
 * 
 *  FILE NAME     :IPlatformDispatchDao.java
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
 * FILE    NAME: IPlatformDispatchDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeDto;

/**
 * 月台分配数据访问
 * @author 104306-foss-wangLong
 * @date 2012-10-31 下午4:38:17
 */
public interface IPlatformDispatchDao {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-3 下午5:45:08
	 * @param  platformDistributeEntity 
	 * @return 受影响的行数
	 */
	int addPlatformDistribute(PlatformDistributeEntity platformDistributeEntity);
	
	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-3 下午5:45:08
	 * @param  platformDistributeEntity 
	 * @return 受影响的行数
	 */
	int updatePlatformDistribute(PlatformDistributeEntity platformDistributeEntity);
	
	/**
	 * 根据主键集合 更新月台分配状态
	 * @author 104306-foss-wangLong
	 * @date 2012-11-12 下午2:09:38
	 * @param ids 
	 * @param status
	 * @return 受影响的行数
	 */
	int updatePlatformDistributeStatusByIds(List<String> ids, String status);
	
	/**
	 * 查询单个对象（按主键查询）
	 * @author 104306-foss-wangLong
	 * @date 2012-11-3 下午5:45:08
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity
	 */
	PlatformDistributeEntity queryPlatformDistribute(String id);
	
	/**
	 * 查询月台信息
	 * @author 104306-foss-wangLong
	 * @date 2012-11-5 上午9:04:16
	 * @param platformDistributeDto
	 * @return 
	 */
	List<PlatformDistributeEntity> queryPlatformDistributeList(PlatformDistributeDto platformDistributeDto);
	
	/**
	 * 查询月台结束时间
	 * @author 104306-foss-wangLong
	 * @date 2012-11-5 上午9:04:16
	 * @param platformDistributeDto
	 * @return 
	 */
	List<PlatformDistributeEntity> queryPlatformDistributeEndTime(PlatformDistributeDto platformDistributeDto);
	/**
	 * 查询装卸车任务和进度  
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 */
	PlatformDistributeEntity queryTaskProgressByPaltformNo(Map<String,Object> map);
	
	/**
	 * 查询月台计划停靠信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-04-09 下午8:38:23
	 * @param deptCode 			部门编码
	 * @param platformNo			月台编号
	 */
	List<PlatformDistributeEntity> queryPlatformPlanByPaltformNo(Map<String,Object> map);
	
	/**
	 * 获取当前部门所属外场信息
	 * @author 046130-foss-xuduowei
	 * @date 2013-05-20 下午8:38:23
	 * @param deptCode 部门编码
	 * @param platformno 月台号
	 */
	int forceDeletePlatFrom(Map<String,Object> map);
}