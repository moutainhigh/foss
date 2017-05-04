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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/TruckDepartPlanDao.java
 * 
 *  FILE NAME     :TruckDepartPlanDao.java
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
 * FILE    NAME: TruckDepartPlanDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.TruckDepartPlanException;

/**
 * 计划DAO实现
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午7:00:14
 */
public class TruckDepartPlanDao extends iBatis3DaoImpl implements ITruckDepartPlanDao {

	/**
	 * 前缀
	 */
	private static String prefix = "Foss.scheduling.TruckDepartPlan.";

	/**
	 * 新增发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:00:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#addTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public void addTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException {
		this.getSqlSession().insert(prefix + "addTruckDepartPlan", truckDepartPlanDto);

	}

	/**
	 * 删除发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:00:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#deleteTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public void deleteTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException {

	}

	/**
	 * 查询发车计划统计
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:00:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto, int limit, int start)
			throws TruckDepartPlanException {
		// 分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		// 查询可用状态
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 班次类型
		List<String> frequencyTypeList = new ArrayList<String>();
		// 不查询出停发的情况
		// 加发
		frequencyTypeList.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		frequencyTypeList.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		truckDepartPlanDto.setFrequencyTypeList(frequencyTypeList);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryTruckDepartPlan", truckDepartPlanDto, rowBounds);
	}

	/**
	 * 
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:00:15
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#updateTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public void updateTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) throws TruckDepartPlanException {

	}

	/**
	 * 根据发车计划的类型、状态、计划日期、出发部门、到达部门查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-29 下午1:42:55
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryTruckDepartPlan(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDepartPlanDto> queryTruckDepartPlan(TruckDepartPlanDto truckDepartPlanDto) {
		// 查询可用状态
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "hasExsitTruckDepartPlan", truckDepartPlanDto);

	}

	/**
	 * 查询发车计划总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午2:05:32
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryTruckDepartPlanTotal(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public Long queryTruckDepartPlanTotal(TruckDepartPlanDto truckDepartPlanDto) {
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "queryTruckDepartPlanTotal", truckDepartPlanDto);
	}

	/**
	 * 更新保存备注及异常标记
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-19 下午2:00:03
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#updatePlanRemark(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void updatePlanRemark(TruckDepartPlanDto planDto) {
		this.getSqlSession().update(prefix + "updatePlanRemark", planDto);
	}

	/**
	 * 根据ID查询发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 上午8:52:49
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryTruckDepartPlanById(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public TruckDepartPlanDto queryTruckDepartPlanById(TruckDepartPlanDto truckDepartPlanDto) {
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDto> tempList = this.getSqlSession().selectList(prefix + "queryTruckDepartPlanById",
				truckDepartPlanDto);
		// 不空
		if (CollectionUtils.isNotEmpty(tempList)) {
			// 返回第一条
			return tempList.get(0);
		} else {
			// 返回空
			return null;
		}
	}

	/**
	 * 
	 * 根据
	 * 
	 * 状态status为Y
	 * 
	 * 线路编号lineNo
	 * 
	 * 发车日期departDate
	 * 
	 * 出发地 origOrgCode
	 * 
	 * 目的地destOrgCode
	 * 
	 * 查询当前最大的下一个班次号
	 * 
	 * 包括停发班次的
	 * 
	 * @author 096598-foss-zhongyubing
	 * 
	 * @date 2013-3-20 上午11:03:35
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryMaxfrequencyNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public Integer queryMaxfrequencyNo(TruckDepartPlanDetailDto detailDto) {
		// 查询最大班次号
		return (Integer) this.getSqlSession().selectOne(prefix + "queryMaxfrequencyNo", detailDto);
	}

	/**
	 * 查询导出
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-3-26 下午3:13:05
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDao#queryTruckDepartPlanForExport(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDepartPlanDto> queryTruckDepartPlanForExport(TruckDepartPlanDto truckDepartPlanDto)
			throws TruckDepartPlanException {
		// 查询可用状态
		truckDepartPlanDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		// 班次类型
		List<String> frequencyTypeList = new ArrayList<String>();
		// 不查询出停发的情况
		// 加发
		frequencyTypeList.add(TruckDepartPlanConstants.FREQUENCY_TYPE_ADD);
		// 正常
		frequencyTypeList.add(TruckDepartPlanConstants.FREQUENCY_TYPE_NORMAL);
		truckDepartPlanDto.setFrequencyTypeList(frequencyTypeList);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryTruckDepartPlan", truckDepartPlanDto);

	}
}