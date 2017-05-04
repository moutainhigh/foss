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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/LeasedVehicleWithDriverDto.java
 * 
 * FILE NAME        	: LeasedVehicleWithDriverDto.java
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
 * 用来交互“外请（司机、车）”的数据实体相关联信息的DTO
 * <p>基本属性包括：外请车的基本信息、外请司机的基本信息</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-6 下午5:29:22</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-6 下午5:29:22
 * @since
 * @version
 */
public class LeasedVehicleWithDriverDto implements Serializable{
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -7401943016672594735L;

    /**
     * 外请司机信息
     */
    private DriverBaseDto leasedDriver;
    
    /**
     * 外请车信息
     */
    private VehicleBaseDto leasedVehicle;

    
    /**
     * 外请司机信息
     * 
     * @return  the leasedDriver
     */
    public DriverBaseDto getLeasedDriver() {
        return leasedDriver;
    }
    
    /**
     * @param leasedDriver the leasedDriver to set
     */
    public void setLeasedDriver(DriverBaseDto leasedDriver) {
        this.leasedDriver = leasedDriver;
    }
    
    /**
     * 外请车信息
     * 
     * @return  the leasedVehicle
     */
    public VehicleBaseDto getLeasedVehicle() {
        return leasedVehicle;
    }

    /**
     * @param leasedVehicle the leasedVehicle to set
     */
    public void setLeasedVehicle(VehicleBaseDto leasedVehicle) {
        this.leasedVehicle = leasedVehicle;
    }
}
