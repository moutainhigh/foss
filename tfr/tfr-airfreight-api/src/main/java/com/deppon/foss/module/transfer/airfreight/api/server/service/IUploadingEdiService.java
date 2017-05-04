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
 *  FILE PATH          :/IUploadingEdiService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirArriveSendInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirStockInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.QueryAirArriveInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.UploadingEdiDto;

/**
 * edi接口 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-26 下午1:33:14
 */
public interface IUploadingEdiService extends IService {

	/**
	 * 根据正单号查询运单list
	 * @param  airWaybillNo 正单号
	 * @return List<UploadingEdiDto> 返回list<dto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午1:45:31
	 */
	List<UploadingEdiDto> queryWayBillByAirWaybillNo (String airWaybillNo);
	
	/**
	 * 查询空运到达派送信息录入情况统计
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	List<AirArriveSendInfoDto> queryFlightArriveSendInfo (QueryAirArriveInfoDto queryAirArriveInfoDto);
	
	/**
	 * 查询空运库存信息
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	List<AirStockInfoDto> queryAirStockInfo(QueryAirArriveInfoDto queryAirArriveInfoDto);
}