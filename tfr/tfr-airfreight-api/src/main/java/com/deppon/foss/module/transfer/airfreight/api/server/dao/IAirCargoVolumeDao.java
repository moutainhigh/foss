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
 *  FILE PATH          :/IAirCargoVolumeDao.java
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
 * PROJECT NAME: tfr-airfreight-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.airfreight.api.server.service
 * FILE    NAME: IAirSpaceService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;


import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailVolumeEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeForExportDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDetailVolumeDto;


/**
 *  空运货量统计Dao接口
 * 
 * @author dp-liming
 * @date 2012-10-18 上午10:31:23
 */
public interface IAirCargoVolumeDao   {
	/**
	 *  分页查询空运货量明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午9:50:47
	 */
	 List<AirCargoVolumeQueryEntity> queryAirCargoVolume(AirCargoVolumeDto airCargoVolumeDto, int limit, int start);

	/**
	 *  查询空运货量明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午9:52:47
	 */
	 List<AirCargoVolumeQueryEntity> queryAirCargoVolume(AirCargoVolumeDto airCargoVolumeDto);
	
	
	/**
	 *  查询空运货量条数
	 * @author  dp-liming
	 * @date 2012-10-19 上午9:55:47
	 */
	 Long queryCount(AirCargoVolumeDto airCargoVolumeDto);

	/**
	 *  查询线路舱位明细
	 * @author  dp-liming
	 * @date 2012-10-19 上午9:55:47
	 */
	 List<AirSpaceDetailVolumeEntity> queryAirSpace(AirSpaceDetailVolumeDto  airSpaceDetailVolumeDto);

	/**
	 *  查询某件货物的库存信息
	 * @author  dp-liming
	 * @date 2012-10-19 上午9:57:47
	 */
	 List<AirCargoVolumeQueryEntity> querySerialNumberStock(AirCargoVolumeDto airCargoVolumeDto);

	 /**
	 * 查询货量统计for export
	 * @author  dp-liming
	 * @date 2012-10-19 上午10:50:47
	 * @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirCargoVolumeDao#queryAirCargoVolume(com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirCargoVolumeDto, int, int)
	 */
	List<AirCargoVolumeForExportDto> queryAirCargoVolumeForExport(
			AirCargoVolumeDto airCargoVolumeDto, int limit, int start);
	
	

	
}