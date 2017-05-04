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
 *  PROJECT NAME  : tfr-gps-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/gps/server/ws/GPSToFOSSService.java
 *  
 *  FILE NAME          :GPSToFOSSService.java
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
 * PROJECT NAME: tfr-gps-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.gps.server.ws
 * FILE    NAME: GPSToFOSSServiceImpl.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.gps.server.ws;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.gps.NotifyArrivaltimeRequestType;
import com.deppon.esb.inteface.domain.gps.NotifyArrivaltimeResponseType;
import com.deppon.esb.inteface.domain.gps.NotifyStarttimeRequestType;
import com.deppon.esb.inteface.domain.gps.NotifyStarttimeResponseType;
import com.deppon.esb.inteface.domain.gps.VehiclePositionNotificationInfo;
import com.deppon.esb.inteface.domain.gps.VehiclePositionNotificationRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.gps.CommonException;
import com.deppon.foss.inteface.gps.FossGpsService;
import com.deppon.foss.module.transfer.departure.api.server.service.IGpsService;
import com.deppon.foss.module.transfer.departure.api.shared.dto.GpsNotifyDTO;

/**
 * FOSS提供给GPS的接口实现
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午6:12:39
 */
public class GPSToFOSSService implements FossGpsService {

	/**
	 * 
	 */
	private static Logger LOGGER = LogManager.getLogger(GPSToFOSSService.class);

	/**
	 * 业务模块接口
	 */
	private IGpsService gpsService;

	/**
	 * 
	 * 通知车辆到达时间
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-3 下午8:28:58
	 * @see com.deppon.foss.inteface.gps.FossGpsService#notifyArrivaltime(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.gps.NotifyArrivaltimeRequestType)
	 */
	@Override
	public NotifyArrivaltimeResponseType notifyArrivaltime(Holder<ESBHeader> esbHeader,
			NotifyArrivaltimeRequestType parameters) throws CommonException {
		NotifyArrivaltimeResponseType response = new NotifyArrivaltimeResponseType();
		// 任务车辆标示
		String vehicleId = parameters.getVehicleId();
		// 到达时间
		XMLGregorianCalendar arriveCalendar = parameters.getArrivalTime();
		// 转换为DATE
		Date arriveDate = convertToDate(arriveCalendar);
		// 判断返回值是否由空值
		try {
			if (StringUtils.isEmpty(vehicleId) || arriveDate == null) {
				LOGGER.debug("GPS到达接口参数不符合要求");
				LOGGER.debug("GPS到达接口，任务车辆唯一标示：" +vehicleId );
				LOGGER.debug("到达时间：" +arriveDate );
				response.setResult(false);
				return response;
			} else {
				LOGGER.info("GPS到达接口参数符合要求");
				LOGGER.info("GPS到达接口，任务车辆唯一标示：" +vehicleId );
				LOGGER.info("到达时间：" +arriveDate );
				gpsService.notifyArrivaltime(vehicleId, arriveDate);
				response.setResult(true);
				return response;
			}
		}  catch (BusinessException e) {
			response.setResult(false);
			return response;
		}
	}

	/**
	 * 
	 * 通知车辆开始时间
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-3 下午8:28:52
	 * @see com.deppon.foss.inteface.gps.FossGpsService#notifyStarttime(javax.xml.ws.Holder,
	 *      com.deppon.esb.inteface.domain.gps.NotifyStarttimeRequestType)
	 */
	@Override
	public NotifyStarttimeResponseType notifyStarttime(Holder<ESBHeader> esbHeader,
			NotifyStarttimeRequestType parameters) throws CommonException {
		NotifyStarttimeResponseType response = new NotifyStarttimeResponseType();
		// 任务车辆标示
		String vehicleId = parameters.getVehicleId();
		// 出发时间
		XMLGregorianCalendar startCalendar = parameters.getStartedTime();
		// 转换为DATE
		Date startDate = convertToDate(startCalendar);
		// 判断返回值是否由空值
		try {
			if (StringUtils.isEmpty(vehicleId) || startDate == null) {
				LOGGER.debug("GPS出发接口参数不符合要求");
				LOGGER.debug("任务车辆唯一标示：" + vehicleId );
				LOGGER.debug("出发时间：" + startDate );
				response.setResult(false);
				return response;
			} else {
				LOGGER.info("GPS出发接口参数符合要求");
				LOGGER.info("任务车辆唯一标示：" + vehicleId );
				LOGGER.info("出发时间：" + startDate );
				gpsService.notifyStarttime(vehicleId, startDate);
				response.setResult(true);
				return response;
			}
		} catch (BusinessException e) {
			response.setResult(false);
			return response;
		}
	}

	/**
	 * 
	 * 修改车辆运行记录
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-3 下午8:29:43
	 * @see com.deppon.foss.inteface.gps.FossGpsService#updateVehicleTrack(com.deppon.esb.header.ESBHeader,
	 *      com.deppon.esb.inteface.domain.gps.VehiclePositionNotificationType)
	 */
	@Override
	public void updateVehicleTrack(ESBHeader esbHeader, VehiclePositionNotificationRequest parameters) {
		List<VehiclePositionNotificationInfo> list = parameters.getVehiclePositionNotificationInfoList();
		for (VehiclePositionNotificationInfo info : list) {
			// 任务车辆标示
			String vehicleId = info.getVehicleId();
			// 路段
			String roadSegmengt = info.getRoadSegment();
			// 状态
			String state = info.getState();
			// 速度
			BigDecimal velocity = info.getVelocity();
			// 预计到达时间
			XMLGregorianCalendar preArrivalTime = info.getPredictArrivalTime();
			Date preArrivalDate = convertToDate(preArrivalTime);
			// 业务实体
			GpsNotifyDTO gpsNotifyDTO = new GpsNotifyDTO();
			// 唯一标示符
			gpsNotifyDTO.setVehicleId(vehicleId);
			// 路段
			gpsNotifyDTO.setRoadSegmengt(roadSegmengt);
			// 状态
			gpsNotifyDTO.setState(state);
			// 速度
			gpsNotifyDTO.setVelocity(velocity);
			// 预计到达时间
			gpsNotifyDTO.setPreArrivalDate(preArrivalDate);
			// 判断标示符是否为空
			if (StringUtils.isEmpty(vehicleId)) {
				LOGGER.debug("GPS在途接口，任务车辆唯一标示：" + vehicleId );
				// 不做处理
				LOGGER.info(gpsNotifyDTO);
			} else {
				gpsService.updateVehicleTrack(gpsNotifyDTO);
			}
		}

	}

	/**
	 * 
	 * 将XMLGregorianCalendar转为Date
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-3 下午8:42:51
	 */
	private Date convertToDate(XMLGregorianCalendar xcal) {

		GregorianCalendar nowGregorianCalendar = new GregorianCalendar();

		nowGregorianCalendar = xcal.toGregorianCalendar();

		Date date = nowGregorianCalendar.getTime();

		return date;
	}

	/**
	 * 设置 业务模块接口.
	 * 
	 * @param gpsService
	 *            the new 业务模块接口
	 */
	public void setGpsService(IGpsService gpsService) {
		this.gpsService = gpsService;
	}
}