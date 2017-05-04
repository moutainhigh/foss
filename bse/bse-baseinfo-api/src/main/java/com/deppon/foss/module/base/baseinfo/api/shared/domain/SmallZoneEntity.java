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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SmallZoneEntity.java
 * 
 * FILE NAME        	: SmallZoneEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 集中接送货小区实体类.
 *
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午11:03:17
 * @since
 * @version
 */
public class SmallZoneEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7419133620618465621L;
    /**
     * 小区编码.
     */
    private String id;
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
     * 所属管理部门编码集合.
     */
    private List<String> managementCodeList;
    
    /**
     * 管理部门名称.
     */
    private String managementName;

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
     *        送货区：DictionaryValueConstants.REGION_TYPE_DE
     */
    private String regionType;

    /**
     * GIS系统小区范围ＩＤ.
     */
    private String gisid;
    
    /**
     * GIS系统小区范围 面积.
     */
    private String gisArea;

	/**
     * 所属大区虚拟编码.
     */
    private String bigzonecode;
    
    /**
     * 所属大区虚拟编码1(扩展字段).
     */
    private String bigzonecode1;
    
    /**
     * 所属大区名称(扩展).
     */
    private String bigzoneName;

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
    
    
    private String  navigationDistance;
    
   

	public String getNavigationDistance() {
		return navigationDistance;
	}



	public void setNavigationDistance(String navigationDistance) {
		this.navigationDistance = navigationDistance;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	/**
     * 获取 小区编码.
     *
     * @return  the regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }
    
    
	
	/**
	 * 获取 所属大区虚拟编码1(扩展字段).
	 *
	 * @return  the bigzonecode1
	 */
	public String getBigzonecode1() {
	    return bigzonecode1;
	}


	
	/**
	 * 设置 所属大区虚拟编码1(扩展字段).
	 *
	 * @param bigzonecode1 the bigzonecode1 to set
	 */
	public void setBigzonecode1(String bigzonecode1) {
	    this.bigzonecode1 = bigzonecode1;
	}


	/**
	 * 获取 所属管理部门编码集合.
	 *
	 * @return  the managementCodeList
	 */
	public List<String> getManagementCodeList() {
	    return managementCodeList;
	}

	
	/**
	 * 设置 所属管理部门编码集合.
	 *
	 * @param managementCodeList the managementCodeList to set
	 */
	public void setManagementCodeList(List<String> managementCodeList) {
	    this.managementCodeList = managementCodeList;
	}

    /**
     * 设置 小区编码.
     *
     * @param regionCode the regionCode to set
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    /**
     * 获取 小区名称.
     *
     * @return  the regionName
     */
    public String getRegionName() {
        return regionName;
    }
    
    /**
     * 设置 小区名称.
     *
     * @param regionName the regionName to set
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    /**
     * 获取 管理部门编码.
     *
     * @return  the management
     */
    public String getManagement() {
        return management;
    }
    
    /**
     * 设置 管理部门编码.
     *
     * @param management the management to set
     */
    public void setManagement(String management) {
        this.management = management;
    }
    
    /**
     * 获取 管理部门名称.
     *
     * @return  the managementName
     */
    public String getManagementName() {
        return managementName;
    }
    
    /**
     * 设置 管理部门名称.
     *
     * @param managementName the managementName to set
     */
    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
   
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
 
    /**
     * 获取 备注.
     *
     * @return  the notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * 设置 备注.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
   
    /**
     * 获取 区域类型：接货区：DictionaryValueConstants.
     *
     * @return  the regionType
     */
    public String getRegionType() {
        return regionType;
    }
    
    /**
     * 设置 区域类型：接货区：DictionaryValueConstants.
     *
     * @param regionType the regionType to set
     */
    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }
    
    /**
     * 获取 gIS系统小区范围ＩＤ.
     *
     * @return  the gisid
     */
    public String getGisid() {
        return gisid;
    }
    
    /**
     * 设置 gIS系统小区范围ＩＤ.
     *
     * @param gisid the gisid to set
     */
    public void setGisid(String gisid) {
        this.gisid = gisid;
    }
    
    /**
     * 获取 所属大区虚拟编码.
     *
     * @return  the bigzonecode
     */
    public String getBigzonecode() {
        return bigzonecode;
    }
    
    /**
     * 设置 所属大区虚拟编码.
     *
     * @param bigzonecode the bigzonecode to set
     */
    public void setBigzonecode(String bigzonecode) {
        this.bigzonecode = bigzonecode;
    }
    
    /**
     * 获取 所属大区名称(扩展).
     *
     * @return  the bigzoneName
     */
    public String getBigzoneName() {
        return bigzoneName;
    }
  
    /**
     * 设置 所属大区名称(扩展).
     *
     * @param bigzoneName the bigzoneName to set
     */
    public void setBigzoneName(String bigzoneName) {
        this.bigzoneName = bigzoneName;
    }
    
    /**
     * 获取 所在省编码.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }
    
    /**
     * 设置 所在省编码.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }
    
    /**
     * 获取 所在省名称（扩展）.
     *
     * @return  the provName
     */
    public String getProvName() {
        return provName;
    }
    
    /**
     * 设置 所在省名称（扩展）.
     *
     * @param provName the provName to set
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }
    
    /**
     * 获取 所在市编码.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }
    
    /**
     * 设置 所在市编码.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 获取 所在市名称（扩展）.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }
    
    /**
     * 设置 所在市名称（扩展）.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    /**
     * 获取 所在区县.
     *
     * @return  the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }
    
    /**
     * 设置 所在区县.
     *
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }
    
    /**
     * 获取 所在区县名称（扩展）.
     *
     * @return  the countyName
     */
    public String getCountyName() {
        return countyName;
    }
    
    /**
     * 设置 所在区县名称（扩展）.
     *
     * @param countyName the countyName to set
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }  
    
    /**
     * 获取GIS 范围面积
     *
     */
    public String getGisArea() {
		return gisArea;
	}

    /**
     * 设置GIS 范围面积
     * @param gisArea
     */
	public void setGisArea(String gisArea) {
		this.gisArea = gisArea;
	}
}