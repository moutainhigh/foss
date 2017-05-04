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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/RegionVehicleDto.java
 * 
 * FILE NAME        	: RegionVehicleDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;


/**
 * 车辆定区相关详细信息DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-29 上午9:56:31 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-29 上午9:56:31
 * @since
 * @version
 */
public class RegionVehicleDto implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -8257561087588678430L;
    
    /**
     * 集中接送货大区实体类.
     */
    private BigZoneEntity bigZoneEntity;
    
    /**
     * 集中接送货小区实体类.
     */
    private SmallZoneEntity smallZoneEntity;
    
    /**
     * 绑定定车定区信息.
     */
    private List<RegionVehicleEntity> regionVehicleList;
    /**
     * 绑定定车定区更具体信息.
     */
    private List<RegionVehicleInfoDto> regionVehicleInfoDtoList;
    
    /**
     * 获取 集中接送货大区实体类.
     *
     * @return  the bigZoneEntity
     */
    public BigZoneEntity getBigZoneEntity() {
        return bigZoneEntity;
    }
    
    /**
     * 设置 集中接送货大区实体类.
     *
     * @param bigZoneEntity the bigZoneEntity to set
     */
    public void setBigZoneEntity(BigZoneEntity bigZoneEntity) {
        this.bigZoneEntity = bigZoneEntity;
    }
    
    /**
     * 获取 集中接送货小区实体类.
     *
     * @return  the smallZoneEntity
     */
    public SmallZoneEntity getSmallZoneEntity() {
        return smallZoneEntity;
    }
    
    /**
     * 设置 集中接送货小区实体类.
     *
     * @param smallZoneEntity the smallZoneEntity to set
     */
    public void setSmallZoneEntity(SmallZoneEntity smallZoneEntity) {
        this.smallZoneEntity = smallZoneEntity;
    }
    
    /**
     * 获取 绑定定车定区信息.
     *
     * @return  the regionVehicleList
     */
    public List<RegionVehicleEntity> getRegionVehicleList() {
        return regionVehicleList;
    }
    
    /**
     * 设置 绑定定车定区信息.
     *
     * @param regionVehicleList the regionVehicleList to set
     */
    public void setRegionVehicleList(List<RegionVehicleEntity> regionVehicleList) {
        this.regionVehicleList = regionVehicleList;
    }
    /**
     * 获取 绑定定车定区信息.
     *
     * @return  the regionVehicleInfoDtoList
     */
	public List<RegionVehicleInfoDto> getRegionVehicleInfoDtoList() {
		return regionVehicleInfoDtoList;
	}
    /**
     * 设置 绑定定车定区信息.
     *
     * @param regionVehicleInfoDtoList the regionVehicleInfoDtoList to set
     */
	public void setRegionVehicleInfoDtoList(
			List<RegionVehicleInfoDto> regionVehicleInfoDtoList) {
		this.regionVehicleInfoDtoList = regionVehicleInfoDtoList;
	}

}
