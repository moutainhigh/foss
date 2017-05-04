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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/VehicleTypeEntity.java
 * 
 * FILE NAME        	: VehicleTypeEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 用来存储交互“车辆车型”的数据库对应实体：SUC-109
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-11 下午4:44:41
 * @since
 * @version
 */
public class VehicleTypeEntity extends BaseEntity {
	
    /**
     * 序列ID
     */
    private static final long serialVersionUID = 6681288354019469345L;
    
    /**
     * 序号
     */
    private BigDecimal seq;

    /**
     * 车辆类型（厢式车/挂车）
     */
    private String vehicleType;

    /**
     * 车型车长
     */
    private BigDecimal vehicleLength;

    /**
     * 是否启用
     */
    private String active;
    
    /**
     * 车型编码
     */
    private String vehicleLengthCode;
    
    /**
     * 公司车或外请车
     */
    private String vehicleSort;

    /**
     * 每公里费用
     */
    private BigDecimal eachKilometersFees;
    
    /**
     * 车型名称
     */
    private String vehicleLengthName;
    
    /**
     * 查询参数
     */
    private String queryParam;
    
    /**
     * @return  the seq
     */
    public BigDecimal getSeq() {
        return seq;
    }
    
    /**
     * @param seq the seq to set
     */
    public void setSeq(BigDecimal seq) {
        this.seq = seq;
    }

    /**
     * @return  the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return  the vehicleLength
     */
    public BigDecimal getVehicleLength() {
        return vehicleLength;
    }

    /**
     * @param vehicleLength the vehicleLength to set
     */
    public void setVehicleLength(BigDecimal vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    /**
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * @return  the vehicleLengthCode
     */
    public String getVehicleLengthCode() {
        return vehicleLengthCode;
    }
    
    /**
     * @param vehicleLengthCode the vehicleLengthCode to set
     */
    public void setVehicleLengthCode(String vehicleLengthCode) {
        this.vehicleLengthCode = vehicleLengthCode;
    }
    
    /**
     * @return  the vehicleSort
     */
    public String getVehicleSort() {
        return vehicleSort;
    }
    
    /**
     * @param vehicleSort the vehicleSort to set
     */
    public void setVehicleSort(String vehicleSort) {
        this.vehicleSort = vehicleSort;
    }
    
    /**
     * @return  the eachKilometersFees
     */
    public BigDecimal getEachKilometersFees() {
        return eachKilometersFees;
    }
    
    /**
     * @param eachKilometersFees the eachKilometersFees to set
     */
    public void setEachKilometersFees(BigDecimal eachKilometersFees) {
        this.eachKilometersFees = eachKilometersFees;
    }

    /**
     * @return  the vehicleLengthName
     */
    public String getVehicleLengthName() {
        return vehicleLengthName;
    }

	/**
     * @param vehicleLengthName the vehicleLengthName to set
     */
    public void setVehicleLengthName(String vehicleLengthName) {
        this.vehicleLengthName = vehicleLengthName;
    }

	/**
	 * @return queryParam
	 */
	public String getQueryParam() {
		return queryParam;
	}

	/**
	 * @param  queryParam  
	 */
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
}
