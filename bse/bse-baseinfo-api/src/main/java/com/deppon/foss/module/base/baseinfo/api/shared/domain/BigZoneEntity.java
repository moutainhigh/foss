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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/BigZoneEntity.java
 * 
 * FILE NAME        	: BigZoneEntity.java
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
 * 集中接送货大区实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-11
 * 上午10:49:27
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午10:49:27
 * @since
 * @version
 */
public class BigZoneEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 6375518632926599731L;
    
    /**
     * 大区编码.
     */
    private String regionCode; 
    
    /**
     * 大区名称.
     */
    private String regionName;
    
    /**
     * 管理部门编码.
     */
    private String management;
    
    /**
     * 所属管理部门编码集合
     */
    private List<String> managementCodeList;
    
    /**
     * 管理部门名称(扩展).
     */
    private String managementName;
    
    /**
     * 区域类型.
     */
    private String type;
    
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

    
    /**
     * 获取 大区编码.
     *
     * @return  the regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @return  the managementCodeList
     */
    public List<String> getManagementCodeList() {
        return managementCodeList;
    }


    
    /**
     * @param managementCodeList the managementCodeList to set
     */
    public void setManagementCodeList(List<String> managementCodeList) {
        this.managementCodeList = managementCodeList;
    }


    /**
     * 设置 大区编码.
     *
     * @param regionCode the regionCode to set
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    
    /**
     * 获取 大区名称.
     *
     * @return  the regionName
     */
    public String getRegionName() {
        return regionName;
    }

    
    /**
     * 设置 大区名称.
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
     * 获取 管理部门名称(扩展).
     *
     * @return  the managementName
     */
    public String getManagementName() {
        return managementName;
    }

    
    /**
     * 设置 管理部门名称(扩展).
     *
     * @param managementName the managementName to set
     */
    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }

    
    /**
     * 获取 区域类型.
     *
     * @return  the type
     */
    public String getType() {
        return type;
    }

    
    /**
     * 设置 区域类型.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    
    /**
     * 获取 所属车队.
     *
     * @return  the transDepartmentCode
     */
    public String getTransDepartmentCode() {
        return transDepartmentCode;
    }

    
    /**
     * 设置 所属车队.
     *
     * @param transDepartmentCode the transDepartmentCode to set
     */
    public void setTransDepartmentCode(String transDepartmentCode) {
        this.transDepartmentCode = transDepartmentCode;
    }

    
    /**
     * 获取 所属车队名称.
     *
     * @return  the transDeptName
     */
    public String getTransDeptName() {
        return transDeptName;
    }

    
    /**
     * 设置 所属车队名称.
     *
     * @param transDeptName the transDeptName to set
     */
    public void setTransDeptName(String transDeptName) {
        this.transDeptName = transDeptName;
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
    
}
