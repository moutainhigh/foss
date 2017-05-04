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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/VehicleBrandEntity.java
 * 
 * FILE NAME        	: VehicleBrandEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“车辆品牌”的数据库对应实体：无SUC,由LMS调用同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-11 下午4:52:28</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午4:52:28
 * @since
 * @version
 */
public class VehicleBrandEntity extends BaseEntity {
	
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -552208656038913057L;

    /**
     * 品牌编码.
     */
    private String brandCode;

    /**
     * 车辆品牌名称.
     */
    private String brandName;

    /**
     * 车辆种类.
     */
    private String vehicleType;

    /**
     * 是否启用.
     */
    private String active;

    /**
     * 获取 品牌编码.
     *
     * @return  the brandCode
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * 设置 品牌编码.
     *
     * @param brandCode the brandCode to set
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    /**
     * 获取 车辆品牌名称.
     *
     * @return  the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置 车辆品牌名称.
     *
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取 车辆种类.
     *
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }
    
    /**
     * 设置 车辆种类.
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
}