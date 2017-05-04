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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/AirportDetailVo.java
 * 
 * FILE NAME        	: AirportDetailVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.shared.vo
 * FILE    NAME: AirportDetailVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;

/**
 * 机场详情vo
 * @author 078823-foss-panGuangJun
 * @date 2012-12-28 下午5:30:28
 */
public class AirportDetailVo extends AirportEntity{

	/**
	*serialVersionUID
	*/
	private static final long serialVersionUID = 4216944585482484100L;
	//机场市名称
	private String cityName;
	//机场区县名称
	private String countyName;
	/**
	 *getter
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 *setter
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 *getter
	 */
	public String getCountyName() {
		return countyName;
	}
	/**
	 *setter
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
}
