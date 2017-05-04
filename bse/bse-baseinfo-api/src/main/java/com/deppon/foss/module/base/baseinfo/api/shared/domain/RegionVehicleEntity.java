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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/RegionVehicleEntity.java
 * 
 * FILE NAME        	: RegionVehicleEntity.java
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
 * 车辆定区实体类.
 *
 * @author 094463-foss-xieyantao
 * @date 2012-10-11 上午11:13:42
 * @since
 * @version
 */
public class RegionVehicleEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8549347585428939872L;
    
    /**
     * 区域名称.
     */
    private String regionName;
    
    /**
     * 区域虚拟编码(存放集中接送货大区、小区虚拟编码).
     */
    private String regionCode;
    
    /**
     * 区域编码.(扩展)
     */
    private String code;
    
    /**
     * 接送货类型.
     */
    private String regionType;
    
    /**
     * 车牌号.
     */
    private String vehicleNo;
    
    /**
     * 车辆所属车队部门编码.
     */
    private String vehicleDept;
    
    /**
     * 车辆所属车队部门编码集合(扩展).
     */
    private List<String> vehicleDeptList;
    
    /**
     * 车辆所属车队部门名称（扩展）.
     */
    private String vehicleDeptName;
    
    /**
     * 车辆职责类型.
     */
    private String vehicleType;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    /**
     * 区域性质：大区：DictionaryValueConstants.REGION_NATURE_BQ 小区:DictionaryValueConstants.REGION_NATURE_SQ
     */
    private String regionNature;

    
    /**
     * 获取 车辆所属车队部门编码集合(扩展).
     *
     * @return  the vehicleDeptList
     */
    public List<String> getVehicleDeptList() {
        return vehicleDeptList;
    }

    
    /**
     * 设置 车辆所属车队部门编码集合(扩展).
     *
     * @param vehicleDeptList the vehicleDeptList to set
     */
    public void setVehicleDeptList(List<String> vehicleDeptList) {
        this.vehicleDeptList = vehicleDeptList;
    }

    /**
     * 获取 区域名称.
     *
     * @return  the regionName
     */
    public String getRegionName() {
        return regionName;
    }
    
    /**
     * 获取 车辆所属车队部门名称（扩展）.
     *
     * @return  the vehicleDeptName
     */
    public String getVehicleDeptName() {
        return vehicleDeptName;
    }

    
    /**
     * 设置 车辆所属车队部门名称（扩展）.
     *
     * @param vehicleDeptName the vehicleDeptName to set
     */
    public void setVehicleDeptName(String vehicleDeptName) {
        this.vehicleDeptName = vehicleDeptName;
    }

    /**
     * 设置 区域名称.
     *
     * @param regionName the regionName to set
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    /**
     * 获取 区域编码(存放集中接送货大区、小区虚拟编码).
     *
     * @return  the regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }
    
    /**
     * 设置 区域编码(存放集中接送货大区、小区虚拟编码).
     *
     * @param regionCode the regionCode to set
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    /**
     * 获取 接送货类型.
     *
     * @return  the regionType
     */
    public String getRegionType() {
        return regionType;
    }
    
    /**
     * 设置 接送货类型.
     *
     * @param regionType the regionType to set
     */
    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }
    
    /**
     * 获取 车牌号.
     *
     * @return  the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }
    
    /**
     * 设置 车牌号.
     *
     * @param vehicleNo the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
    
    /**
     * 获取 车辆所属车队.
     *
     * @return  the vehicleDept
     */
    public String getVehicleDept() {
        return vehicleDept;
    }
    
    /**
     * 设置 车辆所属车队.
     *
     * @param vehicleDept the vehicleDept to set
     */
    public void setVehicleDept(String vehicleDept) {
        this.vehicleDept = vehicleDept;
    }
    
    /**
     * 获取 车辆职责类型.
     *
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * 设置 车辆职责类型.
     *
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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
     * 获取 区域性质：大区：DictionaryValueConstants.
     *
     * @return  the regionNature
     */
    public String getRegionNature() {
        return regionNature;
    }
    
    /**
     * 设置 区域性质：大区：DictionaryValueConstants.
     *
     * @param regionNature the regionNature to set
     */
    public void setRegionNature(String regionNature) {
        this.regionNature = regionNature;
    }
    
    /**
     * 获取 区域编码.
     *
     * @return  the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 设置 区域编码.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }  
}