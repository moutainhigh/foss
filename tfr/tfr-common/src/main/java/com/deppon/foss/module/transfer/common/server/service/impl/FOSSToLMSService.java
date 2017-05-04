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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-common
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/server/service/impl/FOSSToLMSService.java
 *  
 *  FILE NAME          :FOSSToLMSService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.server.service.impl
 * FILE    NAME: FOSSToLMSService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.common.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.vehicle.CarRunInfo;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationRequest;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfo;
import com.deppon.esb.inteface.domain.vehicle.CarStateInfocationRequest;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToLMSService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.dto.LMSVehicleStateDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.LMSVehicleTravelDataDto;

/**
 * FOSS提供车辆状态数据和行驶数据给LMS
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-12-4 下午4:02:37
 */
public class FOSSToLMSService implements IFOSSToLMSService {
	private static final String BUSINESSDESC1_STATUS = "更新车辆状态";
	private static final String VEHICLE_STATE_ERROR_CODE = "FOSSTOLMS VEHICLE STATE ERROR:";
	private static final String VEHICLE_TRIVAL_ERROR_CODE = "FOSSTOLMS VEHICLE TRIVAL ERROR:";
	private static final String BEGIN_SEND_MESSAGE = "开始向LMS发送请求";
	private static final String END_SEND_MESSAGE = "向LMS发送请求结束";

	private static final Logger LOGGER = LogManager.getLogger(FOSSToLMSService.class);

	/**
	 * 更新车辆状态数据 GPS向FOSS发送信息时，FOSS需要同步将此信息通知LMS系统
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-4 下午3:14:29
	 */
	@Override
	public void updateVehicleState(LMSVehicleStateDto lmsVehicleDto) {
		// 请求对象
		CarStateInfocationRequest request = new CarStateInfocationRequest();
		// 修改车辆状态对象
		CarStateInfo carInfo = new CarStateInfo();
		// 车辆到底时间
		carInfo.setArriveDateTime(lmsVehicleDto.getArriveDate());
		// 出发时间
		carInfo.setDepartureTime(lmsVehicleDto.getStartDate());
		// 车牌号
		carInfo.setCarNumber(lmsVehicleDto.getVehicleNo());
		// 车辆状态
		carInfo.setCarState(lmsVehicleDto.getState());
		// 城市编码
		carInfo.setCity(lmsVehicleDto.getCityCode());
		// 将车辆状态数据设置到请求信息中
		request.setCarStateInfo(carInfo);
		// 设置头信息
		AccessHeader header = new AccessHeader();
		//业务id
		header.setBusinessId(lmsVehicleDto.getVehicleNo());
		header.setBusinessDesc1(BUSINESSDESC1_STATUS);
		header.setEsbServiceCode(TransferConstants.LMS_VEHICLE_STATE_SERVICE_CODE);
		//设置头版本信息
		header.setVersion(TransferConstants.ESB_ACCESSHEADER_VERSION);
		
		LOGGER.info(BEGIN_SEND_MESSAGE);
		try {
			// 发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info(END_SEND_MESSAGE);
		} catch (Exception e) {
			LOGGER.error(VEHICLE_STATE_ERROR_CODE, e);
		}

	}

	/**
	 * 
	 * 提供行驶数据 中转录入油耗信息时，提取车辆运行数据给LMS
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-5 上午10:12:11
	 */
	@Override
	public void updateVehicleTravelData(LMSVehicleTravelDataDto lmsVehicleTravelDataDto) {

		CarRuncationRequest request = new CarRuncationRequest();
		CarRunInfo carRunInfo = new CarRunInfo();
		// 车牌号
		carRunInfo.setCarNumber(lmsVehicleTravelDataDto.getVehicleNo());
		// 车辆使用类型
		carRunInfo.setCarUsage(lmsVehicleTravelDataDto.getVehicleUsage());
		// 货柜号
		carRunInfo.setContainerNo(lmsVehicleTravelDataDto.getCounterNo());
		// 原始公里数据
		carRunInfo.setOriginalKilometer(lmsVehicleTravelDataDto.getOriginalKilometer());
		// 目的公里数据
		carRunInfo.setPurposeKilometer(lmsVehicleTravelDataDto.getPurposeKilometer());
		// 到底时间
		carRunInfo.setReachDateTime(lmsVehicleTravelDataDto.getReachDate());
		// 唯一标示号
		carRunInfo.setSoleNumber(lmsVehicleTravelDataDto.getUniqueNo());
		// 操作类型
		carRunInfo.setActionType(lmsVehicleTravelDataDto.getOperatorState());
		request.setCarRunInfo(carRunInfo);

		// 获取头信息
		AccessHeader header = new AccessHeader();
		header.setBusinessId(lmsVehicleTravelDataDto.getVehicleNo());
		header.setBusinessDesc1(lmsVehicleTravelDataDto.getUniqueNo());
		header.setEsbServiceCode(TransferConstants.LMS_VEHICLE_TRAVEL_DATA_SERVICE_CODE);
		//设置头版本信息
		header.setVersion(TransferConstants.ESB_ACCESSHEADER_VERSION);

		LOGGER.info(BEGIN_SEND_MESSAGE);
		try {
			// 异步发送请求
			ESBJMSAccessor.asynReqeust(header, request);
			LOGGER.info(END_SEND_MESSAGE);
		} catch (Exception e) {
			LOGGER.error(VEHICLE_TRIVAL_ERROR_CODE, e);
		}
	}

}