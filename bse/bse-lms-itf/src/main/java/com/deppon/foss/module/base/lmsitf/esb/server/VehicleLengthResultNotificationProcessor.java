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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/server/VehicleLengthResultNotificationProcessor.java
 * 
 * FILE NAME        	: VehicleLengthResultNotificationProcessor.java
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
import com.deppon.esb.inteface.domain.vehicle.CarLengthInfo;
import com.deppon.esb.inteface.domain.vehicle.CarLengthSyncProcessDetail;
import com.deppon.esb.inteface.domain.vehicle.CarLengthSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.CarLengthSyncResponse;
import com.deppon.esb.pojo.transformer.jaxb.CarLengthSyncRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.CarLengthSyncResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleBrandException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.lmsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.NumberUtils;

/**
 * 用来从LMS同步公司“车长信息”到FOSS数据库：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-22 下午3:39:02
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-22 下午3:39:02
 * @since
 * @version
 */
public class VehicleLengthResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VehicleLengthResultNotificationProcessor.class);

	// 同步"车长信息"接口结果操作Service
	private ILeasedVehicleTypeService leasedVehicleTypeService;

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
		LOGGER.info(" ***************************** Began to record data ***************************** ");

		CarLengthSyncRequest carLengthSyncRequest = (CarLengthSyncRequest) req;
		CarLengthSyncResponse carLengthSyncResponse = new CarLengthSyncResponse();

		LOGGER.info(new CarLengthSyncRequestTrans()
				.fromMessage(carLengthSyncRequest));

		if (null != carLengthSyncRequest) {
			int successCount = 0, failCount = 0;
			List<CarLengthSyncProcessDetail> detailList = carLengthSyncResponse
					.getDetail();
			for (CarLengthInfo carLengthInfo : carLengthSyncRequest
					.getCarLengthList()) {
				// FOSS"车辆品牌"信息初始化
				VehicleTypeEntity vehicleType = null;
				try {
					// 业务锁
					MutexElement mutex = new MutexElement(
							carLengthInfo.getCarLengthNo(), "VEHICLELENGTH_NO",
							MutexElementType.VEHICLELENGTH_NO);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,
							NumberConstants.ZERO);
					if (result) {
						LOGGER.info("成功加锁：" + mutex.getBusinessNo());
						vehicleType = this
								.vaidationLMSDataObjectIntoFOSS(carLengthInfo);

						if (null != vehicleType
								&& NumberConstants.NUMERAL_S_ONE
										.equalsIgnoreCase(carLengthInfo
												.getOperateType())) {
							throw new VehicleBrandException(
									DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNOTNULL_ERROR);
						}
						if (null == vehicleType) {
							vehicleType = new VehicleTypeEntity();
						}

						// 不在进行校验，修改时如果不存在，则进行新增
						// else {
						// if (null == vehicleType) {
						// throw new VehicleBrandException(
						// DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
						// } else {
						// }
						// }
						// 数据转换与封装
						vehicleType.setVehicleLengthCode(carLengthInfo
								.getCarLengthNo());
						vehicleType.setVehicleLength(NumberUtils
								.createBigDecimal(String.valueOf(carLengthInfo
										.getCarLength())));

						if (NumberConstants.NUMERAL_S_ONE
								.equalsIgnoreCase(carLengthInfo
										.getOperateType())
								|| null == vehicleType) {
							LOGGER.info("新增车长信息开始。。。。。。。。。。。。。。。。。。。。。。。。");

							// 根据同步的动作执行对应的"新增"
							leasedVehicleTypeService.addLeasedVehicleType(
									vehicleType,
									ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT,
									false);
							LOGGER.info("新增车长信息结束。。。。。。。。。。。。。。。。。。。。。。。。");
						} else if (NumberConstants.NUMERAL_S_THREE
								.equalsIgnoreCase(carLengthInfo
										.getOperateType())) {
							LOGGER.info("作废车长信息开始。。。。。。。。。。。。。。。。。。。。。。。。");
							// 根据同步的动作执行对应的"删除"
							leasedVehicleTypeService.deleteLeasedVehicleType(
									vehicleType.getId(),
									ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT);
							LOGGER.info("作废车长信息结束。。。。。。。。。。。。。。。。。。。。。。。。");
						} else if (NumberConstants.NUMERAL_S_TWO
								.equalsIgnoreCase(carLengthInfo
										.getOperateType())) {
							LOGGER.info("修改车长信息开始。。。。。。。。。。。。。。。。。。。。。。。。");
							// 根据同步的动作执行对应的"修改"
							leasedVehicleTypeService.updateLeasedVehicleType(
									vehicleType,
									ComnConst.EXTERNAL_SYSTEM_LMS_USER_ACCOUNT,
									false);
							LOGGER.info("修改车长信息结束。。。。。。。。。。。。。。。。。。。。。。。。");
						} else {
							throw new LeasedVehicleTypeException(
									DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
						}
						LOGGER.info("开始解锁：" + mutex.getBusinessNo());
						// 解业务锁
						businessLockService.unlock(mutex);
						LOGGER.info("完成解锁：" + mutex.getBusinessNo());
					} else {
						detailList.add(this.recordSynchronizedRequestDataError(
								carLengthInfo, false, "出现了高并发操作！"));
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						failCount++;
						continue;
					}
					successCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							carLengthInfo, true, null));
				} catch (Exception e) {
					failCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							carLengthInfo, false, e.getMessage()));
					LOGGER.error(e.getMessage(), e);
					continue;
				}
			}
			carLengthSyncResponse.setSuccessCount(successCount);
			carLengthSyncResponse.setFailCount(failCount);

			LOGGER.info(new CarLengthSyncResponseTrans()
					.fromMessage(carLengthSyncResponse));
		}

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return carLengthSyncResponse;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>
	 * 验证当前的LMS"车辆品牌"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午6:50:00
	 * @param tractor
	 *            LMS"车辆品牌"信息参数实体
	 * @return FOSS"车辆品牌"信息实体
	 * @see
	 */
	private VehicleTypeEntity vaidationLMSDataObjectIntoFOSS(
			CarLengthInfo carLengthInfo) {
		if (null == carLengthInfo
				|| StringUtils.isBlank(carLengthInfo.getCarLengthNo())) {
			return null;
		}
		VehicleTypeEntity vehicleType = leasedVehicleTypeService
				.queryLeasedVehicleTypeByVehicleLengthCode(carLengthInfo
						.getCarLengthNo());
		return vehicleType;
	}

	/**
	 * <p>
	 * 对同步的LMS对象的做成功和失败的记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午3:42:13
	 * @param tractor
	 *            请求的"车辆品牌"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private CarLengthSyncProcessDetail recordSynchronizedRequestDataError(
			CarLengthInfo carLengthInfo, boolean result, String reason) {
		CarLengthSyncProcessDetail carLengthSyncProcessDetail = new CarLengthSyncProcessDetail();
		carLengthSyncProcessDetail.setCarLengthNo(carLengthInfo
				.getCarLengthNo());
		carLengthSyncProcessDetail.setOperateType(carLengthInfo
				.getOperateType());

		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			carLengthSyncProcessDetail.setReason(reason);
		}
		carLengthSyncProcessDetail.setResult(result);
		return carLengthSyncProcessDetail;
	}

	/**
	 * @param leasedVehicleTypeService
	 *            the leasedVehicleTypeService to set
	 */
	public void setLeasedVehicleTypeService(
			ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}
}
