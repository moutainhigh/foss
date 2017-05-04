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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/OwnVehicleRegionDto.java
 * 
 * FILE NAME        	: OwnVehicleRegionDto.java
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
 * 车辆区域的封装DTO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 下午5:39:25</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 下午5:39:25
 * @since
 * @version
 */
public class OwnVehicleRegionDto extends VehicleAssociationDto implements Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -6089818335305140702L;

    /**
     * 定人定区所属区域编码
     */
    private String vehicleRegionCode;
    
    /**
     * 定人定区所属区域名称
     */
    private String vehicleRegionName;
    
    /**
     * 定人定区所属区域类型（大、小区）
     */
    private String vehicleRegionType;

    /**
     * 定人定区接送货类型（接货区、送货区）
     */
    private String vehicleGoodsType;
    
    /**
     * @return  the vehicleRegionCode
     */
    public String getVehicleRegionCode() {
        return vehicleRegionCode;
    }
    
    /**
     * 定人定区所属区域编码
     * 
     * @param vehicleRegionCode the vehicleRegionCode to set
     */
    public void setVehicleRegionCode(String vehicleRegionCode) {
        this.vehicleRegionCode = vehicleRegionCode;
    }

    /**
     * 定人定区所属区域名称
     * 
     * @return  the vehicleRegionName
     */
    public String getVehicleRegionName() {
        return vehicleRegionName;
    }

    /**
     * @param vehicleRegionName the vehicleRegionName to set
     */
    public void setVehicleRegionName(String vehicleRegionName) {
        this.vehicleRegionName = vehicleRegionName;
    }
    
    /**
     * 定人定区所属区域类型（大、小区）
     * 
     * @return  the vehicleRegionType
     */
    public String getVehicleRegionType() {
        return vehicleRegionType;
    }
    
    /**
     * @param vehicleRegionType the vehicleRegionType to set
     */
    public void setVehicleRegionType(String vehicleRegionType) {
        this.vehicleRegionType = vehicleRegionType;
    }
    
    /**
     * 定人定区接送货类型（接货区、送货区）
     * 
     * @return  the vehicleGoodsType
     */
    public String getVehicleGoodsType() {
        return vehicleGoodsType;
    }
    
    /**
     * @param vehicleGoodsType the vehicleGoodsType to set
     */
    public void setVehicleGoodsType(String vehicleGoodsType) {
        this.vehicleGoodsType = vehicleGoodsType;
    }
}
