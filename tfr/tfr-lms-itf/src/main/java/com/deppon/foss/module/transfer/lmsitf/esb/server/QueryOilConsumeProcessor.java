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
 *  PROJECT NAME  : tfr-lms-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/lmsitf/esb/server/QueryOilConsumeProcessor.java
 *  
 *  FILE NAME          :QueryOilConsumeProcessor.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-lms-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.lmsitf.esb.server
 * FILE    NAME: QueryOilConsumeProcessor.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.lmsitf.esb.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.vehicle.OilCostDetail;
import com.deppon.esb.inteface.domain.vehicle.OilCostSyncRequest;
import com.deppon.esb.inteface.domain.vehicle.OilCostSyncResponse;
import com.deppon.foss.module.transfer.management.api.server.service.IQueryVehicleOilInfoService;
import com.deppon.foss.module.transfer.management.api.shared.dto.VehicleOilInfoDto;

/**
 * FOSS提供给LMS的接口
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-12-10 下午2:12:42
 */
public class QueryOilConsumeProcessor implements IProcess {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(QueryOilConsumeProcessor.class);
	/**
	 * 查询车辆油耗业务接口
	 */
	private IQueryVehicleOilInfoService queryVehicleOilInfoService;

	/**
	 * 提供油耗数据给LMS
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-27 下午3:52:29
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		if (req != null) {
			// 获取查询油耗的条件
			OilCostSyncRequest request = (OilCostSyncRequest) req;
			String vehicleNo = request.getCarNo();
			logger.info("车牌号：" + request.getCarNo());
			logger.info("开始时间：" + request.getBeginTime());
			logger.info("结束时间：" + request.getEndTime());
			OilCostSyncResponse response = new OilCostSyncResponse();
			// 调用业务接口
			List<VehicleOilInfoDto> list = queryVehicleOilInfoService
					.queryVehicleOilInfo(vehicleNo, request.getBeginTime(),
							request.getEndTime());
			/**
			 * 此处需要调用业务接口
			 */
			for (int i = 0; i < list.size(); i++) {
				OilCostDetail ocd = new OilCostDetail();
				// 获取油耗对象
				VehicleOilInfoDto dto = list.get(i);
				// 设置车牌号
				ocd.setCarNo(dto.getVehicleNo());
				// 设置当前公里数
				ocd.setCurentMile_0020(dto.getCurrentMile().intValue());
				// 设置加油升数
				ocd.setRefuelAmount(dto.getRefuelAmount().floatValue());
				// 设置加油时间
				ocd.setRefuelTime(dto.getRefuelTime());

				// 向返回值设置返回对象，将查询的油耗信息添加到返回对象中
				response.getOilCostDetails().add(ocd);
			}

			return response;

		} else {
			logger.debug("查询条件为空");
			return null;
		}
	}

	/**
	 * 错误处理
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-27 下午3:52:29
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {

		return null;
	}

	/**
	 * 设置 查询车辆油耗业务接口.
	 * 
	 * @param queryVehicleOilInfoService
	 *            the new 查询车辆油耗业务接口
	 */
	public void setQueryVehicleOilInfoService(
			IQueryVehicleOilInfoService queryVehicleOilInfoService) {
		this.queryVehicleOilInfoService = queryVehicleOilInfoService;
	}

}