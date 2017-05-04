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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/CommonAllZoneDto.java
 * 
 * FILE NAME        	: CommonAllZoneDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;

/**
 * 集中接送货大小区Dto
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:24:54
 */
public class CommonAllZoneDto implements Serializable {

	private static final long serialVersionUID = -1282807311447660654L;
	
	private String id;
	/**
	 * 查询参数
	 */
	private String queryParam;
	/**
	 * 类别
	 */
	private String zoneType;

	/**
	 * 小区编码.
	 */
	private String regionCode;

	/**
	 * 小区名称.
	 */
	private String regionName;

	/**
	 * 管理部门编码.
	 */
	private String management;

	/**
	 * 管理部门名称.
	 */
	private String managementName;
	  /**
     * 所属车队.
     */
    private String transDepartmentCode;
    
    /**
     * 所属车队名称.
     */
    private String transDeptName;
	/**
	 * 是否启用.
	 */
	private String active;

	/**
	 * 备注.
	 */
	private String notes;

	/**
	 * 虚拟编码.
	 */
	private String virtualCode;

	/**
	 * 区域类型：接货区：DictionaryValueConstants.REGION_TYPE_PK
	 * 送货区：DictionaryValueConstants.REGION_TYPE_DE
	 */
	private String regionType;

	/**
	 * GIS系统小区范围ＩＤ.
	 */
	private String gisId;

	/**
	 * 所属大区虚拟编码.
	 */
	private String bigZoneCode;

	/**
	 * 所属大区名称(扩展).
	 */
	private String bigZoneName;

	/**
	 * 所在省编码.
	 */
	private String provCode;

	/**
	 * 所在省名称（扩展）.
	 */
	private String provName;

	/**
	 * 所在市编码.
	 */
	private String cityCode;

	/**
	 * 所在市名称（扩展）.
	 */
	private String cityName;

	/**
	 * 所在区县.
	 */
	private String countyCode;

	/**
	 * 所在区县名称（扩展）.
	 */
	private String countyName;

	
	public String getQueryParam() {
		return queryParam;
	}

	
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	
	public String getZoneType() {
		return zoneType;
	}

	
	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	
	public String getRegionCode() {
		return regionCode;
	}

	
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	
	public String getRegionName() {
		return regionName;
	}

	
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	
	public String getManagement() {
		return management;
	}

	
	public void setManagement(String management) {
		this.management = management;
	}

	
	public String getManagementName() {
		return managementName;
	}

	
	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}

	
	public String getTransDepartmentCode() {
		return transDepartmentCode;
	}

	
	public void setTransDepartmentCode(String transDepartmentCode) {
		this.transDepartmentCode = transDepartmentCode;
	}

	
	public String getTransDeptName() {
		return transDeptName;
	}

	
	public void setTransDeptName(String transDeptName) {
		this.transDeptName = transDeptName;
	}

	
	public String getActive() {
		return active;
	}

	
	public void setActive(String active) {
		this.active = active;
	}

	
	public String getNotes() {
		return notes;
	}

	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	public String getVirtualCode() {
		return virtualCode;
	}

	
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	
	public String getRegionType() {
		return regionType;
	}

	
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	
	
	public String getBigZoneCode() {
		return bigZoneCode;
	}


	
	
	public String getGisId() {
		return gisId;
	}


	
	public void setGisId(String gisId) {
		this.gisId = gisId;
	}


	public void setBigZoneCode(String bigZoneCode) {
		this.bigZoneCode = bigZoneCode;
	}


	
	public String getBigZoneName() {
		return bigZoneName;
	}


	
	public void setBigZoneName(String bigZoneName) {
		this.bigZoneName = bigZoneName;
	}


	public String getProvCode() {
		return provCode;
	}

	
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	
	public String getProvName() {
		return provName;
	}

	
	public void setProvName(String provName) {
		this.provName = provName;
	}

	
	public String getCityCode() {
		return cityCode;
	}

	
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	
	public String getCityName() {
		return cityName;
	}

	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	
	public String getCountyCode() {
		return countyCode;
	}

	
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	
	public String getCountyName() {
		return countyName;
	}

	
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}


	
	public String getId() {
		return id;
	}


	
	public void setId(String id) {
		this.id = id;
	}

}
