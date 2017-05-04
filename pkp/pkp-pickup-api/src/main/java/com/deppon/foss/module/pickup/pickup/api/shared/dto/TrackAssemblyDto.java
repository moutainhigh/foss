/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/TrackAssemblyDto.java
 * 
 * FILE NAME        	: TrackAssemblyDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 运单配载信息dto
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 4:24:59 PM
 */
public class TrackAssemblyDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5475764127241641016L;

	/**
	 * 配载工具No（汽运配载单的车次 Or 空运正单的航班）
	 */
	private String assemblyToolNo;

	/**
	 * 发车时间
	 */
	private Date departureTime;
	/**
	 * 预到时间
	 */
	private Date planArriveTime;

	/**
	 * 获取 配载工具No（汽运配载单的车次 Or 空运正单的航班）.
	 *
	 * @return the 配载工具No（汽运配载单的车次 Or 空运正单的航班）
	 */
	public String getAssemblyToolNo() {
		return assemblyToolNo;
	}

	/**
	 * 设置 配载工具No（汽运配载单的车次 Or 空运正单的航班）.
	 *
	 * @param assemblyToolNo the new 配载工具No（汽运配载单的车次 Or 空运正单的航班）
	 */
	public void setAssemblyToolNo(String assemblyToolNo) {
		this.assemblyToolNo = assemblyToolNo;
	}

	/**
	 * 获取 发车时间.
	 *
	 * @return the 发车时间
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * 设置 发车时间.
	 *
	 * @param departureTime the new 发车时间
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * 获取 预到时间.
	 *
	 * @return the 预到时间
	 */
	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	/**
	 * 设置 预到时间.
	 *
	 * @param planArriveTime the new 预到时间
	 */
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

}