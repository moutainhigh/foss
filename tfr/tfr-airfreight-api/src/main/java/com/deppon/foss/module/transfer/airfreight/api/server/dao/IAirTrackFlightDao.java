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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirTrackFlightDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;

public interface IAirTrackFlightDao {
	/**
	 * 根据条件查询航空正单
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-17 下午5:07:05
	 */
	List<AirTrackFlightDto> queryAirTrackFlight(AirTrackFlightDto dto, int start, int limit);
	/**
	 * 新增跟踪信息： 起飞跟踪、过程跟踪、到达跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午7:44:01
	 */
	void addAirTrackFlight(List<AirTrackFlightEntity> trackEntityList);
	/**
	 * 统计查询结果记录总数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 下午4:56:50
	 */
	Long queryTrackFlightCount(AirTrackFlightDto dto);
	
	/**
	 * 提供给综合查询的接口，根据正单号查询跟踪信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-23 下午2:52:30
	 */
	List<AirTrackFlightDto> queryTrackInfoByAirWaybillNo(String airWaybillNo);
}