/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/LMSSynchronousService.java
 *  
 *  FILE NAME          :LMSSynchronousService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.IDepartureDao;
import com.deppon.foss.module.transfer.departure.api.server.service.ILMSSynchronousService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LmsTruckDepartPlanEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * LMS同步接口.
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-6 上午11:30:12
 */
public class LMSSynchronousService implements ILMSSynchronousService {
	/** ******日志*******. */
	private static final Logger LOGGER = LogManager
			.getLogger(LMSSynchronousService.class);
	/**
	 * Lms系统往foss系统插入一条数据.
	 * 
	 * @param vehicleNo
	 *            the vehicle no
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param departPlanType
	 *            放行原因
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-24 下午1:52:36
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ILMSSynchronousService#lmsSynchronous(java.lang.String,
	 *      java.util.Date,
	 *      java.util.Date,
	 *      java.lang.String)
	 */
	@Override public void lmsSynchronous(String vehicleNo, Date startDate,
			Date endDate, String departPlanType) {
		// 通过车牌号获取车辆归属部门，车辆归属类型
		VehicleAssociationDto dto = vehicleService
				.queryVehicleAssociationDtoByVehicleNo(vehicleNo);
		LmsTruckDepartPlanEntity lms = new LmsTruckDepartPlanEntity();
		if (dto != null) {
			// 通过车牌还得到车辆的一些信息
			lms.setTruckType(dto.getVehicleOwnershipType());
			lms.setTruckOrgCode(dto.getVehicleOrganizationCode());
		}
		// 设置ID
		lms.setId(UUIDUtils.getUUID());
		// 时间为当前时间
		lms.setCreateTime(new Date());
		// 车牌号
		lms.setVehicleNo(vehicleNo);
		if (parseLmsCodeToFoss(departPlanType) == null) {
			LOGGER.error("放行原因不匹配");
			// 放行原因不匹配
			throw new TfrBusinessException("放行原因不匹配");
		}
		// 放行类型
		lms.setDepartPlanType(parseLmsCodeToFoss(departPlanType));
		// 计划放行时间
		lms.setPlanDepartTime(startDate);
		// 放行截至时间
		lms.setPlanEndTime(endDate);
		departureDao.insertLMS(lms);
	}
	/**
	 * 将Lms中的停车原因转成我们需要的代码.
	 * 
	 * @param str
	 *            the str
	 * @return the string
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-14 下午2:07:32
	 */
	private String parseLmsCodeToFoss(String str) {
		if (str == null) {
			return null;
		}
		if (DepartureConstant.PARKING_REASON_AMINTEN.equals(str)) {
			// 保养
			return DepartureConstant.DEPART_ITEM_TYPE_KEEP;
		}
		if (DepartureConstant.PARKING_REASON_SERVICE.equals(str)) {
			// 维修
			return DepartureConstant.DEPART_ITEM_TYPE_REPAIR;
		} else if (DepartureConstant.PARKING_REASON_ACCIDENT.equals(str)
				|| DepartureConstant.PARKING_REASON_VIOLATE.equals(str)
				|| DepartureConstant.PARKING_REASON_AUDIT.equals(str)
				|| DepartureConstant.PARKING_REASON_CARADS.equals(str)
				|| DepartureConstant.PARKING_REASON_UPDATES.equals(str)
				|| DepartureConstant.PARKING_REASON_TRANSFER.equals(str)) {
			// 审计
			return DepartureConstant.DEPART_ITEM_TYPE_VERIFICATION;
		}
		return null;
	}
	/**
	 * *********************************
	 * ****************.
	 */
	/************* 放行底层数据库执行 **************/
	private IDepartureDao departureDao;
	/**
	 * ***********车辆服务（综合）*************.
	 */
	private IVehicleService vehicleService;
	/**
	 * Sets the departure dao.
	 * 
	 * @param departureDao
	 *            the new departure dao
	 */
	public void setDepartureDao(IDepartureDao departureDao) {
		this.departureDao = departureDao;
	}
	/**
	 * Sets the vehicle service.
	 * 
	 * @param vehicleService
	 *            the new vehicle
	 *            service
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
}