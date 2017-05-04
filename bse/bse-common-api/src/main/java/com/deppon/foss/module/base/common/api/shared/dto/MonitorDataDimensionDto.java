/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/api/shared/dto/MonitorDataDimensionDto.java
 * 
 * FILE NAME        	: MonitorDataDimensionDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common-api
 * PACKAGE NAME: com.deppon.foss.module.base.common.api.shared.dto
 * FILE    NAME: MonitorDataDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.api.shared.dto;

import java.util.Date;

/**
 * 指标数据DTO
 * 
 * @author ibm-zhuwei
 * @date 2013-2-27 下午2:15:14
 */
public class MonitorDataDimensionDto {

	/**
	 * 监控日期
	 */
	private Date monitorDate;

	/**
	 * 监控时间段
	 */
	private String monitorTimeRange;
	
	/**
	 * 默认构造函数
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午3:58:58
	 */
	public MonitorDataDimensionDto() {
		
	}
	
	/**
	 * 构造函数
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午3:55:57
	 */
	public MonitorDataDimensionDto(Date monitorDate, String monitorTimeRange) {
		this.monitorDate = monitorDate;
		this.monitorTimeRange = monitorTimeRange;
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午4:02:28
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return monitorDate.hashCode() + monitorTimeRange.hashCode();
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author ibm-zhuwei
	 * @date 2013-2-28 下午4:02:36
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		MonitorDataDimensionDto dto = (MonitorDataDimensionDto)obj;
		
		return dto.getMonitorDate().equals(this.getMonitorDate())
				&& dto.getMonitorTimeRange().equals(this.getMonitorTimeRange());
	}

	/**
	 * @return monitorDate
	 */
	public Date getMonitorDate() {
		return monitorDate;
	}

	/**
	 * @param monitorDate
	 */
	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	/**
	 * @return monitorTimeRange
	 */
	public String getMonitorTimeRange() {
		return monitorTimeRange;
	}

	/**
	 * @param monitorTimeRange
	 */
	public void setMonitorTimeRange(String monitorTimeRange) {
		this.monitorTimeRange = monitorTimeRange;
	}

}
