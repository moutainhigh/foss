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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/TruckSchedulingDao.java
 * 
 *  FILE NAME     :TruckSchedulingDao.java
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
 * FILE    NAME: TruckSchedulingDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleDriverDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckSchedulingDto;

/**
 * 排班表DAO接口实现
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-26 上午10:02:04
 */
public class TruckSchedulingDao extends iBatis3DaoImpl implements ITruckSchedulingDao {

	/**
	 * 前 缀
	 */
	private static String prefix = "Foss.scheduling.truckScheduling.";

	/**
	 * 插入 排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:02:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#insertTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public void insertTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 插入
		this.getSqlSession().insert(prefix + "insertTruckScheduling", truckScheduling);
	}

	/**
	 * 删除 （更改计划状态到“未知”）
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:02:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#deleteTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public void deleteTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 未知
		truckScheduling.setPlanType(ScheduleConstants.PLAN_TYPE_UNKNOWN);
		// 返回
		this.getSqlSession().update(prefix + "updateTruckScheduling", truckScheduling);

	}

	/**
	 * 更新排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:02:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#updateTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public void updateTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 返回
		this.getSqlSession().update(prefix + "updateTruckScheduling", truckScheduling);

	}

	/**
	 * 查询排班信息列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:02:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckScheduling(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingEntity> queryTruckScheduling(TruckSchedulingEntity truckScheduling) {
		// 返回
		return this.getSqlSession().selectList(prefix + "queryTruckScheduling", truckScheduling);
	}

	/**
	 * 查询一条排班信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 上午10:02:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckSchedulingByParams(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public TruckSchedulingEntity queryTruckSchedulingByParams(TruckSchedulingEntity truckScheduling) {
		// 返回空
		return null;
	}

	/**
	 * 查询某车队，某月的所有司机排班统计表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-26 下午1:09:24
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckSchedulingList(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity truckScheduling, int limit, int start) {
		// 排班对象DTO
		TruckSchedulingDto dto = new TruckSchedulingDto();
		// 排班部门
		dto.setSchedulingDepartCode(truckScheduling.getSchedulingDepartCode());
		// 年月
		dto.setYearMonth(truckScheduling.getYearMonth());
		// 排班类型
		dto.setSchedulingType(truckScheduling.getSchedulingType());
		// 可用
		dto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
		// 工作列表
		List<String> list = new ArrayList<String>();
		// 培训
		list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
		// 值班
		list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
		// 工作
		list.add(ScheduleConstants.PLAN_TYPE_WORK);
		// 设值
		dto.setList(list);
		// 分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		// 返回
		return this.getSqlSession().selectList(prefix + "queryTruckSchedulingList", dto,rowBounds);
	}

	/**
	 * 批量插入排班表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-29 上午9:06:02
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#batchInsertTruckScheduling(java.util.List)
	 */
	@Override
	public void batchInsertTruckScheduling(List<TruckSchedulingEntity> list) {
		// 原来为batchInsertTruckScheduling,后期改为单条循环插入batchInsertTruckSchedulingForOne
		if (CollectionUtils.isNotEmpty(list)) {
			// 循环
			for (TruckSchedulingEntity entity : list) {
				// 插入
				this.getSqlSession().insert(prefix + "batchInsertTruckSchedulingForOne", entity);
			}
		}

	}

	/**
	 * 查询是否 这些司机编码都存在(导入验证),如果有不存在的，则返回不存在的司机代码
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午12:46:06
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryNotExistDriverCodes(com.deppon.foss.module.transfer.scheduling.api.shared.dto.ScheduleDriverDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingEntity> queryNotExistDriverCodes(ScheduleDriverDto scheduleDriverDto) {
		// 返回结果
		return this.getSqlSession().selectList(prefix + "queryNotExistDriverCodes", scheduleDriverDto);
	}

	/**
	 * 批量更新计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-3 下午4:19:28
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#batchUpdateTruckScheduling(java.util.List)
	 */
	@Override
	public void batchUpdateTruckScheduling(List<TruckSchedulingDto> list) {
		// 原有批量更新batchUpdateTruckScheduling改为batchUpdateTruckSchedulingForOne
		if (CollectionUtils.isNotEmpty(list)) {
			// 玄幻
			for (TruckSchedulingDto dto : list) {
				// 更新
				this.getSqlSession().update(prefix + "batchUpdateTruckSchedulingForOne", dto);
			}
		}

	}

	/**
	 * 根据参数查询出一条唯一的计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-6 下午2:49:17
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckSchedulingByParams(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@Override
	public TruckSchedulingEntity queryTruckSchedulingByParams(SimpleTruckScheduleDto simDto) {
		@SuppressWarnings("unchecked")
		// 查询列表
		List<TruckSchedulingEntity> resultList = this.getSqlSession().selectList(
				prefix + "queryTruckSchedulingByParams", simDto);
		// 如果不空
		if (CollectionUtils.isNotEmpty(resultList)) {
			// 返回数据
			return resultList.get(0);
		}
		// 其他情况
		else {
			// 返回空
			return null;
		}

	}

	/**
	 * 查询已经初始化计划列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-14 上午9:57:25
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryHasInitedList(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public Long queryHasInitedList(TruckSchedulingEntity truckScheduling) {
		// 返回数据
		return (Long) this.getSqlSession().selectOne(prefix + "queryHasInitedList", truckScheduling);
	}

	/**
	 * 查询排班任务ID对应的计划数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-6 下午2:25:30
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckScheduleByTaskIds(com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckSchedulingEntity> queryTruckScheduleByTaskIds(SimpleTruckScheduleDto truckScheduling) {
		// 不空
		// 如果不空
		if (truckScheduling != null && CollectionUtils.isNotEmpty(truckScheduling.getList())) {
			// 返回数据
			return this.getSqlSession().selectList(prefix + "queryTruckScheduleByTaskIds", truckScheduling);
		}
		// 其他情况
		else {
			// 返回空
			return null;
		}
	}

	/** 
	 * 查询总条数
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:34:42
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckSchedulingListTotal(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@Override
	public Long queryTruckSchedulingListTotal(TruckSchedulingEntity tsEntity) {
		// 查询总条数
		return (Long) this.getSqlSession().selectOne(prefix + "queryTruckSchedulingListTotal", tsEntity);
	}

	/** 
	 * 导出
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-20 下午7:42:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckSchedulingDao#queryTruckSchedulingList(com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckSchedulingEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckScheduleGridDto> queryTruckSchedulingList(TruckSchedulingEntity tsEntity) {
		// 排班对象DTO
				TruckSchedulingDto dto = new TruckSchedulingDto();
				// 排班部门
				dto.setSchedulingDepartCode(tsEntity.getSchedulingDepartCode());
				// 年月
				dto.setYearMonth(tsEntity.getYearMonth());
				// 排班类型
				dto.setSchedulingType(tsEntity.getSchedulingType());
				// 可用
				dto.setSchedulingStatus(ScheduleConstants.SCHEDULE_STATUS_ACTIVE);
				// 工作列表
				List<String> list = new ArrayList<String>();
				// 培训
				list.add(ScheduleConstants.PLAN_TYPE_TRAIN);
				// 值班
				list.add(ScheduleConstants.PLAN_TYPE_ON_DUTY);
				// 工作
				list.add(ScheduleConstants.PLAN_TYPE_WORK);
				// 设值
				dto.setList(list);
				// 返回
				return this.getSqlSession().selectList(prefix + "queryTruckSchedulingList", dto);
	}
}