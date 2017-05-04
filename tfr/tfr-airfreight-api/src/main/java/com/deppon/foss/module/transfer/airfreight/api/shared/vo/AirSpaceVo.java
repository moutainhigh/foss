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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirSpaceVo.java
 *  
 *  FILE NAME          :AirSpaceVo.java
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;
/**
 * 舱位管理VO,用于与前台数据交互
 * @author 038300-foss-pengzhen
 * @date 2012-10-29 下午3:30:14
 */
public class AirSpaceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7455060359324579443L;
	
	/**
	 * 舱位数据传输对象
	 */
	private AirSpaceDto dto; 
	
	/**
	 * 舱位数据传输对象列表
	 */
	private List<AirSpaceDto> airSpaceDtoList; 
	
	/**
	 * 服务器日期下一天
	 */
	private Date nextDay;

	/**
	 * 获取 服务器日期下一天.
	 *
	 * @return the 服务器日期下一天
	 */
	public Date getNextDay() {
		return nextDay;
	}

	/**
	 * 设置 服务器日期下一天.
	 *
	 * @param nextDay the new 服务器日期下一天
	 */
	public void setNextDay(Date nextDay) {
		this.nextDay = nextDay;
	}

	/**
	 * 获取 舱位数据传输对象.
	 *
	 * @return the 舱位数据传输对象
	 */
	public AirSpaceDto getDto() {
		return dto;
	}

	/**
	 * 设置 舱位数据传输对象.
	 *
	 * @param dto the new 舱位数据传输对象
	 */
	public void setDto(AirSpaceDto dto) {
		this.dto = dto;
	}

	/**
	 * 获取 舱位数据传输对象列表.
	 *
	 * @return the 舱位数据传输对象列表
	 */
	public List<AirSpaceDto> getAirSpaceDtoList() {
		return airSpaceDtoList;
	}

	/**
	 * 设置 舱位数据传输对象列表.
	 *
	 * @param airSpaceDtoList the new 舱位数据传输对象列表
	 */
	public void setAirSpaceDtoList(List<AirSpaceDto> airSpaceDtoList) {
		this.airSpaceDtoList = airSpaceDtoList;
	}

	
}