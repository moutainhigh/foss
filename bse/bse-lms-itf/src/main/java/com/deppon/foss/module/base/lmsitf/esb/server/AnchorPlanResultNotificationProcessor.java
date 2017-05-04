/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/AnchorPlanResultNotificationProcessor.java
 * 
 * FILE NAME        	: AnchorPlanResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.server;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vehicle.CarStopPlanInfo;
import com.deppon.esb.inteface.domain.vehicle.CarStopPlanProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.CarStopPlanRequest;
import com.deppon.esb.inteface.domain.vehicle.CarStopPlanResponse;
import com.deppon.esb.pojo.transformer.jaxb.CarStopPlanRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.CarStopPlanResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.lmsitf.esb.service.ISynchronousOwnVehicleService;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;

/**
 * 用来从LMS同步公司“停车计划”到FOSS数据库数据字典：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-23 下午4:47:09
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-23 下午4:47:09
 * @since
 * @version
 */
public class AnchorPlanResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnchorPlanResultNotificationProcessor.class);

	// 同步"公司车辆（厢式车、挂车、拖头）"接口结果操作Service
	private ISynchronousOwnVehicleService synchronousOwnVehicleService;

	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;

	/**
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info(" ***************************** 同步”停车计划“开始 ***************************** ");

		CarStopPlanRequest carStopPlanRequest = (CarStopPlanRequest) req;
		CarStopPlanResponse carStopPlanResponse = new CarStopPlanResponse();
		LOGGER.info(new CarStopPlanRequestTrans()
				.fromMessage(carStopPlanRequest));

		if (null != carStopPlanRequest) {
			int successCount = 0, failCount = 0;
			List<CarStopPlanProcessDetail> detailList = carStopPlanResponse
					.getDetail();
			for (CarStopPlanInfo carStopPlanInfo : carStopPlanRequest
					.getCarStopPlanList()) {
				try {
					// 业务锁
					MutexElement mutex = new MutexElement(
							carStopPlanInfo.getSeqNo(), "VEHICLELMS_CODE",
							MutexElementType.VEHICLELMS_CODE);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,
							NumberConstants.ZERO);
					if (result) {
						LOGGER.info("成功加锁：" + mutex.getBusinessNo());
					} else {
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						failCount++;
						continue;
					}
					// FOSS"停车计划"信息初始化
					OwnTruckEntity ownTruck = this
							.converterLMSDataObjectToFOSSObjectData(carStopPlanInfo);
					VehicleUnavilableEntity vehicleUnavilable = null;
					if (null == ownTruck) {
						throw new OwnDriverException(
								DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
					} else {
						vehicleUnavilable = new VehicleUnavilableEntity();
					}
					// 停车原因
					vehicleUnavilable.setLmsCode(carStopPlanInfo.getSeqNo());
					if (StringUtils.isNotBlank(carStopPlanInfo.getCarNo())) {
						vehicleUnavilable.setVehicleNo(carStopPlanInfo
								.getCarNo().trim());
					} else {
						vehicleUnavilable.setVehicleNo(carStopPlanInfo
								.getCarNo());
					}

					vehicleUnavilable.setBeginTime(carStopPlanInfo
							.getBeginTime());
					vehicleUnavilable.setEndTime(carStopPlanInfo.getEndTime());
					vehicleUnavilable.setUnavilableCode(carStopPlanInfo
							.getReasonNumber());
					// 数据转换与封装
					ownTruck.setVehicleLmsCode(carStopPlanInfo.getSeqNo());
					ownTruck.setVehicleNo(carStopPlanInfo.getCarNo());
					ownTruck.setBeginDate(carStopPlanInfo.getBeginTime());
					ownTruck.setEndDate(carStopPlanInfo.getEndTime());
					ownTruck.setUnavilableCode(carStopPlanInfo
							.getReasonNumber());

					synchronousOwnVehicleService
							.synchronousOwnVehiclePlanByLMS(ownTruck,
									vehicleUnavilable);

					LOGGER.info("开始解锁：" + mutex.getBusinessNo());
					// 解业务锁
					businessLockService.unlock(mutex);
					LOGGER.info("完成解锁：" + mutex.getBusinessNo());

					// 无异常错误记录
					successCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							carStopPlanInfo, true, null));
				} catch (Exception e) {
					failCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							carStopPlanInfo, false, e.getMessage()));
					LOGGER.error(e.getMessage(), e);
					continue;
				}
			}
			carStopPlanResponse.setSuccessCount(successCount);
			carStopPlanResponse.setFailCount(failCount);
			LOGGER.info(new CarStopPlanResponseTrans()
					.fromMessage(carStopPlanResponse));
		}

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return carStopPlanResponse;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>
	 * 验证当前的LMS"停车计划"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午6:50:00
	 * @param tractor
	 *            LMS"停车计划"信息参数实体
	 * @return FOSS"停车计划"信息实体
	 * @see
	 */
	private OwnTruckEntity converterLMSDataObjectToFOSSObjectData(
			CarStopPlanInfo carStopPlanInfo) {
		if (null == carStopPlanInfo
				|| StringUtils.isBlank(carStopPlanInfo.getCarNo())) {
			return null;
		}
		OwnTruckEntity ownTruck = new OwnTruckEntity();
		ownTruck.setVehicleNo(carStopPlanInfo.getCarNo());
		return synchronousOwnVehicleService
				.queryOwnVehicleBySelective(ownTruck);
	}

	/**
	 * <p>
	 * 对同步的LMS对象的做成功和失败的记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午3:42:13
	 * @param tractor
	 *            请求的"停车计划"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private CarStopPlanProcessDetail recordSynchronizedRequestDataError(
			CarStopPlanInfo carStopPlanInfo, boolean result, String reason) {
		CarStopPlanProcessDetail carStopPlanProcessDetail = new CarStopPlanProcessDetail();
		carStopPlanProcessDetail.setCarNo(carStopPlanInfo.getCarNo());
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			carStopPlanProcessDetail.setReason(reason);
		}
		carStopPlanProcessDetail.setResult(result);
		return carStopPlanProcessDetail;
	}

	/**
	 * @param synchronousOwnVehicleService
	 *            the synchronousOwnVehicleService to set
	 */
	public void setSynchronousOwnVehicleService(
			ISynchronousOwnVehicleService synchronousOwnVehicleService) {
		this.synchronousOwnVehicleService = synchronousOwnVehicleService;
	}
}
