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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/SpecialAddresstDto.java
 * 
 * FILE NAME        	: SpecialAddresstDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FlightException;

/**
 * 
 * 特殊地址实现分页的Dto
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:23:57 </p>
 * @author 何波
 * @date 2013-2-20 下午2:23:57
 * @since
 * @version
 */
public class SpecialAddresstDto extends SpecialAddressEntity implements
		Serializable {

	private static final long serialVersionUID = 2291803260137286517L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialAddresstDto.class);
	
	private String nationName;
	private String provinceName;
	private String cityName;
	private String countyName;

	public SpecialAddresstDto(SpecialAddressEntity specialAddress) {

		try {
			BeanUtils.copyProperties(specialAddress, this, new String[] {
					"createDate", "modifyDate" });

			this.setCreateDate(specialAddress.getCreateDate());
			this.setModifyDate(specialAddress.getModifyDate());
		} catch (FlightException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}

	}

	public SpecialAddresstDto() {

	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

}
