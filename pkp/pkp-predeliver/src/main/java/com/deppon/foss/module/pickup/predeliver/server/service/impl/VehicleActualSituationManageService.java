/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/VehicleActualSituationManageService.java
 * 
 * FILE NAME        	: VehicleActualSituationManageService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleRegionDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IVehicleActualSituationEntityDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IVehicleActualSituationManageService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VehicleActualSituationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.VehicleActualSituationException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 车辆车载信息管理服务
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-3 上午11:39:10
 */
public class VehicleActualSituationManageService implements IVehicleActualSituationManageService {

	private IVehicleActualSituationEntityDao vehicleActualSituationEntityDao;

	// “公司车辆（厢式车、挂车、拖头）”Service接口
	private IOwnVehicleService ownVehicleService;

	// “外请车辆（厢式车、挂车、拖头）”Service接口
	private ILeasedVehicleService leasedVehicleService;

	/**
	 * 通过车牌号查询车载信息.
	 * 
	 * @param vehicleNo 车牌号
	 * @return the vehicle actual situation entity
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 上午11:40:21
	 */
	@Override
	public VehicleActualSituationEntity queryByVehicleNo(String vehicleNo) {
		return vehicleActualSituationEntityDao.queryByVehicleNo(vehicleNo);
	}

	/**
	 * 根据车牌号修改车辆剩余重量和剩余体积.
	 * 
	 * @param vehicleActualSituationEntity 车辆实际情况实体
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-11-3 下午1:54:39
	 */
	@Transactional
	@Override
	public boolean updateWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity) {
		// 判空
		if (vehicleActualSituationEntity != null) {
			int count = vehicleActualSituationEntityDao.updateWVByVehicleNo(vehicleActualSituationEntity);
			if (count == 1) {
				return true;
			} else {
				throw new VehicleActualSituationException(ActionMessageType.VEHICLE_NOT_EXIST);
			}
		}
		return false;
	}

	/**
	 * 根据已有自有车查询出车辆的实际情况.
	 * 
	 * @param ownVehicleRegionDtos 已查询出的自有车List
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-11-9 下午5:50:59
	 */
	@Override
	public List<VehicleActualSituationEntity> queryListByVehicleNo(List<OwnVehicleRegionDto> ownVehicleRegionDtos) {
		return null;
	}

	/**
	 * 根据车牌号清空载重和载空.
	 * 
	 * @param actualSituationEntities the actualSituationEntities
	 * @return the int
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 上午8:41:31
	 */
	@Transactional
	@Override
	public int updateWV2EmptyByVehicleNo(VehicleActualSituationEntity actualSituationEntity) {
		int updateCount = vehicleActualSituationEntityDao.updateWV2EmptyByVehicleNo(actualSituationEntity);
		return updateCount;
	}

	/**
	 * 按分页查询车辆实况表.
	 * 
	 * @param start the start
	 * @param limit the limit
	 * @return the list
	 * @author 038590-foss-wanghui
	 * @date 2012-11-17 下午6:10:31
	 */
	@Override
	public List<VehicleActualSituationEntity> queryByPage(int start, int limit) {
		List<VehicleActualSituationEntity> actualSituationEntities = vehicleActualSituationEntityDao.queryByPage(start, limit);
		return actualSituationEntities;
	}

	/**
	 * @param vehicleActualSituationEntityDao the
	 *            vehicleActualSituationEntityDao to set
	 */
	public void setVehicleActualSituationEntityDao(IVehicleActualSituationEntityDao vehicleActualSituationEntityDao) {
		this.vehicleActualSituationEntityDao = vehicleActualSituationEntityDao;
	}

	/**
	 * 根据车牌号增加车载信息.
	 * 
	 * @param vehicleActualSituationEntity the vehicleActualSituationEntity
	 * @return true, if successful
	 * @author 038590-foss-wanghui
	 * @date 2012-11-19 下午5:20:38
	 */
	@Override
	public boolean addWVByVehicleNo(VehicleActualSituationEntity vehicleActualSituationEntity) {
		// 更新车辆的车载信息
		int updateCount = vehicleActualSituationEntityDao.addWVByVehicleNo(vehicleActualSituationEntity);
		if (updateCount == 0) {
			// 剩余体积
			BigDecimal remainingVolume = BigDecimal.valueOf(0);
			// 剩余重量
			BigDecimal remainingWeight = BigDecimal.valueOf(0);
			// 自有车查询条件
			OwnTruckEntity condition = new OwnTruckEntity();
			// 车牌号
			condition.setVehicleNo(vehicleActualSituationEntity.getVehicleNo());
			// 查询公司自有车
			OwnTruckEntity ownTruck = queryOwnTruck(condition);
			if (ownTruck == null) {
				// 根据车牌号查询外请车
				LeasedTruckEntity leasedTruck = leasedVehicleService.queryLeasedVehicleByVehicleNo(vehicleActualSituationEntity.getVehicleNo());
				if (leasedTruck != null) {
					// 剩余重量
					remainingVolume = BigDecimalOperationUtil.subtract(leasedTruck.getSelfVolume(), vehicleActualSituationEntity.getRemainingVolume());
					// 剩余体积
					remainingWeight = BigDecimalOperationUtil.subtract(leasedTruck.getDeadLoad(),vehicleActualSituationEntity.getRemainingWeight());
				}
			} else {
				// 剩余重量
				remainingVolume = BigDecimalOperationUtil.subtract(ownTruck.getSelfVolume(),vehicleActualSituationEntity.getRemainingVolume());
				// 剩余体积
				remainingWeight = BigDecimalOperationUtil.subtract(ownTruck.getDeadLoad(), vehicleActualSituationEntity.getRemainingWeight());
			}
			VehicleActualSituationEntity record = new VehicleActualSituationEntity();
			// 设置id
			record.setId(UUIDUtils.getUUID());
			// 剩余重量
			record.setRemainingWeight(remainingWeight);
			// 剩余体积
			record.setRemainingVolume(remainingVolume);
			// 车牌号
			record.setVehicleNo(vehicleActualSituationEntity.getVehicleNo());
			vehicleActualSituationEntityDao.addVehicleSituation(record);
		}
		return true;
	}

	/**
	 * 根据车牌号查询公司自有车.
	 * 
	 * @param condition the condition
	 * @return the own truck entity
	 * @author 038590-foss-wanghui
	 * @date 2013-3-14 上午9:35:29
	 */
	private OwnTruckEntity queryOwnTruck(OwnTruckEntity condition) {
		// 根据车牌号查询自有车-- 厢式车
		OwnTruckEntity ownTruck = ownVehicleService.queryOwnVehicleBySelective(condition, DictionaryValueConstants.VEHICLE_TYPE_VAN);
		// 根据车牌号查询自有车-- 拖头
		if (ownTruck == null) {
			ownTruck = ownVehicleService.queryOwnVehicleBySelective(condition, DictionaryValueConstants.VEHICLE_TYPE_TRACTORS);
		}
		// 根据车牌号查询自有车-- 挂车
		if (ownTruck == null) {
			ownTruck = ownVehicleService.queryOwnVehicleBySelective(condition, DictionaryValueConstants.VEHICLE_TYPE_TRAILER);
		}
		return ownTruck;
	}

	/**
	 * 添加车辆实况实体.
	 * 
	 * @param situationEntity 实体 -- vehicleNo 车牌号 remainingWeight 车辆自重
	 *            remainingVolume 车辆净空 createTime
	 * @return the int
	 * @author 038590-foss-wanghui
	 * @date 2012-12-6 上午11:06:24
	 */
	@Override
	public int addVehicleSituation(VehicleActualSituationEntity situationEntity) {
		situationEntity.setId(UUIDUtils.getUUID());
		return vehicleActualSituationEntityDao.addVehicleSituation(situationEntity);
	}

	/**
	 * 删除车辆实况实体.
	 * 
	 * @param vehicleNo the vehicleNo
	 * @return the int
	 * @author 038590-foss-wanghui
	 * @date 2012-12-27 下午3:16:07
	 */
	public int deleteVehicleSituation(String vehicleNo) {
		return vehicleActualSituationEntityDao.deleteVehicleSituation(vehicleNo);
	}

	/**
	 * @param ownVehicleService the ownVehicleService to set
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	/**
	 * @param leasedVehicleService the leasedVehicleService to set
	 */
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}
}