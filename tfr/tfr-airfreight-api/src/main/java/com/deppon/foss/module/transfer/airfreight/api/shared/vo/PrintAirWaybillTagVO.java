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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/PrintAirWaybillTagVO.java
 *  
 *  FILE NAME          :PrintAirWaybillTagVO.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;

/**
 * 打印正单标签
 * @author foss-liuxue(for IBM)
 * @date 2012-12-25 下午6:46:52
 */
public class PrintAirWaybillTagVO implements Serializable {

	private static final long serialVersionUID = -4816350790038856371L;
	
	/**
	 * 存储根据正单号查到的正单信息
	 */
	private AirWaybillEntity airWaybillEntity; 
	
	/**
	 * 存储航空公司信息
	 */
	private List<AirlinesEntity> airlinesEntityList;  
	
	/**
	 * 航空正单dto
	 */
	private AirWayBillDto airWayBillDto;

	/**
	 * 获取 存储根据正单号查到的正单信息.
	 *
	 * @return the 存储根据正单号查到的正单信息
	 */
	public AirWaybillEntity getAirWaybillEntity() {
		return airWaybillEntity;
	}

	/**
	 * 设置 存储根据正单号查到的正单信息.
	 *
	 * @param airWaybillEntity the new 存储根据正单号查到的正单信息
	 */
	public void setAirWaybillEntity(AirWaybillEntity airWaybillEntity) {
		this.airWaybillEntity = airWaybillEntity;
	}

	/**
	 * 获取 存储航空公司信息.
	 *
	 * @return the 存储航空公司信息
	 */
	public List<AirlinesEntity> getAirlinesEntityList() {
		return airlinesEntityList;
	}

	/**
	 * 设置 存储航空公司信息.
	 *
	 * @param airlinesEntityList the new 存储航空公司信息
	 */
	public void setAirlinesEntityList(List<AirlinesEntity> airlinesEntityList) {
		this.airlinesEntityList = airlinesEntityList;
	}

	/**
	 * 获取 航空正单dto.
	 *
	 * @return the 航空正单dto
	 */
	public AirWayBillDto getAirWayBillDto() {
		return airWayBillDto;
	}

	/**
	 * 设置 航空正单dto.
	 *
	 * @param airWayBillDto the new 航空正单dto
	 */
	public void setAirWayBillDto(AirWayBillDto airWayBillDto) {
		this.airWayBillDto = airWayBillDto;
	}

}