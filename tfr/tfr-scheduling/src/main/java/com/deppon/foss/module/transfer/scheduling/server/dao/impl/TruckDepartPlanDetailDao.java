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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/TruckDepartPlanDetailDao.java
 * 
 *  FILE NAME     :TruckDepartPlanDetailDao.java
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
 * FILE    NAME: TruckDepartPlanDetailDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto;

/**
 * 计划明细Dao实现
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午7:02:41
 */
public class TruckDepartPlanDetailDao extends iBatis3DaoImpl implements ITruckDepartPlanDetailDao {

	/**
	 * 前缀
	 */
	private static String prefix = "Foss.scheduling.TruckDepartPlanDetail.";

	/**
	 * 新增计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:02:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#addTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void addTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null) {
			this.getSqlSession().insert(prefix + "addTruckDepartPlanDetail", truckDepartPlanDetailDto);
		}

	}

	/**
	 * 删除计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:02:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#deleteTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void deleteTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null && CollectionUtils.isNotEmpty(truckDepartPlanDetailDto.getIds())) {
			// 删除的ID
			truckDepartPlanDetailDto.setList(truckDepartPlanDetailDto.getIds());
			// 删除标记
			truckDepartPlanDetailDto.setStatus(TruckDepartPlanConstants.STATUS_NOT_ACTIVE);
			// 非导入状态的计划明细
			truckDepartPlanDetailDto.setInitFlag(TruckDepartPlanConstants.INIT_FLAG_N);
			// 非出发状态
			truckDepartPlanDetailDto.setHasLeft(TruckDepartPlanConstants.LEFT_FLAG_N);
			// 执行更新删除动作
			this.getSqlSession().update(prefix + "deleteTruckDepartPlanDetail", truckDepartPlanDetailDto);
		}

	}

	/**
	 * 查询发车计划明细列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:02:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null && StringUtils.isNotBlank(truckDepartPlanDetailDto.getTruckDepartPlanId())) {
			return this.getSqlSession().selectList(prefix + "queryTruckDepartPlanDetail", truckDepartPlanDetailDto);
		} else {
			return null;
		}
	}

	/**
	 * 分页查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 上午10:13:20
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetailBylimit(
			TruckDepartPlanDetailDto truckDepartPlanDetailDto, int limit, int start) {
		if (truckDepartPlanDetailDto != null && StringUtils.isNotBlank(truckDepartPlanDetailDto.getTruckDepartPlanId())) {
			RowBounds row = new RowBounds(start, limit);
			// 非删除状态
			truckDepartPlanDetailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
			return this.getSqlSession()
					.selectList(prefix + "queryTruckDepartPlanDetail", truckDepartPlanDetailDto, row);
		} else {
			return null;
		}
	}

	/**
	 * 导出发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-26 上午10:13:20
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TruckDepartPlanDetailDto> queryTruckDepartPlanDetailBylimitForExport(
			TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null && StringUtils.isNotBlank(truckDepartPlanDetailDto.getTruckDepartPlanId())) {
			// 非删除状态
			truckDepartPlanDetailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
			// 查询
			return this.getSqlSession().selectList(prefix + "queryTruckDepartPlanDetailBylimitForExport",
					truckDepartPlanDetailDto);
		} else {
			return null;
		}
	}

	/**
	 * 更新明细数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:02:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#updateTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void updateTruckDepartPlanDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		this.getSqlSession().update(prefix + "updateTruckDepartPlanDetail", truckDepartPlanDetailDto);

	}

	/**
	 * 批量新增计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-22 上午11:23:44
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#batchAddTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDto)
	 */
	@Override
	public void batchAddTruckDepartPlanDetail(TruckDepartPlanDto truckDepartPlanDto) {
		// 有原来的批量batchAddTruckDepartPlanDetail换为batchAddTruckDepartPlanDetailOne
		if (truckDepartPlanDto != null && CollectionUtils.isNotEmpty(truckDepartPlanDto.getList())) {
			// 循环
			for (TruckDepartPlanDetailDto dto : truckDepartPlanDto.getList()) {
				this.getSqlSession().insert(prefix + "batchAddTruckDepartPlanDetailOne", dto);
			}
		}
	}

	/**
	 * 查询明细条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午10:02:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryTotalCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public Long queryTotalCount(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null && StringUtils.isNotBlank(truckDepartPlanDetailDto.getTruckDepartPlanId())) {
			// 非删除状态
			truckDepartPlanDetailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
			return (Long) this.getSqlSession().selectOne(prefix + "queryTotalCount", truckDepartPlanDetailDto);
		} else {
			return TruckDepartPlanConstants.RESULT_ZERO;
		}
	}

	/**
	 * 根据ID主键查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午5:27:18
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryTruckDepartPlanDetailById(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public TruckDepartPlanDetailDto queryTruckDepartPlanDetailById(TruckDepartPlanDetailDto detailDto) {
		if (detailDto != null && StringUtils.isNotBlank(detailDto.getId())) {
			// 可用
			detailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
			// 查询
			@SuppressWarnings("unchecked")
			List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
					prefix + "queryTruckDepartPlanDetailById", detailDto);
			// 获取发车计划明细
			if (CollectionUtils.isNotEmpty(tempList)) {
				return tempList.get(0);
			} else {
				return null;
			}
		}
		return null;
	}

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
	 * @date 2012-12-12 下午4:44:10
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryLatestTruckDepartPlanDetail(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto) {
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
				prefix + "queryLatestTruckDepartPlanDetail", planDetailDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 下发发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-15 下午4:52:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#releaseTruckDepartPlanDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void releaseTruckDepartPlanDetail(TruckDepartPlanDetailDto planDetailDto) {
		if (planDetailDto != null && CollectionUtils.isNotEmpty(planDetailDto.getIds())) {
			// 下发的ID
			planDetailDto.setList(planDetailDto.getIds());
			// 下发状态
			planDetailDto.setPlanStatus(TruckDepartPlanConstants.PLAN_STATUS_RELEASE);
			this.getSqlSession().update(prefix + "releaseTruckDepartPlanDetail", planDetailDto);
		}

	}

	/**
	 * 通过车牌在发车计划表中查找当天的该车牌号是否有发车计划
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-20 下午4:34:44
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryPlanDetailByVehicleNoAndDate(com.deppon.foss.module.transfer.scheduling.api.shared.dto.CarInfoDto)
	 */
	@Override
	public TruckDepartPlanDetailDto queryPlanDetailByVehicleNoAndDate(CarInfoDto carDto) {
		if (carDto != null) {
			carDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		}
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
				prefix + "queryPlanDetailByVehicleNoAndDate", carDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据 出发部门、到达部门、日期、线路、班次查询，用于排除当天线路班次唯一
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-23 下午4:28:45
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryCurrentDayLineFrequencyOnly(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public TruckDepartPlanDetailDto queryCurrentDayLineFrequencyOnly(TruckDepartPlanDetailDto planDetailDto) {
		if (planDetailDto != null) {
			planDetailDto.setStatus(TruckDepartPlanConstants.STATUS_ACTIVE);
		}
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
				prefix + "queryCurrentDayLineFrequencyOnly", planDetailDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据 出发部门、到达部门、日期查询发车计划明细
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-7 上午8:59:39
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryPlanDetailCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public Long queryPlanDetailCount(TruckDepartPlanDetailDto planDetailDto) {
		return (Long) this.getSqlSession().selectOne(prefix + "queryPlanDetailCount", planDetailDto);
	}

	/**
	 * 改变车辆归属类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-15 下午7:55:18
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#changePlanDetailTruckType(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void changePlanDetailTruckType(TruckDepartPlanDetailDto planDetail) {
		if (planDetail != null && StringUtils.isNotBlank(planDetail.getId())) {
			this.getSqlSession().update(prefix + "changePlanDetailTruckType", planDetail);
		}

	}

	/** 
	 * @author dp-duyi
	 * @date 2013-6-25 下午3:48:03
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryLatestTruckDepartPlanDetailByContainerNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public TruckDepartPlanDetailDto queryLatestTruckDepartPlanDetailByContainerNo(
			TruckDepartPlanDetailDto planDetailDto) {
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
				prefix + "queryLatestTruckDepartPlanDetailByContarnerNo", planDetailDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}
	/**
	 * @author heyongdong	
	 * @date 2014年9月1日 15:47:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryDepartPlanDetailByTrailerVehicleNo(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public TruckDepartPlanDetailDto queryDepartPlanDetailByTrailerVehicleNo(
			TruckDepartPlanDetailDto detailDto) {
		@SuppressWarnings("unchecked")
		List<TruckDepartPlanDetailDto> tempList = this.getSqlSession().selectList(
				prefix + "queryDepartPlanDetailByTrailerVehicleNo", detailDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	* @description 获取长途/短途发车计划信息
	* @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanDetailDao#queryTruckDepartPlanInfoDetail(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:39:16
	* @version V1.0
	 */
	@Override
	public TruckDepartPlanDetailDto queryTruckDepartPlanInfoDetail(TruckDepartPlanDetailDto truckDepartPlanDetailDto) {
		if (truckDepartPlanDetailDto != null) {
			return (TruckDepartPlanDetailDto) this.getSqlSession().selectOne(prefix + "queryLongOrShortDepartPlanInfoDetail", truckDepartPlanDetailDto);
		} else {
			return null;
		}
	}

}