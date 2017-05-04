/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/ShowDeptEntity.java
 * 
 * FILE NAME        	: ShowDeptEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class ShowDeptEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 省份名称
	 */
	private String proName;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 区县名称
	 */
	private String countyName;
	/**
	 * 国家名称
	 */
	private String nationName;
	/**
	 * 部门名称
	 */
        private String deptName;
        /**
         * 部门标杆编码
         */
    private String deptStandCode;
    
	/**
	 * 获取 省份名称.
	 *
	 * @return the 省份名称
	 */
	public String getProName() {
		return proName;
	}
	
	/**
	 * 设置 省份名称.
	 *
	 * @param proName the new 省份名称
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	/**
	 * 获取 城市名称.
	 *
	 * @return the 城市名称
	 */
	public String getCityName() {
		return cityName;
	}
	
	/**
	 * 设置 城市名称.
	 *
	 * @param cityName the new 城市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	/**
	 * 获取 国家名称.
	 *
	 * @return the 国家名称
	 */
	public String getNationName() {
		return nationName;
	}
	
	/**
	 * 设置 国家名称.
	 *
	 * @param nationName the new 国家名称
	 */
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	
	/**
	 * 获取 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}
	
	/**
	 * 设置 部门名称.
	 *
	 * @param deptName the new 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	/**
	 * 获取 区县名称.
	 *
	 * @return the 区县名称
	 */
	public String getCountyName() {
		return countyName;
	}
	
	/**
	 * 设置 区县名称.
	 *
	 * @param countyName the new 区县名称
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	/**
	 * 获取 部门标杆编码.
	 *
	 * @return the 部门标杆编码
	 */
	public String getDeptStandCode() {
		return deptStandCode;
	}
	
	/**
	 * 设置 部门标杆编码.
	 *
	 * @param deptStandCode the new 部门标杆编码
	 */
	public void setDeptStandCode(String deptStandCode) {
		this.deptStandCode = deptStandCode;
	}
	

	

	
}