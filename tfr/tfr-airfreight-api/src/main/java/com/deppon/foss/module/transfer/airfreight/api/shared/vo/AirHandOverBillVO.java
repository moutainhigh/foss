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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirHandOverBillVO.java
 *  
 *  FILE NAME          :AirHandOverBillVO.java
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

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirHandOverBillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirHandOverBillDto;

/**
 * 航空正单交接单vo
 * @author foss-liuxue(for IBM)
 * @date 2013-1-10 下午4:42:18
 */
public class AirHandOverBillVO implements Serializable {

	private static final long serialVersionUID = -8627798593147903908L;
	
	/**
	 * 查询航空正单交接单的结果集
	 */
	private List<AirHandOverBillEntity> airHandOverBillList ;  

	/**
	 * 查询航空正单的结果集
	 */
	private List<AirHandOverBillDetailEntity> airHandOverBillDetailList;  
	
	/**
	 * 航空正单明细的实体
	 */
	private AirHandOverBillDetailEntity airHandOverBillDetailEntity;
	
	/**
	 * 航空交接单dto
	 */
	private AirHandOverBillDto airHandOverBillDto;
	
	/**
	 * 航空交接单dto集合
	 */
	private List<AirHandOverBillDto> airHandOverBillDtos;
	
	/**
	 * 正单交接单号
	 */
	private String airHandOverBillNo;
	
	/**
	 * 获取 正单交接单号.
	 *
	 * @return the 正单交接单号
	 */
	public String getAirHandOverBillNo() {
		return airHandOverBillNo;
	}

	/**
	 * 设置 正单交接单号.
	 *
	 * @param airHandOverBillNo the new 正单交接单号
	 */
	public void setAirHandOverBillNo(String airHandOverBillNo) {
		this.airHandOverBillNo = airHandOverBillNo;
	}

	/**
	 * 获取 航空交接单dto集合.
	 *
	 * @return the 航空交接单dto集合
	 */
	public List<AirHandOverBillDto> getAirHandOverBillDtos() {
		return airHandOverBillDtos;
	}

	/**
	 * 设置 航空交接单dto集合.
	 *
	 * @param airHandOverBillDtos the new 航空交接单dto集合
	 */
	public void setAirHandOverBillDtos(List<AirHandOverBillDto> airHandOverBillDtos) {
		this.airHandOverBillDtos = airHandOverBillDtos;
	}

	/**
	 * 获取 查询航空正单交接单的结果集.
	 *
	 * @return the 查询航空正单交接单的结果集
	 */
	public List<AirHandOverBillEntity> getAirHandOverBillList() {
		return airHandOverBillList;
	}

	/**
	 * 设置 查询航空正单交接单的结果集.
	 *
	 * @param airHandOverBillList the new 查询航空正单交接单的结果集
	 */
	public void setAirHandOverBillList(
			List<AirHandOverBillEntity> airHandOverBillList) {
		this.airHandOverBillList = airHandOverBillList;
	}

	/**
	 * 获取 查询航空正单的结果集.
	 *
	 * @return the 查询航空正单的结果集
	 */
	public List<AirHandOverBillDetailEntity> getAirHandOverBillDetailList() {
		return airHandOverBillDetailList;
	}

	/**
	 * 设置 查询航空正单的结果集.
	 *
	 * @param airHandOverBillDetailList the new 查询航空正单的结果集
	 */
	public void setAirHandOverBillDetailList(
			List<AirHandOverBillDetailEntity> airHandOverBillDetailList) {
		this.airHandOverBillDetailList = airHandOverBillDetailList;
	}

	/**
	 * 获取 航空正单明细的实体.
	 *
	 * @return the 航空正单明细的实体
	 */
	public AirHandOverBillDetailEntity getAirHandOverBillDetailEntity() {
		return airHandOverBillDetailEntity;
	}

	/**
	 * 设置 航空正单明细的实体.
	 *
	 * @param airHandOverBillDetailEntity the new 航空正单明细的实体
	 */
	public void setAirHandOverBillDetailEntity(
			AirHandOverBillDetailEntity airHandOverBillDetailEntity) {
		this.airHandOverBillDetailEntity = airHandOverBillDetailEntity;
	}

	/**
	 * 获取 航空交接单dto.
	 *
	 * @return the 航空交接单dto
	 */
	public AirHandOverBillDto getAirHandOverBillDto() {
		return airHandOverBillDto;
	}

	/**
	 * 设置 航空交接单dto.
	 *
	 * @param airHandOverBillDto the new 航空交接单dto
	 */
	public void setAirHandOverBillDto(AirHandOverBillDto airHandOverBillDto) {
		this.airHandOverBillDto = airHandOverBillDto;
	}

}