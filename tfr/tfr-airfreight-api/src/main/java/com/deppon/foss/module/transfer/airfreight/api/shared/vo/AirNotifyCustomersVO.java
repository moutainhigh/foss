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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirWayBillVO.java
 *  
 *  FILE NAME          :AirWayBillVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirNotifyCustomersSmsInfo;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirNotifyCustomersDto;

/**
 * 空运通知客户vo
 * @author 200968 2015-08-19
 */
public class AirNotifyCustomersVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -68657495213306L;
	/**
	 * 空运通知客户
	 */
	private AirNotifyCustomersDto airNotifyCustomersDto;
	
	/**
	 * 运单 批量通知 
	 */
	private List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList;
	
	/**
	 * 通知信息发送时间范围
	 */
	private String informationReceiveTimeRange;
	
	private List<AirNotifyCustomersDto> airNotifyCustomersDtoList ;

	public AirNotifyCustomersDto getAirNotifyCustomersDto() {
		return airNotifyCustomersDto;
	}

	public void setAirNotifyCustomersDto(AirNotifyCustomersDto airNotifyCustomersDto) {
		this.airNotifyCustomersDto = airNotifyCustomersDto;
	}

	public List<AirNotifyCustomersDto> getAirNotifyCustomersDtoList() {
		return airNotifyCustomersDtoList;
	}

	public void setAirNotifyCustomersDtoList(
			List<AirNotifyCustomersDto> airNotifyCustomersDtoList) {
		this.airNotifyCustomersDtoList = airNotifyCustomersDtoList;
	}

	public String getInformationReceiveTimeRange() {
		return informationReceiveTimeRange;
	}

	public void setInformationReceiveTimeRange(String informationReceiveTimeRange) {
		this.informationReceiveTimeRange = informationReceiveTimeRange;
	}

	public List<AirNotifyCustomersSmsInfo> getAirNotifyCustomersSmsInfoList() {
		return airNotifyCustomersSmsInfoList;
	}

	public void setAirNotifyCustomersSmsInfoList(
			List<AirNotifyCustomersSmsInfo> airNotifyCustomersSmsInfoList) {
		this.airNotifyCustomersSmsInfoList = airNotifyCustomersSmsInfoList;
	}

	
	
	
}